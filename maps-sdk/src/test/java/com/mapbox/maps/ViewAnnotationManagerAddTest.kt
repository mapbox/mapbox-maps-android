package com.mapbox.maps

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.renderer.MapboxRenderThread
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl
import com.mapbox.maps.viewannotation.annotatedLayerFeature
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ViewAnnotationManagerAddTest(
  private val resIdActual: Int,
  private val layoutParamsWidthActual: Int,
  private val layoutParamsHeightActual: Int,
  private val viewAnnotationOptionsActual: ViewAnnotationOptions,
  private val viewAnnotationOptionsExpected: ViewAnnotationOptions,
  private val runtimeExceptionThrown: Boolean,
) {
  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)
  private lateinit var viewAnnotationsLayout: FrameLayout
  private lateinit var renderer: MapboxRenderThread

  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl
  private lateinit var expectedView: View
  private lateinit var layoutParams: ViewGroup.LayoutParams

  @Before
  fun setUp() {
    val mapView: MapView = mockk(relaxUnitFun = true)
    mockkStatic(MeasureSpec::class)
    every { MeasureSpec.makeMeasureSpec(any(), any()) } returns 0
    every { mapView.mapboxMap } returns mapboxMap
    every { mapView.layoutParams = any() } just Runs
    every { mapView.context } returns mockk()
    renderer = mockk(relaxUnitFun = true)
    every { mapView.mapController.renderer.renderThread } returns renderer
    layoutParams = ViewGroup.LayoutParams(0, 0)
    layoutParams.width = layoutParamsWidthActual
    layoutParams.height = layoutParamsHeightActual
    expectedView = mockk(relaxed = true)
    every { expectedView.layoutParams } returns layoutParams
    every { expectedView.measuredWidth } returns layoutParamsWidthActual
    every { expectedView.measuredHeight } returns layoutParamsHeightActual
    every { expectedView.width } returns layoutParamsWidthActual
    every { expectedView.height } returns layoutParamsHeightActual
    every { expectedView.measure(any(), any()) } just Runs
    every { expectedView.layout(any(), any(), any(), any()) } just Runs

    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    viewAnnotationsLayout = mockk()
    every { viewAnnotationsLayout.layoutParams = any() } just Runs
    every { viewAnnotationsLayout.addView(any()) } just Runs
    every { viewAnnotationsLayout.removeView(any()) } just Runs
    every { viewAnnotationsLayout.context } returns mockk()
    val displayMetrics = DisplayMetrics().apply { density = 1f }
    every { mapView.resources.displayMetrics } returns displayMetrics

    mockkStatic(LayoutInflater::class)
    every {
      LayoutInflater.from(any()).inflate(resIdActual, viewAnnotationsLayout, false)
    } returns expectedView

    viewAnnotationManager = ViewAnnotationManagerImpl(mapView, viewAnnotationsLayout)
  }

  @After
  fun tearDown() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    viewAnnotationManager.destroy()
    unmockkStatic(LayoutInflater::class)
    unmockkStatic(MeasureSpec::class)
  }

  @Test
  fun addViewAnnotationWithSyncResId() {
    if (runtimeExceptionThrown) {
      assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          resIdActual,
          viewAnnotationOptionsActual
        )
      }
    } else {
      val actualView = viewAnnotationManager.addViewAnnotation(
        resIdActual,
        viewAnnotationOptionsActual
      )
      assertEquals(expectedView, actualView)
      val optionsSlot = slot<ViewAnnotationOptions>()

      verify(exactly = 1) {
        mapboxMap.addViewAnnotation(
          any(),
          capture(optionsSlot)
        )
      }

      assertViewAnnotationOptions(viewAnnotationOptionsExpected, optionsSlot.captured)
    }
  }

  @Test
  fun addViewAnnotationWithAsyncResId() {
    val asyncInflater = mockk<AsyncLayoutInflater>()
    val callback = slot<AsyncLayoutInflater.OnInflateFinishedListener>()
    every { asyncInflater.inflate(resIdActual, viewAnnotationsLayout, capture(callback)) } answers {
      callback.captured.onInflateFinished(expectedView, resIdActual, viewAnnotationsLayout)
    }
    if (runtimeExceptionThrown) {
      assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          resIdActual,
          viewAnnotationOptionsActual,
          asyncInflater,
        ) {}
      }
    } else {
      viewAnnotationManager.addViewAnnotation(
        resIdActual,
        viewAnnotationOptionsActual,
        asyncInflater,
      ) {
        assertEquals(expectedView, it)
        val optionsSlot = slot<ViewAnnotationOptions>()
        verify(exactly = 1) {
          mapboxMap.addViewAnnotation(
            any(),
            capture(optionsSlot)
          )
        }
        assertViewAnnotationOptions(viewAnnotationOptionsExpected, optionsSlot.captured)
      }
    }
  }

  @Test
  fun addViewAnnotationWithView() {
    val actualView = mockk<View>(relaxed = true)
    every { actualView.layoutParams } returns layoutParams
    every { actualView.width } returns layoutParamsWidthActual
    every { actualView.height } returns layoutParamsHeightActual
    every { actualView.measuredWidth } returns layoutParamsWidthActual
    every { actualView.measuredHeight } returns layoutParamsHeightActual
    if (runtimeExceptionThrown) {
      assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          actualView,
          viewAnnotationOptionsActual
        )
      }
    } else {
      viewAnnotationManager.addViewAnnotation(
        actualView,
        viewAnnotationOptionsActual
      )
      val optionsSlot = slot<ViewAnnotationOptions>()
      verify(exactly = 1) {
        mapboxMap.addViewAnnotation(
          any(),
          capture(optionsSlot)
        )
      }
      assertViewAnnotationOptions(viewAnnotationOptionsExpected, optionsSlot.captured)
    }
  }

  private fun assertViewAnnotationOptions(
    viewAnnotationOptionsExpected: ViewAnnotationOptions,
    capturedOptions: ViewAnnotationOptions
  ) {
    if (capturedOptions.annotatedFeature?.isGeometry == true) {
      assertEquals(
        viewAnnotationOptionsExpected.annotatedFeature!!.geometry,
        capturedOptions.annotatedFeature!!.geometry,
      )
    } else {
      assertEquals(
        viewAnnotationOptionsExpected.annotatedFeature!!.annotatedLayerFeature,
        capturedOptions.annotatedFeature!!.annotatedLayerFeature,
      )
    }
    assertEquals(viewAnnotationOptionsExpected.height, capturedOptions.height)
    assertEquals(viewAnnotationOptionsExpected.width, capturedOptions.width)
    assertEquals(viewAnnotationOptionsExpected.visible, capturedOptions.visible)
    assertEquals(viewAnnotationOptionsExpected.allowOverlap, capturedOptions.allowOverlap)
    assertEquals(viewAnnotationOptionsExpected.selected, capturedOptions.selected)
    assertEquals(viewAnnotationOptionsExpected.variableAnchors, capturedOptions.variableAnchors)
  }

  companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          // width and height are calculated based on layout params
          width(20.0)
          height(30.0)
        },
        /* runtimeExceptionThrown */ false,
      ),
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(40.0)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(40.0)
          height(30.0)
        },
        /* runtimeExceptionThrown */ false,
      ),
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          height(40.0)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(20.0)
          height(40.0)
        },
        /* runtimeExceptionThrown */ false,
      ),
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(50.0)
          height(40.0)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(50.0)
          height(40.0)
        },
        /* runtimeExceptionThrown */ false,
      ),
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(50.0)
          height(40.0)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          annotatedLayerFeature("layer") {
            featureId("feature")
          }
          annotationAnchor {
            anchor(ViewAnnotationAnchor.CENTER)
            offsetX(10.0)
          }
          width(50.0)
          height(40.0)
        },
        /* runtimeExceptionThrown */ true,
      ),
    )
  }
}