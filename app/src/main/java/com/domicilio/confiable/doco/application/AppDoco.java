package com.domicilio.confiable.doco.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.domicilio.confiable.doco.repositorio.DriverDAO;
import com.domicilio.confiable.doco.repositorio.DriversSQLHelper;
import com.domicilio.confiable.doco.repositorio.SessionDAO;
import com.domicilio.confiable.doco.util.Cache;

/**
 * Created by edwinmunoz on 11/18/16.
 */

public class AppDoco extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Cache.getInstance().init(getApplicationContext());
        DriversSQLHelper slqHelper = new DriversSQLHelper(getApplicationContext(),"DocoDB",null,1);
        SessionDAO.setDriverDAO(new DriverDAO(slqHelper));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
