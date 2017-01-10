package com.domicilio.confiable.doco.presenters.activities;

import android.text.Editable;

/**
 * Created by edwin on 17/11/2016.
 */

public interface ILoginPresenter {
    boolean edtLoginTextChanged(String email);
    boolean edtPasswordTextChanged(String paswword);
    void toEnter(String email, String password) ;
    void createRegister();
}
