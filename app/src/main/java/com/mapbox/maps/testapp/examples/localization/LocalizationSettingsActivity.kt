package com.mapbox.maps.testapp.examples.localization

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.bindgen.Expected
import com.mapbox.bindgen.None
import com.mapbox.common.SettingsServiceFactory
import com.mapbox.common.SettingsServiceInterface
import com.mapbox.common.SettingsServiceStorageType
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.model.MapLoadErrorType
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityLocalizationSettingBinding
import java.util.*
import com.mapbox.common.MapboxCommonSettings.LANGUAGE
import com.mapbox.common.MapboxCommonSettings.WORLDVIEW

/**
 * An example to shows how to set Language and Worldview settings with settings api.
 * When set, the tiles will be loaded in localized language / worldview if the query
 * parameters are supported.
 *
 * In order to get localized tiles (Server side localization), Create a Setting service instance using SettingsServiceFactory,
 * and set [MapboxCommonSettings.LANGAUGE] or [MapboxCommonSettings.WORLDVIEW].
 *
 * [SettingsServiceInterface] stores the provided language/worldview as key:value pair in DataStore
 * and try to apply it to the tiles. If the values are supported,
 * the localized tiles will be reloaded on to the map,
 * else server will emit 400 error which can be listen using [OnMapLoadErrorListener] event.
 *
 * The language param should conform to BCP-47 tag and Worldview param to ISO- 3166-1 alpha-2 strings.
 *
 * This is currently an opt-in and experimental feature, i.e. some styles could have a minor visual changes.
 *
 * Previous localization functionality using [StyleInterface.localizeLabel] will not work with the new settings api.
 * It will be deprecated in the future in order to benefit from more languages and additional worldview support.
 * In case you don't want to use server-side localization and it's already set, it can be removed
 * using [SettingsServiceInterface.erase] function.
 */
class LocalizationSettingsActivity : AppCompatActivity(), OnMapLoadErrorListener {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var languageSpinner: Spinner
  private lateinit var worldViewSpinner: Spinner

  /**
   * In order to update localization settings, Initialize settings interface as a Persistent.
   * the language and worldview changes will persist across application lifecycle.
   */
  private val settingsService: SettingsServiceInterface by lazy {
    SettingsServiceFactory.getInstance(
      SettingsServiceStorageType.NON_PERSISTENT
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityLocalizationSettingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    languageSpinner = binding.languageView
    worldViewSpinner = binding.worldView
    mapboxMap = binding.mapView.getMapboxMap()

    mapboxMap.addOnMapLoadErrorListener(this)
    mapboxMap.loadStyleUri(STYLE_URI)
    getLanguageAndWorldViewSettings()
    initSpinners()

    // check applied settings
    binding.buttonCheck.setOnClickListener {
      getLanguageAndWorldViewSettings()
    }
  }

  /**
   * Set language by providing the string value for language in BCP-47 format.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun setLanguage(language: String): Expected<String, None> {
    return settingsService.set(LANGUAGE, Value(language))
  }

  /**
   * set worldview for map in ISO- 3166-1 alpha-2.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun setWorldView(worldView: String): Expected<String, None> {
    return settingsService.set(WORLDVIEW, Value(worldView))
  }

  /**
   * get language settings.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun getLanguageSettings(): Expected<String, Value> {
    return settingsService.get(LANGUAGE)
  }

  /**
   * get worldview settings.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun getWorldViewSettings(): Expected<String, Value> {
    return settingsService.get(WORLDVIEW)
  }

  /**
   * Get language and worldview settings from SettingsInterface.
   */
  private fun getLanguageAndWorldViewSettings() {
    val language: Expected<String, Value> = getLanguageSettings()
    if (language.isError) {
      Log.e(TAG, " Error getting language ${language.error}")
    } else {
      Log.d(TAG, " Current language = ${language.value}")
    }

    val worldview: Expected<String, Value> = getWorldViewSettings()
    if (worldview.isError) {
      Log.e(TAG, " Error getting worldview ${worldview.error}")
    } else {
      Log.d(TAG, " Current worldview = ${worldview.value}")
    }
  }

  private fun initSpinners() {
    // create language adapter
    val languageAdapter = LocalizationAdapter(
      this,
      R.layout.item_spinner_view,
      supportedLanguageList
    )

    // create worldview adapter
    val worldViewAdapter = LocalizationAdapter(
      this,
      R.layout.item_spinner_view,
      supportedWorldviewList
    )

    languageSpinner.adapter = languageAdapter
    worldViewSpinner.adapter = worldViewAdapter

    languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedTag = languageAdapter.getItem(position).second
        val expected = setLanguage(selectedTag)
        if (expected.isError) {
          Log.d(
            TAG,
            "Failed to set language to dataStore. Error : ${expected.error}"
          )
        }
      }
    }

    worldViewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedTag = worldViewAdapter.getItem(position).second
        val expected = setWorldView(selectedTag)
        if (expected.isError) {
          Log.d(
            TAG,
            "Failed to set worldview to dataStore. Error : ${expected.error}"
          )
        }
      }
    }
  }

  override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
    if (eventData.type == MapLoadErrorType.SOURCE) {
      val message = eventData.message
      Toast.makeText(
        this@LocalizationSettingsActivity, "Error: $message", Toast.LENGTH_SHORT
      ).show()
    }
  }

  private class LocalizationAdapter(
    private val baseContext: Context,
    private val layoutResourceId: Int,
    val localizationDataList: List<Pair<String, String>>
  ) : ArrayAdapter<Pair<String, String>>(baseContext, layoutResourceId, localizationDataList) {

    override fun getCount(): Int {
      return localizationDataList.size
    }

    override fun getItem(position: Int): Pair<String, String> {
      return localizationDataList[position]
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
      val view = super.getView(position, convertView, parent)
      val localizationData = localizationDataList[position]
      setSpinnerText(view, localizationData)
      return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
      val view = super.getView(position, convertView, parent)
      val localizationData = localizationDataList[position]
      setSpinnerText(view, localizationData)
      return view
    }

    private fun setSpinnerText(view: View?, localizationData: Pair<String, String>) {
      view?.findViewById<TextView>(R.id.text_view_spinner)?.apply {
        text = baseContext.resources.getString(
          R.string.drop_down_text,
          localizationData.first,
          localizationData.second
        )
      }
    }
  }

  private companion object {
    const val TAG = "LocalizationActivity"
    const val STYLE_URI = "mapbox://styles/alexshalamov/cl1g16tmo001414nmc992d6ox"
    val supportedLanguageList = listOf(
      Pair("English US", Locale.US.toLanguageTag()),
      Pair("English UK", Locale.UK.toLanguageTag()),
      Pair("French (France)", Locale.FRENCH.toLanguageTag()),
      Pair("French (Canada)", Locale.CANADA_FRENCH.toLanguageTag()),
      Pair("Japanese", Locale.JAPANESE.toLanguageTag()),
      Pair("Chinese Mainland", Locale.SIMPLIFIED_CHINESE.toLanguageTag()),
      Pair("Korean (Korea)", Locale.KOREAN.toLanguageTag()),
      Pair("Italian (Italy)", Locale.ITALY.toLanguageTag()),
      Pair("Korean (Korea)", Locale("ru", "RU").toLanguageTag()),
      Pair("Finnish (Finland)", Locale("fi", "FI").toLanguageTag()),
      Pair("Arabic (Saudi-Arabia)", Locale("ar", "SA").toLanguageTag()),
      Pair("Danish (Denmark)", Locale("da", "DK").toLanguageTag()),
      Pair("Spanish (Spain)", Locale("es", "ES").toLanguageTag()),
      Pair("Hindi (India)", Locale("hi", "IN").toLanguageTag()),
      Pair("Chinese (Hong-kong)", Locale("zh", "HK").toLanguageTag()),
      Pair("Bangla (Bangladesh)", Locale("bn", "BD").toLanguageTag()),
      // invalid languages
      Pair("English (Invalid)", Locale("xx","US").toLanguageTag()),
      Pair("Slovanian (Invalid)", Locale("slv", "SVN", "Latn").toLanguageTag())
    )
    val supportedWorldviewList = listOf(
      Pair("Argentina", "AR"),
      Pair("China", "CN"),
      Pair("India", "IN"),
      Pair("Japan", "JP"),
      Pair("Morocco", "MA"),
      Pair("Russian Federation", "RU"),
      Pair("Turkey", "TR"),
      Pair("United States", "US"),
    )
  }
}