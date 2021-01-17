package com.mapbox.maps.testapp.examples.sky

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.generated.SkyLayer
import com.mapbox.maps.extension.style.layers.generated.skyLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.SkyType
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.scalebar.getScaleBarPlugin
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_sky_layer.*

/**
 * Showcase for Sky layer.
 */
class SkyLayerShowcaseActivity : AppCompatActivity() {

  private var skyLayer: SkyLayer? = null
  private var skyType = SkyType.GRADIENT

  // could be between 0.0 and 180.0
  private var skyGradientRadius = 10.0

  // azimuth, could be between -180.0 and 180.0
  private var skyGradientCenterA = 0.0

  // polar, could be between 0.0 and 180.0
  private var skyGradientCenterP = 90.0

  // could be between 0.0 and 100.0
  private var skyAtmosphereSunIntensity = 10.0

  // azimuth, could be between -180.0 and 180.0
  private var skyAtmosphereCenterA = 0.0

  // polar, could be between 0.0 and 180.0
  private var skyAtmosphereCenterP = 90.0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sky_layer)
    applyControls()
    mapView.getMapboxMap().loadStyle(
      styleExtension = style(Style.SATELLITE_STREETS) {
        +skyLayer(SKY_LAYER) {
          skyType(skyType)
          when (skyType) {
            SkyType.GRADIENT -> {
              skyGradient(
                interpolate {
                  linear()
                  skyRadialProgress()
                  literal(0.0)
                  literal(SKY_GRADIENT_COLOR_START)
                  literal(1.0)
                  literal(SKY_GRADIENT_COLOR_END)
                }
              )
              skyGradientRadius(skyGradientRadius)
              skyGradientCenter(listOf(skyGradientCenterA, skyGradientCenterP))
            }
            SkyType.ATMOSPHERE -> {
              skyAtmosphereColor(SKY_ATMOSPHERE_COLOR)
              skyAtmosphereHaloColor(SKY_ATMOSPHERE_HALO_COLOR)
              skyAtmosphereSun(listOf(skyAtmosphereCenterA, skyAtmosphereCenterP))
              skyAtmosphereSunIntensity(skyAtmosphereSunIntensity)
            }
          }
        }
      }
    ) {
      mapView.getScaleBarPlugin().enabled = false
      skyLayer = it.getLayerAs(SKY_LAYER)
    }
  }

  private fun applyControls() {
    applyInitials()
    switchSkyMode.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        skyType = SkyType.ATMOSPHERE
        textSkyMode.text = getString(R.string.sky_mode, skyType.name)
        layoutGradient.visibility = View.GONE
        layoutAtmosphere.visibility = View.VISIBLE
      } else {
        skyType = SkyType.GRADIENT
        textSkyMode.text = getString(R.string.sky_mode, skyType.name)
        layoutGradient.visibility = View.VISIBLE
        layoutAtmosphere.visibility = View.GONE
      }
      applySkyProperties()
    }
    applySkyGradientControls()
    applySkyAtmosphereControls()
  }

  private fun applyInitials() {
    textSkyMode.text = getString(R.string.sky_mode, skyType.name)
    captionAtmosphereCenterA.text = getString(R.string.sky_center_azimuth, skyAtmosphereCenterA.toInt())
    captionAtmosphereCenterP.text = getString(R.string.sky_center_polar, skyAtmosphereCenterP.toInt())
    captionGradientCenterA.text = getString(R.string.sky_center_azimuth, skyGradientCenterA.toInt())
    captionGradientCenterP.text = getString(R.string.sky_center_polar, skyGradientCenterP.toInt())
    captionAtmosphereSunIntensity.text = getString(R.string.sky_sun_intensity, skyAtmosphereSunIntensity.toInt())
    captionGradientRadius.text = getString(R.string.sky_gradient_radius, skyGradientRadius.toInt())
  }

  private fun applySkyGradientControls() {
    seekBarGradientRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        skyGradientRadius = progress.toDouble()
        captionGradientRadius.text = getString(R.string.sky_gradient_radius, skyGradientRadius.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
    seekBarGradientCenterA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // convert [0, 360] range to [-180, 180]
        skyGradientCenterA = progress.toDouble() - 180.0
        captionGradientCenterA.text = getString(R.string.sky_center_azimuth, skyGradientCenterA.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
    seekBarGradientCenterP.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        skyGradientCenterP = progress.toDouble()
        captionGradientCenterP.text = getString(R.string.sky_center_polar, skyGradientCenterP.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
  }

  private fun applySkyAtmosphereControls() {
    seekBarAtmosphereSunIntensity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        skyAtmosphereSunIntensity = progress.toDouble()
        captionAtmosphereSunIntensity.text = getString(R.string.sky_sun_intensity, skyAtmosphereSunIntensity.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
    seekBarAtmosphereCenterA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        // convert [0, 360] range to [-180, 180]
        skyAtmosphereCenterA = progress.toDouble() - 180.0
        captionAtmosphereCenterA.text = getString(R.string.sky_center_azimuth, skyAtmosphereCenterA.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
    seekBarAtmosphereCenterP.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        skyAtmosphereCenterP = progress.toDouble()
        captionAtmosphereCenterP.text = getString(R.string.sky_center_polar, skyAtmosphereCenterP.toInt())
        applySkyProperties()
      }

      override fun onStartTrackingTouch(seekBar: SeekBar?) {}

      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
  }

  private fun applySkyProperties() {
    skyLayer?.skyType(skyType)
    when (skyType) {
      SkyType.GRADIENT -> applySkyGradientProperties()
      SkyType.ATMOSPHERE -> applySkyAtmosphereProperties()
    }
  }

  private fun applySkyGradientProperties() {
    skyLayer?.skyGradientRadius(skyGradientRadius)
    skyLayer?.skyGradientCenter(listOf(skyGradientCenterA, skyGradientCenterP))
  }

  private fun applySkyAtmosphereProperties() {
    skyLayer?.skyAtmosphereSunIntensity(skyAtmosphereSunIntensity)
    skyLayer?.skyAtmosphereSun(listOf(skyAtmosphereCenterA, skyAtmosphereCenterP))
  }

  override fun onStart() {
    super.onStart()
    mapView.onStart()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  companion object {
    private const val SKY_LAYER = "sky"

    private const val SKY_GRADIENT_COLOR_START = "yellow"
    private const val SKY_GRADIENT_COLOR_END = "pink"

    private const val SKY_ATMOSPHERE_COLOR = "blue"
    private const val SKY_ATMOSPHERE_HALO_COLOR = "pink"
  }
}