package com.example.gebruiker.fitnessapp.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.gebruiker.fitnessapp.objects.Workout;
import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workout")
    LiveData<List<Workout>> getAllWorkouts();

    @Insert
    void insertWorkouts(Workout workouts);

    @Delete
    void deleteWorkouts(Workout workouts);

    @Update
    void updateWorkouts(Workout workouts);
}