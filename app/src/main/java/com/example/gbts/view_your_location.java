package com.example.gbts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class view_your_location extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {


private GoogleMap mMap;
        double latitude = 0;
        double longitude = 0;
        double latitude2 = 0;
        double longitude2 = 0;
        LatLng position;
        String fid;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_location);

        User user = SharedPrefManager.getInstance(this).getUser();
        fid = user.getFID();

        checkdata();

        latitude = getIntent().getDoubleExtra("lat", 0);
        longitude = getIntent().getDoubleExtra("long", 0);
        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment)
        getSupportFragmentManager().findFragmentById(R.id.map);
        fm.getMapAsync(view_your_location.this);

        }

@Override
public void onConnected(Bundle bundle) {
        /* getDirection();*/
        /*getCurrentLocation();*/
        }

@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onConnectionFailed(ConnectionResult connectionResult) {

        }

@Override
public void onMapReady(GoogleMap googleMap) {

//        latitude = getIntent().getDoubleExtra("lat", 0);
//
//        // Receiving longitude from MainActivity screen
//        longitude = getIntent().getDoubleExtra("long", 0);


        latitude2 = 73.998;

        // Receiving longitude from MainActivity screen
        longitude2 = 22.4567;

        position = new LatLng(latitude, longitude);

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);

        // Setting title for the MarkerOptions
        options.title("Location");

        // Setting snippet for the MarkerOptions
        // Adding Marker on the Google Map
        try {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude,
        longitude, 1);
        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getAddressLine(1);
        String countryName = addresses.get(0).getAddressLine(2);
        options.snippet(cityName + "," + stateName);

        } catch (Exception ex) {
        ex.getMessage();
        options.snippet("Latitude:" + latitude + ",Longitude:" + longitude);

        }
        googleMap.addMarker(options);
        googleMap.addMarker(options.position(new LatLng(latitude2,
        longitude2)));

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

        // Updating the camera position to the user input latitude and longitude
        googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
        googleMap.animateCamera(updateZoom);


        }


@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      /*
        getMenuInflater().inflate(R.menu.main, menu);
      */
        return true;
        }



        public void checkdata()
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

                            latitude = obj.getDouble("LATITUDE");
                            longitude = obj.getDouble("LONGITUDE");


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