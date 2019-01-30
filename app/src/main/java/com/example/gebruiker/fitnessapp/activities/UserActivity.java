package com.example.gebruiker.fitnessapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.gebruiker.fitnessapp.R;
import com.example.gebruiker.fitnessapp.fragments.LeaderboardFragment;
import com.example.gebruiker.fitnessapp.fragments.ProfileFragment;
import com.example.gebruiker.fitnessapp.fragments.WorkoutFragment;
import com.example.gebruiker.fitnessapp.objects.User;

public class UserActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView tvUser;
    private Toolbar toolbar;

    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadFragment(new LeaderboardFragment());


        user = (User) getIntent().getSerializableExtra("User");

        BottomNavigationView botNav = findViewById(R.id.navigationView);
        botNav.setOnNavigationItemSelectedListener(this);
            
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_item_power) {
            final Intent intent = new Intent(UserActivity.this, MainActivity.class);

            final AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
            builder.setMessage("Are you sure you want to logout");
            builder.setTitle("Log out");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(UserActivity.this, "Log out succesful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    UserActivity.this.finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment;

        switch(item.getItemId()) {
            case R.id.navigation_home:
                selectedFragment = new LeaderboardFragment();
                toolbar.setTitle(R.string.title_home);
                loadFragment(selectedFragment);
                break;
            case R.id.navigation_workouts:
                selectedFragment = new WorkoutFragment();
                toolbar.setTitle(R.string.title_workout);
                loadFragment(selectedFragment);
                break;
            case R.id.navigation_profile:
                selectedFragment = new ProfileFragment();
                toolbar.setTitle(R.string.title_Profile);
                loadFragment(selectedFragment);
                break;
        }
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

