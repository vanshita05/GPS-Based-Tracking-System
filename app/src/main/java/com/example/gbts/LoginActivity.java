package com.example.gbts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _link_signup;

    String email,password,role;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

            User user = SharedPrefManager.getInstance(this).getUser();
            role = user.getROLE();

            if(role.equals("0")) {
                startActivity(new Intent(this, admin.class));
            }
            else
            {
                startActivity(new Intent(this, userdash.class));
            }
            return;

        }


        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _link_signup = findViewById(R.id.link_signup);


        _link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }



    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess()
    {


        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

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
                        // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");
                        JSONObject userJson1 = obj.getJSONObject("userdetail");



//                        //creating a new user object
                        User user = new User(
                                userJson.getString("LOGIN_ID"),
                                userJson.getString("EMAIL_ID"),
                                userJson.getString("PASSWORD"),
                                userJson.getString("PHONE_NO"),
                                userJson.getString("ROLE"),
                                userJson.getString("STATUS"),
                                userJson.getString("DP"),
                                userJson1.getString("NAME"),
                                userJson1.getString("ADDRESS"),
                                userJson1.getString("EMERGENCY_CONTACT"),
                                userJson1.getString("CITY_ID"),
                                userJson1.getString("STATE_ID"),
                                obj.getString("fname"),
                                userJson1.getString("USER_ID")




                        );

                        Log.e("email",userJson.getString("EMAIL_ID"));
                        Log.e("email",userJson.getString("PHONE_NO"));
                        //Log.e("dffdg",userJson1.getString("address"));
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);



                        if(userJson.getString("ROLE").equals("0")) {
                            Intent Page = new Intent(LoginActivity.this, admin.class);
                            startActivity(Page);
                        }
                        else
                        {
                            Intent Page = new Intent(LoginActivity.this, userdash.class);
                            startActivity(Page);
                        }



                        //starting the profile activity


                    } else {

                        Toast.makeText(getApplicationContext(), String.valueOf(obj.getString("message")), Toast.LENGTH_SHORT).show();
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
                params.put("email", email);
                params.put("password", password);

                Log.e("pass",password);
                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();


    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {


        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
