package com.mapbox.maps.compose.testapp

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mapbox.maps.compose.testapp.data.ExamplesProvider
import com.mapbox.maps.compose.testapp.data.model.SpecificExample
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.common.Version as CommonVersion
import com.mapbox.maps.Version as MapsVersion

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

  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  public fun OverviewScreen(navigateToExample: (SpecificExample) -> Unit) {
    val examples =
      if (LocalInspectionMode.current) {
        remember { ExamplesProvider.loadMockExampleEntries() }
      } else {
        val context = LocalContext.current.applicationContext
        remember { ExamplesProvider.loadExampleEntries(context) }
      }
    ExampleScaffold { innerPadding ->
      LazyColumn(contentPadding = innerPadding) {
        item {
          SdkVersions(modifier = Modifier.wrapContentSize())
        }
        examples.forEach { (category, examples) ->
          stickyHeader {
            ExampleCategoryItem(category)
          }

          items(
            items = examples,
            itemContent = {
              ExampleListItem(
                specificExample = it,
                navigateToExample = navigateToExample
              )
            }
          )
        }
      }
    }
  }

  @Composable
  private fun SdkVersions(modifier: Modifier) {
    @SuppressLint("RestrictedApi")
    val glNativeVersion =
      if (LocalInspectionMode.current) {
        "Maps: preview version"
      } else {
        "Maps: ${MapsVersion.getVersionString()} (${MapsVersion.getRevisionString()})"
      }

    @SuppressLint("RestrictedApi")
    val commonVersion =
      if (LocalInspectionMode.current) {
        "Common: preview version"
      } else {
        "Common: ${CommonVersion.getCommonSDKVersionString()} (${CommonVersion.getCommonSDKRevisionString()})"
      }
    Text(
      modifier = modifier,
      text = "$glNativeVersion; $commonVersion",
      fontSize = 10.sp,
    )
  }

  @Preview(name = "Light", device = PHONE, showSystemUi = true)
  @Preview(
    name = "Dark",
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL,
    device = PHONE,
    showSystemUi = true
  )
  @Composable
  public fun DarkPreview() {
    MapboxMapComposeTheme {
      OverviewScreen { }
    }
  }
}