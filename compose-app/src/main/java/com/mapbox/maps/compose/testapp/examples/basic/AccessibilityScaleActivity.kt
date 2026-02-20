package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.SymbolScaleBehavior
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import kotlinx.coroutines.delay

/**
 * Example demonstrating accessibility scaling for map symbols.
 *
 * This example shows:
 * - **Scale Factor**: Controls ALL symbols in the map (system labels + custom annotations)
 *   - Fixed: Manual control with slider
 *   - System: Automatic scaling based on system font size
 *   - Custom: System scaling with custom mapping
 * - **Icon/Text Size Scale Range**: Only affects the custom annotations (shown in blue)
 */
@OptIn(MapboxExperimental::class)
public class AccessibilityScaleActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          AccessibilityScaleExample()
        }
      }
    }
  }
}

private enum class ScaleMode(val displayName: String) {
  FIXED("Fixed"),
  SYSTEM("System"),
  CUSTOM("Custom")
}

private data class Location(val name: String, val lat: Double, val lon: Double)

private val sampleLocations = listOf(
  Location("Harlem", 40.8116, -73.9465),
  Location("Upper West Side", 40.7870, -73.9754),
  Location("Midtown", 40.7549, -73.9840)
)

@OptIn(MapboxExperimental::class, ExperimentalMaterialApi::class)
@Composable
private fun AccessibilityScaleExample() {
  var scaleMode by remember { mutableStateOf(ScaleMode.FIXED) }
  var scaleFactor by remember { mutableStateOf(1.0f) }
  var iconSizeMin by remember { mutableStateOf(0.8) }
  var iconSizeMax by remember { mutableStateOf(2.0) }
  var textSizeMin by remember { mutableStateOf(0.8) }
  var textSizeMax by remember { mutableStateOf(2.0) }
  var showInfoDialog by remember { mutableStateOf(false) }

  // Debounce scaleFactor and range sliders to avoid flooding the map renderer on every slider tick
  var debouncedScaleFactor by remember { mutableStateOf(scaleFactor) }
  LaunchedEffect(scaleFactor) { delay(150L); debouncedScaleFactor = scaleFactor }

  var debouncedIconSizeMin by remember { mutableStateOf(iconSizeMin) }
  var debouncedIconSizeMax by remember { mutableStateOf(iconSizeMax) }
  var debouncedTextSizeMin by remember { mutableStateOf(textSizeMin) }
  var debouncedTextSizeMax by remember { mutableStateOf(textSizeMax) }
  LaunchedEffect(iconSizeMin) { delay(150L); debouncedIconSizeMin = iconSizeMin }
  LaunchedEffect(iconSizeMax) { delay(150L); debouncedIconSizeMax = iconSizeMax }
  LaunchedEffect(textSizeMin) { delay(150L); debouncedTextSizeMin = textSizeMin }
  LaunchedEffect(textSizeMax) { delay(150L); debouncedTextSizeMax = textSizeMax }

  Box(Modifier.fillMaxSize()) {
    MapboxMap(
      Modifier.fillMaxSize(),
      mapViewportState = rememberMapViewportState {
        setCameraOptions {
          center(Point.fromLngLat(-73.9680, 40.7489))
          zoom(11.5)
        }
      },
      style = { MapboxStandardStyle() }
    ) {
      // Create bitmap from drawable for use in PointAnnotationOptions
      val context = LocalContext.current
      val iconBitmap = remember {
        val drawable = requireNotNull(
          androidx.core.content.ContextCompat.getDrawable(context, R.drawable.ic_blue_marker)
        ) { "ic_blue_marker drawable not found" }
        val bitmap = android.graphics.Bitmap.createBitmap(
          drawable.intrinsicWidth,
          drawable.intrinsicHeight,
          android.graphics.Bitmap.Config.ARGB_8888
        )
        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
      }

      // Create annotations with icon set on each
      val annotations = remember(iconBitmap) {
        sampleLocations.map { (name, lat, lon) ->
          PointAnnotationOptions()
            .withPoint(Point.fromLngLat(lon, lat))
            .withIconImage(iconBitmap)
            .withIconAnchor(IconAnchor.BOTTOM)
            .withTextField(name)
            .withTextSize(16.0)
            .withTextOffset(listOf(0.0, 0.3))
            .withTextAnchor(TextAnchor.TOP)
            .withTextColor(android.graphics.Color.parseColor("#007AFF"))
            .withTextHaloColor(android.graphics.Color.WHITE)
            .withTextHaloWidth(2.0)
        }
      }

      PointAnnotationGroup(
        annotations = annotations
      ) {
        // Set group-level properties (scale ranges work here)
        iconSize = 1.5
        iconAllowOverlap = true
        textAllowOverlap = true
        iconSizeScaleRange = listOf(debouncedIconSizeMin, debouncedIconSizeMax)
        textSizeScaleRange = listOf(debouncedTextSizeMin, debouncedTextSizeMax)
      }

      // Update scale behavior when mode or debounced factor changes
      MapEffect(scaleMode, debouncedScaleFactor) { mapView ->
        applyScaleBehavior(mapView.mapboxMap, scaleMode, debouncedScaleFactor)
      }
    }

    // Info button in top-right corner
    IconButton(
      onClick = { showInfoDialog = true },
      modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(16.dp)
        .shadow(2.dp, CircleShape)
        .background(Color.White, CircleShape)
    ) {
      Icon(
        Icons.Default.Info,
        contentDescription = "Information",
        tint = Color(0xFF2196F3) // Blue to match theme
      )
    }

    // Settings Panel at bottom
    Surface(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 30.dp),
      shape = RoundedCornerShape(10.dp),
      elevation = 8.dp,
    ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          // Scale Factor Section
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              "Scale Factor (all symbols)",
              style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
            )

            // Mode picker
            Row(
              horizontalArrangement = Arrangement.spacedBy(4.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              for (mode in ScaleMode.values()) {
                Surface(
                  modifier = Modifier.weight(1f),
                  shape = RoundedCornerShape(8.dp),
                  color = if (scaleMode == mode) Color(0xFF2196F3) else Color(0xFFE0E0E0),
                  onClick = { scaleMode = mode }
                ) {
                  Text(
                    mode.displayName,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.button.copy(
                      color = if (scaleMode == mode) Color.White else Color.Black,
                      fontSize = 12.sp
                    ),
                    textAlign = TextAlign.Center
                  )
                }
              }
            }

            // Mode-specific content
            when (scaleMode) {
              ScaleMode.FIXED -> {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text("Scale:", style = MaterialTheme.typography.caption)
                  Text("%.1f".format(scaleFactor), style = MaterialTheme.typography.caption)
                }
                Slider(
                  value = scaleFactor,
                  onValueChange = { scaleFactor = it },
                  valueRange = 0.5f..3.0f,
                  steps = 24
                )
              }
              ScaleMode.SYSTEM -> {
                Text(
                  "Automatic scaling from Settings → Accessibility",
                  style = MaterialTheme.typography.caption,
                  color = Color.Gray
                )
              }
              ScaleMode.CUSTOM -> {
                Text(
                  "Custom mapping: dampens large accessibility scales",
                  style = MaterialTheme.typography.caption,
                  color = Color.Gray
                )
              }
            }
          }

          Divider()

          // Scale Ranges Section
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              "Scale Ranges (custom annotations)",
              style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
            )

            // Icon Size Scale Range
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Icon Size Range", style = MaterialTheme.typography.caption)
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                  Text("Min: %.1f".format(iconSizeMin), style = MaterialTheme.typography.caption)
                  Slider(
                    value = iconSizeMin.toFloat(),
                    onValueChange = { iconSizeMin = it.toDouble().coerceAtMost(iconSizeMax) },
                    valueRange = 0.1f..5.0f,
                    steps = 48
                  )
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                  Text("Max: %.1f".format(iconSizeMax), style = MaterialTheme.typography.caption)
                  Slider(
                    value = iconSizeMax.toFloat(),
                    onValueChange = { iconSizeMax = it.toDouble().coerceAtLeast(iconSizeMin) },
                    valueRange = 0.1f..5.0f,
                    steps = 48
                  )
                }
              }
            }

            // Text Size Scale Range
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text("Text Size Range", style = MaterialTheme.typography.caption)
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                  Text("Min: %.1f".format(textSizeMin), style = MaterialTheme.typography.caption)
                  Slider(
                    value = textSizeMin.toFloat(),
                    onValueChange = { textSizeMin = it.toDouble().coerceAtMost(textSizeMax) },
                    valueRange = 0.1f..5.0f,
                    steps = 48
                  )
                }
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                  Text("Max: %.1f".format(textSizeMax), style = MaterialTheme.typography.caption)
                  Slider(
                    value = textSizeMax.toFloat(),
                    onValueChange = { textSizeMax = it.toDouble().coerceAtLeast(textSizeMin) },
                    valueRange = 0.1f..5.0f,
                    steps = 48
                  )
                }
              }
            }
          }
        }
      }
    }

    // Info dialog
    if (showInfoDialog) {
      AccessibilityScaleInfoDialog(onDismiss = { showInfoDialog = false })
    }
  }

