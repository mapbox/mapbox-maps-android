package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.mapbox.maps.gradle.plugins.internal.setDisallowChanges
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.util.Locale
import javax.inject.Inject

/**
 * Jacoco extension that takes care of setting up Jacoco.
 *
 * It can be applied to Android library subproject (see [applyTo]).
 */
public abstract class MapboxJacocoExtension @Inject constructor(objects: ObjectFactory) {

  private val excludesProperty: ListProperty<String> = objects.listProperty<String>()
    .convention(DEFAULT_EXCLUDED)

  @Suppress("MemberVisibilityCanBePrivate")
  public var excludes: List<String>
    get() = excludesProperty.get()
    set(value) {
      excludesProperty.setDisallowChanges(value)
    }

  private val xmlEnabledProperty: Property<Boolean> =
    objects.property(Boolean::class.java).convention(true)

  /**
   * A flag that determines whether XML report should be generated.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var xmlEnabled: Boolean
    get() = xmlEnabledProperty.get()
    set(value) {
      xmlEnabledProperty.setDisallowChanges(value)
    }

  private val htmlEnabledProperty: Property<Boolean> =
    objects.property(Boolean::class.java).convention(true)

  /**
   * A flag that determines whether HTML report should be generated.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var htmlEnabled: Boolean
    get() = htmlEnabledProperty.get()
    set(value) {
      htmlEnabledProperty.setDisallowChanges(value)
    }

  private val csvEnabledProperty: Property<Boolean> =
    objects.property(Boolean::class.java).convention(true)

  /**
   * A flag that determines whether CSV report should be generated.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var csvEnabled: Boolean
    get() = csvEnabledProperty.get()
    set(value) {
      csvEnabledProperty.setDisallowChanges(value)
    }

  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    project.extensions.configure<JacocoPluginExtension> {
      toolVersion = "0.8.12"
    }
    project.tasks.withType(Test::class.java).configureEach {
      extensions.configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
      }
    }

    project.extensions.getByType(AndroidComponentsExtension::class.java).onVariants {
      project.configureJacocoForVariant(it)
    }
  }

  private fun Project.configureJacocoForVariant(variant: Variant) {
    val variantName = variant.name
    val capitalizedVariant = variantName.capitalize(Locale.ROOT)
    val testTaskName = "test${capitalizedVariant}UnitTest"
    val reportTaskName = "jacoco${testTaskName.capitalize(Locale.ROOT)}Report"

    tasks.register<JacocoReport>(reportTaskName) {
      dependsOn(testTaskName)
      group = "Reporting"
      description = "Generate Jacoco coverage reports for the $capitalizedVariant variant."

      reports {
        xml.required.set(xmlEnabled)
        xml.outputLocation.set(
          project.layout.buildDirectory.file("jacoco/jacoco.xml").get().asFile
        )

        html.required.set(htmlEnabled)
        html.outputLocation.set(
          project.layout.buildDirectory.file("jacoco/jacocoHtml").get().asFile
        )

        csv.required.set(csvEnabled)
        csv.outputLocation.set(
          project.layout.buildDirectory.file("jacoco/jacoco.csv").get().asFile
        )
      }

      val javaClasses = fileTree("$buildDir/intermediates/javac/$variantName") {
        exclude(this@MapboxJacocoExtension.excludes)
      }
      val kotlinClasses = fileTree("$buildDir/tmp/kotlin-classes/$variantName") {
        exclude(this@MapboxJacocoExtension.excludes)
      }

      classDirectories.setFrom(files(javaClasses, kotlinClasses))

      sourceDirectories.setFrom(
        files(
          "$projectDir/src/main/java",
          "$projectDir/src/$variantName/java",
          "$projectDir/src/main/kotlin",
          "$projectDir/src/$variantName/kotlin"
        )
      )

      executionData.setFrom(
        fileTree(buildDir) {
          include(
            "jacoco/$testTaskName.exec",
            "outputs/unit_test_code_coverage/${variantName}UnitTest/$testTaskName.exec"
          )
        }
      )
    }
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("jacoco")
  }

  private companion object {
    private val DEFAULT_EXCLUDED = listOf(
      "android/**/*.*",
      "**/databinding/*Binding.*",
      "**/R.class",
      "**/R$*.class",
      "**/BuildConfig.*",
      "**/Manifest*.*",
      "**/Lambda$*.class",
      "**/Lambda.class",
      "**/*BR*.*",
      "**/*Companion*.*",
      "**/*Lambda.class",
      "**/*Lambda*.class",
      "**/*\$Lambda\$*.*",
      "**/*Test*.*",
      "**/*MapboxStyleManager.class",
      "**/*RenderThreadRecorder.class",
      "com/mapbox/maps/plugin/*/generated/*",
    )
  }
}