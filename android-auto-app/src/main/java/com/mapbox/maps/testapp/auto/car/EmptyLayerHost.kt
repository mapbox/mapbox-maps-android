package com.mapbox.maps.testapp.auto.car

import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters

class EmptyLayerHost : CustomLayerHost {

    override fun initialize() {
    }

    override fun render(parameters: CustomLayerRenderParameters) {
    }

    override fun contextLost() {
    }

    override fun deinitialize() {
    }
}
