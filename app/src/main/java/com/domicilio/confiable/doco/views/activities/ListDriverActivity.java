package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.views.fragments.DriverAvailableFragment;

public class ListDriverActivity extends AppCompatActivity implements View.OnClickListener, FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_driver);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(this);

        //Maneja la transicion entre fragments y crea una pila de fragment
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new DriverAvailableFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        //Verificamos si hay 2 o mas fragments en la pila
        if(getSupportFragmentManager().getBackStackEntryCount()>=2){
            //Sacamos de la pila el ultimo elemento (ir al fragment anterior)
            getSupportFragmentManager().popBackStackImmediate();

            if(getSupportFragmentManager().getBackStackEntryCount()!=1){
                super.onBackPressed();
            }
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackStackChanged() {
    }
}
