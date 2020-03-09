package edu.hnu.aihotel.ui.social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;

public class SocialFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<SocialNews> socialNews;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_social,container,false);
        initSocialNews();
        initView(page);
        return page;
    }


    private void initView(View view){
        recyclerView = view.findViewById(R.id.social_news);
        SocialNewsAdapter socialNewsAdapter = new SocialNewsAdapter(socialNews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(socialNewsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void initSocialNews(){
        socialNews = new ArrayList<SocialNews>();
        for(int i = 0;i < 6; i++){
            socialNews.add(new SocialNews("1","shihuan","" +
                    "Hello World!","http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg;" +
                    "http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg;" +
                    "http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg;" +
                    "http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg;" +
                            "http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg;"
                    ,"http://img3.imgtn.bdimg.com/it/u=3065809958,4106081615&fm=26&gp=0.jpg",
                    "10分钟前","120","6","66"));
        }

    }
}
