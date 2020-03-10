package edu.hnu.aihotel.util;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import edu.hnu.aihotel.ui.bookHotel.Hotel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static void httpGet(String url,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
