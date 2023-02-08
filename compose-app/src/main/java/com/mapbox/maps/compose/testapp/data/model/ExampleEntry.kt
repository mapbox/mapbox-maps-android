package com.mapbox.maps.compose.testapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

public sealed class ExampleEntry

@Parcelize
public data class ExampleCategory(
  val category: String
) : ExampleEntry(), Parcelable

@Parcelize
public data class SpecificExample(
  val name: String,
  private val label: String? = null,
  private val description: String? = null,
  val category: String
) : ExampleEntry(), Parcelable {

  val simpleName: String
    get() = buildSimpleName()

  private fun buildSimpleName(): String {
    val split =
      name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    return split.lastOrNull() ?: "-"
  }

  public fun getLabel(): String {
    return label ?: simpleName
  }

  public fun getDescription(): String {
    return description ?: "-"
  }
}