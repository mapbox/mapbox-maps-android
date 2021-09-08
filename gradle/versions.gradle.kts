// Android
val androidSdkVersions = hashMapOf(
  "compileSdkVersion" to "android-30",
  "minSdkVersion" to "21",
  "minAndroidAutoSdkVersion" to "23",
  "targetSdkVersion" to "30",
  "ndkVersion" to "21.4.7075529"
)

// Plugins
val plugins = hashMapOf(
  "androidPlugin" to "com.android.tools.build:gradle:${Versions.tools}",
  "kotlinPlugin" to "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}",
  "jacocoPlugin" to "com.hiya:jacoco-android:${Versions.jacoco}",
  "licensePlugin" to "com.jaredsburrows:gradle-license-plugin:${Versions.pluginLicense}",
  "mapboxSdkRegistryPlugin" to "com.mapbox.gradle.plugins:sdk-registry:${Versions.mapboxSdkRegistryPlugin}",
  "mapboxAccessTokenPlugin" to "com.mapbox.gradle.plugins:access-token:${Versions.mapboxAccessTokenPlugin}"
)

// Dependencies
val dependencies = hashMapOf(
  "mapboxBase" to "com.mapbox.base:common:${Versions.mapboxBase}",
  "mapboxAnnotations" to "com.mapbox.base:annotations:${Versions.mapboxBase}",
  "mapboxAnnotationsProcessor" to "com.mapbox.base:annotations-processor:${Versions.mapboxBase}",
  "mapboxJavaGeoJSON" to "com.mapbox.mapboxsdk:mapbox-sdk-geojson:${Versions.mapboxJavaServices}",
  "mapboxServices" to "com.mapbox.mapboxsdk:mapbox-sdk-services:${Versions.mapboxJavaServices}",
  "mapboxGlNative" to "com.mapbox.maps:android-core:${Versions.mapboxGlNative}",
  "mapboxCoreCommon" to "com.mapbox.common:common:${Versions.mapboxCommon}",
  "mapboxOkHttp" to "com.mapbox.common:okhttp:${Versions.mapboxCommon}",
  "mapboxAndroidCore" to "com.mapbox.mapboxsdk:mapbox-android-core:${Versions.mapboxAndroidCore}",
  "mapboxAndroidTelemetry" to "com.mapbox.mapboxsdk:mapbox-android-telemetry:${Versions.mapboxAndroidTelemetry}",
  "mapboxJavaTurf" to "com.mapbox.mapboxsdk:mapbox-sdk-turf:${Versions.mapboxJavaServices}",
  "mapboxGestures" to "com.mapbox.mapboxsdk:mapbox-android-gestures:${Versions.mapboxGestures}",
  "androidxAppCompat" to "androidx.appcompat:appcompat:${Versions.androidxAppcompat}",
  "androidxRecyclerView" to "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}",
  "androidxCoreKtx" to "androidx.core:core-ktx:${Versions.androidxCore}",
  "androidxAnnotations" to "androidx.annotation:annotation:${Versions.androidxAnnotation}",
  "androidxInterpolators" to "androidx.interpolator:interpolator:${Versions.androidxInterpolator}",
  "androidxConstraintLayout" to "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}",
  "androidxEspresso" to "androidx.test.espresso:espresso-core:${Versions.androidxEspresso}",
  "androidxTestJUnit" to "androidx.test.ext:junit:${Versions.androidxJUnit}",
  "androidxRules" to "androidx.test:rules:${Versions.androidxTest}",
  "androidxJUnitTestRules" to "androidx.test:rules:${Versions.androidxTest}",
  "androidxTestRunner" to "androidx.test:runner:${Versions.androidxTest}",
  "androidxTestCore" to "androidx.test:core:${Versions.androidxTest}",
  "androidxUiAutomator" to "androidx.test.uiautomator:uiautomator:${Versions.androidxUiAutomator}",
  "androidxFragmentTest" to "androidx.fragment:fragment-testing:${Versions.androidxCore}",
  "androidxOrchestrator" to "androidx.test:orchestrator:${Versions.androidxTest}",
  "androidxMultidex" to "androidx.multidex:multidex:${Versions.androidxMultidex}",
  "androidxLifecycle" to "androidx.lifecycle:lifecycle-runtime:${Versions.androidxLifecycle}",
  "googleMaterialDesign" to "com.google.android.material:material:${Versions.materialDesign}",
  "googlePlayServicesLocation" to "com.google.android.gms:play-services-location:${Versions.googlePlayServicesLocation}",
  "googleCarAppLibrary" to "androidx.car.app:app:${Versions.googleCarAppLibrary}",
  "squareLeakCanary" to "com.squareup.leakcanary:leakcanary-android:${Versions.squareLeakCanary}",
  "squareRetrofit" to "com.squareup.retrofit2:retrofit:${Versions.squareRetrofit}",
  "squareRetrofitGsonConverter" to "com.squareup.retrofit2:converter-gson:${Versions.squareRetrofit}",
  "kotlin" to "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
  "coroutines" to "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}",
  "junit" to "junit:junit:${Versions.junit}",
  "mockk" to "io.mockk:mockk:${Versions.mockk}",
  "robolectric" to "org.robolectric:robolectric:${Versions.robolectric}",
  "robolectricEgl" to "org.khronos:opengl-api:${Versions.robolectricEgl}",
  "lintApi" to "com.android.tools.lint:lint-api:${Versions.lint}",
  "lintChecks" to "com.android.tools.lint:lint-checks:${Versions.lint}",
  "lint" to "com.android.tools.lint:lint:${Versions.lint}",
  "lintTests" to "com.android.tools.lint:lint-tests:${Versions.lint}",
  "testUtils" to "com.android.tools:testutils:${Versions.lint}",
  "hamcrest" to "org.hamcrest:hamcrest:${Versions.hamcrest}"
)

