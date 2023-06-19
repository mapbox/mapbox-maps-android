package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.gradle.LibraryExtension
import com.android.builder.model.SourceProvider
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.listProperty
import org.jetbrains.dokka.gradle.DokkaCollectorTask
import org.jetbrains.dokka.gradle.DokkaTask
import javax.inject.Inject

/**
 * Dokka extension that takes care of setting up related tasks and configuration for Dokka.
 *
 * It can be applied to root project (see [applyToRootProject]) or Android library subproject
 * (see [applyTo]).
 */
public abstract class MapboxDokkaExtension @Inject constructor(objects: ObjectFactory) {
  private val extraListOfSourcesProperty: ListProperty<String> =
    objects.listProperty<String>().convention(emptyList())

  /**
   * List of files to include as part of the Dokka source sets when generating documentation.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var extraListOfSources: List<String>
    get() = extraListOfSourcesProperty.get()
    set(value) {
      extraListOfSourcesProperty.set(value)
      extraListOfSourcesProperty.disallowChanges()
    }

  private val reportUndocumentedProperty: Property<Boolean> =
    objects.property(Boolean::class.java).convention(true)

  /**
   * Whether to emit warnings about visible undocumented declarations.
   * `true` by default.
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var reportUndocumented: Boolean
    get() = reportUndocumentedProperty.get()
    set(value) {
      reportUndocumentedProperty.set(value)
      reportUndocumentedProperty.disallowChanges()
    }

  internal fun applyToRootProject(project: Project) {
    project.applyRequiredPlugins()
    configureRootProjectDokka(project)
  }

  internal fun applyTo(project: Project, libraryExtension: LibraryExtension) {
    project.applyRequiredPlugins()
    libraryExtension.configureDokka(project)
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("org.jetbrains.dokka")
  }

  private fun configureRootProjectDokka(rootProject: Project) = rootProject.run {
    tasks.withType(DokkaCollectorTask::class.java).configureEach {
      failOnWarning.set(true)
    }
  }

  private fun LibraryExtension.configureDokka(project: Project) {
    // "mapbox.dokkaHtmlFlavor" holds the variant to use. For example, "release". This is used
    // when executing `dokkaHtmlCollector` task to be able to limit the source set to a specific
    // variant
    libraryVariants.all {
      if (this.buildType.isDebuggable) {
        return@all
      }
      val variantName: String = name
      registerVariantDokkaJavadocTask(project, variantName).configure {
        configureDokkaTaskSourceSets(variantName, sourceSets)
      }

      val mapboxHtmlDokkaFlavor: String? = project.findProperty("mapbox.dokkaHtmlFlavor") as String?
      val taskNames = project.gradle.startParameter.taskNames
      if ((taskNames.contains("dokkaHtmlCollector") || taskNames.contains("dokkaHtml")) && mapboxHtmlDokkaFlavor == null) {
        throw GradleException("Parameter `mapbox.dokkaHtmlFlavor` must be defined with `privateRelease` of `publicRelease`. For example, `./gradlew dokkaHtmlCollector -Pmapbox.dokkaHtmlFlavor=privateRelease`")
      }
      if (variantName == mapboxHtmlDokkaFlavor) {
        project.logger.warn("Forcing all Dokka tasks to variant $variantName")
        project.tasks.withType(DokkaTask::class.java).configureEach {
          // Skip our own dokka tasks that are already configured
          if (taskIdentity.taskType == OwnDokkaTask::class.java) {
            return@configureEach
          }
          configureDokkaTaskSourceSets(variantName, sourceSets)
        }
      }
    }
  }

  private fun registerVariantDokkaJavadocTask(
    project: Project,
    variantName: String
  ): TaskProvider<OwnDokkaTask> =
    project.tasks.register("${variantName}DokkaJavadoc", OwnDokkaTask::class.java) {
      // We want to generate Javadoc so we copy the `dokkaJavadoc` task plugins/runtime
      val dokkaJavadocTask = project.tasks.findByName("dokkaJavadoc") as DokkaTask
      plugins.setExtendsFrom(listOf(dokkaJavadocTask.plugins))
      runtime.setExtendsFrom(listOf(dokkaJavadocTask.runtime))
    }

  private fun DokkaTask.configureDokkaTaskSourceSets(
    variantName: String,
    sourceSets: MutableList<SourceProvider>
  ) {
    // First disable all dokka source sets except the `variantName` one that will be used later
    dokkaSourceSets.configureEach {
      suppress.set(name != variantName)
    }
    // To avoid undocumented inherited methods/classes we need to join all the source roots
    // related to the flavor release variant into one source set (`variantName`).
    dokkaSourceSets.named(variantName) {
      sourceSets.forEach {
        it.javaDirectories.forEach { javaDirectory ->
          sourceRoots.from(javaDirectory)
        }
      }
      this@MapboxDokkaExtension.extraListOfSources.forEach { filePath ->
        if (!filePath.startsWith("//")) {
          sourceRoots.from(project.file("${project.rootDir}/$filePath"))
        }
      }
      reportUndocumented.set(this@MapboxDokkaExtension.reportUndocumented)
      failOnWarning.set(true)
    }
  }

  /**
   * Our own [DokkaTask] to differentiate it from the ones provided by the Dokka plugin
   */
  internal abstract class OwnDokkaTask : DokkaTask()
}
