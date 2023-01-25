package com.mapbox.maps.extension.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.*
import com.mapbox.maps.extension.compose.viewport.MapViewport
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.R
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.ViewportStatusObserver
import com.mapbox.maps.plugin.viewport.viewport

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
  gesturesSettings: GesturesSettings = GesturesSettings(),
  locationComponentSettings: LocationComponentSettings = LocationComponentSettings(
    locationPuck = LocationPuck2D(
      topImage = ResourcesCompat.getDrawable(
        LocalContext.current.resources,
        R.drawable.mapbox_user_icon,
        null
      ),
      bearingImage = ResourcesCompat.getDrawable(
        LocalContext.current.resources,
        R.drawable.mapbox_user_stroke_icon,
        null
      ),
      shadowImage = ResourcesCompat.getDrawable(
        LocalContext.current.resources,
        R.drawable.mapbox_user_icon_shadow,
        null
      ),
    )
  ),
  // Add settings for all the plugins...
  style: StyleContract.StyleExtension = style(Style.MAPBOX_STREETS) { },
  cameraOptions: CameraOptions = CameraOptions.Builder().build(),
  mapViewport: MapViewport = MapViewport.Idle,
  viewportStatusObserver: ViewportStatusObserver = ViewportStatusObserver { _, _, _ -> },
  onMapClickListener: OnMapClickListener = OnMapClickListener { false },
  onMapLongClickListener: OnMapLongClickListener = OnMapLongClickListener { false },
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
  val currentLocationComponentSettings by rememberUpdatedState(locationComponentSettings)
  val currentViewport by rememberUpdatedState(mapViewport)
  val currentViewportStatusObserver by rememberUpdatedState(viewportStatusObserver)
  val parentComposition = rememberCompositionContext()

  LaunchedEffect(Unit) {
    logE("compose", "LaunchedEffect")
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
            set(onMapClickListener) {
              this.mapPluginProvider.gestures.addOnMapClickListener(it)
            }
            set(onMapLongClickListener) {
              this.mapPluginProvider.gestures.addOnMapLongClickListener(it)
            }
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
            set(currentLocationComponentSettings.enabled) {
              this.mapPluginProvider.location.enabled = it
            }
            set(currentLocationComponentSettings.locationPuck) {
              this.mapPluginProvider.location.locationPuck = it
            }
            update(currentViewportStatusObserver) {
              this.mapPluginProvider.viewport.addStatusObserver(it)
            }
            set(currentViewport) { mapViewport ->
              logE("compose", "currentViewport set: $mapViewport")
              val viewportPlugin = this.mapPluginProvider.viewport
              when (mapViewport) {
                MapViewport.Idle -> viewportPlugin.idle()
                is MapViewport.FollowPuckState -> {
                  viewportPlugin.transitionTo(
                    viewportPlugin.makeFollowPuckViewportState(
                      mapViewport.stateOptions
                    ),
                    viewportPlugin.makeDefaultViewportTransition(
                      mapViewport.transitionOptions
                    )
                  )
                }
                is MapViewport.OverviewState -> {
                  viewportPlugin.transitionTo(
                    viewportPlugin.makeOverviewViewportState(
                      mapViewport.stateOptions
                    ),
                    viewportPlugin.makeDefaultViewportTransition(
                      mapViewport.transitionOptions
                    )
                  )
                }
              }
            }
          }
        )

        // Update map with updated state
        MapboxMapScope.currentContent()
      }
    }
  }
}