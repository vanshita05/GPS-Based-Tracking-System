package com.example.gbts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";



    EditText _nameText;
    EditText _addressText;
    EditText _emailText;
    EditText _mobileText;
    EditText _passwordText;
    EditText _reEnterPasswordText;
    EditText _useid;
    EditText _emecontact;
    Button _signupButton;
    TextView _loginLink;
    ImageView profile;

    String name,cityname,statename;
    String address,email,mobile,password,reEnterPassword,ec,uid;
    Spinner city,state;
    ArrayList<String> citylist,statelist;
    List<Map<String, Object>> rData = new ArrayList<>();
    List<Map<String, Object>> rData1 = new ArrayList<>();
    ArrayAdapter<String> cityadapter,stateadapter;
    byte[] b;
    String URL="https://gpsbasedtracking.000webhostapp.com/API/manage_state.php";
    String URL2 = "https://gpsbasedtracking.000webhostapp.com/API/manage_city.php";

    File imageFile;
    String path = null , fileExtension , mime , imageName;
    private int GALLERY = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //ButterKnife.bind(this);

        _nameText = findViewById(R.id.input_name);
        _addressText = findViewById(R.id.input_address);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _mobileText = findViewById(R.id.input_mobile);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);
        _useid = findViewById(R.id.input_userid);
        _emecontact = findViewById(R.id.input_emecontact);
        profile = findViewById(R.id.profile);

        name = _nameText.getText().toString();
        address = _addressText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        citylist = new ArrayList<String>();
        statelist = new ArrayList<String>();

        loadSpinnerData1(URL2);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String country=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
//                Toast.makeText(getApplicationContext(),country, Toast.LENGTH_LONG).show();

                Map<String, Object> selectedItem = rData1.get(city.getSelectedItemPosition());
                String name = selectedItem.get("name").toString();
                cityname = selectedItem.get("id").toString();

                // Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        loadSpinnerData(URL);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String country=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
//                Toast.makeText(getApplicationContext(),country, Toast.LENGTH_LONG).show();

                Map<String, Object> selectedItem = rData.get(state.getSelectedItemPosition());
                String name = selectedItem.get("name").toString();
                statename = selectedItem.get("id").toString();

                // Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });











    }


    // end of oncreate method
    private void loadSpinnerData(String url)
    {

        final ProgressDialog pd = new ProgressDialog(SignupActivity.this);
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

                    SimpleAdapter arrayAdapter = new SimpleAdapter(SignupActivity.this, rData,
                            android.R.layout.simple_spinner_dropdown_item,
                            new String[]{"name"}, new int[]{android.R.id.text1});
                    state.setAdapter(arrayAdapter);


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

    private void loadSpinnerData1(String url)
    {

        final ProgressDialog pd = new ProgressDialog(SignupActivity.this);
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
                        JSONArray jsonArray=jsonObject.getJSONArray("city");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String country=jsonObject1.getString("CITY_NAME");
                            String id=jsonObject1.getString("CITY_ID");

                            Map<String, Object> item = new HashMap<>();
                            item.put("id", id);
                            item.put("name",country);
                            rData1.add(item);
                            // CountryName.add(country);
                        }
                    }

                    // spinner.setAdapter(new ArrayAdapter<String>(add_state.this, android.R.layout.simple_spinner_dropdown_item, CountryName));

                    SimpleAdapter arrayAdapter = new SimpleAdapter(SignupActivity.this, rData1,
                            android.R.layout.simple_spinner_dropdown_item,
                            new String[]{"name"}, new int[]{android.R.id.text1});
                    city.setAdapter(arrayAdapter);


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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"};


        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Toast.makeText(SignupActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    profile.setImageBitmap(bitmap);

                    path = getPath(contentURI);
                    imageFile = new File(path);
                    Uri uris = Uri.fromFile(imageFile);
                    fileExtension = MimeTypeMap.getFileExtensionFromUrl(uris.toString());
                    mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
                    imageName = imageFile.getName();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignupActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    //end of image code

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();



        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess()
    {

        name = _nameText.getText().toString();
        address = _addressText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();
        ec = _emecontact.getText().toString().trim();
        uid = _useid.getText().toString().trim();

        new Thread(new Runnable() {
            public void run() {
                // call send message here
                OkHttpClient okHttpClient = new OkHttpClient();

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("dp", imageName, RequestBody.create(imageFile, MediaType.parse(mime)))
                        .addFormDataPart("name",name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("phone", mobile)
                        .addFormDataPart("password", password)
                        .addFormDataPart("address", address)
                        .addFormDataPart("city", cityname)
                        .addFormDataPart("state", statename)
                        .addFormDataPart("econtact",ec)
                        .addFormDataPart("userid", uid)
                        .build();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(URLs.URL_REGISTER)
                        .post(requestBody)
                        .build();

                okhttp3.Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response.body().string());

                    if(!jsonObject.getBoolean("error"))
                    {
                         Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                         Intent i = new Intent(SignupActivity.this,LoginActivity.class);
                         startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    Log.e("asd", String.valueOf(jsonObject));



                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {

        name = _nameText.getText().toString();
        address = _addressText.getText().toString();
        email = _emailText.getText().toString();
        mobile = _mobileText.getText().toString();
        password = _passwordText.getText().toString().trim();
        reEnterPassword = _reEnterPasswordText.getText().toString().trim();
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
            Log.e("error in",name);
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
            Log.e("error in",address);
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
            Log.e("error in",email);
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
            Log.e("error in",mobile);
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
            Log.e("error in",password);
        } else {
            _passwordText.setError(null);
        }

        if(reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("password do not match");
            valid = false;
            Log.e("error in",reEnterPassword);

        }
        else
        {

            _reEnterPasswordText.setError(null);
        }
        return valid;
    }




    //new image code





    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }




}