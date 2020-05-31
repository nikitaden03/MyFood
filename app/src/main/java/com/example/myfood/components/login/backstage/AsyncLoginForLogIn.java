package com.example.myfood.components.login.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;

class AsyncLoginForLogIn extends AsyncTask<String, Void, Integer> {

    private ProgressDialog progressDialog;
    private AsynсCallBack asynCallBack;
    private Data data;
    private String email, password;
    private BaseCompatActivity view;

    AsyncLoginForLogIn(AsynсCallBack asyncCallBack, BaseCompatActivity view, String email, String password) {
        this.asynCallBack = asyncCallBack;
        this.view = view;
        this.email = email;
        this.password = password;
    }

    // Создает ProgressDialog, в котором находится надпись о просьбе подождать
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(view);
        progressDialog.setTitle("Пожалуйста, подождите");
        progressDialog.setMessage("Ведется соединение с сервером!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... strings) {
        data = Data.getInstance();
        return data.logIn(email, password, view);
    }

    // Удаляет ProgressDialog
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        asynCallBack.getAsyncTaskLoginResult(integer);
    }
}
