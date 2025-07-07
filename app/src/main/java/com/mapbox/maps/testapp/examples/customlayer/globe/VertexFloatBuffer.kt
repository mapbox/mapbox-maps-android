package com.mapbox.maps.testapp.examples.customlayer.globe

import com.mapbox.maps.Vec3
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Helper wrapper over float buffer with native order
 * */
@JvmInline
value class VertexFloatBuffer private constructor(
  val value: FloatBuffer,
) {

  // is bytes
  val size get() = value.capacity() * Float.SIZE_BYTES

  constructor(size: Int) : this(
    ByteBuffer.allocateDirect(size * Float.SIZE_BYTES)
      .run {
        // use the device hardware's native byte order
        order(ByteOrder.nativeOrder())
        // create a floating point buffer from the ByteBuffer
        asFloatBuffer()
      }
  )

  fun put(vararg numbers: Number) {
    numbers.forEach { number ->
      value.put(number.toFloat())
    }
  }

  fun rewind() = value.rewind() as FloatBuffer

  companion object {

    fun VertexFloatBuffer.put(vec3: Vec3) = with(vec3) {
      put(x)
      put(y)
      put(z)
    }

    fun fromVec3(list: List<Vec3>) = VertexFloatBuffer(list.size * 3 * Float.SIZE_BYTES).also {
      val buffer = it.value
      list.forEach { vec3 ->
        it.put(vec3)
      }
      buffer.rewind()
    }

    fun fromMatrix(matrix: Matrix4f) = VertexFloatBuffer(16).also { buffer ->
      matrix.value.forEach {
        buffer.put(it)
      }
      buffer.rewind()
    }
  }
}