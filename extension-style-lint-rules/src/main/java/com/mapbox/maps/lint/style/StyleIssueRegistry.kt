package com.mapbox.maps.lint.style

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.mapbox.maps.lint.style.expressions.IllegalNumberOfArgumentsDetector
import com.mapbox.maps.lint.style.expressions.UnusedLiteralDetector

class StyleIssueRegistry : IssueRegistry() {

  override val issues = listOf(
    // Expression issues
    UnusedLiteralDetector.ISSUE,
    IllegalNumberOfArgumentsDetector.ISSUE,
    // Style DSL issues
    UnusedStyleDslDetector.ISSUE,
  )

  override val api: Int = com.android.tools.lint.detector.api.CURRENT_API

  override val vendor = Vendor(
    vendorName = "Mapbox",
    identifier = "com.mapbox.maps:style-lint",
    feedbackUrl = "https://github.com/mapbox/mapbox-maps-android/issues",
    contact = "https://github.com/mapbox/mapbox-maps-android"
  )
}