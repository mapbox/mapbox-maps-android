dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      val catalog = providers.systemProperty("mapbox.conventionPlugin.catalog")
        .getOrElse("../gradle/libs.versions.toml")
      from(files(rootDir.resolve(catalog)))
    }
  }
}

rootProject.name = "MapboxConventionPlugin"