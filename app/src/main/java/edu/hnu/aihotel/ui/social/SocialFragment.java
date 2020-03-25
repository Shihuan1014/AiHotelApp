package edu.hnu.aihotel.ui.social;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.EditNewsActivity;
import edu.hnu.aihotel.ui.bookHotel.RoomNumAdapter;
import edu.hnu.aihotel.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SocialFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Blog> blogList;
    private TextView addNewsBtn;
    private BlogAdapter blogAdapter;

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
        blogAdapter = new BlogAdapter(blogList);
        blogAdapter.setOnItemClickListener(new RoomNumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HttpUtil.httpGet(getResources().getString(R.string.api_host_test) + "/social/deleteBlog?blogId="+blogList.get(position).getId(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"网络故障",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String status = jsonObject.getString("status");
                            if(status!=null && status.equals("success")){
                                blogAdapter.deleteBlog(position);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        blogAdapter.notifyDataSetChanged();
                                    }
                                });
                            }else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(),"删除失败",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(blogAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        addNewsBtn = view.findViewById(R.id.add_news);
        addNewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), EditNewsActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initSocialNews(){
        blogList = new ArrayList<>();
        HttpUtil.httpGet(getResources().getString(R.string.api_host_test) + "/social/getBlogByUserId", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String blogs = jsonObject.getString("blogs");
                    if (blogs!=null){
                        Gson gson = new Gson();
                        blogList = gson.fromJson(blogs,new TypeToken<List<Blog>>(){}.getType());
                        System.out.println(blogList);
                        blogAdapter.initBlog(blogList);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                blogAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 3) {
            String blogId = data.getStringExtra("blogId");
            System.out.println("博文id: " + blogId);
            HttpUtil.httpGet(getResources().getString(R.string.api_host_test)+"/social/getBlog?blogId="+blogId, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String blogStr = jsonObject.getString("blog");
                        System.out.println(blogStr);
                        if(blogStr!=null){
                            Blog blog = gson.fromJson(blogStr, new TypeToken<Blog>(){}.getType());
                            blogAdapter.addBlog(blog);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    blogAdapter.notifyDataSetChanged();
                                    recyclerView.smoothScrollToPosition(0);
                                }
                            });
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
