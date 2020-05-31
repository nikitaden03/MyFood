package com.example.myfood.components.teacherFood.backstage;

import android.content.SharedPreferences;

import com.example.myfood.abstracts.presenter.BasePresenter;

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
    private AsyncCallBack asyncCallBack;
    int cursor;


    public TeacherFoodPresenter () {
        calendar = new GregorianCalendar();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Либо возращает данные, если они уже скачены, либо скачивает данные и затем возращает
    @Override
    public void prepareData(){
        asyncCallBack = (AsyncCallBack) view;

        if (data == null) {
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("token", MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            AsyncGetTeacherFood asyncGetTeacherFood = new AsyncGetTeacherFood(asyncCallBack, token, view);
            asyncGetTeacherFood.execute();
        } else {
            asyncCallBack.showData();
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

    public void setData(LinkedList<TreeMap<String, String[]>> data) {
        this.data = data;
    }
}

