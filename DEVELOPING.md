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

```
$ ${ANDROID_HOME}/tools/bin/sdkmanager \
      "platform-tools" \
      "platforms;android-29" \
      "build-tools;30.0.3" \
      "extras;android;m2repository" \
      "patcher;v4" \
      "extras;google;m2repository" \
      "extras;m2repository;com;android;support;constraint;constraint-layout;1.0.2" \
      "ndk;21.4.7075529"\
      "cmake;3.10.2.4988404"
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
$ make build
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

There's a script that installs pre-commit git hook to check `ktlint` on every commit :
[install-pre-commit-hook](https://github.com/mapbox/mapbox-maps-android-internal/blob/main/scripts/hooks/install-pre-commit.sh). 
Note, that if some global pre-commit (eg [secret-shield](https://github.com/mapbox/secret-shield)) 
git hooks are already installed it will probably prevent this new hook from being called. 
In such case global git hooks will need to call local hooks as well manually, e.g. add
```
if [ -e ./.git/hooks/pre-commit ]; then
    ./.git/hooks/pre-commit "$@"
fi
```
to the end of global pre-commit hook.

## Code

### Project overview
The Mapbox Maps SDK for Android is a multi module gradle project with Android Libraries and Android
applications.

#### sdk-base
This module is the shared dependency across modules and contains primarily interfaces.
This module enables us to independently develop other modules against interfaces and not rely
on any concrete implementation. By having this strict decoupling, we makes sure we think well about
the contracts a module has with other and what public API it exposes to consumers.

#### sdk
The sdk module is the main module developers integrate in their project. It contains the most
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

The CircleCI bot will wait for the Firebase test to finish, collect the results,
publish them as build artifact and fail/succeed accordingly.

Instrumentation tests are divided across 3 modules:
 - sdk: contains instrumentation tests that validate C++ integration of the SDK
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

## Release management

The Mapbox Maps SDK for Android makes stable releases every 6 weeks. Beta build is available in week 2 and a release candidate in week 4, which is promoted to stable in week 6.

We release on a schedule to provide a level of predictability for our customers. Having a cadence that the team can rely on gives transparency and provides a rhythm that becomes second nature.

### Why do we provide pre-releases?
We provide pre-releases during development to enable the community and our customers to provide feedback.

### What is the beta release?
The beta release is a pre-release that exposes initial APIs, behaviors of upcoming features for early feedback. We make this release during week 2 of the release cadence.

### What is the RC release?
We finalize the scope of the beta release during week 4 of the cadence. We normally do not introduce any new APIs however bug fixes could land, and the RC is then promoted to stable in week 6.

### What type of versioning does Maps Mobile use?
We use the standard semantic version scheme ([SEMVER](https://semver.org/)) x.y.z to differentiate between major, minor, or patch releases. We treat stable release as a new major or minor release and not patch release.
Where “x” represents the major releases where incompatible API changes occur “breaking changes”.  
“y” represents minor releases where we add functionality in a backward-compatible manner.  
And “z” represents patch releases where we make backward-compatible bug fixes.

### Understanding release management in Git terms
As soon as time has come to prepare new beta release, we cut the new Git branch `v[major].[minor]` from latest `main`. Beta, RC and stable releases are always cut and published from this branch.

Any bug fix / feature _related_ to the `v[major].[minor]` release (when beta has already been released) will always be merged to `main` firstly and then either merged / cherry-picked to the `v[major].[minor]` branch.

All bug fixes / features _not related_ to the `v[major].[minor]` release (when beta has already been released) will be simply merged to `main` and included in the _next_ release.

#### Preparing patch release
If there is the necessity to prepare the patch release for `v[major].[minor]`, we release it from associated `v[major].[minor]` branch with correct `android-v[major].[minor].[patch]` tag and _not_ from separate patch branch.

Preparing patch checklist:
 - cut patch branch from `v[major].[minor]` branch.
 - add patch bug fixes / features, updated the changelog.
 - merge the patch branch to `v[major].[minor]`, delete the patch branch, and start patch release process by creating `android-v[major].[minor].[patch]` tag on `v[major].[minor]` branch.
 - create PR to `main` branch with changelog entry for the patch release.

### Release management visual representation

![mapbox-release-pipeline](https://user-images.githubusercontent.com/15800566/144036106-027e6105-164e-4e91-bb40-a0d7af0fba17.jpeg)

Diagram could be edited and viewed by following [link](https://viewer.diagrams.net/?tags=%7B%7D&highlight=0000ff&edit=_blank&layers=1&nav=1#R7V1tc6M4Ev41qbr7MJTeJT5OMpm5l92tqZur2tn9ckVsYnPrmCwmM8n9%2BpNssA0NtoxBYA%2FzYRIULIO69XTr6Vbrht49vX5Kguf5z%2FE0XNwQNH29oR9uCCE%2BxfqHaXnbtGBB%2FU3LLImmWduu4Uv0vzBrRFnrSzQNV4Ub0zhepNFzsXESL5fhJC20BUkSfy%2Fe9hgvit%2F6HMxC0PBlEixg66%2FRNJ1vWhWRu%2Fa%2FhdFsnn8zFtn7PQX5zdmbrObBNP6%2B10Tvb%2BhdEsfp5ren17twYUYvH5fN5z7W%2FHX7YEm4TG0%2B8PeXP9ksYL8j%2BYti%2FO0fU%2F%2B9eJf1skrf8hcOp%2Fr9s8s4SefxLF4Gi%2Ftd6%2B3kJfkWmk6xvkjil%2BV0fYX01Tx9WmR%2FCJfT92b89eUyXoablo%2FRYpHd%2Bt8wTd8yeQcvaaybdl%2F3Uxw%2FZ%2F2s0iT%2BYzv2TLc8xss0%2ByDm637T5O2r6dZDSOUNv60bGJd5w4fX7Js3V2%2F7V5%2FDJHoK0zDJG1%2Bj9Gv%2BIvr3TWc8u9r1ZC7e9i7K%2FUAZ5QMevyST8IBgFMmUPUhmYXrgRsk3Nxq57X1FpgOfwlg%2FT%2FKmb0jCRZBG34p6HWTTY7a9b%2FvRz3GkH5qgbC4zjD2VaX02mTkq9bJ5q%2ByD%2B6pY6oszXu5LEVXsa%2FPioC%2F9y95b7ZrWyn6C4mfD%2By1YvGQj0mAm1Cp%2Fc%2BU%2BNm1WelzS0h3na9lR5cmVAPkeJoeV4LgqUSFaUyVGj%2FfVsSpRoEpPQbQE6pSGr2lRSzbSv4sXcbIT5qOWdakpWESzpb6caPkaaLn9FiZppG3U%2B%2BwPT9F0ulbI7%2FMoDb88B2uhf9cm2SievvtxsdaXub4vXJYgdAup2aPi7Ppj8BQtzJDeaVFE%2BnsJ%2BiX8fkjdzGOFrzZ6RAiQGlOZnfy%2BM7M8a5rvWVhG6tWtINlTxci6M4UHrQmwPT0YzqLR3P7tFJPZLjDlVu2o%2BcPUjfkThHi0ZLIEcoozHODMN%2B%2Br95tuekiC5WTeAeIswsf0SvFGaYESDiFHQsjhfkeQI872OSpnfgVCFBzzbNbjFmb9cXelDEIFyMyRMfef3fvZzBZorF2lM4GGE%2BWJoovFSAloapyjtoBGtmsJm%2BhoS%2BuvVi3c6bp%2BENbMH3PYvSEUId9HB3W5c83jvKx51OfN3HIhydG%2BOjaXCpjLX8PwD90CV3pDds1bsH4MA%2BtHJAOmT1Z422Xkac30%2BUA6nyKjP7drVyZazvTvPwfPK%2F3jy4d%2FDkZiRWTbkxWlVVPa%2FGtHhr72YEorb1y1YqKqYsnUlRBzkrZNFqWBtYDrmlOBui170%2FIiCLkBfoItCJkatIbIzxuTO20hP8ZALf8dzG7oe%2FMIy2kSR1OzcHr3EKaBBxnwK7cGlBvNLiyFlA%2BAhKCKhVBn1iBHtp7p2OdFEC3fPayfoQGKFNVk3wC064deTnAidwFt%2BWWOIb%2BMT%2BdqkCpzNY3hjGs4KzmyzuEMEsxgcuRA81PwEC4%2Bx6sojWIDOA9xmsZPFUiUGqWHGFdQ53nwbLp%2Fep2Z4K5nsGky1wbOi5fvTODzP0n4GGrtMbpSnDv6A9MoLDhD9%2B%2FFrRBVs%2BRWccZRCVF1%2B0d1d3931w7oSYwAAcQg%2B0MrXGDaGeh1yDhfJoc8AGRjlsjminfmEizKJRdW%2BGPjmknmNuyK%2BajzQ9N5V6osLUIotqoslew7HIPFCauMH45zkoQBg5uP2J7BxUx5eazPzUpDWqw0AJJMg9V8i0B7IjPtn4NUi2G5biGIAgQhALT2Epb4PoLgI%2FhRK5bjwQvLGX4k9p63nZtKxEEqEUaqIRJUdEaRY6MGqeYGVq7lZCJiYRILat1yZhGzVLkcTSAFRdABEKhhspQPvSXmsYbJRVVPpftzq1yQKc%2FiGHD5cOU2RVFeZgd6j2MQSIFn4oHB%2FSsXD6Zol1IxGPlALti5vT8JWQdio5VkHse%2BjzGiBBFVYoxZw7iwkoCHIb7jzF8brvnSPMABaU1rnp3prGTMlXSbQ0Ag9Vq5xksmP14cSWG5Xbrl4vElQHuzwsuNthvEH2lV1xRT18yRUbW2mCPFwRLBNXNEYCbv6JNYqAGmjHlECr0qI9hX%2BU7CrU%2FCvIZeie4YuCW0vPrsWikgnTgqha1SAJ%2BjaYhk3RtDJjtccolxTllu%2FQ%2BgYzVqoUcxeNu77dncsDr1NVRhS6f%2BZdNtu4oHGdEqH%2BdHc28wUQgYnSoGmxC3DHa%2BUXL0b4YTQssn7lEintoy8Weiok9B2Fio0jrbGhMR3GMpy5Rq19bRH5XesdIPRpcVJPUbe%2F%2BGzeo7cJwnFI26PBwAd6XLGAkC4uKNlRkT7knqb%2F%2BpfvXaMu36RyTLMJK%2BRwXa%2FsPHHUsmPI4dOpa0YsH54%2BBS7%2BZUuoIgzE9CDfulM%2FGBaeW%2B5Xq5NRC6oGTp55fkeT2JyunS2Ryp309QypT%2BwO%2FVh7YStxSg9ZmC6OQ0U5qy40Id95k1dn5y5%2Fq4x%2B8oUxRzGEdsviGNAh7H9Q4OakH4j6B0yH3CGChE%2F6hkw9iPqNR4SUZtUclRBbxWUUn6uO99ZdQiFXooqNTJvrJplIST7IX0WJsHbGetR0BKcv9gZZGjfNXCbkewHKxw%2Bt5FSJsw4y0UkWql7lN79X08RFhxxU%2FVtuEULrK5wWJiYG60RCAIZFtKCvYFiwMxxwV98jImexD2GAbpSxLedFgC76qLblKJAXoJURnP7q7uJiSDf9AqeO80ivkFRGRStMdAtrf52V0tPBh4bkpPag8MOO%2FlJ%2BoawHpZHw5R09HNeXa%2FuY5bc13WG9jOLSxrUazEuq46gjruOFGDQf59HqeP0etooxvaaJmz333a6A4IePc7XGvlY7tJlVIQvGe%2B8BjaBdPyHRAn7Fn1kSdwXTiO%2BtxjGMSNT9%2FJKmnTgpynprKuXwh8VdUo1T0tFSf20E1mLBtp%2B%2FPQjBNYUbaiaKVTvoRZsPajUA8Itaq0b%2B9ChVT26HWc5XUIAUXq3OuoLeEMU%2BCHLM4WJISlAEcm9b63nsHSFNArHLcswUCiQhV7fcpFa6zzrkxvTNU6kI73sXHI0n776v3FHFNyQ7RoEP5rB5P3Wg4qwQJxz68VptYSOOd931MVVb87m%2FZ5UqrltJ8sgtUqmhzyj46ObN1ZCU3iSLVSOsod5ao9FBgx8R0AI3lV0ZNhpMLCwN66Bg9SoVpiYab26jlYFnRM%2FPlijs68NeDxLsMBk2qe2fHt3%2FVvM%2FPTaNJ6zMNgtR8xQpE5ZGCShEGqrUr2bQ9J%2FrG8Rb%2FP5hHy5ovAMFLCMFQ1mdo7pWADX%2F6OriAlfao4cQmr3SLRyakFfEwRPtcZxSB5qfcEF94BRTlm4%2B2GN%2FP1j8ZNuKPkBlOa%2FWgGnW02HsZw973rdDzecmnkywsVNnIBq9S8SZix%2BdSQQ5saWPrMEzBosPPqaEMfUXDadxCdNzk0cXC71HKM95AkBSVlAh9U0yNZcCdVk2s5%2F82%2BCIJ0Mw98Vj7phjbNHvGZBB6PdFu3kFuka19O5qfJdWK0PdVvw2K5UH1HJkCJivMXZTPdVwhEY5njmp38aitsu2WwlC2DlTvZDEYdyjy1zflyiHt5Wcb9ncGY6Z9y838z3ayq0K3K5zV3rZwWYZCRTzjEJ%2Bil1uD4BAHjGH2Y22vlE6jtudv57Op%2B0USAi3fRfIKwCNOMsHQIlgg8e6t%2FWCKXI9Wh7uMzhwEOTq5j3nTLGUy8IinNdQaTGPOm17OGgmWERNIjfv2w16UWgyxmgQTo6JzVg0ClpUhrWdGHHr0%2BE9riU91kP4sLyn4eqqXBPkzR4D7p2dSMGdBnSlVBPOvfgRgGPXyt61VuW40mn139xL%2BRt7dFxpKkrVgKc1zYksRO%2BZaul7VXUf689foX%2BUUv4Q9hW%2FlCuCrVJDn1ENvpKC06n0wWNLih%2B1hl3YXjs0fFVRRGv7LpYMttSttwzNkbDhR0Q8vdWC%2BaUIVPS9xqvbQg54fiwg53beKrwXmx0oK0Hr3Y7g21K2RqN4tTwS0MrqMusom%2BjvZ4GPZY2fKa59pjnxtF3SVoFt1TQRueY0cQBpDuOj9T2h2Z6v0O5smQ4xkt2FtzijJIVVE%2BsLfODxSTVxGkqEyuaog%2FtdI%2BjiC9JFhhyqFqlbOg7EFEnpqt1TWkWEQiknn89PCyqpni%2B5jyR5hO5pmgS%2B71Lb%2BnH%2B8rTWQLAMAo2Z6EnI8rVdu5vgcBjNaEKcuHabSHARYxgQsYYqnQdi%2F04IbYgqC%2FgCGuKsU%2FnDG2yBO%2BhDGmBIR9zRhX7J7tYYwt0l0vYYylIge2Kvs%2BGYZKKwuq6wKGW3s1h5Y8g9FuZUFAjczi6cxi73Xy85rFFyzYigXwfmZDfS5ECzKlDGRK9U4WK4tCDs4Xrsxi4Vorj%2FYqb28z71B5zcF8Wi%2BPmiWhkk15VZjRxkFf4InaSrmrf%2BxOM%2BeUBaEycKjp3YZwSIxIBhN63eKNBQ9hX%2B%2BuXMcOsl8ZeWW2ysrCxm7kUUqbcOjbwgimxliB3Kcij1q1UIV9IAWyiPbhleKSFdM8cnXi1COqYdE9zsACzfRXziTpmBhT18HaKA7PZB8Ko6AsWJsRy4%2FI1xdlJ0SIvpcDHewo%2F3ESDTpPC6jYmGFdjLAiL4CIAydem3o4ZfyosQMne6AILpu271EfgDn%2BoW4cV%2F%2BC0qoGuTOgYhsaRn1DnT9SWp0IFve9Hsk3El24%2B7fNbMgHdtvQq%2BfnQ3YpCWcviyDRjZP46SlKwWgPsggs6WVDrgKV4jDi23pchXwXCuVaTl5oT6qQm9nusx6F2kComPD%2BhQqJmV2d5zSYjSI9SaRaqFWLb8cyteA2OiDb2shUdcSyHQ8W2NZZdEXHUU9gLnkeAi7WZhQKeYI15uOYR8v9Ya%2BcSNsxH%2Bfb7MFtT2dbWvnXKph12cOBKBim5sBro2FAsXhToreyRITCbtXKhqu6wAM3BqI2FZU5FFee9JtpDIOHsLquSJiHIbo4RkP%2FDJ6MN7N8WJkf%2FxoP1OjwQA29FPbQ3r9iJW%2BV55R2c5yGvkxiI%2Fydbhoi5%2Bd4Gpo7%2Fg8%3D).

## Working with snapshots

The Mapbox Maps SDK for Android publishes snapshot releases to our API downloads infrastructure. These
snapshots can be used in downstream project to verify bug fixes or preview new features. However, we
do not recommend using snapshot releases in production environment.

### Automatic snapshot releases

By default, each commit to the main branch of the repository will trigger a snapshot release for the
sdk, as well as all the plugins/extensions/modules, with an unique version name.

The name of the snapshot is defined in the `VERSION_NAME` in the project's `gradle.properties` file,
with the commit hash inserted before the `-SNAPSHOT`.

An example of snapshot for the sdk is `com.mapbox.maps:android:10.0.0-a1d95e7a-SNAPSHOT`.

### Manually triggered snapshot releases

The Mapbox Maps SDK for Android also features manually triggered snapshot releases from a developing
branch.

If a last commit in a developing branch has `publish_android_snapshot` in a title or description,
the `build-sdk-release` CI bot will create a snapshot release.

The name of the snapshot release would be:

${LAST_RELEASE_VERSION_BEFORE_THIS_COMMIT}-${BRANCH_NAME}-SNAPSHOT for example:

`10.0.0-rc.1-peng-commit-message-based-snapshot-SNAPSHOT`

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