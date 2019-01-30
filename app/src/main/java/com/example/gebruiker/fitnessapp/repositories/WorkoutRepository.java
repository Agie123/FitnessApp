package com.example.gebruiker.fitnessapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.gebruiker.fitnessapp.daos.WorkoutDao;
import com.example.gebruiker.fitnessapp.database.AppDatabase;
import com.example.gebruiker.fitnessapp.objects.Workout;

import java.util.List;

public class WorkoutRepository {
    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        workoutDao = appDatabase.workoutDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }

    public void insert(Workout workout) {
        new InsertWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void update(Workout workout) {
        new UpdateWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void delete(Workout workout) {
        new DeleteWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }

    private static class InsertWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        private InsertWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.insertWorkouts(workouts[0]);
            return null;
        }
    }

    private static class UpdateWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        private UpdateWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.updateWorkouts(workouts[0]);
            return null;
        }
    }

    private static class DeleteWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        private DeleteWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.deleteWorkouts(workouts[0]);
            return null;
        }
    }
}
