## Mapbox Maps Viewport Plugin for Android

### Overview

The Mapbox Maps Viewport Plugin for Android is an public library for tracking objects on a map. Users has the flexibility to customise viewport states and transitions between viewport states.

At any given time, the viewport is either:
  - idle (not updating the camera)
  - in a state (camera is being managed by a ViewportState)
  - transitioning (camera is being managed by a ViewportTransition)

Two ViewportState are provided by default that user can create instance from the ViewportPlugin:
 * `viewport.makeFollowPuckViewportState(options): FollowPuckViewportState`
This state allows to follow user's location, and syncs the viewport position with the location puck provided by the LocationComponent.
 * `viewport.makeOverviewViewportState(options): OverviewViewportState`
This state sets the viewport to cover user provided geometry with configurations.

And users are free to add their own ViewportState and use it with the viewport plugin.

Two ViewportTransition are also provided by default that user can create instance from the ViewportPlugin:
* `viewport.makeDefaultViewportTransition(options): DefaultViewportTransition`
The default viewport transition allows user to transition from one state to another state with the default optimised animations.
* `viewport.makeImmediateViewportTransition(): ImmediateViewportTransition`
The immediate viewport transition allows user to transition from one state to another state immediately without animations.

And users are free to add their own ViewportTransition and set to specifically to animate transition from the desired fromState and targetState.

A full overview of classes and interfaces can be found in our [User location guide](https://docs.mapbox.com/android/maps/guides/user-location/#location-tracking) and [API documentation](https://docs.mapbox.com/android/maps/api-reference/).

### Getting Started

This README is intended for developers who are interested in [contributing](https://github.com/mapbox/mapbox-maps-android/blob/master/CONTRIBUTING.md) to the Mapbox Maps Viewport Plugin for Android. Please visit [DEVELOPING.md](https://github.com/mapbox/mapbox-maps-android/blob/master/DEVELOPING.md) for general information and instructions on how to use the Mapbox Maps Plugin System. To add the viewport plugin to your project, you configure its dependency in your `build.gradle` files.

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
  implementation 'com.mapbox.plugin:maps-viewport:10.6.0-rc.1'
}
```

### Example

```kotlin
  val viewportPlugin = mapView.viewport
  val followPuckViewportState: FollowPuckViewportState = viewportPlugin.makeFollowPuckViewportState(
      FollowPuckViewportStateOptions.Builder()
        .bearing(FollowPuckViewportStateBearing.Constant(0.0))
        .animationDurationMs(500)
        .padding(EdgeInsets(200.0 * resources.displayMetrics.density, 0.0, 0.0, 0.0))
        .build()
    )
  val overviewViewportState: OverviewViewportState = viewportPlugin.makeOverviewViewportState(
      OverviewViewportStateOptions.Builder()
        .geometry(routePoints)
        .padding(EdgeInsets(100.0, 100.0, 100.0, 100.0))
        .build()
    )
  }
  val immediateTransition = viewportPlugin.makeImmediateViewportTransition()
  // transition from idle(the default state) to the created followPuckViewportState with default transition
  viewportPlugin.transitionTo(followPuckViewportState) { isFinished ->
    // the transition has been completed with flag to check the finished status
  }
  ...
  // transition from followPuckViewportState to overviewViewportState with immediate transition
  viewportPlugin.transitionTo(overviewViewportState, immediateTransition) { isFinished ->
    // the transition has been completed with flag to check the finished status
  }
```

More concrete examples of the viewport plugin can be found in our [test application](https://github.com/mapbox/mapbox-maps-android/tree/master/app/src/main/java/com/mapbox/maps/testapp).

#### Dependencies

View [LICENSE.md](LICENSE.md) for all dependencies used by this plugin.
