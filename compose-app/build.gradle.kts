plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  id("com.mapbox.maps.token")
  id("io.gitlab.arturbosch.detekt").version(Versions.detekt)
}

val buildFromSource: String by project

android {
  compileSdk = AndroidVersions.Compose.compileSdkVersion
  defaultConfig {
    applicationId = "com.mapbox.maps.testapp.compose"
    minSdk = AndroidVersions.Compose.minSdkVersion
    targetSdk = AndroidVersions.Compose.targetSdkVersion
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
    kotlinCompilerExtensionVersion = Versions.compose
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

androidExtensions {
  isExperimental = true
}

dependencies {
  implementation(project(":sdk"))
  implementation(Dependencies.googleMaterialDesign)
  implementation(Dependencies.composeUi)
  implementation(Dependencies.composeMaterial)
  implementation(Dependencies.composeUiToolingPreview)
  implementation(Dependencies.androidxLifecycleKtx)
  implementation(Dependencies.androidxActivityCompose)
  implementation(Dependencies.androidxAppCompat)
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

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("$rootDir/gradle/detekt.gradle")
}

val localPath:String = org.apache.commons.io.FilenameUtils.getFullPathNoEndSeparator(project.buildscript.sourceFile.toString())
the<com.mapbox.AccessTokenExtension>().file = "${localPath}/src/main/res/values/developer-config.xml"