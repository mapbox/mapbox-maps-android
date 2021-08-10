// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Color
import android.graphics.PointF
import android.os.HandlerThread
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
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.QueriedFeature
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.annotation.*
import com.mapbox.maps.plugin.delegates.*
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
class PolygonAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleInterface = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: FillLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private val queriedFeatures = mockk<Expected<String, List<QueriedFeature>>>()
  private val queriedFeature = mockk<QueriedFeature>()
  private val feature = mockk<Feature>()
  private val queriedFeatureList = listOf(queriedFeature)
  private val querySlot = slot<QueryFeaturesCallback>()
  private val executeQuerySlot = slot<Runnable>()

  private lateinit var manager: PolygonAnnotationManager
  @Before
  fun setUp() {
    GeoJsonSource.workerThread =
      HandlerThread("STYLE_WORKER").apply {
        priority = Thread.MAX_PRIORITY
        start()
      }
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic(ValueConverter::class)
    every { ValueConverter.fromJson(any()) } returns ExpectedFactory.createValue(
      Value(1)
    )
    val captureCallback = slot<(StyleInterface) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    val styleStateDelegate = mockk<MapStyleStateDelegate>()
    every { delegateProvider.styleStateDelegate } returns styleStateDelegate
    every { styleStateDelegate.isFullyLoaded() } returns true
    every { style.addSource(any()) } just Runs
    every { style.getSource(any()) } returns null
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addPersistentLayer(any(), any()) } just Runs
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { style.removeStyleLayer(any()) } returns mockk()
    every { style.removeStyleSource(any()) } returns mockk()
    every { style.pixelRatio } returns 1.0f
    every { style.getStyleImage(any()) } returns null
    every { gesturesPlugin.addOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMapLongClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapLongClickListener(any()) } just Runs
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(any()) } returns gesturesPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapFeatureQueryDelegate } returns mapFeatureQueryDelegate
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(1.0, 1.0)
    every { mapView.scrollX } returns 0
    every { mapView.scrollY } returns 0
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any(), any()) } answers { source }

    every { queriedFeature.feature } returns feature
    every { queriedFeatures.value } returns queriedFeatureList
    every { feature.getProperty(any()).asLong } returns 0L
    every { mapFeatureQueryDelegate.executeOnRenderThread(capture(executeQuerySlot)) } answers {
      executeQuerySlot.captured.run()
    }
    every {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        any<ScreenCoordinate>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(queriedFeatures)
    }

    manager = PolygonAnnotationManager(mapView, delegateProvider)
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
    assertEquals(PolygonAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = PolygonAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
    verify { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }

    manager.addClickListener(mockk())
    manager.addDragListener(mockk())
    manager.addLongClickListener(mockk())
    assertEquals(1, manager.dragListeners.size)
    assertEquals(1, manager.clickListeners.size)
    assertEquals(1, manager.longClickListeners.size)
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    manager.onDestroy()
    verify { style.removeStyleLayer(any()) }
    verify { style.removeStyleSource(any()) }
    verify { gesturesPlugin.removeOnMapClickListener(any()) }
    verify { gesturesPlugin.removeOnMapLongClickListener(any()) }
    verify { gesturesPlugin.removeOnMoveListener(any()) }
    assertTrue(manager.dragListeners.isEmpty())
    assertTrue(manager.clickListeners.isEmpty())
    assertTrue(manager.longClickListeners.isEmpty())
  }

  @Test
  fun initializeBeforeStyleLoad() {
    every { style.styleLayerExists("test_layer") } returns true
    val captureCallback = slot<(StyleInterface) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } just Runs
    manager = PolygonAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, can't create and add layer to style
    verify(exactly = 0) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    // Style is loaded, will create and add layer to style while creating annotations
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
  }

  @Test
  fun createWithGeoJsonOptions() {
    manager = PolygonAnnotationManager(
      mapView, delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0
        )
      )
    )

    val sourceString = manager.source.toString()
    assertTrue(sourceString.contains("maxzoom = 10"))
    assertTrue(sourceString.contains("lineMetrics = true"))
    assertTrue(sourceString.contains("buffer = 20"))
    assertTrue(sourceString.contains("tolerance = 10.0"))
  }

  @Test
  fun create() {
    val annotation = manager.create(
      PolygonAnnotationOptions()
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
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))))
    assertEquals(annotation, manager.annotations[0])
    annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun updateList() {
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
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
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
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
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))),
      PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
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
    val manager = PolygonAnnotationManager(mapView, delegateProvider)
    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnPolygonAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    val interactionListener = mockk<OnPolygonAnnotationInteractionListener>()
    every { interactionListener.onSelectAnnotation(any()) } just Runs
    every { interactionListener.onDeselectAnnotation(any()) } just Runs
    manager.addInteractionListener(interactionListener)

    captureSlot.captured.onMapClick(Point.fromLngLat(0.0, 0.0))
    verify { listener.onAnnotationClick(annotation) }
    verify { interactionListener.onSelectAnnotation(annotation) }
    captureSlot.captured.onMapClick(Point.fromLngLat(0.0, 0.0))
    verify { interactionListener.onDeselectAnnotation(annotation) }

    manager.removeClickListener(listener)
    assertTrue(manager.clickListeners.isEmpty())
    manager.removeInteractionListener(interactionListener)
    assertTrue(manager.interactionListener.isEmpty())
  }

  @Test
  fun longClick() {
    val captureSlot = slot<OnMapLongClickListener>()
    every { gesturesPlugin.addOnMapLongClickListener(capture(captureSlot)) } just Runs
    val manager = PolygonAnnotationManager(mapView, delegateProvider)

    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnPolygonAnnotationLongClickListener>()
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
    val manager = PolygonAnnotationManager(mapView, delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asLong } returns 0L

    val listener = mockk<OnPolygonAnnotationDragListener>()
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
    verify(exactly = 0) { manager.layer?.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
  }

  @Test
  fun testFillColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
  }

  @Test
  fun testFillOutlineColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOutlineColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillOutlineColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOutlineColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillPatternLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillPattern("pedestrian-polygon")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
  }
}