package com.mapbox.maps.extension.compose.ornaments.attribution

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.mapbox.annotation.MapboxDelicateApi
import com.mapbox.annotation.MapboxExperimental
import com.mapbox.common.Cancelable
import com.mapbox.common.geofencing.GeofencingError
import com.mapbox.maps.MapView
import com.mapbox.maps.SourceDataLoadedType
import com.mapbox.maps.StyleAttributionsChangedCallback
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.R
import com.mapbox.maps.extension.compose.ornaments.attribution.internal.AttributionComposePlugin
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import com.mapbox.maps.logW
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.attribution.Attribution
import com.mapbox.maps.plugin.attribution.AttributionParserConfig
import com.mapbox.maps.plugin.attribution.isMapboxFeedback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Objects
import kotlin.coroutines.resume

/**
 * A [MapAttributionScope] provides a scope for adding [Attribution] ornament.
 */
@Suppress("NAMED_ARGUMENTS_NOT_ALLOWED")
@MapboxMapScopeMarker
@Immutable
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
   * @param contentPadding The default padding applied to the [Attribution], paddings from [modifier] will be applied on top of this default padding.
   * @param alignment The alignment of the [Attribution] within the Map.
   * @param iconColor The color tint applied to the attribution icon. Defaults to Mapbox blue (#1E8CAB).
   * @param attributionDialog Defines AlertDialog when the attribution is clicked.
   * @param telemetryDialog Defines TelemetryDialog when the Mapbox telemetry is clicked.
   */
  @Composable
  public fun Attribution(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(92.dp, 4.dp, 4.dp, 4.dp),
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
    Attribution(
      modifier = modifier,
      contentPadding = contentPadding,
      alignment = alignment,
      iconColor = iconColor,
      attributionDialog = attributionDialog,
      telemetryDialog = telemetryDialog,
      geofencingConsentDialog = { onDismissRequest, onDisagree, onAgree, currentUserConsent ->
        GeofencingConsentDialog(
          onDismissRequest = onDismissRequest,
          onDisagree = onDisagree,
          onAgree = onAgree,
          currentUserConsent = currentUserConsent
        )
      }
    )
  }

  /**
   * Add a [Attribution] ornament to the map.
   *
   * Please note that it's **required** to show Mapbox Attribution button in your map. See [Mapbox ToS](https://www.mapbox.com/legal/tos)
   *
   * By default, the [Attribution] will be placed to the [Alignment.BottomStart] of the map with padding of 92dp, 4dp, 4dp, 4dp.
   *
   * @param modifier Modifier to be applied to the [Attribution].
   * @param contentPadding The default padding applied to the [Attribution], paddings from [modifier] will be applied on top of this default padding.
   * @param alignment The alignment of the [Attribution] within the Map.
   * @param iconColor The color tint applied to the attribution icon. Defaults to Mapbox blue (#1E8CAB).
   * @param attributionDialog Defines AlertDialog when the attribution is clicked.
   * @param telemetryDialog Defines TelemetryDialog when the Mapbox telemetry is clicked.
   * @param geofencingConsentDialog Defines GeofencingConsentDialog when the Mapbox Geofencing is clicked.
   */
  @OptIn(MapboxExperimental::class, MapboxDelicateApi::class)
  @Composable
  public fun Attribution(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(92.dp, 4.dp, 4.dp, 4.dp),
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
    },
    geofencingConsentDialog: @Composable (
      onDismissRequest: () -> Unit,
      onDisagree: () -> Unit,
      onAgree: () -> Unit,
      currentUserConsent: Boolean
    ) -> Unit = { onDismissRequest, onDisagree, onAgree, currentUserConsent ->
      GeofencingConsentDialog(
        onDismissRequest = onDismissRequest,
        onDisagree = onDisagree,
        onAgree = onAgree,
        currentUserConsent = currentUserConsent,
      )
    }
  ) {
    val attributionState: AttributionState = remember {
      AttributionState()
    }
    val coroutineScope = rememberCoroutineScope()
    var showAttributionDialog by remember {
      mutableStateOf(false)
    }

    var showTelemetryDialog by remember {
      mutableStateOf(false)
    }
    var showGeofencingConsentDialog by remember {
      mutableStateOf(false)
    }

    var userSelectedTelemetryEnableState: Boolean? by remember {
      mutableStateOf(null)
    }

    var userSelectedGeofencingUserConsentState: Boolean? by remember {
      mutableStateOf(null)
    }

    var userConsentState: UserConsentState? by remember {
      mutableStateOf(null)
    }

    // Whenever telemetryEnableState or geofencingUserConsentState is updated by the user,
    // pass it through to attributionState.
    LaunchedEffect(
      userSelectedTelemetryEnableState,
      userSelectedGeofencingUserConsentState,
    ) {
      attributionState.userConsentState?.let { state ->
        val builder = state.toBuilder()
        userSelectedTelemetryEnableState?.let { builder.setTelemetryEnableState(it) }
        userSelectedGeofencingUserConsentState?.let { builder.setGeofencingUserConsentState(it) }
        userConsentState = builder.build()
      }
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
          userSelectedTelemetryEnableState = false
          showTelemetryDialog = false
        },
        onAgree = {
          userSelectedTelemetryEnableState = true
          showTelemetryDialog = false
        }
      )
    }

    // Show Geofencing consent Dialog
    if (showGeofencingConsentDialog) {
      geofencingConsentDialog(
        onDismissRequest = {
          showGeofencingConsentDialog = false
        },
        onDisagree = {
          userSelectedGeofencingUserConsentState = false
          showGeofencingConsentDialog = false
        },
        onAgree = {
          userSelectedGeofencingUserConsentState = true
          showGeofencingConsentDialog = false
        },
        attributionState.userConsentState?.geofencingUserConsentState ?: true
      )
    }

    // Show Attribution Dialog
    if (showAttributionDialog) {
      attributionDialog(
        attributions = attributionState.attributions,
        onDismissRequest = {
          showAttributionDialog = false
        },
        onAttributionClick = { attribution ->
          showAttributionDialog = false
          when (attribution.url) {
            Attribution.ABOUT_TELEMETRY_URL -> showTelemetryDialog = true
            Attribution.GEOFENCING_URL_MARKER -> showGeofencingConsentDialog = true
            else -> {
              if (attribution.url.isNotEmpty()) {
                if (attribution.isMapboxFeedback()) {
                  coroutineScope.launch {
                    val url = attributionState.buildMapboxFeedbackUrl()
                    showWebPage(url)
                  }
                } else {
                  showWebPage(attribution.url)
                }
              } else {
                logE(TAG, "Attribution url for ${attribution.title} is empty")
              }
            }
          }
        }
      )
    }

    // Show Attribution image button.
    Image(
      modifier = with(boxScope) {
        Modifier
            .padding(contentPadding)
            .then(modifier)
            .align(alignment)
            .clickable {
                showAttributionDialog = true
            }
      },
      painter = painterResource(id = R.drawable.mapbox_attribution_default),
      contentDescription = "Mapbox Attribution",
      colorFilter = ColorFilter.tint(color = iconColor)
    )

    AttributionControl(
      userConsentState = userConsentState,
      attributionState = attributionState
    )
  }

  /**
   * Adds a headless [AttributionControl] to the map for programmatic attribution management.
   *
   * This control provides no visual UI itself, instead exposing [UserConsentState] and [AttributionState] data
   * to enable custom attribution interfaces elsewhere in your application. Use this when
   * implementing custom attribution UI while maintaining ToS compliance.
   *
   * ## Legal Requirements
   * **Mapbox attribution is mandatory** per [Mapbox Terms of Service](https://www.mapbox.com/legal/tos).
   * This control helps you create custom UI while meeting these requirements.
   *
   * ## Telemetry and Privacy
   * Your users should be in charge of their own location data and when it is shared. By default,
   * whenever your application causes the user's location to be gathered, it sends de-identified
   * location and usage data to Mapbox. If you're developing a native app with one of the Mapbox
   * mobile SDKs, our terms of service require that you provide a telemetry opt-out option within
   * your app for all end users. The default attribution control includes an opt out button. If you
   * hide the attribution control, you must provide an alternative opt out method your users can use.
   * You are responsible for allowing your users to opt out of Mapbox Telemetry.
   *
   * See [guide](https://docs.mapbox.com/help/dive-deeper/mobile-apps/#telemetry) for more info.
   *
   * ## Usage Example
   * ```kotlin
   * val attributionState = remember { AttributionState() }
   * var userConsent: UserConsentState? by remember { mutableStateOf(null) }
   *
   * MapboxMap(
   *   attribution = {
   *     // Headless attribution control for custom UI
   *     AttributionControl(
   *       userConsentState = userConsent,
   *       attributionState = attributionState
   *     )
   *   }
   * )
   *
   * // Use attributionState.attributions and attributionState.userConsentState
   * // in your custom UI components
   * ```
   *
   * ## Usage Notes
   * - Attribution data may not be immediately available during map initialization, and it might be updated whenever style source is loaded.
   *
   * @param userConsentState User consent configuration for telemetry and geofencing.
   * When `null`, existing consent state remains unchanged. Use to programmatically
   * update user preferences from your custom UI.
   *
   * @param attributionState Current attribution data containing available attributions
   * and user consent information for building custom UI.
   */
  @MapboxExperimental
  @MapboxDelicateApi
  @Composable
  public fun AttributionControl(
    userConsentState: UserConsentState?,
    attributionState: AttributionState
  ) {
    val pluginId = remember {
      getNextId()
    }

    var attributionComposePlugin: AttributionComposePlugin? by remember {
      mutableStateOf(null)
    }

    DisposableEffect(Unit) {
      // Initialise AttributionComposePlugin when entering composition
      mapView.createPlugin(
        Plugin.Custom(
          id = pluginId,
          instance = AttributionComposePlugin()
        )
      )
      attributionComposePlugin = mapView.getPlugin(pluginId)
      onDispose {
        // Remove AttributionComposePlugin when leaving composition
        mapView.removePlugin(pluginId)
        attributionComposePlugin = null
      }
    }

    attributionState.BindToMap(mapView, attributionComposePlugin)

    // Whenever telemetryEnableState is updated by user, pass it through to mapAttributionDelegate.
    LaunchedEffect(userConsentState?.telemetryEnableState, attributionComposePlugin) {
      userConsentState?.telemetryEnableState?.let { telemetryEnableState ->
        attributionComposePlugin?.let {
          it.mapAttributionDelegate.telemetry().userTelemetryRequestState = telemetryEnableState
          // Update UserConsentState in attributionState
          attributionState._userConsentState.value = UserConsentState.Builder()
            .setTelemetryEnableState(it.mapAttributionDelegate.telemetry().userTelemetryRequestState)
            .setGeofencingUserConsentState(
              it.mapAttributionDelegate.geofencingConsent().getUserConsent()
            )
            .build()
          logD(TAG, "User set telemetry state: $telemetryEnableState")
        }
      }
    }

    // Whenever geofencingUserConsentState is updated by the user, pass it through to mapAttributionDelegate.
    LaunchedEffect(userConsentState?.geofencingUserConsentState, attributionComposePlugin) {
      userConsentState?.geofencingUserConsentState?.let { geofencingUserConsentState ->
        attributionComposePlugin?.let { plugin ->
          val error: GeofencingError? = suspendCancellableCoroutine { continuation ->
            plugin.mapAttributionDelegate.geofencingConsent()
              .setUserConsent(geofencingUserConsentState) {
                continuation.resume(it.error)
                // Update UserConsentState in attributionState
                attributionState._userConsentState.value = UserConsentState.Builder()
                  .setTelemetryEnableState(plugin.mapAttributionDelegate.telemetry().userTelemetryRequestState)
                  .setGeofencingUserConsentState(
                    plugin.mapAttributionDelegate.geofencingConsent().getUserConsent()
                  )
                  .build()
                logD(
                  TAG,
                  "User set geofencingUserConsentState: $geofencingUserConsentState, error: ${it.error}"
                )
              }
          }
          error?.let {
            logW("GeofencingConsent", "Unable to set user consent: ${it.type}")
          }
        }
      }
    }
  }

  /**
   * Represents user consent preferences for data collection and location services.
   *
   * This data class enables programmatic management of user consent states for telemetry
   * and geofencing features, allowing you to implement custom consent UI flows outside
   * of the default attribution interface.
   *
   * ## Telemetry and Privacy
   * Your users should be in charge of their own location data and when it is shared. By default,
   * whenever your application causes the user's location to be gathered, it sends de-identified
   * location and usage data to Mapbox. If you're developing a native app with one of the Mapbox
   * mobile SDKs, our terms of service require that you provide a telemetry opt-out option within
   * your app for all end users. The default attribution control includes an opt out button. If you
   * hide the attribution control, you must provide an alternative opt out method your users can use.
   * You are responsible for allowing your users to opt out of Mapbox Telemetry.
   *
   * See [guide](https://docs.mapbox.com/help/dive-deeper/mobile-apps/#telemetry) for more info.
   */
  @Stable
  @MapboxExperimental
  public class UserConsentState private constructor(
    /**
     * Telemetry opt-in state.
     */
    public val telemetryEnableState: Boolean,
    /**
     * Geofencing opt-in state.
     */
    public val geofencingUserConsentState: Boolean,
  ) {
    /**
     * Calculate the hash code for this object.
     */
    override fun hashCode(): Int {
      return Objects.hash(telemetryEnableState, geofencingUserConsentState)
    }

    /**
     * Determine if this object is equal to the specified [other] object.
     */
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as UserConsentState

      if (telemetryEnableState != other.telemetryEnableState) return false
      if (geofencingUserConsentState != other.geofencingUserConsentState) return false

      return true
    }

    /**
     * Return a string representation of this object.
     */
    override fun toString(): String {
      return "UserConsentState(telemetryEnableState=$telemetryEnableState,geofencingUserConsentState=$geofencingUserConsentState)"
    }

    /**
     * Builder for [UserConsentState].
     */
    @MapboxExperimental
    public class Builder {
      /**
       * Telemetry opt-in state.
       */
      @set:JvmSynthetic
      public var telemetryEnableState: Boolean = true

      /**
       * Geofencing opt-in state.
       */
      @set:JvmSynthetic
      public var geofencingUserConsentState: Boolean = true

      /**
       * Set the telemetry opt-in state.
       */
      public fun setTelemetryEnableState(telemetryEnableState: Boolean): Builder = apply {
        this.telemetryEnableState = telemetryEnableState
      }

      /**
       * Set the geofencing opt-in state.
       */
      public fun setGeofencingUserConsentState(geofencingUserConsentState: Boolean): Builder =
        apply {
          this.geofencingUserConsentState = geofencingUserConsentState
        }

      /**
       * Build an [UserConsentState].
       */
      public fun build(): UserConsentState {
        return UserConsentState(
          telemetryEnableState = telemetryEnableState,
          geofencingUserConsentState = geofencingUserConsentState,
        )
      }
    }

    /**
     * Build an [UserConsentState] with the current state.
     */
    public fun toBuilder(): Builder {
      return Builder()
        .setTelemetryEnableState(telemetryEnableState)
        .setGeofencingUserConsentState(geofencingUserConsentState)
    }
  }

  /**
   * Complete attribution state containing both attribution data and user consent preferences.
   *
   * This data class combines attribution information (such as data sources and copyright notices)
   * with user consent settings, enabling comprehensive attribution management for custom UI
   * implementations that need to display both attribution and handle user consent.
   *
   * ## State Management
   * - Contains current attribution list from active map style and data sources
   * - Includes telemetry and geofencing consent states
   * - Use with [AttributionControl] for programmatic state management
   *
   * ## Telemetry and Privacy
   * Your users should be in charge of their own location data and when it is shared. By default,
   * whenever your application causes the user's location to be gathered, it sends de-identified
   * location and usage data to Mapbox. If you're developing a native app with one of the Mapbox
   * mobile SDKs, our terms of service require that you provide a telemetry opt-out option within
   * your app for all end users. The default attribution control includes an opt out button. If you
   * hide the attribution control, you must provide an alternative opt out method your users can use.
   * You are responsible for allowing your users to opt out of Mapbox Telemetry.
   *
   * See [guide](https://docs.mapbox.com/help/dive-deeper/mobile-apps/#telemetry) for more info.
   */
  @Stable
  @MapboxExperimental
  public class AttributionState {
    private val attributionPluginFlow =
      MutableStateFlow<Pair<MapView, AttributionComposePlugin>?>(null)

    /**
     * List of attributions to be shown in the [AttributionDialog].
     */
    private val _attributions: MutableState<List<Attribution>> = mutableStateOf(emptyList())

    /**
     * User consent state.
     */
    internal val _userConsentState: MutableState<UserConsentState?> = mutableStateOf(null)

    /**
     * List of attributions to be shown in the [AttributionDialog].
     */
    public val attributions: List<Attribution> by _attributions

    /**
     * User consent state.
     */
    public val userConsentState: UserConsentState? by _userConsentState

    /**
     * Build the Mapbox feedback url with the current map states.
     */
    public suspend fun buildMapboxFeedbackUrl(): String {
      attributionPluginFlow.filterNotNull().first().apply {
        return second.mapAttributionDelegate.buildMapBoxFeedbackUrl(first.context)
      }
    }

    @Composable
    private fun UpdateAttributionState(mapView: MapView, plugin: AttributionComposePlugin?) {
      // Update user consent state for the first time
      LaunchedEffect(plugin) {
        if (plugin != null) {
          _userConsentState.value = UserConsentState.Builder()
            .setTelemetryEnableState(plugin.mapAttributionDelegate.telemetry().userTelemetryRequestState)
            .setGeofencingUserConsentState(
              plugin.mapAttributionDelegate.geofencingConsent().getUserConsent()
            )
            .build()
          logD(
            TAG,
            "AttributionComposePlugin(${plugin.hashCode()}) Load initial user consent state: $userConsentState "
          )
        }
      }

      // Refresh the attributions when style changes, as for Standard style, the source events are hidden
      DisposableEffect(plugin) {
        var styleAttributionChangedCancelable: Cancelable? = null
        if (plugin != null) {
          // Refresh the attribution initially to show the Mapbox telemetry, feedback attributions etc, even without source attributions
          updateAttributionState(mapView, plugin)
          // Update attributions when style attribution is changed
          // Used internal styleManager API to utlise this new StyleAttributionsChanged event.
          styleAttributionChangedCancelable = mapView.mapboxMap.styleManager.subscribe(
            StyleAttributionsChangedCallback {
              logD(
                TAG,
                "AttributionComposePlugin(${plugin.hashCode()}) StyleAttributionsChanged, refresh attributions"
              )
              updateAttributionState(mapView, plugin)
            }
          )
        }
        onDispose {
          styleAttributionChangedCancelable?.cancel()
          styleAttributionChangedCancelable = null
        }
      }

      // Refresh the attributions when source is added or removed in the runtime
      DisposableEffect(plugin) {
        var sourceAddedCancellable: Cancelable? = null
        var sourceRemovedCancellable: Cancelable? = null
        val sourceDataLoadedCancellable = hashMapOf<String, Cancelable>()
        if (plugin != null) {
          mapView.mapboxMap.apply {
            // Update attributions when new source is added in the runtime
            sourceAddedCancellable = subscribeSourceAdded { sourceAdded ->
              sourceDataLoadedCancellable[sourceAdded.sourceId] =
                subscribeSourceDataLoaded {
                  // refresh the attribution only when the source metadata is loaded, otherwise the new attribution is not yet parsed
                  if (it.type == SourceDataLoadedType.METADATA && it.sourceId == sourceAdded.sourceId) {
                    logD(
                      TAG,
                      "AttributionComposePlugin(${plugin.hashCode()}) source metadata loaded, refresh attributions"
                    )
                    updateAttributionState(mapView, plugin)
                    sourceDataLoadedCancellable.remove(sourceAdded.sourceId)?.cancel()
                  }
                }
            }
            // Update attribution when the source is removed
            sourceRemovedCancellable = subscribeSourceRemoved {
              updateAttributionState(mapView, plugin)
            }
          }
        }
        onDispose {
          sourceAddedCancellable?.cancel()
          sourceAddedCancellable = null
          sourceRemovedCancellable?.cancel()
          sourceRemovedCancellable = null
          sourceDataLoadedCancellable.values.forEach { it.cancel() }
          sourceDataLoadedCancellable.clear()
        }
      }
    }

    private fun updateAttributionState(mapView: MapView, plugin: AttributionComposePlugin) {
      _attributions.value = plugin.mapAttributionDelegate.parseAttributions(
        mapView.context,
        AttributionParserConfig()
      ).toMutableList().apply {
        find { it.isMapboxFeedback() }?.let {
          remove(it)
          add(it.copy(url = plugin.mapAttributionDelegate.buildMapBoxFeedbackUrl(mapView.context)))
        }
      }
        .toList()
    }

    @Composable
    internal fun BindToMap(mapView: MapView, plugin: AttributionComposePlugin?) {
      LaunchedEffect(plugin) {
        if (plugin != null) {
          attributionPluginFlow.value = mapView to plugin
        }
      }
      UpdateAttributionState(mapView, plugin)
    }
  }

  /**
   * Build an [AttributionDialog] to be added to the [Attribution] ornament.
   *
   * @param attributions The list of [Attribution]s to be shown in the [AttributionDialog].
   * @param onDismissRequest The callback to be invoked when the attribution is dismissed.
   * @param onAttributionClick The callback to be invoked when a attribution is clicked.
   */
  @OptIn(ExperimentalComposeUiApi::class)
  @Composable
  public fun AttributionDialog(
    attributions: List<Attribution>,
    onDismissRequest: () -> Unit,
    onAttributionClick: (attribution: Attribution) -> Unit,
  ) {
    AlertDialog(
      onDismissRequest = onDismissRequest,
      modifier = Modifier.padding(start = 10.dp, end = 10.dp),
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
      properties = DialogProperties(
        // should be fully fixed in Compose 1.5, now using workaround from
        // https://issuetracker.google.com/issues/221643630
        usePlatformDefaultWidth = false
      )
    )
  }

  /**
   * Build a [TelemetryDialog] to be added to the [AttributionDialog] when telemetry settings is clicked.
   *
   * @param onDismissRequest The callback to be invoked when the attribution is dismissed.
   * @param onMoreInfo The callback to be invoked when more information is needed.
   * @param onDisagree The callback to be invoked when user disagrees with the telemetry collection.
   * @param onAgree The callback to be invoked when user agrees with the telemetry collection.
   */
  @Composable
  public fun TelemetryDialog(
    onDismissRequest: () -> Unit,
    onMoreInfo: () -> Unit,
    onDisagree: () -> Unit,
    onAgree: () -> Unit
  ) {
    UserConsentDialog(
      onDismissRequest = onDismissRequest,
      onMoreInfo = onMoreInfo,
      onDisagree = onDisagree,
      onAgree = onAgree,
      neutralStringId = R.string.mapbox_attributionTelemetryNeutral,
      disagreeStringId = R.string.mapbox_attributionTelemetryNegative,
      agreeStringId = R.string.mapbox_attributionTelemetryPositive,
      titleId = R.string.mapbox_attributionTelemetryTitle,
      bodyId = R.string.mapbox_attributionTelemetryMessage
    )
  }

  /**
   * Build a [GeofencingConsentDialog] to be added to the [AttributionDialog] when geofencing settings is clicked.
   *
   * @param onDismissRequest The callback to be invoked when the dialog is dismissed.
   * @param onDisagree The callback to be invoked when user revokes consent for geofencing.
   * @param onAgree The callback to be invoked when user grants consent for geofencing.
   * @param currentUserConsent The current state of the user consent.
   */
  @Composable
  public fun GeofencingConsentDialog(
    onDismissRequest: () -> Unit,
    onDisagree: () -> Unit,
    onAgree: () -> Unit,
    currentUserConsent: Boolean
  ) {
    val disagreeStringId =
      if (currentUserConsent) R.string.mapbox_attributionGeofencingConsentedNegative else R.string.mapbox_attributionGeofencingRevokedNegative
    val agreeStringId =
      if (currentUserConsent) R.string.mapbox_attributionGeofencingConsentedPositive else R.string.mapbox_attributionGeofencingRevokedPositive
    UserConsentDialog(
      onDismissRequest = onDismissRequest,
      onDisagree = onDisagree,
      onAgree = onAgree,
      disagreeStringId = disagreeStringId,
      agreeStringId = agreeStringId,
      titleId = R.string.mapbox_attributionGeofencingTitle,
      bodyId = R.string.mapbox_attributionGeofencingMessage
    )
  }

  @Composable
  private fun UserConsentDialog(
    onDismissRequest: () -> Unit,
    onMoreInfo: (() -> Unit)? = null,
    onDisagree: () -> Unit,
    onAgree: () -> Unit,
    @StringRes
    neutralStringId: Int? = null,
    @StringRes
    disagreeStringId: Int,
    @StringRes
    agreeStringId: Int,
    @StringRes
    titleId: Int,
    @StringRes
    bodyId: Int,
  ) {
    val dialogButton = @Composable { onClick: () -> Unit, textResId: Int ->
      TextButton(onClick) {
        Text(stringResource(id = textResId).uppercase())
      }
    }
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
          if (onMoreInfo != null && neutralStringId != null) {
            dialogButton(onMoreInfo, neutralStringId)
          }

          dialogButton(onDisagree, disagreeStringId)
          dialogButton(onAgree, agreeStringId)
        }
      },
      title = { Text(text = stringResource(id = titleId), fontWeight = FontWeight.Bold) },
      text = { Text(text = stringResource(id = bodyId)) }
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
    try {
      val intent = Intent(Intent.ACTION_VIEW)
      intent.data = Uri.parse(url)
      mapView.context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
      Toast.makeText(
        mapView.context,
        R.string.mapbox_attributionErrorNoBrowser,
        Toast.LENGTH_LONG
      ).show()
    } catch (t: Throwable) {
      Toast.makeText(
        mapView.context,
        t.localizedMessage,
        Toast.LENGTH_LONG
      ).show()
    }
  }

  private companion object {
    private const val TAG = "MapboxAttributionScope"
    private const val PLUGIN_ID = "MAPBOX_ATTRIBUTION_COMPOSE_PLUGIN"
    private var INSTANCE_COUNT = 0
    fun getNextId(): String {
      return "$PLUGIN_ID-${INSTANCE_COUNT++}"
    }
  }
}