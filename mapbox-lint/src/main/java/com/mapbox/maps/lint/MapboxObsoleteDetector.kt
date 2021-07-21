package com.mapbox.maps.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiMethodCallExpression
import org.jetbrains.uast.*
import java.util.*

/**
 * Lint detector for lifecycle events
 */
class MapboxObsoleteDetector : Detector(), SourceCodeScanner {

  override fun getApplicableUastTypes(): List<Class<out UElement>>? =
    listOf(UCallExpression::class.java)

  override fun createUastHandler(context: JavaContext): UElementHandler =
    object : UElementHandler() {
      override fun visitCallExpression(node: UCallExpression) {
        if (node.methodName == ON_START || node.methodName == ON_STOP || node.methodName == ON_DESTROY || node.methodName == ON_LOW_MEMORY) {
          context.report(ISSUE, node, context.getLocation(node), REPORT_MESSAGE)
        }
      }
    }

  companion object Issues {
    private const val ON_START = "onStart"
    private const val ON_STOP = "onStop"
    private const val ON_DESTROY = "onDestroy"
    private const val ON_LOW_MEMORY = "onLowMemory"
    private const val ISSUE_ID = "Lifecycle"
    private const val BRIEF_DESCRIPTION = "You have redundant methods call"
    private const val REPORT_MESSAGE =
      "No need to invoke onStart/onStop/onDestroy/onLowMemory with Mapbox Lifecycle Plugin."

    private val IMPLEMENTATION = Implementation(
      MapboxObsoleteDetector::class.java, Scope.JAVA_FILE_SCOPE
    )

    @JvmField
    val ISSUE = Issue.create(
      ISSUE_ID, BRIEF_DESCRIPTION, REPORT_MESSAGE,
      Category.CORRECTNESS,
      8,
      Severity.WARNING,
      IMPLEMENTATION
    ).setAndroidSpecific(true);
  }
}
