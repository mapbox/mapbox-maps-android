package com.mapbox.maps

/**
 * Describes render cache mode where [cacheSizeMb] is actual GPU render cache size in megabytes.
 */
sealed class RenderCache(
  /**
   * Render cache size in megabytes.
   * That value should be greater than zero and less than GPU memory.
   * It is highly advised to use [RenderCache.Small] or [RenderCache.Large] in most cases.
   */
  val cacheSizeMb: Long
) {
  /**
   * Render cache is disabled.
   */
  object Disabled : RenderCache(CACHE_DISABLED)

  /**
   * Render cache is set to be small.
   * Works best with devices with not so powerful GPUs.
   */
  object Small : RenderCache(CACHE_SIZE_SMALL_MB)

  /**
   * Render cache is set to be large.
   * Works best with devices powerful GPUs.
   */
  object Large : RenderCache(CACHE_SIZE_LARGE_MB)

  /**
   * Custom render cache with given [cacheSizeMb].
   */
  class Custom(cacheSizeMb: Long) : RenderCache(cacheSizeMb)

  /**
   * Static variables.
   */
  companion object {
    internal const val RENDER_CACHE_SETTING = "mapbox_render_cache_size_mb"
    internal const val CACHE_DISABLED = 0L
    internal const val CACHE_SIZE_SMALL_MB = 64L
    internal const val CACHE_SIZE_LARGE_MB = 128L
  }
}