package com.mapbox.maps.plugin.attribution

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.RestrictTo
import androidx.appcompat.view.ContextThemeWrapper
import com.mapbox.maps.plugin.delegates.MapAttributionDelegate

/**
 * Show the SDK's attribution sheet and return its [Dialog], or null if the activity is finishing.
 * Also used by the Compose extension.
 *
 * @suppress
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
fun showAttributionSheet(
  context: Context,
  mapAttributionDelegate: MapAttributionDelegate,
): Dialog? {
  val geofencingConsent = mapAttributionDelegate.geofencingConsent()
  return showAttributionSheetDialog(
    context = context,
    payload = AttributionSheetPayload(
      attributionList = mapAttributionDelegate.parseAttributions(
        context,
        AttributionParserConfig(
          withMapboxGeofencingConsent = geofencingConsent.shouldShowConsent()
        ),
      ),
      mapAttributionDelegate = mapAttributionDelegate,
      telemetry = mapAttributionDelegate.telemetry(),
      geofencingConsent = geofencingConsent,
    ),
  )
}

/**
 * Show the attribution sheet as a [Dialog], or return null if the activity is finishing, where
 * showing it would throw [android.view.WindowManager.BadTokenException].
 */
internal fun showAttributionSheetDialog(
  context: Context,
  payload: AttributionSheetPayload,
): Dialog? {
  // The map's context is usually a wrapper, so unwrap it rather than cast.
  context.findActivity()?.let { activity ->
    if (activity.isFinishing || activity.isDestroyed) {
      return null
    }
  }
  // The rows use AppCompat widgets.
  val themedContext = context.withAppCompatTheme()
  return Dialog(themedContext).apply {
    requestWindowFeature(Window.FEATURE_NO_TITLE)

    val sheet = AttributionSheetView(themedContext).apply {
      bind(payload = payload, onDismissRequest = ::dismiss)
    }

    setContentView(
      FrameLayout(themedContext).apply {
        addView(
          sheet,
          FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM,
          )
        )
        setOnClickListener { dismiss() }
        importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
      },
      FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT,
      )
    )

    window?.apply {
      setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT,
      )
      // The sheet draws its own background.
      setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      setWindowAnimations(android.R.style.Animation_InputMethod)
    }
    show()
  }
}

/**
 * This context if its theme is already AppCompat, otherwise wrapped in [fallbackTheme].
 */
@SuppressLint("PrivateResource")
internal fun Context.withAppCompatTheme(
  fallbackTheme: Int = androidx.appcompat.R.style.Theme_AppCompat_DayNight,
): Context {
  val attributes = obtainStyledAttributes(androidx.appcompat.R.styleable.AppCompatTheme)
  val appCompatThemeUsed = try {
    attributes.hasValue(androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar)
  } catch (_: Throwable) {
    false
  }
  attributes.recycle()
  return if (appCompatThemeUsed) {
    this
  } else {
    ContextThemeWrapper(this, fallbackTheme)
  }
}