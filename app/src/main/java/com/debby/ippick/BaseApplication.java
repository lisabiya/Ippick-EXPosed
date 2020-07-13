package com.debby.ippick;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    void getList() {
//        PackageManager pm = getPackageManager();
//        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);  // 获取所以已安装的包
//        ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
//        for (PackageInfo packageInfo : installedPackages) {
//            ExposedBridge.loadModule()
//        }
//        Context remoteContext = null;
//        try {
//            remoteContext = createPackageContext("com.debby.xposetest", CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (null != remoteContext) {
//            remoteContext.getFileStreamPath()
//            remoteContext.getResources().getIdentifier("ic_launcher", "mipmap", "com.yf.test");
//        }
//        ExposedBridge.loadModule()

    }
}
