package com.domicilio.confiable.doco.repositorio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edwinmunoz on 11/18/16.
 */

public class DriversSQLHelper extends SQLiteOpenHelper {

    public DriversSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE Drivers ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "code TEXT,"+
                "name TEXT,"+
                "movilNumber TEXT,"+
                "satisfationScore INT)";

        //  String imagen =

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
