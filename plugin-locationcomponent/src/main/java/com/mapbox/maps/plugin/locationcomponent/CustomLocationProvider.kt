package com.mapbox.maps.plugin.locationcomponent

import android.animation.ValueAnimator
import android.icu.util.MeasureUnit
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.LinearInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.LocationPuck3D
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.math.*

/**
 * A custom location provider implementation that allows to play location updates at constant speed.
 */
@MapboxExperimental class Journey: LocationProvider, OnIndicatorPositionChangedListener {
  companion object {
    val TICK_DURATION: Long = 500
    val DEFAULT_SPEED: Speed = Speed.CAR
  }
  @MapboxExperimental data class Speed(
  val linear: Double = 60.0,
  val angular: Double = Double.MAX_VALUE) {
    companion object {
      val CAR: Speed by lazy { Speed(15.0, Double.MAX_VALUE) }
      val BIKE: Speed by lazy { Speed(5.0, Double.MAX_VALUE) }
      val ELECTRIC_SCOOTER: Speed by lazy { Speed(7.0, Double.MAX_VALUE) }
      val PEDESTRIAN: Speed by lazy { Speed(2.0, Double.MAX_VALUE) }
      val LIGHT: Speed by lazy { Speed(299792458.0, Double.MAX_VALUE) }
    }

    fun timeToCover(distanceInMeters: Double): Long {
      return (distanceInMeters / linear).toLong()
    }
  }

  @MapboxExperimental class Leg {
    var points: LinkedList<Point> = LinkedList()
    var speed: Speed? = null
    var currentPoint: Point? = null

    internal fun getNextPoint(): Point? {
      if (currentPoint == null) {
        return points.first
      }
      val currentPointIndex = points.indexOf(currentPoint)
      if (currentPointIndex == -1) {
        return null
      }
      return points.getOrNull(currentPointIndex + 1)
    }
  }

  val isOngoing: Boolean get() = currentLeg != null

//  var history = Journey()
  var legs: LinkedList<Leg> = LinkedList()
  set(value) {
    field = value
    if (!isAnimationRunning && isOngoing) {
      drainQueue()
    }
  }
  var speed: Speed? = null
  var currentLeg: Leg? = null
  var stopOnLastLeg = true

  private var locationIndicatorPosition: Point? = null
  private var locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
  private val handler = Handler(Looper.getMainLooper())
  private var isAnimationRunning = false

  fun start() {
    if (currentLeg != null || legs.isEmpty() || legs.first.points.isEmpty()) {
      return
    }

    // move the location indicator to the start
    currentLeg = legs.first
    currentLeg?.currentPoint = legs.first.points.first
    moveLocationIndicatorTo(legs.first.points.first, withSpeed = Speed.LIGHT)
  }

  private fun moveLocationIndicatorTo(destination: Point, withSpeed: Speed) {
    val origin = locationIndicatorPosition

    val travellingTime: Long
    if (origin != null) {
      travellingTime = withSpeed.timeToCover(CustomLocationProvider.distanceInMeters(origin, destination))
    } else {
      travellingTime = 0
    }
    val animationDuration = travellingTime * 1000

    locationConsumers.forEach {
//      it.onBearingUpdated(bearing) {
//        duration = bearingAnimateDuration
//      }
      it.onLocationUpdated(destination) {
        duration = animationDuration
        interpolator = LinearInterpolator()
      }
    }

    isAnimationRunning = true

    handler.postDelayed({
      isAnimationRunning = false
      drainQueue()
    }, animationDuration)
  }

  private fun getNextLeg(): Leg? {
    if (currentLeg == null) {
      return legs.first
    }

    val currentLegIndex = legs.indexOf(currentLeg)
    if (currentLegIndex == -1) {
      return null
    }
    return legs.getOrNull(currentLegIndex + 1)
  }

  private fun getUpcomingLegPoint(): Pair<Leg, Point>? {
    if (currentLeg != null) {
      val upcomingPoint = currentLeg!!.getNextPoint()

      if (upcomingPoint != null) {
        return Pair(currentLeg!!, upcomingPoint)
      }
    }

    return getNextLeg()?.let { leg ->
      leg.getNextPoint()?.let { point ->
        Pair(leg, point)
      }
    }
  }

  fun resolveSpeed(): Speed {
    return currentLeg?.speed ?: speed ?: DEFAULT_SPEED
  }

  private fun drainQueue() {
    val upcoming = getUpcomingLegPoint()

    if (upcoming == null) {
      if (stopOnLastLeg) {
        stop()
      }
      return
    }

    val previousPoint = currentLeg!!.currentPoint!!

    currentLeg = upcoming.first
    currentLeg?.currentPoint = upcoming.second

    val distance = CustomLocationProvider.distanceInMeters(previousPoint, upcoming.second)

    moveLocationIndicatorTo(upcoming.second, withSpeed = resolveSpeed())
  }

  fun pause() {
    error("TODO: Implement")
  }

  fun stop() {
    pause()
    legs = LinkedList()
    currentLeg = null
    handler.removeCallbacksAndMessages(null)
  }

  fun replay() {
//    if (history.legs.isEmpty()) {
//      return
//    }

    error("TODO: Implement")
  }

  override fun registerLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.add(locationConsumer)
  }

  override fun unRegisterLocationConsumer(locationConsumer: LocationConsumer) {
    this.locationConsumers.remove(locationConsumer)
  }

  override fun onIndicatorPositionChanged(point: Point) {
    locationIndicatorPosition = point
  }
}

@MapboxExperimental
class CustomLocationProvider : LocationProvider {
  private var locationConsumers = CopyOnWriteArraySet<LocationConsumer>()
  private var bearingAnimateDuration = 100L
  private var isPlaying = false
  private val handler = Handler(Looper.getMainLooper())
  private val locationList = ConcurrentLinkedQueue<Triple<Point, Double, Long>>()
  private var lastLocation: Point? = null

  var journey = Journey()

  /**
   * Playback speed in m/s.
   */
  var speed = Journey.Speed.CAR


  fun fastForwardJourney(to: Point, withSpeed: Journey.Speed) {

  }
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
      (distanceInMeters(it, location) / speed.linear) * 1000.0
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

  companion object {
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

    fun distanceInMeters(point1: Point, point2: Point): Double {
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