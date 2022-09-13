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

com.android.tools.external.org-jetbrains:uast:30.0.0-alpha14
com.android.tools.external.com-intellij:kotlin-compiler:30.0.0-alpha14
com.android.tools.external.com-intellij:intellij-core:30.0.0-alpha14
com.android.tools.lint:lint-api:30.0.0-alpha14
com.android.tools.lint:lint-checks:30.0.0-alpha14
com.android.tools.lint:lint-gradle:30.0.0-alpha14
com.android.tools.lint:lint:30.0.0-alpha14
com.android.tools:common:30.0.0-alpha14
com.android.tools:sdk-common:30.0.0-alpha14
com.android.tools:sdklib:30.0.0-alpha14
org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.0
org.jetbrains.kotlin:kotlin-reflect:1.5.0
org.ow2.asm:asm:8.0
org.ow2.asm:asm-tree:8.0

```

- That places the latest `metalava.jar` into this folder and prints out its deps 👀

```groovy
// Metalava isn't released yet. Check in its jar and explicitly track its transitive deps.
```

- Copy and paste (update) the new deps into [`gradle/metalava.gradle`](../gradle/metalava.gradle)