package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
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
import com.mapbox.maps.plugin.ScrollMode
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityGesturesBinding
import java.util.*

/**
 * Test activity showcasing APIs around gestures implementation.
 */
class GesturesActivity : AppCompatActivity() {

  private lateinit var mapboxMap: MapboxMap
  private lateinit var gesturesPlugin: GesturesPlugin
  private lateinit var gesturesManager: AndroidGesturesManager
  private val gestureAlertsAdapter: GestureAlertsAdapter = GestureAlertsAdapter()
  private var focalPointLatLng: Point? = null
  private var pointAnnotationManager: PointAnnotationManager? = null
  private lateinit var binding: ActivityGesturesBinding
  private val rotateListener: OnRotateListener = object : OnRotateListener {
    override fun onRotateBegin(detector: RotateGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "ROTATE START"))
    }

    override fun onRotate(detector: RotateGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "ROTATE PROGRESS"))
      recalculateFocalPoint()
    }

    override fun onRotateEnd(detector: RotateGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "ROTATE END"))
    }
  }
  private val moveListener: OnMoveListener = object : OnMoveListener {
    override fun onMoveBegin(detector: MoveGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "MOVE START"))
    }

    override fun onMove(detector: MoveGestureDetector): Boolean {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "MOVE PROGRESS"))
      return false
    }

    override fun onMoveEnd(detector: MoveGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "MOVE END"))
      recalculateFocalPoint()
    }
  }
  private val scaleListener: OnScaleListener = object : OnScaleListener {
    override fun onScaleBegin(detector: StandardScaleGestureDetector) {
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

    override fun onScale(detector: StandardScaleGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "SCALE PROGRESS"))
    }

    override fun onScaleEnd(detector: StandardScaleGestureDetector) {
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
  }
  private val shoveListener: OnShoveListener = object : OnShoveListener {
    override fun onShoveBegin(detector: ShoveGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_START, "SHOVE START"))
    }

    override fun onShove(detector: ShoveGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_PROGRESS, "SHOVE PROGRESS"))
    }

    override fun onShoveEnd(detector: ShoveGestureDetector) {
      gestureAlertsAdapter.addAlert(GestureAlert(GestureAlert.TYPE_END, "SHOVE END"))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGesturesBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.setCamera(
      CameraOptions.Builder()
        .center(Point.fromLngLat(-0.11968, 51.50325))
        .zoom(15.0)
        .build()
    )
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) {
      it.addImage(MARKER_IMAGE_ID, BitmapFactory.decodeResource(resources, R.drawable.red_marker))
    }

    binding.mapView.waitForLayout {
      initializeMap()
    }

    binding.recycler.layoutManager = LinearLayoutManager(this)
    binding.recycler.adapter = gestureAlertsAdapter
  }

  override fun onPause() {
    super.onPause()
    gestureAlertsAdapter.cancelUpdates()
  }

  override fun onDestroy() {
    gesturesPlugin.removeOnMoveListener(moveListener)
    gesturesPlugin.removeOnRotateListener(rotateListener)
    gesturesPlugin.removeOnScaleListener(scaleListener)
    gesturesPlugin.removeOnShoveListener(shoveListener)
    super.onDestroy()
  }

  private fun initializeMap() {
    gesturesPlugin = binding.mapView.gestures
    gesturesManager = gesturesPlugin.getGesturesManager()

    val layoutParams = binding.recycler.layoutParams as RelativeLayout.LayoutParams
    layoutParams.height = (binding.mapView.height / 1.75).toInt()
    layoutParams.width = binding.mapView.width / 3
    binding.recycler.layoutParams = layoutParams

    attachListeners()

    fixedFocalPointEnabled(gesturesPlugin.getSettings().focalPoint != null)
  }

  private fun attachListeners() {
    gesturesPlugin.addOnMoveListener(moveListener)
    gesturesPlugin.addOnRotateListener(rotateListener)
    gesturesPlugin.addOnScaleListener(scaleListener)
    gesturesPlugin.addOnShoveListener(shoveListener)
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
        gesturesPlugin.pinchToZoomDecelerationEnabled =
          !gesturesPlugin.pinchToZoomDecelerationEnabled
        gesturesPlugin.rotateDecelerationEnabled =
          !gesturesPlugin.rotateDecelerationEnabled
        gesturesPlugin.scrollDecelerationEnabled =
          !gesturesPlugin.scrollDecelerationEnabled
        item.isChecked = gesturesPlugin.pinchToZoomDecelerationEnabled &&
          gesturesPlugin.rotateDecelerationEnabled &&
          gesturesPlugin.scrollDecelerationEnabled
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
        gesturesPlugin.pinchToZoomEnabled = !gesturesPlugin.pinchToZoomEnabled
        item.isChecked = gesturesPlugin.pinchToZoomEnabled
      }
      R.id.menu_gesture_scroll -> {
        gesturesPlugin.scrollEnabled = !gesturesPlugin.scrollEnabled
        item.isChecked = gesturesPlugin.scrollEnabled
      }
      R.id.menu_gesture_double_tap -> {
        gesturesPlugin.doubleTapToZoomInEnabled = !gesturesPlugin.doubleTapToZoomInEnabled
        item.isChecked = gesturesPlugin.doubleTapToZoomInEnabled
      }
      R.id.menu_gesture_double_touch -> {
        gesturesPlugin.doubleTouchToZoomOutEnabled = !gesturesPlugin.doubleTouchToZoomOutEnabled
        item.isChecked = gesturesPlugin.doubleTouchToZoomOutEnabled
      }
      R.id.menu_gesture_quick_zoom -> {
        gesturesPlugin.quickZoomEnabled = !gesturesPlugin.quickZoomEnabled
        item.isChecked = gesturesPlugin.quickZoomEnabled
      }
      R.id.menu_gesture_pan_scroll_horizontal_vertical -> {
        binding.mapView.gestures.updateSettings {
          scrollMode = ScrollMode.HORIZONTAL_AND_VERTICAL
        }
        item.isChecked = true
      }
      R.id.menu_gesture_pan_scroll_horizontal -> {
        binding.mapView.gestures.updateSettings {
          scrollMode = ScrollMode.HORIZONTAL
        }
        item.isChecked = true
      }
      R.id.menu_gesture_pan_scroll_vertical -> {
        binding.mapView.gestures.updateSettings {
          scrollMode = ScrollMode.VERTICAL
        }
        item.isChecked = true
      }
      else -> {
        return super.onOptionsItemSelected(item)
      }
    }
    return true
  }

  private fun fixedFocalPointEnabled(enabled: Boolean) {

    if (enabled) {
      focalPointLatLng = FOCAL_POINT
      pointAnnotationManager =
        binding.mapView.annotations.createPointAnnotationManager().apply {
          create(
            PointAnnotationOptions()
              .withPoint(FOCAL_POINT)
              .withIconImage(MARKER_IMAGE_ID)
          )
        }
      mapboxMap.setCamera(CameraOptions.Builder().center(focalPointLatLng).zoom(16.0).build())
      recalculateFocalPoint()
    } else {
      pointAnnotationManager?.let {
        binding.mapView.annotations.removeAnnotationManager(it)
      }
      pointAnnotationManager = null
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

    @SuppressLint("NotifyDataSetChanged")
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

  @SuppressLint("ResourceAsColor")
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
    private const val MARKER_IMAGE_ID = "MARKER_IMAGE_ID"
    private val FOCAL_POINT = Point.fromLngLat(-0.12968, 51.50325)
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