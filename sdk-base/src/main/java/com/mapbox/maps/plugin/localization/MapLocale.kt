package com.mapbox.maps.plugin.localization

import android.os.Build
import com.mapbox.geojson.Point
import com.mapbox.maps.CoordinateBounds
import java.util.*

/**
 * A [MapLocale] object builds off of the [Locale] object and provides additional
 * geographical information particular to the Mapbox Maps SDK. Like Locale, MapLocale can be used to
 * make the map locale sensitive.
 *
 * The [MapLocale] object can be used to acquire the matching Locale's map language; useful for
 * translating the map language into one of the supported ones found in [Languages].
 *
 * You'll also be able to get bounding box information for that same country so the map's starting
 * position target can adjust itself over the device's Locale country.
 *
 * A handful of [MapLocale]'s are already constructed and offered through this class as static
 * variables. If a country is missing and you'd like to add it, you can use one of the
 * [MapLocale] constructors to build a valid map locale. Once this is done, you need to add it
 * to the Locale cache using [MapLocale.addMapLocale(Locale, MapLocale)] where the first
 * parameter is the [Locale]object which matches up with your newly created [MapLocale].
 *
 * @param mapLanguage   a non-null string which is allowed from [Languages]
 * @param countryBounds [CoordinateBounds] object which wraps around the country
 */
data class MapLocale(val mapLanguage: Languages, val countryBounds: CoordinateBounds? = null) {

