package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.SensorManager
import android.util.AttributeSet
import android.view.WindowManager
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentAttributeParser
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import io.mockk.*
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
  shadows = [
    ShadowLocationServiceFactory::class
  ]
)
class LocationComponentPluginImplTest {

  private val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
  private val locationPuckManager = mockk<LocationPuckManager>(relaxed = true)
  private val locationProvider = mockk<LocationProvider>(relaxed = true)

  private val style = mockk<MapboxStyleManager>(relaxed = true)

  private val context = mockk<Context>(relaxed = true)
  private val attrs = mockk<AttributeSet>(relaxUnitFun = true)
  private val typedArray = mockk<TypedArray>(relaxed = true)
  private val pack = "com.mapbox.maps"
  private val drawable = mockk<Drawable>(relaxed = true)
  private val windowManager = mockk<WindowManager>(relaxed = true)
  private val sensorManager = mockk<SensorManager>(relaxed = true)
  private val deviceLocationProvider = mockk<DeviceLocationProvider>(relaxed = true)

  private val styleCallbackSlot = slot<(MapboxStyleManager) -> Unit>()

  private lateinit var locationComponentPlugin: LocationComponentPluginImpl

  @Before
  fun setup() {
    mockkObject(LocationComponentAttributeParser)
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs

    ShadowLocationServiceFactory.deviceLocationProvider = deviceLocationProvider

    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { context.applicationContext.applicationContext.applicationContext.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    every { context.applicationContext.applicationContext.applicationContext.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getInt(any(), any()) } returns 0
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getColor(any(), any()) } returns Color.BLUE
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getDrawable(any()) } returns drawable
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.hasValue(any()) } returns true
    every { typedArray.recycle() } returns Unit

    locationComponentPlugin = LocationComponentPluginImpl()
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  // Utility function to setup initialise the plugin.
  private fun preparePluginInitialisationWithEnabled() {
    every {
      LocationComponentAttributeParser.parseLocationComponentSettings(
        context,
        attrs,
        1f
      )
    } returns LocationComponentSettings(LocationPuck2D()) {
      enabled = true
    }

    every { delegateProvider.getStyle(capture(styleCallbackSlot)) } returns Unit
    locationComponentPlugin.bind(context, attrs, 1f, locationProvider, locationPuckManager)
    locationComponentPlugin.onDelegateProvider(delegateProvider)
    locationComponentPlugin.onStart()
    if (styleCallbackSlot.isCaptured) {
      styleCallbackSlot.captured.invoke(style)
    }
  }

  // Utility function to setup initialise the plugin.
  private fun preparePluginInitialisationWithDisabled() {
    every {
      LocationComponentAttributeParser.parseLocationComponentSettings(
        context,
        attrs,
        1f
      )
    } returns LocationComponentSettings(LocationPuck2D()) {
      enabled = false
    }

    every { delegateProvider.getStyle(capture(styleCallbackSlot)) } returns Unit
    locationComponentPlugin.bind(context, attrs, 1f, locationProvider, locationPuckManager)
    locationComponentPlugin.onDelegateProvider(delegateProvider)
    locationComponentPlugin.onStart()
    if (styleCallbackSlot.isCaptured) {
      styleCallbackSlot.captured.invoke(style)
    }
  }

  @Test
  fun testBindWithPluginDisabled() {
    every {
      LocationComponentAttributeParser.parseLocationComponentSettings(
        context,
        attrs,
        1f
      )
    } returns LocationComponentSettings(LocationPuck2D()) {
      enabled = false
    }
    locationComponentPlugin.bind(context, attrs, 1f)
    assertNull(locationComponentPlugin.getLocationProvider())
  }

  @Test
  fun testBindWithPluginEnabled() {
    every {
      LocationComponentAttributeParser.parseLocationComponentSettings(
        context,
        attrs,
        1f
      )
    } returns LocationComponentSettings(LocationPuck2D()) {
      enabled = true
    }
    locationComponentPlugin.bind(context, attrs, 1f, locationProvider, locationPuckManager)
    assertNotNull(locationComponentPlugin.getLocationProvider())
  }

  @Test
  fun testAddOnIndicatorPositionChangedListener() {
    val positionListener = mockk<OnIndicatorPositionChangedListener>(relaxed = true)
    locationComponentPlugin.indicatorPositionChangedListener.onIndicatorPositionChanged(
      Point.fromLngLat(
        0.0,
        0.0
      )
    )
    locationComponentPlugin.addOnIndicatorPositionChangedListener(positionListener)
    // Verify the newly added listener will get the last updated indicator position
    verify(exactly = 1) { positionListener.onIndicatorPositionChanged(Point.fromLngLat(0.0, 0.0)) }
    locationComponentPlugin.indicatorPositionChangedListener.onIndicatorPositionChanged(
      Point.fromLngLat(
        1.0,
        0.0
      )
    )
    // Verify the listener will get notified when new location arrives.
    verify(exactly = 1) { positionListener.onIndicatorPositionChanged(Point.fromLngLat(1.0, 0.0)) }
  }

  @Test
  fun testRemoveOnIndicatorPositionChangedListener() {
    val positionListener = mockk<OnIndicatorPositionChangedListener>(relaxed = true)
    locationComponentPlugin.addOnIndicatorPositionChangedListener(positionListener)
    locationComponentPlugin.removeOnIndicatorPositionChangedListener(positionListener)
    locationComponentPlugin.indicatorPositionChangedListener.onIndicatorPositionChanged(
      Point.fromLngLat(
        0.0,
        0.0
      )
    )
    verify(exactly = 0) { positionListener.onIndicatorPositionChanged(Point.fromLngLat(0.0, 0.0)) }
  }

  @Test
  fun testAddOnIndicatorBearingChangedListener() {
    val bearingListener = mockk<OnIndicatorBearingChangedListener>(relaxed = true)
    locationComponentPlugin.indicatorBearingChangedListener.onIndicatorBearingChanged(0.0)
    locationComponentPlugin.addOnIndicatorBearingChangedListener(bearingListener)
    // Verify the newly added listener will get the last updated indicator bearing
    verify(exactly = 1) { bearingListener.onIndicatorBearingChanged(0.0) }
    // Verify the listener will get notified when new bearing arrives.
    locationComponentPlugin.indicatorBearingChangedListener.onIndicatorBearingChanged(1.0)
    verify(exactly = 1) { bearingListener.onIndicatorBearingChanged(1.0) }
  }

  @Test
  fun testRemoveOnIndicatorBearingChangedListener() {
    val bearingListener = mockk<OnIndicatorBearingChangedListener>(relaxed = true)
    locationComponentPlugin.addOnIndicatorBearingChangedListener(bearingListener)
    locationComponentPlugin.removeOnIndicatorBearingChangedListener(bearingListener)
    locationComponentPlugin.indicatorBearingChangedListener.onIndicatorBearingChanged(0.0)
    verify(exactly = 0) { bearingListener.onIndicatorBearingChanged(0.0) }
  }

  @Test
  fun testAddOnIndicatorAccuracyRadiusChangedListener() {
    val accuracyListener = mockk<OnIndicatorAccuracyRadiusChangedListener>(relaxed = true)
    locationComponentPlugin.indicatorAccuracyRadiusChangedListener.onIndicatorAccuracyRadiusChanged(0.0)
    locationComponentPlugin.addOnIndicatorAccuracyRadiusChangedListener(accuracyListener)
    // Verify the newly added listener will get the last updated indicator accuracy radius
    verify(exactly = 1) { accuracyListener.onIndicatorAccuracyRadiusChanged(0.0) }
    // Verify the listener will get notified when new accuracy radius arrives.
    locationComponentPlugin.indicatorAccuracyRadiusChangedListener.onIndicatorAccuracyRadiusChanged(1.0)
    verify(exactly = 1) { accuracyListener.onIndicatorAccuracyRadiusChanged(1.0) }
  }

  @Test
  fun testRemoveOnIndicatorAccuracyRadiusChangedListener() {
    val accuracyRadiusListener = mockk<OnIndicatorAccuracyRadiusChangedListener>(relaxed = true)
    locationComponentPlugin.addOnIndicatorAccuracyRadiusChangedListener(accuracyRadiusListener)
    locationComponentPlugin.removeOnIndicatorAccuracyRadiusChangedListener(accuracyRadiusListener)
    locationComponentPlugin.indicatorAccuracyRadiusChangedListener.onIndicatorAccuracyRadiusChanged(.0)
    verify(exactly = 0) { accuracyRadiusListener.onIndicatorAccuracyRadiusChanged(0.0) }
  }

  @Test
  fun testSetLocationProvider() {
    val mockLocationProvider = mockk<LocationProvider>(relaxed = true)
    preparePluginInitialisationWithEnabled()
    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    locationComponentPlugin.setLocationProvider(mockLocationProvider)
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
    verify(exactly = 1) { mockLocationProvider.registerLocationConsumer(any()) }
    assertEquals(mockLocationProvider, locationComponentPlugin.getLocationProvider())
  }

  @Test
  fun testSetLocationProviderDeactivated() {
    val mockLocationProvider = mockk<LocationProvider>(relaxed = true)
    locationComponentPlugin.locationProvider = locationProvider
    locationComponentPlugin.setLocationProvider(mockLocationProvider)
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
    verify(exactly = 0) { mockLocationProvider.registerLocationConsumer(any()) }
    assertEquals(mockLocationProvider, locationComponentPlugin.getLocationProvider())
  }

  @Test
  fun testOnStart() = runTest {
    every { locationPuckManager.isLayerInitialised() } returns false
    preparePluginInitialisationWithEnabled()
    advanceUntilIdle()
    verify(exactly = 1) { locationPuckManager.initialize(style) }
    verify(exactly = 1) { locationPuckManager.onStart() }
    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    assertTrue(locationComponentPlugin.isLocationComponentActivated)
  }

  @Test
  fun testOnStartWithPuckDisabled() {
    every { locationPuckManager.isLayerInitialised() } returns false
    preparePluginInitialisationWithDisabled()
    verify(exactly = 0) { delegateProvider.getStyle(any()) }
    verify(exactly = 0) { locationPuckManager.initialize(style) }
    verify(exactly = 0) { locationPuckManager.onStart() }
    verify(exactly = 0) { locationProvider.registerLocationConsumer(any()) }
    assertFalse(locationComponentPlugin.isLocationComponentActivated)
  }

  @Test
  fun testOnStartWithPuckAlreadyInitialisedAndPluginActivated() {
    every { locationPuckManager.isLayerInitialised() } returns true
    locationComponentPlugin.isLocationComponentActivated = true
    preparePluginInitialisationWithEnabled()
    verify(exactly = 0) { locationPuckManager.initialize(style) }
    verify(exactly = 0) { locationPuckManager.onStart() }
    verify(exactly = 0) { locationProvider.registerLocationConsumer(any()) }
    assertTrue(locationComponentPlugin.isLocationComponentActivated)
  }

  @Test
  fun testOnStop() {
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onStop()
    assertFalse(locationComponentPlugin.isLocationComponentActivated)
    verify { locationPuckManager.onStop() }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
  }

  @Test
  fun testOnLocationUpdated() {
    val valueAnimator = mockk<ValueAnimator.() -> Unit>(relaxed = true)
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onLocationUpdated(Point.fromLngLat(0.0, 0.0), options = valueAnimator)
    verify { locationPuckManager.updateCurrentPosition(Point.fromLngLat(0.0, 0.0), options = valueAnimator) }
  }

  @Test
  fun testOnBearingUpdated() {
    val valueAnimator = mockk<ValueAnimator.() -> Unit>(relaxed = true)
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onBearingUpdated(0.0, options = valueAnimator)
    verify { locationPuckManager.updateCurrentBearing(0.0, options = valueAnimator) }
  }

  @Test
  fun testOnPuckLocationAnimatorDefaultOptionsUpdated() {
    val valueAnimator = mockk<ValueAnimator.() -> Unit>(relaxed = true)
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onPuckLocationAnimatorDefaultOptionsUpdated(valueAnimator)
    verify { locationPuckManager.updateLocationAnimator(valueAnimator) }
  }

  @Test
  fun testOnPuckBearingAnimatorDefaultOptionsUpdated() {
    val valueAnimator = mockk<ValueAnimator.() -> Unit>(relaxed = true)
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onPuckBearingAnimatorDefaultOptionsUpdated(valueAnimator)
    verify { locationPuckManager.updateBearingAnimator(valueAnimator) }
  }

  @Test
  fun testApplySettingsEnabled() {
    preparePluginInitialisationWithDisabled()
    every { locationPuckManager.isLayerInitialised() } returns false
    locationComponentPlugin.enabled = true
    verify { locationPuckManager.updateSettings(any()) }
  }

  @Test
  fun testApplySettingsDisabled() {
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.enabled = false
    verify(exactly = 0) { locationPuckManager.updateSettings(any()) }
    verify { locationPuckManager.cleanUp() }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
    assertFalse(locationComponentPlugin.isLocationComponentActivated)
    assertNull(locationComponentPlugin.locationPuckManager)
  }

  @Test
  fun testLocationProviderRegisterStopStart() {
    preparePluginInitialisationWithEnabled()

    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 0) { locationProvider.unRegisterLocationConsumer(any()) }
    locationComponentPlugin.onStop()
    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }

    every { delegateProvider.getStyle(capture(styleCallbackSlot)) } returns Unit
    locationComponentPlugin.onStart()
    if (styleCallbackSlot.isCaptured) {
      styleCallbackSlot.captured.invoke(style)
    }
    verify(exactly = 2) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
  }

  @Test
  fun testLocationProviderRegisterDisableEnable() {
    every { logE(any(), any()) } just Runs
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.setStyleLayerProperty(any(), any(), any()) } returns ExpectedFactory.createNone()

    preparePluginInitialisationWithEnabled()

    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 0) { locationProvider.unRegisterLocationConsumer(any()) }
    locationComponentPlugin.enabled = false
    verify(exactly = 1) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }

    every { delegateProvider.getStyle(capture(styleCallbackSlot)) } returns Unit
    locationComponentPlugin.enabled = true
    if (styleCallbackSlot.isCaptured) {
      styleCallbackSlot.captured.invoke(style)
    }
    verify(exactly = 1) { style.addPersistentStyleLayer(any(), any()) }
    verify(exactly = 2) { locationProvider.registerLocationConsumer(any()) }
    verify(exactly = 1) { locationProvider.unRegisterLocationConsumer(any()) }
  }

  @Test
  fun testOnStyleChanged() {
    val style = mockk<MapboxStyleManager>()
    preparePluginInitialisationWithEnabled()
    locationComponentPlugin.onStyleChanged(style)
    verify { locationPuckManager.updateStyle(style) }
  }
}