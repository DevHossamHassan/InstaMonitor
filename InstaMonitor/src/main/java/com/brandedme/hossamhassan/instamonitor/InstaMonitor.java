package com.brandedme.hossamhassan.instamonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by HossamHassan on 5/10/2016.
 * InstaMonitor  is a singleton using lazy instantiation technique
 */
public class InstaMonitor {
    private String TAG = "INSTA_MONITOR";
    private Application application;
    private long startTime, endTime;
    ActivityInfo[] list;
    private static InstaMonitor instaMonitor = null;

    private InstaMonitor() {
    }

    public static InstaMonitor getInstance() {
        return (instaMonitor == null) ? new InstaMonitor() : instaMonitor;
    }

    /**
     * init for initialize InstaMonitor
     *
     * @param application
     */
    public void init(Application application) {
        this.application = application;
        setStartTime();
        registerCallbacks();
        startWatcherService();
        getActivities();
    }

    void startWatcherService() {
        Log.i(TAG, "startWatcherService: ");
        application.startService(new Intent(application, OnClearFromRecentService.class));
    }

    /**
     * init application start time
     */
    private void setStartTime() {
        startTime = System.currentTimeMillis();
        Log.i(TAG, "setStartTime: " + startTime);
    }


    /**
     * register activity lifecycle callbacks
     */
    private void registerCallbacks() {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivityCreated: " + activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i(TAG, "onActivityStarted: " + activity.getClass().getSimpleName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
                String activityName = activity.getClass().getSimpleName();
                Log.i(TAG, "onActivityResumed: " + activityName);
                Prefs.setLongPreference(activity, activityName + Prefs.START, System.currentTimeMillis());

            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, "onActivityPaused: " + activity.getClass().getSimpleName());
                calculateActivityTime(activity);
            }

            void calculateActivityTime(Activity activity) {
                String activityName = activity.getClass().getSimpleName();
                Long currentTime = System.currentTimeMillis();
                Prefs.setLongPreference(activity, activityName + Prefs.END, currentTime);
                Long session = currentTime - Prefs.getLongPreference(activity, activityName + Prefs.START, 0);
                Prefs.setLongPreference(activity, activityName + Prefs.SESSION, session);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i(TAG, "onActivityStopped: " + activity.getClass().getSimpleName());


            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivitySaveInstanceState: " + activity.getClass().getSimpleName());

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i(TAG, "onActivityDestroyed: " + activity.getClass().getSimpleName());

            }
        });
    }

    void getActivities() {
        //ActivityInfo[] list;
        try {
            list = application.getPackageManager().getPackageInfo(application.getPackageName(), PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
    }


    private String getDate(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        return DateFormat.format("dd-MM-yyyy HH:mm:ss", calendar).toString();
    }

    public  HashMap<String, String> getMonitorData() {
        HashMap<String, String> dataMap;
        if (list != null) {
            dataMap=new HashMap<>();
            //getDate(Prefs.getLongPreference(application,APP_SESSION))
            for (ActivityInfo activity:list) {
                dataMap.put(activity.getClass().getSimpleName(),
                        getDate(Prefs.getLongPreference(application, activity.getClass().getSimpleName() + Prefs.SESSION,0)));
            }
            return dataMap;

        }
        return null;
    }

    public long getStartTime() {
        return startTime;
    }

}
