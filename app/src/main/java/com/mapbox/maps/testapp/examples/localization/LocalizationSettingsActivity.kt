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
import com.mapbox.maps.extension.style.sources.generated.vectorSource
import com.mapbox.bindgen.Value
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.model.MapLoadErrorType
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.testapp.R
import com.mapbox.maps.testapp.databinding.ActivityLocalizationSettingBinding
import java.util.*
import com.mapbox.common.MapboxCommonSettings.LANGUAGE
import com.mapbox.common.MapboxCommonSettings.WORLDVIEW
import com.mapbox.maps.Style
import kotlinx.coroutines.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * An example to shows how to set Language and Worldview settings with new settings api.
 * When set, the tiles will be loaded in localized language / worldview if the query
 * parameters are supported.
 *
 * In order to get localized tiles (Server side localization), Create a Setting service instance using SettingsServiceFactory,
 * and set [MapboxCommonSettings.LANGAUGE] or [MapboxCommonSettings.WORLDVIEW].
 *
 * [SettingsServiceInterface] stores the provided language/worldview as key:value pair in DataStore
 * and try to apply it to the tiles.if the values are supported,
 * the localized tiles will be reloaded on to the map,
 * else server will emit 400 error which can be listen using [OnMapLoadErrorListener] event.
 *
 * The language param should conform to BCP-47 tag and Worldview param to ISO- 3166-1 alpha-2 strings.
 *
 * This is currently an opt-in and experimental feature, i.e. some styles could have a minor visual changes.
 *
 * Previous localization functionality using [StyleInterface.localizeLabel] will not work with the new settings api.
 * it will be deprecated in the future in order to benefit from more languages and additional worldview support.
 * In case If you don't want to use server side localization and its already set, it can be removed
 * using [SettingsServiceInterface.erase] function.
 */
class LocalizationSettingsActivity : AppCompatActivity(), OnMapLoadErrorListener, CoroutineScope {
  private lateinit var mapboxMap: MapboxMap
  private lateinit var languageSpinner: Spinner
  private lateinit var worldViewSpinner: Spinner
  private val supportedLanguageList = mutableListOf<LocalizationData>()
  private val supportedWorldviewList = mutableListOf<LocalizationData>()
  private val job = Job()

  /**
   * In order to update localization settings, Initialize settings interface as a Persistent.
   * the language and worldview changes will persist across application lifecycle.
   */
  private val settingsServiceInterface: SettingsServiceInterface by lazy {
    SettingsServiceFactory.getInstance(
      SettingsServiceStorageType.PERSISTENT
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityLocalizationSettingBinding.inflate(layoutInflater)
    setContentView(binding.root)

    languageSpinner = binding.languageView
    worldViewSpinner = binding.worldView
    mapboxMap = binding.mapView.getMapboxMap()
    init()

    // check applied settings
    binding.buttonCheck.setOnClickListener {
      getLanguageAndWorldViewSettings()
    }
  }

  private fun init() {
    mapboxMap.addOnMapLoadErrorListener(this)
    mapboxMap.loadStyleUri(Style.MAPBOX_STREETS) { style ->
      val source = vectorSource(SOURCE_ID) {
        url("mapbox://mapbox.mapbox-terrain-v2")
      }
      style.addSource(source)
    }
    createLocalizationData()
    getLanguageAndWorldViewSettings()
    initSpinners()
  }

  /**
   * Set language by providing the string value for language in BCP-47 format.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun setLanguage(language: String): Expected<String, None> {
    return settingsServiceInterface.set(LANGUAGE, Value(language))
  }

  /**
   * set worldview for map in ISO- 3166-1 alpha-2.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun setWorldView(worldView: String): Expected<String, None> {
    return settingsServiceInterface.set(WORLDVIEW, Value(worldView))
  }

  /**
   * get language settings.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun getLanguageSettings(): Expected<String, Value> {
    return settingsServiceInterface.get(LANGUAGE)
  }

  /**
   * get worldview settings.
   * Settings set/get are synchronous, make sure to run it on background thread.
   */
  private fun getWorldViewSettings(): Expected<String, Value> {
    return settingsServiceInterface.get(WORLDVIEW)
  }

  /**
   * Get language and worldview settings from SettingsInterface.
   */
  private fun getLanguageAndWorldViewSettings() {
    launch(Dispatchers.IO) {
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
        val selectedTag = languageAdapter.getItem(position).tag
        launch(Dispatchers.IO) {
          val expected = setLanguage(selectedTag)
          if (expected.isError) {
            Log.d(
              TAG,
              "Failed to set language to dataStore. Error : ${expected.error}"
            )
          }
        }
      }
    }

    worldViewSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onNothingSelected(parent: AdapterView<*>?) {}

      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedTag = worldViewAdapter.getItem(position).tag
        launch(Dispatchers.IO) {
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
  }

  override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
    if (eventData.type == MapLoadErrorType.SOURCE) {
      val message = eventData.message
      Toast.makeText(
        this@LocalizationSettingsActivity, "Error: $message", Toast.LENGTH_SHORT
      ).show()
    }
  }

