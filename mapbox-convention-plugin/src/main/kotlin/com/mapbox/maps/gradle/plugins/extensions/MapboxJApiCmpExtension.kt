package com.mapbox.maps.gradle.plugins.extensions

import com.mapbox.maps.gradle.plugins.internal.setDisallowChanges
import japicmp.model.JApiAnnotation
import japicmp.model.JApiClass
import japicmp.model.JApiCompatibility
import japicmp.model.JApiCompatibilityChangeType
import japicmp.model.JApiMethod
import me.champeau.gradle.japicmp.JapicmpTask
import me.champeau.gradle.japicmp.report.Violation
import me.champeau.gradle.japicmp.report.stdrules.AbstractRecordingSeenMembers
import me.champeau.gradle.japicmp.report.stdrules.BinaryIncompatibleRule
import me.champeau.gradle.japicmp.report.stdrules.RecordSeenMembersSetup
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Exec
import org.gradle.kotlin.dsl.property
import org.gradle.language.base.plugins.LifecycleBasePlugin
import java.nio.file.Files
import javax.inject.Inject

public abstract class MapboxJApiCmpExtension @Inject constructor(objects: ObjectFactory) {
  private val currentVersionProperty: Property<String> = objects.property<String>()
  private val previousVersionProperty: Property<String> = objects.property<String>()
  private val artifactGroupIdProperty: Property<String> = objects.property<String>()
  private val artifactIdProperty: Property<String> = objects.property<String>()
  private val downloadTokenProperty: Property<String> = objects.property<String>()

  /**
   * Current library version (e.g. `11.0.0-SNAPSHOT`)
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var currentVersion: String
    get() = currentVersionProperty.get()
    set(value) {
      currentVersionProperty.setDisallowChanges(value)
    }

  /**
   * Previous library version (e.g. `11.0.0-SNAPSHOT`) to compare against
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var previousVersion: String
    get() = previousVersionProperty.get()
    set(value) {
      previousVersionProperty.setDisallowChanges(value)
    }

  /**
   * Artifact group ID (e.g. `com.mapbox.maps`)
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var artifactGroupId: String
    get() = artifactGroupIdProperty.get()
    set(value) {
      artifactGroupIdProperty.setDisallowChanges(value)
    }

  /**
   * Artifact ID (e.g. `maps-viewport`)
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var artifactId: String
    get() = artifactIdProperty.get()
    set(value) {
      artifactIdProperty.setDisallowChanges(value)
    }

  /**
   * Mapbox Download Token to use when fetching artifacts
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var downloadToken: String
    get() = downloadTokenProperty.get()
    set(value) {
      downloadTokenProperty.setDisallowChanges(value)
    }

  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    project.configure()
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("me.champeau.gradle.japicmp")
    plugins.apply("maven-publish")
  }

  private fun Project.configure() {
    val fetchPreviousReleaseAarTask = tasks.register("fetchPreviousReleaseAar", Exec::class.java) {
      val workingDir = layout.buildDirectory.dir("intermediates/previous_release")
      Files.createDirectories(workingDir.get().asFile.toPath())
      workingDir(workingDir)

      val publishing = project.extensions.getByType(PublishingExtension::class.java)
      val releasePublication = publishing.publications.named("release").get() as MavenPublication
      val lastStableVersion = providers.gradleProperty("LAST_STABLE_VERSION").orNull

      val versionToFetch = if (previousVersionProperty.isPresent) {
        previousVersionProperty.get()
      } else if (lastStableVersion != null) {
        lastStableVersion
      } else {
        val currentVersion =
          currentVersionProperty.getOrElse(releasePublication.version as String)
        val (majorStr, minorStr, patchStr) = semverRegex.find(currentVersion)!!.destructured
        var major = majorStr.toInt()
        var minor = minorStr.toInt()
        var patch = patchStr.toInt()
        if (patch > 0) {
          patch--
        } else if (minor > 0) {
          minor--
        } else {
          major--
        }

        "$major.$minor.$patch"
      }


      val artifactGroupId = artifactGroupIdProperty.getOrElse(releasePublication.groupId)
      val artifactPath = artifactGroupId.replace('.', '/')
      val artifactId = artifactIdProperty.getOrElse(releasePublication.artifactId)
      // https://api.mapbox.com/downloads/v2/releases/maven/com/mapbox/maps/base/11.0.0/base-11.0.0.aar
      val url =
        "https://api.mapbox.com/downloads/v2/releases/maven/${artifactPath}/${artifactId}/${versionToFetch}/${artifactId}-${versionToFetch}.aar"
      val outputAar = "${artifactId}-${versionToFetch}.aar"

      val sdkRegistryToken = downloadTokenProperty.orNull
        ?: System.getenv("SDK_REGISTRY_TOKEN")
        ?: providers.gradleProperty("SDK_REGISTRY_TOKEN").orNull

      commandLine(
        "curl",
        "-fL",
        "--user",
        "mapbox:$sdkRegistryToken",
        url,
        "--output",
        outputAar
      )
      outputs.file(workingDir.get().file(outputAar))
    }
    val preparePreviousReleaseLibJarsTask =
      tasks.register("preparePreviousReleaseLibJars", Exec::class.java) {
        dependsOn(fetchPreviousReleaseAarTask)

        val releaseAar = fetchPreviousReleaseAarTask.get().outputs.files.single()
        val releaseName = releaseAar.nameWithoutExtension
        workingDir = releaseAar.parentFile

        commandLine("unzip", "-qq", releaseAar, "classes.jar")
        val classesJarFile = workingDir.resolve("classes.jar")
        val releaseJarFile = workingDir.resolve("${releaseName}.jar")
        outputs.file(releaseJarFile)

        doLast {
          classesJarFile.renameTo(releaseJarFile)
        }
      }

    tasks.register("japicmp", JapicmpTask::class.java) {
      group = LifecycleBasePlugin.VERIFICATION_GROUP
      description = "Uses japicmp to report was has changed between releases"

      val syncReleaseLibJarsTask = tasks.named("syncReleaseLibJars")
      dependsOn(syncReleaseLibJarsTask, preparePreviousReleaseLibJarsTask)

      // Assign the jar from previous release
      oldClasspath.from(preparePreviousReleaseLibJarsTask.get().outputs.files.single())

      // Figure out the new classes.jar for the current source code
      val inputClassesJar =
        syncReleaseLibJarsTask.get().outputs.files.filter { it.isFile && it.name == "classes.jar" }
      newClasspath.from(inputClassesJar)

      onlyModified.set(true)
      //includeSynthetic.set(true)
      ignoreMissingClasses.set(true)

      // Exclude @RestrictTo, @VisibleForTesting and @MapboxExperimental annotations
      annotationExcludes.set(
        listOf(
          RESTRICT_TO_ANNOTATION,
          EXPERIMENTAL_ANNOTATION,
          MAPS_EXPERIMENTAL_ANNOTATION,
          VISIBLE_FOR_TESTING_ANNOTATION,
        )
      )

      richReport {
        addDefaultRules.set(true)
        addSetupRule(RecordSeenMembersSetup::class.java)
        addRule(InternalFilterRule::class.java)
        destinationDir.set(rootProject.layout.buildDirectory.dir("reports/japi/"))
        reportName.set("${project.path.drop(1)}.html")
      }

      htmlOutputFile.set(
        rootProject.layout.buildDirectory.file("reports/japi/raw/${project.path.drop(1)}.html")
      )
    }
  }

  internal companion object {
    private val semverRegex = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)".toRegex()
  }
}


/**
 * Changes to non-internal properties and changing their visibility to `internal` is a breaking change.
 * However, changes to the internal variables are not considered as a breaking change.
 */
public class InternalFilterRule : AbstractRecordingSeenMembers() {
  private val binaryIncompatibleRule = BinaryIncompatibleRule()

