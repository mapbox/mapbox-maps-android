package com.mapbox.maps

import android.graphics.Bitmap
import com.mapbox.bindgen.Value
import com.mapbox.common.ValueConverter
import java.nio.ByteBuffer
import kotlin.math.abs

/**
 * Extension function to compare two double with delta.
 */
fun Double.equalsWithDelta(other: Double, delta: Double): Boolean {
  return abs(this - other) < delta
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Double.roughlyEquals(other: Double): Boolean {
  return this.equalsWithDelta(other, 1E-5)
}

/**
 * Extension function to compare two double with default delta of 1E-5.
 */
fun Value.toJson(): String {
  return ValueConverter.toJson(this)
}

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