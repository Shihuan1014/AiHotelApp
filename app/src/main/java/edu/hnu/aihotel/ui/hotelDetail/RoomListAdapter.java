package edu.hnu.aihotel.ui.hotelDetail;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.RoomAroundActivity;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private List<Room> roomList;

    public RoomListAdapter(){

    }

    public RoomListAdapter(List<Room> rooms){
        this.roomList = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.room_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListAdapter.ViewHolder holder, int position) {
        Room room = roomList.get(position);
        Glide.with(holder.itemView).load(room.getCoverUrl()).into(holder.cover);
        holder.bedSize.setText(room.getBedSize());
        holder.price.setText(room.getPrice());
        holder.roomName.setText(room.getRoomName());
        holder.roomSize.setText(room.getSize());
        holder.window.setText(room.getWindow());
        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"预定功能完善中",Toast.LENGTH_SHORT);
            }
        });
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), RoomAroundActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(roomList!=null){
            return roomList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView cover;
        private TextView roomName;
        private TextView roomSize;
        private TextView window;
        private TextView bedSize;
        private TextView price;
        private Button order;
        private TextView iconAround;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.room_cover);
            roomName = itemView.findViewById(R.id.room_name);
            roomSize = itemView.findViewById(R.id.room_size);
            window = itemView.findViewById(R.id.room_window);
            bedSize = itemView.findViewById(R.id.room_bed_size);
            price = itemView.findViewById(R.id.room_price);
            order = itemView.findViewById(R.id.room_order);
            Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "icons/iconfont.ttf");
            iconAround = itemView.findViewById(R.id.icon_around);
            iconAround.setTypeface(font);
        }
    }
}
