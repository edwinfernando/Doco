package com.domicilio.confiable.doco.presenters.activities;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.domicilio.confiable.doco.model.UserDoco;
import com.domicilio.confiable.doco.util.Cache;
import com.domicilio.confiable.doco.views.activities.IRegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public class RegisterPresenter implements IRegisterPresenter{
    private IRegisterView view;
    private FirebaseAuth mAuth;
    private Context context;
    private boolean chbox = false;

    public RegisterPresenter (IRegisterView view, Context context) {
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
    public void isChboxAccept(boolean chbox) {
        this.chbox = chbox;
    }

    @Override
    public void register(String email, String password) {
        final String emailtmp = email;
        final String[] username = email.split("@");

        if (!edtLoginTextChanged(email) && !edtPasswordTextChanged(password)) {
            view.showLoading();
            if (chbox) {
                mAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                view.hideLoading();
                                if (task.isSuccessful()) {
                                  //  Cache cache = new Cache();
                                   /* UserDoco userDoco = Cache.getInstance().get("user", UserDoco.class);
                                    userDoco.setIs_active(true);
                                    userDoco.setUser_name(username[0]);
                                    userDoco.setEmail(emailtmp);
                                    Cache.getInstance().add("user", userDoco);*/
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isActive", true);
                                    editor.putString("username", username[0]);
                                    editor.putString("email", emailtmp);
                                    editor.commit();

                                    view.goToMainActivity();
                                } else {
                                    view.showErrorRegister(task.getException());
                                }
                            }
                        });
            } else {
                view.hideLoading();
                view.showNotification("Para crear un nuevo usuario debe aceptar los terminos y condiciones de Doco");
            }
        }
    }
}
