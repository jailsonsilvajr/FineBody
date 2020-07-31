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

public class ViewModelRegister extends ViewModel {

    private MutableLiveData<Response> mutableLiveDataResponseRegister;

    public LiveData<Response> getLiveDataResponseRegister() {

        if(this.mutableLiveDataResponseRegister == null) this.mutableLiveDataResponseRegister = new MutableLiveData<Response>();
        return this.mutableLiveDataResponseRegister;
    }

    public void doRegister(String email, String password){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            mutableLiveDataResponseRegister.setValue(new Response(200, "OK", task.getResult().getUser().getUid()));
                        }else{

                            mutableLiveDataResponseRegister.setValue(new Response(409, task.getException().toString(), null));
                        }
                    }
                });
    }
}
