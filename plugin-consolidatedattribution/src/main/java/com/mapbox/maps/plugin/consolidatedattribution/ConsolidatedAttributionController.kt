package com.mapbox.maps.plugin.consolidatedattribution

import android.content.Context
import android.view.View
import androidx.annotation.RestrictTo
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.android.gestures.RotateGestureDetector
import com.mapbox.android.gestures.StandardScaleGestureDetector
import com.mapbox.common.Cancelable
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StyleAttributionsChangedCallback
import com.mapbox.maps.StyleLoadedCallback
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionDialogManager
import com.mapbox.maps.plugin.attribution.AttributionDialogManagerImpl
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.attribution.isMapboxFeedback
import com.mapbox.maps.plugin.consolidatedattribution.generated.ConsolidatedAttributionSettings
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.gestures.OnFlingListener
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.OnMapLongClickListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.OnRotateListener
import com.mapbox.maps.plugin.gestures.OnScaleListener

/**
 * Drives a [ConsolidatedAttributionView]: updates the label from the style, collapses the pill on
 * map gestures and opens the attribution sheet on tap. Used by both the plugin and Compose.
 *
 * @suppress
 */
@MapboxExperimental
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class ConsolidatedAttributionController(private val view: ConsolidatedAttributionView) {

  private lateinit var mapAttributionDelegate: MapAttributionDelegate
  private lateinit var mapListenerDelegate: MapListenerDelegate
  private lateinit var styleManager: MapboxStyleManager
  private var gesturesPlugin: GesturesPlugin? = null
  private var dialogManager: AttributionDialogManager? = null

  /** The SDK's sheet, restored when the custom manager is removed. */
  private var defaultDialogManager: AttributionDialogManager? = null
  private var styleLoadedCancelable: Cancelable? = null
  private var styleAttributionsChangedCancelable: Cancelable? = null

  /** A disabled control loads no attribution text. */
  private var enabled = false

  /** Report the opt-in once per control. */
  private var featureTelemetryReported = false

  /** Last settings passed to [applyAppearance]. Compose calls it on every recomposition. */
  private var appliedSettings: ConsolidatedAttributionSettings? = null

  /** Resolve the delegates the control needs. Call before [initialize]. */
  fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    mapAttributionDelegate = delegateProvider.mapAttributionDelegate
    mapListenerDelegate = delegateProvider.mapListenerDelegate
    styleManager = delegateProvider.mapStyleManagerDelegate
    gesturesPlugin = delegateProvider.mapPluginProviderDelegate
      .getPlugin(Plugin.MAPBOX_GESTURES_PLUGIN_ID)
  }

  /** Wire the pill's tap target to the attribution sheet. */
  fun attachTapHandling(context: Context) {
    if (defaultDialogManager == null) {
      defaultDialogManager = AttributionDialogManagerImpl(context)
    }
    if (dialogManager == null) {
      dialogManager = defaultDialogManager
    }
    view.setOnTapListener { showAttribution() }
  }

  /** Set a custom [AttributionDialogManager]. Null restores the SDK's sheet. */
  fun setCustomAttributionDialogManager(dialogManager: AttributionDialogManager?) {
    val replacement = dialogManager ?: defaultDialogManager
    if (this.dialogManager === replacement) {
      // Compose sets the same manager on every recomposition; don't close its open sheet.
      return
    }
    this.dialogManager?.onStop()
    this.dialogManager = replacement
  }

  /** The delegate backing the control's attribution text. */
  fun getMapAttributionDelegate(): MapAttributionDelegate = mapAttributionDelegate

  /** Subscribe to the map events the control reacts to. */
  fun initialize() {
    // Gestures only collapse the pill, so listen to them only while it is expanded.
    view.onExpandedChanged = { expanded ->
      if (expanded) subscribeGestures() else unsubscribeGestures()
    }
    styleLoadedCancelable = mapListenerDelegate.subscribeStyleLoaded(
      StyleLoadedCallback { loadAttributionText() }
    )
    // Sources added after the style loads (e.g. satellite imagery) change the attributions too.
    styleAttributionsChangedCancelable = styleManager.subscribeStyleAttributionsChanged(
      StyleAttributionsChangedCallback { loadAttributionText() }
    )
  }

  /** Apply the settings that don't affect placement; the host positions the view. */
  fun applyAppearance(settings: ConsolidatedAttributionSettings) {
    if (appliedSettings == settings) {
      return
    }
    appliedSettings = settings
    enabled = settings.enabled
    if (settings.enabled && !featureTelemetryReported) {
      featureTelemetryReported = true
      ConsolidatedAttributionTelemetryEvents.enabled.increment()
    }
    view.visibility = if (settings.enabled) View.VISIBLE else View.GONE
    view.introExpansion = settings.introExpansion
    view.applyMaterial(settings.material, settings.tintColor)
    view.applyCollapsedStyle(settings.collapsedStyle)
    // The style may already be loaded.
    loadAttributionText()
  }

  /** The map width, which limits the expanded pill. */
  fun onMapWidthChanged(width: Int) {
    view.mapViewWidth = width
  }

  /** Dismiss anything the control is showing, when the map stops. */
  fun onStop() {
    dialogManager?.onStop()
  }

  /** Drop all subscriptions and close the sheet, which holds live map objects. */
  fun cleanup() {
    dialogManager?.onStop()
    styleLoadedCancelable?.cancel()
    styleLoadedCancelable = null
    styleAttributionsChangedCancelable?.cancel()
    styleAttributionsChangedCancelable = null
    view.onExpandedChanged = null
    unsubscribeGestures()
    gesturesPlugin = null
  }

  private fun subscribeGestures() {
    gesturesPlugin?.let {
      it.addOnMapClickListener(collapseOnGesture)
      it.addOnMapLongClickListener(collapseOnGesture)
      it.addOnMoveListener(collapseOnGesture)
      it.addOnScaleListener(collapseOnGesture)
      it.addOnRotateListener(collapseOnGesture)
      it.addOnFlingListener(collapseOnGesture)
    }
  }

  private fun unsubscribeGestures() {
    gesturesPlugin?.let {
      it.removeOnMapClickListener(collapseOnGesture)
      it.removeOnMapLongClickListener(collapseOnGesture)
      it.removeOnMoveListener(collapseOnGesture)
      it.removeOnScaleListener(collapseOnGesture)
      it.removeOnRotateListener(collapseOnGesture)
      it.removeOnFlingListener(collapseOnGesture)
    }
  }

  private fun showAttribution() {
    dialogManager?.showAttribution(mapAttributionDelegate)
  }

  /**
   * Collapses the pill on user gestures. Camera changes from code don't count, or the intro
   * would never show.
   */
  private val collapseOnGesture = object :
    OnMapClickListener,
    OnMapLongClickListener,
    OnFlingListener,
    OnMoveListener,
    OnScaleListener,
    OnRotateListener {
    override fun onMapClick(point: Point): Boolean {
      view.collapseOnInteraction()
      return false
    }

    override fun onMapLongClick(point: Point): Boolean {
      view.collapseOnInteraction()
      return false
    }

    override fun onFling() = view.collapseOnInteraction()

    override fun onMoveBegin(detector: MoveGestureDetector) = view.collapseOnInteraction()
    override fun onMove(detector: MoveGestureDetector) = false
    override fun onMoveEnd(detector: MoveGestureDetector) = Unit

    override fun onScaleBegin(detector: StandardScaleGestureDetector) = view.collapseOnInteraction()
    override fun onScale(detector: StandardScaleGestureDetector) = Unit
    override fun onScaleEnd(detector: StandardScaleGestureDetector) = Unit

    override fun onRotateBegin(detector: RotateGestureDetector) = view.collapseOnInteraction()
    override fun onRotate(detector: RotateGestureDetector) = Unit
    override fun onRotateEnd(detector: RotateGestureDetector) = Unit
  }

  private fun loadAttributionText() {
    if (!enabled || !::mapAttributionDelegate.isInitialized) {
      return
    }
    val context = view.context
    // Only data providers; the rest is in the sheet, and the wordmark stands for Mapbox.
    val config = AttributionParserConfig(
      withImproveMap = false,
      withCopyrightSign = false,
      withTelemetryAttribution = false,
      withMapboxAttribution = false,
      withMapboxPrivacyPolicy = false,
      withMapboxGeofencingConsent = false,
    )
    view.setAttributionText(
      consolidatedAttributionText(mapAttributionDelegate.parseAttributions(context, config))
    )
  }

  /** Builds the pill label: "© Provider" for each data provider. */
  private fun consolidatedAttributionText(attributions: List<Attribution>): String =
    attributions
      .filterNot { it.isMapboxFeedback() }
      .map { it.title.trim() }
      .filter { it.isNotEmpty() && !it.equals(MAPBOX_COPYRIGHT_TITLE, ignoreCase = true) }
      .distinct()
      .joinToString(" ") { COPYRIGHT_SIGN + it }
}

internal fun MapboxStyleManager.subscribeStyleAttributionsChanged(
  callback: StyleAttributionsChangedCallback
): Cancelable = styleManager.subscribe(callback)

// Top-level, so they stay out of the public ABI.
private const val MAPBOX_COPYRIGHT_TITLE = "mapbox"
private const val COPYRIGHT_SIGN = "© "