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
import edu.hnu.aihotel.ui.bookHotel.HotelListAdapter;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class LikeFoodAdapter extends RecyclerView.Adapter<LikeFoodAdapter.ViewHolder>  {

    private List<LikeFood> likeFoods;

    public LikeFoodAdapter(){

    }

    public LikeFoodAdapter(List<LikeFood> likeFoods){
        this.likeFoods = likeFoods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_like_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LikeFood likeFood = likeFoods.get(position);
        Glide.with(holder.imageView.getContext())
                .load(likeFood.getImageUrl()).apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.ALL))
                .transform(new GlideRoundTransform(holder.imageView.getContext(),5))
                .into(holder.imageView);
        holder.foodName.setText(likeFood.getFoodName());
        holder.foodPrice.setText(likeFood.getPrice());
        holder.foodType.setText(likeFood.getType());
    }

    @Override
    public int getItemCount() {
        return likeFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView foodName;
        private TextView foodPrice;
        private TextView foodType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.food_cover);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodType = itemView.findViewById(R.id.food_type);
        }
    }
}
