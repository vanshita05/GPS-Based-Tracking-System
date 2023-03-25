package com.example.gbts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Thread th=new Thread(){

            @Override

            public void run() {
                try {
                    sleep(4000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent obj=new Intent(Splash.this,MainIntro.class);
                    startActivity(obj);
                }
            }


        };

        th.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    }

