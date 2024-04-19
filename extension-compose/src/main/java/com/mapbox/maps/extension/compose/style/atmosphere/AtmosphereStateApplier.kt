package com.mapbox.maps.extension.compose.style.atmosphere

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
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
 * A [MutableStateFlow] to keep the latest value for a Atmosphere Property
 */
private typealias PropertyValueFlow = MutableStateFlow<Value>

internal open class AtmosphereStateApplier internal constructor(
  initialProperties: Map<String, Value>,
  private val coroutineScope: CoroutineScope = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(
      "AtmosphereStateScope"
    )
  ),
) {
  private var propertiesUpdateJobs: MutableList<Job> = mutableListOf()
  private var atmosphereSet = false

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
    val replayCache = propertiesFlowsToCollect.replayCache
    if (replayCache.isNotEmpty()) {
      logD(TAG, "Adding atmosphere: $this")
      mapboxMap.setStyleAtmosphere(
        properties = Value(
          hashMapOf<String, Value>().also { map ->
            // Get the most recent list of properties and their values
            map.putAll(replayCache.associate { it.first to it.second.value })
            logD(TAG, "Setting all properties in one go: $map")
          }
        ),
      ).onError {
        logE(TAG, "Failed to add atmosphere: $it")
      }.onValue {
        logD(TAG, "Added atmosphere: $this")
        atmosphereSet = true
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
            mapboxMap.setStyleAtmosphereProperty(name, value)
              .onError { error ->
                // handle the use case of changing atmosphere e.g. for Standard style
                // and explicitly adding it so that applying property could take effect
                if (!atmosphereSet) {
                  mapboxMap.setStyleAtmosphere(Value.valueOf(hashMapOf())).onValue {
                    atmosphereSet = true
                    mapboxMap.setStyleAtmosphereProperty(name, value).onError {
                      logE(TAG, "Failed to set atmosphere property $name as $value: $error")
                    }.onValue {
                      logD(TAG, "settingProperty: name=$name, value=$value executed")
                    }
                  }.onError {
                    logE(TAG, "Failed to set atmosphere with no properties, error = $it")
                    logE(TAG, "settingProperty: name=$name, value=$value ignored")
                  }
                } else {
                  logE(TAG, "Failed to set atmosphere property $name as $value: $error")
                }
              }.onValue {
                logD(TAG, "settingProperty: name=$name, value=$value executed")
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
  internal fun save(): AtmosphereState.Holder = AtmosphereState.Holder(
    propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value },
  )

  private companion object {
    private const val TAG = "AtmosphereStateApplier"
  }
}