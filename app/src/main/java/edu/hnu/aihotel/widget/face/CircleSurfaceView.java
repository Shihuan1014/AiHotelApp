package edu.hnu.aihotel.widget.face;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * 圆形摄像头预览控件
 *
 * 支持暂停预览，圆形的摄像头预览
 */
public class CircleSurfaceView extends SurfaceView{

    private static final String TAG = "CircleCameraPreview";
    /**
     * 半径
     */
    private int radius;

    /**
     * 中心点坐标
     */
    private Point centerPoint;

    /**
     * 剪切路径
     */
    private Path clipPath;


    public CircleSurfaceView(Context context) {
        super(context);
        init();
    }

    public CircleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        clipPath = new Path();
        centerPoint = new Point();
//        setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredWidth());
//                System.out.println("Measured: " + view.getMeasuredWidth() + "  " + view.getMeasuredHeight());
//                outline.setRoundRect(rect, radius);
//            }
//        });
//        setClipToOutline(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 坐标转换为实际像素
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出圆形的中心点
        centerPoint.x = widthSize >> 1;
        centerPoint.y = heightSize >> 1;
        // 计算出最短的边的一半作为半径
        radius = ( centerPoint.x > centerPoint.y) ? centerPoint.y : centerPoint.x;
        Log.i(TAG, "onMeasure: " + centerPoint.toString());
        clipPath.reset();
        clipPath.addRect(new RectF(0,0,widthSize,heightSize),Path.Direction.CCW);
        clipPath.addCircle(centerPoint.x, centerPoint.y, radius, Path.Direction.CCW);
        setMeasuredDimension(widthSize, heightSize);
    }


    /**
     * 绘制
     *
     * @param canvas 画布
     */
    @Override
    public void draw(Canvas canvas) {
        //裁剪画布，并设置其填充方式
//        if (Build.VERSION.SDK_INT >= 26) {
////            canvas.clipPath(clipPath);
////        } else {
////            canvas.clipPath(clipPath, Region.Op.REPLACE);
////        }
        super.draw(canvas);
    }

    /**
     * 计算最大公约数
     *
     * @param a
     * @param b
     * @return 最大公约数
     */
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}

