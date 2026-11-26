package com.mapbox.maps.plugin

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import java.lang.ref.WeakReference

/**
 * Defines interface of a ViewPlugin.
 */
fun interface ViewPlugin : MapPlugin {

  /**
   * Bind a view instance
   */
  fun bind(mapView: FrameLayout, attrs: AttributeSet?, pixelRatio: Float): View

  /**
   * Provides the view returned by [bind]. With the default Mapbox thread (the main thread) the view
   * has already been added to the MapView. With a non-main Mapbox thread it's added on the main
   * thread shortly after, so don't rely on its parent here.
   *
   * @param view plugin view
   */
  fun onPluginView(view: View) {
    // optional
  }

  /**
   * Initializer class wraps a bind configuration that can be used during plugin initialisation.
   *
   * @param context the context to bind to
   * @param attributes the attributes to derive configuratio from
   * @param pixelRatio the device pixel ratio
   */
  data class Initializer(val context: WeakReference<Context>, val attributes: TypedArray, val pixelRatio: Float)
}