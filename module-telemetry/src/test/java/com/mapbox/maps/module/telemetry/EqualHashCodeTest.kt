package com.mapbox.maps.module.telemetry

import com.google.gson.JsonObject
import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class EqualHashCodeTest {
  @Test
  fun `MapLoadEvent hashCode and equals test`() {
    val clazz = MapLoadEvent::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }

  @Test
  fun `PerformanceEvent hashCode and equals test`() {
    val clazz = PerformanceEvent::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .withRedefinedSuperclass()
      .withPrefabValues(
        JsonObject::class.java,
        JsonObject(),
        JsonObject().apply {
          addProperty("test", false)
        }
      ).verify()
  }

  @Test
  fun `PerformanceAttribute hashCode and equals test`() {
    val clazz = PerformanceEvent.PerformanceAttribute::class.java
    EqualsVerifier.forClass(clazz)
      .usingGetClass()
      .verify()
  }
}