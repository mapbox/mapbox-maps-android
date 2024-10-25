package com.mapbox.maps.renderer.widget

import com.mapbox.maps.MapboxExperimental
import java.util.Objects

/**
 * Specifies widget position relative to the map.
 *
 * @property horizontalAlignment the horizontal position relative to the map.
 * @property verticalAlignment the vertical position relative to the map.
 * @property offsetX the horizontal offset in pixels towards the right of the map.
 * @property offsetY the vertical offset in pixels towards the bottom of the map.
 */
@MapboxExperimental
class WidgetPosition private constructor(
  val horizontalAlignment: Horizontal,
  val verticalAlignment: Vertical,
  val offsetX: Float,
  val offsetY: Float
) {
  /**
   * Deprecated constructor for WidgetPosition.
   *
   * @param horizontal the horizontal position relative to the map.
   * @param vertical the vertical position relative to the map.
   */
  @Deprecated(
    message = "Direct constructor of WidgetPosition is deprecated, and might be removed in future releases.",
    replaceWith = ReplaceWith("WidgetPosition.Builder()")
  )
  constructor(horizontal: Horizontal, vertical: Vertical) : this(horizontal, vertical, 0f, 0f)

  /**
   * The horizontal position relative to the map.
   */
  @Deprecated(
    message = "horizontal has been renamed to horizontalAlignment, and might be removed in future releases.",
    replaceWith = ReplaceWith("horizontalAlignment")
  )
  val horizontal = horizontalAlignment

  /**
   * The vertical position relative to the map.
   */
  @Deprecated(
    message = "vertical has been renamed to verticalAlignment, and might be removed in future releases.",
    replaceWith = ReplaceWith("verticalAlignment")
  )
  val vertical = verticalAlignment

  /**
   * Returns a String for the object.
   */
  override fun toString(): String =
    "WidgetPosition(horizontalAlignment=$horizontalAlignment,verticalAlignment=$verticalAlignment,offsetX=$offsetX,offsetY=$offsetY)"

  /**
   * Indicates whether some other object is "equal to" this one.
   */
  override fun equals(other: Any?): Boolean = other is WidgetPosition &&
    horizontalAlignment == other.horizontalAlignment &&
    verticalAlignment == other.verticalAlignment &&
    offsetX.compareTo(other.offsetX) == 0 &&
    offsetY.compareTo(other.offsetY) == 0

  /**
   * Returns a hash code value for the object.
   */
  override fun hashCode(): Int = Objects.hash(
    horizontalAlignment,
    verticalAlignment,
    offsetX,
    offsetY
  )

  /**
   * Builder for WidgetPosition class.
   *
   * @property horizontalAlignment the horizontal position relative to the map, defaults to [Horizontal.LEFT].
   * @property verticalAlignment the vertical position relative to the map, defaults to [Vertical.TOP].
   * @property offsetX the horizontal offset in pixels towards the right of the map, defaults to 0.
   * @property offsetY the vertical offset in pixels towards the bottom of the map, defaults to 0.
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
    fun setHorizontalAlignment(horizontalAlignment: Horizontal): Builder =
      apply { this.horizontalAlignment = horizontalAlignment }

    /**
     * Set the vertical position, defaults to [Vertical.TOP].
     */
    fun setVerticalAlignment(verticalAlignment: Vertical): Builder =
      apply { this.verticalAlignment = verticalAlignment }

    /**
     * Set the horizontal offset in pixels towards the right of the map, defaults to 0.
     */
    fun setOffsetX(offsetX: Float): Builder = apply { this.offsetX = offsetX }

    /**
     * Set the vertical offset in pixels towards the bottom of the map, defaults to 0.
     */
    fun setOffsetY(offsetY: Float): Builder = apply { this.offsetY = offsetY }

    /**
     * Build the [WidgetPosition] from the current settings.
     */
    fun build(): WidgetPosition =
      WidgetPosition(horizontalAlignment, verticalAlignment, offsetX, offsetY)
  }

  /**
   * Returns a builder that created the [WidgetPosition].
   */
  fun toBuilder(): Builder = Builder()
    .setHorizontalAlignment(horizontalAlignment)
    .setVerticalAlignment(verticalAlignment)
    .setOffsetX(offsetX)
    .setOffsetY(offsetY)

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
}

/**
 * Creates a [WidgetPosition] through a DSL builder.
 *
 * @param initializer the initialisation block
 * @return [WidgetPosition]
 */
@MapboxExperimental
@JvmSynthetic
fun WidgetPosition(initializer: WidgetPosition.Builder.() -> Unit): WidgetPosition {
  return WidgetPosition.Builder().apply(initializer).build()
}