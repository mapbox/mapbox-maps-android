package com.mapbox.maps.extension.observable

import com.mapbox.bindgen.Value
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

@RunWith(RobolectricTestRunner::class)
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
    val request = Request(listOf("network"), "https://api.mapbox.com", "tile", "regular")
    val response = Response(
      eTag = "d8abd8d10bee6b45b4dbf5c05496587a",
      mustRevalidate = false,
      noContent = false,
      modified = "Mon, 05 Oct 2020 14:23:52 GMT",
      source = "network",
      notModified = false,
      expires = "Thu, 15 Oct 2020 14:32:23 GMT",
      size = 181576,
      error = Error("not-found", "error message")
    )
    val requestMap = hashMapOf(
      Pair("loading-method", Value(listOf(Value("network")))),
      Pair("url", Value("https://api.mapbox.com")),
      Pair("kind", Value("tile")),
      Pair("priority", Value("regular"))
    )
    val responseMap = hashMapOf(
      Pair("etag", Value("d8abd8d10bee6b45b4dbf5c05496587a")),
      Pair("must-revalidate", Value(false)),
      Pair("no-content", Value(false)),
      Pair("modified", Value("Mon, 05 Oct 2020 14:23:52 GMT")),
      Pair("source", Value("network")),
      Pair("not-modified", Value(false)),
      Pair("expires", Value("Thu, 15 Oct 2020 14:32:23 GMT")),
      Pair("size", Value(181576)),
      Pair(
        "error",
        Value(
          hashMapOf(
            Pair("reason", Value("not-found")),
            Pair("message", Value("error message"))
          )
        )
      )
    )
    val map = hashMapOf(
      Pair("data-source", Value("network")),
      Pair("request", Value(requestMap)),
      Pair("response", Value(responseMap))
    )
    val event = Event("resource-request", Value.valueOf(map))

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
    assertEquals("network", response.source)
    assertEquals(false, response.notModified)
    assertEquals("Thu, 15 Oct 2020 14:32:23 GMT", response.expires)
    assertEquals(181576, response.size)
    assertEquals(Error("not-found", "error message"), response.error)

    assertEquals("not-found", response.error!!.reason)
    assertEquals("error message", response.error!!.message)
  }
}