package edu.hnu.aihotel;

import android.Manifest;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.os.Bundle;

import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import edu.hnu.aihotel.activity.face.BaseActivity;
import edu.hnu.aihotel.activity.face.ChooseFunctionActivity;
import edu.hnu.aihotel.activity.face.FaceManageActivity;
import edu.hnu.aihotel.activity.face.IdCardRegisterActivity;
import edu.hnu.aihotel.activity.face.RegisterAndRecognizeActivity;
import edu.hnu.aihotel.activity.main.LoginActivity;
import edu.hnu.aihotel.faceserver.CompareResult;
import edu.hnu.aihotel.faceserver.FaceServer;
import edu.hnu.aihotel.model.FacePreviewInfo;
import edu.hnu.aihotel.model.UserInfo;
import edu.hnu.aihotel.ui.Fitness.FitnessFragment;
import edu.hnu.aihotel.ui.bookHotel.BookFragment;
import edu.hnu.aihotel.ui.food.FoodFragment;
import edu.hnu.aihotel.ui.gallery.GalleryFragment;
import edu.hnu.aihotel.ui.home.HomeFragment;
import edu.hnu.aihotel.ui.main.MainAdapter;
import edu.hnu.aihotel.ui.send.SendFragment;
import edu.hnu.aihotel.ui.share.ShareFragment;
import edu.hnu.aihotel.ui.social.SocialFragment;
import edu.hnu.aihotel.util.ConfigUtil;
import edu.hnu.aihotel.util.DrawHelper;
import edu.hnu.aihotel.util.camera.CameraHelper;
import edu.hnu.aihotel.util.camera.CameraListener;
import edu.hnu.aihotel.util.face.FaceHelper;
import edu.hnu.aihotel.util.face.FaceListener;
import edu.hnu.aihotel.util.face.LivenessType;
import edu.hnu.aihotel.util.face.RequestFeatureStatus;
import edu.hnu.aihotel.util.face.RequestLivenessStatus;
import edu.hnu.aihotel.widget.face.FaceMaskLayer;
import edu.hnu.aihotel.widget.face.FaceSearchResultAdapter;
import edu.hnu.aihotel.widget.face.RegisterSuccessDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "RegisterAndRecognize";
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView userAvatar;
    public static UserInfo userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Intent intent3 = new Intent(MainActivity.this, FaceManageActivity.class);
                        startActivity(intent3);
                        drawer.closeDrawer(Gravity.LEFT);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        initView();
        initUserInfo();
        //配置点击弹出的
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initUserInfo(){
        userInfo = new UserInfo();
        userInfo.setUserId("123456");
    }

    private void initView(){

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new BookFragment());
        fragments.add(new FoodFragment());
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
