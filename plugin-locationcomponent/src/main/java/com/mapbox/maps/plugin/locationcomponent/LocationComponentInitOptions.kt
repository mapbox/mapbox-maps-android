package com.mapbox.maps.plugin.locationcomponent

import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.BEARING_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.LOCATION_INDICATOR_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_LAYER
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.MODEL_SOURCE
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.SHADOW_ICON
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants.TOP_ICON
import java.util.Objects
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmSynthetic

/**
 * Initialisation options for location component to allow multiple instances
 * of LocationComponent.
 */
public class LocationComponentInitOptions private constructor(
  public val puck2DLayerId: String,
  public val puck3DLayerId: String,
  public val puck3DSourceId: String,
  public val topIconImageId: String,
  public val shadowIconImageId: String,
  public val bearingIconImageId: String
) {
  public override fun toString() =
    "LocationComponentInitOptions(puck2DLayerId=$puck2DLayerId,puck3DLayerId=$puck3DLayerId, puck3DSourceId=$puck3DSourceId, topIconImageId=$topIconImageId,shadowIconImageId=$shadowIconImageId, bearingIconImageId=$bearingIconImageId)"

  public override fun equals(other: Any?): Boolean = other is LocationComponentInitOptions &&
    puck2DLayerId == other.puck2DLayerId &&
    puck3DLayerId == other.puck3DLayerId &&
    puck3DSourceId == other.puck3DSourceId &&
    topIconImageId == other.topIconImageId &&
    shadowIconImageId == other.shadowIconImageId &&
    bearingIconImageId == other.bearingIconImageId

  public override fun hashCode(): Int = Objects.hash(
    puck2DLayerId, puck3DLayerId, puck3DSourceId,
    topIconImageId, shadowIconImageId, bearingIconImageId
  )

  /**
   * Composes and builds a [LocationComponentInitOptions] object.
   *
   * This is a concrete implementation of the builder design pattern.
   *
   * @property
   */
  public class Builder {
    @set:JvmSynthetic
    public var puck2DLayerId: String = LOCATION_INDICATOR_LAYER

    @set:JvmSynthetic
    public var puck3DLayerId: String = MODEL_LAYER

    @set:JvmSynthetic
    public var puck3DSourceId: String = MODEL_SOURCE

    @set:JvmSynthetic
    public var topIconImageId: String = TOP_ICON

    @set:JvmSynthetic
    public var shadowIconImageId: String = SHADOW_ICON

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
}

/**
 * Creates a [LocationComponentInitOptions] through a DSL-style builder.
 *
 * @param initializer the intialisation block
 * @return LocationComponentInitOptions
 */
@JvmSynthetic
public fun LocationComponentInitOptions(initializer: LocationComponentInitOptions.Builder.() -> Unit):
  LocationComponentInitOptions = LocationComponentInitOptions.Builder().apply(initializer).build()