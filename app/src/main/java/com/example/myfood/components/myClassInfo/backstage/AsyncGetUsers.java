package com.example.myfood.components.myClassInfo.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class AsyncGetUsers extends AsyncTask<String, Void, Integer> {

    ProgressDialog progressDialog;
    AsyncCallBack asynCallBack;
    String INK;
    BaseCompatActivity view;
    ArrayList<String> classmates;

    AsyncGetUsers(String INK, AsyncCallBack asynCallBack, BaseCompatActivity baseCompatActivity) {
        this.INK = INK;
        this.asynCallBack = asynCallBack;
        view = baseCompatActivity;
    }

    // Создает ProgressDialog, в котором находится надпись с просьбой подождать
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setTitle("Пожалуйста, подождите");
        progressDialog.setMessage("Ведется соединение с сервером!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Удаляет ProgressDialog и возращает данные в activity (так как она реализет интерфейс AsyncCallBack)
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        Log.d("MYTAG", "post execute");
        asynCallBack.returnClassmates(classmates);
    }

    // В отдельном потоке вызывает функцию класса Data, которая будет скачивать данные о классе (в котором находится ученик или учитель).
    @Override
    protected Integer doInBackground(String... string) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        classmates = Data.getInstance().getClassmates(token, INK + "");
        return 100;
    }
}
