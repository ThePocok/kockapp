package hu.thepocok.kockapp.persistence.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import hu.thepocok.kockapp.persistence.dao.ResultDao;
import hu.thepocok.kockapp.persistence.entity.Result;

@Database(entities = {Result.class}, version = 1)
public abstract class ResultDatabase extends RoomDatabase {
    private static ResultDatabase INSTANCE;
    private static final String DB_NAME = "base.db";

    public abstract ResultDao resultDao();

    public static ResultDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (ResultDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            ResultDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}