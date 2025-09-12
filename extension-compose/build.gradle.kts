plugins {
  id("com.mapbox.gradle.library")
  id("kotlin-parcelize")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.extension.compose"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.compose.get()
  }

  buildFeatures {
    compose = true
  }

  kotlinOptions {
    freeCompilerArgs += "-Xexplicit-api=strict"
  }

  lint {
    warningsAsErrors = true
  }

  buildTypes {
    debug {
      ndk {
        var abi: String =
          if (System.getenv("ANDROID_ABI") != null) System.getenv("ANDROID_ABI") else ""
        if (abi.isNotBlank()) {
          abiFilters.add(abi)
        }
      }
    }
  }
}

mapboxLibrary {
  // we skip verifying Java binary compatibility for Compose extension as it is pure Kotlin only
  jApiCmpEnabled = false

  publish {
    group = "com.mapbox.extension"
    artifactId = "maps-compose"
    artifactTitle = "The Jetpack Compose extension for the Mapbox Maps SDK for Android"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-compose"
  }
}

dependencies {
  dependencies {
    compileOnly(project(":maps-sdk"))
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material)
    implementation(libs.bundles.base.dependencies)

    implementation(libs.androidx.coreKtx)
    debugImplementation(libs.compose.uiTestManifest)

    androidTestUtil(libs.androidx.orchestrator)

    androidTestImplementation(libs.androidx.testRunner)
    androidTestImplementation(libs.androidx.jUnitTestRules)
    androidTestImplementation(libs.androidx.testJUnit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(libs.androidx.uiAutomator)
    androidTestImplementation(project(":maps-sdk"))
    androidTestImplementation(libs.compose.uiTest)

    testImplementation(libs.bundles.base.dependenciesTests)
    testImplementation(project(":maps-sdk"))
    testImplementation(libs.junit)
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle.kts")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}