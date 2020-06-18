package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.jjsj.finebodyapp.R;
import com.jjsj.finebodyapp.viewmodels.ViewModelRegister;

public class RegisterActivity extends AppCompatActivity {

    private ViewModelRegister viewModelRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Show button
        getSupportActionBar().setHomeButtonEnabled(true); //Activate button

        this.viewModelRegister = new ViewModelProvider(this).get(ViewModelRegister.class);

        Button buttonRegister = findViewById(R.id.layout_register_button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doRegister();
            }
        });
    }

    private void doRegister(){

        TextInputEditText textInputEditTextEmail = findViewById(R.id.layout_register_textInput_email);
        TextInputEditText textInputEditTextPassword = findViewById(R.id.layout_register_textInput_password);
        TextInputEditText textInputEditTextRepeatPassword = findViewById(R.id.layout_register_textInput_repeat_password);

        if(textInputEditTextEmail.getText().toString().isEmpty() || textInputEditTextPassword.getText().toString().isEmpty() || textInputEditTextRepeatPassword.getText().toString().isEmpty()){

            displayDialog(getResources().getString(R.string.TitleAlertEmailOrPasswordInvalid), getResources().getString(R.string.MessageAlertEmailOrPasswordInvalid));
        }else if(!textInputEditTextPassword.getText().toString().equals(textInputEditTextRepeatPassword.getText().toString())){

            displayDialog(getResources().getString(R.string.TitleAlertDifferentPasswords), getResources().getString(R.string.MessageDifferentPasswords));
        }else{

            this.viewModelRegister.doRegister(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString());
            this.viewModelRegister.getIdCoach().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {

                    if(s == null){

                        new MaterialAlertDialogBuilder(RegisterActivity.this)
                                .setTitle(getResources().getString(R.string.TitleAlertRegisterFail))
                                .setMessage(getResources().getString(R.string.MessageAlertRegisterFail))
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

    private void displayDialog(String title, String message){

        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    @Override
    protected void onResume() {

        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}