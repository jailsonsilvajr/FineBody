package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.textfield.TextInputEditText;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.database.firebase.Response;
import com.jjsj.finebodyapp.preferences.PreferenceLogged;
import com.jjsj.finebodyapp.viewmodels.ViewModelLogin;

public class LoginActivity extends AppCompatActivity {

    private ViewModelLogin viewModelLogin;

    private ProgressBar progressBar;
    private TextInputEditText textInputLayout_email;
    private TextInputEditText textInputLayout_password;
    private Button buttonEnter;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.progressBar = findViewById(R.id.layout_login_progressBar);
        this.progressBar.setVisibility(View.GONE);

        this.textInputLayout_email = findViewById(R.id.layout_login_textInput_email);
        this.textInputLayout_password = findViewById(R.id.layout_login_textInput_password);

        this.buttonEnter = findViewById(R.id.layout_login_button_enter);
        this.buttonRegister = findViewById(R.id.layout_login_button_register);

        this.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogin();
            }
        });

        this.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivityRegister();
            }
        });

        this.viewModelLogin = new ViewModelProvider(this).get(ViewModelLogin.class);
        this.viewModelLogin.getLiveDataResponseLogin().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                progressBar.setVisibility(View.GONE);
                if(response.getStatus() != 200){

                    new MaterialAlertDialogBuilder(LoginActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertLoginFail))
                            .setMessage(getResources().getString(R.string.MessageAlertLoginFail))
                            .show();
                }else{
                    insertIdCoachInPreferences(String.class.cast(response.getObject()));
                    openActivityStudents();
                }
            }
        });
    }

    private void doLogin(){

        String email = this.textInputLayout_email.getText().toString();
        String password = this.textInputLayout_password.getText().toString();

        if(email.isEmpty() || password.isEmpty()){

            new MaterialAlertDialogBuilder(this)
                    .setTitle(getResources().getString(R.string.TitleAlertEmailOrPasswordInvalid))
                    .setMessage(getResources().getString(R.string.MessageAlertEmailOrPasswordInvalid))
                    .show();
        }else{

            this.progressBar.setVisibility(View.VISIBLE);
            this.viewModelLogin.doLogin(email, password);
        }
    }

    private void openActivityStudents(){

        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
        finish();
    }

    private void openActivityRegister(){

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void insertIdCoachInPreferences(String idCoach){

        PreferenceLogged preferenceLogged = new PreferenceLogged(this);
        preferenceLogged.setPreference(idCoach);
    }
}
