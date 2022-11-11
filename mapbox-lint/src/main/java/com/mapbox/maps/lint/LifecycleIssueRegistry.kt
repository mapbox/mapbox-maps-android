package com.mapbox.maps.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor

class LifecycleIssueRegistry : IssueRegistry() {

  override val issues = listOf(LifecycleMethodDetector.ISSUE)

  override val api: Int = com.android.tools.lint.detector.api.CURRENT_API

  override val vendor = Vendor(
    vendorName = "Mapbox",
    identifier = "com.mapbox.maps:lifecycle-lint",
    feedbackUrl = "https://github.com/mapbox/mapbox-maps-android/issues",
    contact = "https://github.com/mapbox/mapbox-maps-android"
  )
}