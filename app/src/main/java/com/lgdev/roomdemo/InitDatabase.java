package com.lgdev.roomdemo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by Lagger on 16-08-2019.
 */
public class InitDatabase {
    private static final String TAG = InitDatabase.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    public static int countUser(Context context){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        int count = db.userDao().countUsers();
        return count;
    }

    public static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    public static User addUser(Context context, User user) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        db.userDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("Shivam");
        user.setLastName("Pawar");
        user.setContact("8482998817");
        addUser(db, user);

        List<User> userList = db.userDao().getAll();
        Log.d(TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}



