package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import java.util.*

/**
 * Initialisation options for location component to allow multiple instances
 * of LocationComponent.
 */
public class LocationComponentInitOptions private constructor(
  /**
   * The layer id of the location indicator layer used to draw the 2d puck.
   */
  public val puck2DLayerId: String,
  /**
   * The layer id of the model layer used to draw the 3d puck.
   */
  public val puck3DLayerId: String,
  /**
   * The source id of the model layer used to draw the 3d puck.
   */
  public val puck3DSourceId: String,
  /**
   * The top icon image id for the 2d puck.
   */
  public val topIconImageId: String,
  /**
   * The shadow icon image id for the 2d puck.
   */
  public val shadowIconImageId: String,
  /**
   * The bearing icon image id for the 2d puck.
   */
  public val bearingIconImageId: String
) {
  /**
   * Convert the LocationComponentInitOptions to a String.
   */
  public override fun toString() =
    "LocationComponentInitOptions(puck2DLayerId=$puck2DLayerId,puck3DLayerId=$puck3DLayerId, puck3DSourceId=$puck3DSourceId, topIconImageId=$topIconImageId,shadowIconImageId=$shadowIconImageId, bearingIconImageId=$bearingIconImageId)"

  /**
   * Compares two LocationComponentOptions.
   */
  public override fun equals(other: Any?): Boolean = other is LocationComponentInitOptions &&
    puck2DLayerId == other.puck2DLayerId &&
    puck3DLayerId == other.puck3DLayerId &&
    puck3DSourceId == other.puck3DSourceId &&
    topIconImageId == other.topIconImageId &&
    shadowIconImageId == other.shadowIconImageId &&
    bearingIconImageId == other.bearingIconImageId

  /**
   * The hashcode of the LocationComponentOptions.
   */
  public override fun hashCode(): Int = Objects.hash(
    puck2DLayerId, puck3DLayerId, puck3DSourceId,
    topIconImageId, shadowIconImageId, bearingIconImageId
  )

  /**
   * Convert LocationComponentOptions to a Builder.
   */
  public fun toBuilder(): Builder = Builder()
    .setPuck2DLayerId(this.puck2DLayerId)
    .setPuck3DLayerId(this.puck3DLayerId)
    .setPuck3DSourceId(this.puck3DSourceId)
    .setTopIconImageId(this.topIconImageId)
    .setShadowIconImageId(this.shadowIconImageId)
    .setBearingIconImageId(this.bearingIconImageId)

  /**
   * Composes and builds a [LocationComponentInitOptions] object.
   *
   * This is a concrete implementation of the builder design pattern.
   *
   * @property
   */
  public class Builder {
    /**
     * The layer id of the location indicator layer used to draw the 2d puck.
     */
    @set:JvmSynthetic
    public var puck2DLayerId: String = LOCATION_INDICATOR_LAYER

    /**
     * The layer id of the model layer used to draw the 3d puck.
     */
    @set:JvmSynthetic
    public var puck3DLayerId: String = MODEL_LAYER

    /**
     * The source id of the model layer used to draw the 3d puck.
     */
    @set:JvmSynthetic
    public var puck3DSourceId: String = MODEL_SOURCE

    /**
     * The top icon image id for the 2d puck.
     */
    @set:JvmSynthetic
    public var topIconImageId: String = TOP_ICON

    /**
     * The shadow icon image id for the 2d puck.
     */
    @set:JvmSynthetic
    public var shadowIconImageId: String = SHADOW_ICON

    /**
     * The bearing icon image id for the 2d puck.
     */
    @set:JvmSynthetic
    public var bearingIconImageId: String = BEARING_ICON

    /**
     * Set puck2DLayerId
     *
     * @param puck2DLayerId puck2DLayerId
     * @return Builder
     */
    public fun setPuck2DLayerId(puck2DLayerId: String): Builder {
      this.puck2DLayerId = puck2DLayerId
      return this
    }

    /**
     * Set puck3DLayerId
     *
     * @param puck3DLayerId puck3DLayerId
     * @return Builder
     */
    public fun setPuck3DLayerId(puck3DLayerId: String): Builder {
      this.puck3DLayerId = puck3DLayerId
      return this
    }

    /**
     * Set puck3DSourceId
     *
     * @param puck3DSourceId puck3DSourceId
     * @return Builder
     */
    public fun setPuck3DSourceId(puck3DSourceId: String): Builder {
      this.puck3DSourceId = puck3DSourceId
      return this
    }

    /**
     * Set topIconImageId
     *
     * @param topIconImageId topIconImageId
     * @return Builder
     */
    public fun setTopIconImageId(topIconImageId: String): Builder {
      this.topIconImageId = topIconImageId
      return this
    }

    /**
     * Set shadowIconImageId
     *
     * @param shadowIconImageId shadowIconImageId
     * @return Builder
     */
    public fun setShadowIconImageId(shadowIconImageId: String): Builder {
      this.shadowIconImageId = shadowIconImageId
      return this
    }

    /**
     * Set bearingIconImageId
     *
     * @param bearingIconImageId bearingIconImageId
     * @return Builder
     */
    public fun setBearingIconImageId(bearingIconImageId: String): Builder {
      this.bearingIconImageId = bearingIconImageId
      return this
    }

    /**
     * Returns a [LocationComponentInitOptions] reference to the object being constructed by the
     * builder.
     *
     * Throws an [IllegalArgumentException] when a non-null property wasn't initialised.
     *
     * @return LocationComponentInitOptions
     */
    public fun build(): LocationComponentInitOptions {
      return LocationComponentInitOptions(
        puck2DLayerId, puck3DLayerId, puck3DSourceId,
        topIconImageId, shadowIconImageId, bearingIconImageId
      )
    }
  }

  /**
   * Companion object of [LocationComponentInitOptions].
   */
  companion object {
    private var customLocationComponentCount = 0

    /**
     * Create a unique LocationComponentInitOptions with incremental layer/source/image ids.
     */
    @JvmStatic
    fun getNextUniqueLocationComponentOptions() = LocationComponentInitOptions {
      puck2DLayerId = "custom_location_component_2d_layer_$customLocationComponentCount"
      puck3DLayerId = "custom_location_component_3d_layer_$customLocationComponentCount"
      puck3DSourceId = "custom_location_component_3d_source_$customLocationComponentCount"
      puck3DSourceId = "custom_location_component_top_icon_image_id_$customLocationComponentCount"
      puck3DSourceId =
        "custom_location_component_shadow_icon_image_id_$customLocationComponentCount"
      puck3DSourceId =
        "custom_location_component_bearing_icon_image_id_$customLocationComponentCount"
      customLocationComponentCount++
    }
  }
}

/**
 * Creates a [LocationComponentInitOptions] through a DSL-style builder.
 *
 * @param initializer the initialisation block
 * @return LocationComponentInitOptions
 */
@JvmSynthetic
public fun LocationComponentInitOptions(initializer: LocationComponentInitOptions.Builder.() -> Unit):
  LocationComponentInitOptions = LocationComponentInitOptions.Builder().apply(initializer).build()