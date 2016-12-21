package com.domicilio.confiable.doco.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.presenters.activities.IRegisterPresenter;
import com.domicilio.confiable.doco.presenters.activities.RegisterPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static java.security.AccessController.getContext;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IRegisterView {

    IRegisterPresenter registerPresenter;
    ProgressDialog progress;

    @Bind(R.id.edt_login_register_layout)
    TextInputLayout edt_login_register_layout;

    @Bind(R.id.edt_password_register_layout)
    TextInputLayout edt_password_register_layout;

    @Bind(R.id.edt_login_register)
    EditText edt_login_register;

    @Bind(R.id.edt_password_register)
    EditText edt_password_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        getSupportActionBar().setTitle("");

        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenter(this,this);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public void showLoading() {
        progress = ProgressDialog.show(this,"Procesando","Creando la cuenta");
    }

    @Override
    public void hideLoading() {
        progress.dismiss();
    }

    @OnCheckedChanged(R.id.chbox_accept_terms)
    public void isChboxAccept(boolean chbox) {
        registerPresenter.isChboxAccept(chbox);
    }

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_register_me)
    public void register(){
        String email = edt_login_register.getText().toString();
        String password = edt_password_register.getText().toString();
        registerPresenter.register(email,password);
    }

    @Override
    public void showErrorRegister(Exception e) {
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotification(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLoginText(boolean error, int type) {
        if (error) {
            if (type == 0)
                edt_login_register_layout.setError(getString(R.string.err_msg_login_no_empty));
            else
                edt_login_register_layout.setError(getString(R.string.err_msg_login));
            edt_login_register.requestFocus();
        } else {
            edt_login_register_layout.setErrorEnabled(false);
        }
    }

    @Override
    public void showErrorPasswordText(boolean error) {
        if (error) {
            edt_password_register_layout.setError(getString(R.string.err_msg_password));
            edt_password_register.requestFocus();
        } else {
            edt_password_register_layout.setErrorEnabled(false);
        }
    }
}
