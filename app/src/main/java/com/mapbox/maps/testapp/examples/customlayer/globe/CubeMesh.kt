package com.mapbox.maps.testapp.examples.customlayer.globe

import com.mapbox.maps.Vec3
import kotlin.math.max

/**
 * Create a static cube mesh to be rendered with a transformation matrix.
 * It defines vertices in OpenGL local space [-1.0; 1.0]
 * */
class CubeMesh(
  tessellation: Int = 1,
) {

  val tessellation = max(tessellation, 1)
  val verticesPerCubeSide = tessellation * tessellation * 6
  val vertices = ArrayList<Vec3>(sides.size * this.tessellation * this.tessellation)

  init {
    val step = 1.0 / this.tessellation
    for (side in sides) {
      // Create vertices for the side
      repeat(this.tessellation) { y ->
        repeat(this.tessellation) { x ->
          val topLeft =
            side.topLeft + side.right * step * x.toDouble() + side.down * step * y.toDouble()
          val v00 = topLeft
          val v10 = topLeft + side.right * step
          val v11 = topLeft + side.right * step + side.down * step
          val v01 = topLeft + side.down * step

          vertices.add(v00)
          vertices.add(v10)
          vertices.add(v11)

          vertices.add(v11)
          vertices.add(v01)
          vertices.add(v00)
        }
      }
    }
  }

  companion object {

    private val sides = arrayOf(
      Side(Vec3(-0.5, 0.5, 0.5), Vec3(1.0, 0.0, 0.0), Vec3(0.0, -1.0, 0.0)),
      Side(Vec3(0.5, 0.5, 0.5), Vec3(0.0, 0.0, -1.0), Vec3(0.0, -1.0, 0.0)),
      Side(Vec3(0.5, 0.5, -0.5), Vec3(-1.0, 0.0, 0.0), Vec3(0.0, -1.0, 0.0)),
      Side(Vec3(-0.5, 0.5, -0.5), Vec3(0.0, 0.0, 1.0), Vec3(0.0, -1.0, 0.0)),
      Side(Vec3(-0.5, 0.5, -0.5), Vec3(1.0, 0.0, 0.0), Vec3(0.0, 0.0, 1.0)),
      Side(Vec3(-0.5, -0.5, 0.5), Vec3(1.0, 0.0, 0.0), Vec3(0.0, 0.0, -1.0)),
    )

    private data class Side(val topLeft: Vec3, val right: Vec3, val down: Vec3)
  }
}