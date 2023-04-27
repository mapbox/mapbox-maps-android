package com.mapbox.maps.plugin.animation

import android.animation.Animator
import android.view.animation.DecelerateInterpolator
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.Size
import com.mapbox.maps.plugin.animation.animator.CameraAnimator
import com.mapbox.maps.plugin.delegates.MapCameraManagerDelegate
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.delegates.MapProjectionDelegate
import com.mapbox.maps.plugin.delegates.MapTransformDelegate
import com.mapbox.maps.toCameraOptions
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowLog
import org.robolectric.shadows.ShadowLooper.shadowMainLooper
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class CameraAnimatorsFactoryTest {

  private lateinit var mapTransformDelegate: MapTransformDelegate
  private lateinit var mapCameraManagerDelegate: MapCameraManagerDelegate
  private lateinit var mapProjectionDelegate: MapProjectionDelegate
  private lateinit var cameraAnimatorsFactory: CameraAnimatorsFactory
  private val initialCenter = Point.fromLngLat(-0.11968, 51.50325)
  private val initialCameraPosition = CameraState(
    initialCenter,
    EdgeInsets(0.0, 0.0, 0.0, 0.0),
    0.0,
    -0.0,
    15.0
  )
  private val decelerateInterpolatorNew = DecelerateInterpolator()
  private val delayNew = 20L
  private val durationNew = 50L
  private val customOwner = "customOwner"

  private val emptyListener = object : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }
  }

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
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
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
      .bearing(-180.0)
      .pitch(30.0)
      .build()
    val animators = cameraAnimatorsFactory.getEaseTo(targetEaseToPosition, owner = customOwner)
    testAnimators(animators, targetEaseToPosition)
  }

  @Test
  fun testMoveByAnimators() {
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
    val targetCenter = Point.fromLngLat(-0.12376717562057138, 51.50579407417868)
    every { mapCameraManagerDelegate.coordinateForPixel(any()) } returns targetCenter
    val offset = ScreenCoordinate(500.0, 500.0)
    val target = CameraOptions.Builder().center(targetCenter).build()
    val animators = cameraAnimatorsFactory.getMoveBy(offset, owner = customOwner)
    testAnimators(animators, target)
  }

  @Test
  fun testRotateByAnimators() {
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
    every { mapTransformDelegate.getMapOptions() } returns MapOptions.Builder().size(Size(1078.875f, 1698.375f)).build()
    val target = CameraOptions.Builder().bearing(-25.981604850040434).build()
    val animators = cameraAnimatorsFactory.getRotateBy(
      ScreenCoordinate(0.0, 0.0),
      ScreenCoordinate(500.0, 500.0),
      owner = customOwner
    )
    testAnimators(animators, target)
  }

  @Test
  fun testFlyToAnimatorsWithSinglePixelPadding() {
    val size = Size(800.0f, 600.0f)

    every { mapProjectionDelegate.unproject(any(), any()) } returns initialCenter
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
    every { mapTransformDelegate.getMapOptions() } returns MapOptions.Builder().size(size).build()
    every { mapTransformDelegate.getSize() } returns size

    val target = initialCameraPosition.toCameraOptions().toBuilder()
      .bearing(90.0)
      .padding(EdgeInsets(300.0, 400.0, 300.0, 400.0))
      .build()
    val animators = cameraAnimatorsFactory.getFlyTo(target, owner = customOwner)
    testAnimators(animators, target)
  }

  @Test
  fun testScaleByAnimators() {
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
    val scaleBy = 15.0
    val zoomTarget = CameraTransform.calculateScaleBy(scaleBy, initialCameraPosition.zoom)
    val target = CameraOptions.Builder().zoom(zoomTarget).anchor(ScreenCoordinate(10.0, 10.0)).build()
    val animators = cameraAnimatorsFactory.getScaleBy(scaleBy, target.anchor, owner = customOwner)
    testAnimators(animators, target)
  }

  @Test
  fun testPitchByAnimators() {
    every { mapCameraManagerDelegate.cameraState } returns initialCameraPosition
    val pitchBy = 20.0
    val target = CameraOptions.Builder().pitch(initialCameraPosition.pitch + pitchBy).build()
    val animators = cameraAnimatorsFactory.getPitchBy(pitchBy, owner = customOwner)
    testAnimators(animators, target)
  }

  private fun testAnimators(animators: Array<CameraAnimator<*>>, targetCameraPosition: CameraOptions) {
    animators.forEach {
      Assert.assertEquals(it.interpolator, decelerateInterpolatorNew)
      Assert.assertEquals(it.startDelay, delayNew)
      Assert.assertEquals(it.duration, durationNew)
      Assert.assertEquals(it.owner, customOwner)

      val startValue = it.startValue
      val targetValue = it.targets.first()

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
        }
        CameraAnimatorType.PADDING -> {
          Assert.assertEquals(initialCameraPosition.padding, startValue)
          Assert.assertEquals(targetCameraPosition.padding, targetValue)
        }
      }
    }

    animators.forEach {
      it.addInternalListener(emptyListener)
      it.start()
    }

    shadowMainLooper().idleFor(Duration.ofMillis(delayNew + durationNew))

    animators.forEach {
      val startValue = it.startValue
      val targetValue = it.animatedValue

      when (it.type) {
        CameraAnimatorType.BEARING -> {
          Assert.assertEquals(initialCameraPosition.bearing, startValue)
          Assert.assertEquals(targetCameraPosition.bearing!!, targetValue)
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
        }
        CameraAnimatorType.PADDING -> {
          Assert.assertEquals(initialCameraPosition.padding, startValue)
          Assert.assertEquals(targetCameraPosition.padding, targetValue)
        }
        CameraAnimatorType.CENTER -> {
          Assert.assertEquals(initialCameraPosition.center, startValue)
          Assert.assertEquals(targetCameraPosition.center, targetValue)
        }
      }
    }
  }
}

@RunWith(Parameterized::class)
class WrapCoordinateTest(
  private val actual: Triple<Double, Double, Double>,
  private val expected: Double
) {

  private companion object {
    @JvmStatic
    @Parameterized.Parameters
    fun data() = listOf(
      arrayOf(Triple(-0.911, -LONGITUDE_MAX, LONGITUDE_MAX), -0.911),
      arrayOf(Triple(LONGITUDE_MAX, -LONGITUDE_MAX, LONGITUDE_MAX), -LONGITUDE_MAX),
      arrayOf(Triple(-LONGITUDE_MAX, -LONGITUDE_MAX, LONGITUDE_MAX), -LONGITUDE_MAX),
      arrayOf(Triple(275.0, -LONGITUDE_MAX, LONGITUDE_MAX), -85.0),
      arrayOf(Triple(-458.0, -LONGITUDE_MAX, LONGITUDE_MAX), -98.0),
      arrayOf(Triple(385.0, -LONGITUDE_MAX, LONGITUDE_MAX), 25.0),
      arrayOf(Triple(0.0, LONGITUDE_MAX, LONGITUDE_MAX), Double.NaN),
    )

    private const val DELTA = 0.0001
    private const val LONGITUDE_MAX = 180.0
  }

  @Test
  fun testLongitudeWrap() {
    Assert.assertEquals(
      expected,
      CameraTransform.wrap(actual.first, actual.second, actual.third),
      DELTA
    )
  }
}