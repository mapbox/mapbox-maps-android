package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_style_cross_fade.*

/**
 * Example of cross fading animation when changing between styles.
 */
class StyleCrossFadeAnimationActivity : AppCompatActivity() {

  private lateinit var currentStyle: Style
  private val mainHandler = Handler(Looper.getMainLooper())
  private val originalProperties = mutableMapOf<Pair<String, String>, Value>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_style_cross_fade)
    switchStyleButton.visibility = View.GONE
    mapView.getMapboxMap().setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(ZOOM)
        .pitch(PITCH)
        .build()
    )
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
      currentStyle = it
      switchStyleButton.visibility = View.VISIBLE
      mapView.getMapboxMap().subscribe(
        object : Observer() {
          override fun notify(event: Event) {
            when (event.type) {
              // make newly loaded layers 'invisible' when new style is loaded
              MapEvents.STYLE_LOADED -> {
                for (layer in currentStyle.styleLayers) {
                  if (layer.id.contains("road-") ||
                    layer.id.contains("landuse") ||
                    layer.id.contains("bridge-")
                  ) {
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
              // when map loaded, fade-in layers using original values
              MapEvents.MAP_LOADED -> {
                for (originalProperty in originalProperties) {
                  currentStyle.setStyleLayerProperty(
                    originalProperty.key.first,
                    originalProperty.key.second + "-color-transition",
                    Value(
                      hashMapOf(
                        "duration" to Value(STYLE_TRANSITION_DELAY_MS),
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
              }
            }
          }
        },
        listOf(MapEvents.STYLE_LOADED, MapEvents.MAP_LOADED)
      )
      switchStyleButton.setOnClickListener {
        crossFadeSwitchStyle()
      }
    }
  }

  private fun crossFadeSwitchStyle() {
    switchStyleButton.isEnabled = false
    val newStyleUri = if (currentStyle.styleURI == Style.MAPBOX_STREETS) {
      Style.DARK
    } else {
      Style.MAPBOX_STREETS
    }
    // fade-out flickering layers while new style is loaded
    for (layer in currentStyle.styleLayers) {
      if (layer.id.contains("road-") ||
        layer.id.contains("landuse") ||
        layer.id.contains("bridge-")
      ) {
        currentStyle.setStyleLayerProperty(
          layer.id,
          layer.type + "-color-transition",
          Value(hashMapOf("duration" to Value(STYLE_FADE_OUT_DURATION_MS), "delay" to Value(0)))
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
    mainHandler.postDelayed(
      {
        mapView.getMapboxMap().loadStyleUri(newStyleUri) {
          currentStyle = it
          switchStyleButton.isEnabled = true
        }
      },
      STYLE_TRANSITION_DELAY_MS
    )
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val LATITUDE = 55.7558
    private const val LONGITUDE = 37.6173
    private const val ZOOM = 14.0
    private const val PITCH = 60.0

    private const val STYLE_TRANSITION_DELAY_MS = 300L
    private const val STYLE_FADE_OUT_DURATION_MS = (STYLE_TRANSITION_DELAY_MS / 1.5).toLong()
  }
}