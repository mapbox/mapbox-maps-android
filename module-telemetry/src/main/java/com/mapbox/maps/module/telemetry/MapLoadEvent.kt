package com.mapbox.maps.module.telemetry

import android.annotation.SuppressLint
import android.os.Build
import com.google.gson.annotations.SerializedName
import com.mapbox.maps.base.BuildConfig

/**
 * Event will be sent while map is loaded.
 */
@SuppressLint("ParcelCreator")
internal class MapLoadEvent(
  @field:SerializedName("userId") val userId: String?,
  phoneState: PhoneState
) :
  MapBaseEvent(phoneState) {
  @SerializedName("operatingSystem")
  val operatingSystem: String? = "Android - " + Build.VERSION.RELEASE

  @SerializedName("sdkIdentifier")
  val sdkIdentifier =
    BuildConfig.MAPBOX_SDK_IDENTIFIER

  @SerializedName("sdkVersion")
  val sdkVersion =
    BuildConfig.MAPBOX_SDK_VERSION

  @SerializedName("model")
  val model: String = Build.MODEL

  @SerializedName("carrier")
  val carrier: String? = phoneState.carrier

  @SerializedName("cellularNetworkType")
  val cellularNetworkType: String? = phoneState.cellularNetworkType

  @SerializedName("orientation")
  val orientation: String = phoneState.orientation.orientation

  @SerializedName("resolution")
  val resolution: Float = phoneState.resolution

  @SerializedName("accessibilityFontScale")
  val accessibilityFontScale: Float = phoneState.accessibilityFontScale

  @SerializedName("batteryLevel")
  val batteryLevel: Int = phoneState.batteryLevel

  @SerializedName("pluggedIn")
  val isPluggedIn: Boolean = phoneState.isPluggedIn

  @SerializedName("wifi")
  val isWifi: Boolean = phoneState.isWifi

  override fun getEventName(): String {
    return EVENT_NAME
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val that = other as MapLoadEvent
    if (event != that.event) {
      return false
    }
    if (created != that.created) {
      return false
    }
    if (that.resolution.compareTo(resolution) != 0) {
      return false
    }
    if (that.accessibilityFontScale.compareTo(accessibilityFontScale) != 0) {
      return false
    }
    if (batteryLevel != that.batteryLevel) {
      return false
    }
    if (isPluggedIn != that.isPluggedIn) {
      return false
    }
    if (isWifi != that.isWifi) {
      return false
    }
    if (operatingSystem != that.operatingSystem) {
      return false
    }
    if (sdkIdentifier != that.sdkIdentifier) {
      return false
    }
    if (sdkVersion != that.sdkVersion) {
      return false
    }
    if (model != that.model) {
      return false
    }
    if (userId != that.userId) {
      return false
    }
    if (carrier != that.carrier) {
      return false
    }
    return if (cellularNetworkType != that.cellularNetworkType) {
      false
    } else orientation == that.orientation
  }

  override fun hashCode(): Int {
    var result = operatingSystem?.hashCode() ?: 0
    result = 31 * result + event.hashCode()
    result = 31 * result + created.hashCode()
    result = 31 * result + sdkIdentifier.hashCode()
    result = 31 * result + sdkVersion.hashCode()
    result = 31 * result + model.hashCode()
    result = 31 * result + (userId?.hashCode() ?: 0)
    result = 31 * result + (carrier?.hashCode() ?: 0)
    result = 31 * result + (cellularNetworkType?.hashCode() ?: 0)
    result = 31 * result + orientation.hashCode()
    result =
      31 * result + if (resolution != +0.0f) java.lang.Float.floatToIntBits(resolution) else 0
    result = 31 * result + if (accessibilityFontScale != +0.0f) java.lang.Float.floatToIntBits(
      accessibilityFontScale
    ) else 0
    result = 31 * result + batteryLevel
    result = 31 * result + if (isPluggedIn) 1 else 0
    result = 31 * result + if (isWifi) 1 else 0
    return result
  }

  override fun toString(): String {
    return (
      "MapLoadEvent{" +
        ", operatingSystem='" + operatingSystem + '\'' +
        ", sdkIdentifier='" + sdkIdentifier + '\'' +
        ", sdkVersion='" + sdkVersion + '\'' +
        ", model='" + model + '\'' +
        ", userId='" + userId + '\'' +
        ", carrier='" + carrier + '\'' +
        ", cellularNetworkType='" + cellularNetworkType + '\'' +
        ", orientation='" + orientation + '\'' +
        ", resolution=" + resolution +
        ", accessibilityFontScale=" + accessibilityFontScale +
        ", batteryLevel=" + batteryLevel +
        ", pluggedIn=" + isPluggedIn +
        ", wifi=" + isWifi +
        '}'
      )
  }

  companion object {
    private const val EVENT_NAME = "map.load"
  }
}