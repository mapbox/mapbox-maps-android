# Mapbox Convention plugin

This folder contains two Gradle plugins that encapsulates common build logic for our different modules:
- `MapboxLibraryPlugin`: responsible to configure Android library modules
- `MapboxApplicationPlugin`: responsible to configure Android application modules

## Common features
Both plugins `MapboxLibraryPlugin` and `MapboxApplicationPlugin` will create product flavors for `private` and `public`.

They will also apply the Gradle plugins common.

## MapboxLibraryPlugin

In your build Gradle file:
```
plugins {
  id("com.mapbox.gradle.library")
}

mapboxLibrary {
  dokka {
    extraListOfSources = File("upstream-api-doc-list.txt").readLines()
    reportUndocumented = false
  }
}
```

## How to debug

You can debug this plugin by creating a `Remote JVM Debug` run configuration (instructions [here](https://www.jetbrains.com/help/idea/tutorial-remote-debug.html#debugger_rc)).

Then, every time you want to debug, run the desired Gradle task from the command line with the parameter
`-Dorg.gradle.debug=true` (e.g., `./gradlew plugin-annotation:tasks --no-parallel -no-daemon -Dorg.gradle.debug=true`).
Gradle will start but immediately wait for the debugger to attach before proceeding. 
At this point, run the remote JVM debug configuration you created.