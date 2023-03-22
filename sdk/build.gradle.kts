plugins {
  id("com.mapbox.gradle.library")
  id("com.jaredsburrows.license")
  // FIXME https://mapbox.atlassian.net/browse/MAPSAND-794
  //id("com.mapbox.android.sdk.versions")
}

val buildFromSource: String by project

mapboxLibrary {
  dokka {
    if (buildFromSource.toBoolean()) {
      // when building from source we include the files in "upstream-api-doc-list.txt" which might not have docs
      extraListOfSources = File("upstream-api-doc-list.txt").readLines()
      reportUndocumented = false
    }
  }
}

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
    consumerProguardFiles("proguard-rules.pro")
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments(mapOf("clearPackageData" to "true"))

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
    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }
  }
}

dependencies {
  api(Dependencies.mapboxBase)
  if (buildFromSource.toBoolean()) {
    api(project(":okhttp"))
  } else {
    api(Dependencies.mapboxOkHttp)
  }

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
  api(project(":extension-localization"))
  api(project(":plugin-lifecycle"))
  api(project(":plugin-viewport"))
  compileOnly(Dependencies.asyncInflater)
  implementation(Dependencies.kotlin)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxAnnotations)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.robolectric)
  testImplementation(Dependencies.robolectricEgl)
  testImplementation(Dependencies.asyncInflater)
  testImplementation(Dependencies.equalsVerifier)
  testImplementation(Dependencies.androidxTestJUnit)
  debugImplementation(Dependencies.androidxAppCompat)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxTestJUnit)
  androidTestImplementation(Dependencies.androidxRules)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxEspresso)
  androidTestImplementation(Dependencies.androidxUiAutomator)
  androidTestImplementation(Dependencies.coroutines)
  androidTestUtil(Dependencies.androidxOrchestrator)
  detektPlugins(Dependencies.detektFormatting)
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
