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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class add_city extends BaseDrawerActivity  {

    EditText sname;
    Button add;
    String coname;
    Spinner spinner;
    ArrayList<String> CountryName;
    List<Map<String, Object>> rData = new ArrayList<>();
    String cid;
    String URL="https://gpsbasedtracking.000webhostapp.com/API/manage_state.php";

    File imageFile;
    String path = null , fileExtension , mime , imageName;
    private int GALLERY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_city, frameLayout);
        setTitle("City");

        sname = findViewById(R.id.input_cname);
        spinner = findViewById(R.id.cnames);
        add = findViewById(R.id.btn_add);
        CountryName=new ArrayList<>();

        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String country=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
//                Toast.makeText(getApplicationContext(),country, Toast.LENGTH_LONG).show();

                Map<String, Object> selectedItem = rData.get(spinner.getSelectedItemPosition());
                String name = selectedItem.get("name").toString();
                cid = selectedItem.get("id").toString();

                // Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

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
        navigationView.getMenu().getItem(3).setChecked(true);
    }


    private void loadSpinnerData(String url)
    {

        final ProgressDialog pd = new ProgressDialog(add_city.this);
        pd.setMessage("loading");
        pd.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        pd.dismiss();
                    }
                }, 2000);

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getBoolean("error")==false){
                        JSONArray jsonArray=jsonObject.getJSONArray("state");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String country=jsonObject1.getString("STATE_NAME");
                            String id=jsonObject1.getString("STATE_ID");

                            Map<String, Object> item = new HashMap<>();
                            item.put("id", id);
                            item.put("name",country);
                            rData.add(item);
                            // CountryName.add(country);
                        }
                    }

                    // spinner.setAdapter(new ArrayAdapter<String>(add_state.this, android.R.layout.simple_spinner_dropdown_item, CountryName));

                    SimpleAdapter arrayAdapter = new SimpleAdapter(add_city.this, rData,
                            android.R.layout.simple_spinner_dropdown_item,
                            new String[]{"name"}, new int[]{android.R.id.text1});
                    spinner.setAdapter(arrayAdapter);


                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);




    }

    public void adddata()
    {
        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog pd = new ProgressDialog(add_city.this);
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


        coname = sname.getText().toString();


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
                params.put("sname",cid);
                params.put("cityname",coname);

                Log.e("pass",coname);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADDCITY, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();



    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

    }

    public boolean validate() {


        coname = sname.getText().toString();



        boolean valid = true;



        if (coname.isEmpty()) {
            sname.setError("enter name");
            valid = false;
        } else {
            sname.setError(null);
        }


        return valid;
    }




}
