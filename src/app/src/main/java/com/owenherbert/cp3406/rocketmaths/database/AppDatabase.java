package com.owenherbert.cp3406.rocketmaths.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.owenherbert.cp3406.rocketmaths.database.entity.result.Result;
import com.owenherbert.cp3406.rocketmaths.database.entity.result.ResultDao;
import com.owenherbert.cp3406.rocketmaths.database.entity.user.User;
import com.owenherbert.cp3406.rocketmaths.database.entity.user.UserDao;

@Database(entities = {User.class, Result.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "appDatabase";
    private static volatile AppDatabase appDatabase;

    public static synchronized AppDatabase getInstance(Context context){
        if (appDatabase == null) appDatabase = create(context);
        return appDatabase;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }


    public abstract UserDao userDao();
    public abstract ResultDao resultDao();
}
