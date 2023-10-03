package com.mapbox.maps.renderer.gl

import android.opengl.GLES20
import androidx.annotation.RestrictTo
import com.mapbox.maps.renderer.gl.GlUtils.toFloatBuffer

@RestrictTo(RestrictTo.Scope.LIBRARY)
internal class TextureRenderer(
  private val depth: Float = 0f
) {

  private var program: Int = 0
  private var attributePosition: Int = 0
  private var attributeTextureCoord: Int = 0
  private var uniformTexture: Int = 0
  private var vbo: IntArray = IntArray(2)

  private fun prepare() {
    setupVbo(
      vertexArray = floatArrayOf(
        -1f, -1f, depth,
        1f, -1f, depth,
        -1f, 1f, depth,
        1f, 1f, depth,
      ),
      textureArray = floatArrayOf(
        0f, 0f,
        1f, 0f,
        0f, 1f,
        1f, 1f,
      )
    )

    val vertexShader = GlUtils.loadShader(
      GLES20.GL_VERTEX_SHADER,
      VERTEX_SHADER_CODE
    ).also(GlUtils::checkCompileStatus)

    val fragmentShader = GlUtils.loadShader(
      GLES20.GL_FRAGMENT_SHADER,
      FRAGMENT_SHADER_CODE
    ).also(GlUtils::checkCompileStatus)

    program = GLES20.glCreateProgram().also {
      GlUtils.checkError("glCreateProgram")
      GLES20.glAttachShader(it, vertexShader)
      GlUtils.checkError("glAttachShader")

      GLES20.glAttachShader(it, fragmentShader)
      GlUtils.checkError("glAttachShader")

      GLES20.glLinkProgram(it)
      GlUtils.checkError("glLinkProgram")
    }
    attributePosition = GLES20.glGetAttribLocation(program, "aPosition")
    attributeTextureCoord = GLES20.glGetAttribLocation(program, "aTexCoord")

    uniformTexture = GLES20.glGetUniformLocation(program, "uTexture")
  }

  fun render(textureID: Int) {
    if (program == 0) {
      prepare()
    }
    GLES20.glUseProgram(program)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0])
    GLES20.glVertexAttribPointer(
      attributePosition,
      COORDS_PER_VERTEX,
      GLES20.GL_FLOAT,
      false,
      VERTEX_STRIDE,
      0
    )
    GLES20.glEnableVertexAttribArray(attributePosition)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[1])
    GLES20.glVertexAttribPointer(
      attributeTextureCoord,
      COORDS_PER_TEX,
      GLES20.GL_FLOAT,
      false,
      TEX_STRIDE,
      0
    )
    GLES20.glEnableVertexAttribArray(attributeTextureCoord)

    GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID)
    GLES20.glUniform1i(uniformTexture, 0)

    GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, VERTEX_COUNT)

    GLES20.glDisableVertexAttribArray(attributePosition)
    GLES20.glDisableVertexAttribArray(attributeTextureCoord)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
    GLES20.glUseProgram(0)
  }

  fun release() {
    if (program != 0) {
      GLES20.glDeleteBuffers(vbo.size, vbo, 0)
      GLES20.glDeleteProgram(program)
      program = 0
    }
  }

  private fun setupVbo(vertexArray: FloatArray, textureArray: FloatArray) {
    GLES20.glGenBuffers(2, vbo, 0)
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[0])
    GLES20.glBufferData(
      GLES20.GL_ARRAY_BUFFER,
      vertexArray.size * BYTES_PER_FLOAT,
      vertexArray.toFloatBuffer(),
      GLES20.GL_STATIC_DRAW
    )
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo[1])
    GLES20.glBufferData(
      GLES20.GL_ARRAY_BUFFER,
      textureArray.size * BYTES_PER_FLOAT,
      textureArray.toFloatBuffer(),
      GLES20.GL_STATIC_DRAW
    )
    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0)
  }

  private companion object {
    const val COORDS_PER_VERTEX = 3
    const val COORDS_PER_TEX = 2
    const val BYTES_PER_FLOAT = 4
    const val VERTEX_STRIDE = COORDS_PER_VERTEX * BYTES_PER_FLOAT
    const val TEX_STRIDE = COORDS_PER_TEX * BYTES_PER_FLOAT
    const val VERTEX_COUNT = 4

    val VERTEX_SHADER_CODE = """
        precision highp float;
        attribute vec4 aPosition;
        attribute vec2 aTexCoord;
        varying vec2 vTexCoord;
        void main()
        {
            gl_Position = aPosition;
            vTexCoord = aTexCoord;
        }
    """.trimIndent()

    val FRAGMENT_SHADER_CODE = """
        precision mediump float;
        varying vec2 vTexCoord;
        uniform sampler2D uTexture;
        void main()
        {
            gl_FragColor = texture2D(uTexture, vTexCoord);
        }
    """.trimIndent()
  }
}