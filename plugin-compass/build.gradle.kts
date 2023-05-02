plugins {
  id("com.mapbox.gradle.library")
}

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(project(":sdk-base"))
  implementation(libs.bundles.base.dependencies)

  testImplementation(libs.bundles.base.dependenciesTests)
  testImplementation(libs.mockkAgentApi)
  testImplementation(libs.mockkAgentJvm)
  testImplementation(libs.kotlinReflect)
  testImplementation(project(":plugin-animation"))

  androidTestImplementation(libs.bundles.base.dependenciesAndroidTests)
  detektPlugins(libs.detektFormatting)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
  from("${rootDir}/gradle/jacoco.gradle")
  from("$rootDir/gradle/sdk-registry.gradle")
  from("$rootDir/gradle/track-public-apis.gradle")
  from("$rootDir/gradle/pitest.gradle")
  from("$rootDir/gradle/detekt.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

configure<pl.droidsonroids.gradle.pitest.PitestPluginExtension> {
  targetClasses.set(listOf("com.mapbox.maps.plugin.compass**"))
  targetTests.set(listOf("**Compass**"))
}
