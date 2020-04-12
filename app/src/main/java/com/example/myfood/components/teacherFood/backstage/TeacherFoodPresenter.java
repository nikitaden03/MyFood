package com.example.myfood.components.teacherFood.backstage;

import android.util.Log;

import com.example.myfood.abstracts.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.TreeMap;

public class TeacherFoodPresenter extends BasePresenter implements TeacherFoodContract.Presenter  {

    private int breakfast, teatime, lunch;
    private Calendar calendar;
    private LinkedList<TreeMap<String, String[]>> data;
    int cursor;


    public TeacherFoodPresenter () {
        calendar = new GregorianCalendar();
        //TODO Сделать запрос в дирикторию data, чтобы получить TreeMap
        data = getFakeData();
        cursor = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("MYTAG", cursor+"");
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
        String answer = calendar.get(Calendar.MONTH) + "";
        if ((calendar.get(Calendar.MONTH) + "").length() == 1) {
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

    private LinkedList<TreeMap<String, String[]>> getFakeData(){

        TreeMap<String, String[]> treeMap = new TreeMap<>();
        treeMap.put("Иван Иванов", new String[]{"0", "1", "1"});
        treeMap.put("Иван Иванов1", new String[]{"1", "1", "1"});
        treeMap.put("Иван Иванов2", new String[]{"1", "0", "1"});
        treeMap.put("Иван Иванов3", new String[]{"0", "0", "1"});
        treeMap.put("Иван Иванов4", new String[]{"0", "0", "1"});
        treeMap.put("Иван Иванов5", new String[]{"0", "0", "0"});
        treeMap.put("Иван Иванов6", new String[]{"1", "0", "0"});
        treeMap.put("Иван Иванов7", new String[]{"0", "1", "1"});
        treeMap.put("Иван Иванов8", new String[]{"0", "0", "1"});
        treeMap.put("Иван Иванов9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap1 = new TreeMap<>();
        treeMap1.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap1.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap1.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap1.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap1.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap1.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap1.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap1.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap1.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap1.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap2 = new TreeMap<>();
        treeMap2.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap2.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap2.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap2.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap2.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap2.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap2.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap2.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap2.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap2.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap3 = new TreeMap<>();
        treeMap3.put("Иван Иванов", new String[]{"0", "1", "1"});
        treeMap3.put("Иван Иванов1", new String[]{"1", "1", "1"});
        treeMap3.put("Иван Иванов2", new String[]{"1", "0", "1"});
        treeMap3.put("Иван Иванов3", new String[]{"0", "0", "1"});
        treeMap3.put("Иван Иванов4", new String[]{"0", "0", "1"});
        treeMap3.put("Иван Иванов5", new String[]{"0", "0", "0"});
        treeMap3.put("Иван Иванов6", new String[]{"1", "0", "0"});
        treeMap3.put("Иван Иванов7", new String[]{"0", "1", "1"});
        treeMap3.put("Иван Иванов8", new String[]{"0", "0", "1"});
        treeMap3.put("Иван Иванов9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap4 = new TreeMap<>();
        treeMap4.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap4.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap4.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap4.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap4.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap4.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap4.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap4.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap4.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap4.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap5 = new TreeMap<>();
        treeMap5.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap5.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap5.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap5.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap5.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap5.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap5.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap5.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap5.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap5.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap6 = new TreeMap<>();
        treeMap6.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap6.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap6.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap6.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap6.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap6.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap6.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap6.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap6.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap6.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap7 = new TreeMap<>();
        treeMap7.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap7.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap7.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap7.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap7.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap7.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap7.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap7.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap7.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap7.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap8 = new TreeMap<>();
        treeMap8.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap8.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap8.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap8.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap8.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap8.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap8.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap8.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap8.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap8.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap9 = new TreeMap<>();
        treeMap9.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap9.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap9.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap9.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap9.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap9.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap9.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap9.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap9.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap9.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        TreeMap<String, String[]> treeMap10 = new TreeMap<>();
        treeMap10.put("Иван Сидоров", new String[]{"0", "1", "1"});
        treeMap10.put("Иван Сидоров1", new String[]{"1", "1", "1"});
        treeMap10.put("Иван Сидоров2", new String[]{"1", "0", "1"});
        treeMap10.put("Иван Сидоров3", new String[]{"0", "1", "1"});
        treeMap10.put("Иван Сидоров4", new String[]{"0", "0", "1"});
        treeMap10.put("Иван Сидоров5", new String[]{"0", "0", "0"});
        treeMap10.put("Иван Сидоров6", new String[]{"1", "0", "0"});
        treeMap10.put("Иван Сидоров7", new String[]{"0", "1", "1"});
        treeMap10.put("Иван Сидоров8", new String[]{"0", "0", "1"});
        treeMap10.put("Иван Сидоров9", new String[]{"0", "1", "1"});

        LinkedList<TreeMap<String, String[]>> fakeData = new LinkedList<>();
        fakeData.add(treeMap);
        fakeData.add(treeMap1);
        fakeData.add(treeMap2);
        fakeData.add(treeMap3);
        fakeData.add(treeMap4);
        fakeData.add(treeMap5);
        fakeData.add(treeMap6);
        fakeData.add(treeMap7);
        fakeData.add(treeMap8);
        fakeData.add(treeMap9);
        fakeData.add(treeMap10);
        fakeData.add(treeMap);

        return fakeData;
    }
}
