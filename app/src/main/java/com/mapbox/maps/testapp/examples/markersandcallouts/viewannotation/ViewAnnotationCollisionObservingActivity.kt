package com.mapbox.maps.testapp.examples.markersandcallouts.viewannotation

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.ScreenBox
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.backgroundLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.mbxViewAnnotationCollisionBox
import com.mapbox.maps.viewannotation.viewAnnotationOptions

// Vertical shift of the red box in dp, shared by the pin layout and the avoid region.
private const val SHIFT_DP = 20

/**
 * Shows collision box observing: marking, moving, hiding and removing marked subviews updates the
 * collision boxes of the annotation automatically. An active box is visible as a hole in the "✕"
 * grid, because the symbols under the box are not drawn.
 *
 * The last switch creates an avoid zone below the red box. Shifting the red box into this zone
 * hides the annotation—the only way in this example to trigger collision-based hiding.
 *
 * Mirrors the iOS UIKit `ViewAnnotationCollisionObservingExample` and SwiftUI
 * `ViewAnnotationsCollisionObservingExample`, so the three can be compared side by side.
 */
@OptIn(MapboxExperimental::class)
class ViewAnnotationCollisionObservingActivity : AppCompatActivity() {

  private lateinit var pin: TwoBoxPinView
  private lateinit var mapView: MapView
  private var redShifted = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView = MapView(this)
    pin = TwoBoxPinView(this)

    val root = FrameLayout(this)
    root.addView(mapView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    root.addView(buildControls())
    setContentView(root)

    mapView.mapboxMap.setCamera(cameraOptions { center(CENTER); zoom(ZOOM) })
    mapView.mapboxMap.loadStyle(
      style(EMPTY_STYLE_JSON) {
        +backgroundLayer("bg") { backgroundColor("#FFFFFF") }
        +geoJsonSource("grid") { featureCollection(grid()) }
        +symbolLayer("grid-symbols", "grid") {
          textField("✕")
          textSize(14.0)
          textColor("#555555")
        }
      }
    )

    mapView.viewAnnotationManager.addViewAnnotation(
      view = pin,
      options = viewAnnotationOptions {
        geometry(CENTER)
        enableSymbolLayerCollision(true)
        // Enables avoid regions. Without it, symbol layer collision gives this annotation
        // priority, so it won't hide when moved into an avoid zone.
        enableAvoidRegions(true)
      }
    )
  }

  private fun buildControls(): View {
    val d = resources.displayMetrics.density
    val panel = LinearLayout(this).apply {
      orientation = LinearLayout.VERTICAL
      background = GradientDrawable().apply {
        cornerRadius = PANEL_CORNER_DP * d
        setColor(PANEL_COLOR)
      }
      val padH = (PANEL_PADDING_DP * d).toInt()
      val padV = (PANEL_PADDING_DP * d / 2).toInt()
      setPadding(padH, padV, padH, padV)
      layoutParams = FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        Gravity.BOTTOM
      ).apply { setMargins((16 * d).toInt(), 0, (16 * d).toInt(), (16 * d).toInt()) }
    }

    fun toggle(title: String, onChange: (Boolean) -> Unit) {
      panel.addView(
        SwitchCompat(this).apply {
          text = title
          setTextColor(Color.WHITE)
          textSize = ROW_TEXT_SP
          minHeight = 0
          minimumHeight = 0
          val rowPad = (ROW_PADDING_DP * d).toInt()
          setPadding(0, rowPad, 0, rowPad)
          setOnCheckedChangeListener { _, isChecked -> onChange(isChecked) }
        }
      )
    }

