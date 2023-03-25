package com.example.gbts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String LOGIN_ID = "keyusername";
    private static final String EMAIL_ID = "keyemail";
    private static final String PASSWORD = "keygender";
    private static final String PHONE_NO = "keyid";
    private static final String DP = "keyphone";
    private static final String ROLE = "keyadd";
    private static final String STATUS = "keystate";
    private static final String fname = "keycity";
    private static final String NAME = "keycountry";
    private static final String ADDRESS = "keyprofile";
    private static final String EMERGENCY_CONTACT = "keyprofile";
    private static final String CITY_ID = "keyprofile";
    private static final String STATE_ID = "keyprofile";
    private static final String F_ID = "keyprofile";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOGIN_ID, user.getLOGIN_ID());
        editor.putString(EMAIL_ID, user.getEMAIL_ID());
        editor.putString(PASSWORD, user.getPASSWORD());
        editor.putString(PHONE_NO, user.getPHONE_NO());
        editor.putString(ROLE, user.getROLE());
        editor.putString(DP, user.getDP());
        editor.putString(STATUS, user.getSTATUS());
        editor.putString(NAME, user.getNAME());
        editor.putString(ADDRESS, user.getADDRESS());
        editor.putString(EMERGENCY_CONTACT, user.getEMERGENCY_CONTACT());
        editor.putString(CITY_ID, user.getCITY_ID());
        editor.putString(STATE_ID, user.getSTATE_ID());
        editor.putString(fname, user.getFNAME());
        editor.putString(F_ID, user.getFID());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_ID, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(LOGIN_ID,null),
                sharedPreferences.getString(EMAIL_ID, null),
                sharedPreferences.getString(PASSWORD, null),
                sharedPreferences.getString(PHONE_NO, null),
                sharedPreferences.getString(ROLE, null),
                sharedPreferences.getString(STATUS, null),
                sharedPreferences.getString(DP, null),
                sharedPreferences.getString(NAME, null),
                sharedPreferences.getString(ADDRESS, null),
                sharedPreferences.getString(EMERGENCY_CONTACT, null),
                sharedPreferences.getString(CITY_ID, null),
                sharedPreferences.getString(STATE_ID, null),
                sharedPreferences.getString(fname, null),
                sharedPreferences.getString(F_ID, null)






        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}