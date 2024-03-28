package com.mapbox.maps.extension.compose.ornaments.compass

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.coroutine.cameraChangedEvents
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.animation.camera
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.math.abs

/**
 * A [MapCompassScope] provides a scope for adding [Compass] ornament.
 */
@MapboxMapScopeMarker
@Immutable
@MapboxExperimental
public class MapCompassScope internal constructor(
  private val mapView: MapView,
  private val boxScope: BoxScope
) {

  /**
   * Add a [Compass] ornament to the map, the direction of the compass will follow the map camera's bearing,
   * when the compass is clicked, the map camera's bearing will be reset to face north.
   *
   * By default, the [Compass] will be placed to the [Alignment.TopEnd] of the map with padding of 4dp.
   *
   * @param modifier Modifier to be applied to the [Compass].
   * @param contentPadding The default padding applied to the [Compass], paddings from [modifier] will be applied on top of this default padding.
   * @param alignment The alignment of the [Compass] within the Map.
   * @param fadeWhenFacingNorth Whether the compass fades out to invisible when facing north direction.
   * @param resetToNorthUponClick Whether the map camera bearing should be reset to 0 when the compass is clicked.
   * @param content The composable to draw the compass.
   */
  @Composable
  @MapboxExperimental
  public fun Compass(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    alignment: Alignment = Alignment.TopEnd,
    fadeWhenFacingNorth: Boolean = true,
    resetToNorthUponClick: Boolean = true,
    content: @Composable () -> Unit = {
      Image(
        painter = painterResource(id = R.drawable.mapbox_compass_icon),
        contentDescription = "Mapbox Compass"
      )
    }
  ) {
    var compassBearing by remember {
      mutableStateOf(0f)
    }
    var compassVisibility by remember {
      mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
      compassBearing = -mapView.mapboxMap.cameraState.bearing.toFloat()
      mapView.mapboxMap.cameraChangedEvents
        .map { it.cameraState.bearing.toFloat() }
        .distinctUntilChanged { old, new ->
          abs(old - new) < 0.1
        }
        .collect { cameraBearing ->
          compassBearing = -cameraBearing
          compassVisibility = !(fadeWhenFacingNorth && isFacingNorth(compassBearing))
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    AnimatedVisibility(
      visible = compassVisibility,
      enter = fadeIn(animationSpec = tween(durationMillis = 500)),
      exit = fadeOut(animationSpec = tween(durationMillis = 500, delayMillis = 500)),
      modifier = with(boxScope) {
          Modifier
              .padding(contentPadding)
              .then(modifier)
              .align(alignment)
              .rotate(compassBearing)
              .clickable(
                  interactionSource = interactionSource,
                  indication = null
              ) {
                  if (resetToNorthUponClick) {
                      // Reset bearing to 0 and set the animation owner to GESTURES so that it can interrupt current viewport.
                      mapView.camera.easeTo(
                          cameraOptions { bearing(0.0) },
                          MapAnimationOptions.mapAnimationOptions {
                              owner(
                                  MapAnimationOwnerRegistry.GESTURES
                              )
                          }
                      )
                  }
              }
      }
    ) {
      content()
    }
  }

  private fun isFacingNorth(rotation: Float): Boolean {
    // increase range of facing north to more than only 0.0
    return with(abs(rotation)) {
      this >= 359.0 || this <= 1.0
    }
  }
}