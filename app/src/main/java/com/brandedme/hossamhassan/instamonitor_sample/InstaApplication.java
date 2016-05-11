package com.brandedme.hossamhassan.instamonitor_sample;

import android.app.Application;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;

/**
 * Created by Hossam on 5/11/2016.
 */
public class InstaApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        InstaMonitor instaMonitor= InstaMonitor.getInstance();
        instaMonitor.init(this);
    }
}
