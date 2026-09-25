package com.mapbox.maps.extension.compose.internal

import com.mapbox.maps.LayerPosition
import com.mapbox.maps.extension.compose.SimpleLayerPositionAwareNode
import com.mapbox.maps.extension.compose.assertLayerIds
import com.mapbox.maps.extension.compose.style.internal.StyleLayerPositionNode
import org.junit.Assert
import org.junit.Test

public class LayerPositionAwareNodeTest {
  /**
   * Validates the relative position in a compose block like:
   * ```
   * aboveLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   *   layer("layer_03")
   *   layer("layer_04")
   * }
   * ```
   * The order from top most visible layer to bottom most visible layer should be:
   * ```
   * "layer_04"
   * "layer_03"
   * "layer_02"
   * "layer_01"
   * "style_layer"
   * ```
   */
  @Test
  public fun `relative position above layer`() {
    // First create parent node that is created when calling `aboveLayer("style_layer")` composable
    val parent = StyleLayerPositionNode(LayerPosition("style_layer", null, null))
    // Then create the children nodes that are created when calling `layer("layer_XX")` composable functions
    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")
    val layer4 = SimpleLayerPositionAwareNode("layer_04")

    // MapApplier adds the children in order: 1,2,3 and 4. So we do the same and validate the positions:
    parent.addAndValidatePosition(node = layer1, above = "style_layer")
    parent.addAndValidatePosition(node = layer2, above = "layer_01")
    parent.addAndValidatePosition(node = layer3, above = "layer_02")
    parent.addAndValidatePosition(node = layer4, above = "layer_03")
  }

  /**
   * Validates the relative position in a compose block like:
   * ```
   * belowLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   *   layer("layer_03")
   *   layer("layer_04")
   * }
   * ```
   * The order from top most visible layer to bottom most visible layer should be:
   * ```
   * "style_layer"
   * "layer_04"
   * "layer_03"
   * "layer_02"
   * "layer_01"
   * ```
   */
  @Test
  public fun `relative position below layer`() {
    // First create parent node that is created when calling `belowLayer("style_layer")` composable
    val parent = StyleLayerPositionNode(LayerPosition(null, "style_layer", null))
    // Then create the children nodes that are created when calling `layer("layer_XX")` composable functions
    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")
    val layer4 = SimpleLayerPositionAwareNode("layer_04")

    // MapApplier adds the children in order: 1,2,3 and 4. So we do the same and validate the positions:
    parent.addAndValidatePosition(node = layer1, below = "style_layer")
    // current render layers:
    // "style_layer"
    // "layer_01"

    parent.addAndValidatePosition(node = layer2, above = "layer_01")
    // current render layers:
    // "style_layer"
    // "layer_02"
    // "layer_01"

    parent.addAndValidatePosition(node = layer3, above = "layer_02")
    // current render layers:
    // "style_layer"
    // "layer_03"
    // "layer_02"
    // "layer_01"

    parent.addAndValidatePosition(node = layer4, above = "layer_03")
    // current render layers:
    // "style_layer"
    // "layer_04"
    // "layer_03"
    // "layer_02"
    // "layer_01"
  }

