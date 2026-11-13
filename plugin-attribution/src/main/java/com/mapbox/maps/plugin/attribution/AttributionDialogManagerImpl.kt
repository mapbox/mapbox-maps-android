package com.mapbox.maps.plugin.attribution

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Responsible for managing attribution interactions on the map.
 *
 * When the user clicks the attribution icon, [AttributionDialogManagerImpl.showAttribution] will be
 * invoked and the attribution sheet is shown, listing the attributions found in the map style along
 * with the telemetry and geofencing consent switches.
 */
class AttributionDialogManagerImpl(
  private val context: Context
) : AttributionDialogManager, DialogInterface.OnClickListener {

  private lateinit var attributionList: List<Attribution>
  private var mapAttributionDelegate: MapAttributionDelegate? = null
  private var telemetry: MapTelemetry? = null
  private var geofencingConsent: MapGeofencingConsent? = null

  /** The sheet currently on screen, so it can be dismissed when the map stops. */
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var sheetDialog: Dialog? = null
  /**
   * Invoked when the map attribution should be shown to the end user
   */
  override fun showAttribution(mapAttributionDelegate: MapAttributionDelegate) {
    this.mapAttributionDelegate = mapAttributionDelegate
    this.telemetry = mapAttributionDelegate.telemetry()
    this.geofencingConsent = mapAttributionDelegate.geofencingConsent()
    val withMapboxGeofencingConsent = geofencingConsent?.shouldShowConsent() ?: false
    this.attributionList =
      mapAttributionDelegate.parseAttributions(context, AttributionParserConfig(withMapboxGeofencingConsent = withMapboxGeofencingConsent))
    // A finishing host is showAttributionSheetDialog's business; it declines rather than throwing.
    showAttributionSheet(attributionList)
  }

  /**
   * Show the attribution sheet, whatever the map is hosted in.
   *
   * The sheet is a [Dialog], so it needs nothing from the fragment framework and looks the same in
   * every host.
   */
  private fun showAttributionSheet(attributions: List<Attribution>) {
    val delegate = mapAttributionDelegate ?: return
    if (sheetDialog?.isShowing == true) {
      return
    }

    sheetDialog = showAttributionSheetDialog(
      context = context,
      payload = AttributionSheetPayload(
        attributionList = attributions,
        mapAttributionDelegate = delegate,
        telemetry = telemetry,
        geofencingConsent = geofencingConsent,
      ),
    )?.apply {
      setOnDismissListener { sheetDialog = null }
    }
  }

  /**
   * Opens the web page of the attribution at [which].
   */
  @Deprecated("The attribution sheet replaced the attribution dialog; the SDK no longer calls this.")
  override fun onClick(dialog: DialogInterface, which: Int) {
    showMapAttributionWebPage(attributionList[which].url)
  }

  /**
   * Called when the hosting activity is stopped
   */
  override fun onStop() {
    // The sheet's payload holds live map objects that must not outlive this MapView.
    sheetDialog?.takeIf { it.isShowing }?.dismiss()
    sheetDialog = null
  }

  private fun showMapAttributionWebPage(attributionUrl: String) {
    var url = attributionUrl
    mapAttributionDelegate?.let {
      if (url.contains(FEEDBACK_KEY_WORD)) {
        url = it.buildMapBoxFeedbackUrl(context)
      }
    }
    if (url.isNotEmpty()) {
      showWebPage(url)
    }
  }

  private fun showWebPage(url: String) = context.openAttributionUrl(url)

  private companion object {
    private const val FEEDBACK_KEY_WORD = "contribute"
  }
}