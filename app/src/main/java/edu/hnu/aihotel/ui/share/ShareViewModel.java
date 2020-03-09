package edu.hnu.aihotel.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("朋友的动态以及分享旅途趣事");
    }

    public LiveData<String> getText() {
        return mText;
    }
}