package com.mapbox.maps.testapp.integration

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Before

abstract class BaseIntegrationTest {

  protected lateinit var device: UiDevice
  protected val testRepeatCount = 5

  @Before
  open fun beforeTest() {
    device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
  }
}

/**
 * Launches an activity with FLAG_ACTIVITY_NEW_TASK.
 * <p>
 * To resume an activity, you need to add a single instance launchmode to your manifest configuration.
 * <p>
 */
fun UiDevice.launchActivity(context: Context, clazz: Class<*>) {
  val applicationPackage = InstrumentationRegistry.getInstrumentation().targetContext.packageName
  val intent = Intent(context, clazz)
  intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
  context.startActivity(intent)
  wait(Until.hasObject(By.pkg(applicationPackage).depth(0)), 5000)
}