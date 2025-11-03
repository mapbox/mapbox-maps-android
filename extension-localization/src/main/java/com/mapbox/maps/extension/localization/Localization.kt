package com.mapbox.maps.extension.localization

import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxStyleManager
import com.mapbox.maps.StylePropertyValueKind
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import com.mapbox.maps.logE
import com.mapbox.maps.logI
import java.util.*

/**
 * Apply languages to style by locale. It will replace the `["get","name_en"]` expression with a new expression `["get","name_xx"]`,
 * where name_xx is the supported local name related with locale.
 * For example if locale is [Locale.GERMAN], the original expression
 * `["format",["coalesce",["get","name_en"],["get","name"]],{}]` will be replaced by
 * `["format",["coalesce",["get","name_de"],["get","name"]],{}]`
 */
internal fun setMapLanguage(locale: Locale, style: MapboxStyleManager, layerIds: List<String>?) {
  val convertedLocale = "name_${locale.language}"
  if (!isSupportedLanguage(convertedLocale)) {
    logE(TAG, "Locale: $locale is not supported.")
    return
  }

  layerIds?.forEach { id ->
    localizeTextFieldExpression(
      style = style,
      layerId = id,
      locale = locale,
      convertedLocale = convertedLocale,
      filterSymbolLayers = true,
    )
  } ?: style.styleLayers.forEach { layer ->
    if (layer.type == SYMBOL) {
      localizeTextFieldExpression(
        style = style,
        layerId = layer.id,
        locale = locale,
        convertedLocale = convertedLocale,
        filterSymbolLayers = false,
      )
    }
  }
}

private fun localizeTextFieldExpression(
  style: MapboxStyleManager,
  layerId: String,
  locale: Locale,
  convertedLocale: String,
  filterSymbolLayers: Boolean,
) {
  if (filterSymbolLayers) {
    val type = style.getStyleLayerProperty(layerId, TYPE).value.contents as? String
    if (type != SYMBOL) {
      return
    }
  }
  val textFieldProperty = style.getStyleLayerProperty(layerId, TEXT_FIELD)
  if (textFieldProperty.kind != StylePropertyValueKind.EXPRESSION) {
    return
  }
  val textField = textFieldProperty.value.toJson()
  val adaptedLocale = adaptLocaleToV8orV7IfNeeded(
    style,
    style.getStyleLayerProperty(layerId, SOURCE).value.contents as? String ?: "",
    locale
  ) ?: convertedLocale
  val getExpression = get(adaptedLocale).toJson()
  val localizedTextFieldExpressionAsJson = textField.replace(
    EXPRESSION_REGEX,
    getExpression
  ).replace(EXPRESSION_ABBR_REGEX, getExpression)
  if (BuildConfig.DEBUG) {
    logI(TAG, "Localize layer with expression: $localizedTextFieldExpressionAsJson")
  }
  val expected = Value.fromJson(localizedTextFieldExpressionAsJson)
  expected.value?.let { value ->
    style.setStyleLayerProperty(
      layerId,
      TEXT_FIELD,
      value
    )
  } ?: run {
    logE(TAG, "An error ${expected.error} occurred when converting $localizedTextFieldExpressionAsJson to a Value!")
  }
}

private fun adaptLocaleToV8orV7IfNeeded(style: MapboxStyleManager, sourceId: String, locale: Locale): String? {
  if ((style.getStyleSourceProperty(sourceId, TYPE).value.contents as? String) == VECTOR) {
    (style.getStyleSourceProperty(sourceId, URL).value.contents as? String)?.let { url ->
      return if (url.contains(STREET_V8)) {
        getLanguageNameV8(locale)
      } else if (url.contains(STREET_V7)) {
        getLanguageNameV7(locale)
      } else {
        null
      }
    }
  }
  return null
}

private const val TAG = "LocalizationPluginImpl"
private const val TYPE = "type"
private const val SOURCE = "source"
private const val SYMBOL = "symbol"
private const val URL = "url"
private const val VECTOR = "vector"
private const val TEXT_FIELD = "text-field"
private const val STREET_V7 = "mapbox.mapbox-streets-v7"
private const val STREET_V8 = "mapbox.mapbox-streets-v8"
private val EXPRESSION_REGEX = Regex("\\[\"get\",\\s*\"(name_.{2,7})\"\\]")
private val EXPRESSION_ABBR_REGEX = Regex("\\[\"get\",\\s*\"abbr\"\\]")