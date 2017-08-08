package com.chuk.chuk;

import android.graphics.drawable.Drawable;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class Shape {

    private Drawable drawable;
    private float posX, posY;
    private float scaleFactor = 1.f;
    private float angle;
    private String tag;


    public Shape(Drawable drawable, float posX, float posY){
        this.drawable = drawable;
        this.posX = posX;
        this.posY = posY;
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}