plugins {
  id("com.android.application")
  kotlin("android")
  id("com.mapbox.maps.token")
  id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

apply {
  from("$rootDir/gradle/script-git-version.gradle")
  from("$rootDir/gradle/play-publisher.gradle")
}
val buildFromSource: String by project

android {
  compileSdk = AndroidVersions.compileSdkVersion
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
    applicationId = "com.mapbox.maps.testapp"
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
    versionCode = if (project.hasProperty("gitVersionCode")) project.property("gitVersionCode") as Int else 1
    versionName = if (project.hasProperty("gitVersionName")) project.property("gitVersionName") as String else "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments(mapOf("clearPackageData" to "true"))
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

  packagingOptions {
    if (buildFromSource.toBoolean()) {
      jniLibs.pickFirsts.add("**/libc++_shared.so")
    }
  }
}

dependencies {
  implementation(project(":sdk"))
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
  implementation(Dependencies.androidxFragmentTest)
  implementation(Dependencies.squareRetrofitGsonConverter)

  // By default, the Maps SDK uses the Android Location Provider to obtain raw location updates.
  // And with Android 11, the raw location updates might suffer from precision issue.

  // The Maps SDK also comes pre-compiled with support for the [Google's Fused Location Provider](https://developers.google.com/location-context/fused-location-provider)
  // if that dependency is available. This means, that if your target devices support Google Play
  // Services, [we recommend adding the Google Play Location Services dependency to your project](https://developers.google.com/android/guides/setup).
  implementation(Dependencies.googlePlayServicesLocation)

  // Maps SDK does not provide this dependency so adding explicitly to make use of
  // async view inflation for view annotation manager example
  implementation(Dependencies.asyncInflater)

  debugImplementation(Dependencies.squareLeakCanary)
  androidTestUtil(Dependencies.androidxOrchestrator)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxRules)
  androidTestImplementation(Dependencies.androidxTestJUnit)
  androidTestImplementation(Dependencies.androidxEspresso)
  androidTestImplementation(Dependencies.androidxUiAutomator)
  testImplementation(Dependencies.junit)
  detektPlugins(Dependencies.detektFormatting)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

val localPath:String = org.apache.commons.io.FilenameUtils.getFullPathNoEndSeparator(project.buildscript.sourceFile.toString())
the<com.mapbox.AccessTokenExtension>().file = "${localPath}/src/main/res/values/developer-config.xml"