package com.mapbox.maps.extension.compose.style.internal

import android.os.Parcel
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Feature
import com.mapbox.maps.GeoJSONSourceData
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.sources.generated.GeoJSONData
import kotlinx.parcelize.Parceler

internal object ValueParceler : Parceler<Value> {
  override fun create(parcel: Parcel): Value {
    return Value.fromJson(parcel.readString()!!).value!!
  }

  override fun Value.write(parcel: Parcel, flags: Int) {
    parcel.writeString(this.toJson())
  }
}

@OptIn(MapboxExperimental::class)
internal object GeoJSONDataParceler : Parceler<GeoJSONData> {
  override fun create(parcel: Parcel): GeoJSONData {
    val typeInfo = GeoJSONSourceData.Type.valueOf(parcel.readString()!!)
    return when (typeInfo) {
      GeoJSONSourceData.Type.FEATURE -> GeoJSONData(Feature.fromJson(parcel.readString()!!))
      GeoJSONSourceData.Type.GEOMETRY -> GeoJSONData(
        Feature.fromJson(parcel.readString()!!).geometry()!!
      )

      GeoJSONSourceData.Type.LIST -> {
        val size = parcel.readInt()
        val list = mutableListOf<Feature>()
        repeat(size) {
          list.add(Feature.fromJson(parcel.readString()!!))
        }
        GeoJSONData(list)
      }

      GeoJSONSourceData.Type.STRING -> GeoJSONData(parcel.readString()!!)
      else -> throw IllegalStateException("Cannot create GeoJSONData from parcel $parcel")
    }
  }

  override fun GeoJSONData.write(parcel: Parcel, flags: Int) {
    val typeInfo = data.typeInfo.name
    parcel.writeSerializable(typeInfo)
    when (data.typeInfo) {
      GeoJSONSourceData.Type.FEATURE -> {
        parcel.writeString(data.feature.toJson())
      }

      GeoJSONSourceData.Type.GEOMETRY -> {
        // wrap into a feature to be able to use fromJson constructor.
        parcel.writeString(Feature.fromGeometry(data.geometry).toJson())
      }

      GeoJSONSourceData.Type.LIST -> {
        parcel.writeInt(data.list.size)
        data.list.forEach { parcel.writeString(it.toJson()) }
      }

      GeoJSONSourceData.Type.STRING -> {
        parcel.writeString(data.string)
      }

      else -> {
        Unit
      }
    }
  }
}