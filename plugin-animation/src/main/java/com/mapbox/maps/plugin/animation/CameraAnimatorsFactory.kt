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
import com.mapbox.maps.plugin.animation.CameraTransform.wrapCoordinate
import com.mapbox.maps.plugin.animation.CameraTransform.zoomScale
import com.mapbox.maps.plugin.animation.animator.*
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
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
  private val mapProjectionDelegate: MapProjectionDelegate =
    mapDelegateProvider.mapProjectionDelegate
  private val mapCameraManagerDelegate: MapCameraManagerDelegate = mapDelegateProvider.mapCameraManagerDelegate

  /**
   * Get default animators for "easeTo" animation
   *
   * @param cameraOptions Camera options values to animate
   */
  @JvmOverloads
  fun getEaseTo(cameraOptions: CameraOptions, owner: String? = null): Array<CameraAnimator<*>> {
    val animationList = mutableListOf<ValueAnimator>()
    val currentCameraState = mapCameraManagerDelegate.cameraState

    cameraOptions.center?.let { target ->
      animationList.add(
        CameraCenterAnimator(
          options = cameraAnimatorOptions(target) {
            startValue(currentCameraState.center)
          },
          block = defaultAnimationParameters[CameraAnimatorType.CENTER]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    cameraOptions.anchor?.let {
      animationList.add(
        CameraAnchorAnimator(
          options = cameraAnimatorOptions(it) {
            startValue(it)
          },
          block = defaultAnimationParameters[CameraAnimatorType.ANCHOR]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    cameraOptions.bearing?.let {
      animationList.add(
        CameraBearingAnimator(
          options = cameraAnimatorOptions(it) {
            startValue(currentCameraState.bearing)
          },
          useShortestPath = true,
          block = defaultAnimationParameters[CameraAnimatorType.BEARING]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    cameraOptions.padding?.let { target ->
      animationList.add(
        CameraPaddingAnimator(
          options = cameraAnimatorOptions(target) {
            startValue(currentCameraState.padding)
          },
          block = defaultAnimationParameters[CameraAnimatorType.PADDING]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    cameraOptions.pitch?.let { target ->
      animationList.add(
        CameraPitchAnimator(
          options = cameraAnimatorOptions(target) {
            startValue(currentCameraState.pitch)
          },
          block = defaultAnimationParameters[CameraAnimatorType.PITCH]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    cameraOptions.zoom?.let { target ->
      animationList.add(
        CameraZoomAnimator(
          options = cameraAnimatorOptions(target) {
            startValue(currentCameraState.zoom)
          },
          block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
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
  @JvmOverloads
  fun getPitchBy(pitch: Double, owner: String? = null): Array<CameraAnimator<*>> {
    val startPitch = mapCameraManagerDelegate.cameraState.pitch
    return arrayOf(
      CameraPitchAnimator(
        options = cameraAnimatorOptions(startPitch + pitch) {
          startValue(startPitch)
        },
        block = defaultAnimationParameters[CameraAnimatorType.PITCH]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      }
    )
  }

  /**
   * Get default animators for "scaleBy" animation
   * Add scale value to current camera scale
   *
   * @param amount The amount to scale by
   * @param anchor The optional focal point to scale on
   * @return Array of the created animators
   */
  @JvmOverloads
  fun getScaleBy(
    amount: Double,
    anchor: ScreenCoordinate? = null,
    owner: String? = null
  ): Array<CameraAnimator<*>> {
    val animationList = mutableListOf<CameraAnimator<*>>()
    anchor?.let {
      animationList.add(
        CameraAnchorAnimator(
          options = cameraAnimatorOptions(it) {
            startValue(it)
          },
          block = defaultAnimationParameters[CameraAnimatorType.ANCHOR]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }
    val currentZoom = mapCameraManagerDelegate.cameraState.zoom
    val newScale = CameraTransform.calculateScaleBy(amount, currentZoom)
    animationList.add(
      CameraZoomAnimator(
        options = cameraAnimatorOptions(newScale) {
          startValue(currentZoom)
        },
        block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      }
    )
    return animationList.toTypedArray()
  }

  /**
   * Get default animators for "moveBy" animation
   * Add offset to current camera position
   *
   * @param offset The screen coordinate distance to move by
   * @return Array of the created animators
   */
  @JvmOverloads
  fun getMoveBy(offset: ScreenCoordinate, owner: String? = null): Array<CameraAnimator<*>> {
    val cameraState = mapCameraManagerDelegate.cameraState
    val centerTarget = CameraTransform.calculateLatLngMoveBy(
      offset,
      cameraState,
      mapTransformDelegate,
      mapCameraManagerDelegate
    )
    return arrayOf(
      CameraCenterAnimator(
        options = cameraAnimatorOptions(centerTarget) {
          startValue(cameraState.center)
        },
        block = defaultAnimationParameters[CameraAnimatorType.CENTER]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      }
    )
  }

  /**
   * Get default animators for "rotateBy" animation
   *
   * @param first The first pointer to rotate on
   * @param second The second pointer to rotate on
   * @return Array of the created animators
   */
  @JvmOverloads
  fun getRotateBy(
    first: ScreenCoordinate,
    second: ScreenCoordinate,
    owner: String? = null
  ): Array<CameraAnimator<*>> {
    val cameraOptions = mapCameraManagerDelegate.cameraState
    val mapSizePixels = mapTransformDelegate.getMapOptions().size
    val cameraBearingDegrees = cameraOptions.bearing
    if (mapSizePixels != null) {
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

      return arrayOf(
        CameraBearingAnimator(
          options = cameraAnimatorOptions(bearing) {
            startValue(cameraBearingDegrees)
          },
          useShortestPath = true,
          block = defaultAnimationParameters[CameraAnimatorType.BEARING]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }
    return emptyArray()
  }

  /**
   * Get default animators for "flyTo" animation
   *
   * @param cameraOptions Camera options values to animate
   */
  @JvmOverloads
  fun getFlyTo(cameraOptions: CameraOptions, owner: String? = null): Array<CameraAnimator<*>> {

    val currentCameraState = mapCameraManagerDelegate.cameraState

    val startPadding = currentCameraState.padding
    val endPadding = cameraOptions.padding ?: startPadding
    val endPointRaw = (cameraOptions.center ?: currentCameraState.center).wrapCoordinate()
    var endZoom = cameraOptions.zoom ?: currentCameraState.zoom
    val endBearing = cameraOptions.bearing ?: currentCameraState.bearing
    val startPitch = currentCameraState.pitch
    val endPitch = cameraOptions.pitch ?: startPitch

    val startBearing = currentCameraState.bearing
    val startScale = 2.0.pow(currentCameraState.zoom)
    val startZoom = startScale.scaleZoom()
    endZoom = endZoom.coerceIn(
      mapCameraManagerDelegate.getBounds().minZoom,
      mapCameraManagerDelegate.getBounds().maxZoom
    )

    // Determine endpoints
    var startPointRaw = currentCameraState.center.wrapCoordinate()
    startPointRaw = CameraTransform.unwrapForShortestPath(startPointRaw, endPointRaw)

    val startPoint = mapProjectionDelegate.project(startPointRaw, startScale)
    val endPoint = mapProjectionDelegate.project(endPointRaw, startScale)

    // w₀: Initial visible span, measured in pixels at the initial scale.
    // Known henceforth as a <i>screenful</i>.

    val size = mapTransformDelegate.getSize()
    val pixelRatio = mapTransformDelegate.getMapOptions().pixelRatio
    val w0 = if (size.width.toDouble() != endPadding.left + endPadding.right &&
      size.height.toDouble() != endPadding.bottom + endPadding.top
    ) {
      max(
        (size.width - endPadding.left - endPadding.right) / pixelRatio,
        (size.height - endPadding.top - endPadding.bottom) / pixelRatio
      )
    } else {
      max(
        size.width.toDouble() / pixelRatio,
        size.height.toDouble() / pixelRatio
      )
    }
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
      Double.POSITIVE_INFINITY
    }
    val r1 = if (u1 != 0.0) {
      r(1)
    } else {
      Double.POSITIVE_INFINITY
    }

    // When u₀ = u₁, the optimal path doesn’t require both ascent and descent.
    val isClose = abs(u1) < 0.000001 || r0.isInfinite() || r1.isInfinite()

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
      CameraCenterAnimator(
        evaluator = { fraction, _, _ ->
          val safeFraction = fraction.getSafeFraction()
          // s: The distance traveled along the flight path, measured in
          // ρ-screenfuls.
          val s = safeFraction * S
          val us = if (safeFraction == 1.0f) 1.0 else u(s)
          val interpolated = MercatorCoordinate(
            startPoint.x + us * (endPoint.x - startPoint.x),
            startPoint.y + us * (endPoint.y - startPoint.y)
          )
          mapProjectionDelegate.unproject(interpolated, startScale)
        },
        options = cameraAnimatorOptions(endPointRaw) {
          startValue(startPointRaw)
        },
        block = defaultAnimationParameters[CameraAnimatorType.CENTER]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      },
      CameraZoomAnimator(
        evaluator = { fraction, _, _ ->
          val safeFraction = fraction.getSafeFraction()
          // s: The distance traveled along the flight path, measured in
          // ρ-screenfuls.
          val s = safeFraction * S
          startZoom + (1 / w(s)).scaleZoom()
        },
        options = cameraAnimatorOptions(endZoom) {
          startValue(startZoom)
        },
        block = defaultAnimationParameters[CameraAnimatorType.ZOOM]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      }
    )

    if (endBearing != startBearing) {
      animators.add(
        CameraBearingAnimator(
        options = cameraAnimatorOptions(endBearing) {
          startValue(startBearing)
        },
        useShortestPath = true,
        block = defaultAnimationParameters[CameraAnimatorType.BEARING]
      ).apply {
        owner?.let { customOwner ->
          this.owner = customOwner
        }
      }
      )
    }

    if (endPitch != startPitch) {
      animators.add(
        CameraPitchAnimator(
          options = cameraAnimatorOptions(endPitch) {
            startValue(startPitch)
          },
          block = defaultAnimationParameters[CameraAnimatorType.PITCH]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }

    if (endPadding != startPadding) {
      animators.add(
        CameraPaddingAnimator(
          options = cameraAnimatorOptions(endPadding) {
            startValue(startPadding)
          },
          block = defaultAnimationParameters[CameraAnimatorType.PADDING]
        ).apply {
          owner?.let { customOwner ->
            this.owner = customOwner
          }
        }
      )
    }
    return animators.toTypedArray()
  }

  // TODO: remove it after https://issuetracker.google.com/issues/280660517 will be fixed
  private fun Float.getSafeFraction(): Float = if (this.isNaN()) 0f else this

  /**
   * Static variables and methods.
   */
  companion object {

    /**
     * Default animation duration
     */
    const val DEFAULT_ANIMATION_DURATION_MS = 300L

    /**
     * Interpolator based on Bezier Curve which is default in v9 version of the SDK
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