  /**
   * Validates the relative position when new layers are placed in the middle and extremes in a compose block like:
   * ```
   * aboveLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   * }
   * ```
   * Then adds `layer_03` in between `layer_01` and `layer_02`. Then`layer_04` at the end and `layer_05` at the beginning.
   */
  @Test
  public fun `relative position above layer updates`() {
    // First create parent node that is created when calling `aboveLayer("style_layer")` composable
    val parent = StyleLayerPositionNode(LayerPosition("style_layer", null, null))
    // Then create the children nodes that are created when calling `layer("layer_XX")` composable functions
    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")
    val layer4 = SimpleLayerPositionAwareNode("layer_04")
    val layer5 = SimpleLayerPositionAwareNode("layer_05")

    // MapApplier adds the children in order: 1,2,3 and 4. So we do the same and validate the positions:
    parent.addAndValidatePosition(layer1, 0, "style_layer")
    parent.addAndValidatePosition(layer2, 1, "layer_01")
    parent.assertLayerIds(listOf("layer_01", "layer_02"))
    // current render layers:
    // "layer_02"
    // "layer_01"
    // "style_layer"

    // Insert layer_03 between layer_01 and layer_02
    parent.addAndValidatePosition(layer3, parent.children.size / 2, "layer_01")
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02"))
    // current render layers:
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "style_layer"

    // Insert layer_04 at the end
    parent.addAndValidatePosition(layer4, parent.children.size, "layer_02")
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02", "layer_04"))
    // current render layers:
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "style_layer"

    // Insert layer_05 at the beginning
    parent.addAndValidatePosition(layer5, 0, "style_layer")
    parent.assertLayerIds(listOf("layer_05", "layer_01", "layer_03", "layer_02", "layer_04"))
    // current render layers:
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "layer_05"
    // "style_layer"
  }

  /**
   * Validates the relative position when new layers are placed in the middle and extremes in a compose block like:
   * ```
   * belowLayer("style_layer") {
   *   layer("layer_01")
   *   layer("layer_02")
   * }
   * ```
   * The adds `layer_03` in between `layer_01` and `layer_02`. Then`layer_04` at the end and `layer_05` at the beginning.
   */
  @Test
  public fun `relative position below layer updates`() {
    // First create parent node that is created when calling `aboveLayer("style_layer")` composable
    val parent = StyleLayerPositionNode(LayerPosition(null, "style_layer", null))
    // Then create the children nodes that are created when calling `layer("layer_XX")` composable functions
    val layer1 = SimpleLayerPositionAwareNode("layer_01")
    val layer2 = SimpleLayerPositionAwareNode("layer_02")
    val layer3 = SimpleLayerPositionAwareNode("layer_03")
    val layer4 = SimpleLayerPositionAwareNode("layer_04")
    val layer5 = SimpleLayerPositionAwareNode("layer_05")

    // MapApplier adds the children in order: 1,2,3 and 4. So we do the same and validate the positions:
    parent.addAndValidatePosition(layer1, 0, below = "style_layer")
    parent.addAndValidatePosition(layer2, 1, above = "layer_01")
    parent.assertLayerIds(listOf("layer_01", "layer_02"))
    // current render layers:
    // "style_layer"
    // "layer_02"
    // "layer_01"

    // Insert layer_03 between layer_01 and layer_02
    parent.addAndValidatePosition(layer3, parent.children.size / 2, above = "layer_01")
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02"))
    // current render layers:
    // "style_layer"
    // "layer_02"
    // "layer_03"
    // "layer_01"

    // Insert layer_04 at the end
    parent.addAndValidatePosition(layer4, parent.children.size, above = "layer_02")
    parent.assertLayerIds(listOf("layer_01", "layer_03", "layer_02", "layer_04"))
    // current render layers:
    // "style_layer"
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"

    // Insert layer_05 at the beginning
    parent.addAndValidatePosition(layer5, 0, below = "style_layer")
    parent.assertLayerIds(listOf("layer_05", "layer_01", "layer_03", "layer_02", "layer_04"))
    // current render layers:
    // "style_layer"
    // "layer_04"
    // "layer_02"
    // "layer_03"
    // "layer_01"
    // "layer_05"
  }
}

internal fun StyleLayerPositionNode.addAndValidatePosition(
  node: SimpleLayerPositionAwareNode,
  idx: Int? = null,
  above: String? = null,
  below: String? = null
) {
  if (idx != null) {
    children.add(idx, node)
  } else {
    children.add(node)
  }
  val relativePosition = node.getRelativePositionInfo(this)!!
  Assert.assertEquals(above, relativePosition.above)
  Assert.assertEquals(below, relativePosition.below)
}