package com.example.gebruiker.fitnessapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gebruiker.fitnessapp.R;
import com.example.gebruiker.fitnessapp.activities.UserActivity;
import com.example.gebruiker.fitnessapp.models.SharedViewModel;
import com.example.gebruiker.fitnessapp.objects.User;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private SharedViewModel model;
    private User user;

    private TextView tvInfoEmail;
    private TextView tvInfoName;
    private TextView tvInfoTel;
    private Button butProfilePic;
    private ImageView ivProfilePic;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_profile, container, false);

    tvInfoEmail = view.findViewById(R.id.infoEmail);
    tvInfoName = view.findViewById(R.id.infoName);
    tvInfoTel = view.findViewById(R.id.infoTel);
    ivProfilePic = view.findViewById(R.id.profilePic);

    butProfilePic = view.findViewById(R.id.choosePicButton);

        tvInfoName.setText(UserActivity.user.getName() + " " + UserActivity.user.getLastName());
        tvInfoEmail.setText(UserActivity.user.getEmail());
        tvInfoTel.setText(UserActivity.user.getTel());

        butProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    return view;
}

private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
}

@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            ivProfilePic.setImageURI(imageUri);
        }
}
}