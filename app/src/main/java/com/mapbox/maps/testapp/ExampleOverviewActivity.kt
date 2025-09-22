package com.mapbox.maps.testapp

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mapbox.maps.Version
import com.mapbox.maps.testapp.adapter.ExampleAdapter
import com.mapbox.maps.testapp.adapter.ExampleSectionAdapter
import com.mapbox.maps.testapp.databinding.ActivityExampleOverviewBinding
import com.mapbox.maps.testapp.model.SpecificExample
import com.mapbox.maps.testapp.utils.ItemClickSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity shown when application is started
 *
 * This activity will generate data for RecyclerView based on the AndroidManifest entries.
 * It uses tags as category and description to order the different entries.
 *
 */
class ExampleOverviewActivity : AppCompatActivity() {

  private lateinit var sectionAdapter: ExampleSectionAdapter
  private var currentlyDisplayedExampleList: List<SpecificExample> = ArrayList()
  private var allExampleList: List<SpecificExample> = ArrayList()

  private lateinit var binding: ActivityExampleOverviewBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityExampleOverviewBinding.inflate(layoutInflater)
    setContentView(binding.root)

    @SuppressLint("RestrictedApi")
    val glNativeVersion =
      "Maps: ${Version.getVersionString()} (${Version.getRevisionString()})"
    @SuppressLint("RestrictedApi")
    val commonVersion =
      "Common: ${com.mapbox.common.Version.getCommonSDKVersionString()} (${com.mapbox.common.Version.getCommonSDKRevisionString()})"
    @SuppressLint("SetTextI18n")
    binding.sdkVersions.text = "$glNativeVersion; $commonVersion"

    binding.recyclerView.apply {
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

    lifecycleScope.launch {
      // Always load the examples list. Otherwise, filtering will not work.
      allExampleList = loadExamples()
      if (savedInstanceState == null || !savedInstanceState.containsKey(KEY_STATE_EXAMPLES)) {
        displayExampleList(allExampleList)
      } else {
        @Suppress("DEPRECATION")
        displayExampleList(savedInstanceState.getParcelableArrayList(KEY_STATE_EXAMPLES)!!)
      }
    }

    lifecycleScope.launch {
      callbackFlow {
        val callback = object : TextWatcher {
          override fun afterTextChanged(s: Editable) {
            // Empty because this callback isn't useful for the app
          }

          override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // Empty because this callback isn't useful for the app
          }

          override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
            trySendBlocking(text.toString())
          }
        }
        binding.exampleSearchEdittext.addTextChangedListener(callback)
        awaitClose {
          binding.exampleSearchEdittext.removeTextChangedListener(callback)
        }
      }.conflate()
        .onEach {
          if (it.isEmpty()) {
            binding.clearSearchImageview.visibility = View.INVISIBLE
          } else {
            binding.clearSearchImageview.visibility = View.VISIBLE
          }
        }
        .flowOn(Dispatchers.Main)
        .map { textFilter ->
          if (textFilter.isEmpty()) {
            allExampleList
          } else {
            allExampleList.filter { example ->
              with(example) {
                simpleName.contains(textFilter, true) ||
                  category.contains(textFilter, true) ||
                  description.contains(textFilter, true) ||
                  label.contains(textFilter, true)
              }
            }
          }
        }
        .flowOn(Dispatchers.Default)
        .collectLatest { filteredExamples ->
          displayExampleList(filteredExamples)
          if (filteredExamples.isEmpty() && allExampleList.isNotEmpty()) {
            Snackbar.make(
              binding.rootLayout,
              getString(R.string.no_results_for_search_query),
              Snackbar.LENGTH_SHORT
            ).show()
          }
        }
    }

    binding.clearSearchImageview.setOnClickListener {
      binding.exampleSearchEdittext.text.clear()
    }
  }

  private suspend fun displayExampleList(specificExamplesList: List<SpecificExample>) = withContext(Dispatchers.Default) {
    if (specificExamplesList.isEmpty()) {
      return@withContext
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
      this@ExampleOverviewActivity,
      R.layout.section_main_layout,
      R.id.section_text, ExampleAdapter(specificExamplesList)
    )
    sectionAdapter.setSections(sections.toTypedArray())
    withContext(Dispatchers.Main.immediate) {
      binding.recyclerView.adapter = sectionAdapter
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

  private suspend fun loadExamples(): List<SpecificExample> = withContext(Dispatchers.Default) {
    val categorizedExamples = mutableListOf<SpecificExample>()
    val nonCategorizedExamples = mutableListOf<SpecificExample>()
    @Suppress("DEPRECATION")
    val appPackageInfo = packageManager.getPackageInfo(
      packageName,
      PackageManager.GET_ACTIVITIES or PackageManager.GET_META_DATA
    )
    // We use this activity package name in case the `applicationId`/`packageName` is different
    val packageName = ExampleOverviewActivity::class.java.`package`!!.name
    val categoryKey = getString(R.string.category)
    for (info in appPackageInfo.activities) {
      if (info.labelRes != 0 && info.name.startsWith(packageName) &&
        info.name != ExampleOverviewActivity::class.java.name
      ) {
        val label = getString(info.labelRes)
        val description = runCatching { getString(info.descriptionRes) }.getOrDefault("-")
        val category = info.metaData?.getString(categoryKey)
        if (category != null) {
          categorizedExamples.add(SpecificExample(info.name, label, description, category))
        } else {
          nonCategorizedExamples.add(SpecificExample(info.name, label, description, "Other"))
        }
      }
    }

    if (categorizedExamples.isNotEmpty()) {
      val comparator =
        Comparator<SpecificExample> { lhs, rhs ->
          var result = lhs.category.compareTo(rhs.category, true)

          if (result == 0) {
            result = lhs.label.compareTo(rhs.label, true)
          }
          result
        }
      categorizedExamples.sortWith(comparator)
    }
    return@withContext categorizedExamples + nonCategorizedExamples
  }

  companion object {
    private const val KEY_STATE_EXAMPLES = "examplesList"
  }
}