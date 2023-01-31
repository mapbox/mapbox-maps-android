import org.jetbrains.dokka.gradle.DokkaTask

plugins {
  id("com.android.library")
  kotlin("android")
  id("org.jetbrains.dokka")
  id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
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

tasks.withType<DokkaTask>().configureEach {
  dokkaSourceSets {
    configureEach {
      reportUndocumented.set(true)
      failOnWarning.set(true)
    }
  }
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