package com.mapbox.maps.extension.compose

import com.mapbox.maps.extension.compose.internal.LayerPositionAwareNode
import com.mapbox.maps.extension.compose.internal.MapNode
import org.junit.Assert

internal fun MapNode.assertLayerIds(expected: List<String>) {
  Assert.assertEquals(
    expected,
    children.map { (it as LayerPositionAwareNode).getLayerIds()[0] }
  )
}