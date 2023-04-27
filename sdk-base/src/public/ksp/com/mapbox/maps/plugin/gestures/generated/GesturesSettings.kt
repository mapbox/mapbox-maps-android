@file:Suppress("RedundantVisibilityModifier")

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
   * Whether the rotate gesture is enabled.
   */
  public val rotateEnabled: Boolean,
  /**
   * Whether the pinch to zoom gesture is enabled.
   */
  public val pinchToZoomEnabled: Boolean,
  /**
   * Whether the single-touch scroll gesture is enabled.
   */
  public val scrollEnabled: Boolean,
  /**
   * Whether rotation is enabled for the pinch to zoom gesture.
   */
  public val simultaneousRotateAndPinchToZoomEnabled: Boolean,
  /**
   * Whether the pitch gesture is enabled.
   */
  public val pitchEnabled: Boolean,
  /**
   * Configures the directions in which the map is allowed to move during a scroll gesture.
   */
  public val scrollMode: ScrollMode,
  /**
   * Whether double tapping the map with one touch results in a zoom-in animation.
   */
  public val doubleTapToZoomInEnabled: Boolean,
  /**
   * Whether single tapping the map with two touches results in a zoom-out animation.
   */
  public val doubleTouchToZoomOutEnabled: Boolean,
  /**
   * Whether the quick zoom gesture is enabled.
   */
  public val quickZoomEnabled: Boolean,
  /**
   * By default, gestures rotate and zoom around the center of the gesture. Set this property to
   * rotate and zoom around a fixed point instead.
   */
  public val focalPoint: ScreenCoordinate?,
  /**
   * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by default.
   */
  public val pinchToZoomDecelerationEnabled: Boolean,
  /**
   * Whether a deceleration animation following a rotate gesture is enabled. True by default.
   */
  public val rotateDecelerationEnabled: Boolean,
  /**
   * Whether a deceleration animation following a scroll gesture is enabled. True by default.
   */
  public val scrollDecelerationEnabled: Boolean,
  /**
   * Whether rotate threshold increases when pinching to zoom. true by default.
   */
  public val increaseRotateThresholdWhenPinchingToZoom: Boolean,
  /**
   * Whether pinch to zoom threshold increases when rotating. true by default.
   */
  public val increasePinchToZoomThresholdWhenRotating: Boolean,
  /**
   * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or
   * double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
   */
  public val zoomAnimationAmount: Float,
  /**
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
        zoomAnimationAmount.compareTo(other.zoomAnimationAmount) == 0 &&
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
     * Whether the rotate gesture is enabled.
     */
    @set:JvmSynthetic
    public var rotateEnabled: Boolean = true

    /**
     * Whether the pinch to zoom gesture is enabled.
     */
    @set:JvmSynthetic
    public var pinchToZoomEnabled: Boolean = true

    /**
     * Whether the single-touch scroll gesture is enabled.
     */
    @set:JvmSynthetic
    public var scrollEnabled: Boolean = true

    /**
     * Whether rotation is enabled for the pinch to zoom gesture.
     */
    @set:JvmSynthetic
    public var simultaneousRotateAndPinchToZoomEnabled: Boolean = true

    /**
     * Whether the pitch gesture is enabled.
     */
    @set:JvmSynthetic
    public var pitchEnabled: Boolean = true

    /**
     * Configures the directions in which the map is allowed to move during a scroll gesture.
     */
    @set:JvmSynthetic
    public var scrollMode: ScrollMode = ScrollMode.HORIZONTAL_AND_VERTICAL

    /**
     * Whether double tapping the map with one touch results in a zoom-in animation.
     */
    @set:JvmSynthetic
    public var doubleTapToZoomInEnabled: Boolean = true

    /**
     * Whether single tapping the map with two touches results in a zoom-out animation.
     */
    @set:JvmSynthetic
    public var doubleTouchToZoomOutEnabled: Boolean = true

    /**
     * Whether the quick zoom gesture is enabled.
     */
    @set:JvmSynthetic
    public var quickZoomEnabled: Boolean = true

    /**
     * By default, gestures rotate and zoom around the center of the gesture. Set this property to
     * rotate and zoom around a fixed point instead.
     */
    @set:JvmSynthetic
    public var focalPoint: ScreenCoordinate? = null

    /**
     * Whether a deceleration animation following a pinch-to-zoom gesture is enabled. True by
     * default.
     */
    @set:JvmSynthetic
    public var pinchToZoomDecelerationEnabled: Boolean = true

    /**
     * Whether a deceleration animation following a rotate gesture is enabled. True by default.
     */
    @set:JvmSynthetic
    public var rotateDecelerationEnabled: Boolean = true

    /**
     * Whether a deceleration animation following a scroll gesture is enabled. True by default.
     */
    @set:JvmSynthetic
    public var scrollDecelerationEnabled: Boolean = true

    /**
     * Whether rotate threshold increases when pinching to zoom. true by default.
     */
    @set:JvmSynthetic
    public var increaseRotateThresholdWhenPinchingToZoom: Boolean = true

    /**
     * Whether pinch to zoom threshold increases when rotating. true by default.
     */
    @set:JvmSynthetic
    public var increasePinchToZoomThresholdWhenRotating: Boolean = true

    /**
     * The amount by which the zoom level increases or decreases during a double-tap-to-zoom-in or
     * double-touch-to-zoom-out gesture. 1.0 by default. Must be positive.
     */
    @set:JvmSynthetic
    public var zoomAnimationAmount: Float = 1f

    /**
     * Whether pan is enabled for the pinch gesture.
     */
    @set:JvmSynthetic
    public var pinchScrollEnabled: Boolean = true

    /**
     * Setter for rotateEnabled: whether the rotate gesture is enabled.
     *
     * @param rotateEnabled
     * @return Builder
     */
    public fun setRotateEnabled(rotateEnabled: Boolean): Builder {
      this.rotateEnabled = rotateEnabled
      return this
    }

    /**
     * Setter for pinchToZoomEnabled: whether the pinch to zoom gesture is enabled.
     *
     * @param pinchToZoomEnabled
     * @return Builder
     */
    public fun setPinchToZoomEnabled(pinchToZoomEnabled: Boolean): Builder {
      this.pinchToZoomEnabled = pinchToZoomEnabled
      return this
    }

    /**
     * Setter for scrollEnabled: whether the single-touch scroll gesture is enabled.
     *
     * @param scrollEnabled
     * @return Builder
     */
    public fun setScrollEnabled(scrollEnabled: Boolean): Builder {
      this.scrollEnabled = scrollEnabled
      return this
    }

    /**
     * Setter for simultaneousRotateAndPinchToZoomEnabled: whether rotation is enabled for the pinch
     * to zoom gesture.
     *
     * @param simultaneousRotateAndPinchToZoomEnabled
     * @return Builder
     */
    public
        fun setSimultaneousRotateAndPinchToZoomEnabled(simultaneousRotateAndPinchToZoomEnabled: Boolean):
        Builder {
      this.simultaneousRotateAndPinchToZoomEnabled = simultaneousRotateAndPinchToZoomEnabled
      return this
    }

    /**
     * Setter for pitchEnabled: whether the pitch gesture is enabled.
     *
     * @param pitchEnabled
     * @return Builder
     */
    public fun setPitchEnabled(pitchEnabled: Boolean): Builder {
      this.pitchEnabled = pitchEnabled
      return this
    }

    /**
     * Setter for scrollMode: configures the directions in which the map is allowed to move during a
     * scroll gesture.
     *
     * @param scrollMode
     * @return Builder
     */
    public fun setScrollMode(scrollMode: ScrollMode): Builder {
      this.scrollMode = scrollMode
      return this
    }

    /**
     * Setter for doubleTapToZoomInEnabled: whether double tapping the map with one touch results in
     * a zoom-in animation.
     *
     * @param doubleTapToZoomInEnabled
     * @return Builder
     */
    public fun setDoubleTapToZoomInEnabled(doubleTapToZoomInEnabled: Boolean): Builder {
      this.doubleTapToZoomInEnabled = doubleTapToZoomInEnabled
      return this
    }

    /**
     * Setter for doubleTouchToZoomOutEnabled: whether single tapping the map with two touches
     * results in a zoom-out animation.
     *
     * @param doubleTouchToZoomOutEnabled
     * @return Builder
     */
    public fun setDoubleTouchToZoomOutEnabled(doubleTouchToZoomOutEnabled: Boolean): Builder {
      this.doubleTouchToZoomOutEnabled = doubleTouchToZoomOutEnabled
      return this
    }

    /**
     * Setter for quickZoomEnabled: whether the quick zoom gesture is enabled.
     *
     * @param quickZoomEnabled
     * @return Builder
     */
    public fun setQuickZoomEnabled(quickZoomEnabled: Boolean): Builder {
      this.quickZoomEnabled = quickZoomEnabled
      return this
    }

    /**
     * Setter for focalPoint: by default, gestures rotate and zoom around the center of the gesture.
     * Set this property to rotate and zoom around a fixed point instead.
     *
     * @param focalPoint
     * @return Builder
     */
    public fun setFocalPoint(focalPoint: ScreenCoordinate?): Builder {
      this.focalPoint = focalPoint
      return this
    }

    /**
     * Setter for pinchToZoomDecelerationEnabled: whether a deceleration animation following a
     * pinch-to-zoom gesture is enabled. True by default.
     *
     * @param pinchToZoomDecelerationEnabled
     * @return Builder
     */
    public fun setPinchToZoomDecelerationEnabled(pinchToZoomDecelerationEnabled: Boolean): Builder {
      this.pinchToZoomDecelerationEnabled = pinchToZoomDecelerationEnabled
      return this
    }

    /**
     * Setter for rotateDecelerationEnabled: whether a deceleration animation following a rotate
     * gesture is enabled. True by default.
     *
     * @param rotateDecelerationEnabled
     * @return Builder
     */
    public fun setRotateDecelerationEnabled(rotateDecelerationEnabled: Boolean): Builder {
      this.rotateDecelerationEnabled = rotateDecelerationEnabled
      return this
    }

    /**
     * Setter for scrollDecelerationEnabled: whether a deceleration animation following a scroll
     * gesture is enabled. True by default.
     *
     * @param scrollDecelerationEnabled
     * @return Builder
     */
    public fun setScrollDecelerationEnabled(scrollDecelerationEnabled: Boolean): Builder {
      this.scrollDecelerationEnabled = scrollDecelerationEnabled
      return this
    }

    /**
     * Setter for increaseRotateThresholdWhenPinchingToZoom: whether rotate threshold increases when
     * pinching to zoom. true by default.
     *
     * @param increaseRotateThresholdWhenPinchingToZoom
     * @return Builder
     */
    public
        fun setIncreaseRotateThresholdWhenPinchingToZoom(increaseRotateThresholdWhenPinchingToZoom: Boolean):
        Builder {
      this.increaseRotateThresholdWhenPinchingToZoom = increaseRotateThresholdWhenPinchingToZoom
      return this
    }

    /**
     * Setter for increasePinchToZoomThresholdWhenRotating: whether pinch to zoom threshold
     * increases when rotating. true by default.
     *
     * @param increasePinchToZoomThresholdWhenRotating
     * @return Builder
     */
    public
        fun setIncreasePinchToZoomThresholdWhenRotating(increasePinchToZoomThresholdWhenRotating: Boolean):
        Builder {
      this.increasePinchToZoomThresholdWhenRotating = increasePinchToZoomThresholdWhenRotating
      return this
    }

    /**
     * Setter for zoomAnimationAmount: the amount by which the zoom level increases or decreases
     * during a double-tap-to-zoom-in or double-touch-to-zoom-out gesture. 1.0 by default. Must be
     * positive.
     *
     * @param zoomAnimationAmount
     * @return Builder
     */
    public fun setZoomAnimationAmount(zoomAnimationAmount: Float): Builder {
      this.zoomAnimationAmount = zoomAnimationAmount
      return this
    }

    /**
     * Setter for pinchScrollEnabled: whether pan is enabled for the pinch gesture.
     *
     * @param pinchScrollEnabled
     * @return Builder
     */
    public fun setPinchScrollEnabled(pinchScrollEnabled: Boolean): Builder {
      this.pinchScrollEnabled = pinchScrollEnabled
      return this
    }

    /**
     * Returns a [GesturesSettings] reference to the object being constructed by the builder.
     *
     * @return GesturesSettings
     */
    public fun build(): GesturesSettings = GesturesSettings(rotateEnabled, pinchToZoomEnabled,
        scrollEnabled, simultaneousRotateAndPinchToZoomEnabled, pitchEnabled, scrollMode,
        doubleTapToZoomInEnabled, doubleTouchToZoomOutEnabled, quickZoomEnabled, focalPoint,
        pinchToZoomDecelerationEnabled, rotateDecelerationEnabled, scrollDecelerationEnabled,
        increaseRotateThresholdWhenPinchingToZoom, increasePinchToZoomThresholdWhenRotating,
        zoomAnimationAmount, pinchScrollEnabled)
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
