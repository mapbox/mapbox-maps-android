package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.button.MaterialButtonToggleGroup
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.consolidatedattribution.consolidatedAttribution
import com.mapbox.maps.testapp.R

/**
 * Demonstrates the experimental consolidated attribution plugin.
 *
 * The control combines the Mapbox logo icon, wordmark, attribution text and a disclosure chevron into
 * a single animated pill. It expands on first load, auto-collapses after 5 s, and collapses
 * immediately on any map interaction.
 *
 * The plugin is not in [MapInitOptions.defaultPluginList], so it is registered explicitly below.
 *
 * Use the controls panel to toggle:
 * - **Mode**: Consolidated (pill) vs. Legacy (separate logo + "ⓘ" button)
 * - **Style**: each preset brings a different set of data providers, so the attribution list in
 *   the sheet changes with it
 * - **Position**: four corners
 * - **Collapsed style**: Icon only | Icon + wordmark
 * - **Material**: Solid | Translucent — light/dark follows the system night mode
 * - **Tint**: nudges the pill background toward a hue, in the light appearance only
 * - **Reset expanded state**: forces the pill to expand again on next style load
 */
@OptIn(MapboxExperimental::class)
class ConsolidatedAttributionActivity : AppCompatActivity() {

  private lateinit var mapContainer: FrameLayout
  private lateinit var consolidatedControls: View
  private lateinit var mapView: MapView

  private var consolidatedEnabled = true

  /**
   * Held in a field so the map rebuilt by "Reset expanded state" keeps the chosen tint, and
   * saved in the instance state so a configuration change - switching the system to dark mode
   * recreates the activity - does not silently drop it either.
   */
  private var tintColor = Color.TRANSPARENT

  /** Same reason: the rebuilt map has to come back up on the style the user picked. */
  private var mapStyle = MapStylePreset.values().first()

