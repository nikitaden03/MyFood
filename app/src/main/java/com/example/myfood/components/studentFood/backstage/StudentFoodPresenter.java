package com.example.myfood.components.studentFood.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.myfood.CheckActivity;
import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.components.menu.MenuContract;
import com.example.myfood.data.Data;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class StudentFoodPresenter extends BasePresenter implements StudentFoodContract.Presenter {

    private Calendar calendar;
    private List<String[]> data;
    callBackinterface callBackinterface;
    int cursor;

    public StudentFoodPresenter() {
        calendar = new GregorianCalendar();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void preapareData() {
        callBackinterface = (com.example.myfood.components.studentFood.backstage.callBackinterface) view;

        if (data == null) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            AsyncTaskStudentFood taskTeacherFood = new AsyncTaskStudentFood(callBackinterface, token);
            taskTeacherFood.execute();
        } else {
            callBackinterface.showData();
        }
    }

    @Override
    public String[] getData(){
        return data.get(cursor - 1);
    }

    @Override
    public void sendFood(String breakfast, String teatime, String lunch) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        AsyncTaskStudentSendFood asyncTaskStudentSendFood = new AsyncTaskStudentSendFood(token, breakfast, teatime, lunch);
        asyncTaskStudentSendFood.execute();
    }

    @Override
    public Boolean hasTodayData() {
        try {
            String[] useless = data.get(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            if (useless[0].equals("1") || useless[1].equals("1") || useless[2].equals("1")) return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean hasNext() {
        return cursor < calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Boolean hasPrevious() {
        return cursor > 1;
    }

    @Override
    public String getCursor() {
        if (cursor >= 1 && cursor < 10) {
            return "0"+cursor;
        }
        return cursor+"";
    }

    @Override
    public void cursorUp() {
        cursor++;
    }

    @Override
    public void cursorDown() {
        cursor--;
    }

    @Override
    public String getCurrentMonth() {
        String answer = calendar.get(Calendar.MONTH) + 1 + "";
        if ((calendar.get(Calendar.MONTH) + 1 + "").length() == 1) {
            answer = "0" + answer;
        }

        return answer + "." + calendar.get(Calendar.YEAR);
    }

    class AsyncTaskStudentFood extends AsyncTask<Void, Void, LinkedList<String[]>> {

        com.example.myfood.components.studentFood.backstage.callBackinterface callback;
        String token;
        ProgressDialog progressDialog;

        public AsyncTaskStudentFood(com.example.myfood.components.studentFood.backstage.callBackinterface callback, String token) {
            this.callback = callback;
            this.token = token;
        }

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
        protected void onPostExecute(LinkedList<String[]> treeMaps) {
            super.onPostExecute(treeMaps);
            data = treeMaps;
            progressDialog.dismiss();
            callback.showData();
        }

        @Override
        protected LinkedList<String[]> doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.getStudentFood(token);

        }
    }

    class AsyncTaskStudentSendFood extends AsyncTask<Void, Void, Integer> {

        String token;
        String breakfast, teatime, lunch;
        ProgressDialog progressDialog;

        public AsyncTaskStudentSendFood(String token, String breakfast, String teatime, String lunch) {
            this.token = token;
            this.breakfast = breakfast;
            this.teatime = teatime;
            this.lunch = lunch;
        }

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
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            if (integer == 107) {
                ((MenuContract) view).showActivity(CheckActivity.class);
            }
            callBackinterface.afterSendingFood();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.sendStudentFood(breakfast, teatime, lunch, token, (StudentFoodContract.View) view);

        }
    }

}
