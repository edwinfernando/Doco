package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.domicilio.confiable.doco.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_start_session;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View current = getCurrentFocus();
        if (current != null) current.clearFocus();

        btn_start_session = (Button) findViewById(R.id.btn_start_session);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_start_session.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_start_session:
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
