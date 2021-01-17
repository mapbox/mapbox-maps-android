buildscript {
  var buildFromSource = false
  project.rootProject.file("local.properties").apply {
    if (exists()) {
      val properties = java.util.Properties()
      properties.load(inputStream())
      if (properties.getProperty("buildFromSource")?.toBoolean() == true) {
        buildFromSource = true
      }
    }
  }
  repositories {
    google()
    jcenter()
    if (buildFromSource) {
      maven {
        url = uri("http://mapbox.bintray.com/mapbox_private")
        credentials {
          username = System.getenv("BINTRAY_USER") ?: project.property("BINTRAY_USER") as String
          password = System.getenv("BINTRAY_API_KEY") ?: project.property("BINTRAY_API_KEY") as String
        }
      }
    }
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
    if (buildFromSource) {
      classpath(Plugins.bintray)
      classpath(Plugins.mapboxBindgen)
      classpath(Plugins.mapboxNative)
    }
  }
}

allprojects {
  repositories {
    google()
    jcenter()
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
      url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
  }
}

plugins {
  id("org.jetbrains.dokka") version Versions.pluginDokka
}
repositories {
  maven(url = "https://dl.bintray.com/kotlin/dokka")
}
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
  outputDirectory.set(buildDir.resolve(this.name))
}