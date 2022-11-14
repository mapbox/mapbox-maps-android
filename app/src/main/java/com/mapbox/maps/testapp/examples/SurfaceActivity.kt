package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.FeatureCollection
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.fillExtrusionLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.sources.generated.rasterDemSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.extension.style.terrain.generated.terrain
import com.mapbox.maps.renderer.widget.BitmapWidget
import com.mapbox.maps.renderer.widget.WidgetPosition
import com.mapbox.maps.testapp.databinding.ActivitySurfaceBinding
import com.mapbox.maps.testapp.examples.terrain3D.Terrain3DShowcaseActivity
import java.io.File

/**
 * Example integration with MapSurface through using SurfaceView directly.
 */
class SurfaceActivity : AppCompatActivity(), SurfaceHolder.Callback {

  private lateinit var surfaceHolder: SurfaceHolder
  private lateinit var mapSurface: MapSurface

  class CompassWidget(
    context: Context,
    position: WidgetPosition = WidgetPosition(
      horizontal = WidgetPosition.Horizontal.RIGHT,
      vertical = WidgetPosition.Vertical.TOP,
    ),
    marginX: Float = 20f,
    marginY: Float = 20f,
  ) : BitmapWidget(
    bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mapbox_compass_icon),
    position = position,
    marginX = marginX,
    marginY = marginY,
  )

  @SuppressLint("ClickableViewAccessibility")
  public override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivitySurfaceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Setup map surface
    surfaceHolder = binding.surface.holder
    surfaceHolder.addCallback(this)
    mapSurface = MapSurface(
      this,
      surfaceHolder.surface
    )

    mapSurface.addWidget(CompassWidget(this))

    // Load a map style
    mapSurface.getMapboxMap().loadStyle(style(Style.MAPBOX_STREETS) {
      // TODO uncommenting that makes it work on Xiaomi
//      +rasterDemSource(Terrain3DShowcaseActivity.SOURCE) {
//        url(Terrain3DShowcaseActivity.TERRAIN_URL_TILE_RESOURCE)
//        // 514 specifies padded DEM tile and provides better performance than 512 tiles.
//        tileSize(514)
//      }
//      +terrain(Terrain3DShowcaseActivity.SOURCE)
    }) {
      // TODO code below adds fill extrusion - just for testing
//      val polygon = geoJsonSource(id = "polygon") {
//        featureCollection(
//          FeatureCollection.fromJson(
//            """
//        {
//          "type": "FeatureCollection",
//          "features": [
//            {
//              "type": "Feature",
//              "properties": {},
//              "geometry": {
//                "type": "Polygon",
//                "coordinates": [
//                  [
//                    [
//                      -366.85546875,
//                      18.145851771694467
//                    ],
//                    [
//                      -373.27148437499994,
//                      12.726084296948196
//                    ],
//                    [
//                      -364.39453125,
//                      6.577303118123887
//                    ],
//                    [
//                      -366.85546875,
//                      18.145851771694467
//                    ]
//                  ]
//                ]
//              }
//            }
//          ]
//        }
//          """.trimIndent()
//          )
//        )
//      }
//      it.addSource(polygon)
//      val fillExtrusionLayer = fillExtrusionLayer("fillextrusion", "polygon") {
//        fillExtrusionHeight(1000000.0)
//        fillExtrusionColor(Color.GRAY)
//        fillExtrusionOpacity(1.0)
//      }
//      it.addLayer(fillExtrusionLayer)
    }

    // Touch handling (verify plugin integration)
    binding.surface.setOnTouchListener { _, event -> mapSurface.onTouchEvent(event) }
    binding.surface.setOnGenericMotionListener { _, event -> mapSurface.onGenericMotionEvent(event) }
  }

  override fun surfaceCreated(holder: SurfaceHolder) {
    mapSurface.surfaceCreated()
  }

  override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    mapSurface.surfaceChanged(width, height)
  }

  override fun surfaceDestroyed(holder: SurfaceHolder) {
    mapSurface.surfaceDestroyed()
  }

  override fun onStart() {
    super.onStart()
    mapSurface.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapSurface.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapSurface.onDestroy()
  }

  companion object {
    const val TAG = "SurfaceActivity"
  }
}