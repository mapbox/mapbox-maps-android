// This file is generated.

package com.mapbox.maps.extension.compose.style.internal.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.logE

/**
 * Root style property values as they were baked into the *loaded style JSON*, captured once per
 * style load.
 *
 * Used to reset a root property (rain/snow/atmosphere/terrain) to the style's own value
 * when the corresponding Compose node is removed/disabled.
 *
 * Note: [atmosphere] is parsed from the `"fog"` key in the style JSON, not `"atmosphere"`.
 */
internal data class StyleDefaults(
  val rain: Map<String, Value>,
  val snow: Map<String, Value>,
  val atmosphere: Map<String, Value>,
  val terrain: Map<String, Value>,
) {
  internal companion object {
    private const val TAG = "StyleDefaults"

    internal val EMPTY = StyleDefaults(
      rain = emptyMap(),
      snow = emptyMap(),
      atmosphere = emptyMap(),
      terrain = emptyMap(),
    )

    /**
     * Parses the root-level `rain`/`snow`/`fog`/`terrain` objects out of a loaded style's raw JSON.
     *
     * Parse errors reported by [Value.fromJson] and unexpected JSON shapes are logged, and [EMPTY]
     * is returned.
     */
    internal fun fromJson(styleJson: String): StyleDefaults {
      var styleDefaults = EMPTY
      Value.fromJson(styleJson)
        .onError {
          logE(TAG, "Unable to parse style defaults: $it")
        }
        .onValue { value ->
          @Suppress("UNCHECKED_CAST")
          val root = value.contents as? HashMap<String, Value> ?: return@onValue
          styleDefaults = StyleDefaults(
            rain = objMap(root["rain"]),
            snow = objMap(root["snow"]),
            atmosphere = objMap(root["fog"]),
            terrain = objMap(root["terrain"]),
          )
        }
      return styleDefaults
    }

    private fun objMap(value: Value?): Map<String, Value> {
      @Suppress("UNCHECKED_CAST")
      return (value?.contents as? HashMap<String, Value>)?.toMap() ?: emptyMap()
    }
  }
}
// End of generated file.