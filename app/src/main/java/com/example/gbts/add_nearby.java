package com.example.gbts;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class add_nearby extends BaseDrawerActivity  {

    EditText lname , lat , longi , hs , fs , ps ;
    Button add;
    String slname , slat , slongi , shs , sfs , sps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_nearby, frameLayout);
        setTitle("Add Near By Places");


        lname = findViewById(R.id.lname);
        lat = findViewById(R.id.lat);
        longi = findViewById(R.id.longitude);
        hs = findViewById(R.id.hs);
        fs = findViewById(R.id.fs);
        ps = findViewById(R.id.ps);

        add = findViewById(R.id.btn_add);






        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adddata();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(5).setChecked(true);
    }






    public void adddata()
    {
        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog pd = new ProgressDialog(com.example.gbts.add_nearby.this);
        pd.setMessage("");
        pd.show();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        pd.dismiss();
                    }
                }, 3000);
    }





    public void onLoginSuccess()
    {


        slname = lname.getText().toString();
        slat = lat.getText().toString();
        slongi = longi.getText().toString();
        shs = hs.getText().toString();
        sps = ps.getText().toString();
        sfs = fs.getText().toString();


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
                params.put("LOCATION_NAME",slname);
                params.put("LATITUDE",slat);
                params.put("LONGITUDE",slongi);
                params.put("POLICE_STATION",sps);
                params.put("FIRE_STATION",sfs);
                params.put("HOSPITAL",shs);

                Log.e("sname",slname);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADDNEARBY, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();



    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "OOpss error occured!", Toast.LENGTH_LONG).show();

    }

    public boolean validate() {



        slname = lname.getText().toString();
        slat = lat.getText().toString();
        slongi = longi.getText().toString();
        shs = hs.getText().toString();
        sps = ps.getText().toString();
        sfs = fs.getText().toString();


        boolean valid = true;



        if (slname.isEmpty()) {
            lname.setError("enter text");
            valid = false;
        } else {
            lname.setError(null);
        }

        if (slat.isEmpty()) {
            lat.setError("enter text");
            valid = false;
        } else {
            lat.setError(null);
        }

        if (slongi.isEmpty()) {
            longi.setError("enter text");
            valid = false;
        } else {
            longi.setError(null);
        }

        if (shs.isEmpty()) {
            hs.setError("enter text");
            valid = false;
        } else {
            hs.setError(null);
        }

        if (sps.isEmpty()) {
            ps.setError("enter text");
            valid = false;
        } else {
            ps.setError(null);
        }

        if (sfs.isEmpty()) {
            fs.setError("enter text");
            valid = false;
        } else {
            fs.setError(null);
        }


        return valid;
    }

}
