@file:OptIn(MapboxExperimental::class)

package com.mapbox.maps.extension.compose.annotation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import com.mapbox.maps.MapboxExperimental
import kotlinx.coroutines.delay

/**
 * Defines an animation effect that can be applied to a [Marker].
 *
 * Use the companion object factory methods to create predefined effects:
 * - [wiggle]: Pendulum rotation effect (20° → -20° → 8° → -8° → 0°)
 * - [scale]: Scale animation (default: from 0 to 1)
 * - [fade]: Fade animation (default: from 0 to 1)
 *
 * Effects can be customized with parameters:
 * - [scale(from, to)]: Custom scale range
 * - [fade(from, to)]: Custom fade range
 *
 * Multiple effects can be combined by passing them as a list to [Marker] animation parameters.
 *
 * Example:
 * ```
 * Marker(
 *   point = point,
 *   appearAnimation = listOf(MarkerAnimationEffect.wiggle, MarkerAnimationEffect.scale),
 *   disappearAnimation = listOf(MarkerAnimationEffect.fade)
 * )
 * ```
 */
@MapboxExperimental
public sealed class MarkerAnimationEffect {
  internal abstract val value: Effect

  internal sealed class Effect {
    internal object Wiggle : Effect()
    internal data class Scale(val from: Float, val to: Float) : Effect()
    internal data class Fade(val from: Float, val to: Float) : Effect()
  }

  private class WiggleEffect : MarkerAnimationEffect() {
    override val value: Effect = Effect.Wiggle
  }

  private class ScaleEffect(from: Float, to: Float) : MarkerAnimationEffect() {
    override val value: Effect = Effect.Scale(from, to)
  }

  private class FadeEffect(from: Float, to: Float) : MarkerAnimationEffect() {
    override val value: Effect = Effect.Fade(from, to)
  }

  /**
   * Factory methods for creating marker animation effects.
   */
  public companion object {
    /**
     * Pendulum rotation (20° → -20° → 8° → -8° → 0°).
     */
    public val wiggle: MarkerAnimationEffect = WiggleEffect()

    /**
     * Scale animation (default: 0 to 1).
     */
    public val scale: MarkerAnimationEffect = ScaleEffect(0f, 1f)

    /**
     * Fade in animation (0 to 1 opacity).
     */
    public val fadeIn: MarkerAnimationEffect = FadeEffect(0f, 1f)

    /**
     * Fade out animation (1 to 0 opacity).
     */
    public val fadeOut: MarkerAnimationEffect = FadeEffect(1f, 0f)

    /**
     * Scale animation with custom range.
     */
    public fun scale(from: Float, to: Float): MarkerAnimationEffect = ScaleEffect(from, to)

    /**
     * Fade animation with custom range.
     */
    public fun fade(from: Float, to: Float): MarkerAnimationEffect = FadeEffect(from, to)
  }
}

/**
 * Defines when a marker animation should be triggered.
 */
@MapboxExperimental
public sealed class MarkerAnimationTrigger {
  /**
   * Trigger for when the marker first appears on the map.
   */
  public object Appear : MarkerAnimationTrigger()

  /**
   * Trigger for when the marker is removed from the map.
   */
  public object Disappear : MarkerAnimationTrigger()
}

internal data class MarkerAnimationConfig(
  val effects: List<MarkerAnimationEffect.Effect>
) {
  internal companion object {
    // Bouncy spring feels natural for scale and rotation transformations
    val animationSpec = spring<Float>(
      dampingRatio = 0.6f,
      stiffness = Spring.StiffnessLow
    )

    // Smooth easing for opacity changes
    val fadeAnimationSpec = tween<Float>(
      durationMillis = 800,
      easing = FastOutSlowInEasing
    )

    const val DURATION_MS = 1500
  }
}

/**
 * Wiggle animation sequence with structured keyframes.
 */
internal class WiggleAnimationSequence private constructor(
  val keyframes: List<KeyframeStep>,
  val initialAngle: Float
) {
  companion object {
    /**
     * Standard wiggle sequence: 20° → -20° → 8° → -8° → 0°
     * Duration indicates how long to wait before executing this keyframe.
     */
    fun standard(): WiggleAnimationSequence = WiggleAnimationSequence(
      keyframes = listOf(
        KeyframeStep(angle = -20f, durationMs = 0), // Start immediately
        KeyframeStep(angle = 8f, durationMs = 350), // After 350ms
        KeyframeStep(angle = -8f, durationMs = 300), // After 300ms
        KeyframeStep(angle = 0f, durationMs = 250) // After 250ms
      ),
      initialAngle = 20f
    )
  }

  internal data class KeyframeStep(
    val angle: Float,
    val durationMs: Int // Time to wait before this keyframe
  )

  suspend fun animate(onUpdate: (Float) -> Unit) {
    for (step in keyframes) {
      if (step.durationMs > 0) {
        delay(step.durationMs.toLong())
      }
      onUpdate(step.angle)
    }
  }
}