package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.WindowManager
import com.mapbox.common.ShadowLogger
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowLogger::class])
class LocationCompassEngineTest {
  private val windowManager = mockk<WindowManager>()
  private val sensorManager = mockk<SensorManager>(relaxed = true)
  private val context = mockk<Context>()
  private val compassSensor = mockk<Sensor>()
  private val gravitySensor = mockk<Sensor>()
  private val magneticFieldSensor = mockk<Sensor>()
  private val compassListener = mockk<LocationCompassEngine.CompassListener>()
  private lateinit var locationCompassEngine: LocationCompassEngine

  @Before
  fun setUp() {
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns compassSensor
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns gravitySensor
    every { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) } returns magneticFieldSensor
    every { context.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
  }

  @Test
  fun supportRotationVectorSensor() {
    locationCompassEngine = LocationCompassEngine(context)
    verify(exactly = 1) { sensorManager.getDefaultSensor(any()) }
    verify(exactly = 0) { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    verify(exactly = 0) { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }
  }

  @Test
  fun notSupportRotationVectorSensor() {
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns null
    locationCompassEngine = LocationCompassEngine(context)
    verify(exactly = 1) { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    verify(exactly = 1) { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }
  }

  @Test
  fun addListenerWhileSupportRotationVectorSensor() {
    locationCompassEngine = LocationCompassEngine(context)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.addCompassListener(compassListener)
    verify(exactly = 1) { sensorManager.registerListener(any(), compassSensor, any()) }
    verify(exactly = 0) { sensorManager.registerListener(any(), gravitySensor, any()) }
    verify(exactly = 0) { sensorManager.registerListener(any(), magneticFieldSensor, any()) }
  }

  @Test
  fun removeListenerWhileSupportRotationVectorSensor() {
    locationCompassEngine = LocationCompassEngine(context)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.removeCompassListener(compassListener)
    locationCompassEngine.removeCompassListener(compassListener)
    verify(exactly = 1) { sensorManager.unregisterListener(any(), compassSensor) }
    verify(exactly = 0) { sensorManager.unregisterListener(any(), gravitySensor) }
    verify(exactly = 0) { sensorManager.unregisterListener(any(), magneticFieldSensor) }
  }

  @Test
  fun addListenerWhileNotSupportRotationVectorSensor() {
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns null
    locationCompassEngine = LocationCompassEngine(context)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.addCompassListener(compassListener)
    verify(exactly = 0) { sensorManager.registerListener(any(), compassSensor, any()) }
    verify(exactly = 1) { sensorManager.registerListener(any(), gravitySensor, any()) }
    verify(exactly = 1) { sensorManager.registerListener(any(), magneticFieldSensor, any()) }
  }

  @Test
  fun removeListenerWhileNotSupportRotationVectorSensor() {
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns null
    locationCompassEngine = LocationCompassEngine(context)
    locationCompassEngine.addCompassListener(compassListener)
    locationCompassEngine.removeCompassListener(compassListener)
    locationCompassEngine.removeCompassListener(compassListener)
    verify(exactly = 0) { sensorManager.unregisterListener(any(), compassSensor) }
    verify(exactly = 1) { sensorManager.unregisterListener(any(), gravitySensor) }
    verify(exactly = 1) { sensorManager.unregisterListener(any(), magneticFieldSensor) }
  }
}