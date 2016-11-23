package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;

public class SettingDriverProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_driver_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // toolbar.setLogo(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(this);

        ImageView imageProfile = (ImageView) findViewById(R.id.profile_image_user);
        imageProfile.setImageDrawable(Utilities.roundedBitmapDrawable(this,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_profile_setting))));

        //toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));
        getSupportActionBar().setTitle("");
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.option_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_delete_account:

                return true;
            case R.id.opt_sign_off:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("");
                //builder.setMessage(getResources().getString(R.string.desarrollado));
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
