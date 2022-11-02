package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental
import kotlin.math.abs

/**
 * Specifies widget position relative to the screen.
 *
 * @property horizontalAlignment the horizontal position relative to the screen.
 * @property verticalAlignment the vertical position relative to screen.
 * @property offsetX the horizontal offset in pixels towards the right of the screen.
 * @property offsetY the vertical offset in pixels towards the right of the screen.
 */
@MapboxExperimental
class WidgetPosition private constructor(
  val horizontalAlignment: Horizontal,
  val verticalAlignment: Vertical,
  val offsetX: Float,
  val offsetY: Float
) {
  @Deprecated(
    message = "Direct constructor of WidgetPosition is deprecated, and might be removed in future releases.",
    replaceWith = ReplaceWith("WidgetPosition.Builder()")
  )
  constructor(horizontal: Horizontal, vertical: Vertical) : this(horizontal, vertical, 0f, 0f)

  /**
   * Returns a String for the object.
   */
  override fun toString() =
    "WidgetPosition(horizontalAlignment=$horizontalAlignment,verticalAlignment=$verticalAlignment,offsetX=$offsetX,offsetY=$offsetY"

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?) = other is WidgetPosition &&
    horizontalAlignment == other.horizontalAlignment &&
    verticalAlignment == other.verticalAlignment &&
    abs(offsetX - other.offsetX) < EPS &&
    abs(offsetY - other.offsetY) < EPS

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int {
    var result = 7
    result = 31 * result + horizontalAlignment.hashCode()
    result = 31 * result + verticalAlignment.hashCode()
    result = 31 * result + offsetX.hashCode()
    result = 31 * result + offsetY.hashCode()
    return result
  }

  /**
   * Builder for WidgetPosition class.
   *
   * @property horizontalAlignment the horizontal position relative to the screen, defaults to [Horizontal.LEFT].
   * @property verticalAlignment the vertical position relative to screen, defaults to [Vertical.TOP].
   * @property offsetX the horizontal offset in pixels towards the right of the screen, defaults to 0.
   * @property offsetY the vertical offset in pixels towards the right of the screen, defaults to 0.
   */
  class Builder {
    @set:JvmSynthetic
    var horizontalAlignment: Horizontal = Horizontal.LEFT

    @set:JvmSynthetic
    var verticalAlignment: Vertical = Vertical.TOP

    @set:JvmSynthetic
    var offsetX: Float = 0f

    @set:JvmSynthetic
    var offsetY: Float = 0f

    /**
     * Set the horizontal position, defaults to [Horizontal.LEFT].
     */
    fun setHorizontalAlignment(horizontalAlignment: Horizontal) =
      apply { this.horizontalAlignment = horizontalAlignment }

    /**
     * Set the vertical position, defaults to [Vertical.TOP].
     */
    fun setVerticalAlignment(verticalAlignment: Vertical) =
      apply { this.verticalAlignment = verticalAlignment }

    /**
     * Set the horizontal offset in pixels towards the right of the screen, defaults to 0.
     */
    fun setOffsetX(offsetX: Float) = apply { this.offsetX = offsetX }

    /**
     * Set the vertical offset in pixels towards the right of the screen, defaults to 0.
     */
    fun setOffsetY(offsetY: Float) = apply { this.offsetY = offsetY }

    /**
     * Build the [WidgetPosition] from the current settings.
     */
    fun build() = WidgetPosition(horizontalAlignment, verticalAlignment, offsetX, offsetY)
  }

  /**
   * Returns a builder that created the [WidgetPosition].
   */
  fun toBuilder() =
    Builder().setHorizontalAlignment(horizontalAlignment).setVerticalAlignment(verticalAlignment)
      .setOffsetX(offsetX).setOffsetY(offsetY)


  /**
   * Widget position Horizontal
   */
  enum class Horizontal {
    /**
     * Left Horizontal Position
     */
    LEFT,

    /**
     * Center Horizontal Position
     */
    CENTER,

    /**
     * Right Horizontal Position
     */
    RIGHT,
  }

  /**
   * Widget position Vertical
   */
  enum class Vertical {
    /**
     * Top Vertical Position
     */
    TOP,

    /**
     * Center Vertical Position
     */
    CENTER,

    /**
     * Bottom Vertical Position
     */
    BOTTOM,
  }

  private companion object {
    private const val EPS = 1E-5f
  }
}

/**
 * Creates a [WidgetPosition] through a DSL builder.
 *
 * @param initializer the initialisation block
 * @return [WidgetPosition]
 */
@JvmSynthetic
fun WidgetPosition(initializer: WidgetPosition.Builder.() -> Unit): WidgetPosition {
  return WidgetPosition.Builder().apply(initializer).build()
}