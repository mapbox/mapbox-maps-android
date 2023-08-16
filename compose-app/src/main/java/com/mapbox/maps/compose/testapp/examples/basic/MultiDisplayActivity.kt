package com.mapbox.maps.compose.testapp.examples.basic

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState

/**
 * Showcase showing a map in a secondary display.
 */
@OptIn(MapboxExperimental::class)
public class MultiDisplayActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      var mapOnDefaultDisplay by remember {
        mutableStateOf(false)
      }
      MapboxMapComposeTheme {
        ExampleScaffold(
          floatingActionButton = {
            Column {
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  mapOnDefaultDisplay = !mapOnDefaultDisplay
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                val action = if (mapOnDefaultDisplay) "Hide" else "Show"
                Text(modifier = Modifier.padding(10.dp), text = "$action map on default display")
              }
              FloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                  displayMapInSecondaryScreen()
                },
                shape = RoundedCornerShape(16.dp),
              ) {
                Text(modifier = Modifier.padding(10.dp), text = "Show map on secondary display")
              }
            }
          }
        ) {
          if (mapOnDefaultDisplay) {
            MapboxMap(
              Modifier.fillMaxSize(),
              mapViewportState = MapViewportState().apply {
                setCameraOptions {
                  center(CityLocations.HELSINKI)
                  zoom(15.0)
                }
              }
            )
          }
        }
      }
    }
  }

  private fun displayMapInSecondaryScreen() {
    val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    displayManager.displays.firstOrNull {
      it.displayId != Display.DEFAULT_DISPLAY
    }?.apply {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Use activity options to select the display screen.
        val options = ActivityOptions.makeBasic()
        options.launchDisplayId = displayId
        val intent = Intent(this@MultiDisplayActivity, SimpleMapActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // To display on the second screen, the intent must be launched using singleTask launchMode.
        startActivity(
          intent,
          options.toBundle()
        )
      }
    }
      ?: Toast.makeText(this, "Second screen not found.", Toast.LENGTH_SHORT).show()
  }
}