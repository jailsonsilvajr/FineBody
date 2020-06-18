package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.viewmodels.ViewModelLogin;

public class MainActivity extends AppCompatActivity {

    private ViewModelLogin viewModelLogin;

    private Button buttonEnter;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.viewModelLogin = new ViewModelProvider(this).get(ViewModelLogin.class);
        this.buttonEnter = findViewById(R.id.layout_login_button_enter);
        this.buttonRegister = findViewById(R.id.layout_login_button_register);

        this.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doLogin();
            }
        });
    }

    private void doLogin(){

        TextInputEditText textInputEditTextEmail = findViewById(R.id.layout_login_textInput_email);
        TextInputEditText textInputEditTextPassword = findViewById(R.id.layout_login_textInput_password);

        if(textInputEditTextEmail.getText().toString().isEmpty() || textInputEditTextPassword.getText().toString().isEmpty()){

            new MaterialAlertDialogBuilder(this)
                    .setTitle(getResources().getString(R.string.TitleAlertEmailOrPasswordInvalid))
                    .setMessage(getResources().getString(R.string.MessageAlertEmailOrPasswordInvalid))
                    .show();
        }else{

            this.viewModelLogin.doLogin(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
            this.viewModelLogin.getIdCoach().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    if(s == null){

                        new MaterialAlertDialogBuilder(MainActivity.this)
                                .setTitle(getResources().getString(R.string.TitleAlertLoginFail))
                                .setMessage(getResources().getString(R.string.MessageAlertLoginFail))
                                .show();
                    }else{

                        openActivityStudents(s);
                    }

                }
            });
        }
    }

    private void openActivityStudents(String idCoach){

        Toast.makeText(this, "ID: " + idCoach, Toast.LENGTH_LONG).show();
    }
}
