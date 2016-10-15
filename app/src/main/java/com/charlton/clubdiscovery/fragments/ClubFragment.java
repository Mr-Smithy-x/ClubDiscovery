package com.charlton.clubdiscovery.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charlton.clubdiscovery.ClubSingleton;
import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.activities.ProfileActivity;
import com.charlton.clubdiscovery.adapters.ClubRecyclerAdapter;
import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.charlton.clubdiscovery.databinding.ClubFragmentBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cj on 10/15/16.
 */

public class ClubFragment extends Fragment {

    private ClubFragmentBinding binding;
    private ClubRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.club_fragment, container, false);
        binding.clubRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        binding.clubRecyclerView.setAdapter(adapter = new ClubRecyclerAdapter() {
            @Override
            public void OnProfileClicked(View view, int position, UserModel userModel) {
                if (userModel == null) {
                    Log.e("TO PROFILE", "IS NULL");
                }
                startActivity(ProfileActivity.create(view.getContext(), userModel), ActivityOptionsCompat.makeBasic().toBundle());
            }

            @Override
            public void OnCardClicked(View view, int position, ClubModel clubModel) {

            }
        });
        ClubSingleton.getClubService().getAllClubs().enqueue(new Callback<ResponseModel<ArrayList<ClubModel>>>() {
            @Override
            public void onResponse(Call<ResponseModel<ArrayList<ClubModel>>> call, Response<ResponseModel<ArrayList<ClubModel>>> response) {
                if (response.body() != null) {
                    adapter.removeAll();
                    adapter.addClubs(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<ArrayList<ClubModel>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static ClubFragment instance() {
        return new ClubFragment();
    }

    public void search(ArrayList<ClubModel> clubModels) {
        try {
            adapter.removeAll();
            adapter.addClubs(clubModels);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
