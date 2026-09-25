package com.mapbox.maps.extension.compose.style.atmosphere

import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.bindgen.Value
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

@RunWith(RobolectricTestRunner::class)
public class AtmosphereStateApplierTest {

  private val mapboxMap = mockk<MapboxMap>(relaxed = true)
  private val expected = mockk<Expected<String, None>>(relaxed = true)
  private val valueSlot = slot<Value>()

  @After
  public fun tearDown() {
    unmockkAll()
  }

  private fun testScope() = TestScope(UnconfinedTestDispatcher())

  private fun setUpSuccessfulSetStyleAtmosphere() {
    every { mapboxMap.setStyleAtmosphere(any()) } returns expected
    every { expected.error } returns null
  }

  private fun setUpFailingSetStyleAtmosphere() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } returns Unit
    // Can't use a mocked Expected here — its .onError() won't run the callback.
    // ExpectedFactory.createError() creates a real Expected with actual error
    // handling, so the logE() inside .onError() actually fires and we can verify it.
    every { mapboxMap.setStyleAtmosphere(any()) } returns ExpectedFactory.createError("error")
  }

  @Test
  public fun `merges defaults with user overrides, user wins on overlap`() {
    setUpSuccessfulSetStyleAtmosphere()
    val applier = AtmosphereStateApplier(
      initialProperties = mapOf("color" to Value("red")),
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("color" to Value("blue"), "range" to Value(listOf(Value(0.0), Value(1.0)))))

    verify { mapboxMap.setStyleAtmosphere(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    // user override wins for the overlapping key
    assertEquals(Value("red"), merged["color"])
    // default fills the sub-property the user never set
    assertEquals(Value(listOf(Value(0.0), Value(1.0))), merged["range"])
  }

  @Test
  public fun `fills unset sub-property from defaults`() {
    setUpSuccessfulSetStyleAtmosphere()
    val applier = AtmosphereStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, mapOf("horizon-blend" to Value(0.3)))

    verify { mapboxMap.setStyleAtmosphere(capture(valueSlot)) }
    val merged = valueSlot.captured.contents as Map<*, *>
    assertEquals(Value(0.3), merged["horizon-blend"])
  }

  @Test
  public fun `empty defaults and no user props does not call setStyleAtmosphere`() {
    val applier = AtmosphereStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap, emptyMap())

    verify(exactly = 0) { mapboxMap.setStyleAtmosphere(any()) }
  }

  @Test
  public fun `no defaults argument preserves legacy behavior (only user props)`() {
    setUpSuccessfulSetStyleAtmosphere()
    val applier = AtmosphereStateApplier(
      initialProperties = mapOf("color" to Value("red")),
      coroutineScope = testScope(),
    )

    applier.attachTo(mapboxMap)

    verify { mapboxMap.setStyleAtmosphere(capture(valueSlot)) }
    assertEquals(mapOf("color" to Value("red")), valueSlot.captured.contents)
  }

  @Test
  public fun `merge - native error is logged and does not throw`() {
    setUpFailingSetStyleAtmosphere()
    val applier = AtmosphereStateApplier(
      initialProperties = emptyMap(),
      coroutineScope = testScope(),
    )

    // must not throw despite the native call reporting an error
    applier.attachTo(mapboxMap, mapOf("color" to Value("red")))

    verify { logE("AtmosphereStateApplier", "Failed to add atmosphere: error") }
  }
}