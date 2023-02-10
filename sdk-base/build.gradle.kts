import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
  id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

val VERSION_NAME: String by project

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "MAPBOX_SDK_IDENTIFIER", String.format("\"%s\"", "mapbox-maps-android"))
    buildConfigField("String", "MAPBOX_SDK_VERSION", String.format("\"%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_VERSION_STRING", String.format("\"Mapbox/%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_EVENTS_USER_AGENT", String.format("\"mapbox-maps-android/%s\"", VERSION_NAME))
  }

  flavorDimensions.add("version")
  productFlavors {
    val private by creating {
      dimension = "version"
    }
    val public by creating {
      dimension = "version"
      isDefault = true
    }
  }
}
val buildFromSource: String by project

dependencies {
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.androidxAnnotations)
  api(Dependencies.mapboxGestures)
  if (buildFromSource.toBoolean()) {
    api(project(":maps-core"))
    api(project(":common"))
  } else {
    api(Dependencies.mapboxGlNative)
    api(Dependencies.mapboxCoreCommon)
  }

  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.equalsVerifier)
  detektPlugins(Dependencies.detektFormatting)
}

// let's register different Dokka Javadoc tasks per flavor
android.productFlavors.all {
  val flavor = name
  tasks.register("${flavor}ReleaseDokkaJavadoc", DokkaTask::class.java) {
    // We want to generate Javadoc so we copy the `dokkaJavadoc` task plugins dependencies in order
    // to generate documentation in Javadoc format
    val dokkaJavadocTask = tasks.findByName("dokkaJavadoc") as DokkaTask
    plugins.dependencies.addAll(dokkaJavadocTask.plugins.allDependencies)
    // Make sure we disable all the source sets not related to this flavour (and only for release build)
    dokkaSourceSets.configureEach {
      if (name != "main" && name != flavor && name != "${flavor}Release") {
        suppress.set(true)
      }
      reportUndocumented.set(true)
      failOnWarning.set(true)
    }
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
//  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}