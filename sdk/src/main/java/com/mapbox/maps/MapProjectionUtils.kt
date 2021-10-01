package com.mapbox.maps

import androidx.annotation.VisibleForTesting
import com.mapbox.bindgen.Value
import com.mapbox.maps.plugin.MapProjection
import java.lang.RuntimeException

internal object MapProjectionUtils {

  fun MapProjection.toValue() = when (this) {
    MapProjection.Globe -> Value.valueOf(hashMapOf(NAME_KEY to Value(GLOBE_PROJECTION_NAME)))
    MapProjection.Mercator -> Value.valueOf(hashMapOf(NAME_KEY to Value(MERCATOR_PROJECTION_NAME)))
  }

  fun fromValue(value: Value): MapProjection {
    @Suppress("UNCHECKED_CAST")
    val map = value.contents as? HashMap<String, Value>
    return when (map?.get(NAME_KEY)?.contents as? String) {
      MERCATOR_PROJECTION_NAME -> MapProjection.Mercator
      GLOBE_PROJECTION_NAME -> MapProjection.Globe
      else -> throw RuntimeException("Could not cast given Value to valid MapProjection!")
    }
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  const val NAME_KEY = "name"
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  const val MERCATOR_PROJECTION_NAME = "mercator"
  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  const val GLOBE_PROJECTION_NAME = "globe"
}