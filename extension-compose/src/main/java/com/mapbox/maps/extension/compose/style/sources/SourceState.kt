package com.mapbox.maps.extension.compose.style.sources

import android.os.Parcelable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.internal.GeoJSONDataParceler
import com.mapbox.maps.extension.compose.style.internal.ValueParceler
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJsonSourceState
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

/**
 * A [MutableStateFlow] to keep the latest value for a Source Property
 */
private typealias PropertyValueFlow = MutableStateFlow<Value>

/**
 * A convenient class to hold various details about the source properties to update.
 */
private data class PropertyDetails(
  val name: String,
  val isBuilderProperty: Boolean,
  val valueFlow: PropertyValueFlow
)

/**
 * The base class for SourceStates, which handles the attach/detach from the layer,
 * and updating Source properties.
 *
 * @param sourceId The id of the source state.
 * @param sourceType The type of the source in plain text.
 * @param builderProperties The immutable properties of the source.
 * @param initialProperties The initial mutable properties of the source.
 * @param initialGeoJsonData The initial [GeoJSONData] of the source to be used for [GeoJsonSourceState].
 */
@MapboxExperimental
public abstract class SourceState internal constructor(
  public open val sourceId: String,
  private val sourceType: String,
  initialProperties: List<Triple<String, Boolean, Value>>,
  initialGeoJsonData: GeoJSONData = GeoJSONData.default
) {
  private var mapboxMap: MapboxMap? = null
  private var associatedLayers: MutableSet<String> = mutableSetOf()

  /**
   * A [CoroutineScope] that is valid for as long as this source is attached to a map.
   * See [attachTo].
   */
  private var mapboxMapScope: CoroutineScope? = null

  private val isGeoJsonSource = sourceType == GEO_JSON_SOURCE_TYPE

  /**
   * A [Channel] used to signal that new [GeoJSONData] needs to be processed.
   * We use channel instead of [MutableStateFlow] because when the source is added to the map we will
   * force setting the geojson data. It wouldn't be possible with [MutableStateFlow] because its
   * value would not change, therefore it wouldn't trigger its collection.
   */
  private var geoJSONDataChannel: Channel<GeoJSONData> = Channel(Channel.CONFLATED)

  /**
   * Property to keep the latest [GeoJSONData] for this source.
   * On set, the new value will be send to [geoJSONDataChannel] for async processing.
   */
  protected var geoJSONData: GeoJSONData = initialGeoJsonData
    set(value) {
      // We only care about different values
      if (field != value) {
        field = value
        geoJSONDataChannel.trySend(value)
      }
    }

  /**
   *  A shared flow to keep track of each property and builder property flow ([PropertyValueFlow]).
   *  Every time a new [PropertyDetails] is emitted, we will start
   *  collecting its [PropertyValueFlow] flow. See [launchCollectPropertyFlows].
   */
  private val propertiesFlowsToCollect =
    MutableSharedFlow<PropertyDetails>(replay = Channel.UNLIMITED)

  /**
   * If true it means that the source linked with this [SourceState] was already present in the
   * style or added by some other external mechanism.
   * Therefore, we should not add it again [attachToLayer] neither remove it in [detachFromLayer].
   */
  private var sourceAddedExternally = false

  init {
    initialProperties.forEach { (name, isBuilderProperty, value) ->
      if (isBuilderProperty) {
        setBuilderProperty(name, value)
      } else {
        setProperty(name, value)
      }
    }
  }

  internal fun attachToLayer(layerId: String, mapboxMap: MapboxMap) {
    logD(TAG, "$this attachToLayer: layerId=$layerId")
    this.mapboxMap?.let {
      if (it !== mapboxMap) {
        logW(TAG, "The source state should not be used across multiple map instances!")
      }
    }
    this.mapboxMap = mapboxMap
    // Check if it's the first layer linked to this source and if so attach the source to the map
    if (associatedLayers.add(layerId) && associatedLayers.size == 1) {
      attachTo(mapboxMap)
    }
  }

  internal fun detachFromLayer(layerId: String, mapboxMap: MapboxMap) {
    logD(TAG, "$this detachFromLayer: layerId=$layerId")
    associatedLayers.remove(layerId)
    if (associatedLayers.isEmpty() && !sourceAddedExternally) {
      detachFrom(mapboxMap)
      this.mapboxMap = null
    }
  }

  /**
   * Try to attach this source for the first time to the given [mapboxMap] and start reacting to the
   * source properties changes.
   */
  private fun attachTo(mapboxMap: MapboxMap) {
    logD(TAG, "attachTo() called for $sourceId")
    val sourceAdded = if (mapboxMap.styleSourceExists(sourceId)) {
      /*
      If the sourceId is already present then it means that either:
      - the source was already part of the original style
      - the source was added outside of compose (e.g. MapEffect)
      - there was a previous SourceState with the same id already added
       */
      logW(TAG, "Source [$this] already exists. This might lead to conflicting states.")
      sourceAddedExternally = true
      true
    } else {
      addSource(mapboxMap, emptyMap())
    }

    // Only if we succeeded to add the source (or was already there)
    // we start reacting to the various property changes
    if (sourceAdded) {
      val coroutineScope =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName("SourceStateMapboxMapScope_$sourceId"))
      mapboxMapScope = coroutineScope

      // Start various coroutines within the new mapboxMapScope coroutine scope
      if (isGeoJsonSource) {
        coroutineScope.launchCollectGeoJsonData(mapboxMap)
      }
      coroutineScope.launchCollectPropertyFlows(mapboxMap)
    }
  }

  /**
   * Adds this source to the given [mapboxMap] with the most recent values for its properties and
   * GeoJSONData.
   *
   * This method will called from two different places:
   * 1. The first time the source is added to the map
   * 2. Whenever a build property changes
   *
   * @param cachedProperties a map containing properties that were retrieved from gl-native directly
   * and need to be merged with the ones in this [SourceState].
   *
   * @return false if the source was already added or if it failed to be added.
   */
  private fun addSource(mapboxMap: MapboxMap, cachedProperties: Map<String, Value>): Boolean {
    logD(TAG, "Adding source: $this")
    if (mapboxMap.styleSourceExists(sourceId)) {
      logW(TAG, "Source already exists: $this")
      throw IllegalStateException("Source $sourceId already exists in map $mapboxMap")
    }
    // Merge the cachedProperties from gl-native with the ones we are aware of:
    val properties: HashMap<String, Value> = HashMap()
    properties["type"] = Value(sourceType)
    if (isGeoJsonSource && !cachedProperties.containsKey("data")) {
      // Initially set the "data" to `null` if cachedProperties does not have it.
      // Below (in `onValue`) we might send the current geoJSONData through the
      // [geoJSONDataChannel] to make it async.
      properties["data"] = Value.nullValue()
    }
    properties.putAll(cachedProperties)
    // Get the most recent list of properties (builder or not) and their values
    properties.putAll(propertiesFlowsToCollect.replayCache.associate { it.name to it.valueFlow.value })

    logD(TAG, "Setting all properties in one go: $properties")
    return mapboxMap.addStyleSource(sourceId, Value.valueOf(properties)).fold(
      {
        logE(TAG, "Failed to add source: $it")
        false
      },
      {
        logD(TAG, "Added source: $this")
        if (isGeoJsonSource && geoJSONData != GeoJSONData.default) {
          // Set the GeoJSON data after the source is added
          // Note that if the source was a pre-existing one outside of compose we will override it
          geoJSONDataChannel.trySend(geoJSONData)
        }
        true
      }
    )
  }

  /**
   * Launch a coroutine in [Dispatchers.IO] to listen for new [GeoJSONData] to set.
   */
  private fun CoroutineScope.launchCollectGeoJsonData(mapboxMap: MapboxMap) =
    launch(Dispatchers.IO) {
      geoJSONDataChannel.consumeEach { data ->
        logD(TAG, "setGeoJsonSourceData: $data")

        mapboxMap.setStyleGeoJSONSourceData(
          sourceId = sourceId,
          dataId = "",
          data = data.data
        ).onError {
          logE(TAG, "setStyleGeoJSONSourceData failed: $it")
        }
      }
    }

  /**
   * Launch a coroutine that will listen for new [PropertyValueFlow] to collect.
   */
  private fun CoroutineScope.launchCollectPropertyFlows(mapboxMap: MapboxMap) =
    launch {
      // Every time a property changes its value for the first time there will be a new propertyFlow
      // We start collecting those here
      propertiesFlowsToCollect.collect { details: PropertyDetails ->
        launchCollectProperty(details, mapboxMap)
      }
    }

  /**
   * Launch a coroutine to collect property changes for the [PropertyValueFlow] in [details].
   * We use separate coroutine so we can benefit from backpressure. That is, we only process
   * the most recent Value per each property.
   */
  private fun CoroutineScope.launchCollectProperty(details: PropertyDetails, mapboxMap: MapboxMap) =
    launch {
      logD(TAG, "startCollectingPropertyFlows: start collecting $details")
      // Builder property are updated differently than normal ones
      if (details.isBuilderProperty) {
        details.valueFlow.collectBuilderProperty(details.name, mapboxMap)
      } else {
        details.valueFlow.collectProperty(details.name, mapboxMap)
      }
    }

  private suspend fun PropertyValueFlow.collectBuilderProperty(name: String, mapboxMap: MapboxMap) {
    collect { value ->
      logD(TAG, "collectBuilderProperty: name=$name, value=$value ...")
      // Before removing the source we request its values from gl-native if it is a Source that was
      // added outside of compose.
      val cachedProperties: Map<String, Value> = if (sourceAddedExternally) {
        mapboxMap.getStyleSourceProperties(sourceId).fold(
          { error ->
            logW(TAG, "Unable to cache $sourceId properties: $error")
            emptyMap()
          },
          { values ->
            @Suppress("UNCHECKED_CAST")
            values.contents as Map<String, Value>
          }
        )
      } else {
        emptyMap()
      }
      removeSource(mapboxMap)
      // Add source is responsible to collect the new builder property value and set it
      addSource(mapboxMap, cachedProperties)
    }
  }

  private suspend fun PropertyValueFlow.collectProperty(name: String, mapboxMap: MapboxMap) {
    collect { value ->
      logD(TAG, "settingProperty: name=$name, value=$value ...")
      mapboxMap.setStyleSourceProperty(sourceId, name, value)
        .onError { error ->
          logE(TAG, "Failed to set source property $name as $value on $sourceId: $error")
        }.onValue {
          logD(TAG, "settingProperty: name=$name, value=$value executed")
        }
    }
  }

  /**
   * Detach this source from the given [mapboxMap].
   */
  private fun detachFrom(mapboxMap: MapboxMap) {
    removeSource(mapboxMap)
    // Stop any collect job that changes the source properties
    mapboxMapScope?.cancel()

    if (isGeoJsonSource) {
      // Canceling above scope also closes the channel.
      // Create it again to be collected later on attach.
      geoJSONDataChannel = Channel(capacity = Channel.CONFLATED)
    }
  }

  private fun removeSource(mapboxMap: MapboxMap) {
    logD(TAG, "Removing source: $this")
    mapboxMap.removeStyleSourceUnchecked(sourceId).onError {
      logE(TAG, "Failed to remove $sourceType Source $sourceId: $it")
    }
  }

  protected fun setProperty(name: String, value: Value) {
    setProperty(name, value, false)
  }

  protected fun setBuilderProperty(name: String, value: Value) {
    setProperty(name, value, true)
  }

  private fun setProperty(
    name: String,
    value: Value,
    isBuilderProperty: Boolean
  ) {
    logD(
      TAG,
      "setProperty() called with: name = $name, value = $value, isBuilderProperty = $isBuilderProperty"
    )
    val setOfFlows = propertiesFlowsToCollect.replayCache
    val currentFlow: PropertyDetails? = setOfFlows.firstOrNull {
      it.name == name
    }
    if (currentFlow != null) {
      currentFlow.valueFlow.value = value
    } else {
      logD(TAG, "setProperty: emitting new property to listen to: $name")
      // Add the new property to the set of property flows we want to collect
      val details = PropertyDetails(name, isBuilderProperty, MutableStateFlow(value))
      propertiesFlowsToCollect.tryEmit(details)
    }
  }

  /**
   * @return the most up-to-date value for the [name] property.
   */
  protected fun getProperty(name: String): Value? =
    propertiesFlowsToCollect.replayCache.firstOrNull {
      it.name == name
    }?.valueFlow?.value

  /**
   * The data class that holds the source state to restore from a Savable.
   *
   * @param sourcedId The id of the source state.
   * @param cachedProperties The initial mutable properties of the source.
   * @param geoJSONData The initial [GeoJSONData] of the source(used only in [GeoJsonSourceState]).
   */
  @MapboxExperimental
  @Parcelize
  @TypeParceler<GeoJSONData, GeoJSONDataParceler>
  @TypeParceler<Value, ValueParceler>
  public data class Holder(
    val sourcedId: String,
    val cachedProperties: List<Triple<String, Boolean, Value>>,
    val geoJSONData: GeoJSONData
  ) : Parcelable

  /**
   * Save the current SourceState to the [Holder] class.
   */
  internal fun save(): Holder = Holder(
    sourceId,
    propertiesFlowsToCollect.replayCache.map { Triple(it.name, it.isBuilderProperty, it.valueFlow.value) },
    geoJSONData
  )

  /**
   * Overrides the toString method to print more meaningful information.
   */
  override fun toString(): String {
    return "SourceState(sourceType=$sourceType, sourceId=$sourceId)"
  }

  private companion object {
    private const val TAG = "SourceState"
    private const val GEO_JSON_SOURCE_TYPE = "geojson"
  }
}