// This file is generated.
@file:JvmName("StyleProjectionUtils")

package com.mapbox.maps.extension.style.projection.generated

import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.generated.Name
import com.mapbox.maps.extension.style.utils.unwrapToAny

/**
 * Extension function to get the projection name provided by the Style Extension.
 *
 * @return projection [Name] that is currently applied to the map
 */
fun StyleInterface.getProjectionName(): Name {
  getStyleProjectionProperty("name").apply {
    return if (kind == StylePropertyValueKind.UNDEFINED) {
      Name.MERCATOR
    } else {
      val projectionAsString = value.unwrapToAny() as String
      return Name.valueOf(projectionAsString.uppercase())
    }
  }
}

/**
 * Extension function to set the Projection by name provided by the Style Extension to the Style.
 *
 * @param projectionName The projection [Name] to be set.
 */
fun StyleInterface.setProjectionName(projectionName: Name) {
  val projection = Projection()
  projection.name(projectionName)
  projection.bindTo(this)
}

// End of generated file.