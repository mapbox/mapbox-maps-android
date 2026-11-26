package com.mapbox.maps.extension.compose.style.interactions

import com.mapbox.maps.ClickInteraction
import com.mapbox.maps.InteractionContext
import com.mapbox.maps.MapInteraction
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.style.interactions.generated.FeaturesetFeatureScope
import com.mapbox.maps.interactions.FeatureState
import com.mapbox.maps.interactions.FeaturesetFeature
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(MapboxExperimental::class)
@RunWith(RobolectricTestRunner::class)
internal class BasicStyleInteractionsTest {

  private class TestInteractions : BasicStyleInteractions() {
    val exposedEntries: List<(String?) -> MapInteraction> get() = entries

    fun setScope(scope: FeaturesetFeatureScope?) {
      featuresetFeatureScope = scope
    }

    fun addMapClick(onClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean) =
      clickInteractionMap(onClick)

    fun addMapLongClick(onLongClick: FeaturesetFeatureScope.(InteractionContext) -> Boolean) =
      longClickInteractionMap(onLongClick)

    fun addLayerClick(
      layerId: String,
      onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ) = clickInteractionLayer(layerId, null, null, onClick)

    fun addFeaturesetClick(
      featuresetId: String,
      importId: String?,
      onClick: FeaturesetFeatureScope.(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean
    ) = clickInteractionFeatureset(featuresetId, importId, null, null, onClick)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `each interaction registration adds exactly one entry`() {
    val state = TestInteractions()
    assertEquals(0, state.exposedEntries.size)

    state.addMapClick { true }
    state.addLayerClick("layer") { _, _ -> true }
    state.addFeaturesetClick("poi", importId = null) { _, _ -> true }
    state.addMapLongClick { true }

    assertEquals(4, state.exposedEntries.size)
  }

  @Test
  fun `featureset entry falls back to the bind-time import id when none is provided`() {
    mockkObject(ClickInteraction.Companion)
    val importSlot = slot<String>()
    every {
      ClickInteraction.featureset(any(), capture(importSlot), any(), any(), any())
    } returns mockk(relaxed = true)

    val state = TestInteractions()
    state.addFeaturesetClick("poi", importId = null) { _, _ -> true }
    state.exposedEntries[0].invoke("bind-time-import")

    assertEquals("bind-time-import", importSlot.captured)
  }

  @Test
  fun `featureset entry keeps an explicit import id over the bind-time one`() {
    mockkObject(ClickInteraction.Companion)
    val importSlot = slot<String>()
    every {
      ClickInteraction.featureset(any(), capture(importSlot), any(), any(), any())
    } returns mockk(relaxed = true)

    val state = TestInteractions()
    state.addFeaturesetClick("poi", importId = "explicit") { _, _ -> true }
    state.exposedEntries[0].invoke("bind-time-import")

    assertEquals("explicit", importSlot.captured)
  }

  @Test
  fun `featureset callback is suppressed until a scope is bound`() {
    mockkObject(ClickInteraction.Companion)
    val onClickSlot = slot<(FeaturesetFeature<FeatureState>, InteractionContext) -> Boolean>()
    every {
      ClickInteraction.featureset(any(), any(), any(), any(), capture(onClickSlot))
    } returns mockk(relaxed = true)

    var userCallbackInvoked = false
    val state = TestInteractions()
    state.addFeaturesetClick("poi", importId = null) { _, _ ->
      userCallbackInvoked = true
      true
    }
    state.exposedEntries[0].invoke(null)
    val builtCallback = onClickSlot.captured
    val feature = mockk<FeaturesetFeature<FeatureState>>(relaxed = true)
    val context = mockk<InteractionContext>(relaxed = true)

    // No scope yet (not bound to a map): the user callback must not run, result is false.
    assertFalse(builtCallback.invoke(feature, context))
    assertFalse(userCallbackInvoked)

    // Once a scope is present the user callback runs with it as the receiver.
    state.setScope(mockk(relaxed = true))
    assertTrue(builtCallback.invoke(feature, context))
    assertTrue(userCallbackInvoked)
  }
}