private object Versions {
  const val tools = "4.0.1"
  const val kotlin = "1.4.10"
  const val jacoco = "0.2"
  const val mapboxSdkRegistryPlugin = "0.4.0"
  const val mapboxAccessTokenPlugin = "0.2.1"
  const val pluginLicense = "0.8.5"
  const val mapboxGestures = "0.7.0"
  const val mapboxJavaServices = "5.4.1"
  const val mapboxBase = "0.5.0"
  const val mapboxGlNative = "10.0.0-rc.8"
  const val mapboxCommon = "18.0.0"
  const val mapboxAndroidCore = "5.0.0"
  const val mapboxAndroidTelemetry = "8.1.0"
  const val androidxCore = "1.3.1"
  const val androidxAnnotation = "1.1.0"
  const val androidxAppcompat = "1.3.0"
  const val androidxTest = "1.3.0"
  const val androidxConstraintLayout = "2.0.0"
  const val androidxEspresso = "3.3.0"
  const val androidxJUnit = "1.1.2"
  const val androidxUiAutomator = "2.2.0"
  const val androidxRecyclerView = "1.1.0"
  const val androidxInterpolator="1.0.0"
  const val androidxMultidex = "2.0.1"
  const val androidxLifecycle = "2.3.0"
  const val squareRetrofit="2.9.0"
  const val squareLeakCanary = "2.4"
  const val materialDesign = "1.2.0"
  const val googlePlayServicesLocation = "18.0.0"
  const val googleCarAppLibrary= "1.1.0-alpha01"
  const val kotlinCoroutines = "1.3.9"
  const val junit = "4.12"
  const val mockk = "1.9.3"
  const val robolectric = "4.6.1"
  const val robolectricEgl = "gl1.1-android-2.1_r1"
  const val lint = "27.2.2"
  const val hamcrest = "2.1"
}

project.extra.set("androidSdkVersions", androidSdkVersions)
project.extra.set("plugins", plugins)
project.extra.set("dependencies", dependencies)