package com.mapbox.maps.lint

import com.android.tools.lint.client.api.IssueRegistry

class LifecycleIssueRegistry : IssueRegistry() {

  override val issues = listOf(LifecycleMethodDetector.ISSUE)

  override val api: Int = com.android.tools.lint.detector.api.CURRENT_API
}