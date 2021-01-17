package com.mapbox.maps.plugin.animation

import android.animation.ValueAnimator
import android.view.animation.Interpolator
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MercatorCoordinate
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimatorOptions.Companion.cameraAnimatorOptions
import com.mapbox.maps.plugin.animation.CameraTransform.deg2rad
import com.mapbox.maps.plugin.animation.CameraTransform.offset
import com.mapbox.maps.plugin.animation.CameraTransform.rad2deg
import com.mapbox.maps.plugin.animation.CameraTransform.scaleZoom
import com.mapbox.maps.plugin.animation.CameraTransform.zoomScale
import com.mapbox.maps.plugin.animation.animator.*
import com.mapbox.maps.plugin.delegates.MapCameraDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import kotlin.math.*

/**
 *  [CameraAnimatorsFactory] stands for storing default animation options.
 *  Provides default animators for "easeTo", "scaleBy", "pitchBy", "moveBy", "rotateBy" animations
 */
class CameraAnimatorsFactory internal constructor(mapDelegateProvider: MapDelegateProvider) {

  private val mapTransformDelegate: MapTransformDelegate = mapDelegateProvider.mapTransformDelegate
  private val mapProjectionDelegate: MapProjectionDelegate = mapDelegateProvider.mapProjectionDelegate
  private val mapCameraDelegate: MapCameraDelegate = mapDelegateProvider.mapCameraDelegate

  /**
   * Get default animators for "easeTo" animation
   *
   * @param cameraOptions Camera options values to animate
   */
  internal fun getEaseTo(cameraOptions: CameraOptions): Array<CameraAnimator<*>> {
    val animationList = mutableListOf<ValueAnimator>()
    val currentCameraOptions = mapCameraDelegate.getCameraOptions()

    cameraOptions.anchor?.let {
      animationList.add(
        CameraAnchorAnimator(
          options = cameraAnimatorOptions(it) {
            startValue = it
          },
          block = defaultAnimationParameters[CameraAnimatorType.ANCHOR]
        )
      )
    }
    cameraOptions.bearing?.let {
      var startBearing = currentCameraOptions.bearing ?: it
      var endBearing = it
      // Minimize rotation by taking the shorter path around the circle.
      endBearing = -CameraTransform.normalizeAngleRadians(
        -endBearing.deg2rad(),
        startBearing.deg2rad()
      ).rad2deg()
      startBearing = CameraTransform.normalizeAngleRadians(
        startBearing.deg2rad(),
        endBearing.deg2rad()
      ).rad2deg()
      animationList.add(
        CameraBearingAnimator(
          options = cameraAnimatorOptions(endBearing) {
            startValue = startBearing
          },
          block = defaultAnimationParameters[CameraAnimatorType.BEARING]
        )
      )
    }
    currentCameraOptions.padding?.let { start ->
      cameraOptions.padding?.let { target ->
        animationList.add(
          CameraPaddingAnimator(
            options = cameraAnimatorOptions(target) {
              startValue = start
            },
            block = defaultAnimationParameters[CameraAnimatorType.PADDING]
          )
        )
      }
    }

    currentCameraOptions.pitch?.let { start ->
      cameraOptions.pitch?.let { target ->
        animationList.add(
          CameraPitchAnimator(
            options = cameraAnimatorOptions(target) {
              startValue = start
            },
            block = defaultAnimationParameters[CameraAnimatorType.PITCH]
          )
        )
      }
    }

    currentCameraOptions.center?.let { start ->
      cameraOptions.center?.let { target ->
        animationList.add(
          CameraCenterAnimator(
            options = cameraAnimatorOptions(target) {
              startValue = start
            },
            block = defaultAnimationParameters[CameraAnimatorType.CENTER]
          )
        )
      }
    }

    currentCameraOptions.zoom?.let { start ->
      cameraOptions.zoom?.let { target ->
        animationList.add(
          CameraZoomAnimator(
            options = cameraAnimatorOptions(target) {
              startValue = start
            },
            block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
          )
        )
      }
    }
    return animationList.map { it as CameraAnimator<*> }.toTypedArray()
  }

