package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapControllable
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import kotlinx.coroutines.CoroutineScope

public interface MapController : MapControllable, MapPluginProviderDelegate

@Composable
@MapboxMapComposable
public fun MapEffect(key1: Any?, block: suspend CoroutineScope.(MapView) -> Unit) {
  val map = (currentComposer.applier as MapApplier).mapController as MapView
  LaunchedEffect(key1 = key1) {
    block(map)
  }
}

@Composable
@MapboxMapComposable
public fun MapEffect(vararg keys: Any?, block: suspend CoroutineScope.(MapView) -> Unit) {
  val map = (currentComposer.applier as MapApplier).mapController as MapView
  LaunchedEffect(keys = keys) {
    block(map)
  }
}