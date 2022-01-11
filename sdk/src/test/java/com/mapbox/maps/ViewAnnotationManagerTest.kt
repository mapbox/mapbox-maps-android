package com.mapbox.maps

import android.view.View
import android.widget.FrameLayout
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationManagerImpl.Companion.EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
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
    every { view.addOnAttachStateChangeListener(any()) } just Runs
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
  }

  @After
  fun tearDown() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    every { view.removeOnAttachStateChangeListener(any()) } just Runs
    viewAnnotationManager.destroy()
  }

  @Test
  fun addViewAnnotationWithDuplicateView() {
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    val exception = assertThrows(RuntimeException::class.java) {
      viewAnnotationManager.addViewAnnotation(
        view,
        viewAnnotationOptions {
          geometry(DEFAULT_GEOMETRY)
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
  fun addViewAnnotationWithDuplicateAssociatedFeatureId() {
    val associatedFeatureId = "associatedFeatureId"
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      associatedFeatureId(associatedFeatureId)
    }
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions
    )
    val id = viewAnnotationManager.idLookupMap[view]!!
    val anotherView = mockk<View>()
    every { anotherView.layoutParams } returns frameLayoutParams
    every { anotherView.visibility } returns View.VISIBLE
    every { anotherView.viewTreeObserver } returns mockk(relaxed = true)
    every { mapboxMap.getViewAnnotationOptions(id) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    val exception = assertThrows(RuntimeException::class.java) {
      viewAnnotationManager.addViewAnnotation(
        anotherView,
        viewAnnotationOptions
      )
    }
    assertEquals(
      String.format(
        EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS,
        associatedFeatureId
      ),
      exception.message
    )
  }

  @Test
  fun updateViewAnnotationWithDuplicateAssociatedFeatureId() {
    val associatedFeatureId = "associatedFeatureId"
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      associatedFeatureId(associatedFeatureId)
    }
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions
    )
    val id = viewAnnotationManager.idLookupMap[view]!!
    every { mapboxMap.getViewAnnotationOptions(id) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    val exception = assertThrows(RuntimeException::class.java) {
      viewAnnotationManager.updateViewAnnotation(
        view,
        viewAnnotationOptions {
          associatedFeatureId(associatedFeatureId)
        }
      )
    }
    assertEquals(
      String.format(
        EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS,
        associatedFeatureId
      ),
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
        geometry(DEFAULT_GEOMETRY)
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
      offsetX(-10)
    }
    val updateActualResult = viewAnnotationManager.updateViewAnnotation(mockk(), updatedOptions)
    assertEquals(false, updateActualResult)
    verify(exactly = 0) { mapboxMap.updateViewAnnotation(any(), updatedOptions) }
  }

  @Test
  fun removeViewAnnotationSuccess() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    every { view.removeOnAttachStateChangeListener(any()) } just Runs
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    val id = viewAnnotationManager.idLookupMap[view]
    val removeActualResult = viewAnnotationManager.removeViewAnnotation(view)
    assertEquals(true, removeActualResult)
    assertNull(viewAnnotationManager.idLookupMap[view])
    verify(exactly = 1) { view.removeOnAttachStateChangeListener(any()) }
    verify(exactly = 1) { mapboxMap.removeViewAnnotation(id!!) }
    verify(exactly = 1) { mapView.removeView(view) }
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
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      associatedFeatureId(FEATURE_ID)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(view, viewAnnotationManager.getViewAnnotationByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationByFeatureIdFailureNoFeatureId() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertNull(viewAnnotationManager.getViewAnnotationByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationByFeatureIdFailureNoView() {
    assertNull(viewAnnotationManager.getViewAnnotationByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdSuccess() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      associatedFeatureId(FEATURE_ID)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(viewAnnotationOptions, viewAnnotationManager.getViewAnnotationOptionsByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdFailureNoFeatureId() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val viewId = viewAnnotationManager.idLookupMap[view]
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertNull(viewAnnotationManager.getViewAnnotationOptionsByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationOptionsByFeatureIdFailureNoView() {
    assertNull(viewAnnotationManager.getViewAnnotationOptionsByFeatureId(FEATURE_ID))
  }

  @Test
  fun getViewAnnotationOptionsByViewSuccess() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(viewAnnotationOptions)
    assertEquals(viewAnnotationOptions, viewAnnotationManager.getViewAnnotationOptionsByView(view))
  }

  @Test
  fun getViewAnnotationOptionsByViewNoViewFailure() {
    assertNull(viewAnnotationManager.getViewAnnotationOptionsByView(view))
  }

  private companion object {
    const val FEATURE_ID = "featureId"
    val DEFAULT_GEOMETRY: Geometry = Point.fromLngLat(0.0, 0.0)
  }
}