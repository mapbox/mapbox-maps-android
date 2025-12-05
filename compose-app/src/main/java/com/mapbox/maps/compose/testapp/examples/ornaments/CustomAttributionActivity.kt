package com.mapbox.maps.compose.testapp.examples.ornaments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mapbox.annotation.MapboxDelicateApi
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.examples.utils.CityLocations
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.ornaments.attribution.MapAttributionScope
import com.mapbox.maps.extension.compose.ornaments.attribution.isMapboxFeedback
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardSatelliteStyle
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.plugin.attribution.Attribution
import kotlinx.coroutines.launch

/**
 * Example Activity to showcase custom UI for map attributions using Jetpack Compose.
 *
 * This activity demonstrates how to:
 * - Implement a custom attribution display using a modal bottom sheet
 * - Handle user consent dialogs for telemetry and geofencing
 * - Customize the appearance and behavior of attribution controls
 * - Integrate with MapboxMap's attribution system
 */
@MapboxExperimental
public class CustomAttributionActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class, MapboxDelicateApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden
            )
            var showSatelliteStyle: Boolean by remember {
                mutableStateOf(false)
            }
            val coroutineScope = rememberCoroutineScope()

            var userConsentState: MapAttributionScope.UserConsentState? by remember {
                mutableStateOf(null)
            }
            val currentAttributionState = remember {
                MapAttributionScope.AttributionState()
            }

            MapboxMapComposeTheme {
                ExampleScaffold(
                    floatingActionButton = {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End
                        ) {
                            FloatingActionButton(
                                modifier = Modifier
                                    .padding(bottom = 10.dp),
                                onClick = {
                                    coroutineScope.launch {
                                        if (!sheetState.isVisible) {
                                            sheetState.show()
                                        } else {
                                            sheetState.hide()
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = if (!sheetState.isVisible) "Show Attribution" else "Hide Attribution"
                                )
                            }
                            FloatingActionButton(
                                modifier = Modifier
                                    .padding(bottom = 10.dp),
                                onClick = {
                                    showSatelliteStyle = !showSatelliteStyle
                                },
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = if (showSatelliteStyle) "Show Satellite Style" else "Show Standard Style"
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { scaffoldPadding ->
                    ModalBottomSheetLayout(
                        sheetContent = {
                            CustomAttributionBottomSheetContent(currentAttributionState) {
                                userConsentState = it
                            }
                        },
                        sheetState = sheetState,
                    ) {

                        MapboxMap(
                            modifier = Modifier.fillMaxSize(),
                            attribution = {
                                // Keep original built-in attribution control UI
                                Attribution()
                                // Add custom attribution control through [AttributionControl] and [AttributionState].
                                AttributionControl(
                                    userConsentState = userConsentState,
                                    attributionState = currentAttributionState
                                )
                            },
                            mapViewportState = rememberMapViewportState {
                                setCameraOptions {
                                    zoom(ZOOM)
                                    center(CityLocations.HELSINKI)
                                }
                            },
                            style = {
                                if (showSatelliteStyle) {
                                    MapboxStandardSatelliteStyle()
                                } else {
                                    MapboxStandardStyle()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    /**
     * Composable function that renders the content of the attribution bottom sheet.
     *
     * This function displays:
     * - A title for the custom attribution interface
     * - A list of attribution buttons for each attribution source
     * - User consent dialogs for telemetry and geofencing when triggered
     *
     * @param attributionState The current attribution state containing available attributions
     * and user consent information.
     * @param onUserConsentStateChanged Callback invoked when user consent state changes,
     * passing the updated consent state to the parent component.
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    public fun CustomAttributionBottomSheetContent(
        attributionState: MapAttributionScope.AttributionState,
        onUserConsentStateChanged: (MapAttributionScope.UserConsentState) -> Unit
    ) {
        var showTelemetryDialog by remember {
            mutableStateOf(false)
        }
        var showGeofencingConsentDialog by remember {
            mutableStateOf(false)
        }
        val coroutineScope = rememberCoroutineScope()
        val uriHandler = LocalUriHandler.current
        Column(modifier = Modifier.wrapContentHeight(), horizontalAlignment = CenterHorizontally) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Custom Attribution",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primarySurface
            )
            attributionState.userConsentState?.let { userConsentState ->
                Divider()
                Text("Telemetry Enable State: ${userConsentState.telemetryEnableState}")
                Text("Geofencing Consent State: ${userConsentState.geofencingUserConsentState}")
                if (showTelemetryDialog) {
                    UserConsentDialog(
                        title = stringResource(id = R.string.mapbox_attributionTelemetryTitle),
                        body = stringResource(id = R.string.mapbox_attributionTelemetryMessage),
                        onDismissRequest = { showTelemetryDialog = false },
                        onConsent = { agree ->
                            onUserConsentStateChanged(
                                userConsentState.toBuilder()
                                    .setTelemetryEnableState(agree)
                                    .build()
                            )
                        }
                    )
                }

                if (showGeofencingConsentDialog) {
                    UserConsentDialog(
                        title = stringResource(id = R.string.mapbox_attributionGeofencingTitle),
                        body = stringResource(id = R.string.mapbox_attributionGeofencingMessage),
                        onDismissRequest = { showGeofencingConsentDialog = false },
                        onConsent = { agree ->
                            onUserConsentStateChanged(
                                userConsentState.toBuilder()
                                    .setGeofencingUserConsentState(agree)
                                    .build()
                            )
                        }
                    )
                }
            }
            Divider()
            attributionState.attributions.forEach { attribution ->
                Button(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onClick = {
                        when (attribution.url) {
                            Attribution.ABOUT_TELEMETRY_URL -> showTelemetryDialog = true
                            Attribution.GEOFENCING_URL_MARKER -> showGeofencingConsentDialog = true

                            else -> {
                                if (attribution.isMapboxFeedback()) {
                                    // For MapboxFeedback attribution, use AttributionState.buildMapboxFeedbackUrl() to build the URL with the current map state.
                                    coroutineScope.launch {
                                        uriHandler.openUri(attributionState.buildMapboxFeedbackUrl())
                                    }
                                } else {
                                    uriHandler.openUri(attribution.url)
                                }
                            }
                        }
                    }
                ) {
                    Text(attribution.title)
                }
            }
        }
    }

    /**
     * Composable function that displays a user consent dialog for attribution-related permissions.
     *
     * This dialog presents the user with information about a specific feature (like telemetry
     * or geofencing) and allows them to agree or disagree with the terms. The dialog uses
     * Material Design's AlertDialog with custom button layout.
     *
     * @param title The title text displayed at the top of the dialog
     * @param body The main message content explaining what the user is consenting to
     * @param onDismissRequest Callback invoked when the dialog should be dismissed
     * (e.g., when user taps outside the dialog or presses back)
     * @param onConsent Callback invoked when user makes a choice, passing true for
     * "Agree" and false for "Disagree"
     */
    @Composable
    public fun UserConsentDialog(
        title: String,
        body: String,
        onDismissRequest: () -> Unit,
        onConsent: (Boolean) -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
            },
            text = {
                Text(
                    text = body,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.body1
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            onConsent(true)
                            onDismissRequest()
                        }
                    ) {
                        Text("Agree")
                    }
                    Button(
                        onClick = {
                            onConsent(false)
                            onDismissRequest()
                        }
                    ) {
                        Text("Disagree")
                    }
                }
            }
        )
    }

    private companion object {
        const val ZOOM: Double = 9.0
    }
}