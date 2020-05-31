package com.example.myfood.components.studentFood.backstage;

import android.content.SharedPreferences;

import com.example.myfood.abstracts.presenter.BasePresenter;

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
            AsyncGetStudentFood asyncGetStudentFood = new AsyncGetStudentFood(callBackInterface, token, view);
            asyncGetStudentFood.execute();
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
        AsyncSendStudentFood asyncSendStudentFood = new AsyncSendStudentFood(token, breakfast, teatime, lunch, view, callBackInterface);
        asyncSendStudentFood.execute();
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

    public void setData(LinkedList<String[]> list) {
        data = list;
    }
}
