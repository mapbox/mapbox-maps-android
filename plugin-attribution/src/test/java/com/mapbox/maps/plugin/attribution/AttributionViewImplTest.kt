package com.mapbox.maps.plugin.attribution

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.mapbox.common.ShadowLogger
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class AttributionViewImplTest {

  private lateinit var attributionView: AttributionViewImpl

  @Before
  fun setUp() {
    attributionView = AttributionViewImpl(ApplicationProvider.getApplicationContext())
  }

  @Test
  fun enabled() {
    attributionView.setEnable(false)
    assertEquals(View.GONE, attributionView.visibility)
    attributionView.setEnable(true)
    assertEquals(View.VISIBLE, attributionView.visibility)
  }

  @Test
  fun gravity() {
    attributionView.setGravity(Gravity.TOP)
    assertEquals(Gravity.TOP, (attributionView.layoutParams as FrameLayout.LayoutParams).gravity)
    attributionView.setGravity(Gravity.BOTTOM or Gravity.START)
    assertEquals(
      Gravity.BOTTOM or Gravity.START,
      (attributionView.layoutParams as FrameLayout.LayoutParams).gravity
    )
  }

  @Test
  fun setIconColor() {
    attributionView.setIconColor(Color.WHITE)
    // todo: how to verify this?
  }

  @Test
  fun setMargin() {
    attributionView.setAttributionMargins(1, 2, 3, 4)
    val layoutParams = attributionView.layoutParams as FrameLayout.LayoutParams
    assertEquals(1, layoutParams.marginStart)
    assertEquals(2, layoutParams.topMargin)
    assertEquals(3, layoutParams.marginEnd)
    assertEquals(4, layoutParams.bottomMargin)
  }

  @Test
  fun setOnClickListener() {
    assertFalse(attributionView.hasOnClickListeners())
    attributionView.setViewOnClickListener(mockk())
    assertTrue(attributionView.hasOnClickListeners())
  }
}