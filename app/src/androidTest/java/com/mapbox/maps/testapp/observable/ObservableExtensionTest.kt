package com.mapbox.maps.testapp.observable

import android.graphics.BitmapFactory
import androidx.annotation.UiThread
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.getResourceEventData
import com.mapbox.maps.extension.observable.model.MapLoadErrorType
import com.mapbox.maps.extension.observable.subscribeResourceRequest
import com.mapbox.maps.extension.observable.unsubscribeResourceRequest
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.delegates.listeners.*
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
class ObservableExtensionTest : BaseMapTest() {

  private val targetCameraOptions =
    CameraOptions.Builder().center(Point.fromLngLat(0.0, 0.0)).zoom(16.0).build()

  @Before
  fun setUp() {
    super.before()
  }

  override fun loadMap() {
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap = mapView.getMapboxMap()
      }
    }
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
  @UiThread
  fun subscribeResourceRequest() {
    val latch = CountDownLatch(1)

    val observer = Observer { event ->
      assertEquals("resource-request", event.type)
      assertNotNull(event.getResourceEventData())
      assertNotNull(event.getResourceEventData().begin)
      latch.countDown()
    }
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.subscribeResourceRequest(observer)
        mapboxMap.setCamera(targetCameraOptions)
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.unsubscribeResourceRequest(observer)
  }

  @Test
  @UiThread
  fun subscribeMapLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnMapLoadedListener {
      assertNotNull(it.begin)
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
    mapboxMap.removeOnMapLoadedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeMapLoadingErrorEvent() {
    val latch = CountDownLatch(1)

    val listener = object : OnMapLoadErrorListener {
      override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
        assertNotNull(eventData.type)
        assertNotNull(eventData.message)
        assertNotNull(eventData.begin)
        assertEquals(MapLoadErrorType.STYLE, eventData.type)
        assertEquals(
          "Failed to load style: Unable to resolve host \"wrongurl\": No address associated with hostname",
          eventData.message
        )
        latch.countDown()
      }
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
    mapboxMap.removeOnMapLoadErrorListener(listener)
  }

  @Test
  @UiThread
  fun subscribeMapIdleEvent() {
    val latch = CountDownLatch(1)

    val listener = OnMapIdleListener {
      assertNotNull(it.begin)
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
    mapboxMap.removeOnMapIdleListener(listener)
  }

  @Test
  @UiThread
  fun subscribeStyleDataLoadedEvent() {
    val latch = CountDownLatch(3)

    val listener = OnStyleDataLoadedListener {
      assertNotNull(it.type)
      assertNotNull(it.begin)
      latch.countDown()
    }

    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnStyleDataLoadedListener(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.removeOnStyleDataLoadedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeStyleLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnStyleLoadedListener {
      assertNotNull(it.begin)
      latch.countDown()
    }

    rule.scenario.onActivity {
      it.runOnUiThread {
        mapboxMap.apply {
          addOnStyleLoadedListener(listener)
          setCamera(targetCameraOptions)
        }
      }
    }
    loadTestStyle()
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.removeOnStyleLoadedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeStyleImageMissingEvent() {
    val latch = CountDownLatch(1)

    val listener = OnStyleImageMissingListener {
      assertNotNull(it.begin)
      assertEquals(IMAGE_ID, it.id)
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
    mapboxMap.removeOnStyleImageMissingListener(listener)
  }

  @Test
  @UiThread
  fun subscribeStyleImageUnusedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnStyleImageUnusedListener {
      assertNotNull(it.begin)
      assertEquals(IMAGE_ID, it.id)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          setCamera(targetCameraOptions)
          printAllEventsForDebug()
          addOnStyleImageMissingListener {
            getStyle {
              it.addImage(IMAGE_ID, Image(2048, 2048, ByteArray(2048 * 2048 * 4)))
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
    mapboxMap.removeOnStyleImageUnusedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeSourceDataLoadedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnSourceDataLoadedListener { eventData ->
      assertNotNull(eventData.id)
      assertNotNull(eventData.type)
      assertNotNull(eventData.begin)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          addOnSourceDataLoadedListener(listener)
          printAllEventsForDebug()
          setCamera(targetCameraOptions)
          loadTestStyle()
        }
      }
    }
    if (!latch.await(20000, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
    mapboxMap.removeOnSourceDataLoadedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeSourceAddedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnSourceAddedListener {
      assertNotNull(it.begin)
      assertEquals(SOURCE_ID, it.id)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          addOnSourceAddedListener(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(IMAGE_ID) {
                bitmap(
                  BitmapFactory.decodeResource(
                    activity.resources,
                    R.drawable.blue_marker_view
                  )
                )
              }
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
    mapboxMap.removeOnSourceAddedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeSourceRemovedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnSourceRemovedListener {
      assertNotNull(it.begin)
      assertEquals(SOURCE_ID, it.id)
      latch.countDown()
    }

    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.apply {
          addOnSourceRemovedListener(listener)
          setCamera(targetCameraOptions)
          loadStyle(
            style(Style.MAPBOX_STREETS) {
              +image(IMAGE_ID) {
                bitmap(
                  BitmapFactory.decodeResource(
                    activity.resources,
                    R.drawable.blue_marker_view
                  )
                )
              }
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
    mapboxMap.removeOnSourceRemovedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeRenderFrameStartedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnRenderFrameStartedListener {
      assertNotNull(it.begin)
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
    mapboxMap.removeOnRenderFrameStartedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeRenderFrameFinishedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnRenderFrameFinishedListener { eventData ->
      assertNotNull(eventData.begin)
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
    mapboxMap.removeOnRenderFrameFinishedListener(listener)
  }

  @Test
  @UiThread
  fun subscribeCameraChangedEvent() {
    val latch = CountDownLatch(1)

    val listener = OnCameraChangeListener {
      assertNotNull(it.begin)
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
    mapboxMap.removeOnCameraChangeListener(listener)
  }

  private fun printAllEventsForDebug() {
    rule.scenario.onActivity { activity ->
      activity.runOnUiThread {
        mapboxMap.subscribe(
          {
            Logger.e(TAG, it.toString())
          },
          SUPPORTED_EVENTS
        )
      }
    }
  }

  companion object {
    private const val TAG = "ObservableExtensionTest"
    private const val LAYER_ID = "layer_id"
    private const val SOURCE_ID = "source_id"
    private const val IMAGE_ID = "image_id"
    private val SUPPORTED_EVENTS = listOf(
      // Camera events
      MapEvents.CAMERA_CHANGED,
      // Map events
      MapEvents.MAP_IDLE,
      MapEvents.MAP_LOADING_ERROR,
      MapEvents.MAP_LOADED,
      // Style events
      MapEvents.STYLE_DATA_LOADED,
      MapEvents.STYLE_LOADED,
      MapEvents.STYLE_IMAGE_MISSING,
      MapEvents.STYLE_IMAGE_REMOVE_UNUSED,
      // Render frame events
      MapEvents.RENDER_FRAME_STARTED,
      MapEvents.RENDER_FRAME_FINISHED,
      // Source events
      MapEvents.SOURCE_ADDED,
      MapEvents.SOURCE_DATA_LOADED,
      MapEvents.SOURCE_REMOVED
    )
  }
}