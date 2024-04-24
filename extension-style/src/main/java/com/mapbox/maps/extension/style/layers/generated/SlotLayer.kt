// This file is generated.

package com.mapbox.maps.extension.style.layers.generated

import androidx.annotation.UiThread
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.Layer
import com.mapbox.maps.extension.style.layers.properties.*
import com.mapbox.maps.extension.style.layers.properties.generated.*
import com.mapbox.maps.extension.style.types.*

/**
 * Marks the position of a slot.
 *
 * @see [The online documentation](https://www.mapbox.com/mapbox-gl-style-spec/#layers-slot)
 *
 * @param layerId the ID of the layer
 */
@UiThread
class SlotLayer(override val layerId: String) : SlotLayerDsl, Layer() {

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot
   */
  override fun slot(slot: String): SlotLayer = apply {
    val param = PropertyValue("slot", slot)
    setProperty(param)
  }

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   */
  override val slot: String?
    /**
     * Get the slot property
     *
     * @return slot
     */
    get() {
      return getPropertyValue("slot")
    }

  /**
   * No-op.
   */
  override val visibility: Visibility? = null

  /**
   * No-op.
   */
  override val visibilityAsExpression: Expression? = null

  /**
   * No-op.
   *
   * @param visibility no-op.
   */
  override fun visibility(visibility: Visibility): SlotLayer = this

  /**
   * No-op.
   *
   * @param visibility no-op.
   */
  override fun visibility(visibility: Expression): SlotLayer = this

  /**
   * No-op, returns NULL.
   */
  override val minZoom: Double? = null

  /**
   * No-op.
   *
   * @param minZoom no-op.
   */
  override fun minZoom(minZoom: Double): SlotLayer = this

  /**
   * No-op, returns NULL.
   */
  override val maxZoom: Double? = null

  /**
   * No-op.
   *
   * @param maxZoom no-op.
   */
  override fun maxZoom(maxZoom: Double): SlotLayer = this

  /**
   * Get the type of this layer
   *
   * @return Type of the layer as [String]
   */
  override fun getType(): String {
    return "slot"
  }
}

/**
 * This Interface contains all the functions that will be exposed to Koltin DSL.
 *
 * Separated the DSL receiver class to this interface to avoid IDE code suggestion for
 * property getters.
 */
@LayersDsl
interface SlotLayerDsl {

  /**
   * The slot this layer is assigned to. If specified, and a slot with that name exists,
   * it will be placed at that position in the layer order.
   *
   * @param slot value of slot
   */
  fun slot(slot: String): SlotLayer
}

/**
 * DSL function for creating a [SlotLayer].
 */
fun slotLayer(layerId: String, block: SlotLayerDsl.() -> Unit): SlotLayer = SlotLayer(layerId).apply(block)

// End of generated file.