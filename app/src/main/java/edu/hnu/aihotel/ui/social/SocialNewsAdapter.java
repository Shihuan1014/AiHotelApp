package edu.hnu.aihotel.ui.social;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;

public class SocialNewsAdapter extends RecyclerView.Adapter<SocialNewsAdapter.ViewHolder> {

    private List<SocialNews> socialNews;

    public SocialNewsAdapter(){

    }

    public SocialNewsAdapter(List<SocialNews> socialNews){
        this.socialNews = socialNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.social_news_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocialNews social = socialNews.get(position);
        Glide.with(holder.itemView.getContext())
                .load(social.getAvatar())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.avatar);
        holder.userName.setText(social.getUserName());
        holder.time.setText(social.getTime());
        holder.content.setText(social.getContent());
        holder.shareCount.setText(social.getShareCount());
        holder.likeCount.setText(social.getLikeCount());
        holder.commentCount.setText(social.getCommentCount());
        ((PictureAdapter)holder.pictureList.getAdapter()).initData(social.getPictures().split(";"));
    }

    @Override
    public int getItemCount() {
        if(socialNews!=null)
            return socialNews.size();

        return  0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView avatar;
        private TextView userName;
        private TextView time;
        private TextView content;
        private TextView shareBtn;
        private TextView shareCount;
        private TextView likeBtn;
        private TextView likeCount;
        private TextView commentCount;
        private TextView commentBtn;
        private TextView more;
        private RecyclerView pictureList;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "icons/iconfont.ttf");
            avatar = itemView.findViewById(R.id.avatar);
            userName = itemView.findViewById(R.id.userName);
            time = itemView.findViewById(R.id.share_time);
            content = itemView.findViewById(R.id.news_content);
            shareBtn = itemView.findViewById(R.id.share_btn);
            shareCount = itemView.findViewById(R.id.share_count);
            shareBtn.setTypeface(typeface);
            likeBtn = itemView.findViewById(R.id.like_btn);
            likeCount = itemView.findViewById(R.id.like_count);
            likeBtn.setTypeface(typeface);
            commentBtn = itemView.findViewById(R.id.comment_btn);
            commentCount = itemView.findViewById(R.id.comment_count);
            commentBtn.setTypeface(typeface);
            more = itemView.findViewById(R.id.more);
            more.setTypeface(typeface);
            pictureList = itemView.findViewById(R.id.picture_list);
            PictureAdapter pictureAdapter = new PictureAdapter();
            pictureList.setAdapter(pictureAdapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(pictureList.getContext(),3);
            pictureList.setLayoutManager(gridLayoutManager);
            pictureList.setNestedScrollingEnabled(false);
            pictureList.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        }
    }
}
