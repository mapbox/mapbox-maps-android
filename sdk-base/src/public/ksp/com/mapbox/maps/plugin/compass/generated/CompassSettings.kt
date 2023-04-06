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
   * Enabled
   * Whether the compass is visible on the map.
   */
  public val enabled: Boolean,
  /**
   * Position
   * Defines where the compass is positioned on the map
   */
  public val position: Int,
  /**
   * MarginLeft
   * Defines the margin to the left that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginLeft: Float,
  /**
   * MarginTop
   * Defines the margin to the top that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginTop: Float,
  /**
   * MarginRight
   * Defines the margin to the right that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginRight: Float,
  /**
   * MarginBottom
   * Defines the margin to the bottom that the compass icon honors. This property is specified in
   * pixels.
   */
  public val marginBottom: Float,
  /**
   * Opacity
   * The alpha channel value of the compass image
   */
  public val opacity: Float,
  /**
   * Rotation
   * The clockwise rotation value in degrees of the compass.
   */
  public val rotation: Float,
  /**
   * Visibility
   * Whether the compass is displayed.
   */
  public val visibility: Boolean,
  /**
   * FadeWhenFacingNorth
   * Whether the compass fades out to invisible when facing north direction.
   */
  public val fadeWhenFacingNorth: Boolean,
  /**
   * Clickable
   * Whether the compass can be clicked and click events can be registered.
   */
  public val clickable: Boolean,
  /**
   * Image
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
        marginLeft == other.marginLeft && marginTop == other.marginTop &&
        marginRight == other.marginRight && marginBottom == other.marginBottom &&
        opacity == other.opacity && rotation == other.rotation && visibility == other.visibility &&
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
     * Enabled
     * Whether the compass is visible on the map.
     */
    @set:JvmSynthetic
    public var enabled: Boolean? = true

    /**
     * Position
     * Defines where the compass is positioned on the map
     */
    @set:JvmSynthetic
    public var position: Int? = Gravity.TOP or Gravity.END

    /**
     * MarginLeft
     * Defines the margin to the left that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginLeft: Float? = 4f

    /**
     * MarginTop
     * Defines the margin to the top that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginTop: Float? = 4f

    /**
     * MarginRight
     * Defines the margin to the right that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginRight: Float? = 4f

    /**
     * MarginBottom
     * Defines the margin to the bottom that the compass icon honors. This property is specified in
     * pixels.
     */
    @set:JvmSynthetic
    public var marginBottom: Float? = 4f

    /**
     * Opacity
     * The alpha channel value of the compass image
     */
    @set:JvmSynthetic
    public var opacity: Float? = 1f

    /**
     * Rotation
     * The clockwise rotation value in degrees of the compass.
     */
    @set:JvmSynthetic
    public var rotation: Float? = 0f

    /**
     * Visibility
     * Whether the compass is displayed.
     */
    @set:JvmSynthetic
    public var visibility: Boolean? = true

    /**
     * FadeWhenFacingNorth
     * Whether the compass fades out to invisible when facing north direction.
     */
    @set:JvmSynthetic
    public var fadeWhenFacingNorth: Boolean? = true

    /**
     * Clickable
     * Whether the compass can be clicked and click events can be registered.
     */
    @set:JvmSynthetic
    public var clickable: Boolean? = true

    /**
     * Image
     * The compass image, the visual representation of the compass.
     */
    @set:JvmSynthetic
    public var image: ImageHolder? = null

    /**
     * Set enabled
     * Whether the compass is visible on the map.
     *
     * @param enabled enabled
     * @return Builder
     */
    public fun setEnabled(enabled: Boolean?): Builder {
      this.enabled = enabled
      return this
    }

    /**
     * Set position
     * Defines where the compass is positioned on the map
     *
     * @param position position
     * @return Builder
     */
    public fun setPosition(position: Int?): Builder {
      this.position = position
      return this
    }

    /**
     * Set marginLeft
     * Defines the margin to the left that the compass icon honors. This property is specified in
     * pixels.
     *
     * @param marginLeft marginLeft
     * @return Builder
     */
    public fun setMarginLeft(marginLeft: Float?): Builder {
      this.marginLeft = marginLeft
      return this
    }

    /**
     * Set marginTop
     * Defines the margin to the top that the compass icon honors. This property is specified in
     * pixels.
     *
     * @param marginTop marginTop
     * @return Builder
     */
    public fun setMarginTop(marginTop: Float?): Builder {
      this.marginTop = marginTop
      return this
    }

    /**
     * Set marginRight
     * Defines the margin to the right that the compass icon honors. This property is specified in
     * pixels.
     *
     * @param marginRight marginRight
     * @return Builder
     */
    public fun setMarginRight(marginRight: Float?): Builder {
      this.marginRight = marginRight
      return this
    }

    /**
     * Set marginBottom
     * Defines the margin to the bottom that the compass icon honors. This property is specified in
     * pixels.
     *
     * @param marginBottom marginBottom
     * @return Builder
     */
    public fun setMarginBottom(marginBottom: Float?): Builder {
      this.marginBottom = marginBottom
      return this
    }

    /**
     * Set opacity
     * The alpha channel value of the compass image
     *
     * @param opacity opacity
     * @return Builder
     */
    public fun setOpacity(opacity: Float?): Builder {
      this.opacity = opacity
      return this
    }

    /**
     * Set rotation
     * The clockwise rotation value in degrees of the compass.
     *
     * @param rotation rotation
     * @return Builder
     */
    public fun setRotation(rotation: Float?): Builder {
      this.rotation = rotation
      return this
    }

    /**
     * Set visibility
     * Whether the compass is displayed.
     *
     * @param visibility visibility
     * @return Builder
     */
    public fun setVisibility(visibility: Boolean?): Builder {
      this.visibility = visibility
      return this
    }

    /**
     * Set fadeWhenFacingNorth
     * Whether the compass fades out to invisible when facing north direction.
     *
     * @param fadeWhenFacingNorth fadeWhenFacingNorth
     * @return Builder
     */
    public fun setFadeWhenFacingNorth(fadeWhenFacingNorth: Boolean?): Builder {
      this.fadeWhenFacingNorth = fadeWhenFacingNorth
      return this
    }

    /**
     * Set clickable
     * Whether the compass can be clicked and click events can be registered.
     *
     * @param clickable clickable
     * @return Builder
     */
    public fun setClickable(clickable: Boolean?): Builder {
      this.clickable = clickable
      return this
    }

    /**
     * Set image
     * The compass image, the visual representation of the compass.
     *
     * @param image image
     * @return Builder
     */
    public fun setImage(image: ImageHolder?): Builder {
      this.image = image
      return this
    }

    /**
     * Returns a [CompassSettings] reference to the object being constructed by the builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return CompassSettings
     */
    public fun build(): CompassSettings {
      if (enabled==null) {
      	throw IllegalArgumentException("""Null enabled found when building
          CompassSettings.""".trimIndent())
      }
      if (position==null) {
      	throw IllegalArgumentException("""Null position found when building
          CompassSettings.""".trimIndent())
      }
      if (marginLeft==null) {
      	throw IllegalArgumentException("""Null marginLeft found when building
          CompassSettings.""".trimIndent())
      }
      if (marginTop==null) {
      	throw IllegalArgumentException("""Null marginTop found when building
          CompassSettings.""".trimIndent())
      }
      if (marginRight==null) {
      	throw IllegalArgumentException("""Null marginRight found when building
          CompassSettings.""".trimIndent())
      }
      if (marginBottom==null) {
      	throw IllegalArgumentException("""Null marginBottom found when building
          CompassSettings.""".trimIndent())
      }
      if (opacity==null) {
      	throw IllegalArgumentException("""Null opacity found when building
          CompassSettings.""".trimIndent())
      }
      if (rotation==null) {
      	throw IllegalArgumentException("""Null rotation found when building
          CompassSettings.""".trimIndent())
      }
      if (visibility==null) {
      	throw IllegalArgumentException("""Null visibility found when building
          CompassSettings.""".trimIndent())
      }
      if (fadeWhenFacingNorth==null) {
      	throw IllegalArgumentException("""Null fadeWhenFacingNorth found when building
          CompassSettings.""".trimIndent())
      }
      if (clickable==null) {
      	throw IllegalArgumentException("""Null clickable found when building
          CompassSettings.""".trimIndent())
      }
      return CompassSettings(enabled!!, position!!, marginLeft!!, marginTop!!, marginRight!!,
          marginBottom!!, opacity!!, rotation!!, visibility!!, fadeWhenFacingNorth!!, clickable!!,
          image)
    }
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
