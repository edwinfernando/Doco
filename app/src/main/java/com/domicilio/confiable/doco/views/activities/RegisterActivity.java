package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.domicilio.confiable.doco.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setLogo(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(this);

        //toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));
        getSupportActionBar().setTitle("");
    }


    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}
