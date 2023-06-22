package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.animators.PuckAnimatorManager
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.util.captureVararg
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationPuckManagerTest {

  private val settings = mockk<LocationComponentSettings>(relaxed = true)
  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>(relaxed = true)
  private val style = mockk<Style>(relaxed = true)
  private val positionManager = mockk<LocationComponentPositionManager>(relaxed = true)
  private val locationLayerRenderer = mockk<LocationLayerRenderer>(relaxed = true)
  private val animationManager = mockk<PuckAnimatorManager>(relaxed = true)

  private val callbackSlot = CapturingSlot<(Style) -> Unit>()

  private lateinit var locationPuckManager: LocationPuckManager

  @Before
  fun setup() {
    mockkStatic(Value::class)
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
    every { settings.puckBearingEnabled } returns true
    locationPuckManager = LocationPuckManager(
      settings,
      mockk(),
      delegateProvider,
      positionManager,
      animationManager
    )
    locationPuckManager.locationLayerRenderer = locationLayerRenderer
  }

  @Test
  fun testInitialise() {
    locationPuckManager.initialize(style)
    verify { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify { animationManager.setUpdateListeners(any(), any(), any()) }
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
  fun testDoesntInitialiseIfRendererInitialised() {
    every { locationLayerRenderer.isRendererInitialised() } returns true

    locationPuckManager.initialize(style)

    verify(exactly = 0) { animationManager.setLocationLayerRenderer(locationLayerRenderer) }
    verify(exactly = 0) { animationManager.setUpdateListeners(any(), any(), any()) }
    verify(exactly = 0) { locationLayerRenderer.addLayers(positionManager) }
    verify(exactly = 0) { locationLayerRenderer.initializeComponents(style) }
    verify(exactly = 0) { locationLayerRenderer.hide() }
  }

  @Test
  fun testUpdateStyle() {
    val style = mockk<Style>()
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
    val settings = LocationComponentSettings(LocationPuck2D()) {
      puckBearingEnabled = false
    }
    val bearings = mutableListOf<Double>()
    every { animationManager.puckAnimationEnabled } returns true

    locationPuckManager.lastBearing = lastBearing
    locationPuckManager.updateSettings(settings)
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

  private companion object {
    const val MODEL_SCALE_CONSTANT = 2965820.800757861
  }
}