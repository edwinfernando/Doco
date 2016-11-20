package com.domicilio.confiable.doco.presenters.fragments;

import android.content.Context;

import com.domicilio.confiable.doco.views.fragments.IDriverAvaibleView;

/**
 * Created by edwin on 19/11/2016.
 */

public class DriverAvaiblePresenter implements IDriverAvaiblePresenter {

    IDriverAvaibleView view;

    private Context context;

    public DriverAvaiblePresenter(IDriverAvaibleView view, Context context) {
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
