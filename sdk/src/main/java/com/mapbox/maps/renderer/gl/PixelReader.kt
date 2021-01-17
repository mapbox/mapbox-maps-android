package com.mapbox.maps.renderer.gl

import android.annotation.SuppressLint
import android.opengl.GLES20
import android.opengl.GLES30
import android.os.Build
import androidx.annotation.RequiresApi
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer

/**
 * Designed to perform pixel reading via `glReadPixels` in the most optimal way:
 *
 * 1. By using canonical `glReadPixels` on older devices.
 * 2. By using single PBO with `glReadPixels` on newer devices in order not to block GPU pipeline.
 *
 * Inspired by http://www.songho.ca/opengl/gl_pbo.html
 */
internal class PixelReader(
  val width: Int,
  val height: Int
) {
  private val bufferSize = width * height * channelNum
  private var buffer = ByteBuffer
    .allocateDirect(bufferSize)
    .order(ByteOrder.nativeOrder())
  private val idsPbo = intArrayOf(0)

  init {
    if (supportsPbo) {
      GLES30.glGenBuffers(idsPbo.size, idsPbo, 0)
      GLES30.glBindBuffer(GLES30.GL_PIXEL_PACK_BUFFER, idsPbo[0])
      GLES30.glBufferData(
        GLES30.GL_PIXEL_PACK_BUFFER,
        bufferSize,
        null,
        GLES30.GL_STREAM_READ
      )
      GLES30.glBindBuffer(GLES30.GL_PIXEL_PACK_BUFFER, 0)
    }
  }

  @RequiresApi(Build.VERSION_CODES.N)
  private fun readPixelsWithPBO() {
    GLES30.glReadBuffer(GLES30.GL_FRONT)
    GLES30.glBindBuffer(GLES30.GL_PIXEL_PACK_BUFFER, idsPbo[0])
    GLES30.glReadPixels(
      0, 0, width, height,
      GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, 0
    )
    val bufferData = GLES30.glMapBufferRange(
      GLES30.GL_PIXEL_PACK_BUFFER,
      0,
      bufferSize,
      GLES30.GL_MAP_READ_BIT
    )
    if (bufferData != null) {
      buffer = (bufferData as ByteBuffer).order(ByteOrder.nativeOrder())
      GLES30.glUnmapBuffer(GLES30.GL_PIXEL_PACK_BUFFER)
    }
    GLES30.glBindBuffer(GLES30.GL_PIXEL_PACK_BUFFER, 0)
  }

  @SuppressLint("NewApi")
  fun readPixels(): ByteBuffer {
    buffer.rewind()
    if (supportsPbo) {
      readPixelsWithPBO()
    } else {
      GLES20.glReadPixels(
        0, 0, width, height,
        GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer
      )
    }
    return buffer
  }

  fun release() {
    if (supportsPbo) {
      GLES30.glDeleteBuffers(idsPbo.size, IntBuffer.wrap(idsPbo))
    }
  }

  companion object {
    // currently support just RGBA
    private const val channelNum = 4
    private val supportsPbo = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
  }
}