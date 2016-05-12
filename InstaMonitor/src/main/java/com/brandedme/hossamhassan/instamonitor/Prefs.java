package com.brandedme.hossamhassan.instamonitor;

/**
 * helpful getter and setter methods for reading/writing to SharedPreferences
 * Created by Hossam on 5/4/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * helpful getter and setter methods for reading/writing to SharedPreferences
 */
final public class Prefs {
    final public static String APP_SESSION = "APP_SESSION";
    final public static String START = "START";
    final public static String END = "END";
    final public static String SESSION = "SESSION";
    public static String APP_SESSION_STARTED = "APP_SESSION_STARTED";
    public static String APP_SESSION_ENDED = "APP_SESSION_ENDED";
    final public static String FRAGMENT_NAMES = "FRAGMENT_NAMES";

    private Prefs() {
    }

    /**
     * Helper method to retrieve a String value from {@link SharedPreferences}.
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @return The value from shared preferences, or null if the value could not be read.
     */
    public static String getStringPreference(Context context, String key) {
        String value = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getString(key, null);
        }
        return value;
    }

    /**
     * Helper method to write a String value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @param value   value of this unique key
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * @param context    a {@link Context} object.
     * @param fragmentId value of this unique key
     * @return true if done and false if no preferences
     */
    public static boolean addFragmentIdToShared(Context context, String fragmentId) {
        if (!isExistInShared(context, fragmentId)) {
            setStringPreference(context,FRAGMENT_NAMES,getStringPreference(context, FRAGMENT_NAMES) + "," + fragmentId);
            return true;
        }
        return false;
    }

    /**
     * @param context    for sharedPreferences
     * @param fragmentId for the value to search for
     * @return true if exist false if does not exist
     */
    public static boolean isExistInShared(Context context, String fragmentId) {
        String fragmentsIds = getStringPreference(context, FRAGMENT_NAMES);
        if (fragmentsIds == null || !fragmentsIds.contains(fragmentId))
            return false;
        return true;
    }

    /**
     * get List of Fragment Names from shared
     *
     * @param context a {@link Context} object.
     * @return
     */
    public static List<String> getFragmentsIdFromShared(Context context) {
        String saved = getStringPreference(context, FRAGMENT_NAMES);
        if (saved == null)
            return null;
        saved = (saved.startsWith(",")) ? saved.substring(1) : saved;
        saved =(saved.startsWith("null,"))?saved.substring(5):saved;
        return Arrays.asList(saved.split(","));
    }

    /**
     * Helper method to retrieve a float value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key          should be unique for this value
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static float getFloatPreference(Context context, String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a float value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @param value   value of this unique key
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a long value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key          should be unique for this value
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a long value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @param value   value of this unique key
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve an integer value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key          should be unique for this value
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write an integer value to {@link SharedPreferences}.
     *
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @param value   value of this unique key
     * @return true if the new value was successfully written to persistent storage.
     */
    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    /**
     * Helper method to retrieve a boolean value from {@link SharedPreferences}.
     *
     * @param context      a {@link Context} object.
     * @param key          should be unique for this value
     * @param defaultValue A default to return if the value could not be read.
     * @return The value from shared preferences, or the provided default.
     */
    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }

    /**
     * Helper method to write a boolean value to {@link SharedPreferences}.
     * @param context a {@link Context} object.
     * @param key     should be unique for this value
     * @param value   value of this unique key
     * @return true if the new value was successfully written to persistent storage.
     */

    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }

}