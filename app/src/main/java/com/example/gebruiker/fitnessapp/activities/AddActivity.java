package com.example.gebruiker.fitnessapp.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gebruiker.fitnessapp.database.AppDatabase;
import com.example.gebruiker.fitnessapp.models.SharedViewModel;
import com.example.gebruiker.fitnessapp.objects.Workout;
import com.example.gebruiker.fitnessapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    Spinner sItems;
    FloatingActionButton fabSaveWorkout;
    EditText workoutName;
    EditText workoutDescription;
    String selectedItemText;
    Intent intent;
    String dateNow = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    Workout newWorkout;
    static AppDatabase db;
    private SharedViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);

        db = AppDatabase.getInstance(this);
        sItems = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.aantal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems.setAdapter(adapter);

        workoutName = findViewById(R.id.workoutName);
        workoutDescription = findViewById(R.id.descriptionWorkout);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fabSaveWorkout = findViewById(R.id.saveWorkout);
        fabSaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(workoutName.getText().toString().isEmpty() || workoutDescription.getText().toString().isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Don't leave the fields empty!", Snackbar.LENGTH_LONG).show();
                } else{
                    intent = new Intent();
                    newWorkout = new Workout(workoutName.getText().toString(), workoutDescription.getText().toString(), selectedItemText, dateNow);
                    intent.putExtra(MainActivity.EXTRA_WORKOUT, newWorkout);
                    setResult(Activity.RESULT_OK, intent);
                    model.insertWorkout(newWorkout);
                    finish();
                    Toast.makeText(AddActivity.this, "Added workout", Toast.LENGTH_SHORT).show();


//                    db.workoutDao().insertWorkouts(newWorkout);
//                    db.workoutDao().getAllWorkouts();
                }
            }
        });
        }

    }

