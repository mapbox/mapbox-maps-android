package com.mapbox.maps.module.telemetry

import android.annotation.SuppressLint
import android.os.Parcel
import com.google.gson.annotations.SerializedName
import com.mapbox.android.telemetry.Event

/**
 * Base event class for telemetry events.
 */
@SuppressLint("ParcelCreator")
internal abstract class MapBaseEvent(phoneState: PhoneState) :
  Event() {
  @SerializedName("event")
  val event: String

  @SerializedName("created")
  val created: String

  init {
    event = this.getEventName()
    created = phoneState.created
  }

  abstract fun getEventName(): String

  override fun describeContents(): Int {
    return 0
  }

  override fun writeToParcel(dest: Parcel, flags: Int) {}
}