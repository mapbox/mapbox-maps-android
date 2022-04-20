package com.mapbox.maps.plugin.logo

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.mapbox.maps.logW

/**
 * Concrete implementation of LogoView.
 */
class LogoViewImpl : LogoView, AppCompatImageView {

  private lateinit var presenter: LogoPlugin

  /**
   * Constructor with context.
   *
   * @param context
   */
  constructor(context: Context) : super(context)
  /**
   * Constructor with context and attribute set.
   *
   * @param context
   * @param attrs
   */
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
  /**
   * Constructor with context, attribute set and defStyleAttr.
   *
   * @param context
   * @param attrs The attribution set.
   * @param defStyleAttr An attribute in the current theme that contains a reference to a style
   *  resource that supplies defaults values for the StyledAttributes. Can be 0 to not look for defaults.
   */
  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  init {
    ResourcesCompat.getDrawable(
      resources,
      R.drawable.mapbox_logo_icon,
      null
    )?.let { setImageDrawable(it) }

    // Layout params
    val screenDensity = context.resources.displayMetrics.density
    layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    logoGravity = Gravity.START or Gravity.BOTTOM

    // Default margins
    val defaultMargin: Int = (4 * screenDensity).toInt()
    setLogoMargins(defaultMargin, defaultMargin, defaultMargin, defaultMargin)
  }

  /**
   * Whether the logo view is enabled.
   */
  override var logoEnabled: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
      visibility = if (value) View.VISIBLE else {
        logW("MbxLogo", context.getString(R.string.mapbox_warning_logo_disabled))
        View.GONE
      }
    }

  /**
   * Returns the gravity value of the logo view.
   */
  override var logoGravity: Int
    get() = (layoutParams as FrameLayout.LayoutParams).gravity
    set(value) {
      (layoutParams as FrameLayout.LayoutParams).gravity = value
    }

  /**
   * Set the margins of the logo view.
   *
   * @param left Margin to the left in pixel
   * @param top Margin to the top in pixel
   * @param right Margin to the right in pixel
   * @param bottom Margin to the bottom in pixel
   */
  override fun setLogoMargins(left: Int, top: Int, right: Int, bottom: Int) {
    (layoutParams as FrameLayout.LayoutParams).apply {
      setMargins(left, top, right, bottom)
      // Support RTL
      marginStart = left
      marginEnd = right
    }
  }

  internal fun injectPresenter(presenter: LogoPlugin) {
    this.presenter = presenter
  }
}