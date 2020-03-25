package edu.hnu.aihotel.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.ui.bookHotel.RoomNumAdapter;
import edu.hnu.aihotel.ui.social.PictureEditAdapter;
import edu.hnu.aihotel.util.BitmapUtil;
import edu.hnu.aihotel.util.ExMultipartBody;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.UploadProgressListener;
import edu.hnu.aihotel.util.UriTool;
import edu.hnu.aihotel.widget.main.GridSpacingItemDecoration;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditNewsActivity extends AppCompatActivity {

    private TextView addPhoto;
    private TextView submit;
    private EditText editText;

    private int REQUEST_CODE_CHOOSE = 0;
    private Handler handler = new Handler();
    private int totalCount = 0;
    private int currentCount = 0;
    private PictureEditAdapter pictureEditAdapter;
    private RecyclerView pictureRecycleView;
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_news_edit);
        initView();
    }

    private void initView(){

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

        pictureRecycleView = findViewById(R.id.picture_recycle_view);
        pictureEditAdapter = new PictureEditAdapter();
        pictureRecycleView.setAdapter(pictureEditAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        pictureRecycleView.addItemDecoration(new GridSpacingItemDecoration(3,20,false));
        pictureRecycleView.setLayoutManager(gridLayoutManager);
        pictureEditAdapter.setListener(new RoomNumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Matisse.from(EditNewsActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(9)
                        .spanCount(3)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.pics_grid_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });

        editText = findViewById(R.id.text);
    }

    class ImageSizeFilter extends Filter {
        private int mMinWidth;
        private int mMinHeight;
        private int mMaxSize;

        public ImageSizeFilter(int minWidth, int minHeight, int maxSizeInBytes) {
            mMinWidth = minWidth;
            mMinHeight = minHeight;
            mMaxSize = maxSizeInBytes;
        }

        @Override
        protected Set<MimeType> constraintTypes() {
            /*return EnumSet.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP);*/
            return new HashSet<MimeType>() {{
                add(MimeType.WEBP);
                add(MimeType.PNG);
                add(MimeType.MPEG);
                add(MimeType.JPEG);
                add(MimeType.BMP);
            }};
        }

        @Override
        public IncapableCause filter(Context context, Item item) {
            if (!needFiltering(context, item)) {
                return null;
            }
            Point size = PhotoMetadataUtils.getBitmapBound(context.getContentResolver(), item.getContentUri());
            if (size.x < mMinWidth || size.y < mMinHeight || item.size > mMaxSize) {
                // IncapableCause.TOAST、IncapableCause.DIALOG、IncapableCause.NONE
                return new IncapableCause(IncapableCause.NONE, context.getString(R.string.errorFile, mMinWidth, String.valueOf(PhotoMetadataUtils.getSizeInMB(mMaxSize))));
            }
            return null;
        }
    }

    List<Uri> mSelected;

    private String uriToPath(Context context,Uri uri){
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (columnIndex > -1) {
                    path = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        }
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            for (Uri i : mSelected){
                Bitmap bitmap = BitmapUtil.getSmallBitmap(uriToPath(this,i));
                bitmaps.add(bitmap);
            }
            pictureEditAdapter.initData(bitmaps);
            pictureEditAdapter.notifyDataSetChanged();
            totalCount = mSelected.size();
        }
    }


    private void upload(){
        List<String> strings = new ArrayList<String>();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        for (Bitmap bitmap : bitmaps){
            File file = convertBitmapToFile(bitmap);
            System.out.println(file.getAbsolutePath());
            multipartBodyBuilder.setType(MultipartBody.FORM);
            //遍历map中所有参数到builder
            //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
            multipartBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
//            RequestBody requestBody = multipartBodyBuilder.build();
        }

        multipartBodyBuilder.addFormDataPart("text",editText.getText().toString());

        ExMultipartBody exMultipartBody = new ExMultipartBody(multipartBodyBuilder.build(), new UploadProgressListener() {
            @Override
            public void onProgress(long total, long current) {
                System.out.println((float)(current*100/total));
            }
        });

        HttpUtil.httpRequestBody("http://192.168.124.8:8080/test/social/makeBlog",exMultipartBody,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("hhh: " );
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String blogId = jsonObject.getString("blogId");
                    if(blogId!=null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditNewsActivity.this,"上传成功",Toast.LENGTH_LONG).show();
                            }
                        });
                        Intent intent = new Intent();
                        intent.putExtra("blogId",blogId);
                        setResult(3,intent);
                        finish();
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditNewsActivity.this,"上传失败"+jsonObject,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private File convertBitmapToFile(Bitmap bitmap) {
        File f = null;
        System.out.println("byteCount: " + bitmap.getAllocationByteCount());
        try {
            // create a file to write bitmap data
            f = new File(EditNewsActivity.this.getCacheDir(), System.currentTimeMillis()+".png");
            f.createNewFile();

            // convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            // write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("file convert error");
        }
        return f;
    }
}
