package com.example.gebruiker.fitnessapp.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.gebruiker.fitnessapp.daos.UserDao;
import com.example.gebruiker.fitnessapp.daos.WorkoutDao;
import com.example.gebruiker.fitnessapp.objects.User;
import com.example.gebruiker.fitnessapp.objects.Workout;

@Database(entities = {Workout.class, User.class}, version = 3, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase{

        public abstract WorkoutDao workoutDao();
        public abstract UserDao userDao();

    private final static String NAME_DATABASE = "fitnessApp_db";

        //Static instance
        private static AppDatabase sInstance;

        public static synchronized AppDatabase getInstance(Context context) {

            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context, AppDatabase.class, NAME_DATABASE)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallBack)
                        .build();
            }
            return sInstance;
        }

        private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(sInstance).execute();
            }
        };

        private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
            private WorkoutDao workoutDao;

            private PopulateDbAsyncTask(AppDatabase db) {
                workoutDao = db.workoutDao();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                workoutDao.insertWorkouts(new Workout("Squats", "10x Squats", "3", "28-1-2019"));
                workoutDao.insertWorkouts(new Workout("Push-ups", "10x Push-ups", "3", "28-1-2019"));
                workoutDao.insertWorkouts(new Workout("Sit-Ups", "10x Sit-ups", "3", "28-1-2019"));

                return null;
            }
        }
}
