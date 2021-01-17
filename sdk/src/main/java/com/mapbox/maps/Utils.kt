package com.mapbox.maps

import java.lang.ref.WeakReference

/**
 * Checks the reference and if valid, calls the requested method.
 */
internal fun <T, R> WeakReference<T>.call(method: T.() -> R): R {
  return (this.get() ?: throw IllegalStateException()).method()
}

/**
 * Instantiate the class with given arguments. Each argument needs to be pass as a [Class] to value pair,
 * where [Class] is the exact class definition matching the constructor arguments.
 */
internal fun Class<*>.instantiate(vararg constructorArguments: Pair<Class<*>, Any>): Any {
  val constructor =
    this.getDeclaredConstructor(*constructorArguments.map { it.first }.toTypedArray())
  return constructor.newInstance(*constructorArguments.map { it.second }.toTypedArray())
}