package com.example.myfood.components.studentFood.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.studentFood.backstage.AsyncCallBack;
import com.example.myfood.data.Data;

import java.util.LinkedList;

class AsyncGetStudentFood extends AsyncTask<Void, Void, LinkedList<String[]>> {

    private AsyncCallBack callback;
    private String token;
    private ProgressDialog progressDialog;
    private BaseCompatActivity baseCompatActivity;

    AsyncGetStudentFood(AsyncCallBack callback, String token, BaseCompatActivity baseCompatActivity) {
        this.callback = callback;
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

    // Удаляет ProgressDialog и возвращает дату в activity через AsyncCallBack
    @Override
    protected void onPostExecute(LinkedList<String[]> treeMaps) {
        super.onPostExecute(treeMaps);
        progressDialog.dismiss();
        callback.setData(treeMaps);
        callback.showData();
    }

    // В отдельном потоке вызывает функцию класса Data, которая возращает история за 1 месяц (если, допустим, сегодня 25, значит с 1 по 25)
    @Override
    protected LinkedList<String[]> doInBackground(Void... voids) {
        Data data = Data.getInstance();
        return data.getStudentFood(token, baseCompatActivity);

    }
}
