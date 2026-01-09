package com.mapbox.maps.plugin.indoorselector

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import com.mapbox.maps.IndoorFloor
import com.mapbox.maps.IndoorManager
import com.mapbox.maps.IndoorState
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.indoorselector.generated.IndoorSelectorSettings
import com.mapbox.maps.plugin.indoorselector.generated.IndoorSelectorSettingsBase
import java.util.concurrent.CopyOnWriteArraySet

/**
 * Concrete implementation of IndoorSelectorPlugin.
 *
 * This plugin manages the indoor floor selector UI, displaying available floors
 * and allowing users to switch between them.
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY)
@OptIn(com.mapbox.annotation.MapboxExperimental::class)
internal class IndoorSelectorPluginImpl(
  private val viewImplProvider: (Context) -> IndoorSelectorViewImpl = { IndoorSelectorViewImpl(it) }
) : IndoorSelectorPlugin, IndoorSelectorSettingsBase() {

  private lateinit var indoorSelectorView: IndoorSelectorView
  private var indoorManager: IndoorManager? = null

  override var internalSettings: IndoorSelectorSettings = IndoorSelectorSettings { }

  private val floorSelectedListeners: CopyOnWriteArraySet<OnFloorSelectedListener> =
    CopyOnWriteArraySet()

  @OptIn(com.mapbox.annotation.MapboxExperimental::class)
  private var currentFloors: List<IndoorFloor> = emptyList()
  private var selectedFloorId: String? = null

  private val onFloorSelectedListener = OnFloorSelectedListener { floorId ->
    indoorManager?.selectFloor(floorId)
  }

  private val onIndoorUpdatedCallback = object : IndoorManager.OnIndoorUpdatedCallback {
    override fun onOnIndoorUpdated(indoorState: IndoorState) {
      if (!internalSettings.enabled) return
      updateFloors(indoorState.floors, indoorState.selectedFloorId)
    }
  }

  override fun applySettings() {
    indoorSelectorView.apply {
      isIndoorSelectorVisible = internalSettings.enabled
      setIndoorGravity(internalSettings.position)
      setIndoorSelectorMargins(
        internalSettings.marginLeft.toInt(),
        internalSettings.marginTop.toInt(),
        internalSettings.marginRight.toInt(),
        internalSettings.marginBottom.toInt()
      )
    }
  }

  /**
   * Defines whether the plugin is enabled or disabled.
   */
  override var enabled: Boolean
    get() = internalSettings.enabled
    set(value) {
      if (value == internalSettings.enabled) return
      internalSettings = internalSettings.toBuilder().setEnabled(value).build()
      if (value) {
        addOnFloorSelectedListener(onFloorSelectedListener)
      } else {
        removeOnFloorSelectedListener(onFloorSelectedListener)
      }
      updateIndoorVisibility()
    }

  /**
   * Bind the ViewPlugin with current map context. This will create a View that
   * will be added to the MapView.
   *
   * @param mapView parent view which can be used to fetch [android.content.Context] or [android.view.ViewGroup.LayoutParams]
   * @param attrs parent attributes
   * @param pixelRatio the pixel ratio of the device
   * @return View that will be added to the MapView
   */
  override fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View {
    return viewImplProvider(mapView.context).also {
      it.injectPresenter(this)
    }
  }

  /**
   * Provides a view instance returned in [bind] after it's been added to the MapView.
   *
   * @param view plugin view
   */
  override fun onPluginView(view: View) {
    indoorSelectorView = view as? IndoorSelectorView
      ?: throw IllegalArgumentException("The provided view needs to implement IndoorSelectorView")

    // Initialize with empty state
    updateIndoorVisibility()
  }

  /**
   * Called when the plugin is first added to the map.
   */
  override fun initialize() {
    applySettings()
    if (internalSettings.enabled) {
      addOnFloorSelectedListener(onFloorSelectedListener)
    }
  }

  /**
   * Called when the map is destroyed. Should be used to cleanup plugin resources for that map.
   */
  override fun cleanup() {
    floorSelectedListeners.clear()
    currentFloors = emptyList()
    selectedFloorId = null
    indoorManager = null
  }

  /**
   * Provides all map delegate instances.
   */
  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    this.indoorManager = delegateProvider.indoorManager
    // There's no way to remove this callback at the moment
    // To be fixed with Kotlin Flow implementation
    delegateProvider.indoorManager.setOnIndoorUpdatedCallback(onIndoorUpdatedCallback)
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "started" state.
   */
  override fun onStart() {
    // Update visibility based on current state
    updateIndoorVisibility()
  }

  /**
   * Called whenever activity's/fragment's lifecycle is entering a "stopped" state.
   */
  override fun onStop() {
    // No-op for indoor plugin
  }

  /**
   * Add a listener to be notified when a floor is selected.
   *
   * @param listener The listener to add
   */
  override fun addOnFloorSelectedListener(listener: OnFloorSelectedListener) {
    floorSelectedListeners.add(listener)
  }

  /**
   * Remove a floor selection listener.
   *
   * @param listener The listener to remove
   */
  override fun removeOnFloorSelectedListener(listener: OnFloorSelectedListener) {
    floorSelectedListeners.remove(listener)
  }

  /**
   * Called when a floor is selected by the user via the UI.
   * This method updates the view and notifies all registered listeners.
   *
   * @param floorId The ID of the selected floor
   */
  override fun onFloorSelected(floorId: String) {
    if (selectedFloorId != floorId) {
      selectedFloorId = floorId

      // Update the view to reflect the new selection
      indoorSelectorView.updateFloors(currentFloors, selectedFloorId)

      // Notify all listeners (which can call IndoorManager.selectFloor())
      floorSelectedListeners.forEach { listener ->
        listener.onFloorSelected(floorId)
      }
    }
  }

  /**
   * Update the list of available floors.
   * Call this method when indoor data becomes available or changes.
   *
   * @param floors List of available floors
   * @param selectedFloorId ID of the currently selected floor (optional)
   */
  override fun updateFloors(floors: List<IndoorFloor>, selectedFloorId: String?) {
    currentFloors = floors
    this.selectedFloorId = selectedFloorId

    indoorSelectorView.updateFloors(floors, selectedFloorId)
    updateIndoorVisibility()
  }

  /**
   * Update the visibility of the indoor selector based on current state.
   * The selector is visible only if enabled and there are floors to display.
   */
  private fun updateIndoorVisibility() {
    val shouldBeVisible = internalSettings.enabled && currentFloors.isNotEmpty()
    indoorSelectorView.isIndoorSelectorVisible = shouldBeVisible
  }
}