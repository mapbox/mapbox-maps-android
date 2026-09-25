@file:OptIn(MapboxExperimental::class)

package com.mapbox.maps.extension.compose.annotation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mapbox.maps.MapboxExperimental

/**
 * Internal state management for marker animations.
 */
@Stable
internal class MarkerAnimationState(
  internal val appearConfig: MarkerAnimationConfig?,
  internal val disappearConfig: MarkerAnimationConfig?
) {
  private val initialState = calculateInitialState(appearConfig)

  var scale by mutableStateOf(initialState.scale)
    private set

  var alpha by mutableStateOf(initialState.alpha)
    private set

  var rotationZ by mutableStateOf(initialState.rotationZ)
    private set

  /**
   * Calculates initial animation state from appear config.
   */
  private fun calculateInitialState(config: MarkerAnimationConfig?): InitialState {
    if (config == null) return InitialState()

    var scale = 1f
    var alpha = 1f
    var rotationZ = 0f

    config.effects.forEach { effect ->
      when (effect) {
        is MarkerAnimationEffect.Effect.Scale -> scale = effect.from
        is MarkerAnimationEffect.Effect.Fade -> alpha = effect.from
        is MarkerAnimationEffect.Effect.Wiggle -> rotationZ = 20f
      }
    }

    return InitialState(scale, alpha, rotationZ)
  }

  /**
   * Holds initial animation values.
   */
  private data class InitialState(
    val scale: Float = 1f,
    val alpha: Float = 1f,
    val rotationZ: Float = 0f
  )

  @Composable
  fun animatedScale(): State<Float> = animateFloatAsState(
    targetValue = scale,
    animationSpec = MarkerAnimationConfig.animationSpec,
    label = "markerScale"
  )

  @Composable
  fun animatedAlpha(): State<Float> = animateFloatAsState(
    targetValue = alpha,
    animationSpec = MarkerAnimationConfig.fadeAnimationSpec,
    label = "markerAlpha"
  )

  @Composable
  fun animatedRotation(): State<Float> = animateFloatAsState(
    targetValue = rotationZ,
    animationSpec = MarkerAnimationConfig.animationSpec,
    label = "markerRotation"
  )

  fun animateAppear(): Boolean {
    var hasWiggle = false
    appearConfig?.let { config ->
      hasWiggle = config.effects.any { it is MarkerAnimationEffect.Effect.Wiggle }
      animateToFinalValues(config.effects)
    }
    return hasWiggle
  }

  /**
   * Sets the initial "from" values for effects.
   */
  private fun setInitialValues(effects: List<MarkerAnimationEffect.Effect>) {
    effects.forEach { effect ->
      when (effect) {
        is MarkerAnimationEffect.Effect.Scale -> scale = effect.from
        is MarkerAnimationEffect.Effect.Fade -> alpha = effect.from
        is MarkerAnimationEffect.Effect.Wiggle -> {} // Handled separately
      }
    }
  }

  /**
   * Animates to the final "to" values for effects.
   */
  private fun animateToFinalValues(effects: List<MarkerAnimationEffect.Effect>) {
    effects.forEach { effect ->
      when (effect) {
        is MarkerAnimationEffect.Effect.Scale -> scale = effect.to
        is MarkerAnimationEffect.Effect.Fade -> alpha = effect.to
        is MarkerAnimationEffect.Effect.Wiggle -> {} // Handled separately
      }
    }
  }

  /**
   * Wiggle sequence: 20° → -20° → 8° → -8° → 0°
   */
  suspend fun animateWiggle() {
    val sequence = WiggleAnimationSequence.standard()
    // Set initial angle before starting sequence
    rotationZ = sequence.initialAngle
    sequence.animate { newAngle ->
      rotationZ = newAngle
    }
  }
}