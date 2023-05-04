package com.mapbox.maps.extension.style.model

import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.utils.check

/**
 * Concrete implementation of ModelPlugin, the plugin is used to add a 3D model to be used in the style.
 *
 * This API can also be used for updating a model. If the model id was already added, it gets replaced by the new model.
 */
@MapboxExperimental
class ModelExtensionImpl(private val builder: Builder) : StyleContract.StyleModelExtension {
  /**
   * Add the model to the style.
   */
  override fun bindTo(delegate: StyleInterface) {
    delegate.addStyleModel(
      builder.modelId,
      builder.uri
    ).check()
  }

  /**
   * Builder for the [ModelExtensionImpl].
   */
  @MapboxExperimental
  class Builder(
    /**
     * ID of the model.
     */
    val modelId: String
  ) {
    /**
     * Uri for the model.
     */
    internal var uri: String = ""

    /**
     * Uri for the model.
     */
    fun uri(uri: String) = apply {
      this.uri = uri
    }

    /**
     * Build the ModelExtensionImpl.
     *
     * @return ModelExtensionImpl
     */
    fun build(): ModelExtensionImpl {
      if (this.uri.isEmpty()) {
        throw IllegalStateException("3D Model extension requires model uri input.")
      }
      return ModelExtensionImpl(this)
    }
  }
}