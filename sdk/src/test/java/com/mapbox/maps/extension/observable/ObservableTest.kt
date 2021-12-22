package com.mapbox.maps.extension.observable

import com.mapbox.bindgen.Value
import com.mapbox.maps.Event
import com.mapbox.maps.MapEvents
import com.mapbox.maps.ObservableInterface
import com.mapbox.maps.Observer
import com.mapbox.maps.extension.observable.model.*
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ObservableTest {

  private val observableInterface: ObservableInterface = mockk(relaxed = true)
  private val observer: Observer = mockk(relaxed = true)
  private val request = Request(
    listOf("network"),
    "https://api.mapbox.com",
    RequestType.TILE,
    RequestPriority.REGULAR
  )
  private val response = Response(
    eTag = "d8abd8d10bee6b45b4dbf5c05496587a",
    mustRevalidate = false,
    noContent = false,
    modified = "Mon, 05 Oct 2020 14:23:52 GMT",
    source = ResponseSourceType.NETWORK,
    notModified = false,
    expires = "Thu, 15 Oct 2020 14:32:23 GMT",
    size = 181576,
    error = Error(ResponseErrorReason.NOT_FOUND, "error message")
  )
  private val requestMap = hashMapOf(
    "loading-method" to Value(listOf(Value("network"))),
    "url" to Value("https://api.mapbox.com"),
    "kind" to Value("tile"),
    "priority" to Value("regular")
  )
  private val responseMap = hashMapOf(
    "etag" to Value("d8abd8d10bee6b45b4dbf5c05496587a"),
    "must-revalidate" to Value(false),
    "no-content" to Value(false),
    "modified" to Value("Mon, 05 Oct 2020 14:23:52 GMT"),
    "source" to Value("network"),
    "not-modified" to Value(false),
    "expires" to Value("Thu, 15 Oct 2020 14:32:23 GMT"),
    "size" to Value(181576),
    "error" to
      Value(
        hashMapOf(
          "reason" to Value("not-found"),
          "message" to Value("error message")
        )
      )
  )

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
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "data-source" to Value("network"),
      "request" to Value(requestMap),
      "response" to Value(responseMap),
      "cancelled" to Value(false)
    )
    val event = Event(MapEvents.RESOURCE_REQUEST, Value.valueOf(map))

    val eventData = event.getResourceEventData()
    assertEquals(1L, eventData.begin)
    assertEquals(DataSourceType.NETWORK, eventData.dataSource)
    assertEquals(false, eventData.cancelled)
    assertEquals(request, eventData.request)
    assertEquals(response, eventData.response)

    assertEquals(listOf("network"), eventData.request.loadingMethod)
    assertEquals("https://api.mapbox.com", eventData.request.url)
    assertEquals(RequestType.TILE, eventData.request.kind)
    assertEquals(RequestPriority.REGULAR, eventData.request.priority)

    assertNotNull(eventData.response)
    assertEquals("d8abd8d10bee6b45b4dbf5c05496587a", eventData.response!!.eTag)
    assertEquals(false, eventData.response!!.mustRevalidate)
    assertEquals(false, eventData.response!!.noContent)
    assertEquals(ResponseSourceType.NETWORK, eventData.response!!.source)
    assertEquals(false, eventData.response!!.notModified)
    assertEquals("Thu, 15 Oct 2020 14:32:23 GMT", eventData.response!!.expires)
    assertEquals("Mon, 05 Oct 2020 14:23:52 GMT", eventData.response!!.modified)
    assertEquals(181576, eventData.response!!.size)
    assertNotNull(eventData.response!!.error)
    assertEquals(ResponseErrorReason.NOT_FOUND, eventData.response!!.error!!.reason)
    assertEquals("error message", eventData.response!!.error!!.message)
  }

  @Test
  fun getEventDataWithNullPropertiesInResponse() {
    val responseMap = hashMapOf(
      "must-revalidate" to Value(false),
      "no-content" to Value(false),
      "source" to Value("network"),
      "not-modified" to Value(false),
      "expires" to Value("Thu, 15 Oct 2020 14:32:23 GMT"),
      "size" to Value(181576),
    )
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "data-source" to Value("network"),
      "request" to Value(requestMap),
      "response" to Value(responseMap),
      "cancelled" to Value(false)
    )
    val event = Event(MapEvents.RESOURCE_REQUEST, Value.valueOf(map))
    val eventData = event.getResourceEventData()

    assertNotNull(eventData.response)
    assertNull(eventData.response!!.eTag)
    assertEquals(false, eventData.response!!.mustRevalidate)
    assertEquals(false, eventData.response!!.noContent)
    assertEquals(ResponseSourceType.NETWORK, eventData.response!!.source)
    assertEquals(false, eventData.response!!.notModified)
    assertEquals("Thu, 15 Oct 2020 14:32:23 GMT", eventData.response!!.expires)
    assertNull(eventData.response!!.modified)
    assertEquals(181576, eventData.response!!.size)
    assertNull(eventData.response!!.error)
  }

  @Test
  fun getEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "data-source" to Value("network"),
      "request" to Value(requestMap),
      "response" to Value(responseMap),
      "cancelled" to Value(false)
    )
    val event = Event(MapEvents.RESOURCE_REQUEST, Value.valueOf(map))

    val eventData = event.getResourceEventData()
    assertNull(eventData.end)
  }

  @Test
  fun getEventDataWithNullResponse() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "data-source" to Value("network"),
      "request" to Value(requestMap),
      "cancelled" to Value(false)
    )
    val event = Event(MapEvents.RESOURCE_REQUEST, Value.valueOf(map))
    val eventData = event.getResourceEventData()
    assertNull(eventData.response)
  }

  @Test
  fun getMapLoadEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L)
    )
    val event = Event(MapEvents.MAP_LOADED, Value.valueOf(map))
    val eventData = event.getMapLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
  }

  @Test
  fun getMapLoadEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L)
    )
    val event = Event(MapEvents.MAP_LOADED, Value.valueOf(map))
    val eventData = event.getMapLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
  }

  @Test
  fun getMapLoadingErrorEventData() {
    val tileIDMap = hashMapOf(
      "z" to Value(1),
      "x" to Value(2),
      "y" to Value(3)
    )
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "type" to Value("sprite"),
      "message" to Value("error message"),
      "source-id" to Value("source"),
      "tile-id" to Value(tileIDMap)
    )
    val event = Event(MapEvents.MAP_LOADING_ERROR, Value.valueOf(map))
    val eventData = event.getMapLoadingErrorEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals(MapLoadErrorType.SPRITE, eventData.type)
    assertEquals("source", eventData.sourceId)
    assertEquals("error message", eventData.message)
    assertEquals(1L, eventData.tileId?.zoom)
    assertEquals(2L, eventData.tileId?.x)
    assertEquals(3L, eventData.tileId?.y)
  }

  @Test
  fun getMapLoadingErrorEventDataWithNullProperties() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "type" to Value("sprite"),
      "message" to Value("error message"),
    )
    val event = Event(MapEvents.MAP_LOADING_ERROR, Value.valueOf(map))
    val eventData = event.getMapLoadingErrorEventData()
    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertNull(eventData.sourceId)
    assertEquals("error message", eventData.message)
    assertNull(eventData.tileId)
  }

  @Test
  fun getMapIdleEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L)
    )
    val event = Event(MapEvents.MAP_IDLE, Value.valueOf(map))
    val eventData = event.getMapIdleEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
  }

  @Test
  fun getMapIdleEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
    )
    val event = Event(MapEvents.MAP_IDLE, Value.valueOf(map))
    val eventData = event.getMapIdleEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
  }

  @Test
  fun getStyleDataLoadedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "type" to Value("sources")
    )
    val event = Event(MapEvents.STYLE_DATA_LOADED, Value.valueOf(map))
    val eventData = event.getStyleDataLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals(StyleDataType.SOURCES, eventData.type)
  }

  @Test
  fun getStyleDataLoadedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "type" to Value("sources")
    )
    val event = Event(MapEvents.STYLE_DATA_LOADED, Value.valueOf(map))
    val eventData = event.getStyleDataLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals(StyleDataType.SOURCES, eventData.type)
  }

  @Test
  fun getStyleLoadedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L)
    )
    val event = Event(MapEvents.STYLE_LOADED, Value.valueOf(map))
    val eventData = event.getStyleLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
  }

  @Test
  fun getStyleLoadedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
    )
    val event = Event(MapEvents.STYLE_LOADED, Value.valueOf(map))
    val eventData = event.getStyleLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
  }

  @Test
  fun getSourceDataLoadedEventData() {
    val tileIDMap = hashMapOf(
      "z" to Value(1),
      "x" to Value(2),
      "y" to Value(3)
    )
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "id" to Value("id"),
      "loaded" to Value(true),
      "type" to Value("metadata"),
      "tile-id" to Value(tileIDMap)
    )
    val event = Event(MapEvents.SOURCE_DATA_LOADED, Value.valueOf(map))
    val eventData = event.getSourceDataLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals("id", eventData.id)
    assertEquals(true, eventData.loaded)
    assertEquals(SourceDataType.METADATA, eventData.type)
    assertEquals(1L, eventData.tileID?.zoom)
    assertEquals(2L, eventData.tileID?.x)
    assertEquals(3L, eventData.tileID?.y)
  }

  @Test
  fun getSourceDataLoadedEventDataWithNullProperties() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "id" to Value("id"),
      "type" to Value("metadata"),
    )
    val event = Event(MapEvents.SOURCE_DATA_LOADED, Value.valueOf(map))
    val eventData = event.getSourceDataLoadedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals("id", eventData.id)
    assertNull(eventData.loaded)
    assertEquals(SourceDataType.METADATA, eventData.type)
    assertNull(eventData.tileID)
  }

  @Test
  fun getStyleImageMissingEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.STYLE_IMAGE_MISSING, Value.valueOf(map))
    val eventData = event.getStyleImageMissingEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getStyleImageMissingEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.STYLE_IMAGE_MISSING, Value.valueOf(map))
    val eventData = event.getStyleImageMissingEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getStyleImageUnusedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.STYLE_IMAGE_REMOVE_UNUSED, Value.valueOf(map))
    val eventData = event.getStyleImageUnusedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getStyleImageUnusedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.STYLE_IMAGE_REMOVE_UNUSED, Value.valueOf(map))
    val eventData = event.getStyleImageUnusedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getSourceAddedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.SOURCE_ADDED, Value.valueOf(map))
    val eventData = event.getSourceAddedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getSourceAddedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.SOURCE_ADDED, Value.valueOf(map))
    val eventData = event.getSourceAddedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getSourceRemovedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.SOURCE_REMOVED, Value.valueOf(map))
    val eventData = event.getSourceRemovedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getSourceRemovedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "id" to Value("id"),
    )
    val event = Event(MapEvents.SOURCE_REMOVED, Value.valueOf(map))
    val eventData = event.getSourceRemovedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals("id", eventData.id)
  }

  @Test
  fun getRenderFrameStartedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
    )
    val event = Event(MapEvents.RENDER_FRAME_STARTED, Value.valueOf(map))
    val eventData = event.getRenderFrameStartedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
  }

  @Test
  fun getRenderFrameStartedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
    )
    val event = Event(MapEvents.RENDER_FRAME_STARTED, Value.valueOf(map))
    val eventData = event.getRenderFrameStartedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
  }

  @Test
  fun getRenderFrameFinishedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
      "needs-repaint" to Value(true),
      "placement-changed" to Value(false),
      "render-mode" to Value("full"),
    )
    val event = Event(MapEvents.RENDER_FRAME_FINISHED, Value.valueOf(map))
    val eventData = event.getRenderFrameFinishedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
    assertEquals(true, eventData.needsRepaint)
    assertEquals(false, eventData.placementChanged)
    assertEquals(RenderMode.FULL, eventData.renderMode)
  }

  @Test
  fun getRenderFrameFinishedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "needs-repaint" to Value(true),
      "placement-changed" to Value(false),
      "render-mode" to Value("full"),
    )
    val event = Event(MapEvents.RENDER_FRAME_FINISHED, Value.valueOf(map))
    val eventData = event.getRenderFrameFinishedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
    assertEquals(true, eventData.needsRepaint)
    assertEquals(false, eventData.placementChanged)
    assertEquals(RenderMode.FULL, eventData.renderMode)
  }

  @Test
  fun getCameraChangedEventData() {
    val map = hashMapOf(
      "begin" to Value(1L),
      "end" to Value(2L),
    )
    val event = Event(MapEvents.CAMERA_CHANGED, Value.valueOf(map))
    val eventData = event.getCameraChangedEventData()

    assertEquals(1L, eventData.begin)
    assertEquals(2L, eventData.end)
  }

  @Test
  fun getCameraChangedEventDataWithNullEnd() {
    val map = hashMapOf(
      "begin" to Value(1L),
    )
    val event = Event(MapEvents.CAMERA_CHANGED, Value.valueOf(map))
    val eventData = event.getCameraChangedEventData()

    assertEquals(1L, eventData.begin)
    assertNull(eventData.end)
  }
}