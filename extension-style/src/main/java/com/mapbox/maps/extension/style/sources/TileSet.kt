package com.mapbox.maps.extension.style.sources

import com.mapbox.bindgen.Value
import com.mapbox.maps.extension.style.sources.generated.Encoding
import com.mapbox.maps.extension.style.sources.generated.Scheme
import java.util.HashMap

/**
 * Tile set, allows using TileJson specification as source.
 * Note that `encoding` is only relevant to `raster-dem` sources, and is not supported in the TileJson spec.
 *
 * @see [The tileset specification](https://github.com/mapbox/tilejson-spec/tree/master/2.1.0)
 */
class TileSet private constructor(builder: Builder) : HashMap<String, Value>(builder.parameters) {
  /**
   * Builder for TileSet.
   */
  open class Builder(
    /**
     * @param tilejson A semver.org style version number. Describes the version of the TileJSON spec that is implemented
     *                 by this JSON object.
     */
    val tilejson: String,
    /**
     * @param tiles An array of tile endpoints. {z}, {x} and {y}, if present, are replaced with the corresponding
     *                 integers.
     *                 If multiple endpoints are specified, clients may use any combination of endpoints. All endpoints
     *                 MUST return the same
     *                 content for the same URL. The array MUST contain at least one endpoint.
     *                 Example: "http:localhost:8888/admin/1.0.0/world-light,broadband/{z}/{x}/{y}.png"
     */
    val tiles: List<String>
  ) {
    internal val parameters: HashMap<String, Value> = HashMap()

    init {
      parameters["tilejson"] = Value(tilejson)
      parameters["tiles"] = Value(tiles.map(::Value))
    }

    /**
     * A name describing the tileset. The name can
     * contain any legal character. Implementations SHOULD NOT interpret the
     * name as HTML.
     * "name": "compositing",
     *
     * @param value the name to be set
     */
    fun name(value: String): Builder = apply {
      parameters["name"] = Value(value)
    }

    /**
     * A text description of the tileset. The
     * description can contain any legal character.
     * Implementations SHOULD NOT
     * interpret the description as HTML.
     * "description": "A simple, light grey world."
     *
     * @param value the description to set
     */
    fun description(value: String): Builder = apply {
      parameters["description"] = Value(value)
    }

    /**
     *  Default: "1.0.0". A semver.org style version number. When
     *  changes across tiles are introduced, the minor version MUST change.
     *  This may lead to cut off labels. Therefore, implementors can decide to
     *  clean their cache when the minor version changes. Changes to the patch
     *  level MUST only have changes to tiles that are contained within one tile.
     *  When tiles change significantly, the major version MUST be increased.
     *  Implementations MUST NOT use tiles with different major versions.
     *
     *  @param value the version to set
     */
    fun version(value: String = "1.0.0"): Builder = apply {
      parameters["version"] = Value(value)
    }

    /**
     * Default: null. Contains an attribution to be displayed
     * when the map is shown to a user. Implementations MAY decide to treat this
     * as HTML or literal text. For security reasons, make absolutely sure that
     * this field can't be abused as a vector for XSS or beacon tracking.
     * "attribution": "[OSM contributors](http:openstreetmap.org)",
     *
     * @param value the attribution to set
     */
    fun attribution(value: String): Builder = apply {
      parameters["attribution"] = Value(value)
    }

    /**
     * Contains a mustache template to be used to
     * format data from grids for interaction.
     * See https:github.com/mapbox/utfgrid-spec/tree/master/1.2
     * for the interactivity specification.
     * "template": "{{#__teaser__}}{{NAME}}{{/__teaser__}}"
     *
     * @param value the template to set
     */
    fun template(value: String): Builder = apply {
      parameters["template"] = Value(value)
    }

    /**
     * Contains a legend to be displayed with the map.
     * Implementations MAY decide to treat this as HTML or literal text.
     * For security reasons, make absolutely sure that this field can't be
     * abused as a vector for XSS or beacon tracking.
     * "legend": "Dangerous zones are red, safe zones are green"
     *
     * @param value the legend to set
     */
    fun legend(value: String): Builder = apply {
      parameters["legend"] = Value(value)
    }

    /**
     * Default: "xyz". Either "xyz" or "tms". Influences the y
     * direction of the tile coordinates.
     * The global-mercator (aka Spherical Mercator) profile is assumed.
     * "scheme": "xyz"
     *
     * @param value the scheme to set
     */
    fun scheme(value: Scheme): Builder = apply {
      parameters["scheme"] = Value(value.value)
    }

    /**
     * An array of interactivity endpoints. {z}, {x}
     * and {y}, if present, are replaced with the corresponding integers. If multiple
     * endpoints are specified, clients may use any combination of endpoints.
     * All endpoints MUST return the same content for the same URL.
     * If the array doesn't contain any entries, interactivity is not supported
     * for this tileset.     See https:github.com/mapbox/utfgrid-spec/tree/master/1.2
     * for the interactivity specification.
     *
     *
     * Example: "http:localhost:8888/admin/1.0.0/broadband/{z}/{x}/{y}.grid.json"
     *
     * @param value the grids to set
     */
    fun grids(value: List<String>): Builder = apply {
      parameters["grids"] = Value(value.map(::Value))
    }

    /**
     * An array of data files in GeoJSON format.
     * {z}, {x} and {y}, if present,
     * are replaced with the corresponding integers. If multiple
     * endpoints are specified, clients may use any combination of endpoints.
     * All endpoints MUST return the same content for the same URL.
     * If the array doesn't contain any entries, then no data is present in
     * the map.
     *
     *
     * "http:localhost:8888/admin/data.geojson"
     *
     * @param value the data array to set
     */
    fun data(value: List<String>): Builder = apply {
      parameters["data"] = Value(value.map(::Value))
    }

    /**
     * Default to 0. &gt;= 0, &lt; 22. An integer specifying the minimum zoom level.
     *
     * @param value the minZoom level to set
     */
    fun minZoom(value: Int = 0): Builder = apply {
      parameters["minzoom"] = Value(value.toLong())
    }

    /**
     * Default to 30. &gt;= 0, &lt;= 22. An integer specifying the maximum zoom level.
     *
     * @param value the maxZoom level to set
     */
    fun maxZoom(value: Int = 30): Builder = apply {
      parameters["maxzoom"] = Value(value.toLong())
    }

    /**
     * Default: [-180, -90, 180, 90]. The maximum extent of available map tiles. Bounds MUST define an area
     * covered by all zoom levels. The bounds are represented in WGS:84
     * latitude and longitude values, in the order left, bottom, right, top.
     * Values may be integers or floating point numbers.
     *
     * @param value the Double list to set
     */
    fun bounds(value: List<Double> = listOf(-180.0, -90.0, 180.0, 90.0)): Builder = apply {
      parameters["bounds"] = Value(value.map(::Value))
    }

    /**
     * The first value is the longitude, the second is latitude (both in
     * WGS:84 values), the third value is the zoom level as an integer.
     * Longitude and latitude MUST be within the specified bounds.
     * The zoom level MUST be between minzoom and maxzoom.
     * Implementations can use this value to set the default location. If the
     * value is null, implementations may use their own algorithm for
     * determining a default location.
     *
     * @param value the Double array to set
     */
    fun center(value: List<Double>): Builder = apply {
      parameters["center"] = Value(value.map(::Value))
    }

    /**
     * Build the TileSet.
     *
     * @return the constructed TileSet
     */
    fun build(): TileSet = TileSet(this)
  }
  /**
   * Builder of TileSet for RasterDemSource.
   */
  class RasterDemBuilder(
    /**
     * @param tilejson A semver.org style version number. Describes the version of the TileJSON spec that is implemented
     *                 by this JSON object.
     */
    tilejson: String,
    /**
     * @param tiles An array of tile endpoints. {z}, {x} and {y}, if present, are replaced with the corresponding
     *                 integers.
     *                 If multiple endpoints are specified, clients may use any combination of endpoints. All endpoints
     *                 MUST return the same
     *                 content for the same URL. The array MUST contain at least one endpoint.
     *                 Example: "http:localhost:8888/admin/1.0.0/world-light,broadband/{z}/{x}/{y}.png"
     */
    tiles: List<String>
  ) : Builder(tilejson, tiles) {
    /**
     * Default: "mapbox". The encoding formula for a raster-dem tileset.
     * Supported values are "mapbox" and "terrarium".
     *
     * @param value the String to set
     */
    fun encoding(value: Encoding): RasterDemBuilder = apply {
      parameters["encoding"] = Value(value.value)
    }
  }
}