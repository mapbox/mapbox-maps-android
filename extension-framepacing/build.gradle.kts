plugins {
  id("com.android.library")
}

// important note: libswappy.so we use is built with NDK20 toolchain although we use NDK21 to build our SDK

// that happens to pretty unexpected fact that NDK 21.4.7075529 we're using to build our SDKs is NOT backwards compatible with NDK 21.0.6113669 that Swappy uses
// surprisingly using Swappy built with NDK20 compiles OK so using it for now

android {
  compileSdk = AndroidVersions.compileSdkVersion
  defaultConfig {
    minSdk = AndroidVersions.minSdkVersion
    targetSdk = AndroidVersions.targetSdkVersion
  }
}