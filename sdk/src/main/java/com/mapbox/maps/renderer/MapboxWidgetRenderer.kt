package com.mapbox.maps.renderer

import android.opengl.GLES20
import com.mapbox.common.Logger
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.widget.Widget
import java.util.concurrent.CopyOnWriteArraySet
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLSurface

@MapboxExperimental
internal class MapboxWidgetRenderer(
  private val translucentSurface: Boolean,
  private val antialiasingSampleCount: Int,
) {
  private var widgetEglPrepared = false
  private lateinit var widgetEglSurface: EGLSurface
  private lateinit var widgetEglCore: EGLCore

  private val textures = intArrayOf(0)
  private val framebuffers = intArrayOf(0)

  private val widgets = CopyOnWriteArraySet<Widget>()

  private var width = 0
  private var height = 0

  fun getTextureId() = textures[0]

  val needRender: Boolean
    get() = widgets.any { it.renderer.needRender }

  fun onSharedContext(sharedContext: EGLContext) {
    if (widgetEglPrepared) {
      TODO("New shared context while previous is still alive!")
    }
    widgetEglCore = EGLCore(
      translucentSurface = translucentSurface,
      antialiasingSampleCount = antialiasingSampleCount,
      sharedContext = sharedContext,
    )
    widgetEglSurface = widgetEglCore.eglNoSurface
  }

  fun onSurfaceChanged(width: Int, height: Int) {
    // TODO createOffscreenSurface with new dimensions
    this.width = width
    this.height = height
    widgets.forEach { it.renderer.onSurfaceChanged(width, height) }
  }

  private fun textureToFramebuffer() {
    if (textures[0] != 0) {
      GLES20.glDeleteTextures(textures.size, textures, 0)
    }
    GLES20.glGenTextures(1, textures, 0)

    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])
    GLES20.glTexParameterf(
      GLES20.GL_TEXTURE_2D,
      GLES20.GL_TEXTURE_MAG_FILTER,
      GLES20.GL_LINEAR.toFloat()
    )
    GLES20.glTexParameterf(
      GLES20.GL_TEXTURE_2D,
      GLES20.GL_TEXTURE_MIN_FILTER,
      GLES20.GL_LINEAR.toFloat()
    )
    GLES20.glTexParameterf(
      GLES20.GL_TEXTURE_2D,
      GLES20.GL_TEXTURE_WRAP_S,
      GLES20.GL_CLAMP_TO_EDGE.toFloat()
    )
    GLES20.glTexParameterf(
      GLES20.GL_TEXTURE_2D,
      GLES20.GL_TEXTURE_WRAP_T,
      GLES20.GL_CLAMP_TO_EDGE.toFloat()
    )
    GLES20.glTexImage2D(
      GLES20.GL_TEXTURE_2D,
      0,
      GLES20.GL_RGBA,
      width,
      height,
      0,
      GLES20.GL_RGBA,
      GLES20.GL_UNSIGNED_BYTE,
      null
    )
    GLES20.glFramebufferTexture2D(
      GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
      GLES20.GL_TEXTURE_2D, textures[0], 0
    )
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
  }

  fun release() {
    if (widgetEglPrepared) {
      widgetEglPrepared = false

      if (widgetEglSurface != widgetEglCore.eglNoSurface) {
        widgetEglCore.makeCurrent(widgetEglSurface)

        GLES20.glDeleteFramebuffers(framebuffers.size, framebuffers, 0)
        GLES20.glDeleteTextures(textures.size, textures, 0)
        widgets.forEach {
          it.renderer.release()
        }
        widgets.clear()

        widgetEglCore.releaseSurface(widgetEglSurface)

        widgetEglSurface = widgetEglCore.eglNoSurface
      }

      widgetEglCore.release()
    }
  }

  fun updateTexture() {
    if (needRender) {
      checkEgl()
      widgetEglCore.makeCurrent(widgetEglSurface)
      GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffers[0])
      textureToFramebuffer()
      widgets.forEach {
        it.renderer.render()
      }
      GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
      widgetEglCore.makeNothingCurrent()
    }
  }

  private fun checkEgl() {
    if (widgetEglPrepared && widgetEglSurface != widgetEglCore.eglNoSurface) {
      return
    }

    if (!widgetEglPrepared) {
      widgetEglPrepared = widgetEglCore.prepareEgl()
      if (!widgetEglPrepared) {
        Logger.e(TAG, "Widget EGL was not configured, please check logs above.")
        return
      }
    }

    if (widgetEglSurface == widgetEglCore.eglNoSurface) {
      widgetEglSurface = widgetEglCore.createOffscreenSurface(width = width, height = height)
      if (widgetEglSurface == widgetEglCore.eglNoSurface) {
        return
      }
    }

    widgetEglCore.makeCurrent(widgetEglSurface)

    GLES20.glGenFramebuffers(1, framebuffers, 0)
    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffers[0])
  }

  fun addWidget(widget: Widget) {
    widget.renderer.onSurfaceChanged(width, height)
    widgets.add(widget)
  }

  fun removeWidget(widget: Widget) = widgets.remove(widget)

  companion object {
    private const val TAG: String = "MapboxWidgetRenderer"
  }
}
