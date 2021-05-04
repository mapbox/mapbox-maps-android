## [Metalava](https://android.googlesource.com/platform/tools/metalava/) setup

Use `$> make check-api` / `$> make update-api` from the [`Makefile`](https://github.com/mapbox/mapbox-maps-android/blob/main/Makefile) to interact. Make sure to run compile the SDK first so all deps (`jar`s) are available in the `classpath` and no errors are thrown.

:warning: note that we need to update / push `api/current.txt` files after running `$> make update-api`. If there are changes / errors we're 🆗 with (e.g. `AddedMethod` changes are marked as errors but don't break SemVer) 🚀

#### To update `metalava`

```
$> make update-metalava
sh ./scripts/update_metalava.sh
-n Cloning…
 Done
-n Building…
 Done
-e
Dependencies:

com.android.tools.external.org-jetbrains:uast:27.1.0-alpha10
com.android.tools.external.com-intellij:intellij-core:27.1.0-alpha10
com.android.tools.lint:lint-api:27.1.0-alpha10
com.android.tools.lint:lint-checks:27.1.0-alpha10
com.android.tools.lint:lint-gradle:27.1.0-alpha10
com.android.tools.lint:lint:27.1.0-alpha10
org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72
org.jetbrains.kotlin:kotlin-reflect:1.3.72
```

- That places the latest `metalava.jar` into this folder and prints out its deps 👀

```groovy
// Metalava isn't released yet. Check in its jar and explicitly track its transitive deps.
```

- Copy and paste (update) the new deps into [`gradle/metalava-dependencies.gradle`](../gradle/metalava-dependencies.gradle)