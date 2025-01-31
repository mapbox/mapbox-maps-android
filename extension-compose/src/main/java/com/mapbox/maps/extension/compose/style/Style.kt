package com.mapbox.maps.extension.compose.style

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import com.mapbox.bindgen.Value
import com.mapbox.maps.LayerPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.TransitionOptions
import com.mapbox.maps.extension.compose.MapboxMapComposable
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.style.atmosphere.generated.AtmosphereState
import com.mapbox.maps.extension.compose.style.imports.MapboxStyleImportComposable
import com.mapbox.maps.extension.compose.style.imports.StyleImportsScope
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.extension.compose.style.internal.StyleConfig
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPosition
import com.mapbox.maps.extension.compose.style.internal.StyleSlot
import com.mapbox.maps.extension.compose.style.lights.LightsState
import com.mapbox.maps.extension.compose.style.projection.generated.Projection
import com.mapbox.maps.extension.compose.style.terrain.generated.TerrainState
import com.mapbox.maps.extension.style.utils.transition

/**
 * A simple composable function to set the style to the map without slots or import configs.
 *
 * @param style The Style JSON or Style Uri to be set to the map.
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param projection The projection to be set to the map. Defaults to [Projection.DEFAULT] meaning that projection value is taken from the [style] definition.
 * @param atmosphereState The atmosphere to be set to the map. By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from [style] definition.
 * @param lightsState The lights to be set to the map. By default, no changes to the current lights defined in style.
 * @param styleTransition Transition options applied when loading the style.
 */
