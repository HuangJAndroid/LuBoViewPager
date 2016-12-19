package com.huangj.lunboViewPager;

import android.app.Application;

import org.xutils.x;


/**
 * Created by Administrator on 2016/12/19 17:16
 * 功能描述:
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
