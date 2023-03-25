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

public class add_state extends BaseDrawerActivity {

    EditText sname;
    Button add;
    String coname;

    ArrayList<String> CountryName;
    List<Map<String, Object>> rData = new ArrayList<>();
    String cid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_add_state, frameLayout);
        setTitle("state");


        sname = findViewById(R.id.input_cname);

        add = findViewById(R.id.btn_add);
        CountryName=new ArrayList<>();






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
        navigationView.getMenu().getItem(1).setChecked(true);
    }






    public void adddata()
    {
        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog pd = new ProgressDialog(com.example.gbts.add_state.this);
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
                params.put("sname",coname);

                Log.e("sname",coname);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ADDSTATE, params);
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
