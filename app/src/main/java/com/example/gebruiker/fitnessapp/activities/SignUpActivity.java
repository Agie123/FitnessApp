package com.example.gebruiker.fitnessapp.activities;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.example.gebruiker.fitnessapp.R;
import com.example.gebruiker.fitnessapp.database.AppDatabase;
import com.example.gebruiker.fitnessapp.daos.UserDao;
import com.example.gebruiker.fitnessapp.objects.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtPhone;

    private Button btCancel;
    private Button btRegister;

    private UserDao userDao;

    private ProgressDialog progressDialog;

    //Constants used when calling the update activity
    public static final String EXTRA_USER = "User";
    public static final int REQUESTCODE = 1234;
    private int mModifyPosition;
    public final static int TASK_GET_ALL_USER = 0;
    public final static int TASK_DELETE_USER = 1;
    public final static int TASK_UPDATE_USER = 2;
    public final static int TASK_INSERT_USER = 3;
    static AppDatabase roomDb;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        db = FirebaseFirestore.getInstance();
        DebugDB.getAddressLog();

        edtName = findViewById(R.id.nameinput);
        edtLastName = findViewById(R.id.lastnameinput);
        edtEmail = findViewById(R.id.emailinput);
        edtPassword = findViewById(R.id.passwordinput);
        edtPhone = findViewById(R.id.phoneInput);

        btCancel = findViewById(R.id.btCancel);
        btRegister = findViewById(R.id.btRegister);

        roomDb = AppDatabase.getInstance(this);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(edtName.getText().toString(), edtLastName.getText().toString(),
                                    edtEmail.getText().toString(), edtPassword.getText().toString(), edtPhone.getText().toString());
                           new UserAsyncTask(TASK_INSERT_USER).execute(user);
                            progressDialog.dismiss();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        }
                    }, 1000);

                } else {
                    Toast.makeText(SignUpActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtName.getText().toString()) ||
                TextUtils.isEmpty(edtLastName.getText().toString())
                ) {
            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void USEROnClick(int i) {
//        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
//        mModifyPosition = i;
//        intent.putExtra(EXTRA_USER, mUsers.get(i));
//        startActivityForResult(intent, REQUESTCODE);
//    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {

            if (resultCode == RESULT_OK) {
                User updatedUser = data.getParcelableExtra(SignUpActivity.EXTRA_USER);

                // New timestamp: timestamp of update
                new UserAsyncTask(TASK_UPDATE_USER).execute(updatedUser);
            }
        }
    }

    public class UserAsyncTask extends AsyncTask<User, Void, List> {

        private int taskCode;

        public UserAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(User... users) {
            switch (taskCode) {

                case TASK_DELETE_USER:
                    roomDb.userDao().delete(users[0]);
                    break;


                case TASK_UPDATE_USER:
                    roomDb.userDao().update(users[0]);
                    break;

                case TASK_INSERT_USER:
                    roomDb.userDao().insert(users[0]);
                    // Add a new document with a generated id.
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", users[0].getName() + " " + users[0].getLastName());
                    data.put("email", users[0].getEmail());
                    data.put("password", users[0].getPassword());
                    data.put("phonenumber", users[0].getTel());

                    db.collection("users")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("SignupActivity", "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("SignupActivity", "Error adding document", e);
                                }
                            });
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return roomDb.userDao().getAllUsers();
        }
    }
}
