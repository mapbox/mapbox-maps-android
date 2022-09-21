package com.mapbox.maps.module.telemetry

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName

/**
 * Base event class for telemetry events.
 */
@SuppressLint("ParcelCreator")
internal abstract class MapBaseEvent(phoneState: PhoneState) {
  @SerializedName("event")
  val event: String

  @SerializedName("created")
  val created: String

  init {
    event = this.getEventName()
    created = phoneState.created
  }

  abstract fun getEventName(): String
}