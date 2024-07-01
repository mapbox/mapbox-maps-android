package com.mapbox.maps.extension.compose.style.layers.internal

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.StyleImage
import com.mapbox.maps.extension.compose.style.internal.StyleAwareNode
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.extension.compose.style.sources.SourceState
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@OptIn(MapboxExperimental::class)
internal class LayerNode(
  private val map: MapboxMap,
  private val layerType: String,
  private var layerId: String,
  private var sourceState: SourceState? = null,
  private val coroutineScope: CoroutineScope,
) : LayerPositionAwareNode, MapNode() {
  private val parameters = hashMapOf(
    "id" to Value(layerId),
    "type" to Value(layerType)
  ).apply {
    sourceState?.sourceId?.let {
      this["source"] = Value(it)
    }
  }

  private val styleManagerFlow: MutableStateFlow<MapboxStyleManager?> = MutableStateFlow(null)

  private val addedImages = mutableListOf<String>()
  private val addedModels = mutableListOf<String>()

  private lateinit var parentNode: MapNode

  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached: parent=$parent")
    parameters.apply {
      if (parent is StyleSlotNode) {
        this["slot"] = Value(parent.slotName)
      }
    }
    parentNode = parent
    updateStyleManagerState(parent)
    addLayer()
  }

  private fun updateStyleManagerState(parent: MapNode) {
    when (parent) {
      is StyleAwareNode -> {
        coroutineScope.launch {
          parent.mapStyleNode.styleDataLoaded.firstOrNull()?.let {
            styleManagerFlow.value = map
          }
        }
      }

      else -> styleManagerFlow.value = map
    }
  }

  private fun dispatchWhenStyleDataLoaded(action: (MapboxStyleManager) -> Unit) {
    coroutineScope.launch {
      styleManagerFlow.filterNotNull().first().apply(action)
    }
  }

  override fun onMoved(parent: MapNode, from: Int, to: Int) {
    logD(TAG, "onMoved: from $from to $to")
    dispatchWhenStyleDataLoaded {
      it.repositionCurrentNode(parent)
    }
  }

  private fun attachSource() {
    sourceState?.attachToLayer(layerId, map)
  }

  private fun detachSource() {
    logD(TAG, "detaching Source: $this")
    sourceState?.detachFromLayer(layerId, map)
  }

  override fun onRemoved(parent: MapNode) {
    logD(TAG, "onRemoved: parent=$parent")
    cleanUp()
    super.onRemoved(parent)
  }

  override fun onClear() {
    cleanUp()
    super.onClear()
  }

  private fun cleanUp() {
    removeLayer()
    detachSource()
    cleanUpResources()
  }

  private fun cleanUpResources() {
    addedImages.forEach {
      map.removeStyleImage(it)
    }
    addedImages.clear()
    addedModels.forEach {
      map.removeStyleModel(it)
    }
    addedModels.clear()
  }

  private fun addLayer(parent: MapNode = parentNode) {
    when (parent) {
      is StyleSlotNode -> {
        // layer added within the style node, add as non-persistent layer
        logD(TAG, "Adding layer: $parameters, at slot: ${parent.slotName}")
        // fetch the relative position info synchronously as the add layer can be done
        // async, and the relative position can change later.
        val layerPosition = getRelativePositionInfo(parent)
        dispatchWhenStyleDataLoaded {
          it.addStyleLayer(
            parameters = Value(parameters),
            position = layerPosition
          ).onError {
            logE(TAG, "Failed to add layer: $it")
          }.onValue {
            logD(TAG, "Added layer: $parameters")
            attachSource()
          }
        }
      }

      is StyleLayerPositionNode -> {
        // layer added within the style node, add as non-persistent layer
        logD(TAG, "Adding layer: $parameters, at position: ${parent.layerPosition}")
        // Add layer to the position defined in [StyleLayerPositionNode] if it's the last [LayerPositionAwareNode]
        // under the [StyleLayerPositionNode], otherwise, we insert with relative position.
        val layerPosition = getRelativePositionInfo(parent, parent.layerPosition)
        dispatchWhenStyleDataLoaded { styleManager ->
          styleManager.addStyleLayer(
            parameters = Value(parameters),
            position = layerPosition
          ).onError {
            logE(TAG, "Failed to add layer $parameters at $layerPosition: $it")
            logE(TAG, "Available layers in style:")
            styleManager.styleLayers.forEach { logE(TAG, "\t ${it.id}") }
          }.onValue {
            logD(TAG, "Added layer: $parameters")
            attachSource()
          }
        }
      }

      else -> {
        // layer added outside of the style node, add as persistent layer
        logD(TAG, "Adding persistent layer: $parameters")
        // fetch the relative position info synchronously as the add layer can be done
        // async, and the relative position can change later.
        val layerPosition = getRelativePositionInfo(parent)
        map.addPersistentStyleLayer(
          properties = Value(parameters),
          layerPosition = layerPosition
        ).onError {
          logE(TAG, "Failed to add persistent layer: $it")
        }.onValue {
          logD(TAG, "Added persistent layer: $parameters")
          attachSource()
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

  internal fun addModel(modelInfo: Pair<String, String>) {
    dispatchWhenStyleDataLoaded {
      it.addStyleModel(modelInfo.first, modelInfo.second)
        .onError {
          logW(TAG, "Failed to add style model $modelInfo: $it")
        }.onValue {
          addedModels.add(modelInfo.first)
        }
    }
  }

  internal fun addImage(styleImage: StyleImage) {
    dispatchWhenStyleDataLoaded {
      it.addStyleImage(
        imageId = styleImage.imageId,
        scale = styleImage.scale ?: it.pixelRatio,
        image = styleImage.image,
        sdf = styleImage.sdf,
        stretchX = styleImage.stretchX,
        stretchY = styleImage.stretchY,
        content = styleImage.content
      ).onError {
        logW(TAG, "Failed to add style image $styleImage: $it")
      }.onValue {
        addedImages.add(styleImage.imageId)
      }
    }
  }

  internal fun setProperty(name: String, value: Value) {
    logD(TAG, "[$layerType] settingProperty: property=$name, value=$value ...")
    dispatchWhenStyleDataLoaded {
      parameters[name] = value
      it.setStyleLayerProperty(layerId, name, value).error?.let {
        logW(
          TAG,
          "Failed to set $name property as $value on $layerType layer $layerId: $it"
        )
      }
      logD(TAG, "[$layerType] setProperty: property=$name, value=$value executed")
    }
  }

  override fun getLayerIds(): List<String> {
    return listOf(layerId)
  }

  override fun toString(): String {
    return "LayerNode(layerType=$layerType, parameters=$parameters)"
  }

  private companion object {
    private const val TAG = "LayerNode"
  }
}