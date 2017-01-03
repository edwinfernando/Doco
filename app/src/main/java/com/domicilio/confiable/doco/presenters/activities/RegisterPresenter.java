package com.domicilio.confiable.doco.presenters.activities;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.model.UserDoco;
import com.domicilio.confiable.doco.util.Cache;
import com.domicilio.confiable.doco.views.activities.IRegisterView;
import com.domicilio.confiable.doco.views.activities.RegisterActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public class RegisterPresenter implements IRegisterPresenter {
    private IRegisterView view;
    private FirebaseAuth mAuth;
    private Context context;
    private boolean chbox = false;

    private static final int RC_SIGN_IN = 1;
    GoogleApiClient mGoogleApiClient;

    public RegisterPresenter(final IRegisterView view, final Context context) {
        this.view = view;
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean edtLoginTextChanged(String email) {
        if (email.isEmpty()) {
            view.showErrorLoginText(true, 0);
            return true;
        } else if (!(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            view.showErrorLoginText(true, 1);
            return true;
        } else {
            view.showErrorLoginText(false, 0);
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
        if (!edtLoginTextChanged(email) && !edtPasswordTextChanged(password)) {
            view.showLoading();
            if (chbox) {
                mAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                view.hideLoading();
                                if (task.isSuccessful()) {
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

    @Override
    public void onClickButtonGoogle() {
        view.signInGoogle();
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            // firebaseAuthWithGoogle(account);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                view.goToMainActivity();
                            }else {
                                Log.w("TAG", "signInWithCredential", task.getException());
                                view.showNotification("Authentication failed.");
                            }
                        }
                    });
        } else {
            // Google Sign In failed, update UI appropriately
            // ...
            view.showNotification("Ocurrio una falla de autenticación: "+result.getStatus());
            Log.i("Status", "Ocurrio una falla de autenticación: "+result.getStatus());
        }

    }
}
