package com.mapbox.maps.plugin.gestures

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.getCameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GestureOptionsTest {

  private val context: Context = mockk(relaxed = true)

  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxUnitFun = true)

  private val gestureManager: AndroidGesturesManager = mockk(relaxed = true)

  private val mapTransformDelegate: MapTransformDelegate = mockk(relaxUnitFun = true)

  private val mapProjectionDelegate: MapProjectionDelegate = mockk(relaxUnitFun = true)

  private val mapDelegateProvider: MapDelegateProvider = mockk(relaxUnitFun = true)

  private val mapPluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxUnitFun = true)

  private val cameraAnimationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)

  private val pack = "com.mapbox.maps"

  private val gesturePlugin =
    GesturesPluginImpl(context, mockk(relaxed = true), mockk(relaxed = true))

  @Before
  fun setUp() {
    mockkObject(GesturesAttributeParser::class)
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.hasValue(any()) } returns true
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapPluginProviderDelegate.getCameraAnimationsPlugin() } returns cameraAnimationsPlugin
    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun getGestureSettingsZoomEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().zoomEnabled)
  }

  @Test
  fun getGestureSettingsZoomDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesZoomEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().zoomEnabled)
  }

  @Test
  fun getGestureSettingsRotateEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().rotateEnabled)
  }

  @Test
  fun getGestureSettingsRotateDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesRotateEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().rotateEnabled)
  }

  @Test
  fun getGestureSettingsPitchEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().pitchEnabled)
  }

  @Test
  fun getGestureSettingsPitchDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesPitchEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().pitchEnabled)
  }

  @Test
  fun getGestureSettingsScrollEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().scrollEnabled)
  }

  @Test
  fun getGestureSettingsScrollDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesScrollEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().scrollEnabled)
  }

  @Test
  fun getGestureSettingsQuickZoomEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().quickZoomEnabled)
  }

  @Test
  fun getGestureSettingsQuickZoomDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesQuickZoomEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().quickZoomEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTapEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertTrue(gesturePlugin.getSettings().doubleTapToZoomEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTapDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesDoubleTapToZoomEnabled,
        true
      )
    } returns false
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().doubleTapToZoomEnabled)
  }
}