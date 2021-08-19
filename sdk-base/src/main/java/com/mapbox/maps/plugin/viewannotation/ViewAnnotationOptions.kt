package com.mapbox.maps.plugin.viewannotation

// bunch of potential properties about placing annotation etc
class ViewAnnotationOptions private constructor(
  val width: Int,
  val height: Int,
  val resizeFactor: Float
) {
  class Builder {
    private var resizeFactor = 0f
    private var width = -1
    private var height = -1

    fun resizeFactor(resizeFactor: Float) = apply { this.resizeFactor = resizeFactor }

    /**
     * If used during adding view for the first time and not updating it's advised to not populate
     * them directly as correct width will be calculated from inflated layout
     */
    fun width(width: Int) = apply { this.width = width }

    /**
     * If used during adding view for the first time and not updating it's advised to not populate
     * them directly as correct height will be calculated from inflated layout
     */
    fun height(height: Int) = apply { this.height = height }

    fun build() = ViewAnnotationOptions(width, height, resizeFactor)
  }

  fun toBuilder(): Builder {
    return Builder()
      .resizeFactor(resizeFactor)
      .width(width)
      .height(height)
  }

  /**
   * Hash code method.
   */
  override fun hashCode(): Int {
    var result = resizeFactor.hashCode()
    result += width.hashCode()
    result += height.hashCode()
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
    if (that.width != width) {
      return false
    }
    if (that.height != height) {
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
