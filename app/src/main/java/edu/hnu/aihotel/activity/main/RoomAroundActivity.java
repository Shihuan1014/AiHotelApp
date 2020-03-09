package edu.hnu.aihotel.activity.main;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.util.StatusBarUtil;

public class RoomAroundActivity extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;


    @Override
    protected void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_room_around);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        StatusBarUtil.changeStatusBarColor(getWindow(),getResources().getColor(R.color.transparent));
        webView = findViewById(R.id.webView);
        webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://shihuan.site/a.html");
    }
}
