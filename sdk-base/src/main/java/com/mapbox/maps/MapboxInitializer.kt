package com.mapbox.maps

import android.content.Context
import android.os.Build
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import com.mapbox.maps.loader.MapboxMapsInitializer
import java.io.File

/**
 * Unified Mapbox SDKs initializer class that catches exceptions to avoid crashing during app
 * process start.
 *
 * Most of the crashes reported are related to [UnsatisfiedLinkError]
 * (https://github.com/mapbox/mapbox-maps-android/issues/1109).
 *
 * This solution is valid only when using Mapbox SDK for Android and no other Mapbox SDK (e.g.
 * Navigation, Search,...).
 *
 * In order to use this solution no other Mapbox SDK initializer should run (i.e.
 * [MapboxMapsInitializer] or [com.mapbox.common.MapboxSDKCommonInitializer]) during process start.
 * See the `sdk/src/main/AndroidManifest.xml` file.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
class MapboxInitializer : Initializer<Boolean> {

  /**
   * This code is run exactly one time on process startup.
   */
  override fun create(context: Context): Boolean {
    initializerCalledElapsedTime = SystemClock.elapsedRealtime()
    // try-catch to avoid terminating the whole process
    try {
      init(context)
    } catch (e: Throwable) {
      // Catch the exception, store it and log instead of propagating it. The app process will be
      // able to continue its start. The Mapbox SDK is not loaded and can't be used until `init`
      // function in the companion object is called. `MapView`, `MapSurface` and `Snapshotter` will
      // call the init by themselves.
      initializerFailure = e
      Log.w(TAG, "Exception occurred when initializing Mapbox: ${e.message}")
    }
    return true
  }

  /**
   * We do not need any dependencies here.
   */
  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }

  /**
   * Companion object for [MapboxInitializer] that holds some static state to keep track of
   * initialization state and also provides [init] that does the SDK native stack initialization.
   */
  companion object {
    private const val TAG = "MapboxInitializer"
    private var successfulInit = false
    private var currentAttempt = 0

    /**
     * Elapsed time since boot when [MapboxInitializer.create] was called or null if it was not
     * called.
     */
    internal var initializerCalledElapsedTime: Long? = null
      private set
    internal var initializerFailure: Throwable? = null
      private set

    /**
     * This function initializes Maps SDK native stack if it has not yet been done successfully.
     *
     * It can be called multiple times. If the native stack was already initialized successfully
     * then this is a no-op.
     *
     * If initialization process throws an exception we catch it and enhanced it with system
     * information (see [MapboxInitializerException]).
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
    @MainThread
    @JvmStatic
    @Throws(MapboxInitializerException::class)
    public fun init(context: Context) {
      if (successfulInit) {
        return
      }
      if (Looper.myLooper() != Looper.getMainLooper()) {
        throw RuntimeException("Mapbox must be called from main thread only!")
      }
      // we operate with application context to avoid memory leaks with Activity / View context
      val applicationContext = context.applicationContext
      Log.i(TAG, "MapboxInitializer started initialization, attempt ${++currentAttempt}")
      runCatchingEnhanced(applicationContext) {
        // it is enough to call only MapboxMapsInitializer as it has dependency on MapboxSDKCommonInitializer
        AppInitializer.getInstance(applicationContext)
          .initializeComponent(MapboxMapsInitializer::class.java)
        Log.i(TAG, "MapboxInitializer initialized Maps successfully")
      }
      successfulInit = true
    }

    /**
     * Runs the given [function]. If [function] throws a [Throwable] then a
     * [MapboxInitializerException] is thrown which contains extra information in it.
     */
    @Throws(MapboxInitializerException::class)
    private inline fun runCatchingEnhanced(context: Context, function: () -> Unit) {
      try {
        function()
      } catch (t: Throwable) {
        // if we got to this point there we are most likely hitting UnsatisfiedLinkError, re-throw an exception
        throw MapboxInitializerException(currentAttempt, context, t)
      }
    }
  }
}

internal class MapboxInitializerException(attempt: Int, context: Context, t: Throwable) :
  Throwable(gatherSystemInfo(attempt, context, t), t)

private fun gatherSystemInfo(attempt: Int, context: Context, t: Throwable): String {
  val isInstantApp = runCatching {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      context.packageManager?.isInstantApp
    } else {
      null
    }
  }
  val nativeLibs = runCatching {
    context.packageManager?.getApplicationInfo(context.packageName, 0)?.let { ai ->
      File(ai.nativeLibraryDir).list()?.joinToString() ?: ""
    }
  }
  val initializerCalledMsg = MapboxInitializer.initializerCalledElapsedTime?.let {
    // Log how long since the first time we tried to initialize during app process start. Or "null" if initializer was never called
    "initializer called ${SystemClock.elapsedRealtime() - it }ms ago"
  } ?: "initializer not called"

  return "Failed to initialize: Attempt=$attempt," +
    " exception=[${t.javaClass.simpleName}]," +
    " $initializerCalledMsg," +
    " initializerFailure=[${MapboxInitializer.initializerFailure?.javaClass?.simpleName}]," +
    // Most likely initializerFailure is MapboxInitializerException so try to find its cause
    " initializerFailure.cause=[${MapboxInitializer.initializerFailure?.cause?.javaClass?.simpleName}]," +
    " extractedNativeLibs=[${nativeLibs.getOrNull()}]," +
    " isInstantApp=[${isInstantApp.getOrNull()}],"
}