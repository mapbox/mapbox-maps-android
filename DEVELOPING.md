# Developing

Welcome to the Mapbox Maps SDK for Android project. This document describes every step
of the development cycle of our project and make sure you have everything you
need to get your pull request (PR) landed. Your contributions are appreciated.

## Building

The buildsystem of Mapbox Maps SDK for Android is [Gradle](https://gradle.org/) but there is a [Make](https://www.gnu.org/software/make/manual/make.html)
file to make some tasks more convenient and discoverable to execute.
Familiarity with `make` is a plus but not a requirement.
This document will assume you are familiar with
[git](https://git-scm.com/) and will not get into details about `git` commands.

When not explicitly specified, commands described are assumed to be executed from
the root of the project tree.

### Setting up the repository

Clone the git repository:

```
$ git clone git@github.com:mapbox/mapbox-maps-android.git && cd mapbox-maps-android
```

### Installing the dependencies

The only requirement is to install the latest version of
[Android Studio](https://developer.android.com/studio). After installed, run the
[`sdkmanager`](https://developer.android.com/studio/command-line/sdkmanager) command line or
through the GUI interface to make sure the extra requirements to build Android are met.

Note: Android Gradle Plugin v7.0+ is used to build the project. That requires JDK 11 or higher installed
and `JAVA_HOME` environment variable pointing on it.

```
$ ${ANDROID_HOME}/tools/bin/sdkmanager \
      "platform-tools" \
      "platforms;android-33" \
      "build-tools;30.0.3" \
      "build-tools;33.0.0" \
      "extras;android;m2repository" \
      "patcher;v4" \
      "extras;google;m2repository" \
      "extras;m2repository;com;android;support;constraint;constraint-layout;1.0.2" \
      "ndk;23.2.8568313"\
      "cmake;3.22.1"
```
Optional : In case of errors related to ndk version, make sure the ndk path is set in Android Studio.
You can set ndk path manually by navigating to `File -> Project Structure -> Android NDK Location`
or follow the official guide [Install and configure NDK](https://developer.android.com/studio/projects/install-ndk)

### Opening the Maps SDK

This repository is a regular Android Studio project. Open Android Studio and import this repository’s root `mapbox-maps-android` folder.

### Configuring the project

Before building, the project needs to be configured. A couple of configurations need to be in place before
a successful build and test run can be made. Either export the following values as environement variables
or update them inline in the source code:

#### SDK Registry token

The SDK_REGISTRY_TOKEN is a Mapbox access token, used during compile time, with a scope set to `DOWNLOADS:READ`.
This token allows to download all required Mapbox dependencies from a Mapbox Maven instance.
The DOWNLOADS:READ scope can be set when creating a new access token on https://account.mapbox.com/.
The token configuration can be found in the root `build.gradle.kts` of the project.

#### Mapbox Access token

The MAPBOX_ACCESS_TOKEN is a mapbox access token, packaged as part of test application, to load Mapbox tiles and resources.
This token can be configured on https://account.mapbox.com/ and doesn't require any specific scopes.
After first compilation, a gitignored file, is generated in `app/src/main/res/values/developer-config.xml`.

### Initial build

To verify if everything has been correctly setup so far, you can run the following make command to compile the project:

```
$ make sdk-build
```

## Checks

Before pushing your code and triggering the CI, it is important that all the
checks are passing. Checks are mostly code formatters and code generators. In
order to run the checks you need to execute the `check` target.

```
$ make check
```

This target encapsulates validating:
 - ktlint
 - lint
 - kdoc validation
 - code generation

As a convenience, the CI will run the checks again and will let you know if the
code is completely formatted and if everything that was supposed to be generated
was generated.

### Secret shield

This project uses [secret-shield](https://github.com/mapbox/secret-shield) to help block secrets such
as access tokens from being exposed.

Install `secret-shield` by entering `npm install -g @mapbox/secret-shield`.
Install the pre-commit hook by running `scripts/install-pre-commit/install-pre-commit.sh`

### Code formatting

We follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/reference/coding-conventions.html) 
and official [Kotlin Style Guide](https://developer.android.com/kotlin/style-guide).

All the projects are configured with `kotlin.code.style=official` in gradle.properties, so you 
may autoformat the code in Android Studio by default.

#### ktlint
We use `ktlint` to validate formatting and there's a check on CI preventing PR from merge 
if `ktlint` fails.

Check if format is correct with `ktlint` or `./gradlew ktlint`.
Run `ktlintFormat` or `./gradlew ktlintFormat` or `make fix` to automatically reformat everything and 
fix all the issues.

### Public API changes tracking
We use [binary-compatibility-validator](https://github.com/Kotlin/binary-compatibility-validator) and
[Metalava](https://android.googlesource.com/platform/tools/metalava/) for tracking binary and source
compatibility of the APIs we ship.

* When doing code changes that do not imply any changes in public API, no additional actions need to
be performed. The bots named `check-api-*` will validate if there's any API breaking changes.
* When doing code changes that imply changes in public API, whether it is a new API or adjustments to
the existing one, `check-api-*` tasks will start to fail. `make update-api` should be executed manually,
the resulting diff in `.api` file should be verified: only signatures you expected to change should be changed.
* Commit the resulting `.api` diff along with code changes.

## Code

### Project overview
The Mapbox Maps SDK for Android is a multi module gradle project with Android Libraries and Android
applications.

#### sdk-base
This module is the shared dependency across modules and contains primarily interfaces.
This module enables us to independently develop other modules against interfaces and not rely
on any concrete implementation. By having this strict decoupling, we makes sure we think well about
the contracts a module has with other and what public API it exposes to consumers.

#### maps-sdk
The maps-sdk module is the main module developers integrate in their project. It contains the most
important building blocks of the product that couldn't have been decoupled from the underlying
c++ implementation at this time.

#### module
If a module is prefixed with the name `module-` it indicates it's a required module and will
be enforced to be available during compilation time. This enforcement is enabled and configured
through the annotation processor of [mapbox-base-android](https://github.com/mapbox/mapbox-base-android).

#### plugin
If a module is prefixed with the name `plugin-` it indicates it's an optional module that will
be loaded automatically at runtime if it's part of the compilation classpath. Plugins can be
excluded with a gradle exclusion to disable a plugin fully and to save binary size. By
default we package the majority of the plugins found in this repository.

#### extension
If a module is prefxied with the name `extension-` it indicates it's a collection of extension
functions that makes it more easy and fluent to integrate with an existing, low-level, API.s The code in those
modules use [Kotlin extension functions](https://kotlinlang.org/docs/reference/extensions.html) to
add APIs to existing classes.

#### app
The app module is the internal test and development application. It hosts a variety of different
example use-cases to test the code from all the other modules. This module can be referenced when
building out other examples.

### Generated code
The project contains a large number of code generated files that should not be touched manually.
These can be recognised with a top file comment as `// This file is generated.`. Code generators
are hosted externally from this repository and generated code requires to be up-to-date when making a
pull request.

## Testing

Mapbox Maps SDK for Android relies on unit, instrumentation, robo and benchmark test to verify our
code correctness. No code is accepted by our CI if any of these tests are failing. When developing
a new feature, you are required to write a test and make sure that the test coverage will not go
down. A feature will be considered tested if an unit test is provided that verifies the
business logic and a instrumentation test is provided that executes the codepath down to the C++
code and back.

When fixing a bug, you are also required to provide a test that fails if the fix
for the bug is not applied. The test in this case, triggers the faulty behavior
and will make sure the bug is not going to be reintroduced and our code regresses.

### Unit tests

Unit tests are the tests that validate the business logic of the SDK. Since unit test on Android
are executed on the JVM of the development machine, we aren't able to load native libraries and
call into our C++ stack. Any of those interactions will need to be either mocked or shadowed. To
achieve this we are using a couple of frameworks as [Mockk](https://mockk.io/) and
[Robolectric](http://robolectric.org/).

Unit tests, for all modules, can be run locally with:

```
$ make unit-tests
```

### Instrumentation tests

Instrumentation tests are integration test that validate the integration with the Android OS and
our C++ code. It allows to render the actual Map on an Android device or emulator. It's important
that features are accompanied by a instrumentation test as C++ issues will only show at runtime.

Instrumentation tests are run on [Firebase TestLab](https://firebase.google.com/docs/test-lab).
The CI push a test application and an application with the tests to a physical device on the
Firebase device farm. This device is fully allocated to our test at the moment the test is running.

The build step that pushes the app to Firebase will give you an URL that can be
used for checking the phone logs in case of a crash or test failure. If you
don't have access to Firebase, please ask a member of the Maps team.

In order to run tests on multiple devices, the last commit in a branch must have
`multiple_devices` message. We also run tests on multiple device automatically
when the release tag is added.

The CircleCI bot will wait for the Firebase test to finish, collect the results,
publish them as build artifact and fail/succeed accordingly.

Instrumentation tests are divided across 3 modules:
 - maps-sdk: contains instrumentation tests that validate C++ integration of the SDK
 - app: contains instrumentation tests that validate specific feature integrations
 - extension-style-app: contains instrumentation tests for validating the style integration

Instrumentation tests can be run locally on a device or emulator with:

```
$ make instrumentation-tests
```

#### Generated Instrumentation tests

Next to hand written tests, we generate a large bulk of our tests. Either these are generated as
part of a code generator which will generate a test specific to a feature or we generate smoke test
that validate integration of a feature within our test application.

Generation of the latter, the sanity smoke tests, can be done with:

```
$ make generate-sanity-test
```

The source code of this generator is found under `scripts/sanity-test` and uses
[ejs templates](https://ejs.co/).

### Robo tests

[Firebase Robo tests](https://firebase.google.com/docs/test-lab/android/robo-ux-test) are
integration tests that involves providing invalid, unexpected, or random data as inputs to the
test application. This allows us to find bugs that arise in edge-cases situations. Robo tests are
executed as part of a CI run and run for 10 minutes.

## Commit messages

Commit messages should read as prose. A commit message should tell a story about
the what, how and why a change is happening. All commit messages should be pre fixed with the
module their are changing, omitting the type of module.

```
[logo] Rename visibility feature to enabled.
This is to align plugin vocabulary across the codebase and the plugin system.
Enabled is now made part of the Plugin interface and removed from the Logo specific interface.
All tests and referneces in the SDK and test applications have been updated to resemble this change.
```

## Changelog

If your change is not trivial, it deserves a changelog entry, so we can tell
our users about it on the next release. Please include a changelog entry
as shown by the PR template. If no changelog needs to be added, please include
`skip changelog` label.

## API documentation

Kotlin API documentation can be generated with [Dokka](https://github.com/Kotlin/dokka) in `build/dokka/htmlCollector` with running

```
$ make dokka-html
```

## Code review

Congratulations, you made a pull request. You most certainly added tests,
a good PR description and commit message and all the bots are happy about your contribution.

The humans working on the project might have a constructive feedback to your
code. You will need the approval of at least one team member of the Maps SDK team to land your
patch. It is a rare occasion, except for trivial changes, that a patch will get approved with no
comments.

Please take a moment to appreciate the reviewer feedback. Reviewing pull
requests is an arduous and time consuming task. You can make the life of the
reviewing easier by making small pull requests. It is easier to land a feature
one small self contained functionality at a time. It is also OK to disagree with
the reviewer or ask for more details on why a change is needed.

Sometimes we will approve with a "nit" or trivial request. One example is, "PR
looks good but please fix that typo or let's use a more descriptive name for
variable foobar". In this case you can fix your PR, push it again and land it
as soon as the bots are green.

A reviewer will always trust the developer. It is the developer responsibility
to test the feature end-to-end and make sure we can ship a state of the art map
rendering engine to our users.

And as always, everyone is invited to review code.

## Making releases

### Versioning

The SDK, the default extensions and the default plugins use the same versioning, which is defined by the
`VERSION_NAME` variable in the root project's gradle.properties. However, the project also allows to use
separate versioning for individual subprojects/modules. Subprojects can define the `MODULE_VERSION_NAME`
in the subproject-level gradle.properties to overwrite the `VERSION_NAME` defined in the root project.

### Publish new releases

The CI is setup to publish new releases when new tag that follows the release tag pattern is created.

* To publish a Maps SDK release with all the default plugins/extensions, create a release git tag `v*`.

## Opening tickets

Everyone is welcome to open issues or make feature requests on the Mapbox Maps SDK for Android
repository. Make sure, in case of an issue, it can be reproduced with
the latest version of Mapbox Maps SDK for Android, otherwise it is very likely it was
already fixed. Search our issue tracker for similar issues, if you found a problem,
maybe someone else also found the same problem and you may contribute to the issue
report. Same goes for feature requests, it might be that someone else already
requested the feature you want.

### Issue checklist

A great bug report will have many of the following:

 - Detailed steps to reproduce the issue.
 - The version of the SDK causing the problem.
 - The hardware in use when the problem was detected (i.e. Samsung Galaxy A71).
 - The version of the operating system (i.e. Android KitKat).
 - The style JSON or URL used as the map style.
 - The camera configuration (coordinates, zoom, bearing and pitch).
 - When adding a GeoJSON source to the map, the URL for the GeoJSON.
 - If customizations such as overlays and custom layers are in use and how.
 - If using runtime styling, a list of changes used to mutate the style.
 - A symbolicated backtrace in case of a crash.
 - Screenshot or video demonstrating the problem.
 - A test reduction, meaning a code example that can reproduce the problem.

## Symbolicating native crashes

The Mapbox Maps SDK for Android uses C++ code under the hood. By default, the used C++ binary
will be stripped from symbols which means that stacktraces produced are not readable. To symbolicate
C++ traces you can use [ndk-stack](https://developer.android.com/ndk/guides/ndk-stack) and a binary that has the symbols attached.

### Download the unstripped binary

Next to Mapbox Maven distribution mechanism, Mapbox Android binaries can be downloaded as a zipped
archive. This zipped version contains the additional shared objects with debug symbols.
Replace in following command the SDK_REGISTRY_TOKEN and VERSION with the appropriate values:

```
$ curl --user mapbox:{SDK_REGISTRY_TOKEN} https://api.mapbox.com/downloads/v2/mobile-maps-android-core/releases/android/{VERSION}/android-core-all.zip --output android-core-all.zip
```

### Running NDK stack

After unzipping the archive you can run ndk-stack on the unstripped native binary and your
stacktrace that was saved as a txt file.

```
$ ndk-stack -sym obj/arm64-v8a -dump trace.txt
```

Note that the stacktrace _must_ be formatted properly, otherwise ndk-stack will not be able to symbolicate
the stacktrace. Different crash reporting tools often format stacktraces differently, so you might need to reformat the stacktrace.
The stacktrace should be formatted as follows:

```
*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***
  #00 pc acfed libmapbox-maps.so
  ...
  #12 pc 51f408 libmapbox-maps.so (BuildId: 331a309c6d4e90a8)
```

e.g. start with asterisks line, followed by stack entries. 
Stack entries should start with at least one space, then a hash sign with a stack entry number, 
then `pc`, then the address (_without_ the `0x` prefix), then the library name.

## Working with snapshots

The Mapbox Maps SDK for Android publishes snapshot releases to our API downloads infrastructure. These
snapshots can be used in downstream project to verify bug fixes or preview new features. However, we
do not recommend using snapshot releases in production environment.

### Automatic snapshot releases

Snapshot releases are automatically published once a day and the main branch points to the latest snapshot.

### Use snapshot releases in your app

Snapshot releases are distributed from a different Downloads API endpoint, you will need to add
this API endpoint to your project's `build.gradle` as follows:

```gradle
allprojects {
  repositories {
    maven {
      url 'https://api.mapbox.com/downloads/v2/snapshots/maven'
      authentication {
          basic(BasicAuthentication)
      }
      credentials {
        // Do not change the username below.
        // This should always be `mapbox` (not your username).
          username = 'mapbox'
          // Use the secret token you stored in gradle.properties as the password
          password = project.properties['MAPBOX_DOWNLOADS_TOKEN'] ?: ""
      }
    }
  }
}
```

Where the password is the secret token and can be configured following the [installation guide](https://docs.mapbox.com/android/beta/maps/guides/install/#configure-credentials)

And then update the Mapbox Maps SDK's version name to the snapshot version in your app's `build.gradle`.

## Working with traces

If you're experiencing performance problems, enabling and analyzing traces is a crucial step for diagnosis. For step-by-step instructions on how to enable tracing and interpret the collected data, please consult the [tracing guide](https://docs.mapbox.com/android/maps/guides/debugging-and-profiling/tracing/).

## Logging

The Maps SDK for Android logs are handled by [MapboxLogger](https://github.com/mapbox/mapbox-maps-android/blob/main/sdk-base/src/main/java/com/mapbox/maps/MapboxLogger.kt), which provides a unified logging interface for all SDK components. It uses `com.mapbox.common.Log` from the Mapbox Common SDK under the hood.

Example usage:
```kotlin
import com.mapbox.maps.logD
// ...
logD(TAG, "Debug message")
```
