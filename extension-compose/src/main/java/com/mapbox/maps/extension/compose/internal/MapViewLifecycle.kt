package com.mapbox.maps.extension.compose.internal

import android.annotation.SuppressLint
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapComposable

/**
 * Handle the lifecycle of [MapView] in compose.
 *
 * Note: Some logic in [MapViewLifecycle] overlaps with the [LifecyclePlugin] of the main SDK,
 * the main difference is that it also clean up the [MapView] during the [DisposableEffect].onDispose,
 * which typically happens the the [MapView] is enabled/disabled by a flag during recomposition.
 *
 * When [LifecyclePlugin] is also in use, the `onStart`/`onStop`/`onDestroy` might be called twice
 * in some use cases, but as we guard the duplicated `onStart`/`onStop`/`onDestroy` calls through
 * internal lifecycle states in [MapController], duplicated calls will be bypassed, there shouldn't
 * be an issue.
 */
@Composable
@JvmSynthetic
@MapboxMapComposable
@MapboxExperimental
@SuppressLint("Lifecycle")
internal fun MapViewLifecycle(mapView: MapView) {
  val context = LocalContext.current
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  DisposableEffect(context, lifecycle, mapView) {
    val lifecycleEventObserver = mapView.lifecycleEventObserver()
    val callbacks = mapView.componentCallbacks2()

    lifecycle.addObserver(lifecycleEventObserver)
    context.registerComponentCallbacks(callbacks)

    onDispose {
      lifecycle.removeObserver(lifecycleEventObserver)
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
private fun MapView.lifecycleEventObserver(): LifecycleEventObserver =
  LifecycleEventObserver { _, event ->
    when (event) {
      Lifecycle.Event.ON_CREATE, Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_RESUME, Lifecycle.Event.ON_DESTROY -> {
        // no-ops, onDestroy handled in DisposeEffect.onDispose
      }
      Lifecycle.Event.ON_START -> this.onStart()
      Lifecycle.Event.ON_STOP -> this.onStop()

      else -> throw IllegalStateException()
    }
  }

private fun MapView.componentCallbacks2(): ComponentCallbacks2 =
  object : ComponentCallbacks2 {
    override fun onConfigurationChanged(config: Configuration) {
      // no-ops
    }

    @SuppressLint("Lifecycle")
    override fun onLowMemory() {
      this@componentCallbacks2.onLowMemory()
    }

    @SuppressLint("Lifecycle")
    override fun onTrimMemory(level: Int) {
      when (level) {
        ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL, ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW -> {
          this@componentCallbacks2.onLowMemory()
        }
        ComponentCallbacks2.TRIM_MEMORY_BACKGROUND, ComponentCallbacks2.TRIM_MEMORY_COMPLETE, ComponentCallbacks2.TRIM_MEMORY_MODERATE, ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE, ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> Unit
      }
    }
  }