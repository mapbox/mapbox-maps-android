// This file is generated.

package com.mapbox.maps.extension.style.projection.generated

import android.opengl.GLES20
import android.util.Log
import androidx.annotation.UiThread
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxStyleException
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.layers.properties.PropertyValue
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.utils.unwrap

/**
 * A style's projection property sets which projection a map is rendered in.
 * Mercator, Globe and Albers projections are supported.
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
  internal var delegate: MapboxStyleManager? = null
  private val properties = HashMap<String, PropertyValue<*>>()

  init {
    setProperty(PropertyValue("name", name))
  }

  /**
   * The reference longitude and latitude of the projection. `center` takes the form of [lng, lat]. This property is only configurable for conic projections (Albers and Lambert Conformal Conic). All other projections are centered on [0, 0]. Minimum value: [-180,-90]. Maximum value: [180,90].
   */
  @MapboxExperimental
  val center: List<Double>?
    get() = getPropertyValue("center")

  /**
   * The reference longitude and latitude of the projection. `center` takes the form of [lng, lat]. This property is only configurable for conic projections (Albers and Lambert Conformal Conic). All other projections are centered on [0, 0]. Minimum value: [-180,-90]. Maximum value: [180,90].
   *
   * @param center as List<Double>
   */
  @MapboxExperimental
  fun center(center: List<Double>): Projection = apply {
    setProperty(PropertyValue("center", center))
  }

  /**
   * The standard parallels of the projection, denoting the desired latitude range with minimal distortion. `parallels` takes the form of [lat0, lat1]. This property is only configurable for conic projections (Albers and Lambert Conformal Conic). Minimum value: [-90,-90]. Maximum value: [90,90].
   */
  @MapboxExperimental
  val parallels: List<Double>?
    get() = getPropertyValue("parallels")

  /**
   * The standard parallels of the projection, denoting the desired latitude range with minimal distortion. `parallels` takes the form of [lat0, lat1]. This property is only configurable for conic projections (Albers and Lambert Conformal Conic). Minimum value: [-90,-90]. Maximum value: [90,90].
   *
   * @param parallels as List<Double>
   */
  @MapboxExperimental
  fun parallels(parallels: List<Double>): Projection = apply {
    setProperty(PropertyValue("parallels", parallels))
  }

  /**
   * Bind projection to the map controller.
   *
   * @param delegate The map controller
   */
  override fun bindTo(delegate: MapboxStyleManager) {
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
    return getPropertyValue(propertyName, T::class.java)
  }

  private fun <T> getPropertyValue(propertyName: String, clazz: Class<T>): T? {
    delegate?.let {
      val value = it.getStyleProjectionProperty(propertyName)
      return try {
        value.unwrap(clazz)
      } catch (e: RuntimeException) {
        Log.e(TAG, "Get projection property $propertyName failed: ${e.message}, value: $value")
        null
      }
    }
    throw MapboxStyleException("Get property $propertyName failed: projection is not added to style yet.")
  }

  /**
   * Static variables and methods.
   */
  private companion object {
    private const val TAG = "Mbgl-Projection"
  }
}

/**
 * DSL function for creating [Projection] instance.
 */
fun projection(name: ProjectionName): Projection {
  return Projection(name)
}

// End of generated file.