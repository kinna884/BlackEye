package com.nonvoid.blackeye.io;

import android.content.Context;

import com.nonvoid.blackeye.models.Hint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Matt on 6/21/2016.
 */
public class InternalStorage {
    public static String HINTLISTKEY = "HINTSLIST";

    private static void writeObject(Context context, String key, Object object) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object readObject(Context context, String key) {
        try {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeHintsList(Context context, ArrayList<Hint> arrayList) {
        InternalStorage.writeObject(context, HINTLISTKEY, arrayList);
    }

    public static ArrayList<Hint> readHintList(Context context) {
        ArrayList<Hint> list = (ArrayList<Hint>) InternalStorage.readObject(context, HINTLISTKEY);

        if(list == null)
            return new ArrayList<>();
        else
            return list;
    }

    public static void setUserAsAdmin(Context context){
        InternalStorage.writeObject(context, "UserIsAdmin", true);
    }

    public static boolean userIsAdmin(Context context){
        boolean isAdmin = (boolean) InternalStorage.readObject(context, "UserIsAdmin");
        if(isAdmin)
            return true;
        else
            return false;
    }
}
