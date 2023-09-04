package com.mapbox.maps.plugin.gestures

import android.content.Context
import android.content.res.TypedArray
import android.os.Handler
import android.util.AttributeSet
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
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

  private val animationsTimeoutHandler = mockk<Handler>()

  private val runnableSlot = slot<Runnable>()

  private val gesturePlugin =
    GesturesPluginImpl(context, mockk(relaxed = true), mockk(relaxed = true), animationsTimeoutHandler)

  @Before
  fun setUp() {
    mockkObject(GesturesAttributeParser::class)
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getInt(any(), any()) } returns 0
    every { typedArray.hasValue(any()) } returns true
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapPluginProviderDelegate.camera } returns cameraAnimationsPlugin
    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
  }

  private fun setupGesturePlugin() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
  }

  @After
  fun cleanUp() {
    clearAllMocks()
  }

  @Test
  fun getGestureSettingsPinchToZoomEnabled() {
    setupGesturePlugin()
    assertTrue(gesturePlugin.getSettings().pinchToZoomEnabled)
  }

  @Test
  fun getGestureSettingsPinchToZoomDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesPinchToZoomEnabled,
        true
      )
    } returns false
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().pinchToZoomEnabled)
  }

  @Test
  fun getGestureSettingsRotateEnabled() {
    setupGesturePlugin()
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
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().rotateEnabled)
  }

  @Test
  fun getGestureSettingsPitchEnabled() {
    setupGesturePlugin()
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
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().pitchEnabled)
  }

  @Test
  fun getGestureSettingsScrollEnabled() {
    setupGesturePlugin()
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
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().scrollEnabled)
  }

  @Test
  fun getGestureSettingsQuickZoomEnabled() {
    setupGesturePlugin()
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
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().quickZoomEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTapToZoomInEnabled() {
    setupGesturePlugin()
    assertTrue(gesturePlugin.getSettings().doubleTapToZoomInEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTapToZoomInDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesDoubleTapToZoomInEnabled,
        true
      )
    } returns false
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().doubleTapToZoomInEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTouchToZoomOutEnabled() {
    setupGesturePlugin()
    assertTrue(gesturePlugin.getSettings().doubleTouchToZoomOutEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTouchToZoomOutDisabled() {
    every {
      typedArray.getBoolean(
        R.styleable.mapbox_MapView_mapbox_gesturesDoubleTouchToZoomOutEnabled,
        true
      )
    } returns false
    setupGesturePlugin()
    assertFalse(gesturePlugin.getSettings().doubleTouchToZoomOutEnabled)
  }

  @Test
  fun getGestureSettingsPanScrollDefault() {
    every {
      typedArray.getInt(
        R.styleable.mapbox_MapView_mapbox_gesturesScrollMode,
        any()
      )
    } returns ScrollMode.HORIZONTAL_AND_VERTICAL.ordinal
    setupGesturePlugin()
    assertEquals(gesturePlugin.getSettings().scrollMode, ScrollMode.HORIZONTAL_AND_VERTICAL)
  }

  @Test
  fun getGestureSettingsPanScrollHorizontal() {
    every {
      typedArray.getInt(
        R.styleable.mapbox_MapView_mapbox_gesturesScrollMode,
        any()
      )
    } returns ScrollMode.HORIZONTAL.ordinal
    setupGesturePlugin()
    assertEquals(gesturePlugin.getSettings().scrollMode, ScrollMode.HORIZONTAL)
  }

  @Test
  fun getGestureSettingsPanScrollVertical() {
    every {
      typedArray.getInt(
        R.styleable.mapbox_MapView_mapbox_gesturesScrollMode,
        any()
      )
    } returns ScrollMode.VERTICAL.ordinal
    setupGesturePlugin()
    assertEquals(gesturePlugin.getSettings().scrollMode, ScrollMode.VERTICAL)
  }
}