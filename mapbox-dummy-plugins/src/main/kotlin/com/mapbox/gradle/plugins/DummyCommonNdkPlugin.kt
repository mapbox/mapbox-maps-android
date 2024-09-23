package com.mapbox.gradle.plugins

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


public class DummyCommonNdkPlugin : Plugin<Project> {
  override fun apply(project: Project): Unit = with(project) {
    val androidComponents = extensions.getByType(AndroidComponentsExtension::class.java)
    androidComponents.finalizeDsl { extension ->
      extension.ndkVersion = "23.2.8568313"
    }
  }
}
