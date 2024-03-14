package com.mapbox.maps.testapp.examples.style

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.geojson.Point
import com.mapbox.maps.CanonicalTileID
import com.mapbox.maps.Image
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.TileCoverOptions
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.CustomRasterSource
import com.mapbox.maps.extension.style.sources.customRasterSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.databinding.ActivityCustomRasterSourceBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Example of using custom raster source.
 */
@OptIn(MapboxExperimental::class)
class CustomRasterSourceActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityCustomRasterSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.mapView.gestures.apply {
      // simplify the demo by not needing to handle camera changes
      doubleTapToZoomInEnabled = false
      doubleTouchToZoomOutEnabled = false
      quickZoomEnabled = false
      pinchToZoomEnabled = false
      pitchEnabled = false
      rotateEnabled = false
      scrollEnabled = false
    }
    val customRasterSource = customRasterSource(CUSTOM_RASTER_SOURCE_ID) {
      tileSize(TILE_SIZE.toShort())
      cancelTileFunction { _ -> }
      fetchTileFunction { _ -> }
    }
    binding.mapView.mapboxMap.apply {
      setCamera(INITIAL_CAMERA)
      loadStyle(
        style(Style.STANDARD) {
          +customRasterSource
          +rasterLayer(RASTER_LAYER_ID, CUSTOM_RASTER_SOURCE_ID) { }
        }
      ) {
        refresh(this@apply, customRasterSource)
        binding.refreshBtn.setOnClickListener {
          refresh(this@apply, customRasterSource)
        }
      }
    }
  }

  private fun refresh(mapboxMap: MapboxMap, source: CustomRasterSource) {
    lifecycleScope.launch {
      val coveringTiles = mapboxMap.tileCover(
        TileCoverOptions.Builder()
          .tileSize(TILE_SIZE.toShort())
          .roundZoom(true)
          .build(),
        cameraOptions = null
      )
      coveringTiles.forEach { source.setTileData(it, null as Image?) }
      RasterTileProvider.createTiles(
        coveringTiles,
        nextColor,
        TILE_SIZE,
        TILE_SIZE
      ) { tiles ->
        tiles.forEach {
          source.setTileData(
            it.tileID,
            it.image
          )
        }
      }
    }
  }

  companion object {
    private val INITIAL_CAMERA = cameraOptions {
      center(Point.fromLngLat(24.945389069265598, 60.17195694011002))
      zoom(5.0)
    }
    private const val CUSTOM_RASTER_SOURCE_ID = "custom-raster-source-id"
    private const val RASTER_LAYER_ID = "raster-layer-id"
    private const val TILE_SIZE = 256
    private val colors = intArrayOf(
      Color.CYAN,
      Color.RED,
      Color.GREEN,
      Color.BLUE,
      Color.YELLOW,
    )
    private var colorIndex = 0
    private val nextColor: Int
      get() {
        colorIndex = (colorIndex + 1) % colors.size
        return colors[colorIndex]
      }
  }
}

/**
 * Utility to provide raster tiles.
 */
object RasterTileProvider {
  suspend fun createTiles(
    requiredTiles: List<CanonicalTileID>,
    color: Int,
    width: Int,
    height: Int,
    callback: (List<RasterTile>) -> Unit
  ) {
    val image = createBitmap(color, width, height)
    coroutineScope {
      requiredTiles.map {
        async {
          // simulate slow network
          delay(200)
          RasterTile(it, image)
        }
      }.awaitAll().also {
        callback(it)
      }
    }
  }

  data class RasterTile(
    val tileID: CanonicalTileID,
    val image: Bitmap
  )

  private fun createBitmap(
    color: Int,
    width: Int,
    height: Int,
  ): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(color)

    return bitmap
  }
}