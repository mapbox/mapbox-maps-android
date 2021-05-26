package com.mapbox.maps.testapp.examples.snapshotter

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.Snapshotter
import com.mapbox.maps.extension.style.expressions.dsl.generated.heatmapDensity
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.dsl.generated.zoom
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.get
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.interpolate
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.linear
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.rgb
import com.mapbox.maps.extension.style.expressions.generated.Expression.Companion.rgba
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer

/**
 * Example that showcases using Snapshotter with data driven styling.
 *
 * In this example we highlight both the usage of the low as high level API of Style
 *
 */
class DataDrivenMapSnapshotterActivity : AppCompatActivity() {
  private lateinit var snapshotter: Snapshotter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val snapshotMapOptions = MapSnapshotOptions.Builder()
      .size(Size(512.0f, 512.0f))
      .resourceOptions(MapInitOptions.getDefaultResourceOptions(this))
      .build()

    snapshotter = Snapshotter(this, snapshotMapOptions).apply {
      setStyleListener(object : SnapshotStyleListener {
        override fun onDidFinishLoadingStyle(style: Style) {
          Toast.makeText(
            this@DataDrivenMapSnapshotterActivity,
            "Map load style success!",
            Toast.LENGTH_LONG
          ).show()

          // GeoJSON earthquake source using low level style API
          val properties = HashMap<String, Value>()
          properties["type"] = Value.valueOf("geojson")
          properties["data"] = Value.valueOf(EARTHQUAKE_SOURCE_URL)
          val sourceResult = style.addStyleSource(EARTHQUAKE_SOURCE_ID, Value.valueOf(properties))
          if (sourceResult.isError) {
            throw RuntimeException(sourceResult.error)
          }

          // Heatmap layer using high level style PI
          style.addLayer(getHeatmapLayer())
        }
      })
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(-94.0, 15.0))
          .zoom(5.0)
          .padding(EdgeInsets(1.0, 1.0, 1.0, 1.0))
          .build()
      )
      setStyleUri(Style.OUTDOORS)
    }
    snapshotter.start {
      it?.let { snapshot ->
        val imageView = ImageView(this@DataDrivenMapSnapshotterActivity)
        imageView.setImageBitmap(snapshot.bitmap())
        setContentView(imageView)
      }
    }
  }

  private fun getHeatmapLayer(): HeatmapLayer {
    val layer = HeatmapLayer(LAYER_ID, EARTHQUAKE_SOURCE_ID)
    layer.maxZoom(9.0)
    layer.sourceLayer(HEATMAP_LAYER_SOURCE)
    layer.heatmapColor(
      // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
      // Begin color ramp at 0-stop with a 0-transparency color
      // to create a blur-like effect.
      interpolate(
        linear(), heatmapDensity(),
        literal(0), rgba(33.0, 102.0, 172.0, 0.0),
        literal(0.2), rgb(103.0, 169.0, 207.0),
        literal(0.4), rgb(209.0, 229.0, 240.0),
        literal(0.6), rgb(253.0, 219.0, 199.0),
        literal(0.8), rgb(239.0, 138.0, 98.0),
        literal(1), rgb(178.0, 24.0, 43.0)
      )
    )
    // Increase the heatmap weight based on frequency and property magnitude
    layer.heatmapWeight(
      interpolate(
        linear(), get("mag"),
        literal(0), literal(0),
        literal(6), literal(1)
      )
    )

    // Increase the heatmap color weight weight by zoom level
    // heatmap-intensity is a multiplier on top of heatmap-weight
    layer.heatmapIntensity(
      interpolate(
        linear(), zoom(),
        literal(0), literal(1),
        literal(9), literal(3)
      )
    )

    // Adjust the heatmap radius by zoom level
    layer.heatmapRadius(
      interpolate(
        linear(), zoom(),
        literal(0), literal(2),
        literal(9), literal(20)
      )
    )

    // Transition from heatmap to circle layer by zoom level
    layer.heatmapOpacity(
      interpolate(
        linear(), zoom(),
        literal(7), literal(1),
        literal(9), literal(0)
      )
    )
    return layer
  }

  override fun onDestroy() {
    super.onDestroy()
    snapshotter.cancel()
  }

  companion object {
    const val TAG: String = "DataDrivenMapSnapshotterActivity"
    private const val EARTHQUAKE_SOURCE_URL =
      "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"
    private const val EARTHQUAKE_SOURCE_ID = "earthquakes"
    private const val LAYER_ID = "earthquakes-heat"
    private const val HEATMAP_LAYER_SOURCE = "earthquakes"
  }
}