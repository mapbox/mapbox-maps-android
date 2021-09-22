@file:JvmName("ExtensionUtils")

package com.mapbox.maps

import android.graphics.Bitmap
import java.nio.ByteBuffer

/**
 * Extension function to obtain [Bitmap] from snapshotter converted from [Image].
 */
fun MapSnapshotInterface.bitmap(): Bitmap {
  val image = image()
  val configBmp: Bitmap.Config = Bitmap.Config.ARGB_8888
  val bitmap: Bitmap = Bitmap.createBitmap(image.width, image.height, configBmp)
  val buffer: ByteBuffer = ByteBuffer.wrap(image.data)
  bitmap.copyPixelsFromBuffer(buffer)
  return bitmap
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