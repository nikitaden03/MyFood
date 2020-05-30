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
    private callBackinterface callBackinterface;
    int cursor;


    public TeacherFoodPresenter () {
        calendar = new GregorianCalendar();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("MYTAG", cursor+"");
    }

    @Override
    public void prepareData(){
        callBackinterface = (callBackinterface) view;

        if (data == null) {
            SharedPreferences sharedPreferences = ((TeacherFoodContract.View)view).getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            AsyncTaskTeacherFood taskTeacherFood = new AsyncTaskTeacherFood(callBackinterface, token);
            taskTeacherFood.execute();
        } else {
            callBackinterface.showData();
        }
    }

    @Override
    public ArrayList<String[]> getData(){
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

        callBackinterface callback;
        String token;
        ProgressDialog progressDialog;

        public AsyncTaskTeacherFood(callBackinterface callback, String token) {
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
        protected void onPostExecute(LinkedList<TreeMap<String, String[]>> treeMaps) {
            super.onPostExecute(treeMaps);
            data = treeMaps;
            progressDialog.dismiss();
            callback.showData();
        }

        @Override
        protected LinkedList<TreeMap<String, String[]>> doInBackground(Void... voids) {
            Data data = Data.getInstance();
            return data.getTeacherFood(token);
        }
    }

}

