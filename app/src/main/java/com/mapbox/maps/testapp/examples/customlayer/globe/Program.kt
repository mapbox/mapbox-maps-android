package com.mapbox.maps.testapp.examples.customlayer.globe

import android.opengl.GLES30.*
import com.mapbox.maps.logD
import com.mapbox.maps.testapp.BuildConfig
import java.nio.IntBuffer

/**
 * Helper class to define OpenGL program, shaders, their attributes and uniforms
 * */
class Program(
  vertexShaderSource: String,
  fragmentShaderSource: String,
  attributes: List<String>,
  uniforms: List<String>,
) {

  val vertexShader = loadShader(GL_VERTEX_SHADER, vertexShaderSource)
  val fragmentShader = loadShader(GL_FRAGMENT_SHADER, fragmentShaderSource)
  val program = glCreateProgram().also { program ->
    glAttachShader(program, vertexShader)
    glAttachShader(program, fragmentShader)
    glLinkProgram(program)
    checkError("glCreateProgram")
  }

  private val _attributes = attributes.associate { key ->
    key to glGetAttribLocation(program, key).also { checkError("glGetAttribLocation") }
  }

  private val _uniforms = uniforms.associate { key ->
    key to glGetUniformLocation(program, key).also { checkError("glGetUniformLocation") }
  }

  fun attribute(name: String) = _attributes[name]
    ?: throw IllegalArgumentException("no attribute $name")

  fun uniform(name: String) = _uniforms[name]
    ?: throw IllegalArgumentException("no uniform $name")

  fun deinitialize() {
    glDetachShader(program, vertexShader)
    glDetachShader(program, fragmentShader)
    glDeleteShader(vertexShader)
    glDeleteShader(fragmentShader)
    glDeleteProgram(program)
  }

  /**
   * Creates and compiles a shader.
   * @param type is [GL_VERTEX_SHADER] or [GL_FRAGMENT_SHADER]
   * */
  private fun loadShader(type: Int, shaderCode: String): Int {
    val shader = glCreateShader(type)
    glShaderSource(shader, shaderCode)
    glCompileShader(shader)
    checkCompileStatus(shader)
    return shader
  }

  private fun checkCompileStatus(shader: Int) {
    if (!BuildConfig.DEBUG) return
    val isCompiled = IntBuffer.allocate(1)
    glGetShaderiv(shader, GL_COMPILE_STATUS, isCompiled)
    if (isCompiled[0] == GL_FALSE) {
      val infoLog = glGetShaderInfoLog(program)
      throw RuntimeException("checkCompileStatus error: $infoLog")
    }
  }

  private fun checkError(cmd: String? = null) {
    if (!BuildConfig.DEBUG) return
    when (val error = glGetError()) {
      GL_NO_ERROR -> logD("Program", "$cmd -> no error")
      GL_INVALID_ENUM -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_ENUM")
      GL_INVALID_VALUE -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_VALUE")
      GL_INVALID_OPERATION -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_OPERATION")
      GL_INVALID_FRAMEBUFFER_OPERATION -> throw RuntimeException("$cmd -> error in gl: GL_INVALID_FRAMEBUFFER_OPERATION")
      GL_OUT_OF_MEMORY -> throw RuntimeException("$cmd -> error in gl: GL_OUT_OF_MEMORY")
      else -> throw RuntimeException("$cmd -> error in gl: $error")
    }
  }
}