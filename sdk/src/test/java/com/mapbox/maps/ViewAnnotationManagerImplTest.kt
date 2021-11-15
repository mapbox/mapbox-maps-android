package com.mapbox.maps

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import io.mockk.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ViewAnnotationManagerImplTest {

  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)
  private val mapView: MapView = mockk(relaxUnitFun = true)

  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl

  @Before
  fun setUp() {
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.mapController.pluginRegistry.viewPlugins } returns mutableMapOf()
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView)
  }

  @After
  fun tearDown() {

  }

  // TODO make parametrized
  @Test
  fun addViewAnnotationOne() {
    val resId = 1
    val expectedView = mockk<View>(relaxed = true)
    val params = mockk<FrameLayout.LayoutParams>()
    params.width = 20
    params.height = 20
    mockkStatic(LayoutInflater::class)
    every { mapView.context } returns mockk()
    every { LayoutInflater.from(any()).inflate(resId, mapView, false) } returns expectedView
    every { expectedView.layoutParams } returns params
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()

    val actualView = viewAnnotationManager.addViewAnnotation(
      resId,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(0.0, 0.0))
        offsetX(10)
      }
    )
    Assert.assertEquals(expectedView, actualView)
    verify(exactly = 1) {
      mapboxMap.addViewAnnotation(
        any(),
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
          offsetX(10)
          // width and height are calculated based on layout params
          width(20)
          height(20)
        }
      )
    }

    unmockkStatic(LayoutInflater::class)
  }
}