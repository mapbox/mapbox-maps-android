package com.mapbox.maps.extension.compose.style.internal

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.EventTimeInterval
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.StyleDataLoaded
import com.mapbox.maps.StyleDataLoadedType
import com.mapbox.maps.extension.compose.internal.RootMapNode
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.internal.generated.StyleDefaults
import com.mapbox.maps.extension.compose.style.precipitations.generated.RainState
import com.mapbox.maps.extension.compose.style.precipitations.generated.SnowState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.sources.generated.RasterDemSourceState
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Date

/**
 * Tests consolidated STYLE collector (MAPSAND-2003 fix): single collector replaces four
 * per-property collectors to prevent double-attach races. Parses defaults once per emission,
 * attaches all four properties using current state fields.
 *
 * Requires test seams ([styleDataLoadedEventsProvider], [styleDefaults]) because
 * @JvmSynthetic extensions can't be mocked by mockk (static mocking skips synthetic methods).
 */
@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
public class MapStyleNodeStyleDefaultsTest {

  private val mapboxMap = mockk<MapboxMap>(relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxed = true)
  private val styleDataLoadedFlow = MutableSharedFlow<StyleDataLoaded>(extraBufferCapacity = 4)

  private val fakeDefaults = StyleDefaults(
    rain = mapOf("density" to Value(0.5)),
    snow = mapOf("density" to Value(0.4)),
    atmosphere = mapOf("color" to Value("#fff")),
    terrain = mapOf("exaggeration" to Value(1.5)),
  )

  @Before
  public fun setUp() {
    Dispatchers.setMain(UnconfinedTestDispatcher())
    every { expected.error } returns null
    every { mapboxMap.setStyleRain(any()) } returns expected
    every { mapboxMap.setStyleSnow(any()) } returns expected
    every { mapboxMap.setStyleAtmosphere(any()) } returns expected
    every { mapboxMap.setStyleTerrain(any()) } returns expected
  }

  @After
  public fun tearDown() {
    unmockkAll()
    Dispatchers.resetMain()
  }

  private fun styleLoadedEvent() =
    StyleDataLoaded(StyleDataLoadedType.STYLE, EventTimeInterval(Date(0), Date(0)))

  private fun newNode(
    styleDefaults: (String) -> StyleDefaults = { fakeDefaults },
    atmosphereState: AtmosphereState = AtmosphereState(),
    rainState: RainState = RainState(),
    snowState: SnowState = SnowState(),
    terrainState: TerrainState = TerrainState.DISABLED,
  ): MapStyleNode = MapStyleNode(
    style = "{}",
    mapboxMap = mapboxMap,
    projection = Projection.INITIAL,
    atmosphereState = atmosphereState,
    rainState = rainState,
    snowState = snowState,
    terrainState = terrainState,
    styleDefaults = styleDefaults,
    // Unconfined keeps the tryEmit -> parse -> attach chain synchronous for the assertions below.
    defaultsParseDispatcher = UnconfinedTestDispatcher(),
    styleDataLoadedEventsProvider = { styleDataLoadedFlow },
  )

  @Test
  public fun `first load applies defaults, not merely caches them`() {
    val node = newNode()
    node.onAttached(RootMapNode())

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    val rainSlot = slot<Value>()
    verify(exactly = 1) { mapboxMap.setStyleRain(capture(rainSlot)) }
    assertEquals(mapOf("density" to Value(0.5)), rainSlot.captured.contents)
    verify(exactly = 1) { mapboxMap.setStyleSnow(any()) }
    verify(exactly = 1) { mapboxMap.setStyleAtmosphere(any()) }
    verify(exactly = 1) { mapboxMap.setStyleTerrain(any()) }
  }

