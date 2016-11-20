package com.domicilio.confiable.doco.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by edwinmunoz on 11/18/16.
 */

public class Cache {

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public static Cache instance;

    private Cache() {
    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
            return instance;
        }

        return instance;
    }

    public void init(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void add(String llave, Object objeto) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(llave, gson.toJson(objeto));
        editor.commit();
    }

    public <T> T get(String llave, Class<T> clazz) {
        String objeto = sharedPreferences.getString(llave, "");
        return gson.fromJson(objeto, clazz);
    }
}
