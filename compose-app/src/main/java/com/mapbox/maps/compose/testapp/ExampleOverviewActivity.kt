package com.mapbox.maps.compose.testapp

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.data.ExamplesProvider
import com.mapbox.maps.compose.testapp.data.model.ExampleCategory
import com.mapbox.maps.compose.testapp.data.model.SpecificExample
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme

/**
 * Activity shown when application is started
 *
 * This activity will generate data for RecyclerView based on the AndroidManifest entries.
 * It uses tags as category and description to order the different entries.
 */
public class ExampleOverviewActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        OverviewScreen {
          startActivity(Intent().withComponent(packageName, it.name))
        }
      }
    }
  }

  private fun Intent.withComponent(packageName: String, exampleName: String): Intent {
    component = ComponentName(packageName, exampleName)
    return this
  }

  @Composable
  public fun OverviewScreen(navigateToExample: (SpecificExample) -> Unit) {
    val context = LocalContext.current.applicationContext
    val examples =
      if (LocalInspectionMode.current) {
        remember { ExamplesProvider.loadMockExampleEntries() }
      } else {
        remember { ExamplesProvider.loadExampleEntries(context) }
      }
    ExampleScaffold {
      LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(
          items = examples,
          itemContent = {
            when (it) {
              is SpecificExample -> ExampleListItem(
                specificExample = it,
                navigateToExample = navigateToExample
              )
              is ExampleCategory -> ExampleCategoryItem(category = it)
            }
          }
        )
      }
    }
  }

  @Preview("Light Theme", widthDp = 360, heightDp = 640)
  @Composable
  public fun LightPreview() {
    MapboxMapComposeTheme(darkTheme = false) {
      OverviewScreen { }
    }
  }

  @Preview("Dark Theme", widthDp = 360, heightDp = 640)
  @Composable
  public fun DarkPreview() {
    MapboxMapComposeTheme(darkTheme = true) {
      OverviewScreen { }
    }
  }
}