package com.mapbox.maps.plugin.logo

import android.view.Gravity
import android.widget.FrameLayout
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LogoViewImplPluginTest {

  private lateinit var logoPlugin: LogoPlugin
  private val logoView = mockk<LogoViewImpl>(relaxUnitFun = true)
  private val delegateProvider = mockk<MapDelegateProvider>()
  private val mapCameraDelegate = mockk<MapCameraManagerDelegate>(relaxUnitFun = true)

  @Before
  fun setUp() {
    logoPlugin = LogoViewPlugin { logoView }
    logoPlugin.onPluginView(logoView)
    logoPlugin.onDelegateProvider(delegateProvider)
  }

  @Test
  fun getLogoGravity() {
    assertEquals(Gravity.BOTTOM or Gravity.START, logoPlugin.position)
  }

  @Test
  fun setLogoGravity() {
    logoPlugin.position = Gravity.BOTTOM
    verify { logoView.logoGravity = Gravity.BOTTOM }
  }

  @Test
  fun getEnabled() {
    every { logoView.logoEnabled } returns true
    assertEquals(true, logoPlugin.enabled)
  }

  @Test
  fun setEnabled_true() {
    every { mapCameraDelegate.cameraState.bearing } returns 10.0
    every { logoView.logoEnabled } returns true
    logoPlugin.enabled = true
    verify { logoView.logoEnabled = true }
  }

  @Test
  fun setEnabled_false() {
    every { mapCameraDelegate.cameraState.bearing } returns 0.0
    every { logoView.logoEnabled } returns false
    logoPlugin.enabled = false
    verify { logoView.logoEnabled = false }
  }

  @Test
  fun bind() {
    val mapView = mockk<FrameLayout>()
    every { mapView.context } returns mockk(relaxed = true)
    assertEquals(logoView, logoPlugin.bind(mapView, mockk(), 1.0f))
    verify { logoView.injectPresenter(logoPlugin) }
  }

  @Test
  fun setLogoMarginsViaSettings() {
    logoPlugin.updateSettings {
      marginLeft = 1f
      marginTop = 2f
      marginRight = 3f
      marginBottom = 4f
    }
    verify { logoView.setLogoMargins(1, 2, 3, 4) }
    verify { logoView.requestLayout() }
  }

  @Test
  fun setEnabledViaSettings() {
    logoPlugin.updateSettings {
      enabled = false
    }
    verify { logoView.logoEnabled = false }
  }

  @Test
  fun setLogoGravityViaSettings() {
    logoPlugin.updateSettings {
      position = Gravity.BOTTOM
    }
    verify { logoView.logoGravity = Gravity.BOTTOM }
  }
}