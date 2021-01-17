package com.mapbox.maps.plugin.location

import android.graphics.Bitmap
import com.mapbox.maps.StyleManagerInterface
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.location.LocationComponentConstants.FOREGROUND_ICON
import com.mapbox.maps.plugin.location.LocationComponentConstants.FOREGROUND_LAYER
import com.mapbox.maps.plugin.location.listeneres.OnRenderModeChangedListener
import com.mapbox.maps.plugin.location.modes.RenderMode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LocationLayerControllerTest {

  private val delegateProvider: MapDelegateProvider = mockk(relaxed = true)
  private val style: StyleManagerInterface = mockk(relaxed = true)
  private val indicatorRenderer: IndicatorLocationLayerRenderer = mockk(relaxed = true)
  private val layerSourceProvider: LayerSourceProvider = mockk(relaxed = true)

  private val internalRenderModeChangedListener: OnRenderModeChangedListener = mockk(relaxed = true)
  private val options: LocationComponentOptions = mockk(relaxed = true)
  private val bitmapProvider: LayerBitmapProvider = mockk(relaxed = true)
  private val bitmap: Bitmap = mockk(relaxed = true)
  private val onIndicatorPositionChangedListener: OnIndicatorPositionChangedListener = mockk()

  @Before
  fun before() {
    every { delegateProvider.getStyle(any()) } answers {
      firstArg<(StyleManagerInterface) -> Unit>().invoke(style)
    }
    every { layerSourceProvider.getIndicatorLocationLayerRenderer() } returns indicatorRenderer

    val foregroundLayer: IndicatorLocationLayerWrapper = mockk(relaxed = true)
    every { foregroundLayer.layerId } returns FOREGROUND_LAYER
    every { layerSourceProvider.getIndicatorLocationLayer() } returns foregroundLayer
  }

  @Test
  fun onInitialization_foregroundLayerIsAdded() {
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify { indicatorRenderer.addLayers(any()) }
  }

  @Test
  fun applyStyle_styleShadowWithValidElevation() {
    every { bitmapProvider.generateShadowBitmap(any()) } returns bitmap
    every { options.elevation() } returns 2f
    // Style is applied on initialization
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = bitmap,
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = any(),
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_ignoreStyleShadowWithInvalidElevation() {
    every { bitmapProvider.generateShadowBitmap(any()) } returns bitmap
    every { options.elevation() } returns 0f

    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify(exactly = 0) {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = bitmap,
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = any(),
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_styleForegroundFromOptions() {
    val drawableResId = 123
    val tintColor = 456
    every { options.foregroundDrawable() } returns drawableResId
    every { options.foregroundTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = bitmap,
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_styleForegroundStaleFromOptions() {
    val drawableResId = 123
    val tintColor = 456
    every { options.foregroundDrawableStale() } returns drawableResId
    every { options.foregroundStaleTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = any(),
        foregroundStaleBitmap = bitmap
      )
    }
  }

  @Test
  fun applyStyle_styleBackgroundFromOptions() {
    val drawableResId = 123
    val tintColor = 456
    every { options.backgroundDrawable() } returns drawableResId
    every { options.backgroundTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = bitmap,
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = any(),
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_styleBackgroundStaleFromOptions() {
    val drawableResId = 123
    val tintColor = 456
    every { options.backgroundDrawableStale() } returns drawableResId
    every { options.backgroundStaleTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = bitmap,
        bearingBitmap = any(),
        foregroundBitmap = any(),
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_styleBearingFromOptions() {
    val drawableResId = 123
    val tintColor = 456
    every { options.bearingDrawable() } returns drawableResId
    every { options.bearingTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = bitmap,
        foregroundBitmap = any(),
        foregroundStaleBitmap = any()
      )
    }
  }

  @Test
  fun applyStyle_specializedLayer_ignoreBitmapNames() {
    every { options.foregroundName() } returns "new_name"
    LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.updateIconIds(
        FOREGROUND_ICON,
        any(),
        any(),
        any(),
        any()
      )
    }
  }

  @Test
  fun applyStyle_layerBelowNotChanged() {
    every { bitmapProvider.generateShadowBitmap(any()) } returns bitmap
    val layerBelow = "layer-below"
    every { options.layerBelow() } returns layerBelow
    val layerController = LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )

    layerController.applyStyle(options)
    verify(exactly = 0) { indicatorRenderer.removeLayers() }
    verify(exactly = 1) { indicatorRenderer.addLayers(any()) }
  }

  @Test
  fun renderModeChanged_doNotNotifyAboutDuplicates_NORMAL() {
    val layerController = LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    layerController.setRenderMode(RenderMode.NORMAL)
    layerController.setRenderMode(RenderMode.NORMAL)
    verify(exactly = 0) { internalRenderModeChangedListener.onRenderModeChanged(RenderMode.NORMAL) }
  }

  @Test
  fun renderModeChanged_doNotNotifyAboutDuplicates_GPS() {
    val layerController = LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    layerController.setRenderMode(RenderMode.GPS)
    layerController.setRenderMode(RenderMode.GPS)
    verify(exactly = 1) { internalRenderModeChangedListener.onRenderModeChanged(RenderMode.GPS) }
  }

  @Test
  fun layerHidden_renderModeChanged_layerShown_foregroundIconUpdated() {

    val drawableResId = 123
    val tintColor = 456
    every { options.foregroundDrawable() } returns drawableResId
    every { options.foregroundTintColor() } returns tintColor
    every { bitmapProvider.generateBitmap(drawableResId, tintColor) } returns bitmap
    val layerController = LocationLayerController(
      delegateProvider,
      style,
      layerSourceProvider,
      null,
      bitmapProvider,
      options,
      onIndicatorPositionChangedListener,
      internalRenderModeChangedListener
    )
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = bitmap,
        foregroundStaleBitmap = any()
      )
    }
    val drawableGpsResId = 789
    every { options.gpsDrawable() } returns drawableGpsResId
    val bitmapGps: Bitmap = mockk(relaxed = true)
    every { bitmapProvider.generateBitmap(drawableGpsResId, tintColor) } returns bitmapGps
    layerController.hide()
    layerController.setRenderMode(RenderMode.GPS)
    layerController.show()
    verify {
      indicatorRenderer.addBitmaps(
        renderMode = any(),
        shadowBitmap = any(),
        backgroundBitmap = any(),
        backgroundStaleBitmap = any(),
        bearingBitmap = any(),
        foregroundBitmap = bitmapGps,
        foregroundStaleBitmap = any()
      )
    }
  }
}