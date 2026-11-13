package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.GenericStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionCollapsedStyle
import com.mapbox.maps.plugin.ConsolidatedAttributionIntroExpansion
import com.mapbox.maps.plugin.ConsolidatedAttributionMaterial

/**
 * Demonstrates the experimental consolidated attribution ornament in a Compose map.
 *
 * The control combines the Mapbox logo, the attribution text and a disclosure chevron into a single
 * animated pill. It expands on first load, auto-collapses after 5 s, and collapses immediately on
 * any map interaction.
 *
 * It stands in for both the attribution and the logo ornaments, so the legacy slots are left empty
 * while it is on - which is all the opt-in takes in Compose:
 * ```
 * MapboxMap(
 *   attribution = { ConsolidatedAttribution() },
 *   logo = {},
 * )
 * ```
 *
 * Use the controls panel to change position, collapsed style, material, tint and the intro
 * expansion, to switch back to the legacy logo + attribution ornaments, and to pick a style - each
 * preset brings a different set of data providers, so the pill's label changes with it.
 */
@OptIn(MapboxExperimental::class)
public class ConsolidatedAttributionActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var consolidated by rememberSaveable { mutableStateOf(true) }
      var stylePreset by rememberSaveable { mutableStateOf(MapStylePreset.STANDARD) }
      var position by rememberSaveable { mutableStateOf(PillPosition.BOTTOM_START) }
      var collapsedStyle by rememberSaveable {
        mutableStateOf(ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK)
      }
      var material by rememberSaveable { mutableStateOf(ConsolidatedAttributionMaterial.SOLID) }
      var tint by rememberSaveable { mutableStateOf(TintPreset.NONE) }
      var introExpansion by rememberSaveable {
        mutableStateOf(ConsolidatedAttributionIntroExpansion.ONCE)
      }

      MapboxMapComposeTheme {
        ExampleScaffold(
          bottomBar = {
            ControlsPanel(
              consolidated = consolidated,
              onConsolidatedChange = { consolidated = it },
              stylePreset = stylePreset,
              onStylePresetChange = { stylePreset = it },
              position = position,
              onPositionChange = { position = it },
              collapsedStyle = collapsedStyle,
              onCollapsedStyleChange = { collapsedStyle = it },
              material = material,
              onMaterialChange = { material = it },
              tint = tint,
              onTintChange = { tint = it },
              introExpansion = introExpansion,
              onIntroExpansionChange = { introExpansion = it },
            )
          }
        ) { scaffoldPadding ->
          MapboxMap(
            modifier = Modifier.fillMaxSize(),
            attribution = {
              if (consolidated) {
                // The scaffold padding keeps the control clear of the controls panel, exactly
                // as it does for the other ornaments.
                ConsolidatedAttribution(
                  modifier = Modifier.padding(scaffoldPadding),
                  alignment = position.alignment,
                  collapsedStyle = collapsedStyle,
                  material = material,
                  tintColor = tint.color,
                  introExpansion = introExpansion,
                )
              } else {
                Attribution(modifier = Modifier.padding(scaffoldPadding))
              }
            },
            // The consolidated control already carries the wordmark, so the separate logo
            // ornament would duplicate it.
            logo = { if (!consolidated) Logo(modifier = Modifier.padding(scaffoldPadding)) },
            mapViewportState = rememberMapViewportState {
              setCameraOptions {
                center(SAN_FRANCISCO)
                zoom(ZOOM)
              }
            },
            // Remembered per preset: styleOrJson() builds the JSON presets from scratch, and a
            // fresh string on every recomposition makes the style node reload the same style -
            // which fails with "import is already part of the currently used style" and leaves
            // the map black.
            style = {
              val styleOrJson = remember(stylePreset) { stylePreset.styleOrJson() }
              GenericStyle(style = styleOrJson)
            },
          )
        }
      }
    }
  }

  private companion object {
    val SAN_FRANCISCO: Point = Point.fromLngLat(-122.408, 37.788)
    const val ZOOM = 13.0
  }
}

