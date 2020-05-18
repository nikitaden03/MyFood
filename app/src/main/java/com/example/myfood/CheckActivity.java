package com.example.myfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.login.ui.LogInActivity;
import com.example.myfood.components.mainScreen.ui.MainScreenActivity;
import com.example.myfood.data.Data;
import com.example.myfood.data.models.User;

public class CheckActivity extends BaseCompatActivity implements callback {

    Intent intent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if ("".equals(token)) {
            intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        } else {
            AsyncTas asyncTas = new AsyncTas();
            asyncTas.setVar(token, this, this);
            asyncTas.execute();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void reloadActivity() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("token", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", "");
        editor.apply();
        Intent intent = new Intent(this, CheckActivity.class);
        startActivity(intent);
    }

    @Override
    public void continueActivity(User user) {
        this.user = user;
        Log.d("MYTAG", user.getName());
        intent = new Intent(this, MainScreenActivity.class);
        intent.putExtra("UserClass", user);
        startActivity(intent);
        finish();
    }
}

class AsyncTas extends AsyncTask<String, Void, Boolean> {

    private Data data;
    private String token;
    private BaseCompatActivity baseCompatActivity;
    private User user;
    callback callback;

    AsyncTas() {
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        data = Data.getInstance();
        user = data.getInformation(token, baseCompatActivity);
        return user == null;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        Log.d("MYTAG", "5");
        if (b) {
            callback.reloadActivity();
        } else {
            callback.continueActivity(user);
        }
    }

    public void setVar(String token,  BaseCompatActivity baseCompatActivity, callback callback) {
        this.token = token;
        this.baseCompatActivity = baseCompatActivity;
        this.callback = callback;
    }
}

interface callback {
    void reloadActivity();
    void continueActivity(User user);
}