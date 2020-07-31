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
import com.jjsj.finebodyapp.viewmodels.ViewModelRegister;

public class RegisterActivity extends AppCompatActivity {

    private ViewModelRegister viewModelRegister;

    private ProgressBar progressBar;
    private TextInputEditText textInputEditText_email;
    private  TextInputEditText textInputEditText_password;
    private  TextInputEditText textInputEditText_repeat_password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.progressBar = findViewById(R.id.layout_register_progressBar);
        this.progressBar.setVisibility(View.GONE);

        this.textInputEditText_email = findViewById(R.id.layout_register_textInput_email);
        this.textInputEditText_password = findViewById(R.id.layout_register_textInput_password);
        this.textInputEditText_repeat_password = findViewById(R.id.layout_register_textInput_repeat_password);

        this.button = findViewById(R.id.layout_register_button_register);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doRegister();
            }
        });

        this.viewModelRegister = new ViewModelProvider(this).get(ViewModelRegister.class);
        this.viewModelRegister.getLiveDataResponseRegister().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {

                progressBar.setVisibility(View.GONE);
                if(response.getStatus() != 200){

                    new MaterialAlertDialogBuilder(RegisterActivity.this)
                            .setTitle(getResources().getString(R.string.TitleAlertRegisterFail))
                            .setMessage(getResources().getString(R.string.MessageAlertRegisterFail))
                            .show();
                }else{

                    insertIdCoachInPreferences(String.class.cast(response.getObject()));
                    openActivityStudents();
                    finish();
                }
            }
        });
    }

    private void doRegister(){

        String email = this.textInputEditText_email.getText().toString();
        String password = this.textInputEditText_password.getText().toString();
        String repeat_password = this.textInputEditText_repeat_password.getText().toString();

        if(email.isEmpty() || password.isEmpty() || repeat_password.isEmpty()){

            displayDialog(getResources().getString(R.string.TitleAlertEmailOrPasswordInvalid), getResources().getString(R.string.MessageAlertEmailOrPasswordInvalid));
        }else if(!password.equals(repeat_password)){

            displayDialog(getResources().getString(R.string.TitleAlertDifferentPasswords), getResources().getString(R.string.MessageDifferentPasswords));
        }else{

            this.progressBar.setVisibility(View.VISIBLE);
            this.viewModelRegister.doRegister(email, password);
        }
    }

    private void openActivityStudents(){

        Intent intent = new Intent(this, StudentsActivity.class);
        startActivity(intent);
    }

    private void displayDialog(String title, String message){

        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    private void insertIdCoachInPreferences(String idCoach){

        PreferenceLogged preferenceLogged = new PreferenceLogged(this);
        preferenceLogged.setPreference(idCoach);
    }
}