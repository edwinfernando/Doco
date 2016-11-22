package com.domicilio.confiable.doco.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;

public class SettingUserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_profile);

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
}
