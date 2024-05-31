package com.mapbox.maps.testapp.style.light

import android.graphics.Color
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.light.generated.AmbientLight
import com.mapbox.maps.extension.style.light.generated.DirectionalLight
import com.mapbox.maps.extension.style.light.generated.ambientLight
import com.mapbox.maps.extension.style.light.generated.directionalLight
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LightTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun testGetLight() {
    val ambientLight = ambientLight(AMBIENT_LIGHT_ID) {
      color(Color.WHITE)
      intensity(0.5)
    }
    val directionalLight = directionalLight(DIRECTIONAL_LIGHT_ID) {
      color(Color.YELLOW)
      intensity(0.9)
      castShadows(true)
      direction(listOf(0.0, 15.0))
    }

    setupLight(
      ambientLight,
      directionalLight,
    )

    Assert.assertTrue(getLight(DIRECTIONAL_LIGHT_ID) is DirectionalLight)
    Assert.assertTrue(getLight(AMBIENT_LIGHT_ID) is AmbientLight)
    Assert.assertEquals(0.9, (getLight(DIRECTIONAL_LIGHT_ID) as DirectionalLight).intensity!!, ESP)
    Assert.assertEquals(0.5, (getLight(AMBIENT_LIGHT_ID) as AmbientLight).intensity!!, ESP)
  }

  private companion object {
    private const val AMBIENT_LIGHT_ID = "AMBIENT_LIGHT_ID"
    private const val DIRECTIONAL_LIGHT_ID = "DIRECTIONAL_LIGHT_ID"
    private const val ESP = 0.000001
  }
}