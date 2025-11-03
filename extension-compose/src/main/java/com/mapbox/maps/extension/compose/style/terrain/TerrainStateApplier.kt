package com.mapbox.maps.extension.compose.style.terrain

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.sources.generated.RasterDemSourceState
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Objects

/**
 * A [MutableStateFlow] to keep the latest value for a Terrain Property
 */
private typealias PropertyValueFlow = MutableStateFlow<Value>

/**
 * Propagates the terrain state properties to [MapboxMap].
 *
 * There are two special cases:
 * 1. if [initial] is true then this class will behave as no-op. That is, it won't change anything related to Terrain from current [MapboxMap] state.
 * 2. if [initial] is false but [rasterDemSourceState] is `null` then terrain will be removed (the default (`Value.nullValue()`) in gl-native engine).
 */
internal class TerrainStateApplier internal constructor(
  internal val rasterDemSourceState: RasterDemSourceState?,
  initialProperties: Map<String, Value>,
  internal val initial: Boolean,
  private val coroutineScope: CoroutineScope = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(
      "TerrainStateScope"
    )
  ),
) {
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

  internal fun attachTo(mapboxMap: MapboxMap) {
    if (initial) {
      return
    }
    mapboxMap.setStyleTerrain(
      properties = if (rasterDemSourceState != null) {
        Value(
          hashMapOf<String, Value>().also { map ->
            map["source"] = Value(rasterDemSourceState.sourceId)
            // Get the most recent list of properties and their values
            map.putAll(propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value })
            logD(TAG, "Setting all properties in one go: $map")
          }
        ).also {
          logD(TAG, "Adding terrain: $this")
        }
      } else {
        Value.nullValue().also {
          logD(TAG, "Removing terrain: $this")
        }
      },
    ).onError {
      logE(TAG, "Failed to add terrain: $it")
    }.onValue {
      if (rasterDemSourceState != null) {
        logD(TAG, "Added terrain: $this")
      }
    }
    startCollectingPropertyFlows(mapboxMap)
  }

  private fun startCollectingPropertyFlows(mapboxMap: MapboxMap) {
    val collectNewPropertiesJob = coroutineScope.launch {
      propertiesFlowsToCollect.collect { (name: String, valueFlow: MutableStateFlow<Value>) ->
        val updatePropertyJob = coroutineScope.launch {
          valueFlow.collect { value ->
            logD(TAG, "settingProperty: name=$name, value=$value ...")
            mapboxMap.setStyleTerrainProperty(name, value).onValue {
              logD(TAG, "settingProperty: name=$name, value=$value executed")
            }.onError { error ->
              logE(TAG, "Failed to set terrain property $name as $value: $error")
            }
          }
        }
        propertiesUpdateJobs.add(updatePropertyJob)
      }
    }
    propertiesUpdateJobs.add(collectNewPropertiesJob)
  }

  internal fun detach() {
    // Stop any collect job that changes the source properties
    propertiesUpdateJobs.forEach(Job::cancel)
    propertiesUpdateJobs.clear()
  }

  internal fun setProperty(name: String, value: Value) {
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

  internal fun save(properties: Map<String, Value>): TerrainState.Holder = TerrainState.Holder(
    rasterDemSourceState?.save(),
    properties,
    initial
  )

  /**
   * See [Any.equals]
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TerrainStateApplier

    if (rasterDemSourceState != other.rasterDemSourceState) return false
    if (initial != other.initial) return false

    val thisProperties = propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    val otherProperties = other.propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    if (thisProperties != otherProperties) return false

    return true
  }

  /**
   * See [Any.hashCode]
   */
  override fun hashCode(): Int {
    val thisProperties = propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    return Objects.hash(rasterDemSourceState, initial, thisProperties)
  }

  /**
   * Returns a string representation of the object.
   */
  override fun toString(): String {
    return "TerrainStateApplier(rasterDemSourceState=$rasterDemSourceState, initial=$initial)"
  }

  private companion object {
    private const val TAG = "TerrainStateApplier"
  }
}