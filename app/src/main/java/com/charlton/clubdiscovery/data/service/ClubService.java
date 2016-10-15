package com.charlton.clubdiscovery.data.service;

import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.UserCredential;
import com.charlton.clubdiscovery.data.models.response.ClubModel;
import com.charlton.clubdiscovery.data.models.response.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by cj on 10/14/16.
 */

public interface ClubService {

    @GET("api/bcc/user_login")
    Call<ResponseModel<UserModel>> loginUser(@QueryMap UserCredential userCredentials);

    @GET("api/bcc/user_register")
    Call<ResponseModel<UserModel>> registerUser(@QueryMap UserCredential userCredentials);

    @GET("api/bcc/get_club_by_school")
    Call<ResponseModel<ArrayList<ClubModel>>> getClubsBySchool(@Query("school") String school);

    @GET("api/bcc/get_club_by_school")
    Call<ResponseModel<ArrayList<ClubModel>>> searchClubAtSchool(@Query("name") String club, @Query("school") String school);

    @GET("api/bcc/find_clubs")
    Call<ResponseModel<ArrayList<ClubModel>>> findClubs(@Query("name") String club);

    @GET("api/bcc/is_president")
    Call<ResponseModel<ClubModel>> isPresident(@Query("uid") int user_id);

    @GET("api/bcc/join_club")
    Call<ResponseModel<String>> joinClub(@Query("uid") int user_id, @Query("cid") int club_id);

    @GET("api/bcc/get_all_clubs")
    Call<ResponseModel<ArrayList<ClubModel>>> getAllClubs();

    @GET("api/bcc/get_club")
    Call<ResponseModel<ClubModel>> getClubById(@Query("id") int club_id);
}
