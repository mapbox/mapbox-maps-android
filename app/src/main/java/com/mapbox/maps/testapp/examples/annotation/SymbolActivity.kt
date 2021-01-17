package com.mapbox.maps.testapp.examples.annotation

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.annotation.generated.*
import com.mapbox.maps.plugin.annotation.getAnnotationPlugin
import com.mapbox.maps.plugin.location.utils.BitmapUtils
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.utils.Assets
import kotlinx.android.synthetic.main.activity_add_marker_symbol.*
import java.io.IOException
import java.util.*

/**
 * Example showing how to add Symbol annotations
 */
class SymbolActivity : AppCompatActivity() {
  private val random = Random()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_annotation)
    mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) { style ->
      BitmapUtils.getBitmapFromDrawable(
        ResourcesCompat.getDrawable(
          resources,
          R.drawable.ic_airplanemode_active_black_24dp,
          this@SymbolActivity.theme
        )
      )?.let {
        style.addImage(
          ID_ICON_AIRPORT,
          it, true
        )
      }

      val annotationPlugin = mapView.getAnnotationPlugin()
      val symbolManager = annotationPlugin.getSymbolManager()
      symbolManager.addClickListener(
        OnSymbolClickListener {
          Toast.makeText(this@SymbolActivity, "click", Toast.LENGTH_LONG).show()
          false
        }
      )

      // set non data driven properties
      symbolManager.iconAllowOverlap = true
      symbolManager.textAllowOverlap = true

      // create a symbol
      val symbolOptions: SymbolOptions = SymbolOptions()
        .withPoint(Point.fromLngLat(0.381457, 6.687337))
        .withIconImage(ID_ICON_AIRPORT)
        .withIconSize(1.3)
        .withSymbolSortKey(10.0)
        .withDraggable(true)
      symbolManager.create(symbolOptions)

      // create nearby symbols
      val nearbyOptions: SymbolOptions = SymbolOptions()
        .withPoint(Point.fromLngLat(0.367099, 6.626384))
        .withIconImage(MAKI_ICON_CIRCLE)
        .withIconColor(ColorUtils.colorToRgbaString(Color.YELLOW))
        .withIconSize(2.5)
        .withSymbolSortKey(5.0)
        .withDraggable(true)
      symbolManager.create(nearbyOptions)

      // random add symbols across the globe
      val symbolOptionsList: MutableList<SymbolOptions> = ArrayList()
      for (i in 0..20) {
        symbolOptionsList.add(
          SymbolOptions()
            .withPoint(createRandomPoints())
            .withIconImage(MAKI_ICON_CAR)
            .withDraggable(true)
        )
      }
      symbolManager.create(symbolOptionsList)

      try {
        symbolManager.create(
          FeatureCollection.fromJson(
            Assets.loadStringFromAssets(
              this,
              "annotations.json"
            )
          )
        )
      } catch (e: IOException) {
        throw RuntimeException("Unable to parse annotations.json")
      }
    }
  }

  private fun createRandomPoints(): Point {
    return Point.fromLngLat(
      random.nextDouble() * -360.0 + 180.0,
      random.nextDouble() * -180.0 + 90.0
    )
  }

  companion object {
    private const val ID_ICON_AIRPORT = "airport"
    private const val MAKI_ICON_CAR = "car-15"
    private const val MAKI_ICON_CAFE = "cafe-15"
    private const val MAKI_ICON_CIRCLE = "fire-station-15"
  }
}