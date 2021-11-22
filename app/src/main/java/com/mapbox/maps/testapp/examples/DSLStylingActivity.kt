package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.generated.imageSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import java.text.DateFormat.getDateTimeInstance
import java.util.*

/**
 * Example showcasing usage of style extension.
 */
class DSLStylingActivity : AppCompatActivity(), OnMapClickListener {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyle(createStyle())
    mapboxMap.addOnMapClickListener(this)
  }

  override fun onMapClick(point: Point): Boolean {
    val clicked = mapboxMap.pixelForCoordinate(point)
    mapboxMap.queryRenderedFeatures(
      RenderedQueryGeometry(
        ScreenBox(
          ScreenCoordinate(clicked.x - 50, clicked.y - 50),
          ScreenCoordinate(clicked.x + 50, clicked.y + 50)
        )
      ),
      RenderedQueryOptions(listOf("earthquakeCircle", "earthquakeText"), literal(true))
    ) { expected: Expected<String, MutableList<QueriedFeature>> ->
      val features = expected.value!!
      features.takeIf { it.isNotEmpty() }?.let {
        val time = it.first().feature.getNumberProperty("time")
        Toast.makeText(this, getDateTime(time.toLong()), Toast.LENGTH_SHORT).show()
      }
    }
    return true
  }

  private fun getDateTime(time: Long): String = try {
    val sdf = getDateTimeInstance()
    val netDate = Date(time)
    sdf.format(netDate)
  } catch (e: Exception) {
    e.toString()
  }

  private fun createStyle() = style(styleUri = Style.TRAFFIC_DAY) {
    +imageSource("imag") {
      url(IMAGE_URL)
      coordinates(
        listOf(
          listOf(-35.859375, 58.44773280389084),
          listOf(-16.171875, 58.44773280389084),
          listOf(-16.171875, 54.7246201949245),
          listOf(-35.859375, 54.7246201949245)
        )
      )
    }
    +geoJsonSource(id = "earthquakes") {
      url(GEOJSON_URL)
      cluster(false)
    }
    +circleLayer(layerId = "earthquakeCircle", sourceId = "earthquakes") {
      circleRadius(get { literal("mag") })
      circleColor(rgb(255.0, 0.0, 0.0))
      circleOpacity(0.3)
      circleStrokeColor(Color.WHITE)
    }
    +layerAtPosition(
      symbolLayer(
        layerId = "earthquakeText",
        sourceId = "earthquakes"
      ) {
        filter(
          gt {
            get {
              literal("mag")
            }
            literal(5.0)
          }
        )
        textField(
          format {
            formatSection(
              concat {
                literal("mag")
                toString {
                  get {
                    literal("mag")
                  }
                }
              }
            ) {
              textFont(listOf("Open Sans Regular", "Arial Unicode MS Regular"))
              textColor(Color.BLACK)
            }
          }
        )
        textHaloColor(Color.WHITE)
        textHaloWidth(1.0)
        textAnchor(TextAnchor.TOP)
        textOffset(listOf(0.0, 1.0))
        textSize(10.0)
        textIgnorePlacement(false)
        symbolSortKey(
          subtract {
            toNumber {
              get {
                literal("mag")
              }
            }
          }
        )
      },
      above = "earthquakeCircle"
    )
    +rasterLayer("raster", "imag") {
      rasterOpacity(0.8)
    }
  }

  companion object {
    private const val IMAGE_URL =
      "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png"
    private const val GEOJSON_URL =
      "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"
  }
}