  override fun maybeAddViolation(member: JApiCompatibility): Violation? {
    return if (member.containsInternallyVisibleFunctionOrVariableChanges()) {
      Violation.accept(member, "Kotlin internal visibility")
    } else if (member.hasOnlyKotlinMetadataModification()) {
      Violation.accept(member, "Kotlin metadata change")
    } else {
      binaryIncompatibleRule.maybeAddViolation(member)
    }
  }

  /**
   * This function checks if the given JApiCompatibility object has a change to an internal variable or function.
   */
  private fun JApiCompatibility.containsInternallyVisibleFunctionOrVariableChanges(): Boolean {
    if (this.isBinaryCompatible) return false
    val member = (this as? JApiMethod) ?: return false
    val isKotlinClass = member.itOrDeclaringClassContainsKotlinMetadata()
    // Check if the method corresponds to an internal variable
    return isKotlinClass && member.name.contains("$") && member.accessModifier.valueOld.equals(
      "public", ignoreCase = true
    )
  }

  /**
   * This function checks if the given JApiCompatibility object has a modification to the Kotlin metadata.
   * If the change is an  annotation modification and the member is a class,
   * it checks if the class has only Kotlin metadata changes.
   *
   * @return true if member is not binary compatible and contains only Kotlin metadata changes, false otherwise.
   */
  private fun JApiCompatibility.hasOnlyKotlinMetadataModification(): Boolean {
    if (this.isBinaryCompatible) return false
    val member = this as? JApiClass
    val incompatibleChanges = member?.compatibilityChanges
    val compatibilityChange = incompatibleChanges?.firstOrNull()?.takeIf {
      it.type == JApiCompatibilityChangeType.ANNOTATION_MODIFIED && incompatibleChanges.size == 1
    }
    if (compatibilityChange == null) return false
    return member.annotations.containsKotlinMetadata()
  }

  private fun JApiMethod.itOrDeclaringClassContainsKotlinMetadata(): Boolean {
    return this.annotations.containsKotlinMetadata() || this.getjApiClass()?.annotations.containsKotlinMetadata() == true
  }

  private fun List<JApiAnnotation>?.containsKotlinMetadata(): Boolean {
    return this?.any { it.fullyQualifiedName == "kotlin.Metadata" } == true
  }
}

private const val RESTRICT_TO_ANNOTATION = "@androidx.annotation.RestrictTo"
private const val MAPS_EXPERIMENTAL_ANNOTATION = "@com.mapbox.maps.MapboxExperimental"
private const val VISIBLE_FOR_TESTING_ANNOTATION = "@androidx.annotation.VisibleForTesting"
private const val EXPERIMENTAL_ANNOTATION = "@com.mapbox.annotation.MapboxExperimental"
