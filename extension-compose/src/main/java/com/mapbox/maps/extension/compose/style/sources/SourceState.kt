package com.mapbox.maps.extension.compose.style.sources

import android.os.Parcelable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * A [MutableStateFlow] to keep the latest value for a Source Property
 */
private typealias PropertyValueFlow = MutableStateFlow<Value>

/**
 * The base class for SourceStates, which handles the attach/detach from the layer,
 * and updating Source properties.
 *
 * @param sourceId The id of the source state.
 * @param sourceType The type of the source in plain text.
 * @param coroutineScope The coroutine scope used to observe state flows.
 * @param builderProperties The immutable properties of the source.
 * @param initialProperties The initial mutable properties of the source.
 * @param initialGeoJsonData The initial [GeoJSONData] of the source to be used for [GeoJsonSourceState].
 */
@MapboxExperimental
public abstract class SourceState internal constructor(
  public open val sourceId: String,
  private val sourceType: String,
  private val coroutineScope: CoroutineScope = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(
      "SourceStateScope"
    )
  ),
  private val builderProperties: MutableMap<String, Value>,
  initialProperties: Map<String, Value>,
  initialGeoJsonData: GeoJSONData = GeoJSONData.default
) {
  private var mapboxMap: MapboxMap? = null
  protected val cachedGeoJsonSourceData: MutableStateFlow<GeoJSONData> =
    MutableStateFlow(initialGeoJsonData)
  private var associatedLayers: HashSet<String> = hashSetOf()
  private var geoJsonUpdateJob: Job? = null
  private var propertiesUpdateJobs: MutableList<Job> = mutableListOf()

  /**
   *  A shared flow to keep track of each property own flow ([PropertyValueFlow]).
   *  Every time a new [Pair] is emitted in this flow we will start collecting its flow
   *  ([PropertyValueFlow]), see [startCollectingPropertyFlows].
   */
  private val propertiesFlowsToCollect =
    MutableSharedFlow<Pair<String, PropertyValueFlow>>(replay = Channel.UNLIMITED)

  init {
    initialProperties.forEach {
      setProperty(it.key, it.value)
    }
  }

  internal fun attachToLayer(layerId: String, mapboxMap: MapboxMap) {
    logD(TAG, "$this attachToLayer: layerId=$layerId")
    this.mapboxMap?.let {
      if (it !== mapboxMap) {
        logW(
          TAG,
          "The source state should not be used across multiple map instances! The previous map instance will lose source updates."
        )
        // TODO clean up previous MapboxMap states
      }
    }
    this.mapboxMap = mapboxMap
    associatedLayers.add(layerId)
    addSource()
  }

  internal fun detachFromLayer(layerId: String) {
    logD(TAG, "$this detachFromLayer: layerId=$layerId")
    associatedLayers.remove(layerId)
    if (associatedLayers.isEmpty()) {
      removeSource()
      this.mapboxMap = null
    }
  }

  private fun addSource() {
    logD(TAG, "Adding source: $this")
    if (mapboxMap?.styleSourceExists(sourceId) == true) {
      logD(TAG, "Source already exists: $this")
      return
    }
    mapboxMap?.addStyleSource(
      sourceId = sourceId,
      properties = Value(
        hashMapOf("type" to Value(sourceType)).also { map ->
          if (sourceType == GEO_JSON_SOURCE_TYPE) {
            map["data"] = Value.nullValue()
          }
          map.putAll(builderProperties.entries.associate { it.key to it.value })
          // Get the most recent list of properties and their values
          map.putAll(propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value })
          logD(TAG, "Setting all properties in one go: $map")
        }
      ),
    )?.onError {
      logE(TAG, "Failed to add source: $it")
    }?.onValue {
      logD(TAG, "Added source: $this")
    }
    if (sourceType == GEO_JSON_SOURCE_TYPE) {
      geoJsonUpdateJob = coroutineScope.launch(Dispatchers.IO) {
        cachedGeoJsonSourceData.collect { data ->
          logD(TAG, "setGeoJsonSourceData: $data")
          mapboxMap?.setStyleGeoJSONSourceData(
            sourceId = sourceId,
            dataId = "",
            data = data.data
          )?.onError {
            logE(TAG, "setStyleGeoJSONSourceData failed: $it")
          }
        }
      }
    }

    startCollectingPropertyFlows()
  }

  private fun startCollectingPropertyFlows() {
    val collectNewPropertiesJob = coroutineScope.launch {
      propertiesFlowsToCollect.collect { (name: String, valueFlow: MutableStateFlow<Value>) ->
        val updatePropertyJob = coroutineScope.launch {
          valueFlow.collect { value ->
            logD(TAG, "settingProperty: name=$name, value=$value ...")
            mapboxMap?.setStyleSourceProperty(
              sourceId,
              name,
              value
            )?.onError { error ->
              logE(
                TAG,
                "Failed to set source property $name as $value on $sourceId: $error"
              )
            }?.onValue {
              logD(TAG, "settingProperty: name=$name, value=$value executed")
            }
          }
        }
        propertiesUpdateJobs.add(updatePropertyJob)
      }
    }
    propertiesUpdateJobs.add(collectNewPropertiesJob)
  }

  private fun removeSource() {
    logD(TAG, "Removing source: $this")
    mapboxMap?.removeStyleSource(sourceId)?.onError {
      logE(
        TAG,
        "Failed to remove $sourceType Source $sourceId: $it"
      )
    }
    // Stop any collect job that changes the source properties
    geoJsonUpdateJob?.cancel()
    geoJsonUpdateJob = null
    propertiesUpdateJobs.forEach(Job::cancel)
    propertiesUpdateJobs.clear()
  }

  protected fun setProperty(name: String, value: Value) {
    logD(TAG, "setProperty() called with: name = $name, value = $value")
    val setOfFlows = propertiesFlowsToCollect.replayCache
    val currentFlow: Pair<String, MutableStateFlow<Value>>? = setOfFlows.firstOrNull {
      it.first == name
    }
    if (currentFlow != null) {
      currentFlow.second.value = value
    } else {
      logD(TAG, "setProperty: emitting new property to listen to: $name")
      // Add the new property to the set of property flows we want to collect
      propertiesFlowsToCollect.tryEmit(name to MutableStateFlow(value))
    }
  }

  protected fun getProperty(name: String): Value? =
    propertiesFlowsToCollect.replayCache.firstOrNull {
      it.first == name
    }?.second?.value

  protected fun setBuilderProperty(name: String, value: Value) {
    removeSource()
    builderProperties[name] = value
    addSource()
  }

  protected fun getBuilderProperty(name: String): Value? = builderProperties[name]

  /**
   * The data class that holds the source state to restore from a Savable.
   *
   * @param sourcedId The id of the source state.
   * @param builderProperties The initial immutable properties of the source.
   * @param cachedProperties The initial mutable properties of the source.
   * @param geoJSONData The initial [GeoJSONData] of the source(used only in [GeoJsonSourceState]).
   */
  @MapboxExperimental
  @Parcelize
  public data class Holder(
    val sourcedId: String,
    val builderProperties: @RawValue Map<String, Value>,
    val cachedProperties: @RawValue Map<String, Value>,
    val geoJSONData: @RawValue GeoJSONData
  ) : Parcelable

  /**
   * Save the current SourceState to the [Holder] class.
   */
  internal fun save(): Holder = Holder(
    sourceId,
    builderProperties,
    propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value },
    cachedGeoJsonSourceData.value
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