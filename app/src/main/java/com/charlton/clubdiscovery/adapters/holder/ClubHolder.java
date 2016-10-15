package com.charlton.clubdiscovery.adapters.holder;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.charlton.clubdiscovery.ClubSingleton;
import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Charlton Smith
 */
public class ClubHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView title, college, time;
    private AppCompatButton join;
    private CircleImageView profile_img;
    private boolean join_show = true;
    public ClubHolder(View itemView) {
        super(itemView);
        title = (AppCompatTextView) itemView.findViewById(R.id.club_layout_title);
        college = (AppCompatTextView) itemView.findViewById(R.id.club_layout_college);
        time = (AppCompatTextView) itemView.findViewById(R.id.club_layout_time);
        join = (AppCompatButton) itemView.findViewById(R.id.club_layout_join_btn);
        if (!ClubSingleton.hasUser()) {
            join.setVisibility(View.GONE);
        }
        profile_img = (CircleImageView) itemView.findViewById(R.id.club_layout_profile_img);

    }


    public void bind(final ClubModel club, final OnClickListener onClickListener) {
        title.setText(club.getClub_title());
        college.setText(club.getClub_school_code().getSchool_name());
        time.setText(String.format("%s - %s", club.getTime(club.getClub_start()), club.getTime(club.getClub_end())));
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UserModel userModel = ClubSingleton.getUser();
                final ClubModel clubModel = ClubSingleton.getClub();

                if (clubModel != null) {
                    new AlertDialog.Builder(itemView.getContext()).setTitle("Uh-Oh").setMessage(String.format("You're already apart of the %s club at %s! Do you want to join anyway?", clubModel.getClub_title(), clubModel.getClub_school_code().getSchool_name()))
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    joinClub(club, userModel);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {
                    joinClub(club, userModel);
                }
            }
        });
        if(club.getClub_president() != null && club.getClub_president().getUser_profile_photo() != null) {
            Glide.with(itemView.getContext()).load(club.getClub_president().getUser_profile_photo()).crossFade(750).into(profile_img);
        }
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SHIT",new Gson().toJson(club));
                onClickListener.OnProfileClicked(v, getAdapterPosition(),club.getClub_president());
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnCardClicked(v, getAdapterPosition(), club);
            }
        });
    }

    private void joinClub(ClubModel club, UserModel userModel) {
        ClubSingleton.getClubService().joinClub(userModel.getUser_id(), club.getClub_id()).enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                if(response.body() != null && response.body().getData() != null) {
                    Snackbar.make(itemView.getRootView(), response.body().getData(), Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(itemView.getRootView(), "Unable to join the club ", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                Snackbar.make(itemView.getRootView(), "Unable to join the club", Snackbar.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public interface OnClickListener {
        void OnProfileClicked(View view, int position, UserModel userModel);

        void OnCardClicked(View view, int position, ClubModel clubModel);
    }
}
