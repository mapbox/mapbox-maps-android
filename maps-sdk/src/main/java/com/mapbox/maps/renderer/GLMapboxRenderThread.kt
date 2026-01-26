package com.mapbox.maps.renderer

import android.annotation.SuppressLint
import android.opengl.EGL14
import android.opengl.EGLSurface
import android.opengl.GLES20
import android.view.Surface
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.ContextMode
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.egl.EGLCore
import com.mapbox.maps.renderer.gl.TextureRenderer
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * OpenGL ES -based implementation of MapboxRenderThread.
 */
internal class GLMapboxRenderThread : MapboxRenderThread {

  private val translucentSurface: Boolean
  private val eglCore: EGLCore
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var eglSurface: EGLSurface

  private var widgetRenderCreated = false
  private val widgetTextureRenderer: TextureRenderer

  private val contextMode: ContextMode

  private var eglContextCreated = false

  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    translucentSurface: Boolean,
    antialiasingSampleCount: Int,
    contextMode: ContextMode,
    mapName: String,
  ) : super(mapboxRenderer, mapboxWidgetRenderer, mapName, "GL") {
    this.eglCore = EGLCore(translucentSurface, antialiasingSampleCount, mapName = mapName)
    this.eglSurface = eglCore.eglNoSurface
    this.widgetTextureRenderer = TextureRenderer()
    this.contextMode = contextMode
    this.translucentSurface = translucentSurface
  }

  @SuppressLint("VisibleForTests")
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    mapboxWidgetRenderer: MapboxWidgetRenderer,
    handlerThread: RenderHandlerThread,
    eglCore: EGLCore,
    fpsManager: FpsManager,
    widgetTextureRenderer: TextureRenderer,
    surfaceProcessingLock: ReentrantLock,
    createCondition: Condition,
    destroyCondition: Condition,
  ) : super(
    mapboxRenderer,
    mapboxWidgetRenderer,
    handlerThread,
    fpsManager,
    surfaceProcessingLock,
    createCondition,
    destroyCondition
  ) {
    this.translucentSurface = false
    this.eglCore = eglCore
    this.widgetTextureRenderer = widgetTextureRenderer
    this.eglSurface = eglCore.eglNoSurface
    this.contextMode = ContextMode.UNIQUE
  }

  override fun detachSurfaceFromRenderer(creatingSurface: Boolean) {
    // on Android SDK <= 23 at least on x86 emulators we need to force set EGL14.EGL_NO_CONTEXT
    // when resuming activity
    if (creatingSurface) {
      eglCore.makeNothingCurrent()
    }
  }

  override fun prepareRenderer(): Boolean {
    logI(
      TAG,
      "prepareRenderer:" +
        " eglContextCreated=$eglContextCreated"
    )
    if (!eglContextCreated) {
      val eglOk = eglCore.prepareEgl()
      if (eglOk) {
        eglContextCreated = true
      } else {
        logW(TAG, "EGL was not configured, please check logs above.")
        return false
      }
    }
    return true
  }

  /**
   * Create the EGL surface [eglSurface] from the given surface if not yet created.
   */
  override fun attachSurfaceToRenderer(surface: Surface): Boolean {
    if (eglSurface == eglCore.eglNoSurface) {
      eglSurface = eglCore.createWindowSurface(surface)
      if (eglSurface == eglCore.eglNoSurface) {
        // try to recreate it in next iteration.
        logW(
          TAG,
          "Could not create EGL surface although Android surface was valid," +
            " retrying in $RETRY_DELAY_MS ms..."
        )
        postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
        return false
      }
    }
    // Once we have a valid eglSurface let's make it current.
    return checkEglContextCurrent()
  }

  override fun presentFrame() {
    swapBuffers()
  }

  override fun preRenderWithSharedContext() {
    if (contextMode == ContextMode.SHARED) {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_STENCIL_BUFFER_BIT)
    }
  }

  override fun renderWithWidgets() {
    if (widgetRenderer?.needRender == true) {
      widgetRenderer.renderToFrameBuffer()
      eglCore.makeCurrent(eglSurface)
    }
    render()
    resetGlState()
    if (widgetRenderer?.hasTexture() == true) {
      widgetTextureRenderer.render(widgetRenderer.getTexture())
    }
  }

  override fun releaseResources() {
    releaseEglSurface()
    if (eglContextCreated) {
      eglCore.release()
    }
    eglContextCreated = false
  }

  override fun prepareWidgetRender() {
    if (eglContextCreated && !widgetRenderCreated && widgetRenderer?.hasWidgets() == true) {
      widgetRenderer.setSharedContext(eglCore.eglContext)
      widgetRenderCreated = true
    }
  }

  override fun releaseRenderSurface() {
    releaseEglSurface()
  }

  override fun clearRendererStateListeners() {
    eglCore.clearRendererStateListeners()
  }

  override fun addRendererStateListener(listener: RendererSetupErrorListener) {
    eglCore.addRendererStateListener(listener)
  }

  override fun removeRendererStateListener(listener: RendererSetupErrorListener) {
    eglCore.removeRendererStateListener(listener)
  }

  override fun resize(width: Int, height: Int) {
    GLES20.glViewport(0, 0, width, height)
  }

  override fun renderWithoutWidgets() {
    render()
  }

  private fun render() {
    mapboxRenderer.render()
  }

  override fun flushCommands() {
    // explicit flush as we will not be doing any drawing until buffer swap for the next frame -
    // we send commands to GPU this frame as we should have some free time and perform present frame asap on the next frame
    // note that this doesn't block the calling thread, it merely signals the driver that we might not be sending any additional commands.
    // ref https://stackoverflow.com/a/38297697
    GLES20.glFlush()
  }

  private fun checkEglContextCurrent(): Boolean {
    val eglContextAttached = eglCore.makeCurrent(eglSurface)
    if (!eglContextAttached) {
      logW(TAG, "EGL was configured but context could not be made current. Trying again in a moment...")
      postPrepareRenderFrame(delayMillis = RETRY_DELAY_MS)
      return false
    }
    return true
  }

  /**
   * Resetting OpenGL state to make sure widget textures are rendered on top of the map.
   */
  // TODO remove when rendering engine will handle this for us;
  //  for now we have to clean context a bit so that our texture(s) is (are) drawn on top of the map
  private fun resetGlState() {
    // using at least MapDebugOptions.TILE_BORDERS and perhaps in other situations
    // rending engine may change the blend function; so we explicitly reset it to needed values
    GLES20.glEnable(GLES20.GL_BLEND)
    GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA)
    GLES20.glBlendEquation(GLES20.GL_FUNC_ADD)

    // explicitly disable stencil and depth because otherwise widgets may be rendered
    // behind the map tiles in some scenarios
    GLES20.glDisable(GLES20.GL_STENCIL_TEST)
    GLES20.glDisable(GLES20.GL_DEPTH_TEST)
    GLES20.glUseProgram(0)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0)
  }

  private fun swapBuffers() {
    when (val swapStatus = eglCore.swapBuffers(eglSurface)) {
      EGL14.EGL_SUCCESS -> { }
      EGL14.EGL_CONTEXT_LOST -> {
        logW(TAG, "Context lost. Waiting for re-acquire")
        // release all resources but not release Android surface
        // as it still potentially may be valid - then it could be re-used to recreate EGL;
        // if it's not valid - system should shortly send us brand new surface and
        // we will recreate EGL and native renderer anyway
        releaseAll(tryRecreate = true)
      }
      else -> {
        logW(TAG, "eglSwapBuffer error: $swapStatus. Waiting for new surface")
        releaseEglSurface()
      }
    }
  }

  private fun releaseEglSurface() {
    trace("release-egl-surface") {
      widgetTextureRenderer.release()
      eglCore.releaseSurface(eglSurface)
      isRendererReady = false
      eglSurface = eglCore.eglNoSurface
      widgetRenderCreated = false
      widgetRenderer?.release()
    }
  }
}