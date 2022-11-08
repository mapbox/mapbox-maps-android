package com.mapbox.maps.testapp.utils

import android.app.Activity
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import java.lang.ref.WeakReference

class LocationPermissionHelper(val activity: WeakReference<Activity>) {
  private lateinit var permissionsManager: PermissionsManager

  fun checkPermissions(onMapReady: () -> Unit) {
    if (PermissionsManager.areLocationPermissionsGranted(activity.get())) {
      onMapReady()
    } else {
      permissionsManager = PermissionsManager(object : PermissionsListener {
        override fun onExplanationNeeded(permissionsToExplain: List<String>) {
          Toast.makeText(
            activity.get(), "You need to accept location permissions.",
            Toast.LENGTH_SHORT
          ).show()
        }

        override fun onPermissionResult(granted: Boolean) {
          if (granted) {
            onMapReady()
          } else {
            activity.get()?.finish()
          }
        }
      })
      permissionsManager.requestLocationPermissions(activity.get())
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