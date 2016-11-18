package com.domicilio.confiable.doco.util;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.domicilio.confiable.doco.views.activities.CheckPermissionActivityManager;

public class MyLocationListener implements LocationListener {
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute
    private static final int TWO_MINUTES = 1000 * 60 * 2;


    private Context context;
    private LocationManager locationManager;
    private Location location;
    private Location lastLocation;
    private LocationChangeListener locationChangeListener;

    public MyLocationListener(Context context, LocationChangeListener locationChangeListener) {
        this.context = context;
        this.locationChangeListener = locationChangeListener;

        //Obtener una referencia del servicio de LOCATION de Android
        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    }

    //Verificacion en tiempo de ejecucion
    public void start() {

        //Paso 1: Verificar el permiso
        if(!CheckPermissionActivityManager.checkPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION,//ACCESS_FINE_LOCATION
                //Paso 2: Pedir permiso
                 new CheckPermissionActivityManager.CheckingPermissionListener(){
                     @Override
                     public void onResult(boolean isGranted) {
                         //Paso 3: Volver al flujo de ejecucion
                         if(isGranted)
                             start();
                     }
                 })) {
        }else{
            //addProximityAlert: alerta para notificar proximidad del punto a al b
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

          /*  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);*/
        }
    }

    public void stop() {
        if(CheckPermissionActivityManager.checkPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION, null)) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(isBetterLocation(location, lastLocation)) {
            lastLocation = location;

            locationChangeListener.onLocationChange(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /** Determines whether one Location reading is better than the current Location fix
     * Fuente: https://developer.android.com/guide/topics/location/strategies.html
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
