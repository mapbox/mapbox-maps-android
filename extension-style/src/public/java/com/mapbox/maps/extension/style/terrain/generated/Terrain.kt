// This file is generated.
package com.mapbox.maps.extension.style.terrain.generated

import android.util.Log
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.types.StyleTransition
import com.mapbox.maps.extension.style.types.TerrainDsl
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * A global modifier that elevates layers and markers based on a DEM data source.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#terrain)
 */
@UiThread
class Terrain(private val sourceId: String) : TerrainDslReceiver, StyleContract.StyleTerrainExtension {
  internal var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   */
  val exaggeration: Double?
    /**
     * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
     *
     * @return exaggeration as Double
     */
    get() {
      return getPropertyValue("exaggeration")
    }

  /**
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration as Double
   */
  override fun exaggeration(exaggeration: Double): Terrain = apply {
    setProperty(PropertyValue("exaggeration", exaggeration))
  }

  /**
   * Exaggeration property transition options.
   */
  val exaggerationTransition: StyleTransition?
    /**
     * Get the Exaggeration property transition options.
     *
     * @return transition options for exaggeration
     */
    get() {
      return getTransitionProperty("exaggeration-transition")
    }
  /**
   * Set the Exaggeration property transition options.
   *
   * @param options transition options for exaggeration
   */
  override fun exaggerationTransition(options: StyleTransition): Terrain = apply {
    val propertyValue = PropertyValue("exaggeration-transition", options)
    setProperty(propertyValue)
  }

  /**
   * DSL for [exaggerationTransition].
   */
  override fun exaggerationTransition(block: StyleTransition.Builder.() -> Unit): Terrain = apply {
    exaggerationTransition(StyleTransition.Builder().apply(block).build())
  }

  /**
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * This is an Expression representation of "exaggeration".
   */
  val exaggerationAsExpression: Expression?
    /**
     * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
     *
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
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration value of exaggeration as Expression
   */
  override fun exaggeration(exaggeration: Expression): Terrain = apply {
    val propertyValue = PropertyValue("exaggeration", exaggeration)
    setProperty(propertyValue)
  }

  /**
   * Bind terrain to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    val terrainParams = HashMap<String, Value>()
    terrainParams["source"] = Value(sourceId)
    properties.forEach {
      terrainParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleTerrain(Value(terrainParams))
    expected.error?.let {
      throw MapboxStyleException("Set terrain failed: $it")
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
      throw MapboxStyleException("Set terrain property failed: $it")
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
    throw MapboxStyleException("Get property $propertyName failed: terrain is not added to style yet.")
  }

  private fun getTransitionProperty(transitionName: String): StyleTransition? {
    delegate?.let {
      return try {
        @Suppress("UNCHECKED_CAST")
        val styleLayerProperty =
          it.getStyleTerrainProperty(transitionName).value.contents as HashMap<String, Value>
        val duration = styleLayerProperty["duration"]?.contents as Long
        val delay = styleLayerProperty["delay"]?.contents as Long
        StyleTransition.Builder().delay(delay).duration(duration).build()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get terrain property failed: ${e.message}")
        Log.e(TAG, it.getStyleTerrainProperty(transitionName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $transitionName failed: terrain is not added to style yet.")
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
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration as Double
   */
  fun exaggeration(exaggeration: Double = 1.0): Terrain

  /**
   * Exaggerates the elevation of the terrain by multiplying the data from the DEM with this value.
   *
   * @param exaggeration value of exaggeration as Expression
   */
  fun exaggeration(exaggeration: Expression): Terrain

  /**
   * Set the Exaggeration property transition options.
   *
   * @param options transition options for exaggeration
   */
  fun exaggerationTransition(options: StyleTransition): Terrain

  /**
   * DSL for [exaggerationTransition].
   */
  fun exaggerationTransition(block: StyleTransition.Builder.() -> Unit): Terrain
}

/**
 * DSL function for creating [Terrain] instance.
 */
@JvmOverloads
fun terrain(sourceId: String, block: (TerrainDslReceiver.() -> Unit)? = null): Terrain {
  return if (block != null) {
    Terrain(sourceId).apply(block)
  } else {
    Terrain(sourceId)
  }
}

// End of generated file.