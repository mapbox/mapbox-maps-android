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
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.QueriedFeature
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.Plugin
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
class PolylineAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleInterface = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: LineLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private val queriedFeatures = mockk<Expected<String, List<QueriedFeature>>>()
  private val queriedFeature = mockk<QueriedFeature>()
  private val feature = mockk<Feature>()
  private val queriedFeatureList = listOf(queriedFeature)
  private val querySlot = slot<QueryFeaturesCallback>()
  private val executeQuerySlot = slot<Runnable>()

  private lateinit var manager: PolylineAnnotationManager
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
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(Plugin.MAPBOX_GESTURES_PLUGIN_ID) } returns gesturesPlugin
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

    manager = PolylineAnnotationManager(mapView, delegateProvider)
    manager.layer = layer
    manager.source = source
    every { layer.lineJoin(any<Expression>()) } answers { layer }
    every { layer.lineSortKey(any<Expression>()) } answers { layer }
    every { layer.lineBlur(any<Expression>()) } answers { layer }
    every { layer.lineColor(any<Expression>()) } answers { layer }
    every { layer.lineGapWidth(any<Expression>()) } answers { layer }
    every { layer.lineOffset(any<Expression>()) } answers { layer }
    every { layer.lineOpacity(any<Expression>()) } answers { layer }
    every { layer.linePattern(any<Expression>()) } answers { layer }
    every { layer.lineWidth(any<Expression>()) } answers { layer }
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(PolylineAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = PolylineAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
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
  fun createWithGeoJsonOptions() {
    manager = PolylineAnnotationManager(
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
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(LineString.fromLngLats(listOf(Point.fromLngLat(0.0, 0.0)))))
    val annotations = manager.create(featureCollection.toJson())
    assertEquals(annotations.first(), manager.annotations[0])
    val annotations1 = manager.create(featureCollection)
    assertEquals(annotations1.first(), manager.annotations[1])
  }

  @Test
  fun createList() {
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))))
    assertEquals(annotation, manager.annotations[0])
    annotation.points = listOf(Point.fromLngLat(1.0, 1.0), Point.fromLngLat(1.0, 1.0))
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun updateList() {
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
    annotations[0].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    annotations[1].points = listOf(Point.fromLngLat(1.0, 0.0), Point.fromLngLat(1.0, 0.0))
    manager.update(annotations)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun delete() {
    val annotation = manager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
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
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0))),
      PolylineAnnotationOptions().withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
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
    val manager = PolylineAnnotationManager(mapView, delegateProvider)
    val annotation = manager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnPolylineAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    val interactionListener = mockk<OnPolylineAnnotationInteractionListener>()
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
    val manager = PolylineAnnotationManager(mapView, delegateProvider)

    val annotation = manager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnPolylineAnnotationLongClickListener>()
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
    val manager = PolylineAnnotationManager(mapView, delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PolylineAnnotationOptions()
        .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asLong } returns 0L

    val listener = mockk<OnPolylineAnnotationDragListener>()
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
  fun testLineJoinLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineJoin(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineJoin(LineJoin.MITER)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineJoin(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineJoin(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_JOIN)) }
  }

  @Test
  fun testLineSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineSortKey(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineSortKey(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineSortKey(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_SORT_KEY)) }
  }

  @Test
  fun testLineBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineBlur(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineBlur(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineBlur(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_BLUR)) }
  }

  @Test
  fun testLineColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
  }

  @Test
  fun testLineColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineColor(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_COLOR)) }
  }

  @Test
  fun testLineGapWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineGapWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineGapWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineGapWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineGapWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_GAP_WIDTH)) }
  }

  @Test
  fun testLineOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineOffset(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineOffset(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineOffset(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineOffset(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OFFSET)) }
  }

  @Test
  fun testLineOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineOpacity(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineOpacity(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineOpacity(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_OPACITY)) }
  }

  @Test
  fun testLinePatternLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.linePattern(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLinePattern("pedestrian-polygon")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.linePattern(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.linePattern(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_PATTERN)) }
  }

  @Test
  fun testLineWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.lineWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)) }
    val options = PolylineAnnotationOptions()
      .withPoints(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(0.0, 0.0)))
      .withLineWidth(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.lineWidth(Expression.get(PolylineAnnotationOptions.PROPERTY_LINE_WIDTH)) }
  }
}