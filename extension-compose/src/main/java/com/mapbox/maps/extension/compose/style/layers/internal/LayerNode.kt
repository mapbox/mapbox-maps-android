package com.mapbox.maps.extension.compose.style.layers.internal

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.internal.PausingDispatcherNode
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class LayerNode(
  val map: MapboxMap,
  private val layerType: String,
  layerId: String,
  sourceId: String? = null,
  private val coroutineScope: CoroutineScope,
) : PausingDispatcherNode() {
  private val parameters = hashMapOf(
    "id" to Value(layerId),
    "type" to Value(layerType)
  ).apply {
    sourceId?.let {
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

  override fun onRemoved(parent: MapNode) {
    logD(TAG, "onRemoved: parent=$parent")
    removeLayer()
    super.onRemoved(parent)
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
          onNodeReady()
        }
      }
    }
  }

  private fun removeLayer() {
    logD(TAG, "Removing layer: $this")
    val layerId = parameters["id"]!!.contents as String
    map.removeStyleLayer(layerId).error?.let {
      logW(
        TAG,
        "Failed to remove $layerType layer $layerId: $it"
      )
    }
  }

  /**
   * Use this method to set properties that require removing and re-adding the layer, for example `layerId` and `sourceId`.
   */
  internal fun setConstructorProperty(name: String, value: Value) {
    removeLayer()
    parameters[name] = value
    addLayer()
  }

  internal fun setProperty(name: String, value: Value) {
    logD(TAG, "settingProperty: property=$name, value=$value ...")
    coroutineScope.launch {
      whenNodeReady {
        parameters[name] = value
        val layerId = parameters["id"]!!.contents as String
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