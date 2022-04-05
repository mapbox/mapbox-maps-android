// This file is generated.

package com.mapbox.maps.extension.style.projection.generated

import android.util.Log
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.types.ProjectionDsl
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * A style's projection property sets which projection a map is rendered in.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/projection/)
 */
@UiThread
class Projection : ProjectionDslReceiver, StyleContract.StyleProjectionExtension {
  private var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  /**
   * The name of the projection to be used for rendering the map.
   */
  val name: ProjectionName?
    /**
     * Get the Name property.
     *
     * @return name as ProjectionName
     */
    get() {
      return getPropertyValue("name")
    }

  /**
   * Set the Name property.
   *
   * The name of the projection to be used for rendering the map.
   *
   * @param name as ProjectionName
   */
  override fun name(name: ProjectionName) = apply {
    setProperty(PropertyValue("name", name))
  }

  /**
   * This is an Expression representation of "name".
   *
   * The name of the projection to be used for rendering the map.
   */
  val nameAsExpression: Expression?
    /**
     * Get the Name property as an Expression
     *
     * @return ProjectionName
     */
    get() {
      getPropertyValue<Expression>("name")?.let {
        return it
      }
      name?.let {
        return Expression.literal(it.value)
      }
      return null
    }
  /**
   * Set the Name property
   *
   * @param name value of name as Expression
   */
  override fun name(name: Expression) = apply {
    val propertyValue = PropertyValue("name", name)
    setProperty(propertyValue)
  }

  /**
   * Bind projection to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: StyleInterface) {
    this.delegate = delegate
    val projectionParams = HashMap<String, Value>()
    properties.forEach {
      projectionParams[it.key] = it.value.value
    }
    val expected = delegate.setStyleProjection(Value(projectionParams))
    expected.error?.let {
      throw MapboxStyleException("Set projection failed: $it")
    }
  }

  private fun setProperty(property: PropertyValue<*>) {
    properties[property.propertyName] = property
    updateProperty(property)
  }

  private fun updateProperty(propertyValue: PropertyValue<*>) {
    val expected = delegate?.setStyleProjectionProperty(
      propertyValue.propertyName,
      propertyValue.value
    )
    expected?.error?.let {
      throw MapboxStyleException("Set projection property failed: $it")
    }
  }

  private inline fun <reified T> getPropertyValue(propertyName: String): T? {
    delegate?.let {
      return try {
        it.getStyleProjectionProperty(propertyName).unwrap()
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get projection property failed: ${e.message}")
        Log.e(TAG, it.getStyleProjectionProperty(propertyName).toString())
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: projection is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val TAG = "Mbgl-Projection"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@ProjectionDsl
interface ProjectionDslReceiver {
  /**
   * Set the Name property.
   *
   * The name of the projection to be used for rendering the map.
   *
   * @param name as ProjectionName
   */
  fun name(name: ProjectionName = ProjectionName.MERCATOR): Projection

  /**
   * Set the Name property
   *
   * @param name value of name as Expression
   */
  fun name(name: Expression): Projection
}

/**
 * DSL function for [Projection].
 */
fun projection(block: ProjectionDslReceiver.() -> Unit): Projection {
  return Projection().apply(block)
}

// End of generated file.