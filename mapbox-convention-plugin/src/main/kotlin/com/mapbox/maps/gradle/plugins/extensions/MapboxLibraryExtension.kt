package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.gradle.LibraryExtension
import com.mapbox.maps.gradle.plugins.internal.getVersionCatalog
import com.mapbox.maps.gradle.plugins.internal.newInstance
import com.mapbox.maps.gradle.plugins.internal.setDisallowChanges
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property
import javax.inject.Inject

public abstract class MapboxLibraryExtension @Inject constructor(objects: ObjectFactory) :
  MapboxDependenciesExtension(objects) {

  private var jApiCmpEnabledProperty: Property<Boolean> =
    objects.property<Boolean>().convention(true)

  /** Whether jApiCmpEnabled is enabled. Defaults to true. */
  @Suppress("MemberVisibilityCanBePrivate")
  public var jApiCmpEnabled: Boolean
    get() = jApiCmpEnabledProperty.get()
    set(value) {
      jApiCmpEnabledProperty.setDisallowChanges(value)
    }

  private val dokka = objects.newInstance<MapboxDokkaExtension>()
  private val jApiCmp = objects.newInstance<MapboxJApiCmpExtension>()
  private val jacoco = objects.newInstance<MapboxJacocoExtension>()
  private val publishLibrary = objects.newInstance<MapboxPublishLibraryExtension>()

  /** Configure the inner DSL object, [MapboxDokkaExtension]. */
  public fun dokka(action: Action<MapboxDokkaExtension>) {
    action.execute(dokka)
  }

  /** Configure the inner DSL object, [MapboxJApiCmpExtension]. */
  public fun jApiCmp(action: Action<MapboxJApiCmpExtension>) {
    action.execute(jApiCmp)
  }

  /** Configure the inner DSL object, [MapboxJacocoExtension]. */
  public fun jacoco(action: Action<MapboxJacocoExtension>) {
    action.execute(jacoco)
  }

  /** Configure the inner DSL object, [MapboxJacocoExtension]. */
  public fun publish(action: Action<MapboxPublishLibraryExtension>) {
    publishLibrary.enabled = true
    action.execute(publishLibrary)
  }

  override fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    val libraryExtension = project.extensions.getByType(LibraryExtension::class.java)
    super.applyTo(project)
    libraryExtension.configurePublicResource(project)
    libraryExtension.setMinCompileSdkVersion(project)
    dokka.applyTo(project, libraryExtension)
    // we allow to disable jApiCmp so first we have to evaluate
    project.afterEvaluate {
      if (jApiCmpEnabledProperty.getOrElse(true)) {
        jApiCmp.applyTo(this)
      }
    }
    jacoco.applyTo(project)
    publishLibrary.applyTo(project)
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("com.android.library")
    plugins.apply("kotlin-android")
    plugins.apply("io.gitlab.arturbosch.detekt")
  }

  internal companion object {
    private const val NAME = "mapboxLibrary"

    internal fun Project.mapboxLibraryExtension(): MapboxLibraryExtension =
      extensions.create(NAME, MapboxLibraryExtension::class.java)
  }
}

private fun LibraryExtension.configurePublicResource(project: Project) {
  sourceSets.all {
    // limit amount of exposed library resources
    res.srcDir("${project.projectDir}/src/$name/res-public")
  }
}

private fun LibraryExtension.setMinCompileSdkVersion(project: Project) {
  val androidMinCompileSdkVersion =
    project.getVersionCatalog().findVersion("androidMinCompileSdkVersion")
  if (androidMinCompileSdkVersion.isPresent) {
    val minCompileSdkVersion = androidMinCompileSdkVersion.get().requiredVersion.toInt()
    project.logger.info("Set minCompileSdkVersion=$minCompileSdkVersion")
    defaultConfig.aarMetadata.minCompileSdk = minCompileSdkVersion
  } else {
    project.logger.warn("androidMinCompileSdkVersion not found in versions catalog, skip setting minCompileSdkVersion..")
  }
}