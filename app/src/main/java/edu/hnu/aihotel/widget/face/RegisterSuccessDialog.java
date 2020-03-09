package edu.hnu.aihotel.widget.face;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import edu.hnu.aihotel.R;

public class RegisterSuccessDialog extends Dialog {
    private TextView confirmBtn;
    private ImageView imageView;
    private TextView dialogMsg;
    private Context context;

    public RegisterSuccessDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_face_register_fallback);
        confirmBtn = findViewById(R.id.confirm_button);
        imageView = findViewById(R.id.success_icon);
        dialogMsg = findViewById(R.id.dialog_msg);
    }

    public void setImageOfResource(int resource){
        Glide.with(context).load(resource).into(imageView);
    }

    public void setDialogMsg(String msg){
        dialogMsg.setText(msg);
    }

    public void setConfirmBtnOnClick(View.OnClickListener onClickListener){
        confirmBtn.setOnClickListener(onClickListener);
    }

}
