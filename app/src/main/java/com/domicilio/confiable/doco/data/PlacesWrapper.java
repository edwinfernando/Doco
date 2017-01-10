package com.domicilio.confiable.doco.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan.Cabuyales on 7/01/2017.
 */

public class PlacesWrapper implements Parcelable {

    @SerializedName("description")
    @Expose
    private String descripcion;

    private PlacesWrapper(Parcel in) {
        descripcion = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descripcion);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescripcion() {
        return descripcion;
    }


    public static final Creator<PlacesWrapper> CREATOR = new Creator<PlacesWrapper>() {
        @Override
        public PlacesWrapper createFromParcel(Parcel in) {
            return new PlacesWrapper(in);
        }

        @Override
        public PlacesWrapper[] newArray(int size) {
            return new PlacesWrapper[size];
        }
    };
}
