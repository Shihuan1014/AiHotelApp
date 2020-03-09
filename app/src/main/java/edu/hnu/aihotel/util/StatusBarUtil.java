package edu.hnu.aihotel.util;

import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    public static void changeStatusBarColor(Window window,int color){
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(color);
    }
}
