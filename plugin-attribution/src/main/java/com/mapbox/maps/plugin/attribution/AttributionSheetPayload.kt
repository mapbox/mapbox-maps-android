package com.mapbox.maps.plugin.attribution

import com.mapbox.maps.geofencing.MapGeofencingConsent
import com.mapbox.maps.module.MapTelemetry
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Everything [AttributionSheetView] renders, for one MapView.
 *
 * Holds live map objects, so it must not outlive the MapView that produced it:
 * [AttributionDialogManagerImpl.onStop] dismisses the sheet and drops this.
 */
internal class AttributionSheetPayload(
  val attributionList: List<Attribution>,
  val mapAttributionDelegate: MapAttributionDelegate,
  val telemetry: MapTelemetry?,
  val geofencingConsent: MapGeofencingConsent?,
)