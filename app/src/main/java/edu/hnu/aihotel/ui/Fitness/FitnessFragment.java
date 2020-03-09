package edu.hnu.aihotel.ui.Fitness;

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

public class FitnessFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Course> courseList;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_fitness,container,false);
        initCourse();
        initView(page);
        return page;
    }

    private void initCourse() {
        courseList = new ArrayList<Course>();
        for(int i = 0; i < 5; i++) {
            courseList.add(new Course("瑜伽健身"
                    , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583485220830&di=682e910e2e06d6a3b9fab5d327b3a8d6&imgtype=0&src=http%3A%2F%2Fpic170.nipic.com%2Ffile%2F20180619%2F27082678_235840760000_2.jpg",
                    "中级", "5节课", "410千卡", "50343"));

        }
    }

    private void initView(View page){
        recyclerView = page.findViewById(R.id.fitness_course);
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
        CourseAdapter courseAdapter = new CourseAdapter(courseList);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
