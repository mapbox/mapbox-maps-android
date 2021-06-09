package com.mapbox.maps.extension.style.localization

import com.mapbox.common.Logger
import com.mapbox.maps.StyleObjectInfo
import com.mapbox.maps.extension.style.StyleInterface
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.toJson
import java.util.*

internal fun setMapLanguage(locale: Locale, style: StyleInterface) {
  style.styleSources
    .filter { sourceIsFromMapbox(style, it) }
    .forEach { source ->
      style.styleLayers
        .filter { it.type == LAYER_TYPE_SYMBOL }
        .forEach { layer ->
          val symbolLayer = style.getLayerAs<SymbolLayer>(layer.id)
          symbolLayer.textFieldAsExpression?.let { textFieldExpression ->
            if (sourceIsStreetsV8(style, source)) {
              convertExpressionV8(
                getLanguageNameV8(locale),
                symbolLayer,
                textFieldExpression
              )
            } else {
              val isStreetsV7 = sourceIsStreetsV7(style, source)
              val language =
                if (isStreetsV7) getLanguageNameV7(locale) else getLanguageNameV6(
                  locale
                )
              convertExpression(
                language,
                symbolLayer,
                textFieldExpression,
                isStreetsV7
              )
            }
          }
        }
    }
}

private fun convertExpression(
  language: String, layer: SymbolLayer,
  textFieldExpression: Expression?, isStreetsV7: Boolean
) {
  Logger.e(TAG, "v6, v7=====================")
  textFieldExpression?.let {
    Logger.e(TAG, "${it.toJson()}")
    var text: String = it.toJson().replace(EXPRESSION_REGEX, language)
    Logger.e(TAG, "$text")

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

private fun convertExpressionV8(
  language: String,
  layer: SymbolLayer,
  textFieldExpression: Expression?
) {
  Logger.e(TAG, "=====================")
  textFieldExpression?.let {
    Logger.e(TAG, "${it.toJson()}")

    var stringExpression: String = it.toJson().replace(
      EXPRESSION_REGEXV8,
      get("$language").toJson()
    )
    Logger.e(TAG, "${stringExpression}")

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

private const val TAG = "LocalizationPluginImpl"
private const val SOURCE_TYPE_VECTOR = "vector"
private const val LAYER_TYPE_SYMBOL = "symbol"
private const val STREET_V6 = "mapbox.mapbox-streets-v6"
private const val STREET_V7 = "mapbox.mapbox-streets-v7"
private const val STREET_V8 = "mapbox.mapbox-streets-v8"
private val SUPPORTED_SOURCES = listOf(STREET_V6, STREET_V7, STREET_V8)
private val EXPRESSION_REGEX = Regex("\\b(name|name_.{2,7})\\b")
private val EXPRESSION_REGEXV8 = Regex("\\[\"get\",\"name_.{2,7}\"\\]")

// faulty step expression workaround
private const val STEP_REGEX = "\\[\"zoom\"],"
private const val STEP_TEMPLATE = "[\"zoom\"],\"\","

