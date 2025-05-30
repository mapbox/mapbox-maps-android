// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import com.google.gson.JsonObject
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.DataRef
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.bindgen.None
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import com.mapbox.maps.interactions.TypedFeaturesetDescriptor
import com.mapbox.maps.plugin.annotation.*
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import io.mockk.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
class PointAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: MapboxStyleManager = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val moveGestureDetector: MoveGestureDetector = mockk()
  private val layer: SymbolLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: SymbolLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()
  private val feature = mockk<Feature>()

  private lateinit var manager: PointAnnotationManager
  private val bitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)

  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logE(any(), any()) } just Runs
    every { delegateProvider.mapStyleManagerDelegate } returns style
    every { style.addSource(any()) } just Runs
    every { style.getSource(any()) } returns null
    every { style.addPersistentStyleLayer(any(), any()) } returns ExpectedFactory.createNone()
    every { style.addPersistentLayer(any(), any()) } just Runs
    every { style.setStyleLayerProperty(any(), any(), any()) } returns mockk()
    every { style.styleSourceExists(any()) } returns false
    every { style.styleLayerExists(any()) } returns false
    every { style.removeStyleLayer(any()) } returns mockk()
    every { style.removeStyleSource(any()) } returns mockk()
    every { style.pixelRatio } returns 1.0f
    every { delegateProvider.mapPluginProviderDelegate.getPlugin<GesturesPlugin>(any()) } returns gesturesPlugin
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapInteractionDelegate } returns mapInteractionDelegate
    every { delegateProvider.mapFeatureQueryDelegate } returns mockk()
    every { mapInteractionDelegate.addInteraction(any()) } returns Cancelable { }
    every { gesturesPlugin.getGesturesManager().moveGestureDetector } returns moveGestureDetector
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns Point.fromLngLat(0.0, 0.0)
    every { mapCameraManagerDelegate.pixelForCoordinate(any()) } returns ScreenCoordinate(1.0, 1.0)
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any()) } answers { source }

    manager = PointAnnotationManager(delegateProvider)
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
    every { layer.iconTextFit(any<Expression>()) } answers { layer }
    every { dragLayer.iconTextFit(any<Expression>()) } answers { dragLayer }
    every { layer.iconTextFitPadding(any<Expression>()) } answers { layer }
    every { dragLayer.iconTextFitPadding(any<Expression>()) } answers { dragLayer }
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
    every { layer.textLineHeight(any<Expression>()) } answers { layer }
    every { dragLayer.textLineHeight(any<Expression>()) } answers { dragLayer }
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
    every { layer.iconEmissiveStrength(any<Expression>()) } answers { layer }
    every { dragLayer.iconEmissiveStrength(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloBlur(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloBlur(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloColor(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloColor(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloWidth(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloWidth(any<Expression>()) } answers { dragLayer }
    every { layer.iconOcclusionOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.iconOcclusionOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.iconOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.iconOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.symbolZOffset(any<Expression>()) } answers { layer }
    every { dragLayer.symbolZOffset(any<Expression>()) } answers { dragLayer }
    every { layer.textColor(any<Expression>()) } answers { layer }
    every { dragLayer.textColor(any<Expression>()) } answers { dragLayer }
    every { layer.textEmissiveStrength(any<Expression>()) } answers { layer }
    every { dragLayer.textEmissiveStrength(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloBlur(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloBlur(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloColor(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloColor(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloWidth(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloWidth(any<Expression>()) } answers { dragLayer }
    every { layer.textOcclusionOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.textOcclusionOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.textOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.textOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.iconColorUseTheme(any<Expression>()) } answers { layer }
    every { dragLayer.iconColorUseTheme(any<Expression>()) } answers { dragLayer }
    every { layer.iconHaloColorUseTheme(any<Expression>()) } answers { layer }
    every { dragLayer.iconHaloColorUseTheme(any<Expression>()) } answers { dragLayer }
    every { layer.textColorUseTheme(any<Expression>()) } answers { layer }
    every { dragLayer.textColorUseTheme(any<Expression>()) } answers { dragLayer }
    every { layer.textHaloColorUseTheme(any<Expression>()) } answers { layer }
    every { dragLayer.textHaloColorUseTheme(any<Expression>()) } answers { dragLayer }
  }

  @After
  fun cleanUp() {
    unmockkAll()
  }

  @Test
  fun initialize() {
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(ClickInteraction::class)) }
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(LongClickInteraction::class)) }
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(DragInteraction::class)) }
    assertEquals(PointAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = PointAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
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
    assertTrue(manager.dragListeners.isEmpty())
    assertTrue(manager.clickListeners.isEmpty())
    assertTrue(manager.longClickListeners.isEmpty())
  }

  @Test
  fun initializeBeforeStyleLoad() {
    every { style.styleLayerExists("test_layer") } returns true
    val captureCallback = slot<(MapboxStyleManager) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } just Runs
    manager = PointAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, still create and add layer to style as it's persistent layer
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
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
  fun createWithClusterOptions() {
    @Suppress("UNCHECKED_CAST")
    manager = PointAnnotationManager(
      delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0,
          ClusterOptions(
            clusterRadius = 30,
            clusterMaxZoom = 15,
            clusterMinPoints = 2,
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
    assertTrue(sourceString.contains("clusterMinPoints = 2"))
    assertTrue(sourceString.contains("clusterProperties = {key1=x, key2=y}"))
    assertTrue(sourceString.contains("lineMetrics = true"))
    assertTrue(sourceString.contains("buffer = 20"))
    assertTrue(sourceString.contains("tolerance = 10.0"))
  }

  @Test
  fun iconImageBitmapWithIconImageIdAndBitmap() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
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
  fun iconImageBitmapWithoutIconImageId() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    mockkStatic(DataRef::class)
    every { DataRef.allocateNative(any()) } returns mockk(relaxed = true)
    val imageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode()
    every { style.hasStyleImage(imageId) } returns false
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(imageId, annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(any(), any(), any(), any(), any(), any(), any()) }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun twoPointsWithSameImageBitmap() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    mockkStatic(DataRef::class)
    every { DataRef.allocateNative(any()) } returns mockk(relaxed = true)
    val imageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode()
    every { style.hasStyleImage(imageId) } returns false

    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(imageId, annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }

    every { style.hasStyleImage(imageId) } returns true
    val annotation2 = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(imageId, annotation2.iconImage)
    // Only one image should be added to the style because we're re-using the same bitmap
    verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun onePointUpdateImageBitmap() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    mockkStatic(DataRef::class)
    every { DataRef.allocateNative(any()) } returns mockk(relaxed = true)
    val imageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode()
    every { style.hasStyleImage(imageId) } returns false

    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(imageId, annotation.iconImage)

    verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }

    every { style.hasStyleImage(imageId) } returns true

    val secondBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
    val secondImageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + secondBitmap.hashCode()
    every { style.hasStyleImage(secondImageId) } returns false
    annotation.iconImageBitmap = secondBitmap
    manager.update(annotation)
    // Only one image should be added to the style because we're re-using the same bitmap
    verify(exactly = 1) { style.addStyleImage(secondImageId, any(), any(), any(), any(), any(), any()) }
    unmockkStatic(DataRef::class)
  }

  @Test
  fun updateIconImageBitmap() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    mockkStatic(DataRef::class)
    every { DataRef.allocateNative(any()) } returns mockk(relaxed = true)
    val imageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + bitmap.hashCode()
    every { style.hasStyleImage(imageId) } returns false
    val annotation = manager.create(
      PointAnnotationOptions()
        .withIconImage(bitmap)
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    verify(exactly = 1) { style.addStyleImage(imageId, any(), any(), any(), any(), any(), any()) }

    // Update the bitmap for the same point annotation
    val createBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888)
    val secondImageId = PointAnnotation.ICON_DEFAULT_NAME_PREFIX + createBitmap.hashCode()
    every { style.hasStyleImage(secondImageId) } returns false
    annotation.iconImageBitmap = createBitmap
    manager.update(annotation)
    assertEquals(secondImageId, annotation.iconImage)
    verify(exactly = 1) { style.addStyleImage(secondImageId, any(), any(), any(), any(), any(), any()) }
    unmockkStatic(DataRef::class)
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

    annotation.iconTextFit = IconTextFit.NONE
    assertEquals(IconTextFit.NONE, annotation.iconTextFit)
    annotation.iconTextFit = null
    assertNull(annotation.iconTextFit)

    annotation.iconTextFitPadding = listOf(0.0, 0.0, 0.0, 0.0)
    assertEquals(listOf(0.0, 0.0, 0.0, 0.0), annotation.iconTextFitPadding)
    annotation.iconTextFitPadding = null
    assertNull(annotation.iconTextFitPadding)

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

    annotation.textLineHeight = 1.2
    assertEquals(1.2, annotation.textLineHeight)
    annotation.textLineHeight = null
    assertNull(annotation.textLineHeight)

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

    annotation.iconEmissiveStrength = 1.0
    assertEquals(1.0, annotation.iconEmissiveStrength)
    annotation.iconEmissiveStrength = null
    assertNull(annotation.iconEmissiveStrength)

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

    annotation.iconOcclusionOpacity = 0.0
    assertEquals(0.0, annotation.iconOcclusionOpacity)
    annotation.iconOcclusionOpacity = null
    assertNull(annotation.iconOcclusionOpacity)

    annotation.iconOpacity = 1.0
    assertEquals(1.0, annotation.iconOpacity)
    annotation.iconOpacity = null
    assertNull(annotation.iconOpacity)

    annotation.symbolZOffset = 0.0
    assertEquals(0.0, annotation.symbolZOffset)
    annotation.symbolZOffset = null
    assertNull(annotation.symbolZOffset)

    annotation.textColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.textColorInt)
    annotation.textColorInt = null
    assertNull(annotation.textColorInt)

    annotation.textColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.textColorString)
    annotation.textColorString = null
    assertNull(annotation.textColorString)

    annotation.textEmissiveStrength = 1.0
    assertEquals(1.0, annotation.textEmissiveStrength)
    annotation.textEmissiveStrength = null
    assertNull(annotation.textEmissiveStrength)

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

    annotation.textOcclusionOpacity = 0.0
    assertEquals(0.0, annotation.textOcclusionOpacity)
    annotation.textOcclusionOpacity = null
    assertNull(annotation.textOcclusionOpacity)

    annotation.textOpacity = 1.0
    assertEquals(1.0, annotation.textOpacity)
    annotation.textOpacity = null
    assertNull(annotation.textOpacity)

    annotation.iconColorUseTheme = "default"
    assertEquals("default", annotation.iconColorUseTheme)
    annotation.iconColorUseTheme = null
    assertNull(annotation.iconColorUseTheme)

    annotation.iconHaloColorUseTheme = "default"
    assertEquals("default", annotation.iconHaloColorUseTheme)
    annotation.iconHaloColorUseTheme = null
    assertNull(annotation.iconHaloColorUseTheme)

    annotation.textColorUseTheme = "default"
    assertEquals("default", annotation.textColorUseTheme)
    annotation.textColorUseTheme = null
    assertNull(annotation.textColorUseTheme)

    annotation.textHaloColorUseTheme = "default"
    assertEquals("default", annotation.textHaloColorUseTheme)
    annotation.textHaloColorUseTheme = null
    assertNull(annotation.textHaloColorUseTheme)
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
  fun clickWithNoAnnotation() {
    mockkObject(ClickInteraction.Companion)
    val onClickLayerIdSlot = slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    val customLayerId = "customLayerId"
    every {
      ClickInteraction.layer(id = customLayerId, filter = any(), onClick = capture(onClickLayerIdSlot))
    } answers {
      mockk()
    }
    every {
      ClickInteraction.layer(
        id = any(),
        filter = any(),
        onClick = { _, _ -> return@layer false }
      )
    } returns mockk()
    val manager = PointAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val listener = mockk<OnPointAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, "incorrectId")
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

    onClickLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(customLayerId),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify(exactly = 0) { listener.onAnnotationClick(any()) }
    unmockkObject(ClickInteraction.Companion)
  }

  @Test
  fun click() {
    mockkObject(ClickInteraction.Companion)
    val onClickLayerIdSlot = slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    val customLayerId = "customLayerId"
    every {
      ClickInteraction.layer(id = customLayerId, filter = any(), onClick = capture(onClickLayerIdSlot))
    } answers {
      mockk()
    }
    every {
      ClickInteraction.layer(
        id = any(),
        filter = any(),
        onClick = { _, _ -> return@layer false }
      )
    } returns mockk()
    val manager = PointAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPointAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns true
    manager.addClickListener(listener)

    val interactionListener = mockk<OnPointAnnotationInteractionListener>()
    every { interactionListener.onSelectAnnotation(any()) } just Runs
    every { interactionListener.onDeselectAnnotation(any()) } just Runs
    manager.addInteractionListener(interactionListener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, annotation.id)
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

   onClickLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(customLayerId),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onAnnotationClick(annotation) }
    verify { interactionListener.onSelectAnnotation(annotation) }

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, annotation.id)
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

    onClickLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(annotation.id),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { interactionListener.onDeselectAnnotation(annotation) }

    manager.removeClickListener(listener)
    assertTrue(manager.clickListeners.isEmpty())
    manager.removeInteractionListener(interactionListener)
    assertTrue(manager.interactionListener.isEmpty())
    unmockkObject(ClickInteraction.Companion)
  }

  @Test
  fun longClick() {
    mockkObject(LongClickInteraction.Companion)
    val onLongClickLayerIdSlot = slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    val customLayerId = "customLayerId"
    every {
      LongClickInteraction.layer(id = customLayerId, filter = any(), onLongClick = capture(onLongClickLayerIdSlot))
    } answers {
      mockk()
    }
    every {
      LongClickInteraction.layer(
        id = any(),
        filter = any(),
        onLongClick = { _, _ -> return@layer false }
      )
    } returns mockk()
    val manager = PointAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPointAnnotationLongClickListener>()
    every { listener.onAnnotationLongClick(any()) } returns false
    manager.addLongClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, annotation.id)
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

    onLongClickLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(customLayerId),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onAnnotationLongClick(annotation) }

    manager.removeLongClickListener(listener)
    assertTrue(manager.longClickListeners.isEmpty())
    unmockkObject(LongClickInteraction.Companion)
  }

  @Test
  fun clusterClick() {
    val customClusterLayerId = "customClusterLayerId"
        mockkStatic("com.mapbox.maps.extension.style.layers.generated.CircleLayerKt")
        every { circleLayer(match { it.contains("cluster") }, any(), any()) } returns CircleLayer(
          customClusterLayerId,
          "clusterSource"
        )

    mockkObject(ClickInteraction.Companion)
    val onClickLayerIdSlot =
      slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    every {
      ClickInteraction.layer(
        id = customClusterLayerId,
        filter = any(),
        onClick = capture(onClickLayerIdSlot)
      )
    } answers {
      mockk()
    }
    every {
      ClickInteraction.layer(
        id = any(),
        filter = any(),
        onClick = { _, _ -> return@layer false }
      )
    } returns mockk()

    @Suppress("UNCHECKED_CAST")
    manager = PointAnnotationManager(
      delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0,
          ClusterOptions(
            clusterRadius = 30,
            clusterMaxZoom = 15,
            clusterMinPoints = 2,
            clusterProperties = hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>
          )
        )
      )
    )

    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)
    every { feature.properties() } returns JsonObject().apply {
      addProperty("cluster", true)
      addProperty("cluster_id", "22")
      addProperty("point_count", 22)
      addProperty("point_count_abbreviated", "22K")
    }

    val listener = mockk<OnClusterClickListener>()
    every { listener.onClusterClick(any()) } returns true
    manager.addClusterClickListener(listener)

    val featuresetFeature = FeaturesetFeature(
      id = FeaturesetFeatureId(feature.id()!!, null),
      descriptor = TypedFeaturesetDescriptor.Layer(customClusterLayerId),
      originalFeature = feature,
      state = FeatureState { }
    )

    onClickLayerIdSlot.captured.invoke(
      featuresetFeature,
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onClusterClick(match { it.originalFeature == feature }) }

    manager.removeClusterClickListener(listener)
    assertTrue(manager.clusterClickListeners.isEmpty())
    unmockkObject(ClickInteraction.Companion)
    unmockkStatic("com.mapbox.maps.extension.style.layers.generated.CircleLayerKt")
  }

  @Test
  fun clusterLongClick() {
    val customClusterLayerId = "customClusterLayerId"
    mockkStatic("com.mapbox.maps.extension.style.layers.generated.CircleLayerKt")
    every { circleLayer(match { it.contains("cluster") }, any(), any()) } returns CircleLayer(
      customClusterLayerId,
      "clusterSource"
    )

    mockkObject(LongClickInteraction.Companion)
    val onLongClickLayerIdSlot =
      slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    every {
      LongClickInteraction.layer(
        id = customClusterLayerId,
        filter = any(),
        onLongClick = capture(onLongClickLayerIdSlot)
      )
    } answers {
      mockk()
    }
    every {
      LongClickInteraction.layer(
        id = any(),
        filter = any(),
        onLongClick = { _, _ -> return@layer false }
      )
    } returns mockk()

    @Suppress("UNCHECKED_CAST")
    manager = PointAnnotationManager(
      delegateProvider,
      AnnotationConfig(
        annotationSourceOptions = AnnotationSourceOptions(
          10, 20L, true, 10.0,
          ClusterOptions(
            clusterRadius = 30,
            clusterMaxZoom = 15,
            clusterMinPoints = 2,
            clusterProperties = hashMapOf("key1" to "x", "key2" to "y") as HashMap<String, Any>
          )
        )
      )
    )

    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)
    every { feature.properties() } returns JsonObject().apply {
      addProperty("cluster", true)
      addProperty("cluster_id", "22")
      addProperty("point_count", 22)
      addProperty("point_count_abbreviated", "22K")
    }

    val listener = mockk<OnClusterLongClickListener>()
    every { listener.onClusterLongClick(any()) } returns true
    manager.addClusterLongClickListener(listener)

    val featuresetFeature = FeaturesetFeature(
      id = FeaturesetFeatureId(feature.id()!!, null),
      descriptor = TypedFeaturesetDescriptor.Layer(customClusterLayerId),
      originalFeature = feature,
      state = FeatureState { }
    )

    onLongClickLayerIdSlot.captured.invoke(
      featuresetFeature,
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onClusterLongClick(match { it.originalFeature == feature }) }

    manager.removeClusterLongClickListener(listener)
    assertTrue(manager.clusterLongClickListeners.isEmpty())
    unmockkObject(ClickInteraction.Companion)
    unmockkStatic("com.mapbox.maps.extension.style.layers.generated.CircleLayerKt")
  }

  @Test
  fun drag() {
    mockkObject(DragInteraction.Companion)
    val onDragBeginLayerIdSlot = slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    val onDragSlot = slot<((InteractionContext) -> Unit)>()
    val onDragEndSlot = slot<((InteractionContext) -> Unit)>()
    val customLayerId = "customLayerId"
    every {
      DragInteraction.layer(
        id = customLayerId,
        filter = any(),
        onDragBegin = capture(onDragBeginLayerIdSlot),
        onDrag = capture(onDragSlot),
        onDragEnd = capture(onDragEndSlot)
      )
    } answers {
      mockk()
    }
    every {
      DragInteraction.layer(
        id = any(),
        filter = any(),
        onDragBegin = { _, _ -> return@layer false },
        onDrag = { },
        onDragEnd = { }
      )
    } returns mockk()
    val manager = PointAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPointAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, annotation.id)
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

    onDragBeginLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(customLayerId),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onAnnotationDragStarted(annotation) }
    assertEquals(1, manager.annotations.size)

    val moveDistancesObject = mockk<MoveDistancesObject>()
    every { moveDistancesObject.currentX } returns 1f
    every { moveDistancesObject.currentY } returns 1f
    every { moveDistancesObject.distanceXSinceLast } returns 1f
    every { moveDistancesObject.distanceYSinceLast } returns 1f
    every { moveGestureDetector.getMoveObject(any()) } returns moveDistancesObject
    onDragSlot.captured.invoke(
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onAnnotationDrag(annotation) }
    assertEquals(1, manager.annotations.size)

    onDragEndSlot.captured.invoke(
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
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
    unmockkObject(DragInteraction.Companion)
  }

  @Test
  fun testScaleDoesNotTriggerDrag() {
    mockkObject(DragInteraction.Companion)
    val onDragBeginLayerIdSlot = slot<((FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean)>()
    val onDragSlot = slot<((InteractionContext) -> Unit)>()
    val onDragEndSlot = slot<((InteractionContext) -> Unit)>()
    val customLayerId = "customLayerId"
    every {
      DragInteraction.layer(
        id = customLayerId,
        filter = any(),
        onDragBegin = capture(onDragBeginLayerIdSlot),
        onDrag = capture(onDragSlot),
        onDragEnd = capture(onDragEndSlot)
      )
    } answers {
      mockk()
    }
    every {
      DragInteraction.layer(
        id = any(),
        filter = any(),
        onDragBegin = { _, _ -> return@layer false },
        onDrag = { },
        onDragEnd = { }
      )
    } returns mockk()
    every { mapInteractionDelegate.dispatch(any()) } just runs
    val manager = PointAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PointAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPointAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 2
    every { moveGestureDetector.focalPoint } returns pointF

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PointAnnotation.ID_KEY, annotation.id)
    }
    every { feature.id() } returns "featureId"
    every { feature.geometry() } returns Point.fromLngLat(0.0, 0.0)

    onDragBeginLayerIdSlot.captured.invoke(
      FeaturesetFeature(
        id = FeaturesetFeatureId(feature.id()!!, null),
        descriptor = TypedFeaturesetDescriptor.Layer(customLayerId),
        originalFeature = feature,
        state = FeatureState { }
      ),
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify { listener.onAnnotationDragStarted(annotation) }
    assertEquals(1, manager.annotations.size)

    val moveDistancesObject = mockk<MoveDistancesObject>()
    every { moveDistancesObject.currentX } returns 1f
    every { moveDistancesObject.currentY } returns 1f
    every { moveDistancesObject.distanceXSinceLast } returns 1f
    every { moveDistancesObject.distanceYSinceLast } returns 1f
    every { moveGestureDetector.getMoveObject(any()) } returns moveDistancesObject
    onDragSlot.captured.invoke(
      InteractionContext(
        CoordinateInfo(
          Point.fromLngLat(0.0, 0.0),
          true
        ),
        ScreenCoordinate(0.0, 0.0)
      )
    )
    verify(exactly = 0) { listener.onAnnotationDrag(annotation) }
    verify(exactly = 1) { listener.onAnnotationDragFinished(annotation) }

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
    unmockkObject(DragInteraction.Companion)
  }

  @Test
  fun testIconAnchorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconAnchor(IconAnchor.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
  }

  @Test
  fun testIconAnchorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconAnchor = IconAnchor.CENTER
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.iconAnchor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ANCHOR)) }
  }

  @Test
  fun testIconImageLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconImage("")
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    verify(exactly = 1) { manager.dragLayer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    verify(exactly = 1) { manager.dragLayer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
  }

  @Test
  fun testIconImageInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconImage = ""
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
    verify(exactly = 1) { manager.dragLayer.iconImage(Expression.get(PointAnnotationOptions.PROPERTY_ICON_IMAGE)) }
  }

  @Test
  fun testIconOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconOffset(listOf(0.0, 0.0))
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
  }

  @Test
  fun testIconOffsetInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconOffset = listOf(0.0, 0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.iconOffset(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OFFSET)) }
  }

  @Test
  fun testIconRotateLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconRotate(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
  }

  @Test
  fun testIconRotateInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconRotate = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.iconRotate(Expression.get(PointAnnotationOptions.PROPERTY_ICON_ROTATE)) }
  }

  @Test
  fun testIconSizeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconSize(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
  }

  @Test
  fun testIconSizeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconSize = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.iconSize(Expression.get(PointAnnotationOptions.PROPERTY_ICON_SIZE)) }
  }

  @Test
  fun testIconTextFitLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconTextFit(IconTextFit.NONE)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
  }

  @Test
  fun testIconTextFitInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconTextFit = IconTextFit.NONE
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFit(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT)) }
  }

  @Test
  fun testIconTextFitPaddingLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconTextFitPadding(listOf(0.0, 0.0, 0.0, 0.0))
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
  }

  @Test
  fun testIconTextFitPaddingInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconTextFitPadding = listOf(0.0, 0.0, 0.0, 0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
    verify(exactly = 1) { manager.dragLayer.iconTextFitPadding(Expression.get(PointAnnotationOptions.PROPERTY_ICON_TEXT_FIT_PADDING)) }
  }

  @Test
  fun testSymbolSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withSymbolSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
  }

  @Test
  fun testSymbolSortKeyInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.symbolSortKey = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.symbolSortKey(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_SORT_KEY)) }
  }

  @Test
  fun testTextAnchorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextAnchor(TextAnchor.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
  }

  @Test
  fun testTextAnchorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textAnchor = TextAnchor.CENTER
    manager.create(options)
    verify(exactly = 1) { manager.layer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
    verify(exactly = 1) { manager.dragLayer.textAnchor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ANCHOR)) }
  }

  @Test
  fun testTextFieldLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextField("")
    manager.create(options)
    verify(exactly = 1) { manager.layer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    verify(exactly = 1) { manager.dragLayer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    verify(exactly = 1) { manager.dragLayer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
  }

  @Test
  fun testTextFieldInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textField = ""
    manager.create(options)
    verify(exactly = 1) { manager.layer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
    verify(exactly = 1) { manager.dragLayer.textField(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_FIELD)) }
  }

  @Test
  fun testTextJustifyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextJustify(TextJustify.CENTER)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    verify(exactly = 1) { manager.dragLayer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    verify(exactly = 1) { manager.dragLayer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
  }

  @Test
  fun testTextJustifyInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textJustify = TextJustify.CENTER
    manager.create(options)
    verify(exactly = 1) { manager.layer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
    verify(exactly = 1) { manager.dragLayer.textJustify(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_JUSTIFY)) }
  }

  @Test
  fun testTextLetterSpacingLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextLetterSpacing(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    verify(exactly = 1) { manager.dragLayer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    verify(exactly = 1) { manager.dragLayer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
  }

  @Test
  fun testTextLetterSpacingInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textLetterSpacing = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
    verify(exactly = 1) { manager.dragLayer.textLetterSpacing(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LETTER_SPACING)) }
  }

  @Test
  fun testTextLineHeightLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextLineHeight(1.2)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    verify(exactly = 1) { manager.dragLayer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    verify(exactly = 1) { manager.dragLayer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
  }

  @Test
  fun testTextLineHeightInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textLineHeight = 1.2
    manager.create(options)
    verify(exactly = 1) { manager.layer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
    verify(exactly = 1) { manager.dragLayer.textLineHeight(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_LINE_HEIGHT)) }
  }

  @Test
  fun testTextMaxWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextMaxWidth(10.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
  }

  @Test
  fun testTextMaxWidthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textMaxWidth = 10.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textMaxWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_MAX_WIDTH)) }
  }

  @Test
  fun testTextOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextOffset(listOf(0.0, 0.0))
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
  }

  @Test
  fun testTextOffsetInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textOffset = listOf(0.0, 0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OFFSET)) }
  }

  @Test
  fun testTextRadialOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextRadialOffset(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
  }

  @Test
  fun testTextRadialOffsetInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textRadialOffset = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.textRadialOffset(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_RADIAL_OFFSET)) }
  }

  @Test
  fun testTextRotateLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextRotate(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
  }

  @Test
  fun testTextRotateInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textRotate = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
    verify(exactly = 1) { manager.dragLayer.textRotate(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_ROTATE)) }
  }

  @Test
  fun testTextSizeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextSize(16.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
  }

  @Test
  fun testTextSizeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textSize = 16.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
    verify(exactly = 1) { manager.dragLayer.textSize(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_SIZE)) }
  }

  @Test
  fun testTextTransformLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextTransform(TextTransform.NONE)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    verify(exactly = 1) { manager.dragLayer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    verify(exactly = 1) { manager.dragLayer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
  }

  @Test
  fun testTextTransformInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textTransform = TextTransform.NONE
    manager.create(options)
    verify(exactly = 1) { manager.layer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
    verify(exactly = 1) { manager.dragLayer.textTransform(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_TRANSFORM)) }
  }

  @Test
  fun testIconColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
  }

  @Test
  fun testIconColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
  }

  @Test
  fun testIconColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR)) }
  }

  @Test
  fun testIconEmissiveStrengthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconEmissiveStrength(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
  }

  @Test
  fun testIconEmissiveStrengthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconEmissiveStrength = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.iconEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_ICON_EMISSIVE_STRENGTH)) }
  }

  @Test
  fun testIconHaloBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
  }

  @Test
  fun testIconHaloBlurInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconHaloBlur = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_BLUR)) }
  }

  @Test
  fun testIconHaloColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
  }

  @Test
  fun testIconHaloColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
  }

  @Test
  fun testIconHaloColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconHaloColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR)) }
  }

  @Test
  fun testIconHaloWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
  }

  @Test
  fun testIconHaloWidthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconHaloWidth = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_WIDTH)) }
  }

  @Test
  fun testIconOcclusionOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconOcclusionOpacity(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
  }

  @Test
  fun testIconOcclusionOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconOcclusionOpacity = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OCCLUSION_OPACITY)) }
  }

  @Test
  fun testIconOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
  }

  @Test
  fun testIconOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconOpacity = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.iconOpacity(Expression.get(PointAnnotationOptions.PROPERTY_ICON_OPACITY)) }
  }

  @Test
  fun testSymbolZOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withSymbolZOffset(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
  }

  @Test
  fun testSymbolZOffsetInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.symbolZOffset = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.symbolZOffset(Expression.get(PointAnnotationOptions.PROPERTY_SYMBOL_Z_OFFSET)) }
  }

  @Test
  fun testTextColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
  }

  @Test
  fun testTextColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
  }

  @Test
  fun testTextColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR)) }
  }

  @Test
  fun testTextEmissiveStrengthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextEmissiveStrength(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
  }

  @Test
  fun testTextEmissiveStrengthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textEmissiveStrength = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
    verify(exactly = 1) { manager.dragLayer.textEmissiveStrength(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_EMISSIVE_STRENGTH)) }
  }

  @Test
  fun testTextHaloBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
  }

  @Test
  fun testTextHaloBlurInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textHaloBlur = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloBlur(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_BLUR)) }
  }

  @Test
  fun testTextHaloColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
  }

  @Test
  fun testTextHaloColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
  }

  @Test
  fun testTextHaloColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textHaloColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColor(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR)) }
  }

  @Test
  fun testTextHaloWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
  }

  @Test
  fun testTextHaloWidthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textHaloWidth = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.textHaloWidth(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_WIDTH)) }
  }

  @Test
  fun testTextOcclusionOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextOcclusionOpacity(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
  }

  @Test
  fun testTextOcclusionOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textOcclusionOpacity = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOcclusionOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OCCLUSION_OPACITY)) }
  }

  @Test
  fun testTextOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
  }

  @Test
  fun testTextOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textOpacity = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.textOpacity(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_OPACITY)) }
  }

  @Test
  fun testIconColorUseThemeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconColorUseTheme("default")
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
  }

  @Test
  fun testIconColorUseThemeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconColorUseTheme = "default"
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_COLOR_USE_THEME)) }
  }

  @Test
  fun testIconHaloColorUseThemeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withIconHaloColorUseTheme("default")
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
  }

  @Test
  fun testIconHaloColorUseThemeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.iconHaloColorUseTheme = "default"
    manager.create(options)
    verify(exactly = 1) { manager.layer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.iconHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_ICON_HALO_COLOR_USE_THEME)) }
  }

  @Test
  fun testTextColorUseThemeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextColorUseTheme("default")
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
  }

  @Test
  fun testTextColorUseThemeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textColorUseTheme = "default"
    manager.create(options)
    verify(exactly = 1) { manager.layer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_COLOR_USE_THEME)) }
  }

  @Test
  fun testTextHaloColorUseThemeLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withTextHaloColorUseTheme("default")
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
  }

  @Test
  fun testTextHaloColorUseThemeInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    val options = PointAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.textHaloColorUseTheme = "default"
    manager.create(options)
    verify(exactly = 1) { manager.layer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
    verify(exactly = 1) { manager.dragLayer.textHaloColorUseTheme(Expression.get(PointAnnotationOptions.PROPERTY_TEXT_HALO_COLOR_USE_THEME)) }
  }
}