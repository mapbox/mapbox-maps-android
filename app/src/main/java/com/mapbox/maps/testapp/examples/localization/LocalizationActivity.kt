package com.mapbox.maps.testapp.examples.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.localizeLabels
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
import java.util.*

class LocalizationActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private var mapIsLocalized: Boolean = false
  private var index: Int = 0
  private val nextStyle: String
    get() {
      return AnnotationUtils.STYLES[index++ % AnnotationUtils.STYLES.size]
    }
  private lateinit var locale:Locale
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_localization)
    locale = resources.configuration.locale
    mapIsLocalized = true
    Toast.makeText(this, R.string.change_language_instruction, Toast.LENGTH_LONG).show()
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(nextStyle) {
      it.localizeLabels(locale)
    }
    fabStyles.setOnClickListener {
      mapboxMap.loadStyleUri(nextStyle)
    }
    fabLocalize.setOnClickListener {
      mapIsLocalized = if (mapIsLocalized) {
        mapboxMap.getStyle()?.localizeLabels(Locale.FRANCE)
        Toast.makeText(this, R.string.map_not_localized, Toast.LENGTH_SHORT).show()
        false
      } else {
        mapboxMap.getStyle()?.localizeLabels(locale)
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
        mapboxMap.getStyle()?.localizeLabels(Locale.ENGLISH)
        return true
      }
      R.id.spanish -> {
        mapboxMap.getStyle()?.localizeLabels(Locale("es", "ES"))
        return true
      }
      R.id.french -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.FRENCH)
        return true
      }
      R.id.german -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.GERMAN)
        return true
      }
      R.id.russian -> {
        mapboxMap.getStyle()?.localizeLabels(Locale("ru", "RU"))
        return true
      }
      R.id.chinese -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.CHINESE)
        return true
      }
      R.id.simplified_chinese -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.SIMPLIFIED_CHINESE)
        return true
      }
      R.id.portuguese -> {
        mapboxMap.getStyle()?.localizeLabels(Locale("pt", "PT"))
        return true
      }
      R.id.arabic -> {
        return true
      }
      R.id.japanese -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.JAPANESE)
        return true
      }
      R.id.korean -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.KOREAN)
        return true
      }
      R.id.vietnamese -> {
        mapboxMap.getStyle()?.localizeLabels(Locale("vi", "VN"))
        return true
      }
      R.id.italian -> {
         mapboxMap.getStyle()?.localizeLabels(Locale.ITALIAN)
        return true
      }
      R.id.local -> {
         mapboxMap.getStyle()?.localizeLabels(locale)
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