package com.mapbox.maps.plugin.overlay

import android.view.View
import androidx.annotation.RestrictTo
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.delegates.*
import java.util.*

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class MapOverlayPluginImpl : MapOverlayPlugin {
  private val mapOverlays = mutableListOf<View>()
  private var mapOverlayCoordinatesProvider: MapOverlayCoordinatesProvider? = null
  internal var width: Int = 0
  internal var height: Int = 0
  private var marginLeft: Int = 0
  private var marginTop: Int = 0
  private var marginRight: Int = 0
  private var marginBottom: Int = 0
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate

  /**
   * Invoked when MapView's width and height have changed.
   * @param width the width of mapView
   * @param height the height of mapView
   */
  override fun onSizeChanged(width: Int, height: Int) {
    this.width = width
    this.height = height
  }

  /**
   * To register a MapOverlayCoordinatesProvider instance to the Container
   */
  override fun registerMapOverlayCoordinatesProvider(provider: MapOverlayCoordinatesProvider) {
    mapOverlayCoordinatesProvider = provider
  }

  /**
   * Unregister the MapOverlayCoordinatesProvider.
   */
  override fun unregisterMapOverlayCoordinatesProvider() {
    mapOverlayCoordinatesProvider = null
  }

  /**
   * Register a MapOverlay instance.
   * @param overlay the registered overlay view
   */
  override fun registerOverlay(overlay: View) {
    mapOverlays.add(overlay)
  }

  /**
   * Register views as overlays
   * @param overlays the registered overlay view
   */
  override fun registerOverlays(overlays: List<View>) {
    mapOverlays.addAll(overlays)
  }

  /**
   * Unregister a MapOverlay instance.
   * @param overlay the unregistered overlay view
   */
  override fun unregisterOverlay(overlay: View) {
    mapOverlays.remove(overlay)
  }

  /**
   * Unregister views.
   * @param overlays the unregistered overlay views
   */
  override fun unregisterOverlays(overlays: List<View>) {
    overlays.forEach {
      mapOverlays.remove(it)
    }
  }

  /**
   * Set the margins for the area that is displaying POIs within the overlay bounds.
   * These margins provide offsets to ensure POIs near the border of the overlay not be hidden or covered.
   * @param marginLeft the margin on the left, in pixel
   * @param marginTop the margin on the top, in pixel
   * @param marginRight the margin on the right, in pixel
   * @param marginBottom the margin on the bottom, in pixel
   */
  override fun setDisplayingAreaMargins(
    marginTop: Int,
    marginLeft: Int,
    marginBottom: Int,
    marginRight: Int
  ) {
    this.marginLeft = marginLeft
    this.marginTop = marginTop
    this.marginRight = marginRight
    this.marginBottom = marginBottom
  }

  /**
   * Reframe MapView to a certain zoom and position to make sure every coordinate will be shown
   * on the MapView and not be covered by registered MapOverlays.
   * If no onAnimateReframe is provided, MapView will jump to the new CameraOptions directly;
   * if onAnimateReframe is provided, the new CameraOptions will be return and users can define their
   * own animation to move the camera.
   *
   * @param onReframeFinished the listener to get the CameraOptions
   */
  override fun reframe(onReframeFinished: OnReframeFinished?) {
    reframe { camera ->
      if (onReframeFinished != null) {
        onReframeFinished.onReframeFinished(camera)
      } else {
        camera?.let {
          mapCameraManagerDelegate.setCamera(it)
        }
      }
    }
  }

  /**
   * Get the CameraOptions that make sure every coordinate will be shown
   * on the MapView and not be covered by registered MapOverlays.
   * Users can use their own animation to move the camera with this CameraOptions.
   *
   * @param result callback with CameraOptions that MapView should animate to.
   * Will be null if MapOverlayCoordinatesProvider is not provided.
   */
  private fun reframe(result: (CameraOptions?) -> Unit) {
    mapOverlayCoordinatesProvider?.let {
      val coordinates = it.getShownCoordinates()
      var north = -90.0
      var south = 90.0
      var west = 180.0
      var east = -180.0
      coordinates.forEach { point ->
        north = maxOf(north, point.latitude())
        south = minOf(south, point.latitude())
        west = minOf(west, point.longitude())
        east = maxOf(east, point.longitude())
      }

      mapCameraManagerDelegate.cameraForCoordinates(
        coordinates = listOf(Point.fromLngLat(west, south), Point.fromLngLat(east, north)),
        camera = cameraOptions { },
        coordinatesPadding = getEdgeInsets(),
        maxZoom = null,
        offset = null,
        result
      )
    } ?: run {
      result.invoke(null)
    }
  }

  private fun getMapOverLayRect(view: View): MapOverLayRect {
    return MapOverLayRect(view.left, view.top, view.right, view.bottom)
  }

  /**
   * Get an EdgeInsets that represent the width and height of overlays
   */
  override fun getEdgeInsets(): EdgeInsets {
    val queue = LinkedList<MapOverLayRect>()
    queue.push(MapOverLayRect(0, 0, width, height))

    // BFS find all the possible rectangles
    mapOverlays.map {
      getMapOverLayRect(it)
    }.forEach {
      var size = queue.size
      while (size > 0) {
        val subMapRect = queue.pollFirst()!!
        if (it.isOverLap(subMapRect)) {
          // insert the new subMapRect
          if (it.top - subMapRect.top < subMapRect.bottom - it.bottom) {
            // overlay rect is on the top part of MapRect, create a new MapRect with the bottom part
            queue.offerLast(subMapRect.updateTop(it.bottom))
          } else if (it.top - subMapRect.top > subMapRect.bottom - it.bottom) {
            // overlay rect is on the bottom part of MapRect, create a new MapRect with the top part
            queue.offerLast(subMapRect.updateBottom(it.top))
          } else {
            // overlay rect is on the center of MapRect, create a new MapRect with both the top part and bottom part
            queue.offerLast(subMapRect.updateTop(it.bottom))
            queue.offerLast(subMapRect.updateBottom(it.top))
          }

          if (it.left - subMapRect.left < subMapRect.right - it.right) {
            // overlay rect is on the left part of MapRect, create a new MapRect with the right part
            queue.offerLast(subMapRect.updateLeft(it.right))
          } else if (it.left - subMapRect.left < subMapRect.right - it.right) {
            // overlay rect is on the right part of MapRect, create a new MapRect with the left part
            queue.offerLast(subMapRect.updateRight(it.left))
          } else {
            // overlay rect is on the center part of MapRect, create a new MapRect with both the left and the right part
            queue.offerLast(subMapRect.updateLeft(it.right))
            queue.offerLast(subMapRect.updateRight(it.left))
          }
        } else {
          queue.offerLast(subMapRect)
        }
        size--
      }
    }

    // Choose the one with max area
    val mapRect = queue.maxOrNull()

    return if (mapRect == null) {
      EdgeInsets(
        marginTop.toDouble(), marginLeft.toDouble(), marginBottom.toDouble(),
        marginRight.toDouble()
      )
    } else {
      EdgeInsets(
        mapRect.top.toDouble() + marginTop,
        mapRect.left.toDouble() + marginLeft,
        (height - mapRect.bottom + marginBottom).toDouble(),
        (width - mapRect.right + marginRight).toDouble()
      )
    }
  }

  /**
   * Class that represents the rectangle of MapOverlays on MapView
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY)
  internal class MapOverLayRect(
    var left: Int,
    var top: Int,
    var right: Int,
    var bottom: Int
  ) : Comparable<MapOverLayRect> {

    /**
     * Check whether two rectangles are overlap
     *
     * @return true if they are overlap
     */
    fun isOverLap(reactAnother: MapOverLayRect): Boolean {
      return !(
        right <= reactAnother.left ||
          bottom <= reactAnother.top ||
          left >= reactAnother.right ||
          top >= reactAnother.bottom
        )
    }

    fun updateTop(newTop: Int): MapOverLayRect {
      return MapOverLayRect(left, newTop, right, bottom)
    }

    fun updateLeft(newLeft: Int): MapOverLayRect {
      return MapOverLayRect(newLeft, top, right, bottom)
    }

    fun updateRight(newRight: Int): MapOverLayRect {
      return MapOverLayRect(left, top, newRight, bottom)
    }

    fun updateBottom(newBottom: Int): MapOverLayRect {
      return MapOverLayRect(left, top, right, newBottom)
    }

    override fun compareTo(other: MapOverLayRect): Int {
      return (((right - left) * (bottom - top)) - ((other.right - other.left) * (other.bottom - other.top)))
    }
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    unregisterMapOverlayCoordinatesProvider()
    mapOverlays.clear()
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
  }
}