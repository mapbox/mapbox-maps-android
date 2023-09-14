import com.google.devtools.ksp.gradle.KspTaskJvm
import java.util.Locale

plugins {
  id("com.mapbox.gradle.library")
  id("com.jaredsburrows.license")
  //id("com.mapbox.maps.token") #mapbox-android-gradle-plugins/issues/29
  id("kotlin-parcelize")
  id("com.google.devtools.ksp").version("${libs.versions.kotlin.get()}-${libs.versions.ksp.get()}")
}

val VERSION_NAME: String by project

android {
  compileSdk = libs.versions.androidCompileSdkVersion.get().toInt()
  namespace = "com.mapbox.maps.base"
  defaultConfig {
    minSdk = libs.versions.androidMinSdkVersion.get().toInt()
    targetSdk = libs.versions.androidTargetSdkVersion.get().toInt()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "MAPBOX_SDK_IDENTIFIER", String.format("\"%s\"", "mapbox-maps-android"))
    buildConfigField("String", "MAPBOX_SDK_VERSION", String.format("\"%s\"", VERSION_NAME))
    buildConfigField("String", "MAPBOX_VERSION_STRING", String.format("\"Mapbox/%s\"", VERSION_NAME))
  }

  // Add the `ksp` folder to each variant. Regardless if it exists or not.
  sourceSets.all {
    java.srcDirs("src/$name/ksp")
  }

  // moving generated KSP files from build folder to `src/<variant_name>/ksp`
  afterEvaluate {
    tasks.withType(KspTaskJvm::class.java).all {
      val generatedKspFilesFolder = destination
      var lastFolder = generatedKspFilesFolder.toPath().fileName.toString()
      // We handle "debug" and "release" build types as equal so remove them
      buildTypes.names.forEach {
        lastFolder = lastFolder.replaceFirst(it, "", true)
      }
      // If it's blank then it means that it should go to the default "main" source set
      if (lastFolder.isBlank()) {
        lastFolder = "main"
      }
      lastFolder = lastFolder.decapitalize(Locale.US)
      val destinationPath = projectDir.resolve("src/$lastFolder/ksp")
      // dropping first 3 symbols which are `ksp`
      val variantName = name.drop(3)
      val copyTask = tasks.register("moveKsp${variantName}Task", Sync::class.java) {
        val sourcePath = "${generatedKspFilesFolder.absolutePath}${File.separator}kotlin"
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
          generatedKspFilesFolder.resolve("classes").mkdir()
        }
      }
      tasks.findByName("compile${variantName}")!!.dependsOn(copyTask)
    }
  }
}
val buildFromSource: String by project

if (!buildFromSource.toBoolean()) {
  configurations.all {
    resolutionStrategy {
      force(libs.mapbox.coreCommon)
    }
  }
}

dependencies {
  implementation(libs.kotlin)
  implementation(libs.mapbox.base)
  implementation(libs.androidx.annotations)
  api(libs.mapbox.gestures)
  if (buildFromSource.toBoolean()) {
    api(project(":maps-core"))
    api(project(":common"))
  } else {
    if (libs.versions.mapboxGlNative.get().contains("-SNAPSHOT")) {
      api(libs.mapbox.glNativeSnapshot)
    } else {
      api(libs.mapbox.glNative)
    }
    api(libs.mapbox.coreCommon)
  }

  compileOnly(libs.kotlinDataCompatAnnotation)
  ksp(libs.kotlinDataCompatProcessor)

  testImplementation(libs.bundles.base.dependenciesTests)
  detektPlugins(libs.detektFormatting)
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
