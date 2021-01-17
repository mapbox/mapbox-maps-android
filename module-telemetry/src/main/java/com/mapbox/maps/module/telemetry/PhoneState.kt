package com.mapbox.maps.module.telemetry

import android.content.Context
import android.content.res.Configuration
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import com.mapbox.android.telemetry.TelemetryUtils

/**
 * Class that holds kinds of states of the current phone.
 */
internal class PhoneState {
  @SerializedName("created")
  var created: String

  @SerializedName("cellularNetworkType")
  var cellularNetworkType: String? = null

  @SerializedName("orientation")
  var orientation: Orientation

  @SerializedName("carrier")
  var carrier: String? = null

  @SerializedName("batteryLevel")
  var batteryLevel = 0

  @SerializedName("pluggedIn")
  var isPluggedIn = false

  @SerializedName("wifi")
  var isWifi = false

  @SerializedName("accessibilityFontScale")
  var accessibilityFontScale = 0f

  @SerializedName("resolution")
  var resolution = 0f

  @VisibleForTesting
  constructor() {
    created = "2020-07-12"
    orientation = Orientation.ORIENTATION_PORTRAIT
  }

  constructor(context: Context) {
    created = TelemetryUtils.obtainCurrentDate()
    batteryLevel = TelemetryUtils.obtainBatteryLevel(context)
    isPluggedIn = TelemetryUtils.isPluggedIn(context)
    cellularNetworkType = TelemetryUtils.obtainCellularNetworkType(context)
    orientation =
      Orientation.getOrientation(
        context.resources.configuration.orientation
      )
    accessibilityFontScale = context.resources.configuration.fontScale
    carrier = obtainCellularCarrier(context)
    resolution = obtainDisplayDensity(context)
    isWifi = isConnectedToWifi(context)
  }

  private fun obtainCellularCarrier(context: Context): String {
    val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val carrierName = manager.networkOperatorName
    return if (TextUtils.isEmpty(carrierName)) {
      NO_CARRIER
    } else carrierName
  }

  private fun obtainDisplayDensity(context: Context): Float {
    val displayMetrics = DisplayMetrics()
    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
      .getMetrics(displayMetrics)
    return displayMetrics.density
  }

  private fun isConnectedToWifi(context: Context): Boolean {
    return try {
      val wifiMgr = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
      val wifiInfo = wifiMgr.connectionInfo
      wifiMgr.isWifiEnabled && wifiInfo.networkId != NO_NETWORK
    } catch (exception: Exception) {
      false
    }
  }

  internal enum class Orientation(val orientation: String) {
    ORIENTATION_PORTRAIT("Portrait"), ORIENTATION_LANDSCAPE("Landscape");

    companion object {
      fun getOrientation(index: Int): Orientation {
        return if (Configuration.ORIENTATION_PORTRAIT == index) {
          ORIENTATION_PORTRAIT
        } else ORIENTATION_LANDSCAPE
      }
    }
  }

  companion object {
    private const val NO_CARRIER = "EMPTY_CARRIER"
    private const val NO_NETWORK = -1
  }
}