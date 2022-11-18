package com.mapbox.maps.plugin.locationcomponent

import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.*

/**
 * A custom location provider implementation that allows to play location updates at constant speed.
 */
@MapboxExperimental
class CustomJourneyLocationProvider : LocationProvider {
  private var locationConsumers = CopyOnWriteArraySet<LocationConsumer>()

  fun loadJourney(journey: Journey) {
    journey.observeJourneyUpdates { point, bearing, locationAnimationDurationMs, bearingAnimationDurationMs ->
      emitLocationUpdated(point, bearing, locationAnimationDurationMs, bearingAnimationDurationMs)
      true
    }
  }

  private fun emitLocationUpdated(
    location: Point,
    bearing: Double,
    locationAnimationDuration: Long,
    bearingAnimateDuration: Long,
  ) {
    locationConsumers.forEach {
      it.onBearingUpdated(bearing) {
        duration = bearingAnimateDuration
      }
      it.onLocationUpdated(location) {
        duration = locationAnimationDuration
        interpolator = LinearInterpolator()
      }
    }
  }

  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.add(locationConsumer)
  }

  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.remove(locationConsumer)
  }
}

@MapboxExperimental
class Journey(val speed: Double = 100.0, val angularSpeed: Double = 500.0) {
  private val locationList = CopyOnWriteArrayList<QueueData>()
  private val initialTimeStamp: Long = 0
  private val remainingPoints = ConcurrentLinkedQueue<QueueData>()
  private var isPlaying = false
  private val handler = Handler(Looper.getMainLooper())

  private val observers = CopyOnWriteArraySet<JourneyDataObserver>()

  /**
   * Return the remaining locations in the queue.
   */
  val remainingLocationsInQueue: List<Point>
    get() {
      with(remainingPoints) {
        return this.map { it.location }
      }
    }

  fun observeJourneyUpdates(observer: JourneyDataObserver) {
    observers.add(observer)
  }

  /**
   * Start the playback, any incoming location updates will be queued and played sequentially.
   */
  fun start() {
    isPlaying = true
    drainQueue()
  }

  /**
   * Cancel any ongoing playback, new incoming location updates will be queued but not played.
   */
  fun pause() {
    isPlaying = false
    handler.removeCallbacksAndMessages(null)
  }

  /**
   * Resume the remaining journey.
   */
  fun resume() {
    isPlaying = true
    drainQueue()
  }

  /**
   * Restart the journey.
   */
  fun restart() {
    remainingPoints.clear()
    remainingPoints.addAll(locationList)
    isPlaying = true
  }

  /**
   * Queue a new location update event to be played at constant speed.
   */
  fun queueLocationUpdate(
    location: Point
  ) {
    val bearing = locationList.lastOrNull()?.location?.let {
      bearing(it, location)
    } ?: 0.0
    val animationDurationMs = locationList.lastOrNull()?.location?.let {
      (distanceInMeter(it, location) / speed) * 1000.0
    } ?: 1000L
    val bearingAnimateDurationMs =
      abs(shortestRotation(bearing, locationList.lastOrNull()?.bearing ?: 0.0) / angularSpeed) * 1000.0

    val nextData =
      QueueData(location, bearing, animationDurationMs.toLong(), bearingAnimateDurationMs.toLong())
    locationList.add(nextData)
    remainingPoints.add(nextData)
    if (remainingPoints.size == 1 && isPlaying) {
      drainQueue()
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

  private fun drainQueue() {
    remainingPoints.peek()?.let { data ->
      observers.forEach {
        if (it.onNewData(
            data.location,
            data.bearing,
            data.locationAnimationDurationMs,
            data.bearingAnimateDurationMs
          )
        ) {
          if (isPlaying) {
            handler.postDelayed(
              {
                remainingPoints.poll()
                drainQueue()
              },
              max(data.locationAnimationDurationMs, data.bearingAnimateDurationMs)
            )
          }
        } else {
          observers.remove(it)
        }
      }
    }
  }

  private data class QueueData(
    val location: Point,
    val bearing: Double,
    val locationAnimationDurationMs: Long,
    val bearingAnimateDurationMs: Long
  )

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

    /**
     * Util for finding the shortest path from the current rotated degree to the new degree.
     *
     * @param targetHeading  the new position of the rotation
     * @param currentHeading the current position of the rotation
     * @return the shortest degree of rotation possible
     */
    fun shortestRotation(targetHeading: Double, currentHeading: Double): Double {
      val diff = currentHeading - targetHeading
      return when {
        diff > 180.0f -> {
          targetHeading + 360.0f
        }
        diff < -180.0f -> {
          targetHeading - 360.0f
        }
        else -> {
          targetHeading
        }
      }
    }
  }
}

fun interface JourneyDataObserver {
  /**
   * Notifies that new data is available.
   *
   * @param location the next location update.
   * @param bearing the bearing towards the next location update.
   * @param locationAnimationDurationMs maximum duration of the animation in ms.
   * @param bearingAnimateDurationMs
   *
   * @return true if new data is needed and stay subscribed. returning false will unsubscribe from further data updates.
   */
  fun onNewData(
    location: Point,
    bearing: Double,
    locationAnimationDurationMs: Long,
    bearingAnimateDurationMs: Long
  ): Boolean
}