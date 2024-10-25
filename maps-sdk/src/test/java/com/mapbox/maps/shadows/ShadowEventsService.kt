package com.mapbox.maps.shadows

import com.mapbox.common.EventsServerOptions
import com.mapbox.common.EventsService
import com.mapbox.common.FlushOperationResultCallback
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements

@Implements(EventsService::class)
class ShadowEventsService {
  @Implementation
  fun flush(callback: FlushOperationResultCallback?) {
    TODO("mocked implementation")
  }

  companion object {
    @Implementation
    fun getOrCreate(options: EventsServerOptions): EventsService {
      TODO("mocked implementation")
    }
  }
}