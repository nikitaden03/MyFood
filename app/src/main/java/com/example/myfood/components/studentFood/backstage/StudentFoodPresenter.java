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
    private AsyncCallBack callBackInterface;
    private int cursor;

    public StudentFoodPresenter() {
        calendar = new GregorianCalendar();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Либо возращает данные, если они уже скачены, либо скачивает данные и затем возращает
    @Override
    public void prepareData() {
        callBackInterface = (AsyncCallBack) view;

        if (data == null) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            AsyncTaskGetFood taskTeacherFood = new AsyncTaskGetFood(callBackInterface, token);
            taskTeacherFood.execute();
        } else {
            callBackInterface.showData();
        }
    }

    // Возращает историю на нужный день, так как data - история в течении 1 месяца
    @Override
    public String[] getData(){
        return data.get(cursor - 1);
    }

    // Отправляет заявку пользователя на сервер
    @Override
    public void sendFood(String breakfast, String teatime, String lunch) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        AsyncTaskStudentSendFood asyncTaskStudentSendFood = new AsyncTaskStudentSendFood(token, breakfast, teatime, lunch);
        asyncTaskStudentSendFood.execute();
    }

    // Проверяет отправлял ли пользователь сегодня заявку на питание
    @Override
    public Boolean hasTodayData() {
        try {
            String[] useless = data.get(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            return useless[0].equals("1") || useless[1].equals("1") || useless[2].equals("1");
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

    // Возвращает номер дня в формате 00 (01, 02, 10, 12 и т.д)
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

    // Возвращает номер месяца и года в формате 00.0000 (01.2020, 02.2020, 10.2020, 12.2020 и т.д)
    @Override
    public String getCurrentMonthAndYear() {
        String answer = calendar.get(Calendar.MONTH) + 1 + "";
        if ((calendar.get(Calendar.MONTH) + 1 + "").length() == 1) {
            answer = "0" + answer;
        }

        return answer + "." + calendar.get(Calendar.YEAR);
    }

    class AsyncTaskGetFood extends AsyncTask<Void, Void, LinkedList<String[]>> {

        AsyncCallBack callback;
        String token;
        ProgressDialog progressDialog;

        AsyncTaskGetFood(AsyncCallBack callback, String token) {
            this.callback = callback;
            this.token = token;
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

        // Удаляет ProgressDialog и возвращает дату в activity через AsyncCallBack
        @Override
        protected void onPostExecute(LinkedList<String[]> treeMaps) {
            super.onPostExecute(treeMaps);
            data = treeMaps;
            progressDialog.dismiss();
            callback.showData();
        }

        // В отдельном потоке вызывает функцию класса Data, которая возращает история за 1 месяц (если, допустим, сегодня 25, значит с 1 по 25)
        @Override
        protected LinkedList<String[]> doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.getStudentFood(token, view);

        }
    }

    class AsyncTaskStudentSendFood extends AsyncTask<Void, Void, Integer> {

        String token;
        String breakfast, teatime, lunch;
        ProgressDialog progressDialog;

        AsyncTaskStudentSendFood(String token, String breakfast, String teatime, String lunch) {
            this.token = token;
            this.breakfast = breakfast;
            this.teatime = teatime;
            this.lunch = lunch;
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

        // Удаляет ProgressDialog и, если код ответа 107 (неверный токен (такое могло произойти, если пользователь зашел на другм устройстве), перенаправляет пользователя на проверку токена (а он уже обнулен)
        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
            if (integer == 107) {
                ((MenuContract) view).showActivity(CheckActivity.class);
            }
            callBackInterface.afterSendingFood();
        }

         // В отдельном потоке вызывает функцию класса Data, которая отправляет заявку от пользователя на сегодня.
        @Override
        protected Integer doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.sendStudentFood(breakfast, teatime, lunch, token, view);

        }
    }

}
