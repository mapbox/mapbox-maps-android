package com.mapbox.maps.gradle.plugins.extensions

import japicmp.filter.BehaviorFilter
import japicmp.filter.ClassFilter
import javassist.CtBehavior
import javassist.CtClass
import javassist.NotFoundException
import me.champeau.gradle.japicmp.JapicmpTask
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
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
      currentVersionProperty.set(value)
      currentVersionProperty.disallowChanges()
    }

  /**
   * Previous library version (e.g. `11.0.0-SNAPSHOT`) to compare against
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var previousVersion: String
    get() = previousVersionProperty.get()
    set(value) {
      previousVersionProperty.set(value)
      previousVersionProperty.disallowChanges()
    }

  /**
   * Artifact group ID (e.g. `com.mapbox.maps`)
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var artifactGroupId: String
    get() = artifactGroupIdProperty.get()
    set(value) {
      artifactGroupIdProperty.set(value)
      artifactGroupIdProperty.disallowChanges()
    }

  /**
   * Artifact ID (e.g. `maps-viewport`)
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var artifactId: String
    get() = artifactIdProperty.get()
    set(value) {
      artifactIdProperty.set(value)
      artifactIdProperty.disallowChanges()
    }

  /**
   * Mapbox Download Token to use when fetching artifacts
   */
  @Suppress("MemberVisibilityCanBePrivate")
  public var downloadToken: String
    get() = downloadTokenProperty.get()
    set(value) {
      downloadTokenProperty.set(value)
      downloadTokenProperty.disallowChanges()
    }

  internal fun applyTo(project: Project) {
    project.applyRequiredPlugins()
    project.configure()
  }

  private fun Project.applyRequiredPlugins() {
    plugins.apply("me.champeau.gradle.japicmp")
  }

  private fun Project.configure() {
    val fetchPreviousReleaseAarTask = tasks.register("fetchPreviousReleaseAar", Exec::class.java) {
      val workingDir = layout.buildDirectory.dir("intermediates/previous_release")
      Files.createDirectories(workingDir.get().asFile.toPath())
      workingDir(workingDir)

      val versionToFetch = if (previousVersionProperty.isPresent) {
        previousVersionProperty.get()
      } else {
        val currentVersion =
          currentVersionProperty.getOrElse(project.property("versionName") as String)
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
      val artifactGroupId =
        artifactGroupIdProperty.getOrElse(project.property("mapboxArtifactGroupId") as String)
      val artifactPath = artifactGroupId.replace('.', '/')
      val artifactId = artifactIdProperty.getOrElse(project.property("mapboxArtifactId") as String)
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

      // Exclude classes and/or methods annotated with RestrictTo
      addExcludeFilter(ClassWithRestrictToAnnotationFilter::class.java)
      addExcludeFilter(MethodWithRestrictToAnnotationFilter::class.java)

      // Exclude classes and/or methods annotated with MapboxExperimental
      addExcludeFilter(ClassWithExperimentalAnnotationFilter::class.java)
      addExcludeFilter(MethodWithExperimentalAnnotationFilter::class.java)

      richReport {
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

private const val RESTRICT_TO_ANNOTATION_NAME = "androidx.annotation.RestrictTo"
private const val EXPERIMENTAL_ANNOTATION_NAME = "com.mapbox.maps.MapboxExperimental"

public class ClassWithRestrictToAnnotationFilter : ClassFilter {
  override fun matches(ctClass: CtClass): Boolean =
    ctClass.itOrDeclaringClassHasAnnotation(RESTRICT_TO_ANNOTATION_NAME)
}

public class ClassWithExperimentalAnnotationFilter : ClassFilter {
  override fun matches(ctClass: CtClass): Boolean =
    ctClass.hasAnnotation(EXPERIMENTAL_ANNOTATION_NAME)
}

public class MethodWithExperimentalAnnotationFilter : BehaviorFilter {
  override fun matches(ctBehavior: CtBehavior): Boolean =
    ctBehavior.itOrDeclaringClassHasAnnotation(EXPERIMENTAL_ANNOTATION_NAME)
}

public class MethodWithRestrictToAnnotationFilter : BehaviorFilter {
  override fun matches(ctBehavior: CtBehavior): Boolean =
    ctBehavior.itOrDeclaringClassHasAnnotation(RESTRICT_TO_ANNOTATION_NAME)
}

private fun CtBehavior.itOrDeclaringClassHasAnnotation(annotationName: String): Boolean {
  var hasAnnotation = hasAnnotation(annotationName)
  // Method does not have annotation, let's check its class
  try {
    if (!hasAnnotation) {
      hasAnnotation = declaringClass.itOrDeclaringClassHasAnnotation(annotationName)
    }
  } catch (_: NotFoundException) {
    // Not much we can do if we can't load the class so let's ignore it
  }
  return hasAnnotation
}

private fun CtClass.itOrDeclaringClassHasAnnotation(annotationName: String): Boolean {
  var hasAnnotation = hasAnnotation(annotationName)
  if (!hasAnnotation) {
    try {
      if (declaringClass != null) {
        hasAnnotation = declaringClass.itOrDeclaringClassHasAnnotation(annotationName)
      }
    } catch (_: NotFoundException) {
      // Not much we can do if we can't load the class so let's ignore it
    }
  }
  return hasAnnotation
}

