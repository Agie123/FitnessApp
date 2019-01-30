package com.example.gebruiker.fitnessapp.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.gebruiker.fitnessapp.objects.Workout;
import com.example.gebruiker.fitnessapp.repositories.WorkoutRepository;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private WorkoutRepository workoutRepository;
    private LiveData<List<Workout>> allWorkouts;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        allWorkouts = workoutRepository.getAllWorkouts();
    }

    public void insertWorkout(Workout workout) {
        workoutRepository.insert(workout);
    }

    public void updateWorkout(Workout workout) {
        workoutRepository.update(workout);
    }

    public void deleteWorkout(Workout workout) {
        workoutRepository.delete(workout);
    }

    public LiveData<List<Workout>> getAllWorkouts() {
        return allWorkouts;
    }
}
