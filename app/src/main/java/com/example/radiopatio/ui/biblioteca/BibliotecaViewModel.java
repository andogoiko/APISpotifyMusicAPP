package com.example.radiopatio.ui.biblioteca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BibliotecaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BibliotecaViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}