package com.mapbox.maps.plugin.viewannotation

// bunch of potential properties about placing annotation etc
class ViewAnnotationOptions private constructor(
  val resizeFactor: Float = 0f
) {
  class Builder {
    private var resizeFactor = 0f

    fun resizeFactor(resizeFactor: Float) = apply { this.resizeFactor = resizeFactor }

    fun build() = ViewAnnotationOptions(resizeFactor)
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    val result = resizeFactor.hashCode()
    return result
  }

  /**
   * Equals method.
   */
  override fun equals(other: Any?): Boolean {
    if (this === other) {
      return true
    }
    if (other == null || javaClass != other.javaClass) {
      return false
    }
    val that = other as ViewAnnotationOptions
    if (that.resizeFactor != resizeFactor) {
      return false
    }
    return true
  }

  /**
   * Static methods.
   */
  companion object {
    /**
     * Builder DSL function to create [ViewAnnotationOptions] object.
     */
    fun viewAnnotationOptions(block: Builder.() -> Unit) = Builder().apply(block).build()
  }
}
