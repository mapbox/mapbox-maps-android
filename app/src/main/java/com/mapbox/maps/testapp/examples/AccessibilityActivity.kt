package com.mapbox.maps.testapp.examples

import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import com.mapbox.bindgen.Expected
import com.mapbox.geojson.Point
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

  override fun onCameraChanged(eventData: CameraChangedEventData) {
    onScreenFeatures = null

    // TODO: Currently this implementation doesn't account for scrolling the camera, virtual
    //  view Ids likely needs to be cleared and re-instantiated, and focus reset.
  }

  override fun onMapIdle(eventData: MapIdleEventData) {
    mapboxMap.queryRenderedFeatures(
      RenderedQueryGeometry(
        ScreenBox(
          ScreenCoordinate(mapViewDrawingRect.left.toDouble(), mapViewDrawingRect.top.toDouble()),
          ScreenCoordinate(
            mapViewDrawingRect.right.toDouble(),
            mapViewDrawingRect.bottom.toDouble()
          ),
        )
      ),
      RenderedQueryOptions(listOf(LAYER_ID), literal(true))
    ) { expected: Expected<String, MutableList<QueriedFeature>> ->
      if (!expected.isError) {
        onScreenFeatures = expected.value
      }
    }
  }

  private val exploreByTouchHelper by lazy {
    object : ExploreByTouchHelper(mapView) {
      override fun getVirtualViewAt(x: Float, y: Float): Int {
        // TODO: implement
        return HOST_ID
      }

      override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
        val featureCount = onScreenFeatures?.size ?: 0

        // TODO: right now we're simply using the indices as rendered by queryRenderedFeatures,
        //  for true accessibility support, we might want to sort these by location within the
        //  map relative to the bearing, so the user can navigate through POIs in an order that
        //  makes sense (similar to a document)
        for (i in 0 until featureCount) {
          virtualViewIds.add(i)
        }
      }

      override fun onPopulateNodeForVirtualView(
        virtualViewId: Int,
        node: AccessibilityNodeInfoCompat
      ) {
        onScreenFeatures?.get(virtualViewId)?.let {
          val screenCoordinate = mapboxMap.pixelForCoordinate(it.feature.geometry() as Point)
          
          node.className = mapView::class.simpleName
          node.setParent(mapView)

          // TODO: account for screen density, might also be able to more accurately query map
          //  for the icon's rendered size, to fit bounding box
          node.setBoundsInParent(Rect().apply {
            top = screenCoordinate.y.toInt() - 25
            bottom = screenCoordinate.y.toInt() + 25
            right = screenCoordinate.x.toInt() + 25
            left = screenCoordinate.x.toInt() - 25
          })

          val description = getDescriptionFromShield(it)

          node.contentDescription = description
          node.text = description
        }
      }

      override fun onPerformActionForVirtualView(
        virtualViewId: Int,
        action: Int,
        arguments: Bundle?
      ): Boolean {
        // do nothing
        // TODO: eventually do something, potentially we could issue a fly to to center the map,
        //  with would then move some POIs off screen, and new POIs onto the screen. so we will
        //  want to account for this new order of accessibility nodes.
        return false
      }

    }
  }

  // this logic is localized to the united states, because it's querying these shields
  // we don't have access to road names, potentially you would qRF roads as well, to look up
  // full human friendly names, etc.
  private fun getDescriptionFromShield(queriedFeature: QueriedFeature): String {
    var result = ""

    val shield = queriedFeature.feature.getStringProperty("shield")
    val ref = queriedFeature.feature.getStringProperty("ref")

    when (shield) {
      "us-interstate" -> result = "interstate"
      "us-highway" -> result = "highway"
    }

    result += " $ref"

    return result
  }

  companion object {
    const val TAG = "AccessibilityActivity"
    const val LAYER_ID = "road-number-shield"
  }
}