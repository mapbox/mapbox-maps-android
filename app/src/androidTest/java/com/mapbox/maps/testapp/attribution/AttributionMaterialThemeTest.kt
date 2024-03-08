package com.mapbox.maps.testapp.attribution

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.testapp.EmptyFragmentActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AttributionMaterialThemeTest : BaseAttributionThemeTest() {

  @get:Rule
  var activityRule: ActivityScenarioRule<EmptyFragmentActivity> = ActivityScenarioRule(
    EmptyFragmentActivity::class.java
  )
  @Test
  fun testMaterialTheme() {
    clickAttribution()
  }
}