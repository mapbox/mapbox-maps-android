package com.mapbox.maps.testapp.model

import com.google.gson.annotations.SerializedName

data class IssPosition(
  @SerializedName("latitude") var latitude: Double? = null,
  @SerializedName("longitude") var longitude: Double? = null
)