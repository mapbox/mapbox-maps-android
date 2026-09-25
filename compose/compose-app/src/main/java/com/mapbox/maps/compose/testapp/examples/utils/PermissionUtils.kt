package com.mapbox.maps.compose.testapp.examples.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
public fun RequestLocationPermission(
  requestCount: Int = 0,
  onPermissionDenied: () -> Unit,
  onPermissionReady: () -> Unit
) {
  val context = LocalContext.current
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
  ) { permissionsMap ->
    val granted = permissionsMap.values.all { it }
    if (granted) {
      onPermissionReady()
    } else {
      onPermissionDenied()
    }
  }
  LaunchedEffect(requestCount) {
    context.checkAndRequestLocationPermission(
      locationPermissions,
      launcher,
      onPermissionReady
    )
  }
}

private fun Context.checkAndRequestLocationPermission(
  permissions: Array<String>,
  launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
  onPermissionReady: () -> Unit
) {
  if (permissions.all {
      ContextCompat.checkSelfPermission(
        this,
        it
      ) == PackageManager.PERMISSION_GRANTED
    }
  ) {
    onPermissionReady()
  } else {
    launcher.launch(permissions)
  }
}

private val locationPermissions = arrayOf(
  android.Manifest.permission.ACCESS_FINE_LOCATION,
  android.Manifest.permission.ACCESS_COARSE_LOCATION
)