package com.everglow.wechat60ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


/**
 * 
 *
 * 1、绘制A到Z的26个字母
 *  a、b、c
 *  2、响应触摸事件，让字母的颜色发生改变
 *  3、观察者设计模式将事件通知给Fragment
 *        重新绘制的优化
 *        Handler发送延迟消息需要注意的事项
 */

public class QuickSearchBar extends View {

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private Paint paint;
    private float cellWidth;
    private float cellHeight;
    private Rect rect;
    private int currentIndex = -1;

    public QuickSearchBar(Context context) {
        this(context,null);
    }

    public QuickSearchBar(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public QuickSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);//抗锯齿
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,getResources().getDisplayMetrics()));

        //矩形对象
        rect = new Rect();
    }

    //onMeasure  onLayout  onDraw


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //getMeasuredWidth()
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //getMeasuredHeight()
    }

    //宽度0-->真实的宽度
    //此方法在onMeasure之后被系统调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //提高精度
        cellHeight = measuredHeight*1.0f / SECTIONS.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //因为绘制文字和绘制图片的起点是不一样的，绘制文字的起点是从左下角
        for (int i = 0; i < SECTIONS.length; i++) {
            //获取某一个字符串的矩形边框
            paint.getTextBounds(SECTIONS[i],0,1,rect);
            int textWidth = rect.width();
            int textHeight = rect.height();
            float x = cellWidth/2-textWidth/2;
            float y = cellHeight/2+textHeight/2 + cellHeight*i;

            if(currentIndex == i) {
                paint.setColor(0xFF45C01A);
            } else {
                paint.setColor(Color.BLACK);
            }

            canvas.drawText(SECTIONS[i],x,y,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();//触摸点的y轴坐标
                //A  0~10    索引0
                //B  11~20   索引1
                //C 21~30  索引2
                int oldIndex = currentIndex;
                currentIndex = (int) (y/cellHeight);
                if(currentIndex > SECTIONS.length - 1) {
                    currentIndex = SECTIONS.length - 1;
                }
                if(oldIndex != currentIndex) {
                    notifyLetterChanged(SECTIONS[currentIndex]);
                    invalidate();//请求重新绘制
                }

                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;
                invalidate();
                break;
        }
        return true;
    }

    //1、找出被观察者，被观察者就是事件的发生者
    //2、定义观察者接口，观察者接口中的方法就是观察者感兴趣的事件
    //3、存储观察者对象
    //4、在事件发生的时候通知观察者
    public interface OnLetterChangedListener {
        public void onLetterChanged(String letter);
    }

    private OnLetterChangedListener listener;

    public void setOnLetterChangedListener(OnLetterChangedListener listener) {
        this.listener = listener;
    }

    private void notifyLetterChanged(String letter) {
        if(listener != null) {
            listener.onLetterChanged(letter);
        }
    }
}
