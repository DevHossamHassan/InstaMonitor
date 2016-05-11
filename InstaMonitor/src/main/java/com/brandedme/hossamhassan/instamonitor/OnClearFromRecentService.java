package com.brandedme.hossamhassan.instamonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by HossamHassan on 5/11/2016.
 */
public class OnClearFromRecentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("INSTA_MONITOR", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("INSTA_MONITOR", "Service Destroyed");
    }

    public void onTaskRemoved(Intent rootIntent) {
        Log.i("INSTA_MONITOR", "END");
        Long startTime=InstaMonitor.getInstance().getStartTime();
        Long endTime= System.currentTimeMillis();
        Long pastTime=Prefs.getLongPreference(this,"APP_SESSION_TIME",0);
        pastTime+=(endTime-startTime);
        Prefs.setLongPreference(this,"APP_SESSION_TIME",pastTime);
        Log.i("INSTA_MONITOR", "onTaskRemoved: "+pastTime);
        //Code here
        stopSelf();
    }
}