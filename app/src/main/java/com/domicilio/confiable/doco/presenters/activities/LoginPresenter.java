package com.domicilio.confiable.doco.presenters.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;

import com.domicilio.confiable.doco.model.UserDoco;
import com.domicilio.confiable.doco.util.Cache;
import com.domicilio.confiable.doco.views.activities.ILoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;
    private FirebaseAuth mAuth;
    private Context context;

    public LoginPresenter(ILoginView view, Context context) {
        this.view = view;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public boolean edtLoginTextChanged(String email) {
        if (email.isEmpty()) {
            view.showErrorLoginText(true,0);
            return true;
        } else if (!(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            view.showErrorLoginText(true,1);
            return true;
        } else {
            view.showErrorLoginText(false,0);
            return false;
        }
    }

    @Override
    public boolean edtPasswordTextChanged(String password) {
        if (password.isEmpty() || password.equals("")) {
            view.showErrorPasswordText(true);
            return true;
        } else {
            view.showErrorPasswordText(false);
            return false;
        }
    }

    @Override
    public void toEnter(String email, String password) {
        if (!edtLoginTextChanged(email) && !edtPasswordTextChanged(password)) {
            view.showLoading();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            view.hideLoading();
                            if (task.isSuccessful()) {
                                view.goToMainActivity();
                            } else {
                                view.showErrorAutentication(task.getException());
                            }
                        }
                    });
        }
    }

    @Override
    public void createRegister() {
        view.goToRegisterActivity();
    }



}
