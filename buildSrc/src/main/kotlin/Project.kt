object AndroidVersions {
  const val minSdkVersion = 21
  const val targetSdkVersion = 33
  const val compileSdkVersion = 33
  object AndroidAuto {
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val compileSdkVersion = 33
  }
  object Compose {
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val compileSdkVersion = 33
  }
  object ExampleApp {
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val compileSdkVersion = 33
    // Enforce app module to use ndk-r21e as our CI and native dependencies uses the 21e version
    // If not specified, AGP 7.4.0 defaults to 23.1.7779620, which our CI didn't install.
    // Long term, we should bump the ndk version and align with common and gl-native.
    const val ndkVersion = "21.4.7075529"
  }
}

object Plugins {
  const val android = "com.android.tools.build:gradle:${Versions.pluginAndroidGradle}"
  const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.pluginKotlin}"
  const val jacoco = "com.hiya:jacoco-android:${Versions.pluginJacoco}"
  const val license = "com.jaredsburrows:gradle-license-plugin:${Versions.pluginLicense}"
  const val mapboxAccessToken = "com.mapbox.gradle.plugins:access-token:${Versions.mapboxAccessToken}"
  const val mapboxSdkRegistry = "com.mapbox.gradle.plugins:sdk-registry:${Versions.mapboxSdkRegistry}"
  const val mapboxSdkVersionsPlugin = "com.mapbox.mapboxsdk:mapbox-android-sdk-versions:${Versions.mapboxSdkVersionsPlugin}"
  const val dokkaId = "org.jetbrains.dokka"
  const val binaryCompatibilityValidatorId = "org.jetbrains.kotlinx.binary-compatibility-validator"
  const val taskTreeId = "com.dorongold.task-tree"
  const val pitestPlugin = "pl.droidsonroids.gradle:gradle-pitest-plugin:${Versions.pitest}"
  const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
  const val playPublisher = "com.github.triplet.gradle:play-publisher:${Versions.pluginPlayPublisher}"
  const val gradleVersions = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
}

object Dependencies {
  const val mapboxBase = "com.mapbox.base:common:${Versions.mapboxBase}"
  const val mapboxAnnotations = "com.mapbox.base:annotations:${Versions.mapboxBase}"
  const val mapboxAnnotationsProcessor = "com.mapbox.base:annotations-processor:${Versions.mapboxBase}"
  const val mapboxJavaGeoJSON = "com.mapbox.mapboxsdk:mapbox-sdk-geojson:${Versions.mapboxJavaServices}"
  const val mapboxServices = "com.mapbox.mapboxsdk:mapbox-sdk-services:${Versions.mapboxJavaServices}"
  const val mapboxGlNative = "com.mapbox.maps:android-core:${Versions.mapboxGlNative}"
  const val mapboxCoreCommon = "com.mapbox.common:common:${Versions.mapboxCommon}"
  const val mapboxOkHttp = "com.mapbox.common:okhttp:${Versions.mapboxCommon}"
  const val mapboxJavaTurf = "com.mapbox.mapboxsdk:mapbox-sdk-turf:${Versions.mapboxJavaServices}"
  const val mapboxGestures = "com.mapbox.mapboxsdk:mapbox-android-gestures:${Versions.mapboxGestures}"
  const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
  const val androidxRecyclerView = "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}"
  const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.androidxCore}"
  const val androidxAnnotations = "androidx.annotation:annotation:${Versions.androidxAnnotation}"
  const val androidxInterpolators = "androidx.interpolator:interpolator:${Versions.androidxInterpolator}"
  const val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
  const val androidxEspresso = "androidx.test.espresso:espresso-core:${Versions.androidxEspresso}"
  const val androidxTestJUnit = "androidx.test.ext:junit:${Versions.androidxJUnit}"
  const val androidxRules = "androidx.test:rules:${Versions.androidxTest}"
  const val androidxJUnitTestRules = "androidx.test:rules:${Versions.androidxTest}"
  const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunner}"
  const val androidxTestCore = "androidx.test:core:${Versions.androidxTest}"
  const val androidxTestMonitor = "androidx.test:monitor:${Versions.androidXTestMonitor}"
  const val androidxArchCoreTest = "androidx.arch.core:core-testing:${Versions.androidxArchCoreTest}"
  const val androidxUiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.androidxUiAutomator}"
  const val androidxFragmentTest = "androidx.fragment:fragment-testing:${Versions.androidxFragmentTesting}"
  const val androidxOrchestrator = "androidx.test:orchestrator:${Versions.androidxTestOrchestrator}"
  const val androidxMultidex = "androidx.multidex:multidex:${Versions.androidxMultidex}"
  const val androidxLifecycle = "androidx.lifecycle:lifecycle-runtime:${Versions.androidxLifecycle}"
  const val androidxLifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
  const val androidxLifecycleRuntimeTesting = "androidx.lifecycle:lifecycle-runtime-testing:${Versions.androidxLifecycle}"
  const val androidxActivityCompose = "androidx.activity:activity-compose:${Versions.androidxActivityCompose}"
  const val googleMaterialDesign = "com.google.android.material:material:${Versions.materialDesign}"
  const val googlePlayServicesLocation = "com.google.android.gms:play-services-location:${Versions.googlePlayServicesLocation}"
  const val googleCarAppLibrary = "androidx.car.app:app:${Versions.googleCarAppLibrary}"
  const val androidAutoMapboxMapSdk = "com.mapbox.maps:android:${Versions.androidAutoMapboxMapSdk}"
  const val squareLeakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.squareLeakCanary}"
  const val squareRetrofit = "com.squareup.retrofit2:retrofit:${Versions.squareRetrofit}"
  const val squareRetrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.squareRetrofit}"
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.pluginKotlin}"
  const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
  const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"
  const val junit = "junit:junit:${Versions.junit}"
  const val mockk = "io.mockk:mockk:${Versions.mockk}"
  const val mockkAgentApi = "io.mockk:mockk-agent-api:${Versions.mockk}"
  const val mockkAgentJvm = "io.mockk:mockk-agent-jvm:${Versions.mockk}"
  const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
  const val robolectricEgl = "org.khronos:opengl-api:${Versions.robolectricEgl}"
  const val lintApi = "com.android.tools.lint:lint-api:${Versions.lint}"
  const val lintChecks = "com.android.tools.lint:lint-checks:${Versions.lint}"
  const val lint = "com.android.tools.lint:lint:${Versions.lint}"
  const val lintTests = "com.android.tools.lint:lint-tests:${Versions.lint}"
  const val testUtils = "com.android.tools:testutils:${Versions.lint}"
  const val hamcrest = "org.hamcrest:hamcrest:${Versions.hamcrest}"
  const val annotations = androidxAnnotations
  const val equalsVerifier = "nl.jqno.equalsverifier:equalsverifier:${Versions.equalsVerifier}"
  const val asyncInflater = "androidx.asynclayoutinflater:asynclayoutinflater:${Versions.asyncInflater}"
  const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.pluginKotlin}"
  const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
  const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
  const val composeUi = "androidx.compose.ui:ui"
  const val composeMaterial = "androidx.compose.material:material"
  const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"

}

