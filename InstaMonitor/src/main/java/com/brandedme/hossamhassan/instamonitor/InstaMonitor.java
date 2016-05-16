package com.brandedme.hossamhassan.instamonitor;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.brandedme.hossamhassan.instamonitor.model.Session;
import com.brandedme.hossamhassan.instamonitor.service.InstaTaskService;
import com.brandedme.hossamhassan.instamonitor.util.InstaLog;
import com.brandedme.hossamhassan.instamonitor.util.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * InstaMonitor  is a singleton using lazy instantiation technique
 * Created by HossamHassan on 5/10/2016.
 */
public class InstaMonitor {
    private static String TAG = "INSTA_MONITOR";
    private Application application;
    private long startTime, endTime;
    private ArrayList<Session> sessionsList;
    private List<ActivityInfo> list;
    private static ArrayList<Class> excludedActivitiesList;
    private static Boolean enableDebugMode = false;

    /**
     * Gets tag.
     *
     * @return the tag
     */
    public static String getTAG() {
        return TAG;
    }

    /**
     * Sets tag.
     *
     * @param TAG the tag
     */
    public static void setTAG(String TAG) {
        InstaMonitor.TAG = TAG;
    }

    /**
     * Is enabled debug mode boolean.
     *
     * @return the boolean
     */
    public static Boolean isEnabledDebugMode() {
        return enableDebugMode;
    }

    /**
     * Sets enable debug mode.
     *
     * @param enableDebugMode the enable debug mode
     */
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
     *
     * @param application assign application to user application
     */
    public void init(Application application) {
        this.application = application;
        setStartTime();
        registerCallbacks();
        startInstaService();
        getActivitiesList();
        excludedActivitiesList=new ArrayList<>();
    }

    /**
     * start service for notify if application task is killed
     */
    private void startInstaService() {
        InstaLog.d("startService: ");
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
                if (!excludedActivitiesList.contains(activity.getClass()))
                    InstaLog.d("onActivityCreated: " + activity.getClass().getName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (!excludedActivitiesList.contains(activity.getClass()))
                    InstaLog.d("onActivityStarted: " + activity.getClass().getName());

            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (!excludedActivitiesList.contains(activity.getClass())) {
                    String activityName = activity.getClass().getName();
                    InstaLog.d("onActivityResumed: " + activityName);
                    Prefs.setLongPreference(activity, activityName + Prefs.START, System.currentTimeMillis());
                }

            }

            @Override
            public void onActivityPaused(Activity activity) {
                if (!excludedActivitiesList.contains(activity.getClass())) {
                    InstaLog.d("onActivityPaused: " + activity.getClass().getName());
                    calculateSession(activity, activity.getClass().getName());
                }
            }


            @Override
            public void onActivityStopped(Activity activity) {
                if (!excludedActivitiesList.contains(activity.getClass())) {
                    InstaLog.d("onActivityStopped: " + activity.getClass().getName());
                }


            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                if (!excludedActivitiesList.contains(activity.getClass())) {
                    InstaLog.d("onActivitySaveInstanceState: " + activity.getClass().getName());
                }

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (!excludedActivitiesList.contains(activity.getClass())) {
                    InstaLog.d("onActivityDestroyed: " + activity.getClass().getName());
                }

            }

        });
    }

    /**
     * save session duration for each activity
     *
     * @param context   set timestamp for each activity or fragment  at onPaused event
     * @param className could be full name of Activity or fragment
     */
    public void calculateSession(Context context, String className) {

        Long currentTime = System.currentTimeMillis();

        Prefs.setLongPreference(context, className + Prefs.END, currentTime);

        Long lastStartTimeStamp = Prefs.getLongPreference(context, className + Prefs.START, 0);
        if (lastStartTimeStamp != 0) {
            Long session = (currentTime - lastStartTimeStamp);
            Long lastSession = Prefs.getLongPreference(context, className + Prefs.SESSION, 0);
            Prefs.setLongPreference(context, className + Prefs.SESSION, session + lastSession);
        }
    }


    /**
     * get all application activities
     */
    private void getActivitiesList() {
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
     *
     * @param activities Class get excluded activities from tracking
     */
    public static void ignoreActivity(Class... activities) {
        excludedActivitiesList = new ArrayList<>();
        Collections.addAll(excludedActivitiesList, activities);
    }

    /**
     * convert duration from
     *
     * @param milliSeconds timestamp in milliSeconds could be System.currentTimeMillis()
     * @return Duration as a String
     */
    private String convertDuration(long milliSeconds) {

        int dys = (int) TimeUnit.MILLISECONDS.toDays(milliSeconds);
        int hrs = (int) TimeUnit.MILLISECONDS.toHours(milliSeconds) % 24;
        int min = (int) TimeUnit.MILLISECONDS.toMinutes(milliSeconds) % 60;
        int sec = (int) TimeUnit.MILLISECONDS.toSeconds(milliSeconds) % 60;
        return String.format(Locale.US, " %02dd : %02dh : %02dm : %02ds", dys, hrs, min, sec);
    }

    /**
     * Gets monitor data.
     *
     * @return ArrayList of Sessions contains  name and duration
     */
    public ArrayList<Session> getMonitorData() {
        sessionsList = new ArrayList<>();
        addApplicationSessionToList();
        addActivitiesSessionsToList();
        addFragmentsSessionsToList();
        return sessionsList;
    }

    /**
     * get Application Session From Shared
     * and add it to sessions list
     * added recently calculate the live session (current)
     */
    private void addApplicationSessionToList() {
        Session appSession = new Session();
        Long pastTime = Prefs.getLongPreference(application, Prefs.APP_SESSION, 0);
        Long currentSession = System.currentTimeMillis() - getStartTime();
        appSession.setName(application.getClass().getName());
        appSession.setTime(convertDuration(currentSession + pastTime));
        sessionsList.add(appSession);
    }

    /**
     * get activities sessions and add them to sessions list
     */
    private void addActivitiesSessionsToList() {
        if (list != null) {
            for (ActivityInfo activity : list) {
                if (!excludedActivitiesList.contains(getActivityClassFromActivityInfo(activity))) {
                    Session session = new Session();
                    session.setName(activity.name);
                    session.setTime(convertDuration(Prefs.getLongPreference(application, activity.name + Prefs.SESSION, 0)));
                    sessionsList.add(session);
                }
            }
        }
    }

    /**
     * @param activityInfo to get the actual activity class object
     * @return class object for activity exists in activityInfo
     */
    private Class getActivityClassFromActivityInfo(ActivityInfo activityInfo) {
        Class activityClass = null;
        try {
            activityClass = Class.forName(activityInfo.name);
        } catch (ClassNotFoundException e) {
            InstaLog.d("Can't get Class Object from Activity Info" + e.getMessage());
        }
        return activityClass;
    }

    /**
     * get fragments sessions and add them to sessions list
     */
    private void addFragmentsSessionsToList() {
        List<String> fragmentsIds;
        fragmentsIds = Prefs.getFragmentsIdFromShared(application);
        if (fragmentsIds != null) {
            for (String fragmentId : fragmentsIds) {
                Session session = new Session();
                session.setName(fragmentId);
                session.setTime(convertDuration(Prefs.getLongPreference(application, fragmentId + Prefs.SESSION, 0)));
                sessionsList.add(session);
            }
        }
    }


    /**
     * Gets start time.
     *
     * @return startTime as long of milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * reset all stored states
     */
    public void resetSessionsState() {
        Prefs.resetAllSavedSessions(application);
        setStartTime();
    }

}
