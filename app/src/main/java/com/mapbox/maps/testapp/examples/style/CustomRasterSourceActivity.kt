package com.mapbox.maps.testapp.examples.style

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CanonicalTileID
import com.mapbox.maps.CustomRasterSourceClient
import com.mapbox.maps.CustomRasterSourceTileData
import com.mapbox.maps.CustomRasterSourceTileStatus
import com.mapbox.maps.MapboxDelicateApi
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.rasterLayer
import com.mapbox.maps.extension.style.sources.CustomRasterSource
import com.mapbox.maps.extension.style.sources.customRasterSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.logI
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.testapp.databinding.ActivityCustomRasterSourceBinding
import com.mapbox.maps.toMapboxImage

/**
 * Example of using custom raster source.
 */
@OptIn(MapboxExperimental::class)
class CustomRasterSourceActivity : AppCompatActivity() {

  private lateinit var customRasterSource: CustomRasterSource

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
    val requiredTiles = mutableListOf<CanonicalTileID>()
    val client = CustomRasterSourceClient { tileId: CanonicalTileID, status: CustomRasterSourceTileStatus ->
      logI(
        TAG,
        "tileStatusChangedFunction called: status=${status.name}, tileId=[x=${tileId.x}, y=${tileId.y}, z=${tileId.z}]"
      )
      if (status == CustomRasterSourceTileStatus.REQUIRED) {
        requiredTiles.add(tileId)
        refresh(requiredTiles)
      } else {
        if (requiredTiles.contains(tileId)) {
          requiredTiles.remove(tileId)
          customRasterSource.setTileData(listOf(CustomRasterSourceTileData(tileId, null)))
        }
      }
    }
    customRasterSource = customRasterSource(CUSTOM_RASTER_SOURCE_ID) {
      tileSize(TILE_SIZE.toShort())
      clientCallback(client)
    }
    binding.mapView.mapboxMap.apply {
      setCamera(INITIAL_CAMERA)
      loadStyle(
        style(Style.STANDARD) {
          +customRasterSource
          +rasterLayer(RASTER_LAYER_ID, CUSTOM_RASTER_SOURCE_ID) { }
        }
      ) {
        refresh(requiredTiles)
        binding.refreshBtn.setOnClickListener {
          refresh(requiredTiles)
        }
      }
    }
  }

  private fun refresh(requiredTiles: List<CanonicalTileID>) {
    val tiles = RasterTileProvider.createTiles(
      requiredTiles,
      nextColor,
      TILE_SIZE,
      TILE_SIZE
    )
    customRasterSource.setTileData(tiles)
  }

  companion object {
    private const val TAG = "CRSActivity"
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
  @OptIn(MapboxDelicateApi::class)
  fun createTiles(
    requiredTiles: List<CanonicalTileID>,
    color: Int,
    width: Int,
    height: Int,
  ): List<CustomRasterSourceTileData> {
    val image = createBitmap(color, width, height).toMapboxImage()
    return requiredTiles.map {
      CustomRasterSourceTileData(it, image)
    }
  }

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