  /**
   * Get default animators for "pitchBy" animation
   * Add pitch value to current camera pitch
   *
   * @param pitch The amount to pitch by in degrees
   * @return Array of the created animators
   */
  internal fun getPitchBy(pitch: Double): Array<CameraAnimator<*>> {
    mapCameraDelegate.getCameraOptions().pitch?.let { startPitch ->
      return Array(1) {
        CameraPitchAnimator(
          options = cameraAnimatorOptions(startPitch + pitch) {
            startValue = startPitch
          },
          block = defaultAnimationParameters[CameraAnimatorType.PITCH]
        )
      }
    }
    return emptyArray()
  }

  /**
   * Get default animators for "scaleBy" animation
   * Add scale value to current camera scale
   *
   * @param amount The amount to scale by
   * @param anchor The optional focal point to scale on
   * @return Array of the created animators
   */
  internal fun getScaleBy(amount: Double, anchor: ScreenCoordinate? = null): Array<CameraAnimator<*>> {
    val animationList = mutableListOf<ValueAnimator>()
    anchor?.let {
      animationList.add(
        CameraAnchorAnimator(
          options = cameraAnimatorOptions(it) {
            startValue = it
          },
          block = defaultAnimationParameters[CameraAnimatorType.ANCHOR]
        )
      )
    }
    val currentZoom = mapCameraDelegate.getCameraOptions().zoom
    currentZoom?.let {
      val newScale = CameraTransform.calculateScaleBy(amount, currentZoom)
      animationList.add(
        CameraZoomAnimator(
          options = cameraAnimatorOptions(newScale) {
            startValue = currentZoom
          },
          block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
        )
      )
    }
    return animationList.map { it as CameraAnimator<*> }.toTypedArray()
  }

  /**
   * Get default animators for "moveBy" animation
   * Add offset to current camera position
   *
   * @param offset The screen coordinate distance to move by
   * @return Array of the created animators
   */
  internal fun getMoveBy(
    offset: ScreenCoordinate
  ): Array<CameraAnimator<*>> {
    val cameraOptions = mapCameraDelegate.getCameraOptions()
    val centerTarget = CameraTransform.calculateLatLngMoveBy(
      offset,
      cameraOptions,
      mapTransformDelegate,
      mapProjectionDelegate
    )
    cameraOptions.center?.let { start ->
      centerTarget?.let { target ->
        return Array(1) {
          CameraCenterAnimator(
            options = cameraAnimatorOptions(target) {
              startValue = start
            },
            block = defaultAnimationParameters[CameraAnimatorType.CENTER]
          )
        }
      }
    }
    return emptyArray()
  }

  /**
   * Get default animators for "rotateBy" animation
   *
   * @param first The first pointer to rotate on
   * @param second The second pointer to rotate on
   * @return Array of the created animators
   */
  internal fun getRotateBy(
    first: ScreenCoordinate,
    second: ScreenCoordinate
  ): Array<CameraAnimator<*>> {
    val cameraOptions = mapCameraDelegate.getCameraOptions()
    val mapSizePixels = mapTransformDelegate.getMapOptions().size
    val cameraBearingDegrees = cameraOptions.bearing
    if (cameraBearingDegrees != null && mapSizePixels != null) {
      var mapCenter = CameraTransform.getMapCenter(
        cameraOptions.padding,
        mapSizePixels
      )
      val cameraBearingRadians = -cameraBearingDegrees.deg2rad()
      val offset = first.offset(mapCenter)
      val distance = hypot(offset.x, offset.y)

      // If the first click was too close to the center, move the center of rotation by 200 pixels
      // in the direction of the click.
      if (distance < 200) {
        val heightOffset = -200
        val rotateBearing = atan2(offset.y, offset.x)
        mapCenter = ScreenCoordinate(
          first.x + cos(rotateBearing) * heightOffset,
          first.y + sin(rotateBearing) * heightOffset
        )
      }

      val angleRadians = CameraTransform.angleBetween(
        first.offset(mapCenter),
        second.offset(mapCenter)
      )
      val bearing = -(cameraBearingRadians + angleRadians).rad2deg()

      return Array(1) {
        CameraBearingAnimator(
          options = cameraAnimatorOptions(bearing) {
            startValue = cameraBearingDegrees
          },
          block = defaultAnimationParameters[CameraAnimatorType.BEARING]
        )
      }
    }
    return emptyArray()
  }

