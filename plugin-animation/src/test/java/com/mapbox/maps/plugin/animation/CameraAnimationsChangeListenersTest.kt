package com.mapbox.maps.plugin.animation

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraState
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImplTest.Companion.cameraState
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.math.pow

@RunWith(RobolectricTestRunner::class)
class CameraAnimationsChangeListenersTest {

  private fun CameraAnimationsPluginImpl.onCameraMove(cameraState: CameraState) {
    onCameraMove(
      lat = cameraState.center.latitude(),
      lon = cameraState.center.longitude(),
      zoom = cameraState.zoom,
      pitch = cameraState.pitch,
      bearing = cameraState.bearing,
      padding = cameraState.padding.let { insets ->
        arrayOf(insets.left, insets.top, insets.right, insets.bottom)
      }
    )
  }

  @Test
  fun addCameraZoomChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun removeCameraZoomChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun addCameraCenterChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener =
      CameraAnimatorChangeListener<Point> { updatedValue ->
        Assert.assertEquals(
          Point.fromLngLat(
            CameraAnimationsPluginImplTest.VALUE,
            CameraAnimationsPluginImplTest.VALUE
          ),
          updatedValue
        )
      }
    cameraAnimationsPluginImpl.addCameraCenterChangeListener(listener)
  }

  @Test
  fun removeCameraCenterChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Point> { assert(false) }
    cameraAnimationsPluginImpl.addCameraCenterChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraCenterChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun addCameraPitchChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun removeCameraPitchChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun addCameraBearingChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun removeCameraBearingChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun addCameraPaddingChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<EdgeInsets> {
      Assert.assertEquals(
        EdgeInsets(
          CameraAnimationsPluginImplTest.VALUE,
          CameraAnimationsPluginImplTest.VALUE,
          CameraAnimationsPluginImplTest.VALUE,
          CameraAnimationsPluginImplTest.VALUE
        ),
        it
      )
    }
    cameraAnimationsPluginImpl.addCameraPaddingChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun removeCameraPaddinghangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<EdgeInsets> { assert(false) }
    cameraAnimationsPluginImpl.addCameraPaddingChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraPaddingChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun addCameraAnchorChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorNullableChangeListener<ScreenCoordinate?> { updatedValue ->
      Assert.assertEquals(
        ScreenCoordinate(
          CameraAnimationsPluginImplTest.VALUE,
          CameraAnimationsPluginImplTest.VALUE
        ),
        updatedValue
      )
    }
    cameraAnimationsPluginImpl.addCameraAnchorChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }

  @Test
  fun removeCameraAnchorChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorNullableChangeListener<ScreenCoordinate?> { assert(false) }
    cameraAnimationsPluginImpl.addCameraAnchorChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraAnchorChangeListener(listener)
    cameraAnimationsPluginImpl.onCameraMove(cameraState)
  }
}