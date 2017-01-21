package com.pawan.schooldiary.home.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by divya on 21/01/17.
 */

public class Utils {

    public static void generateToast(Context context, String message, boolean flag) {
        if(flag)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
