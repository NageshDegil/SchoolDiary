package com.pawan.schooldiary.home.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by pawan on 21/1/17.
 */

public class FileDBUtils<T> {

    public static final String RECENT_CHATS = "recent_chats.json";
    public static final String GROUPS = "groups.json";
    public static final String CONTACTS = "contacts.json";
    public static final String USER_DIR = "/db/User";

    private Context context;
    private String dbFileName;
    private File file;
    private String filePath;
    private Class<T> clazz;
    private String directory;

    public FileDBUtils(Context context, String dbFileName, Class<T> clazz, String directory) {
        this.context = context;
        this.dbFileName = dbFileName;
        this.clazz = clazz;
        this.filePath = context.getFilesDir()+ directory;
        this.file = new File(context.getFilesDir()+ directory, dbFileName);
        this.directory = directory;
    }

    public void saveObject(T object) {
        Utils.createDirectory(context, directory);
        String writeData = new Gson().toJson(object);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(writeData);
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }

    public T readObject() {
        if (file.exists()) {
            try {
                //check whether file exists
                FileInputStream is = new FileInputStream(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return new Gson().fromJson(new String(buffer), clazz);
            } catch (IOException e) {
                //Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
                return null;
            }
        }
        return null;
    }
}
