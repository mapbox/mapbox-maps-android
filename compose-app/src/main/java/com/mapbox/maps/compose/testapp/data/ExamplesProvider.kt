package com.mapbox.maps.compose.testapp.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.mapbox.maps.compose.testapp.ExampleOverviewActivity
import com.mapbox.maps.compose.testapp.R
import com.mapbox.maps.compose.testapp.data.model.ExampleCategory
import com.mapbox.maps.compose.testapp.data.model.ExampleEntry
import com.mapbox.maps.compose.testapp.data.model.SpecificExample

/**
 * Provides the [ExampleEntry] list or mock data to be used in the UI.
 */
public object ExamplesProvider {
  private fun loadExamplesFromManifest(context: Context): List<SpecificExample> {
    val appPackageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.PackageInfoFlags.of(PackageManager.GET_ACTIVITIES.toLong() or PackageManager.GET_META_DATA.toLong())
      )
    } else {
      @Suppress("DEPRECATION")
      context.packageManager.getPackageInfo(
        context.packageName,
        PackageManager.GET_ACTIVITIES or PackageManager.GET_META_DATA
      )
    }
    // We use this activity package name in case the `applicationId`/`packageName` is different
    val packageName = ExampleOverviewActivity::class.java.`package`!!.name
    val categoryKey = context.getString(R.string.category)
    return appPackageInfo.activities.filter { info ->
      info.labelRes != 0 && info.name.startsWith(packageName) && info.name != ExampleOverviewActivity::class.java.name
    }.mapNotNull { info ->
      info.metaData?.getString(categoryKey)?.let {
        val label = context.getString(info.labelRes)
        val description = runCatching { context.getString(info.descriptionRes) }.getOrDefault("-")
        SpecificExample(info.name, label, description, it)
      }
    }
  }

  /**
   * Load [ExampleEntry] list from the AndroidManifest.
   */
  public fun loadExampleEntries(context: Context): List<Pair<ExampleCategory, List<SpecificExample>>> =
    loadExamplesFromManifest(context).groupBy(SpecificExample::category).mapKeys {
      ExampleCategory(it.key)
    }.toList().sortedBy { it.first.category }

  /**
   * Load mock [ExampleEntry] list to preview in Android Studio.
   */
  public fun loadMockExampleEntries(
    categoriesCount: Int = 5,
    examplesPerCategory: Int = 4
  ): List<Pair<ExampleCategory, List<SpecificExample>>> =
    List(categoriesCount) { categoryCount ->
      val exampleCategory = ExampleCategory("${MockEntries.CATEGORY} $categoryCount")
      val examples = List(examplesPerCategory) {
        SpecificExample(
          name = "${MockEntries.NAME} $it ",
          label = "${MockEntries.LABEL} $it ",
          description = "${MockEntries.DESCRIPTION} $it ",
          category = "${MockEntries.CATEGORY} $categoryCount"
        )
      }
      exampleCategory to examples
    }

  private object MockEntries {
    const val CATEGORY = "Category"
    const val NAME = "Example"
    const val LABEL = "Label"
    const val DESCRIPTION = "Description"
  }
}