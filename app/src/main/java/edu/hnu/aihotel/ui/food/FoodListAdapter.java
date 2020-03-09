package edu.hnu.aihotel.ui.food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private List<LikeFood> foods;

    public FoodListAdapter(){

    }

    public FoodListAdapter(List<LikeFood> likeFoods){
        this.foods = likeFoods;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name;
        private TextView type;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_cover);
            name = itemView.findViewById(R.id.food_name);
            type = itemView.findViewById(R.id.food_type);
            price = itemView.findViewById(R.id.food_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LikeFood likeFood = foods.get(position);
        Glide.with(holder.imageView.getContext())
                .load(likeFood.getImageUrl()).skipMemoryCache(true).apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.ALL))
                .transform(new GlideRoundTransform(holder.imageView.getContext(),5))
                .into(holder.imageView);
        holder.name.setText(likeFood.getFoodName());
        holder.price.setText(likeFood.getPrice());
        holder.type.setText(likeFood.getType());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
