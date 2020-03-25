package edu.hnu.aihotel.activity.main;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import edu.hnu.aihotel.ui.hotelDetail.RoomType;
import edu.hnu.aihotel.ui.hotelDetail.RoomListAdapter;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.StatusBarUtil;
import edu.hnu.aihotel.widget.main.MyCalendar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HotelDetailActivity extends AppCompatActivity implements MyCalendar.OnDaySelectListener {

    private ImageView hotelCover;
    private TextView hotelName;
    private TextView hotelOpenTime;
    private TextView hotelType;
    private TextView tel;   //打电话
    private TextView map;
    private TextView hotelAddress;
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

    Calendar ca = Calendar.getInstance();
    int  mYear = ca.get(Calendar.YEAR);
    int  mMonth = ca.get(Calendar.MONTH) +1 ;
    int  mDay = ca.get(Calendar.DAY_OF_MONTH);
    private LinearLayout layoutStart;
    private LinearLayout layoutEnd;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private LinearLayout ll;
    NestedScrollView scrollView;
    private String inday,outday,sp_inday,sp_outday;
    MyCalendar c1;
    Date date;
    String nowday;
    long nd = 1000*24L*60L*60L;//一天的毫秒数
    SimpleDateFormat simpleDateFormat,sd1,sd2;
    private boolean canClick = true;
    private int period = 1;
    private Drawable calendar_in, calendar_out;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
            canClick = true;
        }
    };
    private TextView dismissDialog;

    @Override
    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_hotel_detail);
        calendar_in = getDrawable(R.drawable.calendar_in);
        calendar_out = getDrawable(R.drawable.calendar_out);
        inday = "";
        outday = "";
        sp_inday = "";
        sp_outday = "";
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

        ll = findViewById(R.id.layout_main);

        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        nowday=simpleDateFormat.format(new Date());
        sd1=new SimpleDateFormat("yyyy");
        sd2=new SimpleDateFormat("dd");

        gson = new Gson();
        initView();
        initRoomList();
    }

    @Override
    public void onDaySelectListener(View view, String date) {
        //若日历日期小于当前日期，或日历日期-当前日期超过三个月，则不能点击
        try {
            if(simpleDateFormat.parse(date).getTime()<simpleDateFormat.parse(nowday).getTime()){
                return;
            }
            long dayxc=(simpleDateFormat.parse(date).getTime()-simpleDateFormat.parse(nowday).getTime())/nd;
            if(dayxc>30){
                return;
            }
            if(!canClick){
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(sp_inday + " " + sp_outday);
        //若以前已经选择了日期，则在进入日历后会显示以选择的日期，该部分作用则是重新点击日历时，清空以前选择的数据（包括背景图案）
        if(!"".equals(sp_inday)){
            c1.viewIn.setBackgroundColor(Color.WHITE);
            ((TextView) c1.viewIn.findViewById(R.id.tv_calendar_day)).setTextColor(Color.BLACK);
            ((TextView) c1.viewIn.findViewById(R.id.tv_calendar)).setText("");
        }
        if(!"".equals(sp_outday)){
            c1.viewOut.setBackgroundColor(Color.WHITE);
            ((TextView) c1.viewOut.findViewById(R.id.tv_calendar_day)).setTextColor(Color.BLACK);
            ((TextView) c1.viewOut.findViewById(R.id.tv_calendar)).setText("");
        }

        String dateDay=date.split("-")[2];
        if(Integer.parseInt(dateDay)<10){
            dateDay=date.split("-")[2].replace("0", "");
        }
        TextView textDayView=(TextView) view.findViewById(R.id.tv_calendar_day);
        TextView textView=(TextView) view.findViewById(R.id.tv_calendar);
        textDayView.setTextColor(Color.WHITE);
        if(null==inday||inday.equals("")){
            textDayView.setText(dateDay);
            textView.setText("入住");
            inday=date;
            view.setBackground(calendar_in);
        }else{
            if(inday.equals(date)){
                view.setBackgroundColor(Color.WHITE);
                textDayView.setText(dateDay);
                textDayView.setTextColor(Color.BLACK);
                textView.setText("");
                inday="";
            }else{
                try {
                    if(simpleDateFormat.parse(date).getTime()<simpleDateFormat.parse(inday).getTime()){
                        view.setBackgroundColor(Color.WHITE);
                        textDayView.setTextColor(Color.BLACK);
                        Toast.makeText(HotelDetailActivity.this, "离开日期不能小于入住日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                view.setBackground(calendar_out);
                textDayView.setText(dateDay);
                textView.setText("离开");
                outday=date;
                sp_inday = inday;
                sp_outday = outday;
                c1.setDay(inday,outday);
                try {
                    Long l = simpleDateFormat.parse(sp_inday).getTime();
                    Long l2 = simpleDateFormat.parse(sp_outday).getTime();
                    int tmp = (int) ((l2-l) / (24*3600*1000L));
                    System.out.println(l + "   " + l2  + "  " +tmp) ;
                    period = tmp;
                    totalTime.setText("共"+tmp+"晚");
                }catch (Exception e){
                    e.printStackTrace();
                }

                roomListAdapter.setDate(sp_inday,sp_outday,period);
                inday="";
                outday="";
                ((TextView) layoutStart.getChildAt(0)).setText(sp_inday.substring(5));
                ((TextView) layoutEnd.getChildAt(0)).setText(sp_outday.substring(5));
                canClick = false;
                handler.postDelayed(runnable,1000);
            }
        }
    }

    public void dismissDialog(View view){
        dialog.dismiss();
    }

    //根据当前日期，向后数三个月（若当前day不是1号，为满足至少90天，则需要向后数4个月）
    @SuppressLint("SimpleDateFormat")
    public List<String> getDateList(){
        List<String> list=new ArrayList<String>();
        Date date=new Date();
        int nowMon=date.getMonth()+1;
        String yyyy=sd1.format(date);
        String dd=sd2.format(date);
        if(nowMon==12){
            list.add(yyyy+"-12-"+dd);
            list.add((Integer.parseInt(yyyy)+1)+"-01-"+dd);
        }else{
            list.add(yyyy+"-"+getMon(nowMon)+"-"+dd);
            list.add(yyyy+"-"+getMon((nowMon+1))+"-"+dd);
        }
        return list;
    }

    public String getMon(int mon){
        String month="";
        if(mon<10){
            month="0"+mon;
        }else{
            month=""+mon;
        }
        return month;
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

        totalTime = findViewById(R.id.total_time);

        layoutStart = findViewById(R.id.layout_start);
        layoutEnd = findViewById(R.id.layout_end);
        sp_inday = mYear + "-" + (mMonth > 9 ? mMonth : "0"+mMonth) + "-" + mDay;
        sp_outday = mYear + "-" + (mMonth > 9 ? mMonth : "0"+mMonth) + "-" + (mDay+1);
        roomListAdapter.setDate(sp_inday,sp_outday,period);
        ((TextView) layoutStart.getChildAt(0)).setText(mMonth + "月" + mDay + "日");
        ((TextView) layoutEnd.getChildAt(0)).setText(mMonth + "月" + (mDay+1) + "日");
        initChatRoom();
        layoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatRoom();
            }
        });
        layoutEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatRoom();
            }
        });
    }
    private void showChatRoom(){
        initChatRoom();
        dialog.show();
    }

    private void initChatRoom(){
        dialog = new Dialog(this,R.style.DialogTheme);
        View view = View.inflate(this,R.layout.dialog_calendar,null);
        dismissDialog = view.findViewById(R.id.dismiss);
        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LinearLayout linearLayout = view.findViewById(R.id.layout_main);
        List<String> listDate=getDateList();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 15;
        for(int i=0;i<listDate.size();i++){
            c1 = new MyCalendar(this);
            c1.setLayoutParams(params);
            Date date=null;
            try {
                date=simpleDateFormat.parse(listDate.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(!"".equals(sp_inday)){
                c1.setInDay(sp_inday);
            }
            if(!"".equals(sp_outday)){
                c1.setOutDay(sp_outday);
            }
            c1.setTheDay(date);
            c1.setOnDaySelectListener(this);
            linearLayout.addView(c1);
        }
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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
