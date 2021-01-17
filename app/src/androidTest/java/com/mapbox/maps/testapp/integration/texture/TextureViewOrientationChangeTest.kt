package com.mapbox.maps.testapp.integration.texture

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.testapp.examples.TextureViewActivity
import com.mapbox.maps.testapp.integration.BaseIntegrationTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextureViewOrientationChangeTest : BaseIntegrationTest() {

  @get:Rule
  var activityRule: ActivityScenarioRule<TextureViewActivity> =
    ActivityScenarioRule(TextureViewActivity::class.java)

  @Test
  @LargeTest
  fun rotateTextureMap() {
    device.setOrientationLeft()
    device.waitForIdle()
    device.setOrientationNatural()
    device.waitForIdle()
    device.setOrientationRight()
    device.waitForIdle()
    device.setOrientationNatural()
    device.waitForIdle()
    device.setOrientationLeft()
    device.waitForIdle()
    device.setOrientationNatural()
    device.waitForIdle()
    device.setOrientationRight()
    device.waitForIdle()
    device.setOrientationNatural()
    device.waitForIdle()
  }
}