  /**
   * Static properties and methods
   */
  companion object {
    /**
     * Approximate USA bounding box, excluding Hawaii and Alaska
     */
    val USA_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-124.733253, 24.544245),
      Point.fromLngLat(-66.954811, 49.388611), false
    )

    /**
     * Approximate UK bounding box
     */
    val UK_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-8.623555, 49.906193),
      Point.fromLngLat(1.759, 59.360249), false
    )

    /**
     * Approximate Canada bounding box
     */
    val CANADA_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-141.0, 41.67598),
      Point.fromLngLat(-52.636291, 83.110626), false
    )

    /**
     * Approximate China bounding box
     */
    val CHINA_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(73.557693, 15.775416),
      Point.fromLngLat(134.773911, 53.56086), false
    )

    /**
     * Approximate Taiwan bounding box
     */
    val TAIWAN_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(118.115255566105, 21.733333),
      Point.fromLngLat(122.107778, 26.389444), false
    )

    /**
     * Approximate Germany bounding box
     */
    val GERMANY_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(5.865639, 47.275776),
      Point.fromLngLat(15.039889, 55.055637), false
    )

    /**
     * Approximate Korea bounding box
     */
    val KOREA_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(125.887108, 33.190945),
      Point.fromLngLat(129.584671, 38.612446), false
    )

    /**
     * Approximate Japan bounding box
     */
    val JAPAN_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(122.93853, 24.249472),
      Point.fromLngLat(145.820892, 45.52314), false
    )

    /**
     * Approximate France bounding box
     */
    val FRANCE_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-5.142222, 41.371582),
      Point.fromLngLat(9.561556, 51.092804), false
    )

    /**
     * Approximate Russian bounding box
     */
    val RUSSIA_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-168.997849, 41.185902),
      Point.fromLngLat(19.638861, 81.856903), false
    )

    /**
     * Approximate Spain bounding box
     */
    val SPAIN_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-18.3936845, 43.9933088),
      Point.fromLngLat(4.5918885, 27.4335426), false
    )

    /**
     * Approximate Portugal bounding box
     */
    val PORTUGAL_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-18.3936845, 42.280468655),
      Point.fromLngLat(-6.3890876937, 27.4335426), false
    )

    /**
     * Approximate Brazil bounding box
     */
    val BRAZIL_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(-33.8689056, -28.6341164),
      Point.fromLngLat(-73.9830625, 5.2842873), false
    )

    /**
     * Approximate Vietnam bounding box
     */
    val VIETNAM_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(102.216667, 23.666667),
      Point.fromLngLat(109.466667, 8.383333), false
    )

    /**
     * Approximate Italy bounding box
     */
    val ITALY_BBOX: CoordinateBounds = CoordinateBounds(
      Point.fromLngLat(6.7499552751, 47.1153931748),
      Point.fromLngLat(18.4802470232, 36.619987291), false
    )

    /*
   * Some MapLocales already defined (these match with the predefined ones in the Locale class)
   */
    /**
     * Useful constant for FRANCE.
     */
    val FRANCE: MapLocale = MapLocale(Languages.FRENCH, FRANCE_BBOX)

    /**
     * Useful constant for GERMANY.
     */
    val GERMANY: MapLocale = MapLocale(Languages.GERMAN, GERMANY_BBOX)

    /**
     * Useful constant for JAPAN.
     */
    val JAPAN: MapLocale = MapLocale(Languages.JAPANESE, JAPAN_BBOX)

    /**
     * Useful constant for KOREA.
     */
    val KOREA: MapLocale = MapLocale(Languages.KOREAN, KOREA_BBOX)

    /**
     * Useful constant for CHINA.
     */
    val CHINA: MapLocale = MapLocale(Languages.SIMPLIFIED_CHINESE, CHINA_BBOX)

    /**
     * Useful constant for TAIWAN.
     */
    val TAIWAN: MapLocale = MapLocale(Languages.TRADITIONAL_CHINESE, TAIWAN_BBOX)

    /**
     * Useful constant for China with general Simplified Chinese
     */
    val CHINESE_HANS: MapLocale = MapLocale(Languages.SIMPLIFIED_CHINESE)

    /**
     * Useful constant for China with general Traditional Chinese
     */
    val CHINESE_HANT: MapLocale = MapLocale(Languages.TRADITIONAL_CHINESE)

    /**
     * Useful constant for UK.
     */
    val UK: MapLocale = MapLocale(Languages.ENGLISH, UK_BBOX)

    /**
     * Useful constant for US.
     */
    val US: MapLocale = MapLocale(Languages.ENGLISH, USA_BBOX)

    /**
     * Useful constant for CANADA.
     */
    val CANADA: MapLocale = MapLocale(Languages.ENGLISH, CANADA_BBOX)

    /**
     * Useful constant for CANADA_FRENCH.
     */
    val CANADA_FRENCH: MapLocale = MapLocale(Languages.FRENCH, CANADA_BBOX)

    /**
     * Useful constant for RUSSIA.
     */
    val RUSSIA: MapLocale = MapLocale(Languages.RUSSIAN, RUSSIA_BBOX)

    /**
     * Useful constant for SPAIN.
     */
    val SPAIN: MapLocale = MapLocale(Languages.SPANISH, SPAIN_BBOX)

    /**
     * Useful constant for PORTUGAL.
     */
    val PORTUGAL: MapLocale = MapLocale(Languages.PORTUGUESE, PORTUGAL_BBOX)

    /**
     * Useful constant for BRAZIL.
     */
    val BRAZIL: MapLocale = MapLocale(Languages.PORTUGUESE, BRAZIL_BBOX)

    /**
     * Useful constant for VIETNAM.
     */
    val VIETNAM: MapLocale = MapLocale(Languages.VIETNAMESE, VIETNAM_BBOX)

    /**
     * Useful constant for ITALY.
     */
    val ITALY: MapLocale = MapLocale(Languages.ITALIAN, ITALY_BBOX)

    /**
     * When creating a new MapLocale, you'll need to associate a [Locale] so that
     * [Locale.getDefault] will find the correct corresponding [MapLocale].
     *
     * @param locale    a valid [Locale] instance shares a 1 to 1 relationship with the
     * [MapLocale]
     * @param mapLocale the [MapLocale] which shares a 1 to 1 relationship with the
     * [Locale]
     */
    fun addMapLocale(locale: Locale, mapLocale: MapLocale) {
      LOCALE_SET[locale] = mapLocale
    }

    /**
     * Passing in a Locale, you are able to receive the [MapLocale] object which it is currently
     * paired with. If this returns null, there was no matching [MapLocale] to go along with the
     * passed in Locale. If you expected a non-null result, you should make sure you used
     * [.addMapLocale] before making this call.
     *
     * @param locale         the locale which you'd like to receive its matching [MapLocale] if one exists
     * @param acceptFallback whether the locale should fallback to the first declared that matches the language,
     * the fallback locale can be added with [.addMapLocale]
     * @return the matching [MapLocale] if one exists, otherwise null
     * @see .getMapLocaleFallback
     * @since 0.1.0
     */
    fun getMapLocale(locale: Locale, acceptFallback: Boolean = false): MapLocale? {
      var foundLocale = LOCALE_SET[locale]
      if (acceptFallback && foundLocale == null) {
        foundLocale = MapLocale.getMapLocaleFallback(locale)
      }
      return foundLocale
    }

    /**
     * Passing in a Locale, you are able to receive the [MapLocale] object which it is currently
     * paired with as a fallback. If this returns null, there was no matching [MapLocale] to go along with the
     * passed in Locale. If you expected a non-null result, you should make sure you used
     * [.addMapLocale] before making this call.
     *
     * @param locale the locale which you'd like to receive its matching [MapLocale](fallback) if one exists
     * @return the matching [MapLocale] if one exists, otherwise null
     * @since 0.1.0
     */
    private fun getMapLocaleFallback(locale: Locale): MapLocale? {
      val fallbackCode = locale.language.substring(0, 2)
      var foundMapLocale: MapLocale? = null
      for (possibleLocale in LOCALE_SET.keys) {
        if (possibleLocale.language == fallbackCode) {
          foundMapLocale = LOCALE_SET[possibleLocale]
          break
        }
      }
      return foundMapLocale
    }

    /**
     * Maps out the Matching pair of [Locale] and [MapLocale]. In other words, if I have a
     * [Locale.CANADA], this should be matched up with [MapLocale.CANADA].
     */
    private val LOCALE_SET: MutableMap<Locale, MapLocale> = hashMapOf()

    init {
      LOCALE_SET[Locale.US] = US
      LOCALE_SET[Locale.CANADA_FRENCH] = CANADA_FRENCH
      LOCALE_SET[Locale.CANADA] = CANADA
      LOCALE_SET[Locale.CHINA] = CHINESE_HANS
      LOCALE_SET[Locale.TAIWAN] = TAIWAN
      LOCALE_SET[Locale.UK] = UK
      LOCALE_SET[Locale.JAPAN] = JAPAN
      LOCALE_SET[Locale.KOREA] = KOREA
      LOCALE_SET[Locale.GERMANY] = GERMANY
      LOCALE_SET[Locale.FRANCE] = FRANCE
      LOCALE_SET[Locale("ru", "RU")] = RUSSIA
      LOCALE_SET[Locale("es", "ES")] = SPAIN
      LOCALE_SET[Locale("pt", "PT")] = PORTUGAL
      LOCALE_SET[Locale("pt", "BR")] = BRAZIL
      LOCALE_SET[Locale("vi", "VN")] = VIETNAM
      LOCALE_SET[Locale.ITALY] = ITALY
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val zh_CN_Hans =
          Locale.Builder().setLanguage("zh").setRegion("CN").setScript("Hans").build()
        val zh_HK_Hans =
          Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hans").build()
        val zh_MO_Hans =
          Locale.Builder().setLanguage("zh").setRegion("MO").setScript("Hans").build()
        val zh_SG_Hans =
          Locale.Builder().setLanguage("zh").setRegion("SG").setScript("Hans").build()
        val zh_TW_Hant =
          Locale.Builder().setLanguage("zh").setRegion("TW").setScript("Hant").build()
        val zh_HK_Hant =
          Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hant").build()
        val zh_MO_Hant =
          Locale.Builder().setLanguage("zh").setRegion("MO").setScript("Hant").build()

        // streets v8 supports name_zh-Hans(MapLocale.CHINESE_HANS) and name_zh-Hant(MapLocale.CHINESE_HANT)
        // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v8/#name-text--name_lang-code-text
        LOCALE_SET[zh_CN_Hans] = CHINESE_HANS
        LOCALE_SET[zh_HK_Hans] = CHINESE_HANS
        LOCALE_SET[zh_MO_Hans] = CHINESE_HANS
        LOCALE_SET[zh_SG_Hans] = CHINESE_HANS
        LOCALE_SET[zh_TW_Hant] = TAIWAN
        LOCALE_SET[zh_HK_Hant] = CHINESE_HANT
        LOCALE_SET[zh_MO_Hant] = CHINESE_HANT
      }
    }
  }
}

