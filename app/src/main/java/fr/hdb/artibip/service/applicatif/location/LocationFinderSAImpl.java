package fr.hdb.artibip.service.applicatif.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;


@EBean(scope = EBean.Scope.Singleton)
public class LocationFinderSAImpl implements LocationFinderSA {

    private static final int UPDATE_DISTANCE = 3000;
    private static final int UPDATE_DELAY = 10;

    @RootContext
    protected Context context;

    /**
     * get the current location from gps or network provider
     * @return
     */
    @Override
    public Location getCurrentLocation(Context ctx) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {

            }
        };
        Location location = null;
        try {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = false;
            if (locationManager != null)
                // getting GPS status
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = false;
            if (locationManager != null)
                // getting network status
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                // Location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_DISTANCE, UPDATE_DELAY, listener);
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                //Location from GPS
                if (isGPSEnabled && location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_DISTANCE, UPDATE_DELAY, listener);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }

        } catch (Exception e) {
        }
        return location;
    }
}
