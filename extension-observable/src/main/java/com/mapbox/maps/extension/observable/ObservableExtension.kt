package com.mapbox.maps.extension.observable

import com.google.gson.Gson
import com.mapbox.common.ValueConverter
import com.mapbox.maps.Event
import com.mapbox.maps.ObservableInterface
import com.mapbox.maps.Observer

/**
 * Subscribes an Observer for of event type"resource-request".
 * Observable will hold a strong reference to an Observer instance, therefore,
 * in order to stop receiving notifications, caller must call unsubscribe with an
 * Observer instance used for an initial subscription.
 *
 * @param observer an Observer
 */
fun ObservableInterface.subscribeResourceRequest(observer: Observer) =
  subscribe(observer, listOf("resource-request"))

/**
 * Unsubscribes an Observer for event types "resource-request".
 *
 * @param observer an Observer
 */
fun ObservableInterface.unsubscribeResourceRequest(observer: Observer) =
  unsubscribe(observer, listOf("resource-request"))

/**
 * Get the parsed event data.
 * @return a parsed EventData object.
 */
fun Event.getEventData(): EventData {
  val json = ValueConverter.toJson(data)
  return Gson().fromJson(json, EventData::class.java)
}