/** The four corners the control supports, as compose [Alignment]s. */
private enum class PillPosition(val label: String, val alignment: Alignment) {
  BOTTOM_START("↙", Alignment.BottomStart),
  BOTTOM_END("↘", Alignment.BottomEnd),
  TOP_START("↖", Alignment.TopStart),
  TOP_END("↗", Alignment.TopEnd),
}

/**
 * Mirrors the iOS example's presets, with "no tint" first.
 *
 * The SDK takes only the hue and caps how much color it keeps, so a vivid preset still lands as a
 * tinted neutral surface - and only in the light appearance, since the dark pill is too near black
 * to carry a visible tint.
 */
private enum class TintPreset(val color: Color) {
  NONE(Color.Transparent),
  BLUE(Color(0xFF007AFF)),
  GREEN(Color(0xFF34C759)),
  ORANGE(Color(0xFFFF9500)),
  PINK(Color(0xFFFF2D55)),
  PURPLE(Color(0xFFAF52DE)),
}

/**
 * Style presets for the example's picker, mirroring the iOS example's list.
 *
 * The first seven are Mapbox styles; the last two are style JSON built here, to cover attribution
 * cases the stock styles never produce.
 */
private enum class MapStylePreset(val title: String, private val uri: String? = null) {
  STANDARD("Standard", Style.STANDARD),
  STANDARD_SATELLITE("Standard Satellite", Style.STANDARD_SATELLITE),
  LIGHT("Light", Style.LIGHT),
  DARK("Dark", Style.DARK),
  STREETS("Streets", Style.MAPBOX_STREETS),
  SATELLITE_STREETS("Satellite Streets", Style.SATELLITE_STREETS),
  OUTDOORS("Outdoors", Style.OUTDOORS),

  /**
   * Long list, to check how the pill truncates and how the sheet handles overflow. Also covers
   * what the pill filters out: "Mapbox" (the wordmark already covers it) and "Improve this map"
   * (a feedback link, not a copyright), plus a provider with no link at all.
   */
  MANY_ATTRIBUTIONS("Many attributions"),

  /** A single non-actionable attribution: nothing in the sheet is tappable. */
  ONE_ATTRIBUTION("One attribution (no link)"),
  ;

  /** Either the style URI or the style JSON; the style composable accepts both. */
  fun styleOrJson(): String = uri ?: attributionStyleJson(providers)

  /** Providers for the presets built from style JSON rather than a URI. */
  private val providers: List<String>
    get() = when (this) {
      MANY_ATTRIBUTIONS -> MANY_ATTRIBUTIONS_PROVIDERS
      ONE_ATTRIBUTION -> listOf("Not linked attribution")
      // Every URI-backed preset returns before reaching here.
      else -> emptyList()
    }

