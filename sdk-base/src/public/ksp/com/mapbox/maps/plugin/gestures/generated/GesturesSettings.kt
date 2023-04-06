package com.mapbox.maps.plugin.gestures.generated

import android.os.Parcelable
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.ScrollMode
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Gesture configuration allows to control the user touch interaction.
 */
@Parcelize
public class GesturesSettings private constructor(
  /**
   * RotateEnabled
   * Whether the rotate gesture is enabled.
   */
  public val rotateEnabled: Boolean,
  /**
   * PinchToZoomEnabled
   * Whether the pinch to zoom gesture is enabled.
   */
  public val pinchToZoomEnabled: Boolean,
  /**
   * ScrollEnabled
   * Whether the single-touch scroll gesture is enabled.
   */
  public val scrollEnabled: Boolean,
  /**
   * SimultaneousRotateAndPinchToZoomEnabled
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  public val simultaneousRotateAndPinchToZoomEnabled: Boolean,
  /**
   * PitchEnabled
   * Whether the pitch gesture is enabled.
   */
  public val pitchEnabled: Boolean,
  /**
   * ScrollMode
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  public val scrollMode: ScrollMode,
  /**
   * DoubleTapToZoomInEnabled
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  public val doubleTapToZoomInEnabled: Boolean,
  /**
   * DoubleTouchToZoomOutEnabled
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  public val doubleTouchToZoomOutEnabled: Boolean,
  /**
   * QuickZoomEnabled
   * Whether the quick zoom gesture is enabled.
   */
  public val quickZoomEnabled: Boolean,
  /**
   * FocalPoint
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to
   * rotate and zoom around a fixed point instead.
   */
  public val focalPoint: ScreenCoordinate?,
  /**
   * PinchToZoomDecelerationEnabled
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  public val pinchToZoomDecelerationEnabled: Boolean,
  /**
   * RotateDecelerationEnabled
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  public val rotateDecelerationEnabled: Boolean,
  /**
   * ScrollDecelerationEnabled
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  public val scrollDecelerationEnabled: Boolean,
  /**
   * IncreaseRotateThresholdWhenPinchingToZoom
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  public val increaseRotateThresholdWhenPinchingToZoom: Boolean,
  /**
   * IncreasePinchToZoomThresholdWhenRotating
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  public val increasePinchToZoomThresholdWhenRotating: Boolean,
  /**
   * ZoomAnimationAmount
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or
   * double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  public val zoomAnimationAmount: Float,
  /**
   * PinchScrollEnabled
   * Whether pan is enabled for the pinch gesture.
   */
  public val pinchScrollEnabled: Boolean
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """GesturesSettings(rotateEnabled=$rotateEnabled,
      pinchToZoomEnabled=$pinchToZoomEnabled, scrollEnabled=$scrollEnabled,
      simultaneousRotateAndPinchToZoomEnabled=$simultaneousRotateAndPinchToZoomEnabled,
      pitchEnabled=$pitchEnabled, scrollMode=$scrollMode,
      doubleTapToZoomInEnabled=$doubleTapToZoomInEnabled,
      doubleTouchToZoomOutEnabled=$doubleTouchToZoomOutEnabled, quickZoomEnabled=$quickZoomEnabled,
      focalPoint=$focalPoint, pinchToZoomDecelerationEnabled=$pinchToZoomDecelerationEnabled,
      rotateDecelerationEnabled=$rotateDecelerationEnabled,
      scrollDecelerationEnabled=$scrollDecelerationEnabled,
      increaseRotateThresholdWhenPinchingToZoom=$increaseRotateThresholdWhenPinchingToZoom,
      increasePinchToZoomThresholdWhenRotating=$increasePinchToZoomThresholdWhenRotating,
      zoomAnimationAmount=$zoomAnimationAmount,
      pinchScrollEnabled=$pinchScrollEnabled)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as GesturesSettings
    return rotateEnabled == other.rotateEnabled && pinchToZoomEnabled == other.pinchToZoomEnabled &&
        scrollEnabled == other.scrollEnabled &&
        simultaneousRotateAndPinchToZoomEnabled == other.simultaneousRotateAndPinchToZoomEnabled &&
        pitchEnabled == other.pitchEnabled && scrollMode == other.scrollMode &&
        doubleTapToZoomInEnabled == other.doubleTapToZoomInEnabled &&
        doubleTouchToZoomOutEnabled == other.doubleTouchToZoomOutEnabled &&
        quickZoomEnabled == other.quickZoomEnabled && focalPoint == other.focalPoint &&
        pinchToZoomDecelerationEnabled == other.pinchToZoomDecelerationEnabled &&
        rotateDecelerationEnabled == other.rotateDecelerationEnabled &&
        scrollDecelerationEnabled == other.scrollDecelerationEnabled &&
        increaseRotateThresholdWhenPinchingToZoom == other.increaseRotateThresholdWhenPinchingToZoom &&
        increasePinchToZoomThresholdWhenRotating == other.increasePinchToZoomThresholdWhenRotating &&
        zoomAnimationAmount == other.zoomAnimationAmount &&
        pinchScrollEnabled == other.pinchScrollEnabled
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(rotateEnabled, pinchToZoomEnabled,
      scrollEnabled, simultaneousRotateAndPinchToZoomEnabled, pitchEnabled, scrollMode,
      doubleTapToZoomInEnabled, doubleTouchToZoomOutEnabled, quickZoomEnabled, focalPoint,
      pinchToZoomDecelerationEnabled, rotateDecelerationEnabled, scrollDecelerationEnabled,
      increaseRotateThresholdWhenPinchingToZoom, increasePinchToZoomThresholdWhenRotating,
      zoomAnimationAmount, pinchScrollEnabled)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setRotateEnabled(rotateEnabled)
      .setPinchToZoomEnabled(pinchToZoomEnabled) .setScrollEnabled(scrollEnabled)
      .setSimultaneousRotateAndPinchToZoomEnabled(simultaneousRotateAndPinchToZoomEnabled)
      .setPitchEnabled(pitchEnabled) .setScrollMode(scrollMode)
      .setDoubleTapToZoomInEnabled(doubleTapToZoomInEnabled)
      .setDoubleTouchToZoomOutEnabled(doubleTouchToZoomOutEnabled)
      .setQuickZoomEnabled(quickZoomEnabled) .setFocalPoint(focalPoint)
      .setPinchToZoomDecelerationEnabled(pinchToZoomDecelerationEnabled)
      .setRotateDecelerationEnabled(rotateDecelerationEnabled)
      .setScrollDecelerationEnabled(scrollDecelerationEnabled)
      .setIncreaseRotateThresholdWhenPinchingToZoom(increaseRotateThresholdWhenPinchingToZoom)
      .setIncreasePinchToZoomThresholdWhenRotating(increasePinchToZoomThresholdWhenRotating)
      .setZoomAnimationAmount(zoomAnimationAmount) .setPinchScrollEnabled(pinchScrollEnabled)

  /**
   * Composes and builds a [GesturesSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * RotateEnabled
     * Whether the rotate gesture is enabled.
     */
    @set:JvmSynthetic
    public var rotateEnabled: Boolean? = true

    /**
     * PinchToZoomEnabled
     * Whether the pinch to zoom gesture is enabled.
     */
    @set:JvmSynthetic
    public var pinchToZoomEnabled: Boolean? = true

    /**
     * ScrollEnabled
     * Whether the single-touch scroll gesture is enabled.
     */
    @set:JvmSynthetic
    public var scrollEnabled: Boolean? = true

    /**
     * SimultaneousRotateAndPinchToZoomEnabled
     * Whether rotation is enabled for the pinch to zoom gesture.
     */
    @set:JvmSynthetic
    public var simultaneousRotateAndPinchToZoomEnabled: Boolean? = true

    /**
     * PitchEnabled
     * Whether the pitch gesture is enabled.
     */
    @set:JvmSynthetic
    public var pitchEnabled: Boolean? = true

    /**
     * ScrollMode
     * Configures the directions in which the map is allowed to move during a scroll gesture.
     */
    @set:JvmSynthetic
    public var scrollMode: ScrollMode? = ScrollMode.HORIZONTAL_AND_VERTICAL

    /**
     * DoubleTapToZoomInEnabled
     * Whether double tapping the map with one touch results in a zoom-in animation.
     */
    @set:JvmSynthetic
    public var doubleTapToZoomInEnabled: Boolean? = true

    /**
     * DoubleTouchToZoomOutEnabled
     * Whether single tapping the map with two touches results in a zoom-out animation.
     */
    @set:JvmSynthetic
    public var doubleTouchToZoomOutEnabled: Boolean? = true

    /**
     * QuickZoomEnabled
     * Whether the quick zoom gesture is enabled.
     */
    @set:JvmSynthetic
    public var quickZoomEnabled: Boolean? = true

    /**
     * FocalPoint
     * By default, gestures rotate and zoom around the center of the gesture. Set this property to
     * rotate and zoom around a fixed point instead.
     */
    @set:JvmSynthetic
    public var focalPoint: ScreenCoordinate? = null

    /**
     * PinchToZoomDecelerationEnabled
     * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by
     * default.
     */
    @set:JvmSynthetic
    public var pinchToZoomDecelerationEnabled: Boolean? = true

    /**
     * RotateDecelerationEnabled
     * Whether a deceleration animation following a rotate gesture is enabled. True by default.
     */
    @set:JvmSynthetic
    public var rotateDecelerationEnabled: Boolean? = true

    /**
     * ScrollDecelerationEnabled
     * Whether a deceleration animation following a scroll gesture is enabled. True by default.
     */
    @set:JvmSynthetic
    public var scrollDecelerationEnabled: Boolean? = true

    /**
     * IncreaseRotateThresholdWhenPinchingToZoom
     * Whether rotate threshold increases when pinching to zoom. true by default.
     */
    @set:JvmSynthetic
    public var increaseRotateThresholdWhenPinchingToZoom: Boolean? = true

    /**
     * IncreasePinchToZoomThresholdWhenRotating
     * Whether pinch to zoom threshold increases when rotating. true by default.
     */
    @set:JvmSynthetic
    public var increasePinchToZoomThresholdWhenRotating: Boolean? = true

    /**
     * ZoomAnimationAmount
     * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or
     * double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
     */
    @set:JvmSynthetic
    public var zoomAnimationAmount: Float? = 1f

    /**
     * PinchScrollEnabled
     * Whether pan is enabled for the pinch gesture.
     */
    @set:JvmSynthetic
    public var pinchScrollEnabled: Boolean? = true

    /**
     * Set rotateEnabled
     * Whether the rotate gesture is enabled.
     *
     * @param rotateEnabled rotateEnabled
     * @return Builder
     */
    public fun setRotateEnabled(rotateEnabled: Boolean?): Builder {
      this.rotateEnabled = rotateEnabled
      return this
    }

    /**
     * Set pinchToZoomEnabled
     * Whether the pinch to zoom gesture is enabled.
     *
     * @param pinchToZoomEnabled pinchToZoomEnabled
     * @return Builder
     */
    public fun setPinchToZoomEnabled(pinchToZoomEnabled: Boolean?): Builder {
      this.pinchToZoomEnabled = pinchToZoomEnabled
      return this
    }

    /**
     * Set scrollEnabled
     * Whether the single-touch scroll gesture is enabled.
     *
     * @param scrollEnabled scrollEnabled
     * @return Builder
     */
    public fun setScrollEnabled(scrollEnabled: Boolean?): Builder {
      this.scrollEnabled = scrollEnabled
      return this
    }

    /**
     * Set simultaneousRotateAndPinchToZoomEnabled
     * Whether rotation is enabled for the pinch to zoom gesture.
     *
     * @param simultaneousRotateAndPinchToZoomEnabled simultaneousRotateAndPinchToZoomEnabled
     * @return Builder
     */
    public
        fun setSimultaneousRotateAndPinchToZoomEnabled(simultaneousRotateAndPinchToZoomEnabled: Boolean?):
        Builder {
      this.simultaneousRotateAndPinchToZoomEnabled = simultaneousRotateAndPinchToZoomEnabled
      return this
    }

    /**
     * Set pitchEnabled
     * Whether the pitch gesture is enabled.
     *
     * @param pitchEnabled pitchEnabled
     * @return Builder
     */
    public fun setPitchEnabled(pitchEnabled: Boolean?): Builder {
      this.pitchEnabled = pitchEnabled
      return this
    }

    /**
     * Set scrollMode
     * Configures the directions in which the map is allowed to move during a scroll gesture.
     *
     * @param scrollMode scrollMode
     * @return Builder
     */
    public fun setScrollMode(scrollMode: ScrollMode?): Builder {
      this.scrollMode = scrollMode
      return this
    }

    /**
     * Set doubleTapToZoomInEnabled
     * Whether double tapping the map with one touch results in a zoom-in animation.
     *
     * @param doubleTapToZoomInEnabled doubleTapToZoomInEnabled
     * @return Builder
     */
    public fun setDoubleTapToZoomInEnabled(doubleTapToZoomInEnabled: Boolean?): Builder {
      this.doubleTapToZoomInEnabled = doubleTapToZoomInEnabled
      return this
    }

    /**
     * Set doubleTouchToZoomOutEnabled
     * Whether single tapping the map with two touches results in a zoom-out animation.
     *
     * @param doubleTouchToZoomOutEnabled doubleTouchToZoomOutEnabled
     * @return Builder
     */
    public fun setDoubleTouchToZoomOutEnabled(doubleTouchToZoomOutEnabled: Boolean?): Builder {
      this.doubleTouchToZoomOutEnabled = doubleTouchToZoomOutEnabled
      return this
    }

    /**
     * Set quickZoomEnabled
     * Whether the quick zoom gesture is enabled.
     *
     * @param quickZoomEnabled quickZoomEnabled
     * @return Builder
     */
    public fun setQuickZoomEnabled(quickZoomEnabled: Boolean?): Builder {
      this.quickZoomEnabled = quickZoomEnabled
      return this
    }

    /**
     * Set focalPoint
     * By default, gestures rotate and zoom around the center of the gesture. Set this property to
     * rotate and zoom around a fixed point instead.
     *
     * @param focalPoint focalPoint
     * @return Builder
     */
    public fun setFocalPoint(focalPoint: ScreenCoordinate?): Builder {
      this.focalPoint = focalPoint
      return this
    }

    /**
     * Set pinchToZoomDecelerationEnabled
     * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by
     * default.
     *
     * @param pinchToZoomDecelerationEnabled pinchToZoomDecelerationEnabled
     * @return Builder
     */
    public fun setPinchToZoomDecelerationEnabled(pinchToZoomDecelerationEnabled: Boolean?):
        Builder {
      this.pinchToZoomDecelerationEnabled = pinchToZoomDecelerationEnabled
      return this
    }

    /**
     * Set rotateDecelerationEnabled
     * Whether a deceleration animation following a rotate gesture is enabled. True by default.
     *
     * @param rotateDecelerationEnabled rotateDecelerationEnabled
     * @return Builder
     */
    public fun setRotateDecelerationEnabled(rotateDecelerationEnabled: Boolean?): Builder {
      this.rotateDecelerationEnabled = rotateDecelerationEnabled
      return this
    }

    /**
     * Set scrollDecelerationEnabled
     * Whether a deceleration animation following a scroll gesture is enabled. True by default.
     *
     * @param scrollDecelerationEnabled scrollDecelerationEnabled
     * @return Builder
     */
    public fun setScrollDecelerationEnabled(scrollDecelerationEnabled: Boolean?): Builder {
      this.scrollDecelerationEnabled = scrollDecelerationEnabled
      return this
    }

    /**
     * Set increaseRotateThresholdWhenPinchingToZoom
     * Whether rotate threshold increases when pinching to zoom. true by default.
     *
     * @param increaseRotateThresholdWhenPinchingToZoom increaseRotateThresholdWhenPinchingToZoom
     * @return Builder
     */
    public
        fun setIncreaseRotateThresholdWhenPinchingToZoom(increaseRotateThresholdWhenPinchingToZoom: Boolean?):
        Builder {
      this.increaseRotateThresholdWhenPinchingToZoom = increaseRotateThresholdWhenPinchingToZoom
      return this
    }

    /**
     * Set increasePinchToZoomThresholdWhenRotating
     * Whether pinch to zoom threshold increases when rotating. true by default.
     *
     * @param increasePinchToZoomThresholdWhenRotating increasePinchToZoomThresholdWhenRotating
     * @return Builder
     */
    public
        fun setIncreasePinchToZoomThresholdWhenRotating(increasePinchToZoomThresholdWhenRotating: Boolean?):
        Builder {
      this.increasePinchToZoomThresholdWhenRotating = increasePinchToZoomThresholdWhenRotating
      return this
    }

    /**
     * Set zoomAnimationAmount
     * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or
     * double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
     *
     * @param zoomAnimationAmount zoomAnimationAmount
     * @return Builder
     */
    public fun setZoomAnimationAmount(zoomAnimationAmount: Float?): Builder {
      this.zoomAnimationAmount = zoomAnimationAmount
      return this
    }

    /**
     * Set pinchScrollEnabled
     * Whether pan is enabled for the pinch gesture.
     *
     * @param pinchScrollEnabled pinchScrollEnabled
     * @return Builder
     */
    public fun setPinchScrollEnabled(pinchScrollEnabled: Boolean?): Builder {
      this.pinchScrollEnabled = pinchScrollEnabled
      return this
    }

    /**
     * Returns a [GesturesSettings] reference to the object being constructed by the builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return GesturesSettings
     */
    public fun build(): GesturesSettings {
      if (rotateEnabled==null) {
      	throw IllegalArgumentException("""Null rotateEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (pinchToZoomEnabled==null) {
      	throw IllegalArgumentException("""Null pinchToZoomEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (scrollEnabled==null) {
      	throw IllegalArgumentException("""Null scrollEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (simultaneousRotateAndPinchToZoomEnabled==null) {
      	throw IllegalArgumentException("""Null simultaneousRotateAndPinchToZoomEnabled found when
          building GesturesSettings.""".trimIndent())
      }
      if (pitchEnabled==null) {
      	throw IllegalArgumentException("""Null pitchEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (scrollMode==null) {
      	throw IllegalArgumentException("""Null scrollMode found when building
          GesturesSettings.""".trimIndent())
      }
      if (doubleTapToZoomInEnabled==null) {
      	throw IllegalArgumentException("""Null doubleTapToZoomInEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (doubleTouchToZoomOutEnabled==null) {
      	throw IllegalArgumentException("""Null doubleTouchToZoomOutEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (quickZoomEnabled==null) {
      	throw IllegalArgumentException("""Null quickZoomEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (pinchToZoomDecelerationEnabled==null) {
      	throw IllegalArgumentException("""Null pinchToZoomDecelerationEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (rotateDecelerationEnabled==null) {
      	throw IllegalArgumentException("""Null rotateDecelerationEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (scrollDecelerationEnabled==null) {
      	throw IllegalArgumentException("""Null scrollDecelerationEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      if (increaseRotateThresholdWhenPinchingToZoom==null) {
      	throw IllegalArgumentException("""Null increaseRotateThresholdWhenPinchingToZoom found when
          building GesturesSettings.""".trimIndent())
      }
      if (increasePinchToZoomThresholdWhenRotating==null) {
      	throw IllegalArgumentException("""Null increasePinchToZoomThresholdWhenRotating found when
          building GesturesSettings.""".trimIndent())
      }
      if (zoomAnimationAmount==null) {
      	throw IllegalArgumentException("""Null zoomAnimationAmount found when building
          GesturesSettings.""".trimIndent())
      }
      if (pinchScrollEnabled==null) {
      	throw IllegalArgumentException("""Null pinchScrollEnabled found when building
          GesturesSettings.""".trimIndent())
      }
      return GesturesSettings(rotateEnabled!!, pinchToZoomEnabled!!, scrollEnabled!!,
          simultaneousRotateAndPinchToZoomEnabled!!, pitchEnabled!!, scrollMode!!,
          doubleTapToZoomInEnabled!!, doubleTouchToZoomOutEnabled!!, quickZoomEnabled!!, focalPoint,
          pinchToZoomDecelerationEnabled!!, rotateDecelerationEnabled!!,
          scrollDecelerationEnabled!!, increaseRotateThresholdWhenPinchingToZoom!!,
          increasePinchToZoomThresholdWhenRotating!!, zoomAnimationAmount!!, pinchScrollEnabled!!)
    }
  }
}

/**
 * Creates a [GesturesSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return GesturesSettings
 */
@JvmSynthetic
public fun GesturesSettings(initializer: GesturesSettings.Builder.() -> Unit): GesturesSettings =
    GesturesSettings.Builder().apply(initializer).build()
