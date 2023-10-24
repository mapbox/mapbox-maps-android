package com.mapbox.maps.plugin.locationcomponent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mapbox.bindgen.Expected;
import com.mapbox.bindgen.ExpectedFactory;
import com.mapbox.common.location.AccuracyAuthorization;
import com.mapbox.common.location.DeviceLocationProvider;
import com.mapbox.common.location.DeviceLocationProviderFactory;
import com.mapbox.common.location.DeviceLocationProviderType;
import com.mapbox.common.location.LocationError;
import com.mapbox.common.location.LocationProviderRequest;
import com.mapbox.common.location.LocationService;
import com.mapbox.common.location.LocationServiceFactory;
import com.mapbox.common.location.LocationServiceObserver;
import com.mapbox.common.location.PermissionStatus;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(LocationServiceFactory.class)
public class ShadowLocationServiceFactory {
    public static DeviceLocationProvider deviceLocationProvider;

    @Implementation
    public static LocationService locationService() {
        return new LocationService() {
            @NonNull
            @Override
            public Expected<LocationError, DeviceLocationProvider> getDeviceLocationProvider(@NonNull DeviceLocationProviderType deviceLocationProviderType, @Nullable LocationProviderRequest locationProviderRequest, boolean b) {
                return ExpectedFactory.createValue(deviceLocationProvider);
            }

            @Override
            public void setUserDefinedDeviceLocationProviderFactory(@Nullable DeviceLocationProviderFactory deviceLocationProviderFactory) {

            }

            @NonNull
            @Override
            public Expected<LocationError, DeviceLocationProvider> getDeviceLocationProvider(@Nullable LocationProviderRequest locationProviderRequest) {
                return ExpectedFactory.createValue(deviceLocationProvider);
            }

            @Override
            public boolean isAvailable() {
                return false;
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
        };
    }
}
