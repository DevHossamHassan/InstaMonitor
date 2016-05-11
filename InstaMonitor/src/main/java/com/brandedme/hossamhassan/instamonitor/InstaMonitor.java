package com.brandedme.hossamhassan.instamonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by HossamHassan on 5/10/2016.
 * InstaMonitor  is a singleton using lazy instantiation technique
 */
public class InstaMonitor {
    public static String TAG = "INSTA_MONITOR";
    private Application application;
    private long startTime, endTime;
    List<ActivityInfo> list;
    ArrayList<Session> sessionsList;
    ArrayList<Activity> activitiesList;
    Boolean enableDebugMode=false;
    private static InstaMonitor instaMonitor = new InstaMonitor();

    /**
     * private constructor  to prevent user from instantiate objects
     */
    private InstaMonitor() {
    }

    /**
     * singleton
     *
     * @return InstaMonitor object
     */
    public static InstaMonitor getInstance() {
        return instaMonitor;
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
        getActivitiesList();
    }

    /**
     * start service for notify if application task is killed
     */
    void startWatcherService() {
        Log.i(TAG, "startWatcherService: ");
        application.startService(new Intent(application, InstaTaskService.class));
    }

    /**
     * init application start time
     */
    private void setStartTime() {
        startTime = System.currentTimeMillis();
        Prefs.setLongPreference(application, Prefs.APP_SESSION_STARTED, startTime);
        Log.i(TAG, "setStartTime: " + startTime);
    }


    /**
     * register activity lifecycle callbacks
     */
    private void registerCallbacks() {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivityCreated: " + activity.getClass().getName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i(TAG, "onActivityStarted: " + activity.getClass().getName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
                String activityName = activity.getClass().getName();
                Log.i(TAG, "onActivityResumed: " + activityName);
                Prefs.setLongPreference(activity, activityName + Prefs.START, System.currentTimeMillis());

            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i(TAG, "onActivityPaused: " + activity.getClass().getName());
                calculateActivityTime(activity);
            }

            /**
             * save session duration for each activity
             * @param activity
             */
            void calculateActivityTime(Activity activity) {

                String activityName = activity.getClass().getName();

                Long currentTime = System.currentTimeMillis();

                Prefs.setLongPreference(activity, activityName + Prefs.END, currentTime);

                Long session = (currentTime - Prefs.getLongPreference(activity, activityName + Prefs.START, 0));

                Long lastSession = Prefs.getLongPreference(activity, activityName + Prefs.SESSION, 0);

                Prefs.setLongPreference(activity, activityName + Prefs.SESSION, session + lastSession);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i(TAG, "onActivityStopped: " + activity.getClass().getName());


            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.i(TAG, "onActivitySaveInstanceState: " + activity.getClass().getName());

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i(TAG, "onActivityDestroyed: " + activity.getClass().getName());

            }
        });
    }

    /**
     * get all application activities
     */
    void getActivitiesList() {
        list = new ArrayList<>();
        try {
            list = Arrays.asList(application.getPackageManager().getPackageInfo(application.getPackageName(),
                    PackageManager.GET_ACTIVITIES).activities);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
            Log.i(TAG, "getActivities special: " + e1.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "getActivities: general" + e.getMessage());
        }

    }

    /**
     * add activities for ignore monitoring
     *
     * @param activities
     * @return InstaMonitor
     */
    InstaMonitor ignoreActivity(Activity... activities) {
        activitiesList = new ArrayList<>();
        for (Activity activity : activities) {
            activitiesList.add(activity);
        }
        return instaMonitor;
    }

    /**
     * convert duration from
     *
     * @param milliSeconds
     * @return Duration as a String
     */
    public String convertDuration(long milliSeconds) {

        int dys = (int) TimeUnit.MILLISECONDS.toDays(milliSeconds);
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(milliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60;
        return String.format(" %02dd : %02dh : %02dm : %02ds", dys, hrs, min, sec);
    }

    /**
     * @return ArrayList of Sessions contains  name and duration
     */
    public ArrayList<Session> getMonitorData() {

        if (list != null) {
            sessionsList = new ArrayList<>();
            Session appSession = new Session();
            appSession.setName(application.getClass().getName());
            appSession.setTime(convertDuration(Prefs.getLongPreference(application, Prefs.APP_SESSION, 0)));
            sessionsList.add(appSession);
            for (ActivityInfo activity : list) {
                Session session = new Session();
                session.setName(activity.name);
                session.setTime(convertDuration(Prefs.getLongPreference(application, activity.name + Prefs.SESSION, 0)));
                sessionsList.add(session);
            }
            return sessionsList;
        }
        return null;
        /*
        String className = xxx.getClass();
        int pos = className.lastIndexOf ('.') + 1;
        String onlyClass = className.substring(pos);
         */
    }

    /**
     * @return startTime as long of milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

}
