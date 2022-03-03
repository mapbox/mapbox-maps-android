package com.mapbox.maps.testapp.examples

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.mapbox.base.common.logger.Logger
import com.mapbox.bindgen.Expected
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceInterface
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.maps.extension.style.sources.getSourceAs
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.delegates.listeners.OnStyleLoadedListener
import com.mapbox.maps.testapp.databinding.ActivityStyleVectorSourceBinding
import com.mapbox.bindgen.Value
import com.mapbox.common.OnValueChanged
import com.mapbox.common.core.module.MapboxSettings
import com.mapbox.geojson.GeoJson
import com.mapbox.maps.Observer
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.java.RuntimeStylingJavaActivity
import org.json.JSONObject

/**
 * Add a vector source to a map using an URL and visualize it with a line layer.
 */
class VectorTileSourceActivity : AppCompatActivity() {
  private lateinit var mapboxMap : MapboxMap
  private val settingsServiceInterface : SettingsServiceInterface by lazy { SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT) }
  private var languageobserver : Int? = null
  private var worldviewobserver : Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityStyleVectorSourceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    mapboxMap = binding.mapView.getMapboxMap()
    mapboxMap.loadStyle(
      style(Style.LIGHT) {
        +vectorSource("terrain-data") {
          url("mapbox://mapbox.mapbox-terrain-v2")
        }
        +layerAtPosition(
          lineLayer("terrain-data", "terrain-data") {
            sourceLayer("contour")
            lineJoin(LineJoin.ROUND)
            lineCap(LineCap.ROUND)
            lineColor(Color.parseColor("#ff69b4"))
            lineWidth(1.9)
          },
          below = "road-label"
        )
      }
    )
    getLanguageAndWorldViewSettings()
    initSpinners()
    binding.buttonCheck.setOnClickListener {
      val language = MapboxSettings.getLanguage()
      val worldview = MapboxSettings.getWorldView()
      mapboxMap.getStyle()?.getSourceAs<VectorSource>("terrain-data")?.let {
        Log.d(TAG," source url = ${it.url}")
      }
      Log.d(TAG," setting language = ${language.value}")
      Log.d(TAG," setting worldview = ${worldview.value}")
      Toast.makeText(applicationContext, "applied language = ${language.value} and worldview = ${worldview.value}", Toast.LENGTH_LONG).show()
    }
  }

  private fun registerSettingInterface() {
    languageobserver = settingsServiceInterface.registerObserver(SETTINGS_LANGUAGE)
    { key, oldValue, newValue ->
      Log.d(TAG," key = $key, oldValue = $oldValue , newValue=  $newValue")
    }

    worldviewobserver = settingsServiceInterface.registerObserver(SETTINGS_WORLDVIEW)
    { key, oldValue, newValue ->
      Log.d(TAG," key = $key, oldValue = $oldValue , newValue=  $newValue")
    }
  }

  /**
   * Set language by providing the string value for language.
   */
  private fun setMapLanguage(languageStr:String){
    val expected = settingsServiceInterface.set(SETTINGS_LANGUAGE, Value(languageStr))
    if(expected.isError){
      Log.d(TAG, "Error setting language ${expected.error}")
    }
  }

  /**
   * set worldview for map
   */
  private fun setMapWorldView(worldView:String) {
    val expected = settingsServiceInterface.set(SETTINGS_WORLDVIEW, Value(worldView))
    if(expected.isError){
      Log.d(TAG, "Error setting worldView ${expected.error}")
    }
  }

  private fun getLanguageAndWorldViewSettings() {
  // get language
    val language : Expected<String, Value> = MapboxSettings.getLanguage()
    if(language.isError){
      Log.e(TAG," Error getting language ${language.error}")
    } else {
      Log.d(TAG," Current language = ${language.value}")
    }

    // get worldview
    val worldview : Expected<String,Value> = MapboxSettings.getWorldView()
    if(worldview.isError){
      Log.e(TAG," Error getting worldview ${worldview.error}")
    } else {
      Log.d(TAG," Current worldview = ${worldview.value}")
    }
  }

  private fun initSpinners() {
    val languageSpinner: Spinner = findViewById(R.id.languageView)
    val worldViewSpinner : Spinner = findViewById(R.id.worldView)
    val worldViewAdapter = ArrayAdapter.createFromResource(
      this, R.array.worldview_array, R.layout.item_spinner_view
    )
    val languageAdapter = ArrayAdapter.createFromResource(
      this, R.array.language_array_key, R.layout.item_spinner_view
    )
    val languageValuesArray = resources.getStringArray(R.array.language_array_value)
    languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    worldViewAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    languageSpinner.adapter = languageAdapter
    worldViewSpinner.adapter = worldViewAdapter

    languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedVal = languageValuesArray[position]
        setMapLanguage(selectedVal.toString())
      }
    }

    worldViewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedVal = worldViewAdapter.getItem(position)
        setMapWorldView(selectedVal.toString())
      }
    }
  }

  override fun onResume() {
    super.onResume()
    registerSettingInterface()
  }

  override fun onPause() {
    super.onPause()
    unregisterSettingInterface()
  }

  private fun unregisterSettingInterface() {
    languageobserver?.let { languageObserverId ->
      settingsServiceInterface.unregisterObserver(languageObserverId)
      languageobserver = null
    }

    worldviewobserver?.let { worldviewObserverId ->
      settingsServiceInterface.unregisterObserver(worldviewObserverId)
      worldviewobserver = null
    }
  }

  private companion object {
    val TAG = "VectorActivity"
    val SETTINGS_LANGUAGE = "com.mapbox.i18n.language"
    val SETTINGS_WORLDVIEW = "com.mapbox.i18n.worldview"
  }
}