package edu.hnu.aihotel.ui.bookHotel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }



    public LiveData<String> getText() {
        return mText;
    }
}