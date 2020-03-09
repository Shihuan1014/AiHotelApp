package edu.hnu.aihotel.activity.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.hotelDetail.Room;
import edu.hnu.aihotel.ui.hotelDetail.RoomListAdapter;

public class HotelDetailActivity extends AppCompatActivity {

    private ImageView hotelCover;
    private TextView hotelName;
    private TextView openTime;
    private TextView hotelType;
    private TextView tel;   //打电话
    private TextView map;
    private TextView address;
    private TextView checkInTime;
    private TextView leaveTime;
    private TextView totalTime;
    private TextView rating;
    private TextView commentCount;

    private RecyclerView recyclerView;

    private List<Room> roomList;
    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_hotel_detail);
        initRoomList();
        initView();
    }

    private void initView(){
        recyclerView = findViewById(R.id.room_list);
        RoomListAdapter roomListAdapter = new RoomListAdapter(roomList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(roomListAdapter);
        recyclerView.setLayoutManager(layoutManager);

        Typeface font = Typeface.createFromAsset(getAssets(), "icons/iconfont.ttf");
        tel = findViewById(R.id.tel);
        tel.setTypeface(font);
        map = findViewById(R.id.map);
        map.setTypeface(font);

        hotelCover = findViewById(R.id.hotel_cover);
        Glide.with(this)
                .load("http://p0.meituan.net/tdchoteldark/aa000a61da1eb7a83028b8f36370dfc9862822.jpg")
                .into(hotelCover);
    }

    private void initRoomList(){
        roomList = new ArrayList<Room>();
        roomList.add(new Room("单人房","http://dimg13.c-ctrip.com/images/200f1c000001dlejw8DF2_R_130_130.jpg",
                "25㎡","窗宽2.0㎡","有窗","259"
                ));
        roomList.add(new Room("双人房","http://dimg10.c-ctrip.com/images/200e1c000001do4t96FFD_R_130_130.jpg",
                "30㎡","窗宽1.2㎡","有窗","399"
        ));
    }
}
