package com.mapbox.maps

import android.view.View
import android.widget.FrameLayout
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationManagerImpl.Companion.EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS
import com.mapbox.maps.renderer.MapboxRenderThread
import com.mapbox.maps.viewannotation.OnViewAnnotationUpdatedListener
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
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
  private lateinit var renderer: MapboxRenderThread

  @Before
  fun setUp() {
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.mapController.pluginRegistry.viewPlugins } returns mutableMapOf()
    renderer = mockk(relaxUnitFun = true)
    every { mapView.mapController.renderer.renderThread } returns renderer
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView)
    frameLayoutParams = mockk()
    frameLayoutParams.width = 20
    frameLayoutParams.height = 20
    every { mapView.context } returns mockk()
    view = mockk()
    mockView(view)
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
  }

  private fun mockView(view: View) {
    every { view.layoutParams } returns frameLayoutParams
    every { view.visibility } returns View.VISIBLE
    every { view.viewTreeObserver } returns mockk(relaxed = true)
    every { view.addOnAttachStateChangeListener(any()) } just Runs
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
  fun removeAllViewAnnotations() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    every { view.removeOnAttachStateChangeListener(any()) } just Runs
    // add first view
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    val anotherView = mockk<View>()
    every { anotherView.removeOnAttachStateChangeListener(any()) } just Runs
    mockView(anotherView)
    // add another view
    viewAnnotationManager.addViewAnnotation(
      anotherView,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(90.0, 90.0))
      }
    )
    viewAnnotationManager.removeAllViewAnnotations()
    assert(viewAnnotationManager.idLookupMap.isEmpty())
    verify(exactly = 2) { mapboxMap.removeViewAnnotation(any()) }
    verify(exactly = 1) { mapView.removeView(view) }
    verify(exactly = 1) { mapView.removeView(anotherView) }
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

  @Test
  fun addViewPositioningListener() {
    val listener = mockk<OnViewAnnotationUpdatedListener>()
    viewAnnotationManager.addOnViewAnnotationUpdatedListener(listener)
    assert(viewAnnotationManager.viewUpdatedListenerSet.contains(listener))
  }

  @Test
  fun removeViewPositioningListener() {
    val listener = mockk<OnViewAnnotationUpdatedListener>()
    viewAnnotationManager.addOnViewAnnotationUpdatedListener(listener)
    viewAnnotationManager.removeOnViewAnnotationUpdatedListener(listener)
    assert(viewAnnotationManager.viewUpdatedListenerSet.isEmpty())
  }

  @Test
  fun setViewAnnotationUpdateMode() {
    viewAnnotationManager.setViewAnnotationUpdateMode(ViewAnnotationUpdateMode.MAP_FIXED_DELAY)
    verify(exactly = 1) { renderer.viewAnnotationMode = ViewAnnotationUpdateMode.MAP_FIXED_DELAY }
  }

  @Test
  fun getViewAnnotationUpdateMode() {
    every { renderer.viewAnnotationMode } returns ViewAnnotationManager.DEFAULT_UPDATE_MODE
    assertEquals(ViewAnnotationManager.DEFAULT_UPDATE_MODE, viewAnnotationManager.getViewAnnotationUpdateMode())
    every { renderer.viewAnnotationMode } returns ViewAnnotationUpdateMode.MAP_FIXED_DELAY
    assertEquals(ViewAnnotationUpdateMode.MAP_FIXED_DELAY, viewAnnotationManager.getViewAnnotationUpdateMode())
  }

  private companion object {
    const val FEATURE_ID = "featureId"
    val DEFAULT_GEOMETRY: Geometry = Point.fromLngLat(0.0, 0.0)
  }
}