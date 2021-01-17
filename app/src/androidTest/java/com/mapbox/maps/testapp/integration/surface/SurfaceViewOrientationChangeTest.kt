package com.mapbox.maps.testapp.integration.surface

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.testapp.examples.SimpleMapActivity
import com.mapbox.maps.testapp.integration.BaseIntegrationTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurfaceViewOrientationChangeTest : BaseIntegrationTest() {

  @get:Rule
  var activityRule: ActivityScenarioRule<SimpleMapActivity> = ActivityScenarioRule(SimpleMapActivity::class.java)

  @Test
  @LargeTest
  fun rotateSurfaceMap() {
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