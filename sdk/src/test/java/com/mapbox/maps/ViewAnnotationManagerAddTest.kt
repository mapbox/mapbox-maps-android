package com.mapbox.maps

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.renderer.MapboxRenderThread
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl
import com.mapbox.maps.viewannotation.ViewAnnotationManagerImpl.Companion.EXCEPTION_TEXT_GEOMETRY_IS_NULL
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
  private lateinit var frameLayoutParams: FrameLayout.LayoutParams

  @Before
  fun setUp() {
    val mapView: MapView = mockk(relaxUnitFun = true)
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.layoutParams = any() } just Runs
    every { mapView.context } returns mockk()
    renderer = mockk(relaxUnitFun = true)
    every { mapView.mapController.renderer.renderThread } returns renderer
    frameLayoutParams = FrameLayout.LayoutParams(0, 0)
    frameLayoutParams.width = layoutParamsWidthActual
    frameLayoutParams.height = layoutParamsHeightActual
    expectedView = mockk(relaxed = true)
    every { expectedView.layoutParams } returns frameLayoutParams
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    viewAnnotationsLayout = mockk()
    every { viewAnnotationsLayout.layoutParams = any() } just Runs
    every { viewAnnotationsLayout.addView(any()) } just Runs
    every { viewAnnotationsLayout.removeView(any()) } just Runs
    every { viewAnnotationsLayout.context } returns mockk()
    val displayMetrics = DisplayMetrics().apply { density = 1f }
    every { mapView.resources.displayMetrics } returns displayMetrics
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView, viewAnnotationsLayout)
  }

  @After
  fun tearDown() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    viewAnnotationManager.destroy()
  }

  @Test
  fun addViewAnnotationWithSyncResId() {
    mockkStatic(LayoutInflater::class)
    every { LayoutInflater.from(any()).inflate(resIdActual, viewAnnotationsLayout, false) } returns expectedView
    if (runtimeExceptionThrown) {
      val exception = assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          resIdActual,
          viewAnnotationOptionsActual
        )
      }
      assertEquals(EXCEPTION_TEXT_GEOMETRY_IS_NULL, exception.message)
    } else {
      val actualView = viewAnnotationManager.addViewAnnotation(
        resIdActual,
        viewAnnotationOptionsActual
      )
      assertEquals(expectedView, actualView)
      verify(exactly = 1) {
        mapboxMap.addViewAnnotation(
          any(),
          viewAnnotationOptionsExpected
        )
      }
    }
    unmockkStatic(LayoutInflater::class)
  }

  @Test
  fun addViewAnnotationWithAsyncResId() {
    val asyncInflater = mockk<AsyncLayoutInflater>()
    val callback = slot<AsyncLayoutInflater.OnInflateFinishedListener>()
    every { asyncInflater.inflate(resIdActual, viewAnnotationsLayout, capture(callback)) } answers {
      callback.captured.onInflateFinished(expectedView, resIdActual, viewAnnotationsLayout)
    }
    if (runtimeExceptionThrown) {
      val exception = assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          resIdActual,
          viewAnnotationOptionsActual,
          asyncInflater,
        ) {}
      }
      assertEquals(EXCEPTION_TEXT_GEOMETRY_IS_NULL, exception.message)
    } else {
      viewAnnotationManager.addViewAnnotation(
        resIdActual,
        viewAnnotationOptionsActual,
        asyncInflater,
      ) {
        assertEquals(expectedView, it)
        verify(exactly = 1) {
          mapboxMap.addViewAnnotation(
            any(),
            viewAnnotationOptionsExpected
          )
        }
      }
    }
  }

  @Test
  fun addViewAnnotationWithView() {
    val actualView = mockk<View>(relaxed = true)
    every { actualView.layoutParams } returns frameLayoutParams
    if (runtimeExceptionThrown) {
      val exception = assertThrows(RuntimeException::class.java) {
        viewAnnotationManager.addViewAnnotation(
          actualView,
          viewAnnotationOptionsActual
        )
      }
      assertEquals(EXCEPTION_TEXT_GEOMETRY_IS_NULL, exception.message)
    } else {
      viewAnnotationManager.addViewAnnotation(
        actualView,
        viewAnnotationOptionsActual
      )
      verify(exactly = 1) {
        mapboxMap.addViewAnnotation(
          any(),
          viewAnnotationOptionsExpected
        )
      }
    }
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
          offsetX(10)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          // width and height are calculated based on layout params
          width(20)
          height(30)
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
          offsetX(10)
          width(40)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          width(40)
          height(30)
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
          offsetX(10)
          height(40)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          width(20)
          height(40)
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
          offsetX(10)
          width(50)
          height(40)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          width(50)
          height(40)
        },
        /* runtimeExceptionThrown */ false,
      ),
      arrayOf(
        /* resIdActual */ 1,
        /* layoutParamsWidthActual */ 20,
        /* layoutParamsHeightActual */ 30,
        /* viewAnnotationOptionsActual */
        viewAnnotationOptions {
          offsetX(10)
          width(50)
          height(40)
        },
        /* viewAnnotationOptionsExpected */
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          width(50)
          height(40)
        },
        /* runtimeExceptionThrown */ true,
      ),
    )
  }
}