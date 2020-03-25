package edu.hnu.aihotel.ui.order.all;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.model.Order;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import edu.hnu.aihotel.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderListAdapter orderListAdapter;
    private String url;
    private List<Order> orders;

    public OrderFragment(String url){
        this.url = url;
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_order_all,container,false);
        initView(page);
        initOrderList();
        return page;
    }

    private void initView(View page) {
        recyclerView = page.findViewById(R.id.order_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        orders = new ArrayList<Order>();
        orderListAdapter = new OrderListAdapter(orders);
        recyclerView.setAdapter(orderListAdapter);
        initOrderList();
    }

    private void initOrderList() {
        System.out.println(url);
        HttpUtil.httpGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                try {
                    orders = gson.fromJson(result,new TypeToken<List<Order>>(){}.getType());
                }catch (Exception e){
                    e.printStackTrace();
                }
                orderListAdapter.initData(orders);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
