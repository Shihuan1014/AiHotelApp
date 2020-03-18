package edu.hnu.aihotel.util;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import edu.hnu.aihotel.MainActivity;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    public static void httpGet(String url,Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).addHeader("cookie", MainActivity.sessionId).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void httpPost(String url, FormBody body, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).post(body).addHeader("cookie", MainActivity.sessionId).build();;
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void httpRequestBody(String url, RequestBody body, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).post(body).addHeader("cookie", MainActivity.sessionId).build();;
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
