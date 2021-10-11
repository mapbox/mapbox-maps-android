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
import com.mapbox.bindgen.None
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.QueriedFeature
import com.mapbox.maps.QueryFeaturesCallback
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class PointAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: StyleInterface = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapFeatureQueryDelegate: MapFeatureQueryDelegate = mockk()
  private val mapListenerDelegate: MapListenerDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val layer: SymbolLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: SymbolLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()
  private val mapView: View = mockk()
  private val queriedFeatures = mockk<Expected<String, List<QueriedFeature>>>()
  private val queriedFeature = mockk<QueriedFeature>()
  private val feature = mockk<Feature>()
  private val queriedFeatureList = listOf(queriedFeature)
  private val querySlot = slot<QueryFeaturesCallback>()
  private val executeQuerySlot = slot<Runnable>()

  private lateinit var manager: PointAnnotationManager
  val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
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
    every { mapView.scrollX } returns 0
    every { mapView.scrollY } returns 0
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
        any<ScreenCoordinate>(),
        any(),
        capture(querySlot)
      )
    } answers {
      querySlot.captured.run(queriedFeatures)
    }

    manager = PointAnnotationManager(mapView, delegateProvider)
    manager.layer = layer
    manager.source = source
    manager.dragLayer = dragLayer
    manager.dragSource = dragSource
    val expected = mockk<Expected<String, None>>(relaxUnitFun = true, relaxed = true)
    every { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) } returns expected
    every { expected.error } returns null
    every { layer.iconAnchor(any<Expression>()) } answers { layer }
    every { dragLayer.iconAnchor(any<Expression>()) } answers { dragLayer }
    every { layer.iconImage(any<Expression>()) } answers { layer }
    every { dragLayer.iconImage(any<Expression>()) } answers { dragLayer }
    every { layer.iconOffset(any<Expression>()) } answers { layer }
    every { dragLayer.iconOffset(any<Expression>()) } answers { dragLayer }
    every { layer.iconRotate(any<Expression>()) } answers { layer }
    every { dragLayer.iconRotate(any<Expression>()) } answers { dragLayer }
    every { layer.iconSize(any<Expression>()) } answers { layer }
    every { dragLayer.iconSize(any<Expression>()) } answers { dragLayer }
    every { layer.symbolSortKey(any<Expression>()) } answers { layer }
    every { dragLayer.symbolSortKey(any<Expression>()) } answers { dragLayer }
    every { layer.textAnchor(any<Expression>()) } answers { layer }
    every { dragLayer.textAnchor(any<Expression>()) } answers { dragLayer }
    every { layer.textField(any<Expression>()) } answers { layer }
    every { dragLayer.textField(any<Expression>()) } answers { dragLayer }
    every { layer.textJustify(any<Expression>()) } answers { layer }
    every { dragLayer.textJustify(any<Expression>()) } answers { dragLayer }
    every { layer.textLetterSpacing(any<Expression>()) } answers { layer }
    every { dragLayer.textLetterSpacing(any<Expression>()) } answers { dragLayer }
    every { layer.textMaxWidth(any<Expression>()) } answers { layer }
    every { dragLayer.textMaxWidth(any<Expression>()) } answers { dragLayer }
    every { layer.textOffset(any<Expression>()) } answers { layer }
    every { dragLayer.textOffset(any<Expression>()) } answers { dragLayer }
    every { layer.textRadialOffset(any<Expression>()) } answers { layer }
    every { dragLayer.textRadialOffset(any<Expression>()) } answers { dragLayer }
    every { layer.textRotate(any<Expression>()) } answers { layer }
    every { dragLayer.textRotate(any<Expression>()) } answers { dragLayer }
    every { layer.textSize(any<Expression>()) } answers { layer }
    every { dragLayer.textSize(any<Expression>()) } answers { dragLayer }
    every { layer.textTransform(any<Expression>()) } answers { layer }
    every { dragLayer.textTransform(any<Expression>()) } answers { dragLayer }
    every { layer.iconColor(any<Expression>()) } answers { layer }
    every { dragLayer.iconColor(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloBlur(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloBlur(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloColor(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloColor(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloWidth(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloWidth(any<Expression>()) } answers { dragLayer }
    every { layer.iconOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.iconOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.textColor(any<Expression>()) } answers { layer }
    every { dragLayer.textColor(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloBlur(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloBlur(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloColor(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloColor(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloWidth(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloWidth(any<Expression>()) } answers { dragLayer }
    every { layer.textOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.textOpacity(any<Expression>()) } answers { dragLayer }
  }

  @Test
  fun initialize() {
    verify { gesturesPlugin.addOnMapClickListener(any()) }
    verify { gesturesPlugin.addOnMapLongClickListener(any()) }
    verify { gesturesPlugin.addOnMoveListener(any()) }
    assertEquals(PointAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = PointAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
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
    manager = PointAnnotationManager(mapView, delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, can't create and add layer to style
    verify(exactly = 0) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
    every { delegateProvider.getStyle(capture(captureCallback)) } answers {
      captureCallback.captured.invoke(style)
    }
    manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    // Style is loaded, will create and add layer to style while creating annotations
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
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
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), annotation.iconImage)

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
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), any(), any(), any(), any(), any(), any()) }

    val annotation2 = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), annotation2.iconImage)
    // The first one will trigger twice and the second one once.
    verify(exactly = 3) { style.addStyleImage(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), any(), any(), any(), any(), any(), any()) }
  }

  @Test
  fun updateIconImageBitmap() {
    every { style.styleSourceExists(any()) } returns true
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    verify(exactly = 1) { style.addStyleImage(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), any(), any(), any(), any(), any(), any()) }
    val createBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
    annotation.iconImageBitmap = createBitmap
    manager.update(annotation)
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + createBitmap.hashCode(), annotation.iconImage)
    verify(exactly = 1) { style.addStyleImage(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + createBitmap.hashCode(), any(), any(), any(), any(), any(), any()) }
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
  fun annotationPropertiesUpdate() {
    val annotation = manager.create(PointAnnotationOptions().withPoint(Point.fromLngLat(0.0, 0.0)))

    annotation.iconImageBitmap = bitmap
    assertEquals(PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode(), annotation.iconImage)
    annotation.iconImageBitmap = null
    assertNull(annotation.iconImage)

    annotation.iconAnchor = IconAnchor.CENTER
    assertEquals(IconAnchor.CENTER, annotation.iconAnchor)
    annotation.iconAnchor = null
    assertNull(annotation.iconAnchor)

    annotation.iconImage = ""
    assertEquals("", annotation.iconImage)
    annotation.iconImage = null
    assertNull(annotation.iconImage)

    annotation.iconOffset = listOf(0.0, 0.0)
    assertEquals(listOf(0.0, 0.0), annotation.iconOffset)
    annotation.iconOffset = null
    assertNull(annotation.iconOffset)

    annotation.iconRotate = 0.0
    assertEquals(0.0, annotation.iconRotate)
    annotation.iconRotate = null
    assertNull(annotation.iconRotate)

    annotation.iconSize = 1.0
    assertEquals(1.0, annotation.iconSize)
    annotation.iconSize = null
    assertNull(annotation.iconSize)

    annotation.symbolSortKey = 1.0
    assertEquals(1.0, annotation.symbolSortKey)
    annotation.symbolSortKey = null
    assertNull(annotation.symbolSortKey)

    annotation.textAnchor = TextAnchor.CENTER
    assertEquals(TextAnchor.CENTER, annotation.textAnchor)
    annotation.textAnchor = null
    assertNull(annotation.textAnchor)

    annotation.textField = ""
    assertEquals("", annotation.textField)
    annotation.textField = null
    assertNull(annotation.textField)

    annotation.textJustify = TextJustify.AUTO
    assertEquals(TextJustify.AUTO, annotation.textJustify)
    annotation.textJustify = null
    assertNull(annotation.textJustify)

    annotation.textLetterSpacing = 0.0
    assertEquals(0.0, annotation.textLetterSpacing)
    annotation.textLetterSpacing = null
    assertNull(annotation.textLetterSpacing)

    annotation.textMaxWidth = 10.0
    assertEquals(10.0, annotation.textMaxWidth)
    annotation.textMaxWidth = null
    assertNull(annotation.textMaxWidth)

    annotation.textOffset = listOf(0.0, 0.0)
    assertEquals(listOf(0.0, 0.0), annotation.textOffset)
    annotation.textOffset = null
    assertNull(annotation.textOffset)

    annotation.textRadialOffset = 0.0
    assertEquals(0.0, annotation.textRadialOffset)
    annotation.textRadialOffset = null
    assertNull(annotation.textRadialOffset)

    annotation.textRotate = 0.0
    assertEquals(0.0, annotation.textRotate)
    annotation.textRotate = null
    assertNull(annotation.textRotate)

    annotation.textSize = 16.0
    assertEquals(16.0, annotation.textSize)
    annotation.textSize = null
    assertNull(annotation.textSize)

    annotation.textTransform = TextTransform.NONE
    assertEquals(TextTransform.NONE, annotation.textTransform)
    annotation.textTransform = null
    assertNull(annotation.textTransform)

    annotation.iconColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.iconColorInt)
    annotation.iconColorInt = null
    assertNull(annotation.iconColorInt)

    annotation.iconColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.iconColorString)
    annotation.iconColorString = null
    assertNull(annotation.iconColorString)

    annotation.iconHaloBlur = 0.0
    assertEquals(0.0, annotation.iconHaloBlur)
    annotation.iconHaloBlur = null
    assertNull(annotation.iconHaloBlur)

    annotation.iconHaloColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.iconHaloColorInt)
    annotation.iconHaloColorInt = null
    assertNull(annotation.iconHaloColorInt)

    annotation.iconHaloColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.iconHaloColorString)
    annotation.iconHaloColorString = null
    assertNull(annotation.iconHaloColorString)

    annotation.iconHaloWidth = 0.0
    assertEquals(0.0, annotation.iconHaloWidth)
    annotation.iconHaloWidth = null
    assertNull(annotation.iconHaloWidth)

    annotation.iconOpacity = 1.0
    assertEquals(1.0, annotation.iconOpacity)
    annotation.iconOpacity = null
    assertNull(annotation.iconOpacity)

    annotation.textColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.textColorInt)
    annotation.textColorInt = null
    assertNull(annotation.textColorInt)

    annotation.textColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.textColorString)
    annotation.textColorString = null
    assertNull(annotation.textColorString)

    annotation.textHaloBlur = 0.0
    assertEquals(0.0, annotation.textHaloBlur)
    annotation.textHaloBlur = null
    assertNull(annotation.textHaloBlur)

    annotation.textHaloColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.textHaloColorInt)
    annotation.textHaloColorInt = null
    assertNull(annotation.textHaloColorInt)

    annotation.textHaloColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.textHaloColorString)
    annotation.textHaloColorString = null
    assertNull(annotation.textHaloColorString)

    annotation.textHaloWidth = 0.0
    assertEquals(0.0, annotation.textHaloWidth)
    annotation.textHaloWidth = null
    assertNull(annotation.textHaloWidth)

    annotation.textOpacity = 1.0
    assertEquals(1.0, annotation.textOpacity)
    annotation.textOpacity = null
    assertNull(annotation.textOpacity)
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

    val listener = mockk<OnPointAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    val interactionListener = mockk<OnPointAnnotationInteractionListener>()
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
    val manager = PointAnnotationManager(mapView, delegateProvider)

    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

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

    every { feature.getProperty(any()).asLong } returns 0L

    val listener = mockk<OnPointAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val moveGestureDetector = mockk<MoveGestureDetector>()
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    captureSlot.captured.onMoveBegin(moveGestureDetector)
    verify { listener.onAnnotationDragStarted(annotation) }
    assertTrue(manager.annotations.isEmpty())

    val moveDistancesObject = mockk<MoveDistancesObject>()
    every { moveDistancesObject.currentX } returns 1f
    every { moveDistancesObject.currentY } returns 1f
    every { moveDistancesObject.distanceXSinceLast } returns 1f
    every { moveDistancesObject.distanceYSinceLast } returns 1f
    every { moveGestureDetector.getMoveObject(any()) } returns moveDistancesObject
    captureSlot.captured.onMove(moveGestureDetector)
    verify { listener.onAnnotationDrag(annotation) }
    assertTrue(manager.annotations.isEmpty())

    captureSlot.captured.onMoveEnd(moveGestureDetector)
    verify { listener.onAnnotationDragFinished(annotation) }

    manager.removeDragListener(listener)
    assertTrue(manager.dragListeners.isEmpty())
    assertEquals(1, manager.annotations.size)
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
    verify(exactly = 1) { manager.dragLayer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer?.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    verify(exactly = 1) { manager.dragLayer?.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer?.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer?.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    verify(exactly = 1) { manager.dragLayer?.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
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
    verify(exactly = 1) { manager.dragLayer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer?.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
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
    verify(exactly = 1) { manager.dragLayer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer?.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    verify(exactly = 1) { manager.dragLayer?.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
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
    verify(exactly = 1) { manager.dragLayer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    verify(exactly = 1) { manager.dragLayer?.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
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
    verify(exactly = 1) { manager.dragLayer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    verify(exactly = 1) { manager.dragLayer?.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
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
    verify(exactly = 1) { manager.dragLayer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer?.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
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
    verify(exactly = 1) { manager.dragLayer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer?.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
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
    verify(exactly = 1) { manager.dragLayer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer?.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
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
    verify(exactly = 1) { manager.dragLayer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer?.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
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
    verify(exactly = 1) { manager.dragLayer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    verify(exactly = 1) { manager.dragLayer?.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
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
    verify(exactly = 1) { manager.dragLayer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    verify(exactly = 1) { manager.dragLayer?.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer?.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer?.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
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
    verify(exactly = 1) { manager.dragLayer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
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
    verify(exactly = 1) { manager.dragLayer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer?.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
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
    verify(exactly = 1) { manager.dragLayer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer?.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
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
    verify(exactly = 1) { manager.dragLayer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer?.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
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
    verify(exactly = 1) { manager.dragLayer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer?.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
  }
}