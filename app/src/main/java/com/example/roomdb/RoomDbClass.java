package com.example.roomdb;

/*import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;*/
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

//Add database entities
@Database(entities = {MyData.class},version = 1,exportSchema = false)
public abstract class RoomDbClass extends RoomDatabase {

    //Create database instance
    private static RoomDbClass roomDB;
    private static String DATABASE_NAME="database";
    public synchronized static RoomDbClass getInstance(Context context)
    {
        if (roomDB == null)
        {
            roomDB= Room.databaseBuilder(context.getApplicationContext(), RoomDbClass.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return roomDB;
    }

    public abstract MainDao mainDao();


    @NonNull

    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
