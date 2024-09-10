package com.mapbox.maps.extension.compose.style.imports

import android.webkit.URLUtil
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberCoroutineScope
import com.mapbox.maps.ImportPosition
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMapScopeMarker
import com.mapbox.maps.extension.compose.internal.MapApplier
import com.mapbox.maps.extension.compose.internal.MapNode
import com.mapbox.maps.extension.compose.style.ImportConfigs
import com.mapbox.maps.extension.compose.style.internal.MapStyleNode
import com.mapbox.maps.logD
import com.mapbox.maps.logE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

internal class StyleImportNode(
  private var importId: String,
  private var style: String,
  private var config: ImportConfigs?,
  private val coroutineScope: CoroutineScope
) : MapNode() {
  private lateinit var mapStyleNode: MapStyleNode
  override fun onAttached(parent: MapNode) {
    logD(TAG, "onAttached")
    mapStyleNode = parent as MapStyleNode
    addStyleImport()
  }

  private fun addStyleImport() {
    val mapboxMap = mapStyleNode.mapboxMap
    coroutineScope.launch {
      // We only care about the first style data loaded events, as adding style import will also trigger
      // style data loaded events, so we will receive duplicated calls if we use collect here.
      mapStyleNode.styleDataLoaded.firstOrNull().let {
        if (style.isValidUri()) {
          mapboxMap.addStyleImportFromURI(
            importId = importId,
            uri = style,
            config = config?.configs,
            importPosition = getRelativePositionInfo()
          )
        } else {
          mapStyleNode.mapboxMap.addStyleImportFromJSON(
            importId = importId,
            json = style,
            config = config?.configs,
            importPosition = getRelativePositionInfo()
          )
        }.onError {
          logE(TAG, it)
        }.onValue {
          logD(TAG, "added StyleImport($importId, $style, $config, null)")
        }
      }
    }
  }

  private fun removeStyleImport() {
    mapStyleNode.mapboxMap.removeStyleImport(importId)
  }

  internal fun updateImportId(importId: String) {
    removeStyleImport()
    this.importId = importId
    addStyleImport()
  }

  internal fun updateStyle(style: String) {
    removeStyleImport()
    this.style = style
    addStyleImport()
  }

  private fun getRelativePositionInfo(): ImportPosition {
    return ImportPosition(
      /* above = */ null,
      /* below = */ with(mapStyleNode.children.filterIsInstance<StyleImportNode>()) {
        elementAtOrNull(indexOf(this@StyleImportNode) + 1)?.importId
      },
      /* at = */ null
    )
  }

  internal fun updateConfigs(configs: ImportConfigs?) {
    mapStyleNode.mapboxMap.setStyleImportConfigProperties(importId, configs?.configs ?: hashMapOf())
  }

  override fun onRemoved(parent: MapNode) {
    cleanUp()
  }

  override fun onClear() {
    cleanUp()
  }

  private fun cleanUp() {
    removeStyleImport()
    this.children.forEach { it.onClear() }
  }

  override fun toString(): String {
    return "StyleImportNode(importId=$importId)"
  }

  private fun String.isValidUri(): Boolean {
    val isMapboxStyleUri = startsWith("mapbox://", ignoreCase = true)
    val isMapboxAssetUri = startsWith("asset://", ignoreCase = true)
    val isMapboxFileUri = startsWith("file://", ignoreCase = true)
    return isMapboxStyleUri || isMapboxAssetUri || isMapboxFileUri || URLUtil.isValidUrl(this)
  }

  private companion object {
    private const val TAG = "StyleImportNode"
  }
}

/**
 * A [StyleImportsScope] provides a scope for adding [StyleImport].
 */
@MapboxMapScopeMarker
@Immutable
public class StyleImportsScope internal constructor() {
  /**
   * Add a Style Import to the current style.
   *
   * @param importId the id of the style import.
   * @param style the style uri or style json of the style import.
   * @param configs the optional [ImportConfigs] for the imported style.
   */
  @Composable
  @MapboxStyleImportComposable
  @Deprecated("Use overloaded StyleImport composable function with StyleImportState instead")
  public fun StyleImport(
    importId: String,
    style: String,
    configs: ImportConfigs? = null,
  ) {
    StyleImport(
      importId = importId,
      style = style,
      styleImportState = rememberStyleImportState {
        importConfigs = configs
      }
    )
  }

  /**
   * Add a Style Import to the current style.
   *
   * @param importId the id of the style import.
   * @param style the style uri or style json of the style import.
   * @param styleImportState The state holder for the [StyleImport].
   */
  @OptIn(MapboxExperimental::class)
  @Composable
  @MapboxStyleImportComposable
  public fun StyleImport(
    importId: String,
    style: String,
    styleImportState: StyleImportState = rememberStyleImportState()
  ) {
    val applier = currentComposer.applier as? MapApplier
      ?: throw IllegalStateException("Illegal use of StyleImport composable outside of MapboxMapComposable")
    key(styleImportState.interactionsState) {
      styleImportState.interactionsState.BindTo(
        mapboxMap = applier.mapView.mapboxMap,
        importId = importId
      )
    }
    val coroutineScope = rememberCoroutineScope()
    // Insert a MapNode to the MapApplier node tree
    ComposeNode<StyleImportNode, MapApplier>(
      factory = {
        StyleImportNode(importId, style, styleImportState.importConfigs, coroutineScope)
      },
      update = {
        update(importId) {
          updateImportId(it)
        }
        update(style) {
          updateStyle(it)
        }
        update(styleImportState.importConfigs) {
          updateConfigs(styleImportState.importConfigs)
        }
      }
    )
  }
}