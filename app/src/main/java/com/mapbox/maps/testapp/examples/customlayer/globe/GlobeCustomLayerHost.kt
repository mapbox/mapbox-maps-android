package com.mapbox.maps.testapp.examples.customlayer.globe

import android.opengl.GLES30.*
import android.os.SystemClock
import com.mapbox.geojson.Point
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.Projection
import com.mapbox.maps.logW
import com.mapbox.maps.testapp.examples.customlayer.globe.VertexFloatBuffer.Companion.put
import kotlin.math.pow
import kotlin.math.sin

class GlobeCustomLayerHost(
  private val simulatedPointCount: Int = 128,
  private val cubeTesselation: Int = 8,
  private val requestRender: () -> Unit,
) : CustomLayerHost {

  private lateinit var globeProgram: Program
  private lateinit var simpleProgram: Program
  private var startTime = SystemClock.elapsedRealtime()

  private val cubeMesh = CubeMesh(cubeTesselation)
  private val newYorkCube = Cube(
    location = Point.fromLngLat(-74.0060, 40.7128),
    sizeMeters = 2_000_000f,
    altitudeMeters = 1_200_000f,
    colors = intArrayOf(0xfea3aa, 0xf8b88b, 0xfaf884, 0xbaed91, 0xb2cefe, 0xf2a2e8),
    mesh = cubeMesh,
  )

  // keeps references to vertex buffer objects
  private val vbo = object {
    val buffer = IntArray(5)
    val staticCube get() = buffer[0]
    val deformedCubeEcef get() = buffer[1]
    val cubeColor get() = buffer[2]
    val pointsEcef get() = buffer[3]
    val pointsMerc get() = buffer[4]
  }

  // keeps references to vertex array objects
  private val vao = object {
    val buffer = IntArray(3)
    val staticCube get() = buffer[0]
    val deformedCube get() = buffer[1]
    val points get() = buffer[2]
  }

  override fun initialize() {
    startTime = SystemClock.elapsedRealtime()
    simpleProgram = Program(
      vertexShaderSource = VERTEX_SIMPLE_SHADER_SOURCE,
      fragmentShaderSource = FRAGMENT_SHADER_CODE,
      attributes = listOf(SIMPLE_POSITION, COLOR),
      uniforms = listOf(SIMPLE_MATRIX),
    )
    globeProgram = Program(
      vertexShaderSource = VERTEX_SHADER_SOURCE,
      fragmentShaderSource = FRAGMENT_SHADER_CODE,
      attributes = listOf(POSITION_MERCATOR, POSITION_ECEF, COLOR),
      uniforms = listOf(MATRIX_MERCATOR, MATRIX_ECEF, ECEF_TRANSITION),
    )
    initializeVbo()
    initializeVao()
  }

  private fun initializeVbo() {
    glGenBuffers(vbo.buffer.size, vbo.buffer, 0)

    sequenceOf(
      vbo.staticCube to cubeMesh.vertices,
      vbo.deformedCubeEcef to newYorkCube.ecefVertices,
    ).forEach { (vboId, vertices) ->
      val buffer = VertexFloatBuffer.fromVec3(vertices)
      glBindBuffer(GL_ARRAY_BUFFER, vboId)
      glBufferData(GL_ARRAY_BUFFER, buffer.size, buffer.value, GL_STATIC_DRAW)
    }

    run {
      val buffer = VertexIntBuffer(newYorkCube.colorVertices)
      glBindBuffer(GL_ARRAY_BUFFER, vbo.cubeColor)
      glBufferData(GL_ARRAY_BUFFER, buffer.size, buffer.value, GL_STATIC_DRAW)
    }

    sequenceOf(
      vbo.pointsMerc,
      vbo.pointsEcef,
    ).forEach { vboId ->
      glBindBuffer(GL_ARRAY_BUFFER, vboId)
      val size = simulatedPointCount * 3 * Float.SIZE_BYTES
      glBufferData(GL_ARRAY_BUFFER, size, null, GL_STREAM_DRAW)
    }
  }

  private fun initializeVao() {
    glGenVertexArrays(vao.buffer.size, vao.buffer, 0)

    with(simpleProgram) {
      glBindVertexArray(vao.staticCube)
      attribute(SIMPLE_POSITION).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.staticCube)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
      }
      attribute(COLOR).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.cubeColor)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 4, GL_UNSIGNED_BYTE, true, 0, 0)
      }
    }

    with(globeProgram) {
      glBindVertexArray(vao.deformedCube)
      attribute(POSITION_MERCATOR).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.staticCube)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
      }
      attribute(POSITION_ECEF).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.deformedCubeEcef)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
      }
      attribute(COLOR).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.cubeColor)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 4, GL_UNSIGNED_BYTE, true, 0, 0)
      }
    }

    with(globeProgram) {
      glBindVertexArray(vao.points)
      attribute(POSITION_MERCATOR).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.pointsMerc)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
      }
      attribute(POSITION_ECEF).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.pointsEcef)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 3, GL_FLOAT, false, 0, 0)
      }
      attribute(COLOR).let { index ->
        glBindBuffer(GL_ARRAY_BUFFER, vbo.cubeColor)
        glEnableVertexAttribArray(index)
        glVertexAttribPointer(index, 4, GL_UNSIGNED_BYTE, true, 0, 0)
      }
    }

    glBindVertexArray(0)
  }

  override fun render(parameters: CustomLayerRenderParameters) = with(parameters) {
    glEnable(GL_DEPTH_TEST)
    glDepthFunc(GL_LESS)
    glDepthMask(true)
    glDepthRangef(depthRange.min, depthRange.max)

    glEnable(GL_CULL_FACE)
    glFrontFace(GL_CCW)
    glCullFace(GL_BACK)
    glDisable(GL_STENCIL_TEST)

    val projectionMatrix = Matrix4f(projectionMatrix)

    renderCubes(projectionMatrix)
    renderModelsInPixels(projectionMatrix)
    renderDeformedCube(projectionMatrix)
    renderPoints(projectionMatrix)

    glBindVertexArray(0)
  }

  // Example 1. Render 3D models defined in meters
  private fun CustomLayerRenderParameters.renderCubes(
    projectionMatrix: Matrix4f
  ) = with(simpleProgram) {
    glUseProgram(program)
    glBindVertexArray(vao.staticCube)

    val simpleMatrix = uniform(SIMPLE_MATRIX)

    for (loc in metricCubeLocations) {
      // Create a transformation (VP) matrix for placing a 100km^3 sized cube on 100km altitude.
      // The matrix transforms a unit cube from it's local coordinate space into mapbox's world coordinates
      // where the position is defined in `pixels` and altitude in meters. Cubes positioned closer to poles
      // appear larger due to distortion of mercator projection
      val modelMatrix = createModelMatrixMeters(loc, 100_000f, 100_000f)
      val pModelMatrix = modelMatrix.value.map(Float::toDouble)

      val transformedMatrix =
        projection.convertMercatorModelMatrix(pModelMatrix, false) ?: continue

      val finalMatrix = projectionMatrix * Matrix4f(transformedMatrix)

      glUniformMatrix4fv(simpleMatrix, 1, false, VertexFloatBuffer.fromMatrix(finalMatrix).value)
      glDrawArrays(GL_TRIANGLES, 0, 6 * cubeMesh.verticesPerCubeSide)
    }
  }

  // Example 2. Render 3D models defined in pixels
  private fun CustomLayerRenderParameters.renderModelsInPixels(
    projectionMatrix: Matrix4f
  ) = with(simpleProgram) {
    glUseProgram(program)
    glBindVertexArray(vao.staticCube)

    for (loc in pixelCubeLocations) {
      // Render another set of cubes whose size is defined in pixels.
      // Note that `ignoreDistortion` passed to `prepareModelMatrix` is set to true
      // as we want to keep the size identical at each latitude location
      val modelMatrix = createModelMatrixPixels(loc, 0f, 128f)
      val pModelMatrix = modelMatrix.value.map { it.toDouble() }

      val transformedMatrix =
        projection.convertMercatorModelMatrix(pModelMatrix, true) ?: continue

      val wvp = projectionMatrix * Matrix4f(transformedMatrix)

      val wvpBuffer = VertexFloatBuffer.fromMatrix(wvp).value
      glUniformMatrix4fv(uniform(SIMPLE_MATRIX), 1, false, wvpBuffer)
      glDrawArrays(GL_TRIANGLES, 0, 6 * cubeMesh.verticesPerCubeSide)
    }
  }

  // Example 3. Render a deformed cube over New York
  private fun CustomLayerRenderParameters.renderDeformedCube(
    projectionMatrix: Matrix4f
  ) = with(globeProgram) {
    glUseProgram(program)
    glBindVertexArray(vao.deformedCube)

    // Model matrix transforms ecef coordinates into world pixel coordinates.
    val globeWvp = projectionMatrix * Matrix4f(projection.modelMatrix)

    val mercModelMatrix = createModelMatrixMeters(
      newYorkCube.location,
      newYorkCube.altitudeMeters,
      newYorkCube.sizeMeters,
    )

    val mercWvp = projectionMatrix * Matrix4f(projection.transitionMatrix) * mercModelMatrix

    // Interpolate between ecef and mercator coordinates in the vertex shader
    // in order to support transitions between the two projections
    val transitionPhase = projection.transitionPhase

    glUniformMatrix4fv(uniform(MATRIX_MERCATOR), 1, false, mercWvp.value, 0)
    glUniformMatrix4fv(uniform(MATRIX_ECEF), 1, false, globeWvp.value, 0)
    glUniform1f(uniform(ECEF_TRANSITION), transitionPhase)

    glDrawArrays(GL_TRIANGLES, 0, 6 * cubeMesh.verticesPerCubeSide)
  }

  // Example 4. Render a bunch of moving points
  private fun CustomLayerRenderParameters.renderPoints(
    projectionMatrix: Matrix4f,
  ) = with(globeProgram) {
    glUseProgram(program)
    glBindVertexArray(vao.points)

    val elapsed = (SystemClock.elapsedRealtime() - startTime).toDouble() / 1000.0

    // Simulate points going up and down. Compute both ecef and mercator positions
    // and use the transition matrix for interpolating between them.

    val ecefPoints = VertexFloatBuffer(simulatedPointCount * 3)
    val mercatorPoints = VertexFloatBuffer(simulatedPointCount * 3)

    val start = CAPE_TOWN
    val end = TOKYO

    val worldSize = Projection.worldSize(2.0.pow(zoom))

    repeat(simulatedPointCount) { index ->
      val phase = index.toDouble() / (simulatedPointCount - 1)
      val wavePhase = M2_PI * phase * 8.0 + elapsed * M2_PI

      // Note: produces a linear line in mercator projection but not on the globe

      val lat = interpolate(start.latitude(), end.latitude(), phase)
      val lng = interpolate(start.longitude(), end.longitude(), phase)
      val altitude = sin(wavePhase) * 300_000.0 + 400_000.0

      val location = Point.fromLngLat(lng, lat)
      val ecef = location.toEcef(altitude)
      val mercator = Projection.latLngToMercatorXY(location)

      ecefPoints.put(ecef)
      mercatorPoints.put(mercator.x * worldSize, mercator.y * worldSize, altitude)
    }

    ecefPoints.rewind()
    mercatorPoints.rewind()

    glBindBuffer(GL_ARRAY_BUFFER, vbo.pointsEcef)
    glBufferSubData(GL_ARRAY_BUFFER, 0, ecefPoints.size, ecefPoints.value)

    glBindBuffer(GL_ARRAY_BUFFER, vbo.pointsMerc)
    glBufferSubData(GL_ARRAY_BUFFER, 0, mercatorPoints.size, mercatorPoints.value)

    // Model matrix transforms ecef coordinates into world pixel coordinates.
    val globeModelMatrix = Matrix4f(projection.modelMatrix)
    val globeWvp = projectionMatrix * globeModelMatrix

    val mercWvp = projectionMatrix * Matrix4f(projection.transitionMatrix)

    // Interpolate between ecef and mercator coordinates in the vertex shader
    // in order to support transitions between the two projections
    val transitionPhase = projection.transitionPhase

    glUniformMatrix4fv(uniform(MATRIX_MERCATOR), 1, false, mercWvp.value, 0)
    glUniformMatrix4fv(uniform(MATRIX_ECEF), 1, false, globeWvp.value, 0)
    glUniform1f(uniform(ECEF_TRANSITION), transitionPhase)

    glDrawArrays(GL_POINTS, 0, simulatedPointCount)
    requestRender()
  }

  fun CustomLayerRenderParameters.createModelMatrixPixels(
    location: Point,
    altitude: Float,
    size: Float
  ): Matrix4f {
    val translation = createTranslationMatrix(location, altitude)

    // set height to 1 pixel
    val pixelsToMeters =
      Projection.getMetersPerPixelAtLatitude(location.latitude(), zoom)
    val scale = createMetricScaleMatrix(size, size, pixelsToMeters, location.latitude())

    return translation * scale
  }

  fun CustomLayerRenderParameters.createModelMatrixMeters(
    location: Point,
    altitude: Float,
    sizeMeters: Float,
  ): Matrix4f {
    val translation = createTranslationMatrix(location, altitude)

    val metersToPixels =
      1.0 / Projection.getMetersPerPixelAtLatitude(location.latitude(), zoom)
    val xSize = sizeMeters * metersToPixels
    val ySize = sizeMeters * metersToPixels

    val scale = createMetricScaleMatrix(xSize, ySize, sizeMeters, location.latitude())

    return translation * scale
  }

  /**
   * Convert lat&lng to world coordinates where x & y are in pixels and z in meters
   * */
  fun CustomLayerRenderParameters.createTranslationMatrix(
    location: Point,
    altitude: Float,
  ): Matrix4f {
    // in range [0,1]
    val mercatorPos = Projection.latLngToMercatorXY(location)
    // tileSize (512) * 2^zoom
    val worldPos = mercatorPos * Projection.worldSize(2.0.pow(zoom))
    val altitudeScalar = heightScalarForLatitude(location.latitude(), latitude)

    return Matrix4f.translation(worldPos.x, worldPos.y, altitude * altitudeScalar)
  }

  /**
   * Creates a matrix that scales from meters into world units (i.e. pixel units)
   * */
  fun CustomLayerRenderParameters.createMetricScaleMatrix(
    x: Number,
    y: Number,
    z: Number,
    latitude: Double,
  ) = Matrix4f.scale(x, y, z.toDouble() * heightScalarForLatitude(latitude, latitude))

  /**
   * Height of renderable models (i.e. the z-axis) is defined meters and the conversion into pixels
   * is baked into the projection matrix. Because the projection matrix uses `metersToPixels` computed at
   * the map center whereas the value is actually a function of latitude, we might need to apply compensation
   * to transformation matrices of models located at different latitudue coordinates.
   * */
  fun heightScalarForLatitude(latitude: Double, centerLatitude: Double) =
    Projection.getLatitudeScale(centerLatitude) / Projection.getLatitudeScale(latitude)

  override fun contextLost() = logW(TAG, "contextLost")

  override fun deinitialize() {
    globeProgram.deinitialize()
    simpleProgram.deinitialize()
    glDeleteBuffers(vbo.buffer.size, vbo.buffer, 0)
    glDeleteVertexArrays(vao.buffer.size, vao.buffer, 0)
  }

  companion object {
    private const val TAG = "GlobeCustomLayer"

    private val CAPE_TOWN = Point.fromLngLat(19.0114, -32.3117)
    private val TOKYO = Point.fromLngLat(139.7543, 35.6821)

    private val metricCubeLocations = listOf(
      Point.fromLngLat(15.4912, 78.2357),
      Point.fromLngLat(25.7294, 66.5039),
      Point.fromLngLat(13.4050, 52.5200),
      Point.fromLngLat(12.4822, 41.8967),
    )

    val pixelCubeLocations = listOf(
      Point.fromLngLat(-9.1393, 38.7223),
      Point.fromLngLat(0.1276, 51.5072),
      Point.fromLngLat(-21.9408, 64.1470),
    )

    private const val SIMPLE_MATRIX = "u_matrix"

    private const val SIMPLE_POSITION = "a_pos"
    private const val COLOR = "a_color"

    private val VERTEX_SIMPLE_SHADER_SOURCE = """
      uniform mat4 $SIMPLE_MATRIX;
      attribute vec3 $SIMPLE_POSITION;
      attribute vec3 $COLOR;
      varying vec4 v_color;
      void main() {
          gl_Position = $SIMPLE_MATRIX * vec4($SIMPLE_POSITION, 1.0);
          v_color = vec4($COLOR, 0.85);
      }
    """.trimIndent()

    private const val POSITION_MERCATOR = "a_pos_merc"
    private const val POSITION_ECEF = "a_pos_ecef"

    private const val MATRIX_MERCATOR = "u_matrix_merc"
    private const val MATRIX_ECEF = "u_matrix_ecef"
    private const val ECEF_TRANSITION = "u_transition"

    private val VERTEX_SHADER_SOURCE = """
      uniform mat4 $MATRIX_MERCATOR;
      uniform mat4 $MATRIX_ECEF;
      uniform float $ECEF_TRANSITION;
      
      attribute vec3 $POSITION_MERCATOR;
      attribute vec3 $POSITION_ECEF;
      attribute vec3 $COLOR;
      
      varying vec4 v_color;
      
      void main() {
          vec4 pos_merc = $MATRIX_MERCATOR * vec4($POSITION_MERCATOR, 1.0);
          vec4 pos_ecef = $MATRIX_ECEF * vec4($POSITION_ECEF, 1.0);
          gl_Position = mix(pos_merc, pos_ecef, clamp($ECEF_TRANSITION, 0.0, 1.0));
          gl_PointSize = 32.0;
          v_color = vec4($COLOR, 0.85);
      }
    """.trimIndent()

    private val FRAGMENT_SHADER_CODE = """
      precision mediump float;
      varying vec4 v_color;
      void main() {
        gl_FragColor = v_color;
      }
    """.trimIndent()
  }
}