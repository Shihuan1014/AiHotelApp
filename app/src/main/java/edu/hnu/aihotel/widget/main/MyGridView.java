package edu.hnu.aihotel.widget.main;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import edu.hnu.aihotel.R;

public class MyGridView extends GridView { 

    public MyGridView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 

    public MyGridView(Context context) { 
        super(context); 
    } 

    public MyGridView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 

    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 

        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        setSelector(R.drawable.calendar_selector);
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
}
