package com.example.myfood.components.myClassInfo.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.abstracts.view.BaseCompatActivity;
import com.example.myfood.data.Data;
import com.example.myfood.data.models.Classmate;
import com.example.myfood.data.models.User;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyClassPresenter extends BasePresenter implements MyClassContract.Presenter{

    @Override
    public void prepareInformMyClass(User user) {
        AsyncTaskUserInform asyncTask = new AsyncTaskUserInform(user.getINK() + "", (AsyncCallBack) view, view);
        asyncTask.execute();
    }

    class AsyncTaskUserInform extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        AsyncCallBack asynCallBack;
        String INK;
        BaseCompatActivity view;
        ArrayList<Classmate> classmates;

        AsyncTaskUserInform(String INK, AsyncCallBack asynCallBack, BaseCompatActivity baseCompatActivity) {
            this.INK = INK;
            this.asynCallBack = asynCallBack;
            view = baseCompatActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setTitle("Пожалуйста, подождите");
            progressDialog.setMessage("Ведется соединение с сервером!");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            Log.d("MYTAG", "post execute");
            asynCallBack.reternClassmates(classmates);
        }

        @Override
        protected Integer doInBackground(String... string) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            classmates = Data.getInstance().getClassmates(token, INK + "");
            return 100;
        }
    }
}

