package com.mapbox.maps.renderer

import android.graphics.Bitmap
import android.graphics.Matrix
import android.opengl.GLES20
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.annotation.UiThread
import androidx.annotation.VisibleForTesting
import com.mapbox.common.Cancelable
import com.mapbox.maps.DelegatingMapClient
import com.mapbox.maps.MapView.OnSnapshotReady
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.NativeMapImpl
import com.mapbox.maps.RenderFrameFinishedCallback
import com.mapbox.maps.RenderModeType
import com.mapbox.maps.Size
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.renderer.gl.PixelReader
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

internal abstract class MapboxRenderer(mapName: String) : DelegatingMapClient {

  internal lateinit var renderThread: MapboxRenderThread
  internal abstract val widgetRenderer: MapboxWidgetRenderer

  internal var map: NativeMapImpl? = null

  private var width: Int = 0
  private var height: Int = 0

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var pixelReader: PixelReader? = null

  // in order to prevent snapshots being black we capture first moment
  // when map is rendered fully
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var readyForSnapshot = AtomicBoolean(false)
  private var renderFrameCancelable: Cancelable? = null
  private val renderFrameFinishedCallback = RenderFrameFinishedCallback { eventData ->
    if (eventData.renderMode == RenderModeType.FULL) {
      readyForSnapshot.set(true)
      renderFrameCancelable?.cancel()
    }
  }
  @Suppress("PrivatePropertyName")
  private val TAG = "Mbgl-Renderer" + if (mapName.isNotBlank()) "\\$mapName" else ""

  internal var snapshotLegacyModeEnabled = false
    @MainThread
    get
    @MainThread
    set

  @UiThread
  fun onDestroy() {
    widgetRenderer.cleanUpAllWidgets()
    renderThread.destroy()
    renderThread.fpsChangedListener = null
  }

  @AnyThread
  @Synchronized
  fun setMap(map: NativeMapImpl) {
    this.map = map
  }

  @AnyThread
  override fun scheduleRepaint() {
    renderThread.queueRenderEvent(repaintRenderEvent)
  }

  @AnyThread
  fun queueRenderEvent(runnable: Runnable) {
    renderThread.queueRenderEvent(
      RenderEvent(
        runnable = runnable,
        needRender = true,
      )
    )
  }

  @AnyThread
  fun queueNonRenderEvent(runnable: Runnable) {
    renderThread.queueRenderEvent(
      RenderEvent(
        runnable = runnable,
        needRender = false,
      )
    )
  }

  @RenderThread
  fun createRenderer() {
    map?.createRenderer()
  }

  @RenderThread
  fun onSurfaceChanged(width: Int, height: Int) {
    if (width != this.width || height != this.height) {
      this.width = width
      this.height = height
      GLES20.glViewport(0, 0, width, height)
      map?.setSize(Size(width.toFloat(), height.toFloat()))
    }
  }

  @RenderThread
  fun destroyRenderer() {
    map?.destroyRenderer()
    // additionally it's correct moment to release pixel reader while we still have EGL context
    pixelReader?.release()
    pixelReader = null
  }

  @RenderThread
  fun render() {
    map?.render()
  }

  /**
   * Resets the renderer thread service type to Interactive.
   * Must be called on the render thread.
   */
  @MapboxExperimental
  @UiThread
  fun resetThreadServiceType() {
    map?.resetThreadServiceType()
  }

  @UiThread
  fun onStop() {
    renderThread.pause()
    renderFrameCancelable?.cancel()
    readyForSnapshot.set(false)
  }

  @UiThread
  fun onStart() {
    renderThread.resume()
    renderFrameCancelable = map?.subscribe(renderFrameFinishedCallback)
  }

  @UiThread
  fun onResume() {
    renderThread.scheduleThreadServiceTypeReset()
  }

  @RenderThread
  fun snapshot(): Bitmap? {
    if (!readyForSnapshot.get()) {
      logE(TAG, "Could not take map snapshot because map is not ready yet.")
      return null
    }
    val lock = ReentrantLock()
    val waitCondition = lock.newCondition()
    val legacyMode = snapshotLegacyModeEnabled
    lock.withLock {
      var snapshot: Bitmap? = null
      renderThread.queueRenderEvent(
        RenderEvent(
          runnable = {
            lock.withLock {
              snapshot = performSnapshot(legacyMode)
              waitCondition.signal()
            }
          },
          needRender = true,
        )
      )
      waitCondition.await(1, TimeUnit.SECONDS)
      return snapshot
    }
  }

  @AnyThread
  fun snapshot(listener: OnSnapshotReady) {
    if (!readyForSnapshot.get()) {
      logE(TAG, "Could not take map snapshot because map is not ready yet.")
      listener.onSnapshotReady(null)
    }
    val legacyMode = snapshotLegacyModeEnabled
    renderThread.queueRenderEvent(
      RenderEvent(
        runnable = { listener.onSnapshotReady(performSnapshot(legacyMode)) },
        needRender = true,
      )
    )
  }

  @AnyThread
  fun setMaximumFps(fps: Int) {
    if (fps <= 0) {
      logE(TAG, "Maximum FPS could not be <= 0, ignoring $fps value.")
      return
    }
    renderThread.setUserRefreshRate(fps)
  }

  @AnyThread
  @Synchronized
  fun setOnFpsChangedListener(listener: OnFpsChangedListener) {
    renderThread.fpsChangedListener = listener
  }

  @RenderThread
  private fun performSnapshot(legacyMode: Boolean): Bitmap? {
    if (width == 0 && height == 0) {
      logE(TAG, "Could not take map snapshot because map is not ready yet.")
      return null
    }

    if (pixelReader == null || pixelReader?.width != width || pixelReader?.height != height || pixelReader?.legacyMode != legacyMode) {
      pixelReader?.release()
      pixelReader = PixelReader(width, height, legacyMode)
    }
    val pixelReader = pixelReader!!

    try {
      val buffer = pixelReader.readPixels()
      buffer.rewind()
      val flipped = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
      ).apply {
        copyPixelsFromBuffer(buffer)
      }
      val cx = width / 2.0f
      val cy = height / 2.0f
      try {
        return Bitmap.createBitmap(
          flipped,
          0, 0,
          width, height,
          Matrix().apply { postScale(1f, -1f, cx, cy) },
          true
        )
      } finally {
        flipped.recycle()
      }
    } catch (e: Throwable) {
      logW(TAG, "Exception ${e.localizedMessage} happened when reading pixels")
      if (!pixelReader.legacyMode) {
        logW(TAG, "Re-creating PixelReader with no PBO support and making snapshot again")
        pixelReader.release()
        this.pixelReader = PixelReader(
          width = pixelReader.width,
          height = pixelReader.height,
          legacyMode = true
        )
        return performSnapshot(legacyMode = true)
      }
    }
    return null
  }

  companion object {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val repaintRenderEvent = RenderEvent(null, true)
  }
}