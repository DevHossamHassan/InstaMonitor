package com.brandedme.hossamhassan.instamonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by HossamHassan on 5/10/2016.
 * InstaMonitor  is a singleton using lazy instantiation technique
 */
public class InstaMonitor {
    private static String TAG = "INSTA_MONITOR";
    private Application application;
    private long startTime, endTime;
    private List<ActivityInfo> list;
    private ArrayList<Activity> activitiesList;
    private static Boolean enableDebugMode=false;

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        InstaMonitor.TAG = TAG;
    }

    public static Boolean isEnabledDebugMode() {
        return enableDebugMode;
    }

    public static void setEnableDebugMode(Boolean enableDebugMode) {
        InstaMonitor.enableDebugMode = enableDebugMode;
    }

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
     * @param application assign application to user application
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
        InstaLog.d("startWatcherService: ");
        application.startService(new Intent(application, InstaTaskService.class));
    }

    /**
     * init application start time
     */
    private void setStartTime() {
        startTime = System.currentTimeMillis();
        Prefs.setLongPreference(application, Prefs.APP_SESSION_STARTED, startTime);
        InstaLog.d("setStartTime: " + startTime);
    }


    /**
     * register activity lifecycle callbacks
     */
    private void registerCallbacks() {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                InstaLog.d("onActivityCreated: " + activity.getClass().getName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                InstaLog.d("onActivityStarted: " + activity.getClass().getName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
                String activityName = activity.getClass().getName();
                InstaLog.d("onActivityResumed: " + activityName);
                Prefs.setLongPreference(activity, activityName + Prefs.START, System.currentTimeMillis());

            }

            @Override
            public void onActivityPaused(Activity activity) {
                InstaLog.d("onActivityPaused: " + activity.getClass().getName());
                calculateSession(activity,activity.getClass().getName());
            }



            @Override
            public void onActivityStopped(Activity activity) {
                InstaLog.d("onActivityStopped: " + activity.getClass().getName());


            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                InstaLog.d("onActivitySaveInstanceState: " + activity.getClass().getName());

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                InstaLog.d("onActivityDestroyed: " + activity.getClass().getName());

            }

        });
    }
    /**
     * save session duration for each activity
     * @param context set timestamp for each activity or fragment  at onPaused event
     * @param className could be full name of Activity or fragment
     */
    public  void calculateSession(Context context,String className) {

        Long currentTime = System.currentTimeMillis();

        Prefs.setLongPreference(context, className + Prefs.END, currentTime);

        Long session = (currentTime - Prefs.getLongPreference(context, className + Prefs.START, 0));

        Long lastSession = Prefs.getLongPreference(context, className + Prefs.SESSION, 0);

        Prefs.setLongPreference(context, className + Prefs.SESSION, session + lastSession);
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
            InstaLog.d("getActivities Name not found exception: " + e1.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            InstaLog.d("getActivities global exception: " + e.getMessage());
        }

    }

    /**
     * add activities for ignore monitoring
     * @param activities get excluded activities from tracking
     * @return InstaMonitor
     */
    InstaMonitor ignoreActivity(Activity... activities) {
        activitiesList = new ArrayList<>();
        Collections.addAll(activitiesList, activities);
        return instaMonitor;
    }

    /**
     * convert duration from
     *
     * @param milliSeconds timestamp in milliSeconds could be System.currentTimeMillis()
     * @return Duration as a String
     */
    public String convertDuration(long milliSeconds) {

        int dys = (int) TimeUnit.MILLISECONDS.toDays(milliSeconds);
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(milliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60;
        return String.format(Locale.US," %02dd : %02dh : %02dm : %02ds", dys, hrs, min, sec);
    }

    /**
     * @return ArrayList of Sessions contains  name and duration
     */
    public ArrayList<Session> getMonitorData() {
        ArrayList<Session>sessionsList;
        List<String>fragmentsIds;
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
           fragmentsIds= Prefs.getFragmentsIdFromShared(application,Prefs.FRAGMENT_NAMES);
            if (fragmentsIds!=null)
            {
                for (String fragmentId : fragmentsIds) {
                    Session session = new Session();
                    session.setName(fragmentId);
                    session.setTime(convertDuration(Prefs.getLongPreference(application, fragmentId+ Prefs.SESSION, 0)));
                    sessionsList.add(session);

                }
            }
            return sessionsList;
        }
        return null;
    }

    /**
     * @return startTime as long of milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

}