  private companion object {
    const val TAG = "LocalizationActivity"
    const val SOURCE_ID = "source-id"
  }

  private class LocalizationAdapter(
    private val baseContext: Context,
    private val layoutResourceId: Int,
    val localizationDataList: List<LocalizationData>
  ) : ArrayAdapter<LocalizationData>(baseContext, layoutResourceId, localizationDataList) {

    override fun getCount(): Int {
      return localizationDataList.size
    }

    override fun getItem(position: Int): LocalizationData {
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

    private fun setSpinnerText(view: View?, localizationData: LocalizationData) {
      view?.findViewById<TextView>(R.id.text_view_spinner)?.apply {
        text = baseContext.resources.getString(
          R.string.drop_down_text,
          localizationData.description,
          localizationData.tag
        )
      }
    }
  }

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Default

  /**
   * Create a localization data that will populate in the drop down view.
   * language is expected as BCP-47 tag, we extract the language tag from the [Locale] and set
   * it to SettingsInterface method.
   */
  private fun createLocalizationData() {
    supportedLanguageList.addAll(
      listOf(
        LocalizationData("English US", Locale.US.toLanguageTag()),
        LocalizationData("English UK", Locale.UK.toLanguageTag()),
        LocalizationData("French (France)", Locale.FRENCH.toLanguageTag()),
        LocalizationData("French (Canada)", Locale.CANADA_FRENCH.toLanguageTag()),
        LocalizationData("Japanese", Locale.JAPANESE.toLanguageTag()),
        LocalizationData("Chinese Mainland", Locale.SIMPLIFIED_CHINESE.toLanguageTag()),
        LocalizationData("Korean (Korea)", Locale.KOREAN.toLanguageTag()),
        LocalizationData("Italian (Italy)", Locale.ITALY.toLanguageTag()),
        LocalizationData("Korean (Korea)", Locale("ru", "RU").toLanguageTag()),
        LocalizationData("Finnish (Finland)", Locale("fi", "FI").toLanguageTag()),
        LocalizationData("Arabic (Saudi-Arabia)", Locale("ar", "SA").toLanguageTag()),
        LocalizationData("Danish (Denmark)", Locale("da", "DK").toLanguageTag()),
        LocalizationData("Spanish (Spain)", Locale("es", "ES").toLanguageTag()),
        LocalizationData("Hindi (India)", Locale("hi", "IN").toLanguageTag()),
        LocalizationData("Chinese (Hong-kong)", Locale("zh", "HK").toLanguageTag()),
        LocalizationData("Bangla (Bangladesh)", Locale("bn", "BD").toLanguageTag())
      )
    )

    supportedWorldviewList.addAll(
      listOf(
        LocalizationData("Argentina", "AR"),
        LocalizationData("China", "CN"),
        LocalizationData("India", "IN"),
        LocalizationData("Japan", "JP"),
        LocalizationData("Morocco", "MA"),
        LocalizationData("Russian Federation", "RU"),
        LocalizationData("Turkey", "TR"),
        LocalizationData("United States", "US"),
      )
    )
  }

  data class LocalizationData(
    val description: String,
    val tag: String
  )
}