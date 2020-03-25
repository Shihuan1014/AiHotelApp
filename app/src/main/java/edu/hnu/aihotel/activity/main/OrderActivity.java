package edu.hnu.aihotel.activity.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.order.all.OrderFragment;
import edu.hnu.aihotel.ui.order.all.OrderFragmentAdapter;
import edu.hnu.aihotel.util.StatusBarUtil;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_order_list);
        StatusBarUtil.setOpacityStateBar(getWindow(),false);
        initView();

    }

    private void initView(){
        List<Fragment> fragments = new ArrayList<Fragment>();
        String api = getResources().getString(R.string.api_host_test);
        fragments.add(new OrderFragment(api + "book/getOrderByUserId"));
        fragments.add(new OrderFragment(api + "book/getUnPayOrderByUserId"));
        fragments.add(new OrderFragment(api + "book/getCanUseOrderByUserId"));
        fragments.add(new OrderFragment(api + "book/getBackMoneyByUserId"));
        fragments.add(new OrderFragment(api + "book/getBackMoneyByUserId"));
        OrderFragmentAdapter orderFragmentAdapter = new OrderFragmentAdapter(this,getSupportFragmentManager(),fragments);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(orderFragmentAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}
