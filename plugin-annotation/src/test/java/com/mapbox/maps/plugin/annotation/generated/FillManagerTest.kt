// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.PointF
import android.view.View
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.ShadowLogger
import com.mapbox.maps.plugin.annotation.ShadowValueConverter
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class, ShadowLogger::class])
class FillManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleManagerInterface = mockk()
  private val mapProjectionDelegate: MapProjectionDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: FillLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private lateinit var manager: FillManager
  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerKt")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceKt")
    mockkStatic(ValueConverter::class)
    every { delegateProvider.mapListenerDelegate.addOnDidFinishRenderingMapListener(any()) } just Runs
    every { ValueConverter.fromJson(any()) } returns ExpectedFactory.createValue(
      Value(1)
    )
    val captureCallback = slot<(StyleManagerInterface) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    val styleStateDelegate = mockk<MapStyleStateDelegate>()
    every { delegateProvider.styleStateDelegate } returns styleStateDelegate
    every { styleStateDelegate.isFullyLoaded() } returns true
    every { style.addSource(any()) } just Runs
    every { style.addLayer(any()) } just Runs
    every { style.addLayerBelow(any(), any()) } just Runs
    every { style.getSource(any()) } returns null
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { gesturesPlugin.addOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMapLongClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapLongClickListener(any()) } just Runs
    every { delegateProvider.mapPluginProviderDelegate.getPlugin(any<Class<GesturesPlugin>>()) } returns gesturesPlugin
    every { delegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
    every { delegateProvider.mapFeatureQueryDelegate } returns mapFeatureQueryDelegate
    every { mapProjectionDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapProjectionDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(1.0, 1.0)
    every { mapView.scrollX } returns 0
    every { mapView.scrollY } returns 0
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any()) } answers { source }
    manager = FillManager(mapView, delegateProvider)
    manager.layer = layer
    manager.source = source
    every { layer.fillSortKey(any<Expression>()) } answers { layer }
    every { layer.fillColor(any<Expression>()) } answers { layer }
    every { layer.fillOpacity(any<Expression>()) } answers { layer }
    every { layer.fillOutlineColor(any<Expression>()) } answers { layer }
    every { layer.fillPattern(any<Expression>()) } answers { layer }
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(Fill.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addLayer(any()) }
    manager = FillManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
    verify { style.addLayerBelow(any(), "test_layer") }

    manager.addClickListener(mockk())
    manager.addDragListener(mockk())
    manager.addLongClickListener(mockk())
    assertEquals(1, manager.dragListeners.size)
    assertEquals(1, manager.clickListeners.size)
    assertEquals(1, manager.longClickListeners.size)
    manager.onDestroy()
    verify { gesturesPlugin.removeOnMapClickListener(any()) }
    verify { gesturesPlugin.removeOnMapLongClickListener(any()) }
    verify { gesturesPlugin.removeOnMoveListener(any()) }
    assertTrue(manager.dragListeners.isEmpty())
    assertTrue(manager.clickListeners.isEmpty())
    assertTrue(manager.longClickListeners.isEmpty())
  }

  @Test
  fun create() {
    val annotation = manager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Polygon.fromLngLats(listOf(listOf(Point.fromLngLat(0.0, 0.0))))))
    val annotations = manager.create(featureCollection.toJson())
    assertEquals(annotations.first(), manager.annotations[0])
    val annotations1 = manager.create(featureCollection)
    assertEquals(annotations1.first(), manager.annotations[1])
  }

  @Test
  fun createList() {
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))))
    assertEquals(annotation, manager.annotations[0])
    annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun updateList() {
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
    annotations[0].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    annotations[1].points = listOf(listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    manager.update(annotations)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun delete() {
    val annotation = manager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])

    manager.delete(annotations)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteAll() {
    val list = listOf(
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      FillOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])

    manager.deleteAll()
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun click() {
    val captureSlot = slot<OnMapClickListener>()
    every { gesturesPlugin.addOnMapClickListener(capture(captureSlot)) } just Runs
    val manager = FillManager(mapView, delegateProvider)
    val annotation = manager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    val features = mockk<Expected<List<Feature>, String>>()
    val feature = mockk<Feature>()
    val featureList = listOf(feature)

    every { features.value } returns featureList
    every { feature.getProperty(any()).asLong } returns 0L

    val querySlot = slot<QueryFeaturesCallback>()
    every {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        any<ScreenCoordinate>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(features)
    }

    val listener = mockk<OnFillClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)
    captureSlot.captured.onMapClick(Point.fromLngLat(0.0, 0.0))
    verify { listener.onAnnotationClick(annotation) }

    manager.removeClickListener(listener)
    assertTrue(manager.clickListeners.isEmpty())
  }

  @Test
  fun longClick() {
    val captureSlot = slot<OnMapLongClickListener>()
    every { gesturesPlugin.addOnMapLongClickListener(capture(captureSlot)) } just Runs
    val manager = FillManager(mapView, delegateProvider)

    val annotation = manager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    val features = mockk<Expected<List<Feature>, String>>()
    val feature = mockk<Feature>()
    val featureList = listOf(feature)

    every { features.value } returns featureList
    every { feature.getProperty(any()).asLong } returns 0L

    val querySlot = slot<QueryFeaturesCallback>()
    every {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        any<ScreenCoordinate>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(features)
    }

    val listener = mockk<OnFillLongClickListener>()
    every { listener.onAnnotationLongClick(any()) } returns false
    manager.addLongClickListener(listener)
    captureSlot.captured.onMapLongClick(Point.fromLngLat(0.0, 0.0))
    verify { listener.onAnnotationLongClick(annotation) }

    manager.removeLongClickListener(listener)
    assertTrue(manager.longClickListeners.isEmpty())
  }

  @Test
  fun drag() {
    val captureSlot = slot<OnMoveListener>()
    every { gesturesPlugin.addOnMoveListener(capture(captureSlot)) } just Runs
    val manager = FillManager(mapView, delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      FillOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    val features = mockk<Expected<List<Feature>, String>>()
    val feature = mockk<Feature>()
    val featureList = listOf(feature)

    every { features.value } returns featureList
    every { feature.getProperty(any()).asLong } returns 0L

    val querySlot = slot<QueryFeaturesCallback>()
    every {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        any<ScreenCoordinate>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(features)
    }

    val listener = mockk<OnFillDragListener>()
    every { listener.onAnnotationDragStarted(any()) } just Runs
    every { listener.onAnnotationDragFinished(any()) } just Runs
    every { listener.onAnnotationDrag(any()) } just Runs
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val moveGestureDetector = mockk<MoveGestureDetector>()
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    captureSlot.captured.onMoveBegin(moveGestureDetector)
    verify { listener.onAnnotationDragStarted(annotation) }

    val moveDistancesObject = mockk<MoveDistancesObject>()
    every { moveDistancesObject.currentX } returns 1f
    every { moveDistancesObject.currentY } returns 1f
    every { moveDistancesObject.distanceXSinceLast } returns 1f
    every { moveDistancesObject.distanceYSinceLast } returns 1f
    every { moveGestureDetector.getMoveObject(any()) } returns moveDistancesObject
    captureSlot.captured.onMove(moveGestureDetector)
    verify { listener.onAnnotationDrag(annotation) }

    captureSlot.captured.onMoveEnd(moveGestureDetector)
    verify { listener.onAnnotationDragFinished(annotation) }

    manager.removeDragListener(listener)
    assertTrue(manager.dragListeners.isEmpty())
  }

  @Test
  fun testFillSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillSortKey(Expression.get(FillOptions.PROPERTY_FILL_SORT_KEY)) }
    val options = FillOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillSortKey(Expression.get(FillOptions.PROPERTY_FILL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillSortKey(Expression.get(FillOptions.PROPERTY_FILL_SORT_KEY)) }
  }

  @Test
  fun testFillColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillColor(Expression.get(FillOptions.PROPERTY_FILL_COLOR)) }
    val options = FillOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(FillOptions.PROPERTY_FILL_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(FillOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillOpacity(Expression.get(FillOptions.PROPERTY_FILL_OPACITY)) }
    val options = FillOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOpacity(Expression.get(FillOptions.PROPERTY_FILL_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOpacity(Expression.get(FillOptions.PROPERTY_FILL_OPACITY)) }
  }

  @Test
  fun testFillOutlineColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillOutlineColor(Expression.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = FillOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOutlineColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(FillOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillPatternLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillPattern(Expression.get(FillOptions.PROPERTY_FILL_PATTERN)) }
    val options = FillOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillPattern("pedestrian-polygon")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillPattern(Expression.get(FillOptions.PROPERTY_FILL_PATTERN)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillPattern(Expression.get(FillOptions.PROPERTY_FILL_PATTERN)) }
  }
}