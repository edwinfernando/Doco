package com.domicilio.confiable.doco.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


import com.domicilio.confiable.doco.model.Route;
/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
