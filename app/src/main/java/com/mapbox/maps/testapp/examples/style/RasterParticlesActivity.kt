package com.mapbox.maps.testapp.examples.style

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.rasterParticleLayer
import com.mapbox.maps.extension.style.sources.generated.rasterArraySource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.gestures

/**
 * Activity showcasing the wind with [rasterParticleLayer].
 */
@OptIn(MapboxExperimental::class)
class RasterParticlesActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(
      this,
      MapInitOptions(
        this,
        cameraOptions = cameraOptions {
          zoom(1.0)
        }
      )
    )
    setContentView(mapView)
    // disable zooming in as raster particles will be too pixelated
    mapView.gestures.pinchToZoomEnabled = false
    mapView.gestures.doubleTapToZoomInEnabled = false
    mapView.gestures.doubleTouchToZoomOutEnabled = false
    mapView.mapboxMap.loadStyle(
      styleExtension = style(Style.DARK) {
        +rasterArraySource(RASTER_ARRAY_SOURCE_ID) {
          url(RASTER_ARRAY_TILE_JSON_URL)
          tileSize(512)
        }
        +rasterParticleLayer(RASTER_LAYER_ID, RASTER_ARRAY_SOURCE_ID) {
          sourceLayer("10winds")
          rasterParticleSpeedFactor(0.4)
          rasterParticleMaxSpeed(70.0)
          rasterParticleCount(2048L)
          rasterParticleFadeOpacityFactor(0.8)
          rasterParticleResetRateFactor(0.4)
          rasterParticleColor(
            interpolate {
              linear()
              rasterParticleSpeed()
              stop {
                literal(1.5)
                rgba(134.0, 163.0, 171.0, 1.0)
              }
              stop {
                literal(2.5)
                rgba(126.0, 152.0, 188.0, 1.0)
              }
              stop {
                literal(4.12)
                rgba(110.0, 143.0, 208.0, 1.0)
              }
              stop {
                literal(4.63)
                rgba(110.0, 143.0, 208.0, 1.0)
              }
              stop {
                literal(6.17)
                rgba(15.0, 147.0, 167.0, 1.0)
              }
              stop {
                literal(7.72)
                rgba(15.0, 147.0, 167.0, 1.0)
              }
              stop {
                literal(9.26)
                rgba(57.0, 163.0, 57.0, 1.0)
              }
              stop {
                literal(10.29)
                rgba(57.0, 163.0, 57.0, 1.0)
              }
              stop {
                literal(11.83)
                rgba(194.0, 134.0, 62.0, 1.0)
              }
              stop {
                literal(13.37)
                rgba(194.0, 134.0, 63.0, 1.0)
              }
              stop {
                literal(14.92)
                rgba(200.0, 66.0, 13.0, 1.0)
              }
              stop {
                literal(16.46)
                rgba(200.0, 66.0, 13.0, 1.0)
              }
              stop {
                literal(18.00)
                rgba(210.0, 0.0, 50.0, 1.0)
              }
              stop {
                literal(20.06)
                rgba(215.0, 0.0, 50.0, 1.0)
              }
              stop {
                literal(21.60)
                rgba(175.0, 80.0, 136.0, 1.0)
              }
              stop {
                literal(23.66)
                rgba(175.0, 80.0, 136.0, 1.0)
              }
              stop {
                literal(25.21)
                rgba(117.0, 74.0, 147.0, 1.0)
              }
              stop {
                literal(27.78)
                rgba(117.0, 74.0, 147.0, 1.0)
              }
              stop {
                literal(29.32)
                rgba(68.0, 105.0, 141.0, 1.0)
              }
              stop {
                literal(31.89)
                rgba(68.0, 105.0, 141.0, 1.0)
              }
              stop {
                literal(33.44)
                rgba(194.0, 251.0, 119.0, 1.0)
              }
              stop {
                literal(42.18)
                rgba(194.0, 251.0, 119.0, 1.0)
              }
              stop {
                literal(43.72)
                rgba(241.0, 255.0, 109.0, 1.0)
              }
              stop {
                literal(48.87)
                rgba(241.0, 255.0, 109.0, 1.0)
              }
              stop {
                literal(50.41)
                rgba(255.0, 255.0, 255.0, 1.0)
              }
              stop {
                literal(57.61)
                rgba(255.0, 255.0, 255.0, 1.0)
              }
              stop {
                literal(59.16)
                rgba(0.0, 255.0, 255.0, 1.0)
              }
              stop {
                literal(68.93)
                rgba(0.0, 255.0, 255.0, 1.0)
              }
              stop {
                literal(69.44)
                rgba(255.0, 37.0, 255.0, 1.0)
              }
            }
          )
        }
      }
    )
  }

  private companion object {
    private const val RASTER_ARRAY_SOURCE_ID = "wind-mrt-source"
    private const val RASTER_ARRAY_TILE_JSON_URL = "mapbox://rasterarrayexamples.gfs-winds"
    private const val RASTER_LAYER_ID = "layer_particles"
  }
}