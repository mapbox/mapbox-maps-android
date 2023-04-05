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
  compileOnly(Dependencies.lintApi)
  compileOnly(Dependencies.lintChecks)
  compileOnly(Dependencies.kotlin)
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.lint)
  testImplementation(Dependencies.lintTests)
  testImplementation(Dependencies.testUtils)
}
project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/dependency-updates.gradle")
}