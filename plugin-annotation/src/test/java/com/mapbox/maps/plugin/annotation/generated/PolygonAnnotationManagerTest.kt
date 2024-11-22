// This file is generated.

package com.mapbox.maps.plugin.annotation.generated

import android.graphics.Color
import android.graphics.PointF
import com.google.gson.JsonObject
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.ExpectedFactory
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
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
import org.robolectric.annotation.Config

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowProjection::class])
class PolygonAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: MapboxStyleManager = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val moveGestureDetector: MoveGestureDetector = mockk()
  private val layer: FillLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: FillLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()
  private val feature = mockk<Feature>()

  private lateinit var manager: PolygonAnnotationManager
  @Before
  fun setUp() {
    mockkStatic("com.mapbox.maps.extension.style.layers.LayerUtils")
    mockkStatic("com.mapbox.maps.extension.style.sources.SourceUtils")
    mockkStatic(Projection::class)
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
    every { mapCameraManagerDelegate.cameraState } returns mockk(relaxed = true)
    every { layer.layerId } returns "layer0"
    every { source.sourceId } returns "source0"
    every { source.featureCollection(any()) } answers { source }

    manager = PolygonAnnotationManager(delegateProvider)
    manager.layer = layer
    manager.source = source
    manager.dragLayer = dragLayer
    manager.dragSource = dragSource
    every { layer.fillSortKey(any<Expression>()) } answers { layer }
    every { dragLayer.fillSortKey(any<Expression>()) } answers { dragLayer }
    every { layer.fillColor(any<Expression>()) } answers { layer }
    every { dragLayer.fillColor(any<Expression>()) } answers { dragLayer }
    every { layer.fillOpacity(any<Expression>()) } answers { layer }
    every { dragLayer.fillOpacity(any<Expression>()) } answers { dragLayer }
    every { layer.fillOutlineColor(any<Expression>()) } answers { layer }
    every { dragLayer.fillOutlineColor(any<Expression>()) } answers { dragLayer }
    every { layer.fillPattern(any<Expression>()) } answers { layer }
    every { dragLayer.fillPattern(any<Expression>()) } answers { dragLayer }
    every { layer.fillZOffset(any<Expression>()) } answers { layer }
    every { dragLayer.fillZOffset(any<Expression>()) } answers { dragLayer }
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
    assertEquals(PolygonAnnotation.ID_KEY, manager.getAnnotationIdKey())
    verify { style.addPersistentLayer(any(), null) }
    every { style.styleLayerExists("test_layer") } returns true

    manager = PolygonAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
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
    manager = PolygonAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, still create and add layer to style as it's persistent layer
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
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
  fun annotationPropertiesUpdate() {
    val annotation = manager.create(PolygonAnnotationOptions().withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))))

    annotation.fillSortKey = 1.0
    assertEquals(1.0, annotation.fillSortKey)
    annotation.fillSortKey = null
    assertNull(annotation.fillSortKey)

    annotation.fillColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.fillColorInt)
    annotation.fillColorInt = null
    assertNull(annotation.fillColorInt)

    annotation.fillColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.fillColorString)
    annotation.fillColorString = null
    assertNull(annotation.fillColorString)

    annotation.fillOpacity = 1.0
    assertEquals(1.0, annotation.fillOpacity)
    annotation.fillOpacity = null
    assertNull(annotation.fillOpacity)

    annotation.fillOutlineColorInt = Color.BLACK
    assertEquals(Color.BLACK, annotation.fillOutlineColorInt)
    annotation.fillOutlineColorInt = null
    assertNull(annotation.fillOutlineColorInt)

    annotation.fillOutlineColorString = ColorUtils.colorToRgbaString(Color.YELLOW)
    assertEquals(ColorUtils.colorToRgbaString(Color.YELLOW), annotation.fillOutlineColorString)
    annotation.fillOutlineColorString = null
    assertNull(annotation.fillOutlineColorString)

    annotation.fillPattern = "pedestrian-polygon"
    assertEquals("pedestrian-polygon", annotation.fillPattern)
    annotation.fillPattern = null
    assertNull(annotation.fillPattern)

    annotation.fillZOffset = 0.0
    assertEquals(0.0, annotation.fillZOffset)
    annotation.fillZOffset = null
    assertNull(annotation.fillZOffset)
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
    val manager = PolygonAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val listener = mockk<OnPolygonAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PolygonAnnotation.ID_KEY, "incorrectId")
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
    val manager = PolygonAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPolygonAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns true
    manager.addClickListener(listener)

    val interactionListener = mockk<OnPolygonAnnotationInteractionListener>()
    every { interactionListener.onSelectAnnotation(any()) } just Runs
    every { interactionListener.onDeselectAnnotation(any()) } just Runs
    manager.addInteractionListener(interactionListener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PolygonAnnotation.ID_KEY, annotation.id)
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
      addProperty(PolygonAnnotation.ID_KEY, annotation.id)
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
    val manager = PolygonAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPolygonAnnotationLongClickListener>()
    every { listener.onAnnotationLongClick(any()) } returns false
    manager.addLongClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PolygonAnnotation.ID_KEY, annotation.id)
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
    val manager = PolygonAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      PolygonAnnotationOptions()
        .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnPolygonAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    every { feature.properties() } returns JsonObject().apply {
      addProperty(PolygonAnnotation.ID_KEY, annotation.id)
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
    annotation.points = listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0)))
    manager.update(annotation)
    assertEquals(annotation, manager.annotations[0])

    // Verify delete after drag
    manager.delete(annotation)
    assertTrue(manager.annotations.isEmpty())
    unmockkObject(DragInteraction.Companion)
  }

  @Test
  fun testFillSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
  }

  @Test
  fun testFillSortKeyInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillSortKey = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.fillSortKey(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_SORT_KEY)) }
  }

  @Test
  fun testFillColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_COLOR)) }
  }

  @Test
  fun testFillOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
  }

  @Test
  fun testFillOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillOpacity = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.fillOpacity(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OPACITY)) }
  }

  @Test
  fun testFillOutlineColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOutlineColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillOutlineColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillOutlineColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillOutlineColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillOutlineColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.fillOutlineColor(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_OUTLINE_COLOR)) }
  }

  @Test
  fun testFillPatternLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillPattern("pedestrian-polygon")
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    verify(exactly = 1) { manager.dragLayer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    verify(exactly = 1) { manager.dragLayer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
  }

  @Test
  fun testFillPatternInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillPattern = "pedestrian-polygon"
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
    verify(exactly = 1) { manager.dragLayer.fillPattern(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_PATTERN)) }
  }

  @Test
  fun testFillZOffsetLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
      .withFillZOffset(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
  }

  @Test
  fun testFillZOffsetInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    val options = PolygonAnnotationOptions()
      .withPoints(listOf(listOf(Point.fromLngLat(0.0, 0.0), Point.fromLngLat(1.0, 1.0))))
    manager.fillZOffset = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
    verify(exactly = 1) { manager.dragLayer.fillZOffset(Expression.get(PolygonAnnotationOptions.PROPERTY_FILL_Z_OFFSET)) }
  }
}