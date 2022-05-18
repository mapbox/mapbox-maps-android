package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.match
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.step
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.toColor
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.testapp.R
import java.nio.ByteBuffer

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private lateinit var symbolLayer: SymbolLayer
  private var currentCategoryIndex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_custom_layer)
    val mapView = findViewById<MapView>(R.id.mapView)
    val mapboxMap = mapView.getMapboxMap()

    // set camera to munich
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(LONGITUDE, LATITUDE))
        .zoom(15.0)
        .build()
    )

    // load streets style
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {

      // add images to style
      addImageToStyle(it, ID_IMAGE_BAR, R.drawable.ic_bar)
      addImageToStyle(it, ID_IMAGE_HOTEL, R.drawable.ic_hotel)
      addImageToStyle(it, ID_IMAGE_RESTAURANT, R.drawable.ic_restaurant)

      // add source to style
      val geoJsonSource = GeoJsonSource(
        GeoJsonSource.Builder(ID_SYMBOL_SOURCE).data(GEOJSON)
      )
      it.addSource(geoJsonSource)

      // add layer to style
      symbolLayer = SymbolLayer(ID_SYMBOL_LAYER, ID_SYMBOL_SOURCE)
      symbolLayer.iconAllowOverlap(true)
      symbolLayer.iconIgnorePlacement(true)

      // simple example of data driven styling when values match underlying data
      symbolLayer.iconImage(get(ID_MARKER_SYMBOL))

      // but sometimes, you need to be able to if/else against
      // an input type to return a different output type
      // for example string to double
      symbolLayer.iconSize(
        match{
          get(ID_MARKER_SIZE)
          stop {
            literal(ID_SMALL)
            literal(0.25)
          }
          stop {
            literal(ID_MEDIUM)
            literal(0.50)
          }
          literal(0.75)
        }
      )

      symbolLayer.iconOpacity(
        step {
          zoom()
          literal(0.0)
          stop {
            literal(0.0)
            literal(0.0)
          }
          stop {
            literal(13.0)
            match {
              get(ID_MARKER_SIZE)
              stop {
                literal(ID_SMALL)
                literal(0.0)
              }
              stop {
                literal(ID_MEDIUM)
                literal(0.0)
              }
              literal(1.0)
            }
          }

          stop {
            literal(13.5)
            match {
              get(ID_MARKER_SIZE)
              stop {
                literal(ID_SMALL)
                literal(0.0)
              }
              stop {
                literal(ID_MEDIUM)
                literal(0.50)
              }
              literal(1.0)
            }
          }
          stop {
            literal(15.0)
            literal(1.0)
          }
        }
      )
      it.addLayer(symbolLayer)

      // Hide streets POI label layer
      val streetsPoiLayer:SymbolLayer = it.getLayerAs("poi-label")!!
      streetsPoiLayer.visibility(Visibility.NONE)
    }

    // add click listener to change layer filter
    findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
      if (::symbolLayer.isInitialized) {

        // toggle through categories
        currentCategoryIndex++
        if(currentCategoryIndex == CATEGORIES.lastIndex){
          currentCategoryIndex = 0
        }

        // apply filter to hide certain categories
        symbolLayer.filter(
          eq(
            get(ID_MARKER_SYMBOL),
            literal(CATEGORIES[currentCategoryIndex])
          )
        )
      }
    }
  }

  private fun addImageToStyle(style: Style, imageId: String, imageResId: Int) {
    val bitmap = ContextCompat.getDrawable(this, imageResId)?.toBitmap(64, 64)
    val byteBuffer = ByteBuffer.allocate(bitmap!!.byteCount)
    bitmap.copyPixelsToBuffer(byteBuffer)
    style.addStyleImage(
      imageId,
      1f,
      Image(64, 64, byteBuffer.array()),
      false,
      mutableListOf(),
      mutableListOf(),
      null
    )
  }

  companion object {
    private const val ID_SYMBOL_LAYER = "symbol-layer"
    private const val ID_SYMBOL_SOURCE = "symbol-source"
    private const val ID_MARKER_SYMBOL = "marker-symbol"
    private const val ID_MARKER_SIZE = "marker-size"
    private const val ID_MARKER_COLOR = "marker-color"

    private const val ID_IMAGE_BAR = "bar"
    private const val ID_IMAGE_HOTEL = "hotel"
    private const val ID_IMAGE_RESTAURANT = "restaurant"

    private const val ID_SMALL = "small"
    private const val ID_MEDIUM = "medium"

    private val CATEGORIES = listOf(
      ID_IMAGE_BAR, ID_IMAGE_HOTEL, ID_IMAGE_RESTAURANT
    )

    private const val LATITUDE = 48.136240
    private const val LONGITUDE = 11.544765

    private val GEOJSON = """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "properties": {
                "marker-size": "medium",
                "marker-symbol": "hotel"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.543927192687987,
                  48.136637800607815
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "small",
                "marker-symbol": "restaurant"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.542832851409912,
                  48.136709400139345
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "large",
                "marker-symbol": "restaurant"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.544131040573118,
                  48.135084066180966
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "medium",
                "marker-symbol": "restaurant"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.543455123901367,
                  48.13733231185063
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "medium",
                "marker-symbol": "bar"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.54165267944336,
                  48.13572131778541
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "small",
                "marker-symbol": "hotel"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.540848016738892,
                  48.13571415769893
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "small",
                "marker-symbol": "bar"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.543959379196167,
                  48.136079320834966
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "large",
                "marker-symbol": "hotel"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.542360782623291,
                  48.13528455068453
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "marker-size": "large",
                "marker-symbol": "bar"
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  11.540665626525879,
                  48.13671656008703
                ]
              }
            }
          ]
        }      
    """.trimIndent()
  }
}