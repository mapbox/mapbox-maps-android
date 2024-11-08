@file:JvmName("ExtensionUtils")

package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.DataRef

/**
 * Convert [Bitmap] to rendering engine [Image] instance.
 *
 * Important: This will allocate native memory outside the JVM heap. If possible, avoid using it
 * inside loops/animations.
 */
@MapboxDelicateApi
fun Bitmap.toMapboxImage(): Image {
  if (config != Bitmap.Config.ARGB_8888) {
    throw IllegalArgumentException("Only ARGB_8888 bitmap config is supported!")
  }
  val nativeDataRef = DataRef.allocateNative(byteCount)
  copyPixelsToBuffer(nativeDataRef.buffer)
  return Image(width, height, nativeDataRef)
}

/**
 * Extension function to convert [CameraState] to [CameraOptions].
 *
 * If [anchor] is specified - [CameraOptions.center] will be explicitly set to NULL
 * in order for the anchor to apply to the map camera.
 */
@JvmOverloads
fun CameraState.toCameraOptions(anchor: ScreenCoordinate? = null): CameraOptions =
  if (anchor != null) {
    CameraOptions.Builder()
      .anchor(anchor)
      .padding(padding)
      .zoom(zoom)
      .pitch(pitch)
      .bearing(bearing)
      .build()
  } else {
    CameraOptions.Builder()
      .center(center)
      .padding(padding)
      .zoom(zoom)
      .pitch(pitch)
      .bearing(bearing)
      .build()
  }