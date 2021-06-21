package com.mapbox.maps.testapp.examples.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_map_localization.*
import kotlinx.android.synthetic.main.activity_map_overlay.mapView
import java.util.*

/**
 * Example showcasing how to localize a map client side to a specific locale using Style#localizeLabels(locale: Locale).
 * This function will attempt to localize a map into a selected locale if the symbol layers are using
 * a Mapbox source and the locale is being provided as part of the vector source data.
 * This feature supports both v7 and v8 of Mapbox style spec version.
 */
class LocalizationActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private var applySelectedLanguage: Boolean = false
  private var index: Int = 0
  private val styles =
    arrayOf(
      Style.MAPBOX_STREETS,
      MAPBOX_STREETS_V10
    )

  private val nextStyle: String
    get() {
      return styles[index++ % styles.size]
    }
  private lateinit var locale: Locale
  private lateinit var selectedLocale: Locale
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_localization)
    locale = resources.configuration.locale
    selectedLocale = locale
    applySelectedLanguage = false
    Toast.makeText(this, R.string.change_language_instruction, Toast.LENGTH_LONG).show()
    mapboxMap = mapView.getMapboxMap()
    mapboxMap.loadStyleUri(nextStyle) {
      it.localizeLabels(locale)
    }
    fabStyles.setOnClickListener {
      val styleUri = nextStyle
      mapboxMap.loadStyleUri(styleUri) {
        it.localizeLabels(selectedLocale)
      }
      Toast.makeText(this, styleUri, Toast.LENGTH_SHORT).show()
    }
    fabLocalize.setOnClickListener {
      applySelectedLanguage = if (!applySelectedLanguage) {
        mapboxMap.getStyle()?.localizeLabels(selectedLocale)
        Toast.makeText(this, R.string.map_not_localized, Toast.LENGTH_SHORT).show()
        true
      } else {
        mapboxMap.getStyle()?.localizeLabels(locale)
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
    applySelectedLanguage = true
    item.isChecked = true
    selectedLocale = when (item.itemId) {
      R.id.english -> Locale.ENGLISH
      R.id.spanish -> Locale("es", "ES")
      R.id.french -> Locale.FRENCH
      R.id.german -> Locale.GERMAN
      R.id.russian -> Locale("ru", "RU")
      R.id.chinese -> Locale.CHINESE
      R.id.simplified_chinese -> Locale.SIMPLIFIED_CHINESE
      R.id.portuguese -> Locale("pt", "PT")
      R.id.japanese -> Locale.JAPANESE
      R.id.korean -> Locale.KOREAN
      R.id.vietnamese -> Locale("vi", "VN")
      R.id.italian -> Locale.ITALIAN
      else -> locale
    }
    mapboxMap.getStyle()?.localizeLabels(selectedLocale)
    return true
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
    private const val MAPBOX_STREETS_V10 = "mapbox://styles/mapbox/streets-v10"
  }
}