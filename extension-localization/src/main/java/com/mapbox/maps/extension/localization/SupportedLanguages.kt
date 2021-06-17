package com.mapbox.maps.extension.localization

import java.util.*

/**
 * The name (or names) used locally for the place.
 */
internal const val NAME = "name"

/**
 * English (if available)
 */
internal const val NAME_EN = "name_en"

/**
 * French (if available)
 */
internal const val NAME_FR = "name_fr"

/**
 * Arabic (if available)
 */
internal const val NAME_AR = "name_ar"

/**
 * Spanish (if available)
 */
internal const val NAME_ES = "name_es"

/**
 * German (if available)
 */
internal const val NAME_DE = "name_de"

/**
 * Portuguese (if available)
 */
internal const val NAME_PT = "name_pt"

/**
 * Russian (if available)
 */
internal const val NAME_RU = "name_ru"

/**
 * Chinese (if available)
 */
internal const val NAME_ZH = "name_zh"

/**
 * Traditional Chinese (if available)
 */
internal const val NAME_ZH_HANT = "name_zh-Hant"

/**
 * Simplified Chinese (if available)
 */
internal const val NAME_ZH_HANS = "name_zh-Hans"

/**
 * Japanese (if available)
 */
internal const val NAME_JA = "name_ja"

/**
 * Korean (if available)
 */
internal const val NAME_KO = "name_ko"

/**
 * Vietnamese (if available)
 */
internal const val NAME_VI = "name_vi"

/**
 * Italy (if available)
 */
internal const val NAME_IT = "name_it"

/**
 * https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v7/#name-fields
 */
private val supportedV7 =
  arrayOf(
    NAME,
    NAME_AR,
    NAME_EN,
    NAME_ES,
    NAME_FR,
    NAME_DE,
    NAME_PT,
    NAME_RU,
    NAME_JA,
    NAME_KO,
    NAME_ZH,
    NAME_ZH_HANS
  )

/**
 * https://docs.mapbox.com/vector-tiles/reference/mapbox-streets-v8/#common-fields
 */
private val supportedV8 =
  arrayOf(
    NAME,
    NAME_AR,
    NAME_EN,
    NAME_ES,
    NAME_FR,
    NAME_DE,
    NAME_IT,
    NAME_PT,
    NAME_RU,
    NAME_JA,
    NAME_KO,
    NAME_ZH_HANS,
    NAME_ZH_HANT,
    NAME_VI
  )

/**
 * Get the language name for v7, fallback to [NAME] if not supported.
 */
internal fun getLanguageNameV7(locale: Locale): String {
  if (locale.language.startsWith("zh")) {
    return if (locale == Locale.SIMPLIFIED_CHINESE || locale.script == "Hans") NAME_ZH_HANS
    else NAME_ZH
  }
  "name_${locale.language}".apply { return if (supportedV7.contains(this)) this else NAME }
}

/**
 * Get the language name for v8, fallback to [NAME] if not supported.
 */
internal fun getLanguageNameV8(locale: Locale): String {
  if (locale.language.startsWith("zh")) {
    return if (locale == Locale.TAIWAN || locale.script == "Hant") NAME_ZH_HANT
    else NAME_ZH_HANS
  }
  "name_${locale.language}".apply { return if (supportedV8.contains(this)) this else NAME }
}