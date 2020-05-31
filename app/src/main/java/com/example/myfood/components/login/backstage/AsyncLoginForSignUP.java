package com.example.myfood.components.login.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;

class AsyncLoginForSignUP extends AsyncTask<String, Void, Integer> {

    private ProgressDialog progressDialog;
    private AsynсCallBack asynсCallBack;
    private String email, password, name, groupNum, classNum, schoolNum;
    private BaseCompatActivity view;

    AsyncLoginForSignUP(AsynсCallBack asynсCallBack, BaseCompatActivity view, String email, String password, String name, String groupNum, String classNum, String schoolNum) {
        this.asynсCallBack = asynсCallBack;
        this.view = view;
        this.email = email;
        this.password = password;
        this.name = name;
        this.groupNum = groupNum;
        this.classNum = classNum;
        this.schoolNum = schoolNum;
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
        Data data = Data.getInstance();
        return data.signUp(email, password, name, groupNum, classNum, schoolNum);
    }

    // Удаляет ProgressDialog
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        asynсCallBack.getAsyncTaskSignUpResult(integer);
    }
}
