package com.inaara.whatsappscheduler_app.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inaara.whatsappscheduler_app.data.model.Message;

/*
* Defines the Room database for the app, returning a singleton instance of the Room database.
* Ensures only one instance is created throughout the app's lifecycle.
* */
@Database(entities = Message.class, version = 1)
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
