package com.domicilio.confiable.doco.presenters.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.domain.Marker;
import com.domicilio.confiable.doco.util.LocationChangeListener;
import com.domicilio.confiable.doco.util.MyLocationListener;
import com.domicilio.confiable.doco.util.Utilities;
import com.domicilio.confiable.doco.views.fragments.IMapsView;

import com.google.gson.Gson;


public class MapsPresenter implements IMapsPresenter, LocationChangeListener {
    public static final String ACTION_REFRESH_MAP = "REFRESH_MAP";
    private IMapsView view;
    private BroadcastReceiver brRefreshMap;
    private IntentFilter intentFilter;
    private MyLocationListener myLocationListener;

    public MapsPresenter(final IMapsView view) {
        this.view = view;

        brRefreshMap = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String jsonMarker = intent.getStringExtra("marker");
                Gson gson = new Gson();
                Marker marker = gson.fromJson(jsonMarker, Marker.class);
                view.refreshMap(marker);

                Utilities.showNotification(view.getContext(), 123, R.mipmap.ic_launcher,
                        "Nueva notificacion", "Mapa centrado en: "+marker.getName(), null);
            }
        };

        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_REFRESH_MAP);

        myLocationListener = new MyLocationListener(view.getContext(), this);
    }

    @Override
    public void onResume() {
        view.getContext().registerReceiver(brRefreshMap, intentFilter);
        myLocationListener.start();
    }

    @Override
    public void onStop() {
        view.getContext().unregisterReceiver(brRefreshMap);

        myLocationListener.stop();
    }

    @Override
    public void onClickTarget() {
        view.goToDriverComeFragment();
    }

    @Override
    public void onLocationChange(Location location) {
        Marker marker = new Marker("Estoy aqui", location.getLatitude(), location.getLongitude());
        view.refreshMap(marker);
    }
}
