package com.mapbox.maps.plugin.viewport.transition

import com.mapbox.common.Cancelable
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.delegates.MapDelegateProvider
import com.mapbox.maps.plugin.viewport.CompletionListener
import com.mapbox.maps.plugin.viewport.ViewportPlugin
import com.mapbox.maps.plugin.viewport.ViewportPluginImpl.Companion.VIEWPORT_CAMERA_OWNER
import com.mapbox.maps.plugin.viewport.state.ViewportState

/**
 * The [ViewportTransition] that transition to target [ViewportState] immediately without any animation.
 *
 * Use [ViewportPlugin.makeImmediateViewportTransition] to create instances of ImmediateViewportTransition.
 */
internal class ImmediateViewportTransition(delegateProvider: MapDelegateProvider) :
  ViewportTransition {
  private val cameraPlugin = delegateProvider.mapPluginProviderDelegate.camera
  private var cachedAnchor: ScreenCoordinate? = null

  /**
   * Run the [ViewportTransition] from previous [ViewportState] to the target [ViewportState].
   *
   * The completion block contains a Bool that is true if the transition is cancelled and false
   * if it ran to completion. Implementations must be sure to invoke the completion block with false if
   * the returned Cancelable is invoked prior to completion. the completion block must be invoked
   * on the main queue. Transitions must handle the possibility that the "to" state might fail to
   * provide a target camera in a timely manner or might update the target camera multiple times
   * during the transition (a "moving target").
   *
   * @param to The target [ViewportState]
   * @param completionListener The listener to observe the completion state.
   * @return a handle that can be used to cancel the current [ViewportTransition]
   */
  override fun run(
    to: ViewportState,
    completionListener: CompletionListener
  ): Cancelable {
    return to.observeDataSource { cameraOptions ->
      // cache the camera plugin's last anchor point, and reset it once transition is ended
      cachedAnchor = cameraPlugin.anchor
      // For the viewport transition, the anchor should be set to null to avoid unexpected center shift
      // caused by the zoom/bearing animators.
      cameraPlugin.anchor = null
      cameraPlugin.easeTo(
        cameraOptions,
        mapAnimationOptions {
          startDelay(0)
          duration(0)
          owner(VIEWPORT_CAMERA_OWNER)
        }
      )
      cameraPlugin.anchor = cachedAnchor
      completionListener.onComplete(true)
      false
    }
  }
}