package com.mapbox.maps.extension.compose.style.terrain

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.sources.generated.RasterDemSourceState
import com.mapbox.maps.logE
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class TerrainStateApplierTest {

  private val mapboxMap = mockk<MapboxMap>(relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxed = true)
  private val valueSlot = slot<Value>()

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun testScope() = TestScope(UnconfinedTestDispatcher())

  private fun setUpSuccessfulSetStyleTerrain() {
    every { mapboxMap.setStyleTerrain(any()) } returns expected
    every { expected.error } returns null
  }

  private fun setUpFailingSetStyleTerrain() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } returns Unit
    // Can't use a mocked Expected here — its .onError() won't run the callback.
    // ExpectedFactory.createError() creates a real Expected with actual error
    // handling, so the logE() inside .onError() actually fires and we can verify it.
    every { mapboxMap.setStyleTerrain(any()) } returns ExpectedFactory.createError("error")
  }

  @Test
  public fun `null dem source with non-empty defaults resets to style default, not nullValue`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = null,
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(1.5)))

    verify { mapboxMap.setStyleTerrain(capture(valueSlot)) }
    assertEquals(mapOf("exaggeration" to Value(1.5)), valueSlot.captured.contents)
    verify(exactly = 0) { mapboxMap.setStyleTerrain(Value.nullValue()) }
  }

  @Test
  public fun `null dem source with empty defaults calls setStyleTerrain with nullValue`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = null,
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, emptyMap())

    verify { mapboxMap.setStyleTerrain(Value.nullValue()) }
  }

  @Test
  public fun `null dem source with no defaults argument preserves legacy behavior (nullValue)`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = null,
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap)

    verify { mapboxMap.setStyleTerrain(Value.nullValue()) }
  }

  @Test
  public fun `dem source present merges defaults, source key survives the merge`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = RasterDemSourceState(sourceId = "my-dem-source"),
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(2.0), "source" to Value("bogus-default-source")))

    verify { mapboxMap.setStyleTerrain(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    // source key must survive the defaults merge - it is never clobbered by a defaults value
    assertEquals(Value("my-dem-source"), merged["source"])
    // default sub-property is present because the user never set it
    assertEquals(Value(2.0), merged["exaggeration"])
  }

  @Test
  public fun `dem source present, user override wins over default on overlap`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = RasterDemSourceState(sourceId = "my-dem-source"),
      initialProperties = mapOf("exaggeration" to Value(3.0)),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(1.0)))

    verify { mapboxMap.setStyleTerrain(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    assertEquals(Value(3.0), merged["exaggeration"])
    assertEquals(Value("my-dem-source"), merged["source"])
  }

  @Test
  public fun `initial true is a no-op regardless of defaults`() {
    val applier = TerrainStateApplier(
      rasterDemSourceState = RasterDemSourceState(sourceId = "my-dem-source"),
      initialProperties = emptyMap(),
      initial = true,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(1.0)))

    verify(exactly = 0) { mapboxMap.setStyleTerrain(any()) }
  }

  @Test
  public fun `dem source present, no defaults argument preserves legacy behavior (source key set, no defaults merged)`() {
    setUpSuccessfulSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = RasterDemSourceState(sourceId = "my-dem-source"),
      initialProperties = mapOf("exaggeration" to Value(2.5)),
      initial = false,
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap)

    verify { mapboxMap.setStyleTerrain(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    assertEquals(Value("my-dem-source"), merged["source"])
    assertEquals(Value(2.5), merged["exaggeration"])
  }

  @Test
  public fun `null dem source with defaults - native error is logged and does not throw`() {
    setUpFailingSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = null,
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    // must not throw despite the native call reporting an error
    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(1.5)))

    verify { logE("TerrainStateApplier", "Failed to reset terrain to style default: error") }
  }

  @Test
  public fun `dem source present - native error is logged and does not throw`() {
    setUpFailingSetStyleTerrain()
    val applier = TerrainStateApplier(
      rasterDemSourceState = RasterDemSourceState(sourceId = "my-dem-source"),
      initialProperties = emptyMap(),
      initial = false,
      coroutineScope = testScope(),
    )

    // must not throw despite the native call reporting an error
    applier.attachTo(mapboxMap, mapOf("exaggeration" to Value(1.5)))

    verify { logE("TerrainStateApplier", "Failed to add terrain: error") }
  }
}