package edu.hnu.aihotel.ui.social;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.bookHotel.RoomNumAdapter;

public class PictureEditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bitmap> pictures = new ArrayList<Bitmap>();
    private int PIC_VIEW_TYPE = 0;
    private int ADD_BTN_VIEW_TYPE = 1;
    private RoomNumAdapter.OnItemClickListener listener;

    public PictureEditAdapter(){

    }

    public PictureEditAdapter(List<Bitmap> pictures){
        this.pictures = pictures;
    }

    public void setListener(RoomNumAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder v = null;
        if(viewType == PIC_VIEW_TYPE){
            View root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_picture_upload,parent,false);
            v = new ViewHolder(root);
        }else {
            View root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_btn,parent,false);
            v = new ViewHolder2(root);
        }

        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position < pictures.size()){
            Bitmap url = pictures.get(position);
            Glide.with(((ViewHolder)holder).itemView.getContext()).load(url)
                    .into(((ViewHolder)holder).imageView);
        }else{
            ((ViewHolder2)holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < pictures.size()){
            return PIC_VIEW_TYPE;
        }else {
            return ADD_BTN_VIEW_TYPE;
        }
    }

    public void initData(List<Bitmap> pictures){
        this.pictures = pictures;
    }

    public void addBitmap(Bitmap bitmap){
        this.pictures.add(bitmap);
    }

    @Override
    public int getItemCount() {
        return pictures.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteBtn = itemView.findViewById(R.id.delete);
            imageView = itemView.findViewById(R.id.img);
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder{
        private TextView btn;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.add_btn);
        }
    }
}