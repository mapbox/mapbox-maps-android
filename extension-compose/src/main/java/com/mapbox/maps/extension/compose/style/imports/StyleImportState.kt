package com.mapbox.maps.extension.compose.style.imports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.ImportConfigs

/**
 * Create and remember a [StyleImportState] with init block.
 *
 * @param init the initialization block to be applied to the [StyleImportState] after created and remembered.
 *
 * @return [StyleImportState]
 */
@Composable
public inline fun rememberStyleImportState(crossinline init: StyleImportState.() -> Unit = {}): StyleImportState {
  return remember {
    StyleImportState()
  }.apply(init)
}

/**
 * The state holder for the [StyleImport].
 */
@OptIn(MapboxExperimental::class)
@Stable
public class StyleImportState internal constructor(
  initialImportConfigs: ImportConfigs?,
  initialStyleImportInteractionsState: StyleImportInteractionsState
) {
  /**
   * Construct a default [StyleImportState].
   */
  public constructor() : this(
    initialImportConfigs = null,
    initialStyleImportInteractionsState = StyleImportInteractionsState()
  )

  /**
   * The optional [ImportConfigs] for the imported style.
   */
  public var importConfigs: ImportConfigs? by mutableStateOf(initialImportConfigs)

  /**
   * The [StyleImportInteractionsState] defined for the imported style.
   */
  @MapboxExperimental
  public var interactionsState: StyleImportInteractionsState by mutableStateOf(initialStyleImportInteractionsState)
}