package com.mapbox.maps.testapp.examples

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMapRecorder
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.logI
import com.mapbox.maps.mapPlayerOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.gestures.gestures

/**
 * Showcase how to use [MapboxMapRecorder] to record and replay sessions.
 */
@OptIn(MapboxExperimental::class)
class MapboxMapRecorderActivity : AppCompatActivity() {

  private var activityStopped = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mapView = MapView(this)
    setContentView(mapView)

    // do not allow panning the map in this activity
    mapView.gestures.scrollEnabled = false
    val mapboxMap = mapView.mapboxMap
    // Make the ``MapboxMapRecorder`` and start the recording
    val recorder = mapboxMap.createRecorder()
    recorder.startRecording()
    mapboxMap.loadStyle(Style.STANDARD) {
      // Build a new set of CameraOptions for the map to fly to
      val cameraOptions = cameraOptions {
        center(Point.fromLngLat(-73.581, 45.4588))
        zoom(11.0)
        pitch(35.0)
      }
      mapboxMap.flyTo(
        cameraOptions,
        mapAnimationOptions { duration(10_000L) },
        object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            // When the camera animation is complete, stop the map recording
            val recording = recorder.stopRecording()
            if (activityStopped) {
              return
            }
            // Replay the camera animation twice at double speed by passing the recorded sequence returned from the stop method
            logI(TAG, "Replay started")
            recorder.replay(
              recording,
              mapPlayerOptions {
                playbackCount(2)
                playbackSpeedMultiplier(2.0)
                avoidPlaybackPauses(false)
              }
            ) {
              logI(TAG, "Replay ended, state = ${recorder.getPlaybackState()}")
            }
          }
        }
      )
    }
  }

  override fun onStop() {
    activityStopped = true
    // super.onStop will cancel the animation above and call onAnimationEnd
    super.onStop()
  }

  private companion object {
    private const val TAG = "MapboxMapRecorderActivity"
  }
}