package com.mapbox.maps.gradle.plugins.extensions

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.mapbox.maps.gradle.plugins.internal.appendNdkIfNeeded
import com.mapbox.maps.gradle.plugins.internal.setDisallowChanges
import com.mapbox.sdkregistry.SDKRegistryExtension
import com.mapbox.sdkregistry.SDKRegistryPublication
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import javax.inject.Inject

private const val VARIANT_TO_PUBLISH = "release"

/**
 * Extension that takes care of configuring publishing the library artifact to the Mapbox SDK
 * Registry and set up the right artifacts/tasks.
 *
 * It can be applied to Android library subproject (see [applyTo]).
 */
@Suppress("MemberVisibilityCanBePrivate")
public abstract class MapboxPublishLibraryExtension
@Inject
constructor(objects: ObjectFactory) {
  // Leverage SDKRegistryPublication by delegating because we don't want to expose all the properties
  private val sdkRegistryPublication: SDKRegistryPublication = SDKRegistryPublication("", objects)

  /**
   * Defines the name of the sdk, will be placed in the below default api Url.
   *
   * `s3://mapbox-api-downloads-production/v2/${sdkName}/releases/android`
   */
  public var sdkName: String by sdkRegistryPublication::sdkName
  /**
   * Defines whether to run the task in dryRun mode.
   *
   * When enabled, the task will prepare and pack all the artifacts locally but skip uploading to S3.
   */
  public var dryRun: Boolean by sdkRegistryPublication::dryRun
  /**
   * Defines whether to exclude the current module from the root project level's `mapboxSDKRegistryPublishAll` task.
   *
   * If true is specified, the module related sdk-registry tasks will be ignored from root project level's `mapboxSDKRegistryPublishAll` task.
   *
   * Tips:
   *   When [executing tasks by name](https://docs.gradle.org/current/userguide/intro_multi_project_builds.html#sec:executing_tasks_by_name), the basic rule behind Gradle’s behavior is: execute all tasks down the hierarchy which have this name.
   *   To exclude uploading/publishing tasks for given subproject, use following command: `./gradlew mapboxSDKRegistryUpload -x subproject-name-to-exclude:mapboxSDKRegistryUpload`
   *
   * Default: false
   */
  public var excludeFromRootProject: Boolean by sdkRegistryPublication::excludeFromRootProject

  private val enabledProperty: Property<Boolean> = objects.property<Boolean>().convention(false)
  private val publishProperty: Property<Boolean> = objects.property<Boolean>().convention(true)
  private val publishMessageProperty: Property<String> = objects.property<String>().convention("cc @mapbox/maps-android")
  private val versionNameOverrideProperty: Property<String> = objects.property<String>()
  private val snapshotOverrideProperty: Property<Boolean> = objects.property<Boolean>()
  private val groupProperty: Property<String> = objects.property<String>()
  private val artifactIdProperty: Property<String> = objects.property<String>()
  private val artifactTitleProperty: Property<String> = objects.property<String>()
  private val artifactDescriptionProperty: Property<String> = objects.property<String>()
  private val publishNdkVariantProperty: Property<Boolean> = objects.property<Boolean>().convention(true)
  /**
   * Defines whether to publish the release when artifacts are uploaded.
   *
   * When enabled, the task will publish the release after the artifacts are uploaded.
   *
   * Default: true
   */
  public var publish: Boolean
    get() = publishProperty.get()
    set(value) = publishProperty.setDisallowChanges(value)
  /**
   * Defines the message appending to the publish PR's description.
   *
   * Optional, when specified, the message would be appended to the publish PR.
   * Use this option to add the team for review.
   *
   * Default: "cc @mapbox/maps-android"
   */
  public var publishMessage: String
    get() = publishMessageProperty.get()
    set(value) = publishMessageProperty.setDisallowChanges(value)

  /**
   * Internal flag to know if this extension should be configured.
   */
  internal var enabled: Boolean
    get() = enabledProperty.get()
    set(value) = enabledProperty.setDisallowChanges(value)

  /**
   * Version name to use instead of the global one (defined in project property `VERSION_NAME`).
   */
  public var versionNameOverride: String
    get() = versionNameOverrideProperty.get()
    set(value) = versionNameOverrideProperty.setDisallowChanges(value)

  /**
   * Group ID for this project.
   */
  public var group: String
    get() = groupProperty.get()
    set(value) = groupProperty.setDisallowChanges(value)

  /**
   * Artifact ID for this project.
   */
  public var artifactId: String
    get() = artifactIdProperty.get()
    set(value) = artifactIdProperty.setDisallowChanges(value)

  /**
   * Force snapshot value, ignoring if version name contains `-SNAPSHOT`.
   */
  internal var snapshotOverride: Boolean
    get() = snapshotOverrideProperty.get()
    set(value) = snapshotOverrideProperty.setDisallowChanges(value)

  /**
   * The POM description field content.
   */
  public var artifactDescription: String
    get() = artifactDescriptionProperty.get()
    set(value) = artifactDescriptionProperty.setDisallowChanges(value)

  /**
   * The POM title field content.
   */
  public var artifactTitle: String
    get() = artifactTitleProperty.get()
    set(value) = artifactTitleProperty.setDisallowChanges(value)

  /**
   * If true, the artifact ID will be suffixed by the NDK Major version if it's not the default
   * NDK version.
   *
   * True by default.
   */
  public var publishNdkVariant: Boolean
    get() = publishNdkVariantProperty.get()
    set(value) = publishNdkVariantProperty.setDisallowChanges(value)

  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()

    project.extensions.getByType(LibraryAndroidComponentsExtension::class.java).apply {
      configurePublicationVariant()
      // We only care about `release` variant. Otherwise, we can modify the selector
      onVariants(
        selector = selector().withName(VARIANT_TO_PUBLISH)
      ) { variant ->
        project.createMavenPublication(variant.name)
      }
    }

    project.afterEvaluate {
      if (enabled) {
        createSdkRegistryPublication()
      }
    }
  }

  @Suppress("UnstableApiUsage")
  private fun LibraryAndroidComponentsExtension.configurePublicationVariant() {
    finalizeDsl {
      it.publishing {
        singleVariant(VARIANT_TO_PUBLISH) {
          withSourcesJar()
          withJavadocJar()
        }
      }
    }
  }

  private fun Project.createMavenPublication(variantName: String) {
    extensions.getByType(PublishingExtension::class.java).publications {
      register<MavenPublication>(variantName) {
        val publication = this
        this@createMavenPublication.afterEvaluate {
          if (!enabled) {
            return@afterEvaluate
          }
          publication.from(components.getByName(variantName))
          publication.groupId = group.toString()
          publication.artifactId = if (publishNdkVariant) {
            project.appendNdkIfNeeded(this@MapboxPublishLibraryExtension.artifactId)
          } else {
            this@MapboxPublishLibraryExtension.artifactId
          }
          publication.version = this.version.toString()
        }

        pom.withXml {
          asNode().apply {
            appendNode("name", artifactTitle)
            appendNode("description", artifactDescription)
            val url = "https://github.com/mapbox/mapbox-maps-android"
            appendNode("url", url)

            appendNode("licenses").appendNode("license").apply {
              appendNode("name", "Mapbox Terms of Service")
              appendNode("url", "https://www.mapbox.com/legal/tos/")
              appendNode("distribution", "repo")
            }

            appendNode("developers").appendNode("developer").apply {
              appendNode("id", "mapbox")
              appendNode("name", "Mapbox")
            }

            appendNode("scm").apply {
              val scmUrl = "scm:git@github.com:mapbox/mapbox-maps-android.git"
              appendNode("connection", scmUrl)
              appendNode("developerConnection", scmUrl)
              appendNode("url", url)
            }
          }
        }
      }
    }
  }

  private fun Project.createSdkRegistryPublication() {
    val versionName =
      versionNameOverrideProperty.orElse(property("VERSION_NAME")!!.toString()).get()
    version = versionName
    group = this@MapboxPublishLibraryExtension.group

    val mapboxDryRun = (project.findProperty("mapbox.dryRun") as String?)?.toBoolean()
    val finalDryRun = mapboxDryRun ?: dryRun
    val versionNameIsSnapshot = versionName.contains("-SNAPSHOT")
    val isSnapshot = snapshotOverrideProperty.orElse(versionNameIsSnapshot).get()
    val finalSdkName = if (publishNdkVariant) {
      project.appendNdkIfNeeded(sdkName)
    } else {
      sdkName
    }

    components.getByName(VARIANT_TO_PUBLISH) {
      val currentComponent = this
      extensions.getByType(SDKRegistryExtension::class.java).apply {
        registryPublications {
          register(currentComponent.name) {
            sdkName = finalSdkName
            dryRun = finalDryRun
            publish = this@MapboxPublishLibraryExtension.publish
            snapshot = isSnapshot
            // Allow overriding if it"s a SNAPSHOT
            override = isSnapshot
            publishMessage = this@MapboxPublishLibraryExtension.publishMessage
            publications = arrayOf(currentComponent.name)
            excludeFromRootProject = this@MapboxPublishLibraryExtension.excludeFromRootProject
          }
        }
      }
    }
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("maven-publish")
    plugins.apply("com.mapbox.sdkRegistry")
  }
}