@OptIn(MapboxExperimental::class)
private fun applyScaleBehavior(
  mapboxMap: MapboxMap,
  mode: ScaleMode,
  scaleFactor: Float
) {
  mapboxMap.symbolScaleBehavior = when (mode) {
    ScaleMode.FIXED -> SymbolScaleBehavior.fixed(scaleFactor)
    ScaleMode.SYSTEM -> SymbolScaleBehavior.system
    ScaleMode.CUSTOM -> SymbolScaleBehavior.system { systemFontScale ->
      when {
        systemFontScale < 1.0f -> systemFontScale * 1.1f // Boost small scales by 10%
        systemFontScale <= 1.3f -> systemFontScale // Keep medium scales unchanged
        else -> 1.3f + (systemFontScale - 1.3f) * 0.4f // Dampen large scales
      }
    }
  }
}

@Composable
private fun AccessibilityScaleInfoDialog(onDismiss: () -> Unit) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Accessibility Scale Example", style = MaterialTheme.typography.h6) },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          "This example demonstrates how to control symbol scaling for accessibility using the Maps SDK.",
          style = MaterialTheme.typography.body2
        )

        Spacer(Modifier.height(4.dp))

        Text("Scale Factor", style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold))
        Text(
          "Adjusts the global scale factor for all symbol layers on the map using the symbolScaleBehavior property. This affects both system map labels and custom annotations.",
          style = MaterialTheme.typography.body2
        )
        Text("• Fixed: Manual control with a slider", style = MaterialTheme.typography.body2)
        Text("• System: Automatically scales based on the device's font scale (Settings → Accessibility → Font Size and Text Scaling)", style = MaterialTheme.typography.body2)
        Text("• Custom: Uses a custom mapping function to modify system scale values (e.g., dampen large scales)", style = MaterialTheme.typography.body2)

        Spacer(Modifier.height(4.dp))

        Text("Icon Size Scale Range", style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold))
        Text(
          "Sets the minimum and maximum scaling limits for icons using the icon-size-scale-range layout property. This only affects the custom blue annotations in this example.",
          style = MaterialTheme.typography.body2
        )
        Text(
          "Example: Setting [1.0, 1.0] prevents icons from scaling regardless of the scale factor value.",
          style = MaterialTheme.typography.body2.copy(fontStyle = FontStyle.Italic)
        )

        Spacer(Modifier.height(4.dp))

        Text("Text Size Scale Range", style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold))
        Text(
          "Sets the minimum and maximum scaling limits for text using the text-size-scale-range layout property. This only affects the custom blue text labels in this example.",
          style = MaterialTheme.typography.body2
        )
        Text(
          "Example: Setting [0.5, 3.0] allows text to scale from half size to triple size based on the scale factor.",
          style = MaterialTheme.typography.body2.copy(fontStyle = FontStyle.Italic)
        )

        Spacer(Modifier.height(8.dp))

        Text("Try experimenting:", style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold))
        Text("• Set scale factor to 2.0 with icon range [1.0, 1.0] to see text scale while icons stay the same size", style = MaterialTheme.typography.body2)
        Text("• Switch to System mode and change your device's font size in Settings to see automatic scaling", style = MaterialTheme.typography.body2)
        Text("• Use Custom mode to see how mapping functions can dampen extreme scale values", style = MaterialTheme.typography.body2)
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Got it")
      }
    }
  )
}