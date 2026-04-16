package com.mapbox.maps.renderer

import android.view.Surface
import com.mapbox.maps.IVulkanManager
import com.mapbox.maps.RenderCallback
import com.mapbox.maps.logI
import com.mapbox.maps.logW

/**
 * Vulkan-based implementation of MapboxRenderThread.
 */
internal class VulkanMapboxRenderThread(
  mapboxRenderer: MapboxRenderer,
  private val antialiasingSampleCount: Int,
  mapName: String,
) :
  MapboxRenderThread(
    mapboxRenderer = mapboxRenderer,
    widgetRenderer = null,
    mapName = mapName,
    rendererName = "Vulkan"
  ) {

  private var nativeVulkanManager: IVulkanManager? = null
  private val surfaceWrapper: SurfaceWrapper = SurfaceWrapper()

  init {
    logI(TAG, "VulkanMapboxRenderThread created")
  }

  override fun detachSurfaceFromRenderer(creatingSurface: Boolean) {
    // no-op for now
  }

  /**
   * For Vulkan renderer there's not much to prepare, just get the [IVulkanManager].
   * Preparations will be done in [attachSurfaceToRenderer].
   */
  override fun prepareRenderer(): Boolean {
    // TODO: How to handle vulkan not supported in the device?
    if (nativeVulkanManager == null) {
      nativeVulkanManager = mapboxRenderer.map?.getVulkanManager()
      if (nativeVulkanManager == null) {
        logW(TAG, "Failed to obtain VulkanManager - Vulkan rendering will not be available")
      }
    }
    return nativeVulkanManager != null
  }

  override fun attachSurfaceToRenderer(surface: Surface): Boolean {
    surfaceWrapper.setSurface(surface)
    val nativeWindowPtr = surfaceWrapper.aNativeWindow

    if (nativeWindowPtr == 0L) {
      logW(TAG, "Failed to get native window pointer")
      return false
    }

    nativeVulkanManager?.setAntialiasingSampleCount(antialiasingSampleCount)
    val result = nativeVulkanManager?.init(nativeWindowPtr) ?: run {
      // TODO cache surface until nativeVulkanManager is set via setMap
      false
    }

    return result
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

  override fun resize(width: Int, height: Int) {
    // TODO cache width/height if NULL
    nativeVulkanManager?.resize(width, height)
  }

  override fun flushCommands() {
    // no-op for now
  }
}