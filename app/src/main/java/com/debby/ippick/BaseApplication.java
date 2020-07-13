package com.debby.ippick;

import android.app.Application;

import java.util.HashMap;

import me.weishu.exposed.ExposedBridge;

/**
 * Create by wakfu on 2020/7/13
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ExposedBridge.initOnce(this, getApplicationInfo(), getClassLoader());
    }



}
