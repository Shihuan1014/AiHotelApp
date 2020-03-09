package edu.hnu.aihotel.ui.Fitness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courseList;

    public CourseAdapter(){

    }

    public CourseAdapter(List<Course> list){
        this.courseList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_fitness_course,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courseList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(course.getCover())
                .transform(new GlideRoundTransform(holder.itemView.getContext()))
                .into(holder.imageView);
        holder.courseName.setText(course.getCourseName());
        holder.level.setText(course.getLevel());
        holder.courseNum.setText(course.getCourseNum());
        holder.cost.setText(course.getCost());
        holder.people.setText(course.getPeople());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView level;
        private TextView cost;
        private TextView courseNum;
        private TextView courseName;
        private TextView people;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.course_cover);
            level = itemView.findViewById(R.id.course_level);
            cost = itemView.findViewById(R.id.course_cost);
            courseName = itemView.findViewById(R.id.course_name);
            courseNum = itemView.findViewById(R.id.course_num);
            people = itemView.findViewById(R.id.course_people);
        }
    }
}
