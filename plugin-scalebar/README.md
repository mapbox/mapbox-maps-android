## Mapbox Maps ScaleBar Plugin for Android

### Overview

The Mapbox Maps ScaleBar Plugin for Android is an public library for displaying scale bar on top of MapView. By default the plugin will show a scale bar on top of the map at the top-left corner. Scale bar gets visually updated on MapView zoom level or latitude change.

A full overview of classes and interfaces can be found in our [API documentation](https://docs.mapbox.com/android/beta/maps/guides/).

![scalebar-plugin](https://user-images.githubusercontent.com/8577318/95547874-7e651e80-0a36-11eb-8512-b4256db8f81c.gif)

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps ScaleBar Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the ScaleBar plugin to your project, you configure its dependency in your `build.gradle` files.

```groovy
// In the root build.gradle file
// The Mapbox access token needs to a scope set to DOWNLOADS:READ
allprojects {
    repositories {
        maven {
            url 'https://api.mapbox.com/downloads/v2/releases/maven'
            authentication {
                basic(BasicAuthentication)
            }
            credentials {
                username = "mapbox"
                password = "INSERT_MAPBOX_ACCESS_TOKEN_HERE"
            }
        }
    }
}

// In the app build.gradle file
dependencies {
  implementation 'com.mapbox.plugin:maps-scalebar:10.6.0-rc.1'
}
```

### Example

Customizing Mapbox Maps Scale Plugin for Android could be done in two ways: by either providing your own version of the ScaleBar plugin or by setting the ScaleBarSettings to the ScaleBar plugin. The former is documented in [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md), the latter can be achieved with:

```kotlin
val scaleBarPlugin = mapView.scalebar
val settings = scaleBarPlugin.getSettings()
settings.textColor = ContextCompat.getColor(this, R.color.primary)
// Update other properties if needed.
scaleBarPlugin.updateSettings(settings)
```

The ScaleBar plugin can also be configured in layout files:

```xml
<com.mapbox.maps.MapView
    android:id="@+id/mapView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    mapbox:mapbox_scaleBarBorderWidth="1dp"
    mapbox:mapbox_scaleBarHeight="3dp"
    mapbox:mapbox_scaleBarMarginLeft="5dp"
    mapbox:mapbox_scaleBarMarginTop="5dp"
    mapbox:mapbox_scaleBarPrimaryColor="@color/white"
    mapbox:mapbox_scaleBarSecondaryColor="@color/design_default_color_on_secondary"
    mapbox:mapbox_scaleBarTextBarMargin="5dp"
    mapbox:mapbox_scaleBarTextBorderWidth="1dp"
    mapbox:mapbox_scaleBarTextColor="@color/accent"
    mapbox:mapbox_scaleBarTextSize="10dp" />
```
More concrete examples of the ScaleBar plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
