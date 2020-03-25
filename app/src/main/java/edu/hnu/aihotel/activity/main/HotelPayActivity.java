package edu.hnu.aihotel.activity.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Result;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.model.AuthResult;
import edu.hnu.aihotel.model.PayResult;
import edu.hnu.aihotel.ui.bookHotel.Hotel;
import edu.hnu.aihotel.util.OrderInfoUtil2_0;
import edu.hnu.aihotel.util.StatusBarUtil;

public class HotelPayActivity extends AppCompatActivity {

    private TextView backBtn;
    private TextView payBtn;
    private RadioButton wxPay;
    private RadioButton aliPay;
    private TextView leftTimeView;
    private TextView priceView;
    private TextView orderNameView;
    private int payWay = 0;
    private final int WX_PAY = 0;
    private final int ALI_PAY = 1;
    private Long created;
    private String aliPayString = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D";   // 订单信息

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016092300578140";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102176918760";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "unfiym9475@sandbox.com";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCfurlDhuflnhP3bHXl16puvRvuUapIu8P0iaEfKWU+/eYLftO0qbe/sczl27PcPnu2mLo8QmOgklaL9k6Pc98f0WKH6mte7Sb+sW/SAvPn6C+I158fxG6BagZjuvpzeBFw2jij7LVkNlJDWzSTQP06dlzxYUd3eGL4A3SOz0yXC4233cMgniYQCi+ogJWb57jcrPQ3MgxCkJAflULdPdt/WgV5GktEQZc2ff5phR7hXtSe8+fLAPxMGGTIjdoCnHa/N6VgWaQ909xrBh/WXolUKjxXx+igwHAslupxS7ynjDEJ+rJ1lrRY6/jTX2XP5wMHPwvkKi+JE0gOHgEdGlzLAgMBAAECggEBAIjbG9FXzWlXwBPGwl1kI4suTLgtEJGxD3x+T6a7eRw3vtsql4m7wMxmMwDe9YRHpQgWY7stxt7Vg4H4T+dpiEtiw+FgYQ68cyTWMtDl5sVUkyXUWWqGi4gmp8LbeatlvMvMrasGLYTnZiSACNQCYvWsZIYMq4cZhvKpQzz5MM5YoXXk1DdpfmNpH9LkUatJnvAtz4RDj1QTwcekdOW6GPmtI7v6b+HEV+SfkO0fzCOwOeJlHxfdX9OiJdTeiKVafsTPG3aiGmxitgSo1aklCINDHONDhawhoHTIPsHUW1RgiuI89R1OwjXAKNY02RBswqZ3987c14nITzzWZYMVcwECgYEA6tPnUGpCA6tSvxYTPzb8+zfPJbtp3k1yhkqOfKUcxYwWyfaWENrZMP/QltD8+noKKUeqbehHJczvloPdzZDAb0H67Y5UkP8oEdML18eSyGCyI5PwtDkWO7qe1kOtiyvlWcA5/4DjK4KMH7Yt2HzQ2Ub55/+eCnu6XX0Mz6iD89sCgYEAriF2h5d6ap950rNzxbW5kNkAF5CX+6nlqWuFDsDCchlP3R00VgCRGE3W/pH//B9HHNTLDk5DRHTde8WruD+ouu/OiJlQ+DBsZC6T2zcMmBm/p6vgWuKLLrCWymvmDpNXVZ2tMHJ6IQ2CijH0+YOkLDdp4DSL+DvZsh7PUDHnBdECgYAyNPQz4UQT/MFIUbPzxBH6tEf5zUQpX22pAK/OPo8UPYtxyuLy35eoPkmetTghZY5enQ/3R75Oeq6PA8qcdOJv5y97Mv8psSQkC3dwvx7G8MWlja/dGIeEZbYoIXgtVUlIEy1TT39CgjrrXMwYiTct/VrZG3Hy09lYu6/Nhj4QGQKBgFWwDd3ngswZMh+nQx9Vr71Bq7Ps6X1kO6nFSTLbVFRFdHGAv5JasQvesh+PxQJSefIMHOgrRxrWz27q9Pe0C9oBzAaYO6oGWjBqY0QKsN3gPdnK0JqoxIjzSSDPO+SMSPvOnb0reEHvnf9XWg+24gEIO8ei1AYRnazzUF1MUT0hAoGBANxu0pTGsEo5BtQMlMRXiAa5KsBP6mQbk+Wp+S9ju+C4RH3LEI4VqYQJR2ssCS2/FFW6i1ye2KXQiiiZnS02iJTE699wBV81B6N3oeTrzp5aZMP4R7JtHhvN5DF7ymNfLaFmd88KTU9d4OyYCAGhUgsENYCFVzDVMWgf6rCtXaRT";
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJm0A8JQvg+j0moQmqnxlndSicYqdlOT4FqAQhVWGzV+zeYzKU/1aTJpKv+eH28Mk0QF3LC00yuNj+8ZGrdjaCwlwU0eZshnNwheUvYtsKz1mqbRaiiQKAh/Wx+f1kevGcdC3iRZWqx0ROpd0THWmTgD3bsje3eP7G5Yi5GFr2KtAgMBAAECgYBB9EbwqOkFmQx/SuSzK+GfDiHkkO/5aSkKbKLuuOYwDFuZ/jYqA2rCoPI8gcOHSWQf+kv6330F4Vb0CFe4fcWDEd6Rre1A9ljz8iFNP/OA4/ulFK21m7ndc57ECqwx9btXJXDRqjDZbKojLQeB4iqkEiztEj4LMKFCnjicicH/GQJBAMs5UGBbduwk6dQlbPx4zn6nJ1yvAVYB2zIjgmmZC6zPKRzNTmMDJmtpNfdPwccalxcDPnZbGPe+Gp62Husj+DsCQQDBnnnsyirxAzSh6Ft28Rx/nM696d5C1p9ejDjmWPBTXvJBTYacEefl+pDjKJUPEjUZfIfc0Sif5XjRKcXSXEo3AkBNEeFPiaBiEeWzlLXuUYpOMeoVljD1gyKzsw/EKExrLu0yhRHTLuClH3Nd5Rth/M2L5qkLjaS8X5YM1piHKE79AkA6SIpLTVA/3McrNdbSpyH9CCbz5EKTvCedPniiXoN5lUs3fS7YLEuyXwreVokIUGLLmUEMp6mi+GC2Fe0GteFhAkAluqk1iJ7s6PFmBwssiG5k4ezUXd3+uUuJERHX9DrWqCQs6dlqiSHCLIxfYsx+kBgQj9hiKEFv+VZPBeB+3SZn";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private CountDownTimer timer2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(HotelPayActivity.this, getString(R.string.pay_success) + payResult);
                        Intent intent = new Intent();
                        intent.putExtra("result","success");
                        setResult(3,intent);
                        HotelPayActivity.this.finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(HotelPayActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(HotelPayActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(HotelPayActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    @Override
    protected void onCreate(Bundle saveInstance){
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_room_pay);
        Window window = getWindow();
        StatusBarUtil.whiteBgAndBlackFont(window);
        StatusBarUtil.changeStatusBarColor(window, getResources().getColor(R.color.colorGray));
        Intent intent = getIntent();
        aliPayString = intent.getStringExtra("aliPayString");
        created = intent.getLongExtra("created",System.currentTimeMillis());
        initView();
        priceView.setText(intent.getStringExtra("price"));
        orderNameView.setText(intent.getStringExtra("orderName"));

        Timer timer = new Timer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        final long period = 30*60*1000L - (System.currentTimeMillis() - created);
        if(period>0L){
            timer2 = new CountDownTimer(period, 1000) {
                //根据间隔时间来不断回调此方法，这里是每隔1000ms调用一次
                @Override
                public void onTick(long millisUntilFinished) {
                    String s = simpleDateFormat.format(millisUntilFinished);
                    System.out.println(s);
                    leftTimeView.setText(s);
                }

                //结束倒计时调用
                @Override
                public void onFinish() {
                    //todo
                }
            };
            timer2.start();
        }

    }

    private void initView(){
        Typeface typeface = Typeface.createFromAsset(getAssets(), "icons/iconfont.ttf");
        backBtn = findViewById(R.id.back);
        backBtn.setTypeface(typeface);


        payBtn = findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payV2(v);
            }
        });

