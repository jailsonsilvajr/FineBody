package com.jjsj.finebodyapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ViewModelRegister extends ViewModel {

    private MutableLiveData<String> idCoach;

    public LiveData<String> observerIdCoach() {

        if(this.idCoach == null) this.idCoach = new MutableLiveData<>();
        return this.idCoach;
    }

    public void doRegister(String email, String password){

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            idCoach.setValue(task.getResult().getUser().getUid());
                        }else{

                            idCoach.setValue(null);
                        }
                    }
                });
    }
}
