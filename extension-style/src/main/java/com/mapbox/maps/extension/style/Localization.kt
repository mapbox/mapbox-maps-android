package com.mapbox.maps.extension.style

import android.os.Build
import com.mapbox.common.Logger
import com.mapbox.maps.StyleObjectInfo
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.toJson
import java.util.*

internal class Localization {
  /**
   * You can pass in a []Languages] directly into this method which uses the language defined
   * in it to represent the language found on the map.
   *
   * @param Languages the [Languages] object which contains the desired map language
   */
  internal fun setMapLanguage(locale: Locale, style: StyleInterface) {
    LOCALE_LANGUAGES_MAP[locale]?.let { Languages ->
      style.styleSources
        .filter { sourceIsFromMapbox(style, it) }
        .forEach { source ->
          style.styleLayers
            .filter { it.type == LAYER_TYPE_SYMBOL }
            .forEach { layer ->
              val symbolLayer = style.getLayerAs<SymbolLayer>(layer.id)
              symbolLayer.textFieldAsExpression?.let { textFieldExpression ->
                if (sourceIsStreetsV8(style, source)) {
                  convertExpressionV8(Languages, symbolLayer, textFieldExpression)
                } else {
                  convertExpression(
                    Languages,
                    symbolLayer,
                    textFieldExpression,
                    sourceIsStreetsV7(style, source)
                  )
                }
              }
            }
        }
    }
  }

  private fun convertExpression(
    language: Languages, layer: SymbolLayer,
    textFieldExpression: Expression?, isStreetsV7: Boolean
  ) {
    textFieldExpression?.let {
      var newLanguage = language
      if (language == Languages.CHINESE) {
        // need to re-get Languages, since the default is for street-v8
        newLanguage = getChineseLanguages(language, isStreetsV7)
      }
      var text: String = it.toJson().replace(EXPRESSION_REGEX, newLanguage.value)
      //todo: find a way to resolve textFieldExpression.toArray()
      val s = textFieldExpression.getLiteral<List<Expression>>()

//      if (text.startsWith("[\"step") && textFieldExpression.toArray().length % 2 === 0) {
//        // got an invalid step expression from core, we need to add an additional name_x into step
//        text =
//          text.replace(STEP_REGEX.toRegex(), STEP_TEMPLATE)
//      }
      layer.textField(text)
    }
  }

  private fun getChineseLanguages(language: Languages, isStreetsV7: Boolean): Languages {
    return if (isStreetsV7) {
      // streets v7 supports name_zh(Languages.CHINA) and name_zh-Hans(Languages.CHINESE_HANS)
      // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v7/#name-fields
      if (language == Languages.CHINESE_HANS) {
        language
      } else {
        Languages.CHINESE
      }
    } else {
      // streets V6 only supports name_zh(Languages.CHINA)
      // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v6/#name-fields
      Languages.CHINESE
    }
  }

  private fun convertExpressionV8(
    language: Languages,
    layer: SymbolLayer,
    textFieldExpression: Expression?
  ) {
    textFieldExpression?.let {
      var stringExpression: String = textFieldExpression.toJson().replace(
        EXPRESSION_V8_REGEX_LOCALIZED,
        EXPRESSION_V8_TEMPLATE_BASE
      )
      var mapLanguage =language
      if (mapLanguage != Languages.ENGLISH) {
        if (mapLanguage == Languages.CHINESE) {
          mapLanguage = Languages.CHINESE_HANS
        }
        stringExpression = stringExpression.replace(
          EXPRESSION_V8_TEMPLATE_BASE, String.format(
            Locale.US,
            EXPRESSION_V8_TEMPLATE_LOCALIZED,
            mapLanguage.value,
            mapLanguage.value
          )
        )
      }
      layer.textField(Expression.fromRaw(stringExpression))
    }
  }

