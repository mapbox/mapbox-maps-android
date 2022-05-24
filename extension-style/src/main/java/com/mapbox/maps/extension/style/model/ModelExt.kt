@file:JvmName("ModelUtils")
package com.mapbox.maps.extension.style.model

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface

/**
 * DSL function for [ModelExtensionImpl].
 */
@MapboxExperimental
fun model(modelId: String, block: ModelExtensionImpl.Builder.() -> Unit): ModelExtensionImpl =
  ModelExtensionImpl.Builder(modelId).apply(block).build()

/**
 * Extension function to add a model provided by the Style Extension to the Style.
 *
 * @param model The model to be added
 */
@MapboxExperimental
fun StyleInterface.addModel(model: StyleContract.StyleModelExtension) {
  model.bindTo(this)
}