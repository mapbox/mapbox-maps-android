dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("$rootDir/../gradle/libs.versions.toml"))
    }
  }
}

rootProject.name = "MapboxConventionPlugin"