package com.mapbox.maps

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationManagerImpl.Companion.EXCEPTION_TEXT_ASSOCIATED_FEATURE_ID_ALREADY_EXISTS
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.renderer.MapboxRenderThread
import com.mapbox.maps.shadows.ShadowCoordinateBounds
import com.mapbox.maps.viewannotation.*
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowCoordinateBounds::class])
class ViewAnnotationManagerTest {
  private val mapboxMap: MapboxMap = mockk(relaxUnitFun = true)
  private val mapView: MapView = mockk(relaxUnitFun = true)

  private lateinit var viewAnnotationManager: ViewAnnotationManagerImpl
  private lateinit var frameLayoutParams: FrameLayout.LayoutParams
  private lateinit var view: View
  private lateinit var viewAnnotationsLayout: FrameLayout
  private lateinit var renderer: MapboxRenderThread
  private lateinit var viewTreeObserver: ViewTreeObserver
  private val style: Style = mockk(relaxUnitFun = true)

  @Before
  fun setUp() {
    every { mapView.getMapboxMap() } returns mapboxMap
    every { mapView.layoutParams = any() } just Runs
    every { mapView.context } returns mockk()
    renderer = mockk(relaxUnitFun = true)
    viewTreeObserver = mockk(relaxed = true)
    every { mapView.mapController.renderer.renderThread } returns renderer
    frameLayoutParams = FrameLayout.LayoutParams(0, 0)
    frameLayoutParams.width = DEFAULT_WIDTH
    frameLayoutParams.height = DEFAULT_HEIGHT
    view = mockView()
    viewAnnotationsLayout = mockk()
    every { viewAnnotationsLayout.layoutParams = any() } just Runs
    every { viewAnnotationsLayout.removeView(any()) } just Runs
    every { mapboxMap.addViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    val displayMetrics = DisplayMetrics().apply { density = 1f }
    every { mapView.resources.displayMetrics } returns displayMetrics
    viewAnnotationManager = ViewAnnotationManagerImpl(mapView, viewAnnotationsLayout)
    every { mapboxMap.style } returns style
    every { style.getStyleProjectionProperty("name") } returns
      StylePropertyValue(Value(ProjectionName.MERCATOR.value), StylePropertyValueKind.CONSTANT)
  }

  private fun mockView(): View = mockk<View>().also {
    every { it.layoutParams } returns frameLayoutParams
    every { it.visibility } returns View.VISIBLE
    every { it.viewTreeObserver } returns viewTreeObserver
    every { it.addOnAttachStateChangeListener(any()) } just Runs
    every { it.removeOnAttachStateChangeListener(any()) } just Runs
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
    verify(exactly = 1) { viewAnnotationsLayout.removeView(view) }
    verify(exactly = 1) { viewTreeObserver.removeOnDrawListener(any()) }
    verify(exactly = 1) { viewTreeObserver.removeOnGlobalLayoutListener(any()) }
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
    // add first view
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    val anotherView = mockView()
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
    verify(exactly = 1) { viewAnnotationsLayout.removeView(view) }
    verify(exactly = 1) { viewAnnotationsLayout.removeView(anotherView) }
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
    every { mapboxMap.getViewAnnotationOptions(viewId!!) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )
    assertEquals(
      viewAnnotationOptions,
      viewAnnotationManager.getViewAnnotationOptionsByFeatureId(FEATURE_ID)
    )
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
    assertEquals(
      ViewAnnotationManager.DEFAULT_UPDATE_MODE,
      viewAnnotationManager.getViewAnnotationUpdateMode()
    )
    every { renderer.viewAnnotationMode } returns ViewAnnotationUpdateMode.MAP_FIXED_DELAY
    assertEquals(
      ViewAnnotationUpdateMode.MAP_FIXED_DELAY,
      viewAnnotationManager.getViewAnnotationUpdateMode()
    )
  }

  @Test
  fun getViewAnnotationsSize() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    val anotherView = mockView()
    viewAnnotationManager.addViewAnnotation(
      anotherView,
      viewAnnotationOptions {
        geometry(Point.fromLngLat(90.0, 90.0))
      }
    )
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )
    every { anotherView.removeOnAttachStateChangeListener(any()) } just Runs
    assertEquals(2, viewAnnotationManager.annotations.size)
  }

  @Test
  fun getViewAnnotationOptionsFromAnnotationsMap() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      width(DEFAULT_WIDTH)
      height(DEFAULT_HEIGHT)
    }
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    assertEquals(viewAnnotationOptions, viewAnnotationManager.annotations[view])
  }

  @Test
  fun getViewAnnotationsAfterRemoveAnnotation() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    assertEquals(1, viewAnnotationManager.annotations.size)
    viewAnnotationManager.removeViewAnnotation(view)
    assert(viewAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun getViewAnnotationsAfterRemoveAllAnnotations() {
    every { mapboxMap.removeViewAnnotation(any()) } returns ExpectedFactory.createNone()
    every { view.removeOnAttachStateChangeListener(any()) } just Runs
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    viewAnnotationManager.removeAllViewAnnotations()
    assert(viewAnnotationManager.annotations.isEmpty())
  }

  @Test
  fun validateUpdateOptionsFromAnnotationsMap() {
    every { mapboxMap.updateViewAnnotation(any(), any()) } returns ExpectedFactory.createNone()
    val expectedOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
      offsetX(10)
    }
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      expectedOptions
    )
    viewAnnotationManager.addViewAnnotation(
      view,
      viewAnnotationOptions {
        geometry(DEFAULT_GEOMETRY)
      }
    )
    // check annotations size
    assertEquals(1, viewAnnotationManager.annotations.size)
    // update annotation options
    val result = viewAnnotationManager.updateViewAnnotation(view, expectedOptions)
    assertTrue(result)

    // validate annotation options from annotations map
    val actualOptions = viewAnnotationManager.annotations[view]
    assertEquals(expectedOptions, actualOptions)

    // validate annotation size after update options
    assertEquals(1, viewAnnotationManager.annotations.size)
  }

  @Test
  fun validateViewAnnotationSizeAfterAddingDuplicateView() {
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    assertEquals(1, viewAnnotationManager.annotations.size)

    assertThrows(RuntimeException::class.java) {
      viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    }
    assertEquals(1, viewAnnotationManager.annotations.size)
  }

  @Test
  fun testCameraForAnnotationsWithEmptyViews() {
    val annotations = listOf<View>()
    assertNull(viewAnnotationManager.cameraForAnnotations(annotations))
  }

  @Test
  fun testCameraForAnnotationsWithValidViews() {
    val annotations = mutableListOf<View>()
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    annotations.add(view)
    every { mapboxMap.getViewAnnotationOptions(any()) } returns ExpectedFactory.createValue(
      viewAnnotationOptions
    )

    val point = Point.fromJson(DEFAULT_GEOMETRY.toJson())
    val zoom = 1.0
    val expectedCameraOptions = CameraOptions.Builder().center(point).zoom(zoom).build()
    every {
      mapboxMap.cameraForCoordinates(
        any(),
        any(),
        any(),
        any()
      )
    } answers { expectedCameraOptions }
    val coordinateBounds = mockk<CoordinateBounds>(relaxUnitFun = true)
    every { coordinateBounds.north() } returns 20.0
    every { coordinateBounds.east() } returns 20.0
    every { coordinateBounds.west() } returns 10.0
    every { coordinateBounds.south() } returns 10.0

    every { mapboxMap.coordinateBoundsForCamera(any()) } returns coordinateBounds
    every { mapboxMap.getMetersPerPixelAtLatitude(any(), any()) } returns 1.0
    every { mapboxMap.projectedMetersForCoordinate(any()) } returns ProjectedMeters(1.0, 1.0)
    every { mapboxMap.coordinateForProjectedMeters(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapboxMap.cameraForCoordinateBounds(any()) } returns expectedCameraOptions

    val cameraOptionsActual = viewAnnotationManager.cameraForAnnotations(annotations)
    assertNotNull(cameraOptionsActual)
    assertEquals(expectedCameraOptions, cameraOptionsActual!!)
  }

  @Test
  fun testCameraForAnnotationsWithGlobeProjection() {
    every { style.getStyleProjectionProperty("name") } returns
      StylePropertyValue(Value(ProjectionName.GLOBE.value), StylePropertyValueKind.CONSTANT)

    val annotations = mutableListOf<View>()
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    annotations.add(view)

    assertNull(viewAnnotationManager.cameraForAnnotations(annotations))
  }

  @Test
  fun testCameraForAnnotationsWithInvisibleViews() {
    val annotations = mutableListOf<View>()
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    every { view.visibility } returns View.INVISIBLE
    viewAnnotationManager.addViewAnnotation(view, viewAnnotationOptions)
    annotations.add(view)

    assertNull(viewAnnotationManager.cameraForAnnotations(annotations))
  }

  @Test
  fun testCalculateCoordinateBoundForAnnotation() {
    val annotationManagerSpyk = spyk(ViewAnnotationManagerImpl(mapView, viewAnnotationsLayout))
    val viewAnnotationOptions = viewAnnotationOptions {
      geometry(DEFAULT_GEOMETRY)
    }
    val rect = Rect(10, 10, 10, 10)
    val projectedMeters = ProjectedMeters(1.0, 1.0)
    every { annotationManagerSpyk.getViewAnnotationOptionsFrame(any()) } returns rect
    every { mapboxMap.getMetersPerPixelAtLatitude(any(), any()) } returns 1.0
    every { mapboxMap.projectedMetersForCoordinate(any()) } returns projectedMeters
    every { mapboxMap.coordinateForProjectedMeters(any()) } returns Point.fromLngLat(0.0, 0.0)

    val expectedCoordinateBounds =
      CoordinateBounds(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))
    annotationManagerSpyk.addViewAnnotation(view, viewAnnotationOptions)
    val actualCoordinateBounds =
      annotationManagerSpyk.calculateCoordinateBoundForAnnotation(viewAnnotationOptions, rect, 1.0)
    assertEquals(expectedCoordinateBounds, actualCoordinateBounds)
  }

  private companion object {
    private const val FEATURE_ID = "featureId"
    private val DEFAULT_GEOMETRY: Geometry = Point.fromLngLat(0.0, 0.0)
    private const val DEFAULT_WIDTH = 20
    private const val DEFAULT_HEIGHT = 20
  }
}