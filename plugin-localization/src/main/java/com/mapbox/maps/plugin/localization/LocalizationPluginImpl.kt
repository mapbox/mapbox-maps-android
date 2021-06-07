package com.mapbox.maps.plugin.localization

import com.mapbox.bindgen.Value
import com.mapbox.common.Logger
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.StyleObjectInfo
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate
import com.mapbox.maps.toJson
import java.util.*

/**
 * Impl class for LocalizationPlugin
 */
class LocalizationPluginImpl : LocalizationPlugin {
  private var mapLocale: MapLocale? = null
  private var styleDelegate: StyleInterface? = null
  override fun matchMapLanguageWithDeviceDefault(acceptFallback: Boolean) {
    setMapLanguage(Locale.getDefault())
  }

  override fun setMapLanguage(language: Languages) {
    setMapLanguage(MapLocale(language))
  }

  override fun setMapLanguage(locale: Locale, acceptFallback: Boolean) {
    val mapLocale = MapLocale.getMapLocale(locale, acceptFallback)
    if (mapLocale != null) {
      setMapLanguage(mapLocale)
    } else {
      Logger.d(TAG, "Couldn't match Locale $locale ${locale.displayName} to a MapLocale")
    }
  }

  override fun setMapLanguage(mapLocale: MapLocale) {
    this.mapLocale = mapLocale
    styleDelegate?.let { style ->
      for (source in style.styleSources) {
        if (sourceIsFromMapbox(style, source)) {
          val isStreetsV8: Boolean = sourceIsStreetsV8(style, source)
          for (layer in style.styleLayers) {
            if (layer.type == "symbol") {
              val symbolLayer = style.getLayerAs<SymbolLayer>(layer.id)
              val textFieldExpression = symbolLayer.textFieldAsExpression
              textFieldExpression?.let {
                if (isStreetsV8) {
                  convertExpressionV8(mapLocale, symbolLayer, textFieldExpression)
                } else {
                  val isStreetsV7: Boolean = sourceIsStreetsV7(style, source)
                  convertExpression(mapLocale, symbolLayer, textFieldExpression, isStreetsV7)
                }
              }
            }
          }
        } else {
          Logger.d(
            TAG,
            "The source ${source.id} is not based on Mapbox Vector Tiles. Supported sources:\n $SUPPORTED_SOURCES"
          )
        }
      }
    }
  }

  override fun onStyleChanged(styleDelegate: StyleInterface) {
    this.styleDelegate = styleDelegate
    mapLocale?.let {
      setMapLanguage(it)
    }
  }

  private fun convertExpression(
    mapLocale: MapLocale, layer: SymbolLayer,
    textFieldExpression: Expression?, isStreetsV7: Boolean
  ) {
    textFieldExpression?.let {
      var newMapLocale = mapLocale
      val mapLanguage = mapLocale.mapLanguage
      if (mapLanguage == Languages.CHINESE) {
        // need to re-get mapLocale, since the default is for street-v8
        newMapLocale = getChineseMapLocale(mapLocale, isStreetsV7)
      }
      var text: String = it.toJson().replace(EXPRESSION_REGEX, newMapLocale.mapLanguage.value)
      //todo: find a way to resolve textFieldExpression.toArray()
//      if (text.startsWith("[\"step") && textFieldExpression.toArray().length % 2 === 0) {
//        // got an invalid step expression from core, we need to add an additional name_x into step
//        text =
//          text.replace(STEP_REGEX.toRegex(), STEP_TEMPLATE)
//      }
      layer.textField(text)
    }
  }

  private fun getChineseMapLocale(mapLocale: MapLocale, isStreetsV7: Boolean): MapLocale {
    return if (isStreetsV7) {
      // streets v7 supports name_zh(MapLocale.CHINA) and name_zh-Hans(MapLocale.CHINESE_HANS)
      // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v7/#name-fields
      if (mapLocale == MapLocale.CHINESE_HANS) {
        mapLocale
      } else {
        MapLocale.CHINA
      }
    } else {
      // streets V6 only supports name_zh(MapLocale.CHINA)
      // https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v6/#name-fields
      MapLocale.CHINA
    }
  }

  private fun convertExpressionV8(
    mapLocale: MapLocale, layer: SymbolLayer,
    textFieldExpression: Expression?
  ) {
    textFieldExpression?.let {
      var stringExpression: String = textFieldExpression.toJson().replace(
        EXPRESSION_V8_REGEX_LOCALIZED,
        EXPRESSION_V8_TEMPLATE_BASE
      )
      var mapLanguage = mapLocale.mapLanguage
      if (mapLanguage != Languages.ENGLISH) {
        if (mapLanguage == Languages.CHINESE) {
          mapLanguage = Languages.SIMPLIFIED_CHINESE
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
      if (sourceIsTheType(style, source, supportedSource)) {
        return true
      }
    }
    return false
  }

  private fun sourceIsStreetsV7(style: StyleInterface, source: StyleObjectInfo): Boolean =
    sourceIsTheType(style, source, STREET_V7)

  private fun sourceIsStreetsV8(style: StyleInterface, source: StyleObjectInfo): Boolean =
    sourceIsTheType(
      style, source,
      STREET_V8
    )

  private fun sourceIsTheType(
    style: StyleInterface,
    source: StyleObjectInfo,
    type: String
  ): Boolean {
    if (source.type == "vector") {
      val expected = style.getStyleSourceProperties(source.id)
      expected.value?.let { value ->
        @Suppress("UNCHECKED_CAST")
        val map = value.contents as HashMap<String, Value>
        map["url"]?.contents?.let { url ->
          if ((url as String).contains(type)) {
            return true
          }
        }
      }
    }
    return false
  }

  companion object {
    private const val TAG = "LocalizationPluginImpl"
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
  }
}

/**
 * Extension function for MapView to get the localization plugin instance.
 *
 * @return Localization plugin instance
 */
@MapboxExperimental
fun MapPluginProviderDelegate.localization(): LocalizationPlugin {
  return this.getPlugin(LocalizationPluginImpl::class.java)!!
}