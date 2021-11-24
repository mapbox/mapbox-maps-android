package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import android.view.View
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

@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewAnnotationTest {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private lateinit var mainHandler: Handler

  private lateinit var firstView: View
  private lateinit var secondView: View

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
          }
          setCamera(
            CameraOptions.Builder()
              .center(CAMERA_CENTER)
              .zoom(CAMERA_ZOOM)
              .build()
          )
          addOnMapIdleListener {
            latch.countDown()
          }
        }
        mapView.onStart()
      }
    }
    latch.throwExceptionOnTimeoutMs()
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
    latch.throwExceptionOnTimeoutMs()
  }

  /**
   * Helper function that performs some action and verifies result after some delay
   * when view annotations are fixed in the viewport
   */
  private fun viewAnnotationTestHelper(
    additionalLatchCount: Int = 0,
    performAction: () -> Unit,
    makeChecks: (CountDownLatch) -> Unit
  ) {
    val latch = CountDownLatch(1 + additionalLatchCount)
    mainHandler.post {
      performAction.invoke()
      mainHandler.postDelayed(
        {
          makeChecks.invoke(latch)
          latch.countDown()
        },
        VIEW_PLACEMENT_DELAY_MS
      )
    }
    latch.throwExceptionOnTimeoutMs()
  }

  // checking some use-cases when adding view annotation

  @Test
  fun addViewAnnotationNoOptions() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).x - firstView.width / 2.0,
          firstView.translationX.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - firstView.height / 2.0,
          firstView.translationY.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
      }
    )
  }

  @Test
  fun addViewAnnotationAnchor() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, firstView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, firstView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
      }
    )
  }

  @Test
  fun addViewAnnotationOffsets() {
    val offsetX = 30
    val offsetY = 20
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            offsetX(offsetX)
            offsetY(offsetY)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).x + offsetX,
          firstView.translationX.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - offsetY,
          firstView.translationY.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
      }
    )
  }

  @Test
  fun addViewAnnotationVisible() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            visible(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
      },
      makeChecks = {
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
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
      },
      makeChecks = {
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
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneAllowOverlapTrueAnotherFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
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
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneAllowOverlapFalseAnotherTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x, firstView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
        assertEquals(mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y, firstView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
        assertFalse(mapView.hasChildView(secondView))
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsTwoSelectedAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
            selected(true)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).x, secondView.translationX.toDouble(), ADMISSIBLE_ERROR_PX)
        assertEquals(mapboxMap.pixelForCoordinate(CAMERA_CENTER).y, secondView.translationY.toDouble(), ADMISSIBLE_ERROR_PX)
      }
    )
  }

  // checking some use-cases when updating view annotations

  @Test
  fun updateViewAnnotation() {
    val offsetY = 30
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
          }
        )
        viewAnnotationManager.updateViewAnnotation(
          view = firstView,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            offsetY(offsetY)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(
          mapboxMap.pixelForCoordinate(SHIFTED_CENTER).x - firstView.width / 2.0,
          firstView.translationX.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
        assertEquals(
          mapboxMap.pixelForCoordinate(SHIFTED_CENTER).y - firstView.height / 2.0 - offsetY,
          firstView.translationY.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
      }
    )
  }

  @Test
  fun updateViewAnnotationDimensions() {
    val updatedWidthPx = 120
    val updatedHeightPx = 150
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
          }
        )
        viewAnnotationManager.updateViewAnnotation(
          view = firstView,
          options = viewAnnotationOptions {
            width(updatedWidthPx)
            height(updatedHeightPx)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
        assertEquals(updatedWidthPx, firstView.width)
        assertEquals(updatedHeightPx, firstView.height)
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).x - firstView.width / 2.0,
          firstView.translationX.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).y - firstView.height / 2.0,
          firstView.translationY.toDouble(),
          ADMISSIBLE_ERROR_PX
        )
      }
    )
  }

  @Test
  fun updateViewAnnotationAllowOverlap() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
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
            it.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    )
  }

  // checking some use-cases when deleting view annotations

  @Test
  fun removeViewAnnotation() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
          }
        )
        viewAnnotationManager.removeViewAnnotation(firstView)
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
      }
    )
  }

  @Test
  fun removeViewAnnotationWhenTwoOverlap() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
        viewAnnotationManager.removeViewAnnotation(secondView)
        mainHandler.postDelayed(
          {
            assertTrue(mapView.hasChildView(firstView))
            assertFalse(mapView.hasChildView(secondView))
            it.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    )
  }

  // checking some use-cases when using associatedFeatureId

  @Test
  fun associatedFeatureIdWhenFeatureVisible() {
    viewAnnotationTestHelper(
      performAction = {
        prepareStyle(mapboxMap.getStyle()!!, Visibility.VISIBLE)
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.BOTTOM)
            offsetY(50)
            visible(true)
            associatedFeatureId(ASSOCIATED_FEATURE_ID)
          }
        )
      },
      makeChecks = {
        assertTrue(mapView.hasChildView(firstView))
      }
    )
  }

  @Test
  fun associatedFeatureIdWhenFeatureGone() {
    viewAnnotationTestHelper(
      performAction = {
        prepareStyle(mapboxMap.getStyle()!!, Visibility.NONE)
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.BOTTOM)
            offsetY(50)
            visible(true)
            associatedFeatureId(ASSOCIATED_FEATURE_ID)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
      }
    )
  }

  // checking automatic view visibility handling

  @Test
  fun automaticViewVisibilityHandling() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = R.layout.view_annotation,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            allowOverlap(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertTrue(mapView.hasChildView(secondView))
        secondView.visibility = View.GONE
        mainHandler.postDelayed(
          {
            // second view is not removed from parent MapView but as it's gone first view should appear now
            assertTrue(mapView.hasChildView(secondView))
            assertTrue(mapView.hasChildView(firstView))
            it.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    )
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
    const val VIEW_PLACEMENT_DELAY_MS = 1000L
    const val ADMISSIBLE_ERROR_PX = 3.0

    const val ASSOCIATED_FEATURE_ID = "featureTestId"
    const val CAMERA_ZOOM = 10.0
    val CAMERA_CENTER: Point = Point.fromLngLat(0.0, 0.0)
    val SHIFTED_CENTER: Point = Point.fromLngLat(CAMERA_CENTER.longitude() - 0.01, CAMERA_CENTER.latitude() - 0.01)
  }
}