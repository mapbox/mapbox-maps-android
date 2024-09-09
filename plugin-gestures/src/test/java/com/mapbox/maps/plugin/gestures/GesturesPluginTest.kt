package com.mapbox.maps.plugin.gestures

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.PointF
import android.util.AttributeSet
import android.view.InputDevice.SOURCE_CLASS_POINTER
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_POINTER_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.MotionEvent.BUTTON_SECONDARY
import android.view.MotionEvent.TOOL_TYPE_FINGER
import android.view.MotionEvent.obtain
import androidx.test.core.view.PointerCoordsBuilder
import androidx.test.core.view.PointerPropertiesBuilder
import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.android.gestures.ShoveGestureDetector
import com.mapbox.android.gestures.StandardGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapCenterAltitudeMode
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.PlatformEventInfo
import com.mapbox.maps.PlatformEventType
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Size
import com.mapbox.maps.StylePropertyValue
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.logE
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapInteractionDelegate
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.slot
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class GesturesPluginTest {

  private val context: Context = mockk(relaxed = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val mapDelegateProvider: MapDelegateProvider = mockk(relaxUnitFun = true)

  private val mapTransformDelegate: MapTransformDelegate = mockk(relaxUnitFun = true)
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapPluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxUnitFun = true)
  private val mapProjectionDelegate: MapProjectionDelegate = mockk(relaxUnitFun = true)
  @OptIn(MapboxExperimental::class)
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val cameraAnimationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)

  private val mapCenterAltitudeMode: MapCenterAltitudeMode = mockk()

  private val gesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private var rotateGestureDetector: RotateGestureDetector = mockk(relaxUnitFun = true)
  private var shoveGestureDetector: ShoveGestureDetector = mockk()
  private var scaleGestureDetector: StandardScaleGestureDetector = mockk(relaxUnitFun = true)
  private var moveGestureDetector: MoveGestureDetector = mockk(relaxUnitFun = true)

  private lateinit var style: MapboxStyleManager

  private val motionEvent1 = mockk<MotionEvent>()
  private val motionEvent2 = mockk<MotionEvent>()

  private val typedArray: TypedArray = mockk(relaxed = true)
  private val pack = "com.mapbox.maps"

  private val gestureListener = slot<StandardGestureDetector.StandardOnGestureListener>()
  private lateinit var presenter: GesturesPluginImpl

  @MapboxExperimental
  @Before
  fun setUp() {
    mockkObject(GesturesAttributeParser)
    every {
      GesturesAttributeParser.parseGesturesSettings(
        context,
        attrs
      )
    } returns GesturesSettings { }

    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getInt(any(), any()) } returns 2
    every { typedArray.hasValue(any()) } returns true

    every { mapDelegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate

    every { mapDelegateProvider.mapInteractionDelegate } returns mapInteractionDelegate
    every { mapInteractionDelegate.addInteraction(any()) } returns Cancelable { }
    every { mapInteractionDelegate.dispatch(any()) } answers {
      val platformEventInfo = firstArg<PlatformEventInfo>()
      when (platformEventInfo.type) {
        PlatformEventType.CLICK -> presenter.handleClickEvent(platformEventInfo.screenCoordinate)
        PlatformEventType.LONG_CLICK -> presenter.handleLongPressEvent(platformEventInfo.screenCoordinate)
        PlatformEventType.DRAG_BEGIN -> presenter.handleMoveStartEvent()
        PlatformEventType.DRAG -> presenter.handleMove(platformEventInfo.screenCoordinate)
        PlatformEventType.DRAG_END -> presenter.handleMoveEnd()
      }
    }

    every { mapPluginProviderDelegate.getPlugin<CameraAnimationsPlugin>(Plugin.MAPBOX_CAMERA_PLUGIN_ID) } returns cameraAnimationsPlugin

    every { mapTransformDelegate.getSize() } returns Size(100.0f, 100.0f)
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(
      0.0,
      -10.0
    )
    every { mapCameraManagerDelegate.getCenterAltitudeMode() } returns mapCenterAltitudeMode
    every { mapCameraManagerDelegate.setCenterAltitudeMode(any()) } just runs
    style = mockk<MapboxStyleManager>()
    every { style.getStyleProjectionProperty("name") } returns StylePropertyValue(
      Value.valueOf("mercator"),
      StylePropertyValueKind.CONSTANT
    )

    every { motionEvent1.x } returns 0.0f
    every { motionEvent1.y } returns 0.0f
    every { motionEvent2.x } returns 0.0f
    every { motionEvent2.y } returns 0.0f

    presenter = GesturesPluginImpl(context, attrs, style)

    presenter.bind(context, gesturesManager, attrs, 1f)
    presenter.onDelegateProvider(mapDelegateProvider)
    every {
      gesturesManager.setStandardGestureListener(capture(gestureListener))
    } just Runs
    presenter.initialize()

    every { gesturesManager.rotateGestureDetector } returns rotateGestureDetector
    every { gesturesManager.standardScaleGestureDetector } returns scaleGestureDetector
    every { gesturesManager.shoveGestureDetector } returns shoveGestureDetector
    every { gesturesManager.moveGestureDetector } returns moveGestureDetector
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().build()
  }

  private fun setupMoveListener(isEnabled: Boolean = true): OnMoveListener {
    val listener: OnMoveListener = mockk(relaxed = true)
    presenter.addOnMoveListener(listener)
    every { moveGestureDetector.isEnabled } returns isEnabled
    every { moveGestureDetector.focalPoint } returns PointF(1.0f, 1.0f)
    presenter.moveGestureListener.detector = moveGestureDetector
    return listener
  }

  private fun setupRotateListener(isEnabled: Boolean = true): OnRotateListener {
    val listener: OnRotateListener = mockk(relaxed = true)
    presenter.addOnRotateListener(listener)
    every { rotateGestureDetector.isEnabled } returns isEnabled
    return listener
  }

  private fun setupScaleListener(isEnabled: Boolean = true): OnScaleListener {
    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    every { scaleGestureDetector.isEnabled } returns isEnabled
    return listener
  }

  private fun setupShoveListener(isEnabled: Boolean = true): OnShoveListener {
    val listener: OnShoveListener = mockk(relaxed = true)
    presenter.addOnShoveListener(listener)
    every { shoveGestureDetector.isEnabled } returns isEnabled
    return listener
  }

  private fun setupFlingListener(): OnFlingListener {
    val listener: OnFlingListener = mockk(relaxed = true)
    presenter.addOnFlingListener(listener)
    return listener
  }

  @After
  fun cleanUp() {
    clearAllMocks()
  }

  @Test
  fun verifyIgnoreEvent() {
    val touchHandled = presenter.onTouchEvent(null)
    verify(exactly = 0) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 0) { mapCameraManagerDelegate.setCenterAltitudeMode(any()) }
    assertFalse(touchHandled)
  }

  @Test
  fun verifyOnGenericMoveEventIgnored() {
    assertFalse(presenter.onGenericMotionEvent(obtainMotionEventAction(ACTION_DOWN)))
  }

  @Test
  fun verifyOnGenericMoveEvent() {
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 1) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }
  }

  @Test
  fun verifyDoubleTapFinished() {
    setupMoveListener()
    every { gesturesManager.onTouchEvent(any()) } returns true
    presenter.handleDoubleTapEvent(obtainMotionEventAction(ACTION_DOWN), 12.0f)
    val touchHandled = presenter.onTouchEvent(obtainMotionEventAction(ACTION_POINTER_DOWN))
    assert(touchHandled)
  }

  @Test
  fun verifyOnGenericMoveEventIgnore() {
    presenter.pinchToZoomEnabled = false
    assertFalse(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 0) { mapCameraManagerDelegate.setCamera(any<CameraOptions>()) }
  }

  @Test
  fun ignoreNonZeroButtonState() {
    val touchHandled = presenter.onTouchEvent(obtainMotionEventButton(BUTTON_SECONDARY))
    verify(exactly = 0) { mapTransformDelegate.setGestureInProgress(true) }
    assertFalse(touchHandled)
  }

  @Test
  fun verifyMotionEvent() {
    every { gesturesManager.onTouchEvent(any()) }.returns(true)
    val motionEvent: MotionEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.onTouchEvent(motionEvent)
    verify(exactly = 1) { gesturesManager.onTouchEvent(motionEvent) }
  }

  @Test
  fun verifyLongPressListener() {
    val listener: OnMapLongClickListener = mockk()
    every { listener.onMapLongClick(any()) } returns true
    val screenCoordinate = ScreenCoordinate(1.0, 1.0)
    val point = Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.coordinateForPixel(screenCoordinate) } returns point

    presenter.addOnMapLongClickListener(listener)
    presenter.handleLongPressEvent(screenCoordinate)
    verify(exactly = 1) { listener.onMapLongClick(point) }
    presenter.removeOnMapLongClickListener(listener)
    presenter.handleLongPressEvent(screenCoordinate)
    verify(exactly = 1) { listener.onMapLongClick(point) }
  }

  @Test
  fun verifyClickListener() {
    val listener: OnMapClickListener = mockk(relaxed = true)
    every { listener.onMapClick(any()) } returns true
    val screenCoordinate = ScreenCoordinate(1.0, 1.0)
    val point = Point.fromLngLat(0.0, 0.0)

    every { mapCameraManagerDelegate.coordinateForPixel(screenCoordinate) } returns point
    presenter.addOnMapClickListener(listener)
    presenter.handleClickEvent(screenCoordinate)
    verify(exactly = 1) { listener.onMapClick(point) }
    presenter.removeOnMapClickListener(listener)
    presenter.handleClickEvent(screenCoordinate)
    verify(exactly = 1) { listener.onMapClick(point) }
  }

  @Test
  fun verifySingleTapUp() {
    presenter.handleSingleTapUpEvent()
    verify(exactly = 1) { cameraAnimationsPlugin.cancelAllAnimators() }
  }

  @Test
  fun verifyDoubleTapEvent() {
    setupMoveListener()

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify(exactly = 1) { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapCameraManagerDelegate.cameraState.zoom } returns 5.0
    assert(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnoreLargeThreshold() {
    setupMoveListener()

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify(exactly = 1) { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventActionDistant(ACTION_UP)
    every { mapCameraManagerDelegate.cameraState.zoom } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnorePinchToZoomGesturesDisabled() {
    presenter.doubleTapToZoomInEnabled = false

    setupMoveListener()

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify(exactly = 1) { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapCameraManagerDelegate.cameraState.zoom } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnoreDoubleTapGesturesDisabled() {
    presenter.doubleTapToZoomInEnabled = false
    setupMoveListener()

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify(exactly = 1) { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapCameraManagerDelegate.cameraState.zoom } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventFocalPoint() {
    presenter.focalPoint = ScreenCoordinate(0.5, 0.5)

    setupMoveListener()

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify(exactly = 1) { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapCameraManagerDelegate.cameraState.zoom } returns 5.0
    assert(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyFlingListener() {
    val listener = setupFlingListener()
    setupMoveListener()

    presenter.handleFlingEvent(motionEvent2, 0.0f, 0.0f)
    verify(exactly = 1) { listener.onFling() }
    presenter.removeOnFlingListener(listener)
    presenter.handleFlingEvent(motionEvent2, 0.0f, 0.0f)
    verify(exactly = 1) { listener.onFling() }
  }

  @Test
  fun verifyFlingIgnoreSmallDisplacement() {
    val result = presenter.handleFlingEvent(motionEvent2, 0.1f, 0.1f)
    assertFalse(result)
  }

  @Test
  fun verifyFlingIgnoreConfiguration() {
    val listener = setupFlingListener()
    presenter.scrollEnabled = false
    val result = presenter.handleFlingEvent(motionEvent2, 0.1f, 0.1f)
    assertFalse(result)
    verify(exactly = 0) { listener.onFling() }
  }

  @Test
  fun verifyMoveStartDisabled() {
    val listener = setupMoveListener()
    presenter.scrollEnabled = false
    val handled = presenter.moveGestureListener.onMoveBegin(moveGestureDetector)
    assertFalse(handled)
    verify(exactly = 0) { listener.onMoveBegin(any()) }
  }

  @Test
  fun verifyMoveStartListener() {
    val listener = setupMoveListener()
    every { moveGestureDetector.isInProgress } returns false
    every { scaleGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { shoveGestureDetector.isInProgress } returns false
    val handled = presenter.moveGestureListener.onMoveBegin(moveGestureDetector)
    assertTrue(handled)
    verify(exactly = 1) { listener.onMoveBegin(any()) }
    presenter.removeOnMoveListener(listener)
    presenter.handleMoveStartEvent()
    verify(exactly = 1) { listener.onMoveBegin(any()) }
  }

  @Test
  fun verifyHandleMoveExceptionFreeForInvalidFocalPoint() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    val moveGestureDetector = mockk<MoveGestureDetector>()
    every { moveGestureDetector.pointersCount } returns 1
    every {
      moveGestureDetector.focalPoint
    } returns PointF(Float.NaN, Float.NaN)
    var handled = presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    assertFalse(handled)
    verify(exactly = 1) { logE(any(), any()) }
    every {
      moveGestureDetector.focalPoint
    } returns PointF(Float.POSITIVE_INFINITY, Float.NaN)
    handled = presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    assertFalse(handled)
    verify(exactly = 2) { logE(any(), any()) }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun verifyHandleMoveExceptionFreeForInvalidDistance() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    val moveGestureDetector = mockk<MoveGestureDetector>()
    every { moveGestureDetector.pointersCount } returns 1
    every {
      moveGestureDetector.focalPoint
    } returns PointF(0f, 0f)
    presenter.moveGestureListener.onMove(moveGestureDetector, 1f, Float.NaN)
    verify(exactly = 1) { logE(any(), any()) }
    presenter.moveGestureListener.onMove(moveGestureDetector, Float.NaN, 1f)
    verify(exactly = 2) { logE(any(), any()) }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @OptIn(MapboxExperimental::class)
  @Test
  fun verifyMoveListenerPinchScrollDisabled() {
    presenter.pinchScrollEnabled = false
    val listener = setupMoveListener()
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).build()

    val moveGestureDetector = mockk<MoveGestureDetector>()
    every { moveGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    every { moveGestureDetector.isInProgress } returns false
    presenter.moveGestureListener.detector = moveGestureDetector
    every { moveGestureDetector.pointersCount } returns 3
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    every { moveGestureDetector.pointersCount } returns 2
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    every { moveGestureDetector.pointersCount } returns 1
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    // only 1-pan gesture did actually trigger dispatch
    verify(exactly = 1) {
      mapInteractionDelegate.dispatch(
        PlatformEventInfo(
          PlatformEventType.DRAG,
          ScreenCoordinate(-50.0, -50.0)
        )
      )
    }
    // user listener is triggered only once
    verify(exactly = 1) { listener.onMove(any()) }
    // animation is run only once as well
    verify(exactly = 1) {
      mapCameraManagerDelegate.cameraForDrag(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(-50.0, -50.0)
      )
    }
    verify(exactly = 1) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyMoveListenerPinchScrollEnabled() {
    presenter.pinchScrollEnabled = true
    val listener = setupMoveListener()
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).build()
    val moveGestureDetector = mockk<MoveGestureDetector>()
    every { moveGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    every { moveGestureDetector.isInProgress } returns false
    presenter.moveGestureListener.detector = moveGestureDetector
    every { moveGestureDetector.pointersCount } returns 3
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    every { moveGestureDetector.pointersCount } returns 2
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    val mapAnimationOptionsSlot = slot<MapAnimationOptions>()
    verify(exactly = 1) {
      cameraAnimationsPlugin.easeTo(any(), capture(mapAnimationOptionsSlot))
    }
    every { moveGestureDetector.pointersCount } returns 1
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    verify(exactly = 2) { listener.onMove(any()) }
    verify(exactly = 2) {
      mapCameraManagerDelegate.cameraForDrag(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(-50.0, -50.0)
      )
    }
    verify(exactly = 2) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyMoveEndListener() {
    val listener = setupMoveListener()
    presenter.moveGestureListener.onMoveEnd(moveGestureDetector, 0f, 0f)
    verify(exactly = 1) { listener.onMoveEnd(any()) }
  }

  @Test
  fun verifyPinchToZoomDisabled() {
    presenter.pinchToZoomEnabled = false

    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0

    val listener = setupScaleListener()
    val result = presenter.handleScaleBegin(scaleDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onScaleBegin(any()) }
  }

  @Test
  fun verifyScaleBegin() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f
    every { rotateGestureDetector.deltaSinceLast } returns 50.0f
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    val listener = setupScaleListener()
    setupRotateListener()
    val result = presenter.handleScaleBegin(scaleDetector)
    assert(result)
    verify(exactly = 1) { listener.onScaleBegin(any()) }
    presenter.removeOnScaleListener(listener)
    presenter.handleScaleBegin(scaleDetector)
    verify(exactly = 1) { listener.onScaleBegin(any()) }
  }

  @Test
  fun verifySimultaneousRotateAndPinchToZoomDisabled() {
    // Override creation of default GesturesPluginImpl to apply custom GesturesSettings
    every {
      GesturesAttributeParser.parseGesturesSettings(
        context,
        attrs
      )
    } returns GesturesSettings { simultaneousRotateAndPinchToZoomEnabled = false }

    presenter = GesturesPluginImpl(context, attrs, style)
    presenter.bind(context, gesturesManager, attrs, 1f)
    presenter.onDelegateProvider(mapDelegateProvider)

    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f

    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.deltaSinceStart } returns 0.0f
    every { rotateGestureDetector.deltaSinceLast } returns 0.0f
    every { rotateGestureDetector.isEnabled } returns true
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)

    presenter.handleScaleBegin(scaleDetector)
    verify(exactly = 1) { rotateGestureDetector.setEnabled(false) }
  }

  @Test
  fun verifySimultaneousRotateAndPinchToZoomEnabled() {
    // Override creation of default GesturesPluginImpl to apply custom GesturesSettings
    every {
      GesturesAttributeParser.parseGesturesSettings(
        context,
        attrs
      )
    } returns GesturesSettings { simultaneousRotateAndPinchToZoomEnabled = true }

    presenter = GesturesPluginImpl(context, attrs, style)
    presenter.bind(context, gesturesManager, attrs, 1f)
    presenter.onDelegateProvider(mapDelegateProvider)

    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f

    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.deltaSinceStart } returns 0.0f
    every { rotateGestureDetector.deltaSinceLast } returns 0.0f
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)

    presenter.handleScaleBegin(scaleDetector)
    verify(exactly = 0) { rotateGestureDetector.setEnabled(any()) }
  }

  @Test
  fun verifyScaleBeginIgnoreSpeed() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f
    every { rotateGestureDetector.deltaSinceLast } returns 50.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.previousEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    setupScaleListener()
    val result = presenter.handleScaleBegin(scaleDetector)
    assertFalse(result)
  }

  @Test
  fun verifyScaleBeginIgnoreSameTime() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f
    every { rotateGestureDetector.deltaSinceLast } returns 50.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    setupScaleListener()
    val result = presenter.handleScaleBegin(scaleDetector)
    assertFalse(result)
  }

  @Test
  fun verifyScaleWithSimultaneousRotateAndPinchToZoomDisabled() {
    presenter.updateSettings { simultaneousRotateAndPinchToZoomEnabled = false }
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0

    setupScaleListener()
    val result = presenter.handleScale(scaleDetector)
    assert(result)
    // setMoveDetectorEnabled
    verify(exactly = 1) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyScaleWithSimultaneousRotateAndPinchToZoomEnabled() {
    val zoomAnimator = mockk<ValueAnimator>(relaxUnitFun = true)
    every { cameraAnimationsPlugin.createZoomAnimator(any(), any()) } returns zoomAnimator

    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0

    val listener = setupScaleListener()
    val result = presenter.handleScale(scaleDetector)
    assert(result)
    verify(exactly = 1) { cameraAnimationsPlugin.playAnimatorsTogether(any(), zoomAnimator) }
    verify(exactly = 1) { listener.onScale(any()) }
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyScaleQuickZoom() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 1
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { shoveGestureDetector.isInProgress } returns false
    every { scaleGestureDetector.isInProgress } returns false
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    setupMoveListener()
    presenter.handleScaleBegin(scaleDetector)

    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0

    setupScaleListener()
    val result = presenter.handleScale(scaleDetector)
    assert(result)
    // setMoveDetectorEnabled
    verify(exactly = 1) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyScaleEnd() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.isScalingOut } returns true
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapCameraManagerDelegate.cameraState.zoom } returns 1.0

    setupScaleListener()
    presenter.handleScaleEnd(scaleDetector, 15.0f, 15.0f)
    // todo animator testing
  }

  @Test
  fun verifyRotateDisabled() {
    presenter.rotateEnabled = false
    val listener = setupRotateListener()
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    // every { rotateGestureDetector.cursetMoveDetectorEnabledrentEvent } returns obtainMotionEventActionLater(MotionEvent.ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateDisabledByThresholdIfScaleInProgress() {
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { scaleGestureDetector.isInProgress } returns true

    every { rotateGestureDetector.deltaSinceLast } returns 150.5f
    every { rotateGestureDetector.deltaSinceStart } returns 15.5f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
  }

  @Test
  fun verifyRotateIgnoreSameTime() {
    val listener = setupRotateListener()
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateIgnoreTooSlow() {
    val listener = setupRotateListener()
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 0.0f
    every { rotateGestureDetector.deltaSinceStart } returns 0.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateBegin() {
    val listener = setupRotateListener()
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { rotateGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { moveGestureDetector.isInProgress } returns false
    every { scaleGestureDetector.isInProgress } returns false
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assert(result)
    verify(exactly = 1) { listener.onRotateBegin(any()) }
    presenter.removeOnRotateListener(listener)
    presenter.handleRotateBegin(rotateGestureDetector)
    verify(exactly = 1) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateWithSimultaneousRotateAndPinchToZoomDisabled() {
    presenter.updateSettings { simultaneousRotateAndPinchToZoomEnabled = false }
    val rotateGestureDetector = mockk<RotateGestureDetector>()
    setupRotateListener()
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      1.0,
      0.0
    )
    every { rotateGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    val result = presenter.handleRotate(rotateGestureDetector, 34.0f)
    assert(result)
    verify(exactly = 1) { cameraAnimationsPlugin.easeTo(any(), any(), any()) }

    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyRotateWithSimultaneousRotateAndPinchToZoomEnabled() {
    val bearingAnimator = mockk<ValueAnimator>(relaxUnitFun = true)
    every {
      cameraAnimationsPlugin.createBearingAnimator(
        any(),
        any(),
        any()
      )
    } returns bearingAnimator

    val rotateGestureDetector = mockk<RotateGestureDetector>()
    every { rotateGestureDetector.focalPoint } returns PointF(1.0f, 1.0f)
    val listener = setupRotateListener()
    val result = presenter.handleRotate(rotateGestureDetector, 34.0f)
    assert(result)
    verify(exactly = 1) { cameraAnimationsPlugin.playAnimatorsTogether(any(), bearingAnimator) }
    verify(exactly = 1) { listener.onRotate(any()) }

    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyRotateEnd() {
    val rotateGestureDetector = mockk<RotateGestureDetector>()
    every { rotateGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { mapCameraManagerDelegate.cameraState.bearing } returns 0.0
    every { rotateGestureDetector.isInProgress } returns true
    every { scaleGestureDetector.isInProgress } returns false
    setupRotateListener()
    presenter.handleRotateEnd(rotateGestureDetector, 15.0f, 15.0f, 15.0f)
    // todo animator testing
  }

  @Test
  fun verifyShoveDisabled() {
    presenter.pitchEnabled = false

    val listener = setupShoveListener()
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    val result = presenter.handleShoveBegin(gestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onShoveBegin(any()) }
  }

  @Test
  fun verifyShoveBegin() {
    val shoveListener = setupShoveListener()
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    val moveListener = setupMoveListener()
    every { moveGestureDetector.isInProgress } returns true
    val result = presenter.handleShoveBegin(gestureDetector)
    assert(result)
    verify(exactly = 1) { shoveListener.onShoveBegin(any()) }
    presenter.removeOnShoveListener(shoveListener)
    presenter.removeOnMoveListener(moveListener)
    presenter.handleShoveBegin(gestureDetector)
    verify(exactly = 1) { shoveListener.onShoveBegin(any()) }
  }

  @Test
  fun verifyShove() {
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      1.0
    )
    val cameraCenterScreenCoordinate = ScreenCoordinate(
      10.0,
      10.0
    )
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns cameraCenterScreenCoordinate
    setupShoveListener()
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    val result = presenter.handleShove(gestureDetector, -15.0f)
    val resultCameraOptions = slot<CameraOptions>()
    assert(result)
    verify(exactly = 1) {
      cameraAnimationsPlugin.easeTo(
        capture(resultCameraOptions),
        any(),
      )
    }
    assertEquals(resultCameraOptions.captured.pitch!!, 2.5, EPS)
    assertEquals(resultCameraOptions.captured.anchor, cameraCenterScreenCoordinate)

    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 1) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyMapCenterAltitudeModeIsSEABeforeGesture() {
    every { mapCameraManagerDelegate.getCenterAltitudeMode() } returns MapCenterAltitudeMode.SEA
    val moveGestureDetector = mockk<MoveGestureDetector>()
    every {
      moveGestureDetector.focalPoint
    } returns PointF(0.0f, 0.0f)

    every { moveGestureDetector.pointersCount } returns 1
    presenter.moveGestureListener.detector = moveGestureDetector
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    verify(exactly = 2) { mapCameraManagerDelegate.getCenterAltitudeMode() }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(true) }
    verify(exactly = 0) { mapCameraManagerDelegate.setCenterAltitudeMode(MapCenterAltitudeMode.SEA) }

    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify(exactly = 0) { mapCameraManagerDelegate.setCenterAltitudeMode(mapCenterAltitudeMode) }
    verify(exactly = 1) { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyShoveEnd() {
    every { mapCameraManagerDelegate.cameraState.pitch } returns 5.0
    val listener = setupShoveListener()
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    presenter.handleShoveEnd(gestureDetector)
    verify(exactly = 1) { listener.onShoveEnd(any()) }
  }

  @Test
  fun verifyCustomGestureManager() {
    val customManager = mockk<AndroidGesturesManager>(relaxed = true)
    presenter.setGesturesManager(
      customManager,
      attachDefaultListeners = false,
      setDefaultMutuallyExclusives = false
    )
    assertEquals(customManager, presenter.getGesturesManager())
  }

  @Test
  fun verifyAddProtectedAnimationOwner() {
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    presenter.addProtectedAnimationOwner("Owner")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 1) { cameraAnimationsPlugin.cancelAllAnimators(listOf("Owner")) }
  }

  @Test
  fun verifyRemoveProtectedAnimationOwner() {
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      1.0,
      0.0,
      0.0
    )
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    presenter.addProtectedAnimationOwner("OwnerOne")
    presenter.addProtectedAnimationOwner("OwnerTwo")
    presenter.removeProtectedAnimationOwner("OwnerOne")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 1) { cameraAnimationsPlugin.cancelAllAnimators(listOf("OwnerTwo")) }
    presenter.removeProtectedAnimationOwner("OwnerTwo")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 1) { cameraAnimationsPlugin.cancelAllAnimators() }
  }

  @Test
  fun verifyDefaultShoveGestureAngle() {
    verify(exactly = 1) {
      gesturesManager.shoveGestureDetector.maxShoveAngle = MAX_SHOVE_ANGLE
    }
  }

  @Test
  fun verifyDefaultRotationAngleThreshold() {
    verify(exactly = 1) {
      gesturesManager.rotateGestureDetector.angleThreshold = ROTATION_ANGLE_THRESHOLD
    }
  }

  @Test
  fun testIsPointAboveHorizonNanX() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs

    assertEquals(false, presenter.isPointAboveHorizon(ScreenCoordinate(Double.NaN, 10.0)))
    verify {
      mapCameraManagerDelegate.coordinateForPixel(ScreenCoordinate(0.0, 6.0))
    }
    verify {
      logE(any(), "isPointAboveHorizon: screen coordinate x is NaN.")
    }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  @Test
  fun testIsPointAboveHorizonNanY() {
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs

    assertEquals(false, presenter.isPointAboveHorizon(ScreenCoordinate(0.0, Double.NaN)))
    verify {
      mapCameraManagerDelegate.coordinateForPixel(ScreenCoordinate(0.0, -4.0))
    }
    verify {
      logE(any(), "isPointAboveHorizon: screen coordinate y is NaN.")
    }
    unmockkStatic("com.mapbox.maps.MapboxLogger")
  }

  private companion object {
    const val ROTATION_ANGLE_THRESHOLD = 3.0f
    const val MAX_SHOVE_ANGLE = 45.0f

    const val EPS = 0.000001

    fun obtainMotionEventButton(buttonType: Int): MotionEvent {
      return obtain(
        200,
        200,
        MotionEvent.ACTION_SCROLL,
        1,
        arrayOf(
          PointerPropertiesBuilder.newBuilder().setId(0).setToolType(TOOL_TYPE_FINGER).build()
        ),
        arrayOf(PointerCoordsBuilder.newBuilder().build()),
        0,
        buttonType,
        0.0f,
        0.0f,
        0,
        0,
        SOURCE_CLASS_POINTER,
        0
      )
    }

    fun obtainMotionEventAction(action: Int): MotionEvent {
      return obtain(200, 300, action, 15.0f, 10.0f, 0)
    }

    fun obtainMotionEventActionDistant(action: Int): MotionEvent {
      return obtain(200, 300, action, 500.0f, 500.0f, 0)
    }

    fun obtainMotionEventActionLater(action: Int): MotionEvent {
      return obtain(200, 500, action, 15.0f, 10.0f, 0)
    }
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class IsPointAboveHorizonTest(
  private val testProjectionStylePropertyValue: StylePropertyValue,
  private val testScreenCoordinate: ScreenCoordinate,
  private val testMapSize: Size,
  private val testPixelForCoordinate: ScreenCoordinate,
  private val expectedResult: Boolean
) {
  private val context: Context = mockk(relaxed = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)
  private val mapDelegateProvider: MapDelegateProvider = mockk(relaxUnitFun = true)
  private val gesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk(relaxUnitFun = true)
  private val mapTransformDelegate: MapTransformDelegate = mockk(relaxUnitFun = true)
  private val mapPluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxUnitFun = true)
  private val mapProjectionDelegate: MapProjectionDelegate = mockk(relaxUnitFun = true)
  @OptIn(MapboxExperimental::class)
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val cameraAnimationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)
  private val style: MapboxStyleManager = mockk()

  private lateinit var presenter: GesturesPluginImpl

  private val typedArray: TypedArray = mockk(relaxed = true)
  private val pack = "com.mapbox.maps"

  @MapboxExperimental
  @Before
  fun prepare() {
    mockkObject(GesturesAttributeParser)
    every {
      GesturesAttributeParser.parseGesturesSettings(
        context,
        attrs
      )
    } returns GesturesSettings { }

    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getInt(any(), any()) } returns 2
    every { typedArray.hasValue(any()) } returns true

    presenter = GesturesPluginImpl(context, attrs, style)
    presenter.bind(
      context,
      gesturesManager,
      attrs,
      1f
    )

    every { mapDelegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapDelegateProvider.mapInteractionDelegate } returns mapInteractionDelegate
    every { mapInteractionDelegate.addInteraction(any()) } returns Cancelable { }
    every { mapInteractionDelegate.dispatch(any()) } answers {
      val platformEventInfo = firstArg<PlatformEventInfo>()
      when (platformEventInfo.type) {
        PlatformEventType.DRAG -> presenter.handleMove(platformEventInfo.screenCoordinate)
        else -> throw RuntimeException("Not needed in this test")
      }
    }
    every { mapPluginProviderDelegate.getPlugin<CameraAnimationsPlugin>(Plugin.MAPBOX_CAMERA_PLUGIN_ID) } returns cameraAnimationsPlugin
    every { style.getStyleProjectionProperty("name") } returns testProjectionStylePropertyValue
    every { mapTransformDelegate.getSize() } returns testMapSize
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns testPixelForCoordinate
    every { mapCameraManagerDelegate.getCenterAltitudeMode() } returns MapCenterAltitudeMode.TERRAIN

    presenter.onDelegateProvider(mapDelegateProvider)
    presenter.initialize()
  }

  @Test
  fun testIsPointAboveHorizon() {
    assertEquals(expectedResult, presenter.isPointAboveHorizon(testScreenCoordinate))
  }

  @Test
  fun testMoveEvent() {
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).build()

    val moveGestureDetector = mockk<MoveGestureDetector>()
    every {
      moveGestureDetector.focalPoint
    } returns PointF(testScreenCoordinate.x.toFloat(), testScreenCoordinate.y.toFloat())
    every { moveGestureDetector.pointersCount } returns 1
    presenter.moveGestureListener.detector = moveGestureDetector
    presenter.moveGestureListener.onMove(moveGestureDetector, 50.0f, 50.0f)
    verify(exactly = if (expectedResult) 0 else 1) {
      cameraAnimationsPlugin.easeTo(any(), any(), any())
    }
  }

  @Test
  fun testFlingEvent() {
    val motionEvent1 = mockk<MotionEvent>()
    val motionEvent2 = mockk<MotionEvent>()
    every { motionEvent1.x } returns testScreenCoordinate.x.toFloat()
    every { motionEvent1.y } returns testScreenCoordinate.y.toFloat()
    every { motionEvent2.x } returns testScreenCoordinate.x.toFloat()
    every { motionEvent2.y } returns testScreenCoordinate.y.toFloat()
    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      0.0
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).build()

    val listener: OnFlingListener = mockk(relaxed = true)
    presenter.addOnFlingListener(listener)
    presenter.handleFlingEvent(motionEvent2, 1000.0f, 1000.0f)
    verify(exactly = if (expectedResult) 0 else 1) { listener.onFling() }
    verify(exactly = if (expectedResult) 0 else 1) {
      cameraAnimationsPlugin.easeTo(
        any(),
        any(),
        any()
      )
    }
  }

  @After
  fun cleanup() {
    unmockkAll()
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "IsPointAboveHorizon using {0} projection at {1}, with MapSize {2}, testPixelForCoordinate {3} should be {4}")
    fun data() = listOf(
      arrayOf(
        StylePropertyValue(
          Value.valueOf("globe"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 0.0),
        Size(100f, 100f),
        ScreenCoordinate(0.0, 0.0),
        false
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 0.0),
        Size(100f, 100f),
        ScreenCoordinate(0.0, 0.0),
        true
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("any"),
          StylePropertyValueKind.UNDEFINED
        ),
        ScreenCoordinate(0.0, 0.0),
        Size(100f, 100f),
        ScreenCoordinate(0.0, 0.0),
        true
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 2.0),
        Size(100f, 100f),
        ScreenCoordinate(0.0, 0.0),
        true
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 3.0),
        Size(100f, 100f),
        ScreenCoordinate(0.0, 0.0),
        false
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 10.0),
        Size(500f, 500f),
        ScreenCoordinate(0.0, 0.0),
        true
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 11.0),
        Size(500f, 500f),
        ScreenCoordinate(0.0, 0.0),
        false
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 10.0),
        Size(1000f, 1000f),
        ScreenCoordinate(0.0, 0.0),
        true
      ),
      arrayOf(
        StylePropertyValue(
          Value.valueOf("mercator"),
          StylePropertyValueKind.CONSTANT
        ),
        ScreenCoordinate(0.0, 11.0),
        Size(500f, 500f),
        ScreenCoordinate(0.0, 0.0),
        false
      ),
    )
  }
}

