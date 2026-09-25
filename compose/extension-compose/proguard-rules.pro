# ComposeVersion is accessed reflectively to read the runtime version at startup.
-keepclassmembers class androidx.compose.runtime.ComposeVersion {
    int version;
}
