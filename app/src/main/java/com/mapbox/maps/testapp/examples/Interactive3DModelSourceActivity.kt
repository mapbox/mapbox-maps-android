package com.mapbox.maps.testapp.examples

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.bindgen.Value
import com.mapbox.geojson.Point
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.style.layers.generated.modelLayer
import com.mapbox.maps.extension.style.layers.properties.generated.ModelType
import com.mapbox.maps.extension.style.light.dynamicLight
import com.mapbox.maps.extension.style.light.generated.ambientLight
import com.mapbox.maps.extension.style.light.generated.directionalLight
import com.mapbox.maps.extension.style.sources.generated.ModelSourceModel
import com.mapbox.maps.extension.style.sources.generated.modelMaterialOverride
import com.mapbox.maps.extension.style.sources.generated.modelNodeOverride
import com.mapbox.maps.extension.style.sources.generated.modelSource
import com.mapbox.maps.extension.style.sources.generated.modelSourceModel
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityInteractive3dModelSourceBinding

/**
 * Showcase interactive 3D model with source-based updates.
 * Demonstrates node overrides for doors/hood/trunk and material overrides for colors/lights.
 */
@MapboxExperimental
class Interactive3DModelSourceActivity : AppCompatActivity() {

  private lateinit var binding: ActivityInteractive3dModelSourceBinding

  // Vehicle parameters
  private var doorsFrontLeft = 0.5
  private var doorsFrontRight = 0.0
  private var trunk = 0.0
  private var hood = 0.0
  private var brakeLights = 0.0
  private var vehicleColor = Color.WHITE
  private lateinit var model: ModelSourceModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityInteractive3dModelSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Portrait shows the controls as a bottom sheet, landscape as a side panel
    // pinned to the leading edge, where a sheet would leave no usable map area.
    val isBottomSheet = resources.getBoolean(R.bool.control_panel_is_bottom_sheet)

    // The panel runs into the system bars on whichever edge it is pinned to.
    val basePaddingBottom = binding.controls.root.paddingBottom
    val basePaddingStart = binding.controls.root.paddingStart
    ViewCompat.setOnApplyWindowInsetsListener(binding.controls.root) { view, insets ->
      val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      view.updatePadding(
        left = basePaddingStart + if (isBottomSheet) 0 else bars.left,
        bottom = basePaddingBottom + bars.bottom
      )
      insets
    }

    model = createCarModel()

    binding.controlPanel.doOnLayout { panel ->
      if (isBottomSheet) {
        // Start with every control visible; the sheet can still be dragged down to
        // its peek height, and its contents scroll if they do not all fit.
        BottomSheetBehavior.from(panel).state = BottomSheetBehavior.STATE_EXPANDED
      }

      // Frame the car in the map area the panel leaves free: padding moves the
      // camera's focal point away from the covered edge, so the car is centred in
      // the visible region rather than hidden behind the controls.
      val bottomInset = if (isBottomSheet) panel.height.toDouble() else 0.0
      val leftInset = if (isBottomSheet) 0.0 else panel.width.toDouble()
      binding.mapView.mapboxMap.setCamera(
        cameraOptions {
          center(CAR_POSITION)
          zoom(CAR_ZOOM)
          bearing(45.0)
          pitch(60.0)
          padding(EdgeInsets(0.0, leftInset, bottomInset, 0.0))
        }
      )
    }

