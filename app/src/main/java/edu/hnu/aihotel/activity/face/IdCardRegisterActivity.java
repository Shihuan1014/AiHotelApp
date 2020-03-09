package edu.hnu.aihotel.activity.face;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.arcsoft.face.FaceEngine;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.widget.face.ProgressDialog;

public class IdCardRegisterActivity extends BaseActivity{
    private static final String TAG = "SingleImageActivity";
    private ImageView ivShow;
    private TextView tvNotice;
    private Button button;

    /**
     * 请求权限的请求码
     */
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 请求选择本地图片文件的请求码
     */
    private static final int ACTION_CHOOSE_IMAGE = 0x201;

    private static final int ACTION_TAKE_PIC = 0x202;
    /**
     * 提示对话框
     */
    private AlertDialog progressDialog;
    /**
     * 被处理的图片
     */
    private Bitmap mBitmap = null;

    /**
     * 所需的所有权限信息
     */
    private static String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_idcard);
        initView();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            List<String> permissionList = new ArrayList<>(Arrays.asList(NEEDED_PERMISSIONS));
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            NEEDED_PERMISSIONS = permissionList.toArray(new String[0]);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void initView(){
        ivShow = findViewById(R.id.id_card_preview);
        button = findViewById(R.id.camera_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
//                chooseLocalImage(v);
            }
        });

        progressDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.processing)
                .setView(new ProgressBar(this))
                .create();
    }


    private void openCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,ACTION_TAKE_PIC);
    }

    /**
     * 从本地选择文件
     *
     * @param view
     */
    public void chooseLocalImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ACTION_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_CHOOSE_IMAGE) {
            if (data == null || data.getData() == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (mBitmap == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }
            Glide.with(ivShow.getContext())
                    .load(mBitmap)
                    .into(ivShow);
        }
        else if(requestCode == ACTION_TAKE_PIC){
            mBitmap = data.getParcelableExtra("data");
            Glide.with(ivShow.getContext())
                    .load(mBitmap)
                    .into(ivShow);
            progressDialog.setTitle("正在识别证件,请稍等...");
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                    Intent intent = new Intent(IdCardRegisterActivity.this,RegisterAndRecognizeActivity.class);
                    startActivity(intent);
                }
            }).start();
        }
    }

    @Override
    protected void afterRequestPermission(int requestCode, boolean isAllGranted) {

    }


    @Override
    protected void onDestroy() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
        mBitmap = null;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
        System.gc();
        super.onDestroy();
    }


}
