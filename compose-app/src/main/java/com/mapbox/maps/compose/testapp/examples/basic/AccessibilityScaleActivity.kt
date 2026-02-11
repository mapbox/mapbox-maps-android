package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.SymbolScaleBehavior
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce

/**
 * Example demonstrating accessibility scaling for map symbols.
 *
 * This example shows three modes:
 * - Fixed: Manual scale control with slider (no system listeners)
 * - System: Automatic scaling based on system font size
 * - Custom: System scaling with custom mapping that dampens large accessibility scales
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
  SYSTEM("System"),
  CUSTOM("Custom"),
  FIXED("Fixed")
}

@OptIn(MapboxExperimental::class, FlowPreview::class)
@Composable
private fun AccessibilityScaleExample() {
  var selectedMode by remember { mutableStateOf(ScaleMode.FIXED) }
  val customScaleFlow = remember { MutableStateFlow(1.0f) }
  val debouncedScaleValue by customScaleFlow
    .debounce(150) // 150ms debounce to prevent UI freezes during slider drag
    .collectAsState(initial = 1.0f)

  Box(Modifier.fillMaxSize()) {
    MapboxMap(
      Modifier.fillMaxSize(),
      mapViewportState = rememberMapViewportState {
        setCameraOptions {
          center(Point.fromLngLat(-74.0060, 40.7128))
          zoom(12.0)
        }
      }
    ) {
      MapEffect(selectedMode, debouncedScaleValue) { view ->
        val symbolScaleBehavior = when (selectedMode) {
          ScaleMode.FIXED -> {
            // Fixed scale: manual control via slider
            SymbolScaleBehavior.fixed(debouncedScaleValue)
          }
          ScaleMode.SYSTEM -> {
            // System: automatic scaling with default mapping
            SymbolScaleBehavior.system
          }
          ScaleMode.CUSTOM -> {
            // Custom: dampens large accessibility scales to max 1.5x
            SymbolScaleBehavior.system { systemFontScale ->
              when {
                systemFontScale < 1.0f -> systemFontScale.coerceAtLeast(0.8f)
                systemFontScale <= 1.3f -> systemFontScale
                else -> 1.3f + (systemFontScale - 1.3f) * 0.3f // Dampen large scales (max ~1.5x)
              }
            }
          }
        }
        view.mapboxMap.symbolScaleBehavior = symbolScaleBehavior
      }
    }

    // Bottom controls
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 70.dp),
      contentAlignment = Alignment.BottomCenter
    ) {
      Column(
        modifier = Modifier
          .widthIn(max = 340.dp)
          .background(Color(0xCC222222), shape = RoundedCornerShape(16.dp))
          .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Conditional content area (above buttons)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(if (selectedMode == ScaleMode.FIXED) 80.dp else if (selectedMode == ScaleMode.SYSTEM || selectedMode == ScaleMode.CUSTOM) 60.dp else 0.dp),
          contentAlignment = Alignment.TopCenter
        ) {
          when (selectedMode) {
            ScaleMode.FIXED -> {
              Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Scale factor:",
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                  )
                  Spacer(modifier = Modifier.weight(1f))
                  Text(
                    text = "%.2fx".format(customScaleFlow.value),
                    style = MaterialTheme.typography.caption,
                    color = Color.White
                  )
                }
                Slider(
                  value = customScaleFlow.value,
                  onValueChange = { customScaleFlow.value = it },
                  valueRange = 0.8f..2.0f,
                  steps = 11,
                  modifier = Modifier.fillMaxWidth()
                )
                Text(
                  text = "Manual scale control (no system listeners)",
                  style = MaterialTheme.typography.caption,
                  color = Color(0xFFAAAAAA),
                  textAlign = TextAlign.Center,
                  modifier = Modifier.fillMaxWidth()
                )
              }
            }
            ScaleMode.SYSTEM -> {
              Text(
                text = "Automatic scaling with default mapping.\nChange system font size in Settings to see effect.",
                style = MaterialTheme.typography.caption,
                color = Color(0xFFAAAAAA),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
              )
            }
            ScaleMode.CUSTOM -> {
              Text(
                text = "Custom mapping: dampens large accessibility scales (max 1.5x).\nChange system font size in Settings to see effect.",
                style = MaterialTheme.typography.caption,
                color = Color(0xFFAAAAAA),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
              )
            }
          }
        }

        // Mode selector (bottom level)
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          ScaleMode.values().forEach { mode ->
            Box(
              modifier = Modifier
                .weight(1f)
                .background(
                  color = if (selectedMode == mode)
                    MaterialTheme.colors.primary
                  else
                    Color(0xFF444444),
                  shape = RoundedCornerShape(8.dp)
                )
                .clickable(
                  interactionSource = remember { MutableInteractionSource() },
                  indication = null
                ) { selectedMode = mode }
                .padding(vertical = 10.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = mode.displayName,
                style = MaterialTheme.typography.button,
                color = Color.White
              )
            }
          }
        }
      }
    }
  }
}