    binding.mapView.mapboxMap.loadStyle(
      style(Style.STANDARD) {
        +dynamicLight(
          ambientLight("environment") {
            intensity(0.4)
          },
          directionalLight("sun_light") {
            castShadows(true)
          }
        )
        +modelSource(SOURCE_ID) {
          models(listOf(model))
        }
        +modelLayer(LAYER_ID, SOURCE_ID) {
          modelScale(listOf(10.0, 10.0, 10.0))
          modelType(ModelType.LOCATION_INDICATOR)
        }
      }
    ) {
      binding.mapView.mapboxMap.setStyleImportConfigProperty(
        "basemap",
        "show3dObjects",
        Value.valueOf(false)
      )
      setupControls()
    }
  }

  // The sheet swallows touches without being a click target, so there is no click
  // for performClick to report.
  @SuppressLint("ClickableViewAccessibility")
  private fun setupControls() {
    // BottomSheetBehavior only intercepts vertical drags, so a horizontal swipe on
    // the sheet background would otherwise reach the MapView underneath and pan the
    // map. Swallow whatever the sheet's own controls did not handle. A touch
    // listener is used rather than android:clickable, which would make the sheet a
    // pressed view and push that pressed state onto every child slider.
    binding.controlPanel.setOnTouchListener { _, _ -> true }

    // Color picker view
    updateColorPickerBackground()
    binding.controls.colorPickerButton.setOnClickListener {
      showColorPickerDialog()
    }

    // Trunk slider
    binding.controls.sliderTrunk.addOnChangeListener { _, value, _ ->
      trunk = value.toDouble()
      model.nodeOverrides(
        listOf(
          modelNodeOverride("trunk") {
            orientation(listOf(mix(trunk, 0.0, -60.0), 0.0, 0.0))
          }
        )
      )
    }

    // Hood slider
    binding.controls.sliderHood.addOnChangeListener { _, value, _ ->
      hood = value.toDouble()
      model.nodeOverrides(
        listOf(
          modelNodeOverride("hood") {
            orientation(listOf(mix(hood, 0.0, 45.0), 0.0, 0.0))
          }
        )
      )
    }

    // Front left door slider
    binding.controls.sliderDoorLeft.addOnChangeListener { _, value, _ ->
      doorsFrontLeft = value.toDouble()
      model.nodeOverrides(
        listOf(
          modelNodeOverride("doors_front-left") {
            orientation(listOf(0.0, mix(doorsFrontLeft, 0.0, -80.0), 0.0))
          }
        )
      )
    }

    // Front right door slider
    binding.controls.sliderDoorRight.addOnChangeListener { _, value, _ ->
      doorsFrontRight = value.toDouble()
      model.nodeOverrides(
        listOf(
          modelNodeOverride("doors_front-right") {
            orientation(listOf(0.0, mix(doorsFrontRight, 0.0, 80.0), 0.0))
          }
        )
      )
    }

    // Brake lights slider
    binding.controls.sliderBrake.addOnChangeListener { _, value, _ ->
      brakeLights = value.toDouble()
      model.materialOverrides(
          listOf(
            modelMaterialOverride("lights_brakes") {
              modelColor(Color.rgb(224, 0, 0))
              modelColorMixIntensity(brakeLights)
              modelEmissiveStrength(brakeLights)
            },
            modelMaterialOverride("lights-brakes_reverse") {
              modelColor(Color.rgb(224, 0, 0))
              modelColorMixIntensity(brakeLights)
              modelEmissiveStrength(brakeLights)
            },
            modelMaterialOverride("lights_brakes_volume") {
              modelColor(Color.rgb(224, 0, 0))
              modelColorMixIntensity(1.0)
              modelEmissiveStrength(0.8)
              modelOpacity(brakeLights)
            },
            modelMaterialOverride("lights-brakes_reverse_volume") {
              modelColor(Color.rgb(224, 0, 0))
              modelColorMixIntensity(1.0)
              modelEmissiveStrength(0.8)
              modelOpacity(brakeLights)
            }
        )
      )
    }
  }

  private fun showColorPickerDialog() {
    val colors = intArrayOf(
      Color.WHITE,
      Color.BLACK,
      Color.RED,
      Color.rgb(0, 100, 200), // Blue
      Color.rgb(0, 150, 0), // Green
      Color.YELLOW,
      Color.rgb(150, 75, 0), // Brown
      Color.GRAY
    )

    androidx.appcompat.app.AlertDialog.Builder(this)
      .setTitle("Vehicle Color")
      .setItems(arrayOf("White", "Black", "Red", "Blue", "Green", "Yellow", "Brown", "Gray")) { _, which ->
        vehicleColor = colors[which]
        updateColorPickerBackground()

        model.materialOverrides(
          listOf(
            modelMaterialOverride("body") {
              modelColor(vehicleColor)
              modelColorMixIntensity(1.0)
            }
          )
        )
      }
      .show()
  }

  private fun updateColorPickerBackground() {
    val drawable = GradientDrawable().apply {
      shape = GradientDrawable.RECTANGLE
      setColor(vehicleColor)
      cornerRadius = 8f * resources.displayMetrics.density
      setStroke((2 * resources.displayMetrics.density).toInt(), Color.GRAY)
    }
    binding.controls.colorPickerButton.background = drawable
  }

  // Create initial model with all overrides
  private fun createCarModel(): ModelSourceModel {
    val doorOpeningDegMax = 80.0

    // Material overrides
    val materialOverrides = listOf(
      modelMaterialOverride("body") {
        modelColor(vehicleColor)
        modelColorMixIntensity(1.0)
      },
      modelMaterialOverride("lights_brakes") {
        modelColor(Color.rgb(224, 0, 0))
        modelColorMixIntensity(brakeLights)
        modelEmissiveStrength(brakeLights)
      },
      modelMaterialOverride("lights-brakes_reverse") {
        modelColor(Color.rgb(224, 0, 0))
        modelColorMixIntensity(brakeLights)
        modelEmissiveStrength(brakeLights)
      },
      modelMaterialOverride("lights_brakes_volume") {
        modelColor(Color.rgb(224, 0, 0))
        modelColorMixIntensity(1.0)
        modelEmissiveStrength(0.8)
        modelOpacity(brakeLights)
      },
      modelMaterialOverride("lights-brakes_reverse_volume") {
        modelColor(Color.rgb(224, 0, 0))
        modelColorMixIntensity(1.0)
        modelEmissiveStrength(0.8)
        modelOpacity(brakeLights)
      }
    )

    // Node overrides for door/hood/trunk animations
    val nodeOverrides = listOf(
      modelNodeOverride("doors_front-left") {
        orientation(listOf(0.0, mix(doorsFrontLeft, 0.0, -doorOpeningDegMax), 0.0))
      },
      modelNodeOverride("doors_front-right") {
        orientation(listOf(0.0, mix(doorsFrontRight, 0.0, doorOpeningDegMax), 0.0))
      },
      modelNodeOverride("hood") {
        orientation(listOf(mix(hood, 0.0, 45.0), 0.0, 0.0))
      },
      modelNodeOverride("trunk") {
        orientation(listOf(mix(trunk, 0.0, -60.0), 0.0, 0.0))
      }
    )

    return modelSourceModel(CAR_MODEL_KEY) {
      uri(CAR_MODEL_URI)
      position(listOf(CAR_POSITION.longitude(), CAR_POSITION.latitude()))
      orientation(listOf(0.0, 0.0, 0.0))
      nodeOverrides(nodeOverrides)
      materialOverrides(materialOverrides)
    }
  }

  // Helper function to mix values (linear interpolation)
  private fun mix(t: Double, a: Double, b: Double): Double {
    return b * t - a * (t - 1)
  }

  private companion object {
    const val SOURCE_ID = "3d-model-source"
    const val LAYER_ID = "3d-model-layer-for-source-based-updates"
    const val CAR_ZOOM = 18.8
    const val CAR_MODEL_KEY = "car"
    const val CAR_MODEL_URI = "https://docs.mapbox.com/mapbox-gl-js/assets/ego_car.glb"
    val CAR_POSITION: Point = Point.fromLngLat(-74.0138, 40.7154)
  }
}