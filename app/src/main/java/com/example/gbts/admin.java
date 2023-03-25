package com.example.gbts;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView set_email;
    TextView set_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        if (id == R.id.action_logout) {

            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addstate) {
            Intent i = new Intent(admin.this,add_state.class);
            startActivity(i);
        } else if (id == R.id.nav_viewstate) {
            Intent i = new Intent(getApplicationContext(),Display_state.class);
            startActivity(i);
        } else if (id == R.id.nav_addcity) {
            Intent i = new Intent(admin.this,add_city.class);
            startActivity(i);
        } else if (id == R.id.nav_viewcity) {
            Intent i = new Intent(getApplicationContext(),Display_data.class);
            startActivity(i);
        } else if (id == R.id.nav_addnearby) {
            Intent i = new Intent(getApplicationContext(),add_nearby.class);
            startActivity(i);
        } else if (id == R.id.nav_viewnearby) {
            Intent i = new Intent(getApplicationContext(),Display_Nearby.class);
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(admin.this,admin.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
