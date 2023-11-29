// This file is generated.
@file:JvmName("TerrainUtils")

package com.mapbox.maps.extension.style.terrain.generated

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract

/**
 * Extension function to get the terrain provided by the Style Extension.
 *
 * @return Terrain
 */
fun MapboxStyleManager.getTerrain(sourceId: String): Terrain {
  return Terrain(sourceId).also { it.delegate = this }
}

/**
 * Extension function to set the Terrain provided by the Style Extension to the Style.
 *
 * @param terrain The terrain to be set
 */
fun MapboxStyleManager.setTerrain(terrain: StyleContract.StyleTerrainExtension) {
  terrain.bindTo(this)
}

/**
 * Removes terrain from style if it was set.
 */
fun MapboxStyleManager.removeTerrain() {
  setStyleTerrain(Value.nullValue())
}

// End of generated file.