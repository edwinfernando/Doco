package com.domicilio.confiable.doco.data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Juan.Cabuyales on 7/01/2017.
 */

public class PlacesAutoComplete implements SearchSuggestion {

    String description;
    private boolean mIsHistory = false;

    public PlacesAutoComplete(String suggestion) {
        this.description = suggestion.toLowerCase();
    }

    public PlacesAutoComplete(Parcel source) {
        this.description = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }


    @Override
    public String getBody() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeInt(mIsHistory ? 1 : 0);
    }

    public static final Creator<PlacesAutoComplete> CREATOR = new Creator<PlacesAutoComplete>() {
        @Override
        public PlacesAutoComplete createFromParcel(Parcel in) {
            return new PlacesAutoComplete(in);
        }

        @Override
        public PlacesAutoComplete[] newArray(int size) {
            return new PlacesAutoComplete[size];
        }
    };

}