  @Test
  public fun `no double-attach per STYLE emission`() {
    val node = newNode()
    node.onAttached(RootMapNode())

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    // Guards the plan-review-caught regression: a naive 5th-collector-alongside-the-old-4 design
    // would call each applier's attachTo (and therefore each native setter) twice per emission.
    verify(exactly = 1) { mapboxMap.setStyleRain(any()) }
    verify(exactly = 1) { mapboxMap.setStyleSnow(any()) }
    verify(exactly = 1) { mapboxMap.setStyleAtmosphere(any()) }
    verify(exactly = 1) { mapboxMap.setStyleTerrain(any()) }
  }

  @Test
  public fun `recomposition before first STYLE event does not call native setters, but is picked up by the pending emission`() {
    val node = newNode(rainState = RainState.DISABLED)
    node.onAttached(RootMapNode())

    // recompose to an enabled RainState before any STYLE event has fired
    node.updateRain(RainState())
    verify(exactly = 0) { mapboxMap.setStyleRain(any()) }

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    // the pending first STYLE emission reads the *current* (recomposed, enabled) rainState field;
    // setStyleRain(Value.nullValue()) is what removeRain() performs under the hood
    verify(exactly = 1) { mapboxMap.setStyleRain(any()) }
    verify(exactly = 0) { mapboxMap.setStyleRain(Value.nullValue()) }
  }

  @Test
  public fun `recomposition after first STYLE event attaches immediately using cached defaults`() {
    val node = newNode()
    node.onAttached(RootMapNode())
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    verify(exactly = 1) { mapboxMap.setStyleRain(any()) }

    // recompose after the first STYLE event: attaches right away, without waiting for another one
    node.updateRain(RainState())

    verify(exactly = 2) { mapboxMap.setStyleRain(any()) }
  }

  @Test
  public fun `pristine JSON is parsed before any attachTo mutates the style`() {
    every { mapboxMap.styleJSON } returns """{"rain":{"density":0.5}}"""
    val node = newNode(styleDefaults = { StyleDefaults.fromJson(it) })
    node.onAttached(RootMapNode())

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    verifyOrder {
      mapboxMap.styleJSON
      mapboxMap.setStyleRain(any())
    }
    // styleJSON must be read exactly once per STYLE emission -- not re-read by a sibling
    // applier's attachTo, and not skipped/cached across the single emission.
    verify(exactly = 1) { mapboxMap.styleJSON }
  }

  @Test
  public fun `rain property-collector jobs are not duplicated across multiple STYLE emissions`() {
    val rainState = RainState()
    val node = newNode(rainState = rainState)
    node.onAttached(RootMapNode())

    // Emit twice with same RainState (style reload, no user change).
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    rainState.applier.setProperty("intensity", Value(0.42))

    // Should be one native call, not duplicated per emission.
    verify(exactly = 1) { mapboxMap.setStyleRainProperty("intensity", Value(0.42)) }
  }

  @Test
  public fun `attachTerrain re-adds the raster-DEM source on every STYLE emission even when terrain state is unchanged`() {
    every { mapboxMap.styleSourceExists(any()) } returns false
    every { mapboxMap.addStyleSource(any(), any()) } returns ExpectedFactory.createNone()
    every { mapboxMap.removeStyleSourceUnchecked(any()) } returns ExpectedFactory.createNone()
    val terrainState = TerrainState(RasterDemSourceState(sourceId = "my-dem-source"))
    val node = newNode(terrainState = terrainState)
    node.onAttached(RootMapNode())

    // First emit: add source.
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    verify(exactly = 1) { mapboxMap.addStyleSource("my-dem-source", any()) }
    verify(exactly = 0) { mapboxMap.removeStyleSourceUnchecked(any()) }

    // Second emit (style reload): native discards sources; detach-then-re-add keeps Kotlin in sync.
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    verify(exactly = 1) { mapboxMap.removeStyleSourceUnchecked("my-dem-source") }
    verify(exactly = 2) { mapboxMap.addStyleSource("my-dem-source", any()) }
  }

