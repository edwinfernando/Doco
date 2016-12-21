package com.domicilio.confiable.doco.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.model.UserDoco;
import com.domicilio.confiable.doco.util.Cache;

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setReenterTransition(new Explode());
            getWindow().setExitTransition(new Explode().setDuration(500));
        }*/

       /* Transition t3 = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t3 = TransitionInflater.from(this)
                    .inflateTransition(R.transition.explode_transition);
            getWindow().setEnterTransition(t3);

        }*/
        Context context = this;

        SharedPreferences sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        final boolean isActive = sharedPreferences.getBoolean("isActive",false);

        /*UserDoco userDoco =  Cache.getInstance().get("user", UserDoco.class);
        final boolean isActive = userDoco.is_active();*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!isActive){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 4000);
    }
}
