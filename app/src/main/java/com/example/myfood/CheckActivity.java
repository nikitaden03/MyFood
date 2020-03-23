package com.example.myfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myfood.components.login.ui.LogInActivity;
import com.example.myfood.components.mainScreen.ui.MainScreenActivity;
import com.example.myfood.data.User;

public class CheckActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if ("".equals(token)) {
            intent = new Intent(this, LogInActivity.class);
        } else {

            //TODO Делаем запрос в бд, получаем базовую информацию описанною классом USER

            User user = new User(1, "Иван Иванов Ярославович", "156", "10A", 70, 50, 90, 698745, true);

            intent = new Intent(this, MainScreenActivity.class);
            intent.putExtra("UserClass", user);
        }


        startActivity(intent);
        finish();
    }
}
