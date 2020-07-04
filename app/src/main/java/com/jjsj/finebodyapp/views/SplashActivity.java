package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;

public class SplashActivity extends AppCompatActivity {

    private PreferenceLogged preferenceLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.preferenceLogged = new PreferenceLogged(getApplicationContext());

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(preferenceLogged.getPreference()){

                    openActivityStudents();
                }else{

                    openActivityLogin();
                }
            }
        }, 3000);
    }

    private void openActivityLogin(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openActivityStudents(){

        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }
}