package com.mapbox.maps.geofencing

import com.mapbox.common.geofencing.GeofencingUtilsUserConsentResponseCallback

/**
 * Definition of map Geofencing consent utilities
 */
interface MapGeofencingConsent {

  /**
   * Set the end-user consent for Geofencing to run.
   *
   * @param isConsentGiven true if user consents Geofencing, false if not (this will disable Geofencing).
   * @param callback Callback called when state is updated.
   */
  fun setUserConsent(isConsentGiven: Boolean, callback: GeofencingUtilsUserConsentResponseCallback)

  /**
   * Get the Geofencing user consent state.
   *
   * @return `true` if end-user has given consent of Geofencing, `false` otherwise. `true` by default.
   */
  fun getUserConsent(): Boolean

  /**
   * Checks if the Geofencing user consent dialog should be shown.
   *
   * @return `true` to show the option to opt-out Geofencing. That is, Geofencing is active or the user revoked the consent.
   */
  fun shouldShowConsent(): Boolean
}