  /**
   * Get default animators for "flyTo" animation
   *
   * @param cameraOptions Camera options values to animate
   */
  internal fun getFlyTo(cameraOptions: CameraOptions): Array<CameraAnimator<*>> {

    val currentCameraOptions = mapCameraDelegate.getCameraOptions()

    val endPadding = cameraOptions.padding
      ?: currentCameraOptions.padding
      ?: return emptyArray()
    val endPointRaw = cameraOptions.center
      ?: currentCameraOptions.center
      ?: return emptyArray()
    var endZoom = cameraOptions.zoom
      ?: currentCameraOptions.zoom
      ?: return emptyArray()
    var endBearing = cameraOptions.bearing
      ?: currentCameraOptions.bearing
      ?: return emptyArray()
    val endPitch = cameraOptions.pitch
      ?: currentCameraOptions.pitch
      ?: return emptyArray()

    var startBearing = currentCameraOptions.bearing
      ?: return emptyArray()
    val startScale = mapTransformDelegate.getScale()
    val startZoom = startScale.scaleZoom()
    endZoom = endZoom.coerceIn(mapTransformDelegate.getMinZoom(), mapTransformDelegate.getMaxZoom())

    // Determine endpoints
    var startPointRaw = currentCameraOptions.center
      ?: return emptyArray()
    startPointRaw = CameraTransform.unwrapForShortestPath(startPointRaw, endPointRaw)

    val startPoint = mapProjectionDelegate.project(startPointRaw, startScale)
    val endPoint = mapProjectionDelegate.project(endPointRaw, startScale)

    // Minimize rotation by taking the shorter path around the circle.

    endBearing = -CameraTransform.normalizeAngleRadians(-endBearing.deg2rad(), startBearing.deg2rad()).rad2deg()
    startBearing = CameraTransform.normalizeAngleRadians(startBearing.deg2rad(), endBearing.deg2rad()).rad2deg()

    // w₀: Initial visible span, measured in pixels at the initial scale.
    // Known henceforth as a <i>screenful</i>.

    val w0 = max(
      mapTransformDelegate.getSize().width - endPadding.left - endPadding.right,
      mapTransformDelegate.getSize().height - endPadding.top - endPadding.bottom
    )
    // w₁: Final visible span, measured in pixels with respect to the initial
    // scale.
    val w1 = w0 / (endZoom - startZoom).zoomScale()
    // Length of the flight path as projected onto the ground plane, measured
    // in pixels from the world image origin at the initial scale.
    val u1 = hypot((endPoint.offset(startPoint)).x, (endPoint.offset(startPoint)).y)

    val rho = 1.42
    val rho2 = rho * rho

    /**
     * rᵢ: Returns the zoom-out factor at one end of the animation.
     *
     * @param i ∈ {0, 1}
     */
    fun r(i: Int): Double {
      // bᵢ
      val b = (w1 * w1 - w0 * w0 + (if (i == 0) 1 else -1) * rho2 * rho2 * u1 * u1) /
        (2 * (if (i == 0) w0 else w1) * rho2 * u1)
      return ln(sqrt(b * b + 1) - b)
    }

    // r₀: Zoom-out factor during ascent.
    val r0 = if (u1 != 0.0) {
      r(0)
    } else {
      Double.MAX_VALUE
    }
    val r1 = if (u1 != 0.0) {
      r(1)
    } else {
      Double.MAX_VALUE
    }

    // When u₀ = u₁, the optimal path doesn’t require both ascent and descent.
    val isClose = abs(u1) < 0.000001 || r0 == Double.MAX_VALUE || r1 == Double.MAX_VALUE

    /** w(s): Returns the visible span on the ground, measured in pixels with respect to the initial scale.
     * Assumes an angular field of view of 2 arctan ½ ≈ 53°.
     */
    fun w(s: Double) = if (isClose) {
      exp((if (w1 < w0) -1 else 1) * rho * s)
    } else {
      cosh(r0) / cosh(r0 + rho * s)
    }

    // u(s): Returns the distance along the flight path as projected onto the
    // ground plane, measured in pixels from the world image origin at the
    // initial scale.
    fun u(s: Double) = if (isClose)
      0.0
    else
      w0 * (cosh(r0) * tanh(r0 + rho * s) - sinh(r0)) / rho2 / u1

    // S: Total length of the flight path, measured in ρ-screenfuls.
    val S = if (isClose)
      abs(ln(w1 / w0)) / rho
    else
      (r1 - r0) / rho

    val animators = mutableListOf(
      CameraBearingAnimator(
        options = cameraAnimatorOptions(endBearing) {
          startValue = startBearing
        },
        block = defaultAnimationParameters[CameraAnimatorType.BEARING]
      ),
      CameraCenterAnimator(
        evaluator = { fraction, _, _ ->
          // s: The distance traveled along the flight path, measured in
          // ρ-screenfuls.
          val s = fraction * S
          val us = if (fraction == 1.0f) 1.0 else u(s)
          val interpolated = MercatorCoordinate(
            startPoint.x + us * (endPoint.x - startPoint.x),
            startPoint.y + us * (endPoint.y - startPoint.y)
          )
          mapProjectionDelegate.unproject(interpolated, startScale)
        },
        options = cameraAnimatorOptions(endPointRaw) {
          startValue = startPointRaw
        },
        block = defaultAnimationParameters[CameraAnimatorType.CENTER]
      ),
      CameraZoomAnimator(
        evaluator = { fraction, _, _ ->
          // s: The distance traveled along the flight path, measured in
          // ρ-screenfuls.
          val s = fraction * S
          startZoom + (1 / w(s)).scaleZoom()
        },
        options = cameraAnimatorOptions(endZoom) {
          startValue = startZoom
        },
        block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
      )
    )

    currentCameraOptions.pitch?.let { startPitch ->
      animators.add(
        CameraPitchAnimator(
          options = cameraAnimatorOptions(endPitch) {
            startValue = startPitch
          },
          block = defaultAnimationParameters[CameraAnimatorType.PITCH]
        )
      )
    }

    currentCameraOptions.padding?.let { startPadding ->
      animators.add(
        CameraPaddingAnimator(
          options = cameraAnimatorOptions(endPadding) {
            startValue = startPadding
          },
          block = defaultAnimationParameters[CameraAnimatorType.PADDING]
        )
      )
    }

    return animators.toTypedArray()
  }

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Default animation duration
     */
    const val DEFAULT_ANIMATION_DURATION_MS = 300L

