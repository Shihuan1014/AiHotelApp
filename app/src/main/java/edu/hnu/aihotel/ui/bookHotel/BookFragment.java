package edu.hnu.aihotel.ui.bookHotel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;

public class BookFragment extends Fragment {
    private List<Area> areaList;
    private List<Hotel> hotelList;
    private RecyclerView areaRecycleView;
    private RecyclerView hotelRecycleView;
    private TextView region;
    private Button search;

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
                toast("设计：弹出搜索酒店的页面");
            }
        });
        //地区列表
        initAreaList();
        AreaListAdapter areaListAdapter = new AreaListAdapter(areaList);
        areaListAdapter.setItemClickListener(new AreaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                areaListAdapter.setSelectedIndex(position);
            }
        });
        areaRecycleView.setAdapter(areaListAdapter);
        //酒店列表
        initHotelList();
        hotelRecycleView = view.findViewById(R.id.area_hotel_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        hotelRecycleView.setLayoutManager(gridLayoutManager);
        HotelListAdapter hotelListAdapter = new HotelListAdapter(hotelList);
        hotelRecycleView.setAdapter(hotelListAdapter);
        hotelRecycleView.setNestedScrollingEnabled(false);
        hotelRecycleView.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
    }

    private void initAreaList(){
        areaList = new ArrayList<Area>();
        areaList.add(new Area("岳麓区","均价￥145"));
        areaList.add(new Area("雨花区","均价￥164"));
        areaList.add(new Area("开福区","均价￥175"));
        areaList.add(new Area("天心区","均价￥160"));
        areaList.add(new Area("雨花区","均价￥164"));
        areaList.add(new Area("开福区","均价￥175"));
        areaList.add(new Area("天心区","均价￥160"));
        areaList.add(new Area("雨花区","均价￥164"));
        areaList.add(new Area("开福区","均价￥175"));
        areaList.add(new Area("天心区","￥160"));
    }

    private void initHotelList(){
        hotelList = new ArrayList<Hotel>();
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
        hotelList.add(new Hotel("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1550880591,1177688281&fm=26&gp=0.jpg"
                ,"天马学生公寓旁","天穆大酒店","300"));
    }


    private void toast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG);
    }
}
