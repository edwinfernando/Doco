package com.domicilio.confiable.doco.model;

import android.net.Uri;

/**
 * Created by edwin on 16/11/2016.
 */

public class Driver {

    private Uri image_profile;
    private String code_driver;
    private String name_driver;
    private String cel_driver;
    private int satisfation_score;

    public Uri getImage_profile() {
        return image_profile;
    }

    public void setImage_profile(Uri image_profile) {
        this.image_profile = image_profile;
    }

    public String getCode_driver() {
        return code_driver;
    }

    public void setCode_driver(String code_driver) {
        this.code_driver = code_driver;
    }

    public String getName_driver() {
        return name_driver;
    }

    public void setName_driver(String name_driver) {
        this.name_driver = name_driver;
    }

    public String getCel_driver() {
        return cel_driver;
    }

    public void setCel_driver(String cel_driver) {
        this.cel_driver = cel_driver;
    }

    public int getSatisfation_score() {
        return satisfation_score;
    }

    public void setSatisfation_score(int satisfation_score) {
        this.satisfation_score = satisfation_score;
    }

}
