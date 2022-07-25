package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.WindowManager
import com.mapbox.maps.logW
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
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
    mockkStatic("com.mapbox.maps.MapboxLogger")
    every { logW(any(), any()) } just Runs
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) } returns compassSensor
    every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns gravitySensor
    every { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) } returns magneticFieldSensor
    every { context.applicationContext.getSystemService(Context.WINDOW_SERVICE) } returns windowManager
    every { context.applicationContext.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
  }

  @After
  fun cleanUp() {
    unmockkStatic("com.mapbox.maps.MapboxLogger")
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

  @Test
  fun calibrationListenerTest() {
    val calibrationListener = mockk<LocationCompassCalibrationListener>(relaxed = true)
    locationCompassEngine = LocationCompassEngine(context)
    locationCompassEngine.addCalibrationListener(calibrationListener)
    locationCompassEngine.onAccuracyChanged(mockk(), 100)
    verify(exactly = 0) { calibrationListener.onCompassCalibrationNeeded() }
    locationCompassEngine.onAccuracyChanged(mockk(), SensorManager.SENSOR_STATUS_UNRELIABLE)
    verify(exactly = 1) { calibrationListener.onCompassCalibrationNeeded() }
  }
}