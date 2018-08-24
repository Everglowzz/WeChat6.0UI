package com.everglow.wechat60ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.everglow.wechat60ui.R;

/**
 * Created by EverGlow on 2018/8/23 15:57
 */

public class ChangeColorIcon extends View {

    private int mColor = 0xFF45C01A;
    private Bitmap mIcon;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, getResources().getDisplayMetrics());

    private Paint mPaint;
    private Paint mTextPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextRect;

    public void setAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidate();
    }

    public ChangeColorIcon(Context context) {
        this(context, null);
    }

    public ChangeColorIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIcon);
        mColor = t.getColor(R.styleable.ChangeColorIcon_color, mColor);
        mText = t.getString(R.styleable.ChangeColorIcon_text);
        mTextSize = (int) t.getDimension(R.styleable.ChangeColorIcon_textsize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        BitmapDrawable drawable = (BitmapDrawable) t.getDrawable(R.styleable.ChangeColorIcon_icon);
        mIcon = drawable.getBitmap();
        t.recycle();
        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextRect.height();
        int iconSize = Math.min(width, height);
        int left = getMeasuredWidth() / 2 - iconSize / 2;
        int top = getMeasuredHeight() - iconSize - getPaddingBottom() - mTextRect.height();
        mIconRect = new Rect(left, top, left + iconSize, top + iconSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制默认的icon 
        canvas.drawBitmap(mIcon, null, mIconRect, null);
        int Alpha = (int) Math.ceil(255 * mAlpha);
        setupTargetBitmap(Alpha);
        setupText(canvas,Alpha);
        setupTargetText(canvas, Alpha);
        //把内存中的图片绘制到画布
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    //绘制变色文字
    private void setupTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        canvas.drawText(mText, getMeasuredWidth() / 2 - mTextRect.width() / 2, mIconRect.bottom + mTextRect.height(), mTextPaint);
    }

    //绘制文字
    private void setupText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xFF333333);
        mTextPaint.setAlpha(255 - alpha);
        canvas.drawText(mText, getMeasuredWidth() / 2 - mTextRect.width() / 2, mIconRect.bottom + mTextRect.height(), mTextPaint);
    }

    /**
     * 在内存中绘制bitmap,setAlpha 绘制纯色 设置xfermode
     */
    private void setupTargetBitmap(int Alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAlpha(Alpha);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCanvas.drawRect(mIconRect, mPaint);
        //设置 xfermode DST_IN模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIcon, null, mIconRect, mPaint);

    }
}
