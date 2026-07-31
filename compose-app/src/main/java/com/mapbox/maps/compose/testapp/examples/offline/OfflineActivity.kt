package com.mapbox.maps.compose.testapp.examples.offline

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mapbox.bindgen.Value
import com.mapbox.common.Cancelable
import com.mapbox.common.MapboxOptions
import com.mapbox.common.NetworkRestriction
import com.mapbox.common.OfflineSwitch
import com.mapbox.common.TileRegionLoadOptions
import com.mapbox.common.TileStore
import com.mapbox.geojson.Point
import com.mapbox.maps.GlyphsRasterizationMode
import com.mapbox.maps.OfflineManager
import com.mapbox.maps.Style
import com.mapbox.maps.StylePackLoadOptions
import com.mapbox.maps.TilesetDescriptorOptions
import com.mapbox.maps.compose.testapp.ExampleScaffold
import com.mapbox.maps.compose.testapp.ui.theme.MapboxMapComposeTheme
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapState
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.extension.compose.style.MapStyle
import com.mapbox.maps.mapsOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Example app that shows how to use OfflineManager and TileStore to
 * download regions for offline use.
 *
 * Please refer to our [offline guide](https://docs.mapbox.com/android/maps/guides/offline/#limits) for the limitations of the offline usage.
 */
public class OfflineActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MapboxMapComposeTheme {
        ExampleScaffold { padding ->
          OfflineScreen(modifier = Modifier.padding(padding))
        }
      }
    }
  }
}

