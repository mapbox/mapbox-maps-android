package com.mapbox.maps.testapp.examples.customlayer

import android.opengl.GLES20
import com.mapbox.common.Logger
import com.mapbox.geojson.Point
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.Projection
import com.mapbox.maps.testapp.BuildConfig
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.pow

class TriangleCustomLayer : CustomLayerHost {
  private var program = 0
  private var vertexShader = 0
  private var fragmentShader = 0
  private var positionHandle = 0
  private var colorHandle = 0
  private var projectionMatrixHandle = 0

  // initialize vertex byte buffer for shape coordinates
  // (number of coordinate values * 4 bytes per float)
  private val vertexBuffer =
    ByteBuffer.allocateDirect(VERTEX_COUNT * COORDS_PER_VERTEX * BYTES_PER_FLOAT).run {
      // use the device hardware's native byte order
      order(ByteOrder.nativeOrder())
      // create a floating point buffer from the ByteBuffer
      asFloatBuffer()
    }

  // initialize vertex byte buffer for color
  // (number of color values * 4 bytes per float)
  private val colorBuffer =
    ByteBuffer.allocateDirect(COLOR_COUNT * COLOR_SIZE * BYTES_PER_FLOAT).run {
      // use the device hardware's native byte order
      order(ByteOrder.nativeOrder())
      // create a floating point buffer from the ByteBuffer
      asFloatBuffer()
    }

