package com.mapbox.maps.renderer

/**
 * Class describing internal render event.
 *
 * This abstraction is needed to run any render event from [MapboxRenderer]
 * to schedule it on [MapboxRenderThread]'s message queue.
 */
internal data class RenderEvent(
  /**
   * Optional callback to be run on render thread's looper.
   */
  val runnable: Runnable?,
  /**
   * Whether that event requires actual render.
   * If set to `true` - render call will be scheduled, [runnable] will be put in a separate queue which is drained after native draw calls but before swapping buffers.
   * If set to `false` - no render call will be scheduled, [runnable] will be simply put in a render thread message queue and executed asap if render thread is in correct state.
   */
  val needRender: Boolean,
  /**
   * Render event type, refer to [EventType] docs.
   */
  val eventType: EventType
)