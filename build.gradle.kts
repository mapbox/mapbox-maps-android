buildscript {
  repositories {
    google()
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

  project.apply(from = "gradle/versions.gradle.kts")
  val plugins = project.extra.get("plugins") as HashMap<String, String>

  dependencies {
    classpath(plugins["androidPlugin"]!!)
    classpath(plugins["kotlinPlugin"]!!)
    classpath(plugins["jacocoPlugin"]!!)
    classpath(plugins["licensePlugin"]!!)
    classpath(plugins["mapboxAccessTokenPlugin"]!!)
    classpath(plugins["mapboxSdkRegistryPlugin"]!!)
  }
}

allprojects {
  repositories {
    google()
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
      url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
    }
  }
}

plugins {
  id("org.jetbrains.dokka") version "1.4.10"
}
repositories {
  maven(url = "https://dl.bintray.com/kotlin/dokka")
}
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
  outputDirectory.set(buildDir.resolve(this.name))
}