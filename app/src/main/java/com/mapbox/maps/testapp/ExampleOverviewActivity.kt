package com.mapbox.maps.testapp

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mapbox.maps.testapp.adapter.ExampleAdapter
import com.mapbox.maps.testapp.adapter.ExampleSectionAdapter
import com.mapbox.maps.testapp.model.SpecificExample
import com.mapbox.maps.testapp.utils.ItemClickSupport
import kotlinx.android.synthetic.main.activity_example_overview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * Activity shown when application is started
 *
 * This activity will generate data for RecyclerView based on the AndroidManifest entries.
 * It uses tags as category and description to order the different entries.
 *
 */
class ExampleOverviewActivity : AppCompatActivity(), CoroutineScope {

  private lateinit var sectionAdapter: ExampleSectionAdapter
  private var currentlyDisplayedExampleList: List<SpecificExample> = ArrayList()
  private var allExampleList: List<SpecificExample> = ArrayList()
  private val job = Job()

  override val coroutineContext = job + Dispatchers.IO

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_example_overview)

    recyclerView.apply {
      layoutManager = LinearLayoutManager(this@ExampleOverviewActivity)
      addOnItemTouchListener(RecyclerView.SimpleOnItemTouchListener())
      setHasFixedSize(true)
      ItemClickSupport.addTo(this)
        .setOnItemClickListener { _, position, _ ->
          if (!sectionAdapter.isSectionHeaderPosition(position)) {
            val itemPosition = sectionAdapter.getConvertedPosition(position)
            if (currentlyDisplayedExampleList.isNotEmpty()) {
              startExample(currentlyDisplayedExampleList.elementAtOrNull(itemPosition))
            } else {
              startExample(allExampleList.elementAtOrNull(itemPosition))
            }
          }
        }
    }

    if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_STATE_EXAMPLES)) {
      launch {
        loadExamples()
      }
    } else {
      displayExampleList(savedInstanceState.getParcelableArrayList(KEY_STATE_EXAMPLES)!!)
    }

    example_search_edittext.addTextChangedListener(object : TextWatcher {

      override fun afterTextChanged(s: Editable) {
        // Empty because this callback isn't useful for the app
      }

      override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
      ) {
        // Empty because this callback isn't useful for the app
      }

      override fun onTextChanged(
        currentTextInEditText: CharSequence,
        start: Int,
        before: Int,
        count: Int
      ) {
        if (currentTextInEditText.isEmpty()) {
          displayExampleList(allExampleList)
          clear_search_imageview.visibility = View.INVISIBLE
        } else {
          if (clear_search_imageview.visibility == View.INVISIBLE) {
            clear_search_imageview.visibility = View.VISIBLE
          }
          val lowercaseSearchText =
            currentTextInEditText.toString().toLowerCase(Locale.getDefault())
          val filteredList = allExampleList.filter {
            // Set search criteria
            it.getSimpleName().toLowerCase(Locale.getDefault()).contains(lowercaseSearchText) ||
              it.category.toLowerCase(Locale.getDefault()).contains(lowercaseSearchText) ||
              it.getDescription().toLowerCase(Locale.getDefault()).contains(lowercaseSearchText) ||
              it.getLabel().toLowerCase(Locale.getDefault()).contains(lowercaseSearchText)
          }
          if (filteredList.isNotEmpty()) {
            displayExampleList(filteredList)
          } else {
            Snackbar.make(
              root_layout,
              getString(R.string.no_results_for_search_query),
              Snackbar.LENGTH_SHORT
            ).show()
          }
        }
      }
    })

    clear_search_imageview.setOnClickListener {
      example_search_edittext.text.clear()
      clear_search_imageview.visibility = View.INVISIBLE
      currentlyDisplayedExampleList = allExampleList
    }
  }

  private fun displayExampleList(specificExamplesList: List<SpecificExample>) {
    if (specificExamplesList.isEmpty()) {
      return
    }
    val sections = arrayListOf<ExampleSectionAdapter.Section>()
    var currentCat = ""
    for (i in specificExamplesList.indices) {
      val category = specificExamplesList[i].category
      if (currentCat != category) {
        sections.add(ExampleSectionAdapter.Section(i, category))
        currentCat = category
      }
    }
    sectionAdapter = ExampleSectionAdapter(
      this,
      R.layout.section_main_layout,
      R.id.section_text, ExampleAdapter(specificExamplesList)
    )
    runOnUiThread {
      sectionAdapter.setSections(sections.toTypedArray())
      recyclerView.adapter = sectionAdapter
    }
    currentlyDisplayedExampleList = specificExamplesList
  }

  private fun startExample(specificExample: SpecificExample?) {
    specificExample?.let {
      startActivity(Intent().withComponent(packageName, it.name))
    }
  }

  private fun Intent.withComponent(packageName: String, exampleName: String): Intent {
    component = ComponentName(packageName, exampleName)
    return this
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    if (currentlyDisplayedExampleList.isNotEmpty()) {
      outState.putParcelableArrayList(
        KEY_STATE_EXAMPLES,
        currentlyDisplayedExampleList as ArrayList<SpecificExample>?
      )
    }
  }

  private fun loadExamples() {
    val exampleListFromManifest = ArrayList<SpecificExample>()
    val appPackageInfo = packageManager.getPackageInfo(
      packageName,
      PackageManager.GET_ACTIVITIES or PackageManager.GET_META_DATA
    )
    val packageName = applicationContext.packageName
    val metaDataKey = getString(R.string.category)
    for (info in appPackageInfo.activities) {
      if (info.labelRes != 0 && info.name.startsWith(packageName) &&
        info.name != ExampleOverviewActivity::class.java.name
      ) {
        val label = getString(info.labelRes)
        val description = resolveString(info.descriptionRes)
        val category = resolveMetaData(info.metaData, metaDataKey)
        category?.let {
          exampleListFromManifest.add(SpecificExample(info.name, label, description, it))
        }
      }
    }

    if (exampleListFromManifest.isNotEmpty()) {
      val comparator =
        Comparator<SpecificExample> { lhs, rhs ->
          var result = lhs.category.compareTo(rhs.category, true)

          if (result == 0) {
            result = lhs.getLabel().compareTo(rhs.getLabel(), true)
          }
          result
        }
      Collections.sort(exampleListFromManifest, comparator)
      this.allExampleList = exampleListFromManifest
      displayExampleList(exampleListFromManifest)
    }
  }

  private fun resolveMetaData(bundle: Bundle?, key: String): String? {
    var category: String? = null
    if (bundle != null) {
      category = bundle.getString(key)
    }
    return category
  }

  private fun resolveString(@StringRes stringRes: Int): String {
    return try {
      getString(stringRes)
    } catch (exception: Resources.NotFoundException) {
      "-"
    }
  }

  companion object {
    private const val KEY_STATE_EXAMPLES = "examplesList"
  }
}