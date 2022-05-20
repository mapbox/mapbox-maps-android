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
 * Extension function to add an model provided by the Style Extension to the Style.
 *
 * @param image The image to be added
 */
@MapboxExperimental
fun StyleInterface.addModel(image: StyleContract.StyleModelExtension) {
  image.bindTo(this)
}