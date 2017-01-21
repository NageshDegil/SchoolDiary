package com.pawan.schooldiary.home.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.File;

/**
 * Created by pawan on 21/1/17.
 */

public class Utils {

    /**
     * save user preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void savePreferenceData(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * save user preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void savePreferenceData(Context context, String key, Boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * save user preferences
     *
     * @param context
     * @param key
     * @param value
     */
    public static void savePreferenceData(Context context, String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * clear all the key value pairs from the preferences
     *
     * @param context
     */
    public static void clearPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * removes the passed in key from the preferences
     *
     * @param context
     * @param key
     */
    public static void removePreferenceData(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().remove(key).commit();
    }

    /**
     * read user preferences
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String readPreferenceData(Context context, String key, String defaultValue) {
        if (context != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getString(key, defaultValue);
        }
        return null;
    }

    /**
     * read user preferences
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean readPreferenceData(Context context, String key, boolean defaultValue) {
        if (context != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getBoolean(key, defaultValue);
        }
        return false;
    }

    /**
     * read user preferences
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int readPreferenceData(Context context, String key, int defaultValue) {
        if (context != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getInt(key, defaultValue);
        }
        return -1;
    }

    public static void createDirectory(Context context, String path) {
        String tempPath = "";
        File dirPath;
        for (String dir : path.split("/")) {
            dirPath = new File(context.getFilesDir(), tempPath + dir);
            if (!dirPath.exists()) {
                dirPath.mkdir();
                tempPath = tempPath + dir + "/";
            } else {
                tempPath = tempPath + dir + "/";
            }
        }
    }
}
