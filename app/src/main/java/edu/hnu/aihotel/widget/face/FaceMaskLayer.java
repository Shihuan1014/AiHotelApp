package edu.hnu.aihotel.widget.face;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import edu.hnu.aihotel.R;
import edu.hnu.aihotel.util.DrawHelper;

public class FaceMaskLayer extends View {

    private Paint p, paint, transparentPaint, strokePaint, greenPaint;
    private Canvas cns;
    private Bitmap bitmap;
    private int step = 0;
    private int parentWidth;
    private int parentHeight;
    private String tipText;
    private boolean isLiveness = false;
    private boolean isSucceed  = false;
    private RectF rectF;
    private int sweepAngle;
    private ValueAnimator valueAnimator;

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

        strokePaint = new Paint();
        strokePaint.setColor(Color.WHITE);
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(10);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setColor(getResources().getColor(R.color.colorPrimary));
        greenPaint.setStrokeWidth(10);
        greenPaint.setStyle(Paint.Style.STROKE);
        rectF = new RectF(0,0,0,0);

        paint = new Paint();
        paint.setColor(Color.WHITE);

        transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        sweepAngle = 0;
        tipText = "请将摄像头对准人脸";

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
        cns.drawCircle(parentWidth / 2, getPaddingTop() + parentWidth / 2 - 80, parentWidth / 2 - 10, strokePaint);
        cns.drawCircle(parentWidth / 2, getPaddingTop() + parentWidth / 2 - 80, parentWidth / 2 - 20, transparentPaint);
        cns.drawArc(15,getPaddingTop()-60,parentWidth-15,getPaddingTop() + parentWidth - 100,90,sweepAngle,false,greenPaint);
        cns.drawText(tipText,parentWidth/2, 250,p);
        canvas.drawBitmap(bitmap, 0, 0, p);
    }

    private void initAnimator(){
        valueAnimator = ValueAnimator.ofInt(0,360);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidateView();
            }
        });
    }

    private void invalidateView(){
        postInvalidate();
    }

    public void reverseAnimator(){
        new Thread() {
            public void run()
            {
                Looper.prepare();
                valueAnimator.reverse();
                Looper.loop();
            }
        }.start();
    }

    public void startAnimator(){
        new Thread() {
            public void run()
            {
                Looper.prepare();
                valueAnimator.start();
                Looper.loop();
            }
        }.start();
    }

    public void resumeAnimator(){
        new Thread() {
            public void run()
            {
                Looper.prepare();
                valueAnimator.resume();
                Looper.loop();
            }
        }.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension(parentWidth, parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
