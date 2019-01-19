package ru.notificator.sirius.siriusnotificator.vievmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ru.notificator.sirius.siriusnotificator.Recyclik.Boi;

public class TextVievModel extends ViewModel {
    private MutableLiveData<String> mData = new MutableLiveData<>();
    public TextVievModel(){

    }
    public LiveData<String> getData(){
        return mData;
    }
    @Override
    protected void onCleared(){
        super.onCleared();
    }
}
