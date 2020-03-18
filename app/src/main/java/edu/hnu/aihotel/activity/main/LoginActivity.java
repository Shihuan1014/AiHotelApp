package edu.hnu.aihotel.activity.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import edu.hnu.aihotel.MainActivity;
import edu.hnu.aihotel.R;
import edu.hnu.aihotel.activity.face.RegisterAndRecognizeActivity;
import edu.hnu.aihotel.model.User;
import edu.hnu.aihotel.ui.hotelDetail.RoomType;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.StatusBarUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageView avatar;
    private EditText userName;
    private Button faceLoginBtn;
    private TextView otherWay;
    private TextView registerBtn;
    private TextView problemBtn;
    private EditText password;
    private LinearLayout passWordLayout;
    private Button passwordLoginBtn;
    private int currentWay = 0;
    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_login);
        Window window = getWindow();
        StatusBarUtil.changeStatusBarColor(window, Color.WHITE);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
        initListener();
        initBroadcastReceiver();
    }

    private void initView(){
        avatar = findViewById(R.id.avatar);
        userName = findViewById(R.id.userName);
        userName.requestFocus();
        faceLoginBtn = findViewById(R.id.face_login_btn);
        otherWay = findViewById(R.id.other_way_btn);
        otherWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentWay == 0){
                    faceLoginBtn.setVisibility(View.GONE);
                    passwordLoginBtn.setVisibility(View.VISIBLE);
                    passWordLayout.setVisibility(View.VISIBLE);
                    currentWay = 1;
                }else{
                    faceLoginBtn.setVisibility(View.VISIBLE);
                    passwordLoginBtn.setVisibility(View.GONE);
                    passWordLayout.setVisibility(View.GONE);
                    currentWay = 0;
                }
            }
        });
        password = findViewById(R.id.password);
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    login(userName.getText().toString(),password.getText().toString());
                }
                return false;
            }
        });
        passWordLayout = findViewById(R.id.password_layout);
        registerBtn = findViewById(R.id.register_btn);
        problemBtn = findViewById(R.id.problem_btn);
        passwordLoginBtn = findViewById(R.id.password_login_btn);
        passwordLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = userName.getText().toString();
                String pw = password.getText().toString();
                login(tel,pw);
            }
        });
    }

    private void login(String tel, String pw){
        password.clearFocus();
        InputMethodManager manager = ((InputMethodManager)LoginActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null)
            manager.hideSoftInputFromWindow(LoginActivity.this.getWindow().getDecorView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        String url = getString(R.string.api_host_test) + "/userOauth/loginByPw";
        System.out.println(url);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("tel",tel);
        builder.add("password",pw);
        FormBody body = builder.build();
        HttpUtil.httpPost(url, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(str);
                    str = jsonObject.getString("userInfo");
                }catch (Exception e){
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                System.out.println(str);
                User user = gson.fromJson(str,new TypeToken<User>(){}.getType());
                if(user.getId()!=null){
                    MainActivity.user = user;
                    Intent intent = new Intent("loginSuccess");
                    sendBroadcast(intent);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(LoginActivity.this).load(user.getAvatar())
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(avatar);
                            Headers headers =response.headers();
                            List cookies = headers.values("Set-Cookie");
                            String session = cookies.get(0).toString();
                            String sessionid = session.substring(0,session.indexOf(";"));
                            MainActivity.sessionId = sessionid;
                            Toast.makeText(getApplicationContext(),"登录成功, "+ user.getUserName(),Toast.LENGTH_SHORT).show();
                            LoginActivity.this.finish();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                System.out.println(user);
            }
        });
    }

    private void initBroadcastReceiver(){
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case "loginSuccess":
                        LoginActivity.this.finish();
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("loginSuccess");
        registerReceiver(broadcastReceiver, filter);
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
