// This file is generated.

package com.mapbox.maps.extension.style.terrain.generated

import android.util.Log
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.types.TerrainDsl
import com.mapbox.maps.extension.style.utils.unwrap
import kotlin.collections.HashMap

/**
 * A global modifier that elevates layers and markers based on a DEM data source.
 *
 * @see <a href="https://www.mapbox.com/mapbox-gl-style-spec/#terrain">The online documentation</a>
 */
@UiThread
class Terrain(private val sourceId: String) : TerrainDslReceiver, StyleContract.StyleTerrainExtension {
  private var delegate: StyleManagerInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   */
  val exaggeration: Double?
    /**
     * Get the Exaggeration property.
     *
     * @return exaggeration as Double
     */
    get() {
      return getPropertyValue("exaggeration")
    }

  /**
   * Set the Exaggeration property.
   *
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration as Double
   */
  override fun exaggeration(exaggeration: Double) = apply {
    setProperty(PropertyValue("exaggeration", exaggeration))
  }

  /**
   * This is an Expression representation of "exaggeration".
   *
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   */
  val exaggerationAsExpression: Expression?
    /**
     * Get the Exaggeration property as an Expression
     *
     * @return Double
     */
    get() {
      getPropertyValue<Expression>("exaggeration")?.let {
        return it
      }
      exaggeration?.let {
        return Expression.literal(it)
      }
      return null
    }
  /**
   * Set the Exaggeration property
   *
   * @param exaggeration value of exaggeration as Expression
   */
  override fun exaggeration(exaggeration: Expression) = apply {
    val propertyValue = PropertyValue("exaggeration", exaggeration)
    setProperty(propertyValue)
  }

  /**
   * Bind terrain to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: StyleManagerInterface) {
    this.delegate = delegate
    val terrainParams = HashMap<String, Value>()
    terrainParams["source"] = Value(sourceId)
    properties.forEach {
      terrainParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleTerrain(Value(terrainParams))
    expected.error?.let {
      throw RuntimeException("Set Terrain failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleTerrainProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw RuntimeException("Set terrain property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleTerrainProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get terrain property failed: ${e.message}")
        Log.e(TAG, it.getStyleTerrainProperty(propertyName).toString())
        null
      }
    }
    throw RuntimeException("Get property $propertyName failed: terrain is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Terrain"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@TerrainDsl
interface TerrainDslReceiver {
  /**
   * Set the Exaggeration property.
   *
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration as Double
   */
  fun exaggeration(exaggeration: Double = 1.0): Terrain

  /**
   * Set the Exaggeration property
   *
   * @param exaggeration value of exaggeration as Expression
   */
  fun exaggeration(exaggeration: Expression): Terrain
}

/**
 * DSL function for [Terrain].
 */
fun terrain(sourceId: String, block: (TerrainDslReceiver.() -> Unit)? = null): Terrain {
  return if (block != null) {
    Terrain(sourceId).apply(block)
  } else {
    Terrain(sourceId)
  }
}

/**
 * Extension function to get the terrain provided by the Style Extension.
 *
 * @return Terrain
 */
fun StyleManagerInterface.getTerrain(sourceId: String): Terrain {
  return Terrain(sourceId).also { it.bindTo(this) }
}

/**
 * Extension function to add a Terrain provided by the Style Extension to the Style.
 *
 * @param terrain The terrain to be added
 */
fun StyleManagerInterface.addTerrain(terrain: StyleContract.StyleTerrainExtension) {
  terrain.bindTo(this)
}

// End of generated file.