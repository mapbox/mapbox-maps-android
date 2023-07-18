package com.mapbox.maps.testapp.utils

import android.app.Activity
import android.widget.Toast
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import java.lang.ref.WeakReference

class LocationPermissionHelper(val activityRef: WeakReference<Activity>) {
  private lateinit var permissionsManager: PermissionsManager

  fun checkPermissions(onMapReady: () -> Unit) {
    activityRef.get()?.let { activity: Activity ->
      if (PermissionsManager.areLocationPermissionsGranted(activity)) {
        onMapReady()
      } else {
        permissionsManager = PermissionsManager(object : PermissionsListener {

          override fun onExplanationNeeded(permissionsToExplain: List<String>) {
            activityRef.get()?.let {
              Toast.makeText(
                it, "You need to accept location permissions.",
                Toast.LENGTH_SHORT
              ).show()
            }
          }

          override fun onPermissionResult(granted: Boolean) {
            activityRef.get()?.let {
              if (granted) {
                onMapReady()
              } else {
                it.finish()
              }
            }
          }
        })
        permissionsManager.requestLocationPermissions(activity)
      }
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