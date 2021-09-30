package com.mapbox.maps.testapp.utils

import android.os.Handler
import android.os.Looper
import com.mapbox.bindgen.Value
import com.mapbox.maps.*

/**
 * Convenience util class that could be used for smoother switching between styles.
 *
 * Approach used here is not a 100% solution and may require parameter tuning
 * depending on given switched styles, potential blinking layers, device capabilities etc.
 */
class StyleSwitcher(
  val mapboxMap: MapboxMap
) {
  private lateinit var currentStyle: Style
  private val mainHandler = Handler(Looper.getMainLooper())
  private val originalProperties = mutableMapOf<Pair<String, String>, Value>()
  private var styleTransitionDelayMs: Long = 0
  private var onStyleSwitched: ((Style) -> Unit)? = null

  private fun addOnMapLoadedObserver() {
    mapboxMap.subscribe(
      object : Observer {
        override fun notify(event: Event) {
          when (event.type) {
            // when map loaded, fade-in layers using original values
            MapEvents.MAP_LOADED -> {
              for (originalProperty in originalProperties) {
                if (!currentStyle.styleLayerExists(originalProperty.key.first)) {
                  continue
                }
                currentStyle.setStyleLayerProperty(
                  originalProperty.key.first,
                  originalProperty.key.second + "-color-transition",
                  Value(
                    hashMapOf(
                      "duration" to Value(styleTransitionDelayMs),
                      "delay" to Value(0)
                    )
                  )
                )
                currentStyle.setStyleLayerProperty(
                  originalProperty.key.first,
                  originalProperty.key.second + "-color",
                  originalProperty.value
                )
              }
              originalProperties.clear()
              onStyleSwitched?.invoke(currentStyle)
              mapboxMap.unsubscribe(this)
            }
          }
        }
      },
      listOf(MapEvents.MAP_LOADED)
    )
  }

  fun switchStyle(
    newStyleUri: String,
    styleTransitionDelayMs: Long,
    flickeringLayers: List<String> = listOf(),
    onStyleSwitched: ((Style) -> Unit)? = null
  ) {
    this.onStyleSwitched = onStyleSwitched
    this.styleTransitionDelayMs = styleTransitionDelayMs
    mapboxMap.getStyle { style ->
      // fade-out flickering layers while new style is loaded
      for (layer in style.styleLayers) {
        for (flickeringLayer in flickeringLayers) {
          if (layer.id.contains(flickeringLayer)) {
            style.setStyleLayerProperty(
              layer.id,
              layer.type + "-color-transition",
              Value(hashMapOf("duration" to Value(0), "delay" to Value(0)))
            )
            style.setStyleLayerProperty(
              layer.id,
              layer.type + "-color",
              Value(
                arrayListOf(
                  Value("rgba"),
                  Value(0.0), Value(0.0), Value(0.0), Value(0.0)
                )
              )
            )
          }
        }
      }
      mainHandler.postDelayed(
        {
          addOnMapLoadedObserver()
          mapboxMap.loadStyleUri(newStyleUri) {
            currentStyle = it
            // make newly loaded layers 'invisible' when new style is loaded
            for (layer in currentStyle.styleLayers) {
              for (flickeringLayer in flickeringLayers) {
                if (layer.id.contains(flickeringLayer)) {
                  val property =
                    currentStyle.getStyleLayerProperty(layer.id, layer.type + "-color")
                  if (property.value != Value.nullValue()) {
                    originalProperties[Pair(layer.id, layer.type)] = property.value
                    currentStyle.setStyleLayerProperty(
                      layer.id,
                      layer.type + "-color-transition",
                      Value(hashMapOf("duration" to Value(0), "delay" to Value(0)))
                    )
                    currentStyle.setStyleLayerProperty(
                      layer.id,
                      layer.type + "-color",
                      Value(
                        arrayListOf(
                          Value("rgba"),
                          Value(0.0), Value(0.0), Value(0.0), Value(0.0)
                        )
                      )
                    )
                  }
                }
              }
            }
          }
        },
        styleTransitionDelayMs
      )
    }
  }
}