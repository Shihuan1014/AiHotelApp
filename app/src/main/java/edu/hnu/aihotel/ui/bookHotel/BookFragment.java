package edu.hnu.aihotel.ui.bookHotel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.hnu.aihotel.MainActivity;
import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.HotelDetailActivity;
import edu.hnu.aihotel.activity.main.HotelSearchActivity;
import edu.hnu.aihotel.util.CommonUtil;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.widget.main.EndlessRecyclerOnScrollListener;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;
import edu.hnu.aihotel.widget.main.MyCalendar;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookFragment extends Fragment implements MyCalendar.OnDaySelectListener {
    private List<Area> areaList;
    private List<Hotel> hotelList;
//    private RecyclerView areaRecycleView;
    private RecyclerView hotelRecycleView;
    private TextView region;
    private EditText searchWord;
    private Button search;
    private Gson gson;
    private int page = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HotelListAdapter hotelListAdapter;

    private double lat = 28.1726878900,lon = 112.9512400800;
    private String currentCity = "206";
    private boolean isRefresh = false;
    private Handler handler;
    private Runnable cancleRefresh = new Runnable() {
        @Override
        public void run() {
            swipeRefreshLayout.setRefreshing(false);
            isRefresh = false;
        }
    };

    private Dialog dialog;
    private TextView totalTime;
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
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dialog.dismiss();
            canClick = true;
        }
    };
    private TextView dismissDialog;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        handler = new Handler();
        if(MainActivity.location!=null){
           Location location = MainActivity.location;
           lat = location.getLatitude();
           lon = location.getLongitude();
        }
        View page = inflater.inflate(R.layout.fragment_book,container,false);
        initView(page);
        return page;
    }

    private void initView(View view){

        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        nowday=simpleDateFormat.format(new Date());
        sd1=new SimpleDateFormat("yyyy");
        sd2=new SimpleDateFormat("dd");

        calendar_in = view.getResources().getDrawable(R.drawable.calendar_in);
        calendar_out = view.getResources().getDrawable(R.drawable.calendar_out);

//        areaRecycleView = view.findViewById(R.id.area_info);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        areaRecycleView.setLayoutManager(layoutManager);

        searchWord = view.findViewById(R.id.search_input);
        //选择位置
        region = view.findViewById(R.id.region);
        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("设计：弹出模态层进行选择地区");
            }
        });

        search = view.findViewById(R.id.search_hotel);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotelSearchActivity.class);
                intent.putExtra("searchWord",searchWord.getText().toString());
                startActivity(intent);
            }
        });


        //地区列表
//        initAreaList();
//        AreaListAdapter areaListAdapter = new AreaListAdapter(areaList);
//        areaListAdapter.setItemClickListener(new AreaListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                areaListAdapter.setSelectedIndex(position);
//                currentArea = areaList.get(position).getAreaName();
//                searchHotelByArea(currentCity,currentArea,1);
//            }
//        });
//        areaRecycleView.setAdapter(areaListAdapter);
        //酒店列表
        initHotelList();
        hotelRecycleView = view.findViewById(R.id.area_hotel_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        hotelRecycleView.setLayoutManager(gridLayoutManager);
        hotelListAdapter = new HotelListAdapter(hotelList);
        hotelRecycleView.setAdapter(hotelListAdapter);
        hotelRecycleView.setNestedScrollingEnabled(false);
        hotelRecycleView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        swipeRefreshLayout=view.findViewById(R.id.swipe_fresh);
        //设置转动条颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false, CommonUtil.dip2px(getContext(), -40), CommonUtil.dip2px(getContext(), 24));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData(lat,lon,page++);
                //隐藏转动条
                isRefresh = false;
            }
        });
        initData(lat,lon,page++);

        totalTime = view.findViewById(R.id.total_time);

        sp_inday = mYear + "-" + (mMonth > 9 ? mMonth : "0"+mMonth) + "-" + mDay;
        sp_outday = mYear + "-" + (mMonth > 9 ? mMonth : "0"+mMonth) + "-" + (mDay+1);
