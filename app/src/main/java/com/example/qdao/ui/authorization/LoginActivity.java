package com.example.qdao.ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qdao.R;
import service.utils.ToastHelper;

import com.example.qdao.ui.my_proposals.MyProposalsActivity;

import remote.user_models.AuthorizeUserResponseDto;
import service.utils.Result;
import view_model.AuthorizationViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText loginET;
    private EditText passwordET;
    private AuthorizationViewModel viewModel;

    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(AuthorizationViewModel.class);

        viewModel.getUserAuthResult().observe(this, new Observer<Result<AuthorizeUserResponseDto>>() {
            @Override
            public void onChanged(Result<AuthorizeUserResponseDto> authorizeUserResponseDtoResult) {
                if (authorizeUserResponseDtoResult.isSuccess()) {
                    sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putInt("user_id", authorizeUserResponseDtoResult.getData().getId());
                    editor.putInt("user_role", authorizeUserResponseDtoResult.getData().getRole());
                    editor.putString("token", authorizeUserResponseDtoResult.getData().getToken());

                    openMyProposalsPage();
                } else {
                    ToastHelper.make(LoginActivity.this, authorizeUserResponseDtoResult.getErrorMessage());
                }
            }
        });

        viewModel.getUserRegistrationResult().observe(this, new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result.isSuccess()) {
                   ToastHelper.make(LoginActivity.this, "Вы успешно зарегестированы");
                } else {
                    ToastHelper.make(LoginActivity.this, result.getErrorMessage());
                }
            }
        });

        Button createAccountBtn = findViewById(R.id.register_btn);
        Button loginBtn = findViewById(R.id.login_btn);
        passwordET = findViewById(R.id.password);
        loginET = findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.authUser(loginET.getText().toString(), passwordET.getText().toString());
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result<Void> userRegistrationResult = viewModel.registerUser(loginET.getText().toString(), passwordET.getText().toString());
                if (!userRegistrationResult.isSuccess()) {
                    ToastHelper.make(LoginActivity.this, userRegistrationResult.getErrorMessage());
                }
            }
        });
    }

    private void openMyProposalsPage() {
        Intent intent = new Intent(LoginActivity.this, MyProposalsActivity.class);
        startActivity(intent);
        finish();
    }
}


