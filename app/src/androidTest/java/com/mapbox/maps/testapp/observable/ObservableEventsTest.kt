package com.mapbox.maps.testapp.observable

import android.graphics.BitmapFactory
import androidx.annotation.UiThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
        mapboxMap.loadStyleUri(styleUri) { style ->
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
          addOnMapLoadedListener(listener)
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
        mapboxMap.removeOnMapLoadedListener(listener)
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
        "Failed to load style: Unable to resolve host \"wrongurl\": No address associated with hostname",
        eventData.message
      )
      latch.countDown()
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnMapLoadErrorListener(listener)
        }
      }
    }
    loadTestStyle("https://wrongurl")
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.removeOnMapLoadErrorListener(listener)
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
          addOnMapIdleListener(listener)
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
        mapboxMap.removeOnMapIdleListener(listener)
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
          addOnStyleDataLoadedListener(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.removeOnStyleDataLoadedListener(listener)
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
          addOnStyleLoadedListener(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.removeOnStyleLoadedListener(listener)
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
          addOnStyleImageMissingListener(listener)
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
        mapboxMap.removeOnStyleImageMissingListener(listener)
      }
    }
  }

  @Test
  fun subscribeStyleImageUnusedEvent() {
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
          addOnStyleImageMissingListener {
            getStyle {
              it.addImage(IMAGE_ID, Image(2048, 2048, DataRef.allocateNative(2048 * 2048 * 4)))
              setCamera(cameraOptions { center(Point.fromLngLat(60.1733244, 24.9410248)) })
            }
          }
          addOnStyleImageUnusedListener(listener)
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
        mapboxMap.removeOnStyleImageUnusedListener(listener)
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
          addOnSourceDataLoadedListener(listener)
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
        mapboxMap.removeOnSourceDataLoadedListener(listener)
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
          addOnSourceAddedListener(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(
                IMAGE_ID,
                BitmapFactory.decodeResource(
                  activity.resources,
                  R.drawable.blue_marker_view
                )
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
        mapboxMap.removeOnSourceAddedListener(listener)
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
          addOnSourceRemovedListener(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(
                IMAGE_ID,
                BitmapFactory.decodeResource(
                  activity.resources,
                  R.drawable.blue_marker_view
                )
              )
              +geoJsonSource(SOURCE_ID) {
                geometry(Point.fromLngLat(0.0, 0.0))
              }
              +symbolLayer(LAYER_ID, SOURCE_ID) {
                iconImage(IMAGE_ID)
              }
            }
          )
          addOnMapIdleListener {
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
        mapboxMap.removeOnSourceRemovedListener(listener)
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
          addOnRenderFrameStartedListener(listener)
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
        mapboxMap.removeOnRenderFrameStartedListener(listener)
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
          addOnRenderFrameFinishedListener(listener)
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
        mapboxMap.removeOnRenderFrameFinishedListener(listener)
      }
    }
  }

  @Test
  @UiThread
  fun subscribeCameraChangedEvent() {
    val latch = CountDownLatch(1)

    val listener = CameraChangedCallback {
      val actualCameraOption = it.cameraState.toCameraOptions()
      assertNotNull(it.timestamp.time)
      assertEquals(targetCameraOptions.center, actualCameraOption.center)
      assertEquals(targetCameraOptions.zoom, actualCameraOption.zoom)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          addOnCameraChangeListener(listener)
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
        mapboxMap.removeOnCameraChangeListener(listener)
      }
    }
  }

  companion object {
    private const val TAG = "ObservableExtensionTest"
    private const val LAYER_ID = "layer_id"
    private const val SOURCE_ID = "source_id"
    private const val IMAGE_ID = "image_id"
  }
}