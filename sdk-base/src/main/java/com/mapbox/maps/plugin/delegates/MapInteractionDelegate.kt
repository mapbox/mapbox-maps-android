package com.mapbox.maps.plugin.delegates

import androidx.annotation.RestrictTo
import com.mapbox.common.Cancelable
import com.mapbox.maps.MapInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.PlatformEventInfo

/**
 * Definition of the interaction delegate needed to allow adding interactions.
 */
@MapboxExperimental
interface MapInteractionDelegate {

  /**
   * Add the map interaction (e.g. clicking / long clicking map features or map surface).
   *
   * @param interaction any concrete implementation of base [MapInteraction].
   *
   * @return [Cancelable] object. Call [Cancelable.cancel] when the interaction is not needed anymore.
   *  Note: destroying the map will cancel all registered interactions.
   */
  fun addInteraction(interaction: MapInteraction): Cancelable

  /**
   * For internal usage.
   */
  @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
  fun dispatch(platformEventInfo: PlatformEventInfo)
}