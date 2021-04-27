package com.mapbox.maps.plugin.compass

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.MapAnimationOwnerRegistry
import com.mapbox.maps.plugin.delegates.MapCameraDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CompassViewPluginTest {

  private lateinit var compassPlugin: CompassPlugin
  private val compassView = mockk<CompassViewImpl>(relaxUnitFun = true)
  private val fadeAnimator = mockk<ValueAnimator>(relaxUnitFun = true)
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapCameraDelegate = mockk<MapCameraDelegate>(relaxUnitFun = true)
  private val animatePlugin = mockk<CameraAnimationsPlugin>(relaxed = true)
  private lateinit var fadeAnimatorEndListener: Animator.AnimatorListener
  private lateinit var fadeAnimatorUpdateListener: ValueAnimator.AnimatorUpdateListener

  @Before
  fun setUp() {
    val animatorEndListenerSlot = slot<Animator.AnimatorListener>()
    val updateListenerSlot = slot<ValueAnimator.AnimatorUpdateListener>()
    every { fadeAnimator.addListener(capture(animatorEndListenerSlot)) } answers {}
    every { fadeAnimator.addUpdateListener(capture(updateListenerSlot)) } answers {}
    every { fadeAnimator.setDuration(any()) } returns fadeAnimator
    every { delegateProvider.mapCameraDelegate } returns mapCameraDelegate
    every { mapCameraDelegate.getBearing() } returns 0.0
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 0f
    every { delegateProvider.mapPluginProviderDelegate.getPlugin(any<Class<CameraAnimationsPlugin>>()) } returns animatePlugin
    compassPlugin = CompassViewPlugin({ compassView }, fadeAnimator)
    compassPlugin.onPluginView(compassView)
    compassPlugin.onDelegateProvider(delegateProvider)
    fadeAnimatorEndListener = animatorEndListenerSlot.captured
    fadeAnimatorUpdateListener = updateListenerSlot.captured
  }

  @Test
  fun fadeAnimatorDuration() {
    verify { fadeAnimator.duration = 500L }
  }

  @Test
  fun fadeAnimatorDelay() {
    verify { fadeAnimator.startDelay = 500L }
  }

  @Test
  fun fadeAnimatorEndListener() {
    fadeAnimatorEndListener.onAnimationEnd(fadeAnimator)
    verify { compassView.isCompassVisible = false }
  }

  @Test
  fun fadeAnimatorValue() {
    compassPlugin.opacity = 0.5f
    every { fadeAnimator.animatedValue } returns 1.0f
    fadeAnimatorUpdateListener.onAnimationUpdate(fadeAnimator)
    verify(exactly = 0) { compassView.setCompassAlpha(1.0f) }

    every { fadeAnimator.animatedValue } returns 0.6f
    fadeAnimatorUpdateListener.onAnimationUpdate(fadeAnimator)
    verify(exactly = 0) { compassView.setCompassAlpha(0.6f) }

    every { fadeAnimator.animatedValue } returns 0.4f
    fadeAnimatorUpdateListener.onAnimationUpdate(fadeAnimator)
    verify(exactly = 1) { compassView.setCompassAlpha(0.4f) }

    every { fadeAnimator.animatedValue } returns 0.1f
    fadeAnimatorUpdateListener.onAnimationUpdate(fadeAnimator)
    verify(exactly = 1) { compassView.setCompassAlpha(0.1f) }
  }

  @Test
  fun fadeAnimatorUpdateListener() {
    every { fadeAnimator.animatedValue } returns 0.78f
    fadeAnimatorUpdateListener.onAnimationUpdate(fadeAnimator)
    verify { compassView.setCompassAlpha(0.78f) }
  }

  @Test
  fun getFadeCompassViewWhenFacingNorth() {
    assertTrue(compassPlugin.fadeWhenFacingNorth)
  }

  @Test
  fun setFadeCompassViewWhenFacingNorth() {
    every { compassView.isCompassEnabled } returns true
    compassPlugin.fadeWhenFacingNorth = false
    assertFalse(compassPlugin.fadeWhenFacingNorth)
  }

  @Test
  fun resetFadeCompassViewWhenFacingNorth_false() {
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 0.33f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -0.33,
      null,
      null
    )
    compassPlugin.fadeWhenFacingNorth = false
    verify { compassView.setCompassAlpha(1.0f) }
    verify { compassView.isCompassVisible = true }
  }

  @Test
  fun resetFadeCompassViewWhenFacingNorth_true() {
    every { compassView.isCompassEnabled } returns true
    compassPlugin.fadeWhenFacingNorth = false
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 0.33f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -0.33,
      null,
      null
    )
    compassPlugin.fadeWhenFacingNorth = true
    verify { fadeAnimator.start() }
  }

  @Test
  fun getCompassImage() {
    assertNull(compassPlugin.image)
  }

  @Test
  fun setCompassImage() {
    val image = mockk<Drawable>()
    compassPlugin.image = image
    verify { compassView.compassImage = image }
  }

  @Test
  fun getCompassGravity() {
    assertEquals(Gravity.TOP or Gravity.END, compassPlugin.position)
  }

  @Test
  fun setCompassGravity() {
    compassPlugin.position = Gravity.BOTTOM
    verify { compassView.compassGravity = Gravity.BOTTOM }
  }

  @Test
  fun getEnabled() {
    every { compassView.isCompassEnabled } returns true
    assertEquals(true, compassPlugin.enabled)
  }

  @Test
  fun setEnabled_true() {
    every { mapCameraDelegate.getBearing() } returns 10.0
    every { compassView.compassRotation } returns -10f
    every { compassView.isCompassEnabled } returns true
    compassPlugin.enabled = true
    verify { compassView.isCompassEnabled = true }
    verify { compassView.compassRotation = -(10.0).toFloat() }
    verify { compassView.setCompassAlpha(1.0f) }
    verify { compassView.isCompassVisible = true }
  }

  @Test
  fun setEnabled_true_hidden() {
    every { mapCameraDelegate.getBearing() } returns 0.0
    every { compassView.compassRotation } returns 0f
    every { compassView.isCompassEnabled } returns true
    compassPlugin.enabled = true
    verify { compassView.isCompassEnabled = true }
    verify { compassView.compassRotation = (-0.0).toFloat() }
    verify { compassView.setCompassAlpha(0.0f) }
    verify { compassView.isCompassVisible = false }
  }

  @Test
  fun setEnabled_false() {
    every { mapCameraDelegate.getBearing() } returns 0.0
    every { compassView.isCompassEnabled } returns false
    compassPlugin.enabled = false
    verify { compassView.isCompassEnabled = false }
    verify { compassView.compassRotation = (-0.0).toFloat() }
    verify { compassView.setCompassAlpha(0.0f) }
    verify { compassView.isCompassVisible = false }
  }

  @Test
  fun setEnabledWithMarginUpdate_false() {
    every { mapCameraDelegate.getBearing() } returns 0.0
    val enabledSlot = slot<Boolean>()
    every { compassView.isCompassEnabled = capture(enabledSlot) } answers { }
    compassPlugin.enabled = false
    verify { compassView.isCompassEnabled = false }
    verify { compassView.compassRotation = (-0.0).toFloat() }
    verify { compassView.setCompassAlpha(0.0f) }
    compassPlugin.marginLeft = 1f
    assertEquals(false, enabledSlot.captured)
  }

  @Test
  fun bind() {
    val mapView = mockk<FrameLayout>()
    every { mapView.context } returns mockk(relaxed = true)
    assertEquals(compassView, compassPlugin.bind(mapView, mockk(relaxUnitFun = true), 1.0f))
    verify { compassView.injectPresenter(compassPlugin) }
    verify { compassView.isCompassVisible = false }
  }

  @Test
  fun initialize() {
    every { mapCameraDelegate.getBearing() } returns 0.0
    every { compassView.compassRotation } returns 0f
    every { compassView.isCompassEnabled } returns true
    val attrs = mockk<AttributeSet>()
    compassPlugin.bind(mockk(relaxed = true), attrs, 1.0f)
    compassPlugin.initialize()
    verify { compassView.isCompassVisible = false }
    verify { compassView.compassGravity = any() }
    verify { compassView.compassRotation = (-0.0).toFloat() }
  }

  @Test
  fun cleanup() {
    val clickListener = mockk<OnCompassClickListener>()
    compassPlugin.addCompassClickListener(clickListener)
    compassPlugin.cleanup()
    verify { fadeAnimator.cancel() }
    verify { compassView.isCompassEnabled = false }
    compassPlugin.onCompassClicked()
    verify(exactly = 0) { clickListener.onCompassClick() }
  }

  @Test
  fun onStart() {
    every { mapCameraDelegate.getBearing() } returns 0.0
    every { compassView.compassRotation } returns 0f
    every { compassView.isCompassEnabled } returns true
    compassPlugin.onStart()
    verify { compassView.compassRotation = (-0.0).toFloat() }
  }

  @Test
  fun onStop() {
    compassPlugin.onStop()
    verify { fadeAnimator.cancel() }
  }

  @Test
  fun onCameraMove() {
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 15f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -15.0,
      null,
      null
    )
    verify { compassView.compassRotation = -(-15.0).toFloat() }
    verify { fadeAnimator.cancel() }
    verify { compassView.setCompassAlpha(1.0f) }
  }

  @Test
  fun onCameraMove_disabled() {
    every { compassView.isCompassEnabled } returns false
    every { compassView.compassRotation } returns 15f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -15.0,
      null,
      null
    )
    verify { compassView.compassRotation = -(-15.0).toFloat() }
    verify(exactly = 0) { fadeAnimator.cancel() }
    verify(exactly = 0) { compassView.setCompassAlpha(1.0f) }
  }

  @Test
  fun onCameraMove_north_hide() {
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 15f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -15.0,
      null,
      null
    )
    every { compassView.compassRotation } returns 0.33f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -0.33,
      null,
      null
    )
    every { compassView.compassRotation } returns 0.23f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -0.23,
      null,
      null
    )
    verify(exactly = 1) { fadeAnimator.start() }
  }

  @Test
  fun onCameraMove_north_doNotHide() {
    every { compassView.isCompassEnabled } returns true
    every { compassView.compassRotation } returns 15f
    compassPlugin.fadeWhenFacingNorth = false
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -15.0,
      null,
      null
    )
    every { compassView.compassRotation } returns 0.23f
    compassPlugin.onCameraMove(
      0.0,
      0.0,
      0.0,
      0.0,
      -0.23,
      null,
      null
    )
    verify(exactly = 0) { fadeAnimator.start() }
  }

  @Test
  fun setCompassMargins() {
    compassPlugin.updateSettings {
      marginLeft = 1f; marginTop = 2f; marginRight = 3f; marginBottom = 4f
    }
    verify { compassView.setCompassMargins(1, 2, 3, 4) }
    verify { compassView.requestLayout() }
  }

  @Test
  fun addCompassClickListener() {
    val clickListener = mockk<OnCompassClickListener>(relaxUnitFun = true)
    compassPlugin.addCompassClickListener(clickListener)
    compassPlugin.onCompassClicked()
    verify { clickListener.onCompassClick() }
  }

  @Test
  fun removeCompassClickListener() {
    val clickListener = mockk<OnCompassClickListener>()
    compassPlugin.addCompassClickListener(clickListener)
    compassPlugin.removeCompassClickListener(clickListener)
    compassPlugin.onCompassClicked()
    verify(exactly = 0) { clickListener.onCompassClick() }
  }

  @Test
  fun onCompassClicked() {
    compassPlugin.onCompassClicked()
    verify {
      animatePlugin.flyTo(
        any(),
        mapAnimationOptions {
          owner(MapAnimationOwnerRegistry.COMPASS)
          duration(300L)
        }
      )
    }
  }
}