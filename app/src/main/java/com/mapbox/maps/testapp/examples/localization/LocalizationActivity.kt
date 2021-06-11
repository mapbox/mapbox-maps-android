package com.mapbox.maps.testapp.examples.localization

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.localization.localizeLabels
import com.mapbox.maps.testapp.R
import kotlinx.android.synthetic.main.activity_map_localization.*
import kotlinx.android.synthetic.main.activity_map_overlay.mapView
import java.util.*

/**
 * Example showcasing usage of localization.
 */
class LocalizationActivity : AppCompatActivity() {
  private lateinit var mapboxMap: MapboxMap
  private var mapIsLocalized: Boolean = false
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
      val styleUri = nextStyle
      mapboxMap.loadStyleUri(styleUri) {
        it.localizeLabels(locale)
      }
      Toast.makeText(this, styleUri, Toast.LENGTH_SHORT).show()
    }
    fabLocalize.setOnClickListener {
      mapIsLocalized = if (mapIsLocalized) {
        mapboxMap.getStyle()?.localizeLabels(Locale.FRENCH)
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
    private const val MAPBOX_STREETS_V10 = "mapbox://styles/mapbox/streets-v10"
  }
}