import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  id("org.jetbrains.dokka")
  id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

android {
  compileSdk = AndroidVersions.AndroidAuto.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.AndroidAuto.minSdkVersion
    targetSdk = AndroidVersions.AndroidAuto.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
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

dependencies {
  api(Dependencies.androidAutoMapboxMapSdk)
  testImplementation(Dependencies.androidAutoMapboxMapSdk)

  api(Dependencies.googleCarAppLibrary)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxAnnotations)

  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  detektPlugins(Dependencies.detektFormatting)
}

// let's register different Dokka Javadoc tasks per flavor
android.productFlavors.all {
  val flavor = name
  tasks.register("${flavor}ReleaseDokkaJavadoc", DokkaTask::class.java) {
    // We want to generate Javadoc so we copy the `dokkaJavadoc` task plugins/runtime
    val dokkaJavadocTask = tasks.findByName("dokkaJavadoc") as DokkaTask
    plugins.setExtendsFrom(listOf(dokkaJavadocTask.plugins))
    runtime.setExtendsFrom(listOf(dokkaJavadocTask.runtime))

    dokkaSourceSets {
      // To avoid undocumented inherited methods/classes we need to join all the source roots
      // related to the flavor release variant into one source set (`${flavor}Release`).
      named("${flavor}Release") {
        listOf("java", "kotlin").forEach { lang ->
          sourceRoots.from(
            file("src/main/$lang"),
            file("src/${flavor}/$lang"),
            file("src/${flavor}Release/$lang"),
          )
        }
      }
      configureEach {
        // Make sure we disable all the source sets not related to this flavour release variant.
        // Otherwise, we would have duplicate classes or undocumented entries.
        if (name != "${flavor}Release") {
          suppress.set(true)
        }
        reportUndocumented.set(true)
        failOnWarning.set(true)
      }
    }
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}