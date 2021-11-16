package com.mapbox.maps

import android.view.View
import android.widget.FrameLayout
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class ViewAnnotationManagerTest {
  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)
  private val mapView: MapView = mockk(relaxUnitFun = true)

  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl
  private lateinit var frameLayoutParams: FrameLayout.LayoutParams
  private lateinit var view: View

  @Before
  fun setUp() {
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.mapController.pluginRegistry.viewPlugins } returns mutableMapOf()
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView)
    frameLayoutParams = mockk()
    frameLayoutParams.width = 20
    frameLayoutParams.height = 20
    view = mockk()
    every { view.layoutParams } returns frameLayoutParams
    every { view.visibility } returns View.VISIBLE
    every { view.viewTreeObserver } returns mockk(relaxed = true)
    every { mapView.context } returns mockk()
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
  }

  @After
  fun tearDown() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    viewAnnotationManager.destroy()
  }

  @Test
  fun addViewAnnotationWithDuplicateView() {
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(0.0, 0.0))
      }
    )
    val exception = assertThrows(RuntimeException::class.java) {
      viewAnnotationManager.addViewAnnotation(
        view,
        viewAnnotationOptions {
          geometry(Point.fromLngLat(0.0, 0.0))
        }
      )
    }
    assertEquals(
      "Trying to add view annotation that was already added before! " +
        "Please consider deleting annotation view ($view) beforehand.",
      exception.message
    )
  }

  @Test
  fun updateViewAnnotationSuccess() {
    every { mapboxMap.updateViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    val updatedOptions = viewAnnotationOptions {
      offsetX(10)
    }
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(0.0, 0.0))
      }
    )
    val updateActualResult = viewAnnotationManager.updateViewAnnotation(view, updatedOptions)
    assertEquals(true, updateActualResult)
    val id = viewAnnotationManager.idLookupMap[view]
    verify(exactly = 1) { mapboxMap.updateViewAnnotation(id!!, updatedOptions) }
  }

  @Test
  fun updateViewAnnotationFailure() {
    every { mapboxMap.updateViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    val updatedOptions = viewAnnotationOptions {
      offsetX(10)
    }
    val updateActualResult = viewAnnotationManager.updateViewAnnotation(mockk(), updatedOptions)
    assertEquals(false, updateActualResult)
    verify(exactly = 0) { mapboxMap.updateViewAnnotation(any(), updatedOptions) }
  }

  @Test
  fun removeViewAnnotationSuccess() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(0.0, 0.0))
      }
    )
    val id = viewAnnotationManager.idLookupMap[view]
    val removeActualResult = viewAnnotationManager.removeViewAnnotation(view)
    assertEquals(true, removeActualResult)
    verify(exactly = 1) { mapboxMap.removeViewAnnotation(id!!) }
  }

  @Test
  fun removeViewAnnotationFailure() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    val removeActualResult = viewAnnotationManager.removeViewAnnotation(mockk())
    assertEquals(false, removeActualResult)
    verify(exactly = 0) { mapboxMap.removeViewAnnotation(any()) }
  }

  @Test
  fun getViewAnnotationByFeatureIdSuccess() {
    val featureId = "featureId"
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(Point.fromLngLat(0.0, 0.0))
      associatedFeatureId(featureId)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(view, viewAnnotationManager.getViewAnnotationByFeatureId(featureId))
  }

  @Test
  fun getViewAnnotationByFeatureIdFailureNoFeatureId() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(Point.fromLngLat(0.0, 0.0))
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(null, viewAnnotationManager.getViewAnnotationByFeatureId("featureId"))
  }

  @Test
  fun getViewAnnotationByFeatureIdFailureNoView() {
    assertEquals(null, viewAnnotationManager.getViewAnnotationByFeatureId("featureId"))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdSuccess() {
    val featureId = "featureId"
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(Point.fromLngLat(0.0, 0.0))
      associatedFeatureId(featureId)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(viewAnnotationOptions, viewAnnotationManager.getViewAnnotationOptionsByFeatureId(featureId))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdFailureNoFeatureId() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(Point.fromLngLat(0.0, 0.0))
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(null, viewAnnotationManager.getViewAnnotationOptionsByFeatureId("featureId"))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdFailureNoView() {
    assertEquals(null, viewAnnotationManager.getViewAnnotationOptionsByFeatureId("featureId"))
  }

  @Test
  fun getViewAnnotationOptionsByViewSuccess() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(Point.fromLngLat(0.0, 0.0))
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(viewAnnotationOptions, viewAnnotationManager.getViewAnnotationOptionsByView(view))
  }

  @Test
  fun getViewAnnotationOptionsByViewNoViewFailure() {
    assertEquals(null, viewAnnotationManager.getViewAnnotationOptionsByView(view))
  }
}