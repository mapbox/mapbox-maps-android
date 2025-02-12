package com.mapbox.maps.extension.compose.style.layers.internal

import androidx.compose.runtime.Stable
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.internal.StyleLifecycleAwareNode
import com.mapbox.maps.extension.compose.style.StyleImage
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.extension.compose.style.sources.SourceState
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineScope

@Stable
internal class LayerNode(
  private val map: MapboxMap,
  private val layerType: String,
  private var layerId: String,
  private var sourceState: SourceState? = null,
  coroutineScope: CoroutineScope,
) : LayerPositionAwareNode, StyleLifecycleAwareNode(map, coroutineScope) {
  private val parameters = hashMapOf(
    "id" to Value(layerId),
    "type" to Value(layerType)
  ).apply {
    sourceState?.sourceId?.let {
      this["source"] = Value(it)
    }
  }

  private val addedImages = mutableListOf<String>()
  private val addedModels = mutableListOf<String>()

  private lateinit var parentNode: MapNode

  override var isAttached: Boolean = false

  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached: parent=$parent")
    super.onAttached(parent)
    if (parent is StyleSlotNode) {
      parameters["slot"] = Value(parent.slotName)
    }
    parentNode = parent
    addLayer()
  }

  override fun onMoved(parent: MapNode) {
    logD(TAG, "onMoved")
    dispatchWhenReady {
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

  @OptIn(MapboxExperimental::class)
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

  private fun addLayer() {
    logD(TAG, "Adding layer: $parameters, within parent: $parentNode")
    // layer added within the style or slot node, add as non-persistent layer
    if (parentNode is StyleSlotNode || parentNode is StyleLayerPositionNode) {
      dispatchWhenReady {
        val layerPosition = getRelativePositionInfo(parentNode)
        logD(TAG, "Adding layer: $parameters, with layer position: $layerPosition")
        it.addStyleLayer(
          parameters = Value(parameters),
          position = layerPosition
        ).onError { error ->
          logE(TAG, "Failed to add layer: $error")
        }.onValue {
          logD(TAG, "Added layer: $parameters")
          attachSource()
        }
        isAttached = true
      }
    } else {
      // layer added outside of the style node, add as persistent layer
      logD(TAG, "Adding persistent layer: $parameters")
      val layerPosition = getRelativePositionInfo(parentNode)
      logD(TAG, "Adding layer: $parameters, with layer position: $layerPosition")
      map.addPersistentStyleLayer(
        properties = Value(parameters),
        layerPosition = layerPosition
      ).onError {
        logE(TAG, "Failed to add persistent layer: $it")
      }.onValue {
        logD(TAG, "Added persistent layer: $parameters")
        attachSource()
      }
      isAttached = true
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

  @OptIn(MapboxExperimental::class)
  internal fun addModel(modelInfo: Pair<String, String>) {
    dispatchWhenReady {
      it.addStyleModel(modelInfo.first, modelInfo.second)
        .onError { error ->
          logW(TAG, "Failed to add style model $modelInfo: $error")
        }.onValue {
          addedModels.add(modelInfo.first)
        }
    }
  }

  internal fun addImage(styleImage: StyleImage) {
    dispatchWhenReady {
      it.addStyleImage(
        imageId = styleImage.imageId,
        scale = styleImage.scale ?: it.pixelRatio,
        image = styleImage.image,
        sdf = styleImage.sdf,
        stretchX = styleImage.stretchX,
        stretchY = styleImage.stretchY,
        content = styleImage.content
      ).onError { error ->
        logW(TAG, "Failed to add style image $styleImage: $error")
      }.onValue {
        addedImages.add(styleImage.imageId)
      }
    }
  }

  internal fun setProperty(name: String, value: Value) {
    logD(TAG, "[$layerType] settingProperty: property=$name, value=$value ...")
    if (!isAttached) {
      logD(TAG, "setProperty() called before the layer is attached to the map.")
      parameters[name] = value
      return
    }
    dispatchWhenReady {
      parameters[name] = value
      it.setStyleLayerProperty(layerId, name, value).error?.let { error ->
        logW(
          TAG,
          "Failed to set $name property as $value on $layerType layer $layerId: $error"
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