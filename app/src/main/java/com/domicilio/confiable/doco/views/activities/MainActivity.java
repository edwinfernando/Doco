package com.domicilio.confiable.doco.views.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.model.Ubication;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;
import com.domicilio.confiable.doco.views.fragments.BaseExampleFragment;
import com.domicilio.confiable.doco.views.fragments.MapsFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.domicilio.confiable.doco.util.Utilities.getMarkerBitmapFromView;


public class MainActivity extends AppCompatActivity
        implements BaseExampleFragment.BaseExampleFragmentCallbacks, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, IMainActivityView {

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    public boolean FAB_Status = false;

    //Animations
    Animation show_fab;
    Animation hide_fab;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;

    int marginX_fab, marginY_fab;

    FrameLayout.LayoutParams layoutParams;
    FrameLayout.LayoutParams layoutParams2;
    FrameLayout.LayoutParams layoutParams3;

    private DrawerLayout mDrawerLayout;

    ArrayList<MarkerOptions> drivers;
    FirebaseDatabase database;// =
    DatabaseReference reference;// = database.getReference();
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        validateGPS();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        //View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView profile_image_nav = (ImageView) hView.findViewById(R.id.profile_image_nav);
        profile_image_nav.setImageDrawable(Utilities.roundedBitmapDrawable(this, R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_nav))));

        ImageView profile_image_marker = (ImageView) findViewById(R.id.profile_image_marker);
        /**
         profile_image_marker.setImageDrawable(Utilities.roundedBitmapDrawable(this,R.drawable.profile,
         (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_market_map))));**/

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment, new MapsFragment()).commit();

        marginX_fab = (int) (DeviceDimensionsHelper.getDisplayWidth(this) * 0.25);
        marginY_fab = (int) (DeviceDimensionsHelper.getDisplayHeight(this) * 0.03);

        //Floating Action Buttons
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab_3);

        //Animations
        show_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.show_from_bottom);
        hide_fab = AnimationUtils.loadAnimation(getApplication(), R.anim.hide_to_bottom);

        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        //OnClick
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        //Firebase
        drivers = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Ubication.UBICATION);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        List fragments = getSupportFragmentManager().getFragments();
        BaseExampleFragment currentFragment = (BaseExampleFragment) fragments.get(fragments.size() - 1);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (FAB_Status) {
            hideFAB();
            FAB_Status = false;
        } else if (!currentFragment.onActivityBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onAttachSearchViewToDrawer(FloatingSearchView searchView) {
        searchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (FAB_Status) {
                    hideFAB();
                    FAB_Status = false;
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_voice_rec) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payment) {
            // Handle the camera action
        } else if (id == R.id.nav_docos) {

        } else if (id == R.id.nav_free_docos) {
            Intent intent = new Intent(this, FreeDocosActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_promotions) {
            deployDialogPromotions(this);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_driver_doco) {
            deployDialogIsDriverDoco(this);
        } else if (id == R.id.nav_configuration) {
            Intent intent = new Intent(MainActivity.this, SettingUserProfileActivity.class);
            //Intent intent = new Intent(this,SettingDriverProfileActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (!FAB_Status) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
                break;
            case R.id.fab_1:
                break;
            case R.id.fab_2:
                Intent intent = new Intent(MainActivity.this, ListDriverActivity.class);
                startActivity(intent);
                finish();
                hideFAB();
                FAB_Status = false;
                break;
            case R.id.fab_3:
                break;
        }
    }

    @Override
    public void expandFAB() {
        layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams.rightMargin += marginX_fab;
        layoutParams2.bottomMargin += marginY_fab;
        layoutParams3.leftMargin += marginX_fab;

        fab.startAnimation(hide_fab);
        fab.setClickable(false);

        //Floating Action Button 1
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }

    @Override
    public void hideFAB() {
        layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams.rightMargin -= marginX_fab;
        layoutParams2.bottomMargin -= marginY_fab;
        layoutParams3.leftMargin -= marginX_fab;

        fab.startAnimation(show_fab);
        fab.setClickable(true);

        //Floating Action Button 1
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

    @Override
    public void deployDialogPromotions(Context context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_promotions, null);

        alertBuilder.setView(layout);

        EditText edt_code_doco_free = (EditText) layout.findViewById(R.id.edt_code_doco_free);
        Button btn_register_code_doco_free = (Button) layout.findViewById(R.id.btn_register_code_doco_free);

        alertBuilder.create();
        final AlertDialog ad = alertBuilder.show();

        //Utilidades.cambiarTamanoAlertDialog(ad);

        btn_register_code_doco_free.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                }
        );
    }

    @Override
    public void deployDialogIsDriverDoco(Context context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_is_driver_doco, null);

        alertBuilder.setView(layout);

        EditText edt_insert_driver_code = (EditText) layout.findViewById(R.id.edt_insert_driver_code);
        Button btn_enter_driver_code = (Button) layout.findViewById(R.id.btn_enter_driver_code);

        alertBuilder.create();
        final AlertDialog ad = alertBuilder.show();

        //Utilidades.cambiarTamanoAlertDialog(ad);

        btn_enter_driver_code.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ad.dismiss();
                    }
                }
        );
    }

    @Override
    public void addDrivers(LatLng location) {
        reference.push().setValue(new Ubication(location.latitude, location.longitude, "Edwin"));
    }

    @Override
    public void getDrivers(final GoogleMap nMap) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot children) {
                for (DataSnapshot dataSnapshot : children.getChildren()) {
                    Log.d("Informacion--->", dataSnapshot.toString());
                    LatLng driverUbication = new LatLng(dataSnapshot.child("latitude").getValue(Double.class), dataSnapshot.child("longitude").getValue(Double.class));
                    String nameDriver = dataSnapshot.child("nameDriver").getValue(String.class);
                    Log.d("LatLng-->", driverUbication.toString() + " NameDriver--->" + nameDriver);
                    createMarkersDrivers(driverUbication, nameDriver);
                    paintDriverOnMap(nMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<MarkerOptions> getMarkersDriver() {
        return drivers;
    }


    @Override
    public void createMarkersDrivers(LatLng ubicationDriver, String nameDriver) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ubicationDriver);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(this, R.drawable.profile)));
        markerOptions.title(nameDriver);
        drivers.add(markerOptions);
    }

    @Override
    public void paintDriverOnMap(GoogleMap nMap) {

        int i = 0;
        do {
            nMap.addMarker(drivers.get(i));
            i++;
        } while (i < drivers.size());

    }

    @Override
    public void validateGPS() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .build();
            mGoogleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            //**************************
            builder.setAlwaysShow(true); //this is the key ingredient
            //**************************

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can initialize location
                            // requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        MainActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            });

        }
    }
}