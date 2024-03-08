package com.mapbox.maps.testapp.attribution

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.hamcrest.core.Is

abstract class BaseAttributionThemeTest {

  protected fun clickAttribution() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.waitForIdle()
    Thread.sleep(2_000)
    Espresso.onView(
      ViewMatchers.withClassName(Is.`is`("com.mapbox.maps.plugin.attribution.AttributionViewImpl"))
    ).perform(
      ViewActions.click()
    )
    device.waitForIdle()
  }
}