//        System.out.println(((ViewGroup)datePicker.getChildAt(0)).getChildCount());
        layoutStart = view.findViewById(R.id.layout_start);
        layoutEnd = view.findViewById(R.id.layout_end);
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
        try {
            Long l = simpleDateFormat.parse(sp_inday).getTime();
            Long l2 = simpleDateFormat.parse(sp_outday).getTime();
            ((TextView) layoutStart.getChildAt(0)).setText(getWeek(l) + "入住");
            ((TextView) layoutEnd.getChildAt(0)).setText(getWeek(l2)+"离店");
            ((TextView) layoutStart.getChildAt(1)).setText(sp_inday.substring(5));
            ((TextView) layoutEnd.getChildAt(1)).setText(sp_outday.substring(5));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        String result = "周日";
        switch (weekDay){
            case 1:
                result = "周日";
                break;
            case 2:
                result = "周一";
                break;
            case 3:
                result = "周二";
                break;
            case 4:
                result = "周三";
                break;
            case 5:
                result = "周四";
                break;
            case 6:
                result = "周五";
                break;
            case 7:
                result = "周六";
                break;
        }
        return result;
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
    private void showChatRoom(){
        initChatRoom();
        dialog.show();
    }

    private void initChatRoom(){
        dialog = new Dialog(getContext(),R.style.DialogTheme);
        View view = View.inflate(getContext(),R.layout.dialog_calendar,null);
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
            c1 = new MyCalendar(getContext());
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

    public String getMon(int mon){
        String month="";
        if(mon<10){
            month="0"+mon;
        }else{
            month=""+mon;
        }
        return month;
    }

    private void initHotelList(){
        hotelList = new ArrayList<Hotel>();
        gson = new Gson();
    }

    private void searchHotelByArea( String cityNo,String area,int page){
        String url = getResources().getString(R.string.api_host_test) + "/hotel/searchHotelByArea?cityNo="+cityNo+"&area="+area+"&page="+page+"&pageSize=24";
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotelListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initData(double lat,double lon, int page){
        swipeRefreshLayout.setRefreshing(true);
        isRefresh = true;
        String url = getResources().getString(R.string.api_host_test) + "/hotel/getNearByHotel?lat="+lat+"&lon="+lon+"&page="+page+"&pageSize=24";
        HttpUtil.httpGet(url,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        isRefresh = false;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println(str);
                hotelList = gson.fromJson(str,new TypeToken<List<Hotel>>(){}.getType());
                hotelListAdapter.initData(hotelList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        hotelListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
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
                        Toast.makeText(getContext(), "离开日期不能小于入住日期", Toast.LENGTH_SHORT).show();
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
                    ((TextView) layoutStart.getChildAt(0)).setText(getWeek(l) + "入住");
                    ((TextView) layoutEnd.getChildAt(0)).setText(getWeek(l2)+"离店");
                    int tmp = (int) ((l2-l) / (24*3600*1000L));
                    System.out.println(l + "   " + l2  + "  " +tmp) ;
                    period = tmp;
                    totalTime.setText("共"+tmp+"晚");
                }catch (Exception e){
                    e.printStackTrace();
                }
                inday="";
                outday="";
                ((TextView) layoutStart.getChildAt(1)).setText(sp_inday.substring(5));
                ((TextView) layoutEnd.getChildAt(1)).setText(sp_outday.substring(5));
                canClick = false;
                handler.postDelayed(runnable,1000);
            }
        }
    }

    private void updateData(String cityNo,String area,int page){
        String url = getResources().getString(R.string.api_host_test) + "hotel/searchHotelByArea?cityNo="+cityNo+"&area="+area+"&page="+page+"&pageSize=24";
        HttpUtil.httpGet(url,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                System.out.println(str);
                hotelList = gson.fromJson(str,new TypeToken<List<Hotel>>(){}.getType());
                hotelListAdapter.updateData(hotelList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotelListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_LONG);
    }
}
