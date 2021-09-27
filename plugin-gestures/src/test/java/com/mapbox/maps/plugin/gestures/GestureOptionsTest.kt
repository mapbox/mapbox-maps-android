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

  private val mainHandler = mockk<Handler>()

  private val runnableSlot = slot<Runnable>()

  private val gesturePlugin =
    GesturesPluginImpl(context, mockk(relaxed = true), mockk(relaxed = true), mainHandler)

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
    every { mainHandler.post(capture(runnableSlot)) } answers {
      runnableSlot.captured.run()
      true
    }
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun getGestureSettingsPinchToZoomEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().pinchToZoomEnabled)
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
  fun getGestureSettingsDoubleTapToZoomInEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertFalse(gesturePlugin.getSettings().doubleTapToZoomInEnabled)
  }

  @Test
  fun getGestureSettingsDoubleTouchToZoomOutEnabled() {
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
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
    gesturePlugin.bind(context, gestureManager, attrs, 1f)
    gesturePlugin.initialize()
    assertEquals(gesturePlugin.getSettings().scrollMode, ScrollMode.VERTICAL)
  }
}