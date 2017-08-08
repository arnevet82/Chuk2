package com.chuk.chuk;

import android.app.Application;
import android.content.Context;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}