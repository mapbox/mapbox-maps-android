// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Color
import android.graphics.PointF
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.*
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CircleAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleInterface = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val mapListenerDelegate: MapListenerDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: CircleLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: CircleLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()
  private val queriedFeatures = mockk<Expected<String, List<QueriedFeature>>>()
  private val queriedFeature = mockk<QueriedFeature>()
  private val cancelable = mockk<Cancelable>()
  private val feature = mockk<Feature>()
  private val queriedFeatureList = listOf(queriedFeature)
  private val querySlot = slot<QueryFeaturesCallback>()
  private val executeQuerySlot = slot<Runnable>()

  private lateinit var manager: CircleAnnotationManager
  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    mockkObject(GeoJsonSource)
    every { GeoJsonSource.directSetterEnabled() } returns false
    val captureCallback = slot<(StyleInterface) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    val styleStateDelegate = mockk<MapStyleStateDelegate>()
    every { delegateProvider.styleStateDelegate } returns styleStateDelegate
    every { style.addSource(any()) } just Runs
    every { style.getSource(any()) } returns null
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addPersistentLayer(any(), any()) } just Runs
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { style.removeStyleLayer(any()) } returns mockk()
    every { style.removeStyleSource(any()) } returns mockk()
    every { style.pixelRatio } returns 1.0f
    every { gesturesPlugin.addOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMapLongClickListener(any()) } just Runs
    every { gesturesPlugin.addOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMoveListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapClickListener(any()) } just Runs
    every { gesturesPlugin.removeOnMapLongClickListener(any()) } just Runs
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(any()) } returns gesturesPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapFeatureQueryDelegate } returns mapFeatureQueryDelegate
    every { delegateProvider.mapListenerDelegate } returns mapListenerDelegate
    every { mapListenerDelegate.addOnMapIdleListener(any()) } just Runs
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(1.0, 1.0)
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any()) } answers { source }

    every { queriedFeature.feature } returns feature
    every { queriedFeatures.value } returns queriedFeatureList
    every { feature.getProperty(any()).asLong } returns 0L
    every { mapFeatureQueryDelegate.executeOnRenderThread(capture(executeQuerySlot)) } answers {
      executeQuerySlot.captured.run()
    }
    every {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        any<RenderedQueryGeometry>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(queriedFeatures)
      cancelable
    }

    manager = CircleAnnotationManager(delegateProvider)
    manager.layer = layer
    manager.source = source
    manager.dragLayer = dragLayer
    manager.dragSource = dragSource
    every { layer.circleSortKey(any<Expression>()) } answers { layer }
    every { dragLayer.circleSortKey(any<Expression>()) } answers { dragLayer }
    every { layer.circleBlur(any<Expression>()) } answers { layer }
    every { dragLayer.circleBlur(any<Expression>()) } answers { dragLayer }
    every { layer.circleColor(any<Expression>()) } answers { layer }
    every { dragLayer.circleColor(any<Expression>()) } answers { dragLayer }
    every { layer.circleOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.circleOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.circleRadius(any<Expression>()) } answers { layer }
    every { dragLayer.circleRadius(any<Expression>()) } answers { dragLayer }
    every { layer.circleStrokeColor(any<Expression>()) } answers { layer }
    every { dragLayer.circleStrokeColor(any<Expression>()) } answers { dragLayer }
    every { layer.circleStrokeOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.circleStrokeOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.circleStrokeWidth(any<Expression>()) } answers { layer }
    every { dragLayer.circleStrokeWidth(any<Expression>()) } answers { dragLayer }
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(CircleAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = CircleAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
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
    manager = CircleAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, can't create and add layer to style
    verify(exactly = 0) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    // Style is loaded, will create and add layer to style while creating annotations
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
  }

  @Test
  fun createWithGeoJsonOptions() {
    manager = CircleAnnotationManager(
      delegateProvider,
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
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun createFromFeature() {
    val featureCollection =
      FeatureCollection.fromFeature(Feature.fromGeometry(Point.fromLngLat(0.0, 0.0)))
    val annotations = manager.create(featureCollection.toJson())
    assertEquals(annotations.first(), manager.annotations[0])
    val annotations1 = manager.create(featureCollection)
    assertEquals(annotations1.first(), manager.annotations[1])
  }

  @Test
  fun createList() {
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, manager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun annotationPropertiesUpdate() {
    val annotation = manager.create(CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))

    annotation.circleSortKey = 1.0
    assertEquals(1.0, annotation.circleSortKey)
    annotation.circleSortKey = null
    assertNull(annotation.circleSortKey)

    annotation.circleBlur = 0.0
    assertEquals(0.0, annotation.circleBlur)
    annotation.circleBlur = null
    assertNull(annotation.circleBlur)

    annotation.circleColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.circleColorInt)
    annotation.circleColorInt = null
    assertNull(annotation.circleColorInt)

    annotation.circleColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.circleColorString)
    annotation.circleColorString = null
    assertNull(annotation.circleColorString)

    annotation.circleOpacity = 1.0
    assertEquals(1.0, annotation.circleOpacity)
    annotation.circleOpacity = null
    assertNull(annotation.circleOpacity)

    annotation.circleRadius = 5.0
    assertEquals(5.0, annotation.circleRadius)
    annotation.circleRadius = null
    assertNull(annotation.circleRadius)

    annotation.circleStrokeColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.circleStrokeColorInt)
    annotation.circleStrokeColorInt = null
    assertNull(annotation.circleStrokeColorInt)

    annotation.circleStrokeColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.circleStrokeColorString)
    annotation.circleStrokeColorString = null
    assertNull(annotation.circleStrokeColorString)

    annotation.circleStrokeOpacity = 1.0
    assertEquals(1.0, annotation.circleStrokeOpacity)
    annotation.circleStrokeOpacity = null
    assertNull(annotation.circleStrokeOpacity)

    annotation.circleStrokeWidth = 0.0
    assertEquals(0.0, annotation.circleStrokeWidth)
    annotation.circleStrokeWidth = null
    assertNull(annotation.circleStrokeWidth)
  }

  @Test
  fun updateList() {
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
    annotations[0].point = Point.fromLngLat(1.0, 1.0)
    annotations[1].point = Point.fromLngLat(1.0, 1.0)
    manager.update(annotations)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun delete() {
    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])

    manager.deleteAll()
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun clickWithNoAnnotation() {
    val captureSlot = slot<OnMapClickListener>()
    every { gesturesPlugin.addOnMapClickListener(capture(captureSlot)) } just Runs
    val manager = CircleAnnotationManager(delegateProvider)

    val listener = mockk<OnCircleAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    every { feature.getProperty(any()) } returns null
    captureSlot.captured.onMapClick(Point.fromLngLat(0.0, 0.0))
    verify(exactly = 0) { listener.onAnnotationClick(any()) }
  }

  @Test
  fun click() {
    val captureSlot = slot<OnMapClickListener>()
    every { gesturesPlugin.addOnMapClickListener(capture(captureSlot)) } just Runs
    val manager = CircleAnnotationManager(delegateProvider)
    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnCircleAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    val interactionListener = mockk<OnCircleAnnotationInteractionListener>()
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
    val manager = CircleAnnotationManager(delegateProvider)

    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    val listener = mockk<OnCircleAnnotationLongClickListener>()
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
    val manager = CircleAnnotationManager(delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asLong } returns 0L

    val listener = mockk<OnCircleAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val moveGestureDetector = mockk<MoveGestureDetector>()
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    captureSlot.captured.onMoveBegin(moveGestureDetector)
    verify { listener.onAnnotationDragStarted(annotation) }
    assertEquals(1, manager.annotations.size)

    val moveDistancesObject = mockk<MoveDistancesObject>()
    every { moveDistancesObject.currentX } returns 1f
    every { moveDistancesObject.currentY } returns 1f
    every { moveDistancesObject.distanceXSinceLast } returns 1f
    every { moveDistancesObject.distanceYSinceLast } returns 1f
    every { moveGestureDetector.getMoveObject(any()) } returns moveDistancesObject
    captureSlot.captured.onMove(moveGestureDetector)
    verify { listener.onAnnotationDrag(annotation) }
    assertEquals(1, manager.annotations.size)

    captureSlot.captured.onMoveEnd(moveGestureDetector)
    verify { listener.onAnnotationDragFinished(annotation) }

    manager.removeDragListener(listener)
    assertTrue(manager.dragListeners.isEmpty())
    assertEquals(1, manager.annotations.size)

    // Verify update after drag
    annotation.point = Point.fromLngLat(1.0, 1.0)
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])

    // Verify delete after drag
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun testCircleSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer?.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer?.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
  }

  @Test
  fun testCircleBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    verify(exactly = 1) { manager.dragLayer?.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    verify(exactly = 1) { manager.dragLayer?.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
  }

  @Test
  fun testCircleColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
  }

  @Test
  fun testCircleRadiusLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleRadius(5.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    verify(exactly = 1) { manager.dragLayer?.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    verify(exactly = 1) { manager.dragLayer?.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
  }

  @Test
  fun testCircleStrokeColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
  }

  @Test
  fun testCircleStrokeWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer?.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
  }
}