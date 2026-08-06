package com.mapbox.maps.extension.compose.style.precipitations

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.precipitations.generated.removeRain
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logI
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
import java.util.Objects

/**
 * A [MutableStateFlow] to keep the latest value for the Property
 */
internal typealias PropertyValueFlow = MutableStateFlow<Value>

@MapboxExperimental
internal class RainStateApplier internal constructor(
  initialProperties: Map<String, Value>,
  private val coroutineScope: CoroutineScope = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(
      "RainStateScope"
    )
  ),
  internal val enabled: Boolean
) {
  private var propertiesUpdateJobs: MutableList<Job> = mutableListOf()
  private var rainSet = false

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

  /**
   * Attaches this applier to [mapboxMap].
   *
   * @param styleDefaults the root-level `rain` values baked into the loaded style JSON, captured
   * once per style load (see `StyleDefaults`). Used for whole-object reset/merge:
   * - disabled: non-empty [styleDefaults] -> reset rain to the style's own value
   *   (`setStyleRain(styleDefaults)`); empty -> `removeRain()` (style shipped none).
   * - enabled: [styleDefaults] merged with the user-set properties, user overrides winning,
   *   fills any sub-property left unset by the user.
   */
  internal fun attachTo(mapboxMap: MapboxMap, styleDefaults: Map<String, Value> = emptyMap()) {
    // `attachTo` can legitimately be called more than once on the same instance without an
    // intervening `detach()` -- e.g. `MapStyleNode`'s consolidated STYLE collector re-attaches
    // every emission (real style reload/switch) using the node's *current* state. Cancel any
    // property-collector jobs from a previous `attachTo` call first, otherwise `startCollectingPropertyFlows`
    // below would pile up duplicate collectors (and duplicate native property-setter calls) on every
    // re-attach.
    detach()
    if (!enabled) {
      if (styleDefaults.isNotEmpty()) {
        logD(TAG, "Resetting rain to style default: $styleDefaults")
        mapboxMap.setStyleRain(Value(HashMap(styleDefaults))).onError {
          logE(TAG, "Failed to reset rain to style default: $it")
        }
      } else {
        mapboxMap.removeRain()
      }
      return
    }
    val userProperties = propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    val merged = HashMap<String, Value>(styleDefaults).apply { putAll(userProperties) }
    if (merged.isNotEmpty()) {
      logD(TAG, "Adding rain: $this")
      mapboxMap.setStyleRain(
        properties = Value(merged),
      ).onError {
        logE(TAG, "Failed to add rain: $it")
      }.onValue {
        logD(TAG, "Added rain: $this")
        rainSet = true
      }
    }
    startCollectingPropertyFlows(mapboxMap)
  }

  private fun startCollectingPropertyFlows(mapboxMap: MapboxMap) {
    val collectNewPropertiesJob = coroutineScope.launch {
      propertiesFlowsToCollect.collect { (name: String, valueFlow: PropertyValueFlow) ->
        val updatePropertyJob = coroutineScope.launch {
          valueFlow.collect { value ->
            logD(TAG, "settingProperty: name=$name, value=$value ...")
            mapboxMap.setStyleRainProperty(name, value)
              .onError { error ->
                // handle the use case of changing rain e.g. for Standard style
                // and explicitly adding it so that applying property could take effect
                if (!rainSet) {
                  mapboxMap.setStyleRain(Value.valueOf(hashMapOf())).onValue {
                    rainSet = true
                    mapboxMap.setStyleRainProperty(name, value).onError {
                      logW(TAG, "Failed to set rain property $name as $value: $error")
                    }.onValue {
                      logD(TAG, "settingProperty: name=$name, value=$value executed")
                    }
                  }.onError {
                    logW(TAG, "Failed to set rain with no properties, error = $it")
                    logI(TAG, "settingProperty: name=$name, value=$value ignored")
                  }
                } else {
                  logW(TAG, "Failed to set rain property $name as $value: $error")
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

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RainStateApplier

    val thisProperties =
      propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    val otherProperties =
      other.propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    if (thisProperties != otherProperties) return false

    return rainSet == other.rainSet
  }

  override fun hashCode(): Int {
    val thisProperties =
      propertiesFlowsToCollect.replayCache.associate { it.first to it.second.value }
    return Objects.hash(rainSet, thisProperties)
  }

  private companion object {
    private const val TAG = "RainStateApplier"
  }
}