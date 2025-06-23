plugins {
  id("com.mapbox.gradle.application")
  id("org.jetbrains.kotlin.plugin.parcelize")
}


android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.compose.testapp"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
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

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      // For local testing only, should use a different keystore if used besides testing.
      signingConfig = signingConfigs.getByName("debug")
      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"),
        "proguard-rules.pro",
        "mapbox-services-proguard-rules.pro",
        "retrofit2-proguard-rules.pro"
      )
    }
    getByName("debug") {
      isMinifyEnabled = false
      signingConfig = signingConfigs.getByName("debug")
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

  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(project(":maps-sdk"))
  implementation(project(":extension-compose"))
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.material)
  implementation(appsLibs.androidx.activityCompose)
  implementation(appsLibs.compose.uiToolingPreview)
  implementation(appsLibs.compose.uiTooling)
  implementation(appsLibs.googleMaterialDesign)
  implementation(appsLibs.mapbox.turf)
  implementation(appsLibs.mapbox.services)

  implementation(libs.androidx.appCompat)
  implementation(libs.androidx.coreKtx)
  implementation(appsLibs.googlePlayServicesLocation)
  androidTestUtil(libs.androidx.orchestrator)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.testJUnit)
  androidTestImplementation(libs.androidx.uiAutomator)
  testImplementation(libs.junit)
  detektPlugins(libs.detektFormatting)
  debugImplementation(appsLibs.squareLeakCanary)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}