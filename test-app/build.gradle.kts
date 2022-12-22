plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.test_app"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.test_app"
        minSdk = 24
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// gradle task to copy all dependencies to `libs` folder
tasks.register("getDependencies") {
  val runtimeClasspath =
    project.configurations.matching { it.name == "releaseRuntimeClasspath" }
  runtimeClasspath.all {
    this.files.forEach {
      project.copy {
        from(it)
        into("${rootProject.projectDir}/libs")
      }
    }
  }
}

dependencies {
  implementation("com.mapbox.maps:android:10.10.0")
}