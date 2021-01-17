package com.mapbox.maps.plugin.location

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.view.animation.LinearInterpolator
import com.mapbox.maps.StyleManagerInterface
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationComponentActivationOptionsTest {
  private val context: Context = mockk()
  private val array: TypedArray = mockk(relaxed = true)
  private val resources: Resources = mockk(relaxed = true)
  private val style: StyleManagerInterface = mockk()

  @Before
  fun setUp() {
    every {
      context.obtainStyledAttributes(
        R.style.mapbox_LocationComponent,
        R.styleable.mapbox_LocationComponent
      )
    } returns array
    every {
      array.getResourceId(
        R.styleable.mapbox_LocationComponent_mapbox_foregroundDrawable,
        -1
      )
    } returns R.drawable.mapbox_user_icon
    every { context.resources } returns resources
  }

  @Test
  fun sanity() {
    // TODO https://github.com/mapbox/mapbox-maps-android/issues/219
    // every { style.isFullyLoaded() } returns true
    val locationComponentOptions =
      LocationComponentOptions.builder(context)
        .accuracyAlpha(0.5f)
        .build()
    assertNotNull(locationComponentOptions)
    val locationComponentActivationOptions =
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(true)
        .build()
    assertNotNull(locationComponentActivationOptions)
  }

  @Test
  fun sanityWithDefaultPulsingCircle() {
    // TODO https://github.com/mapbox/mapbox-maps-android/issues/219
    // every { style.isFullyLoaded() } returns true
    val locationComponentOptions =
      LocationComponentOptions.builder(context)
        .accuracyAlpha(0.5f)
        .pulseEnabled(true)
        .build()
    assertNotNull(locationComponentOptions)
    val locationComponentActivationOptions =
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(true)
        .build()
    assertNotNull(locationComponentActivationOptions)
  }

  @Test
  fun sanityWithCustomizedPulsingCircle() {
    // TODO https://github.com/mapbox/mapbox-maps-android/issues/219
    // every { style.isFullyLoaded() } returns true
    val locationComponentOptions =
      LocationComponentOptions.builder(context)
        .accuracyAlpha(0.5f)
        .pulseEnabled(true)
        .pulseColor(Color.RED)
        .pulseInterpolator(LinearInterpolator())
        .build()
    assertNotNull(locationComponentOptions)
    val locationComponentActivationOptions =
      LocationComponentActivationOptions.builder(context, style)
        .locationComponentOptions(locationComponentOptions)
        .useDefaultLocationEngine(true)
        .build()
    assertNotNull(locationComponentActivationOptions)
  }

  @Test(expected = IllegalArgumentException::class)
  fun includingBothStyleResAndComponentOptions_causesExceptionToBeThrown() {
    val locationComponentOptions =
      LocationComponentOptions.builder(context)
        .accuracyAlpha(0.5f)
        .build()
    LocationComponentActivationOptions.builder(context, style)
      .locationComponentOptions(locationComponentOptions)
      .styleRes(R.style.mapbox_LocationComponent)
      .build()
  }

  // TODO https://github.com/mapbox/mapbox-maps-android/issues/219
  // @Test(expected = IllegalArgumentException::class)
  // fun locationComponent_exceptionThrownWithDefaultLocationEngineButNotFullyLoadedStyle() {
  // TODO https://github.com/mapbox/mapbox-maps-android/issues/219
  // every { style.isFullyLoaded() } returns false
  //  LocationComponentActivationOptions.builder(context, style).build()
  // }
}