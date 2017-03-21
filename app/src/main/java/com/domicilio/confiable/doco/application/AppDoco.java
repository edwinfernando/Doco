package com.domicilio.confiable.doco.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.domicilio.confiable.doco.model.UserDoco;
import com.domicilio.confiable.doco.repositorio.DriverDAO;
import com.domicilio.confiable.doco.repositorio.DriversSQLHelper;
import com.domicilio.confiable.doco.repositorio.SessionDAO;
import com.domicilio.confiable.doco.util.Cache;
import com.google.android.gms.location.places.UserDataType;

/**
 * Created by edwinmunoz on 11/18/16.
 */

public class AppDoco extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Cache.getInstance().init(getApplicationContext());
        DriversSQLHelper slqHelper = new DriversSQLHelper(getApplicationContext(),"DocoDB",null,1);
        SessionDAO.getInstance().setDriverDAO(new DriverDAO(slqHelper));

      /*  UserDoco userDoco = new UserDoco();
        userDoco.setIs_active(false);

        Cache.getInstance().add("user", userDoco);*/
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isActive", false);
        editor.commit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
