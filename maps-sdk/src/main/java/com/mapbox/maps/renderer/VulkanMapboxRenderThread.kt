package com.mapbox.maps.renderer

import android.annotation.SuppressLint
import android.view.Surface
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.IVulkanManager
import com.mapbox.maps.RenderCallback
import com.mapbox.maps.logI
import com.mapbox.maps.logW
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * Vulkan-based implementation of MapboxRenderThread.
 */
internal class VulkanMapboxRenderThread : MapboxRenderThread {

  private val antialiasingSampleCount: Int
  private var nativeVulkanManager: IVulkanManager? = null
  private val surfaceWrapper: SurfaceWrapper = SurfaceWrapper()

  /**
   * Latest resize dimensions received before [nativeVulkanManager] was available.
   * Drained by [applyCachedResize] once the manager is obtained.
   *
   * Thread-confined to the render handler thread — both [resize] and
   * [prepareRenderer] are dispatched through it, so no synchronization needed.
   */
  private var cachedSize: Pair<Int, Int>? = null

  constructor(
    mapboxRenderer: MapboxRenderer,
    antialiasingSampleCount: Int,
    mapName: String,
  ) : super(
    mapboxRenderer = mapboxRenderer,
    widgetRenderer = null,
    mapName = mapName,
    rendererName = "Vulkan"
  ) {
    this.antialiasingSampleCount = antialiasingSampleCount
  }

  @SuppressLint("VisibleForTests")
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  constructor(
    mapboxRenderer: MapboxRenderer,
    antialiasingSampleCount: Int,
    handlerThread: RenderHandlerThread,
    fpsManager: FpsManager,
    surfaceProcessingLock: ReentrantLock,
    createCondition: Condition,
    destroyCondition: Condition,
  ) : super(
    mapboxRenderer,
    null,
    handlerThread,
    fpsManager,
    surfaceProcessingLock,
    createCondition,
    destroyCondition
  ) {
    this.antialiasingSampleCount = antialiasingSampleCount
  }

  init {
    logI(TAG, "VulkanMapboxRenderThread created")
  }

  override fun detachSurfaceFromRenderer(creatingSurface: Boolean) {
    // no-op for now
  }

  /**
   * For Vulkan renderer there's not much to prepare, just get the [IVulkanManager].
   * Preparations will be done in [attachSurfaceToRenderer].
   *
   * Returns `false` only when the map is set but [IVulkanManager] cannot be obtained — genuine
   * lack of Vulkan support. Returns `true` while waiting for [onMapSet] so the caller does not
   * mark the renderer unsupported.
   */
  override fun prepareRenderer(): Boolean {
    if (nativeVulkanManager != null) return true
    val map = mapboxRenderer.map ?: return true
    nativeVulkanManager = map.getVulkanManager()
    if (nativeVulkanManager == null) {
      logW(TAG, "Failed to obtain VulkanManager - Vulkan rendering will not be available")
      return false
    }
    applyCachedResize()
    return true
  }

  override fun attachSurfaceToRenderer(surface: Surface): Boolean {
    val mgr = nativeVulkanManager ?: run {
      logI(TAG, "VulkanManager not available yet, surface will be attached when map is set")
      return false
    }
    surfaceWrapper.setSurface(surface)
    val nativeWindowPtr = surfaceWrapper.aNativeWindow
    if (nativeWindowPtr == 0L) {
      logW(TAG, "Failed to get native window pointer")
      return false
    }
    mgr.setAntialiasingSampleCount(antialiasingSampleCount)
    return mgr.init(nativeWindowPtr)
  }

  private val renderCallback: RenderCallback = RenderCallback(mapboxRenderer::render)

  override fun renderWithoutWidgets() {
    nativeVulkanManager?.render(renderCallback)
  }

  override fun presentFrame() {
    // no-op as presentFrame is called from native side as part of `render { }`
  }

  override fun preRenderWithSharedContext() {
    // no-op for now
  }

  override fun renderWithWidgets() {
    TODO("Not yet supported in Vulkan")
  }

  override fun releaseResources() {
    isRendererReady = false
    surfaceWrapper.releaseSurface()
    nativeVulkanManager?.release()
  }

  override fun prepareWidgetRender() {
    // no-op for now
  }

  override fun releaseRenderSurface() {
    nativeVulkanManager?.releaseSurface()
    isRendererReady = false
  }

  override fun clearRendererStateListeners() {
    // no-op for now
  }

  override fun addRendererStateListener(listener: RendererSetupErrorListener) {
    // no-op for now
  }

  override fun removeRendererStateListener(listener: RendererSetupErrorListener) {
    // no-op for now
  }

  @RenderThread
  override fun resize(width: Int, height: Int) {
    val mgr = nativeVulkanManager
    if (mgr != null) {
      mgr.resize(width, height)
    } else {
      cachedSize = width to height
    }
  }

  /**
   * Forwards the cached pre-init resize to the manager and clears the cache.
   *
   * Apply-once is intentional: once [nativeVulkanManager] is non-null,
   * subsequent [resize] calls go directly to the manager and never repopulate
   * [cachedSize]. [prepareRenderer] also short-circuits while the manager is
   * non-null, so this method does not re-enter. If a manager destroy/re-init
   * lifecycle is added, this contract needs revisiting.
   */
  @RenderThread
  private fun applyCachedResize() {
    cachedSize?.let { (w, h) ->
      logI(TAG, "Applying cached resize($w, $h)")
      nativeVulkanManager?.resize(w, h)
      cachedSize = null
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal fun prepareRendererForTest(): Boolean = prepareRenderer()

  override fun flushCommands() {
    // no-op for now
  }
}