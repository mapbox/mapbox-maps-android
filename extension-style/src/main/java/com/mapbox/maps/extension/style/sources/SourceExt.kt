@file:JvmName("SourceUtils")

package com.mapbox.maps.extension.style.sources

import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.StyleContract
import com.mapbox.maps.extension.style.sources.generated.*
import com.mapbox.maps.extension.style.utils.silentUnwrap
import com.mapbox.maps.logE
import com.mapbox.maps.logW

/**
 * Extension function to get a Source provided by the Style Extension by source id.
 *
 * @param sourceId the source id
 * @return StyleSourcePlugin
 */
fun Style.getSource(sourceId: String): Source? {
  return this.getStyleSourceProperty(sourceId, "type").silentUnwrap<String>()?.let { type ->
    when (type) {
      "vector" -> VectorSource.Builder(sourceId).build().also { it.delegate = this }
      "geojson" -> GeoJsonSource.Builder(sourceId).build().also { it.delegate = this }
      "image" -> ImageSource.Builder(sourceId).build().also { it.delegate = this }
      "raster-dem" -> RasterDemSource.Builder(sourceId).build().also { it.delegate = this }
      "raster" -> RasterSource.Builder(sourceId).build().also { it.delegate = this }
      else -> {
        logE("StyleSourcePlugin", "Source type: $type unknown.")
        null
      }
    }
  }
}

/**
 * Tries to cast the Source to T.
 *
 * @param sourceId the layer id
 * @return T if Source is T and null otherwise
 */
@SuppressWarnings("ChangedType")
inline fun <reified T : Source> Style.getSourceAs(sourceId: String): T? {
  val source = getSource(sourceId)
  if (source !is T) {
    logW(
      "StyleSourcePlugin",
      "Given sourceId = $sourceId is not requested type in getSourceAs."
    )
    return null
  }
  return source
}

/**
 * Extension function to add a Source provided by the Style Extension to the Style.
 *
 * @param source The source to be added
 */
fun Style.addSource(source: StyleContract.StyleSourceExtension) {
  source.bindTo(this)
}