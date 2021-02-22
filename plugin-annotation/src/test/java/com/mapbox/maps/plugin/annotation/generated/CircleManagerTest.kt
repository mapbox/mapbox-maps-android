// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Color
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
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
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
class CircleManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleManagerInterface = mockk()
  private val mapProjectionDelegate: MapProjectionDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: CircleLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private lateinit var manager: CircleManager
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
    manager = CircleManager(mapView, delegateProvider)
    manager.layer = layer
    manager.source = source
    every { layer.circleSortKey(any<Expression>()) } answers { layer }
    every { layer.circleBlur(any<Expression>()) } answers { layer }
    every { layer.circleColor(any<Expression>()) } answers { layer }
    every { layer.circleOpacity(any<Expression>()) } answers { layer }
    every { layer.circleRadius(any<Expression>()) } answers { layer }
    every { layer.circleStrokeColor(any<Expression>()) } answers { layer }
    every { layer.circleStrokeOpacity(any<Expression>()) } answers { layer }
    every { layer.circleStrokeWidth(any<Expression>()) } answers { layer }
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(Circle.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addLayer(any()) }
    manager = CircleManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
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
  fun createWithGeoJsonOptions() {
    manager = CircleManager(
      mapView, delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0, true, 10,
          (hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>)
        )
      )
    )

    val sourceString = manager.source.toString()
    assertTrue(sourceString.contains("maxzoom = 10"))
    assertTrue(sourceString.contains("cluster = true"))
    assertTrue(sourceString.contains("clusterProperties = {key1=x, key2=y}"))
    assertTrue(sourceString.contains("lineMetrics = true"))
    assertTrue(sourceString.contains("buffer = 20"))
    assertTrue(sourceString.contains("clusterMaxZoom = 10"))
    assertTrue(sourceString.contains("tolerance = 10.0"))
  }

  @Test
  fun create() {
    val annotation = manager.create(
      CircleOptions()
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
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, manager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun updateList() {
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      CircleOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
    val manager = CircleManager(mapView, delegateProvider)
    val annotation = manager.create(
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
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

    val listener = mockk<OnCircleClickListener>()
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
    val manager = CircleManager(mapView, delegateProvider)

    val annotation = manager.create(
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
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

    val listener = mockk<OnCircleLongClickListener>()
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
    val manager = CircleManager(mapView, delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      CircleOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
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

    val listener = mockk<OnCircleDragListener>()
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
  fun testCircleSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleSortKey(Expression.get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleSortKey(Expression.get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleSortKey(Expression.get(CircleOptions.PROPERTY_CIRCLE_SORT_KEY)) }
  }

  @Test
  fun testCircleBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleBlur(Expression.get(CircleOptions.PROPERTY_CIRCLE_BLUR)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleBlur(Expression.get(CircleOptions.PROPERTY_CIRCLE_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleBlur(Expression.get(CircleOptions.PROPERTY_CIRCLE_BLUR)) }
  }

  @Test
  fun testCircleColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_OPACITY)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_OPACITY)) }
  }

  @Test
  fun testCircleRadiusLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleRadius(Expression.get(CircleOptions.PROPERTY_CIRCLE_RADIUS)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleRadius(5.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleRadius(Expression.get(CircleOptions.PROPERTY_CIRCLE_RADIUS)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleRadius(Expression.get(CircleOptions.PROPERTY_CIRCLE_RADIUS)) }
  }

  @Test
  fun testCircleStrokeColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeColor(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeOpacity(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
  }

  @Test
  fun testCircleStrokeWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.circleStrokeWidth(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    val options = CircleOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeWidth(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.circleStrokeWidth(Expression.get(CircleOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
  }
}