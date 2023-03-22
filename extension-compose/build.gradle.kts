plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = AndroidVersions.Compose.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.Compose.minSdkVersion
    targetSdk = AndroidVersions.Compose.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests.apply {
      isIncludeAndroidResources = true
    }
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Versions.compose
  }

  buildFeatures {
    compose = true
  }

  kotlinOptions {
    freeCompilerArgs += "-Xexplicit-api=strict"
  }
}

dependencies {
  dependencies {
    compileOnly(project(":sdk"))
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial)

    implementation(Dependencies.androidxCoreKtx)
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
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
//  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}