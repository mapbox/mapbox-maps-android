package com.mapbox.maps.plugin.attribution

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat

/**
 * Concrete implementation of AttributionView from AttributionContract.
 */
class AttributionViewImpl @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : AttributionView, AppCompatImageView(context, attrs, defStyleAttr) {

  init {
    ResourcesCompat.getDrawable(resources, R.drawable.mapbox_attribution_selector, null)
      ?.let { setImageDrawable(it) }
    layoutParams = FrameLayout.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
  }

  /**
   * Set whether the attribution view is enabled.
   * @param enabled: the enable state
   */
  override fun setEnable(enabled: Boolean) {
    visibility = if (enabled) View.VISIBLE else View.GONE
  }

  /**
   * Set the gravity value of the attribution view.
   * @param gravity view's gravity
   */
  override fun setGravity(gravity: Int) {
    (layoutParams as FrameLayout.LayoutParams).gravity = gravity
  }

  /**
   * Set the attribution tint color
   * @param color the tint color
   */
  override fun setIconColor(color: Int) {
    ImageViewCompat.setImageTintList(
      this,
      ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf()),
        intArrayOf(color, color)
      )
    )
  }

  /**
   * Set the margins of the logo view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  override fun setAttributionMargins(left: Int, top: Int, right: Int, bottom: Int) {
    (layoutParams as FrameLayout.LayoutParams).setMargins(left, top, right, bottom)
  }

  /**
   * Set an [View.OnClickListener] to AttributionView
   */
  override fun setOnClickListener(listener: OnClickListener) {
    super.setOnClickListener(listener)
  }
}