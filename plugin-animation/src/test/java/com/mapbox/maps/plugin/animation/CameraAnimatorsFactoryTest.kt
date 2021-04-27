package com.mapbox.maps.plugin.animation

import android.view.animation.DecelerateInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Size
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class CameraAnimatorsFactoryTest {

  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var mapProjectionDelegate: MapProjectionDelegate
  private lateinit var cameraAnimatorsFactory: CameraAnimatorsFactory
  private val initialCenter = Point.fromLngLat(-0.11968, 51.50325)
  private val initialCameraPosition = CameraOptions.Builder().bearing(-0.0).pitch(15.0).zoom(0.0).center(initialCenter).build()
  private val decelerateInterpolatorNew = DecelerateInterpolator()
  private val delayNew = 20L
  private val durationNew = 50L

  @Before
  fun setUp() {
    ShadowLog.stream = System.out
    val delegateProvider = mockk<MapDelegateProvider>(relaxed = true)
    mapCameraManagerDelegate = mockk(relaxed = true)
    mapTransformDelegate = mockk(relaxed = true)
    mapProjectionDelegate = mockk(relaxed = true)

    every { delegateProvider.mapCameraManagerDelegate } returns mapCameraManagerDelegate
    every { delegateProvider.mapTransformDelegate } returns mapTransformDelegate
    every { delegateProvider.mapProjectionDelegate } returns mapProjectionDelegate
    every { mapCameraManagerDelegate.getCameraOptions(null) } returns initialCameraPosition
    cameraAnimatorsFactory = CameraAnimatorsFactory(delegateProvider)

    CameraAnimatorsFactory.setDefaultAnimatorOptions {
      this.interpolator = decelerateInterpolatorNew
      this.startDelay = delayNew
      this.duration = durationNew
    }
  }

  @Test
  fun testEaseToAnimators() {
    val targetEaseToPosition = CameraOptions.Builder()
      .center(Point.fromLngLat(-0.07520, 51.50550))
      .zoom(17.0)
      .bearing(180.0)
      .pitch(30.0)
      .build()
    val animators = cameraAnimatorsFactory.getEaseTo(targetEaseToPosition)
    targetEaseToPosition.bearing = -targetEaseToPosition.bearing!!
    testAnimators(animators, targetEaseToPosition)
  }

  @Test
  fun testMoveByAnimators() {
    every { mapCameraManagerDelegate.getCameraOptions(null) } returns initialCameraPosition
    val targetCenter = Point.fromLngLat(-0.12376717562057138, 51.50579407417868)
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns targetCenter
    val offset = ScreenCoordinate(500.0, 500.0)
    val target = CameraOptions.Builder().center(targetCenter).build()
    val animators = cameraAnimatorsFactory.getMoveBy(offset)
    testAnimators(animators, target)
  }

  @Test
  fun testRotateByAnimators() {
    every { mapCameraManagerDelegate.getCameraOptions(null) } returns initialCameraPosition
    every { mapTransformDelegate.getMapOptions() } returns MapOptions.Builder().size(Size(1078.875f, 1698.375f)).build()
    val target = CameraOptions.Builder().bearing(-25.981604850040434).build()
    val animators = cameraAnimatorsFactory.getRotateBy(ScreenCoordinate(0.0, 0.0), ScreenCoordinate(500.0, 500.0))
    testAnimators(animators, target)
  }

  @Test
  fun testScaleByAnimators() {
    every { mapCameraManagerDelegate.getCameraOptions(null) } returns initialCameraPosition
    val scaleBy = 15.0
    val zoomTarget = CameraTransform.calculateScaleBy(scaleBy, initialCameraPosition.zoom!!)
    val target = CameraOptions.Builder().zoom(zoomTarget).anchor(ScreenCoordinate(10.0, 10.0)).build()
    val animators = cameraAnimatorsFactory.getScaleBy(scaleBy, target.anchor)
    testAnimators(animators, target)
  }

  @Test
  fun testPitchByAnimators() {
    every { mapCameraManagerDelegate.getCameraOptions(null) } returns initialCameraPosition
    val pitchBy = 20.0
    val target = CameraOptions.Builder().pitch(initialCameraPosition.pitch!! + pitchBy).build()
    val animators = cameraAnimatorsFactory.getPitchBy(pitchBy)
    testAnimators(animators, target)
  }

  private fun testAnimators(animators: Array<CameraAnimator<*>>, targetCameraPosition: CameraOptions) {
    animators.forEach {
      val startValue = it.startValue
      val targetValue = it.targets.first()

      Assert.assertEquals(it.interpolator, decelerateInterpolatorNew)
      Assert.assertEquals(it.startDelay, delayNew)
      Assert.assertEquals(it.duration, durationNew)

      when (it.type) {
        CameraAnimatorType.BEARING -> {
          Assert.assertEquals(initialCameraPosition.bearing, startValue)
          Assert.assertEquals(targetCameraPosition.bearing!!, targetValue)
        }
        CameraAnimatorType.CENTER -> {
          Assert.assertEquals(initialCameraPosition.center, startValue)
          Assert.assertEquals(targetCameraPosition.center, targetValue)
        }
        CameraAnimatorType.PITCH -> {
          Assert.assertEquals(initialCameraPosition.pitch, startValue)
          Assert.assertEquals(targetCameraPosition.pitch, targetValue)
        }
        CameraAnimatorType.ZOOM -> {
          Assert.assertEquals(initialCameraPosition.zoom, startValue)
          Assert.assertEquals(targetCameraPosition.zoom, targetValue)
        }
        CameraAnimatorType.ANCHOR -> {
          Assert.assertEquals(targetCameraPosition.anchor, targetValue)
          Assert.assertEquals(targetCameraPosition.anchor, targetValue)
        }
        CameraAnimatorType.PADDING -> {
          Assert.assertEquals(initialCameraPosition.padding, startValue)
          Assert.assertEquals(targetCameraPosition.padding, targetValue)
        }
      }
    }
  }
}