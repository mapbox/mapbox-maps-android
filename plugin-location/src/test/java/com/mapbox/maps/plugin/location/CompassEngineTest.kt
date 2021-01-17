package com.mapbox.maps.plugin.location

import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.WindowManager
import com.mapbox.common.ShadowLogger
import com.mapbox.maps.plugin.location.LocationComponentCompassEngine.SENSOR_DELAY_MICROS
import com.mapbox.maps.plugin.location.listeneres.CompassListener
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
internal class CompassEngineTest {
  private var compassEngine: LocationComponentCompassEngine = mockk(relaxed = true)
  private val windowManager: WindowManager = mockk(relaxed = true)
  private val sensorManager: SensorManager = mockk(relaxed = true)
  private val compassSensor: Sensor = mockk(relaxed = true)
  private val compassListener: CompassListener = mockk(relaxed = true)

  @Before
  @Throws(Exception::class)
  fun setUp() {
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns compassSensor
    compassEngine = LocationComponentCompassEngine(windowManager, sensorManager)
  }

  @Test
  fun lastKnownCompassBearingAccuracyDefault() {
    assertEquals("Last accuracy should match", compassEngine.lastAccuracySensorStatus, 0)
  }

  @Test
  fun lastKnownCompassAccuracyStatusValue() {
    val sensor: Sensor = mockk(relaxed = true)
    compassEngine.onAccuracyChanged(sensor, 2)
    assertEquals("Last accuracy should match", compassEngine.lastAccuracySensorStatus, 2)
  }

  @Test
  fun whenGyroscopeIsNull_fallbackToGravity() {
    val sensorManager: SensorManager = mockk(relaxed = true)
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns null
    LocationComponentCompassEngine(windowManager, sensorManager)
    verify(exactly = 1) { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
  }

  @Test
  fun whenGyroscopeIsNull_fallbackToMagneticField() {
    val sensorManager: SensorManager = mockk(relaxed = true)
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns null
    LocationComponentCompassEngine(windowManager, sensorManager)
    verify(exactly = 1) { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }
  }

  @Test
  fun listener_registerOnAdd() {
    compassEngine.addCompassListener(compassListener)
    verify {
      sensorManager.registerListener(
        any(),
        compassSensor,
        SENSOR_DELAY_MICROS
      )
    }
  }

  @Test
  fun listener_unregisterOnRemove() {
    compassEngine.addCompassListener(compassListener)
    compassEngine.removeCompassListener(compassListener)
    verify {
      sensorManager.unregisterListener(
        any(),
        compassSensor
      )
    }
  }
}