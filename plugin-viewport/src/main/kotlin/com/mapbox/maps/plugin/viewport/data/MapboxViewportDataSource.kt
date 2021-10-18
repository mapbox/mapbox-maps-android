package com.mapbox.maps.plugin.viewport.data

import android.location.Location
import androidx.annotation.UiThread
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.viewport.data.ViewportDataSourceProcessor.getMapAnchoredPaddingFromUserPadding
import com.mapbox.maps.plugin.viewport.data.ViewportDataSourceProcessor.getScreenBoxForFraming
import com.mapbox.maps.plugin.viewport.data.ViewportDataSourceProcessor.getSmootherBearingForMap
import com.mapbox.maps.plugin.viewport.data.ViewportProperty.BearingProperty
import com.mapbox.maps.plugin.viewport.data.ViewportProperty.CenterProperty
import com.mapbox.maps.plugin.viewport.data.ViewportProperty.PitchProperty
import com.mapbox.maps.plugin.viewport.data.ViewportProperty.ZoomProperty
import com.mapbox.maps.plugin.viewport.transition.normalizeBearing
import com.mapbox.maps.toCameraOptions
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.max
import kotlin.math.min

@UiThread
class MapboxViewportDataSource(
  delegateProvider: MapDelegateProvider
) : ViewportDataSource {

  private val cameraManager: MapCameraManagerDelegate = delegateProvider.mapCameraManagerDelegate
  private val mapTransform: MapTransformDelegate = delegateProvider.mapTransformDelegate

  /**
   * Holds options that impact generation of camera frames.
   *
   * You can freely mutate these options and results will be applied when the next frame is evaluated.
   */
  val options = MapboxViewportDataSourceOptions()

  private val followingCenterProperty = CenterProperty(null, NULL_ISLAND_POINT)
  private val followingZoomProperty = ZoomProperty(null, options.followingFrameOptions.maxZoom)
  private val followingBearingProperty = BearingProperty(null, BEARING_NORTH)
  private val followingPitchProperty =
    PitchProperty(null, options.followingFrameOptions.defaultPitch)
  private val overviewCenterProperty = CenterProperty(null, NULL_ISLAND_POINT)
  private val overviewZoomProperty = ZoomProperty(null, options.overviewFrameOptions.maxZoom)
  private val overviewBearingProperty = BearingProperty(null, BEARING_NORTH)
  private val overviewPitchProperty = PitchProperty(null, ZERO_PITCH)

  private val viewportDataSourceUpdateObservers =
    CopyOnWriteArraySet<ViewportDataSourceUpdateObserver>()

  var overviewPadding: EdgeInsets = EMPTY_EDGE_INSETS
  var followingPadding: EdgeInsets = EMPTY_EDGE_INSETS
  private var appliedFollowingPadding: EdgeInsets = followingPadding
  private var targetLocation: Location? = null

  private var followingCameraOptions = CameraOptions.Builder().build()
  private var overviewCameraOptions = CameraOptions.Builder().build()

  private var viewportData: ViewportData = ViewportData(
    cameraForFollowing = CameraOptions.Builder()
      .center(followingCenterProperty.get())
      .zoom(followingZoomProperty.get())
      .bearing(followingBearingProperty.get())
      .pitch(followingPitchProperty.get())
      .padding(appliedFollowingPadding)
      .build(),
    cameraForOverview = CameraOptions.Builder()
      .center(overviewCenterProperty.get())
      .zoom(overviewZoomProperty.get())
      .bearing(overviewBearingProperty.get())
      .pitch(overviewPitchProperty.get())
      .padding(overviewPadding)
      .build()
  )
    set(value) {
      if (value != field) {
        field = value
        viewportDataSourceUpdateObservers.forEach {
          it.viewportDataSourceUpdated(value)
        }
      }
    }

  override fun getViewportData(): ViewportData = viewportData

  override fun registerUpdateObserver(
    viewportDataSourceUpdateObserver: ViewportDataSourceUpdateObserver
  ) {
    viewportDataSourceUpdateObservers.add(viewportDataSourceUpdateObserver)
    viewportDataSourceUpdateObserver.viewportDataSourceUpdated(viewportData)
  }

  override fun unregisterUpdateObserver(
    viewportDataSourceUpdateObserver: ViewportDataSourceUpdateObserver
  ) {
    viewportDataSourceUpdateObservers.remove(viewportDataSourceUpdateObserver)
  }

  /**
   * Call whenever new user location is available.
   *
   * @see [evaluate]
   */
  fun onLocationChanged(location: Location) {
    targetLocation = location
  }

  /**
   * Computes [ViewportData] based on the available data, saves the value,
   * and notifies the [ViewportDataSourceUpdateObserver]s.
   *
   * @see [registerUpdateObserver]
   * @see [getViewportData]
   */
  fun evaluate() {
    updateFollowingData()
    updateOverviewData()

    options.followingFrameOptions.run {
      followingCameraOptions = CameraOptions.Builder().apply {
        if (centerUpdatesAllowed) center(followingCenterProperty.get())
        if (zoomUpdatesAllowed) zoom(followingZoomProperty.get())
        if (bearingUpdatesAllowed) bearing(followingBearingProperty.get())
        if (pitchUpdatesAllowed) pitch(followingPitchProperty.get())
        if (paddingUpdatesAllowed) padding(appliedFollowingPadding)
      }.build()
    }

    options.overviewFrameOptions.run {
      overviewCameraOptions = CameraOptions.Builder().apply {
        if (centerUpdatesAllowed) center(overviewCenterProperty.get())
        if (zoomUpdatesAllowed) zoom(overviewZoomProperty.get())
        if (bearingUpdatesAllowed) bearing(overviewBearingProperty.get())
        if (pitchUpdatesAllowed) pitch(overviewPitchProperty.get())
        if (paddingUpdatesAllowed) padding(overviewPadding)
      }.build()
    }

    viewportData = ViewportData(
      cameraForFollowing = followingCameraOptions,
      cameraForOverview = overviewCameraOptions
    )
  }

  private fun updateFollowingData() {
    targetLocation?.let { nonNullTargetLocation ->
      val pointsForFollowing = listOf(nonNullTargetLocation.toPoint())

      options.followingFrameOptions.bearingSmoothing.run {
        val locationBearing = nonNullTargetLocation.bearing.toDouble() ?: BEARING_NORTH
        followingBearingProperty.fallback =
          getSmootherBearingForMap(enabled, cameraManager.cameraState.bearing, locationBearing)
      }

      val cameraFrame = if (pointsForFollowing.size > 1 &&
        options.followingFrameOptions.maximizeViewableGeometryWhenPitchZero &&
        followingPitchProperty.get() == ZERO_PITCH
      ) {
        cameraManager.cameraForCoordinates(
          pointsForFollowing,
          followingPadding,
          followingBearingProperty.get(),
          followingPitchProperty.get()
        )
      } else {
        val mapSize = mapTransform.getSize()
        val screenBox = getScreenBoxForFraming(mapSize, followingPadding)
        val cameraState = cameraManager.cameraState
        val fallbackCameraOptions = CameraOptions.Builder()
          .center(pointsForFollowing.firstOrNull() ?: cameraState.center)
          .padding(getMapAnchoredPaddingFromUserPadding(mapSize, followingPadding))
          .bearing(followingBearingProperty.get())
          .pitch(followingPitchProperty.get())
          .zoom(cameraState.zoom)
          .build()
        if (pointsForFollowing.size > 1) {
          cameraManager.cameraForCoordinates(
            pointsForFollowing,
            fallbackCameraOptions,
            screenBox
          )
        } else {
          fallbackCameraOptions
        }
      }

      followingCenterProperty.fallback = cameraFrame.center!!
      options.followingFrameOptions.run {
        followingZoomProperty.fallback = max(min(cameraFrame.zoom!!, maxZoom), minZoom)
      }
      appliedFollowingPadding = cameraFrame.padding!!
      return
    }
    // nothing to frame
    options.followingFrameOptions.run {
      val cameraState = cameraManager.cameraState
      followingBearingProperty.fallback = cameraState.bearing
      followingPitchProperty.fallback = defaultPitch
      followingCenterProperty.fallback = cameraState.center
      followingZoomProperty.fallback = max(min(cameraState.zoom, maxZoom), minZoom)
    }
  }

  private fun updateOverviewData() {
    targetLocation?.let { nonNullTargetLocation ->
      val pointsForOverview = listOf(nonNullTargetLocation.toPoint())

      overviewBearingProperty.fallback = normalizeBearing(
        cameraManager.cameraState.bearing,
        BEARING_NORTH
      )

      val cameraFrame = if (pointsForOverview.isNotEmpty()) {
        cameraManager.cameraForCoordinates(
          pointsForOverview,
          overviewPadding,
          overviewBearingProperty.get(),
          overviewPitchProperty.get()
        )
      } else {
        cameraManager.cameraState.toCameraOptions()
      }

      overviewCenterProperty.fallback = cameraFrame.center!!
      overviewZoomProperty.fallback = min(
        cameraFrame.zoom!!,
        options.overviewFrameOptions.maxZoom
      )
      return
    }
    // nothing to frame
    options.overviewFrameOptions.run {
      val cameraState = cameraManager.cameraState
      overviewBearingProperty.fallback = cameraState.bearing
      overviewPitchProperty.fallback = cameraState.pitch
      overviewCenterProperty.fallback = cameraState.center
      overviewZoomProperty.fallback = min(cameraState.zoom, maxZoom)
    }
  }

  private fun Location.toPoint(): Point = Point.fromLngLat(longitude, latitude)

  internal companion object {
    private const val TAG = "MbxViewportDataSource"
    internal val NULL_ISLAND_POINT = Point.fromLngLat(0.0, 0.0)
    internal val EMPTY_EDGE_INSETS = EdgeInsets(0.0, 0.0, 0.0, 0.0)
    internal const val ZERO_PITCH = 0.0
    internal const val BEARING_NORTH = 0.0
  }
}