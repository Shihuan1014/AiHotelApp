package edu.hnu.aihotel.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.main.LinearSpacingItemDecoration;

public class FoodFragment extends Fragment {
    private List<LikeFood> likeFoodList = new ArrayList<LikeFood>();
    private RecyclerView likeFoodRecycleView;
    private RecyclerView foodListRecycleView;
    private FoodListAdapter foodListAdapter;
    private LikeFoodAdapter likeFoodAdapter;
    private ImageView hotelCover;
    private View page;
    protected boolean isVisibleToUser;
    protected boolean isViewInitiated;
    protected boolean isDataInitiated;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        page = inflater.inflate(R.layout.fragment_food,container,false);
        initLikeFood();
        initView(page);
        return page;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("美食Activity加载!");
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser && !isViewInitiated){
//            System.out.println("美食页面已加载!");
//            this.isVisibleToUser = true;
//            initView(page);
//            initLikeFood();
//        }
//    }


    private void initView(View view){
        if(view!=null){
            likeFoodRecycleView = view.findViewById(R.id.guess_you_like);
            foodListRecycleView = view.findViewById(R.id.food_list);
            likeFoodAdapter = new LikeFoodAdapter(likeFoodList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            likeFoodRecycleView.setAdapter(likeFoodAdapter);
            likeFoodRecycleView.setLayoutManager(layoutManager);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(view.getContext());
            layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
            foodListAdapter = new FoodListAdapter(likeFoodList);
            foodListRecycleView.setLayoutManager(layoutManager2);
            foodListRecycleView.setNestedScrollingEnabled(false);
            foodListRecycleView.setAdapter(foodListAdapter);
            isViewInitiated = true;
            hotelCover = view.findViewById(R.id.hotel_cover);
            Glide.with(view).load("http://p0.meituan.net/tdchoteldark/aa000a61da1eb7a83028b8f36370dfc9862822.jpg")
                    .into(hotelCover);
        }
    }

    private void initLikeFood(){
        if(!isDataInitiated){
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            likeFoodList.add(new LikeFood("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1582899898838&di=5dc58bc9df118be6932f26d0113adefb&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2694447093%2C3399628079%26fm%3D214%26gp%3D0.jpg",
                    "意面","面食","20"));
            this.isDataInitiated = true;
        }
    }
}
