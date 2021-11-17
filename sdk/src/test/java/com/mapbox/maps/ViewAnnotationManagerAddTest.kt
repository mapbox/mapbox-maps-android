package com.mapbox.maps

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationManagerImpl.Companion.EXCEPTION_TEXT_GEOMETRY_IS_NULL
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
  private val mapView: MapView = mockk(relaxUnitFun = true)

  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl
  private lateinit var expectedView: View
  private lateinit var frameLayoutParams: FrameLayout.LayoutParams

  @Before
  fun setUp() {
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.mapController.pluginRegistry.viewPlugins } returns mutableMapOf()
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView)
    expectedView = mockk(relaxed = true)
    frameLayoutParams = mockk()
    frameLayoutParams.width = layoutParamsWidthActual
    frameLayoutParams.height = layoutParamsHeightActual
    every { mapView.context } returns mockk()
    every { expectedView.layoutParams } returns frameLayoutParams
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
  }

  @After
  fun tearDown() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    viewAnnotationManager.destroy()
  }

  @Test
  fun addViewAnnotationWithSyncResId() {
    mockkStatic(LayoutInflater::class)
    every { LayoutInflater.from(any()).inflate(resIdActual, mapView, false) } returns expectedView
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
    every { asyncInflater.inflate(resIdActual, mapView, capture(callback)) } answers {
      callback.captured.onInflateFinished(expectedView, resIdActual, mapView)
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