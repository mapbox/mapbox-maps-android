package com.mapbox.maps.testapp.auto.car

import android.annotation.SuppressLint
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.R
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.LocationPuck3D
import com.mapbox.maps.plugin.ModelScaleMode

/**
 * Provides car location puck definitions.
 */
internal object CarLocationPuck {
  /**
   * 3D location puck with the real world size.
   */
  @OptIn(MapboxExperimental::class)
  val duckLocationPuckRealWorld = LocationPuck3D(
    modelUri = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf",
    modelScale = listOf(20f, 20f, 20f),
    modelScaleMode = ModelScaleMode.MAP,
    modelRotation = listOf(0f, 0f, -90f)
  )

  /**
   * 3D location puck with a constant size across zoom levels.
   */
  @OptIn(MapboxExperimental::class)
  val duckLocationPuckConstantSize = LocationPuck3D(
    modelUri = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Duck/glTF-Embedded/Duck.gltf",
    modelScale = listOf(20f, 20f, 20f),
    modelScaleMode = ModelScaleMode.VIEWPORT,
    modelRotation = listOf(0f, 0f, -90f)
  )

  /**
   * Classic 2D location puck with blue dot and arrow.
   */
  @SuppressLint("UseCompatLoadingForDrawables")
  val classicLocationPuck2D = LocationPuck2D(
    topImage = ImageHolder.Companion.from(R.drawable.mapbox_user_icon),
    bearingImage = ImageHolder.Companion.from(R.drawable.mapbox_user_bearing_icon),
    shadowImage = ImageHolder.Companion.from(R.drawable.mapbox_user_stroke_icon)
  )
}