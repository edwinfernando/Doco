package com.domicilio.confiable.doco.model;

import android.net.Uri;

/**
 * Created by edwin on 16/11/2016.
 */

public class Driver {

    public static  final String NAME_TABLE = "Drivers";
    public static  final String CODE = "code";
    public static  final String NAME = "name";
    public static  final String MOVIL_NUMBER = "movilNumber";
    public static  final String SATISFATION_SCORE = "satisfationScore";

    private Uri image_profile;
    private String code;
    private String name;
    private String movil_number;
    private int satisfation_score;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovilNumber() {
        return movil_number;
    }

    public void setMovilNumber(String movil_number) {
        this.movil_number = movil_number;
    }

    public int getSatisfationScore() {
        return satisfation_score;
    }

    public void setSatisfationScore(int satisfation_score) {
        this.satisfation_score = satisfation_score;
    }
}
