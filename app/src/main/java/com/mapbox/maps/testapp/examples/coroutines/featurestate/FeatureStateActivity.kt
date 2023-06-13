package com.mapbox.maps.testapp.examples.coroutines.featurestate

import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.coroutine.cameraChanges
import com.mapbox.maps.coroutine.getFeatureState
import com.mapbox.maps.coroutine.queryRenderedFeatures
import com.mapbox.maps.coroutine.setFeatureState
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.switchCase
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.testapp.databinding.ActivityFeatureStateBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DateFormat.getDateTimeInstance
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example showcasing usage of feature state.
 */
class FeatureStateActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var crosshair: View
  private lateinit var binding: ActivityFeatureStateBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFeatureStateBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
    binding.mapView.scalebar.enabled = false
    mapboxMap.loadStyle(createStyle()) {
      mapboxMap.setCamera(
        cameraOptions {
          center(CAMERA_CENTER)
          zoom(CAMERA_ZOOM)
        }
      )
    }
    showCrosshair()

    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        highlightFeatureOnHover()
      }
    }
  }

  private suspend fun highlightFeatureOnHover() {
    var lastFeatureId: String? = null
    mapboxMap
      .cameraChanges()
      .map {
        Rect().apply {
          crosshair.getDrawingRect(this)
          binding.mapView.offsetDescendantRectToMyCoords(crosshair, this)
        }
      }
      .map { offsetViewBounds ->
        mapboxMap
          .queryRenderedFeatures(
            RenderedQueryGeometry(
              ScreenBox(
                ScreenCoordinate(offsetViewBounds.left.toDouble(), offsetViewBounds.top.toDouble()),
                ScreenCoordinate(
                  offsetViewBounds.right.toDouble(),
                  offsetViewBounds.bottom.toDouble()
                )
              )
            ),
            RenderedQueryOptions(listOf(LAYER_ID), literal(true))
          )
      }
      .map { expected ->
        expected.value?.firstOrNull()
      }
      .distinctUntilChangedBy { renderedFeature ->
        renderedFeature?.queriedFeature?.feature?.id()
      }
      .collect { renderedFeature ->
        val selectedFeature = renderedFeature?.queriedFeature?.feature
        val selectedFeatureId = selectedFeature?.id()
        lastFeatureId?.let { lastId ->
          setHoverFeatureState(lastId, false)
        }
        lastFeatureId = selectedFeatureId

        if (selectedFeatureId != null) {
          setHoverFeatureState(selectedFeatureId, true)
          logHoverFeatureState(selectedFeatureId)

          // Update Magnitude, location and date text view.
          val time = selectedFeature.getNumberProperty("time")
          binding.date.text = getDateTime(time.toLong())
          binding.location.text = selectedFeature.getStringProperty("place")
          binding.magnitude.text = selectedFeature.getNumberProperty("mag").toString()
        }
      }
  }

  private suspend fun setHoverFeatureState(featureId: String, hover: Boolean) {
    mapboxMap.setFeatureState(
      sourceId = SOURCE_ID,
      featureId = featureId,
      state = Value(
        hashMapOf(
          "hover" to Value(hover)
        )
      )
    )
  }

  private suspend fun logHoverFeatureState(featureId: String) {
    val featureState = mapboxMap.getFeatureState(
      sourceId = SOURCE_ID,
      featureId = featureId,
    )

    logD(TAG, "getFeatureState: ${featureState.value}")
  }

  private fun showCrosshair() {
    crosshair = View(this)
    crosshair.layoutParams = FrameLayout.LayoutParams(20, 20, Gravity.CENTER)
    crosshair.setBackgroundColor(Color.RED)
    binding.mapView.addView(crosshair)
  }

  private fun getDateTime(time: Long): String = try {
    val sdf = getDateTimeInstance()
    val netDate = Date(time)
    sdf.format(netDate)
  } catch (e: Exception) {
    e.toString()
  }

  private fun createStyle() = style(style = Style.TRAFFIC_DAY) {
    +geoJsonSource(id = SOURCE_ID) {
      url(GEOJSON_URL)
      cluster(false)
      generateId(true)
    }
    +circleLayer(layerId = LAYER_ID, sourceId = SOURCE_ID) {
      circleRadius(
        switchCase {
          boolean {
            featureState {
              literal("hover")
            }
            literal(false)
          }
          interpolate {
            linear()
            get("mag")
            stop {
              literal(1.0)
              literal(8.0)
            }
            stop {
              literal(1.5)
              literal(10.0)
            }
            stop {
              literal(2.0)
              literal(12.0)
            }
            stop {
              literal(2.5)
              literal(14.0)
            }
            stop {
              literal(3.0)
              literal(16.0)
            }
            stop {
              literal(3.5)
              literal(18.0)
            }
            stop {
              literal(4.5)
              literal(20.0)
            }
            stop {
              literal(6.5)
              literal(22.0)
            }
            stop {
              literal(8.5)
              literal(24.0)
            }
            stop {
              literal(10.5)
              literal(26.0)
            }
          }
          literal(5.0)
        }
      )
      circleStrokeColor(Color.BLACK)
      circleStrokeWidth(1.0)
      circleColor(
        switchCase {
          boolean {
            featureState {
              literal("hover")
            }
            literal(false)
          }
          interpolate {
            linear()
            get("mag")
            stop {
              literal(1.0)
              literal("#fff7ec")
            }
            stop {
              literal(1.5)
              literal("#fee8c8")
            }
            stop {
              literal(2.0)
              literal("#fdd49e")
            }
            stop {
              literal(2.5)
              literal("#fdbb84")
            }
            stop {
              literal(3.0)
              literal("#fc8d59")
            }
            stop {
              literal(3.5)
              literal("#ef6548")
            }
            stop {
              literal(4.5)
              literal("#d7301f")
            }
            stop {
              literal(6.5)
              literal("#b30000")
            }
            stop {
              literal(8.5)
              literal("#7f0000")
            }
            stop {
              literal(10.5)
              literal("#000")
            }
          }
          literal("#000")
        }
      )
    }
  }

  companion object {
    /**
     * Use data from the USGS Earthquake Catalog API, which returns information about recent earthquakes,
     * including the magnitude, location, and the time at which the earthquake happened.
     */
    private val GEOJSON_URL =
      "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&minmagnitude=1&starttime=${
        // Get the date seven days ago as an ISO 8601 timestamp, as required by the USGS Earthquake Catalog API.
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
          timeZone = TimeZone.getTimeZone("UTC")
        }.format(
          Calendar.getInstance().apply {
            add(Calendar.DATE, -7)
          }.time
        )
      }"
    private const val LAYER_ID = "earthquakes-viz"
    private const val SOURCE_ID = "earthquakes"
    private val CAMERA_CENTER = Point.fromLngLat(-122.44121, 37.76132)
    private const val CAMERA_ZOOM = 3.5
    private const val TAG = "FeatureStateActivity"
  }
}