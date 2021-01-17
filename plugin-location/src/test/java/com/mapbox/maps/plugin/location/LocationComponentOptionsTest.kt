package com.mapbox.maps.plugin.location

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LocationComponentOptionsTest {

  private val context: Context = mockk()
  private val array: TypedArray = mockk(relaxed = true)
  private val resources: Resources = mockk(relaxed = true)

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
    val locationComponentOptions =
      LocationComponentOptions.builder(context)
        .accuracyAlpha(0.5f)
        .build()
    Assert.assertNotNull(locationComponentOptions)
  }

  @Test(expected = IllegalArgumentException::class)
  fun passingOutOfRangeAccuracyAlpha_throwsException() {
    LocationComponentOptions.builder(context)
      .accuracyAlpha(2f)
      .build()
  }

  @Test(expected = IllegalArgumentException::class)
  fun negativeElevation_causesExceptionToBeThrown() {
    LocationComponentOptions.builder(context)
      .elevation(-500f)
      .build()
  }

  @Test(expected = IllegalArgumentException::class)
  fun passingBothLayerPositionOptions_throwsException() {
    LocationComponentOptions.builder(context!!)
      .layerAbove("above")
      .layerBelow("below")
      .build()
  }
}