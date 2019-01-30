package com.example.gebruiker.fitnessapp.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gebruiker.fitnessapp.daos.WorkoutDao;
import com.example.gebruiker.fitnessapp.database.AppDatabase;
import com.example.gebruiker.fitnessapp.models.SharedViewModel;
import com.example.gebruiker.fitnessapp.objects.Workout;
import com.example.gebruiker.fitnessapp.R;
import com.example.gebruiker.fitnessapp.activities.AddActivity;
import com.example.gebruiker.fitnessapp.activities.MainActivity;
import com.example.gebruiker.fitnessapp.adapters.WorkoutAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class WorkoutFragment extends Fragment {
    private SharedViewModel model;

    WorkoutAdapter adapter = new WorkoutAdapter();
    private WorkoutDao workoutDao;
    static AppDatabase db;


    private List<Workout> workoutList;
//    private RecyclerView rView;
//    private WorkoutAdapter workoutAdapter;
//    static AppDatabase db;
//    FragmentActivity listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(SharedViewModel.class);
        model.getAllWorkouts().observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable List<Workout> workouts) {
                adapter.setWorkouts(workouts);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_workout_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.workoutList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        db = AppDatabase.getInstance(getContext());
        workoutDao = db.workoutDao();

        model.getAllWorkouts().observe(this, new Observer<List<Workout>>() {
            @Override
            public void onChanged(@Nullable List<Workout> workouts) {
                adapter.setWorkouts(workouts);
            }
        });

        //workoutList = new ArrayList<>();
//        rView = view.findViewById(R.id.workoutList);
//        workoutAdapter = new WorkoutAdapter(workoutList);
//        rView.setAdapter(workoutAdapter);
//        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getActivity());
//        rView.setLayoutManager(layoutmanager);
//        db = AppDatabase.getInstance(this.getContext());
//        new WorkoutAsyncTask(TASK_GET_ALL_WORKOUTS).execute();
        FloatingActionButton fab = view.findViewById(R.id.addWorkout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);

            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        model.deleteWorkout(model.getAllWorkouts().getValue().get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //updateUI();

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ADD_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Workout workout = data.getParcelableExtra(MainActivity.EXTRA_WORKOUT);
//                new WorkoutAsyncTask(TASK_INSERT_WORKOUT).execute(workout);
//                System.out.println("insert");
//            }
//        } else if (requestCode == EDIT_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Workout workout = data.getParcelableExtra(MainActivity.EXTRA_WORKOUT);
//                new WorkoutAsyncTask(TASK_UPDATE_WORKOUT).execute(workout);
//            }
//        }
//    }
//
//    private void updateUI() {
//        if (workoutAdapter == null) {
//            workoutAdapter = new WorkoutAdapter(workoutList);
//            rView.setAdapter(workoutAdapter);
//        } else {
//            workoutAdapter.swapList(workoutList);
//        }
//    }
//
//    public class WorkoutAsyncTask extends AsyncTask<Workout, Void, List> {
//
//        private int taskCode;
//
//        public WorkoutAsyncTask(int taskCode){
//            this.taskCode = taskCode;
//        }
//
//        @Override
//        protected List doInBackground(Workout... workouts) {
//            switch (taskCode) {
//                case TASK_DELETE_WORKOUT:
//                    db.workoutDao().deleteWorkouts(workouts[0]);
//                    break;
//
//                case TASK_UPDATE_WORKOUT:
//                    db.workoutDao().updateWorkouts(workouts[0]);
//                    break;
//
//                case TASK_INSERT_WORKOUT:
//                    db.workoutDao().insertWorkouts(workouts[0]);
//                    System.out.println("Insert");
//                    break;
//            }
//
//            //To return a new list with the updated data, we get all the data from the database again.
//            return db.workoutDao().getAllWorkouts();
//        }
//
//        @Override
//        protected void onPostExecute(List list) {
//            super.onPostExecute(list);
//            onWorkoutDbUpdated(list);
//        }
//    }
//
//    public void onWorkoutDbUpdated(List list) {
//        workoutList = list;
//        updateUI();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        this.listener = null;
//    }
}
