package com.mapbox.maps.plugin.annotation

import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.unmockkStatic
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AnnotationPluginImplTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: MapboxStyleManager = mockk(relaxed = true)
  private val gesturesPlugin: GesturesPlugin = mockk(relaxed = true)
  private lateinit var annotationPluginImpl: AnnotationPluginImpl

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { logE(any(), any()) } just Runs

    every { style.addSource(any()) } just Runs
    every { style.addLayer(any()) } just Runs
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { style.setStyleLayerProperty(any(), any(), any()) } returns ExpectedFactory.createNone()
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(Plugin.MAPBOX_GESTURES_PLUGIN_ID) } returns gesturesPlugin
    every { delegateProvider.mapStyleManagerDelegate } returns style
    every { delegateProvider.mapCameraManagerDelegate } returns mockk()
    every { delegateProvider.mapFeatureQueryDelegate } returns mockk()
    every { delegateProvider.mapListenerDelegate } returns mockk()

    annotationPluginImpl = AnnotationPluginImpl()
    annotationPluginImpl.onDelegateProvider(delegateProvider)
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    unmockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    unmockkStatic("com.mapbox.maps.MapboxLogger")
    unmockkAll()
  }

  @Test
  fun createAndRemove() {
    val circleAnnotationManager =
      annotationPluginImpl.createAnnotationManager(AnnotationType.CircleAnnotation, null)
    assert(circleAnnotationManager is CircleAnnotationManager)
    val pointAnnotationManager =
      annotationPluginImpl.createAnnotationManager(AnnotationType.PointAnnotation, null)
    assert(pointAnnotationManager is PointAnnotationManager)
    val polygonAnnotationManager =
      annotationPluginImpl.createAnnotationManager(AnnotationType.PolygonAnnotation, null)
    assert(polygonAnnotationManager is PolygonAnnotationManager)
    val polylineAnnotationManager =
      annotationPluginImpl.createAnnotationManager(AnnotationType.PolylineAnnotation, null)
    assert(polylineAnnotationManager is PolylineAnnotationManager)
    assertEquals(4, annotationPluginImpl.managerList.size)

    annotationPluginImpl.removeAnnotationManager(circleAnnotationManager)
    annotationPluginImpl.removeAnnotationManager(pointAnnotationManager)
    assertEquals(2, annotationPluginImpl.managerList.size)

    annotationPluginImpl.cleanup()
    assertEquals(0, annotationPluginImpl.managerList.size)
  }
}