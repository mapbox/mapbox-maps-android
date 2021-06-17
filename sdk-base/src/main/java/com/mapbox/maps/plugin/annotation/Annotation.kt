package com.mapbox.maps.plugin.annotation

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.mapbox.android.gestures.MoveDistancesObject
import com.mapbox.geojson.Geometry
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate

/**
 * Base class for annotations
 */
abstract class Annotation<T : Geometry>(
  /** The id for annotation */
  val id: Long,
  /** The jsonObject of annotation, saves all the property values of this annotation */
  protected val jsonObject: JsonObject,
  /** The geometry that determines the location/shape of this annotation */
  var geometry: T
) {

  /**
   * Get the type of this annotation
   */
  abstract fun getType(): AnnotationType

  /**
   * Set the used data-driven properties
   */
  abstract fun setUsedDataDrivenProperties()

  /**
   * Get the offset geometry for the touch point
   */
  abstract fun getOffsetGeometry(
    mapCameraManagerDelegate: MapCameraManagerDelegate,
    moveDistancesObject: MoveDistancesObject,
    touchAreaShiftX: Int,
    touchAreaShiftY: Int
  ): T?

  /**
   * Set the arbitrary json data of the annotation.
   *
   * @param jsonElement the arbitrary json object data
   */
  open fun setData(jsonElement: JsonElement?) {
    jsonObject.add(ID_DATA, jsonElement)
  }

  /**
   * Get the arbitrary json object data of the annotation.
   *
   * @return the arbitrary json object data if set, else null
   */
  open fun getData(): JsonElement? {
    return jsonObject[ID_DATA]
  }

  /**
   * Get a copy of jsonObject from this annotation
   */
  fun getJsonObjectCopy(): JsonObject {
    return jsonObject.deepCopy()
  }

  /**
   * Whether this annotation is draggable
   */
  var isDraggable: Boolean = false

  /**
   * Whether or not this annotation has been selected, either via a tap gesture or programmatically.
   */
  var isSelected: Boolean = false

  /**
   * Static variables and methods.
   */
  companion object {
    /** Key for data */
    const val ID_DATA = "custom_data"

    /**
     * Maximum latitude value in Mercator projection.
     */
    const val MAX_MERCATOR_LATITUDE = 85.05112877980659

    /**
     * Minimum latitude value in Mercator projection.
     */
    const val MIN_MERCATOR_LATITUDE = -85.05112877980659
  }
}