package com.mapbox.maps.compose.testapp.data

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.StringRes
import com.mapbox.maps.compose.testapp.ExampleOverviewActivity
import com.mapbox.maps.compose.testapp.data.model.ExampleCategory
import com.mapbox.maps.compose.testapp.data.model.ExampleEntry
import com.mapbox.maps.compose.testapp.data.model.SpecificExample
import java.util.*

/**
 * Provides the [ExampleEntry] list or mock data to be used in the UI.
 */
public object ExamplesProvider {
  private fun resolveMetaData(bundle: Bundle?, key: String): String? {
    var category: String? = null
    if (bundle != null) {
      category = bundle.getString(key)
    }
    return category
  }

  private fun Context.resolveString(@StringRes stringRes: Int): String {
    return try {
      getString(stringRes)
    } catch (exception: Resources.NotFoundException) {
      "-"
    }
  }

  private fun loadExamplesFromManifest(context: Context): List<SpecificExample> {
    val exampleListFromManifest = ArrayList<SpecificExample>()
    val appPackageInfo = context.packageManager.getPackageInfo(
      context.packageName,
      PackageManager.GET_ACTIVITIES or PackageManager.GET_META_DATA
    )
    val packageName = context.applicationContext.packageName
    val metaDataKey = context.getString(com.mapbox.maps.compose.testapp.R.string.category)
    for (info in appPackageInfo.activities) {
      if (info.labelRes != 0 && info.name.startsWith(packageName) &&
        info.name != ExampleOverviewActivity::class.java.name
      ) {
        val label = context.getString(info.labelRes)
        val description = context.resolveString(info.descriptionRes)
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
    }
    return exampleListFromManifest
  }

  /**
   * Load [ExampleEntry] list from the AndroidManifest.
   */
  public fun loadExampleEntries(context: Context): List<ExampleEntry> {
    return loadExamplesFromManifest(context).groupBy(SpecificExample::category).entries.flatMap {
      mutableListOf(ExampleCategory(it.key)) + it.value
    }
  }

  /**
   * Load mock [ExampleEntry] list to preview in Android Studio.
   */
  public fun loadMockExampleEntries(
    categoriesCount: Int = 5,
    examplesPerCategory: Int = 4
  ): List<ExampleEntry> {
    val mockExamples = mutableListOf<ExampleEntry>()
    repeat(categoriesCount) { categoryCount ->
      mockExamples.add(ExampleCategory("${MockEntries.CATEGORY} $categoryCount"))
      repeat(examplesPerCategory) {
        mockExamples.add(
          SpecificExample(
            name = "${MockEntries.NAME} $it ",
            label = "${MockEntries.LABEL} $it ",
            description = "${MockEntries.DESCRIPTION} $it ",
            category = "${MockEntries.CATEGORY} $categoryCount"
          )
        )
      }
    }
    return mockExamples
  }

  private object MockEntries {
    const val CATEGORY = "Category"
    const val NAME = "Example"
    const val LABEL = "Label"
    const val DESCRIPTION = "Description"
  }
}