package com.charlton.clubdiscovery;

import android.databinding.DataBindingUtil;
import android.inputmethodservice.Keyboard;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.charlton.clubdiscovery.adapters.ClubRecyclerAdapter;
import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.charlton.clubdiscovery.databinding.MainActivityBinding;
import com.charlton.clubdiscovery.dialog.CollegeFilterDialog;
import com.charlton.clubdiscovery.fragments.ClubFragment;
import com.charlton.clubdiscovery.login.LoginActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MainActivityBinding mainActivityBinding;
    ActionBarDrawerToggle actionBarDrawerToggle;
    UserModel userModel;
    ClubModel clubModel;
    boolean elevated = false;
    private ClubFragment clubSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        mainActivityBinding.mainSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    ClubSingleton.getClubService().searchClubAtSchool(mainActivityBinding.mainSearch.getText().toString(), String.valueOf(userModel.getUser_school_code().getSchool_code())).enqueue(new Callback<ResponseModel<ArrayList<ClubModel>>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<ArrayList<ClubModel>>> call, Response<ResponseModel<ArrayList<ClubModel>>> response) {
                            if (response.body() != null && response.body().getData() != null) {
                                ArrayList<ClubModel> clubModels = response.body().getData();
                                if (clubSearch != null)
                                    clubSearch.search(clubModels);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<ArrayList<ClubModel>>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        mainActivityBinding.filterBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });
        userModel = ClubSingleton.getUser();
        if (userModel == null) {
            Log.e("USER", "IS NULL");
        }
        mainActivityBinding.slideDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mainActivityBinding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        initFragment(clubSearch != null ? clubSearch : (clubSearch = ClubFragment.instance()), ClubFragment.class.getName());
        signin();
    }

    private void initFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Fragment frag = fm.findFragmentByTag(tag);
        if (frag != null) {
            if (fm.getFragments() != null) {
                for (Fragment f : fm.getFragments()) {
                    if (f != null && !f.getTag().equals(tag)) {
                        ft.hide(f);
                    }
                }
            }
            ft.show(frag);
        } else if (frag == null) {
            if (fm.getFragments() != null) {
                for (Fragment f : fm.getFragments()) {
                    if (f == null) continue;
                    if (f.getTag() == null || !f.getTag().equals(tag)) {
                        ft.hide(f);
                    }
                }
            }
            ft.addToBackStack(tag);
            ft.add(R.id.replacement_framelayout, fragment, tag);
        }
        ft.commit();
    }


    private void showFilterDialog() {
        new CollegeFilterDialog().show(getSupportFragmentManager(), "COLLEGE_FILTER");
    }

    public void signin() {
        if (mainActivityBinding.navigationDrawer.getHeaderView(0) != null) {
            View view = mainActivityBinding.navigationDrawer.getHeaderView(0);
            view.findViewById(R.id.drawer_profile_settings).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(LoginActivity.create(MainActivity.this), LoginActivity.LOGIN);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLogin();
        initUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLogin();
        initUser();
    }

    void initUser() {
        View view = mainActivityBinding.navigationDrawer.getHeaderView(0);
        setUserContent(view);
    }

    void initLogin() {
        if (ClubSingleton.hasUser()) {
            if (userModel.getUser_club_id() != 0) {
                ClubSingleton.getClubService().getClubById(userModel.getUser_club_id()).enqueue(new Callback<ResponseModel<ClubModel>>() {
                    @Override
                    public void onResponse(Call<ResponseModel<ClubModel>> call, Response<ResponseModel<ClubModel>> response) {
                        ClubSingleton.setClub(response.body().getData());
                    }

                    @Override
                    public void onFailure(Call<ResponseModel<ClubModel>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
            ClubSingleton.getClubService().isPresident(userModel.getUser_id()).enqueue(new Callback<ResponseModel<ClubModel>>() {
                @Override
                public void onResponse(Call<ResponseModel<ClubModel>> call, Response<ResponseModel<ClubModel>> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            clubModel = response.body().getData();
                            elevated = true;
                        } else {
                            elevated = false;
                        }
                        initSwitch();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel<ClubModel>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    void setVisibility(View view, boolean hide) {
        view.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    void setUserContent(View view) {

        Log.e("USER", ClubSingleton.getSharedPreferences().getString(ClubSingleton.SP_USER_KEY, "NO USER"));
        if (userModel != null) {
            AppCompatTextView name = (AppCompatTextView) view.findViewById(R.id.drawer_profile_name);
            CircleImageView icon = (CircleImageView) view.findViewById(R.id.drawer_profile_icon);
            AppCompatTextView college = (AppCompatTextView) view.findViewById(R.id.drawer_profile_college);
            AppCompatTextView major = (AppCompatTextView) view.findViewById(R.id.drawer_profile_major);
            if (userModel.getUser_school_code() != null && userModel.getUser_school_code().getSchool_name() != null) {
                college.setText(userModel.getUser_school_code().getSchool_name());
            }
            if (userModel.getUser_firstname() != null && userModel.getUser_lastname() != null) {
                name.setText(userModel.getFullName());
            }
            if (userModel.getUser_major_id() != null) {
                major.setText(userModel.getUser_major_id().getMajor_title());
            }
            if (userModel.getUser_profile_photo() != null) {
                Glide.with(this).load(userModel.getUser_profile_photo()).crossFade(750).into(icon);
            }
        }
    }

    void initSwitch() {
        if (mainActivityBinding.clubOfficer != null) {
            setVisibility(mainActivityBinding.clubOfficer, elevated);
        } else {
            View view = mainActivityBinding.navigationDrawer.getHeaderView(0);
            setVisibility(view.findViewById(R.id.club_officer), elevated);
        }
    }
}
