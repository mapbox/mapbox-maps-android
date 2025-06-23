plugins {
  id("com.mapbox.gradle.library")
  kotlin("kapt")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.module.telemetry"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    ndk {
      val abi: String =
        if (System.getenv("ANDROID_ABI") != null) System.getenv("ANDROID_ABI") else ""
      if (abi.isNotBlank() && !project.hasProperty("android.injected.invoked.from.ide")) {
        abiFilters.add(abi)
      }
    }
  }
}

mapboxLibrary {
  publish {
    group = "com.mapbox.module"
    artifactId = "maps-telemetry"
    artifactTitle = "Telemetry for the Mapbox Maps SDK"
    artifactDescription = artifactTitle
    sdkName = "mobile-maps-android-telemetry"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(libs.mapbox.base)
  implementation(libs.coroutines)
  compileOnly(libs.mapbox.annotations)
  kapt(libs.mapbox.annotationsProcessor)
  implementation(libs.kotlin)

  testImplementation(libs.bundles.base.dependenciesTests)
  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  androidTestImplementation(libs.androidx.testJUnit)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}