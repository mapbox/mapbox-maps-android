// This file is generated.
@file:JvmName("TerrainUtils")

package com.mapbox.maps.extension.style.terrain.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract

/**
 * Extension function to get the terrain provided by the Style Extension.
 *
 * @return Terrain
 */
fun Style.getTerrain(sourceId: String): Terrain {
  return Terrain(sourceId).also { it.delegate = this }
}

/**
 * Extension function to set the Terrain provided by the Style Extension to the Style.
 *
 * @param terrain The terrain to be set
 */
fun Style.setTerrain(terrain: StyleContract.StyleTerrainExtension) {
  terrain.bindTo(this)
}

/**
 * Removes terrain from style if it was set.
 */
fun Style.removeTerrain() {
  setStyleTerrain(Value.nullValue())
}

// End of generated file.