package com.mapbox.maps.testapp.attribution

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.testapp.TestMapActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AttributionAppCompatThemeTest : BaseAttributionThemeTest() {

  @get:Rule
  var activityRule: ActivityScenarioRule<TestMapActivity> = ActivityScenarioRule(
    TestMapActivity::class.java
  )
  @Test
  fun testAppCompatTheme() {
    clickAttribution()
  }
}