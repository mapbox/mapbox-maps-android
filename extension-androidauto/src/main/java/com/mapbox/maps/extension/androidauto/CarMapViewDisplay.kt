package com.mapbox.maps.extension.androidauto

import android.app.Presentation
import android.content.Context
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.view.WindowManager
import androidx.car.app.SurfaceContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.logE
import com.mapbox.maps.logI

/**
 * Shows a [MapView] on a car surface through a [VirtualDisplay] and a [Presentation].
 * The map view is started by [create] and destroyed by [destroy].
 * View-tree owners and ViewModels are scoped to this display.
 */
internal class CarMapViewDisplay(
  private val presentation: Presentation,
  private val virtualDisplay: VirtualDisplay,
  val mapView: MapView,
  private val surfaceContainer: SurfaceContainer? = null,
) : SavedStateRegistryOwner, ViewModelStoreOwner {

  private val lifecycleRegistry = LifecycleRegistry(this)
  private val savedStateRegistryController = SavedStateRegistryController.create(this)
  private val viewModelStore = ViewModelStore()

  override fun getLifecycle(): Lifecycle = lifecycleRegistry

  override val savedStateRegistry: SavedStateRegistry
    get() = savedStateRegistryController.savedStateRegistry

  override fun getViewModelStore(): ViewModelStore = viewModelStore

  internal fun show() {
    savedStateRegistryController.performRestore(null)
    val decorView = checkNotNull(presentation.window).decorView
    ViewTreeLifecycleOwner.set(decorView, this)
    decorView.setViewTreeSavedStateRegistryOwner(this)
    ViewTreeViewModelStoreOwner.set(decorView, this)
    lifecycleRegistry.currentState = Lifecycle.State.STARTED
    presentation.setContentView(mapView)
    presentation.show()
  }

  fun destroy() {
    logI(TAG, "Destroying virtual display id=${virtualDisplay.display?.displayId} for car surface $surfaceContainer")
    presentation.dismiss()
    destroyLifecycle()
    if (mapView.mapboxMap.isValid()) mapView.onDestroy()
    viewModelStore.clear()
    virtualDisplay.release()
  }

  private fun destroyLifecycle() {
    if (lifecycleRegistry.currentState.isAtLeast(Lifecycle.State.CREATED)) {
      lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
  }

  companion object {
    private const val TAG = "CarMapViewDisplay"
    private const val VIRTUAL_DISPLAY_NAME = "MapboxCarMapViewDisplay"

    fun create(
      context: Context,
      surfaceContainer: SurfaceContainer,
      mapInitOptions: MapInitOptions,
    ): CarMapViewDisplay? {
      val surface = surfaceContainer.surface ?: run {
        logE(TAG, "Cannot create map view display without a surface")
        return null
      }
      if (surfaceContainer.width == 0 || surfaceContainer.height == 0 || surfaceContainer.dpi == 0) {
        logE(TAG, "Cannot create map view display with invalid dimensions or density: $surfaceContainer")
        return null
      }
      var virtualDisplay: VirtualDisplay? = null
      var presentation: Presentation? = null
      var mapView: MapView? = null
      var display: CarMapViewDisplay? = null
      try {
        virtualDisplay = context.getSystemService(DisplayManager::class.java).createVirtualDisplay(
          VIRTUAL_DISPLAY_NAME,
          surfaceContainer.width,
          surfaceContainer.height,
          surfaceContainer.dpi,
          surface,
          DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY,
        ) ?: run {
          logE(TAG, "Failed to allocate map view virtual display")
          return null
        }
        presentation = MapSurfaceProvider.createPresentation(context, virtualDisplay.display)
        val displayContext = presentation.context
        val displayMapOptions = mapInitOptions.mapOptions.toBuilder()
          .pixelRatio(displayContext.resources.displayMetrics.density)
          .build()
        mapView = MapSurfaceProvider.createMapView(
          displayContext,
          mapInitOptions.copy(context = displayContext, mapOptions = displayMapOptions)
        )
        display = CarMapViewDisplay(presentation, virtualDisplay, mapView, surfaceContainer)
        display.show()
        logI(TAG, "Created virtual display id=${virtualDisplay.display?.displayId} for car surface $surfaceContainer")
        return display
      } catch (failure: Throwable) {
        val displayId = runCatching { virtualDisplay?.display?.displayId }.getOrNull()
        runCatching { presentation?.dismiss() }.onFailure(failure::addSuppressed)
        runCatching { display?.destroyLifecycle() }.onFailure(failure::addSuppressed)
        runCatching {
          mapView?.takeIf { it.mapboxMap.isValid() }?.onDestroy()
        }.onFailure(failure::addSuppressed)
        runCatching { display?.viewModelStore?.clear() }.onFailure(failure::addSuppressed)
        runCatching { virtualDisplay?.release() }.onFailure(failure::addSuppressed)
        when (failure) {
          is WindowManager.InvalidDisplayException, is WindowManager.BadTokenException -> {
            logE(
              TAG,
              "Unable to show map view on virtual display id=$displayId for car surface $surfaceContainer: " +
                failure.stackTraceToString()
            )
            return null
          }

          else -> throw failure
        }
      }
    }
  }
}