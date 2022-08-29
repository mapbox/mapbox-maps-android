package com.mapbox.maps

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.LayoutRes
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.moveBy
import com.mapbox.maps.test.R
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(Parameterized::class)
@LargeTest
class ViewAnnotationTest(
  @LayoutRes private val layoutResId: Int,
  private val mode: ViewAnnotationUpdateMode,
) {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var mapView: MapView
  private lateinit var viewAnnotationManager: ViewAnnotationManager
  private val mainHandler = Handler(Looper.getMainLooper())

  private lateinit var firstView: View
  private lateinit var secondView: View

  private lateinit var actualVisibilityUpdateList: MutableList<Pair<View, Boolean>>

  @get:Rule
  var rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Before
  @UiThreadTest
  fun before() {
    val latch = CountDownLatch(2)
    actualVisibilityUpdateList = mutableListOf()
    rule.scenario.onActivity {
      val context = InstrumentationRegistry.getInstrumentation().targetContext
      mapView = MapView(context)
      mapView.id = R.id.mapView
      it.setContentView(mapView)

      viewAnnotationManager = mapView.viewAnnotationManager.apply {
        setViewAnnotationUpdateMode(mode)
        // no need to remove it afterwards as map view is destroyed in cleanup
        addOnViewAnnotationUpdatedListener(object : OnViewAnnotationUpdatedListener {
          override fun onViewAnnotationPositionUpdated(
            view: View,
            leftTopCoordinate: ScreenCoordinate,
            width: Int,
            height: Int
          ) { /** no-op **/ }

          override fun onViewAnnotationVisibilityUpdated(view: View, visible: Boolean) {
            actualVisibilityUpdateList.add(Pair(view, visible))
          }
        })
      }
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
    // it seems that IDLE listener is sometimes not triggered, needs investigation,
    // for now we proceed after timeout if style was loaded (count == 1)
    // TODO https://github.com/mapbox/mapbox-maps-android/issues/1170
    if (!latch.await(DEFAULT_LATCH_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
      if (latch.count < 1) {
        throw TimeoutException()
      }
    }
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
    latch.throwExceptionOnTimeoutMs(timeoutMs = TEST_RUN_MAX_TIME_MS)
  }

  // checking some use-cases when adding view annotation

  @Test
  fun addViewAnnotationNoOptions() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addViewAnnotationAnchor() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
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
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addViewAnnotationNotVisible() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            visible(false)
          }
        )
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertArrayEquals(
          arrayOf(),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
            Pair(secondView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(secondView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        // although first view is selected - we still respect addition order
        assertArrayEquals(
          arrayOf(
            Pair(secondView, true),
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneAllowOverlapTrueAnotherFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
            Pair(secondView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneAllowOverlapFalseAnotherTrue() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(secondView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsOneSelectedAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun addTwoViewAnnotationsTwoSelectedAllowOverlapFalse() {
    viewAnnotationTestHelper(
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(SHIFTED_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
            allowOverlap(false)
            selected(true)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(secondView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
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
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
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
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
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
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
            assertArrayEquals(
              arrayOf(
                Pair(secondView, true),
                Pair(firstView, true),
              ),
              actualVisibilityUpdateList.toTypedArray()
            )
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
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
          }
        )
        viewAnnotationManager.removeViewAnnotation(firstView)
      },
      makeChecks = {
        assertFalse(mapView.hasChildView(firstView))
        assertArrayEquals(
          arrayOf(),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun removeViewAnnotationWhenTwoOverlap() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
            assertArrayEquals(
              arrayOf(
                Pair(secondView, true),
                Pair(secondView, false),
                Pair(firstView, true),
              ),
              actualVisibilityUpdateList.toTypedArray()
            )
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
          resId = layoutResId,
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
        assertArrayEquals(
          arrayOf(
            Pair(firstView, true),
          ),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun associatedFeatureIdWhenFeatureGone() {
    viewAnnotationTestHelper(
      performAction = {
        prepareStyle(mapboxMap.getStyle()!!, Visibility.NONE)
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        // view does not even appear so update listener should not be triggered
        assertArrayEquals(
          arrayOf(),
          actualVisibilityUpdateList.toTypedArray()
        )
      }
    )
  }

  @Test
  fun associatedFeatureIdWhenFeatureVisibleThenGone() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        prepareStyle(mapboxMap.getStyle()!!, Visibility.VISIBLE)
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
        // hide marker
        mapboxMap.getStyle()?.getLayer("layer")?.visibility(Visibility.NONE)
        mainHandler.postDelayed(
          {
            assertArrayEquals(
              arrayOf(
                Pair(firstView, true),
                Pair(firstView, false),
              ),
              actualVisibilityUpdateList.toTypedArray()
            )
            it.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    )
  }

  @Test
  fun automaticViewVisibilityHandlingViewsOverlap() {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            allowOverlap(false)
          }
        )
        secondView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
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
            assertArrayEquals(
              arrayOf(
                Pair(secondView, true),
                Pair(secondView, false),
                Pair(firstView, true)
              ),
              actualVisibilityUpdateList.toTypedArray()
            )
            it.countDown()
          },
          VIEW_PLACEMENT_DELAY_MS
        )
      }
    )
  }

  @Test
  fun automaticViewVisibilityHandlingInvisibleCase() {
    automaticViewVisibilityTest(View.INVISIBLE)
  }

  @Test
  fun automaticViewVisibilityHandlingGoneCase() {
    automaticViewVisibilityTest(View.GONE)
  }

  private fun automaticViewVisibilityTest(resultVisibility: Int) {
    viewAnnotationTestHelper(
      additionalLatchCount = 1,
      performAction = {
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          }
        )
        firstView.visibility = View.VISIBLE
      },
      makeChecks = {
        val initialTranslation = firstView.translationX.toDouble()
        assertEquals(
          mapboxMap.pixelForCoordinate(CAMERA_CENTER).x,
          initialTranslation,
          ADMISSIBLE_ERROR_PX
        )
        firstView.visibility = resultVisibility
        val shiftX = 50.0
        // trigger map movement while view annotation is not visible to trigger several core updates
        mapboxMap.moveBy(
          ScreenCoordinate(
            shiftX,
            0.0
          ),
          mapAnimationOptions {
            duration(100L)
          }
        )
        mainHandler.postDelayed(
          {
            // make visible and move the map to make sure position is updated after view is visible
            firstView.visibility = View.VISIBLE
            val additionalShiftX = 50.0
            mapboxMap.moveBy(
              ScreenCoordinate(
                additionalShiftX,
                0.0
              ),
              mapAnimationOptions {
                duration(100L)
              }
            )
            mainHandler.postDelayed(
              {
                assertArrayEquals(
                  arrayOf(
                    Pair(firstView, true),
                    Pair(firstView, false),
                    Pair(firstView, true)
                  ),
                  actualVisibilityUpdateList.toTypedArray()
                )
                assertEquals(
                  initialTranslation + shiftX + additionalShiftX,
                  firstView.translationX.toDouble(),
                  ADMISSIBLE_ERROR_PX
                )
                it.countDown()
              },
              VIEW_PLACEMENT_DELAY_MS / 3
            )
          },
          VIEW_PLACEMENT_DELAY_MS / 3
        )
      }
    )
  }

  @Test
  fun viewAnnotationUpdateListener() {
    var positionCallbackTriggerCount = 0
    var visibilityCallbackTriggerCount = 0
    var actualWidth = 0
    var actualHeight = 0
    var actualLeftTop = ScreenCoordinate(0.0, 0.0)
    var actualVisibility = false
    viewAnnotationTestHelper(
      performAction = {
        viewAnnotationManager.addOnViewAnnotationUpdatedListener(object : OnViewAnnotationUpdatedListener {
          override fun onViewAnnotationPositionUpdated(
            view: View,
            leftTopCoordinate: ScreenCoordinate,
            width: Int,
            height: Int
          ) {
            if (firstView == view) {
              positionCallbackTriggerCount++
              actualLeftTop = leftTopCoordinate
              actualWidth = width
              actualHeight = height
            }
          }

          override fun onViewAnnotationVisibilityUpdated(view: View, visible: Boolean) {
            if (firstView == view) {
              visibilityCallbackTriggerCount++
              actualVisibility = visible
            }
          }
        })
        firstView = viewAnnotationManager.addViewAnnotation(
          resId = layoutResId,
          options = viewAnnotationOptions {
            geometry(CAMERA_CENTER)
            anchor(ViewAnnotationAnchor.TOP_LEFT)
          }
        )
      },
      makeChecks = {
        // callback should be triggered once and contain correct placement data
        assert(positionCallbackTriggerCount == 1)
        assertEquals(firstView.translationX.toDouble(), actualLeftTop.x, ADMISSIBLE_ERROR_PX)
        assertEquals(firstView.translationY.toDouble(), actualLeftTop.y, ADMISSIBLE_ERROR_PX)
        assertEquals(firstView.width, actualWidth)
        assertEquals(firstView.height, actualHeight)
        // callback should be triggered once and contain correct visibility data
        assert(visibilityCallbackTriggerCount == 1)
        assertEquals(true, actualVisibility)
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
    const val VIEW_PLACEMENT_DELAY_MS = 3000L
    const val TEST_RUN_MAX_TIME_MS = 10_000L
    const val ADMISSIBLE_ERROR_PX = 3.0

    const val ASSOCIATED_FEATURE_ID = "featureTestId"
    const val CAMERA_ZOOM = 10.0
    val CAMERA_CENTER: Point = Point.fromLngLat(0.0, 0.0)
    val SHIFTED_CENTER: Point = Point.fromLngLat(CAMERA_CENTER.longitude() - 0.01, CAMERA_CENTER.latitude() - 0.01)

    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(R.layout.view_annotation, ViewAnnotationUpdateMode.MAP_SYNCHRONIZED),
      arrayOf(R.layout.view_annotation, ViewAnnotationUpdateMode.MAP_FIXED_DELAY),
      arrayOf(R.layout.view_annotation_wrap_content, ViewAnnotationUpdateMode.MAP_SYNCHRONIZED),
      arrayOf(R.layout.view_annotation_wrap_content, ViewAnnotationUpdateMode.MAP_FIXED_DELAY),
    )
  }
}