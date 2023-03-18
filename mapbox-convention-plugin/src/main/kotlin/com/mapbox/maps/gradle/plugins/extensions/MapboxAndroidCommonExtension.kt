package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized

public open class MapboxAndroidCommonExtension {
  @Suppress("UnstableApiUsage")
  internal fun CommonExtension<*, *, *, *>.configureFlavors(project: Project) {
    flavorDimensions.add(DIMENSION_NAME)
    productFlavors {
      create(PRIVATE_NAME) {
        dimension = DIMENSION_NAME
      }
      create(PUBLIC_NAME) {
        dimension = DIMENSION_NAME
      }
    }

    sourceSets.getByName(PUBLIC_NAME) {
      // limit amount of exposed library resources
      res.srcDir("${project.projectDir}/src/public/res-public")
    }

    val projectDir = project.projectDir
    val privatePath =
      "${projectDir.path}/../../mapbox-maps-android-private/${projectDir.name}/src/"
    sourceSets.getByName(PRIVATE_NAME) {
      java.srcDir("$privatePath$name/java")
      res.srcDir("$privatePath$name/res")
      // limit amount of exposed library resources
      res.srcDir("$privatePath$name/res-public")
    }
    sourceSets.getByName("test${PRIVATE_NAME.capitalized()}") {
      java.srcDir("$privatePath$name/java")
    }
    sourceSets.getByName("androidTest${PRIVATE_NAME.capitalized()}") {
      java.srcDir("$privatePath$name/java")
    }
  }

  private companion object {
    private const val DIMENSION_NAME = "version"
    private const val PRIVATE_NAME = "private"
    private const val PUBLIC_NAME = "public"
  }
}