package com.mapbox.maps.module.telemetry

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import com.mapbox.common.TelemetrySystemUtils

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
    created = TelemetrySystemUtils.obtainCurrentDate()
    batteryLevel = TelemetrySystemUtils.obtainBatteryLevel(context)
    isPluggedIn = TelemetrySystemUtils.isPluggedIn(context)
    cellularNetworkType = TelemetrySystemUtils.obtainCellularNetworkType(context)
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
    @Suppress("DEPRECATION")
    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
      .getMetrics(displayMetrics)
    return displayMetrics.density
  }

  @SuppressLint("MissingPermission")
  private fun isConnectedToWifi(context: Context): Boolean {
    try {
      val connectivityManager =
        context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
      } else {
        @Suppress("DEPRECATION")
        val activeNetworkInfo = connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return activeNetworkInfo.isConnected && activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
      }
    } catch (exception: Exception) {
      return false
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