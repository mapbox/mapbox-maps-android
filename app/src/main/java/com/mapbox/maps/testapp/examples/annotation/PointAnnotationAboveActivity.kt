package com.mapbox.maps.testapp.examples.annotation

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Image
import com.mapbox.maps.ImageContent
import com.mapbox.maps.ImageStretches
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.observable.eventdata.MapLoadedEventData
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadedListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityAnnotationBinding
import java.nio.ByteBuffer

class PointAnnotationAboveActivity : AppCompatActivity(), OnMapLoadedListener {

  private var pointAnnotationManager: PointAnnotationManager? = null
  private lateinit var mapboxMap: MapboxMap
  private lateinit var binding: ActivityAnnotationBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityAnnotationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(
          Point.fromLngLat(
            LONGITUDE,
            LATITUDE
          )
        )
        .zoom(12.0)
        .build()
    )

    mapboxMap.addOnMapLoadedListener(this)
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
    binding.deleteAll.visibility = View.GONE
    binding.changeStyle.visibility = View.GONE

    mapboxMap.addOnMapClickListener {
      pointAnnotationManager!!.create(
        PointAnnotationOptions()
          .withTextSize(24.0)
          .withTextField("5 mins faster")
          .withTextAnchor(TextAnchor.CENTER)
          .withIconImage(ICON_ID)
          .withTextOffset(listOf(0.0, -0.8))
          .withGeometry(Point.fromLngLat(it.longitude(), it.latitude()))
      )

      true
    }
  }

  fun Int.px(): Double = (this * resources.displayMetrics.density).toDouble()

  override fun onMapLoaded(eventData: MapLoadedEventData) {
    val style = mapboxMap.getStyle()!!

    ContextCompat.getDrawable(this, R.drawable.ic_faster_route_label)?.let {
      val topMargin = 4.px().toFloat()
      val leftMargin = 32.px().toFloat()
      val rightMargin = it.intrinsicWidth - 32.px().toFloat()
      val bottomMargin = it.intrinsicHeight - 19.px().toFloat()

      val bitmap = it.toBitmap()
      val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
      bitmap.copyPixelsToBuffer(byteBuffer)
      val image = Image(bitmap.width, bitmap.height, byteBuffer.array())

      style.addStyleImage(
        ICON_ID,
        Resources.getSystem().displayMetrics.density,
        image,
        false,
        mutableListOf(
          ImageStretches(leftMargin, leftMargin + 1),
          ImageStretches(rightMargin - 1, rightMargin),
        ),
        mutableListOf(
          ImageStretches(28.px().toFloat(), 28.px().toFloat() + 1)
        ),
        ImageContent(
          leftMargin,
          topMargin,
          rightMargin,
          bottomMargin,
        )
      )
    }
    pointAnnotationManager = binding.mapView.annotations.createPointAnnotationManager().apply {
      iconTextFit = IconTextFit.BOTH
    }
  }

  companion object {
    private const val LATITUDE = 55.665957
    private const val LONGITUDE = 12.550343

    private const val ICON_ID = "faster-route-label"
  }
}