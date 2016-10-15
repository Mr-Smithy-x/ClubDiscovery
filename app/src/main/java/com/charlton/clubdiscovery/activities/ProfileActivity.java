package com.charlton.clubdiscovery.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.charlton.clubdiscovery.ClubSingleton;
import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.charlton.clubdiscovery.databinding.ProfileActivityBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    public static Intent create(Context context, UserModel userModel) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("USER", new Gson().toJson(userModel));
        return intent;
    }

    UserModel userModel;
    ProfileActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        if (getIntent() != null && getIntent().hasExtra("USER")) {
            userModel = new Gson().fromJson(getIntent().getStringExtra("USER"),UserModel.class);
            if(userModel == null) {
                Log.e("ERROR","IS NULL");
                finish();
                return;
            }
            Glide.with(this).load(userModel.getUser_profile_photo()).crossFade(750).into(binding.profileUserIcon);
            Glide.with(this).load(userModel.getUser_splash_photo()).crossFade(750).into(binding.headerView);


            if (userModel.getUser_school_code() != null && userModel.getUser_school_code().getSchool_name() != null) {
                binding.profileCollege.setText(userModel.getUser_school_code().getSchool_name());
            }
            if (userModel.getUser_major_id() != null) {
                binding.profileClubTitle.setText(userModel.getUser_major_id().getMajor_title());
            }

            if (userModel.getUser_email() != null) {
                binding.profileEmail.setText(userModel.getUser_email());
            }
            if (userModel.getUser_phone() != null) {
                binding.profilePhone.setText(userModel.getUser_phone());
            }
            if (userModel.getFullName() != null) {
                binding.profileName.setText(userModel.getFullName());
            }

        } else {
            finish();
        }

        ClubSingleton.getClubService().getClubById(userModel.getUser_id()).enqueue(new Callback<ResponseModel<ClubModel>>() {
            @Override
            public void onResponse(Call<ResponseModel<ClubModel>> call, Response<ResponseModel<ClubModel>> response) {
                if (response.body() != null && response.body().getData() != null) {
                    binding.profileClubTitle.setText(response.body().getData().getClub_title());
                    UserModel president = response.body().getData().getClub_president(),
                            officer_1 = response.body().getData().getClub_officer_1(),
                            officer_2 = response.body().getData().getClub_officer_2(),
                            officer_3 = response.body().getData().getClub_officer_3();
                    if (president != null && president.getUser_club_id() == userModel.getUser_club_id()) {
                        binding.profileClubStatus.setText("President");
                    } else if (officer_1 != null && officer_1.getUser_club_id() == userModel.getUser_club_id()) {
                        binding.profileClubStatus.setText("Officer");
                    } else if (officer_2 != null && officer_2.getUser_club_id() == userModel.getUser_club_id()) {
                        binding.profileClubStatus.setText("Officer");
                    } else if (officer_3 != null && officer_3.getUser_club_id() == userModel.getUser_club_id()) {
                        binding.profileClubStatus.setText("Officer");
                    } else {
                        binding.profileClubStatus.setText("Member");
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ClubModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
