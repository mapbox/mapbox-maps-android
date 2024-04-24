package com.mapbox.maps.testapp.style.layers

import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mapbox.maps.extension.style.layers.generated.slotLayer
import com.mapbox.maps.testapp.style.BaseStyleTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SlotLayerTest : BaseStyleTest() {

  @Test
  @UiThreadTest
  fun slotTest() {
    val slotLayerId = "id"
    val testValue = "customSlot"
    val layer = slotLayer(slotLayerId) {
      slot(testValue)
    }
    setupLayer(layer)
    Assert.assertEquals(testValue, layer.slot)
  }
}