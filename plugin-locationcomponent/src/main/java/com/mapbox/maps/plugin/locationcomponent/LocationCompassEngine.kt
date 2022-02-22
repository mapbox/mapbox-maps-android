package com.mapbox.maps.plugin.locationcomponent

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import android.view.Surface
import android.view.WindowManager
import com.mapbox.common.Logger

/**
 * This class handles compass events and tracking the current device heading.
 */
internal class LocationCompassEngine(context: Context) : SensorEventListener {
  private val windowManager: WindowManager =
    context.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
  private val sensorManager: SensorManager =
    context.applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
  private val compassListeners = mutableSetOf<CompassListener>()

  // Not all devices have a compassSensor
  private var compassSensor: Sensor? = null
  private var gravitySensor: Sensor? = null
  private var magneticFieldSensor: Sensor? = null

  private val rotationMatrix = FloatArray(9)
  private var rotationVectorValue: FloatArray? = null
  private var compassUpdateNextTimestamp: Long = 0
  private var gravityValues = FloatArray(3)
  private var magneticValues = FloatArray(3)
  private val orientation = FloatArray(3)

  init {
    compassSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    if (compassSensor == null) {
      Logger.w(
        TAG,
        "Rotation vector sensor not supported on device, falling back to accelerometer and magnetic field."
      )
      gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
      magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }
  }

  internal fun addCompassListener(compassListener: CompassListener) {
    if (compassListeners.isEmpty()) {
      registerSensorListeners()
    }
    compassListeners.add(compassListener)
  }

  internal fun removeCompassListener(compassListener: CompassListener) {
    if (compassListeners.remove(compassListener) && compassListeners.isEmpty()) {
      unregisterSensorListeners()
    }
  }

  override fun onSensorChanged(event: SensorEvent) {
    when (event.sensor.type) {
      Sensor.TYPE_ROTATION_VECTOR -> {
        rotationVectorValue = event.values
      }
      Sensor.TYPE_ACCELEROMETER -> {
        gravityValues = lowPassFilter(event.values, gravityValues)
      }
      Sensor.TYPE_MAGNETIC_FIELD -> {
        magneticValues = lowPassFilter(event.values, magneticValues)
      }
    }
    updateOrientation()
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    // no need
  }

  private fun updateOrientation() {
    // check when the last time the compass was updated, return if too soon.
    val currentTime = SystemClock.elapsedRealtime()
    if (currentTime < compassUpdateNextTimestamp) {
      return
    }
    if (rotationVectorValue != null) {
      SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVectorValue)
    } else {
      // Get rotation matrix given the gravity and geomagnetic matrices
      SensorManager.getRotationMatrix(rotationMatrix, null, gravityValues, magneticValues)
    }
    val (worldAxisForDeviceAxisX, worldAxisForDeviceAxisY) = getWorldAxisFromRotation()
    val adjustedRotationMatrix = FloatArray(9)
    SensorManager.remapCoordinateSystem(
      rotationMatrix, worldAxisForDeviceAxisX,
      worldAxisForDeviceAxisY, adjustedRotationMatrix
    )

    // Transform rotation matrix into azimuth/pitch/roll
    SensorManager.getOrientation(adjustedRotationMatrix, orientation)
    val (adjustedWorldAxisForDeviceAxisX, adjustedWorldAxisForDeviceAxisY) = adjustWorldAxis(
      orientation,
      worldAxisForDeviceAxisX,
      worldAxisForDeviceAxisY
    )
    SensorManager.remapCoordinateSystem(
      rotationMatrix, adjustedWorldAxisForDeviceAxisX,
      adjustedWorldAxisForDeviceAxisY, adjustedRotationMatrix
    )

    // Transform rotation matrix into azimuth/pitch/roll
    SensorManager.getOrientation(adjustedRotationMatrix, orientation)

    // The x-axis is all we care about here.
    notifyCompassChangeListeners(Math.toDegrees(orientation[0].toDouble()).toFloat())

