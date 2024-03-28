package com.mapbox.maps.extension.compose.ornaments.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.R

/**
 * A [MapLogoScope] provides a scope for adding [Logo] ornament.
 */
@MapboxMapScopeMarker
@Immutable
@MapboxExperimental
public class MapLogoScope internal constructor(
  private val boxScope: BoxScope
) {

  /**
   * Add a Mapbox [Logo] ornament to the map.
   *
   * Please note that it's **required** to show Mapbox Logo in your map. See [Mapbox ToS](https://www.mapbox.com/legal/tos)
   *
   * By default, the [Logo] will be placed to the [Alignment.BottomStart] of the map with padding of 4dp.
   *
   * @param modifier Modifier to be applied to the [Logo].
   * @param contentPadding The default padding applied to the [Logo], paddings from [modifier] will be applied on top of this default padding.
   * @param alignment The alignment of the [Logo] within the Map.
   */
  @Composable
  @MapboxExperimental
  public fun Logo(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    alignment: Alignment = Alignment.BottomStart
  ) {
    Image(
      modifier = with(boxScope) {
        Modifier
          .padding(contentPadding)
          .then(modifier)
          .align(alignment)
      },
      painter = painterResource(id = R.drawable.mapbox_logo_icon),
      contentDescription = "Mapbox Logo"
    )
  }
}