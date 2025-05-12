package com.mapbox.maps.testapp.observable

import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.bindgen.DataRef
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.BaseMapTest
import com.mapbox.maps.testapp.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ObservableEventsTest : BaseMapTest() {

  private val targetCameraOptions =
    CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).zoom(16.0).build()

  private lateinit var cancelable: Cancelable

  @Before
  fun setUp() {
    super.before()
  }

  private fun loadTestStyle(styleUri: String = Style.MAPBOX_STREETS) {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.loadStyle(styleUri) { style ->
          this.style = style
        }
        mapView.onStart()
      }
    }
  }

  @Test
  fun subscribeResourceRequest() {
    val latch = CountDownLatch(1)

    val resourceRequestCallback = ResourceRequestCallback { event ->
      assertNotNull(event.request)
      assertNotNull(event.timeInterval.begin)
      assertNotNull(event.timeInterval.end)
      cancelable.cancel()
      latch.countDown()
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        cancelable = mapboxMap.subscribeResourceRequest(resourceRequestCallback)
        mapboxMap.setCamera(targetCameraOptions)
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeMapLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = MapLoadedCallback {
      assertNotNull(it.timeInterval.begin)
      assertNotNull(it.timeInterval.end)
      latch.countDown()
    }

    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeMapLoaded(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeMapLoadingErrorEvent() {
    val latch = CountDownLatch(1)

    val listener = MapLoadingErrorCallback { eventData ->
      assertNotNull(eventData.type)
      assertNotNull(eventData.message)
      assertNotNull(eventData.timestamp.time)
      assertEquals(MapLoadingErrorType.STYLE, eventData.type)
      assertEquals(
        "Failed to load style 'https://wrongurl': Couldn't connect to server: Exception in CronetUrlRequest: net::ERR_NAME_NOT_RESOLVED, ErrorCode=1, InternalErrorCode=-105, Retryable=false",
        eventData.message
      )
      latch.countDown()
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeMapLoadingError(listener)
        }
      }
    }
    loadTestStyle("https://wrongurl")
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeMapIdleEvent() {
    val latch = CountDownLatch(1)

    val listener = MapIdleCallback {
      assertNotNull(it.timestamp.time)
      latch.countDown()
    }

    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeMapIdle(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeStyleDataLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = StyleDataLoadedCallback {
      assertNotNull(it.type)
      assertNotNull(it.timeInterval.begin)
      assertNotNull(it.timeInterval.end)
      latch.countDown()
    }

    loadTestStyle()
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeStyleDataLoaded(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeStyleLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = StyleLoadedCallback {
      assertNotNull(it.timeInterval.begin)
      assertNotNull(it.timeInterval.end)
      latch.countDown()
    }

    loadTestStyle()
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeStyleLoaded(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeStyleImageMissingEvent() {
    val latch = CountDownLatch(1)

    val listener = StyleImageMissingCallback {
      assertNotNull(it.timestamp.time)
      assertEquals(IMAGE_ID, it.imageId)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeStyleImageMissing(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +geoJsonSource(SOURCE_ID) {
                geometry(Point.fromLngLat(0.0, 0.0))
              }
              +symbolLayer(LAYER_ID, SOURCE_ID) {
                iconImage(IMAGE_ID)
              }
            }
          )
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeStyleImageRemoveUnusedEvent() {
    val latch = CountDownLatch(1)

    val listener = StyleImageRemoveUnusedCallback {
      assertNotNull(it.timestamp.time)
      assertEquals(IMAGE_ID, it.imageId)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          setCamera(targetCameraOptions)
          cancelable = subscribeStyleImageMissing {
            getStyle {
              it.addImage(IMAGE_ID, Image(2048, 2048, DataRef.allocateNative(2048 * 2048 * 4)))
              setCamera(cameraOptions { center(Point.fromLngLat(60.1733244, 24.9410248)) })
            }
          }
          subscribeStyleImageRemoveUnused(listener)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +geoJsonSource(SOURCE_ID) {
                geometry(Point.fromLngLat(0.0, 0.0))
              }
              +symbolLayer(LAYER_ID, SOURCE_ID) {
                iconImage(IMAGE_ID)
              }
            }
          )
        }
      }
    }

    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeSourceDataLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = SourceDataLoadedCallback { eventData ->
      assertNotNull(eventData.sourceId)
      assertNotNull(eventData.type)
      assertNotNull(eventData.timeInterval.begin)
      assertNotNull(eventData.timeInterval.end)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeSourceDataLoaded(listener)
          setCamera(targetCameraOptions)
          loadTestStyle()
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeSourceAddedEvent() {
    val latch = CountDownLatch(1)

    val listener = SourceAddedCallback {
      assertNotNull(it.timestamp.time)
      assertEquals(SOURCE_ID, it.sourceId)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeSourceAdded(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(
                IMAGE_ID,
                ContextCompat.getDrawable(
                  activity,
                  R.drawable.ic_blue_marker
                )!!.toBitmap()
              )
              +geoJsonSource(SOURCE_ID) {
                geometry(Point.fromLngLat(0.0, 0.0))
              }
              +symbolLayer(LAYER_ID, SOURCE_ID) {
                iconImage(IMAGE_ID)
              }
            }
          )
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  fun subscribeSourceRemovedEvent() {
    val latch = CountDownLatch(1)

    val listener = SourceRemovedCallback {
      assertNotNull(it.timestamp.time)
      assertEquals(SOURCE_ID, it.sourceId)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeSourceRemoved(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(
                IMAGE_ID,
                ContextCompat.getDrawable(
                  activity,
                  R.drawable.ic_blue_marker
                )!!.toBitmap()
              )
              +geoJsonSource(SOURCE_ID) {
                geometry(Point.fromLngLat(0.0, 0.0))
              }
              +symbolLayer(LAYER_ID, SOURCE_ID) {
                iconImage(IMAGE_ID)
              }
            }
          )
          subscribeMapIdle {
            getStyle { style ->
              style.removeStyleLayer(LAYER_ID)
              style.removeStyleSource(SOURCE_ID)
            }
          }
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  @UiThread
  fun subscribeRenderFrameStartedEvent() {
    val latch = CountDownLatch(1)

    val listener = RenderFrameStartedCallback {
      assertNotNull(it.timestamp.time)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeRenderFrameStarted(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @Test
  @UiThread
  fun subscribeRenderFrameFinishedEvent() {
    val latch = CountDownLatch(1)

    val listener = RenderFrameFinishedCallback { eventData ->
      assertNotNull(eventData.timeInterval.begin)
      assertNotNull(eventData.timeInterval.end)
      assertNotNull(eventData.renderMode)
      assertNotNull(eventData.needsRepaint)
      assertNotNull(eventData.placementChanged)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          cancelable = subscribeRenderFrameFinished(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        cancelable.cancel()
      }
    }
  }

  @OptIn(MapboxExperimental::class)
  @Test
  @UiThread
  fun subscribeCameraChangedEvent() {
    var latch = CountDownLatch(2)
    val targetCameraOptions2 = cameraOptions {
        center(Point.fromLngLat(1.0, 2.0))
        zoom(16.0)
        bearing(18.0)
        pitch(85.0)
        anchor(ScreenCoordinate(1.0, 2.0))
        padding(EdgeInsets(1.0, 2.0, 3.0, 4.0))
      }

    val cameraChangedResults = mutableListOf<CameraChanged>()
    val listener = CameraChangedCallback {
      cameraChangedResults.add(it)
      latch.countDown()
    }

    val coalescedLatch = CountDownLatch(1)
    val cameraChangedCoalescedResults = mutableListOf<CameraChangedCoalesced>()
    val listenerCoalesced = CameraChangedCoalescedCallback {
      cameraChangedCoalescedResults.add(it)
      coalescedLatch.countDown()
    }

    var cancelableCoalesced: Cancelable? = null

    try {
      rule.scenario.onActivity { activity ->
        activity.runOnUiThread {
          mapboxMap.apply {
            cancelable = subscribeCameraChanged(listener)
            cancelableCoalesced = subscribeCameraChangedCoalesced(listenerCoalesced)
            setCamera(targetCameraOptions)
            setCamera(targetCameraOptions2)
          }
        }
      }
      loadTestStyle()

      // For normal camera changed events we should get 2 events (same as `setCamera` calls)
      assertTrue(latch.await(20_000, TimeUnit.MILLISECONDS))
      assertEquals(2, cameraChangedResults.size)
      assertNotNull(cameraChangedResults[0].timestamp.time)
      with(cameraChangedResults[0].cameraState) {
        assertEquals(targetCameraOptions.center!!.longitude(), center.longitude(), EPS)
        assertEquals(targetCameraOptions.center!!.latitude(), center.latitude(), EPS)
        assertEquals(targetCameraOptions.zoom, zoom)
      }
      with(cameraChangedResults[1].cameraState) {
        assertEquals(targetCameraOptions2.center!!.longitude(), center.longitude(), EPS)
        assertEquals(targetCameraOptions2.center!!.latitude(), center.latitude(), EPS)
        assertEquals(targetCameraOptions2.zoom, zoom)
        assertEquals(targetCameraOptions2.bearing, bearing)
        assertEquals(targetCameraOptions2.pitch, pitch)
        assertEquals(targetCameraOptions2.anchor, ScreenCoordinate(1.0, 2.0))
        assertEquals(targetCameraOptions2.padding, EdgeInsets(1.0, 2.0, 3.0, 4.0))
      }

      // We should only get one coalesced event instead of 2 like the normal camera changed event
      assertEquals(1, cameraChangedCoalescedResults.size)
      assertNotNull(cameraChangedCoalescedResults[0].timestamp.time)
      with(cameraChangedCoalescedResults[0].cameraState) {
        assertEquals(targetCameraOptions2.center!!.longitude(), center.longitude(), EPS)
        assertEquals(targetCameraOptions2.center!!.latitude(), center.latitude(), EPS)
        assertEquals(targetCameraOptions2.zoom, zoom)
        assertEquals(targetCameraOptions2.bearing, bearing)
        assertEquals(targetCameraOptions2.pitch, pitch)
        assertEquals(targetCameraOptions2.anchor, ScreenCoordinate(1.0, 2.0))
        assertEquals(targetCameraOptions2.padding, EdgeInsets(1.0, 2.0, 3.0, 4.0))
      }

      latch = CountDownLatch(1)
      rule.scenario.onActivity { activity ->
        activity.runOnUiThread {
          mapboxMap.apply {
            setCamera(targetCameraOptions2)
          }
        }
      }
      assertTrue(latch.await(20_000, TimeUnit.MILLISECONDS))
      assertEquals(3, cameraChangedResults.size)
      assertNotNull(cameraChangedResults[2].timestamp.time)
      with(cameraChangedResults[2].cameraState) {
        assertEquals(targetCameraOptions2.center!!.longitude(), center.longitude(), EPS)
        assertEquals(targetCameraOptions2.center!!.latitude(), center.latitude(), EPS)
        assertEquals(targetCameraOptions2.zoom, zoom)
        assertEquals(targetCameraOptions2.bearing, bearing)
        assertEquals(targetCameraOptions2.pitch, pitch)
        assertEquals(targetCameraOptions2.anchor, ScreenCoordinate(1.0, 2.0))
        assertEquals(targetCameraOptions2.padding, EdgeInsets(1.0, 2.0, 3.0, 4.0))
      }

      // The camera changed coalesced event should not be called again because we pushed the same
      // camera than last time.
      assertEquals(1, cameraChangedCoalescedResults.size)
    } finally {
      rule.scenario.onActivity { activity ->
        activity.runOnUiThread {
          cancelable.cancel()
          cancelableCoalesced?.cancel()
        }
      }
    }
  }

  companion object {
    private const val EPS = 0.0001
    private const val LAYER_ID = "layer_id"
    private const val SOURCE_ID = "source_id"
    private const val IMAGE_ID = "image_id"
  }
}