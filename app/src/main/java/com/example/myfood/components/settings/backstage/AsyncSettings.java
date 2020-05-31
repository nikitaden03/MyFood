package com.example.myfood.components.settings.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;

class AsyncSettings extends AsyncTask<String, Void, Void> {

    private ProgressDialog progressDialog;
    private int breakfast, teatime, lunch;
    private String token;
    private BaseCompatActivity baseCompatActivity;

    AsyncSettings(int breakfast, int teatime, int lunch, String token, BaseCompatActivity baseCompatActivity) {
        this.breakfast = breakfast;
        this.teatime = teatime;
        this.lunch = lunch;
        this.token = token;
        this.baseCompatActivity = baseCompatActivity;
    }

    // Создает ProgressDialog, в котором находится надпись о просьбе подождать
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(baseCompatActivity);
        progressDialog.setTitle("Пожалуйста, подождите");
        progressDialog.setMessage("Ведется соединение с сервером!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // В отдельном потоке вызывает функцию класса Data, которая изменяет цены питания на сервере.
    @Override
    protected Void doInBackground(String... strings) {
        Data data = Data.getInstance();
        data.addPrice(breakfast, teatime, lunch, token, baseCompatActivity);
        return null;
    }

    // Удаляет ProgressDialog
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
