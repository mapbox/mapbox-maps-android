package com.mapbox.maps.extension.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.extension.compose.style.internal.StyleConfig
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPosition
import com.mapbox.maps.extension.compose.style.internal.StyleSlot
import com.mapbox.maps.extension.compose.style.projection.Projection

/**
 * The convenient composable function to set a Mapbox Standard style to the map, with available slots
 * and config options.
 *
 * @param topSlot The content to be set to the top slot of the Mapbox Standard style.
 * @param middleSlot The content to be set to the middle slot of the Mapbox Standard style.
 * @param bottomSlot The content to be set to the bottom slot of the Mapbox Standard style.
 * @param lightPreset The lightPreset settings of the Mapbox Standard Style, available lightPresets including "day", "night", "dawn", "dusk".
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapboxStandardStyle(
  topSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  middleSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  bottomSlot: (@Composable @MapboxMapComposable () -> Unit)? = null,
  lightPreset: Value = Value.nullValue(),
) {
  GenericStyle(
    style = Style.STANDARD,
    slots = mapOf(
      "top" to topSlot,
      "middle" to middleSlot,
      "bottom" to bottomSlot,
    ),
    configs = mapOf(
      "basemap" to mapOf(
        "lightPreset" to lightPreset
      )
    )
  )
}

/**
 * A simple composable function to set the style to the map without slots or import configs.
 *
 * @param style The Style JSON or Style Uri to be set to the map.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapStyle(style: String) {
  GenericStyle(style = style)
}

/**
 * Generic Style that you can insert [MapboxMapComposable] functions in any slot you defined as a
 * plain string, or set any map import configs given as plain string and [Value] pairs.
 *
 * Please note it's your own responsibility to make sure the slots you provided exists in the style,
 * and the import configs are valid within the style, otherwise it will result in unexpected behaviour.
 *
 * Always prefer strongly typed [MapboxStyleComposable] over the [GenericStyle].
 *
 * @param style The Style JSON or Style Uri to be set to the map.
 * @param slots The slot name and [MapboxStyleComposable] pairs that would be inserted to the corresponding slots in the style.
 * @param configs The map of importId and the config name/value pairs that would be applied to the style as style import configs.
 * @param projection The projection to be set to the map. Defaults to [Projection.default] meaning that projection value is taken from the [style] definition.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun GenericStyle(
  style: String,
  slots: Map<String, (@Composable @MapboxMapComposable () -> Unit)?> = emptyMap(),
  layerPositions: Map<LayerPosition, (@Composable @MapboxMapComposable () -> Unit)?> = emptyMap(),
  configs: Map<String, Map<String, Value>> = emptyMap(),
  projection: Projection = Projection.default,
) {
  // When style is changed, we want to trigger the recompose of the whole style node
  key(style) {
    val mapApplier = currentComposer.applier as? MapApplier
      ?: throw IllegalStateException("Illegal use of Style composable outside of MapboxMapComposable")

    // Insert a MapStyleNode to the MapApplier node tree
    ComposeNode<MapStyleNode, MapApplier>(
      factory = {
        MapStyleNode(
          style = style,
          mapboxMap = mapApplier.mapView.mapboxMap,
          projection = projection
        )
      },
      update = {
        update(projection) {
          updateProjection(projection)
        }
      }
    ) {
      slots.entries.filter { it.value != null }.forEach {
        key(it.key) {
          StyleSlot(name = it.key, content = it.value)
        }
      }
      layerPositions.filter { it.value != null }.entries.forEach {
        key(it.key) {
          StyleLayerPosition(layerPosition = it.key, content = it.value)
        }
      }
      configs.entries.forEach { import ->
        import.value.entries.forEach {
          key(import.key, it.key) {
            StyleConfig(importId = import.key, name = it.key, property = it.value)
          }
        }
      }
    }
  }
}