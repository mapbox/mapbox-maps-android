# Mapbox Maps Compose Extension - Developer Guide

This directory is a **standalone Gradle project** for developing and testing the Compose extension.
It is built independently of the root `mapbox-maps-android` project.

For user-facing documentation and integration instructions, see
[extension-compose/README.md](extension-compose/README.md).

## Modules

| Module | Purpose |
|---|---|
| `extension-compose` | Published library (`com.mapbox.extension:maps-compose`) |
| `compose-app` | demo application |

## Opening in Android Studio

Import the `compose/` folder as the Android Studio project root.

## Prerequisites

Same as the main SDK:
- JDK 17 (see `../.java-version`)
- `SDK_REGISTRY_TOKEN` environment variable: a Mapbox access token with `DOWNLOADS:READ` scope

## Building

### How `maps-sdk` is resolved

`com.mapbox.maps:android` is always resolved as a Maven artifact at the version pinned by
`VERSION_NAME` in `gradle.properties`. When iterating on `maps-sdk` itself (or when no matching
snapshot is published), publish your local build to Maven Local first — `mavenLocal()` is the
first repository, so it wins over the remote registries:

```
$ cd .. && ./gradlew publishToMavenLocal
```

### Assemble the test app

```
$ ./gradlew :compose-app:assembleDebug
```

### Build with the Vulkan renderer

Publish the Vulkan-flavoured `maps-sdk` to Maven Local, then build the app:

```
$ cd .. && ./gradlew publishToMavenLocal -Pmapbox.abis=arm64-v8a -PvulkanEnabled=true
$ cd compose && ./gradlew :compose-app:assembleDebug -Pmapbox.abis=arm64-v8a
```

## Make targets

All targets are run from the `compose/` directory.

```
make check        # ktlint + lint
make fix          # ktlintFormat (auto-reformat)
make test         # unit tests
make check-api    # validate public API snapshot
make update-api   # regenerate API snapshot after intentional API changes
make clean        # clean build outputs
```

## Public API tracking

`extension-compose` uses [binary-compatibility-validator](https://github.com/Kotlin/binary-compatibility-validator)
to track public API changes. The snapshot lives in `extension-compose/api/`.

- No public API change: no action needed.
- Public API changed: run `make update-api`, review the diff in `extension-compose/api/`, and commit it with your code change.

## Running instrumentation tests

```
$ ./gradlew :extension-compose:connectedAndroidTest
```

Requires a connected device or running emulator.

## Versioning

The version is defined by `VERSION_NAME` in `gradle.properties`. It follows the same versioning as
the parent SDK but is released independently as `com.mapbox.extension:maps-compose`.
