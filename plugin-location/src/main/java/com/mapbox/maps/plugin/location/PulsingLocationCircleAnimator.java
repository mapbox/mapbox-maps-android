package com.mapbox.maps.plugin.location;

/**
 * Manages the logic of the interpolated animation which is applied to the LocationComponent's pulsing circle
 *
 * @deprecated Location Plugin is deprecated, use Location Component Plugin instead.
 */
@Deprecated
public class PulsingLocationCircleAnimator extends MapboxFloatAnimator {

  /**
   *
   * @param updateListener  the {@link AnimationsValueChangeListener} associated with this animator.
   * @param maxAnimationFps the maximum frames per second that the animator should use. Default
   *                        is the {@link LocationLayerAnimatorCoordinator#maxAnimationFps} variable.
   */
  public PulsingLocationCircleAnimator(AnimationsValueChangeListener updateListener,
                                       int maxAnimationFps,
                                       float circleMaxRadius) {
    super(new Float[]{0f, circleMaxRadius}, updateListener, maxAnimationFps);
  }
}
