package com.mapbox.maps.testapp.examples.customlayer.globe

import android.opengl.Matrix

@JvmInline
value class Matrix4f private constructor(
  val value: FloatArray = FloatArray(16),
) {

  constructor(values: List<Number>) : this(
    kotlin.run {
      if (values.size != 16) {
        throw IllegalArgumentException("values size must be 16 but was ${values.size}")
      }
      FloatArray(16) { i ->
        values[i].toFloat()
      }
    }
  )

  constructor(vararg values: Number) : this(values.toList())

  operator fun get(index: Int): Float = value[index]
  operator fun set(index: Int, value: Number) {
    this.value[index] = value.toFloat()
  }

  operator fun set(row: Int, column: Int, value: Number) {
    this.value[row * 4 + column] = value.toFloat()
  }

  operator fun times(other: Matrix4f) = Matrix4f().also { result ->
    Matrix.multiplyMM(result.value, 0, this.value, 0, other.value, 0)
  }

  companion object {
    fun identity() = Matrix4f(
      1, 0, 0, 0,
      0, 1, 0, 0,
      0, 0, 1, 0,
      0, 0, 0, 1,
    )

    fun translation(x: Number, y: Number, z: Number) = Matrix4f(
      1, 0, 0, 0,
      0, 1, 0, 0,
      0, 0, 1, 0,
      x, y, z, 1,
    )

    fun scale(x: Number, y: Number, z: Number) = Matrix4f(
      x, 0, 0, 0,
      0, y, 0, 0,
      0, 0, z, 0,
      0, 0, 0, 1,
    )
  }
}