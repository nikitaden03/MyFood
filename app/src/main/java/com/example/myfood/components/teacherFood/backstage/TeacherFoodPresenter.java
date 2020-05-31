package com.example.myfood.components.teacherFood.backstage;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myfood.abstracts.presenter.BasePresenter;
import com.example.myfood.data.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.TreeMap;

import static android.content.Context.MODE_PRIVATE;

public class TeacherFoodPresenter extends BasePresenter implements TeacherFoodContract.Presenter  {

    private int breakfast, teatime, lunch;
    private Calendar calendar;
    private LinkedList<TreeMap<String, String[]>> data;
    private AsyncCallBack AsyncCallBack;
    int cursor;


    public TeacherFoodPresenter () {
        calendar = new GregorianCalendar();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Либо возращает данные, если они уже скачены, либо скачивает данные и затем возращает
    @Override
    public void prepareData(){
        AsyncCallBack = (AsyncCallBack) view;

        if (data == null) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            AsyncTaskTeacherFood taskTeacherFood = new AsyncTaskTeacherFood(AsyncCallBack, token);
            taskTeacherFood.execute();
        } else {
            AsyncCallBack.showData();
        }
    }

    // Возращает историю на нужный день, так как data - история в течении 1 месяца
    @Override
    public ArrayList<String[]> getData(){
        // Если время меньш 8:00
        if (cursor == calendar.get(Calendar.DAY_OF_MONTH) && calendar.get(Calendar.HOUR_OF_DAY) < 8) {
            ArrayList<String[]> arrayList = new ArrayList<>();
            arrayList.add(new String[]{"0"});
            return arrayList;
        }
        breakfast = 0;
        teatime = 0;
        lunch = 0;
        TreeMap<String, String[]> treeMap = data.get(cursor - 1);
        ArrayList<String[]> arrayList = new ArrayList<>();
        // Если в этот день нет заявок от учеников
        if (treeMap.size() == 0) {
            arrayList.add(new String[]{"5"});
        }
        for (String i: treeMap.keySet()) {
            String[] arr = new String[4];
            arr[0] = i;
            arr[1] = treeMap.get(i)[0];
            arr[2] = treeMap.get(i)[1];
            arr[3] = treeMap.get(i)[2];
            arrayList.add(arr);
            if (treeMap.get(i)[0].equals("1")) breakfast++;
            if (treeMap.get(i)[1].equals("1")) teatime++;
            if (treeMap.get(i)[2].equals("1")) lunch++;
        }
        return arrayList;
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
        if ((calendar.get(Calendar.MONTH) + 1 +  "").length() == 1) {
            answer = "0" + answer;
        }

        return answer + "." + calendar.get(Calendar.YEAR);
    }


    public int getBreakfast() {
        return breakfast;
    }

    public int getTeatime() {
        return teatime;
    }

    public int getLunch() {
        return lunch;
    }

    class AsyncTaskTeacherFood extends AsyncTask<Void, Void, LinkedList<TreeMap<String, String[]>>> {

        AsyncCallBack callback;
        String token;
        ProgressDialog progressDialog;

        public AsyncTaskTeacherFood(AsyncCallBack callback, String token) {
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
        protected void onPostExecute(LinkedList<TreeMap<String, String[]>> treeMaps) {
            super.onPostExecute(treeMaps);
            data = treeMaps;
            progressDialog.dismiss();
            callback.showData();
        }

        // В отдельном потоке вызывает функцию класса Data, которая возращает история за 1 месяц (если, допустим, сегодня 25, значит с 1 по 25)
        @Override
        protected LinkedList<TreeMap<String, String[]>> doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.getTeacherFood(token, view);
        }
    }

}

