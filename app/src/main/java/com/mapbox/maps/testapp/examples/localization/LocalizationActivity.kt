package com.mapbox.maps.testapp.examples.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.plugin.localization.Languages
import com.mapbox.maps.plugin.localization.LocalizationPlugin
import com.mapbox.maps.plugin.localization.MapLocale
import com.mapbox.maps.plugin.localization.MapLocale.Companion.CANADA
import com.mapbox.maps.plugin.localization.localization
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.examples.annotation.AnnotationUtils
import kotlinx.android.synthetic.main.activity_map_localization.*
import kotlinx.android.synthetic.main.activity_map_overlay.*
import kotlinx.android.synthetic.main.activity_map_overlay.mapView

class LocalizationActivity : AppCompatActivity() {
  private lateinit var localizationPlugin: LocalizationPlugin
  private lateinit var mapboxMap: MapboxMap
  private var mapIsLocalized: Boolean = false
  private var index: Int = 0
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_localization)
    mapIsLocalized = true
    Toast.makeText(this, R.string.change_language_instruction, Toast.LENGTH_LONG).show()
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(nextStyle) {
      localizationPlugin = mapView.localization()
      localizationPlugin.matchMapLanguageWithDeviceDefault()
    }
    fabStyles.setOnClickListener {
      mapboxMap.loadStyleUri(nextStyle)
    }
    fabLocalize.setOnClickListener {
      mapIsLocalized = if (mapIsLocalized) {
        localizationPlugin.setMapLanguage(MapLocale(Languages.FRENCH))
        Toast.makeText(this, R.string.map_not_localized, Toast.LENGTH_SHORT).show()
        false
      } else {
        localizationPlugin.matchMapLanguageWithDeviceDefault()
        Toast.makeText(this, R.string.map_localized, Toast.LENGTH_SHORT).show()
        true
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_languages, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.english -> {
        localizationPlugin.setMapLanguage(Languages.ENGLISH)
        return true
      }
      R.id.spanish -> {
        localizationPlugin.setMapLanguage(Languages.SPANISH)
        return true
      }
      R.id.french -> {
        localizationPlugin.setMapLanguage(Languages.FRENCH)
        return true
      }
      R.id.german -> {
        localizationPlugin.setMapLanguage(Languages.GERMAN)
        return true
      }
      R.id.russian -> {
        localizationPlugin.setMapLanguage(Languages.RUSSIAN)
        return true
      }
      R.id.chinese -> {
        localizationPlugin.setMapLanguage(Languages.CHINESE)
        return true
      }
      R.id.simplified_chinese -> {
        localizationPlugin.setMapLanguage(Languages.SIMPLIFIED_CHINESE)
        return true
      }
      R.id.portuguese -> {
        localizationPlugin.setMapLanguage(Languages.PORTUGUESE)
        return true
      }
      R.id.arabic -> {
        localizationPlugin.setMapLanguage(Languages.ARABIC)
        return true
      }
      R.id.japanese -> {
        localizationPlugin.setMapLanguage(Languages.JAPANESE)
        return true
      }
      R.id.korean -> {
        localizationPlugin.setMapLanguage(Languages.KOREAN)
        return true
      }
      R.id.vietnamese -> {
        localizationPlugin.setMapLanguage(Languages.VIETNAMESE)
        return true
      }
      R.id.italian -> {
        localizationPlugin.setMapLanguage(Languages.ITALIAN)
        return true
      }
      R.id.local -> {
        localizationPlugin.setMapLanguage(Languages.LOCAL_NAME)
        return true
      }
      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
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

    private val LOCALES = arrayOf(
      CANADA, MapLocale.GERMANY, MapLocale.CHINA,
      MapLocale.US, MapLocale.CANADA_FRENCH, MapLocale.JAPAN, MapLocale.KOREA,
      MapLocale.FRANCE, MapLocale.SPAIN, MapLocale.VIETNAM, MapLocale.ITALY, MapLocale.UK
    )

    private var index: Int = 0

    val nextMapLocale: MapLocale
      get() {
        index++
        if (index == LOCALES.size) {
          index = 0
        }
        return LOCALES[index]
      }
  }
}