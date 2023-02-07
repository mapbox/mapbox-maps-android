package com.mapbox.maps.compose.testapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.data.model.SpecificExample

/**
 * Display the [SpecificExample] as a [Card] in the UI.
 */
@Composable
internal fun ExampleListItem(
  specificExample: SpecificExample,
  navigateToExample: (SpecificExample) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .padding(8.dp)
      .fillMaxSize(),
    shape = RoundedCornerShape(corner = CornerSize(16.dp))
  ) {
    Row(Modifier.clickable { navigateToExample(specificExample) }) {
      Column(
        modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth()
          .align(Alignment.CenterVertically)
      ) {
        Text(text = specificExample.getLabel(), style = typography.h6)
        Text(text = specificExample.getDescription(), style = typography.caption)
      }
    }
  }
}