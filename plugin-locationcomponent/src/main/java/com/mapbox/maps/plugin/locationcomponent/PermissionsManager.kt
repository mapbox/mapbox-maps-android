package com.mapbox.maps.plugin.locationcomponent

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mapbox.maps.logW

/**
 * Helps request permissions at runtime.
 */
class PermissionsManager(private val listener: PermissionsListener) {

  /**
   * Type of accuracy granted by a user to the app.
   */
  enum class AccuracyAuthorization {
    /** No location permission granted.  */
    NONE,

    /** Provides the location accuracy that the ACCESS_FINE_LOCATION permission provides.  */
    PRECISE,

    /** Provides the location accuracy that the ACCESS_COARSE_LOCATION permission provides.  */
    APPROXIMATE
  }

  /**
   * Request location permissions to the user for the given [activity].
   *
   * Remember to call [onRequestPermissionsResult] from the [activity]'s own
   * [onRequestPermissionsResult][Activity.onRequestPermissionsResult]
   */
  fun requestLocationPermissions(activity: Activity) {
    try {
      @Suppress("DEPRECATION")
      val packageInfo = activity.packageManager.getPackageInfo(
        activity.packageName, PackageManager.GET_PERMISSIONS
      )
      val requestedPermissions: Array<String>? = packageInfo.requestedPermissions
      if (requestedPermissions != null) {
        val backgroundLocPermission = requestedPermissions.contains(
          BACKGROUND_LOCATION_PERMISSION
        )

        // Request location permissions
        if (requestedPermissions.contains(FINE_LOCATION_PERMISSION)) {
          requestLocationPermissions(activity, true, backgroundLocPermission)
        } else if (requestedPermissions.contains(COARSE_LOCATION_PERMISSION)) {
          requestLocationPermissions(activity, false, backgroundLocPermission)
        } else {
          logW(TAG, "Location permissions are missing")
        }
      }
    } catch (exception: Exception) {
      logW(TAG, exception.message!!)
    }
  }

  private fun requestLocationPermissions(
    activity: Activity,
    requestFineLocation: Boolean,
    requestBackgroundLocation: Boolean
  ) {
    val permissions: MutableList<String> = mutableListOf()
    if (requestFineLocation) {
      permissions.add(FINE_LOCATION_PERMISSION)
    } else {
      permissions.add(COARSE_LOCATION_PERMISSION)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && requestBackgroundLocation) {
      permissions.add(BACKGROUND_LOCATION_PERMISSION)
    }
    requestPermissions(activity, permissions.toTypedArray())
  }

  private fun requestPermissions(activity: Activity, permissions: Array<String>) {
    val permissionsToExplain = permissions.filter {
      ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
    }
    if (permissionsToExplain.isNotEmpty()) {
      // The developer should show an explanation to the user asynchronously
      listener.onExplanationNeeded(permissionsToExplain)
    }
    ActivityCompat.requestPermissions(activity, permissions, REQUEST_PERMISSIONS_CODE)
  }

  /**
   * You should call this method from your activity onRequestPermissionsResult.
   *
   * @param requestCode  The request code passed in requestPermissions(android.app.Activity, String[], int)
   * @param permissions  The requested permissions. Never null.
   * @param grantResults The grant results for the corresponding permissions which is either
   * PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
   */
  fun onRequestPermissionsResult(
    requestCode: Int,
    @Suppress("UNUSED_PARAMETER")
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    if (requestCode == REQUEST_PERMISSIONS_CODE) {
      val granted =
        grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
      listener.onPermissionResult(granted)
    }
  }

  /** Static variables and methods */
  companion object {
    private const val REQUEST_PERMISSIONS_CODE = 0
    private const val TAG = "PermissionsManager"
    private const val COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
    private const val FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    private const val BACKGROUND_LOCATION_PERMISSION =
      "android.permission.ACCESS_BACKGROUND_LOCATION"

    private fun isPermissionGranted(context: Context, permission: String): Boolean =
      ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    private fun isCoarseLocationPermissionGranted(context: Context): Boolean =
      isPermissionGranted(context, COARSE_LOCATION_PERMISSION)

    private fun isFineLocationPermissionGranted(context: Context): Boolean =
      isPermissionGranted(context, FINE_LOCATION_PERMISSION)

    /**
     * @param context The application context
     * @return true if user has granted background location permission
     * (i.e. [BACKGROUND_LOCATION_PERMISSION])
     */
    @JvmStatic
    fun isBackgroundLocationPermissionGranted(context: Context): Boolean =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        isPermissionGranted(context, BACKGROUND_LOCATION_PERMISSION)
      } else {
        areLocationPermissionsGranted(context)
      }

    /**
     *
     * @param context The application context
     * @return true if user has granted coarse, fine or both location permissions
     */
    @JvmStatic
    fun areLocationPermissionsGranted(context: Context): Boolean =
      isCoarseLocationPermissionGranted(context) || isFineLocationPermissionGranted(context)

    /**
     * @return true if requesting runtime permissions is required
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
    @JvmStatic
    fun areRuntimePermissionsRequired(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    /**
     * Returns the type of accuracy given by a user
     * to the application.
     *
     * @param context the application context
     * @return the type of accuracy authorization
     * @see AccuracyAuthorization
     */
    @JvmStatic
    fun accuracyAuthorization(context: Context): AccuracyAuthorization =
      if (isFineLocationPermissionGranted(context)) {
        AccuracyAuthorization.PRECISE
      } else if (isCoarseLocationPermissionGranted(context)) {
        AccuracyAuthorization.APPROXIMATE
      } else {
        AccuracyAuthorization.NONE
      }
  }
}