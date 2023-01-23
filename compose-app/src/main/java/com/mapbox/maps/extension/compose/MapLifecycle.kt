package com.mapbox.maps.extension.compose

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.mapbox.maps.MapView


/**
 * Registers lifecycle observers to the local [MapView].
 */
@SuppressLint("Lifecycle")
@Composable
@MapboxMapComposable
internal fun MapLifecycle(mapView: MapView) {
  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  val previousState = remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
  DisposableEffect(context, lifecycle, mapView) {
    val mapLifecycleObserver = mapView.lifecycleObserver(previousState)
    val callbacks = mapView.componentCallbacks()

    lifecycle.addObserver(mapLifecycleObserver)
    context.registerComponentCallbacks(callbacks)

    onDispose {
      lifecycle.removeObserver(mapLifecycleObserver)
      context.unregisterComponentCallbacks(callbacks)
    }
  }
  DisposableEffect(mapView) {
    onDispose {
      mapView.onDestroy()
      mapView.removeAllViews()
    }
  }
}

@SuppressLint("Lifecycle")
private fun MapView.lifecycleObserver(previousState: MutableState<Lifecycle.Event>): LifecycleEventObserver =
  LifecycleEventObserver { _, event ->
    when (event) {
      Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_RESUME -> {
        /// no-ops
      }
      Lifecycle.Event.ON_START -> this.onStart()
      Lifecycle.Event.ON_STOP -> this.onStop()
      Lifecycle.Event.ON_DESTROY -> {
        //handled in onDispose
      }
      else -> throw IllegalStateException()
    }
    previousState.value = event
  }

private fun MapView.componentCallbacks(): ComponentCallbacks =
  object : ComponentCallbacks {
    override fun onConfigurationChanged(config: Configuration) {}

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
      this@componentCallbacks.onLowMemory()
    }
  }