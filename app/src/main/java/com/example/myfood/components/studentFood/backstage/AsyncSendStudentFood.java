package com.example.myfood.components.studentFood.backstage;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.myfood.CheckActivity;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.data.Data;

class AsyncSendStudentFood extends AsyncTask<Void, Void, Integer> {

    private String token;
    private String breakfast, teatime, lunch;
    private ProgressDialog progressDialog;
    private BaseCompatActivity baseCompatActivity;
    private AsyncCallBack asyncCallBack;

    AsyncSendStudentFood(String token, String breakfast, String teatime, String lunch, BaseCompatActivity baseCompatActivity, AsyncCallBack asyncCallBack) {
        this.token = token;
        this.breakfast = breakfast;
        this.teatime = teatime;
        this.lunch = lunch;
        this.baseCompatActivity = baseCompatActivity;
        this.asyncCallBack = asyncCallBack;
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

    // Удаляет ProgressDialog и, если код ответа 107 (неверный токен (такое могло произойти, если пользователь зашел на другм устройстве), перенаправляет пользователя на проверку токена (а он уже обнулен)
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
        if (integer == 107) {
            ((MenuContract) baseCompatActivity).showActivity(CheckActivity.class);
        }
        asyncCallBack.afterSendingFood();
    }

    // В отдельном потоке вызывает функцию класса Data, которая отправляет заявку от пользователя на сегодня.
    @Override
    protected Integer doInBackground(Void... voids) {
        Data data = Data.getInstance();
        return data.sendStudentFood(breakfast, teatime, lunch, token, baseCompatActivity);

    }
}
