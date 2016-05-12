package com.brandedme.hossamhassan.instamonitor;

import android.util.Log;

/**
 * @author Hossam
 * InstaLog final class for printing log messages if enabled in user options in InstaMonitor
 */
public final  class InstaLog {
    static String TAG=InstaMonitor.getTAG();
    private InstaLog()
    {}

    /**
     * to print log debug messages to logcat
     * @param msg for msg
     */
    public static void d(String msg)
    {
        //if not enabled return
        if(!InstaMonitor.isEnabledDebugMode())
            return;
        //if is enabled do
        Log.d(TAG,msg);
    }

}