  private companion object {
    val MANY_ATTRIBUTIONS_PROVIDERS = listOf(
      """<a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>""",
      """<a href="https://www.mapbox.com/about/maps/">Mapbox</a>""",
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

@Composable
private fun ControlsPanel(
  consolidated: Boolean,
  onConsolidatedChange: (Boolean) -> Unit,
  stylePreset: MapStylePreset,
  onStylePresetChange: (MapStylePreset) -> Unit,
  position: PillPosition,
  onPositionChange: (PillPosition) -> Unit,
  collapsedStyle: ConsolidatedAttributionCollapsedStyle,
  onCollapsedStyleChange: (ConsolidatedAttributionCollapsedStyle) -> Unit,
  material: ConsolidatedAttributionMaterial,
  onMaterialChange: (ConsolidatedAttributionMaterial) -> Unit,
  tint: TintPreset,
  onTintChange: (TintPreset) -> Unit,
  introExpansion: ConsolidatedAttributionIntroExpansion,
  onIntroExpansionChange: (ConsolidatedAttributionIntroExpansion) -> Unit,
) {
  Surface(elevation = 8.dp) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        // No height of its own: the panel is dense enough to ask only for what it needs, and the
        // map takes the rest. The scroll is a landscape guard, not the normal path.
        .verticalScroll(rememberScrollState())
        // The app is edge-to-edge, so the last row would otherwise sit under the gesture handle.
        .navigationBarsPadding()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      ChipRow(
        label = "Mode",
        options = listOf(false to "Legacy", true to "Consolidated"),
        selected = consolidated,
        onSelect = onConsolidatedChange,
      )

      StylePicker(stylePreset, onStylePresetChange)

      if (consolidated) {
        ChipRow(
          label = "Position",
          options = PillPosition.values().map { it to it.label },
          selected = position,
          onSelect = onPositionChange,
        )
        ChipRow(
          label = "Collapsed",
          options = listOf(
            ConsolidatedAttributionCollapsedStyle.ICON_ONLY to "Icon",
            ConsolidatedAttributionCollapsedStyle.ICON_AND_WORDMARK to "Icon + wordmark",
          ),
          selected = collapsedStyle,
          onSelect = onCollapsedStyleChange,
        )
        ChipRow(
          label = "Material",
          options = listOf(
            ConsolidatedAttributionMaterial.SOLID to "Solid",
            ConsolidatedAttributionMaterial.TRANSLUCENT to "Translucent",
          ),
          selected = material,
          onSelect = onMaterialChange,
        )
        TintRow(tint, onTintChange)
        ChipRow(
          label = "Intro",
          options = listOf(
            ConsolidatedAttributionIntroExpansion.ONCE to "Once",
            ConsolidatedAttributionIntroExpansion.EVERY_LAUNCH to "Every launch",
          ),
          selected = introExpansion,
          onSelect = onIntroExpansionChange,
        )
      }
    }
  }
}

/** Width shared by every row label, so the chips line up in one column. */
private val LABEL_WIDTH = 88.dp
private val CONTROL_HEIGHT = 36.dp

@Composable
private fun RowLabel(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.body2,
    modifier = Modifier.width(LABEL_WIDTH),
  )
}

/**
 * A compact tap target standing in for iOS's segmented picker: small enough that the whole panel
 * fits in a few rows, leaving the map the rest of the screen.
 */
@Composable
private fun Chip(text: String, selected: Boolean, onClick: () -> Unit) {
  Surface(
    shape = RoundedCornerShape(CONTROL_HEIGHT / 2),
    color = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
    border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
    modifier = Modifier
      .height(CONTROL_HEIGHT)
      .clickable(onClick = onClick),
  ) {
    Box(
      modifier = Modifier.padding(horizontal = 14.dp),
      contentAlignment = Alignment.Center,
    ) {
      Text(
        text = text,
        fontSize = 14.sp,
        maxLines = 1,
        color = if (selected) {
          MaterialTheme.colors.onPrimary
        } else {
          MaterialTheme.colors.onSurface
        },
      )
    }
  }
}

/** A labelled row of mutually exclusive [Chip]s. */
@Composable
private fun <T> ChipRow(
  label: String,
  options: List<Pair<T, String>>,
  selected: T,
  onSelect: (T) -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    RowLabel(label)
    options.forEach { (value, text) ->
      Chip(text = text, selected = value == selected) { onSelect(value) }
    }
  }
}

@Composable
private fun StylePicker(selected: MapStylePreset, onSelect: (MapStylePreset) -> Unit) {
  var expanded by remember { mutableStateOf(false) }
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    RowLabel("Style")
    Chip(text = selected.title, selected = false) { expanded = true }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      MapStylePreset.values().forEach { preset ->
        DropdownMenuItem(
          onClick = {
            expanded = false
            onSelect(preset)
          }
        ) {
          Text(text = preset.title)
        }
      }
    }
  }
}

@Composable
private fun TintRow(selected: TintPreset, onSelect: (TintPreset) -> Unit) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    RowLabel("Tint")
    TintPreset.values().forEach { preset ->
        Spacer(
          modifier = Modifier
            .size(CONTROL_HEIGHT)
            .background(
              // "No tint" has no color of its own to show, so it gets a neutral swatch.
              color = if (preset == TintPreset.NONE) Color.LightGray else preset.color,
              shape = CircleShape,
            )
            .border(
              width = if (preset == selected) 3.dp else 1.dp,
              color = MaterialTheme.colors.onSurface,
              shape = CircleShape,
            )
            .clickable { onSelect(preset) }
        )
    }
  }
}