@RunWith(ParameterizedRobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class FlingGestureTest(
  private val targetPitch: Double,
  private val targetScrollMode: ScrollMode,
  private val targetVelocity: Pair<Float, Float>,
  private val expectedCoordinate: ScreenCoordinate,
  private val expectedFlingDuration: Long
) {
  private val context: Context = mockk(relaxed = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)
  private val mapDelegateProvider: MapDelegateProvider = mockk(relaxUnitFun = true)
  private val gesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk(relaxUnitFun = true)
  private val mapTransformDelegate: MapTransformDelegate = mockk(relaxUnitFun = true)
  private val mapPluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxUnitFun = true)
  private val mapProjectionDelegate: MapProjectionDelegate = mockk(relaxUnitFun = true)
  private val cameraAnimationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)

  private lateinit var presenter: GesturesPluginImpl

  private val typedArray: TypedArray = mockk(relaxed = true)
  private val pack = "com.mapbox.maps"

  private val motionEvent1 = mockk<MotionEvent>()
  private val motionEvent2 = mockk<MotionEvent>()
  private val mapAnimationOptionsSlot = slot<MapAnimationOptions>()

  @MapboxExperimental
  @Before
  fun prepare() {
    mockkObject(GesturesAttributeParser)
    every {
      GesturesAttributeParser.parseGesturesSettings(
        context,
        attrs
      )
    } returns GesturesSettings { }

    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getInt(any(), any()) } returns 2
    every { typedArray.hasValue(any()) } returns true

    every { mapDelegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
    every { mapDelegateProvider.mapInteractionDelegate } returns mockk(relaxed = true)
    every { mapPluginProviderDelegate.getPlugin<CameraAnimationsPlugin>(Plugin.MAPBOX_CAMERA_PLUGIN_ID) } returns cameraAnimationsPlugin
    every { mapTransformDelegate.getSize() } returns Size(100.0f, 100.0f)
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(1.0, 1.0)
    // Make sure isPointAboveHorizon return false
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(
      0.0,
      -10.0
    )
    every { mapCameraManagerDelegate.getCenterAltitudeMode() } returns MapCenterAltitudeMode.TERRAIN
    val style = mockk<MapboxStyleManager>()
    every { style.getStyleProjectionProperty("name") } returns StylePropertyValue(
      Value.valueOf("mercator"),
      StylePropertyValueKind.CONSTANT
    )

    presenter = GesturesPluginImpl(context, attrs, style)

    presenter.bind(context, gesturesManager, attrs, 1f)
    presenter.onDelegateProvider(mapDelegateProvider)
    presenter.initialize()

    every { mapCameraManagerDelegate.cameraState } returns CameraState(
      Point.fromLngLat(0.0, 0.0),
      EdgeInsets(0.0, 0.0, 0.0, 0.0),
      0.0,
      0.0,
      targetPitch
    )
    every {
      mapCameraManagerDelegate.cameraForDrag(
        any(),
        any()
      )
    } returns CameraOptions.Builder().build()
    presenter.updateSettings { scrollMode = targetScrollMode }
    every { motionEvent1.x } returns 0.0f
    every { motionEvent1.y } returns 0.0f
    every { motionEvent2.x } returns 0.0f
    every { motionEvent2.y } returns 0.0f
  }

  @Test
  fun testFling() {
    val result =
      presenter.handleFlingEvent(
        motionEvent2,
        targetVelocity.first,
        targetVelocity.second
      )
    verify(exactly = 1) {
      mapCameraManagerDelegate.cameraForDrag(
        ScreenCoordinate(0.0, 0.0),
        expectedCoordinate
      )
    }
    verify(exactly = 1) {
      cameraAnimationsPlugin.easeTo(
        CameraOptions.Builder().build(),
        capture(mapAnimationOptionsSlot),
        any()
      )
    }
    assert(result)
    assertEquals(MapAnimationOwnerRegistry.GESTURES, mapAnimationOptionsSlot.captured.owner)
    assertEquals(expectedFlingDuration, mapAnimationOptionsSlot.captured.duration)
  }

  private companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "Fling at pitch {0} with scroll mode {1} and velocity {2} should end at screen coordinate {3} and fling duration should be {4}")
    fun data() = listOf(
      arrayOf(
        0.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(FLING_VELOCITY, FLING_VELOCITY),
        ScreenCoordinate(FLING_DISPLACEMENT, FLING_DISPLACEMENT),
        1414L
      ),
      arrayOf(
        55.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(FLING_VELOCITY, FLING_VELOCITY),
        ScreenCoordinate(645.1612903225806, 645.1612903225806),
        912L
      ),
      arrayOf(
        80.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(FLING_VELOCITY, FLING_VELOCITY),
        ScreenCoordinate(67.93869850933991, 67.93869850933991),
        96L
      ),
      arrayOf(
        0.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(800f, 750f),
        ScreenCoordinate(80.0, 75.0),
        109L
      ),
      arrayOf(
        55.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(800f, 750f),
        ScreenCoordinate(51.61290322580645, 48.38709677419355),
        70L
      ),
      arrayOf(
        80.0,
        ScrollMode.HORIZONTAL_AND_VERTICAL,
        Pair(800f, 750f),
        ScreenCoordinate(5.435095880747194, 5.095402388200494),
        7L
      ),
      arrayOf(
        0.0,
        ScrollMode.VERTICAL,
        Pair(FLING_VELOCITY, FLING_VELOCITY),
        ScreenCoordinate(0.0, FLING_DISPLACEMENT),
        1414L
      ),
      arrayOf(
        0.0,
        ScrollMode.HORIZONTAL,
        Pair(FLING_VELOCITY, FLING_VELOCITY),
        ScreenCoordinate(FLING_DISPLACEMENT, 0.0),
        1414L
      ),
    )

    const val FLING_DISPLACEMENT = 1000.0
    const val FLING_VELOCITY = 10000f
  }
}