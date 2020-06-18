package com.jjsj.finebodyapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.jjsj.finebodyapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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


        }
    }

    private void displayDialog(String title, String message){

        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}