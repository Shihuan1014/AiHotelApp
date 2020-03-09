package edu.hnu.aihotel.ui.bookHotel;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.hnu.aihotel.R;

public class AreaListAdapter extends RecyclerView.Adapter<AreaListAdapter.ViewHolder> {

    private List<Area> areas;
    private int selectedIndex;
    private int oldIndex;

    private OnItemClickListener myItemClickListener;

    public AreaListAdapter(List<Area> areas){
        this.areas = areas;
        this.selectedIndex = 0;
        this.oldIndex = 0;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_area_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.areaName.setText(area.getAreaName());
        holder.areaPrice.setText(area.getAreaPrice());
        if(selectedIndex == position){
            holder.itemView.setBackgroundResource(R.drawable.book_area_select);
            holder.areaName.setTextColor(Color.WHITE);
            holder.areaPrice.setTextColor(Color.WHITE);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.book_area_normal);
            holder.areaName.setTextColor(Color.BLACK);
            holder.areaPrice.setTextColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public void setSelectedIndex(int position){
        oldIndex = selectedIndex;
        this.selectedIndex = position;
        notifyItemChanged(oldIndex);
        notifyItemChanged(position);
        System.out.println("从" + oldIndex + "到" + position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView areaName;
        private TextView areaPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (myItemClickListener != null) {
                        myItemClickListener.onItemClick(itemView,getAdapterPosition());
                    }
                }
            });
            areaName = itemView.findViewById(R.id.area_name);
            areaPrice = itemView.findViewById(R.id.area_price);
        }
    }
}
