package edu.hnu.aihotel.widget.face;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.util.DrawHelper;

public class FaceMaskLayer extends View {

    private Paint p,grayPaint, paint, transparentPaint, strokePaint, greenPaint;
    private Canvas cns;
    private Bitmap bitmap;
    private int step = 0;
    private int parentWidth;
    private int parentHeight;
    private String tipText;
    private boolean isLiveness = false;
    private boolean isSucceed  = false;
    private RectF rectF;
    private float sweepAngle;
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimator2;
    private ValueAnimator valueAnimator3;

    public FaceMaskLayer(Context context) {
        this(context, null);
    }

    public void changeStep(int i){
        step = i;
    }

    public FaceMaskLayer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(65);
        p.setTextAlign(Paint.Align.CENTER);

        grayPaint = new Paint();
        grayPaint.setColor(getResources().getColor(R.color.colorGray));
        grayPaint.setStyle(Paint.Style.STROKE);
        grayPaint.setStrokeWidth(15);


        strokePaint = new Paint();
        strokePaint.setColor(Color.WHITE);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(10);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setColor(getResources().getColor(R.color.colorPrimary));
        greenPaint.setStrokeWidth(15);
        greenPaint.setStyle(Paint.Style.STROKE);
        rectF = new RectF(0,0,0,0);

        paint = new Paint();
        paint.setColor(Color.WHITE);

        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        sweepAngle = 0;
        tipText = "未检测到人脸";

//        Float[] positions = new Float[5];
//
//        //18.5/24.0/28.0/35.0
//        positions[0]=Float.parseFloat(df.format(position_line[0]/maxCount));
//        positions[1]=Float.parseFloat(df.format(position_line[1]/maxCount));
//        positions[2]=Float.parseFloat(df.format(position_line[2]/maxCount));
//        positions[3]=Float.parseFloat(df.format(position_line[3]/maxCount));
//        positions[4]=Float.parseFloat(df.format(position_line[4]/maxCount));
//
//        SweepGradient sweepGradient = new SweepGradient(centerX+padding, centerX+padding, colors, positions);
//        Matrix matrix = new Matrix();
//        matrix.setRotate(130, centerX, centerX);//加上旋转还是很有必要的，每次最右边总是有一部分多余了,不太美观,也可以不加
//        sweepGradient.setLocalMatrix(matrix);
//        paintCurrent.setShader(sweepGradient);
        initAnimator();
    }

    public void passLiveness(){
        if(!isLiveness && !isSucceed) {
            tipText = "通过活体检测";
            isLiveness = true;
            postInvalidate();
        }
    }

    public void failLiveness(){
        if(isLiveness && !isSucceed){
            tipText = "请将摄像头对准人脸";
            isLiveness = false;
            postInvalidate();
        }
    }

    public void succeed(){
        isSucceed = true;
        tipText = "成功注册人脸";
//        initAnimator3();
        postInvalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //需要在Bitmap上创建画布, 再去画，不然PorterDuff.Mode.CLEAR会使得画的圆是黑色的，
        //因为其父级是Activity
        bitmap = Bitmap.createBitmap(parentWidth, parentHeight, Bitmap.Config.ARGB_8888);
        cns = new Canvas(bitmap);
        cns.drawRect(0, 0, cns.getWidth(), cns.getHeight(), paint);
//        cns.drawCircle(parentWidth / 2, getPaddingTop() + parentWidth / 2 - 80, parentWidth / 2 - 10, strokePaint);
        cns.drawCircle(parentWidth / 2, getPaddingTop() + parentWidth / 2 - 80, parentWidth / 2 - 20, transparentPaint);
        cns.drawArc(14,getPaddingTop()-55,parentWidth-12,getPaddingTop() + parentWidth - 95,120,300,false,grayPaint);
        cns.drawArc(14,getPaddingTop()-55,parentWidth-12,getPaddingTop() + parentWidth - 95,120,sweepAngle,false,greenPaint);
        cns.drawText(tipText,parentWidth/2, 250,p);
        canvas.drawBitmap(bitmap, 0, 0, p);
    }

    private void initAnimator(){
        valueAnimator = ValueAnimator.ofInt(0,145);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidateView();
            }
        });
//        initAnimator2();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                initAnimator2();
            }
        });
        valueAnimator.start();
    }

    private void initAnimator2(){
        valueAnimator2 = ValueAnimator.ofFloat(145,165);
        valueAnimator2.setDuration(1000);
        valueAnimator2.setRepeatCount(1000);
        valueAnimator2.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                invalidateView();
            }
        });
        valueAnimator2.start();
    }

    public void initAnimator3(){
        valueAnimator3 = ValueAnimator.ofFloat(180,300);
        valueAnimator3.setDuration(1500);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (float) animation.getAnimatedValue();
                invalidateView();
            }
        });

        new Thread() {
            public void run()
            {
                Looper.prepare();
                valueAnimator3.start();
                if(valueAnimator2!=null)
                    valueAnimator2.pause();
                Looper.loop();
            }
        }.start();
    }



    private void invalidateView(){
        postInvalidate();
    }

    public void reverseAnimator(){
//        new Thread() {
//            public void run()
//            {
//                Looper.prepare();
//                valueAnimator.reverse();
//                Looper.loop();
//            }
//        }.start();
    }

    public void startAnimator(){
//        new Thread() {
//            public void run()
//            {
//                Looper.prepare();
//                valueAnimator.start();
//                Looper.loop();
//            }
//        }.start();
    }

    public void resumeAnimator(){
//        new Thread() {
//            public void run()
//            {
//                Looper.prepare();
//                valueAnimator.resume();
//                Looper.loop();
//            }
//        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
