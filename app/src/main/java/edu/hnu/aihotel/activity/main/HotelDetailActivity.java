package edu.hnu.aihotel.activity.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import edu.hnu.aihotel.ui.hotelDetail.RoomType;
import edu.hnu.aihotel.ui.hotelDetail.RoomListAdapter;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.StatusBarUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotelDetailActivity extends AppCompatActivity {

    private ImageView hotelCover;
    private TextView hotelName;
    private TextView hotelOpenTime;
    private TextView hotelType;
    private TextView tel;   //打电话
    private TextView map;
    private TextView hotelAddress;
    private TextView checkInTime;
    private TextView leaveTime;
    private TextView totalTime;
    private TextView hotelScore;
    private TextView hotelCommentCount;
    private RecyclerView recyclerView;
    private List<RoomType> roomList;
    private Gson gson;
    private RoomListAdapter roomListAdapter;
    private FloatingActionButton floatingActionButton;
    private Dialog dialog;
    /*
    * HotelInfo
    * */
    private Hotel hotel;


    /*
    * orderInfo
    * */
    private String startDate = "3月6日";
    private String endDate = "3月7日";
    private String dayCount = "共一晚";


    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_hotel_detail);
        hotel = new Hotel();
        Intent intent = getIntent();
        hotel.setId(intent.getStringExtra("hotelId"));
        hotel.setAddress(intent.getStringExtra("address"));
        hotel.setName(intent.getStringExtra("name"));
        hotel.setStardesc(intent.getStringExtra("starDesc"));
        hotel.setScore(intent.getDoubleExtra("score",3.5));
        hotel.setDpcount(intent.getIntExtra("dpCount",0));
        String tmp = intent.getStringExtra("img");
        hotel.setImg(tmp.substring(0,tmp.length()-11) + "300_225.jpg");
        System.out.println(hotel.getImg());
        hotel.setOpenYear(intent.getStringExtra("openYear"));

        gson = new Gson();
        initView();
        initRoomList();
    }
    private void initView(){
        recyclerView = findViewById(R.id.room_list);
        roomList = new ArrayList<RoomType>();
        roomListAdapter = new RoomListAdapter(roomList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(roomListAdapter);
        recyclerView.setLayoutManager(layoutManager);

        hotelName = findViewById(R.id.hotel_name);
        hotelType = findViewById(R.id.hotel_type);
        hotelCover = findViewById(R.id.hotel_cover);
        hotelAddress = findViewById(R.id.hotel_address);
        hotelOpenTime = findViewById(R.id.open_time);
        hotelScore = findViewById(R.id.hotel_score);
        hotelCommentCount = findViewById(R.id.hotel_comments);
        hotelName.setText(hotel.getName());
        hotelType.setText(hotel.getStardesc());
        hotelAddress.setText(hotel.getAddress());
        hotelOpenTime.setText(hotel.getOpenYear());
        hotelScore.setText(String.valueOf(hotel.getScore()));
        hotelCommentCount.setText(hotel.getDpcount()+"人评价>");

        floatingActionButton = findViewById(R.id.float_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatRoom();
            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "icons/iconfont.ttf");
        tel = findViewById(R.id.tel);
        tel.setTypeface(font);
        map = findViewById(R.id.map);
        map.setTypeface(font);

        hotelCover = findViewById(R.id.hotel_cover);
        Glide.with(this)
                .load(hotel.getImg())
                .into(hotelCover);
    }

    private void showChatRoom(){
        dialog = new Dialog(this,R.style.DialogTheme);
        View view = View.inflate(this,R.layout.dialog_chatroom,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }

    private void initRoomList(){
        String url = getResources().getString(R.string.api_host_test) + "/room/getRoomTypeOfHotel?hotelId="+hotel.getId();
        HttpUtil.httpGet(url,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println(str);
                roomList = gson.fromJson(str,new TypeToken<List<RoomType>>(){}.getType());
                roomListAdapter.initData(roomList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roomListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK && dialog!=null && dialog.isShowing()){
            dialog.dismiss();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