        leftTimeView = findViewById(R.id.left_time);
        priceView = findViewById(R.id.price);
        orderNameView = findViewById(R.id.order_name);
    }

    public void back(){
        HotelPayActivity.this.finish();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }


    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

//        final String orderInfo = orderParam + "&" + sign;
        System.out.println(aliPayString);
//        String aliPayString = "alipay_sdk=alipay-sdk-java-3.1.0&app_id=2016092300578140&biz_content=%7B%22body%22%3A%22%E8%AE%A2%E5%8D%95%E6%B5%8B%E8%AF%95Body%22%2C%22out_trade_no%22%3A%220316213343-1611%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98Subject%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22300.0%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=UqoaGO6P0TZdeA2Lzg%2F75fEqPHiLow9oaH%2BfI3ZbjsozE0pUCRuvrhR4zyFYOxvl35YjPTj5x8BAuBHAl%2FS2D4Pvvz6fG%2Bc0qFGElcUBq%2Funy9mtqi6v2eQzxPDMunWevgeVQIdTj7fplvlzSToELdTEcBxLffJgBxbe9DQDcSspPifOMpT4FKalHvx3fy8ysfv1MEEzdbW32WkPy1D5JD1V%2FkQRk7s21shTtM8CHYApHqEfrGlt%2Fjy1kGtQ1oyCf%2FXusGFX2yUiGBq136XVj7%2BL1Av6kczThhs9Eoc3qP9TxQ48iMF4wvT4eQ9cH4VkxM01NcYpchMYgczhXoeBCg%3D%3D&sign_type=RSA2&timestamp=2020-03-16+21%3A33%3A43&version=1.0";

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(HotelPayActivity.this);
                Map<String, String> result = alipay.payV2(aliPayString, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        timer2.cancel();
        timer2 = null;
    }
}
