// This file is generated.

package com.mapbox.maps.extension.style.projection.generated

import android.opengl.GLES20
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName

/**
 * A style's projection property sets which projection a map is rendered in.
 * Mercator and Globe projections are supported.
 *
 * Globe projection is a custom map projection mode for rendering the map wrapped around a full 3D globe.
 * Conceptually it is the undistorted and unskewed “ground truth” view of the map
 * that preserves true proportions between different areas of the map.
 *
 * Some layers are not supported when map is in globe projection:
 *  - custom
 *  - location indicator
 *
 * Using [ProjectionName.GLOBE] requires OpenGL [GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS] be more than zero.
 * If [GLES20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS] is zero (which may happen for low-end devices and old Android versions) -
 * [ProjectionName.GLOBE] will fallback to [ProjectionName.MERCATOR] with the log warning.
 *
 * @see [The online documentation](https://docs.mapbox.com/mapbox-gl-js/style-spec/projection/)
 *
 * @param name from [ProjectionName] enum
 */
@UiThread
class Projection(val name: ProjectionName) : StyleContract.StyleProjectionExtension {
  internal var delegate: StyleInterface? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  init {
    setProperty(PropertyValue("name", name))
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
}

/**
 * DSL function for creating [Projection] instance.
 */
fun projection(name: ProjectionName): Projection {
  return Projection(name)
}

// End of generated file.