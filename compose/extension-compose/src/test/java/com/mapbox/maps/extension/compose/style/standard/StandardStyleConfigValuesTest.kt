package com.mapbox.maps.extension.compose.style.standard

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.expressions.generated.Expression
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class StandardStyleConfigValuesTest {

  @Test
  fun `presetNameOrNull returns the name for a string preset`() {
    assertEquals("day", LightPresetValue("day").presetNameOrNull)
    assertEquals("day", LightPresetValue.DAY.presetNameOrNull)
    assertEquals("night", LightPresetValue.NIGHT.presetNameOrNull)
  }

  @Test
  fun `presetNameOrNull is null for an expression preset`() {
    assertNull(LightPresetValue(Expression.literal(1L)).presetNameOrNull)
  }

  @Test
  fun `string presets are equal by value`() {
    assertEquals(LightPresetValue.DAY, LightPresetValue("day"))
  }

  @Test
  fun `INITIAL and DEFAULT are not equal`() {
    assertNotEquals(LightPresetValue.INITIAL, LightPresetValue.DEFAULT)
  }

  @Test
  fun `notInitial relies on referential identity`() {
    val lookalike = LightPresetValue(Value.valueOf("LightPresetValue.INITIAL"))

    assertEquals(LightPresetValue.INITIAL, lookalike)
    assertTrue(lookalike.notInitial)
    assertFalse(LightPresetValue.INITIAL.notInitial)
    assertTrue(LightPresetValue.DAY.notInitial)
  }

  @Test
  fun `ThemeValue follows the same presetNameOrNull contract`() {
    assertEquals("monochrome", ThemeValue.MONOCHROME.presetNameOrNull)
    assertEquals(ThemeValue.MONOCHROME, ThemeValue("monochrome"))
    assertNull(ThemeValue(Expression.literal(1L)).presetNameOrNull)
    assertTrue(ThemeValue.MONOCHROME.notInitial)
    assertFalse(ThemeValue.INITIAL.notInitial)
  }
}