package com.mapbox.maps

import android.opengl.GLES20
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(Parameterized::class)
@LargeTest
class RenderTest(
  private val useTexture: Boolean,
  private val eventNeedRender: Boolean,
) {

  @get:Rule
  val rule = ActivityScenarioRule(EmptyActivity::class.java)

  @Test
  fun userQueueEventNeedRender() {
    val latch = CountDownLatch(1)
    var openGlVersionString = ""
    rule.scenario.onActivity {
      it.runOnUiThread {
        val mapView = MapView(it, MapInitOptions(it, textureView = useTexture))
        mapView.queueEvent(
          {
            openGlVersionString = GLES20.glGetString(GLES20.GL_VERSION)
            // assume anything should already have OpenGL ES 3+...
            assert(openGlVersionString.contains("OpenGL ES 3."))
            latch.countDown()
          },
          needRender = eventNeedRender
        )
        it.frameLayout.addView(mapView)
        mapView.onStart()
      }
    }
    if (!latch.await(LATCH_TIMEOUT_SEC, TimeUnit.SECONDS)) {
      throw TimeoutException()
    }
  }

  companion object {
    private const val LATCH_TIMEOUT_SEC = 5L
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(/* useTexture */ true, /* eventNeedRender */ true),
      arrayOf(/* useTexture */ true, /* eventNeedRender */ false),
      arrayOf(/* useTexture */ false, /* eventNeedRender */ true),
      arrayOf(/* useTexture */ false, /* eventNeedRender */ false),
    )
  }
}