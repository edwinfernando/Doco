package com.domicilio.confiable.doco.views.activities;

import android.text.Editable;
import android.widget.EditText;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public interface ILoginView {
    void showLoading();
    void hideLoading();
    void toEnter();
    void createRegister();
    //void isChboxAccept(boolean chbox);
    void goToMainActivity();
    void goToRegisterActivity();
    void showErrorAutentication(Exception e);
    void showErrorLoginText(boolean error, int type);
    void showErrorPasswordText(boolean error);

}
