package com.mapbox.maps.extension.compose.style.sources.internal

import com.mapbox.bindgen.Value
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.internal.PausingDispatcherNode
import com.mapbox.maps.extension.compose.style.internal.StyleSlotNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SourceNode(
  val map: MapboxMap,
  private val sourceType: String,
  private var sourceId: String,
  private val cacheProperties: HashMap<String, Value>,
  private val coroutineScope: CoroutineScope
) : PausingDispatcherNode() {
  private var cacheGeoJSONSourceData: GeoJSONSourceData? = null

  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached: parent=$parent")
    when (parent) {
      is StyleSlotNode -> {
        coroutineScope.launch {
          parent.mapStyleNode.styleSourcesLoaded.collect {
            addSource()
          }
        }
      }
      else -> {
        addSource()
      }
    }
  }

  override fun onRemoved(parent: MapNode) {
    logD(TAG, "onRemoved: parent=$parent, sourceId=$sourceId, sourceType=$sourceType")
    removeSource()
    super.onRemoved(parent)
  }

  private fun addSource() {
    logD(TAG, "Adding source: $this")
    map.addStyleSource(
      sourceId = sourceId,
      properties = Value(
        hashMapOf("type" to Value(sourceType)).also { it.putAll(cacheProperties) }
      ),
    ).error?.let {
      logE(TAG, "Failed to add source: $it")
    } ?: run {
      logD(TAG, "Added source: $this")
      onNodeReady()
    }
    cacheGeoJSONSourceData?.let {
      setGeoJsonSourceData(it)
    }
  }

  private fun removeSource() {
    logD(TAG, "Removing source: $this")
    map.removeStyleSource(sourceId).error?.let {
      logE(
        TAG,
        "Failed to remove $sourceType Source $sourceId: $it"
      )
    }
  }

  internal fun setBuilderProperty(name: String, value: Value) {
    cacheProperties[name] = value
    removeSource()
    addSource()
  }

  internal fun updateSourceId(sourceId: String) {
    removeSource()
    this.sourceId = sourceId
    addSource()
  }

  internal fun setProperty(name: String, value: Value) {
    logD(TAG, "settingProperty: property=$name, value=$value ...")
    coroutineScope.launch {
      whenNodeReady {
        map.setStyleSourceProperty(sourceId, name, value).error?.let {
          logW(
            TAG,
            "Failed to set $name property as $value on SymbolLayer $sourceId: $it"
          )
        }
        cacheProperties[name] = value
        logD(TAG, "setProperty: property=$name, value=$value executed")
      }
    }
  }

  internal fun setGeoJsonSourceData(data: GeoJSONSourceData) {
    logD(TAG, "setGeoJsonSourceData...")
    coroutineScope.launch {
      whenNodeReady(Dispatchers.IO) {
        map.setStyleGeoJSONSourceData(sourceId = sourceId, dataId = "", data = data)
        cacheGeoJSONSourceData = data
        logD(TAG, "setGeoJsonSourceData executed")
      }
    }
  }

  override fun toString(): String {
    return "SourceNode(sourceType=$sourceType, sourceId=$sourceId)"
  }

  private companion object {
    private const val TAG = "SourceNode"
  }
}