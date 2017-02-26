package com.domicilio.confiable.doco.presenters.fragments;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public interface IMapsPresenter {
    void onResume();
    void onStop();
    void onClickTarget();
    void showDriversOnMap(ArrayList<MarkerOptions> drivers);
}
