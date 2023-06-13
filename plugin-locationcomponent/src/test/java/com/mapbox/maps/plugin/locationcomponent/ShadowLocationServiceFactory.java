package com.mapbox.maps.plugin.locationcomponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.ExpectedFactory;
import com.mapbox.bindgen.Value;
import com.mapbox.common.Cancelable;
import com.mapbox.common.location.AccuracyAuthorization;
import com.mapbox.common.location.GetLocationCallback;
import com.mapbox.common.location.LiveTrackingClient;
import com.mapbox.common.location.LocationError;
import com.mapbox.common.location.LocationService;
import com.mapbox.common.location.LocationServiceFactory;
import com.mapbox.common.location.LocationServiceObserver;
import com.mapbox.common.location.PermissionStatus;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

import java.util.List;

@Implements(LocationServiceFactory.class)
public class ShadowLocationServiceFactory {
    public static LiveTrackingClient liveTrackingClient;

    @Implementation
    public static LocationService locationService() {
        return new LocationService() {
            @Override
            public boolean isAvailable() {
                return false;
            }

            @Nullable
            @Override
            public Cancelable getLastLocation(@NonNull GetLocationCallback callback) {
                return null;
            }

            @NonNull
            @Override
            public Expected<LocationError, LiveTrackingClient> getBestLiveTrackingClient() {
                return null;
            }

            @Override
            public int getCurrentLocation(@Nullable Value settings, @NonNull GetLocationCallback callback) {
                return 0;
            }

            @Override
            public void cancelGetCurrentLocation(int requestId) {

            }

            @NonNull
            @Override
            public PermissionStatus getPermissionStatus() {
                return null;
            }

            @NonNull
            @Override
            public AccuracyAuthorization getAccuracyAuthorization() {
                return null;
            }

            @Override
            public void registerObserver(@NonNull LocationServiceObserver observer) {

            }

            @Override
            public void unregisterObserver(@NonNull LocationServiceObserver observer) {

            }

            @NonNull
            @Override
            public List<String> getLiveTrackingClients() {
                return null;
            }

            @Nullable
            @Override
            public Value getLiveTrackingClientCapabilities(@Nullable String name) {
                return null;
            }

            @Nullable
            @Override
            public Value getLiveTrackingClientSettings(@Nullable String name) {
                return null;
            }

            @NonNull
            @Override
            public Expected<LocationError, LiveTrackingClient> getLiveTrackingClient(@Nullable String name, @Nullable Value capabilities, @Nullable Value settings) {
                return ExpectedFactory.createValue(liveTrackingClient);
            }
        };
    }
}