  override fun initialize() {
    vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE).also {
      checkCompileStatus(it)
    }
    fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE).also {
      checkCompileStatus(it)
    }
    program = GLES20.glCreateProgram().also {
      // add the vertex shader to program
      GLES20.glAttachShader(it, vertexShader)
      // add the fragment shader to program
      GLES20.glAttachShader(it, fragmentShader)
      // creates OpenGL ES program executables
      GLES20.glLinkProgram(it)
      checkError("glCreateProgram")
    }

    // get handle to vertex shader's a_position member
    positionHandle = GLES20.glGetAttribLocation(program, "a_position").also { checkError("glGetAttribLocation") }

    // get handle to vertex shader's a_color member
    colorHandle = GLES20.glGetAttribLocation(program, "a_color").also { checkError("glGetAttribLocation") }
  }

  override fun render(parameters: CustomLayerRenderParameters) {
    if (program != 0) {
      // Add program to OpenGL ES environment
      GLES20.glUseProgram(program).also { checkError("glUseProgram") }

      GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positionHandle).also { checkError("glBindBuffer") }

      // Enable a handle to the triangle vertices
      GLES20.glEnableVertexAttribArray(positionHandle).also { checkError("glEnableVertexAttribArray") }

      // Calculate the point in Mercator projection
      val zoomScale = 2.0.pow(parameters.zoom)
      val helsinki = Projection.project(HELSINKI, zoomScale)
      val berlin = Projection.project(BERLIN, zoomScale)
      val kyiv = Projection.project(KYIV, zoomScale)
      val triangleCoordinates = floatArrayOf(
        helsinki.x.toFloat(), helsinki.y.toFloat(),
        berlin.x.toFloat(), berlin.y.toFloat(),
        kyiv.x.toFloat(), kyiv.y.toFloat()
      )
      // Fill the coordinates to the vertex buffer
      vertexBuffer.apply {
        clear()
        // add the coordinates to the FloatBuffer
        put(triangleCoordinates)
        // set the buffer to read the first coordinate
        rewind()
      }

      // Prepare the triangle coordinate data
      GLES20.glVertexAttribPointer(
        positionHandle, COORDS_PER_VERTEX,
        GLES20.GL_FLOAT, false,
        VERTEX_STRIDE, vertexBuffer
      ).also { checkError("glVertexAttribPointer") }

      // Enable a handle to the color
      GLES20.glEnableVertexAttribArray(colorHandle).also { checkError("glEnableVertexAttribArray") }

      // Fill the color to the color buffer
      colorBuffer.apply {
        clear()
        // add the color to the FloatBuffer
        put(color)
        // rewind the buffer to the beginning
        rewind()
      }

      GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positionHandle).also { checkError("glBindBuffer") }

      // Set color for drawing the triangle
      GLES20.glVertexAttribPointer(
        colorHandle, COLOR_SIZE,
        GLES20.GL_FLOAT, false,
        COLOR_STRIDE, colorBuffer
      ).also { checkError("glVertexAttribPointer") }

      // get handle to shape's transformation matrix
      projectionMatrixHandle = GLES20.glGetUniformLocation(program, "u_matrix").also {
        checkError("glGetUniformLocation")
      }

      // Apply the projection transformation
      GLES20.glUniformMatrix4fv(
        projectionMatrixHandle,
        1,
        false,
        parameters.projectionMatrix.map { it.toFloat() }.toFloatArray(),
        0
      ).also {
        checkError("glUniformMatrix4fv")
      }

      // Draw the triangle
      GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT).also { checkError("glDrawArrays") }

      // Disable vertex array
      GLES20.glDisableVertexAttribArray(positionHandle).also { checkError("glDisableVertexAttribArray") }
      GLES20.glDisableVertexAttribArray(colorHandle).also { checkError("glDisableVertexAttribArray") }
    }
  }

  override fun contextLost() {
    Logger.w(TAG, "contextLost")
    program = 0
  }

  override fun deinitialize() {
    if (program != 0) {
      // Disable vertex array
      GLES20.glDisableVertexAttribArray(positionHandle)
      GLES20.glDisableVertexAttribArray(colorHandle)
      GLES20.glDeleteShader(vertexShader)
      GLES20.glDeleteShader(fragmentShader)
      GLES20.glDeleteProgram(program)
      program = 0
    }
  }

  private fun checkCompileStatus(shader: Int) {
    if (BuildConfig.DEBUG) {
      val isCompiled = IntArray(1)
      GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, isCompiled, 0)
      if (isCompiled[0] == GLES20.GL_FALSE) {
        val infoLog = GLES20.glGetShaderInfoLog(program)
        throw RuntimeException("checkCompileStatus error: $infoLog")
      }
    }
  }

  companion object {
    private const val TAG = "TriangleCustomLayer"
    // number of coordinates per vertex in this array
    private const val COORDS_PER_VERTEX = 2
    private const val VERTEX_COUNT = 3
    private const val BYTES_PER_FLOAT = 4
    private const val VERTEX_STRIDE = COORDS_PER_VERTEX * BYTES_PER_FLOAT

    private const val COLOR_SIZE = 4
    private const val COLOR_COUNT = 3
    private const val COLOR_STRIDE = COLOR_SIZE * BYTES_PER_FLOAT

    private val HELSINKI = Point.fromLngLat(25.004, 60.239)
    private val BERLIN = Point.fromLngLat(13.403, 52.562)
    private val KYIV = Point.fromLngLat(30.498, 50.541)

    var color = floatArrayOf(
      1.0f, 0.0f, 0.0f, 0.5f,
      0.0f, 1.0f, 0.0f, 0.5f,
      0.0f, 0.0f, 1.0f, 0.5f
    )

    private val VERTEX_SHADER_CODE = """
      uniform mat4 u_matrix;
      attribute vec2 a_position;
      attribute vec4 a_color;
      varying vec4 v_color;
      void main() {
        gl_Position = u_matrix * vec4(a_position, 0.0, 1.0);
        v_color = a_color;
      }
    """.trimIndent()
    private val FRAGMENT_SHADER_CODE = """
      precision mediump float;
      varying vec4 v_color;
      void main() {
        gl_FragColor = v_color;
      }
    """.trimIndent()

    private fun loadShader(type: Int, shaderCode: String): Int {
      // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
      // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
      return GLES20.glCreateShader(type).also { shader ->

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
      }
    }

    private fun checkError(cmd: String? = null) {
      if (BuildConfig.DEBUG) {
        when (val error = GLES20.glGetError()) {
          GLES20.GL_NO_ERROR -> {
            Logger.d(TAG, "$cmd -> no error")
          }
          GLES20.GL_INVALID_ENUM -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_ENUM")
          GLES20.GL_INVALID_VALUE -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_VALUE")
          GLES20.GL_INVALID_OPERATION -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_OPERATION")
          GLES20.GL_INVALID_FRAMEBUFFER_OPERATION -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_FRAMEBUFFER_OPERATION")
          GLES20.GL_OUT_OF_MEMORY -> throw RuntimeException("$cmd -> error in gl: GL_OUT_OF_MEMORY")
          else -> throw RuntimeException("$cmd -> error in gl: $error")
        }
      }
    }
  }
}