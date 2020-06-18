package com.jjsj.finebodyapp.database.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FireConnection {

    private FirebaseAuth firebaseAuth;

    public FireConnection(){

        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> doLogin(String email, String password){
        Log.i("Test", "doLogin of Firebase");
        final MutableLiveData<String> idCoach = new MutableLiveData<>();
        this.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    idCoach.setValue(task.getResult().getUser().getUid());
                    Log.i("Test", "set id value");
                }
            }
        });
        return idCoach;
    }
}
