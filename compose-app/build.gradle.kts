plugins {
  id("com.mapbox.gradle.application")
  id("org.jetbrains.kotlin.plugin.parcelize")
}

val buildFromSource: String by project

android {
  compileSdk = libs.versions.composeCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.composeMinSdkVersion.get().toInt()
    targetSdk = libs.versions.composeTargetSdkVersion.get().toInt()
    applicationId = "com.mapbox.maps.compose.testapp"
    versionCode = 1
    versionName = "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments(mapOf("clearPackageData" to "true"))
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.get()
  }

  kotlinOptions {
    freeCompilerArgs += "-Xexplicit-api=strict"
  }

  testOptions {
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }

  packagingOptions {
    if (buildFromSource.toBoolean()) {
      jniLibs.pickFirsts.add("**/libc++_shared.so")
    }
  }

  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(project(":sdk"))
  implementation(project(":extension-compose"))
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.material)
  implementation(libs.androidx.activityCompose)
  implementation(libs.googleMaterialDesign)
  implementation(libs.compose.uiToolingPreview)
  implementation(libs.compose.uiTooling)

  implementation(libs.squareRetrofit)
  implementation(libs.androidx.appCompat)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.multidex)
  androidTestUtil(libs.androidx.orchestrator)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.testJUnit)
  androidTestImplementation(libs.androidx.uiAutomator)
  testImplementation(libs.junit)
  detektPlugins(libs.detektFormatting)
  debugImplementation(libs.squareLeakCanary)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}