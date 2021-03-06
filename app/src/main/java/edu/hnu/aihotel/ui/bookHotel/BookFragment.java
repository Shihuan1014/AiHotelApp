package edu.hnu.aihotel.ui.bookHotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.HotelSearchActivity;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookFragment extends Fragment {
    private List<Area> areaList;
    private List<Hotel> hotelList;
    private RecyclerView areaRecycleView;
    private RecyclerView hotelRecycleView;
    private TextView region;
    private EditText searchWord;
    private Button search;
    private Gson gson;
    private HotelListAdapter hotelListAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_book,container,false);
        initView(page);
        return page;
    }

    private void initView(View view){
        areaRecycleView = view.findViewById(R.id.area_info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        areaRecycleView.setLayoutManager(layoutManager);

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
                intent.putExtra("searchWord",searchWord.getText());
                startActivity(intent);
            }
        });


        //地区列表
        initAreaList();
        AreaListAdapter areaListAdapter = new AreaListAdapter(areaList);
        areaListAdapter.setItemClickListener(new AreaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                areaListAdapter.setSelectedIndex(position);
                searchHotelByArea("206",areaList.get(position).getAreaName(),1);
            }
        });
        areaRecycleView.setAdapter(areaListAdapter);
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
        getData("206",1);
    }

    private void initAreaList(){
        areaList = new ArrayList<Area>();
        areaList.add(new Area("五一广场/贺龙商圈","均价￥245"));
        areaList.add(new Area("长沙火车站商圈","均价￥264"));
        areaList.add(new Area("高铁南站/国际会展中心","均价￥275"));
        areaList.add(new Area("雨花亭/东塘商圈","均价￥180"));
        areaList.add(new Area("黄花国际机场地区","均价￥364"));
        areaList.add(new Area("梅溪湖/麓谷商圈","均价￥164"));
        areaList.add(new Area("德思勤城市广场商圈","均价￥175"));
        areaList.add(new Area("湖南广电世界之窗商圈","均价￥160"));
        areaList.add(new Area("岳麓山高校区","均价￥164"));
        areaList.add(new Area("市委/市政府商圈","均价￥175"));
        areaList.add(new Area("望城经济开发区","￥360"));
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

    private void getData( String cityNo,int page){
        String url = getResources().getString(R.string.api_host_test) + "/hotel/getHotelListPage?cityNo="+cityNo+"&page="+page+"&pageSize=24";
        HttpUtil.httpGet(url,new Callback() {
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

    private void toast(String msg){
        Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_LONG);
    }
}
