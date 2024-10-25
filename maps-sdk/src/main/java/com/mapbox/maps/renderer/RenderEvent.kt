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
   * If set to `true` - render call will be scheduled, [runnable] will be put in a separate queue
   * which is drained after native draw calls but before swapping buffers.
   * If set to `false` - no render call will be scheduled, [runnable] will be either be put in a
   * render thread message queue and executed asap if no pending VSYNC event
   * or executed right after swapping buffers if VSYNC event is pending. Runnables are executed
   * only if render thread is in fully prepared state.
   */
  val needRender: Boolean,
)