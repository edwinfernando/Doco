package com.domicilio.confiable.doco.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.domicilio.confiable.doco.model.Driver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwinmunoz on 11/18/16.
 */

public class DriverDAO implements IDriverDAO {

    private DriversSQLHelper sqlHelper;

    public DriverDAO(DriversSQLHelper slqHelper) {
        this.sqlHelper = slqHelper;
    }

    @Override
    public void insert(Driver driver) {
        ContentValues values = new ContentValues();
        values.put(Driver.CODE,driver.getCode());
        values.put(Driver.NAME,driver.getName());
        values.put(Driver.MOVIL_NUMBER,driver.getMovilNumber());
        values.put(Driver.SATISFATION_SCORE,driver.getSatisfationScore());

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(Driver.NAME_TABLE,null,values);
    }

    @Override
    public List<Driver> obtenerTodos() {
        String columnas[] = new String[]{
                Driver.CODE,
                Driver.NAME,
                Driver.MOVIL_NUMBER,
                Driver.SATISFATION_SCORE};

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(Driver.NAME_TABLE,columnas,null,null,null,null,null,null);

        Driver driver;
        List<Driver> list = new ArrayList<>();

        while (cursor.moveToNext()){
            driver = new Driver();
            driver.setCode(cursor.getString(cursor.getColumnIndex(Driver.CODE)));
            driver.setName(cursor.getString(cursor.getColumnIndex(Driver.NAME)));
            driver.setMovilNumber(cursor.getString(cursor.getColumnIndex(Driver.MOVIL_NUMBER)));
            driver.setSatisfationScore(cursor.getInt(cursor.getColumnIndex(Driver.SATISFATION_SCORE)));

            list.add(driver);
        }
        return list;
    }

    @Override
    public Driver obtenerPorCodigo(String code) {
        String sql = "Select * From"+Driver.NAME_TABLE+" Where "+"code = ";

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{code});

        Driver driver = null;
        if(cursor.moveToNext()){
            driver = new Driver();
            driver.setCode(cursor.getString(cursor.getColumnIndex(Driver.CODE)));
            driver.setName(cursor.getString(cursor.getColumnIndex(Driver.NAME)));
            driver.setMovilNumber(cursor.getString(cursor.getColumnIndex(Driver.MOVIL_NUMBER)));
            driver.setSatisfationScore(cursor.getInt(cursor.getColumnIndex(Driver.SATISFATION_SCORE)));
        }
        return driver;
    }
}
