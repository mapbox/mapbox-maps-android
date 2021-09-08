import org.jetbrains.dokka.gradle.DokkaTask

project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  id("com.android.library")
  kotlin("android")
  id("com.jaredsburrows.license")
  id("org.jetbrains.dokka")
}

android {
  val androidSdkVersions = project.extra.get("androidSdkVersions") as HashMap<String, String>
  compileSdkVersion(androidSdkVersions["compileSdkVersion"]!!)
  defaultConfig {
    minSdkVersion(androidSdkVersions["minSdkVersion"])
    targetSdkVersion(androidSdkVersions["targetSdkVersion"])
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
    animationsDisabled = true
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }

  sourceSets {
    // limit amount of exposed library resources
    getByName("main").res.srcDirs("src/main/res-public")
  }
}

val buildFromSource: String by project

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  api(dependencies["mapboxBase"]!!)
  if (buildFromSource.toBoolean()) {
    api(project(":okhttp"))
  } else {
    api(dependencies["mapboxOkHttp"]!!)
  }
  implementation(dependencies["mapboxAnnotations"]!!)
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
  api(project(":extension-localization"))
  api(project(":plugin-lifecycle"))
  implementation(dependencies["kotlin"]!!)
  implementation(dependencies["androidxCoreKtx"]!!)
  implementation(dependencies["androidxAnnotations"]!!)
  testImplementation(dependencies["junit"]!!)
  testImplementation(dependencies["mockk"]!!)
  testImplementation(dependencies["androidxTestCore"]!!)
  testImplementation(dependencies["robolectric"]!!)
  testImplementation(dependencies["robolectricEgl"]!!)
  debugImplementation(dependencies["androidxAppCompat"]!!)
  androidTestImplementation(dependencies["androidxTestRunner"]!!)
  androidTestImplementation(dependencies["androidxTestJUnit"]!!)
  androidTestImplementation(dependencies["androidxRules"]!!)
  androidTestImplementation(dependencies["androidxJUnitTestRules"]!!)
  androidTestImplementation(dependencies["androidxEspresso"]!!)
  androidTestImplementation(dependencies["androidxUiAutomator"]!!)
  androidTestUtil(dependencies["androidxOrchestrator"]!!)
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
  from("$rootDir/gradle/track-public-apis.gradle")
}