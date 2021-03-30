// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
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
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.plugin.annotation.*
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
class PointAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleManagerInterface = mockk()
  private val mapProjectionDelegate: MapProjectionDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: SymbolLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private lateinit var manager: PointAnnotationManager
  val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerKt")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceKt")
    mockkStatic(ValueConverter::class)
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
    every { style.getStyleImage(any()) } returns null
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
    manager = PointAnnotationManager(mapView, delegateProvider)
    manager.layer = layer
    manager.source = source
    val expected = mockk<Expected<Void, String>>(relaxUnitFun = true, relaxed = true)
    every { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { layer.iconAnchor(any<Expression>()) } answers { layer }
    every { layer.iconImage(any<Expression>()) } answers { layer }
    every { layer.iconOffset(any<Expression>()) } answers { layer }
    every { layer.iconRotate(any<Expression>()) } answers { layer }
    every { layer.iconSize(any<Expression>()) } answers { layer }
    every { layer.symbolSortKey(any<Expression>()) } answers { layer }
    every { layer.textAnchor(any<Expression>()) } answers { layer }
    every { layer.textField(any<Expression>()) } answers { layer }
    every { layer.textFont(any<Expression>()) } answers { layer }
    every { layer.textJustify(any<Expression>()) } answers { layer }
    every { layer.textLetterSpacing(any<Expression>()) } answers { layer }
    every { layer.textMaxWidth(any<Expression>()) } answers { layer }
    every { layer.textOffset(any<Expression>()) } answers { layer }
    every { layer.textRadialOffset(any<Expression>()) } answers { layer }
    every { layer.textRotate(any<Expression>()) } answers { layer }
    every { layer.textSize(any<Expression>()) } answers { layer }
    every { layer.textTransform(any<Expression>()) } answers { layer }
    every { layer.iconColor(any<Expression>()) } answers { layer }
    every { layer.iconHaloBlur(any<Expression>()) } answers { layer }
    every { layer.iconHaloColor(any<Expression>()) } answers { layer }
    every { layer.iconHaloWidth(any<Expression>()) } answers { layer }
    every { layer.iconOpacity(any<Expression>()) } answers { layer }
    every { layer.textColor(any<Expression>()) } answers { layer }
    every { layer.textHaloBlur(any<Expression>()) } answers { layer }
    every { layer.textHaloColor(any<Expression>()) } answers { layer }
    every { layer.textHaloWidth(any<Expression>()) } answers { layer }
    every { layer.textOpacity(any<Expression>()) } answers { layer }
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(PointAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addLayer(any()) }
    manager = PointAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
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
    manager = PointAnnotationManager(
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
  fun createWithClusterOptions() {
    manager = PointAnnotationManager(
      mapView, delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0,
          ClusterOptions(
            clusterRadius = 30,
            clusterMaxZoom = 15,
            clusterProperties = hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>
          )
        )
      )
    )

    val sourceString = manager.source.toString()
    assertTrue(sourceString.contains("maxzoom = 10"))
    assertTrue(sourceString.contains("cluster = true"))
    assertTrue(sourceString.contains("clusterRadius = 30"))
    assertTrue(sourceString.contains("clusterMaxZoom = 15"))
    assertTrue(sourceString.contains("clusterProperties = {key1=x, key2=y}"))
    assertTrue(sourceString.contains("lineMetrics = true"))
    assertTrue(sourceString.contains("buffer = 20"))
    assertTrue(sourceString.contains("tolerance = 10.0"))
  }

  @Test
  fun iconImageBitmapWithIconImage() {
    every { style.styleSourceExists(any()) } returns true
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage("car-15")
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals("car-15", annotation.iconImage)
    verify(exactly = 0) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
  }

  @Test
  fun iconImageBitmapWithoutIconImage() {
    every { style.styleSourceExists(any()) } returns true
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.generationId, annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
  }

  @Test
  fun iconImageBitmapWithSameImage() {
    every { style.styleSourceExists(any()) } returns true
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.generationId, annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }

    every { style.getStyleImage(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.generationId) } returns mockk()
    val annotation2 = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.generationId, annotation2.iconImage)

    verify(exactly = 1) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
  }

  @Test
  fun updateIconImageBitmap() {
    every { style.styleSourceExists(any()) } returns true
    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    verify(exactly = 0) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
    annotation.iconImageBitmap = bitmap
    manager.update(annotation)
    verify(exactly = 1) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
  }
  @Test
  fun create() {
    val annotation = manager.create(
      PointAnnotationOptions()
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
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
    )
    val annotations = manager.create(list)
    assertEquals(annotations[0], manager.annotations[0])
    assertEquals(annotations[1], manager.annotations[1])
  }

  @Test
  fun update() {
    val annotation = manager.create(PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))
    assertEquals(annotation, manager.annotations[0])
    annotation.point = Point.fromLngLat(1.0, 1.0)
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])
  }

  @Test
  fun updateList() {
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
  }

  @Test
  fun deleteList() {
    val list = listOf(
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)),
      PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0))
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
    val manager = PointAnnotationManager(mapView, delegateProvider)
    val annotation = manager.create(
      PointAnnotationOptions()
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

    val listener = mockk<OnPointAnnotationClickListener>()
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
    val manager = PointAnnotationManager(mapView, delegateProvider)

    val annotation = manager.create(
      PointAnnotationOptions()
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

    val listener = mockk<OnPointAnnotationLongClickListener>()
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
    val manager = PointAnnotationManager(mapView, delegateProvider)
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PointAnnotationOptions()
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

    val listener = mockk<OnPointAnnotationDragListener>()
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
  fun testIconAnchorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconAnchor(IconAnchor.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
  }

  @Test
  fun testIconImageLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconImage("")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
  }

  @Test
  fun testIconOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconOffset(listOf(0.0, 0.0))
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
  }

  @Test
  fun testIconRotateLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconRotate(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
  }

  @Test
  fun testIconSizeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconSize(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
  }

  @Test
  fun testSymbolSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withSymbolSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
  }

  @Test
  fun testTextAnchorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextAnchor(TextAnchor.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
  }

  @Test
  fun testTextFieldLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextField("")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
  }

  @Test
  fun testTextFontLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textFont(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FONT)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textFont(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FONT)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textFont(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FONT)) }
  }

  @Test
  fun testTextJustifyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextJustify(TextJustify.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
  }

  @Test
  fun testTextLetterSpacingLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextLetterSpacing(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
  }

  @Test
  fun testTextMaxWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextMaxWidth(10.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
  }

  @Test
  fun testTextOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextOffset(listOf(0.0, 0.0))
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
  }

  @Test
  fun testTextRadialOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextRadialOffset(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
  }

  @Test
  fun testTextRotateLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextRotate(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
  }

  @Test
  fun testTextSizeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextSize(16.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
  }

  @Test
  fun testTextTransformLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextTransform(TextTransform.NONE)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
  }

  @Test
  fun testIconColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
  }

  @Test
  fun testIconColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
  }

  @Test
  fun testIconHaloBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
  }

  @Test
  fun testIconHaloColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
  }

  @Test
  fun testIconHaloColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
  }

  @Test
  fun testIconHaloWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
  }

  @Test
  fun testIconOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
  }

  @Test
  fun testTextColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
  }

  @Test
  fun testTextColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
  }

  @Test
  fun testTextHaloBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
  }

  @Test
  fun testTextHaloColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
  }

  @Test
  fun testTextHaloColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
  }

  @Test
  fun testTextHaloWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
  }

  @Test
  fun testTextOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    verify(exactly = 0) { manager.layer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
  }
}