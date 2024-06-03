package com.mapbox.maps.extension.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.extension.compose.style.internal.StyleConfig
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPosition
import com.mapbox.maps.extension.compose.style.internal.StyleSlot
import com.mapbox.maps.extension.compose.style.projection.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState

/**
 * A simple composable function to set the style to the map without slots or import configs.
 *
 * @param style The Style JSON or Style Uri to be set to the map.
 * @param projection The projection to be set to the map. Defaults to [Projection.default] meaning that projection value is taken from the [style] definition.
* @param atmosphereState The atmosphere to be set to the map. By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from [style] definition.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun MapStyle(
  style: String,
  projection: Projection = Projection.default,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.initial,
) {
  GenericStyle(
    style = style,
    projection = projection,
    atmosphereState = atmosphereState,
    terrainState = terrainState
  )
}

/**
 * Class that holds [MapboxMapComposable] content per each slot.
 */
@Stable
@MapboxExperimental
public data class SlotsContent internal constructor(
  internal val entries: MutableMap<String, @Composable @MapboxMapComposable () -> Unit> = mutableMapOf()
) {
  /**
   * Assign a new [MapboxMapComposable] [content] to the given slot [name].
   *
   * @param name The name of the slot.
   * @param content a [MapboxMapComposable] to be applied to the slot.
   *
   * @throws IllegalArgumentException if [name] slot already has [content].
   */
  public fun slot(name: String, content: @Composable @MapboxMapComposable () -> Unit) {
    if (entries.containsKey(name)) {
      throw IllegalArgumentException("Slot [$name] already has content.")
    }
    entries[name] = content
  }
}

/**
 * Type-safe builder for slot based [MapboxMapComposable] content.
 */
@MapboxExperimental
public fun slotsContent(init: SlotsContent.() -> Unit): SlotsContent = SlotsContent().apply(init)

/**
 * Class that holds [MapboxMapComposable] content per each [LayerPosition].
 */
@Stable
@MapboxExperimental
public data class LayerPositionedContent internal constructor(
  internal val entries: MutableMap<LayerPosition, @Composable @MapboxMapComposable () -> Unit> = mutableMapOf()
) {
  /**
   * Assign a new [MapboxMapComposable] [content] to the given [layerPosition].
   *
   * @param layerPosition The layer position the content will be inserted to.
   * @param content a [MapboxMapComposable] to be applied at the [layerPosition].
   *
   * @throws IllegalArgumentException if [layerPosition] already has [content].
   */
  public fun layerPosition(
    layerPosition: LayerPosition,
    content: @Composable @MapboxMapComposable () -> Unit
  ) {
    if (entries.containsKey(layerPosition)) {
      throw IllegalArgumentException("LayerPosition [$layerPosition] already has content.")
    }
    entries[layerPosition] = content
  }

  /**
   * Assign a new [MapboxMapComposable] [content] above [layerId].
   *
   * @param [layerId] The layer id the content will be inserted above.
   * @param content a [MapboxMapComposable] to be applied above the [layerId].
   *
   * @throws IllegalArgumentException if [layerPosition] already has [content].
   */
  public fun aboveLayer(layerId: String, content: @Composable @MapboxMapComposable () -> Unit) {
    layerPosition(LayerPosition(layerId, null, null), content)
  }

  /**
   * Assign a new [MapboxMapComposable] [content] below [layerId].
   *
   * @param [layerId] The layer id the content will be inserted below.
   * @param content a [MapboxMapComposable] to be applied below the [layerId].
   *
   * @throws IllegalArgumentException if [layerPosition] already has [content].
   */
  public fun belowLayer(layerId: String, content: @Composable @MapboxMapComposable () -> Unit) {
    layerPosition(LayerPosition(null, layerId, null), content)
  }

  /**
   * Assign a new [MapboxMapComposable] [content] at [index].
   *
   * @param [index] The index that the content will be inserted to in the layers stack.
   * @param content a [MapboxMapComposable] to be applied to the index position.
   *
   * @throws IllegalArgumentException if [layerPosition] already has [content].
   */
  public fun atIndex(index: Int, content: @Composable @MapboxMapComposable () -> Unit) {
    layerPosition(LayerPosition(null, null, index), content)
  }
}

