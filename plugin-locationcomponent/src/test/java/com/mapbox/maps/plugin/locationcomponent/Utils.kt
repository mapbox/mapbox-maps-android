package com.mapbox.maps.plugin.locationcomponent

import android.os.Bundle
import com.mapbox.bindgen.Value
import com.mapbox.common.toValue
import com.mapbox.maps.plugin.ModelScaleMode

fun Any.toValue(): Value {
  return when (this) {
    is Value -> this
    is Byte -> Value.valueOf(this.toLong())
    is Int -> Value.valueOf(this.toLong())
    is Long -> Value.valueOf(this)
    is Float -> Value.valueOf(this.toDouble())
    is Double -> Value.valueOf(this)
    is Boolean -> Value.valueOf(this)
    is String -> Value.valueOf(this)
    is List<*> -> Value(this.map { it!!.toValue() })
    is ModelScaleMode -> Value.valueOf(this.value)
    is Bundle -> this.toValue()
    is Enum<*> -> { Value.valueOf(this.ordinal.toLong()) }
    is Map<*, *> -> {
      val map = HashMap<String, Value>(this.size)
      for ((key, value) in this) {
        if (key is String) {
          map[key] = value!!.toValue()
        }
      }
      Value(map)
    }
    else -> {
      throw IllegalStateException("Can not convert $this of class ${this.javaClass} to value!")
    }
  }
}