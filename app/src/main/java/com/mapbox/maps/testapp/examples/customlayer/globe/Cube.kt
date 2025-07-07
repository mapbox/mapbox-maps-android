package com.mapbox.maps.testapp.examples.customlayer.globe

import com.mapbox.geojson.Point
import com.mapbox.maps.Projection
import com.mapbox.maps.Vec3

/**
 * Creates data and vertices for a cube in ECEF coordinates
 * */
class Cube(
  val location: Point,
  val sizeMeters: Float,
  val altitudeMeters: Float,
  colors: IntArray,
  mesh: CubeMesh,
) {

  private val _ecefVertices = ArrayList<Vec3>(mesh.vertices.size)
  val ecefVertices: List<Vec3> get() = _ecefVertices

  // NxN grid, of quads.
  private val _colorVertices = ArrayList<Int>(mesh.verticesPerCubeSide * colors.size)
  val colorVertices: List<Int> get() = _colorVertices

  init {
    // normalized to [0, 1]
    val positionMercator = Projection.latLngToMercatorXY(location)
    val metersToMercator = metersToMercator(location.latitude())
    for (vertex in mesh.vertices) {
      val x = positionMercator.x + vertex.x * sizeMeters * metersToMercator
      val y = positionMercator.y + vertex.y * sizeMeters * metersToMercator
      val altitude = vertex.z * sizeMeters + altitudeMeters

      val vertexEcef = toEcef(
        latitude = latFromMercatorY(y),
        longitude = lngFromMercatorX(x),
        altitude,
      )

      _ecefVertices.add(vertexEcef)
    }

    // Create vertex buffer for cube side colors
    for (color in colors) {
      repeat(mesh.verticesPerCubeSide) {
        _colorVertices.add(color)
      }
    }
  }
}