/**
 * Type-safe builder for layer position based [MapboxMapComposable] content.
 */
@MapboxExperimental
public fun layerPositionedContent(init: LayerPositionedContent.() -> Unit): LayerPositionedContent =
  LayerPositionedContent().apply(init)

/**
 * Class that holds [ImportConfig] per each Import ID.
 */
@Stable
@MapboxExperimental
public data class StyleImportsConfig internal constructor(
  internal val entries: MutableMap<String, ImportConfig> = mutableMapOf()
) {
  /**
   * Assign a new [ImportConfig] to the given [importId].
   *
   * @param importId The import ID to configure.
   * @param configs The configs to be applied to the import id.
   *
   * @throws IllegalArgumentException if [importId] already has [configs].
   */
  public fun importConfig(importId: String, configs: ImportConfig.() -> Unit) {
    if (entries.containsKey(importId)) {
      throw IllegalArgumentException("Configs for Import ID [$importId] already set.")
    }
    entries[importId] = ImportConfig().apply(configs)
  }
}

/**
 * Type-safe builder for [StyleImportsConfig].
 */
@MapboxExperimental
public fun styleImportsConfig(init: StyleImportsConfig.() -> Unit): StyleImportsConfig =
  StyleImportsConfig().apply(init)

/**
 * Class that holds the configs for one import ID.
 */
@Stable
@MapboxExperimental
public data class ImportConfig internal constructor(
  internal val configs: MutableMap<String, Value> = mutableMapOf()
) {
  /**
   * Assign a new [Value] to the given config name.
   *
   * @param name The configuration name.
   * @param value The value for the configuration.
   *
   * @throws IllegalArgumentException if [name] already has [value].
   */
  public fun config(name: String, value: Value) {
    if (configs.containsKey(name)) {
      throw IllegalArgumentException("Config name [$name] already set")
    }
    configs[name] = value
  }
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
 * @param slotsContent The slots and their [MapboxMapComposable] that would be inserted to the corresponding slots in the style. You can use [slotsContent] to create it.
 * @param layerPositionedContent The [MapboxMapComposable] content to be placed at specific layer position in the style. You can use [layerPositionedContent] to create it.
 * @param styleImportsConfig The style import configurations for all the style imports in the style. You can use [styleImportsConfig] to create it.
 * @param projection The projection to be set to the map. Defaults to [Projection.default] meaning that projection value is taken from the [style] definition.
 * @param atmosphereState The atmosphere to be set to the map. By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from [style] definition.
 */
@Composable
@MapboxStyleComposable
@MapboxExperimental
public fun GenericStyle(
  style: String,
  slotsContent: SlotsContent = SlotsContent(),
  layerPositionedContent: LayerPositionedContent = LayerPositionedContent(),
  styleImportsConfig: StyleImportsConfig = StyleImportsConfig(),
  projection: Projection = Projection.default,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.initial,
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
          projection = projection,
          atmosphereState = atmosphereState,
          terrainState = terrainState,
        )
      },
      update = {
        update(projection) {
          updateProjection(projection)
        }
        update(atmosphereState) {
          updateAtmosphere(atmosphereState)
        }
        update(terrainState) {
          updateTerrain(terrainState)
        }
      }
    ) {
      atmosphereState.UpdateProperties()
      terrainState.UpdateProperties()
      slotsContent.entries.forEach {
        key(it.key) {
          StyleSlot(name = it.key, content = it.value)
        }
      }
      layerPositionedContent.entries.forEach {
        key(it.key) {
          StyleLayerPosition(layerPosition = it.key, content = it.value)
        }
      }
      styleImportsConfig.entries.forEach { import ->
        import.value.configs.forEach { config ->
          key(import.key, config.key) {
            StyleConfig(importId = import.key, name = config.key, property = config.value)
          }
        }
      }
    }
  }
}