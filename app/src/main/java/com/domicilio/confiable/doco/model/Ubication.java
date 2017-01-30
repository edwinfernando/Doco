package com.domicilio.confiable.doco.model;

/**
 * Created by Juan.Cabuyales on 29/01/2017.
 * @description: Clase que representa la ubicacion del conductor
 */

public class Ubication {

    private double latitude;
    private double longitude;
    private String nameDriver;
    public final static String UBICATION="Ubicaciones";

    public Ubication(double nLatitude,double nLongitude,String nNameDriver){
        latitude = nLatitude;
        longitude = nLongitude;
        nameDriver = nNameDriver;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }
}
