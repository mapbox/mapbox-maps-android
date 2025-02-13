package com.mapbox.maps.testapp.examples.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Value
import com.mapbox.common.MapboxCommonSettings
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityMapLocalizationBinding
import java.util.Locale

/**
 * Experimental not fully supported example showcasing how to localize a map client side to a specific locale.
 *
 * This feature supports [Style.STANDARD].
 */
class LocalizationActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private var applySelectedLanguage: Boolean = false
  private var index: Int = 0
  private val styles =
    arrayOf(
      Style.STANDARD,
      Style.MAPBOX_STREETS
    )

  private val nextStyle: String
    get() {
      return styles[index++ % styles.size]
    }
  private lateinit var deviceLocale: Locale
  private lateinit var selectedLocale: Locale

  private fun applyLocale(selectedLocale: Locale) {
    val settingsService = SettingsServiceFactory.getInstance(SettingsServiceStorageType.PERSISTENT)
    settingsService.set(MapboxCommonSettings.LANGUAGE, Value.valueOf(selectedLocale.toLanguageTag()))
    // Force style reload
    mapboxMap.style?.let {
      mapboxMap.loadStyle(it.styleURI) {
        // re-apply here any custom style
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMapLocalizationBinding.inflate(layoutInflater)
    setContentView(binding.root)
    @Suppress("DEPRECATION")
    deviceLocale = resources.configuration.locale
    selectedLocale = deviceLocale
    applySelectedLanguage = false
    Toast.makeText(this, R.string.change_language_instruction, Toast.LENGTH_LONG).show()
    mapboxMap = binding.mapView.mapboxMap
    applyLocale(deviceLocale)
    mapboxMap.loadStyle(nextStyle)
    binding.fabStyles.setOnClickListener {
      val styleUri = nextStyle
      mapboxMap.loadStyle(styleUri)
      Toast.makeText(this, styleUri, Toast.LENGTH_SHORT).show()
    }
    binding.fabLocalize.setOnClickListener {
      applySelectedLanguage = if (!applySelectedLanguage) {
        applyLocale(selectedLocale)
        Toast.makeText(this, R.string.map_not_localized, Toast.LENGTH_SHORT).show()
        true
      } else {
        applyLocale(deviceLocale)
        Toast.makeText(this, R.string.map_localized, Toast.LENGTH_SHORT).show()
        false
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_languages, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.groupId) {
      R.id.group -> {
        applySelectedLanguage = true
        item.isChecked = true
        selectedLocale = when (item.itemId) {
          R.id.english -> Locale.ENGLISH
          R.id.spanish -> Locale("es", "ES")
          R.id.french -> Locale.FRENCH
          R.id.german -> Locale.GERMAN
          R.id.russian -> Locale("ru", "RU")
          R.id.portuguese -> Locale("pt", "PT")
          R.id.japanese -> Locale.JAPANESE
          R.id.korean -> Locale.KOREAN
          R.id.vietnamese -> Locale("vi", "VN")
          R.id.italian -> Locale.ITALIAN
          else -> deviceLocale
        }
      }
      else -> return super.onOptionsItemSelected(item)
    }
    applyLocale(selectedLocale)
    return true
  }
}