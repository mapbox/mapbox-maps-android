package com.mapbox.maps.extension.compose

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.*
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
 */
@Composable
@MapboxExperimental
public fun MapboxMap(
  /**
   * Modifier to be applied to the Mapbox map.
   */
  modifier: Modifier = Modifier,
  /**
   * Defines the initialisation configurations factory for a [MapboxMap].
   *
   * It can only be set once and not mutable after the initialisation. Mutating the [MapInitOptions]
   * during recomposition will result in a [IllegalStateException].
   */
  mapInitOptionsFactory: (Context) -> MapInitOptions = { context -> MapInitOptions(context) },
  /**
   * Settings for showing the attribution icon on the map.
   */
  attributionSettings: AttributionSettings = DefaultSettingsProvider.defaultAttributionSettings(
    LocalContext.current.applicationContext
  ),
  /**
   * Settings for showing the compass icon on the map.
   */
  compassSettings: CompassSettings = DefaultSettingsProvider.defaultCompassSettings(
    LocalContext.current.applicationContext
  ),
  /**
   * Gesture configuration allows to control the user touch interaction.
   */
  gesturesSettings: GesturesSettings = DefaultSettingsProvider.defaultGesturesSettings,
  /**
   * Settings for showing a location puck on the map.
   */
  locationComponentSettings: LocationComponentSettings = DefaultSettingsProvider.defaultLocationComponentSettings(
    LocalContext.current.applicationContext,
  ),
  /**
   * Settings for showing the Mapbox logo on the map.
   */
  logoSettings: LogoSettings = DefaultSettingsProvider.defaultLogoSettings(
    LocalContext.current.applicationContext
  ),
  /**
   * Settings for showing the scale bar on the map.
   */
  scaleBarSettings: ScaleBarSettings = DefaultSettingsProvider.defaultScaleBarSettings(
    LocalContext.current.applicationContext
  ),
  /**
   * Set the camera of the map.
   */
  cameraOptions: CameraOptions = CameraOptions.Builder().build(),
  /**
   * Lambda to be invoked when camera changes.
   */
  onCameraStateChange: (CameraState) -> Unit = DefaultSettingsProvider.defaultOnCameraStateChange,
  /**
   * Callback to be invoked when the user clicks on the map view.
   */
  onMapClickListener: OnMapClickListener = DefaultSettingsProvider.defaultOnClickListener,
  /**
   * Callback to be invoked when the user long clicks on the map view.
   */
  onMapLongClickListener: OnMapLongClickListener = DefaultSettingsProvider.defaultOnLongClickListener,
  /**
   * The content of the map.
   */
  content: (@Composable @MapboxMapComposable MapboxMapScope.() -> Unit)? = null
) {
  // display placeholder when in preview mode.
  if (LocalInspectionMode.current) {
    MapPreviewPlaceHolder(modifier)
    return
  }
  val context = LocalContext.current.applicationContext
  val mapView = remember {
    MapView(
      context,
      mapInitOptions = mapInitOptionsFactory.invoke(context)
    )
  }
  AndroidView(
    factory = {
      mapView
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
  val currentCameraOptions by rememberUpdatedState(cameraOptions)
  val currentOnCameraStateChange by rememberUpdatedState(onCameraStateChange)
  val currentOnMapClickListener by rememberUpdatedState(onMapClickListener)
  val currentOnMapLongClickListener by rememberUpdatedState(onMapLongClickListener)
  val currentContent by rememberUpdatedState(content)

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
            currentCameraOptions,
            currentOnCameraStateChange,
            currentOnMapClickListener,
            currentOnMapLongClickListener
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