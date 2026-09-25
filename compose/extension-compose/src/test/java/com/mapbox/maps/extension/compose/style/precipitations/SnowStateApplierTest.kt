package com.mapbox.maps.extension.compose.style.precipitations

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
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

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
public class SnowStateApplierTest {

  private val mapboxMap = mockk<MapboxMap>(relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxed = true)
  private val valueSlot = slot<Value>()

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun testScope() = TestScope(UnconfinedTestDispatcher())

  private fun setUpSuccessfulSetStyleSnow() {
    every { mapboxMap.setStyleSnow(any()) } returns expected
    every { expected.error } returns null
  }

  private fun setUpFailingSetStyleSnow() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } returns Unit
    // Can't use a mocked Expected here — its .onError() won't run the callback.
    // ExpectedFactory.createError() creates a real Expected with actual error
    // handling, so the logE() inside .onError() actually fires and we can verify it.
    every { mapboxMap.setStyleSnow(any()) } returns ExpectedFactory.createError("error")
  }

  @Test
  public fun `disabled with non-empty defaults resets to style default, removeSnow not called`() {
    setUpSuccessfulSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = false
    )

    applier.attachTo(mapboxMap, mapOf("density" to Value(0.5)))

    verify { mapboxMap.setStyleSnow(capture(valueSlot)) }
    assertEquals(mapOf("density" to Value(0.5)), valueSlot.captured.contents)
    // removeSnow() boils down to setStyleSnow(Value.nullValue()); assert on the underlying
    // native call — verifying the un-mocked extension directly is unreliable.
    verify(exactly = 0) { mapboxMap.setStyleSnow(Value.nullValue()) }
  }

  @Test
  public fun `disabled with empty defaults calls removeSnow`() {
    setUpSuccessfulSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = false
    )

    applier.attachTo(mapboxMap, emptyMap())

    // removeSnow() is a MapboxStyleManager extension that calls setStyleSnow(Value.nullValue())
    verify { mapboxMap.setStyleSnow(Value.nullValue()) }
  }

  @Test
  public fun `disabled with no defaults argument preserves legacy behavior (removeSnow)`() {
    setUpSuccessfulSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = false
    )

    applier.attachTo(mapboxMap)

    verify { mapboxMap.setStyleSnow(Value.nullValue()) }
  }

  @Test
  public fun `enabled merges defaults with user overrides, user wins on overlap`() {
    setUpSuccessfulSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = mapOf("density" to Value(0.9)),
      coroutineScope = testScope(),
      enabled = true
    )

    applier.attachTo(mapboxMap, mapOf("density" to Value(0.2), "intensity" to Value(1.0)))

    verify { mapboxMap.setStyleSnow(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    // user override wins for the overlapping key
    assertEquals(Value(0.9), merged["density"])
    // default fills the sub-property the user never set
    assertEquals(Value(1.0), merged["intensity"])
  }

  @Test
  public fun `enabled fills unset sub-property from defaults`() {
    setUpSuccessfulSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = true
    )

    applier.attachTo(mapboxMap, mapOf("opacity" to Value(0.7)))

    verify { mapboxMap.setStyleSnow(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    assertEquals(Value(0.7), merged["opacity"])
  }

  @Test
  public fun `enabled with no defaults and no user props does not call setStyleSnow`() {
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = true
    )

    applier.attachTo(mapboxMap, emptyMap())

    verify(exactly = 0) { mapboxMap.setStyleSnow(any()) }
  }

  @Test
  public fun `disabled with defaults - native error is logged and does not throw`() {
    setUpFailingSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = false
    )

    // must not throw despite the native call reporting an error
    applier.attachTo(mapboxMap, mapOf("density" to Value(0.5)))

    verify { logE("SnowStateApplier", "Failed to reset snow to style default: error") }
  }

  @Test
  public fun `enabled merge - native error is logged and does not throw`() {
    setUpFailingSetStyleSnow()
    val applier = SnowStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
      enabled = true
    )

    // must not throw despite the native call reporting an error
    applier.attachTo(mapboxMap, mapOf("density" to Value(0.5)))

    verify { logE("SnowStateApplier", "Failed to add snow: error") }
  }
}