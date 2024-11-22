package com.mapbox.maps

import com.mapbox.common.geofencing.GeofencingUtils
import com.mapbox.common.geofencing.GeofencingUtilsUserConsentResponseCallback
import com.mapbox.maps.geofencing.MapGeofencingConsent

@OptIn(MapboxExperimental::class, com.mapbox.annotation.MapboxExperimental::class)
internal class MapGeofencingConsentImpl : MapGeofencingConsent {
  override fun setUserConsent(
    isConsentGiven: Boolean,
    callback: GeofencingUtilsUserConsentResponseCallback
  ) {
    GeofencingUtils.setUserConsent(isConsentGiven, callback)
  }

  override fun getUserConsent(): Boolean = GeofencingUtils.getUserConsent()

  override fun shouldShowConsent(): Boolean {
    // We want to show the option to opt-out Geofencing if:
    // 1. the user explicitly revoked the user consent
    // 2. or if the geofencing engine is active
    return !GeofencingUtils.getUserConsent() || GeofencingUtils.isActive()
  }
}