/**
 * Supported Mapbox map languages.
 */

enum class Languages(val value: String) {
  /**
   * The name (or names) used locally for the place.
   */
  LOCAL_NAME("name"),

  /**
   * English (if available)
   */
  ENGLISH("name_en"),

  /**
   * French (if available)
   */
  FRENCH("name_fr"),

  /**
   * Arabic (if available)
   */
  ARABIC("name_ar"),

  /**
   * Spanish (if available)
   */
  SPANISH("name_es"),

  /**
   * German (if available)
   */
  GERMAN("name_de"),

  /**
   * Portuguese (if available)
   */
  PORTUGUESE("name_pt"),

  /**
   * Russian (if available)
   */
  RUSSIAN("name_ru"),

  /**
   * Chinese (if available)
   */
  CHINESE("name_zh"),

  /**
   * Traditional Chinese (if available)
   */
  TRADITIONAL_CHINESE("name_zh-Hant"),

  /**
   * Simplified Chinese (if available)
   */
  SIMPLIFIED_CHINESE("name_zh-Hans"),

  /**
   * Japanese (if available)
   */
  JAPANESE("name_ja"),

  /**
   * Korean (if available)
   */
  KOREAN("name_ko"),

  /**
   * Vietnamese (if available)
   */
  VIETNAMESE("name_vi"),

  /**
   * Italy (if available)
   */
  ITALIAN("name_it")
}