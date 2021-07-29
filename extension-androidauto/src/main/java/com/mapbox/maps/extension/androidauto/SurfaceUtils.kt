package com.mapbox.maps.extension.androidauto

import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import android.view.Surface
import java.lang.Exception

fun Surface.drawWidget(bitmap: Bitmap) {
  if (!isValid) {
    // Surface is not available, or has been destroyed, skip this frame.
    Log.e("testtest", "Surface is not available, or has been destroyed, skip this frame.")
    return
  }
  val height = bitmap.height
  val width = bitmap.width
  try {
    this.lockCanvas(Rect(0, 0, width, height))?.let { canvas ->
      Log.e("testtest", "lockCanvas done")
      canvas.drawBitmap(bitmap, 0f, 0f, null)
      this.unlockCanvasAndPost(canvas)
    }
  } catch (e:Exception) {
    Log.e("testtest", e.toString())
  }
}