package com.mapbox.maps.extension.compose.style.internal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable

internal class StyleConfigNode(
  private val importId: String,
  private val configName: String,
  private val mapboxMap: MapboxMap,
) : MapNode() {
  lateinit var mapStyleNode: MapStyleNode
  override fun onAttached(parent: MapNode) {
    mapStyleNode = parent as MapStyleNode
  }

  fun setProperty(value: Value) {
    mapboxMap.setStyleImportConfigProperty(importId = importId, config = configName, value)
  }

  override fun toString(): String {
    return "StyleConfigNode(importId=$importId, configName=$configName)"
  }
}

/**
 * This is a internal composable function because we only want to expose it through Style with hashmap.
 * As it's bound with the configs available in the style JSON, and we also don't want multiple Config/Import declaration
 * with the same name, as it will mess up with our layer ordering.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
internal fun StyleConfig(
  importId: String,
  name: String,
  property: Value,
) {
  val applier = currentComposer.applier as? MapApplier
  applier?.let { mapApplier ->
    // Insert a MapNode to the MapApplier node tree
    ComposeNode<StyleConfigNode, MapApplier>(
      factory = {
        StyleConfigNode(importId, name, mapApplier.mapView.mapboxMap)
      },
      update = {
        set(property) {
          setProperty(property)
        }
      }
    )
  }
    ?: throw IllegalStateException("Illegal use of StyleConfig composable outside of MapboxMapComposable")
}