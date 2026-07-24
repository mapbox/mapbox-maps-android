package com.mapbox.maps.testapp

/**
 * Forwards [agentId] via [setAgentId] when it is non-empty.
 *
 * [agentId] is expected to come from [BuildConfig.MAPBOX_AGENT], which is populated at build
 * time from the optional `-Pmapbox.agent=<id>` Gradle property (see `app/build.gradle.kts`).
 * When that property is not supplied on the command line, [BuildConfig.MAPBOX_AGENT] defaults to
 * an empty string and this function is a no-op, so normal (non-test) builds are unaffected.
 *
 * The forwarding logic itself is kept free of any Mapbox Common SDK dependency so that it can be
 * unit tested in isolation; callers supply [setAgentId] to perform the actual forwarding, e.g.:
 *
 * ```
 * forwardAgentIdIfPresent(BuildConfig.MAPBOX_AGENT) { agentId ->
 *   MapboxAgentContextFactory.getInstance().setMapboxAgentId(agentId)
 * }
 * ```
 */
internal fun forwardAgentIdIfPresent(agentId: String, setAgentId: (String) -> Unit) {
  if (agentId.isNotEmpty()) {
    setAgentId(agentId)
  }
}
