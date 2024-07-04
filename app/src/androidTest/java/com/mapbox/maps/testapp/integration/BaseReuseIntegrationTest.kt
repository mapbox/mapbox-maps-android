package com.mapbox.maps.testapp.integration

import android.app.Activity
import android.view.KeyEvent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Rule
import org.junit.Test

abstract class BaseReuseIntegrationTest(activityClass: Class<out Activity>) {
  protected val device: UiDevice by lazy { UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()) }
  private val testRepeatCount = 5

  @get:Rule
  var activityRule = ActivityScenarioRule(activityClass)

  @Test
  @LargeTest
  fun reopenActivityWithHomeButton() {
    repeat(testRepeatCount) {
      device.waitForIdle()
      device.pressHome()
      device.waitForIdle()
      // double click restores last opened application
      device.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH)
      device.waitForIdle()
      device.pressKeyCode(KeyEvent.KEYCODE_APP_SWITCH)
      // some time to set things up
      Thread.sleep(1_000L)
    }
  }

  @Test
  @LargeTest
  fun reopenActivityWithPowerButton() {
    repeat(testRepeatCount) {
      device.waitForIdle()
      device.sleep()
      Thread.sleep(2_000)
      device.waitForIdle()
      device.wakeUp()
      Thread.sleep(2_000)
    }
  }

  @Test
  @LargeTest
  fun rotateActivity() {
    device.waitForIdle()
    device.setOrientationLeft()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationNatural()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationRight()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationNatural()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationLeft()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationNatural()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationRight()
    Thread.sleep(1_000)
    device.waitForIdle()
    device.setOrientationNatural()
    Thread.sleep(1_000)
    device.waitForIdle()
  }
}