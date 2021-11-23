package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.children
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.test.R
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewAnnotationTest {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var mainHandler: Handler

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  fun before() {
    val latch = CountDownLatch(2)
    rule.scenario.onActivity {
      it.runOnUiThread {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mapView = MapView(context)
        mapView.id = R.id.mapView
        it.setContentView(mapView)

        mainHandler = Handler(Looper.getMainLooper())
        viewAnnotationManager = mapView.viewAnnotationManager
        mapboxMap = mapView.getMapboxMap().apply {
          loadStyleUri(
            Style.MAPBOX_STREETS
          ) {
            latch.countDown()
            addOnMapIdleListener {
              latch.countDown()
            }
          }
          setCamera(
            CameraOptions.Builder()
              .center(CAMERA_CENTER)
              .zoom(CAMERA_ZOOM)
              .build()
          )
        }
        mapView.onStart()
      }
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @After
  fun tearDown() {
    val latch = CountDownLatch(1)
    rule.scenario.onActivity {
      it.runOnUiThread {
        mapView.onStop()
        mapView.onDestroy()
        mainHandler.removeCallbacksAndMessages(null)
        latch.countDown()
      }
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  // checking some use-cases when adding view annotation

  @Test
  fun addViewAnnotationNoOptions() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(view))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x - view.width / 2.0, view.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - view.height / 2.0, view.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addViewAnnotationAnchor() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(view))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, view.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, view.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addViewAnnotationOffsets() {
    val latch = CountDownLatch(1)
    val offsetX = 30
    val offsetY = 20
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          offsetX(offsetX)
          offsetY(offsetY)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(view))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x + offsetX, view.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - offsetY, view.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addViewAnnotationVisible() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          visible(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(view))
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapTrue() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(true)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(true)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(firstView))
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x, firstView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y, firstView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          assertTrue(mapView.hasChildView(secondView))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          MatcherAssert.assertThat(
            mapView.getChildViewIndex(secondView),
            Matchers.greaterThan(mapView.getChildViewIndex(firstView))
          )
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapFalse() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(firstView))
          assertTrue(mapView.hasChildView(secondView))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapTrue() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(true)
          selected(true)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(true)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(firstView))
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x, firstView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y, firstView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          assertTrue(mapView.hasChildView(secondView))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          MatcherAssert.assertThat(
            mapView.getChildViewIndex(secondView),
            Matchers.lessThan(mapView.getChildViewIndex(firstView))
          )
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapFalse() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
          selected(true)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(firstView))
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x, firstView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y, firstView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          assertFalse(mapView.hasChildView(secondView))
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun addTwoViewAnnotationsTwoSelectedAllowOverlapFalse() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
          selected(true)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          anchor(ViewAnnotationAnchor.TOP_LEFT)
          allowOverlap(false)
          selected(true)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(firstView))
          assertTrue(mapView.hasChildView(secondView))
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  // checking some use-cases when updating view annotations

  @Test
  fun updateViewAnnotation() {
    val latch = CountDownLatch(1)
    val offsetY = 30
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
        }
      )
      viewAnnotationManager.updateViewAnnotation(
        view = view,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          offsetY(offsetY)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(view))
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x - view.width / 2.0, view.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y - view.height / 2.0 - offsetY, view.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun updateViewAnnotationDimensions() {
    val latch = CountDownLatch(1)
    val updatedWidthPx = 120
    val updatedHeightPx = 150
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
        }
      )
      viewAnnotationManager.updateViewAnnotation(
        view = view,
        options = viewAnnotationOptions {
          width(updatedWidthPx)
          height(updatedHeightPx)
        }
      )
      mainHandler.postDelayed(
        {
          assertTrue(mapView.hasChildView(view))
          assertEquals(updatedWidthPx, view.width)
          assertEquals(updatedHeightPx, view.height)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x - view.width / 2.0, view.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
          assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - view.height / 2.0, view.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun updateViewAnnotationAllowOverlap() {
    val latch = CountDownLatch(2)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          allowOverlap(false)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          allowOverlap(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(firstView))
          assertTrue(mapView.hasChildView(secondView))
          latch.countDown()
          // update allowOverlap only for underlying view annotation
          viewAnnotationManager.updateViewAnnotation(
            firstView,
            viewAnnotationOptions {
              allowOverlap(true)
            }
          )
          mainHandler.postDelayed(
            {
              assertTrue(mapView.hasChildView(firstView))
              assertTrue(mapView.hasChildView(secondView))
              latch.countDown()
            },
            VIEW_PLACEMENT_DELAY_MS
          )
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  // checking some use-cases when deleting view annotations

  @Test
  fun removeViewAnnotation() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      val view = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
        }
      )
      viewAnnotationManager.removeViewAnnotation(view)
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(view))
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun removeViewAnnotationWhenTwoOverlap() {
    val latch = CountDownLatch(2)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          allowOverlap(false)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          allowOverlap(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(firstView))
          assertTrue(mapView.hasChildView(secondView))
          latch.countDown()
          viewAnnotationManager.removeViewAnnotation(secondView)
          mainHandler.postDelayed(
            {
              assertTrue(mapView.hasChildView(firstView))
              assertFalse(mapView.hasChildView(secondView))
              latch.countDown()
            },
            VIEW_PLACEMENT_DELAY_MS
          )
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  // checking some use-cases when using associatedFeatureId

  @Test
  fun associatedFeatureIdWhenFeatureVisible() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      mapboxMap.getStyle {
        prepareStyle(it, Visibility.VISIBLE)
        val view = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.BOTTOM)
            offsetY(50)
            visible(true)
            associatedFeatureId(ASSOCIATED_FEATURE_ID)
          }
        )
        mainHandler.postDelayed(
          {
            assertTrue(mapView.hasChildView(view))
            latch.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  @Test
  fun associatedFeatureIdWhenFeatureGone() {
    val latch = CountDownLatch(1)
    mainHandler.post {
      mapboxMap.getStyle {
        prepareStyle(it, Visibility.NONE)
        val view = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.BOTTOM)
            offsetY(50)
            visible(true)
            associatedFeatureId(ASSOCIATED_FEATURE_ID)
          }
        )
        mainHandler.postDelayed(
          {
            assertFalse(mapView.hasChildView(view))
            latch.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  // checking automatic view visibility handling

  @Test
  fun automaticViewVisibilityHandling() {
    val latch = CountDownLatch(2)
    mainHandler.post {
      val firstView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(CAMERA_CENTER)
          allowOverlap(false)
        }
      )
      val secondView = viewAnnotationManager.addViewAnnotation(
        resId = R.layout.view_annotation,
        options = viewAnnotationOptions {
          geometry(SHIFTED_CENTER)
          allowOverlap(false)
        }
      )
      mainHandler.postDelayed(
        {
          assertFalse(mapView.hasChildView(firstView))
          assertTrue(mapView.hasChildView(secondView))
          latch.countDown()
          secondView.visibility = View.GONE
          mainHandler.postDelayed(
            {
              // second view is not removed from parent MapView but as it's gone first view should appear now
              assertTrue(mapView.hasChildView(secondView))
              assertTrue(mapView.hasChildView(firstView))
              latch.countDown()
            },
            VIEW_PLACEMENT_DELAY_MS
          )
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    if (!latch.await(CONFIGURE_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      throw TimeoutException()
    }
  }

  private fun MapView.hasChildView(view: View): Boolean {
    children.forEach { v ->
      if (v == view) {
        return true
      }
    }
    return false
  }

  private fun MapView.getChildViewIndex(view: View): Int {
    children.forEachIndexed { i, v ->
      if (v == view) {
        return i
      }
    }
    return -1
  }

  private fun prepareStyle(style: Style, visibility: Visibility) {
    style.addSource(
      geoJsonSource("source") {
        feature(Feature.fromGeometry(CAMERA_CENTER, null, ASSOCIATED_FEATURE_ID))
      }
    )
    style.addLayer(
      symbolLayer("layer", "source") {
        textField(ASSOCIATED_FEATURE_ID)
        visibility(visibility)
      }
    )
  }

  private companion object {
    const val CONFIGURE_TIMEOUT_MS = 5000L
    const val VIEW_PLACEMENT_DELAY_MS = 1000L
    const val ADMISSIBLE_ERROR_PX = 3.0

    const val ASSOCIATED_FEATURE_ID = "featureTestId"
    const val CAMERA_ZOOM = 10.0
    val CAMERA_CENTER: Point = Point.fromLngLat(0.0, 0.0)
    val SHIFTED_CENTER: Point = Point.fromLngLat(CAMERA_CENTER.longitude() - 0.01, CAMERA_CENTER.latitude() - 0.01)
  }
}