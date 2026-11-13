package com.mapbox.maps.extension.compose.ornaments.attribution.internal

import android.content.Context
import android.util.AttributeSet
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.plugin.ContextBinder
import com.mapbox.maps.plugin.LifecyclePlugin
import com.mapbox.maps.plugin.MapPlugin
import com.mapbox.maps.plugin.consolidatedattribution.ConsolidatedAttributionController
import com.mapbox.maps.plugin.consolidatedattribution.ConsolidatedAttributionView
import com.mapbox.maps.plugin.delegates.MapDelegateProvider

/**
 * Drives a [ConsolidatedAttributionView] that the composition owns, rather than the MapView.
 *
 * The behaviour is the plugin's own [ConsolidatedAttributionController]; this class only feeds it
 * the map's delegates. Compose lays the control out with modifiers and measures the width the
 * pill may expand into, so nothing here positions or sizes it.
 */
@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
internal class ConsolidatedAttributionComposePlugin(
  private val view: ConsolidatedAttributionView,
) : MapPlugin, ContextBinder, LifecyclePlugin {

  internal val controller = ConsolidatedAttributionController(view)

  override fun onDelegateProvider(delegateProvider: MapDelegateProvider) {
    controller.onDelegateProvider(delegateProvider)
  }

  override fun bind(context: Context, attrs: AttributeSet?, pixelRatio: Float) {
    controller.attachTapHandling(context)
  }

  override fun initialize() {
    controller.initialize()
  }

  override fun onStart() = Unit

  override fun onStop() {
    controller.onStop()
  }

  override fun cleanup() {
    controller.cleanup()
  }

  internal companion object {
    private const val PLUGIN_ID = "MAPBOX_CONSOLIDATED_ATTRIBUTION_COMPOSE_PLUGIN"
    private var INSTANCE_COUNT = 0
    internal fun getNextId(): String = "$PLUGIN_ID-${INSTANCE_COUNT++}"
  }
}