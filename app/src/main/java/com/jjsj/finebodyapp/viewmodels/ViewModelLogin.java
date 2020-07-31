package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jjsj.finebodyapp.database.firebase.Response;

public class ViewModelLogin extends ViewModel {

    private MutableLiveData<Response> mutableLiveDataResponseLogin;

    public LiveData<Response> getLiveDataResponseLogin(){

        if(this.mutableLiveDataResponseLogin == null) this.mutableLiveDataResponseLogin = new MutableLiveData<Response>();
        return this.mutableLiveDataResponseLogin;
    }

    public void doLogin(String email, String password){

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            mutableLiveDataResponseLogin.setValue(new Response(200, "OK", task.getResult().getUser().getUid()));
                        }else{

                            mutableLiveDataResponseLogin.setValue(new Response(401, task.getException().toString(), null));
                        }
                    }
                });
    }
}
