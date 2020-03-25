package edu.hnu.aihotel;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import edu.hnu.aihotel.activity.face.ChooseFunctionActivity;
import edu.hnu.aihotel.activity.face.FaceManageActivity;
import edu.hnu.aihotel.activity.face.IdCardRegisterActivity;
import edu.hnu.aihotel.activity.face.RegisterAndRecognizeActivity;
import edu.hnu.aihotel.activity.main.LoginActivity;
import edu.hnu.aihotel.activity.main.OrderActivity;
import edu.hnu.aihotel.model.User;
import edu.hnu.aihotel.ui.Fitness.FitnessFragment;
import edu.hnu.aihotel.ui.bookHotel.BookFragment;
import edu.hnu.aihotel.ui.main.MainAdapter;
import edu.hnu.aihotel.ui.social.SocialFragment;
import edu.hnu.aihotel.util.HttpUtil;
import edu.hnu.aihotel.util.UserDbHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "RegisterAndRecognize";
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView userAvatar;
    private TextView userName;
    private TextView brief;
    public static User user = new User();
    private MainReceiver broadcastReceiver;
    public static String sessionId = "";
    public static UserDbHelper userDbHelper;
    private LocationManager locationManager;
    public static Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDbHelper = new UserDbHelper(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = null;
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(i);
        }
        try {
            if (locationProvider!=null){
                location = locationManager.getLastKnownLocation(locationProvider);
            }
        }catch (SecurityException e){
            e.printStackTrace();
        }

        Map<String,String> map = userDbHelper.getLoginUser();
        String tel = map.get("tel");
        String password = map.get("password");
        if(tel!=null && password!=null){
            login(tel,password);
        }


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headview = navigationView.getHeaderView(0);
        userAvatar = headview.findViewById(R.id.avatar);
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IdCardRegisterActivity.class);
                startActivity(intent);
            }
        });

        userName = headview.findViewById(R.id.userName);
        brief = headview.findViewById(R.id.brief);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.getItemId()是被点击item的ID
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(MainActivity.this,RegisterAndRecognizeActivity.class);
                        intent.putExtra("type",2);
                        startActivity(intent);
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.face_login:
                        Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent2);
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.face_manage:
                        Intent intent3 = new Intent(MainActivity.this, ChooseFunctionActivity.class);
                        startActivity(intent3);
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.order_list:
                        Intent intent4 = new Intent(MainActivity.this, OrderActivity.class);
                        startActivity(intent4);
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        initView();
        initBroadcastReceiver();
        //配置点击弹出的
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void login(String tel, String pw){
        String url = getString(R.string.api_host_test) + "/userOauth/loginByPw";
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            userName.setText(user.getUserName());
                            brief.setText(user.getBrief());
                            Glide.with(MainActivity.this).load(user.getAvatar()).into(userAvatar);
                            Headers headers =response.headers();
                            List cookies = headers.values("Set-Cookie");
                            String session = cookies.get(0).toString();
                            String sessionid = session.substring(0,session.indexOf(";"));
                            MainActivity.sessionId = sessionid;
                            Toast.makeText(getApplicationContext(),"登录成功, "+ user.getUserName(),Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    private void initBroadcastReceiver(){
        broadcastReceiver = new MainReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("loginSuccess");
        registerReceiver(broadcastReceiver, filter);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确定", null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    public class MainReceiver extends BroadcastReceiver {

        private static final String TAG = "MainReceiver";
        public MainReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "loginSuccess":
                    userName.setText(user.getUserName());
                    brief.setText(user.getBrief());
                    Glide.with(MainActivity.this).load(user.getAvatar()).into(userAvatar);
                    break;
            }
        }
    }


    private void initView(){

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new BookFragment());
//        fragments.add(new FoodFragment());
        fragments.add(new FitnessFragment());
        fragments.add(new SocialFragment());
        MainAdapter mainAdapter = new MainAdapter(this,getSupportFragmentManager(),fragments);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainAdapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView navigatorBtn = findViewById(R.id.navigatorBtn);
        Typeface font = Typeface.createFromAsset(getAssets(), "icons/iconfont.ttf");
        navigatorBtn.setTypeface(font);
        navigatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
