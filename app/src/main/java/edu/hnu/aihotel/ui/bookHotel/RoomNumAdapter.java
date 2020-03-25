package edu.hnu.aihotel.ui.bookHotel;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.HotelDetailActivity;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class RoomNumAdapter extends RecyclerView.Adapter<RoomNumAdapter.ViewHolder>{

    private List<String> list;
    private int currentSelect = 0;
    private int lastSelect = 0;
    private OnItemClickListener mOnItemClickListener;

    public RoomNumAdapter(List<String> list){
        this.list = list;
    }

    public void initData(List<String> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public RoomNumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_choose,parent,false);
        RoomNumAdapter.ViewHolder holder = new RoomNumAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomNumAdapter.ViewHolder viewHolder, int position) {
        RoomNumAdapter.ViewHolder holder = viewHolder;
        holder.num.setText(list.get(position));
        holder.num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelect = currentSelect;
                currentSelect = position;
                notifyItemChanged(position);
                notifyItemChanged(lastSelect);

                mOnItemClickListener.onItemClick(v,position);
            }
        });
        if (position == currentSelect){
            holder.num.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.num.setBackgroundColor(holder.itemView.getResources().getColor(R.color.colorGray));
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null)
            return list.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num = itemView.findViewById(R.id.room_num);
        }
    }
}
