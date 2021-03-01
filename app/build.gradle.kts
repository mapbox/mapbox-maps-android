plugins {
  id("com.android.application")
  kotlin("android")
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
    testInstrumentationRunnerArguments = mapOf(
      "clearPackageData" to "true"
    )
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
  debugImplementation(Dependencies.squareLeakCanary)
  androidTestUtil(Dependencies.androidxOrchestrator)
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