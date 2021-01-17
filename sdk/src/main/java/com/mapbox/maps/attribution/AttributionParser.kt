package com.mapbox.maps.attribution

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.URLSpan
import com.mapbox.maps.base.R
import com.mapbox.maps.plugin.attribution.Attribution
import java.lang.ref.WeakReference
import java.util.*

/**
 * Responsible for parsing attribution data coming from Sources and MapSnapshot.
 *
 *
 * Exposes multiple configuration options to manipulate data being parsed.
 * Use the Options object to build these configurations.
 *
 */
open class AttributionParser internal constructor(
  private val context: WeakReference<Context>,
  private val attributionData: String,
  private val withImproveMap: Boolean,
  private val withCopyrightSign: Boolean,
  private val withTelemetryAttribution: Boolean,
  private val withMapboxAttribution: Boolean
) {
  private val attributions: MutableSet<Attribution> = LinkedHashSet()

  /**
   * Get parsed attributions.
   *
   * @return the attributions
   */
  fun getAttributions(): Set<Attribution> {
    return attributions
  }

  /**
   * Get parsed attribution string.
   *
   * @param shortenedOutput if attribution string should contain shortened output
   * @return the parsed attribution string
   */
  @JvmOverloads
  fun createAttributionString(shortenedOutput: Boolean = false): String {
    if (attributions.isEmpty()) {
      return EMPTY_STRING
    }

    val stringBuilder = StringBuilder(if (withCopyrightSign) EMPTY_STRING else COPYRIGHT)
    var counter = 0
    for (attribution in attributions) {
      counter++
      stringBuilder.append(if (!shortenedOutput) attribution.title else attribution.titleAbbreviated)
      if (counter != attributions.size) {
        stringBuilder.append(" / ")
      }
    }
    return stringBuilder.toString()
  }

  /**
   * Main attribution for configuration
   */
  protected fun parse() {
    parseAttributions()
    addAdditionalAttributions()
  }

  /**
   * Parse attributions
   */
  private fun parseAttributions() {
    val htmlBuilder =
      fromHtml(
        attributionData
      ) as SpannableStringBuilder
    val urlSpans =
      htmlBuilder.getSpans(0, htmlBuilder.length, URLSpan::class.java)
    for (urlSpan in urlSpans) {
      parseUrlSpan(htmlBuilder, urlSpan)
    }
  }

  /**
   * Parse an URLSpan containing an attribution.
   *
   * @param htmlBuilder the html builder
   * @param urlSpan the url span to be parsed
   */
  private fun parseUrlSpan(htmlBuilder: SpannableStringBuilder, urlSpan: URLSpan) {
    val url = urlSpan.url
    if (isUrlValid(url)) {
      var anchor = parseAnchorValue(htmlBuilder, urlSpan)
      if (isImproveThisMapAnchor(anchor)) {
        anchor = translateImproveThisMapAnchor(anchor)
      }
      attributions.add(
        Attribution(
          anchor,
          url
        )
      )
    }
  }

  /**
   * Invoked to validate if an url is valid to be included in the final attribution.
   *
   * @param url the url to be validated
   * @return if the url is valid
   */
  private fun isUrlValid(url: String): Boolean {
    return isValidForImproveThisMap(url) && isValidForMapbox(url)
  }

  /**
   * Invoked to validate if an anchor is equal to Improve this map coming from tilesets.
   *
   * @param anchor the anchor to be validated
   * @return if the url is valid
   */
  private fun isImproveThisMapAnchor(anchor: String): Boolean {
    return anchor == IMPROVE_THIS_MAP
  }

  /**
   * Invoked to replace the english Improve this map with localized variant.
   *
   * @param anchorString the anchor to be translated
   * @return the translated anchor
   */
  private fun translateImproveThisMapAnchor(anchorString: String): String {
    var anchor = anchorString
    val context = context.get()
    if (context != null) {
      anchor = context.getString(R.string.mapbox_telemetryImproveMap)
    }
    return anchor
  }

  /**
   * Invoked to validate if an url is valid for the improve map configuration.
   *
   * @param url the url to be validated
   * @return if the url is valid for improve this map
   */
  private fun isValidForImproveThisMap(url: String): Boolean {
    return withImproveMap || !IMPROVE_MAP_URLS.contains(url)
  }

  /**
   * Invoked to validate if an url is valid for the Mapbox configuration.
   *
   * @param url the url to be validated
   * @return if the url is valid for Mapbox
   */
  private fun isValidForMapbox(url: String): Boolean {
    return withMapboxAttribution || url != Attribution.ABOUT_MAPS_URL
  }

  /**
   * Parse the attribution by parsing the anchor value of html href tag.
   *
   * @param htmlBuilder the html builder
   * @param urlSpan the current urlSpan
   * @return the parsed anchor value
   */
  private fun parseAnchorValue(
    htmlBuilder: SpannableStringBuilder,
    urlSpan: URLSpan
  ): String {
    val start = htmlBuilder.getSpanStart(urlSpan)
    val end = htmlBuilder.getSpanEnd(urlSpan)
    val length = end - start
    val charKey = CharArray(length)
    htmlBuilder.getChars(start, end, charKey, 0)
    return stripCopyright(String(charKey))
  }

  /**
   * Utility to strip the copyright sign from an attribution
   *
   * @param anchorString the attribution string to strip
   * @return the stripped attribution string without the copyright sign
   */
  private fun stripCopyright(anchorString: String): String {
    var anchor = anchorString
    if (!withCopyrightSign && anchor.startsWith(COPYRIGHT)) {
      anchor = anchor.substring(2, anchor.length)
    }
    return anchor
  }

  /**
   * Invoked to manually add attributions
   */
  private fun addAdditionalAttributions() {
    if (withTelemetryAttribution) {
      val context = context.get()
      attributions.add(
        Attribution(
          if (context != null) context.getString(R.string.mapbox_telemetrySettings) else Attribution.TELEMETRY_SETTINGS,
          Attribution.ABOUT_TELEMETRY_URL
        )
      )
    }
  }

  /**
   * Static variables and methods.
   */
  companion object {
    private const val EMPTY_STRING = ""
    private const val COPYRIGHT = "© "
    private const val IMPROVE_THIS_MAP = "Improve this map"

    /**
     * List of improve map URLs.
     */
    val IMPROVE_MAP_URLS: MutableList<String> = ArrayList()

    /**
     * Convert a string to a spanned html representation.
     *
     * @param html the string to convert
     * @return the spanned html representation
     */
    private fun fromHtml(html: String): Spanned {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
      } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(html)
      }
    }

    init {
      // Using a List makes URL backwards compatible
      IMPROVE_MAP_URLS.add("https://www.mapbox.com/feedback/")
      IMPROVE_MAP_URLS.add("https://www.mapbox.com/map-feedback/")
      IMPROVE_MAP_URLS.add("https://apps.mapbox.com/feedback/")
    }
  }

  /**
   * Builder to configure using an AttributionParser.
   *
   *
   * AttributionData, set with [.withAttributionData], is the only required property to build
   * the underlying AttributionParser. Other properties include trimming the copyright sign, adding telemetry
   * attribution or hiding attribution as improve this map and Mapbox.
   *
   */
  class Options(context: Context) {
    private val context: WeakReference<Context> = WeakReference(context)
    private var withImproveMap = true
    private var withCopyrightSign = true
    private var withTelemetryAttribution = false
    private var withMapboxAttribution = true
    private var attributionDataStringArray: Array<String>? = null

    /**
     * Adds attribution data to the attribution parser builder
     */
    fun withAttributionData(vararg attributionData: String): Options {
      attributionDataStringArray = arrayOf(*attributionData)
      return this
    }

    /**
     * Flag indicating to add improve this map to the attribution dialog
     */
    fun withImproveMap(withImproveMap: Boolean): Options {
      this.withImproveMap = withImproveMap
      return this
    }

    /**
     * Flag indicating to add the copyright sign
     */
    fun withCopyrightSign(withCopyrightSign: Boolean): Options {
      this.withCopyrightSign = withCopyrightSign
      return this
    }

    /**
     * Flag indicating to add telemetry attribution
     */
    fun withTelemetryAttribution(withTelemetryAttribution: Boolean): Options {
      this.withTelemetryAttribution = withTelemetryAttribution
      return this
    }

    /**
     * Flag indicating to add Mapbox attribution
     */
    fun withMapboxAttribution(withMapboxAttribution: Boolean): Options {
      this.withMapboxAttribution = withMapboxAttribution
      return this
    }

    /**
     * Build the attribution parser
     */
    fun build(): AttributionParser {
      checkNotNull(attributionDataStringArray) { "Using builder without providing attribution data" }
      val fullAttributionString = parseAttribution(attributionDataStringArray!!)
      val attributionParser =
        AttributionParser(
          context,
          fullAttributionString,
          withImproveMap,
          withCopyrightSign,
          withTelemetryAttribution,
          withMapboxAttribution
        )
      attributionParser.parse()
      return attributionParser
    }

    private fun parseAttribution(attribution: Array<String>): String {
      val builder = StringBuilder()
      for (attr in attribution) {
        if (attr.isNotEmpty()) {
          builder.append(attr)
        }
      }
      return builder.toString()
    }
  }
}