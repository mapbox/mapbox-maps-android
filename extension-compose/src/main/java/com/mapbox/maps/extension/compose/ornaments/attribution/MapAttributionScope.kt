package com.mapbox.maps.extension.compose.ornaments.attribution

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.ornaments.attribution.internal.AttributionComposePlugin
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionParserConfig

/**
 * A [MapAttributionScope] provides a scope for adding [Attribution] ornament.
 */
@Suppress("NAMED_ARGUMENTS_NOT_ALLOWED")
@MapboxMapScopeMarker
@Immutable
@MapboxExperimental
public class MapAttributionScope internal constructor(
  private val mapView: MapView,
  private val boxScope: BoxScope
) {
  /**
   * Add a [Attribution] ornament to the map.
   *
   * Please note that it's **required** to show Mapbox Attribution button in your map. See [Mapbox ToS](https://www.mapbox.com/legal/tos)
   *
   * By default, the [Attribution] will be placed to the [Alignment.BottomStart] of the map with padding of 92dp, 4dp, 4dp, 4dp.
   *
   * @param modifier Modifier to be applied to the [Attribution].
   * @param alignment The alignment of the [Attribution] within the Map.
   * @param attributionDialog Defines AlertDialog when the attribution is clicked.
   */
  @Composable
  @MapboxExperimental
  public fun Attribution(
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.BottomStart,
    iconColor: Color = Color(0xFF1E8CAB),
    attributionDialog: @Composable (
      attributions: List<Attribution>,
      onDismissRequest: () -> Unit,
      onAttributionClick: (Attribution) -> Unit
    ) -> Unit = { attributions, onDismissRequest, onAttributionClick ->
      AttributionDialog(
        attributions = attributions,
        onDismissRequest = onDismissRequest,
        onAttributionClick = onAttributionClick
      )
    },
    telemetryDialog: @Composable (
      onDismissRequest: () -> Unit,
      onMoreInfo: () -> Unit,
      onDisagree: () -> Unit,
      onAgree: () -> Unit
    ) -> Unit = { onDismissRequest, onMoreInfo, onDisagree, onAgree ->
      TelemetryDialog(
        onDismissRequest = onDismissRequest,
        onMoreInfo = onMoreInfo,
        onDisagree = onDisagree,
        onAgree = onAgree
      )
    }
  ) {
    val pluginId = remember {
      getNextId()
    }
    var attributions by remember {
      mutableStateOf(listOf<Attribution>())
    }
    var showAttributionDialog by remember {
      mutableStateOf(false)
    }
    var showTelemetryDialog by remember {
      mutableStateOf(false)
    }
    var mapboxFeedbackUrl by remember {
      mutableStateOf("")
    }

    var telemetryEnableState by remember {
      mutableStateOf(true)
    }

    DisposableEffect(Unit) {
      // Initialise AttributionComposePlugin when entering composition
      mapView.createPlugin(
        Plugin.Custom(
          id = pluginId,
          instance = AttributionComposePlugin()
        )
      )
      onDispose {
        // Remove AttributionComposePlugin when leaving composition
        mapView.removePlugin(pluginId)
      }
    }

    // Whenever telemetryEnableState is updated by user, pass it through to mapAttributionDelegate.
    LaunchedEffect(telemetryEnableState) {
      mapView.getPlugin<AttributionComposePlugin>(pluginId)?.let {
        it.mapAttributionDelegate.telemetry().userTelemetryRequestState = telemetryEnableState
      }
    }

    // Whenever attribution dialog is about to appear, update the attribution list and feedback url according
    // to current style.
    LaunchedEffect(showAttributionDialog) {
      mapView.getPlugin<AttributionComposePlugin>(pluginId)?.let {
        attributions =
          it.mapAttributionDelegate.parseAttributions(mapView.context, AttributionParserConfig())
        mapboxFeedbackUrl = it.mapAttributionDelegate.buildMapBoxFeedbackUrl(mapView.context)
      }
    }

    // Show Attribution Dialog
    if (showAttributionDialog) {
      attributionDialog(
        attributions = attributions,
        onDismissRequest = {
          showAttributionDialog = false
        },
        onAttributionClick = { attribution ->
          showAttributionDialog = false
          if (attribution.url == Attribution.ABOUT_TELEMETRY_URL) {
            showTelemetryDialog = true
          } else {
            val url = if (attribution.url.contains(FEEDBACK_KEY_WORD)) {
              mapboxFeedbackUrl
            } else attribution.url
            if (url.isNotEmpty()) {
              showWebPage(url)
            }
          }
        }
      )
    }

    // Show Telemetry Dialog
    if (showTelemetryDialog) {
      telemetryDialog(
        onDismissRequest = {
          showTelemetryDialog = false
        },
        onMoreInfo = {
          showWebPage(mapView.context.getString(R.string.mapbox_telemetryLink))
          showTelemetryDialog = false
        },
        onDisagree = {
          telemetryEnableState = false
          showTelemetryDialog = false
        },
        onAgree = {
          telemetryEnableState = true
          showTelemetryDialog = false
        }
      )
    }
    // Show Attribution image button.
    Image(
      modifier = with(boxScope) {
        if (modifier === Modifier) {
          Modifier
            .padding(92.dp, 4.dp, 4.dp, 4.dp)
        } else {
          modifier
        }
          .align(alignment)
          .clickable {
            showAttributionDialog = true
          }
      },
      painter = painterResource(id = R.drawable.mapbox_attribution_default),
      contentDescription = "Mapbox Attribution",
      colorFilter = ColorFilter.tint(color = iconColor)
    )
  }

  /**
   * Build an [AttributionDialog] to be added to the [Attribution] ornament.
   *
   * @param attributions The list of [Attribution]s to be shown in the [AttributionDialog].
   * @param onDismissRequest The callback to be invoked when the attribution is dismissed.
   * @param onAttributionClick The callback to be invoked when a attribution is clicked.
   */
  @MapboxExperimental
  @Composable
  public fun AttributionDialog(
    attributions: List<Attribution>,
    onDismissRequest: () -> Unit,
    onAttributionClick: (attribution: Attribution) -> Unit,
  ) {
    AlertDialog(
      onDismissRequest = onDismissRequest,
      confirmButton = { },
      title = {
        Text(
          text = stringResource(id = R.string.mapbox_attributionsDialogTitle),
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        LazyColumn {
          items(
            attributions
          ) { attribution ->
            ClickableText(
              text = attribution.toAnnotatedString(),
              modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .fillParentMaxWidth()
                .wrapContentHeight(),
              onClick = {
                onAttributionClick(attribution)
              }
            )
          }
        }
      },
      properties = DialogProperties()
    )
  }

  /**
   * Build an [TelemetryDialog] to be added to the [AttributionDialog] when telemetry settings is clicked.
   *
   * @param onDismissRequest The callback to be invoked when the attribution is dismissed.
   * @param onMoreInfo The callback to be invoked when more information is needed.
   * @param onDisagree The callback to be invoked when user disagrees with the telemetry collection.
   * @param onAgree The callback to be invoked when user agrees with the telemetry collection.
   */
  @MapboxExperimental
  @Composable
  public fun TelemetryDialog(
    onDismissRequest: () -> Unit,
    onMoreInfo: () -> Unit,
    onDisagree: () -> Unit,
    onAgree: () -> Unit
  ) {
    AlertDialog(
      onDismissRequest = onDismissRequest,
      buttons = {
        Column(
          modifier = Modifier
            // The alert dialog `title` and `text` are padded 24.dp by default.
            // To align the button text, we use 16.dp here because TextButton adds 8.dp padding around the text
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        ) {
          TextButton(
            onClick = { onMoreInfo() }
          ) {
            Text(stringResource(id = R.string.mapbox_attributionTelemetryNeutral).uppercase())
          }

          TextButton(
            onClick = { onDisagree() }
          ) {
            Text(stringResource(id = R.string.mapbox_attributionTelemetryNegative).uppercase())
          }

          TextButton(
            onClick = { onAgree() }
          ) {
            Text(stringResource(id = R.string.mapbox_attributionTelemetryPositive).uppercase())
          }
        }
      },
      title = {
        Text(
          text = stringResource(id = R.string.mapbox_attributionTelemetryTitle),
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Text(text = stringResource(id = R.string.mapbox_attributionTelemetryMessage))
      }
    )
  }

  @Composable
  private fun Attribution.toAnnotatedString(): AnnotatedString =
    buildAnnotatedString {
      withStyle(
        style = SpanStyle(
          fontSize = 13.sp,
          color = colorResource(id = if (url.isEmpty()) R.color.mapbox_gray else R.color.mapbox_blue)
        )
      ) {
        append(this@toAnnotatedString.title.uppercase())
      }
    }

  private fun showWebPage(url: String) {
    if (mapView.context is Activity) {
      try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        mapView.context.startActivity(intent)
      } catch (exception: ActivityNotFoundException) { // explicitly handling if the device hasn't have a web browser installed. #8899
        Toast.makeText(
          mapView.context,
          R.string.mapbox_attributionErrorNoBrowser,
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }

  private companion object {
    private const val PLUGIN_ID = "MAPBOX_ATTRIBUTION_COMPOSE_PLUGIN"
    private var INSTANCE_COUNT = 0
    private const val FEEDBACK_KEY_WORD = "feedback"
    fun getNextId(): String {
      return "$PLUGIN_ID-${INSTANCE_COUNT++}"
    }
  }
}