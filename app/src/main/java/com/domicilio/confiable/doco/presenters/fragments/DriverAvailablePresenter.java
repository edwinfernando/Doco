package com.domicilio.confiable.doco.presenters.fragments;

import android.content.Context;

import com.domicilio.confiable.doco.views.fragments.IDriverAvailableView;

/**
 * Created by edwin on 19/11/2016.
 */

public class DriverAvailablePresenter implements IDriverAvailablePresenter {

    IDriverAvailableView view;

    private Context context;

    public DriverAvailablePresenter(IDriverAvailableView view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void gotoDriverProfileFragment() {
        view.gotoDriverProfileFragment();
    }

    @Override
    public void gotoDriverComeFragment() {view.gotoDriverComeFragment();}
}
