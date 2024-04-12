package com.mapbox.maps.extension.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapPreviewPlaceHolder
import com.mapbox.maps.extension.compose.internal.MapViewLifecycle
import com.mapbox.maps.extension.compose.internal.MapboxMapComposeNode
import com.mapbox.maps.extension.compose.ornaments.attribution.MapAttributionScope
import com.mapbox.maps.extension.compose.ornaments.compass.MapCompassScope
import com.mapbox.maps.extension.compose.ornaments.logo.MapLogoScope
import com.mapbox.maps.extension.compose.ornaments.scalebar.MapScaleBarScope
import com.mapbox.maps.extension.compose.style.MapboxStandardStyle
import com.mapbox.maps.extension.compose.style.MapboxStyleComposable
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import kotlinx.coroutines.awaitCancellation

/**
 * Entry point for adding a Mapbox Map instance to the Jetpack Compose UI.
 *
 * @param modifier Modifier to be applied to the Mapbox map.
 * @param composeMapInitOptions Defines the initialisation configurations for a [MapboxMap]. It should only be set once and not mutated after the initialisation. Mutating the [ComposeMapInitOptions] will result in internal [MapView] recreation and impact performance.
 * @param gesturesSettings Gesture configuration allows to control the user touch interaction.
 * @param compass The Mapbox Compass ornament of the map, consider using [MapCompassScope.Compass].
 * @param scaleBar The Mapbox ScaleBar ornament of the map, consider using [MapScaleBarScope.ScaleBar].
 * @param logo The Mapbox Logo ornament of the map, consider using [MapLogoScope.Logo].
 * @param attribution The Mapbox Attribution ornament of the map, consider using [MapAttributionScope.Attribution].
 * @param mapViewportState A state object that can be hoisted to control and observe the map's camera state. A [MapViewportState] may only be used by a single [MapboxMap] composable at a time as it reflects instance state for a single view of a map.
 * @param onMapClickListener Callback to be invoked when the user clicks on the map view.
 * @param onMapLongClickListener Callback to be invoked when the user long clicks on the map view.
 * @param style The Style of the map.
 * @param content The content of the map.
 */
@Composable
@MapboxExperimental
public fun MapboxMap(
  modifier: Modifier = Modifier,
  mapEvents: MapEvents? = null,
  composeMapInitOptions: ComposeMapInitOptions = ComposeMapInitOptions(LocalDensity.current.density),
  gesturesSettings: GesturesSettings = GesturesSettings { },
  compass: (@Composable MapCompassScope.() -> Unit) = { Compass() },
  scaleBar: (@Composable MapScaleBarScope.() -> Unit) = { ScaleBar() },
  logo: (@Composable MapLogoScope.() -> Unit) = { Logo() },
  attribution: (@Composable MapAttributionScope.() -> Unit) = { Attribution() },
  mapViewportState: MapViewportState = rememberMapViewportState(),
  onMapClickListener: OnMapClickListener = DefaultSettingsProvider.defaultOnClickListener,
  onMapLongClickListener: OnMapLongClickListener = DefaultSettingsProvider.defaultOnLongClickListener,
  style: @Composable @MapboxStyleComposable () -> Unit = { MapboxStandardStyle() },
  content: (@Composable @MapboxMapComposable MapboxMapScope.() -> Unit)? = null
) {
  // display placeholder when in preview mode.
  if (LocalInspectionMode.current) {
    MapPreviewPlaceHolder(modifier)
    return
  }

  // Re-create the map every time the init options change
  key(composeMapInitOptions) {
    val context = LocalContext.current
    val mapView = remember {
      MapView(
        context,
        mapInitOptions = composeMapInitOptions.getMapInitOptions(context)
      )
    }
    MapViewLifecycle(mapView = mapView)

    Box(modifier = modifier) {
      AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize(),
      )
      MapCompassScope(mapView, this).compass()
      MapScaleBarScope(mapView, this).scaleBar()
      MapLogoScope(this).logo()
      MapAttributionScope(mapView, this).attribution()
    }

    val parentComposition = rememberCompositionContext()
    val currentGesturesSettings by rememberUpdatedState(gesturesSettings)
    val currentMapViewportState by rememberUpdatedState(mapViewportState)
    val currentOnMapClickListener by rememberUpdatedState(onMapClickListener)
    val currentOnMapLongClickListener by rememberUpdatedState(onMapLongClickListener)
    val currentContent by rememberUpdatedState(content)
    val currentMapEvents by rememberUpdatedState(mapEvents)
    val currentStyle by rememberUpdatedState(style)
    LaunchedEffect(Unit) {
      disposingComposition(
        Composition(
          MapApplier(mapView), parentComposition
        ).apply {
          setContent {
            MapboxMapComposeNode(
              currentGesturesSettings,
              currentMapViewportState,
              currentOnMapClickListener,
              currentOnMapLongClickListener,
              currentMapEvents,
            )
            // add Style node with the styleUri
            currentStyle.invoke()
            currentContent?.let { MapboxMapScope.it() }
          }
        }
      )
    }
  }
}

private suspend inline fun disposingComposition(composition: Composition) {
  try {
    awaitCancellation()
  } finally {
    composition.dispose()
  }
}