package com.mapbox.maps.plugin.location

import android.animation.Animator
import android.location.Location
import android.os.SystemClock
import android.util.SparseArray
import android.view.animation.DecelerateInterpolator
import androidx.annotation.Size
import androidx.annotation.VisibleForTesting
import androidx.core.util.keyIterator
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.location.LocationComponentConstants.MAX_ANIMATION_DURATION_MS
import com.mapbox.maps.plugin.location.MapboxAnimator.*
import kotlin.math.min
import kotlin.math.roundToLong

/**
 * Class is responsible only for the puck/custom model animations
 */
internal class LocationLayerAnimatorCoordinator(
  private val projection: MapProjectionDelegate,
  private val animatorSetProvider: MapboxAnimatorSetProvider,
  private val animatorProvider: MapboxAnimatorProvider
) {

  @VisibleForTesting
  internal val puckAnimators = SparseArray<MapboxAnimator<*>>()

  private var previousAccuracyRadius = -1f
  private var previousCompassBearing = -1f
  private var locationUpdateTimestamp = -1L
  var compassAnimationEnabled = false
  var accuracyAnimationEnabled = false

  var maxAnimationFps = Int.MAX_VALUE
    set(value) {
      if (value <= 0) {
        Logger.e(TAG, "Max animation FPS cannot be less or equal to 0.")
        return
      }
      field = value
    }

  @VisibleForTesting
  internal val listeners = SparseArray<AnimationsValueChangeListener<Any>>()

  fun updateAnimatorListenerHolders(listenerHolders: Set<AnimatorListenerHolder>) {
    listeners.clear()
    listenerHolders.forEach {
      listeners.append(it.animatorType, it.listener)
    }
    puckAnimators.keyIterator().forEach { animatorType ->
      if (listeners[animatorType] == null) {
        val animator = puckAnimators[animatorType]
        animator?.makeInvalid()
      }
    }
  }

  fun feedNewLocation(
    @Size(min = 1) newLocations: Array<Location>,
    previousLocation: Location?,
    animationDuration: Long?
  ): Long {
    val newLocation = newLocations[newLocations.size - 1]
    return feedNewLayerLocation(
      newLocations,
      newLocation,
      previousLocation ?: newLocation,
      animationDuration
    )
  }

  private fun feedNewLayerLocation(
    @Size(min = 1) newLocations: Array<Location>,
    newLocation: Location,
    previousLocation: Location,
    animationDuration: Long?
  ): Long {

    val previousLayerLatLng = getPreviousLayerLatLng(previousLocation)
    val previousLayerBearing = getPreviousLayerGpsBearing(previousLocation)

    val latLngValues = getLatLngValues(previousLayerLatLng, newLocations)
    val bearingValues = getBearingValues(previousLayerBearing, newLocations)
    updateLayerAnimators(latLngValues, bearingValues)

    val targetLatLng = Point.fromLngLat(newLocation.longitude, newLocation.latitude)
    val snap = Utils.immediateAnimation(projection, previousLayerLatLng, targetLatLng)

    val previousUpdateTimeStamp: Long = locationUpdateTimestamp
    locationUpdateTimestamp = SystemClock.elapsedRealtime()
    var duration = 0L
    if (animationDuration == null) {
      if (!snap) {
        duration = if (previousUpdateTimeStamp == -1L) {
          0L
        } else {
          ((locationUpdateTimestamp - previousUpdateTimeStamp) * 1.1f).roundToLong()
          /* make animation slightly longer with durationMultiplier, defaults to 1.1f */
        }

        duration = min(duration, MAX_ANIMATION_DURATION_MS)
      }
    } else {
      duration = animationDuration
    }

    playAnimators(
      duration,
      ANIMATOR_LAYER_LATLNG,
      ANIMATOR_LAYER_GPS_BEARING
    )
    return duration
  }

  fun feedNewCompassBearing(targetCompassBearing: Float, animationDuration: Long) {
    if (previousCompassBearing < 0) {
      previousCompassBearing = targetCompassBearing
    }
    feedNewCompassLayerBearing(targetCompassBearing, animationDuration)
    previousCompassBearing = targetCompassBearing
  }

  private fun feedNewCompassLayerBearing(
    targetCompassBearing: Float,
    animationDuration: Long
  ) {
    val previousLayerBearing = getPreviousLayerCompassBearing()
    updateCompassLayerBearing(targetCompassBearing, previousLayerBearing)
    playAnimators(animationDuration, ANIMATOR_LAYER_COMPASS_BEARING)
  }

  fun feedNewAccuracyRadius(targetAccuracyRadius: Float, noAnimation: Boolean) {
    if (previousAccuracyRadius < 0) {
      previousAccuracyRadius = targetAccuracyRadius
    }
    val previousAccuracyRadius = getPreviousAccuracyRadius()
    updateAccuracyAnimators(targetAccuracyRadius, previousAccuracyRadius)
    val duration =
      if (noAnimation || !accuracyAnimationEnabled) 0 else LocationComponentConstants.ACCURACY_RADIUS_ANIMATION_DURATION
    playAnimators(duration, ANIMATOR_LAYER_ACCURACY)
    this.previousAccuracyRadius = targetAccuracyRadius
  }

  private fun updateAccuracyAnimators(targetAccuracyRadius: Float, previousAccuracyRadius: Float) {
    createNewFloatAnimator(ANIMATOR_LAYER_ACCURACY, previousAccuracyRadius, targetAccuracyRadius)
  }

  /**
   * Initializes the [PulsingLocationCircleAnimator], which is a type of [MapboxAnimator].
   * This method also adds the animator to this class' animator array.
   *
   * @param options the [LocationComponentOptions] passed to this class upstream from the
   * [LocationPluginImpl].
   */
  fun startLocationComponentCirclePulsing(options: LocationComponentOptions) {
    cancelAnimator(ANIMATOR_PULSING_CIRCLE)
    listeners[ANIMATOR_PULSING_CIRCLE]?.let { listener ->
      val interpolator =
        if (options.pulseInterpolator() == null) DecelerateInterpolator() else options.pulseInterpolator()
      val pulsingLocationCircleAnimator = animatorProvider.pulsingCircleAnimator(
        listener,
        maxAnimationFps,
        options.pulseSingleDuration(),
        options.pulseMaxRadius(),
        interpolator
      )
      puckAnimators.put(ANIMATOR_PULSING_CIRCLE, pulsingLocationCircleAnimator)
      playPulsingAnimator()
    }
  }

  private fun getPreviousLayerLatLng(previousLocation: Location) =
    (puckAnimators[ANIMATOR_LAYER_LATLNG]?.animatedValue as? Point)
      ?: Point.fromLngLat(previousLocation.longitude, previousLocation.latitude)

  private fun getPreviousLayerGpsBearing(previousLocation: Location) =
    (puckAnimators[ANIMATOR_LAYER_GPS_BEARING]?.animatedValue as? Float)
      ?: previousLocation.bearing

  private fun getPreviousLayerCompassBearing() =
    (puckAnimators[ANIMATOR_LAYER_COMPASS_BEARING]?.animatedValue as? Float)
      ?: previousCompassBearing

  private fun getPreviousAccuracyRadius(): Float {
    val animator = puckAnimators[ANIMATOR_LAYER_ACCURACY]
    return if (animator != null) {
      animator.animatedValue as Float
    } else {
      previousAccuracyRadius
    }
  }

  private fun getLatLngValues(previousLatLng: Point, targetLocations: Array<Location>) =
    Array<Point>(targetLocations.size + 1) { i ->
      if (i == 0) {
        previousLatLng
      } else {
        val location = targetLocations[i - 1]
        Point.fromLngLat(location.longitude, location.latitude)
      }
    }

  private fun getBearingValues(
    previousBearing: Float,
    targetLocations: Array<Location>
  ): Array<Float> {
    val bearings = Array(targetLocations.size + 1) { 0f }
    // Because Location bearing values are normalized to [0, 360]
    // we need to do the same for the previous bearing value to determine the shortest path
    bearings[0] = Utils.normalize(previousBearing)
    for (i in 1 until bearings.size) {
      bearings[i] = Utils.shortestRotation(targetLocations[i - 1].bearing, bearings[i - 1])
    }
    return bearings
  }

  private fun updateLayerAnimators(latLngValues: Array<Point>, bearingValues: Array<Float>) {
    createNewLatLngAnimator(latLngValues)
    createNewFloatAnimator(ANIMATOR_LAYER_GPS_BEARING, bearingValues)
  }

  private fun updateCompassLayerBearing(targetBearing: Float, previousBearing: Float) {
    val normalizedLayerBearing = Utils.shortestRotation(targetBearing, previousBearing)
    createNewFloatAnimator(
      ANIMATOR_LAYER_COMPASS_BEARING,
      previousBearing,
      normalizedLayerBearing
    )
  }

  private fun createNewLatLngAnimator(values: Array<Point>) {
    cancelAnimator(ANIMATOR_LAYER_LATLNG)
    listeners[ANIMATOR_LAYER_LATLNG]?.let { listener ->
      puckAnimators.put(
        ANIMATOR_LAYER_LATLNG,
        animatorProvider.latLngAnimator(values, listener, maxAnimationFps)
      )
    }
  }

  private fun createNewFloatAnimator(
    @MapboxAnimator.Type animatorType: Int,
    previous: Float,
    target: Float
  ) = createNewFloatAnimator(animatorType, arrayOf(previous, target))

  private fun createNewFloatAnimator(
    @MapboxAnimator.Type animatorType: Int,
    @Size(min = 2) values: Array<Float>
  ) {
    cancelAnimator(animatorType)
    listeners[animatorType]?.let { listener ->
      puckAnimators.put(
        animatorType,
        animatorProvider.floatAnimator(values, listener, maxAnimationFps)
      )
    }
  }

  private fun playAnimators(duration: Long, vararg animatorTypes: Int) {
    val animators = mutableListOf<Animator>()
    animatorTypes.forEach { animatorType ->
      puckAnimators[animatorType]?.let { animators.add(it) }
    }
    animatorSetProvider.startAnimation(
      animators,
      MapboxAnimatorSetProvider.animatorInstance,
      duration
    )
  }

  /**
   * Starts the [PulsingLocationCircleAnimator] in the animator array. This method is separate
   * from [.playAnimators] because the MapboxAnimatorSetProvider has many more
   * customizable animation parameters than the other [MapboxAnimator]s.
   */
  private fun playPulsingAnimator() = puckAnimators[ANIMATOR_PULSING_CIRCLE]?.start()

  fun resetAllLayerAnimations() {
    val latLngAnimator = puckAnimators[ANIMATOR_LAYER_LATLNG] as? MapboxLatLngAnimator
    val gpsBearingAnimator = puckAnimators[ANIMATOR_LAYER_GPS_BEARING] as? MapboxFloatAnimator
    val compassBearingAnimator =
      puckAnimators[ANIMATOR_LAYER_COMPASS_BEARING] as? MapboxFloatAnimator
    val accuracyAnimator = puckAnimators[ANIMATOR_LAYER_ACCURACY] as? MapboxFloatAnimator
    if (latLngAnimator != null && gpsBearingAnimator != null) {
      val currentLatLng = latLngAnimator.animatedValue as Point
      val currentLatLngTarget = latLngAnimator.target
      createNewLatLngAnimator(arrayOf(currentLatLng, currentLatLngTarget))

      val currentGpsBearing = gpsBearingAnimator.animatedValue as Float
      val currentGpsBearingTarget = gpsBearingAnimator.target
      createNewFloatAnimator(
        ANIMATOR_LAYER_GPS_BEARING,
        currentGpsBearing,
        currentGpsBearingTarget
      )
      val duration = latLngAnimator.duration - latLngAnimator.currentPlayTime
      playAnimators(
        duration,
        ANIMATOR_LAYER_LATLNG,
        ANIMATOR_LAYER_GPS_BEARING
      )
    }
    if (compassBearingAnimator != null) {
      val currentLayerBearing = getPreviousLayerCompassBearing()
      val currentLayerBearingTarget = compassBearingAnimator.target
      createNewFloatAnimator(
        ANIMATOR_LAYER_COMPASS_BEARING,
        currentLayerBearing,
        currentLayerBearingTarget
      )
      val duration =
        if (compassAnimationEnabled) LocationComponentConstants.COMPASS_UPDATE_RATE_MS else 0
      playAnimators(
        duration,
        ANIMATOR_LAYER_COMPASS_BEARING
      )
    }
    if (accuracyAnimator != null) {
      feedNewAccuracyRadius(previousAccuracyRadius, false)
    }
  }

  fun cancelAndRemoveGpsBearingAnimation() {
    cancelAnimator(ANIMATOR_LAYER_GPS_BEARING)
    puckAnimators.remove(ANIMATOR_LAYER_GPS_BEARING)
  }

  /**
   * Cancel the pulsing circle location animator.
   */
  fun stopPulsingCircleAnimation() = cancelAnimator(ANIMATOR_PULSING_CIRCLE)

  fun cancelAllAnimations() = puckAnimators.keyIterator().forEach { cancelAnimator(it) }

  private fun cancelAnimator(@MapboxAnimator.Type animatorType: Int) {
    puckAnimators[animatorType]?.let { animator ->
      animator.cancel()
      animator.removeAllUpdateListeners()
      animator.removeAllListeners()
    }
  }

  companion object {
    private const val TAG = "Mbgl-LocationAnimatorCoordinator"
  }
}