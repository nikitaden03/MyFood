package com.example.myfood.components.studentFood.backstage;

import com.example.myfood.abstracts.presenter.BasePresenter;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class StudentFoodPresenter extends BasePresenter implements StudentFoodContract.Presenter {

    private Calendar calendar;
    private List<String[]> data;
    int cursor;

    public StudentFoodPresenter() {
        calendar = new GregorianCalendar();
        //TODO Сделать запрос в дирикторию data, чтобы получить TreeMap
        data = getFakeData();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public String[] getData(){
        return data.get(cursor - 1);
    }

    @Override
    public void sendFood(String breakfast, String teatime, String lunch) {

    }

    @Override
    public Boolean hasTodayData() {
        try {
            String[] useless = data.get(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            return true;
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
        String answer = calendar.get(Calendar.MONTH) + "";
        if ((calendar.get(Calendar.MONTH) + "").length() == 1) {
            answer = "0" + answer;
        }

        return answer + "." + calendar.get(Calendar.YEAR);
    }

    LinkedList<String[]> getFakeData(){

        LinkedList<String[]> linkedList = new LinkedList<>();

        linkedList.add(new String[]{"1", "1", "1"});
        linkedList.add(new String[]{"1", "0", "1"});
        linkedList.add(new String[]{"0", "1", "1"});
        linkedList.add(new String[]{"1", "0", "1"});
        linkedList.add(new String[]{"1", "1", "0"});
        linkedList.add(new String[]{"1", "0", "0"});
        linkedList.add(new String[]{"0", "1", "0"});
        linkedList.add(new String[]{"1", "1", "1"});
        linkedList.add(new String[]{"0", "1", "0"});
        linkedList.add(new String[]{"0", "1", "1"});
        linkedList.add(new String[]{"1", "1", "1"});

        return linkedList;
    }

}
