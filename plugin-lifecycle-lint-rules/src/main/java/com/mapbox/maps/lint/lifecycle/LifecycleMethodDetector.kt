package com.mapbox.maps.lint.lifecycle

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Lint detector for lifecycle events
 */
class LifecycleMethodDetector : Detector(), Detector.UastScanner {

  override fun getApplicableMethodNames() = listOf(ON_START, ON_STOP, ON_DESTROY, ON_LOW_MEMORY)

  override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
    if (context.evaluator.isMemberInClass(method, "com.mapbox.maps.MapView")) {
      context.report(
        ISSUE, node, context.getLocation(node),
        REPORT_MESSAGE
      )
    }
  }

  companion object Issues {
    private const val ON_START = "onStart"
    private const val ON_STOP = "onStop"
    private const val ON_DESTROY = "onDestroy"
    private const val ON_LOW_MEMORY = "onLowMemory"
    private const val ISSUE_ID = "Lifecycle"
    private const val BRIEF_DESCRIPTION = "Redundant method call"
    private const val PRIORITY = 8
    private const val REPORT_MESSAGE =
      "It's no longer required to manually invoke onStart/onStop/onDestroy/onLowMemory MapView lifecycle methods explicitly. The Mapbox Maps SDK for Android depends on the plugin-lifecycle that leverages androidx.lifecycle to make the MapView a lifecycle aware component. This behavior is automatically enforced when appcompact 1.3.0+ is used. The plugin-lifecycle can be removed from class path to opt-out of the associated behavior."

    private val IMPLEMENTATION = Implementation(
      LifecycleMethodDetector::class.java, Scope.JAVA_FILE_SCOPE
    )

    @JvmField
    val ISSUE = Issue.create(
      ISSUE_ID, BRIEF_DESCRIPTION, REPORT_MESSAGE,
      Category.CORRECTNESS,
      PRIORITY,
      Severity.WARNING,
      IMPLEMENTATION
    ).setAndroidSpecific(true)
  }
}