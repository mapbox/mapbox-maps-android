package com.mapbox.maps.plugin.animation

import com.mapbox.maps.plugin.animation.CameraTransform.deg2rad
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class CameraTransformTest {

  @Before
  fun setUp() {
    ShadowLog.stream = System.out
  }

  @Test
  fun normalizeAngleRadians1() {
    val normalized = CameraTransform.normalizeAngleRadians(20.0.deg2rad(), 30.0.deg2rad())
    Assert.assertEquals(20.0.deg2rad(), normalized, EPS)
  }

  @Test
  fun normalizeAngleRadians2() {
    val normalized = CameraTransform.normalizeAngleRadians(20.0.deg2rad(), 320.0.deg2rad())
    Assert.assertEquals(380.0.deg2rad(), normalized, EPS)
  }

  @Test
  fun normalizeAngleRadians3() {
    val normalized = CameraTransform.normalizeAngleRadians(150.0.deg2rad(), 200.0.deg2rad())
    Assert.assertEquals(150.0.deg2rad(), normalized, EPS)
  }

  @Test
  fun normalizeAngleRadians4() {
    val normalized = CameraTransform.normalizeAngleRadians(150.0.deg2rad(), 335.0.deg2rad())
    Assert.assertEquals(510.0.deg2rad(), normalized, EPS)
  }

  companion object {
    private const val EPS = 0.00001
  }
}