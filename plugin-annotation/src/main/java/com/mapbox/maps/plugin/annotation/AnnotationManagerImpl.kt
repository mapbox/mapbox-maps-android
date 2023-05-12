package com.mapbox.maps.plugin.annotation

import android.graphics.PointF
import androidx.annotation.MainThread
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.all
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.gt
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.gte
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.has
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.lt
import com.mapbox.maps.extension.style.image.addImage
import com.mapbox.maps.extension.style.image.image
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.addPersistentLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.Plugin.Companion.MAPBOX_GESTURES_PLUGIN_ID
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.delegates.*
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.LinkedHashMap

/**
 * Base class for annotation managers
 */
@MainThread
abstract class AnnotationManagerImpl<G : Geometry, T : Annotation<G>, S : AnnotationOptions<G, T>, D : OnAnnotationDragListener<T>, U : OnAnnotationClickListener<T>, V : OnAnnotationLongClickListener<T>, I : OnAnnotationInteractionListener<T>, L : Layer>(
  /** The delegateProvider */
  final override val delegateProvider: MapDelegateProvider,
  private val annotationConfig: AnnotationConfig?
) : AnnotationManager<G, T, S, D, U, V, I> {
  private var mapCameraManagerDelegate: MapCameraManagerDelegate =
    delegateProvider.mapCameraManagerDelegate
  private var mapFeatureQueryDelegate: MapFeatureQueryDelegate =
    delegateProvider.mapFeatureQueryDelegate
  private var mapListenerDelegate: MapListenerDelegate = delegateProvider.mapListenerDelegate
  protected val dataDrivenPropertyUsageMap: MutableMap<String, Boolean> = HashMap()
  private var width = 0
  private var height = 0
  private val mapClickResolver = MapClick()
  private val mapLongClickResolver = MapLongClick()
  private val mapMoveResolver = MapMove()
  private var draggingAnnotation: T? = null
  private val annotationMap = LinkedHashMap<String, T>()
  private val dragAnnotationMap = LinkedHashMap<String, T>()
  protected abstract val layerId: String
  protected abstract val sourceId: String
  protected abstract val dragLayerId: String
  protected abstract val dragSourceId: String

  @Suppress("UNCHECKED_CAST")
  private var gesturesPlugin: GesturesPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
    MAPBOX_GESTURES_PLUGIN_ID
  ) ?: throw InvalidPluginConfigurationException(
    "Can't look up an instance of plugin, is it available on the clazz path and loaded through the map?"
  )

  /** The layer created by this manager. Annotations will be added to this layer.*/
  internal var layer: L? = null

  /** The source created by this manager. Feature data will be added to this source.*/
  internal var source: GeoJsonSource? = null

  /** The drag layer created by this manager. The dragging annotation will be added to this layer.*/
  internal var dragLayer: L? = null

  /** The drag source created by this manager. The feature data of dragging annotation will be added to this source.*/
  internal var dragSource: GeoJsonSource? = null

  /**
   * The added annotations
   */
  override val annotations: List<T>
    get() {
      return annotationMap.values.plus(dragAnnotationMap.values)
    }

  /**
   * The added dragListeners
   */
  override val dragListeners: MutableList<D> = mutableListOf()

  /**
   * The Added clickListeners
   */
  override val clickListeners: MutableList<U> = mutableListOf()

  /**
   * The added longClickListeners
   */
  override val longClickListeners: MutableList<V> = mutableListOf()

  /**
   * The added interactionListener
   */
  override val interactionListener: MutableList<I> = mutableListOf()

  init {
    gesturesPlugin.addOnMapClickListener(mapClickResolver)
    gesturesPlugin.addOnMapLongClickListener(mapLongClickResolver)
    gesturesPlugin.addOnMoveListener(mapMoveResolver)
  }

  /**
   * Get the key of the id of the annotation.
   *
   * @return the key of the id of the annotation
   */
  abstract fun getAnnotationIdKey(): String

  /**
   * Create the layer for managed annotations
   *
   * @return the layer created
   */
  protected abstract fun createLayer(): L

  /**
   * Create the drag layer for dragging annotations
   *
   * @return the layer created
   */
  protected abstract fun createDragLayer(): L

  /**
   * Set filter on the managed annotations.
   */
  abstract var layerFilter: Expression?

  private fun createSource(): GeoJsonSource {
    return geoJsonSource(sourceId) {
      annotationConfig?.annotationSourceOptions?.let { options ->
        options.maxZoom?.let {
          maxzoom(it)
        }
        options.buffer?.let {
          buffer(it)
        }
        options.lineMetrics?.let {
          lineMetrics(it)
        }
        options.tolerance?.let {
          tolerance(it)
        }
        options.clusterOptions?.let { clusterOptions ->
          cluster(clusterOptions.cluster)
          clusterMaxZoom(clusterOptions.clusterMaxZoom)
          clusterRadius(clusterOptions.clusterRadius)
          clusterOptions.clusterProperties?.let {
            clusterProperties(it)
          }
        }
      }
    }
  }

  private fun createDragSource(): GeoJsonSource {
    return geoJsonSource(dragSourceId) {
      annotationConfig?.annotationSourceOptions?.let { options ->
        options.maxZoom?.let {
          maxzoom(it)
        }
        options.buffer?.let {
          buffer(it)
        }
        options.lineMetrics?.let {
          lineMetrics(it)
        }
        options.tolerance?.let {
          tolerance(it)
        }
      }
    }
  }

  protected fun initLayerAndSource(style: Style) {
    if (layer == null || source == null) {
      initializeDataDrivenPropertyMap()
      source = createSource()
      layer = createLayer()
      dragSource = createDragSource()
      dragLayer = createDragLayer()
    }

    source?.let {
      if (!style.styleSourceExists(it.sourceId)) {
        style.addSource(it)
      }
    }

    layer?.let {
      if (!style.styleLayerExists(it.layerId)) {
        var layerAdded = false
        annotationConfig?.belowLayerId?.let { belowLayerId ->
          // Check whether the below layer exists in the current style.
          if (style.styleLayerExists(belowLayerId)) {
            style.addPersistentLayer(it, LayerPosition(null, annotationConfig.belowLayerId, null))
            layerAdded = true
          } else {
            logW(
              TAG,
              "Layer with id $belowLayerId doesn't exist in style ${style.styleURI}, will add annotation layer directly."
            )
          }
        }
        if (!layerAdded) {
          style.addPersistentLayer(it)
        }
      }
    }

    dragSource?.let {
      if (!style.styleSourceExists(it.sourceId)) {
        style.addSource(it)
      }
    }
    dragLayer?.let {
      if (!style.styleLayerExists(it.layerId)) {
        layer?.layerId?.let { aboveLayerId ->
          // Add drag layer above the annotation layer
          style.addPersistentLayer(it, LayerPosition(aboveLayerId, null, null))
        }
      }
    }
    if (layer is SymbolLayer) {
      // Only apply cluster for SymbolManager
      initClusterLayers(style)
    }
    updateSource()
  }

  private fun initClusterLayers(style: Style) {
    annotationConfig?.annotationSourceOptions?.clusterOptions?.let {
      it.colorLevels.forEachIndexed { level, _ ->
        val clusterLevelLayer = createClusterLevelLayer(level, it.colorLevels)
        if (!style.styleLayerExists(clusterLevelLayer.layerId)) {
          style.addPersistentLayer(clusterLevelLayer)
        }
      }
      val clusterTextLayer = createClusterTextLayer()
      if (!style.styleLayerExists(clusterTextLayer.layerId)) {
        style.addPersistentLayer(clusterTextLayer)
      }
    }
  }

  private fun createClusterLevelLayer(level: Int, colorLevels: List<Pair<Int, Int>>) =
    circleLayer("mapbox-android-cluster-circle-layer-$level", sourceId) {
      circleColor(colorLevels[level].second)
      annotationConfig?.annotationSourceOptions?.clusterOptions?.let {
        if (it.circleRadiusExpression == null) {
          circleRadius(it.circleRadius)
        } else {
          circleRadius(it.circleRadiusExpression as Expression)
        }
      }
      val pointCount = Expression.toNumber(get(POINT_COUNT))
      filter(
        if (level == 0) all(
          has(POINT_COUNT),
          gte(pointCount, literal(colorLevels[level].first.toLong()))
        ) else all(
          has(POINT_COUNT),
          gt(pointCount, literal(colorLevels[level].first.toLong())),
          lt(pointCount, literal(colorLevels[level - 1].first.toLong()))
        )
      )
    }

  private fun createClusterTextLayer() = symbolLayer(CLUSTER_TEXT_LAYER_ID, sourceId) {
    annotationConfig?.annotationSourceOptions?.clusterOptions?.let {
      textField(if (it.textField == null) DEFAULT_TEXT_FIELD else it.textField as Expression)
      if (it.textSizeExpression == null) {
        textSize(it.textSize)
      } else {
        textSize(it.textSizeExpression as Expression)
      }
      if (it.textColorExpression == null) {
        textColor(it.textColor)
      } else {
        textColor(it.textColorExpression as Expression)
      }
      textIgnorePlacement(true)
      textAllowOverlap(true)
    }
  }

  /**
   * Create an annotation with the option
   */
  override fun create(option: S): T {
    return option.build(UUID.randomUUID().toString(), this).also {
      annotationMap[it.id] = it
      updateSource()
    }
  }

  /**
   * Create some annotations with the options
   */
  override fun create(options: List<S>): List<T> {
    val list = options.map { option ->
      option.build(UUID.randomUUID().toString(), this).also {
        annotationMap[it.id] = it
      }
    }
    updateSource()
    return list
  }

  /**
   * Delete the annotation
   */
  override fun delete(annotation: T) {
    when {
      annotationMap.containsKey(annotation.id) -> {
        annotationMap.remove(annotation.id)
        updateSource()
      }
      dragAnnotationMap.containsKey(annotation.id) -> {
        dragAnnotationMap.remove(annotation.id)
        updateDragSource()
      }
      else -> {
        logE(
          TAG,
          "Can't delete annotation: $annotation, the annotation isn't an active annotation."
        )
      }
    }
  }

  /**
   * Delete annotations in the list
   */
  override fun delete(annotations: List<T>) {
    var needUpdateSource = false
    var needUpdateDragSource = false
    annotations.forEach {
      if (annotationMap.containsKey(it.id)) {
        annotationMap.remove(it.id)
        needUpdateSource = true
      } else if (dragAnnotationMap.containsKey(it.id)) {
        dragAnnotationMap.remove(it.id)
        needUpdateDragSource = true
      }
    }
    if (needUpdateSource) {
      updateSource()
    }
    if (needUpdateDragSource) {
      updateDragSource()
    }
  }

  /**
   * Delete all the added annotations
   */
  override fun deleteAll() {
    if (annotationMap.isNotEmpty()) {
      annotationMap.clear()
      updateSource()
    }
    if (dragAnnotationMap.isNotEmpty()) {
      dragAnnotationMap.clear()
      updateDragSource()
    }
  }

  private fun updateDragSource() {
    delegateProvider.getStyle { style ->
      dragSource?.let { geoJsonSource ->
        dragLayer?.let { layer ->
          if (!style.styleSourceExists(geoJsonSource.sourceId) || !style.styleLayerExists(layer.layerId)) {
            logE(
              TAG,
              "Can't update dragSource: drag source or layer has not been added to style."
            )
            return@getStyle
          }
          addIconToStyle(style, dragAnnotationMap.values)
          val features = convertAnnotationsToFeatures(dragAnnotationMap.values)
          geoJsonSource.featureCollection(FeatureCollection.fromFeatures(features))
        }
      }
    }
  }

  /**
   * Trigger an update to the underlying source
   */
  private fun updateSource() {
    delegateProvider.getStyle { style ->
      if (source == null || layer == null) {
        initLayerAndSource(style)
      }
      source?.let { geoJsonSource ->
        layer?.let { layer ->
          if (!style.styleSourceExists(geoJsonSource.sourceId) || !style.styleLayerExists(layer.layerId)) {
            logE(TAG, "Can't update source: source or layer has not been added to style.")
            return@getStyle
          }
          addIconToStyle(style, annotationMap.values)
          val features = convertAnnotationsToFeatures(annotationMap.values)
          geoJsonSource.featureCollection(FeatureCollection.fromFeatures(features))
        }
      }
    }
  }

  private fun addIconToStyle(style: Style, annotations: Collection<T>) {
    annotations
      .filter { it.getType() == AnnotationType.PointAnnotation }
      .forEach {
        val symbol = it as PointAnnotation
        symbol.iconImage?.let { image ->
          if (image.startsWith(PointAnnotation.ICON_DEFAULT_NAME_PREFIX)) {
            // User set the bitmap icon, add the icon to style
            symbol.iconImageBitmap?.let { bitmap ->
              val imagePlugin = image(image) {
                bitmap(bitmap)
              }
              style.addImage(imagePlugin)
            }
          }
        }
      }
  }

  private fun convertAnnotationsToFeatures(annotations: Collection<T>): List<Feature> =
    annotations.map {
      it.setUsedDataDrivenProperties()
      Feature.fromGeometry(it.geometry, it.getJsonObjectCopy(), it.id)
    }

  /**
   * Update the annotation
   */
  override fun update(annotation: T) {
    when {
      annotationMap.containsKey(annotation.id) -> {
        annotationMap[annotation.id] = annotation
        updateSource()
      }
      dragAnnotationMap.containsKey(annotation.id) -> {
        dragAnnotationMap[annotation.id] = annotation
        updateDragSource()
      }
      else -> {
        logE(
          TAG,
          "Can't update annotation: $annotation, the annotation isn't an active annotation."
        )
      }
    }
  }

  /**
   * Update annotations in the list
   */
  override fun update(annotations: List<T>) {
    var needUpdateSource = false
    var needUpdateDragSource = false
    annotations.forEach {
      when {
        annotationMap.containsKey(it.id) -> {
          annotationMap[it.id] = it
          needUpdateSource = true
        }
        dragAnnotationMap.containsKey(it.id) -> {
          dragAnnotationMap[it.id] = it
          needUpdateDragSource = true
        }
        else -> {
          logE(
            TAG,
            "Can't update annotation: $it, the annotation isn't an active annotation."
          )
        }
      }
    }
    if (needUpdateSource) {
      updateSource()
    }
    if (needUpdateDragSource) {
      updateDragSource()
    }
  }

  /**
   * Invoked when Mapview or Annotation manager is destroyed.
   */
  override fun onDestroy() {
    delegateProvider.getStyle { style ->
      layer?.let {
        if (style.styleLayerExists(it.layerId)) {
          style.removeStyleLayer(it.layerId)
        }
      }
      source?.let {
        if (style.styleSourceExists(it.sourceId)) {
          style.removeStyleSource(it.sourceId)
        }
      }
      dragLayer?.let {
        if (style.styleLayerExists(it.layerId)) {
          style.removeStyleLayer(it.layerId)
        }
      }
      dragSource?.let {
        if (style.styleSourceExists(it.sourceId)) {
          style.removeStyleSource(it.sourceId)
        }
      }
    }

    gesturesPlugin.removeOnMapClickListener(mapClickResolver)
    gesturesPlugin.removeOnMapLongClickListener(mapLongClickResolver)
    gesturesPlugin.removeOnMoveListener(mapMoveResolver)
    annotationMap.clear()
    dragAnnotationMap.clear()
    dragListeners.clear()
    clickListeners.clear()
    longClickListeners.clear()
    interactionListener.clear()
  }

  /**
   * Toggles the annotation's selection state.
   * If the annotation is deselected, it becomes selected.
   * If the annotation is selected, it becomes deselected.
   * @param annotation: The annotation to select.
   */
  override fun selectAnnotation(annotation: T) {
    when {
      annotationMap.containsKey(annotation.id) -> {
        annotation.isSelected = !annotation.isSelected
        annotationMap[annotation.id] = annotation
        interactionListener.forEach {
          if (annotation.isSelected) {
            it.onSelectAnnotation(annotation)
          } else {
            it.onDeselectAnnotation(annotation)
          }
        }
      }
      dragAnnotationMap.containsKey(annotation.id) -> {
        annotation.isSelected = !annotation.isSelected
        dragAnnotationMap[annotation.id] = annotation
        interactionListener.forEach {
          if (annotation.isSelected) {
            it.onSelectAnnotation(annotation)
          } else {
            it.onDeselectAnnotation(annotation)
          }
        }
      }
      else -> {
        logE(
          TAG,
          "Can't select annotation: $annotation, the annotation isn't an active annotation."
        )
      }
    }
  }

  /**
   * Class handle the map click event
   */
  inner class MapClick : OnMapClickListener {
    /**
     * Called when the user clicks on the map view.
     * Note that calling this method is blocking main thread until querying map for features is finished.
     *
     * @param point The projected map coordinate the user clicked on.
     * @return True if this click should be consumed and not passed further to other listeners registered afterwards,
     * false otherwise.
     */
    override fun onMapClick(point: Point): Boolean {
      queryMapForFeatures(point)?.let {
        clickListeners.forEach { listener ->
          if (listener.onAnnotationClick(it)) {
            return true
          }
        }
        selectAnnotation(it)
      }
      return false
    }
  }

  /**
   * Class handle the map long click event
   */
  inner class MapLongClick : OnMapLongClickListener {
    /**
     * Called when the user long clicks on the map view.
     *
     * @param point The projected map coordinate the user clicked on.
     * @return True if this click should be consumed and not passed further to other listeners registered afterwards,
     * false otherwise.
     */
    override fun onMapLongClick(point: Point): Boolean {
      if (longClickListeners.isEmpty()) {
        return false
      }
      queryMapForFeatures(point)?.let {
        longClickListeners.forEach { listener ->
          if (listener.onAnnotationLongClick(it)) {
            return true
          }
        }
      }
      return false
    }
  }

  /**
   * Class handle the map move event
   */
  inner class MapMove : OnMoveListener {
    /**
     * Called when the move gesture is starting.
     */
    override fun onMoveBegin(detector: MoveGestureDetector) {
      if (detector.pointersCount == 1) {
        queryMapForFeatures(
          ScreenCoordinate(
            detector.focalPoint.x.toDouble(),
            detector.focalPoint.y.toDouble()
          )
        )?.let {
          startDragging(it)
        }
      }
    }

    /**
     * Called when the move gesture is executing.
     */
    override fun onMove(detector: MoveGestureDetector): Boolean {
      // Updating symbol's position
      draggingAnnotation?.let { annotation ->
        if (detector.pointersCount > 1 || !annotation.isDraggable) {
          // Stopping the drag when we don't work with a simple, on-pointer move anymore
          stopDragging()
          return true
        }
        val moveObject = detector.getMoveObject(0)
        val x = moveObject.currentX
        val y = moveObject.currentY
        val pointF = PointF(x, y)
        if (pointF.x < 0 || pointF.y < 0 || pointF.x > width || pointF.y > height) {
          stopDragging()
          return true
        }

        if (annotationMap.containsKey(annotation.id)) {
          // Delete the dragging annotation from original source and add it to drag source
          annotationMap.remove(annotation.id)
          dragAnnotationMap[annotation.id] = annotation
          updateSource()
        }

        delegateProvider.let {
          annotation.getOffsetGeometry(
            it.mapCameraManagerDelegate, moveObject
          )
        }?.let { geometry ->
          annotation.geometry = geometry
          updateDragSource()
          dragListeners.forEach {
            it.onAnnotationDrag(annotation)
          }
          return true
        }

        /* The dragging annotation has been removed from original source,
         update drag source to make sure it is shown in drag layer.
         */
        updateDragSource()
      }
      return false
    }

    /**
     * Called when the move gesture is ending.
     */
    override fun onMoveEnd(detector: MoveGestureDetector) {
      // Stopping the drag when move ends
      stopDragging()
    }

    private fun startDragging(annotation: T): Boolean {
      if (!annotation.isDraggable) {
        return false
      }
      dragListeners.forEach { it.onAnnotationDragStarted(annotation) }
      draggingAnnotation = annotation
      return true
    }

    private fun stopDragging() {
      draggingAnnotation?.let { annotation ->
        dragListeners.forEach { it.onAnnotationDragFinished(annotation) }
        draggingAnnotation = null
      }
    }
  }

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  override fun onSizeChanged(width: Int, height: Int) {
    this.width = width
    this.height = height
  }

  /**
   * Enable a data-driven property
   */
  override fun enableDataDrivenProperty(property: String) {
    if (dataDrivenPropertyUsageMap[property] == false) {
      dataDrivenPropertyUsageMap[property] = true
      setDataDrivenPropertyIsUsed(property)
    }
  }

  /**
   * Update a data-driven property to used state. Please visit [The online documentation](https://docs.mapbox.com/android/maps/guides/data-driven-styling/) for more details about data-driven-styling
   */
  protected abstract fun setDataDrivenPropertyIsUsed(property: String)

  /**
   * Init data-driven properties map. please visit [The online documentation](https://docs.mapbox.com/android/maps/guides/data-driven-styling/ for more details about data-driven-styling)
   */
  protected abstract fun initializeDataDrivenPropertyMap()

  /**
   * Query the rendered annotation around the point
   *
   * @param point the point for querying
   * @return the queried annotation at this point
   */
  fun queryMapForFeatures(point: Point): T? {
    val screenCoordinate = mapCameraManagerDelegate.pixelForCoordinate(point)
    return queryMapForFeatures(screenCoordinate)
  }

  /**
   * Query the rendered annotation around the point
   *
   * @param screenCoordinate the screenCoordinate for querying
   * @return the queried annotation on this screenCoordinate
   */
  fun queryMapForFeatures(screenCoordinate: ScreenCoordinate): T? {
    var annotation: T? = null
    val layerList = mutableListOf<String>()
    layer?.let {
      layerList.add(it.layerId)
    }
    dragLayer?.let {
      layerList.add(it.layerId)
    }
    val latch = CountDownLatch(1)
    mapFeatureQueryDelegate.executeOnRenderThread {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        RenderedQueryGeometry(screenCoordinate),
        RenderedQueryOptions(
          layerList,
          literal(true)
        )
      ) { features ->
        features.value?.firstOrNull()?.queriedFeature?.feature?.getProperty(getAnnotationIdKey())
          ?.let { annotationId ->
            val id = annotationId.asString
            when {
              annotationMap.containsKey(id) -> {
                annotation = annotationMap[id]
              }
              dragAnnotationMap.containsKey(id) -> {
                annotation = dragAnnotationMap[id]
              }
              else -> {
                logE(
                  TAG,
                  "The queried id: $id, doesn't belong to an active annotation."
                )
              }
            }
          }
        latch.countDown()
      }
    }
    latch.await(QUERY_WAIT_TIME, TimeUnit.SECONDS)
    return annotation
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    /**
     * Tag for log
     */
    private const val TAG = "AnnotationManagerImpl"
    private const val POINT_COUNT = "point_count"

    /** At most wait 2 seconds to prevent ANR */
    private const val QUERY_WAIT_TIME = 2L
    private const val CLUSTER_TEXT_LAYER_ID = "mapbox-android-cluster-text-layer"
    private val DEFAULT_TEXT_FIELD = get("point_count")
  }
}