package edu.hnu.aihotel.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import edu.hnu.aihotel.ui.bookHotel.HotelListAdapter;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.StatusBarUtil;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class HotelSearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerView;
    private String defaultKeyWord = "搜索酒店、地区";
    private List<Hotel> hotelList;
    private HotelListAdapter hotelListAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_search_hotel);
        StatusBarUtil.whiteBgAndBlackFont(getWindow());
        initView();

    }

    private void initView(){
        searchInput = findViewById(R.id.search_input);
        recyclerView = findViewById(R.id.search_hotel_list);

        String kw = getIntent().getStringExtra("searchWord");
        searchInput.setHint(defaultKeyWord);
        if (kw!=null && kw.trim().length()> 0){
            searchInput.setText(kw);
        }
        searchInput.requestFocus();
        searchInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    search(searchInput.getText().toString());
                }
                return false;
            }
        });
        gson = new Gson();
        hotelList = new ArrayList<Hotel>();
        hotelListAdapter = new HotelListAdapter(hotelList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(hotelListAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HotelSearchActivity.this,2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
    }

    private void search(String word){
        if(word.trim().length()==0){
            word = defaultKeyWord;
            searchInput.setText(defaultKeyWord);
        }
        fetchDataByKeyword(word);
        searchInput.clearFocus();
        InputMethodManager manager = ((InputMethodManager)HotelSearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(HotelSearchActivity.this.getWindow().getDecorView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void fetchDataByKeyword(String kw){
        String url = getResources().getString(R.string.api_host_test) + "/hotel/searchHotelByKeyword?cityNo=206&keyword="+kw+"&page=1&pageSize=24";
        HttpUtil.httpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println(str);
                hotelList = gson.fromJson(str,new TypeToken<List<Hotel>>(){}.getType());
                hotelListAdapter.initData(hotelList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotelListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        hotelList = null;
        System.gc();
    }

}
