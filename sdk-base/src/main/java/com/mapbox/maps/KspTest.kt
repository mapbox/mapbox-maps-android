package com.mapbox.maps

import com.morfly.Function
import kotlin.collections.Map

@Function(name = "myAmazingFunction")
interface MyAmazingFunction {
  val arg1: String?
  val arg2: List<Int?>
  val arg3: List<Map<String, *>>
}