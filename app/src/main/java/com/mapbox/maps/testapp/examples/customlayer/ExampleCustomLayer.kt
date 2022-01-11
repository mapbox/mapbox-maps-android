package com.mapbox.maps.testapp.examples.customlayer

import android.opengl.GLES20
import com.mapbox.common.Logger
import com.mapbox.maps.CustomLayerHost
import com.mapbox.maps.CustomLayerRenderParameters
import com.mapbox.maps.testapp.BuildConfig
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class ExampleCustomLayer : CustomLayerHost {
  private var program = 0
  private var positionHandle = 0
  private var colorHandle = 0
  private var vertexShader = 0
  private var fragmentShader = 0

  private val vertexBuffer: FloatBuffer by lazy {
    // initialize vertex byte buffer for shape coordinates
    // (number of coordinate values * 4 bytes per float)
    ByteBuffer.allocateDirect(BACKGROUND_COORDINATES.size * BYTES_PER_FLOAT).run {
      // use the device hardware's native byte order
      order(ByteOrder.nativeOrder())

      // create a floating point buffer from the ByteBuffer
      asFloatBuffer().apply {
        // add the coordinates to the FloatBuffer
        put(BACKGROUND_COORDINATES)
        // set the buffer to read the first coordinate
        rewind()
      }
    }
  }

  override fun initialize() {
    val maxAttrib = IntArray(1)
    GLES20.glGetIntegerv(GLES20.GL_MAX_VERTEX_ATTRIBS, maxAttrib, 0)
    Logger.d(TAG, "Max vertex attributes: ${maxAttrib[0]}")

    // load and compile shaders
    vertexShader = loadShader(
      GLES20.GL_VERTEX_SHADER,
      VERTEX_SHADER_CODE
    ).also { checkCompileStatus(it) }

    fragmentShader = loadShader(
      GLES20.GL_FRAGMENT_SHADER,
      FRAGMENT_SHADER_CODE
    ).also { checkCompileStatus(it) }

    // create empty OpenGL ES Program
    program = GLES20.glCreateProgram().also {
      checkError("glCreateProgram")
      // add the vertex shader to program
      GLES20.glAttachShader(it, vertexShader).also { checkError("glAttachShader") }

      // add the fragment shader to program
      GLES20.glAttachShader(it, fragmentShader).also { checkError("glAttachShader") }

      // creates OpenGL ES program executables
      GLES20.glLinkProgram(it).also {
        checkError("glLinkProgram")
      }
    }

    // get handle to vertex shader's vPosition member
    positionHandle =
      GLES20.glGetAttribLocation(program, "a_pos").also { checkError("glGetAttribLocation") }

    // get handle to fragment shader's vColor member
    colorHandle = GLES20.glGetUniformLocation(program, "fill_color")
      .also { checkError("glGetUniformLocation") }
  }

  override fun render(parameters: CustomLayerRenderParameters) {
    if (program != 0) {
      // Add program to OpenGL ES environment
      GLES20.glUseProgram(program).also { checkError("glUseProgram") }

      GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positionHandle)

      // Enable a handle to the vertices
      GLES20.glEnableVertexAttribArray(positionHandle)
        .also { checkError("glEnableVertexAttribArray") }

      // Prepare the coordinate data
      GLES20.glVertexAttribPointer(
        positionHandle, COORDS_PER_VERTEX,
        GLES20.GL_FLOAT, false,
        VERTEX_STRIDE, vertexBuffer
      ).also { checkError("glVertexAttribPointer") }

      // Set color for drawing the background
      GLES20.glUniform4fv(colorHandle, 1, color, 0).also { checkError("glUniform4fv") }

      // Draw the background
      GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT)
        .also { checkError("glDrawArrays") }
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
      GLES20.glDetachShader(program, vertexShader)
      GLES20.glDetachShader(program, fragmentShader)
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
    private const val TAG = "ExampleCustomLayer"

    // number of coordinates per vertex in this array
    private const val COORDS_PER_VERTEX = 2
    private const val BYTES_PER_FLOAT = 4
    private const val VERTEX_STRIDE = COORDS_PER_VERTEX * BYTES_PER_FLOAT // 4 bytes per vertex
    private val BACKGROUND_COORDINATES = floatArrayOf( // in counterclockwise order:
      -1.0f, -1.0f,
      1.0f, -1.0f,
      -1.0f, 1.0f,
      1.0f, 1.0f
    )
    private val VERTEX_COUNT = BACKGROUND_COORDINATES.size / COORDS_PER_VERTEX

    // Set color with red, green, blue and alpha (opacity) values
    var color = floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f)

    private val VERTEX_SHADER_CODE = """
      attribute vec2 a_pos;
      void main() {
        gl_Position = vec4(a_pos, 0.0, 1.0);
      }
    """.trimIndent()

    private val FRAGMENT_SHADER_CODE = """
      precision mediump float;
      uniform vec4 fill_color;
      void main() {
        gl_FragColor = fill_color;
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