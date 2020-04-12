package com.example.myfood.abstracts.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood.components.login.ui.LogInActivity;

public abstract class BaseCompatActivity extends AppCompatActivity implements BaseMvpView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void checkSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if ("".equals(token)) {
            startActivity(new Intent(this, LogInActivity.class));
        }
    }

}
