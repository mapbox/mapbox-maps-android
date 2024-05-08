package com.mapbox.maps.extension.compose.style.terrain

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
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

/**
 * A [MutableStateFlow] to keep the latest value for a Terrain Property
 */
private typealias PropertyValueFlow = MutableStateFlow<Value>

@OptIn(MapboxExperimental::class)
internal open class TerrainStateApplier internal constructor(
  private val rasterDemSourceState: RasterDemSourceState?,
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

  internal fun getProperty(name: String): Value? =
    propertiesFlowsToCollect.replayCache.firstOrNull {
      it.first == name
    }?.second?.value

  @OptIn(MapboxExperimental::class)
  internal fun save(): TerrainState.Holder = TerrainState.Holder(
    rasterDemSourceState?.save(),
    propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value },
    initial
  )

  private companion object {
    private const val TAG = "TerrainStateApplier"
  }
}