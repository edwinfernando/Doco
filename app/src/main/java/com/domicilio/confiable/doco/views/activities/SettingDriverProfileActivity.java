package com.domicilio.confiable.doco.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingDriverProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private FirebaseUser user;

    @Bind(R.id.profile_image_user)
    ImageView imageProfile;

    @Bind(R.id.edt_user_name_profile)
    EditText edt_user_name_profile;

    @Bind(R.id.edt_user_last_name_profile)
    EditText edt_user_last_name_profile;

    @Bind(R.id.edt_user_profile_email)
    EditText edt_user_profile_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(user.getDisplayName());
        // toolbar.setLogo(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(this);

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        imageProfile.setImageDrawable(Utilities.bitmapDrawable(this,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_profile_setting))));

        //loadImageParallax(R.drawable.profile);

        edt_user_name_profile.setText(user.getDisplayName());
        edt_user_profile_email.setText(user.getEmail());

        //toolbar.setTitleTextColor(getResources().getColor(R.color.ColorPrimary));
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
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
                builder = new AlertDialog.Builder(this);
                builder.setTitle("");
                builder.setMessage(getResources().getString(R.string.msg_delete_account));
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(SettingDriverProfileActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            case R.id.opt_sign_off:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("");
                builder.setMessage(getResources().getString(R.string.msg_sign_off));
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(SettingDriverProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
