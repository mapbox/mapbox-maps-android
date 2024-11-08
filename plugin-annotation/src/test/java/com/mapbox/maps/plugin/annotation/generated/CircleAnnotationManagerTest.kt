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
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
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
class CircleAnnotationManagerTest {
  private val delegateProvider: MapDelegateProvider = mockk()
  private val style: MapboxStyleManager = mockk()
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mockk()
  private val mapInteractionDelegate: MapInteractionDelegate = mockk()
  private val gesturesPlugin: GesturesPlugin = mockk()
  private val moveGestureDetector: MoveGestureDetector = mockk()
  private val layer: CircleLayer = mockk()
  private val source: GeoJsonSource = mockk()
  private val dragLayer: CircleLayer = mockk()
  private val dragSource: GeoJsonSource = mockk()
  private val feature = mockk<Feature>()

  private lateinit var manager: CircleAnnotationManager
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
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(ClickInteraction::class)) }
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(LongClickInteraction::class)) }
    verify(exactly = 2) { mapInteractionDelegate.addInteraction(ofType(DragInteraction::class)) }
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
    assertTrue(manager.dragListeners.isEmpty())
    assertTrue(manager.clickListeners.isEmpty())
    assertTrue(manager.longClickListeners.isEmpty())
  }

  @Test
  fun initializeBeforeStyleLoad() {
    every { style.styleLayerExists("test_layer") } returns true
    val captureCallback = slot<(MapboxStyleManager) -> Unit>()
    every { delegateProvider.getStyle(capture(captureCallback)) } just Runs
    manager = CircleAnnotationManager(delegateProvider, AnnotationConfig("test_layer"))
    // Style is not loaded, still create and add layer to style as it's persistent layer
    verify(exactly = 1) { style.addPersistentLayer(any(), LayerPosition(null, "test_layer", null)) }
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
  fun createWithClusterOptions() {
    @Suppress("UNCHECKED_CAST")
    manager = CircleAnnotationManager(
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
    val manager = CircleAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val listener = mockk<OnCircleAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns false
    manager.addClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(CircleAnnotation.ID_KEY, "incorrectId")
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
    val manager = CircleAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnCircleAnnotationClickListener>()
    every { listener.onAnnotationClick(any()) } returns true
    manager.addClickListener(listener)

    val interactionListener = mockk<OnCircleAnnotationInteractionListener>()
    every { interactionListener.onSelectAnnotation(any()) } just Runs
    every { interactionListener.onDeselectAnnotation(any()) } just Runs
    manager.addInteractionListener(interactionListener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(CircleAnnotation.ID_KEY, annotation.id)
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
      addProperty(CircleAnnotation.ID_KEY, annotation.id)
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
    val manager = CircleAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )

    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])
    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnCircleAnnotationLongClickListener>()
    every { listener.onAnnotationLongClick(any()) } returns false
    manager.addLongClickListener(listener)

    every { feature.properties() } returns JsonObject().apply {
      addProperty(CircleAnnotation.ID_KEY, annotation.id)
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
    manager = CircleAnnotationManager(
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
    manager = CircleAnnotationManager(
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
    val manager = CircleAnnotationManager(
      delegateProvider,
      annotationConfig = AnnotationConfig(layerId = customLayerId)
    )
    manager.onSizeChanged(100, 100)
    val annotation = manager.create(
      CircleAnnotationOptions()
        .withPoint(Point.fromLngLat(0.0, 0.0))
    )
    assertEquals(annotation, manager.annotations[0])

    every { feature.getProperty(any()).asString } returns annotation.id

    val listener = mockk<OnCircleAnnotationDragListener>(relaxed = true)
    manager.addDragListener(listener)

    annotation.isDraggable = true
    val pointF = PointF(0f, 0f)
    every { moveGestureDetector.pointersCount } returns 1
    every { moveGestureDetector.focalPoint } returns pointF

    every { feature.properties() } returns JsonObject().apply {
      addProperty(CircleAnnotation.ID_KEY, annotation.id)
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
  fun testCircleSortKeyLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleSortKey(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
  }

  @Test
  fun testCircleSortKeyInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleSortKey = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
    verify(exactly = 1) { manager.dragLayer.circleSortKey(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_SORT_KEY)) }
  }

  @Test
  fun testCircleBlurLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleBlur(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
  }

  @Test
  fun testCircleBlurInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleBlur = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
    verify(exactly = 1) { manager.dragLayer.circleBlur(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_BLUR)) }
  }

  @Test
  fun testCircleColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_COLOR)) }
  }

  @Test
  fun testCircleOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
  }

  @Test
  fun testCircleOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleOpacity = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_OPACITY)) }
  }

  @Test
  fun testCircleRadiusLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleRadius(5.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    verify(exactly = 1) { manager.dragLayer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    verify(exactly = 1) { manager.dragLayer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
  }

  @Test
  fun testCircleRadiusInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleRadius = 5.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
    verify(exactly = 1) { manager.dragLayer.circleRadius(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_RADIUS)) }
  }

  @Test
  fun testCircleStrokeColorIntLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor(Color.YELLOW)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeColorLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeColor("rgba(0, 0, 0, 1)")
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeColorInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleStrokeColorString = "rgba(0, 0, 0, 1)"
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeColor(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_COLOR)) }
  }

  @Test
  fun testCircleStrokeOpacityLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeOpacity(1.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
  }

  @Test
  fun testCircleStrokeOpacityInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleStrokeOpacity = 1.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeOpacity(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_OPACITY)) }
  }

  @Test
  fun testCircleStrokeWidthLayerProperty() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
      .withCircleStrokeWidth(0.0)
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
  }

  @Test
  fun testCircleStrokeWidthInAnnotationManager() {
    every { style.styleSourceExists(any()) } returns true
    every { style.styleLayerExists(any()) } returns true
    verify(exactly = 0) { manager.layer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    val options = CircleAnnotationOptions()
      .withPoint(Point.fromLngLat(0.0, 0.0))
    manager.circleStrokeWidth = 0.0
    manager.create(options)
    verify(exactly = 1) { manager.layer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
    verify(exactly = 1) { manager.dragLayer.circleStrokeWidth(Expression.get(CircleAnnotationOptions.PROPERTY_CIRCLE_STROKE_WIDTH)) }
  }
}