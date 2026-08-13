package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings2
import com.mapbox.maps.util.captureVararg
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.math.abs

@RunWith(RobolectricTestRunner::class)
class LocationPuckManagerTest {

  private val settings = mockk<LocationComponentSettings>(relaxed = true)
  private val accuracyRadiusSettings = mockk<LocationComponentSettings2>(relaxed = true)
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>(relaxed = true)
  private val style = mockk<StyleInterface>(relaxed = true)
  private val positionManager = mockk<LocationComponentPositionManager>(relaxed = true)
  private val layerSourceProvider = mockk<LayerSourceProvider>(relaxed = true)
  private val locationLayerRenderer = mockk<LocationLayerRenderer>(relaxed = true)
  private val animationManager = mockk<PuckAnimatorManager>(relaxed = true)

  private val callbackSlot = CapturingSlot<(StyleInterface) -> Unit>()
  private val valueSlot = CapturingSlot<Value>()

  private lateinit var locationPuckManager: LocationPuckManager

  @Before
  fun setup() {
    mockkStatic(Value::class)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every { mapCameraDelegate.cameraState.bearing } returns 0.0
    every { mapCameraDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every { settings.locationPuck } returns LocationPuck2D()
    every { settings.enabled } returns true
    every { accuracyRadiusSettings.puckBearingEnabled } returns true
    locationPuckManager = LocationPuckManager(
      settings,
      accuracyRadiusSettings,
      delegateProvider,
      positionManager,
      layerSourceProvider,
      animationManager
    )
    locationPuckManager.locationLayerRenderer = locationLayerRenderer
  }

  @After
  fun teardown() {
    unmockkStatic(Value::class)
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun testInitialise() {
    locationPuckManager.initialize(style)
    verify { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify { animationManager.setUpdateListeners(any(), any(), any()) }
    verify { animationManager.applyPulsingAnimationSettings(settings) }
    verify { locationLayerRenderer.addLayers(positionManager) }
    verify { locationLayerRenderer.initializeComponents(style) }
    verify { locationLayerRenderer.hide() }
  }

  @Test
  fun testInitialiseWithLocation() {
    locationPuckManager.lastLocation = Point.fromLngLat(0.0, 0.0)
    locationPuckManager.initialize(style)
    verify { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify { animationManager.setUpdateListeners(any(), any(), any()) }
    verify { animationManager.applyPulsingAnimationSettings(settings) }
    verify { locationLayerRenderer.addLayers(positionManager) }
    verify { locationLayerRenderer.initializeComponents(style) }
    verify { locationLayerRenderer.show() }
  }

  @Test
  fun testInitialiseSetsBearingAndLocationBeforeAddingLayers() {
    locationPuckManager.lastLocation = Point.fromLngLat(10.0, 20.0)
    locationPuckManager.initialize(style)

    val locations = mutableListOf<Point>()
    val bearings = mutableListOf<Double>()

    verifyOrder {
      animationManager.animatePosition(targets = captureVararg(locations), options = null)
      animationManager.animateBearing(
        targets = captureVararg(bearings).toDoubleArray(),
        options = null
      )
      locationLayerRenderer.initializeComponents(style)
      locationLayerRenderer.show()
    }

    assertArrayEquals(
      locations.toTypedArray(),
      arrayOf(locationPuckManager.lastLocation, locationPuckManager.lastLocation)
    )
    assertArrayEquals(
      bearings.toTypedArray(),
      arrayOf(0.0, 0.0)
    )
  }

  @Test
  fun testInitialiseWithDisabled() {
    every { settings.enabled } returns false
    locationPuckManager.initialize(style)
    verify { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify { locationLayerRenderer.addLayers(positionManager) }
    verify { locationLayerRenderer.initializeComponents(style) }
    verify { locationLayerRenderer.hide() }
  }

  @Test
  fun testCleanUp() {
    locationPuckManager.cleanUp()
    verify { locationLayerRenderer.hide() }
    assertTrue(locationPuckManager.isHidden)
    verify { locationLayerRenderer.hide() }
    verify { locationLayerRenderer.clearBitmaps() }
    verify { locationLayerRenderer.removeLayers() }
  }

  @Test
  fun testIsLayerInitialised() {
    every { locationLayerRenderer.isRendererInitialised() } returns true
    assertTrue(locationPuckManager.isLayerInitialised())
    verify { locationLayerRenderer.isRendererInitialised() }
  }

  @Test
  fun testUpdateSettings() {
    every { settings.layerAbove } returns "layer-above"
    every { settings.layerBelow } returns "layer-below"
    every { delegateProvider.getStyle(capture(callbackSlot)) } returns Unit

    locationPuckManager.updateSettings(settings)
    verify { positionManager.layerAbove = "layer-above" }
    verify { positionManager.layerBelow = "layer-below" }
    verify { locationLayerRenderer.clearBitmaps() }
    verify { locationLayerRenderer.removeLayers() }
    locationPuckManager.locationLayerRenderer = locationLayerRenderer
    callbackSlot.captured.invoke(style)
    verify { locationLayerRenderer.addLayers(positionManager) }
    verify { locationLayerRenderer.initializeComponents(style) }
    verify { locationLayerRenderer.hide() }

    locationPuckManager.lastLocation = Point.fromLngLat(0.0, 0.0)
    locationPuckManager.updateSettings(settings)
    locationPuckManager.locationLayerRenderer = locationLayerRenderer
    callbackSlot.captured.invoke(style)
    verify { locationLayerRenderer.show() }
  }

  @Test
  fun testOnStart() {
    locationPuckManager.onStart()
    verify { animationManager.onStart() }
  }

  @Test
  fun testOnStop() {
    locationPuckManager.onStop()
    verify { animationManager.onStop() }
  }

  @Test
  fun testUpdateCurrentPosition() {
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(0.0, 0.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
  }

  @Test
  fun testFirstLocationUpdateWhileEnabled() {
    every { settings.enabled } returns true
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(0.0, 0.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 1) { locationLayerRenderer.show() }
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(10.0, 10.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 1) { locationLayerRenderer.show() }
  }

  @Test
  fun testFirstLocationUpdateWhileDisabled() {
    every { settings.enabled } returns false
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(0.0, 0.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 0) { locationLayerRenderer.show() }
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(10.0, 10.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 0) { locationLayerRenderer.show() }
  }

  @Test
  fun testUpdateCurrentBearing() {
    locationPuckManager.updateCurrentBearing(0.1)
    verify { animationManager.animateBearing(targets = anyDoubleVararg(), options = null) }
  }

  @Test
  fun testUpdateCurrentBearingWithinThreshold() {
    locationPuckManager.updateCurrentBearing(0.005)
    verify(exactly = 0) {
      animationManager.animateBearing(
        targets = anyDoubleVararg(),
        options = null
      )
    }
  }

  @Test
  fun testUpdateCurrentBearingWithForceUpdate() {
    locationPuckManager.updateCurrentBearing(0.005, forceUpdate = true)
    verify { animationManager.animateBearing(targets = anyDoubleVararg(), options = null) }
  }

  @Test
  fun testUpdateLocationAnimator() {
    locationPuckManager.updateLocationAnimator { }
    verify { animationManager.updatePositionAnimator(any()) }
  }

  @Test
  fun testUpdateBearingAnimator() {
    locationPuckManager.updateBearingAnimator { }
    verify { animationManager.updateBearingAnimator(any()) }
  }

  @Test
  fun testShow() {
    locationPuckManager.isHidden = true
    locationPuckManager.show()
    verify { locationLayerRenderer.show() }
    assertFalse(locationPuckManager.isHidden)
  }

  @Test
  fun testShowWhileAlreadyShown() {
    locationPuckManager.isHidden = false
    locationPuckManager.show()
    verify(exactly = 0) { locationLayerRenderer.show() }
    assertFalse(locationPuckManager.isHidden)
  }

  @Test
  fun testShowForceUpdate() {
    locationPuckManager.isHidden = false
    locationPuckManager.show(forceUpdate = true)
    verify { locationLayerRenderer.show() }
    assertFalse(locationPuckManager.isHidden)
  }

  @Test
  fun testHide() {
    locationPuckManager.hide()
    verify { locationLayerRenderer.hide() }
    assertTrue(locationPuckManager.isHidden)
  }

  @Test
  fun test2DStyleScaling() {
    every { settings.locationPuck } returns LocationPuck2D(
      scaleExpression = "expression"
    )
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("expression"))
    locationPuckManager.styleScaling(settings)
    verify { locationLayerRenderer.styleScaling(Value("expression")) }
  }

  @Test
  fun testDefault2DStyleScaling() {
    every { settings.locationPuck } returns LocationPuck2D()
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("expression"))
    locationPuckManager.styleScaling(settings)
    verify(exactly = 0) { locationLayerRenderer.styleScaling(any()) }
  }

  @Test
  fun test3DStyleScaling() {
    every { settings.locationPuck } returns LocationPuck3D(
      modelUri = "uri",
      modelScaleExpression = "expression"
    )
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("expression"))
    locationPuckManager.styleScaling(settings)
    verify { locationLayerRenderer.styleScaling(Value("expression")) }
  }

  @Test
  fun testDefault3DStyleScaling() {
    every { settings.locationPuck } returns LocationPuck3D(
      modelUri = "uri",
    )
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("expression"))
    locationPuckManager.styleScaling(settings)
    verify { locationLayerRenderer.styleScaling(capture(valueSlot)) }
    val value = valueSlot.captured.toString()
    assertTrue(value.startsWith("[interpolate, [exponential, 0.5], [zoom], 0.5, [literal, ["))
    assertTrue(value.endsWith("]], 22.0, [literal, [1.0, 1.0, 1.0]]]"))
    value
      .replace("[interpolate, [exponential, 0.5], [zoom], 0.5, [literal, [", "")
      .replace("]], 22.0, [literal, [1.0, 1.0, 1.0]]]", "")
      .split(", ")
      .map { it.toDouble() }
      .forEach {
        val absoluteDifference: Float = abs(it - MODEL_SCALE_CONSTANT).toFloat()
        val maxUlp = Math.ulp(it).coerceAtLeast(Math.ulp(MODEL_SCALE_CONSTANT)).toFloat()
        assert(absoluteDifference < 2 * maxUlp)
      }
  }

  @Test
  fun testDefault3DStyleScalingWithMercatorScale() {
    // in mercator projection we take actual location update as camera center, so
    // lat = 60.0, cos(60.0) = 0.5
    testDefault3DScaling(
      actualProjection = "mercator",
      actualLocationUpdate = Point.fromLngLat(60.0, 60.0),
      expectedLastMercatorScale = 0.5,
      expectedLastMercatorScaleString = "0.5000000000000001"
    )
  }

  @Test
  fun testDefault3DStyleScalingWithGlobeScale() {
    // in globe projection we take mapCameraDelegate.cameraState as camera center, so
    // lat = 0.0, cos(0.0) = 1.0
    testDefault3DScaling(
      actualProjection = "globe",
      actualLocationUpdate = Point.fromLngLat(60.0, 60.0),
      expectedLastMercatorScale = 1.0,
      expectedLastMercatorScaleString = "1.0"
    )
  }

  private fun testDefault3DScaling(
    actualProjection: String,
    actualLocationUpdate: Point,
    expectedLastMercatorScale: Double,
    expectedLastMercatorScaleString: String
  ) {
    val onLocationUpdatedSlot = slot<((Point) -> Unit)>()
    val getStyleSlot = slot<((StyleInterface) -> Unit)>()
    val style = mockk<StyleInterface>()
    every { style.getStyleProjectionProperty("name") } returns
      StylePropertyValue(Value(actualProjection), StylePropertyValueKind.CONSTANT)
    val valueSlots = mutableListOf<Value>()
    every { settings.locationPuck } returns LocationPuck3D(
      modelUri = "uri",
    )
    every { Value.fromJson(any()) } returns ExpectedFactory.createValue(Value("expression"))
    locationPuckManager.initialize(mockk())
    verify { animationManager.setUpdateListeners(capture(onLocationUpdatedSlot), any(), any()) }
    onLocationUpdatedSlot.captured.invoke(actualLocationUpdate)
    verify { delegateProvider.getStyle(capture(getStyleSlot)) }
    getStyleSlot.captured.invoke(style)
    assertEquals(expectedLastMercatorScale, locationPuckManager.lastMercatorScale, 1E-5)
    verify { locationLayerRenderer.styleScaling(capture(valueSlots)) }
    val value = valueSlots.last().toString()
    assertTrue(value.startsWith("[interpolate, [exponential, 0.5], [zoom], 0.5, [literal, ["))
    assertTrue(value.endsWith("]], 22.0, [literal, [$expectedLastMercatorScaleString, $expectedLastMercatorScaleString, $expectedLastMercatorScaleString]]]"))
    value
      .replace("[interpolate, [exponential, 0.5], [zoom], 0.5, [literal, [", "")
      .replace(
        "]], 22.0, [literal, [$expectedLastMercatorScaleString, $expectedLastMercatorScaleString, $expectedLastMercatorScaleString]]]",
        ""
      )
      .split(", ")
      .map { it.toDouble() }
      .forEach {
        val absoluteDifference: Float =
          abs(it - MODEL_SCALE_CONSTANT * expectedLastMercatorScale).toFloat()
        val maxUlp =
          Math.ulp(it).coerceAtLeast(Math.ulp(MODEL_SCALE_CONSTANT * expectedLastMercatorScale))
            .toFloat()
        assert(absoluteDifference < 2 * maxUlp)
      }
  }

  @Test
  fun testDoesntInitialiseIfRendererInitialised() {
    every { locationLayerRenderer.isRendererInitialised() } returns true

    locationPuckManager.initialize(style)

    verify(exactly = 0) { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify(exactly = 0) { animationManager.setUpdateListeners(any(), any(), any()) }
    verify(exactly = 0) { animationManager.applyPulsingAnimationSettings(settings) }
    verify(exactly = 0) { locationLayerRenderer.addLayers(positionManager) }
    verify(exactly = 0) { locationLayerRenderer.initializeComponents(style) }
    verify(exactly = 0) { locationLayerRenderer.hide() }
  }

  @Test
  fun testUpdateStyle() {
    val style = mockk<StyleInterface>()
    locationPuckManager.updateStyle(style)
    verify {
      locationLayerRenderer.updateStyle(style)
      positionManager.updateStyle(style)
    }
  }

  @Test
  fun testMaxPulsingRadiusToFollowAccuracyRing() {
    every { settings.pulsingMaxRadius } returns -1f
    locationPuckManager.updateMaxPulsingRadiusToFollowAccuracyRing(10.0)
    verify { animationManager.updatePulsingRadius(any(), settings) }
  }

  @Test
  fun testDefaultMaxPulsingRadiusBehaviour() {
    every { settings.pulsingMaxRadius } returns 10.0f
    locationPuckManager.updateMaxPulsingRadiusToFollowAccuracyRing(10.0)
    verify(exactly = 0) { animationManager.updatePulsingRadius(any(), settings) }
  }

  @Test
  fun testDisablePuckBearingSnapsToNorth() {
    val lastBearing = 180.0
    val newBearing = 90.0
    val settings2 = LocationComponentSettings2().apply { puckBearingEnabled = false }
    val bearings = mutableListOf<Double>()
    every { animationManager.puckAnimationEnabled } returns true

    locationPuckManager.lastBearing = lastBearing
    locationPuckManager.updateSettings2(settings2)
    locationPuckManager.updateCurrentBearing(newBearing)

    verify {
      animationManager.animateBearing(
        targets = captureVararg(bearings).toDoubleArray(),
        options = any()
      )
    }

    assertArrayEquals(
      bearings.toTypedArray(),
      arrayOf(180.0, 0.0)
    )
  }

  @Test
  fun testAnimateToBearingAllNonFiniteIgnored() {
    locationPuckManager.animateToBearing(
      doubleArrayOf(Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY),
      forceUpdate = true
    )
    verify(exactly = 0) {
      animationManager.animateBearing(targets = anyDoubleVararg(), options = null)
    }
  }

  @Test
  fun testAnimateToBearingMixedKeepsFiniteValues() {
    val bearings = mutableListOf<Double>()
    locationPuckManager.animateToBearing(doubleArrayOf(Double.NaN, 90.0), forceUpdate = true)
    verify {
      animationManager.animateBearing(targets = captureVararg(bearings).toDoubleArray(), options = null)
    }
    assertArrayEquals(arrayOf(0.0, 90.0), bearings.toTypedArray())
  }

  @Test
  fun testOnBearingUpdatedListenerIgnoresNonFiniteBearing() {
    val onBearingUpdatedSlot = slot<(Double) -> Unit>()
    locationPuckManager.initialize(style)
    verify { animationManager.setUpdateListeners(any(), capture(onBearingUpdatedSlot), any()) }
    locationPuckManager.lastBearing = 45.0
    onBearingUpdatedSlot.captured.invoke(Double.NaN)
    assertEquals(45.0, locationPuckManager.lastBearing, 0.0)
  }

  @Test
  fun testLastBearingDefaultsToZeroWhenCameraBearingNonFinite() {
    every { mapCameraDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      Double.NaN,
      0.0
    )
    val manager = LocationPuckManager(
      settings,
      accuracyRadiusSettings,
      delegateProvider,
      positionManager,
      layerSourceProvider,
      animationManager
    )
    assertEquals(0.0, manager.lastBearing, 0.0)
  }

  @Test
  fun testReinitializeAfterNonFiniteBearingUpdateBakesOnlyFiniteBearing() {
    locationPuckManager.initialize(style)
    val onBearingUpdatedSlot = slot<(Double) -> Unit>()
    verify { animationManager.setUpdateListeners(any(), capture(onBearingUpdatedSlot), any()) }
    onBearingUpdatedSlot.captured.invoke(45.0)
    onBearingUpdatedSlot.captured.invoke(Double.NaN)
    assertEquals(45.0, locationPuckManager.lastBearing, 0.0)

    every { locationLayerRenderer.isRendererInitialised() } returns false
    val bearings = mutableListOf<Double>()
    locationPuckManager.initialize(style)
    verify {
      animationManager.animateBearing(targets = captureVararg(bearings).toDoubleArray(), options = null)
    }
    assertTrue(bearings.all { it.isFinite() })
  }

  @Test
  fun testUpdateCurrentPositionAllNonFiniteIgnored() {
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(Double.NaN, Double.NaN))
    verify(exactly = 0) { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 0) { locationLayerRenderer.show() }
  }

  @Test
  fun testUpdateCurrentPositionMixedKeepsFiniteValues() {
    val locations = mutableListOf<Point>()
    locationPuckManager.updateCurrentPosition(
      Point.fromLngLat(Double.NaN, Double.NaN),
      Point.fromLngLat(1.0, 2.0)
    )
    verify { animationManager.animatePosition(targets = captureVararg(locations), options = null) }
    assertArrayEquals(arrayOf(Point.fromLngLat(1.0, 2.0), Point.fromLngLat(1.0, 2.0)), locations.toTypedArray())
  }

  @Test
  fun testUpdateCurrentPositionDegradesToTwoDWithNonFiniteAltitude() {
    val locations = mutableListOf<Point>()
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(1.0, 2.0, Double.NaN))
    verify { animationManager.animatePosition(targets = captureVararg(locations), options = null) }
    assertArrayEquals(arrayOf(Point.fromLngLat(1.0, 2.0), Point.fromLngLat(1.0, 2.0)), locations.toTypedArray())
  }

  @Test
  fun testUpdateCurrentPositionFullyFiniteReachesShow() {
    every { settings.enabled } returns true
    locationPuckManager.updateCurrentPosition(Point.fromLngLat(1.0, 2.0, 3.0))
    verify { animationManager.animatePosition(targets = anyVararg(), options = null) }
    verify(exactly = 1) { locationLayerRenderer.show() }
  }

  @Test
  fun testUpdateAccuracyRadiusAllNonFiniteIgnored() {
    locationPuckManager.updateAccuracyRadius(Double.NaN, Double.POSITIVE_INFINITY)
    verify(exactly = 0) {
      animationManager.animateAccuracyRadius(targets = anyDoubleVararg(), options = null)
    }
  }

  @Test
  fun testUpdateAccuracyRadiusMixedKeepsFiniteValues() {
    val radii = mutableListOf<Double>()
    locationPuckManager.updateAccuracyRadius(Double.NaN, 5.0)
    verify {
      animationManager.animateAccuracyRadius(targets = captureVararg(radii).toDoubleArray(), options = null)
    }
    assertArrayEquals(radii.toTypedArray(), arrayOf(0.0, 5.0))
  }

  @Test
  fun testOnAccuracyRadiusUpdatedListenerIgnoresNonFiniteRadius() {
    val onAccuracyRadiusUpdatedSlot = slot<(Double) -> Unit>()
    locationPuckManager.initialize(style)
    verify { animationManager.setUpdateListeners(any(), any(), capture(onAccuracyRadiusUpdatedSlot)) }
    onAccuracyRadiusUpdatedSlot.captured.invoke(10.0)
    onAccuracyRadiusUpdatedSlot.captured.invoke(Double.NaN)
    val radii = mutableListOf<Double>()
    locationPuckManager.updateAccuracyRadius(20.0)
    verify {
      animationManager.animateAccuracyRadius(targets = captureVararg(radii).toDoubleArray(), options = null)
    }
    assertArrayEquals(radii.toTypedArray(), arrayOf(10.0, 20.0))
  }

  @Test
  fun testOnLocationUpdatedListenerIgnoresNonFiniteLocation() {
    val onLocationUpdatedSlot = slot<(Point) -> Unit>()
    locationPuckManager.initialize(style)
    verify { animationManager.setUpdateListeners(capture(onLocationUpdatedSlot), any(), any()) }
    onLocationUpdatedSlot.captured.invoke(Point.fromLngLat(10.0, 20.0))
    onLocationUpdatedSlot.captured.invoke(Point.fromLngLat(Double.NaN, Double.NaN))
    assertEquals(Point.fromLngLat(10.0, 20.0), locationPuckManager.lastLocation)
  }

  private companion object {
    const val MODEL_SCALE_CONSTANT = 2965820.800757861
  }
}