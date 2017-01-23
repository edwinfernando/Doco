package com.domicilio.confiable.doco.views.activities;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by edwinmunoz on 11/28/16.
 */

public interface IMainActivityView {
    void expandFAB();
    void hideFAB();
    void deployDialogPromotions(Context context);
    void deployDialogIsDriverDoco(Context context);
    //Firebase
    void getDrivers(GoogleMap nMap);
    void createMarkersDrivers(LatLng ubicationDriver,String nameDriver);
    void paintDriverOnMap(GoogleMap nMap);


}
