package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.bindgen.Value
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.*
import com.mapbox.maps.extension.style.layers.*
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.fillExtrusionLayer
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.light.generated.light
import com.mapbox.maps.extension.style.light.generated.setLight
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.sources.getSource
import com.mapbox.maps.testapp.R

/**
 * Example showcasing usage of style extension.
 */
class RuntimeStylingActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(
      Style.STANDARD
    ) { setupStyle(it) }
  }

  private fun setupStyle(style: Style) {
    addImage(style)
    addSymbolSource(style)
    addSymbolLayer(style)

    addFillSource(style)
    setFillLayer(style)
    addFillExtrusionLayer(style)
    addFillExtrusionLight(style)

    addImageSource(style)
    addRasterLayer(style)

    addLayerWithoutStyleExtension(style)

    val source = style.getSource("composite") as VectorSource
    logE(TAG, "getSource: $source")
  }

  private fun addImage(style: Style) {
    val bitmap = ContextCompat.getDrawable(this, R.drawable.android_symbol)?.toBitmap(64, 64)!!
    val expected = style.addImage(
      "myImage",
      bitmap,
      true
    )
    expected.error?.let {
      logE(TAG, it)
    }
    expected.value?.let {
      logD(TAG, it.toString())
    }
  }

  private fun addSymbolSource(style: Style) {
    val source = geoJsonSource(id = "points") {
      featureCollection(
        FeatureCollection.fromJson(
          """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "properties": {
                "count": 0
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  -42.978515625,
                  22.024545601240337
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "count": 0
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  -29.355468750000004,
                  25.64152637306577
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "count": 1
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  -3.69140625,
                  -4.214943141390639
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "count": 1              
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  -27.861328125,
                  3.337953961416485
                ]
              }
            },
            {
              "type": "Feature",
              "properties": {
                "count": 1
              },
              "geometry": {
                "type": "Point",
                "coordinates": [
                  -27.773437499999996,
                  -17.644022027872712
                ]
              }
            }
          ]
        }
          """.trimIndent()
        )
      )
      cluster(true)
      prefetchZoomDelta(1)
    }
    logI(TAG, source.toString())
    style.addSource(source)
    logI(TAG, "prefetchZoomDelta : ${source.prefetchZoomDelta}")
  }

  private fun addSymbolLayer(style: Style) {
    val symbolLayer = symbolLayer(layerId = "symbollayer", sourceId = "points") {
      filter(
        eq {
          get {
            literal("count")
          }
          literal(0)
        }
      )
      iconImage(literal("myImage"))
      iconOpacity(
        subtract {
          literal(1.0)
          literal(0.6)
        }
      )
      textField(
        format {
          formatSection("London") {
            fontScale(1.0)
            textFont(
              listOf(
                "Open Sans Regular",
                "Arial Unicode MS Regular"
              )
            )
            textColor(Color.RED)
          }
          formatSection(
            image {
              literal("london-underground")
            }
          ) {
            fontScale(0.9)
          }
          formatSection("underground") {
            fontScale(0.8)
            textFont(
              listOf(
                "Open Sans Regular",
                "Arial Unicode MS Regular"
              )
            )
            textColor(Color.WHITE)
          }
        }
      )
      iconColor(Color.GREEN)
      textAnchor(TextAnchor.CENTER)
      iconAnchor(IconAnchor.BOTTOM)
      textIgnorePlacement(false)
      iconIgnorePlacement(false)
    }
    style.addLayer(symbolLayer)
    logI(TAG, symbolLayer.iconOpacityAsExpression.toString())
  }

  private fun addFillSource(style: Style) {
    val polygon = geoJsonSource(id = "polygon") {
      featureCollection(
        FeatureCollection.fromJson(
          """
        {
          "type": "FeatureCollection",
          "features": [
            {
              "type": "Feature",
              "properties": {},
              "geometry": {
                "type": "Polygon",
                "coordinates": [
                  [
                    [
                      -366.85546875,
                      18.145851771694467
                    ],
                    [
                      -373.27148437499994,
                      12.726084296948196
                    ],
                    [
                      -364.39453125,
                      6.577303118123887
                    ],
                    [
                      -366.85546875,
                      18.145851771694467
                    ]
                  ]
                ]
              }
            }
          ]
        }
          """.trimIndent()
        )
      )
    }
    logI(TAG, polygon.toString())
    style.addSource(polygon)
  }

  private fun setFillLayer(style: Style) {
    val fillLayer = style.getLayer("water") as FillLayer
    fillLayer.fillColor(
      interpolate {
        exponential {
          literal(0.5)
        }
        zoom()
        stop {
          literal(1.0)
          color(Color.RED)
        }
        stop {
          literal(5.0)
          color(Color.BLUE)
        }
        stop {
          literal(10.0)
          color(Color.GREEN)
        }
      }
    )
    fillLayer.visibility(Visibility.VISIBLE)
    logI(TAG, fillLayer.fillColorAsExpression.toString())
  }

  private fun addFillExtrusionLayer(style: Style) {
    val fillExtrusionLayer = fillExtrusionLayer("fillextrusion", "polygon") {
      fillExtrusionHeight(1000000.0)
      fillExtrusionColor(Color.GRAY)
      fillExtrusionOpacity(1.0)
    }
    style.addLayer(fillExtrusionLayer)
  }

  private fun addFillExtrusionLight(style: Style) {
    val light = light {
      anchor(Anchor.MAP)
      color(Color.YELLOW)
      position(
        radialCoordinate = 10.0,
        azimuthalAngle = 40.0,
        polarAngle = 50.0
      )
    }
    style.setLight(light)
  }

  private fun addImageSource(style: Style) {
    val imageSource = imageSource("imag") {
      url("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Mapbox_logo_2019.svg/2560px-Mapbox_logo_2019.svg.png")
      coordinates(
        listOf(
          listOf(-35.859375, 58.44773280389084),
          listOf(-16.171875, 58.44773280389084),
          listOf(-16.171875, 54.7246201949245),
          listOf(-35.859375, 54.7246201949245)
        )
      )
    }
    style.addSource(imageSource)
  }

  private fun addRasterLayer(style: Style) {
    val raster = rasterLayer("raster", "imag") {}
    style.addLayer(raster)
  }

  private fun addLayerWithoutStyleExtension(style: Style) {
    val bitmap = ContextCompat.getDrawable(this, R.drawable.android_symbol)?.toBitmap(64, 64)
    val expected = style.addStyleImage(
      "myImage",
      1f,
      bitmap!!.toMapboxImage(),
      false,
      mutableListOf(),
      mutableListOf(),
      null
    )
    expected.error?.let {
      logE(TAG, it)
    }
    expected.value?.let {
      logD(TAG, it.toString())
    }

    val sourceParams = HashMap<String, Value>()
    sourceParams["type"] = Value("geojson")
    val data = HashMap<String, Value>()
    data["type"] = Value("Feature")
    val geometry = HashMap<String, Value>()
    geometry["type"] = Value("Point")
    geometry["coordinates"] = Value(listOf(Value(24.9384), Value(60.1699)))
    data["geometry"] = Value(geometry)
    sourceParams["data"] = Value(data)
    style.addStyleSource("source", Value(sourceParams))

    val layerParams = HashMap<String, Value>()
    layerParams["id"] = Value("layer")
    layerParams["type"] = Value("symbol")
    layerParams["source"] = Value("source")
    style.addStyleLayer(Value(layerParams), null)
    style.setStyleLayerProperty("layer", "icon-image", Value("myImage"))
    style.setStyleLayerProperty("layer", "icon-opacity", Value(1.0))
    style.setStyleLayerProperty("layer", "icon-size", Value(5.0))
    style.setStyleLayerProperty("layer", "icon-color", Value("white"))
  }

  companion object {
    private const val TAG = "RuntimeStylingActivity"
  }
}