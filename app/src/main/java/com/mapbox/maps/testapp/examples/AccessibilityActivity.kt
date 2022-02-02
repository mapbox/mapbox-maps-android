package com.mapbox.maps.testapp.examples

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import com.mapbox.bindgen.Expected
import com.mapbox.maps.*
import com.mapbox.maps.extension.observable.eventdata.CameraChangedEventData
import com.mapbox.maps.extension.observable.eventdata.MapIdleEventData
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.plugin.delegates.listeners.OnCameraChangeListener
import com.mapbox.maps.plugin.delegates.listeners.OnMapIdleListener
import com.mapbox.maps.testapp.databinding.ActivityAccessibilityBinding

class AccessibilityActivity: AppCompatActivity(), OnCameraChangeListener, OnMapIdleListener {
  private lateinit var mapView: MapView
  private lateinit var mapboxMap: MapboxMap

  private val mapViewDrawingRect by lazy {
    Rect().apply(mapView::getDrawingRect)
  }

  private var onScreenFeatures: MutableList<QueriedFeature>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityAccessibilityBinding.inflate(layoutInflater)
    setContentView(binding.root)
    mapView = binding.mapView

    ViewCompat.setAccessibilityDelegate(mapView, exploreByTouchHelper)

    mapboxMap = mapView.getMapboxMap()
    mapboxMap.addOnCameraChangeListener(this)
    mapboxMap.addOnMapIdleListener(this)
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
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

  override fun onMapIdle(eventData: MapIdleEventData) {
    mapboxMap.queryRenderedFeatures(
      RenderedQueryGeometry(
        ScreenBox(
          ScreenCoordinate(mapViewDrawingRect.left.toDouble(), mapViewDrawingRect.top.toDouble()),
          ScreenCoordinate(mapViewDrawingRect.right.toDouble(), mapViewDrawingRect.bottom.toDouble()),
        )
      ),
      RenderedQueryOptions(listOf(LAYER_ID), literal(true))
    ) { expected: Expected<String, MutableList<QueriedFeature>> ->
      if (!expected.isError) {
        onScreenFeatures = expected.value

        expected.value?.forEach {
          Log.d(TAG, "onCameraChanged: $it")
        }
      }
    }
  }

  override fun onCameraChanged(eventData: CameraChangedEventData) {
    onScreenFeatures = null
  }

  private val exploreByTouchHelper by lazy {
    object : ExploreByTouchHelper(mapView) {
      override fun getVirtualViewAt(x: Float, y: Float): Int {
        return 0
      }

      override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
        val featureCount = onScreenFeatures?.size ?: 0

        // maybe find a unique ID based off of lat,lng
        for (i in 0 until featureCount) {
          virtualViewIds.add(i)
        }
      }

      override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
      ) {
        onScreenFeatures?.get(virtualViewId)?.let {
          node.className = mapView::class.simpleName
          node.contentDescription = "${it.feature.getStringProperty("shield")} ${it.feature.getStringProperty("ref")}"
        }
      }

      override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
      ): Boolean {
        return false
      }

    }
  }

  companion object {
    const val TAG = "AccessibilityActivity"
    const val LAYER_ID = "road-number-shield"
  }
}