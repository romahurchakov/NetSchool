package com.example.mipro.netschool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mipro.netschool.Autentification.Autentification;
import com.example.mipro.netschool.Autentification.SchoolList;
import com.example.mipro.netschool.Diary.ClientSocketHelper;
import com.example.mipro.netschool.Diary.Diary;
import com.example.mipro.netschool.Resources.GroupList;
import com.example.mipro.netschool.Settings.Settings;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static ClientSocketHelper SOCKET = new ClientSocketHelper();
    public final static String LOG_TAG = "nsLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ( !ft.isEmpty()) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setTitle("NetSchool");
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (id) {
            case R.id.nav_diary: {
                ft.replace(R.id.flMAIN, new Diary());
                ft.addToBackStack("stack");
                ft.commit();
                break;
            }
            case R.id.nav_timetable: {
                ft.replace(R.id.flMAIN, new Timetable());
                ft.addToBackStack("stack");
                ft.commit();
                break;
            }
            case R.id.nav_settings: {
                ft.replace(R.id.flMAIN, new Settings());
                ft.addToBackStack("stack");
                ft.commit();
                break;
            }
            case R.id.nav_t_materials: {
                ft.replace(R.id.flMAIN, new GroupList());
                ft.addToBackStack("stack");
                ft.commit();
                break;
            }
            default: {
                Intent intent = new Intent(this, Autentification.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
