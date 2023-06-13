plugins {
  id("java-library")
  id("kotlin")
  id("com.android.lint")
}

lint {
  htmlReport = true
  htmlOutput = file("lint-report.html")
  textReport = true
  absolutePaths = false
  ignoreTestSources = true
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
  compileOnly(libs.lintApi)
  compileOnly(libs.lintChecks)
  compileOnly(libs.kotlin)
  
  testImplementation(libs.junit)
  testImplementation(libs.lint)
  testImplementation(libs.lintTests)
  testImplementation(libs.testUtils)
}
project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}

tasks.withType<Test> {
  maxParallelForks = (Runtime.getRuntime().availableProcessors()).takeIf { it > 0 } ?: 1
}
