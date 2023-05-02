plugins {
  id("com.mapbox.gradle.application")
}

apply {
  from("$rootDir/gradle/script-git-version.gradle")
  from("$rootDir/gradle/play-publisher.gradle")
}
val buildFromSource: String by project

android {
  compileSdk = libs.versions.exampleCompileSdkVersion.get().toInt()
  signingConfigs {
    create("release") {
      storeFile = rootProject.file("$rootDir/testapp-release.keystore")
      storePassword = if (project.hasProperty("APP_KEYSTORE_PASSWORD")) {
        project.property("APP_KEYSTORE_PASSWORD") as String
      } else {
        System.getenv("APP_KEYSTORE_PASSWORD")
      }
      keyAlias = if (project.hasProperty("APP_KEYSTORE_ALIAS")) {
        project.property("APP_KEYSTORE_ALIAS") as String
      } else {
        System.getenv("APP_KEYSTORE_ALIAS")
      }
      keyPassword = if (project.hasProperty("APP_KEY_PASSWORD")) {
        project.property("APP_KEY_PASSWORD") as String
      } else {
        System.getenv("APP_KEY_PASSWORD")
      }
    }
  }
  defaultConfig {
    minSdk = libs.versions.exampleMinSdkVersion.get().toInt()
    targetSdk = libs.versions.exampleTargetSdkVersion.get().toInt()
    applicationId = "com.mapbox.maps.testapp"
    versionCode = if (project.hasProperty("gitVersionCode")) project.property("gitVersionCode") as Int else 1
    versionName = if (project.hasProperty("gitVersionName")) project.property("gitVersionName") as String else "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      signingConfig = if (rootProject.file("$rootDir/testapp-release.keystore").exists()) {
        signingConfigs.getByName("release")
      } else {
        signingConfigs.getByName("debug")
      }
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
    getByName("debug") {
      isMinifyEnabled = false
      signingConfig = signingConfigs.getByName("debug")
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  buildFeatures {
    viewBinding = true
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

  ndkVersion = libs.versions.exampleNdkVersion.get()

  packagingOptions {
    if (buildFromSource.toBoolean()) {
      jniLibs.pickFirsts.add("**/libc++_shared.so")
    }
  }
}

dependencies {
  implementation(project(":sdk"))
  implementation(libs.kotlin)
  implementation(libs.mapbox.javaTurf)
  implementation(libs.mapbox.javaGeoJSON)
  implementation(libs.mapbox.services)
  implementation(libs.coroutines)
  implementation(libs.androidx.appCompat)
  implementation(libs.androidx.coreKtx)
  implementation(libs.androidx.recyclerView)
  implementation(libs.androidx.constraintLayout)
  implementation(libs.androidx.multidex)
  implementation(libs.androidx.lifecycleKtx)
  implementation(libs.googleMaterialDesign)
  implementation(libs.squareRetrofit)
  implementation(libs.androidx.fragmentTest)
  implementation(libs.squareRetrofitGsonConverter)

  // By default, the Maps SDK uses the Android Location Provider to obtain raw location updates.
  // And with Android 11, the raw location updates might suffer from precision issue.

  // The Maps SDK also comes pre-compiled with support for the [Google's Fused Location Provider](https://developers.google.com/location-context/fused-location-provider)
  // if that dependency is available. This means, that if your target devices support Google Play
  // Services, [we recommend adding the Google Play Location Services dependency to your project](https://developers.google.com/android/guides/setup).
  implementation(libs.googlePlayServicesLocation)

  // Maps SDK does not provide this dependency so adding explicitly to make use of
  // async view inflation for view annotation manager example
  implementation(libs.asyncInflater)

  debugImplementation(libs.squareLeakCanary)
  debugImplementation(libs.androidx.testMonitor)
  androidTestUtil(libs.androidx.orchestrator)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.jUnitTestRules)
  androidTestImplementation(libs.androidx.testJUnit)
  androidTestImplementation(libs.androidx.uiAutomator)
  testImplementation(libs.junit)
  detektPlugins(libs.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}