package com.domicilio.confiable.doco.views.activities;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public interface IRegisterView {
    void showLoading();
    void hideLoading();
    void isChboxAccept(boolean chbox);
    void goToMainActivity();
    void register();
    void showErrorRegister(Exception e);
    void showNotification(String msg);
    void showErrorLoginText(boolean error, int type);
    void showErrorPasswordText(boolean error);
}
