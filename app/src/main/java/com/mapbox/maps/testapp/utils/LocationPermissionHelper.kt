package com.mapbox.maps.testapp.utils

import android.app.Activity
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class LocationPermissionHelper(val activity: Activity) {
  private lateinit var permissionsManager: PermissionsManager

  fun checkPermissions(onMapReady: () -> Unit) {
    if (PermissionsManager.areLocationPermissionsGranted(activity)) {
      onMapReady()
    } else {
      permissionsManager = PermissionsManager(object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
          Toast.makeText(
            activity, "You need to accept location permissions.",
            Toast.LENGTH_SHORT
          ).show()
        }

        override fun onPermissionResult(granted: Boolean) {
          if (granted) {
            onMapReady()
          } else {
            activity.finish()
          }
        }
      })
      permissionsManager.requestLocationPermissions(activity)
    }
  }

  fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }
}