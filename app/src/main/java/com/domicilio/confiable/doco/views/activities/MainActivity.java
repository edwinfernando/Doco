package com.domicilio.confiable.doco.views.activities;

import android.animation.Animator;
import android.os.Bundle;

import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.animation.AnimatorListenerCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.domicilio.confiable.doco.R;
import com.domicilio.confiable.doco.views.fragments.MapsFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public static FloatingActionMenu menuYellow;
  //  private List<FloatingActionMenu> menus = new ArrayList<FloatingActionMenu>();
  //  private Handler mUiHandler = new Handler();

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

       /* FloatingSearchView floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        floatingSearchView.setOnQueryChangeListener(this);*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_fragment, new MapsFragment()).commit();

        menuYellow = (FloatingActionMenu) findViewById(R.id.menu_yellow);

        final FloatingActionButton fab_conductores = (FloatingActionButton) findViewById(R.id.fab_conductores);
        fab_conductores.setOnClickListener(this);


        FloatingActionButton fab_pedidos = (FloatingActionButton) findViewById(R.id.fab_pedidos);
        fab_pedidos.setOnClickListener(this);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pide tú doco", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        menuYellow.setAlpha(0.9f);

        menuYellow.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    menuYellow.setAlpha(1f);
                } else {
                    menuYellow.setAlpha(0.9f);
                }
            }
        });

       /* menus.add(menuYellow);
        menuYellow.hideMenuButton(false);

        //Step 4
        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }*/

        menuYellow.setClosedOnTouchOutside(true);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } if(menuYellow.isOpened()) {
            menuYellow.close(true);
        }else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_conductores:
                break;
            case R.id.fab_pedidos:
                break;
        }
    }
}
