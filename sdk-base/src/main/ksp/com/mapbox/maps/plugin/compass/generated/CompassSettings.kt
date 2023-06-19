@file:Suppress("RedundantVisibilityModifier")

package com.mapbox.maps.plugin.compass.generated

import android.os.Parcelable
import android.view.Gravity
import com.mapbox.maps.ImageHolder
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Float
import kotlin.Int
import kotlin.Unit
import kotlin.jvm.JvmSynthetic
import kotlinx.parcelize.Parcelize

/**
 * Shows the compass on the map.
 */
@Parcelize
public class CompassSettings private constructor(
  /**
   * Whether the compass is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Defines where the compass is positioned on the map
   */
  public val position: Int,
  /**
   * Defines the margin to the left that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginLeft: Float,
  /**
   * Defines the margin to the top that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginTop: Float,
  /**
   * Defines the margin to the right that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * Defines the margin to the bottom that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginBottom: Float,
  /**
   * The alpha channel value of the compass image
   */
  public val opacity: Float,
  /**
   * The clockwise rotation value in degrees of the compass.
   */
  public val rotation: Float,
  /**
   * Whether the compass is displayed.
   */
  public val visibility: Boolean,
  /**
   * Whether the compass fades out to invisible when facing north direction.
   */
  public val fadeWhenFacingNorth: Boolean,
  /**
   * Whether the compass can be clicked and click events can be registered.
   */
  public val clickable: Boolean,
  /**
   * The compass image, the visual representation of the compass.
   */
  public val image: ImageHolder?
) : Parcelable {
  /**
   * Overloaded toString function.
   */
  public override fun toString() = """CompassSettings(enabled=$enabled, position=$position,
      marginLeft=$marginLeft, marginTop=$marginTop, marginRight=$marginRight,
      marginBottom=$marginBottom, opacity=$opacity, rotation=$rotation, visibility=$visibility,
      fadeWhenFacingNorth=$fadeWhenFacingNorth, clickable=$clickable, image=$image)""".trimIndent()

  /**
   * Overloaded equals function.
   */
  public override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    other as CompassSettings
    return enabled == other.enabled && position == other.position &&
        marginLeft.compareTo(other.marginLeft) == 0 && marginTop.compareTo(other.marginTop) == 0 &&
        marginRight.compareTo(other.marginRight) == 0 &&
        marginBottom.compareTo(other.marginBottom) == 0 && opacity.compareTo(other.opacity) == 0 &&
        rotation.compareTo(other.rotation) == 0 && visibility == other.visibility &&
        fadeWhenFacingNorth == other.fadeWhenFacingNorth && clickable == other.clickable &&
        image == other.image
  }

  /**
   * Overloaded hashCode function based on all class properties.
   */
  public override fun hashCode(): Int = Objects.hash(enabled, position, marginLeft, marginTop,
      marginRight, marginBottom, opacity, rotation, visibility, fadeWhenFacingNorth, clickable,
      image)

  /**
   * Convert to Builder allowing to change class properties.
   */
  public fun toBuilder(): Builder = Builder() .setEnabled(enabled) .setPosition(position)
      .setMarginLeft(marginLeft) .setMarginTop(marginTop) .setMarginRight(marginRight)
      .setMarginBottom(marginBottom) .setOpacity(opacity) .setRotation(rotation)
      .setVisibility(visibility) .setFadeWhenFacingNorth(fadeWhenFacingNorth)
      .setClickable(clickable) .setImage(image)

  /**
   * Composes and builds a [CompassSettings] object.
   *
   * This is a concrete implementation of the builder design pattern.
   */
  public class Builder {
    /**
     * Whether the compass is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean = true

    /**
     * Defines where the compass is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int = Gravity.TOP or Gravity.END

    /**
     * Defines the margin to the left that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float = 4f

    /**
     * Defines the margin to the top that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float = 4f

    /**
     * Defines the margin to the right that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float = 4f

    /**
     * Defines the margin to the bottom that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float = 4f

    /**
     * The alpha channel value of the compass image
     */
    @set:JvmSynthetic
    public var opacity: Float = 1f

    /**
     * The clockwise rotation value in degrees of the compass.
     */
    @set:JvmSynthetic
    public var rotation: Float = 0f

    /**
     * Whether the compass is displayed.
     */
    @set:JvmSynthetic
    public var visibility: Boolean = true

    /**
     * Whether the compass fades out to invisible when facing north direction.
     */
    @set:JvmSynthetic
    public var fadeWhenFacingNorth: Boolean = true

    /**
     * Whether the compass can be clicked and click events can be registered.
     */
    @set:JvmSynthetic
    public var clickable: Boolean = true

    /**
     * The compass image, the visual representation of the compass.
     */
    @set:JvmSynthetic
    public var image: ImageHolder? = null

    /**
     * Setter for enabled: whether the compass is visible on the map.
     *
     * @param enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Setter for position: defines where the compass is positioned on the map.
     *
     * @param position
     * @return Builder
     */
    public fun setPosition(position: Int): Builder {
      this.position = position
      return this
    }

    /**
     * Setter for marginLeft: defines the margin to the left that the compass icon honors. This
     * property is specified in pixels.
     *
     * @param marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Setter for marginTop: defines the margin to the top that the compass icon honors. This
     * property is specified in pixels.
     *
     * @param marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Setter for marginRight: defines the margin to the right that the compass icon honors. This
     * property is specified in pixels.
     *
     * @param marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Setter for marginBottom: defines the margin to the bottom that the compass icon honors. This
     * property is specified in pixels.
     *
     * @param marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Setter for opacity: the alpha channel value of the compass image.
     *
     * @param opacity
     * @return Builder
     */
    public fun setOpacity(opacity: Float): Builder {
      this.opacity = opacity
      return this
    }

    /**
     * Setter for rotation: the clockwise rotation value in degrees of the compass.
     *
     * @param rotation
     * @return Builder
     */
    public fun setRotation(rotation: Float): Builder {
      this.rotation = rotation
      return this
    }

    /**
     * Setter for visibility: whether the compass is displayed.
     *
     * @param visibility
     * @return Builder
     */
    public fun setVisibility(visibility: Boolean): Builder {
      this.visibility = visibility
      return this
    }

    /**
     * Setter for fadeWhenFacingNorth: whether the compass fades out to invisible when facing north
     * direction.
     *
     * @param fadeWhenFacingNorth
     * @return Builder
     */
    public fun setFadeWhenFacingNorth(fadeWhenFacingNorth: Boolean): Builder {
      this.fadeWhenFacingNorth = fadeWhenFacingNorth
      return this
    }

    /**
     * Setter for clickable: whether the compass can be clicked and click events can be registered.
     *
     * @param clickable
     * @return Builder
     */
    public fun setClickable(clickable: Boolean): Builder {
      this.clickable = clickable
      return this
    }

    /**
     * Setter for image: the compass image, the visual representation of the compass.
     *
     * @param image
     * @return Builder
     */
    public fun setImage(image: ImageHolder?): Builder {
      this.image = image
      return this
    }

    /**
     * Returns a [CompassSettings] reference to the object being constructed by the builder.
     *
     * @return CompassSettings
     */
    public fun build(): CompassSettings = CompassSettings(enabled, position, marginLeft, marginTop,
        marginRight, marginBottom, opacity, rotation, visibility, fadeWhenFacingNorth, clickable,
        image)
  }
}

/**
 * Creates a [CompassSettings] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return CompassSettings
 */
@JvmSynthetic
public fun CompassSettings(initializer: CompassSettings.Builder.() -> Unit): CompassSettings =
    CompassSettings.Builder().apply(initializer).build()
