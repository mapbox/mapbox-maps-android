package com.mapbox.maps.compose.testapp.examples.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

/**
 * Example showcasing [MapboxMap] inside a [LazyColumn].
 */
public class LazyColumnMapActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold {
          // Known issue on Compose Foundation 1.7.x (e.g. composeBom 2025.12.01):
          // LazyColumn prefetch uses PausableComposition, which can apply changes to a deactivated
          // AndroidView-backed slot and crash with "Apply is called on a deactivated node".
          // Workaround: disable prefetch via a no-op LazyListPrefetchStrategy:
          //
          // val listState = rememberLazyListState(
          //   prefetchStrategy = object : LazyListPrefetchStrategy {
          //     override fun LazyListPrefetchScope.onScroll(delta: Float, layoutInfo: LazyListLayoutInfo) {}
          //     override fun LazyListPrefetchScope.onVisibleItemsUpdated(layoutInfo: LazyListLayoutInfo) {}
          //     override fun NestedPrefetchScope.onNestedPrefetch(firstVisibleItemIndex: Int) {}
          //   }
          // )
          // LazyColumn(state = listState, modifier = Modifier.padding(it)) {
          LazyColumn(modifier = Modifier.padding(it)) {
            repeat(10) {
              item {
                MapboxMap(modifier = Modifier.height(MAP_ITEM_HEIGHT_DP.dp))
              }
            }
          }
        }
      }
    }
  }

  private companion object {
    const val MAP_ITEM_HEIGHT_DP = 200
  }
}