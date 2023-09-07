package com.mapbox.maps.extension.compose

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.*
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapPreviewPlaceHolder
import com.mapbox.maps.extension.compose.internal.MapboxMapComposeNode
import com.mapbox.maps.plugin.attribution.generated.AttributionSettings
import com.mapbox.maps.plugin.compass.generated.CompassSettings
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarSettings
import kotlinx.coroutines.awaitCancellation

/**
 * Entry point for adding a Mapbox Map instance to the Jetpack Compose UI.
 *
 * @param modifier Modifier to be applied to the Mapbox map.
 * @param mapInitOptionsFactory Defines the initialisation configurations factory for a [MapboxMap]. It can only be set once and not mutable after the initialisation. Mutating the [MapInitOptions] during recomposition will result in a [IllegalStateException].
 * @param attributionSettings Settings for showing the attribution icon on the map.
 * @param compassSettings Settings for showing the compass icon on the map.
 * @param gesturesSettings Gesture configuration allows to control the user touch interaction.
 * @param locationComponentSettings Settings for showing a location puck on the map.
 * @param logoSettings Settings for showing the Mapbox logo on the map.
 * @param scaleBarSettings Settings for showing the scale bar on the map.
 * @param mapViewportState A state object that can be hoisted to control and observe the map's camera state. A [MapViewportState] may only be used by a single [MapboxMap] composable at a time as it reflects instance state for a single view of a map.
 * @param onMapClickListener Callback to be invoked when the user clicks on the map view.
 * @param onMapLongClickListener Callback to be invoked when the user long clicks on the map view.
 * @param content The content of the map.
 */
@Composable
@MapboxExperimental
public fun MapboxMap(
  modifier: Modifier = Modifier,
  mapEvents: MapEvents? = null,
  mapInitOptionsFactory: (Context) -> MapInitOptions = { context -> MapInitOptions(context) },
  attributionSettings: AttributionSettings = DefaultSettingsProvider.defaultAttributionSettings(
    LocalContext.current
  ),
  compassSettings: CompassSettings = DefaultSettingsProvider.defaultCompassSettings(
    LocalContext.current
  ),
  gesturesSettings: GesturesSettings = DefaultSettingsProvider.defaultGesturesSettings,
  locationComponentSettings: LocationComponentSettings = DefaultSettingsProvider.defaultLocationComponentSettings(
    LocalContext.current
  ),
  logoSettings: LogoSettings = DefaultSettingsProvider.defaultLogoSettings(
    LocalContext.current
  ),
  scaleBarSettings: ScaleBarSettings = DefaultSettingsProvider.defaultScaleBarSettings(
    LocalContext.current
  ),
  mapViewportState: MapViewportState = rememberMapViewportState(),
  onMapClickListener: OnMapClickListener = DefaultSettingsProvider.defaultOnClickListener,
  onMapLongClickListener: OnMapLongClickListener = DefaultSettingsProvider.defaultOnLongClickListener,
  content: (@Composable @MapboxMapComposable MapboxMapScope.() -> Unit)? = null
) {
  // display placeholder when in preview mode.
  if (LocalInspectionMode.current) {
    MapPreviewPlaceHolder(modifier)
    return
  }

  lateinit var mapView: MapView

  AndroidView(
    factory = { context ->
      MapView(context, mapInitOptions = mapInitOptionsFactory.invoke(context)).also {
        mapView = it
      }
    },
    modifier = modifier,
  )

  val parentComposition = rememberCompositionContext()
  val currentMapInitOptionsFactory by rememberUpdatedState(mapInitOptionsFactory)
  val currentAttributionSettings by rememberUpdatedState(attributionSettings)
  val currentCompassSettings by rememberUpdatedState(compassSettings)
  val currentGesturesSettings by rememberUpdatedState(gesturesSettings)
  val currentLocationComponentSettings by rememberUpdatedState(locationComponentSettings)
  val currentLogoSettings by rememberUpdatedState(logoSettings)
  val currentScaleBarSettings by rememberUpdatedState(scaleBarSettings)
  val currentMapViewportState by rememberUpdatedState(mapViewportState)
  val currentOnMapClickListener by rememberUpdatedState(onMapClickListener)
  val currentOnMapLongClickListener by rememberUpdatedState(onMapLongClickListener)
  val currentContent by rememberUpdatedState(content)
  val currentMapEvents by rememberUpdatedState(mapEvents)

  LaunchedEffect(Unit) {
    disposingComposition(
      Composition(
        MapApplier(mapView), parentComposition
      ).apply {
        setContent {
          MapboxMapComposeNode(
            currentMapInitOptionsFactory,
            currentAttributionSettings,
            currentCompassSettings,
            currentGesturesSettings,
            currentLocationComponentSettings,
            currentLogoSettings,
            currentScaleBarSettings,
            currentMapViewportState,
            currentOnMapClickListener,
            currentOnMapLongClickListener,
            currentMapEvents,

          )
          currentContent?.let { MapboxMapScope.it() }
        }
      }
    )
  }
}

private suspend inline fun disposingComposition(composition: Composition) {
  try {
    awaitCancellation()
  } finally {
    composition.dispose()
  }
}