    // Update the compassUpdateNextTimestamp
    compassUpdateNextTimestamp = currentTime + LocationComponentConstants.COMPASS_UPDATE_RATE_MS
  }

  private fun adjustWorldAxis(
    orientation: FloatArray,
    worldAxisForDeviceAxisX: Int,
    worldAxisForDeviceAxisY: Int
  ): Pair<Int, Int> {
    var worldAxisForDeviceAxisX1 = worldAxisForDeviceAxisX
    var worldAxisForDeviceAxisY1 = worldAxisForDeviceAxisY
    when {
      orientation[1] < -Math.PI / 4 -> {
        // The pitch is less than -45 degrees.
        // Remap the axes as if the device screen was the instrument panel.
        when (windowManager.defaultDisplay.rotation) {
          Surface.ROTATION_90 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_Z
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_X
          }
          Surface.ROTATION_180 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_Z
          }
          Surface.ROTATION_270 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_Z
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_X
          }
          else -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_Z
          }
        }
      }
      orientation[1] > Math.PI / 4 -> {
        // The pitch is larger than 45 degrees.
        // Remap the axes as if the device screen was upside down and facing back.
        when (windowManager.defaultDisplay.rotation) {
          Surface.ROTATION_90 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_Z
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_X
          }
          Surface.ROTATION_180 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_Z
          }
          Surface.ROTATION_270 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_Z
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_X
          }
          else -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_Z
          }
        }
      }
      Math.abs(orientation[2]) > Math.PI / 2 -> {
        // The roll is less than -90 degrees, or is larger than 90 degrees.
        // Remap the axes as if the device screen was face down.
        when (windowManager.defaultDisplay.rotation) {
          Surface.ROTATION_90 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_Y
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_X
          }
          Surface.ROTATION_180 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_MINUS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_Y
          }
          Surface.ROTATION_270 -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_Y
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_X
          }
          else -> {
            worldAxisForDeviceAxisX1 = SensorManager.AXIS_X
            worldAxisForDeviceAxisY1 = SensorManager.AXIS_MINUS_Y
          }
        }
      }
    }
    return worldAxisForDeviceAxisX1 to worldAxisForDeviceAxisY1
  }

  private fun getWorldAxisFromRotation(): Pair<Int, Int> {
    var worldAxisForDeviceAxisX: Int
    var worldAxisForDeviceAxisY: Int
    when (windowManager.defaultDisplay.rotation) {
      Surface.ROTATION_90 -> {
        worldAxisForDeviceAxisX = SensorManager.AXIS_Y
        worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_X
      }
      Surface.ROTATION_180 -> {
        worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_X
        worldAxisForDeviceAxisY = SensorManager.AXIS_MINUS_Y
      }
      Surface.ROTATION_270 -> {
        worldAxisForDeviceAxisX = SensorManager.AXIS_MINUS_Y
        worldAxisForDeviceAxisY = SensorManager.AXIS_X
      }
      else -> {
        worldAxisForDeviceAxisX = SensorManager.AXIS_X
        worldAxisForDeviceAxisY = SensorManager.AXIS_Y
      }
    }
    return worldAxisForDeviceAxisX to worldAxisForDeviceAxisY
  }

  private fun notifyCompassChangeListeners(heading: Float) {
    for (compassListener in compassListeners) {
      compassListener.onCompassChanged(heading)
    }
  }

  private fun registerSensorListeners() {
    if (isCompassSensorAvailable()) {
      // Does nothing if the sensors already registered.
      sensorManager.registerListener(
        this,
        compassSensor,
        SENSOR_DELAY_MICROS
      )
    } else {
      sensorManager.registerListener(
        this,
        gravitySensor,
        SENSOR_DELAY_MICROS
      )
      sensorManager.registerListener(
        this,
        magneticFieldSensor,
        SENSOR_DELAY_MICROS
      )
    }
  }

  private fun unregisterSensorListeners() {
    if (isCompassSensorAvailable()) {
      sensorManager.unregisterListener(this, compassSensor)
    } else {
      sensorManager.unregisterListener(this, gravitySensor)
      sensorManager.unregisterListener(this, magneticFieldSensor)
    }
  }

  private fun isCompassSensorAvailable() = compassSensor != null

  /**
   * Helper function, that filters newValues, considering previous values
   *
   * @param newValues      array of float, that contains new data
   * @param smoothedValues array of float, that contains previous state
   * @return float filtered array of float
   */
  private fun lowPassFilter(newValues: FloatArray, smoothedValues: FloatArray?): FloatArray {
    if (smoothedValues == null) {
      return newValues
    }
    for (i in newValues.indices) {
      smoothedValues[i] += ALPHA * (newValues[i] - smoothedValues[i])
    }
    return smoothedValues
  }

  /**
   * Callback to receive compass update event
   */
  fun interface CompassListener {
    /**
     * Callback's invoked when a new compass update occurs.
     *
     * @param userHeading the new compass heading
     */
    fun onCompassChanged(userHeading: Float)
  }

  private companion object {
    private const val TAG = "LocationCompassProvider"

    // The rate sensor events will be delivered at. As the Android documentation states, this is only
    // a hint to the system and the events might actually be received faster or slower then this
    // specified rate. Since the minimum Android API levels about 9, we are able to set this value
    // ourselves rather than using one of the provided constants which deliver updates too quickly for
    // our use case. The default is set to 100ms
    private const val SENSOR_DELAY_MICROS = 100 * 1000

    // Filtering coefficient 0 < ALPHA < 1
    private const val ALPHA = 0.45f
  }
}