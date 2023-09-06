package com.mapbox.maps.renderer

import android.opengl.EGLContext
import android.opengl.EGLSurface
import android.opengl.GLES20
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.logE
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.widget.Widget
import java.util.concurrent.CopyOnWriteArraySet

@OptIn(MapboxExperimental::class)
internal class MapboxWidgetRenderer(
  private val antialiasingSampleCount: Int,
) {
  private var eglCore: EGLCore? = null
  private var eglContextCreated = false
  private var eglSurface: EGLSurface? = null
  private var sizeChanged = false

  private val textures = intArrayOf(0)
  private val framebuffers = intArrayOf(0)

  private val widgets = CopyOnWriteArraySet<Widget>()

  private var width = 0
  private var height = 0

  val needTextureUpdate: Boolean
    get() = widgets.any { it.renderer.needRender }

  fun hasWidgets() = widgets.isNotEmpty()

  fun hasTexture() = textures[0] != 0

  fun getTexture() = textures[0]

  fun setSharedContext(sharedContext: EGLContext) {
    if (eglContextCreated) {
      release()
    }
    eglCore = EGLCore(
      translucentSurface = false,
      antialiasingSampleCount = antialiasingSampleCount,
      sharedContext = sharedContext,
    )
  }

  fun onSurfaceChanged(width: Int, height: Int) {
    sizeChanged = true
    this.width = width
    this.height = height
    widgets.forEach { it.renderer.onSurfaceChanged(width, height) }
  }

  private fun attachTexture() {
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
    val eglCore = this.eglCore
    val eglSurface = this.eglSurface
    if (eglCore != null) {
      if (eglSurface != null && eglSurface != eglCore.eglNoSurface) {
        eglCore.makeCurrent(eglSurface)

        GLES20.glDeleteFramebuffers(framebuffers.size, framebuffers, 0)
        GLES20.glDeleteTextures(textures.size, textures, 0)

        // Do not clear widgets here, or they will be lost if Activity is paused/resumed.
        widgets.forEach {
          it.renderer.release()
        }

        eglCore.releaseSurface(eglSurface)
      }

      eglCore.release()
    }
    this.eglSurface = null
    this.eglCore = null
    this.eglContextCreated = false
  }

  fun updateTexture() {
    if (needTextureUpdate) {
      checkSizeChanged()
      checkEgl()
      val eglCore = this.eglCore
      val eglSurface = this.eglSurface
      if (eglCore != null && eglSurface != null && eglSurface != eglCore.eglNoSurface) {
        eglCore.makeCurrent(eglSurface)
        bindFramebuffer()
        attachTexture()
        widgets.forEach {
          it.renderer.render()
        }
        unbindFramebuffer()
      }
    }
  }

  private fun checkSizeChanged() {
    if (sizeChanged) {
      val eglCore = this.eglCore
      val eglSurface = this.eglSurface
      if (eglCore != null && eglSurface != null && eglSurface != eglCore.eglNoSurface) {
        eglCore.releaseSurface(eglSurface)
        this.eglSurface = eglCore.eglNoSurface
      }

      sizeChanged = false
    }
  }

  private fun unbindFramebuffer() {
    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
  }

  private fun bindFramebuffer() {
    if (framebuffers[0] == 0) {
      GLES20.glGenFramebuffers(1, framebuffers, 0)
    }
    GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, framebuffers[0])
  }

  private fun checkEgl() {
    val eglSurface = this.eglSurface
    val eglCore = this.eglCore

    if (eglCore == null) {
      logE(TAG, "Cannot prepare egl, eglCore has not been initialized yet.")
      return
    }
    if (eglSurface != null && eglSurface != eglCore.eglNoSurface) {
      return
    }

    if (!eglContextCreated) {
      eglContextCreated = eglCore.prepareEgl()
      if (!eglContextCreated) {
        logE(TAG, "Widget EGL was not configured, please check logs above.")
        return
      }
    }

    if (eglSurface == null || eglSurface == eglCore.eglNoSurface) {
      this.eglSurface = eglCore.createOffscreenSurface(width = width, height = height)
      if (eglSurface == eglCore.eglNoSurface) {
        logE(TAG, "Widget offscreen surface was not configured, please check logs above.")
        return
      }
    }
  }

  fun addWidget(widget: Widget) {
    widget.renderer.onSurfaceChanged(width, height)
    widgets.add(widget)
  }

  fun removeWidget(widget: Widget): Boolean {
    val removed = widgets.remove(widget)
    if (removed) {
      widget.setTriggerRepaintAction(null)
    }
    return removed
  }

  fun cleanUpAllWidgets() {
    widgets.forEach { it.setTriggerRepaintAction(null) }
    widgets.clear()
  }

  private companion object {
    private const val TAG: String = "MapboxWidgetRenderer"
  }
}