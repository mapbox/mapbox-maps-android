package com.mapbox.maps.plugin.annotation

import android.view.View
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import io.mockk.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class, ShadowLogger::class])
class AnnotationPluginImplTest {
  private val delegateProvider: MapDelegateProvider = mockk(relaxed = true)
  private val style: StyleInterface = mockk(relaxed = true)
  private val gesturesPlugin: GesturesPlugin = mockk(relaxed = true)
  private val mapView: View = mockk(relaxed = true)
  private lateinit var annotationPluginImpl: AnnotationPluginImpl

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")

    val styleStateDelegate = mockk<MapStyleStateDelegate>()
    every { delegateProvider.styleStateDelegate } returns styleStateDelegate
    every { styleStateDelegate.isFullyLoaded() } returns true
    every { style.addSource(any()) } just Runs
    every { style.addLayer(any()) } just Runs
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { delegateProvider.mapPluginProviderDelegate.getPlugin(any<Class<GesturesPlugin>>()) } returns gesturesPlugin

    annotationPluginImpl = AnnotationPluginImpl()
    annotationPluginImpl.onDelegateProvider(delegateProvider)
  }

  @Test
  fun createAndRemove() {
    val circleAnnotationManager =
      annotationPluginImpl.createAnnotationManager(mapView, AnnotationType.CircleAnnotation, null)
    assert(circleAnnotationManager is CircleAnnotationManager)
    val pointAnnotationManager =
      annotationPluginImpl.createAnnotationManager(mapView, AnnotationType.PointAnnotation, null)
    assert(pointAnnotationManager is PointAnnotationManager)
    val polygonAnnotationManager =
      annotationPluginImpl.createAnnotationManager(mapView, AnnotationType.PolygonAnnotation, null)
    assert(polygonAnnotationManager is PolygonAnnotationManager)
    val polylineAnnotationManager =
      annotationPluginImpl.createAnnotationManager(mapView, AnnotationType.PolylineAnnotation, null)
    assert(polylineAnnotationManager is PolylineAnnotationManager)
    assertEquals(4, annotationPluginImpl.managerList.size)

    annotationPluginImpl.removeAnnotationManager(circleAnnotationManager)
    annotationPluginImpl.removeAnnotationManager(pointAnnotationManager)
    annotationPluginImpl.removeAnnotationManager(polygonAnnotationManager)
    annotationPluginImpl.removeAnnotationManager(polylineAnnotationManager)
    assertEquals(0, annotationPluginImpl.managerList.size)
  }
}