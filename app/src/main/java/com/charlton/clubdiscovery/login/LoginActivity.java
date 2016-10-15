package com.charlton.clubdiscovery.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.charlton.clubdiscovery.ClubSingleton;
import com.charlton.clubdiscovery.R;
import com.charlton.clubdiscovery.data.models.ResponseModel;
import com.charlton.clubdiscovery.data.models.UserCredential;
import com.charlton.clubdiscovery.data.models.response.UserModel;
import com.charlton.clubdiscovery.databinding.LoginActivityBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final int LOGIN = 4711;

    LoginActivityBinding binding;
    boolean sign_in = true;

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finishActivity(LOGIN);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        binding.toggleLoginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_in = !sign_in;
                initLayout();
            }
        });
        binding.toggleActionLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sign_in) {
                    ClubSingleton.getClubService().loginUser(UserCredential.Login(binding.email.getText().toString(), binding.password.getText().toString())).enqueue(new Callback<ResponseModel<UserModel>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<UserModel>> call, Response<ResponseModel<UserModel>> response) {
                            if (response.body() != null) {
                                ResponseModel<UserModel> userModelResponse = response.body();
                                if (userModelResponse.getStatus() == 200) {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    ClubSingleton.setUser(response.body().getData());
                                    setResult(RESULT_OK);
                                    finishActivity(LOGIN);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<UserModel>> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    ClubSingleton.getClubService().registerUser(UserCredential.Register(binding.email.getText().toString(), binding.password.getText().toString(), binding.phonenumber.getText().toString(), binding.firstname.getText().toString(), binding.lastname.getText().toString())).enqueue(new Callback<ResponseModel<UserModel>>() {
                        @Override
                        public void onResponse(Call<ResponseModel<UserModel>> call, Response<ResponseModel<UserModel>> response) {
                            if (response.body() != null) {
                                ResponseModel<UserModel> userModelResponse = response.body();
                                if (userModelResponse.getStatus() == 200) {
                                    Toast.makeText(LoginActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    ClubSingleton.setUser(userModelResponse.getData());
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel<UserModel>> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Invalid Registration", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        initLayout();
    }

    private void initLayout() {
        if (sign_in) {
            binding.headerLogo.setVisibility(View.VISIBLE);
            binding.toggleActionLoginRegister.setText(R.string.sign_in);
            binding.toggleLoginOrRegister.setText(R.string.don_apos_t_have_and_account_register);
            binding.resetOrTerms.setText(R.string.reset_password);
            binding.registerLayouts.setVisibility(View.GONE);
        } else {
            binding.headerLogo.setVisibility(View.GONE);
            binding.toggleActionLoginRegister.setText(R.string.register);
            binding.resetOrTerms.setText(R.string.terms_of_use);
            binding.toggleLoginOrRegister.setText(R.string.already_have_an_account_login);
            binding.registerLayouts.setVisibility(View.VISIBLE);
        }
    }

    public static Intent create(Context context) {
        return new Intent(context,LoginActivity.class);
    }
}
