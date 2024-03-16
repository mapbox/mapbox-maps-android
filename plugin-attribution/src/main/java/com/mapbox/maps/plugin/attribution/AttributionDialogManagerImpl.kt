package com.mapbox.maps.plugin.attribution

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
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
      showAttributionDialog(attributionList)
    }
  }

  /**
   * Invoked when the map attribution dialog should be shown to the end user
   *
   * @param attributions an array of attribution titles
   */
  private fun showAttributionDialog(attributions: List<Attribution>) {
    val builder = prepareDialogBuilder()
    builder.setTitle(R.string.mapbox_attributionsDialogTitle)
    val adapter: ArrayAdapter<Attribution> = object : ArrayAdapter<Attribution>(
      context,
      R.layout.mapbox_attribution_list_item, attributions
    ) {
      override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val attribution = attributions[position]
        view.findViewById<TextView>(android.R.id.text1).apply {
          // if attribution url is empty, we show them as disabled.
          setTextColor(
            if (attribution.url.isEmpty()) Color.GRAY else ContextCompat.getColor(
              context,
              R.color.mapbox_blue
            )
          )
          text = attribution.title
        }
        return view
      }
    }
    builder.setAdapter(adapter, this)
    dialog = builder.show()
  }

  /**
   * Called when someone selects an attribution or telemetry settings from the dialog
   */
  override fun onClick(dialog: DialogInterface, which: Int) {
    val attribution = attributionList[which]
    if (attribution.url == Attribution.ABOUT_TELEMETRY_URL) {
      showTelemetryDialog()
    } else {
      showMapAttributionWebPage(attribution.url)
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
    val builder = prepareDialogBuilder()
    builder.setTitle(R.string.mapbox_attributionTelemetryTitle)
    builder.setMessage(R.string.mapbox_attributionTelemetryMessage)
    builder.setPositiveButton(R.string.mapbox_attributionTelemetryPositive) { dialog, _ ->
      telemetry?.userTelemetryRequestState = true
      dialog.cancel()
    }
    builder.setNeutralButton(R.string.mapbox_attributionTelemetryNeutral) { dialog, _ ->
      showWebPage(context.resources.getString(R.string.mapbox_telemetryLink))
      dialog.cancel()
    }
    builder.setNegativeButton(R.string.mapbox_attributionTelemetryNegative) { dialog, _ ->
      telemetry?.userTelemetryRequestState = false
      dialog.cancel()
    }
    telemetryDialog = builder.show()
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

  private fun showWebPage(url: String) {
    try {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(url)
      context.applicationContext.startActivity(intent)
    } catch (exception: ActivityNotFoundException) {
      Toast.makeText(context, R.string.mapbox_attributionErrorNoBrowser, Toast.LENGTH_LONG).show()
    } catch (t: Throwable) {
      Toast.makeText(context, t.localizedMessage, Toast.LENGTH_LONG).show()
    }
  }

  @SuppressLint("PrivateResource")
  private fun prepareDialogBuilder(): AlertDialog.Builder {
    // using way from AOSP to determine if current theme used is AppCompat, see
    // https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:appcompat/appcompat/src/main/java/androidx/appcompat/app/AppCompatDelegateImpl.java;l=908
    val a = context.obtainStyledAttributes(R.styleable.AppCompatTheme)
    val appCompatThemeUsed = try {
      a.hasValue(R.styleable.AppCompatTheme_windowActionBar)
    } catch (_: Throwable) {
      false
    }
    val builder = if (appCompatThemeUsed) {
      AlertDialog.Builder(context)
    } else {
      AlertDialog.Builder(
        // explicitly use Day-Night AppCompat theme if non AppCompat theme is used in activity
        // noting that using ContextThemeWrapper should make sure we apply our theme on top of base one
        ContextThemeWrapper(context, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
      )
    }
    a.recycle()
    return builder
  }

  private companion object {
    private const val FEEDBACK_KEY_WORD = "feedback"
  }
}