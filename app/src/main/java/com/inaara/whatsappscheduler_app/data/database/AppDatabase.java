package com.inaara.whatsappscheduler_app.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MessageDao.class, version = 0)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MessageDao messageDao();
    private static AppDatabase dbInstance;

    public static AppDatabase getInstance (Context context) {
        if (dbInstance == null) {
            context = context.getApplicationContext();
            dbInstance = Room.databaseBuilder(context, AppDatabase.class, "Messages.db")
                    .build();
        }
        return dbInstance;
    }

}