  /** Likewise for every pill setting the controls can change. */
  private var collapsedStyle = ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK
  private var material = ConsolidatedAttributionMaterial.SOLID
  private var position = Gravity.BOTTOM or Gravity.START

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_consolidated_attribution)

    savedInstanceState?.let(::restoreControlState)

    mapContainer = findViewById(R.id.map_container)
    consolidatedControls = findViewById(R.id.consolidated_controls)

    createMapView()
    wireControls()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putBoolean(KEY_CONSOLIDATED_ENABLED, consolidatedEnabled)
    outState.putInt(KEY_TINT_COLOR, tintColor)
    outState.putString(KEY_MAP_STYLE, mapStyle.name)
    outState.putString(KEY_COLLAPSED_STYLE, collapsedStyle.value)
    outState.putString(KEY_MATERIAL, material.value)
    outState.putInt(KEY_POSITION, position)
  }

  private fun restoreControlState(state: Bundle) {
    consolidatedEnabled = state.getBoolean(KEY_CONSOLIDATED_ENABLED, consolidatedEnabled)
    tintColor = state.getInt(KEY_TINT_COLOR, tintColor)
    mapStyle = state.getString(KEY_MAP_STYLE)
      ?.let { name -> MapStylePreset.values().firstOrNull { it.name == name } }
      ?: mapStyle
    collapsedStyle = state.getString(KEY_COLLAPSED_STYLE)?.let(::ConsolidatedAttributionCollapsedStyle) ?: collapsedStyle
    material = state.getString(KEY_MATERIAL)?.let(::ConsolidatedAttributionMaterial) ?: material
    position = state.getInt(KEY_POSITION, position)
  }

  // ── map lifecycle ─────────────────────────────────────────────────────────────────────────

  private fun createMapView() {
    mapContainer.removeAllViews()
    mapView = MapView(
      this,
      MapInitOptions(
        context = this,
        plugins = MapInitOptions.defaultPluginList +
          Plugin.Mapbox(Plugin.MAPBOX_CONSOLIDATED_ATTRIBUTION_PLUGIN_ID),
      )
    )
    mapContainer.addView(
      mapView,
      FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT,
      )
    )
    mapView.mapboxMap.apply {
      setCamera(
        CameraOptions.Builder()
          .center(Point.fromLngLat(-122.408, 37.788))
          .zoom(13.0)
          .build()
      )
      loadStyle(mapStyle.styleOrJson())
    }
    applyMode()
    mapView.consolidatedAttribution.updateSettings {
      collapsedStyle = this@ConsolidatedAttributionActivity.collapsedStyle
      material = this@ConsolidatedAttributionActivity.material
      tintColor = this@ConsolidatedAttributionActivity.tintColor
      position = this@ConsolidatedAttributionActivity.position
    }
  }

  private fun applyMode() {
    mapView.consolidatedAttribution.updateSettings { enabled = consolidatedEnabled }
  }

  // ── control wiring ────────────────────────────────────────────────────────────────────────

  private fun wireControls() {
    val modeGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggle_mode)
    val positionGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggle_position)
    val collapsedStyleGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggle_collapsed_style)
    val materialGroup = findViewById<MaterialButtonToggleGroup>(R.id.toggle_material)

    // Checked before the listeners are attached, so restoring state does not re-apply it.
    modeGroup.check(if (consolidatedEnabled) R.id.mode_consolidated else R.id.mode_legacy)
    positionGroup.check(
      when (position) {
        Gravity.BOTTOM or Gravity.END -> R.id.position_br
        Gravity.TOP or Gravity.START -> R.id.position_tl
        Gravity.TOP or Gravity.END -> R.id.position_tr
        else -> R.id.position_bl
      }
    )
    collapsedStyleGroup.check(
      when (collapsedStyle) {
        ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK -> R.id.collapsed_wordmark
        else -> R.id.collapsed_icon_only
      }
    )
    materialGroup.check(
      when (material) {
        ConsolidatedAttributionMaterial.TRANSLUCENT -> R.id.material_translucent
        else -> R.id.material_solid
      }
    )
    consolidatedControls.visibility = if (consolidatedEnabled) View.VISIBLE else View.GONE

    modeGroup.onSelection { checkedId ->
      consolidatedEnabled = checkedId != R.id.mode_legacy
      consolidatedControls.visibility = if (consolidatedEnabled) View.VISIBLE else View.GONE
      applyMode()
    }

    positionGroup.onSelection { checkedId ->
      position = when (checkedId) {
        R.id.position_br -> Gravity.BOTTOM or Gravity.END
        R.id.position_tl -> Gravity.TOP or Gravity.START
        R.id.position_tr -> Gravity.TOP or Gravity.END
        else -> Gravity.BOTTOM or Gravity.START
      }
      mapView.consolidatedAttribution.updateSettings {
        position = this@ConsolidatedAttributionActivity.position
      }
    }

    collapsedStyleGroup.onSelection { checkedId ->
      collapsedStyle = when (checkedId) {
        R.id.collapsed_wordmark -> ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK
        else -> ConsolidatedAttributionCollapsedStyle.ICON_ONLY
      }
      mapView.consolidatedAttribution.updateSettings {
        collapsedStyle = this@ConsolidatedAttributionActivity.collapsedStyle
      }
    }

    materialGroup.onSelection { checkedId ->
      material = when (checkedId) {
        R.id.material_translucent -> ConsolidatedAttributionMaterial.TRANSLUCENT
        else -> ConsolidatedAttributionMaterial.SOLID
      }
      mapView.consolidatedAttribution.updateSettings {
        this.material = this@ConsolidatedAttributionActivity.material
      }
    }

    wireMapStyleSpinner(findViewById(R.id.spinner_map_style))

    wireTintSwatches(findViewById(R.id.tint_swatches))
  }

  /** Switching style only reloads it; the map is not rebuilt, so the pill keeps its state. */
  private fun wireMapStyleSpinner(spinner: Spinner) {
    // The adapter inflates both the closed item and the dropdown rows, so it has to use the
    // spinner's themed context - the activity's would bring back AppTheme's white-on-white.
    spinner.adapter = ArrayAdapter(
      spinner.context,
      android.R.layout.simple_spinner_item,
      MapStylePreset.values().map { getString(it.titleRes) },
    ).apply {
      setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
    spinner.setSelection(MapStylePreset.values().indexOf(mapStyle))
    spinner.onItemSelected { position ->
      val selected = MapStylePreset.values()[position]
      // Spinner reports its initial selection too, and reloading the style the map already
      // has would restart the pill's intro expansion.
      if (selected != mapStyle) {
        mapStyle = selected
        mapView.mapboxMap.loadStyle(selected.styleOrJson())
      }
    }
  }

  /**
   * The SDK takes only the swatch's hue and caps how much color it keeps, so a vivid preset
   * still lands as a tinted neutral surface — and only in the light appearance, since the dark
   * pill is too near black to carry a visible tint.
   */
  private fun wireTintSwatches(row: LinearLayout) {
    val sizePx = (SWATCH_SIZE_DP * resources.displayMetrics.density).toInt()
    val marginPx = (SWATCH_MARGIN_DP * resources.displayMetrics.density).toInt()
    TINT_PRESETS.forEach { (color, label) ->
      val swatch = ImageView(this).apply {
        tag = color
        contentDescription = if (color == Color.TRANSPARENT) {
          getString(R.string.consolidated_attribution_tint_none)
        } else {
          getString(R.string.consolidated_attribution_tint_swatch, label)
        }
        layoutParams = LinearLayout.LayoutParams(sizePx, sizePx).apply {
          marginEnd = marginPx
        }
        setOnClickListener {
          tintColor = color
          mapView.consolidatedAttribution.updateSettings { tintColor = color }
          row.updateSwatchSelection()
        }
      }
      row.addView(swatch)
    }
    row.updateSwatchSelection()
  }

  /** Redraws every swatch, so the ring marks the selected preset and nothing else. */
  private fun LinearLayout.updateSwatchSelection() {
    val strokePx = (SWATCH_STROKE_DP * resources.displayMetrics.density).toInt()
    children.forEach { swatch ->
      val color = swatch.tag as Int
      swatch.background = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setColor(if (color == Color.TRANSPARENT) Color.LTGRAY else color)
        setStroke(if (color == tintColor) strokePx else strokePx / 2, Color.BLACK)
      }
    }
  }

  /** [Spinner] has no listener for "selection only", so drop the rest of [OnItemSelectedListener]. */
  private inline fun Spinner.onItemSelected(crossinline action: (Int) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        action(position)

      override fun onNothingSelected(parent: AdapterView<*>?) = Unit
    }
  }

  /** Fires only for the newly checked button, mirroring a single-selection segmented control. */
  private inline fun MaterialButtonToggleGroup.onSelection(crossinline action: (Int) -> Unit) {
    addOnButtonCheckedListener { _, checkedId, isChecked ->
      if (isChecked) action(checkedId)
    }
  }

  private companion object {
    const val KEY_CONSOLIDATED_ENABLED = "consolidatedEnabled"
    const val KEY_TINT_COLOR = "tintColor"
    const val KEY_MAP_STYLE = "mapStyle"
    const val KEY_COLLAPSED_STYLE = "collapsedStyle"
    const val KEY_MATERIAL = "material"
    const val KEY_POSITION = "position"

    const val SWATCH_SIZE_DP = 32f
    const val SWATCH_MARGIN_DP = 8f
    const val SWATCH_STROKE_DP = 2f

    /** Mirrors the iOS example's presets, with "no tint" first. */
    val TINT_PRESETS = listOf(
      Color.TRANSPARENT to "none",
      Color.parseColor("#007AFF") to "blue",
      Color.parseColor("#34C759") to "green",
      Color.parseColor("#FF9500") to "orange",
      Color.parseColor("#FF2D55") to "pink",
      Color.parseColor("#AF52DE") to "purple",
    )
  }
}
/**
 * Style presets for the example's picker, mirroring the iOS example's list.
 *
 * The first seven are Mapbox styles; the last two are style JSON built here, to cover attribution
 * cases the stock styles never produce.
 */
