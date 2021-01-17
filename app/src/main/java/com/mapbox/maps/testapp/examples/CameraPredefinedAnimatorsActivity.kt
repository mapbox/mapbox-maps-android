package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.animation.*
import com.mapbox.maps.plugin.animation.CameraAnimatorsFactory.Companion.DEFAULT_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_dds_style_circles_categorically.*

/**
 * Example of using predefined animators with camera animation system.
 */
class CameraPredefinedAnimatorsActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var cameraAnimationsPlugin: CameraAnimationsPlugin
  private var currentAnimators: Array<CameraAnimator<Any>> = arrayOf()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_camera_predefined_animators)
    mapboxMap = mapView.getMapboxMap()
    cameraAnimationsPlugin = mapView.getCameraAnimationsPlugin()
    mapboxMap.loadStyle(
      style(Style.MAPBOX_STREETS) {
        mapboxMap.jumpTo(START_CAMERA_POSITION)
      }
    )
    initSpinner()
  }

  private fun initSpinner() {
    val spinner: Spinner = findViewById(R.id.spinnerView)
    val adapter = ArrayAdapter.createFromResource(
      this, R.array.interpolators_array, R.layout.item_spinner_view
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        stopAnimation()
        mapboxMap.jumpTo(START_CAMERA_POSITION)
        when (position) {
          0 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = FastOutSlowInInterpolator()
            }
          }
          1 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = CameraAnimatorsFactory.CUBIC_BEZIER_INTERPOLATOR
            }
          }
          2 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = FastOutLinearInInterpolator()
            }
          }
          3 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = LinearOutSlowInInterpolator()
            }
          }
          4 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = BounceInterpolator()
            }
          }
          5 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = AnticipateInterpolator()
            }
          }
          6 -> {
            CameraAnimatorsFactory.setDefaultAnimatorOptions {
              duration = DEFAULT_ANIMATION_DURATION_MS
              interpolator = AnticipateOvershootInterpolator()
            }
          }
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_predefined_animators, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId != android.R.id.home) {
      resetCameraPosition()
      playAnimation(item.itemId)
    }
    return super.onOptionsItemSelected(item)
  }

  private fun resetCameraPosition() {
    mapboxMap.jumpTo(START_CAMERA_POSITION)
  }

  private fun stopAnimation() {
    cameraAnimationsPlugin.cancelAllAnimators()
    cameraAnimationsPlugin.unregisterAnimators(*currentAnimators)
  }

  private fun playAnimation(itemId: Int) {
    stopAnimation()
    when (itemId) {
      R.id.menu_action_jump_to -> mapboxMap.jumpTo(START_CAMERA_POSITION)
      R.id.menu_action_ease_to -> mapboxMap.easeTo(
        EASE_TO_TARGET_CAMERA_POSITION,
        mapAnimationOptions {
          duration = 2000
        }
      )
      R.id.menu_action_fly_to -> mapboxMap.flyTo(
        EASE_TO_TARGET_CAMERA_POSITION,
        mapAnimationOptions {
          duration = 2000
        }
      )
      R.id.menu_action_pitch_by -> mapboxMap.pitchBy(
        70.0,
        mapAnimationOptions {
          duration = 2000
        }
      )
      R.id.menu_action_scale_by -> mapboxMap.scaleBy(
        15.0,
        ScreenCoordinate(10.0, 10.0),
        mapAnimationOptions {
          duration = 2000
        }
      )
      R.id.menu_action_move_by -> mapboxMap.moveBy(
        ScreenCoordinate(500.0, 500.0),
        mapAnimationOptions {
          duration = 3000
        }
      )
      R.id.menu_action_rotate_by -> mapboxMap.rotateBy(
        ScreenCoordinate(0.0, 0.0),
        ScreenCoordinate(500.0, 500.0),
        mapAnimationOptions {
          duration = 7000
        }
      )
    }
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {

    private val START_CAMERA_POSITION = CameraOptions.Builder()
      .center(Point.fromLngLat(-0.11968, 51.50325)) // Sets the new camera position
      .zoom(15.0) // Sets the zoom
      .bearing(0.0) // Rotate the camera
      .pitch(0.0) // Set the camera pitch
      .build() // Creates a CameraPosition from the builder

    private val EASE_TO_TARGET_CAMERA_POSITION = CameraOptions.Builder()
      .center(Point.fromLngLat(-0.07520, 51.50550))
      .zoom(17.0)
      .bearing(180.0)
      .pitch(30.0)
      .build()
  }
}