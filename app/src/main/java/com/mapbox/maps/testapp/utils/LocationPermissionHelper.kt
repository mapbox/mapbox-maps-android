package com.mapbox.maps.testapp.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
        permissionsManager = createPermissionsManager(onMapReady)
        // TODO: temporary workaround to fix broken request foreground permissions
        val permissions: List<String> = buildList {
          add(Manifest.permission.ACCESS_FINE_LOCATION)
          add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), 0)
        // TODO: uncomment after the fix
        // permissionsManager.requestLocationPermissions(activity)
      }
    }
  }

  /**
   * PermissionsManager reads all location permission declared in Android manifest and request them all together
   * It doesn't work if we want to get background permission. In this case we need first request regular location permissions
   * and only then background permission
   */
  // TODO: move this logic to PermissionsManager
  fun checkBackgroundPermission(onMapReady: () -> Unit) {
    activityRef.get()?.let { activity: Activity ->
      if (PermissionsManager.areLocationPermissionsGranted(activity)) {
        if (PermissionsManager.isBackgroundLocationPermissionGranted(activity) || Build.VERSION.SDK_INT < 29) {
          onMapReady()
        } else {
          // Request background permission only
          permissionsManager = createPermissionsManager(
            onMapReady,
            onCustomPermissionResult = {
              activityRef.get()?.let {
                if (PermissionsManager.areLocationPermissionsGranted(activity) &&
                  PermissionsManager.isBackgroundLocationPermissionGranted(activity)
                ) {
                  onMapReady()
                } else {
                  it.finish()
                }
              }
            }
          )
          // TODO: make this call in PermissionsManager
          ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
            0
          )
        }
      } else {
        // request regular location permissions
        permissionsManager = createPermissionsManager(
          onMapReady,
          onCustomPermissionResult = { granted ->
            activityRef.get()?.let {
              if (granted) {
                checkBackgroundPermission(onMapReady = onMapReady)
              } else {
                it.finish()
              }
            }
          }
        )

        // TODO: make this call in PermissionsManager
        ActivityCompat.requestPermissions(
          activity,
          arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
          ),
          0
        )
      }
    }
  }

  fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    if (permissions.any { it == Manifest.permission.ACCESS_FINE_LOCATION || it == Manifest.permission.ACCESS_COARSE_LOCATION || it == Manifest.permission.ACCESS_BACKGROUND_LOCATION }) {
      if (::permissionsManager.isInitialized) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
      }
    }
  }

  /**
   * [onCustomPermissionResult] - PermissionsManager doesn't check result of background permission, so wee need to have custom logic here
   * //TODO: move this logic to PermissionsManager
   */
  private fun createPermissionsManager(
    onMapReady: () -> Unit,
    onCustomPermissionResult: ((Boolean) -> Unit)? = null
  ) =
    PermissionsManager(object : PermissionsListener {

      override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        activityRef.get()?.let {
          Toast.makeText(
            it, "You need to accept location permissions.",
            Toast.LENGTH_SHORT
          ).show()
        }
      }

      override fun onPermissionResult(granted: Boolean) {
        if (onCustomPermissionResult == null) {
          activityRef.get()?.let {
            if (granted) {
              onMapReady()
            } else {
              it.finish()
            }
          }
        } else {
          onCustomPermissionResult(granted)
        }
      }
    })
}