package com.mapbox.maps.testapp.examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource

/**
 * Example of displaying a map.
 */
class SimpleMapActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)
    mapboxMap = mapView.getMapboxMap()
      .apply {
        setCamera(
          CameraOptions.Builder()
            .center(Point.fromLngLat(LONGITUDE, LATITUDE))
            .zoom(ZOOM)
            .build()
        )
      }
  }

  override fun onStart() {
    super.onStart()
    val json = """
      {
          "coordinates": [
              [
                  [
                      9.9507706,
                      57.0581363
                  ],
                  [
                      9.9507233,
                      57.0581687
                  ],
                  [
                      9.9507757,
                      57.0581914
                  ],
                  [
                      9.9508231,
                      57.058159
                  ],
                  [
                      9.950785250219118,
                      57.05814263447568
                  ],
                  [
                      9.9507847355813,
                      57.05814298643625
                  ],
                  [
                      9.950821937959335,
                      57.058159072035934
                  ],
                  [
                      9.950775568041005,
                      57.058190767929474
                  ],
                  [
                      9.950724460898005,
                      57.058168628003024
                  ],
                  [
                      9.950770733103315,
                      57.058136932031516
                  ],
                  [
                      9.950772230569081,
                      57.05813757950722
                  ],
                  [
                      9.95077274520702,
                      57.05813722754667
                  ],
                  [
                      9.9507706,
                      57.0581363
                  ]
              ]
          ],
          "bbox": [
              9.9507233,
              57.0581363,
              9.9508231,
              57.0581914
          ],
          "type": "Polygon"
      }
    """.trimIndent()

    mapboxMap.getStyle {
      val fillLayer = FillLayer("layer", "source")
      fillLayer.fillColor("#FF00FF")
      it.addLayer(fillLayer)
      val source = geoJsonSource("source").data(json)

      it.addSource(source)
    }
  }

  companion object {
    private const val LATITUDE = 57.0581363
    private const val LONGITUDE = 9.9507233
    private const val ZOOM = 20.5
  }
}