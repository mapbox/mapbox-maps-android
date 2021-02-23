package com.mapbox.maps.plugin.animation

import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.CameraAnimationsPluginImplTest.Companion.cameraOptions
import org.junit.Assert
import org.junit.Test
import kotlin.math.pow

class CameraAnimationsChangeListenersTest {

  @Test
  fun addCameraZoomChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraZoomChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraZoomChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
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
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraCenterChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Point> { assert(false) }
    cameraAnimationsPluginImpl.addCameraCenterChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraCenterChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun addCameraPitchChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraPitchChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraPitchChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun addCameraBearingChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> {
      Assert.assertEquals(CameraAnimationsPluginImplTest.VALUE, it, 10.0.pow(-6.0))
    }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraBearingChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<Double> { assert(false) }
    cameraAnimationsPluginImpl.addCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraBearingChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
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
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraPaddinghangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorChangeListener<EdgeInsets> { assert(false) }
    cameraAnimationsPluginImpl.addCameraPaddingChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraPaddingChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
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
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }

  @Test
  fun removeCameraAnchorChangeListener() {
    val cameraAnimationsPluginImpl = CameraAnimationsPluginImpl()
    val listener = CameraAnimatorNullableChangeListener<ScreenCoordinate?> { assert(false) }
    cameraAnimationsPluginImpl.addCameraAnchorChangeListener(listener)
    cameraAnimationsPluginImpl.removeCameraAnchorChangeListener(listener)
    cameraAnimationsPluginImpl.notifyListeners(cameraOptions)
  }
}