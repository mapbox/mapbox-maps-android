package com.mapbox.maps.extension.style.types

import com.mapbox.bindgen.Value

/**
 * Concrete implementation of the StyleTransition interface.
 */
@LayersDsl
class StyleTransition private constructor(builder: Builder) {
  /**
   * Duration of the transition.
   */
  val duration: Long?
  /**
   * Delay of the transition.
   */
  val delay: Long?

  init {
    this.duration = builder.duration
    this.delay = builder.delay
  }

  /**
   * The [Value] representation of the transition object.
   */
  fun toValue(): Value {
    val transition = HashMap<String, Value>()
    transition["delay"] = Value(0)
    transition["duration"] = Value(0)
    this.delay?.let {
      transition["delay"] = Value(it)
    }
    this.duration?.let {
      transition["duration"] = Value(it)
    }
    return Value(transition)
  }

  /**
   * Builder for StyleTransitionImpl.
   */
  @LayersDsl
  class Builder {
    /**
     * Duration of the transition.
     */
    var duration: Long? = null
      private set
    /**
     * Delay of the transition.
     */
    var delay: Long? = null
      private set

    /**
     * Duration of the transition.
     */
    fun duration(duration: Long): Builder = apply { this.duration = duration }

    /**
     * Delay of the transition.
     */
    fun delay(delay: Long): Builder = apply { this.delay = delay }

    /**
     * Build the StyleTransitionImpl.
     *
     * @return StyleTransitionImpl
     */
    fun build() = StyleTransition(this)
  }

  /**
   * Assert an object is equal to this object.
   */
  override fun equals(other: Any?): Boolean {
    if (other is StyleTransition) {
      if (this.delay == other.delay && this.duration == other.duration) {
        return true
      }
    }
    return false
  }

  /**
   * Override the hash code calculation.
   */
  override fun hashCode(): Int {
    var result = duration?.hashCode() ?: 0
    result = 31 * result + (delay?.hashCode() ?: 0)
    return result
  }
}

/**
 * DSL function for [StyleTransition].
 */
fun transitionOptions(block: StyleTransition.Builder.() -> Unit): StyleTransition =
  StyleTransition.Builder().apply(block).build()