@Composable
@Deprecated("Use overloaded MapStyle composable function with StyleState")
@MapboxStyleComposable
@SuppressLint("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
public fun MapStyle(
  style: String,
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  projection: Projection = Projection.INITIAL,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.INITIAL,
  lightsState: LightsState = LightsState.INITIAL,
  styleTransition: TransitionOptions = remember { transition { } },
) {
  MapStyle(
    style = style,
    styleImportsContent = styleImportsContent,
    styleState = rememberStyleState {
      this.projection = projection
      this.atmosphereState = atmosphereState
      this.terrainState = terrainState
      this.lightsState = lightsState
      this.styleTransition = styleTransition
    }
  )
}

/**
 * A simple composable function to set the style to the map without slots or import configs.
 *
 * @param style The Style JSON or Style Uri to be set to the map.
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param styleState The state holder for the Style.
 */
@Composable
@MapboxStyleComposable
@SuppressLint("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
public fun MapStyle(
  style: String,
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  styleState: StyleState = rememberStyleState()
) {
  GenericStyle(
    style = style,
    styleImportsContent = styleImportsContent,
    styleState = styleState
  )
}

/**
 * Class that holds [MapboxMapComposable] content per each slot.
 */
@Stable
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
public fun slotsContent(init: SlotsContent.() -> Unit): SlotsContent = SlotsContent().apply(init)

/**
 * Class that holds [MapboxMapComposable] content per each [LayerPosition].
 */
@Stable
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
public fun layerPositionedContent(init: LayerPositionedContent.() -> Unit): LayerPositionedContent =
  LayerPositionedContent().apply(init)

/**
 * Class that holds [ImportConfigs] per each Import ID.
 */
@Stable
public data class StyleImportsConfig internal constructor(
  internal val entries: MutableMap<String, ImportConfigs> = mutableMapOf()
) {
  /**
   * Assign a new [ImportConfigs] to the given [importId].
   *
   * @param importId The import ID to configure.
   * @param configs The configs to be applied to the import id.
   *
   * @throws IllegalArgumentException if [importId] already has [configs].
   */
  public fun importConfig(importId: String, configs: ImportConfigs.() -> Unit) {
    if (entries.containsKey(importId)) {
      throw IllegalArgumentException("Configs for Import ID [$importId] already set.")
    }
    entries[importId] = ImportConfigs().apply(configs)
  }
}

/**
 * Type-safe builder for [StyleImportsConfig].
 */
public fun styleImportsConfig(init: StyleImportsConfig.() -> Unit): StyleImportsConfig =
  StyleImportsConfig().apply(init)

/**
 * Class that holds the configs for one import ID.
 */
@Stable
public data class ImportConfigs internal constructor(
  internal val configs: HashMap<String, Value> = hashMapOf()
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
 * Type-safe builder for [ImportConfigs].
 */
public fun importConfigs(init: ImportConfigs.() -> Unit): ImportConfigs =
  ImportConfigs().apply(init)

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
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param slotsContent The slots and their [MapboxMapComposable] that would be inserted to the corresponding slots in the style. You can use [slotsContent] to create it.
 * @param layerPositionedContent The [MapboxMapComposable] content to be placed at specific layer position in the style. You can use [layerPositionedContent] to create it.
 * @param styleImportsConfig The style import configurations for all the style imports in the style. You can use [styleImportsConfig] to create it.
 * @param projection The projection to be set to the map. Defaults to [Projection.DEFAULT] meaning that projection value is taken from the [style] definition.
 * @param atmosphereState The atmosphere to be set to the map. By default, no changes to the current atmosphere.
 * @param terrainState The terrain to be set to the map. Defaults to initial state meaning no custom terrain is added, default value is taken from [style] definition.
 * @param lightsState The lights to be set to the map. By default, no changes to the current lights defined in style.
 * @param styleTransition Transition options applied when loading the style.
 */
@Composable
@MapboxStyleComposable
@SuppressLint("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
@Deprecated("Use overloaded GenericStyle function with StyleState")
public fun GenericStyle(
  style: String,
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  slotsContent: SlotsContent = remember { SlotsContent() },
  layerPositionedContent: LayerPositionedContent = remember { LayerPositionedContent() },
  styleImportsConfig: StyleImportsConfig = remember { StyleImportsConfig() },
  projection: Projection = Projection.INITIAL,
  atmosphereState: AtmosphereState = remember { AtmosphereState() },
  terrainState: TerrainState = TerrainState.INITIAL,
  lightsState: LightsState = LightsState.INITIAL,
  styleTransition: TransitionOptions = remember { transition { } },
) {
  GenericStyle(
    style = style,
    styleImportsContent = styleImportsContent,
    slotsContent = slotsContent,
    layerPositionedContent = layerPositionedContent,
    styleState = rememberStyleState {
      this.styleImportsConfig = styleImportsConfig
      this.projection = projection
      this.atmosphereState = atmosphereState
      this.terrainState = terrainState
      this.lightsState = lightsState
      this.styleTransition = styleTransition
    }
  )
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
 * @param styleImportsContent The style imports to be added to the current style, note layers and annotations shouldn't be added to this block.
 * @param slotsContent The slots and their [MapboxMapComposable] that would be inserted to the corresponding slots in the style. You can use [slotsContent] to create it.
 * @param layerPositionedContent The [MapboxMapComposable] content to be placed at specific layer position in the style. You can use [layerPositionedContent] to create it.
 * @param styleState The state holder for the Style.
 */
@OptIn(MapboxExperimental::class)
@Composable
@MapboxStyleComposable
@SuppressLint("ComposableLambdaParameterNaming", "ComposableLambdaParameterPosition")
public fun GenericStyle(
  style: String,
  styleImportsContent: (@Composable @MapboxStyleImportComposable StyleImportsScope.() -> Unit)? = null,
  slotsContent: SlotsContent = remember { SlotsContent() },
  layerPositionedContent: LayerPositionedContent = remember { LayerPositionedContent() },
  styleState: StyleState = rememberStyleState()
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
          projection = styleState.projection,
          atmosphereState = styleState.atmosphereState,
          rainState = styleState.rainState,
          snowState = styleState.snowState,
          terrainState = styleState.terrainState
        )
      },
      update = {
        update(styleState.projection) {
          updateProjection(styleState.projection)
        }
        update(styleState.atmosphereState) {
          updateAtmosphere(styleState.atmosphereState)
        }
        update(styleState.rainState) {
          updateRain(styleState.rainState)
        }
        update(styleState.snowState) {
          updateSnow(styleState.snowState)
        }
        update(styleState.terrainState) {
          updateTerrain(styleState.terrainState)
        }
        set(styleState.styleTransition) {
          updateStyleTransition(it)
        }
        set(styleState.styleColorTheme) {
          updateStyleColorTheme(it.colorTheme, it.isStyleDefault)
        }
      }
    ) {
      styleState.atmosphereState.UpdateProperties()
      styleState.rainState.UpdateProperties()
      styleState.snowState.UpdateProperties()
      styleState.terrainState.UpdateProperties()
      styleState.lightsState.BindToMap(mapboxMap = mapApplier.mapView.mapboxMap)
      val styleImportsScope = remember {
        StyleImportsScope()
      }
      styleImportsContent?.let {
        styleImportsScope.it()
      }
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
      styleState.styleImportsConfig.entries.forEach { import ->
        import.value.configs.forEach { config ->
          key(import.key, config.key) {
            StyleConfig(importId = import.key, name = config.key, property = config.value)
          }
        }
      }
      key(styleState.styleInteractionsState) {
        styleState.styleInteractionsState.BindTo(mapboxMap = mapApplier.mapView.mapboxMap)
      }
    }
  }
}