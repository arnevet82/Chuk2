package com.chuk.chuk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class CustomProductTouchView extends View {


    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;

    private float heightScreen;
    private float widthScreen;

    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;

    public static TextView textView;
    public static StaticLayout sl;
    public static TextPaint textPaint;
    public static float angle;
    private float textPivotx;
    private float textPivoty;
    private float textPosX;
    private float textPosY;
    private float textScaleFactor = 1.f;
    private ScaleGestureDetector textScaleDetector;


    public CustomProductTouchView(Context context) {
        this(context, null, 0);
    }

    public CustomProductTouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProductTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        setLayoutParams();


    }

    public void setLayoutParams() {

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(pixels*3, pixels*2 + pixels/2);
        lp.addRule(Gravity.CENTER);
        setLayoutParams(lp);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.save();
        canvas.translate(textView.getX(), textView.getY());
        canvas.scale(textScaleFactor, textScaleFactor, textPivotx, textPivoty);
        canvas.rotate(angle, textPivotx, textPivoty);
        sl.draw(canvas);
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        textScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {


                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = ev.getPointerId(0);

                break;
            }


            case MotionEvent.ACTION_MOVE: {

                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                if (!mScaleDetector.isInProgress()) {
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;

                    float xpos = textView.getX();
                    float ypos = textView.getY();
                    textView.setX(xpos += dx);
                    textView.setY(ypos += dy);


                    invalidate();


                    mLastTouchX = x;
                    mLastTouchY = y;

                    break;
                }
            }


            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {

                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }

                break;
            }



        }
        return true;
    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            textScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            textScaleFactor = Math.max(0.1f, Math.min(textScaleFactor, 5.0f));




            invalidate();
            return true;
        }
    }






    public void init(){


        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        mPosX = widthScreen/3;
        mPosY = heightScreen/5f;

        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        textScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        textPosX = widthScreen/8;
        textPosY = heightScreen/8;
        textView = new TextView(getContext());
        textView.setX(textPosX);
        textView.setY(textPosY);
        textView.setWidth(CustomProductActivity.editText.getWidth());

        if(CustomProductActivity.item.equals("petTag")){
            textPosY = CustomProductActivity.editText.getY();
        }else if(CustomProductActivity.item.equals("gyring")){
            textView.setAllCaps(true);
        }

        Typeface tf =Typeface.createFromAsset(getContext().getAssets(),"Montserrat-ExtraBold.ttf");
        textPaint = new TextPaint();
        textPaint.setTypeface(tf);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        textPaint.setShadowLayer(7, 1, 1, Color.parseColor("#64000000"));
        sl=new StaticLayout("", textPaint,(int)widthScreen/2,
                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);

        textPivotx = sl.getWidth()/2;
        textPivoty = sl.getHeight()/2;


    }
}