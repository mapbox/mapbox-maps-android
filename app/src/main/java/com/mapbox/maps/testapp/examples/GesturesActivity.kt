package com.mapbox.maps.testapp.examples

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.android.gestures.*
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_gestures.*
import kotlinx.android.synthetic.main.activity_gestures.mapView
import java.util.*

/**
 * Test activity showcasing APIs around gestures implementation.
 */
class GesturesActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var gesturesPlugin: GesturesPlugin
  private lateinit var gesturesManager: AndroidGesturesManager
  private lateinit var gestureAlertsAdapter: GestureAlertsAdapter
  private var focalPointLatLng: Point? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_gestures)
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-0.11968, 51.50325))
        .zoom(15.0)
        .build()
    )
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)

    mapView.waitForLayout {
      initializeMap()
    }

    recycler.layoutManager = LinearLayoutManager(this)
    gestureAlertsAdapter = GestureAlertsAdapter()
    recycler.adapter = gestureAlertsAdapter
  }

  override fun onPause() {
    super.onPause()
    gestureAlertsAdapter.cancelUpdates()
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

  private fun initializeMap() {
    gesturesPlugin = mapView.gestures
    gesturesManager = gesturesPlugin.getGesturesManager()

    val layoutParams = recycler.layoutParams as RelativeLayout.LayoutParams
    layoutParams.height = (mapView.height / 1.75).toInt()
    layoutParams.width = mapView.width / 3
    recycler.layoutParams = layoutParams

    attachListeners()

    fixedFocalPointEnabled(mapboxMap.getGesturesSettings()?.focalPoint != null)
  }

  private fun attachListeners() {
    gesturesPlugin.addOnMoveListener(object : OnMoveListener {
      override fun onMoveBegin(@NonNull detector: MoveGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "MOVE START"))
      }

      override fun onMove(@NonNull detector: MoveGestureDetector): Boolean {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "MOVE PROGRESS"))
        return false
      }

      override fun onMoveEnd(@NonNull detector: MoveGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "MOVE END"))
        recalculateFocalPoint()
      }
    })

    gesturesPlugin.addOnRotateListener(object : OnRotateListener {
      override fun onRotateBegin(@NonNull detector: RotateGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "ROTATE START"))
      }

      override fun onRotate(@NonNull detector: RotateGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "ROTATE PROGRESS"))
        recalculateFocalPoint()
      }

      override fun onRotateEnd(@NonNull detector: RotateGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "ROTATE END"))
      }
    })

    gesturesPlugin.addOnScaleListener(object : OnScaleListener {
      override fun onScaleBegin(@NonNull detector: StandardScaleGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "SCALE START"))
        if (focalPointLatLng != null) {
          gestureAlertsAdapter.addAlert(
            GestureAlert(
              GestureAlert.TYPE_OTHER,
              "INCREASING MOVE THRESHOLD"
            )
          )
          gesturesManager.moveGestureDetector.moveThreshold = 175 * resources.displayMetrics.density

          gestureAlertsAdapter.addAlert(
            GestureAlert(
              GestureAlert.TYPE_OTHER,
              "MANUALLY INTERRUPTING MOVE"
            )
          )
          gesturesManager.moveGestureDetector.interrupt()
        }
        recalculateFocalPoint()
      }

      override fun onScale(@NonNull detector: StandardScaleGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "SCALE PROGRESS"))
      }

      override fun onScaleEnd(@NonNull detector: StandardScaleGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "SCALE END"))

        if (focalPointLatLng != null) {
          gestureAlertsAdapter.addAlert(
            GestureAlert(
              GestureAlert.TYPE_OTHER,
              "REVERTING MOVE THRESHOLD"
            )
          )
          gesturesManager.moveGestureDetector.moveThreshold = 0f
        }
      }
    })

    gesturesPlugin.addOnShoveListener(object : OnShoveListener {
      override fun onShoveBegin(@NonNull detector: ShoveGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "SHOVE START"))
      }

      override fun onShove(@NonNull detector: ShoveGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "SHOVE PROGRESS"))
      }

      override fun onShoveEnd(@NonNull detector: ShoveGestureDetector) {
        gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "SHOVE END"))
      }
    })
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_gestures, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_gesture_focus_point -> {
        fixedFocalPointEnabled(focalPointLatLng == null)
        item.isChecked = focalPointLatLng == null
      }
      R.id.menu_gesture_animation -> {
        gesturesPlugin.scaleVelocityAnimationEnabled =
          !gesturesPlugin.scaleVelocityAnimationEnabled
        gesturesPlugin.rotateVelocityAnimationEnabled =
          !gesturesPlugin.rotateVelocityAnimationEnabled
        gesturesPlugin.flingVelocityAnimationEnabled =
          !gesturesPlugin.flingVelocityAnimationEnabled
        item.isChecked = gesturesPlugin.scaleVelocityAnimationEnabled &&
          gesturesPlugin.rotateVelocityAnimationEnabled &&
          gesturesPlugin.flingVelocityAnimationEnabled
      }
      R.id.menu_gesture_rotate -> {
        gesturesPlugin.rotateEnabled = !gesturesPlugin.rotateEnabled
        item.isChecked = gesturesPlugin.rotateEnabled
      }
      R.id.menu_gesture_pitch -> {
        gesturesPlugin.pitchEnabled = !gesturesPlugin.pitchEnabled
        item.isChecked = gesturesPlugin.pitchEnabled
      }
      R.id.menu_gesture_zoom -> {
        gesturesPlugin.zoomEnabled = !gesturesPlugin.zoomEnabled
        item.isChecked = gesturesPlugin.zoomEnabled
      }
      R.id.menu_gesture_scroll -> {
        gesturesPlugin.scrollEnabled = !gesturesPlugin.scrollEnabled
        item.isChecked = gesturesPlugin.scrollEnabled
      }
      R.id.menu_gesture_double_tap -> {
        gesturesPlugin.doubleTapToZoomEnabled = !gesturesPlugin.doubleTapToZoomEnabled
        item.isChecked = gesturesPlugin.doubleTapToZoomEnabled
      }
      R.id.menu_gesture_quick_zoom -> {
        gesturesPlugin.quickZoomEnabled = !gesturesPlugin.quickZoomEnabled
        item.isChecked = gesturesPlugin.quickZoomEnabled
      }
    }
    return true
  }

  private fun fixedFocalPointEnabled(enabled: Boolean) {

    if (enabled) {
      focalPointLatLng = Point.fromLngLat(-0.12968, 51.50325)
      // TODO add marker
      // marker = mapboxMap.addMarker(MarkerOptions().position(focalPointLatLng))
      mapboxMap.setCamera(CameraOptions.Builder().center(focalPointLatLng).zoom(16.0).build())
      recalculateFocalPoint()
    } else {
      // TODO add marker
      // if (marker != null) {
      //    mapboxMap.removeMarker(marker)
      //    marker = null
      // }
      focalPointLatLng = null
      gesturesPlugin.focalPoint = null
    }
  }

  private fun recalculateFocalPoint() {
    focalPointLatLng?.let {
      gesturesPlugin.focalPoint = mapboxMap.pixelForCoordinate(it)
    }
  }

  private class GestureAlertsAdapter : RecyclerView.Adapter<GestureAlertsAdapter.ViewHolder>() {

    private var isUpdating: Boolean = false
    private val updateHandler = Handler()
    private val alerts = ArrayList<GestureAlert>()

    private val updateRunnable = Runnable {
      notifyDataSetChanged()
      isUpdating = false
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
      internal var alertMessageTv: TextView = view.findViewById(R.id.alert_message)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
      val view =
        LayoutInflater.from(parent.context).inflate(R.layout.item_gesture_alert, parent, false)
      return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
      val alert = alerts[position]
      holder.alertMessageTv.text = alert.message
      holder.alertMessageTv.setTextColor(
        ContextCompat.getColor(holder.alertMessageTv.context, alert.color)
      )
    }

    override fun getItemCount(): Int {
      return alerts.size
    }

    fun addAlert(alert: GestureAlert) {
      for (gestureAlert in alerts) {
        if (gestureAlert.alertType != GestureAlert.TYPE_PROGRESS) {
          break
        }

        if (alert.alertType == GestureAlert.TYPE_PROGRESS && gestureAlert == alert) {
          return
        }
      }

      if (itemCount >= MAX_NUMBER_OF_ALERTS) {
        alerts.removeAt(itemCount - 1)
      }

      alerts.add(0, alert)
      if (!isUpdating) {
        isUpdating = true
        updateHandler.postDelayed(updateRunnable, 250)
      }
    }

    fun cancelUpdates() {
      updateHandler.removeCallbacksAndMessages(null)
    }
  }

  private class GestureAlert(
    @param:Type @field:Type val alertType: Int,
    val message: String?
  ) {

    @ColorInt
    var color: Int = 0
      private set

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(TYPE_NONE, TYPE_START, TYPE_PROGRESS, TYPE_END, TYPE_OTHER)
    annotation class Type

    init {

      when (alertType) {
        TYPE_NONE -> color = android.R.color.black
        TYPE_END -> color = android.R.color.holo_red_dark
        TYPE_OTHER -> color = android.R.color.holo_purple
        TYPE_PROGRESS -> color = android.R.color.holo_orange_dark
        TYPE_START -> color = android.R.color.holo_green_dark
      }
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) {
        return true
      }
      if (other == null || javaClass != other.javaClass) {
        return false
      }

      val that = other as GestureAlert?

      if (alertType != that?.alertType) {
        return false
      }
      return if (message != null) message == that.message else that.message == null
    }

    override fun hashCode(): Int {
      var result = alertType
      result = 31 * result + (message?.hashCode() ?: 0)
      return result
    }

    companion object {
      internal const val TYPE_NONE = 0
      internal const val TYPE_START = 1
      internal const val TYPE_END = 2
      internal const val TYPE_PROGRESS = 3
      internal const val TYPE_OTHER = 4
    }
  }

  companion object {
    private const val MAX_NUMBER_OF_ALERTS = 30
  }
}

inline fun View.waitForLayout(crossinline f: () -> Unit) {
  viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
    override fun onGlobalLayout() {
      viewTreeObserver.removeOnGlobalLayoutListener(this)
      f()
    }
  })
}