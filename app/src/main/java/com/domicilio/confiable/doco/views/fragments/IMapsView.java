package com.domicilio.confiable.doco.views.fragments;

import android.content.Context;

import com.domicilio.confiable.doco.domain.Marker;


public interface IMapsView {
    Context getContext();

    void refreshMap(Marker marker);
    void onClickTarget();
    void goToDriverComeFragment();
}
