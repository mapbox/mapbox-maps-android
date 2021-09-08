project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("android.extensions")
  id("com.mapbox.maps.token")
}

val buildFromSource: String by project

android {
  val androidSdkVersions = project.extra.get("androidSdkVersions") as HashMap<String, String>
  compileSdkVersion(androidSdkVersions["compileSdkVersion"]!!)
  defaultConfig {
    applicationId = "com.mapbox.maps.testapp.style"
    minSdkVersion(androidSdkVersions["minSdkVersion"])
    targetSdkVersion(androidSdkVersions["targetSdkVersion"])
    versionCode = 1
    versionName = "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments = mapOf(
      "clearPackageData" to "true"
    )
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  testOptions {
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }

  if (buildFromSource.toBoolean()) {
    packagingOptions {
      pickFirst("**/libc++_shared.so")
    }
  }
}

androidExtensions {
  isExperimental = true
}

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  implementation(project(":sdk"))
  implementation(dependencies["kotlin"]!!)
  implementation(dependencies["androidxAppCompat"]!!)
  androidTestUtil(dependencies["androidxOrchestrator"]!!)
  androidTestImplementation(dependencies["androidxTestRunner"]!!)
  androidTestImplementation(dependencies["androidxJUnitTestRules"]!!)
  androidTestImplementation(dependencies["androidxRules"]!!)
  androidTestImplementation(dependencies["androidxTestJUnit"]!!)
  androidTestImplementation(dependencies["androidxEspresso"]!!)
  androidTestImplementation(dependencies["androidxUiAutomator"]!!)
  testImplementation(dependencies["junit"]!!)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
}

val localPath:String = org.apache.commons.io.FilenameUtils.getFullPathNoEndSeparator(project.buildscript.sourceFile.toString())
the<com.mapbox.AccessTokenExtension>().file = "${localPath}/src/main/res/values/developer-config.xml"