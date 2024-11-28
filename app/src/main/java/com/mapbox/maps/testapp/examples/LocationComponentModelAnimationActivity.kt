package com.mapbox.maps.testapp.examples

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.core.constants.Constants
import com.mapbox.geojson.LineString
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.DefaultLocationProvider
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.locationcomponent.model.ModelMaterialPart
import com.mapbox.maps.plugin.locationcomponent.model.ModelNodePart
import com.mapbox.maps.plugin.locationcomponent.model.createAnimatableModel
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityLocationComponentBinding
import com.mapbox.maps.testapp.examples.OpenableNode.Companion.DEFAULT_OPEN_DEGREES
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import com.mapbox.maps.testapp.utils.LocationPermissionHelper
import com.mapbox.maps.testapp.utils.SimulateRouteLocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import kotlin.random.Random

/**
 * Showcase customise 3D location puck and animating the model parts, e.g. the wheel rotation, lights
 * on/off etc.
 *
 * Please note the model part override ids are corresponding to the specific 3d model, please update
 * according to your own model when using these API.
 */
@OptIn(MapboxExperimental::class)
class LocationComponentModelAnimationActivity : AppCompatActivity() {
  private var loadingJob: Job? = null

  private lateinit var locationPermissionHelper: LocationPermissionHelper
  private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
    // Jump to the current indicator position
    binding.mapView.mapboxMap.setCamera(CameraOptions.Builder().center(it).build())
    // Set the gestures plugin's focal point to the current indicator location.
    binding.mapView.gestures.focalPoint = binding.mapView.mapboxMap.pixelForCoordinate(it)
  }
  private lateinit var binding: ActivityLocationComponentBinding

  // Create overridable model node parts
  private val hood = OpenableNode(
    nodeOverride = MODEL_HOOD_ID,
    openRotation = listOf(DEFAULT_OPEN_DEGREES, 0.0, 0.0)
  )
  private val trunk = OpenableNode(
    nodeOverride = MODEL_TRUNK_ID,
    openRotation = listOf(-DEFAULT_OPEN_DEGREES, 0.0, 0.0)
  )
  private val frontLeftDoor = OpenableNode(
    nodeOverride = MODEL_FRONT_LEFT_DOOR_ID,
    openRotation = listOf(0.0, -DEFAULT_OPEN_DEGREES, 0.0)
  )
  private val frontRightDoor = OpenableNode(
    nodeOverride = MODEL_FRONT_RIGHT_DOOR_ID,
    openRotation = listOf(0.0, DEFAULT_OPEN_DEGREES, 0.0)
  )
  private val backLeftDoor = OpenableNode(
    nodeOverride = MODEL_BACK_LEFT_DOOR_ID,
    openRotation = listOf(0.0, -DEFAULT_OPEN_DEGREES, 0.0)
  )
  private val backRightDoor = OpenableNode(
    nodeOverride = MODEL_BACK_RIGHT_DOOR_ID,
    openRotation = listOf(0.0, DEFAULT_OPEN_DEGREES, 0.0)
  )
  private val frontLeftWheel = WheelNode(nodeOverride = MODEL_FRONT_LEFT_WHEEL_ID)
  private val frontRightWheel = WheelNode(nodeOverride = MODEL_FRONT_RIGHT_WHEEL_ID)
  private val backLeftWheel = WheelNode(nodeOverride = MODEL_BACK_LEFT_WHEEL_ID)
  private val backRightWheel = WheelNode(nodeOverride = MODEL_BACK_RIGHT_WHEEL_ID)

  // Create overridable model material parts
  private val frontLight: List<BaseLightMaterialPart> = listOf(
    BaseLightMaterialPart(
      materialOverride = MODEL_FRONT_LIGHT_ID,
      lightOnColor = Color.argb(255, 225, 225, 225),
      lightOffColor = Color.argb(255, 120, 120, 120)
    ),
    BaseLightVolume(
      materialOverride = MODEL_FRONT_LIGHT_VOLUME_ID,
      lightOnColor = Color.argb(255, 225, 225, 225),
      lightOffColor = Color.argb(255, 120, 120, 120)
    )
  )
  private val brakesLights: List<BaseLightMaterialPart> = listOf(
    BaseLightMaterialPart(
      featureStateId = "lights_brakes",
      materialOverrides = listOf(
        MODEL_BRAKES_LIGHT_ID,
        MODEL_BRAKES_LIGHT_REVERSE_ID
      ),
      lightOnColor = Color.argb(255, 225, 0, 0),
      lightOffColor = Color.argb(255, 120, 0, 0)
    ),
    BaseLightVolume(
      featureStateId = "lights_brakes_volume",
      materialOverrides = listOf(
        MODEL_BRAKES_LIGHT_VOLUME_ID,
        MODEL_BRAKES_LIGHT_REVERSE_VOLUME_ID
      ),
      lightOnColor = Color.argb(255, 225, 0, 0),
      lightOffColor = Color.argb(255, 120, 0, 0)
    )
  )
  private val leftTurnLights: List<BlinkerMaterialPart> = listOf(
    BlinkerMaterialPart(
      featureStateId = "indicator-light-left",
      materialOverrides = listOf(
        MODEL_FRONT_LEFT_TURN_LIGHT_ID,
        MODEL_BRAKE_LEFT_TURN_LIGHT_ID,
      ),
      lightOnColor = Color.argb(255, 250, 155, 0),
      lightOffColor = Color.argb(255, 160, 120, 0)
    ),
    BlinkerVolume(
      featureStateId = "indicator-light-left_volume",
      materialOverrides = listOf(
        MODEL_FRONT_LEFT_TURN_LIGHT_VOLUME_ID,
        MODEL_BRAKE_LEFT_TURN_LIGHT_VOLUME_ID,
        MODEL_LEFT_TURN_LIGHT_VOLUME_ID
      ),
      lightOnColor = Color.argb(255, 250, 155, 0),
      lightOffColor = Color.argb(255, 160, 120, 0)
    )
  )
  private val rightTurnLights: List<BlinkerMaterialPart> = listOf(
    BlinkerMaterialPart(
      featureStateId = "indicator-light-right",
      materialOverrides = listOf(
        MODEL_FRONT_RIGHT_TURN_LIGHT_ID,
        MODEL_BRAKE_RIGHT_TURN_LIGHT_ID
      ),
      lightOnColor = Color.argb(255, 250, 155, 0),
      lightOffColor = Color.argb(255, 160, 120, 0)
    ),
    BlinkerVolume(
      featureStateId = "indicator-light-right_volume",
      materialOverrides = listOf(
        MODEL_FRONT_RIGHT_TURN_LIGHT_VOLUME_ID,
        MODEL_BRAKE_RIGHT_TURN_LIGHT_VOLUME_ID,
        MODEL_RIGHT_TURN_LIGHT_VOLUME_ID
      ),
      lightOnColor = Color.argb(255, 250, 155, 0),
      lightOffColor = Color.argb(255, 160, 120, 0)
    )
  )
  private val body = ModelMaterialPart(
    materialOverride = MODEL_BODY_ID
  )
  private val modelParts = listOf(
    // node part overrides
    hood,
    trunk,
    frontLeftWheel,
    frontRightWheel,
    backLeftWheel,
    backRightWheel,
    frontLeftDoor,
    frontRightDoor,
    backLeftDoor,
    backRightDoor,
    // material part override for body
    body
  ) + listOf(
    // material parts overrides for lights
    brakesLights,
    frontLight,
    leftTurnLights,
    rightTurnLights,
  ).flatten()

  private val carModel by lazy {
    binding.mapView.location.createAnimatableModel(
      modelUri = MODEL_URI,
      modelParts = modelParts
    )
  }

  private var lastBearing: Double? = null

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_location_component_model_customisation, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_hood -> {
        hood.opened = !hood.opened
        return true
      }

      R.id.action_trunk -> {
        trunk.opened = !trunk.opened
        return true
      }

      R.id.action_doorsFrontLeft -> {
        frontLeftDoor.opened = !frontLeftDoor.opened
        return true
      }

      R.id.action_doorsFrontRight -> {
        frontRightDoor.opened = !frontRightDoor.opened
        return true
      }

      R.id.action_doorsBackLeft -> {
        backLeftDoor.opened = !backLeftDoor.opened
        return true
      }

      R.id.action_doorsBackRight -> {
        backRightDoor.opened = !backRightDoor.opened
        return true
      }

      R.id.action_headlights -> {
        val lightsOn = !frontLight.first().lightsOnOffState
        frontLight.forEach { it.lightsOnOffState = lightsOn }
        return true
      }

      R.id.action_breaking -> {
        val lightsOn = !brakesLights.first().lightsOnOffState
        brakesLights.forEach { it.lightsOnOffState = lightsOn }
        return true
      }

      R.id.action_indicatorLeft -> {
        val blinkerOn = !leftTurnLights.first().blinkerOn
        leftTurnLights.forEach { it.blinkerOn = blinkerOn }
        return true
      }

      R.id.action_indicatorRight -> {
        val blinkerOn = !rightTurnLights.first().blinkerOn
        rightTurnLights.forEach { it.blinkerOn = blinkerOn }
        return true
      }

      R.id.action_rotate_wheels -> {
        val rotationSpeed = 1.0 // 1 rotation per second
        val forwardRotation = WheelNode.Rotation.ForwardRotation(rotationSpeed)
        val backwardRotation = WheelNode.Rotation.BackwardRotation(rotationSpeed)
        when (frontLeftWheel.wheelRotation) {
          is WheelNode.Rotation.BackwardRotation -> {
            frontLeftWheel.wheelRotation = WheelNode.Rotation.Stopped
            frontRightWheel.wheelRotation = WheelNode.Rotation.Stopped
            backLeftWheel.wheelRotation = WheelNode.Rotation.Stopped
            backRightWheel.wheelRotation = WheelNode.Rotation.Stopped
          }

          is WheelNode.Rotation.ForwardRotation -> {
            frontLeftWheel.wheelRotation = backwardRotation
            frontRightWheel.wheelRotation = backwardRotation
            backLeftWheel.wheelRotation = backwardRotation
            backRightWheel.wheelRotation = backwardRotation
          }

          WheelNode.Rotation.Stopped -> {
            frontLeftWheel.wheelRotation = forwardRotation
            frontRightWheel.wheelRotation = forwardRotation
            backLeftWheel.wheelRotation = forwardRotation
            backRightWheel.wheelRotation = forwardRotation
          }
        }
        return true
      }

      R.id.action_body_color -> {
        body.updateColor(generateRandomColor())
        return true
      }

      R.id.action_simulate_route -> {
        if (binding.mapView.location.getLocationProvider() is DefaultLocationProvider) {
          loadingJob?.cancel()
          loadingJob = lifecycleScope.launch {
            val routePoints = withContext(Dispatchers.Default) {
              LineString.fromPolyline(
                DirectionsResponse.fromJson(
                  AnnotationUtils.loadStringFromAssets(
                    this@LocationComponentModelAnimationActivity,
                    NAVIGATION_ROUTE_JSON_NAME
                  )
                ).routes()[0].geometry()!!,
                Constants.PRECISION_6
              )
            }
            val simulateRouteLocationProvider = SimulateRouteLocationProvider(route = routePoints)
            binding.mapView.location.setLocationProvider(simulateRouteLocationProvider)
          }
        } else {
          binding.mapView.location.setLocationProvider(DefaultLocationProvider(this))
        }

        return true
      }

      else -> return super.onOptionsItemSelected(item)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLocationComponentBinding.inflate(layoutInflater)
    setContentView(binding.root)
    locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
    locationPermissionHelper.checkPermissions {
      binding.mapView.apply {
        mapboxMap.loadStyle(Style.STANDARD) {
          mapboxMap.setCamera(
            cameraOptions {
              pitch(60.0)
              zoom(14.0)
            }
          )
          // Disable scroll gesture, since we are updating the camera position based on the indicator location.
          gestures.scrollEnabled = false
          gestures.addOnMapClickListener { point ->
            location
              .isLocatedAt(point) { isPuckLocatedAtPoint ->
                if (isPuckLocatedAtPoint) {
                  Toast.makeText(context, "Clicked on location puck", Toast.LENGTH_SHORT).show()
                }
              }
            true
          }
          gestures.addOnMapLongClickListener { point ->
            location.isLocatedAt(point) { isPuckLocatedAtPoint ->
              if (isPuckLocatedAtPoint) {
                Toast.makeText(context, "Long-clicked on location puck", Toast.LENGTH_SHORT)
                  .show()
              }
            }
            true
          }
          val locationProvider = location.getLocationProvider() as DefaultLocationProvider
          locationProvider.addOnCompassCalibrationListener {
            Toast.makeText(context, "Compass needs to be calibrated", Toast.LENGTH_LONG).show()
          }
          setupLocationComponent()
        }
      }
    }
  }

  private fun setupLocationComponent() {
    binding.mapView.location.updateSettings {
      enabled = true
      locationPuck = carModel.getLocationPuck3D().copy(modelScale = listOf(70f, 70f, 70f))
      puckBearingEnabled = true
      puckBearing = PuckBearing.COURSE
    }
    // setup the initial 3D model wheels rotation
    val forwardRotation = WheelNode.Rotation.ForwardRotation(1.0)
    frontLeftWheel.wheelRotation = forwardRotation
    frontRightWheel.wheelRotation = forwardRotation
    backLeftWheel.wheelRotation = forwardRotation
    backRightWheel.wheelRotation = forwardRotation
    // Setup the lights
    frontLight.forEach { it.lightsOnOffState = true }
    brakesLights.forEach { it.lightsOnOffState = true }
    rightTurnLights.forEach { it.blinkerOn = false }
    leftTurnLights.forEach { it.blinkerOn = false }

    binding.mapView.location.addOnIndicatorBearingChangedListener { bearing ->
      // Adjust wheel direction based on bearing changes.
      lastBearing?.let {
        val scaleFactor = 60.0 // assuming animator is interpolated at 60 fps
        val diff = (it - bearing) * scaleFactor
        frontLeftWheel.wheelDirection = diff
        frontRightWheel.wheelDirection = diff
        if (diff > 40.0) {
          leftTurnLights.forEach { it.blinkForThreeSecond() }
          rightTurnLights.forEach { it.blinkerOn = false }
        } else if (diff < -40.0) {
          rightTurnLights.forEach { it.blinkForThreeSecond() }
          leftTurnLights.forEach { it.blinkerOn = false }
        }
      }
      lastBearing = bearing
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  override fun onStart() {
    super.onStart()
    binding.mapView.location
      .addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
  }

  override fun onStop() {
    super.onStop()
    binding.mapView.location
      .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
  }

  override fun onDestroy() {
    super.onDestroy()
    modelParts.filterIsInstance<AnimatablePart>().forEach { it.cleanUpAnimator() }
  }

  private companion object {
    private const val NAVIGATION_ROUTE_JSON_NAME = "navigation_route.json"
    private fun generateRandomColor(): Int {
      val rnd = Random.Default
      return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    private const val MODEL_URI = "asset://ego_car.glb"

    // IDs associated with the model
    // Model node override IDs
    private const val MODEL_HOOD_ID = "hood"
    private const val MODEL_TRUNK_ID = "trunk"
    private const val MODEL_FRONT_LEFT_DOOR_ID = "doors_front-left"
    private const val MODEL_FRONT_RIGHT_DOOR_ID = "doors_front-right"
    private const val MODEL_BACK_LEFT_DOOR_ID = "doors_back-left"
    private const val MODEL_BACK_RIGHT_DOOR_ID = "doors_back-right"
    private const val MODEL_FRONT_LEFT_WHEEL_ID = "wheels_front-left"
    private const val MODEL_FRONT_RIGHT_WHEEL_ID = "wheels_front-right"
    private const val MODEL_BACK_LEFT_WHEEL_ID = "wheels_back-left"
    private const val MODEL_BACK_RIGHT_WHEEL_ID = "wheels_back-right"

    // Model material override IDs
    private const val MODEL_FRONT_LIGHT_ID = "lights_front"
    private const val MODEL_FRONT_LIGHT_VOLUME_ID = "lights_front_volume"
    private const val MODEL_BRAKES_LIGHT_ID = "lights_brakes"
    private const val MODEL_BRAKES_LIGHT_VOLUME_ID = "lights_brakes_volume"
    private const val MODEL_BRAKES_LIGHT_REVERSE_ID = "lights-brakes_reverse"
    private const val MODEL_BRAKES_LIGHT_REVERSE_VOLUME_ID = "lights-brakes_reverse_volume"
    private const val MODEL_FRONT_LEFT_TURN_LIGHT_ID = "lights-front_turn-left"
    private const val MODEL_FRONT_LEFT_TURN_LIGHT_VOLUME_ID = "lights-front_turn-left_volume"
    private const val MODEL_BRAKE_LEFT_TURN_LIGHT_ID = "lights-brakes_turn-left"
    private const val MODEL_BRAKE_LEFT_TURN_LIGHT_VOLUME_ID = "lights-brakes_turn-left_volume"
    private const val MODEL_LEFT_TURN_LIGHT_VOLUME_ID = "lights_turn-left_volume"
    private const val MODEL_FRONT_RIGHT_TURN_LIGHT_ID = "lights-front_turn-right"
    private const val MODEL_FRONT_RIGHT_TURN_LIGHT_VOLUME_ID = "lights-front_turn-right_volume"
    private const val MODEL_BRAKE_RIGHT_TURN_LIGHT_ID = "lights-brakes_turn-right"
    private const val MODEL_BRAKE_RIGHT_TURN_LIGHT_VOLUME_ID = "lights-brakes_turn-right_volume"
    private const val MODEL_RIGHT_TURN_LIGHT_VOLUME_ID = "lights_turn-right_volume"
    private const val MODEL_BODY_ID = "body"
  }
}

/**
 * The part is driven by an animator, so we will need to clean up the animator when the activity is destroyed.
 */
interface AnimatablePart {
  /**
   * Clean up the animator.
   */
  fun cleanUpAnimator()
}

// Node overrides
/**
 * A [ModelNodePart] that represents a car wheel, additional animators are introduced
 * here to animate the rotation of the wheel based on the car's status.
 *
 * @param nodeOverride the node id to override
 */
@MapboxExperimental
class WheelNode(nodeOverride: String) : ModelNodePart(nodeOverride), AnimatablePart {
  private var currentWheelRotation: Double = 0.0
  private val animator = ValueAnimator().apply {
    setObjectValues(360f, 0f)
    addUpdateListener {
      currentWheelRotation = (it.animatedValue as Float).toDouble()
      rotation = listOf(currentWheelRotation, wheelDirection, 0.0)
    }
    interpolator = LinearInterpolator()
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.RESTART
    start()
    pause()
  }

  /**
   * The wheel's rotation status.
   */
  sealed class Rotation {
    /**
     * Wheel will rotate forward simulating driving forward
     *
     * @param speed rotations per second
     */
    class ForwardRotation(val speed: Double) : Rotation()

    /**
     * Wheel will rotate backward simulating driving backward
     *
     * @param speed rotations per second
     */
    class BackwardRotation(val speed: Double) : Rotation()

    /**
     * No rotation
     */
    object Stopped : Rotation()
  }

  /**
   * The rotation of the wheel.
   */
  var wheelRotation: Rotation = Rotation.Stopped
    set(value) {
      field = value
      when (value) {
        is Rotation.BackwardRotation -> {
          animator.setObjectValues(0f, 360f)
          animator.duration = (1000.0 / value.speed).toLong()
          animator.resume()
        }

        is Rotation.ForwardRotation -> {
          animator.setObjectValues(360f, 0f)
          animator.duration = (1000.0 / value.speed).toLong()
          animator.resume()
        }

        Rotation.Stopped -> animator.pause()
      }
    }
  var wheelDirection: Double = 0.0
    set(value) {
      field = value.coerceIn(-MAX_WHEEL_ROTATION_DEGREES, MAX_WHEEL_ROTATION_DEGREES)
      rotation = listOf(currentWheelRotation, field, 0.0)
    }

  override fun cleanUpAnimator() {
    animator.cancel()
  }

  companion object {
    private const val MAX_WHEEL_ROTATION_DEGREES = 60.0
  }
}

/**
 * A [ModelNodePart] that is openable, meaning a [opened] state is available to switch between two
 * rotation states.
 *
 * @param nodeOverride the node id to override
 * @param openRotation the rotation(a list rotation around x, y and z axis in degrees) when the [opened] state is true.
 */
@MapboxExperimental
class OpenableNode(nodeOverride: String, private val openRotation: List<Double>) :
  ModelNodePart(nodeOverride) {
  private val defaultRotation = listOf(0.0, 0.0, 0.0)

  /**
   * Whether the node is opened or not.
   */
  var opened: Boolean = false
    set(value) {
      field = value
      if (value) {
        rotation = listOf(openRotation[0], openRotation[1], openRotation[2])
      } else {
        rotation = listOf(defaultRotation[0], defaultRotation[1], defaultRotation[2])
      }
    }

  companion object {
    const val DEFAULT_OPEN_DEGREES = 60.0
  }
}

// Material overrides
/**
 * A [ModelMaterialPart] that presents a light.
 *
 * @param featureStateId the feature state id to customise this part
 * @param materialOverrides the material ids to override
 * @param lightOnColor the color of the light when [lightsOnOffState] is true
 * @param lightOffColor the color of the light when [lightsOnOffState] is false
 */
@MapboxExperimental
open class BaseLightMaterialPart(
  featureStateId: String,
  materialOverrides: List<String>,
  private val lightOnColor: Int = Color.argb(255, 225, 225, 225),
  private val lightOffColor: Int = Color.argb(255, 120, 0, 0)
) : ModelMaterialPart(featureStateId, materialOverrides) {
  /**
   * A [ModelMaterialPart] that presents a light.
   *
   * @param materialOverride the material id to override
   * @param lightOnColor the color of the light when [lightsOnOffState] is true
   * @param lightOffColor the color of the light when [lightsOnOffState] is false
   */
  constructor(
    materialOverride: String,
    lightOnColor: Int = Color.argb(255, 225, 225, 225),
    lightOffColor: Int = Color.argb(255, 120, 0, 0)
  ) : this(
    featureStateId = materialOverride,
    materialOverrides = listOf(materialOverride),
    lightOnColor = lightOnColor,
    lightOffColor = lightOffColor
  )

  /**
   * Whether the light is on or off.
   */
  var lightsOnOffState: Boolean = false
    set(value) {
      field = value
      updateLight(value)
    }

  open fun updateLight(on: Boolean) {
    if (on) {
      updateColor(lightOnColor)
      emissiveStrength = 0.8
    } else {
      updateColor(lightOffColor)
      emissiveStrength = 0.0
    }
  }
}

/**
 * A [ModelMaterialPart] that presents a light volume to show bloom effect.
 *
 * @param featureStateId the feature state id to customise this part
 * @param materialOverrides the material ids to override
 * @param lightOnColor the color of the light when [lightsOnOffState] is true
 * @param lightOffColor the color of the light when [lightsOnOffState] is false
 */
@MapboxExperimental
class BaseLightVolume(
  featureStateId: String,
  materialOverrides: List<String>,
  lightOnColor: Int = Color.argb(255, 225, 225, 225),
  lightOffColor: Int = Color.argb(255, 120, 0, 0)
) : BaseLightMaterialPart(
  featureStateId, materialOverrides, lightOnColor, lightOffColor
) {
  /**
   * A [ModelMaterialPart] that presents a light volume to show bloom effect.
   *
   * @param materialOverride the material id to override
   * @param lightOnColor the color of the light when [lightsOnOffState] is true
   * @param lightOffColor the color of the light when [lightsOnOffState] is false
   */
  constructor(
    materialOverride: String,
    lightOnColor: Int = Color.argb(255, 225, 225, 225),
    lightOffColor: Int = Color.argb(255, 120, 0, 0)
  ) : this(
    featureStateId = materialOverride,
    materialOverrides = listOf(materialOverride),
    lightOnColor = lightOnColor,
    lightOffColor = lightOffColor
  )

  override fun updateLight(on: Boolean) {
    super.updateLight(on)
    // update bloom effect
    opacity = if (on) 1.0 else 0.0
  }
}

/**
 * A [ModelMaterialPart] that presents a light.
 *
 * @param featureStateId the feature state id to customise this part
 * @param materialOverrides the material ids to override
 * @param lightOnColor the color of the light when [blinkerOn] is true
 * @param lightOffColor the color of the light when [blinkerOn] is false
 */
@MapboxExperimental
open class BlinkerMaterialPart(
  featureStateId: String,
  materialOverrides: List<String> = listOf(featureStateId),
  private val lightOnColor: Int = Color.argb(255, 250, 155, 0),
  private val lightOffColor: Int = Color.argb(255, 160, 120, 0)
) : ModelMaterialPart(featureStateId, materialOverrides), AnimatablePart {
  private val countdownTimer = object : CountDownTimer(3000, 1000) {
    override fun onTick(p0: Long) {
    }

    override fun onFinish() {
      blinkerOn = false
    }
  }
  private val animator = ValueAnimator().apply {
    setObjectValues(1f, 0f)
    addUpdateListener {
      onAnimatedValueUpdated(it.animatedValue as Float)
    }
    duration = 1000
    interpolator = LinearInterpolator()
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.RESTART
  }

  open fun onAnimatedValueUpdated(animatedValue: Float) {
    if (animatedValue > 0.5f) {
      updateColor(lightOnColor)
    } else {
      updateColor(lightOffColor)
    }
    emissiveStrength = animatedValue.toDouble()
  }

  open fun onBlinkerOff() {
    animator.cancel()
    updateColor(lightOffColor)
    emissiveStrength = 0.0
  }

  /**
   * Whether the blinker is on or off.
   */
  var blinkerOn: Boolean = false
    set(value) {
      field = value
      if (value) {
        animator.start()
      } else {
        onBlinkerOff()
      }
    }

  /**
   * Blink the lights for 3 seconds.
   */
  fun blinkForThreeSecond() {
    countdownTimer.cancel()
    countdownTimer.start()
    blinkerOn = true
  }

  override fun cleanUpAnimator() {
    animator.cancel()
  }
}

/**
 * A [ModelMaterialPart] that presents a light volume for blinker, the volume is used to render the
 * bloom effect of the blinker.
 *
 * @param featureStateId the feature state id to customise this part
 * @param materialOverrides the material ids to override
 * @param lightOnColor the color of the light when [blinkerOn] is true
 * @param lightOffColor the color of the light when [blinkerOn] is false
 */
@MapboxExperimental
class BlinkerVolume(
  featureStateId: String,
  materialOverrides: List<String> = listOf(featureStateId),
  lightOnColor: Int = Color.argb(255, 250, 155, 0),
  lightOffColor: Int = Color.argb(255, 160, 120, 0)
) : BlinkerMaterialPart(
  featureStateId, materialOverrides, lightOnColor, lightOffColor
) {
  override fun onAnimatedValueUpdated(animatedValue: Float) {
    super.onAnimatedValueUpdated(animatedValue)
    // Update bloom effect
    opacity = if (animatedValue > 0.5f) {
      1.0
    } else {
      0.0
    }
  }

  override fun onBlinkerOff() {
    super.onBlinkerOff()
    opacity = 0.0
  }
}