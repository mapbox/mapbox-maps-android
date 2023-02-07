package com.mapbox.maps.compose.testapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.data.model.ExampleCategory

/**
 * Display the [ExampleCategory] as a [Card] in the UI.
 */
@Composable
internal fun ExampleCategoryItem(
  category: ExampleCategory,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier
      .padding(8.dp)
      .fillMaxSize(),
    elevation = 0.dp
  ) {
    Text(text = category.category, style = typography.h4)
  }
}