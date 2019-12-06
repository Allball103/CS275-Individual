package com.example.a275del3.dummy;

import android.content.Context;
import android.provider.SyncStateContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {MatchupChart.Chart.class},version = 1, exportSchema = false)
public abstract class MatchupChartDatabase extends RoomDatabase {
    public abstract MatchupChartDao getNoteDao();

    private static MatchupChartDatabase muDB;

    public static MatchupChartDatabase getInstance(Context context) {
        if (null == muDB) {
            muDB = buildDatabaseInstance(context);
        }
        return muDB;
    }

    private static MatchupChartDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                MatchupChartDatabase.class,
                "MU_db")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        muDB = null;
    }
}
