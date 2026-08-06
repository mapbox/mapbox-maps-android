package com.mapbox.maps.extension.compose.style.internal

import com.mapbox.maps.extension.compose.style.internal.generated.StyleDefaults
import com.mapbox.maps.logE
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class StyleDefaultsTest {

  @After
  public fun tearDown() {
    unmockkAll()
  }

  @Test
  public fun `parse extracts rain snow fog and terrain blocks`() {
    val styleJson = """
      {
        "version": 8,
        "rain": { "density": 0.5, "intensity": 1.0 },
        "snow": { "density": 0.8 },
        "fog": { "color": "white", "horizon-blend": 0.1 },
        "terrain": { "source": "mapbox-dem", "exaggeration": 1.5 }
      }
    """.trimIndent()

    val defaults = StyleDefaults.fromJson(styleJson)

    assertEquals(setOf("density", "intensity"), defaults.rain.keys)
    assertEquals(setOf("density"), defaults.snow.keys)
    assertEquals(setOf("color", "horizon-blend"), defaults.atmosphere.keys)
    assertEquals(setOf("source", "exaggeration"), defaults.terrain.keys)
  }

  @Test
  public fun `parse maps fog key to atmosphere`() {
    val styleJson = """{ "fog": { "color": "blue" } }"""

    val defaults = StyleDefaults.fromJson(styleJson)

    // no top-level "atmosphere" key exists in the style spec, so asserts we didn't
    // accidentally look it up under that name.
    assertEquals(1, defaults.atmosphere.size)
    assertTrue(defaults.atmosphere.containsKey("color"))
  }

  @Test
  public fun `parse returns empty maps when blocks are missing`() {
    val styleJson = """{ "version": 8, "name": "test-style" }"""

    val defaults = StyleDefaults.fromJson(styleJson)

    assertEquals(StyleDefaults.EMPTY, defaults)
  }

  @Test
  public fun `parse returns EMPTY on malformed JSON`() {
    val defaults = StyleDefaults.fromJson("{ not valid json ")

    assertEquals(StyleDefaults.EMPTY, defaults)
  }

  @Test(expected = NumberFormatException::class)
  public fun `parse throws on an integer literal above Long MAX_VALUE`() {
    // onError does not cover this: the JSON is well-formed, but the literal has no '.', so
    // Value.read() calls Long.valueOf and throws NumberFormatException (Value.java:256).
    // Value.fromJson converts only IOException into Expected.error (Value.java:229), so the
    // exception escapes before any Expected exists.
    StyleDefaults.fromJson("""{"rain":{"density":99999999999999999999999}}""")
  }

  @Test
  public fun `parse logs the parse error on malformed JSON`() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } returns Unit

    StyleDefaults.fromJson("{ not valid json ")

    verify { logE("StyleDefaults", match { it.startsWith("Unable to parse style defaults:") }) }
  }

  @Test
  public fun `parse returns EMPTY when root is not a JSON object`() {
    val defaults = StyleDefaults.fromJson("[1, 2, 3]")

    assertEquals(StyleDefaults.EMPTY, defaults)
  }

  @Test
  public fun `parse returns EMPTY for empty string`() {
    val defaults = StyleDefaults.fromJson("")

    assertEquals(StyleDefaults.EMPTY, defaults)
  }

  @Test
  public fun `a root key with the wrong JSON shape falls back to an empty map for that key only`() {
    // "rain" is a string, not an object -- objMap's per-key safe-cast must fall back to emptyMap()
    // for that key alone, without failing the whole parse (the other, well-shaped keys survive).
    val styleJson = """
      {
        "rain": "oops",
        "snow": ["also", "wrong"],
        "fog": { "color": "white" },
        "terrain": 42
      }
    """.trimIndent()

    val defaults = StyleDefaults.fromJson(styleJson)

    assertEquals(emptyMap<String, Any>(), defaults.rain)
    assertEquals(emptyMap<String, Any>(), defaults.snow)
    assertEquals(emptyMap<String, Any>(), defaults.terrain)
    assertEquals(setOf("color"), defaults.atmosphere.keys)
  }
}