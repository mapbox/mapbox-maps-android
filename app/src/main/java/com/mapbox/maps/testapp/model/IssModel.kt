package com.mapbox.maps.testapp.model

import com.google.gson.annotations.SerializedName

data class IssModel(
  @SerializedName("iss_position") var issPosition: IssPosition? = null,
  @SerializedName("message") var message: String? = null,
  @SerializedName("timestamp") var timestamp: Int? = null
)