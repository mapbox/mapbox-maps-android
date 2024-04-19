package com.mapbox.maps.extension.compose.style.layers.internal

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.internal.PausingDispatcherNode
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.extension.compose.style.sources.SourceState
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(MapboxExperimental::class)
internal class LayerNode(
  private val map: MapboxMap,
  private val layerType: String,
  private var layerId: String,
  private var sourceState: SourceState? = null,
  private val coroutineScope: CoroutineScope,
) : PausingDispatcherNode() {
  private val parameters = hashMapOf(
    "id" to Value(layerId),
    "type" to Value(layerType)
  ).apply {
    sourceState?.sourceId?.let {
      this["source"] = Value(it)
    }
  }

  private lateinit var parentNode: MapNode

  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached: parent=$parent")
    parameters.apply {
      if (parent is StyleSlotNode) {
        this["slot"] = Value(parent.slotName)
      }
    }
    parentNode = parent
    addLayer()
  }

  private fun attachSource() {
    sourceState?.attachToLayer(layerId, map)
  }

  private fun detachSource() {
    sourceState?.detachFromLayer(layerId, map)
  }

  override fun onRemoved(parent: MapNode) {
    logD(TAG, "onRemoved: parent=$parent")
    removeLayer()
    detachSource()
    super.onRemoved(parent)
  }

  override fun onClear() {
    removeLayer()
    detachSource()
    super.onClear()
  }

  private fun addLayer(parent: MapNode = parentNode) {
    when (parent) {
      is StyleSlotNode -> {
        // layer added within the style node, add as non-persistent layer
        logD(TAG, "Adding layer: $parameters, at slot: ${parent.slotName}")
        coroutineScope.launch {
          parent.mapStyleNode.styleDataLoaded.collect {
            map.addStyleLayer(
              parameters = Value(parameters),
              position = null
            ).error?.let {
              logE(TAG, "Failed to add layer: $it")
            } ?: run {
              logD(TAG, "Added layer: $parameters")
              attachSource()
              onNodeReady()
            }
          }
        }
      }

      is StyleLayerPositionNode -> {
        // layer added within the style node, add as non-persistent layer
        logD(TAG, "Adding layer: $parameters, at position: ${parent.layerPosition}")
        coroutineScope.launch {
          parent.mapStyleNode.styleDataLoaded.collect {
            map.addStyleLayer(
              parameters = Value(parameters),
              position = parent.layerPosition
            ).error?.let { error ->
              logE(TAG, "Failed to add layer $parameters at ${parent.layerPosition}: $error")
              logE(TAG, "Available layers in style:")
              map.styleLayers.forEach { logE(TAG, "\t ${it.id}") }
            } ?: run {
              logD(TAG, "Added layer: $parameters")
              attachSource()
              onNodeReady()
            }
          }
        }
      }

      else -> {
        // layer added outside of the style node, add as persistent layer
        logD(TAG, "Adding persistent layer: $parameters")
        map.addPersistentStyleLayer(
          properties = Value(parameters),
          layerPosition = null
        ).error?.let {
          logE(TAG, "Failed to add persistent layer: $it")
        } ?: run {
          logD(TAG, "Added persistent layer: $parameters")
          attachSource()
          onNodeReady()
        }
      }
    }
  }

  private fun removeLayer() {
    logD(TAG, "Removing layer: $this")
    map.removeStyleLayer(layerId).error?.let {
      logW(
        TAG,
        "Failed to remove $layerType layer $layerId: $it"
      )
    }
  }

  internal fun updateLayerId(layerId: String) {
    detachSource()
    removeLayer()
    this.layerId = layerId
    parameters["id"] = Value(layerId)
    addLayer()
    attachSource()
  }

  internal fun updateSource(sourceState: SourceState) {
    detachSource()
    removeLayer()
    this.sourceState = sourceState
    parameters["source"] = Value(sourceState.sourceId)
    addLayer()
    attachSource()
  }

  internal fun setProperty(name: String, value: Value) {
    logD(TAG, "settingProperty: property=$name, value=$value ...")
    coroutineScope.launch {
      whenNodeReady {
        parameters[name] = value
        map.setStyleLayerProperty(layerId, name, value).error?.let {
          logW(
            TAG,
            "Failed to set $name property as $value on SymbolLayer $layerId: $it"
          )
        }
        logD(TAG, "setProperty: property=$name, value=$value executed")
      }
    }
  }

  override fun toString(): String {
    return "LayerNode(layerType=$layerType, parameters=$parameters)"
  }

  private companion object {
    private const val TAG = "LayerNode"
  }
}