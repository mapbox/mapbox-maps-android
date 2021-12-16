plugins {
  id("com.android.application")
  kotlin("android")
  id("com.mapbox.maps.token")
}

val buildFromSource: String by project

android {
  compileSdkVersion(AndroidVersions.compileSdkVersion)
  defaultConfig {
    applicationId = "com.mapbox.maps.testapp"
    minSdkVersion(AndroidVersions.minSdkVersion)
    targetSdkVersion(AndroidVersions.targetSdkVersion)
    versionCode = 1
    versionName = "0.1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments = mapOf(
      "clearPackageData" to "true"
    )
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      signingConfig = signingConfigs.getByName("debug")
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }

  buildFeatures {
    viewBinding = true
  }

  testOptions {
    animationsDisabled = true
    if (!project.hasProperty("android.injected.invoked.from.ide")) {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
  }
  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
    }
  }
  dexOptions {
    javaMaxHeapSize = "4g"
  }

  if (buildFromSource.toBoolean()) {
    packagingOptions {
      pickFirst("**/libc++_shared.so")
      pickFirst("**/libmapbox-common.so")
    }
  }

  if (buildFromSource.toBoolean()) {
    project.afterEvaluate {
      getTasksByName("packageDebug", false).iterator().next().doFirst {
        // def platforms = ["arm64-v8a", "armeabi-v7a", "x86", "x86_64"]
        val platforms = listOf("arm64-v8a")

        platforms.forEach { platform ->
          print("injecting unstripped symbol binaries for maps $platform\n")
          delete(
            fileTree("build/intermediates/stripped_native_libs/debug/out/lib/$platform") {
              include("**/libmapbox-maps.so")
            }
          )
          copy {
            from("../../mapbox-gl-native-internal/internal/platform/android/sdk/build/intermediates/cmake/debug/obj/$platform")
            into("build/intermediates/stripped_native_libs/debug/out/lib/$platform")
            include("*.so")
          }

          print("injecting unstripped symbol binaries for common $platform\n")
          delete(
            fileTree("build/intermediates/stripped_native_libs/debug/out/lib/$platform") {
              include("**/libmapbox-common.so")
            }
          )
          copy {
            from("../../mapbox-gl-native-internal/internal/vendor/common/platform/android/common/build/intermediates/cmake/debug/obj/$platform")
            into("build/intermediates/stripped_native_libs/debug/out/lib/$platform")
            include("*.so")
          }
        }
      }
    }
  }
}

dependencies {
  implementation(project(":sdk"))
  implementation(Dependencies.kotlin)
  implementation(Dependencies.mapboxJavaTurf)
  implementation(Dependencies.mapboxJavaGeoJSON)
  implementation(Dependencies.mapboxServices)
  implementation(Dependencies.coroutines)
  implementation(Dependencies.androidxAppCompat)
  implementation(Dependencies.androidxCoreKtx)
  implementation(Dependencies.androidxRecyclerView)
  implementation(Dependencies.androidxConstraintLayout)
  implementation(Dependencies.androidxMultidex)
  implementation(Dependencies.googleMaterialDesign)
  implementation(Dependencies.squareRetrofit)
  implementation(Dependencies.androidxFragmentTest)
  implementation(Dependencies.squareRetrofitGsonConverter)

  // By default, the Maps SDK uses the Android Location Provider to obtain raw location updates.
  // And with Android 11, the raw location updates might suffer from precision issue.

  // The Maps SDK also comes pre-compiled with support for the [Google's Fused Location Provider](https://developers.google.com/location-context/fused-location-provider)
  // if that dependency is available. This means, that if your target devices support Google Play
  // Services, [we recommend adding the Google Play Location Services dependency to your project](https://developers.google.com/android/guides/setup).
  implementation(Dependencies.googlePlayServicesLocation)

  debugImplementation(Dependencies.squareLeakCanary)
  androidTestUtil(Dependencies.androidxOrchestrator)
  androidTestImplementation(Dependencies.androidxTestRunner)
  androidTestImplementation(Dependencies.androidxJUnitTestRules)
  androidTestImplementation(Dependencies.androidxRules)
  androidTestImplementation(Dependencies.androidxTestJUnit)
  androidTestImplementation(Dependencies.androidxEspresso)
  androidTestImplementation(Dependencies.androidxUiAutomator)
  testImplementation(Dependencies.junit)
}

configurations.all {
  resolutionStrategy {
    force("com.mapbox.common:common:20.0.0-worker-thread-priority-SNAPSHOT")
    force("com.mapbox.maps:android-core:10.0.2")
  }
}

project.apply {
  from("$rootDir/gradle/ktlint.gradle")
  from("$rootDir/gradle/lint.gradle")
}

val localPath: String =
  org.apache.commons.io.FilenameUtils.getFullPathNoEndSeparator(project.buildscript.sourceFile.toString())
the<com.mapbox.AccessTokenExtension>().file =
  "${localPath}/src/main/res/values/developer-config.xml"