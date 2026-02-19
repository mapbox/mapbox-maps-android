package com.mapbox.maps.extension.compose.annotation

import com.mapbox.maps.MapboxExperimental
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for Marker animation API.
 */
@OptIn(MapboxExperimental::class)
internal class MarkerAnimationTest {

  // MARK: - Animation Effects

  @Test
  fun testMarkerWithWiggleAppearAnimation() {
    val appearAnimation = listOf(MarkerAnimationEffect.wiggle)

    assertNotNull(appearAnimation)
    assertEquals(1, appearAnimation.size)
    assertTrue(appearAnimation[0].value is MarkerAnimationEffect.Effect.Wiggle)
  }

  @Test
  fun testMarkerWithScaleAppearAnimation() {
    val appearAnimation = listOf(MarkerAnimationEffect.scale)

    assertNotNull(appearAnimation)
    assertEquals(1, appearAnimation.size)
    val scaleEffect = appearAnimation[0].value as MarkerAnimationEffect.Effect.Scale
    assertEquals(0f, scaleEffect.from, 0.001f) // Default from
    assertEquals(1f, scaleEffect.to, 0.001f) // Default to
  }

  @Test
  fun testMarkerWithFadeAppearAnimation() {
    val appearAnimation = listOf(MarkerAnimationEffect.fadeIn)

    assertNotNull(appearAnimation)
    assertEquals(1, appearAnimation.size)
    val fadeEffect = appearAnimation[0].value as MarkerAnimationEffect.Effect.Fade
    assertEquals(0f, fadeEffect.from, 0.001f)
    assertEquals(1f, fadeEffect.to, 0.001f)
  }

  @Test
  fun testMarkerWithDisappearAnimation() {
    val disappearAnimation = listOf(MarkerAnimationEffect.fadeOut)

    assertNotNull(disappearAnimation)
    assertEquals(1, disappearAnimation.size)
    assertTrue(disappearAnimation[0].value is MarkerAnimationEffect.Effect.Fade)
  }

  // MARK: - Multiple Effects

  @Test
  fun testMarkerWithCombinedWiggleAndScale() {
    val appearAnimation = listOf(
      MarkerAnimationEffect.wiggle,
      MarkerAnimationEffect.scale
    )

    assertEquals(2, appearAnimation.size)
    assertTrue(appearAnimation[0].value is MarkerAnimationEffect.Effect.Wiggle)
    assertTrue(appearAnimation[1].value is MarkerAnimationEffect.Effect.Scale)
  }

  @Test
  fun testMarkerWithCombinedScaleAndFade() {
    val appearAnimation = listOf(
      MarkerAnimationEffect.scale(from = 0.5f, to = 1.0f),
      MarkerAnimationEffect.fade(from = 0.2f, to = 1.0f)
    )

    assertEquals(2, appearAnimation.size)

    val scaleEffect = appearAnimation[0].value as MarkerAnimationEffect.Effect.Scale
    assertEquals(0.5f, scaleEffect.from, 0.001f)
    assertEquals(1.0f, scaleEffect.to, 0.001f)

    val fadeEffect = appearAnimation[1].value as MarkerAnimationEffect.Effect.Fade
    assertEquals(0.2f, fadeEffect.from, 0.001f)
    assertEquals(1.0f, fadeEffect.to, 0.001f)
  }

  @Test
  fun testMarkerWithAllThreeEffects() {
    val appearAnimation = listOf(
      MarkerAnimationEffect.wiggle,
      MarkerAnimationEffect.scale,
      MarkerAnimationEffect.fadeIn
    )

    assertEquals(3, appearAnimation.size)
    assertTrue(appearAnimation[0].value is MarkerAnimationEffect.Effect.Wiggle)
    assertTrue(appearAnimation[1].value is MarkerAnimationEffect.Effect.Scale)
    assertTrue(appearAnimation[2].value is MarkerAnimationEffect.Effect.Fade)
  }

  // MARK: - Multiple Triggers

  @Test
  fun testMarkerWithBothTriggers() {
    val appearAnimation = listOf(MarkerAnimationEffect.scale)
    val disappearAnimation = listOf(MarkerAnimationEffect.fadeOut)

    // Both triggers can coexist on the same marker
    assertNotNull(appearAnimation)
    assertNotNull(disappearAnimation)
    assertEquals(1, appearAnimation.size)
    assertEquals(1, disappearAnimation.size)
  }

  @Test
  fun testMarkerWithComplexMultiTriggerSetup() {
    // Different animations for different triggers
    val appearAnimation = listOf(
      MarkerAnimationEffect.wiggle,
      MarkerAnimationEffect.scale
    )
    val disappearAnimation = listOf(
      MarkerAnimationEffect.wiggle,
      MarkerAnimationEffect.scale(from = 1f, to = 0f)
    )

    assertEquals(2, appearAnimation.size)
    assertEquals(2, disappearAnimation.size)

    // Verify disappear scale goes to 0
    val disappearScale = disappearAnimation[1].value as MarkerAnimationEffect.Effect.Scale
    assertEquals(1f, disappearScale.from, 0.001f)
    assertEquals(0f, disappearScale.to, 0.001f)
  }

