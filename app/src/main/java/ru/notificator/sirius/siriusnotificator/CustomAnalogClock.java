package ru.notificator.sirius.siriusnotificator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class CustomAnalogClock extends View {

    public CustomAnalogClock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mHeight, mWidth = 0;
    private String[] mClockHours = {"α", "β", "γ", "δ", "η", "λ", "π", "ζ", "ξ", "ψ", "ω", "τ"};

    private int mPadding = 0;
    private int mNumeralSpacing = 0;

    private int mHandTruncation, mHourHandTruncation = 0;

    private int mRadius = 0;
    private Paint mPaint;
    private Rect mRect = new Rect();
    private boolean isInit;  // it will be true once the clock will be initialized.


    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInit) {
            mPaint = new Paint();
            mHeight = getHeight();
            mWidth = getWidth();
            mPadding = mNumeralSpacing + 50;
            int minAttr = Math.min(mHeight, mWidth);
            mRadius = minAttr / 2 - mPadding;

            mHandTruncation = minAttr / 20;
            mHourHandTruncation = minAttr / 17;

            isInit = true;  // set true once initialized
        }

        canvas.drawColor(Color.WHITE);
        mPaint.reset();
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius + mPadding - 10, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 12, mPaint);  // the 03 clock hands will be rotated from this center point.
        int fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 28, getResources().getDisplayMetrics());
        mPaint.setTextSize(fontSize);  // set font size (optional)
        int ind = -2;
        for (String hour : mClockHours) {
            String tmp = String.valueOf(hour);
            mPaint.getTextBounds(tmp, 0, tmp.length(), mRect);  // for circle-wise bounding

            // find the circle-wise (x, y) position as mathematical rule
            double angle = Math.PI / 6 * (ind++);
            int x = (int) (mWidth / 2 + Math.cos(angle) * mRadius - mRect.width() / 2);
            int y = (int) (mHeight / 2 + Math.sin(angle) * mRadius + mRect.height() / 2);
            canvas.drawText(String.valueOf(hour), x, y, mPaint);  // you can draw dots to denote hours as alternative
        }
        Calendar calendar = Calendar.getInstance();
        ;
        float hour = calendar.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;

        drawHandLine(canvas, (hour + calendar.get(Calendar.MINUTE) / 60) * 5f, true, false, false); // draw hours
        drawHandLine(canvas, calendar.get(Calendar.MINUTE), false, true, false); // draw minutes
        drawHandLine(canvas, calendar.get(Calendar.SECOND), false, false, true); // draw seconds

        /** invalidate the appearance for next representation of time  */
        postInvalidateDelayed(500);
        invalidate();
    }

    private void drawHandLine(Canvas canvas, double moment, boolean isHour, boolean isMinute, boolean isSecond) {
        double angle = Math.PI * moment / 30 - Math.PI / 2;
        int handRadius = isHour ? mRadius - mHandTruncation - mHourHandTruncation : mRadius - mHandTruncation;
        if (isHour) mPaint.setColor(Color.rgb(150, 0, 255));
        if (isMinute) mPaint.setColor(Color.rgb(255, 100, 0));
        if (isSecond) mPaint.setColor(Color.rgb(0, 0, 255));

        canvas.drawLine(mWidth / 2, mHeight / 2, (float) (mWidth / 2 + Math.cos(angle) * handRadius), (float) (mHeight / 2 + Math.sin(angle) * handRadius), mPaint);
    }
}