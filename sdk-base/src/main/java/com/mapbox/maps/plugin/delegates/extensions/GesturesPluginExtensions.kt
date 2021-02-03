package com.mapbox.maps.plugin.delegates.extensions

import com.mapbox.android.gestures.AndroidGesturesManager
import com.mapbox.maps.plugin.gestures.*

/**
 * Add a callback that is invoked when the map is clicked.
 */
fun MapPluginExtensionsDelegate.addOnMapClickListener(onMapClickListener: OnMapClickListener) {
  getGesturesPlugin()?.addOnMapClickListener(onMapClickListener)
}

/**
 * Remove a callback that is invoked when the map is clicked.
 */
fun MapPluginExtensionsDelegate.removeOnMapClickListener(onMapClickListener: OnMapClickListener) {
  getGesturesPlugin()?.removeOnMapClickListener(onMapClickListener)
}

/**
 * Add a callback that is invoked when the map is long clicked.
 */
fun MapPluginExtensionsDelegate.addOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  getGesturesPlugin()?.addOnMapLongClickListener(onMapLongClickListener)
}

/**
 * Remove a callback that is invoked when the map is long clicked.
 */
fun MapPluginExtensionsDelegate.removeOnMapLongClickListener(onMapLongClickListener: OnMapLongClickListener) {
  getGesturesPlugin()?.removeOnMapLongClickListener(onMapLongClickListener)
}

/**
 * Add a callback that is invoked when the map is has received a fling gesture.
 */
fun MapPluginExtensionsDelegate.addOnFlingListener(onFlingListener: OnFlingListener) {
  getGesturesPlugin()?.addOnFlingListener(onFlingListener)
}

/**
 * Remove a callback that is invoked when the map is has received a fling gesture.
 */
fun MapPluginExtensionsDelegate.removeOnFlingListener(onFlingListener: OnFlingListener) {
  getGesturesPlugin()?.removeOnFlingListener(onFlingListener)
}

/**
 * Add a callback that is invoked when the map is moved.
 */
fun MapPluginExtensionsDelegate.addOnMoveListener(listener: OnMoveListener) {
  getGesturesPlugin()?.addOnMoveListener(listener)
}

/**
 * Remove a callback that is invoked when the map is moved.
 */
fun MapPluginExtensionsDelegate.removeOnMoveListener(listener: OnMoveListener) {
  getGesturesPlugin()?.removeOnMoveListener(listener)
}

/**
 * Add a callback that is invoked when the map is rotated.
 */
fun MapPluginExtensionsDelegate.addOnRotateListener(listener: OnRotateListener) {
  getGesturesPlugin()?.addOnRotateListener(listener)
}

/**
 * Remove a callback that is invoked when the map is rotated.
 */
fun MapPluginExtensionsDelegate.removeOnRotateListener(listener: OnRotateListener) {
  getGesturesPlugin()?.removeOnRotateListener(listener)
}

/**
 * Add a callback that is invoked when the map is scaled.
 */
fun MapPluginExtensionsDelegate.addOnScaleListener(listener: OnScaleListener) {
  getGesturesPlugin()?.addOnScaleListener(listener)
}

/**
 * Remove a callback that is invoked when the map is scaled.
 */
fun MapPluginExtensionsDelegate.removeOnScaleListener(listener: OnScaleListener) {
  getGesturesPlugin()?.removeOnScaleListener(listener)
}

/**
 * Add a callback that is invoked when the map is shoved.
 */
fun MapPluginExtensionsDelegate.addOnShoveListener(listener: OnShoveListener) {
  getGesturesPlugin()?.addOnShoveListener(listener)
}

/**
 * Remove a callback that is invoked when the map is shoved.
 */
fun MapPluginExtensionsDelegate.removeOnShoveListener(listener: OnShoveListener) {
  getGesturesPlugin()?.removeOnShoveListener(listener)
}

/**
 * Get the current configured AndroidGesturesManager.
 */
fun MapPluginExtensionsDelegate.getGesturesManager(): AndroidGesturesManager? {
  return getGesturesPlugin()?.getGesturesManager()
}

/**
 * Set the AndroidGesturesManager instance.
 */
fun MapPluginExtensionsDelegate.setGesturesManager(
  androidGesturesManager: AndroidGesturesManager,
  attachDefaultListeners: Boolean,
  setDefaultMutuallyExclusives: Boolean
) {
  getGesturesPlugin()?.setGesturesManager(
    androidGesturesManager,
    attachDefaultListeners,
    setDefaultMutuallyExclusives
  )
}

/**
 * The gesture configuration object.
 */
fun MapPluginExtensionsDelegate.getGesturesSettings() = getGesturesPlugin()?.getSettings()