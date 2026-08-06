package com.mapbox.maps.testapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AgentTelemetryForwardingTest {

  @Test
  fun `does not forward when agent id is empty`() {
    var forwardedAgentId: String? = null
    forwardAgentIdIfPresent("") { forwardedAgentId = it }
    assertNull(
      "Setter should not be invoked when BuildConfig.MAPBOX_AGENT is empty (the default when " +
        "`-Pmapbox.agent` is not supplied)",
      forwardedAgentId
    )
  }

  @Test
  fun `forwards agent id when non-empty`() {
    var forwardedAgentId: String? = null
    forwardAgentIdIfPresent("claude-code") { forwardedAgentId = it }
    assertEquals("claude-code", forwardedAgentId)
  }

  @Test
  fun `forwards agent id exactly once`() {
    var invocationCount = 0
    forwardAgentIdIfPresent("codex") { invocationCount++ }
    assertEquals(1, invocationCount)
  }
}
