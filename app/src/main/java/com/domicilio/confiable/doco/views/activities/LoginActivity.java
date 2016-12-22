package com.domicilio.confiable.doco.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.presenters.activities.ILoginPresenter;
import com.domicilio.confiable.doco.presenters.activities.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private ILoginPresenter loginPresenter;
    ProgressDialog progress;

    @Bind(R.id.edt_login_layout)
    TextInputLayout edt_login_layout;

    @Bind(R.id.edt_password_layout)
    TextInputLayout edt_password_layout;

    @Bind(R.id.edt_login)
    EditText edt_login;

    @Bind(R.id.edt_password)
    EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        View current = getCurrentFocus();
        if (current != null) current.clearFocus();

        loginPresenter = new LoginPresenter(this, this);
    }

    @Override
    public void showLoading() {
        progress = ProgressDialog.show(this, "Procesando", "Comprobando datos de ingreso");
    }

    @Override
    public void hideLoading() {
        progress.dismiss();
    }


    @OnClick(R.id.btn_start_session)
    @Override
    public void toEnter() {
        String email = edt_login.getText().toString();
        String password = edt_password.getText().toString();

        loginPresenter.toEnter(email, password);
    }

    @OnClick(R.id.btn_register)
    @Override
    public void createRegister() {
        loginPresenter.createRegister();
    }

   /* @OnCheckedChanged(R.id.chbox_remember_me)
    @Override
    public void isChboxAccept(boolean chbox) {
        loginPresenter.isChboxAccept(chbox);
    }*/

    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorAutentication(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLoginText(boolean error, int type) {
        if (error) {
            if (type == 0)
                edt_login_layout.setError(getString(R.string.err_msg_login_no_empty));
            else
                edt_login_layout.setError(getString(R.string.err_msg_login));
            edt_login.requestFocus();
        } else {
            edt_login_layout.setErrorEnabled(false);
        }
    }

    @Override
    public void showErrorPasswordText(boolean error) {
        if (error) {
            edt_password_layout.setError(getString(R.string.err_msg_password));
            edt_password.requestFocus();
        } else {
            edt_password_layout.setErrorEnabled(false);
        }
    }
}
