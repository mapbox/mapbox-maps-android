package com.mapbox.maps.plugin.compass

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat

/**
 * Concrete implementation of CompassView.
 */
open class CompassViewImpl
/**
 * Constructor with context, attribute set and defStyleAttr.
 *
 * @param context
 * @param attrs The attribution set.
 * @param defStyleAttr An attribute in the current theme that contains a reference to a style
 *  resource that supplies defaults values for the StyledAttributes. Can be 0 to not look for defaults.
 */
@JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : CompassView, AppCompatImageView(context, attrs, defStyleAttr) {

  private lateinit var presenter: CompassPlugin

  init {
    ResourcesCompat.getDrawable(
      resources,
      R.drawable.mapbox_compass_icon,
      null
    )?.let { compassImage = it }

    // Layout params
    val compassViewSize = resources.getDimensionPixelSize(R.dimen.compass_view_size)
    layoutParams = FrameLayout.LayoutParams(compassViewSize, compassViewSize)

    // click listener
    setOnClickListener { presenter.onCompassClicked() }
  }

  /**
   * Whether the compass view is visible.
   */
  override var isCompassVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
      visibility = if (value) {
        View.VISIBLE
      } else {
        View.GONE
      }
    }

  /**
   * Returns the gravity value of the CompassView.
   */
  override var compassGravity: Int
    get() = (layoutParams as FrameLayout.LayoutParams).gravity
    set(value) {
      (layoutParams as FrameLayout.LayoutParams).gravity = value
    }

  /**
   * The direction of the CompassView.
   */
  override var compassRotation: Float
    get() = rotation
    set(value) {
      rotation = value
    }

  /**
   * Whether the compass view is enabled.
   */
  override var isCompassEnabled: Boolean
    get() = isEnabled
    set(value) {
      isEnabled = value
    }

  /**
   * The CompassView image as a Drawable.
   */
  override var compassImage: Drawable
    get() = drawable
    set(compass) {
      setImageDrawable(compass)
    }

  /**
   * Set the margins of the compass view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  override fun setCompassMargins(left: Int, top: Int, right: Int, bottom: Int) {
    (layoutParams as FrameLayout.LayoutParams).setMargins(left, top, right, bottom)
  }

  /**
   * Set the alpha value of the compass.
   *
   * @param float the alpha value
   */
  override fun setCompassAlpha(float: Float) {
    alpha = float
  }

  internal fun injectPresenter(presenter: CompassPlugin) {
    this.presenter = presenter
  }
}