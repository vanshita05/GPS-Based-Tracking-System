package com.example.gbts;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class userdash extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView set_email;
    TextView set_name;
    ImageView dp;
    double latitude = 0;
    double longitude = 0;

    private GpsTracker gpsTracker;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    double getlat;
    double getlong;
    String uid,fname,fid;
    TextView tv1;

    CardView cardView,cardView2,cardView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdash);
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
        dp = headerView.findViewById(R.id.tv_profile);

        User user = SharedPrefManager.getInstance(this).getUser();
        set_email.setText(user.getEMAIL_ID());
        set_name.setText(user.getNAME());
        uid = user.getLOGIN_ID();
        fname = user.getFNAME();
        fid = user.getFID();

        Log.e("asd",fid);
        cardView = findViewById(R.id.sendemergency);
        cardView2 = findViewById(R.id.friendlocation);
        cardView3 = findViewById(R.id.viewlocation);
        tv1 = findViewById(R.id.friendname);
        tv1.setText("VIEW LOCATION OF "+ fname.toUpperCase());


        String path = "https://gpsbasedtracking.000webhostapp.com/API/" + user.getDP();
        Glide.with(this).load(path).into(dp);

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        gpsTracker = new GpsTracker(userdash.this);
        if(gpsTracker.canGetLocation()){
            getlat = gpsTracker.getLatitude();
            getlong = gpsTracker.getLongitude();

        }else{
            gpsTracker.showSettingsAlert();
        }


        if(getlat>0) {
           updatelocation(URLs.URL_UPDATELOCATION);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getlat>0)
                {
                    updatelocation(URLs.URL_EMERGENCY);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Not Able to Send Emergency.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                checkdata();


            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(userdash.this, MainActivity.class);
                startActivity(i);

            }
        });




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

        //noinspection SimplifiableIfStatement
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


        if (id == R.id.nav_viewnearby2) {
            Intent i = new Intent(getApplicationContext(),Display_Nearbyuser.class);
            startActivity(i);
        }
        else if (id == R.id.nav_logout) {
            finish();
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
        else if (id == R.id.nav_home) {
            Intent i = new Intent(userdash.this, userdash.class);
            startActivity(i);
        }
        else if (id == R.id.nav_viewyourlocation) {
            Intent i = new Intent(userdash.this, MainActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_flocation) {
            checkdata();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updatelocation(final String url)
    {




        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("LOGIN_ID", uid);
                params.put("LATITUDE", String.valueOf(getlat));
                params.put("LONGITUDE",String.valueOf(getlong));


                //returing the response
                return requestHandler.sendPostRequest(url, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();



    }


    public void checkdata()
    {


        final ProgressDialog progressDialog = new ProgressDialog(userdash.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting Location...");
        progressDialog.show();



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        progressDialog.dismiss();
                    }
                }, 2000);

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONObject userJson = obj.getJSONObject("data");

                        latitude = userJson.getDouble("LATITUDE");
                        longitude = userJson.getDouble("LONGITUDE");

                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);


                    } else {

                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("LOGIN_ID", fid);



                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_TRACK, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();



    }

}
