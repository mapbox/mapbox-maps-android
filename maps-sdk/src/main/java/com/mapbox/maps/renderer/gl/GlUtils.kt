package com.mapbox.maps.renderer.gl

import android.opengl.GLES20
import android.opengl.Matrix
import com.mapbox.maps.BuildConfig
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

internal object GlUtils {

  fun FloatArray.put(vararg values: Float) {
    values.forEachIndexed { index, value ->
      this[index] = value
    }
  }

  fun FloatBuffer.put(vararg values: Float) {
    rewind()
    values.forEach { value ->
      this.put(value)
    }
    rewind()
  }

  fun FloatArray.toFloatBuffer(): FloatBuffer = ByteBuffer.allocateDirect(size * 4)
    .order(ByteOrder.nativeOrder())
    .asFloatBuffer().also {
      it.put(this@toFloatBuffer)
      it.rewind()
    }

  /**
   * @param type GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER
   */
  fun loadShader(type: Int, shaderCode: String) =
    GLES20.glCreateShader(type).also { shader ->
      GLES20.glShaderSource(shader, shaderCode)
      GLES20.glCompileShader(shader)
    }

  // We inline this function so in release flavour it is completely removed and no call is done
  @Suppress("NOTHING_TO_INLINE")
  inline fun checkError(cmd: String? = null) {
    if (BuildConfig.DEBUG) {
      throwIfError(cmd)
    }
  }

  private fun throwIfError(cmd: String?) {
    when (val error = GLES20.glGetError()) {
      GLES20.GL_NO_ERROR -> {}
      else -> throw java.lang.RuntimeException(
        "$cmd - error in GL : ${
          when (error) {
            GLES20.GL_INVALID_ENUM -> "GL_INVALID_ENUM"
            GLES20.GL_INVALID_VALUE -> "GL_INVALID_VALUE"
            GLES20.GL_INVALID_OPERATION -> "GL_INVALID_OPERATION"
            GLES20.GL_INVALID_FRAMEBUFFER_OPERATION -> "GL_INVALID_FRAMEBUFFER_OPERATION"
            GLES20.GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY"
            else -> error
          }
        }"
      )
    }
  }

  fun checkCompileStatus(shader: Int) {
    if (BuildConfig.DEBUG) {
      val isCompiled = IntArray(1)
      GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, isCompiled, 0)
      if (isCompiled[0] == GLES20.GL_FALSE) {
        val infoLog = GLES20.glGetShaderInfoLog(shader)
        throw RuntimeException("checkCompileStatus error: $infoLog")
      }
    }
  }

  fun getIdentityMatrix(): FloatArray = FloatArray(16).also { Matrix.setIdentityM(it, 0) }
}