package com.mapbox.maps.plugin.locationcomponent

import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.*

/**
 * A custom location provider implementation that allows to play location updates at constant speed.
 */
@MapboxExperimental
class CustomLocationProvider : LocationProvider {
  private var locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
  private var bearingAnimateDuration = 100L
  private var isPlaying = false
  private val handler = Handler(Looper.getMainLooper())
  private val locationList = ConcurrentLinkedQueue<Triple<Point, Double, Long>>()
  private var lastLocation: Point? = null

  /**
   * Playback speed in m/s.
   */
  var speed = 60.0

  /**
   * Return the remaining locations in the queue.
   */
  val remainingLocationsInQueue: List<Point>
    get() {
      with(locationList) {
        return this.map { it.first }
      }
    }


  /**
   * Queue a list of geo locations to be played at constant speed.
   */
  fun queueLocationUpdates(locations: List<Point>) {
    locations.forEach {
      queueLocationUpdate(it)
    }
  }

  /**
   * Queue a new location update event to be played at constant speed.
   */
  fun queueLocationUpdate(
    location: Point
  ) {
    val bearing = (locationList.lastOrNull()?.first ?: lastLocation)?.let {
      bearing(it, location)
    } ?: 0.0
    val animationDurationMs = (locationList.lastOrNull()?.first ?: lastLocation)?.let {
      (distanceInMeter(it, location) / speed) * 1000.0
    } ?: 1000L
    locationList.add(Triple(location, bearing, animationDurationMs.toLong()))
    if (locationList.size == 1 && isPlaying) {
      drainQueue()
    }
  }

  /**
   * Start the playback, any incoming location updates will be queued and played sequentially.
   */
  fun startPlayback() {
    isPlaying = true
  }

  /**
   * Cancel any ongoing playback, new incoming location updates will be queued but not played.
   */
  fun pausePlayback() {
    isPlaying = false
    handler.removeCallbacksAndMessages(null)
  }

  /**
   * Cancel any ongoing playback and clear remaining location queue.
   * New incoming location updates will be queued but not played.
   */
  fun cancelPlayback() {
    isPlaying = false
    locationList.clear()
    handler.removeCallbacksAndMessages(null)
  }

  private fun drainQueue() {
    locationList.peek()?.let {
      emitLocationUpdated(it.first, it.second, it.third) {
        lastLocation = locationList.poll()?.first
        drainQueue()
      }
    }
  }

  private fun emitLocationUpdated(
    location: Point,
    bearing: Double,
    animationDuration: Long,
    finished: () -> Unit
  ) {
    locationConsumers.forEach {
      it.onBearingUpdated(bearing) {
        duration = bearingAnimateDuration
      }
      it.onLocationUpdated(location) {
        duration = animationDuration
        interpolator = LinearInterpolator()
      }
    }
    handler.postDelayed(finished, animationDuration)
  }

  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.add(locationConsumer)
  }

  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.remove(locationConsumer)
  }

  private companion object {
    /**
     * Takes two [Point] and finds the geographic bearing between them.
     *
     * @param point1 first point used for calculating the bearing
     * @param point2 second point used for calculating the bearing
     * @return bearing in decimal degrees
     */
    fun bearing(point1: Point, point2: Point): Double {
      val lon1: Double = degreesToRadians(point1.longitude())
      val lon2: Double = degreesToRadians(point2.longitude())
      val lat1: Double = degreesToRadians(point1.latitude())
      val lat2: Double = degreesToRadians(point2.latitude())
      val value1 = sin(lon2 - lon1) * cos(lat2)
      val value2 = cos(lat1) * sin(lat2) - (sin(lat1) * cos(lat2) * cos(lon2 - lon1))
      return radiansToDegrees(atan2(value1, value2))
    }

    fun radiansToDegrees(radians: Double): Double {
      val degrees = radians % (2 * Math.PI)
      return degrees * 180 / Math.PI
    }

    fun degreesToRadians(degrees: Double): Double {
      val radians = degrees % 360
      return radians * Math.PI / 180
    }

    fun distanceInMeter(point1: Point, point2: Point): Double {
      val radius = 6370000.0
      val lat = degreesToRadians(point2.latitude() - point1.latitude())
      val lon = degreesToRadians(point2.longitude() - point1.longitude())
      val a = sin(lat / 2) * sin(lat / 2) + cos(degreesToRadians(point1.latitude())) * cos(
        degreesToRadians(point2.latitude())
      ) * sin(lon / 2) * sin(lon / 2)
      val c = 2 * atan2(sqrt(a), sqrt(1 - a))
      return abs(radius * c)
    }
  }
}