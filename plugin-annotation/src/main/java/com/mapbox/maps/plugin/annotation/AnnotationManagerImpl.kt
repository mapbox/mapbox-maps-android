package com.mapbox.maps.plugin.annotation

import android.graphics.PointF
import android.view.View
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.common.Logger
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Geometry
import com.mapbox.geojson.Point
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.StyleManagerInterface
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
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.plugin.InvalidPluginConfigurationException
import com.mapbox.maps.plugin.PLUGIN_GESTURE_CLASS_NAME
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapFeatureQueryDelegate
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapStyleStateDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Base class for annotation managers
 */
abstract class AnnotationManagerImpl<G : Geometry, T : Annotation<G>, S : AnnotationOptions<G, T>, D : OnAnnotationDragListener<T>, U : OnAnnotationClickListener<T>, V : OnAnnotationLongClickListener<T>, L : Layer>(
  mapView: View,
  /** The delegateProvider */
  final override val delegateProvider: MapDelegateProvider,
  private val annotationConfig: AnnotationConfig?
) : AnnotationManager<G, T, S, D, U, V> {
  protected lateinit var style: StyleManagerInterface
  private var mapProjectionDelegate: MapProjectionDelegate = delegateProvider.mapProjectionDelegate
  private var mapFeatureQueryDelegate: MapFeatureQueryDelegate =
    delegateProvider.mapFeatureQueryDelegate
  private var styleStateDelegate: MapStyleStateDelegate = delegateProvider.styleStateDelegate
  protected val dataDrivenPropertyUsageMap: MutableMap<String, Boolean> = HashMap()
  protected val constantPropertyUsageMap = mutableListOf<PropertyValue<*>>()
  private var currentId = 0L
  private var width = 0
  private var height = 0
  private val mapClickResolver = MapClick()
  private val mapLongClickResolver = MapLongClick()
  private val mapMoveResolver = MapMove()
  private var draggedAnnotation: T? = null
  private val annotationMap = ConcurrentHashMap<Long, T>()
  protected var touchAreaShiftX: Int = mapView.scrollX
  protected var touchAreaShiftY: Int = mapView.scrollY
  protected abstract val layerId: String
  protected abstract val sourceId: String
  private var gesturesPlugin: GesturesPlugin = delegateProvider.mapPluginProviderDelegate.getPlugin(
    Class.forName(
      PLUGIN_GESTURE_CLASS_NAME
    ) as Class<GesturesPlugin>
  )
    ?: throw InvalidPluginConfigurationException(
      "Can't look up an instance of plugin, " +
        "is it available on the clazz path and loaded through the map?"
    )

  /** The layer created by this manger. Annotations will be added to this layer.*/
  internal var layer: L? = null

  /** The source created by this manger. Feature data will bed added to this source.*/
  internal var source: GeoJsonSource? = null

  /**
   * The added annotations
   */
  override val annotations: List<T>
    get() {
      return annotationMap.values.toList()
    }

  /**
   * The added dragListeners
   */
  override val dragListeners = mutableListOf<D>()

  /**
   * The Added clickListeners
   */
  override val clickListeners = mutableListOf<U>()

  /**
   * The added longClickListeners
   */
  override val longClickListeners = mutableListOf<V>()

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
   * Set filter on the managed annotations.
   */
  abstract var layerFilter: Expression?

  private fun createSource(): GeoJsonSource {
    return geoJsonSource(sourceId) {
      featureCollection(FeatureCollection.fromFeatures(listOf()))
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

  protected fun initLayerAndSource() {
    if (layer == null || source == null) {
      initializeDataDrivenPropertyMap()
      source = createSource()
      layer = createLayer()
    }

    source?.let {
      if (!style.styleSourceExists(it.sourceId)) {
        style.addSource(it)
      }
    }
    layer?.let {
      if (!style.styleLayerExists(it.layerId)) {
        if (annotationConfig?.belowLayerId == null) {
          style.addLayer(it)
        } else {
          style.addLayerBelow(it, annotationConfig.belowLayerId)
        }
      }
    }
    if (layer is SymbolLayer) {
      // Only apply cluster for SymbolManager
      initClusterLayers()
    }
    updateSource()
  }

  private fun initClusterLayers() {
    annotationConfig?.annotationSourceOptions?.clusterOptions?.let {
      it.colorLevels.forEachIndexed { level, _ ->
        val clusterLevelLayer = createClusterLevelLayer(level, it.colorLevels)
        if (!style.styleLayerExists(clusterLevelLayer.layerId)) {
          style.addLayer(clusterLevelLayer)
        }
      }
      val clusterTextLayer = createClusterTextLayer()
      if (!style.styleLayerExists(clusterTextLayer.layerId)) {
        style.addLayer(clusterTextLayer)
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
   * Invoked when the style is loaded
   */
  override fun onStyleLoaded(styleDelegate: StyleManagerInterface) {
    style = styleDelegate
    initLayerAndSource()
  }

  /**
   * Create an annotation with the option
   */
  override fun create(option: S): T {
    return option.build(currentId, this).also {
      annotationMap[it.id] = it
      currentId++
      updateSource()
    }
  }

  /**
   * Create some annotations with the options
   */
  override fun create(options: List<S>): List<T> {
    val list = options.map { option ->
      option.build(currentId, this).also {
        annotationMap[it.id] = it
        currentId++
      }
    }
    updateSource()
    return list
  }

  /**
   * Delete the annotation
   */
  override fun delete(annotation: T) {
    annotationMap.remove(annotation.id)
    updateSource()
  }

  /**
   * Delete annotations in the list
   */
  override fun delete(annotations: List<T>) {
    annotations.forEach {
      this.annotationMap.remove(it.id)
    }
    updateSource()
  }

  /**
   * Delete all the added annotations
   */
  override fun deleteAll() {
    annotationMap.clear()
    updateSource()
  }

  /**
   * Trigger an update to the underlying source
   */
  private fun updateSource() {
    if (!styleStateDelegate.isFullyLoaded()) {
      Logger.e(TAG, "Can't update source: style is not fully loaded.")
      return
    }
    source?.let { geoJsonSource ->
      if (!style.styleSourceExists(geoJsonSource.sourceId)) {
        Logger.e(TAG, "Can't update source: source has not been added to style.")
        return
      }
      annotations
        .filter { it.getType() == AnnotationType.PointAnnotation }
        .forEach {
          val symbol = it as PointAnnotation
          symbol.iconImage?.let { image ->
            if (image.startsWith(PointAnnotation.ICON_DEFAULT_NAME_PREFIX)) {
              // User set the bitmap icon, add the icon to style
              symbol.iconImageBitmap?.let { bitmap ->
                if (style.getStyleImage(image) == null) {
                  val imagePlugin = image(image) {
                    bitmap(bitmap)
                  }
                  style.addImage(imagePlugin)
                }
              }
            }
          }
        }
      val features = annotations.map {
        val annotation = Feature.fromGeometry(it.geometry, it.jsonObject)
        it.setUsedDataDrivenProperties()
        annotation
      }

      geoJsonSource.featureCollection(FeatureCollection.fromFeatures(features))
    }
  }

  /**
   * Update the annotation
   */
  override fun update(annotation: T) {
    if (annotationMap.containsKey(annotation.id)) {
      annotationMap[annotation.id] = annotation
      updateSource()
    } else {
      Logger.e(
        TAG,
        "Can't update annotation: $annotation.toString(), the annotation isn't an active annotation."
      )
    }
  }

  /**
   * Update annotations in the list
   */
  override fun update(annotations: List<T>) {
    annotations.forEach {
      if (annotationMap.containsKey(it.id)) {
        annotationMap[it.id] = it
      } else {
        Logger.e(
          TAG,
          "Can't update annotation: $it.toString(), the annotation isn't an active annotation."
        )
      }
    }
    updateSource()
  }

  /**
   * Invoked when Mapview is destroyed
   */
  override fun onDestroy() {
    gesturesPlugin.removeOnMapClickListener(mapClickResolver)
    gesturesPlugin.removeOnMapLongClickListener(mapLongClickResolver)
    gesturesPlugin.removeOnMoveListener(mapMoveResolver)
    dragListeners.clear()
    clickListeners.clear()
    longClickListeners.clear()
  }

  /**
   * Class handle the map click event
   */
  inner class MapClick : OnMapClickListener {
    /**
     * Called when the user clicks on the map view.
     *
     * @param point The projected map coordinate the user clicked on.
     * @return True if this click should be consumed and not passed further to other listeners registered afterwards,
     * false otherwise.
     */
    override fun onMapClick(point: Point): Boolean {
      if (clickListeners.isEmpty()) {
        return false
      }
      queryMapForFeatures(point) {
        clickListeners.forEach { listener ->
          listener.onAnnotationClick(it)
        }
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
      queryMapForFeatures(point) {
        longClickListeners.forEach { listener ->
          listener.onAnnotationLongClick(it)
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
        ) {
          startDragging(it)
        }
      }
    }

    /**
     * Called when the move gesture is executing.
     */
    override fun onMove(detector: MoveGestureDetector): Boolean {
      if (draggedAnnotation != null && (detector.pointersCount > 1 || !draggedAnnotation!!.isDraggable)) {
        // Stopping the drag when we don't work with a simple, on-pointer move anymore
        stopDragging()
        return true
      }

      // Updating symbol's position
      draggedAnnotation?.let { annotation ->
        val moveObject = detector.getMoveObject(0)
        val x = moveObject.currentX - touchAreaShiftX
        val y = moveObject.currentY - touchAreaShiftY
        val pointF = PointF(x, y)
        if (pointF.x < 0 || pointF.y < 0 || pointF.x > width || pointF.y > height) {
          stopDragging()
          return true
        }
        val shiftedGeometry: G? = delegateProvider.let {
          annotation.getOffsetGeometry(
            it.mapProjectionDelegate, moveObject, touchAreaShiftX, touchAreaShiftY
          )
        }
        shiftedGeometry?.let { geometry ->
          annotation.geometry = geometry
          updateSource()
          dragListeners.forEach {
            it.onAnnotationDrag(annotation)
          }
          return true
        }
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
      if (annotation.isDraggable) {
        dragListeners.forEach { it.onAnnotationDragStarted(annotation) }
        draggedAnnotation = annotation
        return true
      }
      return false
    }

    private fun stopDragging() {
      draggedAnnotation?.let { annotation ->
        dragListeners.forEach { it.onAnnotationDragFinished(annotation) }
      }
      draggedAnnotation = null
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
   * @param callback query callback
   */
  fun queryMapForFeatures(point: Point, callback: QueryAnnotationCallback<T>) {
    val screenCoordinate = mapProjectionDelegate.pixelForCoordinate(point)
    queryMapForFeatures(screenCoordinate, callback)
  }

  /**
   * Query the rendered annotation around the point
   *
   * @param screenCoordinate the screenCoordinate for querying
   * @param callback query callback
   */
  fun queryMapForFeatures(
    screenCoordinate: ScreenCoordinate,
    callback: QueryAnnotationCallback<T>
  ) {
    layer?.let {
      mapFeatureQueryDelegate.queryRenderedFeatures(
        screenCoordinate,
        RenderedQueryOptions(
          listOf(it.layerId),
          literal(true)
        )
      ) { features ->
        features.value?.let { featureList ->
          if (featureList.isNotEmpty()) {
            val id = featureList.first().getProperty(getAnnotationIdKey()).asLong
            annotationMap[id]?.let { annotation -> callback.onQueryAnnotation(annotation) }
          }
        }
      }
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    /**
     * Tag for log
     */
    private const val TAG = "AnnotationManagerImpl"
    private const val POINT_COUNT = "point_count"
    private const val CLUSTER_TEXT_LAYER_ID = "mapbox-android-cluster-text-layer"
    private val DEFAULT_TEXT_FIELD = get("point_count")
  }
}