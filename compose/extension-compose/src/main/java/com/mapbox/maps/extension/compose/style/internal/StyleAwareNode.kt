package com.mapbox.maps.extension.compose.style.internal

import com.mapbox.maps.extension.compose.internal.MapNode

internal abstract class StyleAwareNode : MapNode() {
  abstract val mapStyleNode: MapStyleNode
}