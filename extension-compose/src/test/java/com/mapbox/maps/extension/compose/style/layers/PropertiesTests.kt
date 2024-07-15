package com.mapbox.maps.extension.compose.style.layers

import com.mapbox.maps.extension.compose.style.StyleImage
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
public class PropertiesTests {
  @Test
  public fun `FormattedValue string getter`() {
    val formattedValue = FormattedValue("test")
    assertEquals("test", formattedValue.stringOrNull)
  }

  @Test
  public fun `ImageValue imageId getter`() {
    val imageValue = ImageValue("test-id")
    assertEquals("test-id", imageValue.imageIdOrNull)
  }

  @Test
  public fun `ImageValue with styleImage imageId getter`() {
    val imageValue = ImageValue(
      StyleImage(imageId = "test-id", image = mockk())
    )
    assertEquals("test-id", imageValue.imageIdOrNull)
  }
  @Test
  public fun `ModelIdValue modelId getter`() {
    val modelIdValue = ModelIdValue("test-id")
    assertEquals("test-id", modelIdValue.modelIdOrNull)
  }

  @Test
  public fun `ModelIdValue with uri modelId getter`() {
    val modelIdValue = ModelIdValue(
      modelId = "test-id",
      uri = "testuri"
    )
    assertEquals("test-id", modelIdValue.modelIdOrNull)
    assertEquals("testuri", modelIdValue.uriOrNull)
  }
}