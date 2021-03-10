plugins {
  id("com.android.application")
  id("kotlin-android")
  kotlin("android.extensions")
  id("com.mapbox.maps.token")
}

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)
  defaultConfig {
    applicationId = "com.mapbox.maps.testapp"
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    versionCode = 1
    versionName = "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["clearPackageData"] = "true"
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      signingConfig = signingConfigs.getByName("debug")
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  // temporary config until https://github.com/mapbox/mapbox-base-android/issues/53 is resolved
  packagingOptions {
    exclude("META-INF/*.kotlin_module")
  }

  buildFeatures {
    // Enables Jetpack Compose for this module
    compose = true
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  composeOptions {
    kotlinCompilerVersion = "1.4.30"
    kotlinCompilerExtensionVersion = "1.0.0-beta01"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  testOptions {
    animationsDisabled = true
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
    }
  }
}

androidExtensions {
  isExperimental = true
}

dependencies {
  implementation(project(":sdk"))
  implementation(project(":plugin-location"))
  implementation(project(":extension-observable"))
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxJavaTurf)
  implementation(Dependencies.mapboxJavaGeoJSON)
  implementation(Dependencies.mapboxServices)
  implementation(Dependencies.coroutines)
  implementation(Dependencies.androidxAppCompat)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxRecyclerView)
  implementation(Dependencies.androidxConstraintLayout)
  implementation(Dependencies.androidxMultidex)
  implementation(Dependencies.googleMaterialDesign)
  implementation(Dependencies.squareRetrofit)
  implementation(Dependencies.squareRetrofitGsonConverter)
  // jetpack compose dependencies
  implementation("androidx.compose.ui:ui:1.0.0-beta01")
  // Tooling support (Previews, etc.)
  implementation("androidx.compose.ui:ui-tooling:1.0.0-beta01")
  // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
  implementation("androidx.compose.foundation:foundation:1.0.0-beta01")
  // Material Design
  implementation("androidx.compose.material:material:1.0.0-beta01")
  // Material design icons
  implementation("androidx.compose.material:material-icons-core:1.0.0-beta01")
  implementation("androidx.compose.material:material-icons-extended:1.0.0-beta01")
  // Integration with activities
  implementation("androidx.activity:activity-compose:1.3.0-alpha03")
  // Integration with ViewModels
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02")
  // Integration with observables
  implementation("androidx.compose.runtime:runtime-livedata:1.0.0-beta01")
  implementation("androidx.compose.runtime:runtime-rxjava2:1.0.0-beta01")

  debugImplementation(Dependencies.squareLeakCanary)
  androidTestImplementation(Dependencies.androidxOrchestrator)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxRules)
  androidTestImplementation(Dependencies.androidxTestJUnit)
  androidTestImplementation(Dependencies.androidxEspresso)
  androidTestImplementation(Dependencies.androidxUiAutomator)
  testImplementation(Dependencies.junit)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
}

the<com.mapbox.AccessTokenExtension>().file =
  "${project.rootDir}/app/src/main/res/values/developer-config.xml"