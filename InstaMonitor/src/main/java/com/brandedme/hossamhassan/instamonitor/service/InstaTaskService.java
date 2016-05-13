package com.brandedme.hossamhassan.instamonitor.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.brandedme.hossamhassan.instamonitor.InstaMonitor;
import com.brandedme.hossamhassan.instamonitor.util.InstaLog;
import com.brandedme.hossamhassan.instamonitor.util.Prefs;

/**
 * InstaTaskService for track the application session
 * Created by HossamHassan on 5/11/2016.
 */
public class InstaTaskService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        InstaLog.d("Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InstaLog.d("Service Destroyed");
    }

    /**
     * save session end time
     * @param rootIntent
     */
    public void onTaskRemoved(Intent rootIntent) {
        InstaLog.d("Application END");
        Long startTime= Prefs.getLongPreference(this,Prefs.APP_SESSION_STARTED, InstaMonitor.getInstance().getStartTime());
        Long endTime= System.currentTimeMillis();
        Long pastTime=Prefs.getLongPreference(this,Prefs.APP_SESSION,0);
        pastTime+=(endTime-startTime);
        Prefs.setLongPreference(this,Prefs.APP_SESSION,pastTime);
        InstaLog.d("onTaskRemoved: "+pastTime);
        stopSelf();
    }
}