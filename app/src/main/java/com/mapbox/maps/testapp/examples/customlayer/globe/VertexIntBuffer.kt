package com.mapbox.maps.testapp.examples.customlayer.globe

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer

/**
 * Helper wrapper over int buffer with native order
 * */
@JvmInline
value class VertexIntBuffer(
  val value: IntBuffer,
) {

  // is bytes
  val size get() = value.capacity() * Int.SIZE_BYTES

  constructor(list: List<Int>) : this(
    ByteBuffer.allocateDirect(list.size * Int.SIZE_BYTES)
      .run {
        // use the device hardware's native byte order
        order(ByteOrder.nativeOrder())
        asIntBuffer()
      }.also { buffer ->
        list.forEach { value ->
          buffer.put(value)
        }
        buffer.rewind()
      }
  )

  fun put(vararg numbers: Number) {
    numbers.forEach { number ->
      value.put(number.toInt())
    }
  }

  fun rewind() = value.rewind() as IntBuffer
}