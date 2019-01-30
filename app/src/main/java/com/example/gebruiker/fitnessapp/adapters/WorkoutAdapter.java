package com.example.gebruiker.fitnessapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gebruiker.fitnessapp.objects.Workout;
import com.example.gebruiker.fitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder> {
    private List<Workout> workouts = new ArrayList<>();

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_workout, viewGroup, false);
        return new WorkoutHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder workoutHolder, int i) {
        Workout currentWorkout = workouts.get(i);

        workoutHolder.textViewName.setText(currentWorkout.getName());
        workoutHolder.textViewDescription.setText(currentWorkout.getDescription());
        workoutHolder.textViewDate.setText(currentWorkout.getDate());
        workoutHolder.textViewTrophies.setText(currentWorkout.getAantal());
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    class WorkoutHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewTrophies;

        public WorkoutHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.mName);
            textViewDescription = itemView.findViewById(R.id.mDescription);
            textViewDate = itemView.findViewById(R.id.mDate);
            textViewTrophies = itemView.findViewById(R.id.mTrophies);
        }
    }
}