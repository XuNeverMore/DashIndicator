package com.nevermore.dashindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Create by XuChuanting
 * on 2018/9/14-16:40
 */
public class DashIndicator extends View {

    private Paint mPaint;
    private int mIndicatorHeight;
    private float mLongLength;
    private float mShortLength;
    private float mIndicatorSpace;
    private ViewPager mViewPager;
    private int mPageCount;
    private int mCurrentPosition;
    private RectF mRectF;
    private boolean roundCap = true;


    public DashIndicator(Context context) {
        this(context, null);
    }

    public DashIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashIndicator);
        mIndicatorHeight = (int) typedArray.getDimension(R.styleable.DashIndicator_dash_indicator_height, 15);
        mLongLength = typedArray.getDimension(R.styleable.DashIndicator_dash_indicator_long_length, 105);
        mShortLength = typedArray.getDimension(R.styleable.DashIndicator_dash_indicator_short_length, 36);
        mIndicatorSpace = typedArray.getDimension(R.styleable.DashIndicator_dash_indicator_space, 15);
        int IndicatorColor = typedArray.getColor(R.styleable.DashIndicator_dish_indicator_color, 0xffffffff);
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(IndicatorColor);
        mRectF = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width, height;
        width = (int) (mLongLength + (mShortLength + mIndicatorSpace) * (mPageCount - 1));
        height = mIndicatorHeight;
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawIndicator(canvas);

    }

    private void drawIndicator(Canvas canvas) {

        float left = 0;
        float top = 0;
        float right;
        float bottom = mIndicatorHeight;
        for (int i = 0; i < mPageCount; i++) {
            boolean isLong = mCurrentPosition == i;
            right = left + (isLong ? mLongLength : mShortLength);
            mRectF.set(left, top, right, bottom);
            if (roundCap) {
                canvas.drawRoundRect(mRectF, mIndicatorHeight / 2, mIndicatorHeight / 2, mPaint);
            } else {
                canvas.drawRect(mRectF, mPaint);
            }
            left = right + mIndicatorSpace;
        }

    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            mCurrentPosition = position;
            invalidate();
        }
    };

    public void setUpWithViewPager(@NonNull ViewPager viewPager) {
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
        }
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            mPageCount = adapter.getCount();
            mCurrentPosition = mViewPager.getCurrentItem();
            invalidate();
        }
    }
}
