package edu.hnu.aihotel.activity.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.hnu.aihotel.MainActivity;
import edu.hnu.aihotel.R;
import edu.hnu.aihotel.model.Order;
import edu.hnu.aihotel.ui.bookHotel.RoomNumAdapter;
import edu.hnu.aihotel.ui.hotelDetail.RoomType;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.StatusBarUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HotelCheckOrderActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private TextView submitBtn;
    private String roomId;
    private RoomType roomType;
    private Gson gson;
    private TextView roomTypeView;
    private TextView bedTypeView;
    private TextView priceView;
    private TextView discountView;
    private TextView hotelNameView;
    private Order order;
    private EditText telEditText;
    /*
    * 图标：房间选择、人员信息、通讯录、到店时间
    * */
    private TextView roomBtn;
    private TextView roomNum;
    private TextView nameBtn;
    private TextView telBtn;
    private TextView timeBtn;
    private TextView arriveTime;
    private TextView payDetail;
    private TextView backBtn;
    private RecyclerView roomNumListView;
    private RecyclerView arriveTimeListView;
    private Typeface typeface;
    private List<String> roomList;
    private List<String> arriveTimeList;
    private LinearLayout guestsLayout;
    private String startDate;
    private String endDate;
    private int period;
    private RoomNumAdapter roomNumAdapter;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_check_order);
        gson = new Gson();
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        startDate = intent.getStringExtra("startDate");
        endDate = intent.getStringExtra("endDate");
        period = intent.getIntExtra("period",0);
        Window window = getWindow();
        StatusBarUtil.changeStatusBarColor(window, getResources().getColor(R.color.colorPrimary));
        //小键盘不破坏布局
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        order = new Order();
        order.setRoomNum(1);
        initRoom();
        initView();
    }

    private void initRoom(){
        String url = getResources().getString(R.string.api_host_test) + "/room/getRoomTypeByIdWithNum?roomId="+
                roomId+"&startDate="+startDate+"&endDate="+endDate;
        HttpUtil.httpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                roomType = gson.fromJson(str,new TypeToken<RoomType>(){}.getType());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roomTypeView.setText(roomType.getName());
                        bedTypeView.setText(roomType.getBedType() + "·" + roomType.getBreakfast());
                        discountView.setText("已优惠 ￥" + String.valueOf(roomType.getDiscount()));
                        priceView.setText("￥" + String.valueOf(period*(int)roomType.getPrice()));
                        hotelNameView.setText(roomType.getHotelName());
                        order.setRoomId(roomType.getId());
                        order.setHotelId(roomType.getHotelId());
                        int l = roomType.getLeftNum();
                        if(l > 6){
                            l = 6;
                        }else if(l == 0){
                            l = 1;
                        }
                        for(int i = 1;i < l+1;i ++){
                            roomList.add(i+"间");
                        }
                        roomNumAdapter.initData(roomList);
                        setGuestsNum(1);
                    }
                });
            }
        });
    }

    public void toggleRoomChoose(View view){
        if(roomNumListView.getVisibility() == View.VISIBLE){
            roomNumListView.setVisibility(View.GONE);
            roomBtn.setText(R.string.arrow_down);
        }else{
            roomNumListView.setVisibility(View.VISIBLE);
            roomBtn.setText(R.string.arrow_up);
        }
    }

    public void toggleTimeChoose(View view){
        if(arriveTimeListView.getVisibility() == View.VISIBLE){
            arriveTimeListView.setVisibility(View.GONE);
            timeBtn.setText(R.string.arrow_down);
        }else{
            arriveTimeListView.setVisibility(View.VISIBLE);
            timeBtn.setText(R.string.arrow_up);
        }
    }

    public void back(View view){
        finish();
    }

    private void initView(){
        submitBtn = findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t;
                order.setHotelId(roomType.getHotelId());
                order.setRoomId(roomType.getId());
                order.setStartDate(startDate);
                order.setEndDate(endDate);
                String guests = "";
                EditText tmpEdit;
                for(int i = 0; i < guestsLayout.getChildCount(); i++){
                    tmpEdit = ((EditText)((LinearLayout)guestsLayout.getChildAt(i)).getChildAt(0));
                    t = tmpEdit.getText().toString().trim();
                    if(t.length() > 0){
                        guests += t;
                    }else{
                        showToast("请填写住客姓名");
                        tmpEdit.requestFocus();
                        return;
                    }
                }
                t = telEditText.getText().toString().trim();
                if(t.length() > 0){
                    order.setPhones(telEditText.getText().toString().trim());
                }else{
                    showToast("请填写联系电话");
                    telEditText.requestFocus();
                    return;
                }
                order.setGuests(guests);
                order.setUserId(MainActivity.user.getId());
                System.out.println(order);
                String url = getString(R.string.api_host_test) + "/book/makeOrder";

                RequestBody body = RequestBody.create(JSON, gson.toJson(order));
                HttpUtil.httpRequestBody(url, body, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            System.out.println(jsonObject);
                            Intent intent = new Intent(HotelCheckOrderActivity.this, HotelPayActivity.class);
                            intent.putExtra("aliPayString", jsonObject.getString("aliPayString"));
                            intent.putExtra("price",jsonObject.getString("price"));
                            intent.putExtra("orderName",jsonObject.getString("orderName"));
                            intent.putExtra("created",jsonObject.getLong("created"));
                            startActivityForResult(intent,1);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        roomTypeView = findViewById(R.id.room_type);
        bedTypeView = findViewById(R.id.bed_type_and_breakfast);
        priceView = findViewById(R.id.price);
        discountView = findViewById(R.id.discount);
        hotelNameView = findViewById(R.id.hotel_name);

        roomList = new ArrayList<String>();
        roomNumListView = findViewById(R.id.room_num_choose);
        roomNumAdapter = new RoomNumAdapter(roomList);
        roomNumAdapter.setOnItemClickListener(new RoomNumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setGuestsNum(position+1);
                order.setRoomNum(position+1);
                roomNum.setText(roomList.get(position));
            }
        });
        roomNumListView.setAdapter(roomNumAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(roomNumListView.getContext(),5);
        roomNumListView.setLayoutManager(gridLayoutManager);


        arriveTimeList = new ArrayList<String>();
        arriveTimeList.add("16:00");arriveTimeList.add("16:30");
        arriveTimeList.add("17:00");
        arriveTimeList.add("17:30");arriveTimeList.add("18:00");
        arriveTimeListView = findViewById(R.id.arrive_time_choose);
        order.setArriveTime("2020-03-18T06:27:48.436+0000");
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(arriveTimeListView.getContext(),4);
        RoomNumAdapter roomNumAdapter1 = new RoomNumAdapter(arriveTimeList);
        roomNumAdapter1.setOnItemClickListener(new RoomNumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                arriveTime.setText(arriveTimeList.get(position) + "(房间将整晚保留)");
                order.setArriveTime("2020-03-18T06:27:48.436+0000");
            }
        });
        arriveTimeListView.setAdapter(roomNumAdapter1);
        arriveTimeListView.setLayoutManager(gridLayoutManager2);

        typeface = Typeface.createFromAsset(getAssets(),"icons/iconfont.ttf");
        roomBtn = findViewById(R.id.room_more);
        roomBtn.setTypeface(typeface);
        roomNum = findViewById(R.id.room_num);
        nameBtn = findViewById(R.id.name_more);
        nameBtn.setTypeface(typeface);
        telBtn = findViewById(R.id.tel_more);
        telBtn.setTypeface(typeface);
        timeBtn = findViewById(R.id.time_more);
        timeBtn.setTypeface(typeface);
        arriveTime = findViewById(R.id.arrive_time);
        payDetail = findViewById(R.id.pay_more);
        payDetail.setTypeface(typeface);
        backBtn = findViewById(R.id.back);
        backBtn.setTypeface(typeface);

        telEditText = findViewById(R.id.tel);
        telEditText.setText(MainActivity.user.getTel());
        guestsLayout = findViewById(R.id.layout_guests);


        TextView dateView = findViewById(R.id.date_str);
        String str = "";

        str +=  startDate.substring(5,7)+ "月" + startDate.substring(8)+"日 /" ;
        str +=  endDate.substring(5,7)+ "月" + endDate.substring(8)+"日 共" ;
        str += period + "晚";
        dateView.setText(str);
    }

    private void setGuestsNum(int i){
        int sum = guestsLayout.getChildCount();
        int price = (int) (roomType.getPrice() * i * period);
        priceView.setText("￥"+ String.valueOf(price));
        if(i < sum){
            guestsLayout.removeViews(i,sum-i);
        }else{
            sum = i - sum;
            for (int t = 0; t < sum; t++){
                LinearLayout layout= (LinearLayout) LinearLayout.inflate(this,R.layout.item_edittext,null);
                TextView nameMore = layout.findViewById(R.id.name_more);
                nameMore.setTypeface(typeface);
                guestsLayout.addView(layout);
            }
        }
    }


    private void showToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            String result = data.getStringExtra("result");
            if(result.equals("success")){
                finish();
            }
        }
    }
}
