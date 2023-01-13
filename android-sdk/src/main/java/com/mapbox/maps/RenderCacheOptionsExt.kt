package com.mapbox.maps

/**
 * Set render cache size to 0MB in order to disable it.
 */
fun RenderCacheOptions.Builder.setDisabled(): RenderCacheOptions.Builder = also {
  size(RENDER_CACHE_DISABLED)
}

/**
 * Set render cache size to small(64MB).
 *
 * Works best with devices with not so powerful GPUs, or devices with pixel ratio of 1.0.
 */
fun RenderCacheOptions.Builder.setSmallSize(): RenderCacheOptions.Builder = also {
  size(RENDER_CACHE_SIZE_SMALL_MB)
}

/**
 * Set render cache size to large(128MB).
 *
 * Works best with devices powerful GPUs, or devices with pixel ratio > 1.0.
 */
fun RenderCacheOptions.Builder.setLargeSize(): RenderCacheOptions.Builder = also {
  size(RENDER_CACHE_SIZE_LARGE_MB)
}

/**
 * Static variables.
 */
internal const val RENDER_CACHE_DISABLED = 0
internal const val RENDER_CACHE_SIZE_SMALL_MB = 64
internal const val RENDER_CACHE_SIZE_LARGE_MB = 128