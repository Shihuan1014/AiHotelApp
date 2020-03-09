package edu.hnu.aihotel.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.face.RegisterAndRecognizeActivity;
import edu.hnu.aihotel.util.StatusBarUtil;

public class LoginActivity extends AppCompatActivity {

    private ImageView avatar;
    private EditText userName;
    private Button faceLoginBtn;
    private TextView otherWay;
    private TextView registerBtn;
    private TextView problemBtn;

    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_login);
        Window window = getWindow();
        StatusBarUtil.changeStatusBarColor(window, Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initView();
        initListener();
    }

    private void initView(){
        avatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.userName);
        faceLoginBtn = findViewById(R.id.face_login_btn);
        otherWay = findViewById(R.id.other_way_btn);
        registerBtn = findViewById(R.id.register_btn);
        problemBtn = findViewById(R.id.problem_btn);
    }


    private void initListener(){
        faceLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterAndRecognizeActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
    }
}
