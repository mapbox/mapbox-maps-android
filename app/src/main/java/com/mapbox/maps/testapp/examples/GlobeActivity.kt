package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.expressions.dsl.generated.eq
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.fillExtrusionLayer
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.projection.generated.getProjection
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.projection.generated.setProjection
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityGlobeViewBinding

/**
 * Example of using 3D globe projection.
 *
 * It allows switching map projection in runtime and checking out current zoom level + projection
 * in special info window.
 */
class GlobeActivity : AppCompatActivity() {

  /**
   * Current projection.
   * However depending on zoom level actual projection may be mercator even if [currentProjectionName] is Globe.
   */
  private var currentProjectionName: ProjectionName = ProjectionName.GLOBE
  private lateinit var mapboxMap: MapboxMap
  private lateinit var infoTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityGlobeViewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    infoTextView = binding.infoText
    mapboxMap = binding.mapView.getMapboxMap().apply {
      loadStyle(
        style(Style.TRAFFIC_DAY) {
          +fillExtrusionLayer("3d-buildings", "composite") {
            sourceLayer("building")
            filter(
              eq {
                get("extrude")
                literal("true")
              }
            )
            minZoom(12.0)
            fillExtrusionColor(Color.parseColor("#aaaaaa"))
            fillExtrusionHeight(Expression.get("height"))
            fillExtrusionBase(Expression.get("min_height"))
            fillExtrusionOpacity(0.6)
          }
          +skyLayer("sky") {
            skyType(SkyType.ATMOSPHERE)
            skyAtmosphereSun(listOf(15.0, 89.5))
          }
          +projection(currentProjectionName)
        }
      )
    }

    binding.switchProjectionBtn.setOnClickListener {
      val newProjection = if (currentProjectionName === ProjectionName.GLOBE) {
        ProjectionName.MERCATOR
      } else {
        ProjectionName.GLOBE
      }
      mapboxMap.getStyle { style ->
        style.setProjection(projection(newProjection))
        currentProjectionName = newProjection
        updateInfoText(mapboxMap.cameraState.zoom)
      }
    }

    binding.flyToBtn.setOnClickListener {
      with(binding.mapView.camera) {
        val centerAnimator = createCenterAnimator(cameraAnimatorOptions(TARGET_POINT)) {
          duration = ANIMATION_DURATION_MS / 2
          interpolator = AccelerateDecelerateInterpolator()
        }
        val bearingAnimator = createBearingAnimator(cameraAnimatorOptions(TARGET_BEARING)) {
          duration = ANIMATION_DURATION_MS
          interpolator = AccelerateDecelerateInterpolator()
        }
        val zoomAnimator = createZoomAnimator(cameraAnimatorOptions(TARGET_ZOOM)) {
          startDelay = ANIMATION_DURATION_MS / 2
          duration = ANIMATION_DURATION_MS
          interpolator = AccelerateDecelerateInterpolator()
        }
        val pitchAnimator = createPitchAnimator(cameraAnimatorOptions(TARGET_PITCH)) {
          startDelay = ANIMATION_DURATION_MS / 4
          duration = ANIMATION_DURATION_MS / 2
          interpolator = LinearInterpolator()
        }
        playAnimatorsTogether(centerAnimator, bearingAnimator, zoomAnimator, pitchAnimator)
      }
    }

    updateInfoText(mapboxMap.cameraState.zoom)
    binding.mapView.camera.addCameraZoomChangeListener {
      updateInfoText(it)
    }
  }

  private fun updateInfoText(zoom: Double) {
    mapboxMap.getStyle {
      infoTextView.text = getString(
        R.string.info_text,
        "%.2f".format(zoom.toFloat()),
        it.getProjection().name?.value,
        currentProjectionName.value
      )
    }
  }

  companion object {
    private const val ANIMATION_DURATION_MS = 10_000L
    private const val TARGET_BEARING = 16.35586889454862
    private const val TARGET_ZOOM = 18.51596061886663
    private const val TARGET_PITCH = 78.50097654853042

    private val MODEL_POINT = Point.fromLngLat(-73.99462885531243, 40.7188264245498)
    private val TARGET_POINT = Point.fromLngLat(-73.99429285004713, 40.71976882733935)
  }
}