import com.google.devtools.ksp.gradle.KspTaskJvm

plugins {
  id("com.mapbox.gradle.library")
  id("com.jaredsburrows.license")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
  id("kotlin-parcelize")
  id("com.google.devtools.ksp").version("${Versions.pluginKotlin}-${Versions.ksp}")
}

val VERSION_NAME: String by project

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "MAPBOX_SDK_IDENTIFIER", String.format("\"%s\"", "mapbox-maps-android"))
    buildConfigField("String", "MAPBOX_SDK_VERSION", String.format("\"%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_VERSION_STRING", String.format("\"Mapbox/%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_EVENTS_USER_AGENT", String.format("\"mapbox-maps-android/%s\"", VERSION_NAME))
  }

  val privateKspDestinationPath =
    "${project.projectDir.path}/../../mapbox-maps-android-private/sdk-base/src/private/ksp"
  sourceSets {
    getByName("public").java.srcDirs(
      "src/public/ksp"
    )
    getByName("private").java.srcDirs(
      privateKspDestinationPath
    )
  }

  // moving generated KSP files to sources
  afterEvaluate {
    tasks.withType(KspTaskJvm::class.java).all {
      // dropping first 3 symbols which are `ksp`
      val variantName = name.drop(3)
      val destinationPath = if (variantName.startsWith("Public")) {
        "${projectDir.path}${File.separator}src${File.separator}public${File.separator}ksp"
      } else if (variantName.startsWith("Private")) {
        privateKspDestinationPath
      } else {
        throw RuntimeException("Flavour for variant=$variantName is not supported!")
      }
      // using Sync task to clean destination directory first
      val copyTask = tasks.register("moveKsp${variantName}Task", Sync::class.java) {
        val sourcePath = "${destination.get().absolutePath}${File.separator}kotlin"
        // make sure we always execute this task when KSP build folder is not empty
        outputs.upToDateWhen {
          File(sourcePath).exists().not()
        }
        dependsOn(this@all)
        from(sourcePath)
        into(destinationPath)
        doLast {
          delete(sourcePath)
          // metalava expects `getKspClassOutputDir` from
          // https://github.com/google/ksp/blob/main/gradle-plugin/src/main/kotlin/com/google/devtools/ksp/gradle/KspSubplugin.kt#L90
          // to contain bytecode BUT all KSP bytecode is actually located in the same place as other bytecode;
          // so to allow metalava run successfully we simply create an empty dir where metalava expects it
          destination.get().resolve("classes").mkdir()
        }
      }
      tasks.findByName("compile${variantName}")!!.dependsOn(copyTask)
    }
  }
}
val buildFromSource: String by project

dependencies {
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxBase)
  implementation(Dependencies.androidxAnnotations)
  api(Dependencies.mapboxGestures)
  if (buildFromSource.toBoolean()) {
    api(project(":maps-core"))
    api(project(":common"))
  } else {
    api(Dependencies.mapboxGlNative)
    api(Dependencies.mapboxCoreCommon)
  }

  implementation(Dependencies.kotlinDataCompatAnnotation)
  ksp(Dependencies.kotlinDataCompatProcessor)

  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockk)
  testImplementation(Dependencies.androidxTestCore)
  testImplementation(Dependencies.equalsVerifier)
  detektPlugins(Dependencies.detektFormatting)
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