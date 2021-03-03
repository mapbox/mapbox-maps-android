package com.mapbox.maps.extension.observable

import com.google.gson.Gson
import com.mapbox.common.ValueConverter
import com.mapbox.maps.Event
import com.mapbox.maps.MapEvents
import com.mapbox.maps.ObservableInterface
import com.mapbox.maps.Observer
import com.mapbox.maps.extension.observable.map.IDStringEventData
import com.mapbox.maps.extension.observable.map.MapLoadingErrorEventData
import com.mapbox.maps.extension.observable.map.RenderFrameFinishedEventData
import com.mapbox.maps.extension.observable.resourcerequest.ResourceEventData

/**
 * Subscribes an Observer for of event type"resource-request".
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeResourceRequest(observer: Observer) =
  subscribe(observer, listOf(MapEvents.RESOURCE_REQUEST))

/**
 * Unsubscribes an Observer for event types "resource-request".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeResourceRequest(observer: Observer) =
  unsubscribe(observer, listOf(MapEvents.RESOURCE_REQUEST))

/**
 * Get the parsed event data.
 * @return a parsed EventData object.
 */
fun Event.getResourceEventData(): ResourceEventData {
  val json = ValueConverter.toJson(data)
  return Gson().fromJson(json, ResourceEventData::class.java)
}

fun Event.getMapLoadingErrorEventData(): MapLoadingErrorEventData {
  val json = ValueConverter.toJson(data)
  return Gson().fromJson(json, MapLoadingErrorEventData::class.java)
}

fun Event.getIDStringEventData(): IDStringEventData {
  val json = ValueConverter.toJson(data)
  return Gson().fromJson(json, IDStringEventData::class.java)
}

fun Event.getStyleImageMissingEventData(): IDStringEventData = getIDStringEventData()

fun Event.getStyleImageRemoveUnusedEventData(): IDStringEventData = getIDStringEventData()
fun Event.getSourceAddedEventData(): IDStringEventData = getIDStringEventData()
fun Event.getSourceChangedEventData(): IDStringEventData = getIDStringEventData()
fun Event.getSourceRemovedEventData(): IDStringEventData = getIDStringEventData()

fun Event.getRenderFrameFinishedEventData(): RenderFrameFinishedEventData {
  val json = ValueConverter.toJson(data)
  return Gson().fromJson(json, RenderFrameFinishedEventData::class.java)
}