    toggle("Mark blue as collision box") { pin.blueBox.mbxViewAnnotationCollisionBox = it }
    toggle("Shift red box down") { redShifted = it; pin.setRedShifted(it) }
    toggle("Red box INVISIBLE (still collides)") { pin.setRedInvisible(it) }
    toggle("Remove red box (falls back to full bounds)") { pin.setRedRemoved(it) }
    toggle("Red box GONE (stops colliding)") { pin.setRedGone(it) }
    toggle("Avoid region below red box") { on ->
      mapView.viewAnnotationManager.viewAnnotationAvoidRegions = if (on) avoidRegionBelowRedBox() else emptyList()
    }
    return panel
  }

  /**
   * A small screen rectangle just below the red box. Turning this on and then shifting the red
   * box down moves the collision box into the region, so the map stops showing the annotation.
   * Shifting the red box back reports the corrected box and the annotation is shown again.
   */
  private fun avoidRegionBelowRedBox(): List<ScreenBox> {
    val d = resources.displayMetrics.density
    // Derived from the red box's own rectangle, so it also works when the layout is mirrored
    // in a right to left locale. The shift is taken out so the region lands at the same place
    // whichever switch is flipped first.
    val rect = Rect(0, 0, pin.redBox.width, pin.redBox.height)
    pin.offsetDescendantRectToMyCoords(pin.redBox, rect)
    val shift = if (redShifted) SHIFT_DP * d else 0f
    val top = pin.translationY + rect.bottom - shift + REGION_GAP_DP * d
    return listOf(
      ScreenBox(
        ScreenCoordinate((pin.translationX + rect.left).toDouble(), top.toDouble()),
        ScreenCoordinate(
          (pin.translationX + rect.right).toDouble(),
          (top + REGION_HEIGHT_DP * d).toDouble()
        )
      )
    )
  }

  private fun grid(): FeatureCollection {
    val features = mutableListOf<Feature>()
    for (row in -20..20) {
      for (column in -12..12) {
        features.add(
          Feature.fromGeometry(
            Point.fromLngLat(
              CENTER.longitude() + column * 0.0006,
              CENTER.latitude() + row * 0.0004
            )
          )
        )
      }
    }
    return FeatureCollection.fromFeatures(features)
  }

  private companion object {
    val CENTER: Point = Point.fromLngLat(-73.9866, 40.7306)
    const val ZOOM = 14.79
    const val EMPTY_STYLE_JSON =
      """{"version":8,"glyphs":"mapbox://fonts/mapbox/{fontstack}/{range}.pbf","sources":{},"layers":[]}"""
    const val REGION_GAP_DP = 5
    const val REGION_HEIGHT_DP = 30

    // Black at 60 percent, the same as the iOS example's control panel.
    const val PANEL_COLOR = 0x99000000.toInt()
    const val PANEL_CORNER_DP = 12f
    const val PANEL_PADDING_DP = 12
    const val ROW_PADDING_DP = 1
    const val ROW_TEXT_SP = 12f
  }
}

/**
 * Manual-layout pin with two circles; only the red one is marked as a collision box initially.
 * Mirrors the iOS example's pin: 30pt circles, 30pt gap, 10pt padding.
 */
@OptIn(MapboxExperimental::class)
private class TwoBoxPinView(context: Context) : FrameLayout(context) {
  val redBox: View = circle(Color.parseColor("#FF3B30"))
  val blueBox: View = circle(Color.parseColor("#007AFF"))

  private val d = resources.displayMetrics.density

  init {
    layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    clipChildren = false
    addView(redBox, boxParams(Gravity.START, marginDp = 10, topDp = 10))
    // Anchored to the end, so the pin shrinking to 50dp centers the blue circle (like the iOS pin).
    addView(blueBox, boxParams(Gravity.END, marginDp = 10, topDp = 10))
    redBox.mbxViewAnnotationCollisionBox = true
  }

  /** Fixed size like the iOS pin's `sizeThatFits`: 110x50, 50x50 without the red box. */
  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = if (redBox.parent == null) 50 else 110
    setMeasuredDimension((width * d).toInt(), (50 * d).toInt())
  }

  fun setRedShifted(shifted: Boolean) {
    redBox.layoutParams = boxParams(Gravity.START, marginDp = 10, topDp = 10 + (if (shifted) SHIFT_DP else 0))
  }

  fun setRedRemoved(removed: Boolean) {
    if (removed) removeView(redBox) else addView(redBox)
  }

  // Both toggles share View.visibility; keep them independent like iOS alpha/isHidden.
  private var redInvisible = false
  private var redGone = false

  fun setRedInvisible(invisible: Boolean) {
    redInvisible = invisible
    applyRedVisibility()
  }

  fun setRedGone(gone: Boolean) {
    redGone = gone
    applyRedVisibility()
  }

  private fun applyRedVisibility() {
    redBox.visibility = when {
      redGone -> View.GONE
      redInvisible -> View.INVISIBLE
      else -> View.VISIBLE
    }
  }

  private fun boxParams(gravity: Int, marginDp: Int, topDp: Int) =
    LayoutParams((30 * d).toInt(), (30 * d).toInt(), gravity or Gravity.TOP).apply {
      val margin = (marginDp * d).toInt()
      setMargins(margin, (topDp * d).toInt(), margin, 0)
    }

  private fun circle(color: Int) = View(context).apply {
    background = GradientDrawable().apply {
      shape = GradientDrawable.OVAL
      setColor(color)
    }
  }
}