package com.domicilio.confiable.doco.views.activities;

import android.content.Context;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by edwinmunoz on 11/28/16.
 */

public interface IMainActivityView {
    void expandFAB();
    void hideFAB();
    void deployDialogPromotions(Context context);
    void deployDialogIsDriverDoco(Context context);
    //Firebase
    void addDrivers(LatLng ubicationDriver);
    void getDrivers(GoogleMap nMap);
    ArrayList<MarkerOptions> getMarkersDriver();
    void createMarkersDrivers(LatLng ubicationDriver,String nameDriver);
    void paintDriverOnMap(GoogleMap nMap);
    void validateGPS();
    void addDriverPerson();


}
