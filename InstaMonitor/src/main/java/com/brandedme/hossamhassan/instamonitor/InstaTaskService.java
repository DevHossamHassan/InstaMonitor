package com.brandedme.hossamhassan.instamonitor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by HossamHassan on 5/11/2016.
 */
public class InstaTaskService extends Service {
    String TAG=InstaMonitor.TAG;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Destroyed");
    }

    /**
     * save session end time
     * @param rootIntent
     */
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "END");
        Long startTime=Prefs.getLongPreference(this,Prefs.APP_SESSION_STARTED,InstaMonitor.getInstance().getStartTime());
        Long endTime= System.currentTimeMillis();
        Long pastTime=Prefs.getLongPreference(this,Prefs.APP_SESSION,0);
        pastTime+=(endTime-startTime);
        Prefs.setLongPreference(this,Prefs.APP_SESSION,pastTime);
        Log.i(TAG, "onTaskRemoved: "+pastTime);
        stopSelf();
    }
}