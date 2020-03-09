package edu.hnu.aihotel.ui.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hnu.aihotel.R;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private String[] pictures;

    public PictureAdapter(){

    }

    public PictureAdapter(String[] pictures){
        this.pictures = pictures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item_picture,parent,false);
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = pictures[position];
        Glide.with(holder.itemView.getContext()).load(url)
                .into(holder.imageView);
    }

    public void initData(String[] pictures){
        this.pictures = pictures;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pictures.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
        }
    }
}