private enum class MapStylePreset(@StringRes val titleRes: Int, private val uri: String? = null) {
  STANDARD(R.string.consolidated_attribution_map_style_standard, Style.STANDARD),
  STANDARD_SATELLITE(R.string.consolidated_attribution_map_style_standard_satellite, Style.STANDARD_SATELLITE),
  LIGHT(R.string.consolidated_attribution_map_style_light, Style.LIGHT),
  DARK(R.string.consolidated_attribution_map_style_dark, Style.DARK),
  STREETS(R.string.consolidated_attribution_map_style_streets, Style.MAPBOX_STREETS),
  SATELLITE_STREETS(R.string.consolidated_attribution_map_style_satellite_streets, Style.SATELLITE_STREETS),
  OUTDOORS(R.string.consolidated_attribution_map_style_outdoors, Style.OUTDOORS),

  /**
   * Long list, to check how the sheet handles overflow. Also covers what the pill filters out:
   * "Mapbox" (the wordmark already covers it) and "Improve this map" (a feedback link, not a
   * copyright), plus a provider with no link at all.
   */
  MANY_ATTRIBUTIONS(R.string.consolidated_attribution_map_style_many),

  /** A single non-actionable attribution: nothing in the list is tappable. */
  ONE_ATTRIBUTION(R.string.consolidated_attribution_map_style_one),
  ;

  /** Either the style URI or the style JSON; `loadStyle` accepts both. */
  fun styleOrJson(): String = uri ?: when (this) {
    MANY_ATTRIBUTIONS -> attributionStyleJson(MANY_ATTRIBUTIONS_PROVIDERS)
    else -> attributionStyleJson(listOf("Not linked attribution"))
  }

  private companion object {
    val MANY_ATTRIBUTIONS_PROVIDERS = listOf(
      """<a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>""",
      """<a href="https://www.mapbox.com/about/maps/">Mapbox</a>""",
      """<a href="https://www.mapbox.com/feedback/">Improve this map</a>""",
      """<a href="https://www.maxar.com/">Maxar</a>""",
      """<a href="https://www.naturalearthdata.com/">Natural Earth</a>""",
      """<a href="https://www.usgs.gov/">U.S. Geological Survey</a>""",
      """<a href="https://www.noaa.gov/">NOAA</a>""",
      """<a href="https://www.eea.europa.eu/">European Environment Agency</a>""",
      """<a href="https://datasf.org/opendata/">City and County of San Francisco Open Data</a>""",
      "NoLinkProvider",
    )

    /** One empty GeoJSON source per attribution, imported over Standard so the map still draws. */
    fun attributionStyleJson(attributions: List<String>): String {
      val sources = attributions.mapIndexed { index, attribution ->
        """
        "attribution-source-$index": {
          "type": "geojson",
          "attribution": "${attribution.replace("\"", "\\\"")}",
          "data": { "type": "FeatureCollection", "features": [] }
        }
        """.trimIndent()
      }.joinToString(",\n")

      return """
      {
        "version": 8,
        "imports": [{ "id": "basemap", "url": "mapbox://styles/mapbox/standard" }],
        "sources": { $sources },
        "layers": []
      }
      """.trimIndent()
    }
  }
}