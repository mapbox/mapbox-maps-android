project.apply(from = "../gradle/versions.gradle.kts")

plugins {
  `java-library`
  id("org.jetbrains.kotlin.jvm")
  id("com.android.lint")
}

lintOptions {
  htmlReport = true
  htmlOutput = file("lint-report.html")
  textReport = true
  isAbsolutePaths = false
  isIgnoreTestSources = true
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  val dependencies = project.extra.get("dependencies") as HashMap<*, *>
  compileOnly(dependencies["lintApi"]!!)
  compileOnly(dependencies["lintChecks"]!!)
  compileOnly(dependencies["kotlin"]!!)
  testImplementation(dependencies["junit"]!!)
  testImplementation(dependencies["lint"]!!)
  testImplementation(dependencies["lintTests"]!!)
  testImplementation(dependencies["testUtils"]!!)
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
}