package edu.hnu.aihotel.ui.order.all;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.model.Order;
import edu.hnu.aihotel.widget.main.GlideRoundTransform;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private List<Order> orderList;

    public OrderListAdapter(List<Order> orderList){
        this.orderList = orderList;
    }

    public void initData(List<Order> orderList){
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.hotelName.setText(order.getHotelName());
        holder.orderName.setText(order.getOrderName());
        holder.period.setText(order.getStartDate() + " - " + order.getEndDate());
        holder.price.setText("总价: ￥" + order.getPrice());
        if(order.getPayStatus()==1){
            if(order.getStatus()==1){
                holder.status.setText("可使用");
            }else{
                holder.status.setText("已完成");
            }
        }else{
            holder.status.setText("支付超时");
        }
        Glide.with(holder.itemView.getContext()).load(order.getHotelCover())
                .transform(new GlideRoundTransform(holder.itemView.getContext()))
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if(orderList!=null)
            return orderList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView hotelName;
        private TextView status;
        private ImageView img;
        private TextView orderName;
        private TextView period;
        private TextView price;
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name);
            status = itemView.findViewById(R.id.order_status);
            img = itemView.findViewById(R.id.hotel_cover);
            orderName = itemView.findViewById(R.id.order_name);
            period = itemView.findViewById(R.id.order_period);
            price = itemView.findViewById(R.id.order_price);
            button = itemView.findViewById(R.id.order_btn);
        }
    }
}