@Composable
private fun OfflineScreen(modifier: Modifier = Modifier) {
  val tileStore: TileStore = remember { MapboxOptions.mapsOptions.tileStore!! }
  val offlineManager: OfflineManager = remember { OfflineManager() }
  val cancelables = remember { mutableStateListOf<Cancelable>() }
  val logs = remember { mutableStateListOf<LogEntry>() }
  val mapState = rememberMapState()
  fun addLog(entry: LogEntry) {
    when (entry) {
      is LogEntry.Error -> Log.e(TAG, entry.text)
      else -> Log.d(TAG, entry.text)
    }
    logs.add(0, entry)
  }

  fun removeOfflineRegions() {
    // Remove the tile region with the tile region ID.
    // Note this will not remove the downloaded tile packs, instead, it will just mark the tileset
    // not a part of a tile region. The tiles still exists as a predictive cache in TileStore.
    tileStore.removeTileRegion(TILE_REGION_ID)

    // Remove the style pack with the style url.
    // Note this will not remove the downloaded style pack, instead, it will just mark the resources
    // not a part of the existing style pack. The resources still exists as disk cache.
    offlineManager.removeStylePack(Style.STANDARD_SATELLITE)
    offlineManager.removeStylePack(Style.STANDARD)

    @OptIn(DelicateCoroutinesApi::class)
    // we can't use rememberCoroutineScope because removeOfflineRegions is called from onDispose so this job will never launch in that call
    GlobalScope.launch {
      val result = MapState.clearData()
      result.error?.let { error -> addLog(LogEntry.Error(error)) }
    }

    // Explicitly clear ambient cache data (so that if we try to download tile store regions again - it would actually truly download it from network).
    // Ambient cache data is anything not associated with an offline region or a style pack, including predictively cached data.
    // Note that it is advisable to rely on internal TileStore implementation to clear cache when needed.
    tileStore.clearAmbientCache { it.error?.let { error -> addLog(LogEntry.Error(error.message)) } }
  }

  val density = LocalDensity.current.density

  var phase by remember { mutableStateOf(Phase.Idle) }
  var satelliteProgress by remember { mutableStateOf(PackProgress()) }
  var standardProgress by remember { mutableStateOf(PackProgress()) }
  var tileProgress by remember { mutableStateOf(PackProgress()) }

  LaunchedEffect(phase, satelliteProgress, standardProgress, tileProgress) {
    if (phase == Phase.Downloading &&
      satelliteProgress.isComplete &&
      standardProgress.isComplete &&
      tileProgress.isComplete
    ) {
      phase = Phase.ReadyToView
    }
  }

  DisposableEffect(Unit) {
    onDispose {
      cancelables.forEach { it.cancel() }
      cancelables.clear()
      removeOfflineRegions()
      OfflineSwitch.getInstance().isMapboxStackConnected = true
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
  ) {
    val mapStyleUri = when (phase) {
      Phase.ViewingSatellite -> Style.STANDARD_SATELLITE
      Phase.ViewingStandard -> Style.STANDARD
      else -> null
    }
    if (mapStyleUri != null) {
      OfflineMap(styleUri = mapStyleUri, mapState = mapState, modifier = Modifier.weight(6f))
    } else {
      Spacer(Modifier.weight(6f))
      Column(
        modifier = Modifier
          .padding(vertical = 16.dp, horizontal = 8.dp)
          .fillMaxWidth()
      ) {
        ProgressRow("Satellite style pack", satelliteProgress)
        ProgressRow("Standard style pack", standardProgress)
        ProgressRow("Tile region", tileProgress)
      }
    }
    LogList(logs, modifier = Modifier.weight(4f))
    ActionBar(
      phase = phase,
      onDownload = {
        logs.clear()
        phase = Phase.Downloading
        startDownload(
          offlineManager = offlineManager,
          tileStore = tileStore,
          density = density,
          cancelables = cancelables,
          onLog = ::addLog,
          onSatelliteProgress = { satelliteProgress = it },
          onStandardProgress = { standardProgress = it },
          onTileProgress = { tileProgress = it },
        )
      },
      onCancel = {
        cancelables.forEach { it.cancel() }
        cancelables.clear()
        phase = Phase.Idle
      },
      onViewSatellite = {
        OfflineSwitch.getInstance().isMapboxStackConnected = false
        addLog(LogEntry.Info("Mapbox network stack disabled."))
        phase = Phase.ViewingSatellite
      },
      onViewStandard = { phase = Phase.ViewingStandard },
      onShowRegions = { showDownloaded(tileStore, offlineManager, ::addLog) },
      onRemove = {
        removeOfflineRegions()
        satelliteProgress = PackProgress()
        standardProgress = PackProgress()
        tileProgress = PackProgress()
        OfflineSwitch.getInstance().isMapboxStackConnected = true
        addLog(LogEntry.Info("Mapbox network stack enabled."))
        phase = Phase.Cleared
      },
    )
  }
}

@Composable
private fun OfflineMap(styleUri: String, mapState: MapState, modifier: Modifier = Modifier) {
  val mapViewportState = rememberMapViewportState {
    setCameraOptions {
      zoom(ZOOM)
      center(TOKYO)
    }
  }
  LaunchedEffect(styleUri) {
    if (styleUri == Style.STANDARD) {
      mapViewportState.flyTo(
        cameraOptions {
          center(Point.fromLngLat(139.76567069012344, 35.68134814430844))
          zoom(15.0)
          bearing(356.1)
          pitch(59.8)
        },
        mapAnimationOptions { duration(1000L) }
      )
    }
  }
  MapboxMap(
    modifier = modifier.fillMaxWidth(),
    mapViewportState = mapViewportState,
    mapState = mapState,
    style = { MapStyle(style = styleUri) },
  )
}

@Composable
private fun ColumnScope.ActionBar(
  phase: Phase,
  onDownload: () -> Unit,
  onCancel: () -> Unit,
  onViewSatellite: () -> Unit,
  onViewStandard: () -> Unit,
  onShowRegions: () -> Unit,
  onRemove: () -> Unit,
) {
  val textModifier = Modifier
    .navigationBarsPadding()
    .padding(vertical = 8.dp)
  when (phase) {
    Phase.Idle, Phase.Cleared -> Button(
      onClick = onDownload,
      modifier = Modifier.fillMaxWidth()
    ) { Text(modifier = textModifier, text = "Download") }

    Phase.Downloading -> Button(
      onClick = onCancel,
      modifier = Modifier.fillMaxWidth()
    ) { Text(modifier = textModifier, text = "Cancel download") }

    Phase.ReadyToView -> Button(
      onClick = onViewSatellite,
      modifier = Modifier.fillMaxWidth()
    ) { Text(modifier = textModifier, text = "View standard satellite (offline)") }

    Phase.ViewingSatellite -> Button(
      onClick = onViewStandard,
      modifier = Modifier.fillMaxWidth()
    ) { Text(modifier = textModifier, text = "View standard map") }

    Phase.ViewingStandard -> {
      Button(
        onClick = onShowRegions,
        modifier = Modifier.fillMaxWidth()
      ) { Text("Show downloaded regions") }
      Spacer(Modifier.height(4.dp))
      Button(
        onClick = onRemove,
        modifier = Modifier.fillMaxWidth()
      ) { Text(modifier = textModifier, text = "Remove downloaded regions") }
    }
  }
}

@Composable
private fun ProgressRow(label: String, p: PackProgress) {
  Column(
    Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp)
  ) {
    Text(label, style = MaterialTheme.typography.caption)
    LinearProgressIndicator(
      progress = p.fraction,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
private fun LogList(logs: List<LogEntry>, modifier: Modifier = Modifier) {
  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .background(MaterialTheme.colors.background),
    verticalArrangement = Arrangement.Top,
    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
  ) {
    items(logs) { entry ->
      Text(
        text = entry.text,
        color = if (entry.color == Color.Unspecified) MaterialTheme.colors.onBackground else entry.color,
        modifier = Modifier.padding(vertical = 2.dp),
      )
    }
  }
}

private fun startDownload(
  offlineManager: OfflineManager,
  tileStore: TileStore,
  density: Float,
  cancelables: MutableList<Cancelable>,
  onLog: (LogEntry) -> Unit,
  onSatelliteProgress: (PackProgress) -> Unit,
  onStandardProgress: (PackProgress) -> Unit,
  onTileProgress: (PackProgress) -> Unit,
) {
  // 1. Create style package with loadStylePack() call.

  // A style pack (a Style offline package) contains the loaded style and its resources: loaded
  // sources, fonts, sprites. Style packs are identified with their style URI.

  // Style packs are stored in the disk cache database, but their resources are not subject to
  // the data eviction algorithm and are not considered when calculating the disk cache size.
  cancelables.add(
    offlineManager.loadStylePack(
      Style.STANDARD_SATELLITE,
      // Build Style pack load options
      StylePackLoadOptions.Builder()
        .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
        .metadata(Value(STYLE_PACK_SAT_METADATA))
        .build(),
      { progress ->
        onSatelliteProgress(PackProgress(progress.completedResourceCount, progress.requiredResourceCount))
        onLog(LogEntry.StyleProgress("StylePackLoadProgress (sat): $progress"))
      },
      { expected ->
        expected.value?.let { onLog(LogEntry.Success("StylePack downloaded: $it")) }
        expected.error?.let { onLog(LogEntry.Error("StylePackError: $it")) }
      }
    )
  )

  // Download standard style pack
  cancelables.add(
    offlineManager.loadStylePack(
      Style.STANDARD,
      StylePackLoadOptions.Builder()
        .glyphsRasterizationMode(GlyphsRasterizationMode.IDEOGRAPHS_RASTERIZED_LOCALLY)
        .metadata(Value(STYLE_PACK_STD_METADATA))
        .build(),
      { progress ->
        onStandardProgress(PackProgress(progress.completedResourceCount, progress.requiredResourceCount))
        onLog(LogEntry.StyleProgress("StylePackLoadProgress (std): $progress"))
      },
      { expected ->
        expected.value?.let { onLog(LogEntry.Success("StylePack downloaded: $it")) }
        expected.error?.let { onLog(LogEntry.Error("StylePackError: $it")) }
      }
    )
  )

  // 2. Create a tile region with tiles for the satellite street style

  // A Tile Region represents an identifiable geographic tile region with metadata, consisting of
  // a set of tiles packs that cover a given area (a polygon). Tile Regions allow caching tiles
  // packs in an explicit way: By creating a Tile Region, developers can ensure that all tiles in
  // that region will be downloaded and remain cached until explicitly deleted.

  // Creating a Tile Region requires supplying a description of the area geometry, the tilesets
  // and zoom ranges of the tiles within the region.

  // The tileset descriptor encapsulates the tile-specific data, such as which tilesets, zoom ranges,
  // pixel ratio etc. the cached tile packs should have. It is passed to the Tile Store along with
  // the region area geometry to load a new Tile Region.

  // The OfflineManager is responsible for creating tileset descriptors for the given style and zoom range.
  val tilesetDescriptors = listOf(
    offlineManager.createTilesetDescriptor(
      TilesetDescriptorOptions.Builder()
        .styleURI(Style.STANDARD_SATELLITE)
        .pixelRatio(density)
        .minZoom(0)
        .maxZoom(16)
        .build()
    ),
    offlineManager.createTilesetDescriptor(
      TilesetDescriptorOptions.Builder()
        .styleURI(Style.STANDARD)
        .pixelRatio(density)
        .minZoom(0)
        .maxZoom(16)
        .build()
    ),
  )

  // Use the default TileStore to load this region. You can create custom TileStores that are
  // unique for a particular file path, i.e. there is only ever one TileStore per unique path.

  // Note that the TileStore path must be the same with the TileStore used when initialize the MapView.
  cancelables.add(
    tileStore.loadTileRegion(
      TILE_REGION_ID,
      TileRegionLoadOptions.Builder()
        .geometry(TOKYO)
        .descriptors(tilesetDescriptors)
        .metadata(Value(TILE_REGION_METADATA))
        .acceptExpired(true)
        .networkRestriction(NetworkRestriction.NONE)
        .build(),
      { progress ->
        onTileProgress(PackProgress(progress.completedResourceCount, progress.requiredResourceCount))
        onLog(LogEntry.TileProgress("TileRegionLoadProgress: $progress"))
      },
    ) { expected ->
      expected.value?.let { onLog(LogEntry.Success("TileRegion downloaded: $it")) }
      expected.error?.let { onLog(LogEntry.Error("TileRegionError: $it")) }
    }
  )
}

private fun showDownloaded(
  tileStore: TileStore,
  offlineManager: OfflineManager,
  onLog: (LogEntry) -> Unit,
) {
  // Get a list of tile regions that are currently available.
  tileStore.getAllTileRegions { e ->
    e.value?.let { onLog(LogEntry.Info("Existing tile regions: $it")) }
    e.error?.let { onLog(LogEntry.Error("TileRegionError: $it")) }
  }
  // Get a list of style packs that are currently available.
  offlineManager.getAllStylePacks { e ->
    e.value?.let { onLog(LogEntry.Info("Existing style packs: $it")) }
    e.error?.let { onLog(LogEntry.Error("StylePackError: $it")) }
  }
}

private enum class Phase {
  Idle, Downloading, ReadyToView, ViewingSatellite, ViewingStandard, Cleared
}

private data class PackProgress(val current: Long = 0, val total: Long = 0) {
  val isComplete: Boolean = total > 0 && total <= current
  val fraction: Float = if (total <= 0) 0f else current.toFloat() / total.toFloat()
}

private sealed class LogEntry(val text: String, val color: Color) {
  class Info(text: String) : LogEntry(text, Color.Unspecified)
  class Success(text: String) : LogEntry(text, Color(0xFF2E7D32))
  class Error(text: String) : LogEntry(text, Color(0xFFC62828))
  class StyleProgress(text: String) : LogEntry(text, Color(0xFFE65100))
  class TileProgress(text: String) : LogEntry(text, Color(0xFF6A1B9A))
}

private const val TAG = "OfflineActivity"
private const val ZOOM = 12.0
private val TOKYO = Point.fromLngLat(139.769305, 35.682027)
private const val TILE_REGION_ID = "myTileRegion"
private const val STYLE_PACK_SAT_METADATA = "my-standard-satellite-style-pack"
private const val STYLE_PACK_STD_METADATA = "my-standard-style-pack"
private const val TILE_REGION_METADATA = "my-offline-region"