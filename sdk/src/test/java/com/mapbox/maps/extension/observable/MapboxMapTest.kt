package com.mapbox.maps.extension.observable

import com.mapbox.bindgen.Value
import com.mapbox.common.ShadowValueConverter
import com.mapbox.maps.Event
import com.mapbox.maps.ObservableInterface
import com.mapbox.maps.Observer
import com.mapbox.maps.extension.observable.resourcerequest.*
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowValueConverter::class])
class MapboxMapTest {

  private val observableInterface: ObservableInterface = mockk(relaxed = true)
  private val observer: Observer = mockk(relaxed = true)

  @Test
  fun addResourceObserver() {
    observableInterface.subscribeResourceRequest(observer)
    verify { observableInterface.subscribe(observer, listOf("resource-request")) }
  }

  @Test
  fun removeResourceObserver() {
    observableInterface.unsubscribeResourceRequest(observer)
    verify { observableInterface.unsubscribe(observer, listOf("resource-request")) }
  }

  @Test
  fun getEventData() {
    val event = Event("resource-request", Value.valueOf("test"))
    val request = Request(listOf("network"), "https://api.mapbox.com", "tile", "regular")
    val response = Response(
      "d8abd8d10bee6b45b4dbf5c05496587a",
      false,
      false,
      "Mon, 05 Oct 2020 14:23:52 GMT",
      false,
      false,
      "Thu, 15 Oct 2020 14:32:23 GMT",
      181576,
      Error("not-found", "error message")
    )

    val eventData = event.getResourceEventData()
    assertEquals("network", eventData.dataSource)
    assertEquals(false, eventData.cancelled)
    assertEquals(request, eventData.request)
    assertEquals(response, eventData.response)

    assertEquals(listOf("network"), request.loadingMethod)
    assertEquals("https://api.mapbox.com", request.url)
    assertEquals("tile", request.kind)
    assertEquals("regular", request.priority)

    assertEquals("d8abd8d10bee6b45b4dbf5c05496587a", response.eTag)
    assertEquals(false, response.mustRevalidate)
    assertEquals(false, response.noContent)
    assertEquals(false, response.offlineData)
    assertEquals(false, response.notModified)
    assertEquals("Thu, 15 Oct 2020 14:32:23 GMT", response.expires)
    assertEquals(181576, response.size)
    assertEquals(Error("not-found", "error message"), response.error)

    assertEquals("not-found", response.error!!.reason)
    assertEquals("error message", response.error!!.message)
  }
}