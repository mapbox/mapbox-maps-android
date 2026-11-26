package com.mapbox.maps.extension.compose.style.sources

import androidx.compose.runtime.Composable
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.ShadowLogConfiguration
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Config(shadows = [ShadowLogConfiguration::class])
@RunWith(RobolectricTestRunner::class)
internal class SourceStateTest {

  private val map = mockk<MapboxMap>(relaxed = true)

  private class TestSourceState(
    sourceId: String,
    sourceType: String,
    initialProperties: Map<String, Pair<Boolean, Value>> = emptyMap(),
    initialGeoJsonData: GeoJSONData = GeoJSONData.DEFAULT,
  ) : SourceState(sourceId, sourceType, initialProperties, initialGeoJsonData) {
    @Composable
    override fun UpdateProperties() {
      // no-op
    }

    fun readProperty(name: String): Value? = getProperty(name)
    fun currentData(): GeoJSONData = geoJSONData
    fun setData(data: GeoJSONData) {
      geoJSONData = data
    }
  }

  @Before
  fun setup() {
    // SourceState launches its property collectors on Dispatchers.Main.immediate.
    Dispatchers.setMain(UnconfinedTestDispatcher())
    every { map.styleSourceExists(any()) } returns false
    every { map.addStyleSource(any(), any()) } returns ExpectedFactory.createNone()
    every { map.setStyleSourceProperty(any(), any(), any()) } returns ExpectedFactory.createNone()
    every { map.removeStyleSourceUnchecked(any()) } returns ExpectedFactory.createNone()
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    unmockkAll()
  }

  @Test
  fun `setProperty stores value and getProperty returns the latest`() {
    val state = TestSourceState("src", "vector")

    state.setProperty("a", Value("1"))
    assertEquals(Value("1"), state.readProperty("a"))

    // setting the same property again updates the existing flow value rather than adding a new one
    state.setProperty("a", Value("2"))
    assertEquals(Value("2"), state.readProperty("a"))

    assertNull(state.readProperty("missing"))
  }

  @Test
  fun `initial properties are replayed into the state`() {
    val state = TestSourceState(
      "src",
      "vector",
      initialProperties = mapOf(
        "builder" to (true to Value("b")),
        "regular" to (false to Value("r")),
      ),
    )

    assertEquals(Value("b"), state.readProperty("builder"))
    assertEquals(Value("r"), state.readProperty("regular"))
  }

  @Test
  fun `geoJSONData setter keeps the latest value`() {
    val state = TestSourceState("src", "geojson")
    assertEquals(GeoJSONData.DEFAULT, state.currentData())

    val data = GeoJSONData("{}")
    state.setData(data)
    assertEquals(data, state.currentData())
  }

  @Test
  fun `save captures id, properties with builder flags and geoJSONData`() {
    val data = GeoJSONData("{}")
    val state = TestSourceState(
      "src",
      "geojson",
      initialProperties = mapOf(
        "builder" to (true to Value("b")),
        "regular" to (false to Value("r")),
      ),
      initialGeoJsonData = data,
    )

    val holder = state.save()

    assertEquals("src", holder.sourcedId)
    assertEquals(true to Value("b"), holder.savedProperties["builder"])
    assertEquals(false to Value("r"), holder.savedProperties["regular"])
    assertEquals(data, holder.geoJSONData)
  }

  @Test
  fun `attachToLayer adds the source once, regardless of layer count`() {
    val state = TestSourceState("src", "vector")

    state.attachToLayer("layer-1", map)
    verify(exactly = 1) { map.addStyleSource("src", any()) }

    // A second layer referencing the same source does not re-add it.
    state.attachToLayer("layer-2", map)
    verify(exactly = 1) { map.addStyleSource("src", any()) }
  }

  @Test
  fun `source is removed only when the last layer detaches`() {
    val state = TestSourceState("src", "vector")
    state.attachToLayer("layer-1", map)
    state.attachToLayer("layer-2", map)

    state.detachFromLayer("layer-1", map)
    verify(exactly = 0) { map.removeStyleSourceUnchecked("src") }

    state.detachFromLayer("layer-2", map)
    verify(exactly = 1) { map.removeStyleSourceUnchecked("src") }
  }

  @Test
  fun `a pre-existing source is treated as external and is neither added nor removed`() {
    every { map.styleSourceExists("src") } returns true
    val state = TestSourceState("src", "vector")

    state.attachToLayer("layer-1", map)
    verify(exactly = 0) { map.addStyleSource(any(), any()) }

    state.detachFromLayer("layer-1", map)
    verify(exactly = 0) { map.removeStyleSourceUnchecked(any()) }
  }

  @Test
  fun `a non-builder property is applied via setStyleSourceProperty after attach`() {
    val state = TestSourceState(
      "src",
      "vector",
      initialProperties = mapOf("prop" to (false to Value("v"))),
    )

    state.attachToLayer("layer-1", map)

    verify(exactly = 1) { map.addStyleSource("src", any()) }
    verify { map.setStyleSourceProperty("src", "prop", Value("v")) }
  }

  @Test
  fun `an initial builder property re-adds the source once when first collected on attach`() {
    val state = TestSourceState(
      "src",
      "vector",
      initialProperties = mapOf("builder" to (true to Value("b"))),
    )

    state.attachToLayer("layer-1", map)

    // first add on attach, second add after the builder property is collected
    verify(exactly = 2) { map.addStyleSource("src", any()) }
    verify(exactly = 1) { map.removeStyleSourceUnchecked("src") }
  }

  @Test
  fun `a builder property change after attach removes and re-adds the source with the new value`() {
    val state = TestSourceState(
      "src",
      "vector",
      initialProperties = mapOf("builder" to (true to Value("b"))),
    )
    state.attachToLayer("layer-1", map)
    clearMocks(map, answers = false, recordedCalls = true)

    state.setBuilderProperty("builder", Value("b2"))

    verify(exactly = 1) { map.removeStyleSourceUnchecked("src") }
    verify(exactly = 1) {
      map.addStyleSource(
        "src",
        match {
          @Suppress("UNCHECKED_CAST")
          (it.contents as Map<String, Value>)["builder"] == Value("b2")
        }
      )
    }
    verify(exactly = 0) { map.setStyleSourceProperty(any(), any(), any()) }
  }
}