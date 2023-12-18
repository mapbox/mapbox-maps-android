plugins {
  id("com.mapbox.gradle.library")
  id("com.jaredsburrows.license")
  // FIXME https://mapbox.atlassian.net/browse/MAPSAND-794
  //id("com.mapbox.android.sdk.versions")
}

val buildFromSource: String by project

mapboxLibrary {
  dokka {
    // Include extra list of files to generate documentation if available
    val extraApiDocs = mutableListOf<String>()

    val coreApiDocFile = rootProject.file("api-doc-list-maps-core.txt")
    if (coreApiDocFile.exists()) {
      extraApiDocs.addAll(coreApiDocFile.readLines())
    }
    val commonApiDocFile = rootProject.file("api-doc-list-maps-common.txt")
    if (commonApiDocFile.exists()) {
      extraApiDocs.addAll(commonApiDocFile.readLines())
    }
    if (extraApiDocs.isNotEmpty()) {
      extraListOfSources = extraApiDocs
      // which might not have docs, so disable report undocumented
      reportUndocumented = false
    }
  }
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
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
  api(libs.mapbox.base)

  implementation(libs.mapbox.annotations)
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
  compileOnly(libs.asyncInflater)
  api(libs.kotlin)
  api(libs.coroutines)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.annotations)

  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(libs.robolectricEgl)
  testImplementation(libs.asyncInflater)
  testImplementation(libs.androidx.testJUnit)
  testImplementation(libs.coroutinesTest)
  debugImplementation(libs.androidx.appCompat)

  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.testJUnit)
  androidTestImplementation(libs.androidx.jUnitTestRules)
  androidTestImplementation(libs.androidx.uiAutomator)
  androidTestImplementation(libs.coroutines)
  androidTestUtil(libs.androidx.orchestrator)

  detektPlugins(libs.detektFormatting)
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
