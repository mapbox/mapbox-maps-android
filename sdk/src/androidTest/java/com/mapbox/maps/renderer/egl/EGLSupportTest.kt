package com.mapbox.maps.renderer.egl

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mapbox.maps.EmptyActivity
import com.mapbox.maps.MapView
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EGLSupportTest {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  @UiThreadTest
  @Test
  fun testEglContext() {
    EGLCore(false, MapView.DEFAULT_ANTIALIASING_SAMPLE_COUNT).let { eglCore ->
      val eglConfigOk = eglCore.prepareEgl()
      val eglVersion = eglCore.queryContextVersion()

      eglCore.release()

      assertEquals(true, eglConfigOk)
      assertThat(eglVersion, isOneOf(3, 2))
    }
  }
}