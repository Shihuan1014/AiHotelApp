package edu.hnu.aihotel.ui.bookHotel;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.main.HotelDetailActivity;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {
    private List<Hotel> hotels;

    public HotelListAdapter(List<Hotel> hotels){
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_area_hotel_item,parent,false);
        HotelListAdapter.ViewHolder holder = new HotelListAdapter.ViewHolder(view);
        return holder;
    }

    public void initData(List<Hotel> list){
        hotels = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), HotelDetailActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        Glide.with(holder.itemView.getContext()).load(hotel.getCover())
                .apply(RequestOptions.skipMemoryCacheOf(true).diskCacheStrategy(DiskCacheStrategy.ALL))
                .transform(new GlideRoundTransform(holder.itemView.getContext()))
                .into(holder.imageView);
        holder.hotelAddress.setText(hotel.getAddress());
        holder.hotelName.setText(hotel.getName());
        holder.minPrice.setText(hotel.getPrice());
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView hotelAddress;
        private TextView hotelName;
        private TextView minPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_hotel_cover);
            hotelAddress = itemView.findViewById(R.id.hotel_address);
            hotelName = itemView.findViewById(R.id.hotel_name);
            minPrice = itemView.findViewById(R.id.minPrice);
        }
    }
}