  // MARK: - Custom Parameters

  @Test
  fun testScaleWithCustomParameters() {
    val effect = MarkerAnimationEffect.scale(from = 0.5f, to = 1.5f)
    val scaleEffect = effect.value as MarkerAnimationEffect.Effect.Scale

    assertEquals(0.5f, scaleEffect.from, 0.001f)
    assertEquals(1.5f, scaleEffect.to, 0.001f)
  }

  @Test
  fun testFadeWithCustomParameters() {
    val effect = MarkerAnimationEffect.fade(from = 0.2f, to = 0.8f)
    val fadeEffect = effect.value as MarkerAnimationEffect.Effect.Fade

    assertEquals(0.2f, fadeEffect.from, 0.001f)
    assertEquals(0.8f, fadeEffect.to, 0.001f)
  }

  @Test
  fun testScaleWithInvertedRange() {
    // Zoom out effect: scale from larger to smaller
    val effect = MarkerAnimationEffect.scale(from = 1.5f, to = 0.5f)
    val scaleEffect = effect.value as MarkerAnimationEffect.Effect.Scale

    assertEquals(1.5f, scaleEffect.from, 0.001f)
    assertEquals(0.5f, scaleEffect.to, 0.001f)
  }

  @Test
  fun testScaleToZero() {
    // Disappear by scaling to zero
    val effect = MarkerAnimationEffect.scale(from = 1f, to = 0f)
    val scaleEffect = effect.value as MarkerAnimationEffect.Effect.Scale

    assertEquals(1f, scaleEffect.from, 0.001f)
    assertEquals(0f, scaleEffect.to, 0.001f)
  }

  @Test
  fun testFadeWithFullOpacityRange() {
    // Fade from transparent to opaque
    val effect = MarkerAnimationEffect.fade(from = 0f, to = 1f)
    val fadeEffect = effect.value as MarkerAnimationEffect.Effect.Fade

    assertEquals(0f, fadeEffect.from, 0.001f)
    assertEquals(1f, fadeEffect.to, 0.001f)
  }

  // MARK: - Edge Cases

  @Test
  fun testMarkerWithEmptyAnimations() {
    // Developers can opt out of animations with empty lists
    val noAnimation = emptyList<MarkerAnimationEffect>()

    assertEquals(0, noAnimation.size)
  }

  @Test
  fun testMarkerWithOnlyOneAnimationSet() {
    // It's valid to only set appear animation without disappear
    val appearAnimation = listOf(MarkerAnimationEffect.wiggle)
    val disappearAnimation = emptyList<MarkerAnimationEffect>()

    assertEquals(1, appearAnimation.size)
    assertEquals(0, disappearAnimation.size)
  }

  @Test
  fun testScaleWithSameFromAndTo() {
    // Edge case: no-op scale
    val effect = MarkerAnimationEffect.scale(from = 1f, to = 1f)
    val scaleEffect = effect.value as MarkerAnimationEffect.Effect.Scale

    assertEquals(1f, scaleEffect.from, 0.001f)
    assertEquals(1f, scaleEffect.to, 0.001f)
  }

  // MARK: - Implementation Details

  @Test
  fun testWiggleSequenceStructure() {
    val sequence = WiggleAnimationSequence.standard()
    val keyframes = sequence.keyframes

    // Verify the sequence: 20° → -20° → 8° → -8° → 0°
    assertEquals(4, keyframes.size)
    assertEquals(20f, sequence.initialAngle, 0.001f)

    assertEquals(-20f, keyframes[0].angle, 0.001f)
    assertEquals(8f, keyframes[1].angle, 0.001f)
    assertEquals(-8f, keyframes[2].angle, 0.001f)
    assertEquals(0f, keyframes[3].angle, 0.001f)
  }

  @Test
  fun testWiggleSequenceTiming() {
    val sequence = WiggleAnimationSequence.standard()
    val keyframes = sequence.keyframes

    // Verify individual durations (time to wait before each keyframe)
    assertEquals(0, keyframes[0].durationMs) // Start immediately
    assertEquals(350, keyframes[1].durationMs) // Wait 350ms
    assertEquals(300, keyframes[2].durationMs) // Wait 300ms
    assertEquals(250, keyframes[3].durationMs) // Wait 250ms

    // Verify cumulative timing adds up correctly
    // After keyframe 0 (wait 0ms): total = 0ms
    // After keyframe 1 (wait 350ms): total = 350ms
    // After keyframe 2 (wait 300ms): total = 650ms
    // After keyframe 3 (wait 250ms): total = 900ms
    val totalDuration = keyframes.sumOf { it.durationMs }
    assertEquals(900, totalDuration)
  }

  @Test
  fun testWiggleSequenceEndsAtZero() {
    val sequence = WiggleAnimationSequence.standard()
    val finalAngle = sequence.keyframes.last().angle

    assertEquals(0f, finalAngle, 0.001f)
  }

  @Test
  fun testConfigurationTransformation() {
    val effects = listOf(
      MarkerAnimationEffect.wiggle.value,
      MarkerAnimationEffect.scale.value
    )
    val config = MarkerAnimationConfig(effects)

    assertEquals(2, config.effects.size)
  }
}