package com.gihub.digitspagerindicator.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 *
 *带数字的ViewPager指示器
 *
 */
public class DigitsPagerIndicator extends View {
    private int mPageCount;
    private float mRadius;

    private final Paint mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintChar = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mPaintCharIndex = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mCurrentPage;
    private Context context;
    private float mPageOffset;
    private int mTextSize;

    public DigitsPagerIndicator(Context context) {
        super(context);
        init(context);
    }

    public DigitsPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DigitsPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (isInEditMode()) {
            return;
        }

        mTextSize = ScreenUtil.dp2px(context,16);

        mPaintChar.setStyle(Paint.Style.STROKE);
        mPaintChar.setColor(0xFFC8C8C8);
        mPaintChar.setTextSize(mTextSize);
        mPaintChar.setTextAlign(Paint.Align.CENTER);


        mPaintCharIndex.setStyle(Paint.Style.STROKE);
        mPaintCharIndex.setColor(0xFFFFFFFF);
        mPaintCharIndex.setTextSize(mTextSize);
        mPaintCharIndex.setTextAlign(Paint.Align.CENTER);

        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintFill.setColor(0xFFFFFFFF);

        mRadius = ScreenUtil.dp2px(context, 10);
        this.context = context;
    }

    public void setPageOffset(float pageOffset) {
        mPageOffset = pageOffset;
    }

    public void setPaintColor(int normalColor, int currentColor,int currentFillColor) {
//        mPaintPageFill.setColor(normalColor);
        mPaintChar.setColor(normalColor);
        mPaintCharIndex.setColor(currentColor);
        mPaintFill.setColor(currentFillColor);
    }

    public void setRadius(int dp) {
        mRadius = ScreenUtil.dp2px(context, dp);
        requestLayout();
    }

    public void setPageCount(int num) {
        mPageCount = num;
    }

    public void setCurrentPage(int page) {
        mCurrentPage = page;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPageCount == 0) {
            return;
        }

        int longSize = getWidth();
        int longPaddingBefore = getPaddingLeft();
        int longPaddingAfter = getPaddingRight();
        int shortPaddingBefore = getPaddingTop();

        final float threeRadius = mRadius * 3;
        final float shortOffset = shortPaddingBefore + mRadius;
        float longOffset = longPaddingBefore + mRadius;
        longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f) - ((mPageCount * threeRadius) / 2.0f) + mRadius;

        float cx = mCurrentPage * threeRadius;

        float dX = longOffset + cx + mPageOffset * threeRadius;
        canvas.drawCircle(dX, shortOffset, mRadius, mPaintFill);
//        Log.e("e", "shortOffset==" + shortOffset+"    mRadius"+mRadius);
//        canvas.drawText("" + (mCurrentPage + 1), dX, dY + 9, mPaintCharIndex);


        int position = -1;
        for (int i = 0; i < mPageCount; i++) {
            float x = longOffset + (i * threeRadius);
            if (x >= dX - mRadius && x <= dX + mRadius) {
                position = i;
                break;
            }
        }

        drawText(canvas, position, threeRadius, longOffset, shortOffset);

    }

    private void drawText(Canvas canvas, int position, final float threeRadius, final float longOffset, final float shortOffset) {
        float dX;
        Paint.FontMetricsInt fontMetrics = mPaintCharIndex.getFontMetricsInt();
        int baseline = (int) ((shortOffset*2 -fontMetrics.bottom - fontMetrics.top)/2);
        for (int i = 0; i < mPageCount; i++) {
            dX = longOffset + (i * threeRadius);
            if (i == position) {
                canvas.drawText("" + (i + 1), dX, baseline, mPaintCharIndex);
            } else {
                canvas.drawText("" + (i + 1), dX, baseline, mPaintChar);

            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    }

    private int measureLong(int measureSpec) {
        int width;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (MeasureSpec.EXACTLY == specMode) {
            width = specSize;
        } else {
            width = (int) (getPaddingLeft() + getPaddingRight() + (mPageCount * 2 * mRadius) + (mPageCount - 1) * (mRadius * 2));
            if (MeasureSpec.AT_MOST == specMode) {
                width = Math.min(width, specSize);
            }

        }

        return width;
    }

    private int measureShort(int measureSpec) {
        int height;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (MeasureSpec.EXACTLY == specMode) {
            height = specSize;
        } else {
            height = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
            if (MeasureSpec.AT_MOST == specMode) {
                height = Math.min(height, specSize);
            }
        }

        return height;
    }
}