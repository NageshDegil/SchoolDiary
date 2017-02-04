package com.pawan.schooldiary.home.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.pawan.schooldiary.R;
import com.pawan.schooldiary.home.teacher.fragment.home.TeacherHomeFragment_;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;

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



    public static void generateToast(Context context, String message, boolean flag) {
        if(flag)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getLoggedInEmail(Context context) {
        if(Utils.readPreferenceData(context, Constants.LOGIN_TYPE, "").equals("T"))
            return Utils.readPreferenceData(context, Constants.TEACHER_EMAIL_KEY, "");
        else
            return Utils.readPreferenceData(context, Constants.PARENTS_EMAIL_KEY, "");
    }

    public static void networkError(Context context, String title, String msg, Throwable e) {
        if(e != null) {
            if(e instanceof HttpException) {
                if (((HttpException) e).code() == 500) {
                }
            } else if(e instanceof ConnectException) {
                showAlert(context, title, msg);
            }
        }
    }

    private static void showAlert(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearGlideCache(final Context context) {
        Glide.get(context).clearMemory();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Glide.get(context).clearDiskCache();
            }
        };

        new Thread(runnable).start();
    }
}
