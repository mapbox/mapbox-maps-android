package com.mapbox.maps.plugin.gestures

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PointF
import android.util.AttributeSet
import android.view.InputDevice.SOURCE_CLASS_POINTER
import android.view.MotionEvent
import android.view.MotionEvent.*
import androidx.test.core.view.PointerCoordsBuilder
import androidx.test.core.view.PointerPropertiesBuilder
import com.mapbox.android.gestures.*
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesAttributeParser
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class GesturePluginTest {

  private val context: Context = mockk(relaxed = true)
  private val attrs: AttributeSet = mockk(relaxUnitFun = true)

  private val mapDelegateProvider: MapDelegateProvider = mockk(relaxUnitFun = true)

  private val mapTransformDelegate: MapTransformDelegate = mockk(relaxUnitFun = true)
  private val mapProjectionDelegate: MapProjectionDelegate = mockk(relaxUnitFun = true)
  private val mapPluginProviderDelegate: MapPluginProviderDelegate = mockk(relaxUnitFun = true)
  private val cameraAnimationsPlugin: CameraAnimationsPlugin = mockk(relaxed = true)

  private val gesturesManager: AndroidGesturesManager = mockk(relaxed = true)
  private var rotateGestureDetector: RotateGestureDetector = mockk(relaxUnitFun = true)
  private var shoveGestureDetector: ShoveGestureDetector = mockk()
  private var scaleGestureDetector: StandardScaleGestureDetector = mockk(relaxUnitFun = true)
  private var moveGestureDetector: MoveGestureDetector = mockk(relaxUnitFun = true)

  private val typedArray: TypedArray = mockk(relaxed = true)
  private val pack = "com.mapbox.maps"

  private lateinit var presenter: GesturesPluginImpl

  @Before
  fun setUp() {
    mockkObject(GesturesAttributeParser::class)
    every { GesturesAttributeParser.parseGesturesSettings(context, attrs, any()) } returns GesturesSettings()

    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { context.packageName } returns pack
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.hasValue(any()) } returns true

    every { mapDelegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapDelegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
    every { mapDelegateProvider.mapPluginProviderDelegate } returns mapPluginProviderDelegate
    every { mapPluginProviderDelegate.getPlugin(any<Class<CameraAnimationsPlugin>>()) } returns cameraAnimationsPlugin

    presenter = GesturesPluginImpl(context, attrs, mockk(relaxed = true))

    presenter.bind(context, gesturesManager, attrs, 1f)
    presenter.onDelegateProvider(mapDelegateProvider)
    presenter.initialize()

    every { gesturesManager.rotateGestureDetector } returns rotateGestureDetector
    every { gesturesManager.standardScaleGestureDetector } returns scaleGestureDetector
    every { gesturesManager.shoveGestureDetector } returns shoveGestureDetector
    every { gesturesManager.moveGestureDetector } returns moveGestureDetector
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun verifyIgnoreEvent() {
    val touchHandled = presenter.onTouchEvent(null)
    verify(exactly = 0) { mapTransformDelegate.setGestureInProgress(true) }
    assertFalse(touchHandled)
  }

  @Test
  fun verifyOnGenericMoveEventIgnored() {
    assertFalse(presenter.onGenericMotionEvent(obtainMotionEventAction(ACTION_DOWN)))
  }

  @Test
  fun verifyOnGenericMoveEvent() {
    every { mapTransformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().zoom(1.0).build()
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
  }

  @Test
  fun verifyDoubleTapFinished() {
    every { gesturesManager.onTouchEvent(any()) } returns true
    presenter.handleDoubleTapEvent(obtainMotionEventAction(ACTION_DOWN), 12.0f)
    val touchHandled = presenter.onTouchEvent(obtainMotionEventAction(ACTION_POINTER_DOWN))
    assert(touchHandled)
  }

  @Test
  fun verifyOnGenericMoveEventIgnore() {
    presenter.zoomEnabled = false
    assertFalse(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify(exactly = 0) { mapTransformDelegate.setCamera(any()) }
  }

  @Test
  fun ignoreNonZeroButtonState() {
    val touchHandled = presenter.onTouchEvent(obtainMotionEventButton(BUTTON_SECONDARY))
    verify(exactly = 0) { mapTransformDelegate.setGestureInProgress(true) }
    assertFalse(touchHandled)
  }

  @Test
  fun verifyGestureInProgress() {
    every { gesturesManager.onTouchEvent(any()) }.returns(true)
    presenter.onTouchEvent(obtainMotionEventAction(ACTION_DOWN))
    verify { mapTransformDelegate.setGestureInProgress(true) }
    presenter.onTouchEvent(obtainMotionEventAction(ACTION_UP))
    verify { mapTransformDelegate.setGestureInProgress(false) }
  }

  @Test
  fun verifyMotionEvent() {
    every { gesturesManager.onTouchEvent(any()) }.returns(true)
    val motionEvent: MotionEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.onTouchEvent(motionEvent)
    verify { gesturesManager.onTouchEvent(motionEvent) }
  }

  @Test
  fun verifyLongPressListener() {
    val listener: OnMapLongClickListener = mockk()
    every { listener.onMapLongClick(any()) } returns true
    val screenCoordinate = ScreenCoordinate(1.0, 1.0)
    val point = Point.fromLngLat(0.0, 0.0)
    every { mapProjectionDelegate.coordinateForPixel(screenCoordinate) } returns point

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

    every { mapProjectionDelegate.coordinateForPixel(screenCoordinate) } returns point
    presenter.addOnMapClickListener(listener)
    presenter.handleClickEvent(screenCoordinate)
    verify { listener.onMapClick(point) }
    presenter.removeOnMapClickListener(listener)
    presenter.handleClickEvent(screenCoordinate)
    verify { listener.onMapClick(point) }
  }

  @Test
  fun verifySingleTapUp() {
    presenter.handleSingleTapUpEvent()
    verify { cameraAnimationsPlugin.cancelAllAnimators() }
  }

  @Test
  fun verifyDoubleTapEvent() {
    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapTransformDelegate.getZoom() } returns 5.0
    assert(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnoreLargeThreshold() {
    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventActionDistant(ACTION_UP)
    every { mapTransformDelegate.getZoom() } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnoreZoomGesturesDisabled() {
    presenter.zoomEnabled = false

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapTransformDelegate.getZoom() } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventIgnoreDoubleTapGesturesDisabled() {
    presenter.doubleTapToZoomEnabled = false

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapTransformDelegate.getZoom() } returns 5.0
    assertFalse(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyDoubleTapEventFocalPoint() {
    presenter.focalPoint = ScreenCoordinate(0.5, 0.5)

    // verify initial tap
    val downEvent = obtainMotionEventAction(ACTION_DOWN)
    presenter.handleDoubleTapEvent(downEvent, 0.0f)
    verify { moveGestureDetector.isEnabled = false }
    assertTrue(presenter.doubleTapRegistered)

    // verify second tap
    val upEvent = obtainMotionEventAction(ACTION_UP)
    every { mapTransformDelegate.getZoom() } returns 5.0
    assert(presenter.handleDoubleTapEvent(upEvent, 0.0f))
  }

  @Test
  fun verifyFlingListener() {
    val listener: OnFlingListener = mockk(relaxed = true)
    presenter.addOnFlingListener(listener)
    presenter.handleFlingEvent(mockk(), mockk(), 0.0f, 0.0f)
    verify { listener.onFling() }
    presenter.removeOnFlingListener(listener)
    presenter.handleFlingEvent(mockk(), mockk(), 0.0f, 0.0f)
    verify(exactly = 1) { listener.onFling() }
  }

  @Test
  fun verifyFlingIgnoreSmallDisplacement() {
    val result = presenter.handleFlingEvent(mockk(), mockk(), 0.1f, 0.1f)
    assertFalse(result)
  }

  @Test
  fun verifyFlingIgnoreConfiguration() {
    val listener: OnFlingListener = mockk(relaxed = true)
    presenter.addOnFlingListener(listener)
    presenter.scrollEnabled = false
    val result = presenter.handleFlingEvent(mockk(), mockk(), 10000f, 10000f)
    assertFalse(result)
    verify(exactly = 0) { listener.onFling() }
  }

  @Test
  fun verifyFling() {
    every { mapTransformDelegate.getPitch() } returns 0.0
    every { mapTransformDelegate.getDragCameraOptions(any(), any()) } returns CameraOptions.Builder().build()
    val result = presenter.handleFlingEvent(mockk(), mockk(), 10000f, 10000f)
    verify {
      mapTransformDelegate.getDragCameraOptions(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(666.6666666666667, 666.6666666666667)
      )
    }
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
    assert(result)
  }

  @Test
  fun verifyMoveStartDisabled() {
    val listener: OnMoveListener = mockk(relaxed = true)
    presenter.addOnMoveListener(listener)
    presenter.scrollEnabled = false
    val handled = presenter.handleMoveStartEvent(mockk())
    assertFalse(handled)
    verify(exactly = 0) { listener.onMoveBegin(any()) }
  }

  @Test
  fun verifyMoveStartListener() {
    val listener: OnMoveListener = mockk(relaxed = true)
    presenter.addOnMoveListener(listener)
    every { moveGestureDetector.isInProgress } returns false
    every { scaleGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { shoveGestureDetector.isInProgress } returns false
    val handled = presenter.handleMoveStartEvent(mockk())
    assert(handled)
    verify(exactly = 1) { listener.onMoveBegin(any()) }
    presenter.removeOnMoveListener(listener)
    presenter.handleMoveStartEvent(mockk())
    verify(exactly = 1) { listener.onMoveBegin(any()) }
  }

  @Test
  fun verifyMoveListener() {
    val listener: OnMoveListener = mockk(relaxed = true)
    presenter.addOnMoveListener(listener)
    every { mapTransformDelegate.getCameraOptions(any()) } returns CameraOptions.Builder().build()
    every { mapTransformDelegate.getDragCameraOptions(any(), any()) } returns CameraOptions.Builder().build()
    val handled = presenter.handleMove(mockk(), 50.0f, 50.0f)
    assert(handled)
    verify { listener.onMove(any()) }
    verify {
      mapTransformDelegate.getDragCameraOptions(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(-50.0, -50.0)
      )
    }
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
  }

  @Test
  fun verifyMoveEndListener() {
    val listener: OnMoveListener = mockk(relaxed = true)
    presenter.addOnMoveListener(listener)
    presenter.handleMoveEnd(mockk())
    verify { listener.onMoveEnd(any()) }
  }

  @Test
  fun verifyScaleDisabled() {
    presenter.zoomEnabled = false

    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 1
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { mapTransformDelegate.getZoom() } returns 1.0

    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
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
    every { mapTransformDelegate.getZoom() } returns 1.0
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    val result = presenter.handleScaleBegin(scaleDetector)
    assert(result)
    verify { listener.onScaleBegin(any()) }
    presenter.removeOnScaleListener(listener)
    presenter.handleScaleBegin(scaleDetector)
    verify(exactly = 1) { listener.onScaleBegin(any()) }
  }

  @Test
  fun verifyScaleBeginIgnoreSpeed() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.pointersCount } returns 2
    every { scaleDetector.currentSpan } returns 80.0f
    every { scaleDetector.previousSpan } returns 5000.0f
    every { rotateGestureDetector.deltaSinceLast } returns 50.0f
    every { mapTransformDelegate.getZoom() } returns 1.0
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.previousEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
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
    every { mapTransformDelegate.getZoom() } returns 1.0
    every { scaleGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { rotateGestureDetector.isInProgress } returns false
    every { scaleDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { scaleDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    val result = presenter.handleScaleBegin(scaleDetector)
    assertFalse(result)
  }

  @Test
  fun verifyScale() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapTransformDelegate.getZoom() } returns 1.0

    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    val result = presenter.handleScale(scaleDetector)
    assert(result)
    // setMoveDetectorEnabled
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
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
    every { mapTransformDelegate.getZoom() } returns 1.0
    presenter.handleScaleBegin(scaleDetector)

    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapTransformDelegate.getZoom() } returns 1.0

    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    val result = presenter.handleScale(scaleDetector)
    assert(result)
    // setMoveDetectorEnabled
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
  }

  @Test
  fun verifyScaleEnd() {
    val scaleDetector = mockk<StandardScaleGestureDetector>()
    every { scaleDetector.isScalingOut } returns true
    every { scaleDetector.currentSpan } returns 100.0f
    every { scaleDetector.previousSpan } returns 80.0f
    every { scaleDetector.focalPoint } returns PointF(1.0f, 1.0f)
    every { scaleDetector.scaleFactor } returns 2.0f
    every { mapTransformDelegate.getZoom() } returns 1.0

    val listener: OnScaleListener = mockk(relaxed = true)
    presenter.addOnScaleListener(listener)
    presenter.handleScaleEnd(scaleDetector, 15.0f, 15.0f)
    // todo animator testing
  }

  @Test
  fun verifyRotateDisabled() {
    presenter.rotateEnabled = false
    val listener: OnRotateListener = mockk(relaxed = true)
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    // every { rotateGestureDetector.cursetMoveDetectorEnabledrentEvent } returns obtainMotionEventActionLater(MotionEvent.ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    presenter.addOnRotateListener(listener)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateIgnoreSameTime() {
    val listener: OnRotateListener = mockk(relaxed = true)
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    presenter.addOnRotateListener(listener)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateIgnoreTooSlow() {
    val listener: OnRotateListener = mockk(relaxed = true)
    val rotateGestureDetector = mockk<RotateGestureDetector>(relaxUnitFun = true)
    every { rotateGestureDetector.deltaSinceLast } returns 0.0f
    every { rotateGestureDetector.deltaSinceStart } returns 0.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    presenter.addOnRotateListener(listener)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotateBegin() {
    val listener: OnRotateListener = mockk(relaxed = true)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { rotateGestureDetector.deltaSinceStart } returns 10000.0f
    every { rotateGestureDetector.currentEvent } returns obtainMotionEventActionLater(ACTION_MOVE)
    every { rotateGestureDetector.previousEvent } returns obtainMotionEventAction(ACTION_MOVE)
    every { rotateGestureDetector.isInProgress } returns true
    every { moveGestureDetector.isInProgress } returns false
    every { moveGestureDetector.isInProgress } returns false
    every { scaleGestureDetector.isInProgress } returns false
    presenter.addOnRotateListener(listener)
    val result = presenter.handleRotateBegin(rotateGestureDetector)
    assert(result)
    verify(exactly = 1) { listener.onRotateBegin(any()) }
    presenter.removeOnRotateListener(listener)
    presenter.handleRotateBegin(rotateGestureDetector)
    verify(exactly = 1) { listener.onRotateBegin(any()) }
  }

  @Test
  fun verifyRotate() {
    val rotateGestureDetector = mockk<RotateGestureDetector>()
    val listener: OnRotateListener = mockk(relaxed = true)
    presenter.addOnRotateListener(listener)
    every { mapTransformDelegate.getBearing() } returns 0.0
    every { rotateGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    val result = presenter.handleRotate(rotateGestureDetector, 34.0f)
    assert(result)
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
  }

  @Test
  fun verifyRotateEnd() {
    val rotateGestureDetector = mockk<RotateGestureDetector>()
    every { rotateGestureDetector.focalPoint } returns PointF(0.0f, 0.0f)
    every { rotateGestureDetector.deltaSinceLast } returns 500.0f
    every { mapTransformDelegate.getBearing() } returns 0.0
    every { rotateGestureDetector.isInProgress } returns true
    every { scaleGestureDetector.isInProgress } returns false
    val listener: OnRotateListener = mockk(relaxed = true)
    presenter.addOnRotateListener(listener)
    presenter.handleRotateEnd(rotateGestureDetector, 15.0f, 15.0f, 15.0f)
    // todo animator testing
  }

  @Test
  fun verifyShoveDisabled() {
    presenter.pitchEnabled = false
    val listener: OnShoveListener = mockk(relaxed = true)
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    presenter.addOnShoveListener(listener)
    val result = presenter.handleShoveBegin(gestureDetector)
    assertFalse(result)
    verify(exactly = 0) { listener.onShoveBegin(any()) }
  }

  @Test
  fun verifyShoveBegin() {
    val listener: OnShoveListener = mockk(relaxed = true)
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    presenter.addOnShoveListener(listener)
    every { moveGestureDetector.isInProgress } returns true
    val result = presenter.handleShoveBegin(gestureDetector)
    assert(result)
    verify(exactly = 1) { listener.onShoveBegin(any()) }
    presenter.removeOnShoveListener(listener)
    presenter.handleShoveBegin(gestureDetector)
    verify(exactly = 1) { listener.onShoveBegin(any()) }
  }

  @Test
  fun verifyShove() {
    every { mapTransformDelegate.getPitch() } returns 5.0
    val listener: OnShoveListener = mockk(relaxed = true)
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    presenter.addOnShoveListener(listener)
    val result = presenter.handleShove(gestureDetector, 15.0f)
    assert(result)
    verify { cameraAnimationsPlugin.easeTo(any(), any()) }
  }

  @Test
  fun verifyShoveEnd() {
    every { mapTransformDelegate.getPitch() } returns 5.0
    val listener: OnShoveListener = mockk(relaxed = true)
    val gestureDetector = mockk<ShoveGestureDetector>(relaxUnitFun = true)
    presenter.addOnShoveListener(listener)
    presenter.handleShoveEnd(gestureDetector)
    verify { listener.onShoveEnd(any()) }
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
    every { mapTransformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().zoom(1.0).build()
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    presenter.addProtectedAnimationOwner("Owner")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify { cameraAnimationsPlugin.cancelAllAnimators(listOf("Owner")) }
  }

  @Test
  fun verifyRemoveProtectedAnimationOwner() {
    every { mapTransformDelegate.getCameraOptions(null) } returns CameraOptions.Builder().zoom(1.0).build()
    every { cameraAnimationsPlugin.calculateScaleBy(any(), any()) } returns 2.0
    presenter.addProtectedAnimationOwner("OwnerOne")
    presenter.addProtectedAnimationOwner("OwnerTwo")
    presenter.removeProtectedAnimationOwner("OwnerOne")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify { cameraAnimationsPlugin.cancelAllAnimators(listOf("OwnerTwo")) }
    presenter.removeProtectedAnimationOwner("OwnerTwo")
    assert(presenter.onGenericMotionEvent(obtainMotionEventButton(BUTTON_SECONDARY)))
    verify { cameraAnimationsPlugin.cancelAllAnimators() }
  }

  fun obtainMotionEventButton(buttonType: Int): MotionEvent {
    return MotionEvent.obtain(
      200,
      200,
      MotionEvent.ACTION_SCROLL,
      1,
      arrayOf(PointerPropertiesBuilder.newBuilder().setId(0).setToolType(TOOL_TYPE_FINGER).build()),
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
    return MotionEvent.obtain(200, 300, action, 15.0f, 10.0f, 0)
  }

  fun obtainMotionEventActionDistant(action: Int): MotionEvent {
    return MotionEvent.obtain(200, 300, action, 500.0f, 500.0f, 0)
  }

  fun obtainMotionEventActionLater(action: Int): MotionEvent {
    return MotionEvent.obtain(200, 500, action, 15.0f, 10.0f, 0)
  }
}

private fun MapTransformDelegate.getZoom(): Double {
  return getCameraOptions(null).zoom!!
}

private fun MapTransformDelegate.getPitch(): Double {
  return getCameraOptions(null).pitch!!
}

private fun MapTransformDelegate.getBearing(): Double {
  return getCameraOptions(null).bearing!!
}