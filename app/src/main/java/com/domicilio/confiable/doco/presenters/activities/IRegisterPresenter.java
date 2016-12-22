package com.domicilio.confiable.doco.presenters.activities;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by edwin on 17/11/2016.
 */

public interface IRegisterPresenter {
    boolean edtLoginTextChanged(String email);
    boolean edtPasswordTextChanged(String password);
    void isChboxAccept(boolean chbox);
    void register(String email, String password);
    void onClickButtonGoogle();
    void firebaseAuthWithGoogle(GoogleSignInResult result);
}