  @Test
  public fun `snow recomposition before first STYLE event does not call native setters, but is picked up by the pending emission`() {
    val node = newNode(snowState = SnowState.DISABLED)
    node.onAttached(RootMapNode())

    node.updateSnow(SnowState())
    verify(exactly = 0) { mapboxMap.setStyleSnow(any()) }

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    verify(exactly = 1) { mapboxMap.setStyleSnow(any()) }
  }

  @Test
  public fun `snow recomposition after first STYLE event attaches immediately using cached defaults`() {
    val node = newNode()
    node.onAttached(RootMapNode())
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    verify(exactly = 1) { mapboxMap.setStyleSnow(any()) }

    node.updateSnow(SnowState())

    verify(exactly = 2) { mapboxMap.setStyleSnow(any()) }
  }

  @Test
  public fun `atmosphere recomposition after first STYLE event attaches immediately using cached defaults`() {
    val node = newNode()
    node.onAttached(RootMapNode())
    styleDataLoadedFlow.tryEmit(styleLoadedEvent())
    verify(exactly = 1) { mapboxMap.setStyleAtmosphere(any()) }

    node.updateAtmosphere(AtmosphereState())

    verify(exactly = 2) { mapboxMap.setStyleAtmosphere(any()) }
  }

  @Test
  public fun `terrain recomposition before first STYLE event does not attach the layer, but is picked up by the pending emission`() {
    every { mapboxMap.styleSourceExists(any()) } returns false
    every { mapboxMap.addStyleSource(any(), any()) } returns ExpectedFactory.createNone()
    every { mapboxMap.removeStyleSourceUnchecked(any()) } returns ExpectedFactory.createNone()
    val node = newNode(terrainState = TerrainState.DISABLED)
    node.onAttached(RootMapNode())

    node.updateTerrain(TerrainState(RasterDemSourceState(sourceId = "my-dem-source")))
    verify(exactly = 0) { mapboxMap.addStyleSource(any(), any()) }
    verify(exactly = 0) { mapboxMap.setStyleTerrain(any()) }

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    verify(exactly = 1) { mapboxMap.addStyleSource("my-dem-source", any()) }
    verify(exactly = 1) { mapboxMap.setStyleTerrain(any()) }
  }

  @Test
  public fun `style switch re-parses and does not reuse stale defaults`() {
    val firstStyleDefaults = StyleDefaults(
      rain = mapOf("density" to Value(0.1)),
      snow = emptyMap(),
      atmosphere = emptyMap(),
      terrain = emptyMap(),
    )
    val secondStyleDefaults = StyleDefaults(
      rain = mapOf("density" to Value(0.9)),
      snow = emptyMap(),
      atmosphere = emptyMap(),
      terrain = emptyMap(),
    )
    var callCount = 0
    val node = newNode(
      styleDefaults = {
        (if (callCount == 0) firstStyleDefaults else secondStyleDefaults).also { callCount++ }
      }
    )
    val rainSlot = mutableListOf<Value>()
    every { mapboxMap.setStyleRain(capture(rainSlot)) } returns expected
    node.onAttached(RootMapNode())

    styleDataLoadedFlow.tryEmit(styleLoadedEvent()) // initial load
    styleDataLoadedFlow.tryEmit(styleLoadedEvent()) // simulated runtime style switch

    assertEquals(2, rainSlot.size)
    assertEquals(mapOf("density" to Value(0.1)), rainSlot[0].contents)
    assertEquals(mapOf("density" to Value(0.9)), rainSlot[1].contents)
  }

  @Test
  public fun `parse failure falls back to empty defaults, preserving current behavior`() {
    every { mapboxMap.styleJSON } returns "{ not valid json"
    val node = newNode(
      styleDefaults = { StyleDefaults.fromJson(it) },
      rainState = RainState.DISABLED,
    )
    node.onAttached(RootMapNode())

    styleDataLoadedFlow.tryEmit(styleLoadedEvent())

    // empty (fallback) defaults + disabled rain -> removeRain(), i.e. setStyleRain(nullValue())
    verify { mapboxMap.setStyleRain(Value.nullValue()) }
  }
}