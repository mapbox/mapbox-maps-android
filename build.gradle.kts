buildscript {
  repositories {
    google()
    jcenter() // Remove, see mapbox-maps-android#92
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      credentials {
        username = "mapbox"
        password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
      }
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
  }
  dependencies {
    classpath(Plugins.android)
    classpath(Plugins.kotlin)
    classpath(Plugins.jacoco)
    classpath(Plugins.license)
    classpath(Plugins.androidPublish)
    classpath(Plugins.mapboxAccessToken)
    classpath(Plugins.mapboxSdkRegistry)
  }
}

allprojects {
  repositories {
    google()
    jcenter() // Remove, see mapbox-maps-android#92
    mavenCentral()
    maven {
      url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
      credentials {
        username = "mapbox"
        password = System.getenv("SDK_REGISTRY_TOKEN") ?: project.property("SDK_REGISTRY_TOKEN") as String
      }
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
}

plugins {
  id("org.jetbrains.dokka") version Versions.pluginDokka
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
  outputDirectory.set(buildDir.resolve(this.name))
}