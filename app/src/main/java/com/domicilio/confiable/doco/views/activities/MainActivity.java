package com.domicilio.confiable.doco.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.util.DeviceDimensionsHelper;
import com.domicilio.confiable.doco.util.Utilities;
import com.domicilio.confiable.doco.views.fragments.MapsFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        //View hView =  navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView profile_image_nav = (ImageView) hView.findViewById(R.id.profile_image_nav);
        profile_image_nav.setImageDrawable(Utilities.roundedBitmapDrawable(this,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_nav))));

        ImageView profile_image_marker = (ImageView) findViewById(R.id.profile_image_marker);
        profile_image_marker.setImageDrawable(Utilities.roundedBitmapDrawable(this,R.drawable.profile,
                (int) (DeviceDimensionsHelper.getDisplayWidth(this) * getResources().getDimension(R.dimen.size_photo_market_map))));

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (FAB_Status) {
            hideFAB();
            FAB_Status = false;
        } else {
            super.onBackPressed();
        }
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

        } else if (id == R.id.nav_promotions) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_driver_doco) {

        } else if (id == R.id.nav_configuration) {
          //  Intent intent = new Intent(MainActivity.this,SettingUserProfileActivity.class);
            Intent intent = new Intent(this,SettingDriverProfileActivity.class);
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

    private void expandFAB() {
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
}
