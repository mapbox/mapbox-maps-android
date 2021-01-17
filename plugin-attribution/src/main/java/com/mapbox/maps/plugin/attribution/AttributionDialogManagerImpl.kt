package com.mapbox.maps.plugin.attribution

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Responsible for managing attribution interactions on the map.
 *
 * When the user clicks the attribution icon, [AttributionDialogManagerImpl.showAttribution]} will be invoked.
 * An attribution dialog will be shown to the user with contents based on the attributions found in the map style.
 * Additionally an telemetry option item is shown to configure telemetry settings.
 *
 */
class AttributionDialogManagerImpl(
  private val context: Context
) : AttributionDialogManager, DialogInterface.OnClickListener {

  private lateinit var attributionList: List<Attribution>
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var dialog: AlertDialog? = null
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  internal var telemetryDialog: AlertDialog? = null
  private var mapAttributionDelegate: MapAttributionDelegate? = null
  private var telemetry: MapTelemetry? = null
  /**
   * Invoked when the map attribution should be shown to the end user
   */
  override fun showAttribution(mapAttributionDelegate: MapAttributionDelegate) {
    this.mapAttributionDelegate = mapAttributionDelegate
    this.telemetry = mapAttributionDelegate.telemetry()
    this.attributionList =
      mapAttributionDelegate.parseAttributions(context, AttributionParserConfig())
    var isActivityFinishing = false
    if (context is Activity) {
      isActivityFinishing = context.isFinishing
    }
    // check if hosting activity isn't finishing
    if (!isActivityFinishing) {
      val attributionTitles = attributionList.map { it.title }.toTypedArray()
      showAttributionDialog(attributionTitles)
    }
  }

  /**
   * Invoked when the map attribution dialog should be shown to the end user
   *
   * @param attributionTitles an array of attribution titles
   */
  private fun showAttributionDialog(attributionTitles: Array<String>) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(R.string.mapbox_attributionsDialogTitle)
    builder.setAdapter(
      ArrayAdapter(
        context,
        R.layout.mapbox_attribution_list_item,
        attributionTitles
      ),
      this
    )
    dialog = builder.show()
  }

  /**
   * Called when someone selects an attribution or telemetry settings from the dialog
   */
  override fun onClick(dialog: DialogInterface, which: Int) {
    if (attributionList[which].title.contains(TELEMETRY_KEY_WORLD)) {
      showTelemetryDialog()
    } else {
      showMapAttributionWebPage(which)
    }
  }

  /**
   * Called when the hosting activity is stopped
   */
  override fun onStop() {
    dialog?.takeIf { it.isShowing }?.dismiss()
    telemetryDialog?.takeIf { it.isShowing }?.dismiss()
  }

  private fun showTelemetryDialog() {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(R.string.mapbox_attributionTelemetryTitle)
    builder.setMessage(R.string.mapbox_attributionTelemetryMessage)
    builder.setPositiveButton(R.string.mapbox_attributionTelemetryPositive) { dialog, _ ->
      telemetry?.setUserTelemetryRequestState(true)
      dialog.cancel()
    }
    builder.setNeutralButton(R.string.mapbox_attributionTelemetryNeutral) { dialog, _ ->
      showWebPage(context.resources.getString(R.string.mapbox_telemetryLink))
      dialog.cancel()
    }
    builder.setNegativeButton(R.string.mapbox_attributionTelemetryNegative) { dialog, _ ->
      telemetry?.setUserTelemetryRequestState(false)
      dialog.cancel()
    }
    telemetryDialog = builder.show()
  }

  private fun showMapAttributionWebPage(which: Int) {
    var url = attributionList[which].url
    mapAttributionDelegate?.let {
      if (url.contains(FEEDBACK_KEY_WORLD)) {
        url = it.buildMapBoxFeedbackUrl(context)
      }
    }
    showWebPage(url)
  }

  private fun showWebPage(url: String) {
    if (context is Activity) {
      try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)
      } catch (exception: ActivityNotFoundException) { // explicitly handling if the device hasn't have a web browser installed. #8899
        Toast.makeText(context, R.string.mapbox_attributionErrorNoBrowser, Toast.LENGTH_LONG).show()
      }
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val FEEDBACK_KEY_WORLD = "feedback"
    private const val TELEMETRY_KEY_WORLD = "Telemetry"
  }
}