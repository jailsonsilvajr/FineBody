package com.jjsj.finebodyapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jjsj.finebodyapp.repository.Repository;

import java.io.IOException;

public class ViewModelProfile extends ViewModel {

    private MutableLiveData<byte[]> imgProfileBytes;

    public LiveData<byte[]> observerImgProfileBytes(){

        if(this.imgProfileBytes == null) this.imgProfileBytes = new MutableLiveData<>();
        return this.imgProfileBytes;
    }

    public void downloadImgProfile(String path) throws IOException {

        Repository.getInstance().downloadPhoto(path, imgProfileBytes);
    }
}
