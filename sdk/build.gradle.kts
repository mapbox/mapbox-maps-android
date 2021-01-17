import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
}

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)

  defaultConfig {
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    consumerProguardFiles("proguard-rules.pro")
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments = mapOf(
      "clearPackageData" to "true"
    )

    if (project.hasProperty("android.injected.invoked.from.ide")) {
      buildConfigField("boolean", "RUN_FROM_IDE", "true")
    } else {
      buildConfigField("boolean", "RUN_FROM_IDE", "false")
    }
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }

  // temporary config until gl-native v10.0.0-beta.5
  packagingOptions {
    exclude("META-INF/*.kotlin_module")
  }
}

dependencies {
  api(Dependencies.mapboxBase)
  api(Dependencies.mapboxOkHttp)
  implementation(Dependencies.mapboxAnnotations)
  api(project(":sdk-base"))
  implementation(project(":module-telemetry"))
  api(project(":extension-style"))
  api(project(":plugin-logo"))
  api(project(":plugin-compass"))
  api(project(":plugin-gestures"))
  api(project(":plugin-attribution"))
  api(project(":plugin-locationcomponent"))
  api(project(":plugin-animation"))
  api(project(":plugin-scalebar"))
  api(project(":plugin-overlay"))
  api(project(":plugin-annotation"))
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxAnnotations)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  testImplementation(Dependencies.robolectricEgl)
  debugImplementation(Dependencies.androidxAppCompat)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxTestJUnit)
  androidTestImplementation(Dependencies.androidxRules)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  androidTestUtil(Dependencies.androidxOrchestrator)
}

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets {
    configureEach {
      reportUndocumented.set(true)
      // https://github.com/mapbox/mapbox-maps-android/issues/301#issuecomment-712736885
      failOnWarning.set(false)
    }
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
}