    /**
     Interpolator based on Bezier Curve which is default in Hydrogen SDK
     */
    @JvmField
    val CUBIC_BEZIER_INTERPOLATOR: Interpolator = PathInterpolatorCompat.create(
      0.0f,
      0.0f,
      0.25f,
      1.0f
    )

    private val DEFAULT_INTERPOLATOR = FastOutSlowInInterpolator()

    private val defaultAnimationParameters =
      hashMapOf<CameraAnimatorType, ValueAnimator.() -> Unit>().apply {
        put(CameraAnimatorType.ANCHOR) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
        put(CameraAnimatorType.BEARING) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
        put(CameraAnimatorType.PADDING) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
        put(CameraAnimatorType.PITCH) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
        put(CameraAnimatorType.CENTER) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
        put(CameraAnimatorType.ZOOM) {
          duration = DEFAULT_ANIMATION_DURATION_MS
          interpolator = DEFAULT_INTERPOLATOR
        }
      }

    /**
     * Set default global options for given animator type.
     * All newly created animators will have those options applied.
     *
     * @param type Type of default animator
     * @param block Block to apply any [ValueAnimator] parameters
     */
    @JvmStatic
    fun setDefaultAnimatorOptions(type: CameraAnimatorType, block: ValueAnimator.() -> Unit) {
      defaultAnimationParameters[type] = block
    }

    /**
     * Set default global options for all animator types
     * All newly created animators will have those options applied.
     *
     * @param block Block to apply any [ValueAnimator] parameters
     */
    @JvmStatic
    fun setDefaultAnimatorOptions(block: ValueAnimator.() -> Unit) {
      defaultAnimationParameters[CameraAnimatorType.CENTER] = block
      defaultAnimationParameters[CameraAnimatorType.ZOOM] = block
      defaultAnimationParameters[CameraAnimatorType.BEARING] = block
      defaultAnimationParameters[CameraAnimatorType.PITCH] = block
      defaultAnimationParameters[CameraAnimatorType.ANCHOR] = block
      defaultAnimationParameters[CameraAnimatorType.PADDING] = block
    }
  }
}