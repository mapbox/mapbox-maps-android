// This file is generated.
@file:JvmName("StyleProjectionUtils")

package com.mapbox.maps.extension.style.projection.generated

import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.utils.unwrapToAny

/**
 * Extension function to get the projection name provided by the Style Extension.
 *
 * @return projection [ProjectionName] that is currently applied to the map
 */
fun StyleInterface.getProjectionName(): ProjectionName {
  getStyleProjectionProperty("name").apply {
    return if (kind == StylePropertyValueKind.UNDEFINED) {
      ProjectionName.MERCATOR
    } else {
      val projectionAsString = value.unwrapToAny() as String
      return ProjectionName.valueOf(projectionAsString.uppercase())
    }
  }
}

/**
 * Extension function to get the projection provided by the Style Extension.
 *
 * @return projection [Projection] that is currently applied to the map
 */
fun StyleInterface.getProjection(): Projection {
  val projection = Projection()
  getStyleProjectionProperty("name").apply {
    if (kind == StylePropertyValueKind.UNDEFINED) {
      projection.name(ProjectionName.MERCATOR)
    } else {
      val projectionAsString = value.unwrapToAny() as String
      projection.name(ProjectionName.valueOf(projectionAsString.uppercase()))
    }
  }
  projection.bindTo(this)
  return projection
}

/**
 * Extension function to set the Projection by name provided by the Style Extension to the Style.
 *
 * @param projectionName The projection [ProjectionName] to be set.
 */
fun StyleInterface.setProjectionName(projectionName: ProjectionName) {
  val projection = Projection()
  projection.name(projectionName)
  projection.bindTo(this)
}

/**
 * Extension function to set the Projection provided by the Style Extension to the Style.
 *
 * @param projection The projection [Projection] to be set.
 */
fun StyleInterface.setProjection(projection: Projection) {
  projection.bindTo(this)
}

// End of generated file.