package com.mapbox.maps.testapp.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpecificExample(
  var name: String,
  private var label: String? = null,
  private var description: String? = null,
  var category: String
) : Parcelable {

  @IgnoredOnParcel
  private var simpleName: String = ""

  init {
    val split =
      name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    simpleName = split[split.size - 1]
  }

  fun getSimpleName(): String {
    return simpleName
  }

  fun getLabel(): String {
    return if (label != null) label!! else simpleName
  }

  fun getDescription(): String {
    return if (description != null) description!! else "-"
  }
}