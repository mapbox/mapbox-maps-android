package com.mapbox.maps.plugin.scalebar

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.FrameLayout
import com.mapbox.maps.MapOptions
import com.mapbox.maps.Projection
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapListenerDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.plugin.scalebar.generated.ScaleBarAttributeParser
import io.mockk.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowProjection::class])
class ScaleBarPluginTest {

  private lateinit var scaleBarPlugin: ScaleBarPluginImpl
  private val scaleBarView = mockk<ScaleBarImpl>(relaxUnitFun = true)
  private val delegateProvider = mockk<MapDelegateProvider>(relaxUnitFun = true)
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>(relaxed = true)
  private val mapListenerManagerDelegate = mockk<MapListenerDelegate>(relaxed = true)
  private val mapTransformDelegate = mockk<MapTransformDelegate>()
  private val mapOptions = mockk<MapOptions>()
  private val mapView = mockk<FrameLayout>()
  private val context: Context = mockk(relaxed = true)
  private val typedArray: TypedArray = mockk(relaxUnitFun = true)
  private val drawable = mockk<Drawable>(relaxed = true)

  @Before
  fun setUp() {
    mockkStatic(Projection::class)
    every { Projection.getMetersPerPixelAtLatitude(any(), any()) } returns 2.0
    every { mapTransformDelegate.getMapOptions() } returns mapOptions
    every { mapOptions.pixelRatio } returns 1.0f
    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraDelegate
    every { delegateProvider.mapListenerDelegate } returns mapListenerManagerDelegate
    every { delegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { mapView.width } returns 1024
    every { mapView.context } returns context
    mockkObject(ScaleBarAttributeParser::class)
    every { context.obtainStyledAttributes(any(), any(), 0, 0) } returns typedArray
    every { typedArray.getString(any()) } returns "pk.token"
    every { typedArray.getBoolean(any(), any()) } returns true
    every { typedArray.getInt(any(), any()) } returns 100
    every { typedArray.getColor(any(), any()) } returns Color.RED
    every { typedArray.getDimension(any(), any()) } returns 10.0f
    every { typedArray.getFloat(any(), any()) } returns 10.0f
    every { typedArray.getDrawable(any()) } returns drawable
    every { typedArray.hasValue(any()) } returns true

    scaleBarPlugin = ScaleBarPluginImpl { scaleBarView }
    scaleBarPlugin.onDelegateProvider(delegateProvider)
    scaleBarPlugin.onPluginView(scaleBarView)
    scaleBarPlugin.initialize()
  }

  @After
  fun destroy() {
    scaleBarPlugin.cleanup()
  }

  @Test
  fun getEnabled() {
    every { scaleBarView.enable } returns true
    assertEquals(true, scaleBarPlugin.enabled)
  }

  @Test
  fun setEnabled_true() {
    scaleBarPlugin.enabled = true
    assertEquals(true, scaleBarPlugin.getSettings().enabled)
  }

  @Test
  fun setEnabled_false() {
    scaleBarPlugin.enabled = false
    assertEquals(false, scaleBarPlugin.getSettings().enabled)
  }

  @Test
  fun get_settings() {
    val settings = scaleBarPlugin.getSettings()
    assertEquals(2F, settings.height)
    assertEquals(Color.BLACK, settings.textColor)
  }

  @Test
  fun set_distancePerPixel() {
    scaleBarPlugin.distancePerPixel = 100F
    verify { scaleBarPlugin.distancePerPixel = 100F }
    verify { scaleBarView.distancePerPixel = 100F }
  }

  @Test
  fun set_useContinuousRendering() {
    scaleBarPlugin.useContinuousRendering = true
    verify { scaleBarPlugin.useContinuousRendering = true }
    verify { scaleBarView.useContinuousRendering = true }
  }
}