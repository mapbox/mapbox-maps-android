package com.mapbox.maps.extension.style.sources

import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource

/**
 * Interface used to notify when geojson's data set through
 * [GeoJsonSource.featureCollection], [GeoJsonSource.feature] or [GeoJsonSource.geometry] is parsed.
 */
fun interface OnGeoJsonParsed {

  /**
   * Invoked when actual data is parsed.
   *
   * @param source [GeoJsonSource] with prepared data.
   */
  fun onGeoJsonParsed(source: GeoJsonSource)
}