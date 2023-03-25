package com.example.gbts;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

        DrawerLayout drawerLayout;
        Toolbar toolbar;
        FrameLayout frameLayout;
        NavigationView navigationView;
        TextView set_email;
        TextView set_name;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_admin);;

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            frameLayout = (FrameLayout) findViewById(R.id.content_frame);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerView = navigationView.getHeaderView(0);
            set_email = headerView.findViewById(R.id.tvemail);
            set_name = headerView.findViewById(R.id.tvname);
            User user = SharedPrefManager.getInstance(this).getUser();
            set_email.setText(user.getEMAIL_ID());
            set_name.setText(user.getNAME());
        }

        @Override
        public void onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int id = item.getItemId();

//            //to prevent current item select over and over
//        if (item.isChecked()){
//            drawerLayout.closeDrawer(GravityCompat.START);
//            return false;
//        }



        if (id == R.id.nav_addstate) {
            Intent i = new Intent(getApplicationContext(),add_state.class);
            startActivity(i);
        } else if (id == R.id.nav_viewstate) {
            Intent i = new Intent(getApplicationContext(),Display_state.class);
            startActivity(i);
        } else if (id == R.id.nav_addcity) {
            Intent i = new Intent(getApplicationContext(),add_city.class);
            startActivity(i);
        }else if (id == R.id.nav_viewcity) {
            Intent i = new Intent(getApplicationContext(),Display_data.class);
            startActivity(i);
        } else if (id == R.id.nav_addnearby) {
            Intent i = new Intent(getApplicationContext(),add_nearby.class);
            startActivity(i);
        } else if (id == R.id.nav_viewnearby) {
            Intent i = new Intent(getApplicationContext(),Display_Nearby.class);
            startActivity(i);
        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(),admin.class);
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    }


