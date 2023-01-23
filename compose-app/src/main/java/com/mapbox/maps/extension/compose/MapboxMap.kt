package com.mapbox.maps.extension.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.maps.*
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures

internal class MapRootNode(
  val mapController: MapControllable,
  val mapPluginProvider: MapPluginProviderDelegate,
  var gesturesState: GesturesSettings,
  var styleState: StyleContract.StyleExtension,
  var cameraOptions: CameraOptions
) : MapNode {

  override fun onAttached() {
    // setup map listeners
  }
}


@SuppressLint("Lifecycle")
@Composable
@MapboxMapComposable
public fun MapboxMap(
  modifier: Modifier = Modifier,
  mapInitOptions: MapInitOptions = MapInitOptions(LocalContext.current),
//  gesturesState: GesturesState = rememberGesturesState(),
  gesturesSettings: GesturesSettings = GesturesSettings(),
  // Add states for all the plugins...
//  styleState: StyleState = rememberStyleState(),
  style: StyleContract.StyleExtension = style(Style.MAPBOX_STREETS) { },
  cameraOptions: CameraOptions = CameraOptions.Builder().build(),
  content: @Composable MapboxMapScope.() -> Unit
) {
  if (LocalInspectionMode.current) {
    // display placeholder when in preview mode.
    MapboxMapPreviewPlaceHolder(modifier)
    return
  }
  logE("compose", "MapboxMap")
  val context = LocalContext.current
  val mapView = remember {
    logE("compose", "creating MapView")
    MapView(context, mapInitOptions)
  }
  MapLifecycle(mapView = mapView)
  AndroidView(
    factory = {
      mapView
    },
    modifier = modifier,
  )

  val currentGesturesState by rememberUpdatedState(gesturesSettings)
  val currentStyleState by rememberUpdatedState(style)
  val currentCameraOptions by rememberUpdatedState(cameraOptions)
  val currentContent by rememberUpdatedState(content)
  val parentComposition = rememberCompositionContext()

  LaunchedEffect(Unit) {
    logE("compose", "LaunchedEffect")
//    currentGesturesState.setGesturesPlugin(plugin = mapView.gestures)
//    currentStyleState.setMapboxMap(mapboxMap = mapView.getMapboxMap())
    disposingComposition {
      mapView.newComposition(parentComposition) {
        logE("compose", "mapView.newComposition")
        ComposeNode<MapRootNode, MapApplier>(
          factory = {
            logE("compose", "MapRootNode factory")
            MapRootNode(
              mapView,
              mapView,
              currentGesturesState,
              currentStyleState,
              currentCameraOptions
            )
          },
          update = {
            set(currentGesturesState) {
              logE("compose", "currentGesturesState set: $it")
              this.gesturesState = it
              this.mapPluginProvider.gestures.applySettings(it)
            }
            set(currentStyleState) {
              logE("compose", "currentStyleState set: $it")
              this.styleState = it
              this.mapController.getMapboxMap().loadStyle(it)
            }
            set(currentCameraOptions) {
              logE("compose", "currentCameraOptions set: $it")
              this.cameraOptions = it
              this.mapController.getMapboxMap().setCamera(it)
            }
          }
        )

        // Update map with updated state
        MapboxMapScope.currentContent()
      }
    }
  }
}