  private fun sourceIsFromMapbox(style: StyleInterface, source: StyleObjectInfo): Boolean {
    for (supportedSource in SUPPORTED_SOURCES) {
      if (sourceIsType(style, source, supportedSource)) {
        return true
      }
    }
    Logger.w(
      TAG,
      "The source ${source.id} is not based on Mapbox Vector Tiles. Supported sources:\n $SUPPORTED_SOURCES"
    )
    return false
  }

  private fun sourceIsStreetsV7(style: StyleInterface, source: StyleObjectInfo): Boolean =
    sourceIsType(style, source, STREET_V7)

  private fun sourceIsStreetsV8(style: StyleInterface, source: StyleObjectInfo): Boolean =
    sourceIsType(style, source, STREET_V8)

  private fun sourceIsType(style: StyleInterface, source: StyleObjectInfo, type: String): Boolean {
    if (source.type == SOURCE_TYPE_VECTOR) {
      style.getSourceAs<VectorSource>(source.id)?.url?.let {
        return it.contains(type)
      }
    }
    return false
  }

  companion object {
    private const val TAG = "LocalizationPluginImpl"
    private const val SOURCE_TYPE_VECTOR = "vector"
    private const val LAYER_TYPE_SYMBOL = "symbol"
    private const val STREET_V6 = "mapbox.mapbox-streets-v6"
    private const val STREET_V7 = "mapbox.mapbox-streets-v7"
    private const val STREET_V8 = "mapbox.mapbox-streets-v8"
    private val SUPPORTED_SOURCES = listOf(STREET_V6, STREET_V7, STREET_V8)
    private const val EXPRESSION_REGEX = "\\b(name|name_.{2,7})\\b"

    private val EXPRESSION_V8_TEMPLATE_BASE = "${get("name_en").toJson()},${get("name").toJson()}"
    private val EXPRESSION_V8_REGEX_LOCALIZED = Regex(
      "\\[\"match\",\"(name|name_.{2,7})\","
        + "\"name_zh-Hant\",\\[\"coalesce\","
        + "\\[\"get\",\"name_zh-Hant\"],"
        + "\\[\"get\",\"name_zh-Hans\"],"
        + "\\[\"match\",\\[\"get\",\"name_script\"],\"Latin\",\\[\"get\",\"name\"],\\[\"get\",\"name_en\"]],"
        + "\\[\"get\",\"name\"]],"
        + "\\[\"coalesce\","
        + "\\[\"get\",\"(name|name_.{2,7})\"],"
        + "\\[\"match\",\\[\"get\",\"name_script\"],\"Latin\",\\[\"get\",\"name\"],\\[\"get\",\"name_en\"]],"
        + "\\[\"get\",\"name\"]]"
        + "]"
    )
    private const val EXPRESSION_V8_TEMPLATE_LOCALIZED = ("[\"match\",\"%s\","
      + "\"name_zh-Hant\",[\"coalesce\","
      + "[\"get\",\"name_zh-Hant\"],"
      + "[\"get\",\"name_zh-Hans\"],"
      + "[\"match\",[\"get\",\"name_script\"],\"Latin\",[\"get\",\"name\"],[\"get\",\"name_en\"]],"
      + "[\"get\",\"name\"]],"
      + "[\"coalesce\","
      + "[\"get\",\"%s\"],"
      + "[\"match\",[\"get\",\"name_script\"],\"Latin\",[\"get\",\"name\"],[\"get\",\"name_en\"]],"
      + "[\"get\",\"name\"]]"
      + "]")

    // faulty step expression workaround
    private const val STEP_REGEX = "\\[\"zoom\"],"
    private const val STEP_TEMPLATE = "[\"zoom\"],\"\","

    /**
     * Maps out the Matching pair of [Locale] and [Languages]. In other words, if I have a
     * [Locale.FRENCH], this should be matched up with [Languages.FRENCH].
     */
    private val LOCALE_LANGUAGES_MAP: MutableMap<Locale, Languages> = hashMapOf()

    init {
      LOCALE_LANGUAGES_MAP[Locale.US] = Languages.ENGLISH
      LOCALE_LANGUAGES_MAP[Locale.ENGLISH] = Languages.ENGLISH
      LOCALE_LANGUAGES_MAP[Locale.CANADA_FRENCH] = Languages.FRENCH
      LOCALE_LANGUAGES_MAP[Locale.CANADA] = Languages.ENGLISH
      LOCALE_LANGUAGES_MAP[Locale.CHINA] = Languages.CHINESE
      LOCALE_LANGUAGES_MAP[Locale.CHINESE] = Languages.CHINESE
      LOCALE_LANGUAGES_MAP[Locale.SIMPLIFIED_CHINESE] = Languages.CHINESE_HANS
      LOCALE_LANGUAGES_MAP[Locale.TRADITIONAL_CHINESE] = Languages.CHINESE_HANT
      LOCALE_LANGUAGES_MAP[Locale.TAIWAN] = Languages.CHINESE_HANT
      LOCALE_LANGUAGES_MAP[Locale.UK] = Languages.ENGLISH
      LOCALE_LANGUAGES_MAP[Locale.JAPAN] = Languages.JAPANESE
      LOCALE_LANGUAGES_MAP[Locale.JAPANESE] = Languages.JAPANESE
      LOCALE_LANGUAGES_MAP[Locale.KOREA] = Languages.KOREAN
      LOCALE_LANGUAGES_MAP[Locale.KOREAN] = Languages.KOREAN
      LOCALE_LANGUAGES_MAP[Locale.GERMAN] = Languages.GERMANY
      LOCALE_LANGUAGES_MAP[Locale.GERMANY] = Languages.GERMANY
      LOCALE_LANGUAGES_MAP[Locale.FRENCH] = Languages.FRENCH
      LOCALE_LANGUAGES_MAP[Locale.FRANCE] = Languages.FRENCH
      LOCALE_LANGUAGES_MAP[Locale("ru", "RU")] = Languages.RUSSIAN
      LOCALE_LANGUAGES_MAP[Locale("es", "ES")] = Languages.SPANISH
      LOCALE_LANGUAGES_MAP[Locale("pt", "PT")] = Languages.PORTUGUESE
      LOCALE_LANGUAGES_MAP[Locale("pt", "BR")] = Languages.PORTUGUESE
      LOCALE_LANGUAGES_MAP[Locale("vi", "VN")] = Languages.VIETNAMESE
      LOCALE_LANGUAGES_MAP[Locale.ITALY] = Languages.ITALIAN
      LOCALE_LANGUAGES_MAP[Locale.ITALIAN] = Languages.ITALIAN
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

        // streets v8 supports name_zh-Hans(Languages.CHINESE_HANS) and name_zh-Hant(Languages.CHINESE_HANT)
        // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v8/#name-text--name_lang-code-text
        LOCALE_LANGUAGES_MAP[zh_CN_Hans] = Languages.CHINESE_HANS
        LOCALE_LANGUAGES_MAP[zh_HK_Hans] = Languages.CHINESE_HANS
        LOCALE_LANGUAGES_MAP[zh_MO_Hans] = Languages.CHINESE_HANS
        LOCALE_LANGUAGES_MAP[zh_SG_Hans] = Languages.CHINESE_HANS
        LOCALE_LANGUAGES_MAP[zh_TW_Hant] = Languages.CHINESE_HANT
        LOCALE_LANGUAGES_MAP[zh_HK_Hant] = Languages.CHINESE_HANT
        LOCALE_LANGUAGES_MAP[zh_MO_Hant] = Languages.CHINESE_HANT
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
  GERMANY("name_de"),

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
  CHINESE_HANT("name_zh-Hant"),

  /**
   * Simplified Chinese (if available)
   */
  CHINESE_HANS("name_zh-Hans"),

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