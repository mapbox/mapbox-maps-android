package com.mapbox.maps.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectResult
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.currentComposer
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.internal.MapApplier

/**
 * [DisposableMapEffect] is a [DisposableEffect] that provides the raw map controller. Use this API
 * to interact with all the raw Mapbox Maps SDK APIs.
 *
 * Note: Use raw map controller with caution, as it may alter the map state without notifying the compose
 * node tree and result in unexpected behaviour.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun DisposableMapEffect(key1: Any?, block: DisposableEffectScope.(MapView) -> DisposableEffectResult) {
  val map = (currentComposer.applier as MapApplier).mapView
  DisposableEffect(key1 = key1) {
    block(map)
  }
}

/**
 * [DisposableMapEffect] is a [DisposableEffect] that provides the raw map controller. Use this API
 * to interact with all the raw Mapbox Maps SDK APIs.
 *
 * Note: Use raw map controller with caution, as it may alter the map state without notifying the compose
 * node tree and result in unexpected behaviour.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun DisposableMapEffect(key1: Any?, key2: Any?, block: DisposableEffectScope.(MapView) -> DisposableEffectResult) {
  val map = (currentComposer.applier as MapApplier).mapView
  DisposableEffect(key1 = key1, key2 = key2) {
    block(map)
  }
}

/**
 * [DisposableMapEffect] is a [DisposableEffect] that provides the raw map controller. Use this API
 * to interact with all the raw Mapbox Maps SDK APIs.
 *
 * Note: Use raw map controller with caution, as it may alter the map state without notifying the compose
 * node tree and result in unexpected behaviour.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun DisposableMapEffect(
  key1: Any?,
  key2: Any?,
  key3: Any?,
  block: DisposableEffectScope.(MapView) -> DisposableEffectResult
) {
  val map = (currentComposer.applier as MapApplier).mapView
  DisposableEffect(key1 = key1, key2 = key2, key3 = key3) {
    block(map)
  }
}

/**
 * [DisposableMapEffect] is a [DisposableEffect] that provides the raw map controller. Use this API
 * to interact with all the raw Mapbox Maps SDK APIs.
 *
 * Note: Use raw map controller with caution, as it may alter the map state without notifying the compose
 * node tree and result in unexpected behaviour.
 */
@Composable
@MapboxMapComposable
@MapboxExperimental
public fun DisposableMapEffect(vararg keys: Any?, block: DisposableEffectScope.(MapView) -> DisposableEffectResult) {
  val map = (currentComposer.applier as MapApplier).mapView
  DisposableEffect(keys = keys) {
    block(map)
  }
}