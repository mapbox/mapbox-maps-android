package com.mapbox.maps.testapp.model

import android.os.Parcel
import android.os.Parcelable

data class SpecificExample(
  val name: String,
  private val _label: String? = null,
  private val _description: String? = null,
  val category: String
) : Parcelable {

  val simpleName: String = buildSimpleName()

  constructor(parcel: Parcel) : this(
    parcel.readString()!!,
    parcel.readString(),
    parcel.readString(),
    parcel.readString()!!
  )

  private fun buildSimpleName(): String {
    val split =
      name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return split[split.size - 1]
  }

  val label: String = _label ?: simpleName

  val description: String = _description ?: "-"

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(label)
    parcel.writeString(description)
    parcel.writeString(category)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<SpecificExample> {
    override fun createFromParcel(parcel: Parcel): SpecificExample {
      return SpecificExample(parcel)
    }

    override fun newArray(size: Int): Array<SpecificExample?> {
      return arrayOfNulls(size)
    }
  }
}