object Versions {
  const val pluginAndroidGradle = "7.4.2"
  const val pluginKotlin = "1.8.10"
  const val pluginLicense = "0.9.0"
  const val pluginDokka =  "1.8.10"
  const val pluginJacoco = "0.2"
  const val pluginBinaryCompatibilityValidator = "0.8.0"
  const val pluginTaskTree = "2.1.0"
  const val mapboxAccessToken="0.4.0"
  const val mapboxSdkRegistry="1.2.1"
  const val mapboxGestures = "0.8.0"
  const val mapboxJavaServices = "5.4.1"
  const val mapboxBase = "0.8.0"
  const val mapboxGlNative = "11.0.0-SNAPSHOT.0406T0706Z.51449bc"
  const val mapboxCommon = "23.5.0-SNAPSHOT.0321T1613Z.cc81dc2"
  const val androidxCore = "1.9.0" // last version compatible with kotlin 1.8.10
  const val androidxFragmentTesting = "1.5.0"
  // can not bump to v1.6.0, due to incompatible
  // `@androidx.annotation.VisibleForTesting(otherwise = null)`
  // generated by the telemetry module setup, we can update either by updating the annotation
  // processor in mapbox-android-base, or remove the module setup in mapbox-maps-android
  const val androidxAnnotation = "1.1.0"
  const val androidxAppcompat = "1.6.1"
  const val androidxTest = "1.5.0"
  const val androidxTestRunner = "1.5.2"
  const val androidxTestOrchestrator = "1.4.2"
  const val androidXTestMonitor = "1.6.1"
  const val androidxArchCoreTest = "2.1.0"
  const val androidxConstraintLayout = "2.0.0"
  const val androidxEspresso = "3.5.1"
  const val androidxJUnit = "1.1.5"
  const val androidxUiAutomator = "2.2.0"
  const val androidxRecyclerView = "1.1.0"
  const val androidxInterpolator="1.0.0"
  const val androidxMultidex = "2.0.1"
  const val androidxLifecycle = "2.3.0"
  const val androidxActivityCompose = "1.4.0"
  const val squareRetrofit="2.9.0"
  const val squareLeakCanary = "2.9.1"
  const val materialDesign = "1.2.0"
  const val googlePlayServicesLocation = "18.0.0"
  const val googleCarAppLibrary= "1.2.0"
  const val androidAutoMapboxMapSdk = "10.5.0"
  // Use kotlinCoroutines to 1.6.1 to be compatible with platform runtime.
  const val kotlinCoroutines = "1.6.1"
  const val junit = "4.13.2"
  const val mockk = "1.13.4"
  const val robolectric = "4.9.2"
  const val robolectricEgl = "gl1.1-android-2.1_r1"
  const val lint = "30.4.2"
  const val hamcrest = "2.1"
  const val equalsVerifier = "3.14"
  const val asyncInflater = "1.0.0"
  const val mapboxSdkVersionsPlugin = "1.1.3"
  const val pitest = "0.2.8"
  const val detekt = "1.22.0"
  const val compose = "1.4.3"
  const val composeBom = "2023.01.00"
  const val pluginPlayPublisher = "3.7.0"
  const val gradleVersionsPlugin = "0.42.0"
}

/**
 * @return True if this build is part of Circleci job triggered from a release tag
 */
public val isBuildingReleaseTag by lazy { "^v[0-9]+\\.[0-9]+\\.[0-9]+.*\$".toRegex().matches(System.getenv("CIRCLE_TAG") ?: "")}

/**
 * @return True if this build is part of Circleci job triggered from a PR that targets a release branch
 */
public val isTargettingReleaseBranch by lazy { "^v[0-9]+\\.[0-9]+\$".toRegex().matches(System.getenv("PR_TARGET_BRANCH") ?: "")}
